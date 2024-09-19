package com.labospring.LaboFootApp.dal;

import com.labospring.LaboFootApp.dal.repositories.UserRepository;
import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

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
    }
}
