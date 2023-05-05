package com.binar.challenge5.challenge5.service;

import com.binar.challenge5.challenge5.repository.BookingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    private AutoCloseable autoCloseable;
    private BookingService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BookingService(bookingRepository);
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllBookings() {
        // when
        underTest.getAllBookings();
        // then
        verify(bookingRepository).findAll();
    }

    @Test
    void postBooking() {
    }

    @Test
    void updateStatusBooking() {
    }

    @Test
    void findByBookingCodeWhere() {
    }

    @Test
    void printReport() {
    }
}