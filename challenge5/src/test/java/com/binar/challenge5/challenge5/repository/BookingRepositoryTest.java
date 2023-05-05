package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Booking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookingRepositoryTest {

    UUID uuid = UUID.randomUUID();
    @Autowired
    private BookingRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldfindByMovieIdExists() {
        Booking booking = new Booking(
                1L,
                String.valueOf(uuid),
                "jumanji",
                "udin",
                "XXI"
        );

        underTest.save(booking);

        Booking byMovieId = underTest.findByMovieId(1L);

        assertThat(byMovieId.getMovieId()).isEqualTo(booking.getMovieId());
    }

    @Test
    void itShouldfindByMovieIdDoesExists() {
        Booking booking = new Booking(
                1L,
                String.valueOf(uuid),
                "jumanji",
                "udin",
                "XXI"
        );

        underTest.save(booking);

        Booking byMovieId = underTest.findByMovieId(1L);

        long unitTest = 2L;

        assertThat(byMovieId.getMovieId()).isNotEqualTo(unitTest);
    }
}