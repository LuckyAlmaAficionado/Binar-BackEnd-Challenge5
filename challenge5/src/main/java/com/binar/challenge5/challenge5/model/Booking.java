package com.binar.challenge5.challenge5.model;


import com.binar.challenge5.challenge5.Status;
import com.binar.challenge5.challenge5.repository.MovieRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Booking {
    @Id
    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "booking_sequence"
    )
    private Long bookingId;
    private String kodeBooking;
    private Long movieId;
    private Long scheduleId;
    private String userCredential;
    private String studio;
    private String movieName;
    private Time startTime;
    private String[] seat;
    private Integer price;
    private Status status;
    private LocalDateTime lastUpdate;

    public LocalDateTime getLastUpdate() {
        return LocalDateTime.now();
    }

    public Booking(Long movieId, String[] seat, Status status) {
        this.movieId = movieId;
        this.seat = seat;
        this.status = status;
    }

    public Booking(String kodeBooking, Long movieId, Long scheduleId, String userCredential, String studio, String movieName, Time startTime, Integer price, Status status, LocalDateTime lastUpdate) {
        this.kodeBooking = kodeBooking;
        this.movieId = movieId;
        this.scheduleId = scheduleId;
        this.userCredential = userCredential;
        this.studio = studio;
        this.movieName = movieName;
        this.startTime = startTime;
        this.price = price;
        this.status = status;
        this.lastUpdate = lastUpdate;
    }

    public Booking(String kodeBooking, Long movieId, Long scheduleId, String userCredential, String studio, String movieName, Time startTime, String[] seat, Integer price, Status status) {
        this.kodeBooking = kodeBooking;
        this.movieId = movieId;
        this.scheduleId = scheduleId;
        this.userCredential = userCredential;
        this.studio = studio;
        this.movieName = movieName;
        this.startTime = startTime;
        this.seat = seat;
        this.price = price;
        this.status = status;
    }
}
