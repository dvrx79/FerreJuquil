/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.io.File;

/**
 *
 * @author gaelj
 */
public class Backup {
    public static void respaldarBaseDeDatos() {
        try {
            String usuario = "root";
            String password = "1234";
            String nombreBase = "sistema_ventas_mejorado";

            String carpeta = "C:\\respaldo_bd_ferreteria";
            File directorio = new File(carpeta);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Carpeta creada: " + carpeta);
                } else {
                    System.out.println("No se pudo crear la carpeta.");
                    return;
                }
            }

            String fecha = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String rutaBackup = carpeta + "\\backup_" + fecha + ".sql";

            String comando = "\"C:\\Program Files\\MySQL\\MySQL Workbench 8.0\\mysqldump.exe\" -u " + usuario + " -p" + password + " " + nombreBase + " -r \"" + rutaBackup + "\"";

            Process proceso = Runtime.getRuntime().exec(comando);
            int resultado = proceso.waitFor();

            if (resultado == 0) {
                System.out.println("Respaldo creado correctamente en: " + rutaBackup);
            } else {
                System.out.println("Error al crear el respaldo.");
            }

        } catch (Exception e) {
            System.err.println("Error al ejecutar el respaldo:");
            e.printStackTrace();
        }
    }
}
