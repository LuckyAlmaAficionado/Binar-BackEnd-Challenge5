package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.Booking;
import com.binar.challenge5.challenge5.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService service;

    @GetMapping
    public List<Booking> getAllBookingCode() {
        return service.getAllBooking();
    }

    @PostMapping
    public Booking postBooking(@RequestParam String email,
                               @RequestParam Long movieId,
                               @RequestParam Long scheduleId,
                               @RequestParam String[] seat) {
        return service.postBooking(email, movieId, scheduleId, seat);
    }

    @PutMapping(path = "{bookingId}")
    public Booking updateStatusBooking(@PathVariable Long bookingId,
                                       @RequestParam(required = false) Integer status) {
        return service.updateStatusBooking(bookingId, status);
    }

//    @GetMapping(path = "{bookingCode}")
//    public Collection<Booking> findByBookingCode(@PathVariable String bookingCode) {
//        return service.findByBookingCodeWhere(bookingCode);
//    }
//
//    @PostMapping(path = "{bookingCode}")
//    public String printJasperReport(@PathVariable String bookingCode) throws JRException, FileNotFoundException {
//        return service.printReport(bookingCode);
//    }

}
