package com.aplicacionestudiosmedicos.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aplicacionestudiosmedicos.entities.Paciente;
import com.aplicacionestudiosmedicos.entities.PacienteImagen;
import com.aplicacionestudiosmedicos.service.PacienteService;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteService.listarTodos();
        model.addAttribute("pacientes", pacientes);
        return "pacientes";
    }

    //  Mostrar formulario para crear nuevo paciente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "paciente_form";
    }

    //  Guardar paciente
    @PostMapping("/guardar")
    public String guardarPaciente(@ModelAttribute Paciente paciente, RedirectAttributes redirectAttrs) {
        try {
            Paciente pacienteGuardado = pacienteService.guardar(paciente);
            redirectAttrs.addFlashAttribute("msg", "Paciente registrado correctamente ✅");
            return "redirect:/pacientes/" + pacienteGuardado.getId() + "/imagenes";

        } catch (IllegalArgumentException e) {
            Optional<Paciente> pacienteExistente = pacienteService.obtenerPorCedula(paciente.getCedula());

            if (pacienteExistente.isPresent()) {
                redirectAttrs.addFlashAttribute("msg", "El paciente ya existe. Se muestran sus imágenes.");
                return "redirect:/pacientes/" + pacienteExistente.get().getId() + "/imagenes";
            }

            redirectAttrs.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/pacientes/nuevo";
        }
    }
    //  Eliminar paciente
    @PostMapping("/{id}/eliminar")
    public String eliminarPaciente(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            pacienteService.eliminarPorId(id);
            redirectAttrs.addFlashAttribute("msg", "Paciente eliminado correctamente ✅");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("msg", "Error al eliminar el paciente ❌");
        }
        return "redirect:/pacientes";
    }

    //  Ver imágenes de un paciente específico
   @GetMapping("/{id}/imagenes")
    public String verImagenesPaciente(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteService.obtenerPorId(id);
        List<PacienteImagen> imagenes = paciente.getImagenes();

        //  Agrupar imágenes por fecha (solo día, no hora)
        Map<LocalDate, List<PacienteImagen>> imagenesPorFecha = imagenes.stream()
                .collect(Collectors.groupingBy(img -> img.getFechaSubida().toLocalDate(),
                        TreeMap::new, Collectors.toList())); // TreeMap para ordenar por fecha

        model.addAttribute("paciente", paciente);
        model.addAttribute("imagenesPorFecha", imagenesPorFecha);
        return "paciente_imagenes";
    }


    //  Subir imagen para un paciente
    @PostMapping("/{id}/imagenes/subir")
    public String subirImagenPaciente(@PathVariable Long id,
            @RequestParam("imagen") MultipartFile imagen,
            Model model) {
        try {
            pacienteService.guardarImagenPaciente(id, imagen);
            model.addAttribute("msg", "Imagen subida correctamente");
        } catch (IOException e) {
            model.addAttribute("msg", "Error al subir imagen: " + e.getMessage());
        }
        return "redirect:/pacientes/" + id + "/imagenes";
    }
    @GetMapping("/{id}/qr")
    public String generarQrYMostrar(
            @PathVariable Long id,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {

        // 1. Verificar paciente
        Paciente paciente = pacienteService.obtenerPorId(id);
        if (paciente == null) {
            model.addAttribute("msg", "Paciente no encontrado");
            return "error";
        }

        // Funcionalidad temporalmente deshabilitada para evitar generar y almacenar PDF/QR.
        model.addAttribute("paciente", paciente);
        model.addAttribute("fecha", fecha);
        model.addAttribute("msg", "La generacion de PDF y QR esta temporalmente deshabilitada.");

        return "ver_qr"; // nombre de la vista
    }
}
