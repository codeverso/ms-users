package com.codeverso.msusers.controller;

import com.codeverso.msusers.exception.NotFoundException;
import com.codeverso.msusers.model.dto.UserResponse;
import com.codeverso.msusers.service.UserService;
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

import static org.hamcrest.Matchers.*;
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
    private static final String USER_BY_ID_ENDPOINT = "/users/{userId}";

    @Test
    @DisplayName("Should return 404 NotFound when the id does not exist")
    public void shouldReturnNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        String invalidId = "abc123";

        when(userService.getUserById(invalidId))
                .thenThrow(new NotFoundException());

        mockMvc.perform(get(USER_BY_ID_ENDPOINT, invalidId))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(invalidId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Should return an user by id")
    public void shouldReturnAnUserById() throws Exception {
        String uuid = UUID.randomUUID().toString();

        UserResponse userResponse = UserResponse.builder()
                .name("Gabriel")
                .age(27)
                .uuid(uuid)
                .build();

        when(userService.getUserById(uuid))
                .thenReturn(userResponse);

        mockMvc.perform(get(USER_BY_ID_ENDPOINT, uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(uuid)))
                .andExpect(jsonPath("$.name", is("Gabriel")))
                .andExpect(jsonPath("$.age", is(27)));

        verify(userService, times(1)).getUserById(uuid);
        verifyNoMoreInteractions(userService);
    }

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

        when(userService.getAllUsers())
                .thenReturn(users);

        mockMvc.perform(get(USERS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].uuid", containsInAnyOrder(uuid)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Gabriel")))
                .andExpect(jsonPath("$[*].age", containsInAnyOrder(27)));

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

}
