package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.Booking;
import com.binar.challenge5.challenge5.repository.BookingRepository;
import com.binar.challenge5.challenge5.service.BookingService;
import com.binar.challenge5.challenge5.service.SortAscDesc;
import com.binar.challenge5.challenge5.utils.MessageModelPagination;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private SortAscDesc sortAscDesc;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public List<Booking> getAllBookingCode() {
        return service.getAllBookings();
    }

    @PostMapping
    public Booking postBooking(@RequestParam String email,
                               @RequestParam Long movieId,
                               @RequestParam Long scheduleId,
                               @RequestParam String seat) throws JRException, FileNotFoundException {
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
    @PostMapping(path = "{bookingCode}")
    public String printJasperReport(@PathVariable String bookingCode) throws JRException, FileNotFoundException {
        return service.printReport(bookingCode);
    }

    @GetMapping("/pagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Booking> data = bookingRepository.findAll(pageRequest);

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
