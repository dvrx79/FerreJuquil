/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MetodoAdicionales;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author yael
 */
public class CreadorBarras {
    public String generarCodigoBarras() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 12; i++) {
        sb.append(random.nextInt(10));
    }
    int checksum = calcularChecksumEAN13(sb.toString());
    sb.append(checksum);
    return sb.toString();
}

public int calcularChecksumEAN13(String codigo) {
    int suma = 0;
    for (int i = 0; i < codigo.length(); i++) {
        int digito = Character.getNumericValue(codigo.charAt(i));
        suma += (i % 2 == 0) ? digito : digito * 3;
    }
    return (10 - (suma % 10)) % 10;
}
public BufferedImage generarImagenCodigoBarras(String texto) throws Exception {
    BitMatrix bitMatrix = new MultiFormatWriter().encode(
        texto,
        BarcodeFormat.EAN_13,
        300, 150
    );
    return MatrixToImageWriter.toBufferedImage(bitMatrix);
}
public void guardarImagenComoPDF(BufferedImage imagen, String nombreArchivo) throws Exception {
    float width = imagen.getWidth();
    float height = imagen.getHeight();

    com.itextpdf.text.Document document = new com.itextpdf.text.Document(
        new com.itextpdf.text.Rectangle(width, height)
    );
    com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(nombreArchivo));
    document.open();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(imagen, "png", baos);
    baos.flush();

    com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(baos.toByteArray());
    img.setAbsolutePosition(0, 0); // Imagen en la esquina inferior izquierda
    document.add(img);

    document.close();
}
}
