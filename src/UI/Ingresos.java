/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import ManejoTablas.ReporteVentas;
import UIEmergentes.EscogerAnio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author salin
 */
public class Ingresos extends javax.swing.JFrame {
    ReporteVentas reporte = new ReporteVentas();
    /**
     * Creates new form vistaLogin
     */
    public Ingresos() {
        initComponents();
       // mostrarGraficaVentasPorTipoProducto(2025);
      setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo6.png")).getImage());
        
    }
    
        public void cargarDatos(int n){
            if(n == 1){
                mostrarGraficaVentasPorSemana();
            }else if(n == 2){
                mostrarGraficaVentasPorMes();
            }else if(n == 3){
                int anioActual = Year.now().getValue();
                mostrarGraficaVentasPorTipoProducto(2025);
            }
        }

        public void mostrarGraficaVentasPorSemana() {
    try {
        ReporteVentas reporte = new ReporteVentas();
        Map<String, Double> ventasPorDia = ReporteVentas.obtenerTotalVentasPorDiaSemana();

        // Traducción ordenada de días en inglés a español
        String[] diasIngles = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Map<String, String> traducciones = Map.of(
            "Monday", "Lunes",
            "Tuesday", "Martes",
            "Wednesday", "Miércoles",
            "Thursday", "Jueves",
            "Friday", "Viernes",
            "Saturday", "Sábado",
            "Sunday", "Domingo"
        );

        // Dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String diaIngles : diasIngles) {
            // Formatear por si vienen inconsistencias de la BD (precaución extra)
            String diaFormateado = diaIngles.substring(0, 1).toUpperCase() + diaIngles.substring(1).toLowerCase().trim();
            double valor = ventasPorDia.getOrDefault(diaFormateado, 0.0);
            String diaEsp = traducciones.get(diaFormateado);

            if (diaEsp != null) {
                dataset.addValue(valor, "Ventas", diaEsp);
            } else {
                System.err.println("Día sin traducción válida: " + diaFormateado);
            }
        }

        // Crear el gráfico
        JFreeChart graficoBarras = ChartFactory.createBarChart(
            "Total de Ventas por Día de la Semana "+obtenerRangoSemanaActual(),
            "Día de la Semana",
            "Total de Ventas - $",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        // Personalización opcional del gráfico
        CategoryPlot plot = graficoBarras.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Crear el panel del gráfico
        ChartPanel chartPanel = new ChartPanel(graficoBarras);
        chartPanel.setPreferredSize(new java.awt.Dimension(panelGrafica.getWidth(), panelGrafica.getHeight()));

        // Limpiar el panel y agregar el nuevo gráfico
        panelGrafica.removeAll();
        panelGrafica.setLayout(new java.awt.BorderLayout());
        panelGrafica.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGrafica.revalidate();
        panelGrafica.repaint();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
        
      
        public void mostrarGraficaVentasPorMes() {
    try {
        ReporteVentas reporte = new ReporteVentas();
        Map<String, Double> ventasPorFecha = ReporteVentas.obtenerTotalVentasPorDiaMes();

        TreeMap<LocalDate, Double> ventasOrdenadas = new TreeMap<>();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Map.Entry<String, Double> entry : ventasPorFecha.entrySet()) {
            try {
                LocalDate fecha = LocalDate.parse(entry.getKey(), formatoFecha);
                ventasOrdenadas.put(fecha, entry.getValue());
            } catch (DateTimeParseException e) {
                System.err.println("Fecha malformada: " + entry.getKey());
            }
        }

        // Dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        YearMonth mesActual = YearMonth.now(); // mayo 2025, por ejemplo
        int diasDelMes = mesActual.lengthOfMonth();

        for (int dia = 1; dia <= diasDelMes; dia++) {
            LocalDate fecha = mesActual.atDay(dia);
            double total = ventasOrdenadas.getOrDefault(fecha, 0.0);

            String etiqueta = String.valueOf(fecha.getDayOfMonth());

            dataset.addValue(total, "Ventas", etiqueta);
        }

        JFreeChart graficoBarras = ChartFactory.createBarChart(
            "Total de Ventas del Mes de " + obtenerMesActualEnTexto(), 
            "Fecha",
            "Total de Ventas - $",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        // Estética
        CategoryPlot plot = graficoBarras.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        ChartPanel chartPanel = new ChartPanel(graficoBarras);
        chartPanel.setPreferredSize(new java.awt.Dimension(panelGrafica.getWidth(), panelGrafica.getHeight()));

        panelGrafica.removeAll();
        panelGrafica.setLayout(new java.awt.BorderLayout());
        panelGrafica.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGrafica.revalidate();
        panelGrafica.repaint();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

        
        public void mostrarGraficaVentasPorAnio(int año) {
    try {
        Map<String, Double> ventasPorMes = ReporteVentas.obtenerTotalVentasPorAnio(año); 
        // formato clave: "01", "02", ..., "12"

        // Asegurar que estén todos los meses del año, incluso si no hay ventas
        Map<Integer, Double> ventasMensuales = new TreeMap<>();
        for (int i = 1; i <= 12; i++) {
            ventasMensuales.put(i, 0.0); // valor por defecto
        }

        for (Map.Entry<String, Double> entry : ventasPorMes.entrySet()) {
            int mes = Integer.parseInt(entry.getKey()); // clave: "01" → int 1
            ventasMensuales.put(mes, entry.getValue());
        }

        // Dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Double> entry : ventasMensuales.entrySet()) {
            int mesNumero = entry.getKey();
            double total = entry.getValue();

            String nombreMes = Month.of(mesNumero)
                    .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1);

            dataset.addValue(total, "Ventas", nombreMes);
        }

        // Crear gráfico
        JFreeChart graficoBarras = ChartFactory.createBarChart(
            "Total de Ventas del Año " + año,
            "Mes",
            "Total de Ventas - $",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        // Estética
        CategoryPlot plot = graficoBarras.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

         NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(new java.text.DecimalFormat("#,##0.00"));
        rangeAxis.setAutoRange(true); // Asegurarse de que el auto-rango esté activado
        
        
        ChartPanel chartPanel = new ChartPanel(graficoBarras);
        chartPanel.setPreferredSize(new java.awt.Dimension(panelGrafica.getWidth(), panelGrafica.getHeight()));

        panelGrafica.removeAll();
        panelGrafica.setLayout(new java.awt.BorderLayout());
        panelGrafica.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGrafica.revalidate();
        panelGrafica.repaint();

    } catch (Exception e) {
        e.printStackTrace();
    }
}


        
    public void mostrarGraficaVentasPorTipoProducto(int año) {
         try {
        Map<Integer, Map<String, Double>> ventasAnualesPorTipo = reporte.obtenerVentasMensualesPorTipoProducto(año);
        

        // Dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int mesNumero = 1; mesNumero <= 12; mesNumero++) {
            String nombreMes = Month.of(mesNumero)
                    .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1);

            if (ventasAnualesPorTipo.containsKey(mesNumero)) {
                Map<String, Double> ventasMes = ventasAnualesPorTipo.get(mesNumero);
                double construccion = ventasMes.getOrDefault("Construcción", 0.0);
                double ferreteria = ventasMes.getOrDefault("Ferreteria", 0.0);
                dataset.addValue(construccion, "Construcción", nombreMes);
                dataset.addValue(ferreteria, "Ferreteria", nombreMes);
            } else {
                dataset.addValue(0.0, "Construcción", nombreMes);
                dataset.addValue(0.0, "Ferreteria", nombreMes);
            }
        }

        // Crear gráfico
        JFreeChart graficoBarras = ChartFactory.createBarChart(
                "Ventas Anuales por Tipo de Producto (" + año + ")",
                "Mes",
                "Total de Ventas - $",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Estética y Renderer para agrupar barras
        CategoryPlot plot = graficoBarras.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Formatear el eje Y
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(new java.text.DecimalFormat("#,##0.00"));
        rangeAxis.setAutoRange(true); // Asegurarse de que el auto-rango esté activado

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 128, 255)); // Azul para Construcción
        renderer.setSeriesPaint(1, new Color(255, 0, 0));   // Rojo para Ferretería

        ChartPanel chartPanel = new ChartPanel(graficoBarras);
        chartPanel.setPreferredSize(new java.awt.Dimension(panelGrafica.getWidth(), panelGrafica.getHeight()));

        panelGrafica.removeAll();
        panelGrafica.setLayout(new java.awt.BorderLayout());
        panelGrafica.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelGrafica.revalidate();
        panelGrafica.repaint();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener datos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    } 
    }


    

// Función auxiliar para capitalizar mes
private String capitalizar(String texto) {
    if (texto == null || texto.isEmpty()) return texto;
    return texto.substring(0, 1).toUpperCase() + texto.substring(1);
}


        
        
        public String obtenerMesActualEnTexto() {
    LocalDate ahora = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES"));
    return ahora.format(formatter).substring(0, 1).toUpperCase() + ahora.format(formatter).substring(1);
}

       
        public static String obtenerRangoSemanaActual() {
    LocalDate hoy = LocalDate.now();

    // Día de la semana (1 = lunes, 7 = domingo)
    DayOfWeek diaActual = hoy.getDayOfWeek();

    // Calcular lunes y domingo
    LocalDate lunes = hoy.minusDays(diaActual.getValue() - 1);
    LocalDate domingo = lunes.plusDays(6);

    // Formatear fechas
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd MMM", new Locale("es", "ES"));
    String inicio = lunes.format(formato);
    String fin = domingo.format(formato);

    return inicio + " - " + fin;
}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnIni = new javax.swing.JButton();
        btnPed = new javax.swing.JButton();
        btnVen = new javax.swing.JButton();
        btnInv = new javax.swing.JButton();
        btnProv = new javax.swing.JButton();
        btnIng = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelRound2 = new UI.PanelRound();
        btn1 = new javax.swing.JPanel();
        btnI1 = new UI.PanelRound();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelRound6 = new UI.PanelRound();
        btn4 = new javax.swing.JPanel();
        btnI4 = new UI.PanelRound();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelRound9 = new UI.PanelRound();
        btn6 = new javax.swing.JPanel();
        btnI6 = new UI.PanelRound();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panelRound11 = new UI.PanelRound();
        btn8 = new javax.swing.JPanel();
        btnI8 = new UI.PanelRound();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelRound13 = new UI.PanelRound();
        panelGrafica = new UI.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ventas");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1364, 780));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Logo_pequeño2.png"))); // NOI18N

        btnIni.setBackground(new java.awt.Color(251, 218, 160));
        btnIni.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnIni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N
        btnIni.setText("Inicio");
        btnIni.setBorder(null);
        btnIni.setContentAreaFilled(false);
        btnIni.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIni.setOpaque(true);
        btnIni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIniMouseExited(evt);
            }
        });
        btnIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniActionPerformed(evt);
            }
        });

        btnPed.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnPed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pedidos.png"))); // NOI18N
        btnPed.setText("Pedidos");
        btnPed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnPed.setContentAreaFilled(false);
        btnPed.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPedMouseExited(evt);
            }
        });
        btnPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedActionPerformed(evt);
            }
        });

        btnVen.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnVen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dinero 24-3.png"))); // NOI18N
        btnVen.setText("Ventas");
        btnVen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnVen.setContentAreaFilled(false);
        btnVen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVenMouseExited(evt);
            }
        });
        btnVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenActionPerformed(evt);
            }
        });

        btnInv.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/productos.png"))); // NOI18N
        btnInv.setText("Inventario");
        btnInv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnInv.setContentAreaFilled(false);
        btnInv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInvMouseExited(evt);
            }
        });
        btnInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvActionPerformed(evt);
            }
        });

        btnProv.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/prov.png"))); // NOI18N
        btnProv.setText("Proveedor");
        btnProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnProv.setContentAreaFilled(false);
        btnProv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProvMouseExited(evt);
            }
        });
        btnProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProvActionPerformed(evt);
            }
        });

        btnIng.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnIng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingresos.png"))); // NOI18N
        btnIng.setText("Ingresos");
        btnIng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnIng.setContentAreaFilled(false);
        btnIng.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIngMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIngMouseExited(evt);
            }
        });
        btnIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnIng, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(btnIni, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPed, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnInv, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProv, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIng, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 231, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setText("Ingresos");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(250, 176, 47));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FERRETERÍA \"JUQUILITA\"");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 575, 27));

        btnSalir.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalirMouseExited(evt);
            }
        });
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(1198, 7));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        panelRound2.setBackground(new java.awt.Color(250, 176, 47));
        panelRound2.setRoundBottomLeft(50);
        panelRound2.setRoundBottomRight(50);
        panelRound2.setRoundTopLeft(50);
        panelRound2.setRoundTopRight(50);

        btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn1.setOpaque(false);
        btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn1MouseExited(evt);
            }
        });
        btn1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnI1.setBackground(new java.awt.Color(255, 255, 255));
        btnI1.setRoundBottomLeft(50);
        btnI1.setRoundBottomRight(50);
        btnI1.setRoundTopLeft(50);
        btnI1.setRoundTopRight(50);
        btnI1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnI1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnI1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnI1MouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tipo de Producto Vendido Mensualmente");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cemento.png"))); // NOI18N

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/herramienta.png"))); // NOI18N

        javax.swing.GroupLayout btnI1Layout = new javax.swing.GroupLayout(btnI1);
        btnI1.setLayout(btnI1Layout);
        btnI1Layout.setHorizontalGroup(
            btnI1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnI1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel11)
                .addGap(26, 26, 26)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnI1Layout.setVerticalGroup(
            btnI1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnI1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(12, 12, 12))
        );

        btn1.add(btnI1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 340, -1));

        panelRound6.setBackground(new java.awt.Color(153, 153, 153));
        panelRound6.setRoundBottomLeft(50);
        panelRound6.setRoundBottomRight(50);
        panelRound6.setRoundTopLeft(50);
        panelRound6.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        btn1.add(panelRound6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 340, 110));

        btn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn4.setOpaque(false);
        btn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn4MouseExited(evt);
            }
        });
        btn4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnI4.setBackground(new java.awt.Color(255, 255, 255));
        btnI4.setRoundBottomLeft(50);
        btnI4.setRoundBottomRight(50);
        btnI4.setRoundTopLeft(50);
        btnI4.setRoundTopRight(50);
        btnI4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnI4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnI4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnI4MouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("De la Semana");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/semanal.png"))); // NOI18N

        javax.swing.GroupLayout btnI4Layout = new javax.swing.GroupLayout(btnI4);
        btnI4.setLayout(btnI4Layout);
        btnI4Layout.setHorizontalGroup(
            btnI4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnI4Layout.createSequentialGroup()
                .addGroup(btnI4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnI4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnI4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnI4Layout.setVerticalGroup(
            btnI4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(12, 12, 12))
        );

        btn4.add(btnI4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        panelRound9.setBackground(new java.awt.Color(153, 153, 153));
        panelRound9.setRoundBottomLeft(50);
        panelRound9.setRoundBottomRight(50);
        panelRound9.setRoundTopLeft(50);
        panelRound9.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound9Layout = new javax.swing.GroupLayout(panelRound9);
        panelRound9.setLayout(panelRound9Layout);
        panelRound9Layout.setHorizontalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panelRound9Layout.setVerticalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        btn4.add(panelRound9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, 110));

        btn6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn6.setOpaque(false);
        btn6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn6MouseExited(evt);
            }
        });
        btn6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnI6.setBackground(new java.awt.Color(255, 255, 255));
        btnI6.setRoundBottomLeft(50);
        btnI6.setRoundBottomRight(50);
        btnI6.setRoundTopLeft(50);
        btnI6.setRoundTopRight(50);
        btnI6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnI6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnI6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnI6MouseExited(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Anual");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anual.png"))); // NOI18N

        javax.swing.GroupLayout btnI6Layout = new javax.swing.GroupLayout(btnI6);
        btnI6.setLayout(btnI6Layout);
        btnI6Layout.setHorizontalGroup(
            btnI6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnI6Layout.createSequentialGroup()
                .addGroup(btnI6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnI6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnI6Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel18)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnI6Layout.setVerticalGroup(
            btnI6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(12, 12, 12))
        );

        btn6.add(btnI6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        panelRound11.setBackground(new java.awt.Color(153, 153, 153));
        panelRound11.setRoundBottomLeft(50);
        panelRound11.setRoundBottomRight(50);
        panelRound11.setRoundTopLeft(50);
        panelRound11.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound11Layout = new javax.swing.GroupLayout(panelRound11);
        panelRound11.setLayout(panelRound11Layout);
        panelRound11Layout.setHorizontalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panelRound11Layout.setVerticalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        btn6.add(panelRound11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, 110));

        btn8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn8.setOpaque(false);
        btn8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn8MouseExited(evt);
            }
        });
        btn8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnI8.setBackground(new java.awt.Color(255, 255, 255));
        btnI8.setRoundBottomLeft(50);
        btnI8.setRoundBottomRight(50);
        btnI8.setRoundTopLeft(50);
        btnI8.setRoundTopRight(50);
        btnI8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnI8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnI8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnI8MouseExited(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Del Mes");

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mensual.png"))); // NOI18N

        javax.swing.GroupLayout btnI8Layout = new javax.swing.GroupLayout(btnI8);
        btnI8.setLayout(btnI8Layout);
        btnI8Layout.setHorizontalGroup(
            btnI8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnI8Layout.createSequentialGroup()
                .addGroup(btnI8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnI8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(btnI8Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel22)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnI8Layout.setVerticalGroup(
            btnI8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addGap(12, 12, 12))
        );

        btn8.add(btnI8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        panelRound13.setBackground(new java.awt.Color(153, 153, 153));
        panelRound13.setRoundBottomLeft(50);
        panelRound13.setRoundBottomRight(50);
        panelRound13.setRoundTopLeft(50);
        panelRound13.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound13Layout = new javax.swing.GroupLayout(panelRound13);
        panelRound13.setLayout(panelRound13Layout);
        panelRound13Layout.setHorizontalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        panelRound13Layout.setVerticalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        btn8.add(panelRound13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, 110));

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelGrafica.setBackground(new java.awt.Color(255, 255, 255));
        panelGrafica.setRoundBottomLeft(50);
        panelGrafica.setRoundBottomRight(50);
        panelGrafica.setRoundTopLeft(50);
        panelGrafica.setRoundTopRight(50);

        javax.swing.GroupLayout panelGraficaLayout = new javax.swing.GroupLayout(panelGrafica);
        panelGrafica.setLayout(panelGraficaLayout);
        panelGraficaLayout.setHorizontalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1128, Short.MAX_VALUE)
        );
        panelGraficaLayout.setVerticalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 541, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(panelGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(12, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 449, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 790));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniActionPerformed
        inicio i = new inicio();
        i.cargarDatos(1);
        i.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnIniActionPerformed

    private void btnPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedActionPerformed
       Pedidos p = new Pedidos();
       p.cargarDatos(1);
       p.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnPedActionPerformed

    private void btnVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenActionPerformed
        Ventas v = new Ventas();
        v.cargarDatos(1);
        v.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVenActionPerformed

    private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
        Inventario i = new Inventario();
        i.cargarDatos(1);
        i.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInvActionPerformed

    private void btnProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProvActionPerformed
        Proveedores p = new Proveedores();
        p.cargarDatos(1);
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnProvActionPerformed

    private void btnIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIngActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnIniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniMouseEntered
        btnIni.setBackground(new Color(249,193,107));
    }//GEN-LAST:event_btnIniMouseEntered

    private void btnIniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniMouseExited
        btnIni.setBackground(new Color(251,218,160));
    }//GEN-LAST:event_btnIniMouseExited

    private void btnPedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPedMouseEntered
        btnPed.setBackground(new Color(249,193,107));
        btnPed.setOpaque(true);
    }//GEN-LAST:event_btnPedMouseEntered

    private void btnPedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPedMouseExited
     btnPed.setOpaque(false);
    }//GEN-LAST:event_btnPedMouseExited

    private void btnVenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenMouseEntered
        btnVen.setBackground(new Color(249,193,107));
        btnVen.setOpaque(true);
    }//GEN-LAST:event_btnVenMouseEntered

    private void btnVenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenMouseExited
        btnVen.setOpaque(false);
    }//GEN-LAST:event_btnVenMouseExited

    private void btnInvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvMouseEntered
        btnInv.setBackground(new Color(249,193,107));
        btnInv.setOpaque(true);
    }//GEN-LAST:event_btnInvMouseEntered

    private void btnInvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvMouseExited
        btnInv.setOpaque(false);
    }//GEN-LAST:event_btnInvMouseExited

    private void btnProvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProvMouseEntered
        btnProv.setBackground(new Color(249,193,107));
        btnProv.setOpaque(true);
    }//GEN-LAST:event_btnProvMouseEntered

    private void btnProvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProvMouseExited
        btnProv.setOpaque(false);
    }//GEN-LAST:event_btnProvMouseExited

    private void btnIngMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngMouseEntered
        btnIng.setBackground(new Color(249,193,107));
        btnIng.setOpaque(true);
    }//GEN-LAST:event_btnIngMouseEntered

    private void btnIngMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngMouseExited
        btnIng.setOpaque(false);
    }//GEN-LAST:event_btnIngMouseExited

    private void btnSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseEntered
        btnSalir.setBackground(new Color(249,103,94));
        btnSalir.setOpaque(true);
    }//GEN-LAST:event_btnSalirMouseEntered

    private void btnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseExited
        btnSalir.setOpaque(false);
    }//GEN-LAST:event_btnSalirMouseExited

    private void btn8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn8MouseExited

    private void btn8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn8MouseEntered

    private void btnI8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI8MouseExited
        btnI8.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnI8MouseExited

    private void btnI8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI8MouseEntered
        btnI8.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnI8MouseEntered

    private void btn6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn6MouseExited

    private void btn6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn6MouseEntered

    private void btnI6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI6MouseExited
        btnI6.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnI6MouseExited

    private void btnI6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI6MouseEntered
        btnI6.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnI6MouseEntered

    private void btn4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn4MouseExited

    private void btn4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn4MouseEntered

    private void btnI4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI4MouseExited
        btnI4.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnI4MouseExited

    private void btnI4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI4MouseEntered
        btnI4.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnI4MouseEntered

    private void btn1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseExited

    }//GEN-LAST:event_btn1MouseExited

    private void btn1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseEntered

    }//GEN-LAST:event_btn1MouseEntered

    private void btnI1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI1MouseExited
        btnI1.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnI1MouseExited

    private void btnI1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI1MouseEntered
        btnI1.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnI1MouseEntered

    private void btnI4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI4MouseClicked
        cargarDatos(1);
    }//GEN-LAST:event_btnI4MouseClicked

    private void btnI8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI8MouseClicked
        cargarDatos(2);
    }//GEN-LAST:event_btnI8MouseClicked

    private void btnI6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI6MouseClicked
        EscogerAnio ea = new EscogerAnio(null, true);
        ea.setVisible(true);
        int eleccion = ea.getAnioEscogido();
        mostrarGraficaVentasPorAnio(eleccion);
        
    }//GEN-LAST:event_btnI6MouseClicked

    private void btnI1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI1MouseClicked
        EscogerAnio ea = new EscogerAnio(null, true);
        ea.setVisible(true);
        int eleccion = ea.getAnioEscogido();
        mostrarGraficaVentasPorTipoProducto(eleccion);
    }//GEN-LAST:event_btnI1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ingresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ingresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ingresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ingresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ingresos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn1;
    private javax.swing.JPanel btn4;
    private javax.swing.JPanel btn6;
    private javax.swing.JPanel btn8;
    private UI.PanelRound btnI1;
    private UI.PanelRound btnI4;
    private UI.PanelRound btnI6;
    private UI.PanelRound btnI8;
    private javax.swing.JButton btnIng;
    private javax.swing.JButton btnIni;
    private javax.swing.JButton btnInv;
    private javax.swing.JButton btnPed;
    private javax.swing.JButton btnProv;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVen;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblLogo;
    private UI.PanelRound panelGrafica;
    private UI.PanelRound panelRound11;
    private UI.PanelRound panelRound13;
    private UI.PanelRound panelRound2;
    private UI.PanelRound panelRound6;
    private UI.PanelRound panelRound9;
    // End of variables declaration//GEN-END:variables
}
