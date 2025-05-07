/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import Modelo.MostrarProveedor;
import com.formdev.flatlaf.ui.FlatScrollBarUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Modelo.*;
import ManejoTablas.*;
import UIEmergentes.ActualizarPreciosInv;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;
import java.sql.*;

/**
 *
 * @author salin
 */
public class Ventas extends javax.swing.JFrame {
    private ManejoVentas manejoventas = new ManejoVentas();
    private ManejoProductos manejoproductos = new ManejoProductos();
    private MostrarVentas ventas;
    /**
     * Creates new form vistaLogin
     */
    public Ventas() {
        initComponents();
        ventas = null;
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo6.png")).getImage());
        TablaVentas.setRowHeight(30);
        panelTabla.getViewport().setBackground(Color.WHITE);
        JTableHeader header = TablaVentas.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.BOLD, 16));
        header.setBackground(new Color(0,0,0));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(),40));
        header.setOpaque(true);

        DefaultTableCellRenderer hr = new DefaultTableCellRenderer();
        hr.setBackground(new Color(0,0,0));
        hr.setForeground(Color.WHITE);
        hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TablaVentas.getTableHeader().setDefaultRenderer(hr);
        
         DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(new Color(255, 255, 255)); // Color uniforme para todas las celdas
                cell.setForeground(Color.BLACK); // Texto en negro
                return cell;
            }
        };

        // Aplicar el renderer a todas las columnas
        for (int i = 0; i < TablaVentas.getColumnCount(); i++) {
            TablaVentas.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        TablaVentas.getColumnModel().getColumn(6).setMinWidth(0);
        TablaVentas.getColumnModel().getColumn(6).setMaxWidth(0);
        TablaVentas.getColumnModel().getColumn(6).setWidth(0);
        
        quitabordes();
    }

    
    
     public void quitabordes(){
        JScrollBar vs = panelTabla.getVerticalScrollBar();
        vs.setUI(new FlatScrollBarUI());
        vs.setPreferredSize(new Dimension(12, 0)); // Ancho de la barra
        vs.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(204, 204, 204));// Color para el pulgar, ocre -> 250 191 91
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vs.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vs.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(204, 204, 204)); // Color del pulgar (gris)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
            
            
            JScrollBar vh = panelTabla.getHorizontalScrollBar();
        vh.setUI(new FlatScrollBarUI());
        vh.setPreferredSize(new Dimension(0, 12)); // Ancho de la barra
        vh.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(250, 191, 91)); // Color azul para el pulgar
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vh.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vh.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(250, 191, 91)); // Color del pulgar (ocre)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
            
            
            JScrollBar vlist = panelLista.getVerticalScrollBar();
        vlist.setUI(new FlatScrollBarUI());
        vlist.setPreferredSize(new Dimension(12, 0)); // Ancho de la barra
        vlist.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(204, 204, 204));// Color para el pulgar, ocre -> 250 191 91
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vlist.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vlist.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(204, 204, 204)); // Color del pulgar (gris)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
            
            
            JScrollBar hlist = panelLista.getHorizontalScrollBar();
        hlist.setUI(new FlatScrollBarUI());
        hlist.setPreferredSize(new Dimension(0, 12)); // Ancho de la barra
        hlist.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(250, 191, 91)); // Color azul para el pulgar
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            hlist.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            hlist.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(250, 191, 91)); // Color del pulgar (ocre)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
    
     }
    
    
    public void cargarDatos(int btn){
         List<MostrarVentas> listaVentas;
        if(btn == 1){
            try{
                listaVentas = manejoventas.obtenerVentas();
                DefaultTableModel modelo = (DefaultTableModel) TablaVentas.getModel();
                modelo.setRowCount(0);
                for(MostrarVentas mv: listaVentas){
                    Object[] fila = {
                        mv.getFecha(),
                        "$ "+mv.getMonto(),
                        mv.getFormaPago(),
                        mv.getEstado(),
                        mv.getCliente(),
                        mv.getNumeroCliente(),
                        mv.getIdVenta()
                    };
                    modelo.addRow(fila);
                }
                ordenarTabla(modelo);
                configurarColoresTabla();
            }catch(SQLException e){}
        }else if(btn == 2){
            try{
                listaVentas = manejoventas.obtenerVentasPorTipoProducto("Construcción");
                DefaultTableModel modelo = (DefaultTableModel) TablaVentas.getModel();
                modelo.setRowCount(0);
                for(MostrarVentas mv: listaVentas){
                    Object[] fila = {
                        mv.getFecha(),
                        "$ "+mv.getMonto(),
                        mv.getFormaPago(),
                        mv.getEstado(),
                        mv.getCliente(),
                        mv.getNumeroCliente(),
                        mv.getIdVenta()
                    };
                    modelo.addRow(fila);
                }
                ordenarTabla(modelo);
                configurarColoresTabla();
            }catch(SQLException e){}
        }else if(btn == 3){
            try{
                listaVentas = manejoventas.obtenerVentasPorTipoProducto("Ferreteria");
                DefaultTableModel modelo = (DefaultTableModel) TablaVentas.getModel();
                modelo.setRowCount(0);
                for(MostrarVentas mv: listaVentas){
                    Object[] fila = {
                        mv.getFecha(),
                        "$ "+mv.getMonto(),
                        mv.getFormaPago(),
                        mv.getEstado(),
                        mv.getCliente(),
                        mv.getNumeroCliente(),
                        mv.getIdVenta()
                    };
                    modelo.addRow(fila);
                }
                ordenarTabla(modelo);
                configurarColoresTabla();
            }catch(SQLException e){}
        }else if(btn == 4){
            try{
                listaVentas = manejoventas.obtenerVentasPorMetodoPago("Efectivo");
                DefaultTableModel modelo = (DefaultTableModel) TablaVentas.getModel();
                modelo.setRowCount(0);
                for(MostrarVentas mv: listaVentas){
                    Object[] fila = {
                        mv.getFecha(),
                        "$ "+mv.getMonto(),
                        mv.getFormaPago(),
                        mv.getEstado(),
                        mv.getCliente(),
                        mv.getNumeroCliente(),
                        mv.getIdVenta()
                    };
                    modelo.addRow(fila);
                }
                ordenarTabla(modelo);
                configurarColoresTabla();
            }catch(SQLException e){}
        }else if(btn == 5){
            try{
                listaVentas = manejoventas.obtenerVentasPorMetodoPagoBancario("Efectivo");
                DefaultTableModel modelo = (DefaultTableModel) TablaVentas.getModel();
                modelo.setRowCount(0);
                for(MostrarVentas mv: listaVentas){
                    Object[] fila = {
                        mv.getFecha(),
                        "$ "+mv.getMonto(),
                        mv.getFormaPago(),
                        mv.getEstado(),
                        mv.getCliente(),
                        mv.getNumeroCliente(),
                        mv.getIdVenta()
                    };
                    modelo.addRow(fila);
                }
                ordenarTabla(modelo);
                configurarColoresTabla();
            }catch(SQLException e){}
        }
        
        
    }
    
    public void buscarVentaPorFecha(Date fE){
            try{
                List<MostrarVentas> listaVentas = manejoventas.obtenerVentasPorFecha(fE);
                DefaultTableModel modelo = (DefaultTableModel) TablaVentas.getModel();
                modelo.setRowCount(0);
                for(MostrarVentas mv: listaVentas){
                    Object[] fila = {
                        mv.getFecha(),
                        "$ "+mv.getMonto(),
//                        mv.getTipo(),
                        mv.getFormaPago(),
                        mv.getEstado(),
                        mv.getCliente(),
                        mv.getNumeroCliente(),
                        mv.getIdVenta()
                    };
                    modelo.addRow(fila);
                }
                ordenarTabla(modelo);
                configurarColoresTabla();
            }catch(SQLException e){}
    }
    
    private void ordenarTabla(DefaultTableModel modelo){
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);

        sorter.setComparator(3, new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = o1.toString();
            String s2 = o2.toString();
            
            if (s1.equals("pendiente") && !s2.equals("pendiente")) {
                return -1;
            } else if (!s1.equals("pendiente") && s2.equals("pendiente")) {
                return 1;
            } else {
                return s1.compareTo(s2); // orden alfabético normal si son iguales o distintos de "Pendiente"
            }
           }
        });

        TablaVentas.setRowSorter(sorter);
        
        List<RowSorter.SortKey> orden = new ArrayList<>();
        orden.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sorter.setSortKeys(orden);
        
      sorter.sort();
    }

    private void configurarColoresTabla() {
    TablaVentas.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setHorizontalAlignment(SwingConstants.CENTER); // Centrar contenido

            String estado = value != null ? value.toString() : "";

            if ("completado".equalsIgnoreCase(estado)) {
                cell.setForeground(new Color(0, 153, 0)); // Verde
            } else if ("pendiente".equalsIgnoreCase(estado)) {
                cell.setForeground(new Color(153, 0, 0)); // Rojo
            } else {
                cell.setForeground(Color.BLACK); // Otro
            }

            cell.setBackground(Color.WHITE); // Fondo blanco para todos
            return cell;
        }
    });
    
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
        jLabel5 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelRound2 = new UI.PanelRound();
        panelRound1 = new UI.PanelRound();
        jSeparator1 = new javax.swing.JSeparator();
        obtFecha = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        panelRound3 = new UI.PanelRound();
        btnBuscarFecha = new javax.swing.JLabel();
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
        panelTabla = new javax.swing.JScrollPane();
        TablaVentas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        panelLista = new javax.swing.JScrollPane();
        lstProductos = new javax.swing.JList<>();
        pnlPagoCpto = new UI.PanelRound();
        btnPagoCompletado = new javax.swing.JButton();

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
        jLabel3.setText("Ventas");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, -1, -1));

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(250, 176, 47));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FERRETERÍA \"JUQUILITA\"");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 575, 27));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jLabel5.setText("Recargar");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 150, 50));

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

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setRoundBottomLeft(70);
        panelRound1.setRoundBottomRight(70);
        panelRound1.setRoundTopLeft(70);
        panelRound1.setRoundTopRight(70);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        obtFecha.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setText("Selecciona una fecha :");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(obtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(obtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(70);
        panelRound3.setRoundBottomRight(70);
        panelRound3.setRoundTopLeft(70);
        panelRound3.setRoundTopRight(70);

        btnBuscarFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnBuscarFecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        btnBuscarFecha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarFechaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBuscarFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBuscarFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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
        jLabel17.setText("Transacción");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/transaccion.png"))); // NOI18N

        javax.swing.GroupLayout btnI6Layout = new javax.swing.GroupLayout(btnI6);
        btnI6.setLayout(btnI6Layout);
        btnI6Layout.setHorizontalGroup(
            btnI6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnI6Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(btnI6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(106, 106, 106))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI6Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
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

        btn6.add(btnI6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 280, -1));

        panelRound11.setBackground(new java.awt.Color(153, 153, 153));
        panelRound11.setRoundBottomLeft(50);
        panelRound11.setRoundBottomRight(50);
        panelRound11.setRoundTopLeft(50);
        panelRound11.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound11Layout = new javax.swing.GroupLayout(panelRound11);
        panelRound11.setLayout(panelRound11Layout);
        panelRound11Layout.setHorizontalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelRound11Layout.setVerticalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        btn6.add(panelRound11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 290, 110));

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
        jLabel21.setText("Efectivo");

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/efectivo.png"))); // NOI18N

        javax.swing.GroupLayout btnI8Layout = new javax.swing.GroupLayout(btnI8);
        btnI8.setLayout(btnI8Layout);
        btnI8Layout.setHorizontalGroup(
            btnI8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnI8Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addGroup(btnI8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI8Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(117, 117, 117))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnI8Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
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

        btn8.add(btnI8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 300, -1));

        panelRound13.setBackground(new java.awt.Color(153, 153, 153));
        panelRound13.setRoundBottomLeft(50);
        panelRound13.setRoundBottomRight(50);
        panelRound13.setRoundTopLeft(50);
        panelRound13.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound13Layout = new javax.swing.GroupLayout(panelRound13);
        panelRound13.setLayout(panelRound13Layout);
        panelRound13Layout.setHorizontalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelRound13Layout.setVerticalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        btn8.add(panelRound13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 300, 110));

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelRound2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        TablaVentas.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "MontoTotal", "Forma de Pago", "Estado de pago", "Cliente", "Numero de Cliente", "Title 7"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaVentas.setGridColor(new java.awt.Color(0, 0, 0));
        TablaVentas.setRowSelectionAllowed(false);
        TablaVentas.setSelectionBackground(new java.awt.Color(255, 255, 255));
        TablaVentas.setShowGrid(false);
        TablaVentas.setShowHorizontalLines(true);
        TablaVentas.setShowVerticalLines(true);
        TablaVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaVentasMouseClicked(evt);
            }
        });
        panelTabla.setViewportView(TablaVentas);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Productos vendidos:");

        lstProductos.setBorder(null);
        lstProductos.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lstProductos.setForeground(new java.awt.Color(153, 153, 153));
        lstProductos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Selecciona una venta" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        panelLista.setViewportView(lstProductos);

        pnlPagoCpto.setBackground(new java.awt.Color(250, 176, 47));
        pnlPagoCpto.setRoundBottomLeft(50);
        pnlPagoCpto.setRoundBottomRight(50);
        pnlPagoCpto.setRoundTopLeft(50);
        pnlPagoCpto.setRoundTopRight(50);
        pnlPagoCpto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlPagoCptoMouseEntered(evt);
            }
        });

        btnPagoCompletado.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnPagoCompletado.setText("Pago Completado");
        btnPagoCompletado.setBorder(null);
        btnPagoCompletado.setContentAreaFilled(false);
        btnPagoCompletado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPagoCompletadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPagoCompletadoMouseExited(evt);
            }
        });
        btnPagoCompletado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagoCompletadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPagoCptoLayout = new javax.swing.GroupLayout(pnlPagoCpto);
        pnlPagoCpto.setLayout(pnlPagoCptoLayout);
        pnlPagoCptoLayout.setHorizontalGroup(
            pnlPagoCptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPagoCptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPagoCompletado, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPagoCptoLayout.setVerticalGroup(
            pnlPagoCptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagoCptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPagoCompletado, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 908, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelLista, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(pnlPagoCpto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(622, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelLista, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(pnlPagoCpto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGap(0, 713, Short.MAX_VALUE)
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
                        .addGap(0, 133, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1207, Short.MAX_VALUE)
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
        Ingresos i = new Ingresos();
        i.cargarDatos(3);
        i.setVisible(true);
        this.dispose();
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

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        cargarDatos(1);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void TablaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaVentasMouseClicked
          int filaSeleccionada = TablaVentas.getSelectedRow();

if (filaSeleccionada != -1) { 
    Object idVenta = TablaVentas.getValueAt(filaSeleccionada, 6);

    int id_venta = Integer.parseInt(idVenta.toString());
    
    List<MostrarProducto> listaProductos;
    try{
        listaProductos = manejoproductos.ObtenerProductosPorVentaExclusivo(id_venta);
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        
         for(MostrarProducto mp: listaProductos){
                    modeloLista.addElement(mp.getNombre()+" - "+mp.getCantidadVendida()+" "+mp.getUnidadMedida()+" - $"+mp.getCostoVenta());
                }
         lstProductos.setForeground(Color.BLACK);
         lstProductos.setModel(modeloLista);
         
    }catch(SQLException e){}
}
 
    }//GEN-LAST:event_TablaVentasMouseClicked

    private void btnI8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI8MouseClicked
        cargarDatos(4);
    }//GEN-LAST:event_btnI8MouseClicked

    private void btnI6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnI6MouseClicked
        cargarDatos(5);
    }//GEN-LAST:event_btnI6MouseClicked

    private void btnPagoCompletadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoCompletadoActionPerformed
            int filaSeleccionada = TablaVentas.getSelectedRow();

if (filaSeleccionada != -1) { 
    Object estado = TablaVentas.getValueAt(filaSeleccionada, 3);
    Object idV = TablaVentas.getValueAt(filaSeleccionada, 6);


    String estPago = estado.toString();
    int idVenta = Integer.parseInt(idV.toString());
    
    if(estPago.equals("completado")){
        JOptionPane.showMessageDialog(null,"La venta Ya ha sido pagada");
    }else{
        try {
            manejoventas.actualizarEstadoPagoPorIdVenta(idVenta, "completado");
            JOptionPane.showMessageDialog(null,"La Venta ha sido Pagada");
            cargarDatos(1);
        } catch (SQLException ex) { }
    }
} else {
    JOptionPane.showMessageDialog(null,"No se ha seleccionado ninguna fila.");
}
      
    }//GEN-LAST:event_btnPagoCompletadoActionPerformed

    private void btnPagoCompletadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoCompletadoMouseEntered
        pnlPagoCpto.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnPagoCompletadoMouseEntered

    private void btnPagoCompletadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoCompletadoMouseExited
        pnlPagoCpto.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnPagoCompletadoMouseExited

    private void pnlPagoCptoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPagoCptoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlPagoCptoMouseEntered

    private void btnBuscarFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarFechaMouseClicked
        java.util.Date fechaUtil = obtFecha.getDate();
        java.sql.Date fechaEscogida = new java.sql.Date(fechaUtil.getTime());
        buscarVentaPorFecha(fechaEscogida);
    }//GEN-LAST:event_btnBuscarFechaMouseClicked

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
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaVentas;
    private javax.swing.JPanel btn6;
    private javax.swing.JPanel btn8;
    private javax.swing.JLabel btnBuscarFecha;
    private UI.PanelRound btnI6;
    private UI.PanelRound btnI8;
    private javax.swing.JButton btnIng;
    private javax.swing.JButton btnIni;
    private javax.swing.JButton btnInv;
    private javax.swing.JButton btnPagoCompletado;
    private javax.swing.JButton btnPed;
    private javax.swing.JButton btnProv;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVen;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JList<String> lstProductos;
    private com.toedter.calendar.JDateChooser obtFecha;
    private javax.swing.JScrollPane panelLista;
    private UI.PanelRound panelRound1;
    private UI.PanelRound panelRound11;
    private UI.PanelRound panelRound13;
    private UI.PanelRound panelRound2;
    private UI.PanelRound panelRound3;
    private javax.swing.JScrollPane panelTabla;
    private UI.PanelRound pnlPagoCpto;
    // End of variables declaration//GEN-END:variables
}
