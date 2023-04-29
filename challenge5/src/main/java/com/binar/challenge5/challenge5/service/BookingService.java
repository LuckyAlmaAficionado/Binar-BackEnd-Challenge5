package com.binar.challenge5.challenge5.service;

import com.binar.challenge5.challenge5.Status;
import com.binar.challenge5.challenge5.model.Booking;
import com.binar.challenge5.challenge5.model.Schedule;
import com.binar.challenge5.challenge5.model.User;
import com.binar.challenge5.challenge5.repository.BookingRepository;
import com.binar.challenge5.challenge5.repository.MovieRepository;
import com.binar.challenge5.challenge5.repository.ScheduleRepository;
import com.binar.challenge5.challenge5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BookingService {

    Booking booking;
    @Autowired
    private BookingRepository repository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Booking> getAllBooking() {
        return repository.findAll();
    }

    private static String getRand() {
        String saltChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rand =  new Random();
        while (salt.length() < 6) {
            int index = (int) (rand.nextFloat() * saltChar.length());
            salt.append(saltChar.charAt(index));
        }

        return salt.toString();
    }

    public Booking postBooking(String email, Long movieId, Long scheduleId, String[] seat) {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("email does not exists"));
        if (!movieRepository.findById(movieId).isPresent()) throw new NullPointerException("movie id" + movieId + " does not have a schedule");


        Schedule byScheduleId = scheduleRepository.findByScheduleId(scheduleId);

//        booking.setMovieId(movieId);
//        booking.setUserCredential(user.getUserCredential());
//        booking.setScheduleId(scheduleId);
//        booking.setStudio(byScheduleId.getStudio());
//        booking.setMovieName(movieRepository.findById(movieId).get().getMovieName());
//        booking.setStartTime(byScheduleId.getStartTime());
//        booking.setSeat(seat);
//        booking.setStatus(Status.ON_PROCESS_PAYMENT);

        Booking booking = new Booking(getRand().toUpperCase(), movieId, scheduleId, user.getUserCredential(), byScheduleId.getStudio(),
                movieRepository.findById(movieId).get().getMovieName(), byScheduleId.getStartTime(),
                seat, (byScheduleId.getPrice() * seat.length), Status.ON_PROCESS_PAYMENT);

        return repository.save(booking);
    }

    public Booking updateStatusBooking(Long bookingId, Integer status) {
        Booking booking1 = repository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("booking id " + bookingId + " does not exists"));
        booking1.setStatus(Status.EXPIRED_PAYMENT);
        if (status == 1) booking1.setStatus(Status.FULL_PAYMENT);
        return repository.save(booking1);
    }


//    public Collection<Booking> findByBookingCodeWhere(String bookingCode) {
//        Collection<Booking> bookingByBookingCode = repository.findBookingByBookingCode(bookingCode);
//        if (bookingByBookingCode.isEmpty()) throw new IllegalArgumentException("booking code " + bookingCode + " does not exists");
//        return repository.findBookingByBookingCode(bookingCode);
//    }
//
//
//    public String printReport(String bookingCode) throws FileNotFoundException, JRException {
//        String path = "D:\\code\\.springboot\\challenge 5\\jasper";
//
//        Collection<Booking> bookingCollection = repository.findBookingByBookingCode(bookingCode);
//
//        File file = ResourceUtils.getFile("classpath:jasper-report.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookingCollection);
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("createdBy", "Lucky Alma");
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\booking.pdf");
//        return "report generate in path: " + path;
//    }
}
