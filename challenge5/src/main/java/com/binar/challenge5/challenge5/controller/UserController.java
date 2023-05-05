package com.binar.challenge5.challenge5.controller;

import com.binar.challenge5.challenge5.model.User;
import com.binar.challenge5.challenge5.repository.UserRepository;
import com.binar.challenge5.challenge5.service.SortAscDesc;
import com.binar.challenge5.challenge5.service.UserService;
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
@RequestMapping(path = "api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private SortAscDesc sortAscDesc;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping(path = "{id}")
    public User getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public String postUser(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password) {
        return service.addUser(username, email, password);
    }

    @PutMapping(path = "{userId}")
    public String updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password
    ) {
        return service.updateUser(userId, username, email, password);
    }

    @DeleteMapping(path = "{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return service.deleteUser(userId);
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

            Page<User> data = userRepository.findAll(pageRequest);

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
