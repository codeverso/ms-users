package com.codeverso.msusers.controller;

import com.codeverso.msusers.model.dto.UserRequest;
import com.codeverso.msusers.model.dto.UserResponse;
import com.codeverso.msusers.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable(name = "userId") String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRequest userRequest, HttpServletResponse httpServletResponse) {
        String createdUserId = userService.createUser(userRequest);
        httpServletResponse.setHeader("Location", createdUserId);
    }
}