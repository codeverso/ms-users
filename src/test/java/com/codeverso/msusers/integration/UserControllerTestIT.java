package com.codeverso.msusers.integration;

import com.codeverso.msusers.integration.core.IntegrationTest;
import com.codeverso.msusers.model.entity.UserEntity;
import com.codeverso.msusers.repository.UserRepository;
import com.codeverso.msusers.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

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

        mockMvc.perform(get(USERS_ENDPOINT))
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

        mockMvc.perform(get(USER_BY_ID_ENDPOINT, uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", hasLength(UUID.randomUUID().toString().length())))
                .andExpect(jsonPath("$.name", is("Murillo")))
                .andExpect(jsonPath("$.age", is(33)));
    }

    @Test
    @DisplayName("Should return 404 NotFound when the id does not exist")
    public void shouldReturnNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        String invalidId = "abc123";

        mockMvc.perform(get(USER_BY_ID_ENDPOINT, invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should create a new user")
    public void shouldCreateANewUser() throws Exception {
        String requestBody = FileUtils.getJSONFromFile("createUser.json");

        mockMvc.perform(post(USERS_ENDPOINT)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", hasLength(UUID.randomUUID().toString().length())))
                .andExpect(jsonPath("$.name", is("johndoe")))
                .andExpect(jsonPath("$.age", is(33)));
    }
}
