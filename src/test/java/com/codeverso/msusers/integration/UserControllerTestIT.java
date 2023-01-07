package com.codeverso.msusers.integration;

import com.codeverso.msusers.integration.core.IntegrationTest;
import com.codeverso.msusers.model.entity.UserEntity;
import com.codeverso.msusers.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
                .andExpect(jsonPath("$[0].uuid").isNotEmpty())
                .andExpect(jsonPath("$[0].name", is("Murillo")))
                .andExpect(jsonPath("$[0].age", is(33)))
                .andExpect(jsonPath("$[1].uuid").isNotEmpty())
                .andExpect(jsonPath("$[1].name", is("Babler")))
                .andExpect(jsonPath("$[1].age", is(27)))
        ;
    }

    @Test
    @DisplayName("Should return an user by id")
    public void shouldReturnAnUserById() throws Exception {
        UserEntity userMurillo = UserEntity.builder()
                .age(33)
                .name("Murillo")
                .build();

        userRepository.save(userMurillo);

        mvc.perform(get(USERS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].uuid").isNotEmpty())
                .andExpect(jsonPath("$[0].name", is("Murillo")))
                .andExpect(jsonPath("$[0].age", is(33)))
        ;
    }

    @AfterEach
    void tearDown() {
        deleteFromTables(jdbcTemplate, "users");
    }
}
