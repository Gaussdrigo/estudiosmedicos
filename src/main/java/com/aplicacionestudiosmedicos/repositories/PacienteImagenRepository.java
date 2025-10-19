package com.aplicacionestudiosmedicos.repositories;

import com.aplicacionestudiosmedicos.entities.Paciente;
import com.aplicacionestudiosmedicos.entities.PacienteImagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PacienteImagenRepository extends JpaRepository<PacienteImagen, Long> {
    List<PacienteImagen> findByPaciente(Paciente paciente); 
    List<PacienteImagen> findByPacienteAndFechaSubidaBetween(
            Paciente paciente,
            LocalDateTime desde,
            LocalDateTime hasta
    );
}
