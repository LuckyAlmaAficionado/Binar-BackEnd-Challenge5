package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    public Movie getDataSample() {
        return new Movie(
                "Jumanji",
                "Adventure, Comedy",
                true
        );
    }

    @Test
    void findByIsShowingTrue() {


        underTest.save(getDataSample());

        List<Movie> byIsShowingTrue = underTest.findByIsShowingTrue();

        assertThat(!byIsShowingTrue.isEmpty()).isTrue();

    }

    @Test
    void findByIsShowingTrueDataNull() {

        List<Movie> byIsShowingTrue = underTest.findByIsShowingTrue();

        assertThat(byIsShowingTrue.isEmpty()).isTrue();

    }


    @Test
    void findMovieByMovieName() {

        underTest.save(getDataSample());

        Optional<Movie> movieByMovieName = underTest.findMovieByMovieName(getDataSample().getMovieName());

        Assertions.assertNotNull(movieByMovieName);

    }

    @Test
    void findMovieByMovieNameDoesNotExists() {

        Optional<Movie> movieByMovieName = underTest.findMovieByMovieName(getDataSample().getMovieName());

        Assertions.assertNotNull(movieByMovieName);

    }


}