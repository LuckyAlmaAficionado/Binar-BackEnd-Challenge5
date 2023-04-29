package com.binar.challenge5.challenge5.service;

import com.binar.challenge5.challenge5.model.Booking;
import com.binar.challenge5.challenge5.model.Schedule;
import com.binar.challenge5.challenge5.repository.BookingRepository;
import com.binar.challenge5.challenge5.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository repository;

    @Autowired
    private BookingRepository bookingRepository;

    public String addSchedule(Schedule schedule) {
        repository.save(schedule);
        return "berhasil di tambahkan";
    }

    public List<Schedule> getSchedules() {
        return repository.findAll();
    }

    @Transactional
    public Long deleteByMovieCodeWhere(Long deleteMovieId) {
        return repository.deleteByMovieFk(deleteMovieId);
    }
}
