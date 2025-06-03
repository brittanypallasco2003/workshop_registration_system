package com.app.workshop_registration_system.Utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    public String loadHtmlTemplate(String path) {
        try {
            var resource = new ClassPathResource(path);
            byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar la plantilla HTML", e);
        }
    }

}
