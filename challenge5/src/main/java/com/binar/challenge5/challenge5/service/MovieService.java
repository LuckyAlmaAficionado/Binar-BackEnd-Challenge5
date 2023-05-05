package com.binar.challenge5.challenge5.service;

import com.binar.challenge5.challenge5.model.Movie;
import com.binar.challenge5.challenge5.repository.BookingRepository;
import com.binar.challenge5.challenge5.repository.MovieRepository;
import com.binar.challenge5.challenge5.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    public List<Movie> getMovie() {
        return repository.findAll();
    }

    public Movie getById(Long movieId) { return repository.getById(movieId); }

    public List<Movie> getMovieIsShowingTrue() {
        List<Movie> collect = repository.findByIsShowingTrue().stream()
                .filter(scheduleList -> !scheduleList.getSchedulesList().isEmpty())
                .collect(Collectors.toList());
        return collect;
    }

    public Movie postMovie(Movie movie) {
        Optional<Movie> exists = repository.findMovieByMovieName(movie.getMovieName());
        if (exists.isPresent()) throw new IllegalArgumentException("movie taken");
        return repository.save(movie);
    }

    public String deleteMovieById(Long deleteId) {
        Optional<Movie> exists = repository.findById(deleteId);
        if (!exists.isPresent()) throw new IllegalArgumentException("can't find movie");
        repository.deleteById(deleteId);
        scheduleRepository.deleteByMovieFk(deleteId);
        return "movie delete..!";
    }

    @Transactional
    public String updateMovie(Long updateId, String movieName, String genre, Boolean isShowing) {
        Movie movie = repository.findById(updateId).orElseThrow(() -> new IllegalArgumentException("movie with id " + updateId + " does not exists"));

        if (!movieName.isEmpty() && !Objects.equals(movieName, movie.getMovieName())) {
            movie.setMovieName(movieName);
        }

        if (!genre.isEmpty() && !Objects.equals(genre, movie.getGenre())) {
            movie.setGenre(genre);
        }

        movie.setIsShowing(isShowing);

        return "data berhasil di update";
    }
}
