/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author salin
 */

import java.awt.Color;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import javax.swing.filechooser.FileSystemView;


public class ModificaPDF {

    public static void main(String[] args) {
        String outputFolderName = "Facturas";
        String outputFileName = "Factura_";
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        Path outputFolderPath = Paths.get(desktopDir.getAbsolutePath(), "Facturas");

        try {
            if (!Files.exists(outputFolderPath)) {
                Files.createDirectories(outputFolderPath);
                System.out.println("Carpeta creada en el escritorio: " + outputFolderPath.toString());
            }

            //System.out.println("Ruta encontrada: " + ModificaPDF.class.getClassLoader().getResource("/PDF/Referencia_FACTURA.pdf"));

            
            InputStream pdfInputStream = ModificaPDF.class.getResourceAsStream("Referencia_FACTURA.pdf");


            if (pdfInputStream == null) {
                System.err.println("Error: No se pudo encontrar el archivo Referencia_FACTURA.pdf dentro del paquete.");
                return;
            }

            // Convertir el InputStream a byte[]
            byte[] pdfBytes = leerBytesDesdeInputStream(pdfInputStream);

            try (PDDocument document = Loader.loadPDF(pdfBytes)) {
                PDPage page = document.getPage(0);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    float startX = 473;
                    float startY = 705;
                    float lineHeight = 25;
                    float fontSize = 11;

                    PDFont fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                    PDFont fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                    PDColor redColor = new PDColor(new float[]{1, 0, 0}, PDDeviceRGB.INSTANCE);

                    contentStream.setNonStrokingColor(redColor);
                    contentStream.setFont(fontBold, fontSize);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.showText("A - 00001");
                    contentStream.endText();
                    startY -= lineHeight;

                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(fontNormal, 9);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX-128, startY);
                    contentStream.showText("755AD17E-C125-43CE-B4B7-738AFC3A5FBB");
                    contentStream.endText();
                    startY -= lineHeight;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX-40, startY);
                    contentStream.showText("20001000000100005868");
                    contentStream.endText();
                    startY -= lineHeight;

                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(fontNormal, 10);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX-35, startY-2);
                    contentStream.showText("02-10-2013 01:01:04");
                    contentStream.endText();
                    
                     //Datos Cliente
                    float startX2 = 142;
                    float startY2 = 602;
                    float lineHeight2 = 13;
                    float fontSize2 = 10;
                    
                    contentStream.setFont(fontBold, fontSize2);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX2, startY2);
                    contentStream.showText("JORGE A. RAUL");
                    contentStream.endText();
                    startY2 -= lineHeight2;

                    contentStream.setNonStrokingColor(Color.BLACK);
                   contentStream.setFont(fontBold, fontSize2);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX2, startY2);
                    contentStream.showText("ALUJ010101KL");
                    contentStream.endText();
                    startY2 -= lineHeight2;

                    contentStream.setFont(fontBold, fontSize2);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX2, startY2);
                    contentStream.showText("27 de Febrero No Ext. 1527 No Int. 2 Col. Centro");
                    contentStream.endText();
                    startY2 -= lineHeight2;
                    
                    contentStream.setFont(fontBold, fontSize2);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX2, startY2);
                    contentStream.showText("Villahermosa, Centro, Tabasco,  C.P. 86069, México");
                    contentStream.endText();
                    startY2 -= lineHeight2;
                    
                    contentStream.setFont(fontBold, fontSize2);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX2, startY2-2);
                    contentStream.showText("9932662897");
                    contentStream.endText();
                    
                    
                    //Tabla
                    
                    float startXTabla = 80;
                    float startYTabla = 497;
                    float separacionX = 65;
                    float fontSizeTabla = 8;
                    float anchoDescripcion = 155; // desde x=210 hasta x=365
                    float alturaFila = 12; // espacio entre filas
                    float espacioLinea = 10;

                    // Supongamos que tienes una lista de filas:
                    List<String[]> filas = Arrays.asList(
                    new String[]{"1.000", "No Aplica", "FUNDA PROTECTORA CON BATERIA EXTERNA RECARGABLE PARA IPHONE 4 Y 4S", "602.590", "602.590"},
                    new String[]{"1.000", "No Aplica", "AUXILIARY AUDIO CABLE (2010 PKGING)", "188.790", "188.790"}
                    );

                    for (String[] fila : filas) {
                        float x = startXTabla;

                        // Cantidad
                        contentStream.setFont(fontNormal, fontSizeTabla);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(x, startYTabla);
                        contentStream.showText(fila[0]);
                        contentStream.endText();
                        x += separacionX;

                        // Unidad
                        contentStream.setFont(fontNormal, fontSizeTabla);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(x, startYTabla);
                        contentStream.showText(fila[1]);
                        contentStream.endText();
                        x += separacionX;

                        // Descripción con saltos de línea
                        String descripcion = fila[2];
                        List<String> lineasDescripcion = new ArrayList<>();
                        String[] palabras = descripcion.split(" ");
                        StringBuilder lineaActual = new StringBuilder();

                        for (String palabra : palabras) {
                        String pruebaLinea = lineaActual + (lineaActual.length() > 0 ? " " : "") + palabra;
                        float ancho = fontNormal.getStringWidth(pruebaLinea) / 1000 * fontSizeTabla;
                        if (ancho < anchoDescripcion) {
                        lineaActual.append((lineaActual.length() > 0 ? " " : "")).append(palabra);
                        } else {
                        lineasDescripcion.add(lineaActual.toString());
                         lineaActual = new StringBuilder(palabra);
                            }
                        }
                        if (!lineaActual.isEmpty()) {
                             lineasDescripcion.add(lineaActual.toString());
                         }

                        float yDescripcionFinal = startYTabla;
                        for (String linea : lineasDescripcion) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(x, yDescripcionFinal);
                            contentStream.showText(linea);
                            contentStream.endText();
                            yDescripcionFinal -= espacioLinea;
                        }

                        // P. Unitario (alineado con la PRIMERA línea de descripción)
                        contentStream.beginText();
                        contentStream.setFont(fontNormal, fontSizeTabla);
                        contentStream.newLineAtOffset(x + 173, startYTabla);
                        contentStream.showText(fila[3]);
                        contentStream.endText();

                        // Importe (alineado con la PRIMERA línea de descripción)
                        contentStream.beginText();
                        contentStream.setFont(fontNormal, fontSizeTabla);
                        contentStream.newLineAtOffset(x + 175 + 75, startYTabla);
                        contentStream.showText(fila[4]);
                        contentStream.endText();

                            // Actualizar Y para la siguiente fila, dependiendo del alto de la descripción
                        startYTabla = yDescripcionFinal - alturaFila;
                    }
                    
                    float startX3 = 460;
                    float startY3 = 171;
                    float Separacion3 = 13;
                    float fontSize3 = 10;
                    
                     // Texto PostTabla
                    contentStream.setFont(fontBold, fontSizeTabla);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(75, startY3);
                    contentStream.showText("(CIENTO CINCUETA MIL OCHOCIENTOS SESENTA Y TRES PESOS 00/100 M.N)");
                    contentStream.endText();
                    
                    contentStream.setFont(fontBold, 10);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(240, 146);
                    contentStream.showText("NO IDENTIFICADO");
                    contentStream.endText();
                    
                    contentStream.setFont(fontBold, 10);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(194, 133);
                    contentStream.showText("PERSONA MORAL");
                    contentStream.endText();

                    // TOTAL
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(fontNormal, fontSize3);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX3, startY3);
                    contentStream.showText("124,000.77");
                    contentStream.endText();
                    startY3 -= Separacion3;

                    contentStream.setFont(fontNormal, fontSize3);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX3, startY3);
                    contentStream.showText("0.00");
                    contentStream.endText();
                    startY3 -= Separacion3;

                    contentStream.setFont(fontNormal, fontSize3);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX3, startY3);
                    contentStream.showText("26,893.23");
                    contentStream.endText();
                    startY3 -= Separacion3;
                    
                    contentStream.setFont(fontNormal, fontSize3);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX3, startY3);
                    contentStream.showText("150,893.00");
                    contentStream.endText();
                    
                    
                    
                }

                String uniqueFileName = outputFileName + System.currentTimeMillis() + ".pdf";
                Path outputFilePath = outputFolderPath.resolve(uniqueFileName);
                document.save(outputFilePath.toString());
                System.out.println("PDF modificado y guardado en el escritorio: " + outputFilePath.toString());

            } catch (IOException e) {
                System.err.println("Error al procesar el PDF: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("Error al crear la carpeta en el escritorio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static byte[] leerBytesDesdeInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
    
    public static void drawWrappedText(PDPageContentStream contentStream, PDFont font, float fontSize,
                                   float x, float y, float width, float leading, String text) throws IOException {
    List<String> lines = new ArrayList<>();
    String[] words = text.split(" ");
    StringBuilder line = new StringBuilder();

    for (String word : words) {
        String testLine = line + word + " ";
        float size = font.getStringWidth(testLine) / 1000 * fontSize;
        if (size > width) {
            lines.add(line.toString());
            line = new StringBuilder(word + " ");
        } else {
            line.append(word).append(" ");
        }
    }
    lines.add(line.toString());

    for (String ln : lines) {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(ln.trim());
        contentStream.endText();
        y -= leading;
    }
}
  
}