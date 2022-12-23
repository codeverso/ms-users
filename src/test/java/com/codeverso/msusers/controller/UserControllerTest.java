package com.codeverso.msusers.controller;

import com.codeverso.msusers.controller.UserController;
import com.codeverso.msusers.model.dto.UserResponse;
import com.codeverso.msusers.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@ExtendWith(SpringExtension.class)
@DisplayName("User Controller Tests")
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private static final String USERS_ENDPOINT = "/users";

    @Test
    @DisplayName("Should return a list with all users")
    public void shouldReturnAListWithAllUsers() throws Exception {
        List<UserResponse> users = new ArrayList<>();

        String uuid = UUID.randomUUID().toString();

        UserResponse userResponse = UserResponse.builder()
                .name("Gabriel")
                .age(27)
                .uuid(uuid)
                .build();

        users.add(userResponse);

        when(this.userService.getAllUsers())
                .thenReturn(users);

        mockMvc.perform(get(USERS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].uuid", is(uuid)))
                .andExpect(jsonPath("$[0].name", is("Gabriel")))
                .andExpect(jsonPath("$[0].age", is(27)));

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }
}
