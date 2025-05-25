/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.util.*;

/**
 *
 * @author gaelj
 */
public class Programar_respaldo {

    public static void iniciarRespaldoDiario() {
        Timer timer = new Timer();

        // Hace backup cada 24 horas
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Ejecutando respaldo diario");
                Backup.respaldarBaseDeDatos(); //Método de respaldo
            }
        }, 0, /*24 * 60 * 60 * 1000*/10 * 1000); // cada 24 horas //10 * 1000 cada 10 segundos para probar
    }
}
