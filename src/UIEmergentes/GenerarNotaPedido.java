/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UIEmergentes;

/**
 *
 * @author salin
 */
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;

import javax.swing.filechooser.FileSystemView;
import java.awt.Color;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GenerarNotaPedido {

    public void generarNota(DatosNota datos) {
        String outputFolderName = "Notas de Venta";
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        Path outputFolderPath = Paths.get(desktopDir.getAbsolutePath(), outputFolderName);

        try {
            if (!Files.exists(outputFolderPath)) {
                Files.createDirectories(outputFolderPath);
            }

            InputStream pdfInputStream = GenerarNotaPedido.class.getResourceAsStream("Base_Nota Pedido.pdf");

            if (pdfInputStream == null) {
                System.err.println("Error: No se encontró la plantilla PDF.");
                return;
            }

            byte[] pdfBytes = leerBytesDesdeInputStream(pdfInputStream);

            try (PDDocument document = Loader.loadPDF(pdfBytes)) {
                PDPage page = document.getPage(0);
                try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {

                    PDFont fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                    PDFont fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                    // === Folio y Metadatos ===
                    datos.folio = generarNuevoFolio(outputFolderPath);

                    float x = 486, y = 720, lh = 25;
                    cs.setNonStrokingColor(new PDColor(new float[]{1, 0, 0}, PDDeviceRGB.INSTANCE));
                    cs.setFont(fontBold, 11);
                    escribir(cs, x, y, datos.folio); 

                    cs.setNonStrokingColor(Color.BLACK);
                    cs.setFont(fontNormal, 10);
                    escribir(cs, x , y - 13, datos.fecha);


                    // === Tabla productos ===
                    float startX = 80, startY = 600, separacionX = 65, fontSize = 8;
                    float anchoDescripcion = 155, espacioLinea = 10, alturaFila = 12;

                    for (String[] fila : datos.productos) {
                        float columnaX = startX;

                        cs.setFont(fontNormal, fontSize);
                        escribir(cs, columnaX, startY, fila[0]); columnaX += separacionX;
                        escribir(cs, columnaX, startY, fila[1]); columnaX += separacionX;

                        List<String> lineas = dividirTexto(fila[2], fontNormal, fontSize, anchoDescripcion);
                        float yDescripcion = startY;
                        for (String linea : lineas) {
                            escribir(cs, columnaX, yDescripcion, linea);
                            yDescripcion -= espacioLinea;
                        }

                        escribir(cs, columnaX + 173, startY, fila[3]);
                        escribir(cs, columnaX + 248, startY, fila[4]);

                        startY = yDescripcion - alturaFila;
                    }

                    // === Totales y pie ===
                    x = 75; y = 148;
                    cs.setFont(fontBold, fontSize);
                    escribir(cs, x, y, datos.totalLetra);

                    cs.setFont(fontBold, 10);
                    escribir(cs, 240, 123, datos.usoCFDI);
                    escribir(cs, 195, 108, datos.tipoPersona);

                    x = 460; y = 148; lh = 13;
                    cs.setFont(fontNormal, 10);
                    escribir(cs, x, y, datos.subtotal); y -= lh;
                    escribir(cs, x, y, datos.descuento); y -= lh;
                    escribir(cs, x, y, datos.impuestos); y -= lh;
                    escribir(cs, x, y, datos.total);
                }

                // === Generar nombre único del archivo ===
                String fechaHoy = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
                //String nombreClienteLimpio = datos.nombreCliente.replaceAll("\\s+", "");
                String baseFileName = datos.folio + "Nota_Venta_Pedido" + "_" + fechaHoy;
                String extension = ".pdf";

                Path outputFilePath = outputFolderPath.resolve(baseFileName + extension);
                int contador = 1;

                while (Files.exists(outputFilePath)) {
                    outputFilePath = outputFolderPath.resolve(baseFileName + "_" + contador + extension);
                    contador++;
                }

                document.save(outputFilePath.toString());
                System.out.println("Factura generada: " + outputFilePath.toString());

            }
        } catch (IOException e) {
            System.err.println("Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void escribir(PDPageContentStream cs, float x, float y, String texto) throws IOException {
        cs.beginText();
        cs.newLineAtOffset(x, y);
        cs.showText(texto);
        cs.endText();
    }

    private List<String> dividirTexto(String texto, PDFont font, float fontSize, float anchoMax) throws IOException {
        List<String> lineas = new ArrayList<>();
        StringBuilder linea = new StringBuilder();
        for (String palabra : texto.split(" ")) {
            String prueba = linea + (linea.length() > 0 ? " " : "") + palabra;
            float ancho = font.getStringWidth(prueba) / 1000 * fontSize;
            if (ancho < anchoMax) {
                if (linea.length() > 0) linea.append(" ");
                linea.append(palabra);
            } else {
                lineas.add(linea.toString());
                linea = new StringBuilder(palabra);
            }
        }
        if (!linea.isEmpty()) {
            lineas.add(linea.toString());
        }
        return lineas;
    }

    private byte[] leerBytesDesdeInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int nRead;
        while ((nRead = inputStream.read(data)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    private String generarNuevoFolio(Path carpetaFacturas) throws IOException {
        String prefijo = "A";
        int numero = 1;

        if (!Files.exists(carpetaFacturas)) return String.format("%s-%05d", prefijo, numero);

        int maxNumero = 0;
        String maxPrefijo = "A";

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(carpetaFacturas, "*.pdf")) {
            for (Path archivo : stream) {
                String nombre = archivo.getFileName().toString();
                // Buscar patrón del folio A-00001
                if (nombre.matches("A-\\d{5}.*\\.pdf")) {
                    String folioParte = nombre.substring(0, 7);
                    String numeroStr = folioParte.substring(2);
                    try {
                        int num = Integer.parseInt(numeroStr);
                        if (num > maxNumero) {
                            maxNumero = num;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar nombres inválidos
                    }
                }
            }
        }

        if (maxNumero >= 99999) {
            char nuevoPrefijo = (char) (maxPrefijo.charAt(0) + 1);
            return String.format("%s-00001", nuevoPrefijo);
        } else {
            return String.format("%s-%05d", maxPrefijo, maxNumero + 1);
        }
    }
}