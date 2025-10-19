package com.aplicacionestudiosmedicos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.aplicacionestudiosmedicos.service.UsuarioService;

@SpringBootApplication
public class EstudiosmedicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudiosmedicosApplication.class, args);

	}
	@Bean
	CommandLineRunner init(UsuarioService usuarioService) {
        return args -> {
            // Verificamos si el usuario no existe ya
            if (usuarioService.buscarPorNombre("doctor1") == null) {
                usuarioService.registrarUsuario("doctor1", "1234");
                System.out.println("✅ Usuario 'doctor1' creado con contraseña encriptada.");
            }
        };
    }
}
