package ru.filit.notificationapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "notification-app",
                version = "0.1"
        ),
        servers = {
                @Server(description = "dev", url = "http://localhost:8080")
        }
)
@Configuration
public class SwaggerConfig {

}
