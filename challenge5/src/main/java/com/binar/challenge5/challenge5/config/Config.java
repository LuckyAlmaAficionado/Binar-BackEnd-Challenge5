package com.binar.challenge5.challenge5.config;

import com.binar.challenge5.challenge5.Status;
import com.binar.challenge5.challenge5.model.Booking;
import com.binar.challenge5.challenge5.model.Movie;
import com.binar.challenge5.challenge5.model.Schedule;
import com.binar.challenge5.challenge5.repository.BookingRepository;
import com.binar.challenge5.challenge5.repository.MovieRepository;
import com.binar.challenge5.challenge5.repository.ScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
public class Config {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository, ScheduleRepository scheduleRepository, BookingRepository bookingRepository) {
        return args -> {
            Movie avatar = new Movie(
                    "avatar the legends of aang",
                    "Adventure",
                    true
            );

            Schedule schedule1 = new Schedule(
                    dateFormat.parse("2023-09-08"),
                    Time.valueOf("16:00:00"),
                    Time.valueOf("18:00:00"),
                    "Transmart",
                    45000,
                    1L
            );

            Schedule schedule2 = new Schedule(
                    dateFormat.parse("2022-09-08"),
                    Time.valueOf("16:00:00"),
                    Time.valueOf("18:00:00"),
                    "Ramayana Cinema",
                    45000,
                    1L
            );

            Schedule schedule3 = new Schedule(
                    dateFormat.parse("2021-09-08"),
                    Time.valueOf("16:00:00"),
                    Time.valueOf("18:00:00"),
                    "XXI",
                    45000,
                    1L
            );

            Movie lion = new Movie(
                    "The Lion King",
                    "Animals",
                    true
            );
            Movie joker = new Movie(
                    "Joker",
                    "Thriller",
                    true
            );
            Movie jumanji = new Movie(
                    "Jumanji: Welcome to the Jungle",
                    "Adventure, Comedy",
                    true
            );

//            Booking booking = new Booking(
//                    "2RG42F",
//                    "Lucky Alma Aficionado Rigel",
//                    "Jumanji: Welcome To The Jungle",
//                    "Cinema21",
//                    "B3",
//                    dateFormat.parse("2022-09-08"),
//                    Time.valueOf("17:20:00"),
//                    Time.valueOf("18:30:00"),
//                    450000
//            );
//
//            Booking booking1 = new Booking(
//                    "2RG42G",
//                    "Lucky Alma Aficionado Rigel",
//                    "Jumanji: Welcome To The Jungle",
//                    "Cinema21",
//                    "B3",
//                    dateFormat.parse("2022-09-08"),
//                    Time.valueOf("17:20:00"),
//                    Time.valueOf("18:30:00"),
//                    450000
//            );

            Booking booking = new Booking(
                    1L,
                    List.of("A1", "A2").toArray(new String[0]),
                    Status.ON_PROCESS_PAYMENT
            );
            movieRepository.saveAll(
                    List.of(avatar, lion, joker, jumanji)
            );

            bookingRepository.saveAll(
                    List.of(booking)
            );
            scheduleRepository.saveAll(
                    List.of(schedule1)
            );

        };
    }

}
