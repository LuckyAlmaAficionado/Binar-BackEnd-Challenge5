package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.Schedule;
import com.binar.challenge5.challenge5.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService service;

    @GetMapping
    public List<Schedule> getSchedules() {
        return service.getSchedules();
    }

    @PostMapping
    public String addNewSchedule(@RequestBody Schedule schedule) {
        return service.addSchedule(schedule);
    }

    @DeleteMapping(path = "{deleteMovieId}")
    public Long deleteSchedule(@PathVariable Long deleteMovieId) {
        return service.deleteByMovieCodeWhere(deleteMovieId);
    }
}
