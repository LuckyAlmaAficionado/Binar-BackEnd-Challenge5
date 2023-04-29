package com.binar.challenge5.challenge5.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Objects;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Seat {
    @Id
    @SequenceGenerator(
            name = "seat_sequence",
            sequenceName = "seat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "seat_sequence"
    )
    private Long id;
    private Long scheduleFk;
    private String seat;
    private String[] a = {"A1", "A2", "A3"};

    public Seat(Long scheduleFk, String seat) {
        this.scheduleFk = scheduleFk;
        this.seat = seat;
    }

}
