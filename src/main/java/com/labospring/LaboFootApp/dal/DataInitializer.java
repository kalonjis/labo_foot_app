package com.labospring.LaboFootApp.dal;

import com.labospring.LaboFootApp.dal.repositories.*;
import com.labospring.LaboFootApp.dl.entities.*;

import java.util.*;

import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import com.labospring.LaboFootApp.dl.enums.Role;
import com.labospring.LaboFootApp.dl.enums.TournamentType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final CoachRepository coachRepository;
    private final RefereeRepository refereeRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final ParticipatingTeamRepository participatingTeamRepository;
    private final FootMatchRepository footMatchRepository;


    // Méthode pour choisir une équipe aléatoirement
    public Team getRandomTeam(List<Team> teams) {
        Random random = new Random();
        return teams.get(random.nextInt(teams.size()));
    }

    @Override
    public void run(String... args) throws Exception {

        // region  User
        User user1 = new User(null, "steby81", "test123", "Steph", "Kal", "kalonj1981@hotmail.com", LocalDate.of(1981, 5, 12), "0498123456", new Address("123 Main St", "Brussels", "1000", "Brussels", "Belgium"), Role.ADMIN);
        User user2 = new User(null, "gabinho", "test123", "Jean", "Gabin", "kalonjis@outlook.com", LocalDate.of(1990, 3, 25), "0498765432", new Address("456 Elm St", "Antwerp", "2000", "Antwerp", "Belgium"), Role.ADMIN);
        User user3 = new User(null, "homerSps", "test123", "Homer", "Simpson", "kalonji_s@skynet.be", LocalDate.of(1978, 6, 15), "0476987654", new Address("789 Maple St", "Liège", "4000", "Liège", "Belgium"), Role.USER);
        User user4 = new User(null, "margeSimps", "test123", "Marge", "Simpson", "marge.s@springfield.com", LocalDate.of(1975, 10, 1), "0466554433", new Address("123 Springfield Ave", "Springfield", "5000", "Illinois", "USA"), Role.USER);
        User user5 = new User(null, "bartSimps", "test123", "Bart", "Simpson", "bart.s@springfield.com", LocalDate.of(2005, 4, 1), "0456789123", new Address("456 Springfield Ave", "Springfield", "5001", "Illinois", "USA"), Role.USER);
        User user6 = new User(null, "lisaSimps", "test123", "Lisa", "Simpson", "lisa.s@springfield.com", LocalDate.of(2007, 5, 9), "0478996453", new Address("789 Springfield Ave", "Springfield", "5002", "Illinois", "USA"), Role.USER);
        User user7 = new User(null, "maggySimps", "test123", "Maggy", "Simpson", "maggy.s@springfield.com", LocalDate.of(2010, 8, 12), "0487654321", new Address("101 Springfield Ave", "Springfield", "5003", "Illinois", "USA"), Role.USER);
        User user8 = new User(null, "nedFlanders", "test123", "Ned", "Flanders", "ned.f@springfield.com", LocalDate.of(1965, 2, 27), "0496453123", new Address("111 Oak St", "Springfield", "5004", "Illinois", "USA"), Role.USER);
        User user9 = new User(null, "moeSzyslak", "test123", "Moe", "Szyslak", "moe.s@springfield.com", LocalDate.of(1970, 11, 15), "0467123890", new Address("222 Bar St", "Springfield", "5005", "Illinois", "USA"), Role.USER);
        User user10 = new User(null, "apuNahasapeemapetilon", "test123", "Apu", "Nahasapeemapetilon", "apu.n@kwikemart.com", LocalDate.of(1968, 10, 5), "0467001112", new Address("333 Kwik-E-Mart", "Springfield", "5006", "Illinois", "USA"), Role.USER);

        List<User> users = List.of(
                user1, user2, user3, user4, user5, user6, user7, user8, user9, user10
        );

        if (teamRepository.count() == 0) {
            userRepository.saveAll(users);
        }
        //endregion

        // region Team
        Team team1 = new Team(null, "Manchester United", new Coach("Alex", "Ferguson"), new HashSet<>());
        Team team2 = new Team(null, "Real Madrid", new Coach("Zinedine", "Zidane"), new HashSet<>());
        Team team3 = new Team(null, "FC Barcelona", new Coach("Pep", "Guardiola"), new HashSet<>());
        Team team4 = new Team(null, "Liverpool FC", new Coach("Jurgen", "Klopp"), new HashSet<>());
        Team team5 = new Team(null, "Bayern Munich", new Coach("Carlo", "Ancelotti"), new HashSet<>());
        Team team6 = new Team(null, "Juventus FC", new Coach("Massimiliano", "Allegri"), new HashSet<>());
        Team team7 = new Team(null, "Paris Saint-Germain", new Coach("Mauricio", "Pochettino"), new HashSet<>());
        Team team8 = new Team(null, "Chelsea FC", new Coach("Thomas", "Tuchel"), new HashSet<>());
        Team team9 = new Team(null, "Atletico Madrid", new Coach("Diego", "Simeone"), new HashSet<>());
        Team team10 = new Team(null, "Inter Milan", new Coach("Simone", "Inzaghi"), new HashSet<>());
        // 6 équipes sans joueurs
        Team team11 = new Team(null, "AC Milan", new Coach("Pippo", "Inzaghi"), new HashSet<>());
        Team team12 = new Team(null, "Tottenham Hotspur", new Coach("Antonio", "Conte"), new HashSet<>());
        Team team13 = new Team(null, "AS Roma", new Coach("Marcelo", "Bielsa"), new HashSet<>());
        Team team14 = new Team(null, "Borussia Dortmund", new Coach("Thomas", "Tuchel"), new HashSet<>());
        Team team15 = new Team(null, "Olympique Lyonnais", new Coach("Roberto", "Mancini"), new HashSet<>());
        Team team16 = new Team(null, "Ajax Amsterdam", new Coach("Luis", "Enrique"), new HashSet<>());

        List<Team> teams = List.of(
                team1, team2, team3, team4, team5, team6, team7, team8, team9, team10, team11, team12, team13, team14, team15, team16
        );

        if (teamRepository.count() == 0) {
            teamRepository.saveAll(teams);
        }
        //endregion

        // region Player
        Player player1 = new Player(null, "Cristiano", "Ronaldo", "CR7", 7, FieldPosition.FORWARD);
        Player player2 = new Player(null, "Marcus", "Rashford", "Rashy", 10, FieldPosition.FORWARD);
        Player player3 = new Player(null, "Karim", "Benzema", "Benz", 9, FieldPosition.FORWARD);
        Player player4 = new Player(null, "Luka", "Modric", "Modric", 10, FieldPosition.MIDFIELDER);
        Player player5 = new Player(null, "Lionel", "Messi", "Messi", 10, FieldPosition.FORWARD);
        Player player6 = new Player(null, "Gerard", "Pique", "Pique", 3, FieldPosition.DEFENDER);
        Player player7 = new Player(null, "Mohamed", "Salah", "Salah", 11, FieldPosition.FORWARD);
        Player player8 = new Player(null, "Virgil", "van Dijk", "VVD", 4, FieldPosition.DEFENDER);
        Player player9 = new Player(null, "Robert", "Lewandowski", "Lewy", 9, FieldPosition.FORWARD);
        Player player10 = new Player(null, "Joshua", "Kimmich", "Kimmich", 6, FieldPosition.MIDFIELDER);
        Player player11 = new Player(null, "Paulo", "Dybala", "Dybala", 10, FieldPosition.FORWARD);
        Player player12 = new Player(null, "Leonardo", "Bonucci", "Bonucci", 19, FieldPosition.DEFENDER);
        Player player13 = new Player(null, "Kylian", "Mbappe", "Mbappe", 7, FieldPosition.FORWARD);
        Player player14 = new Player(null, "Neymar", "Junior", "Neymar", 10, FieldPosition.FORWARD);
        Player player15 = new Player(null, "Mason", "Mount", "Mount", 19, FieldPosition.MIDFIELDER);
        Player player16 = new Player(null, "Thiago", "Silva", "T.Silva", 6, FieldPosition.DEFENDER);
        Player player17 = new Player(null, "Luis", "Suarez", "El Pistolero", 9, FieldPosition.FORWARD);
        Player player18 = new Player(null, "Jan", "Oblak", "Oblak", 1, FieldPosition.GOALKEEPER);
        Player player19 = new Player(null, "Romelu", "Lukaku", "Lukaku", 9, FieldPosition.FORWARD);
        Player player20 = new Player(null, "Milan", "Skriniar", "Skriniar", 37, FieldPosition.DEFENDER);
        Player player21 = new Player(null, "Zlatan", "Ibrahimovic", "Zlatan", null, FieldPosition.FORWARD);
        Player player22 = new Player(null, "Son", "Heung-min", "Sonny", null, FieldPosition.FORWARD);
        Player player23 = new Player(null, "Henrikh", "Mkhitaryan", "Mkhitaryan", null, FieldPosition.MIDFIELDER);
        Player player24 = new Player(null, "Erling", "Haaland", "Haaland", null, FieldPosition.FORWARD);
        Player player25 = new Player(null, "Memphis", "Depay", "Memphis", null, FieldPosition.FORWARD);
        Player player26 = new Player(null, "Dusan", "Tadic", "Tadic", null, FieldPosition.MIDFIELDER);
        Player player27 = new Player(null, "Christian", "Eriksen", "Eriksen", null, FieldPosition.MIDFIELDER);
        Player player28 = new Player(null, "Eden", "Hazard", "Hazard", null, FieldPosition.FORWARD);
        Player player29 = new Player(null, "Kevin", "De Bruyne", "KDB", null, FieldPosition.MIDFIELDER);
        Player player30 = new Player(null, "Sadio", "Mane", "Mane", null, FieldPosition.FORWARD);
        Player player31 = new Player(null, "Fabinho", "Tavares", "Fabinho", null, FieldPosition.DEFENDER);
        Player player32 = new Player(null, "Antoine", "Griezmann", "Grizi", null, FieldPosition.FORWARD);
        Player player33 = new Player(null, "Jadon", "Sancho", "Sancho", null, FieldPosition.FORWARD);
        Player player34 = new Player(null, "Gareth", "Bale", "Bale", null, FieldPosition.FORWARD);
        Player player35 = new Player(null, "Alvaro", "Morata", "Morata", null, FieldPosition.FORWARD);
        Player player36 = new Player(null, "Marco", "Veratti", "Veratti", null, FieldPosition.MIDFIELDER);
        Player player37 = new Player(null, "David", "De Gea", "De Gea", null, FieldPosition.GOALKEEPER);
        Player player38 = new Player(null, "Cesar", "Azpilicueta", "Azpi", null, FieldPosition.DEFENDER);
        Player player39 = new Player(null, "Hakim", "Ziyech", "Ziyech", null, FieldPosition.MIDFIELDER);
        Player player40 = new Player(null, "N'Golo", "Kante", "Kante", null, FieldPosition.MIDFIELDER);

        List<Player> players = List.of(
                player1, player2, player3, player4, player5, player6, player7, player8, player9, player10,
                player11, player12, player13, player14, player15, player16, player17, player18, player19, player20,
                player21, player22, player23, player24, player25, player26, player27, player28, player29, player30,
                player31, player32, player33, player34, player35, player36, player37, player38, player39, player40
        );

        // Assigner les 30 premiers joueurs aux équipes correspondantes de manière aléatoire
        for (int i = 0; i < 30; i++) {
            Player player = players.get(i);
            Team randomTeam = getRandomTeam(List.of(team1, team2, team3, team4, team5, team6, team7, team8, team9, team10)); // Récupérer une équipe aléatoire
            player.setTeam(randomTeam);
            randomTeam.addPlayer(player); // Ajouter le joueur à l'équipe
        }

        if (playerRepository.count() == 0) {
            // Sauvegarder tous les joueurs
            playerRepository.saveAll(players);
        }
        //endregion

        // region Coach
        Coach coach1 = new Coach("Alex", "Ferguson");
        Coach coach2 = new Coach("Zinedine", "Zidane");
        Coach coach3 = new Coach("Pep", "Guardiola");
        Coach coach4 = new Coach("Jurgen", "Klopp");
        Coach coach5 = new Coach("Carlo", "Ancelotti");
        Coach coach6 = new Coach("Massimiliano", "Allegri");
        Coach coach7 = new Coach("Mauricio", "Pochettino");
        Coach coach8 = new Coach("Thomas", "Tuchel");
        Coach coach9 = new Coach("Diego", "Simeone");
        Coach coach10 = new Coach("Simone", "Inzaghi");
        Coach coach11 = new Coach("Pippo", "Inzaghi");
        Coach coach12 = new Coach("Antonio", "Conte");
        Coach coach13 = new Coach("Marcelo", "Bielsa");
        Coach coach14 = new Coach("Sergio", "Palavichino"); // Note: Duplication (change if needed)
        Coach coach15 = new Coach("Roberto", "Mancini");
        Coach coach16 = new Coach("Luis", "Enrique");

        List<Coach> coaches = List.of(
                coach1, coach2, coach3, coach4, coach5, coach6, coach7, coach8, coach9, coach10,
                coach11, coach12, coach13, coach14, coach15, coach16
        );

        if (coachRepository.count() == 0) {
            coachRepository.saveAll(coaches);
        }
        // endregion

        // region Referee
        Referee referee1 = new Referee("Howard", "Webb");
        Referee referee2 = new Referee("Mark", "Clattenburg");
        Referee referee3 = new Referee("Pierluigi", "Collina");
        Referee referee4 = new Referee("Cüneyt", "Çakır");
        Referee referee5 = new Referee("Néstor", "Pitana");
        Referee referee6 = new Referee("Felix", "Brych");
        Referee referee7 = new Referee("Antonio", "López");
        Referee referee8 = new Referee("Jonas", "Eriksson");
        Referee referee9 = new Referee("Jair", "Marrufo");
        Referee referee10 = new Referee("Damir", "Skomina");

        List<Referee> referees = List.of(referee1, referee2, referee3, referee4, referee5, referee6,
                referee7, referee8, referee9, referee10);
        if (refereeRepository.count() == 0) {
            refereeRepository.saveAll(referees);
        }
        // endregion

        // region Tournament
        Tournament tournament1 = new Tournament(
                null, // ID auto-généré
                "Champions League 2024",
                LocalDateTime.of(2024, 9, 15, 20, 45),
                LocalDateTime.of(2025, 5, 28, 20, 45),
                "Stade de France",
                new Address("Stadium Street", "Paris", "75000", "Ile-de-France", "France"),
                TournamentType.CHAMPIONS_LEAGUE_32,
                false
        );

        Tournament tournament2 = new Tournament(
                null, // ID auto-généré
                "World Cup 2026",
                LocalDateTime.of(2026, 11, 18, 17, 0),
                LocalDateTime.of(2026, 12, 18, 20, 0),
                "Lusail Stadium",
                new Address("Lusail City", "Doha", "12345", "Doha", "Qatar"),
                TournamentType.WORLD_CUP_32,
                false
        );

        Tournament tournament3 = new Tournament(
                null, // ID auto-généré
                "Copa America 2024",
                LocalDateTime.of(2024, 6, 10, 19, 0),
                LocalDateTime.of(2024, 7, 12, 21, 0),
                "Maracanã",
                new Address("Av. Pres. Castelo Branco", "Rio de Janeiro", "20271-130", "RJ", "Brazil"),
                TournamentType.COPA_AMERICA_16,
                false
        );

        Tournament tournament4 = new Tournament(
                null, // ID auto-généré
                "Euro 2024",
                LocalDateTime.of(2024, 6, 14, 21, 0),
                LocalDateTime.of(2024, 7, 14, 20, 0),
                "Wembley Stadium",
                new Address("Wembley", "London", "HA9 0WS", "Greater London", "United Kingdom"),
                TournamentType.EUROPEAN_CHAMPIONSHIP_24,
                false
        );

        Tournament tournament5 = new Tournament(
                null, // ID auto-généré
                "African Cup of Nations 2023",
                LocalDateTime.of(2023, 1, 10, 18, 0),
                LocalDateTime.of(2023, 2, 6, 20, 0),
                "Stade Ahmadou Ahidjo",
                new Address("Rue de la Solidarité", "Yaoundé", "C10", "Centre", "Cameroon"),
                TournamentType.AFRICAN_CUP_10,
                false
        );

        Tournament tournament6 = new Tournament(
                null, // ID auto-généré
                "Asian Cup 2024",
                LocalDateTime.of(2024, 1, 5, 18, 30),
                LocalDateTime.of(2024, 2, 1, 19, 30),
                "National Stadium",
                new Address("Kallang", "Singapore", "397629", "Singapore", "Singapore"),
                TournamentType.ASIAN_CUP_12,
                false
        );

        Tournament tournament7 = new Tournament(
                null, // ID auto-généré
                "Knockout Tournament 16 Teams",
                LocalDateTime.of(2024, 10, 20, 20, 0),
                LocalDateTime.of(2024, 11, 10, 20, 0),
                "Allianz Arena",
                new Address("Werner-Heisenberg-Allee 25", "Munich", "80939", "Bavaria", "Germany"),
                TournamentType.KNOCKOUT_16,
                false
        );

        Tournament tournament8 = new Tournament(
                null, // ID auto-généré
                "Knockout Tournament 8 Teams",
                LocalDateTime.of(2024, 3, 18, 18, 0),
                LocalDateTime.of(2024, 4, 2, 20, 0),
                "Stadio Olimpico",
                new Address("Viale dei Gladiatori", "Rome", "00135", "Lazio", "Italy"),
                TournamentType.KNOCKOUT_8,
                false
        );

        Tournament tournament9 = new Tournament(
                null, // ID auto-généré
                "Knockout Tournament 4 Teams",
                LocalDateTime.of(2024, 5, 25, 20, 0),
                LocalDateTime.of(2024, 5, 28, 20, 0),
                "Camp Nou",
                new Address("Carrer d'Arístides Maillol", "Barcelona", "08028", "Catalonia", "Spain"),
                TournamentType.KNOCKOUT_4,
                false
        );

        Tournament tournament10 = new Tournament(
                null, // ID auto-généré
                "Final Match",
                LocalDateTime.of(2024, 6, 8, 20, 45),
                LocalDateTime.of(2024, 6, 8, 22, 45),
                "Old Trafford",
                new Address("Sir Matt Busby Way", "Manchester", "M16 0RA", "Greater Manchester", "United Kingdom"),
                TournamentType.KNOCKOUT_2,
                true // Tournoi clôturé
        );

        List<Tournament> tournaments = List.of(
                tournament1,
                tournament2,
                tournament3,
                tournament4,
                tournament5,
                tournament6,
                tournament7,
                tournament8,
                tournament9,
                tournament10
        );

        if (tournamentRepository.count() == 0) {
            tournamentRepository.saveAll(tournaments);
        }
        // endregion

        // region Participating Teams for Tournament 8
        ParticipatingTeam participatingTeam1 = new ParticipatingTeam(tournament8, team1);
        ParticipatingTeam participatingTeam2 = new ParticipatingTeam(tournament8, team2);
        ParticipatingTeam participatingTeam3 = new ParticipatingTeam(tournament8, team3);
        ParticipatingTeam participatingTeam4 = new ParticipatingTeam(tournament8, team4);
        ParticipatingTeam participatingTeam5 = new ParticipatingTeam(tournament8, team5);
        ParticipatingTeam participatingTeam6 = new ParticipatingTeam(tournament8, team6);
        ParticipatingTeam participatingTeam7 = new ParticipatingTeam(tournament8, team7);
        ParticipatingTeam participatingTeam8 = new ParticipatingTeam(tournament8, team8);

        List<ParticipatingTeam> participatingTeams = List.of(
                participatingTeam1, participatingTeam2, participatingTeam3, participatingTeam4,
                participatingTeam5, participatingTeam6, participatingTeam7, participatingTeam8
        );

        if (participatingTeamRepository.count() == 0) {
            participatingTeamRepository.saveAll(participatingTeams);
        }
// endregion

        // region FootMatch
        // Récupérer le tournoi avec l'ID 8 (Tournament 8)


        // Récupérer les équipes participantes au tournoi 8
        List<Team> tournament8Teams =new ArrayList<>(List.of(team1, team2, team3, team4, team5, team6, team7, team8));

        // Création des 7 matchs pour le tournoi 8
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            FootMatch match = new FootMatch();

            // Mélanger les équipes pour s'assurer qu'on ne choisit pas la même
            Collections.shuffle(tournament8Teams);
            Team teamHome = tournament8Teams.get(0);
            Team teamAway = tournament8Teams.get(1); // Prendre la deuxième équipe après le mélange
            // Sélectionner un arbitre au hasard
            Referee referee = referees.get(random.nextInt(referees.size()));

            // Configuration du match
            match.setTeamHome(teamHome);
            match.setTeamAway(teamAway);
            match.setMatchDateTime(LocalDateTime.now().plusDays(i)); // Date prévue, un match par jour à partir d'aujourd'hui
            match.setMatchStatus(MatchStatus.SCHEDULED); // Statut du match comme prévu
            match.setTournament(tournament8); // Associer le match au Tournament 8
            match.setReferee(referee); // Associer un arbitre au match

            // Sauvegarder le match dans la base de données
            footMatchRepository.save(match);
        }
        // endregion
    }
}


