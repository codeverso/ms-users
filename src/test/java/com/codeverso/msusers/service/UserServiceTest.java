package com.codeverso.msusers.service;

import com.codeverso.msusers.model.dto.UserResponse;
import com.codeverso.msusers.model.entity.UserEntity;
import com.codeverso.msusers.repository.UserRepository;
import com.codeverso.msusers.service.UserService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User service test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final List<UserEntity> entities = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        UserEntity userOne = UserEntity.builder()
                .age(20)
                .name("Bob")
                .uuid(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        UserEntity userTwo = UserEntity.builder()
                .age(30)
                .name("John")
                .uuid(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        entities.add(userOne);
        entities.add(userTwo);
    }

    @Test
    @DisplayName("Should return a list with all users")
    public void shouldReturnAListWithAllUsers() {
        //given
        given(userRepository.findAll())
                .willReturn(entities);

        List<UserResponse> entitiesConverted = entities.stream()
                .map(UserResponse::valueOf)
                .toList();

        //when
        List<UserResponse> userResponses = userService.getAllUsers();

        //then
        assertEquals(entitiesConverted, userResponses);

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Should return an empty list")
    public void shouldReturnAnEmptyList() {
        given(userRepository.findAll())
                .willReturn(Collections.emptyList());

        List<UserResponse> userResponses = userService.getAllUsers();

        assertEquals(userResponses.size(), 0);

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }
}
