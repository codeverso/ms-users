package com.codeverso.msusers.integration.factory;

import com.codeverso.msusers.model.entity.UserEntity;

public class UserFactory {
    public static UserEntity createUser(String name, int age) {
        return UserEntity.builder()
                .name(name)
                .age(age)
                .build();
    }
}
