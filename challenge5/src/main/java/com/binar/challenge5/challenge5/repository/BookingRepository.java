package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

//    Collection<Booking> findBookingByBookingCode(String bookingCode);

    List<Booking> findByMovieId(Long movieId);

}