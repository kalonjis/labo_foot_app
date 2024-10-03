package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.events.ScoreUpdateEvent;
import com.labospring.LaboFootApp.bll.events.StatusUpdateEvent;
import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.exceptions.FootMatchNeedWinnerException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectMatchStatusException;
import com.labospring.LaboFootApp.bll.service.*;
import com.labospring.LaboFootApp.bll.service.models.FootMatchEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.FootMatchCreateBusiness;
import com.labospring.LaboFootApp.bll.service.models.FootMatchSpecificationDTO;
import com.labospring.LaboFootApp.bll.service.models.ScoreBusiness;
import com.labospring.LaboFootApp.bll.specification.FootMatchSpecification;
import com.labospring.LaboFootApp.dal.repositories.FootMatchRepository;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.enums.MatchStage;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import com.labospring.LaboFootApp.il.utils.ChampionshipCalendarGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FootMatchServiceImpl implements FootMatchService {

    private final FootMatchRepository footMatchRepository;
    private final TeamService teamService;
    private final RefereeService refereeService;
    private final TournamentService tournamentService;
    private final ValidMatchService validMatchService;
    private final BracketService bracketService;
    private final RankingService rankingService;
    private final UserService userService;
    private final ParticipatingTeamService participatingTeamService;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional
    public Long addOne(FootMatchEditBusiness entityBusiness) {

        FootMatch footMatch= turnIntoFootMatch(entityBusiness);
        if(!validMatchService.isValid(footMatch))
            throw new RuntimeException("Not valid Match");

        footMatch.setMatchStatus(MatchStatus.SCHEDULED);
        return footMatchRepository.save(footMatch).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public FootMatch getOne(Long id) {
        return footMatchRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Football Match with ID : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FootMatch> getAll() {
        return footMatchRepository.findAll();
    }

    @Override
    public List<FootMatch> findAllByUser(User user) {
        return footMatchRepository.findAllByUser(user);
    }

    @Override
    @Transactional
    public void deleteOne(Long id) {
        FootMatch footMatch = getOne(id);
        footMatchRepository.delete(footMatch);
    }

    @Override
    @Transactional
    public void updateOne(Long id, FootMatchEditBusiness entityBusiness) {
        FootMatch footMatch = getOne(id);
        FootMatch footMatchUpdated= turnIntoFootMatch(entityBusiness);

        footMatch.setMatchStage(footMatchUpdated.getMatchStage());
        footMatch.setMatchDateTime(footMatchUpdated.getMatchDateTime());
        footMatch.setFieldLocation(entityBusiness.getFieldLocation());
        footMatch.setReferee(footMatchUpdated.getReferee());
        footMatch.setTeamHome(footMatchUpdated.getTeamHome());
        footMatch.setTeamAway(footMatchUpdated.getTeamAway());

        if(!validMatchService.isValid(footMatch))
            throw new RuntimeException("Not valid Match");

        footMatchRepository.save(footMatch);
    }

    @Override
    @Transactional
    public void changeStatus(Long id, MatchStatus newStatus) {
        FootMatch footMatch = getOne(id);
        MatchStatus currentStatus = footMatch.getMatchStatus();

        // Validation des transitions de statut
        if (!isValidMatchStatusTransition(currentStatus, newStatus)) {
            throw new IncorrectMatchStatusException(
                    String.format("Cannot change match status from %s to %s", currentStatus, newStatus), 409
            );
        }

        // Mettre à jour les effets en fonction du nouveau statut
        if (footMatch.getMatchStage() == MatchStage.GROUP_STAGE) {
            Long tournamentId = footMatch.getTournament().getId();
            Team teamHome = footMatch.getTeamHome();
            Team teamAway = footMatch.getTeamAway();
            Ranking rankingTeamHome = rankingService.getByTournamentIdAndTeamId(tournamentId, teamHome.getId());
            Ranking rankingTeamAway = rankingService.getByTournamentIdAndTeamId(tournamentId, teamAway.getId());

            if (newStatus == MatchStatus.INPROGRESS && currentStatus == MatchStatus.SCHEDULED) {
                rankingService.updateStartingMatch(rankingTeamHome, rankingTeamAway);
                rankingService.updatePosition(rankingTeamHome);
            }
        }

        if (newStatus == MatchStatus.FINISHED) {
            updateBracket(footMatch);
        }

        // Mise à jour du statut du match
        footMatch.setMatchStatus(newStatus);
        footMatchRepository.save(footMatch);
        if (newStatus == MatchStatus.FINISHED || newStatus == MatchStatus.INPROGRESS) {
            eventPublisher.publishEvent(new StatusUpdateEvent(this, footMatch));
        }
    }

    // Méthode pour valider les transitions de statut de match
    private boolean isValidMatchStatusTransition(MatchStatus currentStatus, MatchStatus newStatus) {
        switch (currentStatus) {
            case SCHEDULED:
                return newStatus == MatchStatus.INPROGRESS || newStatus == MatchStatus.CANCELED;
            case INPROGRESS:
                return newStatus == MatchStatus.FINISHED || newStatus == MatchStatus.INTERRUPTED || newStatus == MatchStatus.CANCELED;
            case FINISHED:
                return false; // Un match terminé ne peut pas changer de statut
            case INTERRUPTED:
                return newStatus == MatchStatus.INPROGRESS || newStatus == MatchStatus.CANCELED;
            case CANCELED:
                return false; // Un match annulé ne peut pas changer de statut
            default:
                return false;
        }
    }

    private void updateBracket(FootMatch footMatch) {
        Integer positionBracket = bracketService.getBracketPosition(footMatch);
        if(positionBracket != null){
            FootMatch nextMatch = footMatch.getNextMatch();
            Team winnerTeam = getWinnerTeam(footMatch);
            if(winnerTeam == null)
                throw new FootMatchNeedWinnerException("Need a winner with bracket");

            if(positionBracket % 2 == 0)
                nextMatch.setTeamAway(winnerTeam);
            else
                nextMatch.setTeamHome(winnerTeam);

            footMatchRepository.save(nextMatch);
        }
    }

    @Override
    @Transactional
    public void changeScore(Long id, ScoreBusiness scoreBusiness) {
        FootMatch footMatch = getOne(id);
        if (footMatch.getMatchStatus() == MatchStatus.SCHEDULED) {
            throw new IncorrectMatchStatusException("Cannot change score for a match that has not started yet.", 409);
        }

        if (footMatch.getMatchStage() == MatchStage.GROUP_STAGE) {
            Long tournamentId = footMatch.getTournament().getId();
            Team teamHome = footMatch.getTeamHome();
            Team teamAway = footMatch.getTeamAway();
            Ranking rankingTeamHome = rankingService.getByTournamentIdAndTeamId(tournamentId, teamHome.getId());
            Ranking rankingTeamAway = rankingService.getByTournamentIdAndTeamId(tournamentId, teamAway.getId());
            int existingScoreTeamHome = footMatch.getScoreTeamHome();
            int existingScoreTeamAway = footMatch.getScoreTeamAway();

            if (existingScoreTeamHome != scoreBusiness.scoreHome()) {
                rankingService.updateGoalsFor(rankingTeamHome, scoreBusiness.scoreHome() - existingScoreTeamHome);
                rankingService.updateGoalsAgainst(rankingTeamAway, scoreBusiness.scoreHome() - existingScoreTeamHome);
            }
            if (existingScoreTeamAway != scoreBusiness.scoreAway()) {
                rankingService.updateGoalsFor(rankingTeamAway, scoreBusiness.scoreAway() - existingScoreTeamAway);
                rankingService.updateGoalsAgainst(rankingTeamHome, scoreBusiness.scoreAway() - existingScoreTeamAway);
            }

            if(existingScoreTeamHome == existingScoreTeamAway) {
                // les equipes faisaient match nul et la teamHome passe devant
                if (scoreBusiness.scoreHome() > scoreBusiness.scoreAway()) {
                    rankingService.updateGettingWinner(rankingTeamHome, rankingTeamAway);
                }
                // les equipes faisaient match nul et la teamAway passe devant
                if (scoreBusiness.scoreAway()> scoreBusiness.scoreHome()){
                    rankingService.updateGettingWinner(rankingTeamAway, rankingTeamHome);
                }
            }

            if(existingScoreTeamHome > existingScoreTeamAway){
                // L'equipe teamHome gagnait et se fait rejoindre au score
                if(scoreBusiness.scoreHome() == scoreBusiness.scoreAway()){
                    rankingService.updateGettingDrawer(rankingTeamHome, rankingTeamAway);
                }
                // L'equipe teamHome gagnait et perd (dans le cas où il n'y a pas eu d'étape d'égalisation
                if(scoreBusiness.scoreHome() < scoreBusiness.scoreAway()){
                    rankingService.updateGettingWinnerFromLoser(rankingTeamAway, rankingTeamHome);
                }
            }

            if(existingScoreTeamAway > existingScoreTeamHome){
                // L'equipe teamAway gagnait et se fait rejoindre au score
                if (scoreBusiness.scoreAway() == scoreBusiness.scoreHome()){
                    rankingService.updateGettingDrawer(rankingTeamAway, rankingTeamHome);
                }
                // L'equipe teamHome gagnait et perd (dans le cas où il n'y a pas eu d'étape d'égalisation
                if(scoreBusiness.scoreAway() < scoreBusiness.scoreHome()){
                    rankingService.updateGettingWinnerFromLoser(rankingTeamHome, rankingTeamAway);
                }
            }

            rankingService.updatePosition(rankingTeamHome); // choix arbitraire du ranking car cela va affecter tous les ranking du groupe
        }

        if(haveDifferentScore(scoreBusiness, footMatch)){
            footMatch.setScoreTeamHome(scoreBusiness.scoreHome());
            footMatch.setScoreTeamAway(scoreBusiness.scoreAway());
            footMatchRepository.save(footMatch);
            // Publish the event to trigger WebSocket notifications
            eventPublisher.publishEvent(new ScoreUpdateEvent(this, footMatch));
        }

    }

    private static boolean haveDifferentScore(ScoreBusiness scoreBusiness, FootMatch footMatch) {
        return footMatch.getScoreTeamHome() != scoreBusiness.scoreHome() ||
                footMatch.getScoreTeamAway() != scoreBusiness.scoreAway();
    }

    @Override
    @Transactional
    public void changeModerator(Long id, Long moderatorId){
        FootMatch footMatch = getOne(id);

        footMatch.setUserModerator(userService.getOne(moderatorId));

        footMatchRepository.save(footMatch);
    }


    @Override
    public List<FootMatch> getByCriteria(FootMatchSpecificationDTO footMatch) {
        Specification<FootMatch> specification = Specification.where(null);

        if(footMatch.team1() != null && !footMatch.team1().isEmpty()){
            specification = specification.and(FootMatchSpecification.hasTeamName(footMatch.team1()));
        }

        if(footMatch.team2() != null && !footMatch.team2().isEmpty()){
            specification = specification.and(FootMatchSpecification.hasTeamName(footMatch.team2()));
        }
        if(footMatch.after() != null){
            specification = specification.and(FootMatchSpecification.hasMatchDateTimeAfter(footMatch.after()));
        }
        if(footMatch.before() != null){
            specification = specification.and(FootMatchSpecification.hasMatchDateTimeBefore(footMatch.before()));
        }
        if(footMatch.fieldLocation() != null && !footMatch.fieldLocation().isEmpty()){
            specification = specification.and(FootMatchSpecification.hasFieldLocation(footMatch.fieldLocation()));
        }

        if(footMatch.referee() != null && !footMatch.referee().isEmpty()){
            specification = specification.and(FootMatchSpecification.hasRefereeName(footMatch.referee()));
        }
        if(footMatch.tournament_title() != null && !footMatch.tournament_title().isEmpty()){
            specification = specification.and(FootMatchSpecification.hasTournament(footMatch.tournament_title()));
        }


        return footMatchRepository.findAll(specification);
    }

    private FootMatch turnIntoFootMatch(FootMatchEditBusiness entityBusiness){
        if(Objects.equals(entityBusiness.getTeamHomeId(), entityBusiness.getTeamAwayId()))
            throw new RuntimeException("Teams can't be the same");
        Team teamHome = teamService.getOne(entityBusiness.getTeamHomeId());
        Team teamAway = teamService.getOne(entityBusiness.getTeamAwayId());

        Tournament tournament = entityBusiness instanceof FootMatchCreateBusiness ?
                tournamentService.getOne(((FootMatchCreateBusiness) entityBusiness).getTournamentId()) : null;

        Referee referee = null;
        if(entityBusiness.getRefereeId() != null)
            referee = refereeService.getOne(entityBusiness.getRefereeId());

        return entityBusiness.toEntity(teamHome, teamAway, tournament, referee);
    }

    private Team getWinnerTeam(FootMatch footMatch){
        return footMatch.getScoreTeamHome() > footMatch.getScoreTeamAway() ? footMatch.getTeamHome() :
                footMatch.getScoreTeamAway() > footMatch.getScoreTeamHome() ? footMatch.getTeamAway() :
                        null;
    }

    @Override
    public void buildChampionshipCalendar(Long tournamentId){
        Tournament tournament = tournamentService.getOne(tournamentId);
        List<FootMatch> matches = ChampionshipCalendarGenerator.generateMatchSchedule(tournament, participatingTeamService);
        for (FootMatch match: matches) {

            footMatchRepository.save(match);
        }
    }

}
