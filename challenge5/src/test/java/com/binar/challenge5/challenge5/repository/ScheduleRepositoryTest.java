package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Movie;
import com.binar.challenge5.challenge5.model.Schedule;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository underTest;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() throws ParseException {
        movieRepository.saveAll(getDataDummyMovie());
        underTest.saveAll(getDataDummySchedule());
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<Movie> getDataDummyMovie() {
        Movie lion = new Movie(
                1L,
                "The Lion King",
                "Animals",
                true
        );
        Movie joker = new Movie(
                2L,
                "Joker",
                "Thriller",
                true
        );
        Movie jumanji = new Movie(
                3L,
                "Jumanji: Welcome to the Jungle",
                "Adventure, Comedy",
                true
        );

        return List.of(
                lion,
                joker,
                jumanji
        );
    }

    public List<Schedule> getDataDummySchedule() throws ParseException {
        return List.of(
                new Schedule(
                        dateFormat.parse( "2022-09-20"),
                        Time.valueOf("19:30:00"),
                        Time.valueOf("21:00:00"),
                        "Transmart",
                        55000,
                        1L
                ),
                new Schedule(
                        dateFormat.parse("2022-09-08"),
                        Time.valueOf("16:00:00"),
                        Time.valueOf("18:00:00"),
                        "Ramayana Cinema",
                        45000,
                        2L
                ),
                new Schedule(
                        dateFormat.parse("2022-09-08"),
                        Time.valueOf("16:00:00"),
                        Time.valueOf("18:00:00"),
                        "XXI",
                        45000,
                        3L
                )
        );
    }


    @Test
    void deleteByMovieFk() throws ParseException {
        
       underTest.deleteByMovieFk(3L);

       Assertions.assertNull(underTest.findByMovieFk(3L));


    }

    @Test
    void findByMovieFk() {



    }

    @Test
    void findByScheduleId() {
    }
}