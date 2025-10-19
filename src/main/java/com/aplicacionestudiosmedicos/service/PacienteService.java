package com.aplicacionestudiosmedicos.service;

import com.aplicacionestudiosmedicos.entities.Paciente;
import com.aplicacionestudiosmedicos.entities.PacienteImagen;
import com.aplicacionestudiosmedicos.repositories.PacienteImagenRepository;
import com.aplicacionestudiosmedicos.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PacienteService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteImagenRepository imagenRepository;

    // 🔹 CRUD de Paciente
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente obtenerPorId(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    // 🔹 Obtener imágenes de un paciente
    public List<PacienteImagen> obtenerImagenesPorPaciente(Long pacienteId) {
        Paciente paciente = obtenerPorId(pacienteId);
        return (paciente != null) ? imagenRepository.findByPaciente(paciente) : List.of();
    }
    public List<PacienteImagen> obtenerImagenesPorFecha(Long pacienteId, LocalDate fecha) {
        Paciente paciente = obtenerPorId(pacienteId);
        if (paciente == null) return List.of();

        // Definimos el rango del día (desde las 00:00 hasta 23:59:59.999999999)
        LocalDateTime desde = fecha.atStartOfDay();
        LocalDateTime hasta = fecha.plusDays(1).atStartOfDay().minusNanos(1);

        return imagenRepository.findByPacienteAndFechaSubidaBetween(paciente, desde, hasta);
    }

    // 🔹 Guardar imagen asociada a un paciente
    public void guardarImagenPaciente(Long pacienteId, MultipartFile file) throws IOException {
        if (file.isEmpty()) return;

        Paciente paciente = obtenerPorId(pacienteId);
        if (paciente == null) throw new IOException("Paciente no encontrado");

        // Crear carpeta uploads/ si no existe
        File carpeta = new File(UPLOAD_DIR);
        if (!carpeta.exists()) carpeta.mkdirs();

        // Ruta del archivo
        String ruta = UPLOAD_DIR + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(ruta), StandardCopyOption.REPLACE_EXISTING);

        // Guardar información en la base
        PacienteImagen imagen = new PacienteImagen();
        imagen.setPaciente(paciente);
        imagen.setNombreArchivo(file.getOriginalFilename());
        imagen.setRutaArchivo(ruta);
        imagenRepository.save(imagen);
    }

    // 🔹 Obtener todas las imágenes del sistema (por si quieres mostrar en /home)
    public List<PacienteImagen> obtenerTodasLasImagenes() {
        return imagenRepository.findAll();
    }
}
