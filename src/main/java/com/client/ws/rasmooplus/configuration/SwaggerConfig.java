package com.client.ws.rasmooplus.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(info()).servers(List.of(server()));
    }

    private Server server() {
        Server server = new Server();
        server.setUrl("http://localhost:8082");
        server.setDescription("Servidor");
        return server;
    }

    private Contact contact() {
        Contact contact = new Contact();
        contact.setEmail("rasmoo@email.com");
        contact.setName("Rasmoo");
        contact.setUrl("https://www.rasmoo.com");
        return contact;
    }

    private License license() {
        return new License().name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");
    }

    private Info info() {
        return new Info().title("Api rasmooplus")
                .version("1.0")
                .contact(contact())
                .description("Essa Api expõe os endepoints para gerenciar o rasmooplus")
                .termsOfService("https://www.rasmoo.com/terms")
                .license(license());
    }

}
