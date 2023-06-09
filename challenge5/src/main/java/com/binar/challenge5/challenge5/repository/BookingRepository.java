package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Collection<Booking> findBookingByCodeBooking(String bookingCode);

    Booking findByMovieId(Long movieId);

    List<Booking> findBookingByMovieIdAndScheduleId(Long movieFk, Long scheduleId);

    Collection<Booking> findBookingByMovieIdAndScheduleIdAndSeat(Long movieFk, Long scheduleId, String seat);

}
