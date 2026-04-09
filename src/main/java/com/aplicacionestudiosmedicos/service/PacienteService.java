package com.aplicacionestudiosmedicos.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aplicacionestudiosmedicos.entities.Paciente;
import com.aplicacionestudiosmedicos.entities.PacienteImagen;
import com.aplicacionestudiosmedicos.repositories.PacienteImagenRepository;
import com.aplicacionestudiosmedicos.repositories.PacienteRepository;
import com.aplicacionestudiosmedicos.service.storage.ArchivoStorageService;
import com.aplicacionestudiosmedicos.service.storage.StoredFile;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteImagenRepository imagenRepository;

    @Autowired
    private ArchivoStorageService archivoStorageService;

    //  CRUD de Paciente
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente guardar(Paciente paciente) {
    boolean existe = pacienteRepository.existsByCedula(paciente.getCedula());
        if (existe) {
            throw new IllegalArgumentException("Paciente ya registrado con esa cédula");
        }
        return pacienteRepository.save(paciente);
    }
    public void eliminarPorId(Long id) {
        pacienteRepository.deleteById(id);
    }
    public Paciente obtenerPorId(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }
    public Optional<Paciente> obtenerPorCedula(String cedula) {
        return pacienteRepository.findByCedula(cedula);
    }

    //  Obtener imágenes de un paciente
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

    //  Guardar imagen asociada a un paciente
    public void guardarImagenPaciente(Long pacienteId, MultipartFile file) throws IOException {
        if (file.isEmpty()) return;

        Paciente paciente = obtenerPorId(pacienteId);
        if (paciente == null) throw new IOException("Paciente no encontrado");

        StoredFile archivoSubido = archivoStorageService.uploadImage(file, "estudiosmedicos/imagenes");

        // Guardar información en la base
        PacienteImagen imagen = new PacienteImagen();
        imagen.setPaciente(paciente);
        imagen.setNombreArchivo(file.getOriginalFilename());
        imagen.setRutaArchivo(archivoSubido.secureUrl());
        imagen.setPublicUrl(archivoSubido.secureUrl());
        imagen.setPublicId(archivoSubido.publicId());
        imagen.setResourceType(archivoSubido.resourceType());
        imagen.setStorageProvider(archivoStorageService.getClass().getSimpleName());
        imagenRepository.save(imagen);
    }

    //  Obtener todas las imágenes del sistema (por si quieres mostrar en /home)
    public List<PacienteImagen> obtenerTodasLasImagenes() {
        return imagenRepository.findAll();
    }
}
