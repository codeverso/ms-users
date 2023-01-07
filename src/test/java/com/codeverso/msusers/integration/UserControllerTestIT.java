package com.codeverso.msusers.integration;

import com.codeverso.msusers.integration.core.IntegrationTest;
import com.codeverso.msusers.model.entity.UserEntity;
import com.codeverso.msusers.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserControllerTestIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String USERS_ENDPOINT = "/users";
    private static final String USER_BY_ID_ENDPOINT = "/users/{userId}";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() throws Exception {
        UserEntity userMurillo = UserEntity.builder()
                .age(33)
                .name("Murillo")
                .build();

        UserEntity userBabler = UserEntity.builder()
                .age(27)
                .name("Babler")
                .build();

        userRepository.save(userMurillo);
        userRepository.save(userBabler);

        mvc.perform(get(USERS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].uuid", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Murillo", "Babler")))
                .andExpect(jsonPath("$[*].age", containsInAnyOrder(33, 27)));
    }

    @Test
    @DisplayName("Should return an user by id")
    public void shouldReturnAnUserById() throws Exception {
        UserEntity userMurillo = UserEntity.builder()
                .age(33)
                .name("Murillo")
                .build();

        UserEntity userSaved = userRepository.save(userMurillo);
        String uuid = userSaved.getUuid();

        mvc.perform(get(USER_BY_ID_ENDPOINT, uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", hasLength(UUID.randomUUID().toString().length())))
                .andExpect(jsonPath("$.name", is("Murillo")))
                .andExpect(jsonPath("$.age", is(33)));
    }
}
