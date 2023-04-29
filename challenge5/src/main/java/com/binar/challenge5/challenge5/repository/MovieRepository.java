package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByIsShowingTrue();
    Optional<Movie> findMovieByMovieName(String movieName);

}
