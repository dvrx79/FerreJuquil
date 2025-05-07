/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UIEmergentes;

/**
 *
 * @author salin
 */
public class NumeroALetras {

    private static final String[] UNIDADES = {
        "", "uno", "dos", "tres", "cuatro", "cinco",
        "seis", "siete", "ocho", "nueve", "diez",
        "once", "doce", "trece", "catorce", "quince",
        "dieciséis", "diecisiete", "dieciocho", "diecinueve", "veinte"
    };

    private static final String[] DECENAS = {
        "", "", "veinte", "treinta", "cuarenta",
        "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };

    private static final String[] CENTENAS = {
        "", "ciento", "doscientos", "trescientos", "cuatrocientos",
        "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    public static String convertir(double numero) {
        int parteEntera = (int) numero;
        int centavos = (int) Math.round((numero - parteEntera) * 100);

        String texto = convertirNumero(parteEntera).toUpperCase();

        return texto + " PESOS " + String.format("%02d", centavos) + "/100 M.N.";
    }

    private static String convertirNumero(int numero) {
        if (numero == 0) {
            return "cero";
        }
        if (numero == 100) {
            return "cien";
        }

        StringBuilder resultado = new StringBuilder();

        if (numero >= 1_000_000) {
            int millones = numero / 1_000_000;
            resultado.append(convertirNumero(millones)).append(" millón");
            if (millones > 1) resultado.append("es");
            numero %= 1_000_000;
            if (numero > 0) resultado.append(" ");
        }

        if (numero >= 1000) {
            int miles = numero / 1000;
            if (miles == 1) {
                resultado.append("mil");
            } else {
                resultado.append(convertirNumero(miles)).append(" mil");
            }
            numero %= 1000;
            if (numero > 0) resultado.append(" ");
        }

        if (numero >= 100) {
            int centenas = numero / 100;
            resultado.append(CENTENAS[centenas]);
            numero %= 100;
            if (numero > 0) resultado.append(" ");
        }

        if (numero > 20) {
            int decenas = numero / 10;
            int unidades = numero % 10;
            resultado.append(DECENAS[decenas]);
            if (unidades > 0) {
                resultado.append(" y ").append(UNIDADES[unidades]);
            }
        } else if (numero > 0) {
            resultado.append(UNIDADES[numero]);
        }

        return resultado.toString();
    }
}

