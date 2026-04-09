package com.aplicacionestudiosmedicos.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "paciente_imagenes")
public class PacienteImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;

    @Column(length = 1000)
    private String rutaArchivo;

    @Column(length = 1000)
    private String publicUrl;

    @Column(length = 255)
    private String publicId;

    @Column(length = 32)
    private String resourceType;

    @Column(length = 32)
    private String storageProvider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    private LocalDateTime fechaSubida = LocalDateTime.now();

    @Transient
    public String getUrlAcceso() {
        if (publicUrl != null && !publicUrl.isBlank()) {
            return publicUrl;
        }
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            return rutaArchivo;
        }
        if (rutaArchivo.startsWith("http://") || rutaArchivo.startsWith("https://") || rutaArchivo.startsWith("/")) {
            return rutaArchivo;
        }
        return "/" + rutaArchivo;
    }
}
