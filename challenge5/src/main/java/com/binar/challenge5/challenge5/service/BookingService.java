package com.binar.challenge5.challenge5.service;

import com.binar.challenge5.challenge5.Status;
import com.binar.challenge5.challenge5.model.Booking;
import com.binar.challenge5.challenge5.model.Movie;
import com.binar.challenge5.challenge5.model.Schedule;
import com.binar.challenge5.challenge5.model.User;
import com.binar.challenge5.challenge5.repository.BookingRepository;
import com.binar.challenge5.challenge5.repository.MovieRepository;
import com.binar.challenge5.challenge5.repository.ScheduleRepository;
import com.binar.challenge5.challenge5.repository.UserRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    public BookingService(BookingRepository repository) {
    }

    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    private String getRand() {
        String saltChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rand = new Random();
        while (salt.length() < 6) {
            int index = (int) (rand.nextFloat() * saltChar.length());
            salt.append(saltChar.charAt(index));
        }

        return salt.toString();
    }

    public Booking postBooking(String email, Long movieId, Long scheduleId, String[] seats) throws JRException, FileNotFoundException {


        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("email does not exists"));

        Movie movieDoesNotExists = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("movie does not exists"));

        Schedule scheduleTemp = scheduleRepository.findByScheduleId(scheduleId);

        String bookingCode = getRand().toUpperCase();

        Booking booking = null;

        if (seats.length > 1) {
            for (String seat : seats) {

                Collection<Booking> andSeat = repository.findBookingByMovieIdAndScheduleIdAndSeat(movieId, scheduleId, seat);

                if (!andSeat.isEmpty()) throw new IllegalArgumentException("seat " + seat + " reserved");

                booking = new Booking(bookingCode, movieId, movieDoesNotExists.getMovieName(), scheduleId, user.getUsername(),
                        scheduleTemp.getStudio(), scheduleTemp.getStartTime(), scheduleTemp.getEndTime(), String.valueOf(scheduleTemp.getDate()),
                        seat, scheduleTemp.getPrice(), Status.ON_PROCESS_PAYMENT);
                repository.save(booking);
            }
        } else {

            Collection<Booking> andSeat = repository.findBookingByMovieIdAndScheduleIdAndSeat(movieId, scheduleId, seats[0]);

            if (!andSeat.isEmpty()) throw new IllegalArgumentException("seat " + seats[0] + " reserved");

             booking = new Booking(bookingCode, movieId, movieDoesNotExists.getMovieName(), scheduleId, user.getUsername(),
                    scheduleTemp.getStudio(), scheduleTemp.getStartTime(), scheduleTemp.getEndTime(), String.valueOf(scheduleTemp.getDate()),
                    seats[0], scheduleTemp.getPrice(), Status.ON_PROCESS_PAYMENT);
            repository.save(booking);
        }

//        printReport(bookingCode);

        return booking;
    }

    public Booking updateStatusBooking(Long bookingId, Integer status) {
        Booking booking1 = repository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("booking id " + bookingId + " does not exists"));
        booking1.setStatus(Status.EXPIRED_PAYMENT);
        if (status == 1) booking1.setStatus(Status.FULL_PAYMENT);
        return repository.save(booking1);
    }


    public Collection<Booking> findByBookingCodeWhere(String bookingCode) {
        Collection<Booking> bookingByBookingCode = repository.findBookingByCodeBooking(bookingCode);
        if (bookingByBookingCode.isEmpty())
            throw new IllegalArgumentException("booking code " + bookingCode + " does not exists");
        return repository.findBookingByCodeBooking(bookingCode);
    }


    public String printReport(String bookingCode) throws FileNotFoundException, JRException {
        String path = "D:\\code\\.springboot\\challenge 5 - Backup\\jasper";

        Collection<Booking> bookingCollection = repository.findBookingByCodeBooking(bookingCode);

        File file = ResourceUtils.getFile("classpath:jasper-report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookingCollection);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Lucky Alma");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\" + bookingCode + ".pdf");
        return "report generate in path: " + path;
    }

}
