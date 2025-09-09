package com.app.workshop_registration_system.Config;


import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Workshop Registration System", description = "Sistema backend para la gestión de inscripciones a talleres", version = "1.0.0", contact = @Contact(name = "Brittany Espinel", url = "https://brittany-portfolio.vercel.app/", email = "nohemiespinel0@gmail.com")), servers = {
        @Server(description = "PROD SERVER", url = "https://workshop-registration-system.onrender.com/"),
        @Server(description = "DEV SERVER", url = "http://localhost:8080")},security = @SecurityRequirement(name = "Security Token"))
@SecurityScheme(name = "Security Token", description = "Access Token for my API", type = SecuritySchemeType.HTTP, paramName = HttpHeaders.AUTHORIZATION, in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {

}
