package com.aplicacionestudiosmedicos.service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplicacionestudiosmedicos.entities.Paciente;
import com.aplicacionestudiosmedicos.entities.PacienteImagen;
import com.aplicacionestudiosmedicos.service.storage.ArchivoStorageService;
import com.aplicacionestudiosmedicos.service.storage.StoredFile;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ArchivoStorageService archivoStorageService;

    public String generarPdfEstudios(Paciente paciente, LocalDate fecha) throws Exception {
        String nombreArchivo = "estudios_" + paciente.getId() + "_" + fecha + ".pdf";

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        PdfWriter.getInstance(document, pdfOutputStream);
        document.open();

        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Font subTituloFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);
        Font fechaFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);

        document.add(new Paragraph("Estudios del Paciente", tituloFont));
        document.add(new Paragraph(paciente.getNombre(), subTituloFont));
        document.add(new Paragraph("Fecha del Estudio: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fechaFont));
        document.add(new Paragraph("\n"));

        List<PacienteImagen> imagenes = pacienteService.obtenerImagenesPorFecha(paciente.getId(), fecha);

        if (imagenes.isEmpty()) {
            document.add(new Paragraph("No hay imágenes registradas en esta fecha.", fechaFont));
        } else {
            for (PacienteImagen imagenData : imagenes) {
                try {
                    Image imagen = resolverImagen(imagenData.getUrlAcceso());
                    imagen.scaleToFit(450, 450);
                    imagen.setAlignment(Image.ALIGN_CENTER);
                    document.add(imagen);
                    document.add(new Paragraph("\n"));
                } catch (Exception e) {
                    document.add(new Paragraph("⚠ Error al cargar imagen: " + imagenData.getNombreArchivo(), fechaFont));
                }
            }
        }

        document.close();

        StoredFile archivoPdf = archivoStorageService.uploadPdf(
                pdfOutputStream.toByteArray(),
                nombreArchivo,
                "estudiosmedicos/pdfs"
        );

        return archivoPdf.secureUrl();
    }

    private Image resolverImagen(String rutaArchivo) throws Exception {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new IllegalArgumentException("La imagen no tiene ruta disponible");
        }

        if (rutaArchivo.startsWith("http://") || rutaArchivo.startsWith("https://")) {
            return Image.getInstance(new URL(rutaArchivo));
        }

        String rutaNormalizada = rutaArchivo.startsWith("/") ? rutaArchivo.substring(1) : rutaArchivo;
        Path rutaLocal = Paths.get(rutaNormalizada).toAbsolutePath();
        if (Files.notExists(rutaLocal)) {
            throw new IllegalArgumentException("No se encontró la imagen en la ruta local: " + rutaLocal);
        }
        return Image.getInstance(rutaLocal.toString());
    }
}
