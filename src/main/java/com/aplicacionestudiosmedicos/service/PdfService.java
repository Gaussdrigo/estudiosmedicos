package com.aplicacionestudiosmedicos.service;

import com.aplicacionestudiosmedicos.entities.Paciente;
import com.aplicacionestudiosmedicos.entities.PacienteImagen;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    private static final String PDF_DIR = "pdfs/";

    @Autowired
    private PacienteService pacienteService;

    public String generarPdfEstudios(Paciente paciente, LocalDate fecha, HttpServletRequest request) throws Exception {
        // ✅ 1. Crear carpeta si no existe
        if (!Files.exists(Paths.get(PDF_DIR))) {
            Files.createDirectories(Paths.get(PDF_DIR));
        }

        // ✅ 2. Ruta del archivo PDF
        String nombreArchivo = "estudios_" + paciente.getId() + "_" + fecha + ".pdf";
        String pdfPath = PDF_DIR + nombreArchivo;

        // ✅ 3. Configurar documento
        Document document = new Document(PageSize.A4, 36, 36, 54, 36); // márgenes profesionales
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        document.open();

        // ✅ 4. Fuentes elegantes
        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Font subTituloFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);
        Font fechaFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);

        // ✅ 5. Encabezado del PDF
        document.add(new Paragraph("Estudios del Paciente", tituloFont));
        document.add(new Paragraph(paciente.getNombre(), subTituloFont));
        document.add(new Paragraph("Fecha del Estudio: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fechaFont));
        document.add(new Paragraph("\n"));

        // ✅ 6. Obtener imágenes por fecha
        List<PacienteImagen> imagenes = pacienteService.obtenerImagenesPorFecha(paciente.getId(), fecha);

        if (imagenes.isEmpty()) {
            document.add(new Paragraph("No hay imágenes registradas en esta fecha.", fechaFont));
        } else {
            for (PacienteImagen imagenData : imagenes) {
                try {
                    Image imagen = Image.getInstance(imagenData.getRutaArchivo());
                    imagen.scaleToFit(450, 450); // Ajuste proporcional
                    imagen.setAlignment(Image.ALIGN_CENTER);
                    document.add(imagen);
                    document.add(new Paragraph("\n"));
                } catch (Exception e) {
                    document.add(new Paragraph("⚠ Error al cargar imagen: " + imagenData.getNombreArchivo(), fechaFont));
                }
            }
        }

        document.close();

        // ✅ 7. Construir URL pública para QR
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return baseUrl + "/" + pdfPath;
    }
}
