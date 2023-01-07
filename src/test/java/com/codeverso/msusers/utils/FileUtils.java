package com.codeverso.msusers.utils;

import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;

public class FileUtils {

    public static String getJSONFromFile(String fileName) throws Exception {
        ClassPathResource resource = new ClassPathResource(fileName);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }
}
