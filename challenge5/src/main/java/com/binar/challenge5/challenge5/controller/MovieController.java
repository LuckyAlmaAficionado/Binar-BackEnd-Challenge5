package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.Movie;
import com.binar.challenge5.challenge5.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping(path = "api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public List<Movie> getMovie() {
        return service.getMovie();
    }

    @GetMapping(path = "/showing")
    public List<Movie> getMovieIsShowing() {
        return service.getMovieIsShowingTrue();
    }

    @PostMapping
    public Movie addNewMovie(@RequestBody Movie movie) {
        return service.postMovie(movie);
    }

    @DeleteMapping(path = "{deleteId}")
    public String deleteMovie(@PathVariable Long deleteId) {
        return service.deleteMovieById(deleteId);
    }

    @PutMapping(path = "{updateId}")
    public String updateMovie(
            @PathVariable("updateId") Long updateId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Boolean showing) {
        return service.updateMovie(updateId, name, genre, showing);
    }
}
