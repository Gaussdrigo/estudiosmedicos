package com.aplicacionestudiosmedicos.service;

import com.aplicacionestudiosmedicos.entities.Usuario;
import com.aplicacionestudiosmedicos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Registrar usuario con contraseña cifrada
    public Usuario registrarUsuario(String nombre, String password) {
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setPassword(passwordEncoder.encode(password));
        return usuarioRepository.save(u);
    }

    // Buscar usuario por nombre
    public Usuario buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    //Validar login (con BCrypt)
    public boolean validarLogin(String nombre, String passwordPlano) {
        Usuario usuario = usuarioRepository.findByNombre(nombre);

        if (usuario == null) {
            return false; // Usuario no encontrado
        }

        // Compara la contraseña ingresada con el hash almacenado
        return passwordEncoder.matches(passwordPlano, usuario.getPassword());
    }
}
