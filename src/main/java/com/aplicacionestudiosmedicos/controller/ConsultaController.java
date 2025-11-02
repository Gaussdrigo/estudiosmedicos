package com.aplicacionestudiosmedicos.controller;

import com.aplicacionestudiosmedicos.entities.Paciente;
import com.aplicacionestudiosmedicos.entities.PacienteImagen;
import com.aplicacionestudiosmedicos.service.PacienteService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


@Controller
public class ConsultaController {
     @Autowired
    private PacienteService pacienteService;

    @GetMapping("/consulta")
    public String mostrarConsulta() {
        return "consulta_paciente";
    }

    @GetMapping("/consulta/buscar")
    public String buscarPorCedula(@RequestParam("cedula") String cedula, Model model) {
        Optional<Paciente> pacienteOpt = pacienteService.obtenerPorCedula(cedula);

        if (!pacienteOpt.isPresent()) {
            model.addAttribute("errorMsg", "No existe paciente con ese número de cédula");
            return "consulta_paciente";
        }
        Paciente paciente = pacienteOpt.get();
        List<PacienteImagen> imagenes = paciente.getImagenes();
        Map<LocalDate, List<PacienteImagen>> imagenesPorFecha = imagenes.stream()
                .collect(Collectors.groupingBy(img -> img.getFechaSubida().toLocalDate(), TreeMap::new, Collectors.toList()));
        model.addAttribute("paciente", paciente);
        model.addAttribute("imagenesPorFecha", imagenesPorFecha);
        return "consulta_paciente";
    }
    
}
