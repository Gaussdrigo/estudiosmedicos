package com.aplicacionestudiosmedicos.controller;

import com.aplicacionestudiosmedicos.entities.Usuario;
import com.aplicacionestudiosmedicos.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    // Página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
    @GetMapping("/")
    public String redirigir() {
        return "redirect:/consulta";
    }

    @PostMapping("/auth-login")
    @ResponseBody
    public String procesarLogin(
            @RequestParam String nombre,
            @RequestParam String password,
            HttpServletRequest request) {

        boolean valido = usuarioService.validarLogin(nombre, password);

        if (valido) {
            Usuario usuario = usuarioService.buscarPorNombre(nombre);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    usuario.getNombre(),
                    null,
                    Collections.emptyList());

            // Registrar autenticación en el contexto
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Vincular autenticación a la sesión
            request.getSession(true).setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            return "OK";
        } else {
            return "ERROR";
        }
    }
}
