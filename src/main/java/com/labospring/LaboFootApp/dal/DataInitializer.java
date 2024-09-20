package com.labospring.LaboFootApp.dal;

import com.labospring.LaboFootApp.dal.repositories.*;
import com.labospring.LaboFootApp.dl.entities.*;
import com.labospring.LaboFootApp.dl.enums.FieldPosition;
import com.labospring.LaboFootApp.dl.enums.Role;
import com.labospring.LaboFootApp.dl.enums.TournamentType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final CoachRepository coachRepository;
    private final RefereeRepository refereeRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {

        // region  User
        User user1 = new User(
                null,
                "steby81",
                "test123",
                "Steph",
                "Kal",
                "kalonj1981@hotmail.com",
                LocalDate.of(1981, 5, 12),
                "0498123456",
                new Address("123 Main St", "Brussels", "1000", "Brussels", "Belgium"),
                Role.ADMIN
        );

        User user2 = new User(
                null,
                "gabinho",
                "test123",
                "Jean",
                "Gabin",
                "kalonjis@outlook.com",
                LocalDate.of(1990, 3, 25),
                "0498765432",
                new Address("456 Elm St", "Antwerp", "2000", "Antwerp", "Belgium"),
                Role.ADMIN
        );

        User user3 = new User(
                null,
                "homerSps",
                "test123",
                "Homer",
                "Simpson",
                "kalonji_s@skynet.be",
                LocalDate.of(1978, 6, 15),
                "0476987654",
                new Address("789 Maple St", "Liège", "4000", "Liège", "Belgium"),
                Role.USER
        );

        // Adding 7 more users with different addresses

        User user4 = new User(
                null,
                "margeSimps",
                "test123",
                "Marge",
                "Simpson",
                "marge.s@springfield.com",
                LocalDate.of(1975, 10, 1),
                "0466554433",
                new Address("123 Springfield Ave", "Springfield", "5000", "Illinois", "USA"),
                Role.USER
        );

        User user5 = new User(
                null,
                "bartSimps",
                "test123",
                "Bart",
                "Simpson",
                "bart.s@springfield.com",
                LocalDate.of(2005, 4, 1),
                "0456789123",
                new Address("456 Springfield Ave", "Springfield", "5001", "Illinois", "USA"),
                Role.USER
        );

        User user6 = new User(
                null,
                "lisaSimps",
                "test123",
                "Lisa",
                "Simpson",
                "lisa.s@springfield.com",
                LocalDate.of(2007, 5, 9),
                "0478996453",
                new Address("789 Springfield Ave", "Springfield", "5002", "Illinois", "USA"),
                Role.USER
        );

        User user7 = new User(
                null,
                "maggySimps",
                "test123",
                "Maggy",
                "Simpson",
                "maggy.s@springfield.com",
                LocalDate.of(2010, 8, 12),
                "0487654321",
                new Address("101 Springfield Ave", "Springfield", "5003", "Illinois", "USA"),
                Role.USER
        );

        User user8 = new User(
                null,
                "nedFlanders",
                "test123",
                "Ned",
                "Flanders",
                "ned.f@springfield.com",
                LocalDate.of(1965, 2, 27),
                "0496453123",
                new Address("111 Oak St", "Springfield", "5004", "Illinois", "USA"),
                Role.USER
        );

        User user9 = new User(
                null,
                "moeSzyslak",
                "test123",
                "Moe",
                "Szyslak",
                "moe.s@springfield.com",
                LocalDate.of(1970, 11, 15),
                "0467123890",
                new Address("222 Bar St", "Springfield", "5005", "Illinois", "USA"),
                Role.USER
        );

        User user10 = new User(
                null,
                "apuNahasapeemapetilon",
                "test123",
                "Apu",
                "Nahasapeemapetilon",
                "apu.n@kwikemart.com",
                LocalDate.of(1968, 10, 5),
                "0467001112",
                new Address("333 Kwik-E-Mart", "Springfield", "5006", "Illinois", "USA"),
                Role.USER
        );

        // Save all users, regardless of whether the user table is empty or not
        List<User> users = List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);
        userRepository.saveAll(users);
        //endregion

        // region Team & Player
        if (teamRepository.count() == 0) {
            // Créer 16 équipes, dont 10 sans joueurs
            List<Team> teams = List.of(
                    new Team(null, "Manchester United", new Coach("Alex", "Ferguson"), new HashSet<>()),
                    new Team(null, "Real Madrid", new Coach("Zinedine", "Zidane"), new HashSet<>()),
                    new Team(null, "FC Barcelona", new Coach("Pep", "Guardiola"), new HashSet<>()),
                    new Team(null, "Liverpool FC", new Coach("Jurgen", "Klopp"), new HashSet<>()),
                    new Team(null, "Bayern Munich", new Coach("Carlo", "Ancelotti"), new HashSet<>()),
                    new Team(null, "Juventus FC", new Coach("Massimiliano", "Allegri"), new HashSet<>()),
                    new Team(null, "Paris Saint-Germain", new Coach("Mauricio", "Pochettino"), new HashSet<>()),
                    new Team(null, "Chelsea FC", new Coach("Thomas", "Tuchel"), new HashSet<>()),
                    new Team(null, "Atletico Madrid", new Coach("Diego", "Simeone"), new HashSet<>()),
                    new Team(null, "Inter Milan", new Coach("Simone", "Inzaghi"), new HashSet<>()),
                    // 6 équipes sans joueurs
                    new Team(null, "AC Milan", new Coach("Pippo", "Inzaghi"), new HashSet<>()),
                    new Team(null, "Tottenham Hotspur", new Coach("Antonio", "Conte"), new HashSet<>()),
                    new Team(null, "AS Roma", new Coach("Marcelo", "Bielsa"), new HashSet<>()),
                    new Team(null, "Borussia Dortmund", new Coach("Thomas", "Tuchel"), new HashSet<>()),
                    new Team(null, "Olympique Lyonnais", new Coach("Roberto", "Mancini"), new HashSet<>()),
                    new Team(null, "Ajax Amsterdam", new Coach("Luis", "Enrique"), new HashSet<>())
            );

            teamRepository.saveAll(teams);

            // region Player
            if (playerRepository.count() == 0) {
                // 50 joueurs, dont 30 avec une équipe
                List<Player> players = List.of(
                        // 30 joueurs avec équipes
                        new Player(null, "Cristiano", "Ronaldo", "CR7", 7, FieldPosition.FORWARD),
                        new Player(null, "Marcus", "Rashford", "Rashy", 10, FieldPosition.FORWARD),
                        new Player(null, "Karim", "Benzema", "Benz", 9, FieldPosition.FORWARD),
                        new Player(null, "Luka", "Modric", "Modric", 10, FieldPosition.MIDFIELDER),
                        new Player(null, "Lionel", "Messi", "Messi", 10, FieldPosition.FORWARD),
                        new Player(null, "Gerard", "Pique", "Pique", 3, FieldPosition.DEFENDER),
                        new Player(null, "Mohamed", "Salah", "Salah", 11, FieldPosition.FORWARD),
                        new Player(null, "Virgil", "van Dijk", "VVD", 4, FieldPosition.DEFENDER),
                        new Player(null, "Robert", "Lewandowski", "Lewy", 9, FieldPosition.FORWARD),
                        new Player(null, "Joshua", "Kimmich", "Kimmich", 6, FieldPosition.MIDFIELDER),
                        new Player(null, "Paulo", "Dybala", "Dybala", 10, FieldPosition.FORWARD),
                        new Player(null, "Leonardo", "Bonucci", "Bonucci", 19, FieldPosition.DEFENDER),
                        new Player(null, "Kylian", "Mbappe", "Mbappe", 7, FieldPosition.FORWARD),
                        new Player(null, "Neymar", "Junior", "Neymar", 10, FieldPosition.FORWARD),
                        new Player(null, "Mason", "Mount", "Mount", 19, FieldPosition.MIDFIELDER),
                        new Player(null, "Thiago", "Silva", "T.Silva", 6, FieldPosition.DEFENDER),
                        new Player(null, "Luis", "Suarez", "El Pistolero", 9, FieldPosition.FORWARD),
                        new Player(null, "Jan", "Oblak", "Oblak", 1, FieldPosition.GOALKEEPER),
                        new Player(null, "Romelu", "Lukaku", "Lukaku", 9, FieldPosition.FORWARD),
                        new Player(null, "Milan", "Skriniar", "Skriniar", 37, FieldPosition.DEFENDER),
                        // 20 joueurs sans équipes
                        new Player(null, "Zlatan", "Ibrahimovic", "Zlatan", null, FieldPosition.FORWARD),
                        new Player(null, "Son", "Heung-min", "Sonny", null, FieldPosition.FORWARD),
                        new Player(null, "Henrikh", "Mkhitaryan", "Mkhitaryan", null, FieldPosition.MIDFIELDER),
                        new Player(null, "Erling", "Haaland", "Haaland", null, FieldPosition.FORWARD),
                        new Player(null, "Memphis", "Depay", "Memphis", null, FieldPosition.FORWARD),
                        new Player(null, "Dusan", "Tadic", "Tadic", null, FieldPosition.MIDFIELDER),
                        new Player(null, "Christian", "Eriksen", "Eriksen", null, FieldPosition.MIDFIELDER),
                        new Player(null, "Eden", "Hazard", "Hazard", null, FieldPosition.FORWARD),
                        new Player(null, "Kevin", "De Bruyne", "KDB", null, FieldPosition.MIDFIELDER),
                        new Player(null, "Sadio", "Mane", "Mane", null, FieldPosition.FORWARD),
                        new Player(null, "Fabinho", "Tavares", "Fabinho", null, FieldPosition.DEFENDER),
                        new Player(null, "Antoine", "Griezmann", "Grizi", null, FieldPosition.FORWARD),
                        new Player(null, "Jadon", "Sancho", "Sancho", null, FieldPosition.FORWARD),
                        new Player(null, "Gareth", "Bale", "Bale", null, FieldPosition.FORWARD),
                        new Player(null, "Alvaro", "Morata", "Morata", null, FieldPosition.FORWARD),
                        new Player(null, "Marco", "Veratti", "Veratti", null, FieldPosition.MIDFIELDER),
                        new Player(null, "David", "De Gea", "De Gea", null, FieldPosition.GOALKEEPER),
                        new Player(null, "Cesar", "Azpilicueta", "Azpi", null, FieldPosition.DEFENDER),
                        new Player(null, "Hakim", "Ziyech", "Ziyech", null, FieldPosition.MIDFIELDER),
                        new Player(null, "N'Golo", "Kante", "Kante", null, FieldPosition.MIDFIELDER)
                );
                // Assigner les 30 premiers joueurs aux équipes correspondantes
                for (int i = 0; i < 30; i++) {
                    Player player = players.get(i);
                    player.setTeam(teams.get(i % 10)); // Assigne un joueur à une des 10 premières équipes
                    teams.get(i % 10).addPlayer(player);
                }

                // Sauvegarder tous les joueurs
                playerRepository.saveAll(players);
            }
            // endregion
        }
//endregion



        // region Coach
        if (coachRepository.count() == 0) {
            List<Coach> coaches = List.of(
                    new Coach("Pep", "Guardiola"),
                    new Coach("Jurgen", "Klopp"),
                    new Coach("Zinedine", "Zidane"),
                    new Coach("Carlo", "Ancelotti"),
                    new Coach("Diego", "Simeone")
            );
            coachRepository.saveAll(coaches);
        }
        // endregion

        // region Referee
        if (refereeRepository.count() == 0) {
            List<Referee> referees = List.of(
                    new Referee("Howard", "Webb"),
                    new Referee("Mark", "Clattenburg"),
                    new Referee("Pierluigi", "Collina"),
                    new Referee("Cüneyt", "Çakır"),
                    new Referee("Néstor", "Pitana"),
                    new Referee("Felix", "Brych"),
                    new Referee("Antonio", "López"),
                    new Referee("Jonas", "Eriksson"),
                    new Referee("Jair", "Marrufo"),
                    new Referee("Damir", "Skomina")
            );
            refereeRepository.saveAll(referees);
        }
        // endregion



        // region tournament
        if (tournamentRepository.count() == 0) {
            List<Tournament> tournaments = List.of(
                    new Tournament(
                            null, // ID auto-généré
                            "Champions League 2024",
                            LocalDateTime.of(2024, 9, 15, 20, 45),
                            LocalDateTime.of(2025, 5, 28, 20, 45),
                            "Stade de France",
                            new Address("Stadium Street", "Paris", "75000", "Ile-de-France", "France"),
                            TournamentType.CHAMPIONS_LEAGUE_32,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "World Cup 2026",
                            LocalDateTime.of(2026, 11, 18, 17, 0),
                            LocalDateTime.of(2026, 12, 18, 20, 0),
                            "Lusail Stadium",
                            new Address("Lusail City", "Doha", "12345", "Doha", "Qatar"),
                            TournamentType.WORLD_CUP_32,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "Copa America 2024",
                            LocalDateTime.of(2024, 6, 10, 19, 0),
                            LocalDateTime.of(2024, 7, 12, 21, 0),
                            "Maracanã",
                            new Address("Av. Pres. Castelo Branco", "Rio de Janeiro", "20271-130", "RJ", "Brazil"),
                            TournamentType.COPA_AMERICA_16,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "Euro 2024",
                            LocalDateTime.of(2024, 6, 14, 21, 0),
                            LocalDateTime.of(2024, 7, 14, 20, 0),
                            "Wembley Stadium",
                            new Address("Wembley", "London", "HA9 0WS", "Greater London", "United Kingdom"),
                            TournamentType.EUROPEAN_CHAMPIONSHIP_24,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "African Cup of Nations 2023",
                            LocalDateTime.of(2023, 1, 10, 18, 0),
                            LocalDateTime.of(2023, 2, 6, 20, 0),
                            "Stade Ahmadou Ahidjo",
                            new Address("Rue de la Solidarité", "Yaoundé", "C10", "Centre", "Cameroon"),
                            TournamentType.AFRICAN_CUP_10,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "Asian Cup 2024",
                            LocalDateTime.of(2024, 1, 5, 18, 30),
                            LocalDateTime.of(2024, 2, 1, 19, 30),
                            "National Stadium",
                            new Address("Kallang", "Singapore", "397629", "Singapore", "Singapore"),
                            TournamentType.ASIAN_CUP_12,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "Knockout Tournament 16 Teams",
                            LocalDateTime.of(2024, 10, 20, 20, 0),
                            LocalDateTime.of(2024, 11, 10, 20, 0),
                            "Allianz Arena",
                            new Address("Werner-Heisenberg-Allee 25", "Munich", "80939", "Bavaria", "Germany"),
                            TournamentType.KNOCKOUT_16,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "Knockout Tournament 8 Teams",
                            LocalDateTime.of(2024, 3, 18, 18, 0),
                            LocalDateTime.of(2024, 4, 2, 20, 0),
                            "Stadio Olimpico",
                            new Address("Viale dei Gladiatori", "Rome", "00135", "Lazio", "Italy"),
                            TournamentType.KNOCKOUT_8,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "Knockout Tournament 4 Teams",
                            LocalDateTime.of(2024, 5, 25, 20, 0),
                            LocalDateTime.of(2024, 5, 28, 20, 0),
                            "Camp Nou",
                            new Address("Carrer d'Arístides Maillol", "Barcelona", "08028", "Catalonia", "Spain"),
                            TournamentType.KNOCKOUT_4,
                            false
                    ),
                    new Tournament(
                            null, // ID auto-généré
                            "Final Match",
                            LocalDateTime.of(2024, 6, 8, 20, 45),
                            LocalDateTime.of(2024, 6, 8, 22, 45),
                            "Old Trafford",
                            new Address("Sir Matt Busby Way", "Manchester", "M16 0RA", "Greater Manchester", "United Kingdom"),
                            TournamentType.KNOCKOUT_2,
                            true // Tournoi clôturé
                    )
            );
            tournamentRepository.saveAll(tournaments);
        }
        // endregion
    }
}


