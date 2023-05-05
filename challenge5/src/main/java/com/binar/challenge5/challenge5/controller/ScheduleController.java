package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.Schedule;
import com.binar.challenge5.challenge5.repository.ScheduleRepository;
import com.binar.challenge5.challenge5.service.ScheduleService;
import com.binar.challenge5.challenge5.service.SortAscDesc;
import com.binar.challenge5.challenge5.utils.MessageModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService service;

    @Autowired
    private SortAscDesc sortAscDesc;

    @Autowired
    private ScheduleRepository scheduleRepository;

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

    @GetMapping(path = "/pagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Schedule> data = scheduleRepository.findAll(pageRequest);

            msg.setStatus(true);
            msg.setMessage("Success to get all data..");
            msg.setData(data.getContent());
            msg.setCurrentPage(data.getNumber());
            msg.setTotalPages(data.getTotalPages());
            msg.setTotalItems((int) data.getTotalElements());
            msg.setNumberOfElement(data.getNumberOfElements());

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }

    }

}
