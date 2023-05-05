package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByIsShowingTrue();
    Optional<Movie> findMovieByMovieName(String movieName);

    @Query(value = "SELECT m FROM Movie m WHERE movieId = :movieId")
    Movie getById(@Param("movieId") Long movieId);

}
