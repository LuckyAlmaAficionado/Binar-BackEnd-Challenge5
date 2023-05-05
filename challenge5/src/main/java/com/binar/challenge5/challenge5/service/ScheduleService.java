package com.binar.challenge5.challenge5.service;

import com.binar.challenge5.challenge5.model.Booking;
import com.binar.challenge5.challenge5.model.Schedule;
import com.binar.challenge5.challenge5.repository.BookingRepository;
import com.binar.challenge5.challenge5.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository repository;

    @Autowired
    private BookingRepository bookingRepository;

    public String addSchedule(Schedule schedule) {
        String[] temp = {"A1","A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9"};
        schedule.setSeats(temp);
        repository.save(schedule);
        return "berhasil di tambahkan";
    }

    public List<Schedule> getSchedules() {


        List<Schedule> schedules = repository.findAll();

        for (Schedule seat : schedules) {
            List<String> temp = new ArrayList<>();
            temp.addAll(Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9"));

            Long movieFK = seat.getMovieFk();
            Long scheduleId = seat.getScheduleId();

            System.out.println("movieFK : " + movieFK + ", schedule Id: " + scheduleId);
            List<Booking> bookings = bookingRepository.findBookingByMovieIdAndScheduleId(movieFK, scheduleId);

            for (Booking kursi : bookings) {
                System.out.println("kursi yang sudah dipersan: " + kursi.getSeat());
                temp.remove(kursi.getSeat());
            }


            System.out.println(temp.size());
            String[] kursiKosong = new String[temp.size()];

            for (int i = 0; i < temp.size(); i++) {
                kursiKosong[i] = temp.get(i);
            }

            seat.setSeats(kursiKosong);
        }

        return schedules;
    }

    @Transactional
    public Long deleteByMovieCodeWhere(Long deleteMovieId) {
        return repository.deleteByMovieFk(deleteMovieId);
    }
}
