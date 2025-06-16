package com.app.workshop_registration_system.Utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    public String loadHtmlTemplate(String path) {
        try {
            var resource = new ClassPathResource(path);
            try (InputStream inputStream = resource.getInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar la plantilla HTML: " + path, e);
        }
    }

}
