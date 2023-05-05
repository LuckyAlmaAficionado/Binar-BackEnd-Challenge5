package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.Movie;
import com.binar.challenge5.challenge5.repository.MovieRepository;
import com.binar.challenge5.challenge5.service.MovieService;
import com.binar.challenge5.challenge5.service.SortAscDesc;
import com.binar.challenge5.challenge5.utils.MessageModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping(path = "api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    private SortAscDesc sortAscDesc;

    @Autowired
    private MovieRepository movieRepository;

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

    @GetMapping("/pagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Movie> data = movieRepository.findAll(pageRequest);

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
