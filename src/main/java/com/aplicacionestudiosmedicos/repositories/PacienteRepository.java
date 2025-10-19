package com.aplicacionestudiosmedicos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aplicacionestudiosmedicos.entities.Paciente;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
    List<Paciente> findByCedulaContaining(String cedula);
}