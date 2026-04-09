package com.aplicacionestudiosmedicos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.aplicacionestudiosmedicos.service.UsuarioService;

@SpringBootApplication
public class EstudiosmedicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudiosmedicosApplication.class, args);

	}
	@Bean
	CommandLineRunner init(UsuarioService usuarioService, Environment environment) {
        return args -> {
            String adminUsername = environment.getProperty("app.admin.username", "");
            String adminPassword = environment.getProperty("app.admin.password", "");

            if (adminUsername == null || adminUsername.isBlank() || adminPassword == null || adminPassword.isBlank()) {
                System.out.println("ℹ️ No se configuró usuario admin inicial. Se omite la creación automática.");
                return;
            }

            if (usuarioService.buscarPorNombre(adminUsername) == null) {
                usuarioService.registrarUsuario(adminUsername, adminPassword);
                System.out.println("✅ Usuario admin inicial creado desde variables de entorno.");
            } else {
                System.out.println("⚠️ El usuario admin inicial ya existe. No se creó un nuevo usuario.");
            }
        };
    }
}
