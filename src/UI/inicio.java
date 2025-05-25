/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import Modelo.*;
import ManejoTablas.*;
import UIEmergentes.AgregarProductosVenta;
import UIEmergentes.PagarPedido;
import UIEmergentes.PagarVenta;
import com.formdev.flatlaf.ui.FlatScrollBarUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author salin
 */
public class inicio extends javax.swing.JFrame {
    private ManejoProductos manejoproductos = new ManejoProductos();
    private ManejoInventario manejoinventario = new ManejoInventario();
    private Producto producto;
    private DefaultListModel<String> modeloLista = new DefaultListModel<>(); //Modelo Lista Nombre
    private DefaultListModel<String> modeloLista2 = new DefaultListModel<>(); //Modelo Lista Cantidad
    private DefaultListModel<String> modeloLista3 = new DefaultListModel<>(); //Modelo lista Total
    private DefaultListModel<Integer> modeloLista4 = new DefaultListModel<>(); //Modelo Lista ID
    /**
     * Creates new form vistaLogin
     */
    public inicio() {
        initComponents();
        Conexion.Programar_respaldo.iniciarRespaldoDiario();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo6.png")).getImage());
        lstNombre.setModel(modeloLista);
        lstCantidad.setModel(modeloLista2);
        lstTotal.setModel(modeloLista3);
        lstID.setModel(modeloLista4);
        
        producto = null;
        TablaProductos.setRowHeight(130);
        panelTabla.getViewport().setBackground(Color.WHITE);
        JTableHeader header = TablaProductos.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.BOLD, 16));
        header.setBackground(new Color(0,0,0));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(),1));
        header.setOpaque(true);

        DefaultTableCellRenderer hr = new DefaultTableCellRenderer();
        int desiredWidth = 110;  // Ancho de la imagen
        int desiredHeight = 110; // Alto de la imagen
        int anchoBoton = 60;
        int altoBoton = 60;
        hr.setBackground(new Color(0,0,0));
        hr.setForeground(Color.WHITE);
        hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TablaProductos.getTableHeader().setVisible(false);
        //TablaProductos.setTableHeader(null);
        
         DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(new Color(255, 255, 255)); // Color uniforme para todas las celdas
                cell.setForeground(Color.BLACK); // Texto en negro
                if (cell instanceof JLabel) {
                    ((JLabel) cell).setHorizontalAlignment(CENTER); // Alineación del texto al centro
                }
                 //Mostrar imagenes de productos
                 if (column == 1 && value instanceof ImageIcon) {
                    ImageIcon originalIcon = (ImageIcon) value;
                    Image originalImage = originalIcon.getImage();
                    Image scaledImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    JLabel label = new JLabel(scaledIcon);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
                 
                 // Mostrar el botón
                 if (column == 4 && value instanceof ImageIcon) {
                    ImageIcon buttonIcon = (ImageIcon) value;
                    Image originalButtonImage = buttonIcon.getImage();
                    Image scaledButtonImage = originalButtonImage.getScaledInstance(anchoBoton, altoBoton, Image.SCALE_SMOOTH);
                    ImageIcon scaledButtonIcon = new ImageIcon(scaledButtonImage);
                    JLabel label = new JLabel(scaledButtonIcon);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
                
                return cell;
            }
        };

        // Aplicar el renderer a todas las columnas
        TablaProductos.setDefaultRenderer(Object.class, cellRenderer); // Aplica el renderer a todas las columnas por defecto
        TablaProductos.getColumnModel().getColumn(0).setCellRenderer(hr);

       
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
            
            quitabordes();
            agregar();
        
            
            TablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
            TablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            TablaProductos.getColumnModel().getColumn(0).setWidth(0);
            TablaProductos.getColumnModel().getColumn(4).setWidth(40);
            TablaProductos.getColumnModel().getColumn(2).setWidth(80);
            pnlListaNovisible.setVisible(false);
            lstID.setVisible(false);
    }
    
    public void cargarDatos(int n) {
    List<Producto> listaProductos;
    if (n == 1) {
        try {
            listaProductos = manejoproductos.ObtenerProductos();
            DefaultTableModel modelo = (DefaultTableModel) TablaProductos.getModel();
            modelo.setRowCount(0);
            for (Producto p : listaProductos) {
                ImageIcon icono = null;
                String rutaImagen = p.getImagen(); 


                if (rutaImagen != null && !rutaImagen.isEmpty()) {
                    URL imageUrl = getClass().getResource(rutaImagen);
                    if (imageUrl != null) {
                        icono = new ImageIcon(imageUrl);
                        // Opcional: Escalar el icono
                    } else {
                        System.err.println("No se encontró la imagen en la ruta del paquete: " + rutaImagen);
                        // Cargar icono por defecto si es necesario
                    }
                }
                
                ImageIcon iconoDefault = null;
                    URL defaultImageUrl = getClass().getResource("/imagenes/Agregar.png"); // Ruta de la imagen por defecto
                    if (defaultImageUrl != null) {
                        iconoDefault = new ImageIcon(defaultImageUrl);
                        // Opcional: Escalar el icono por defecto si es necesario
                    } else {
                        System.err.println("No se encontró la imagen por defecto.");
                    }
                

                Object[] fila = {
                        p.getIdProducto(),
                        icono, // Intentamos guardar el ImageIcon
                        p.getNombre(),
                        "$ " + p.getCostoVenta(),
                        iconoDefault
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    public void cargarProductosPorTipo(String tipo){
        List<Producto> listaProductos;
        try {
            listaProductos = manejoproductos.ObtenerProductosPorTipoProducto(tipo);
            DefaultTableModel modelo = (DefaultTableModel) TablaProductos.getModel();
            modelo.setRowCount(0);
            for (Producto p : listaProductos) {
                ImageIcon icono = null;
                String rutaImagen = p.getImagen(); 


                if (rutaImagen != null && !rutaImagen.isEmpty()) {
                    URL imageUrl = getClass().getResource(rutaImagen);
                    if (imageUrl != null) {
                        icono = new ImageIcon(imageUrl);
                        // Opcional: Escalar el icono
                    } else {
                        System.err.println("No se encontró la imagen en la ruta del paquete: " + rutaImagen);
                        // Cargar icono por defecto si es necesario
                    }
                }
                
                ImageIcon iconoDefault = null;
                    URL defaultImageUrl = getClass().getResource("/imagenes/Agregar.png"); // Ruta de la imagen por defecto
                    if (defaultImageUrl != null) {
                        iconoDefault = new ImageIcon(defaultImageUrl);
                        // Opcional: Escalar el icono por defecto si es necesario
                    } else {
                        System.err.println("No se encontró la imagen por defecto.");
                    }
                

                Object[] fila = {
                        p.getIdProducto(),
                        icono, // Intentamos guardar el ImageIcon
                        p.getNombre(),
                        "$ " + p.getCostoVenta(),
                        iconoDefault
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
    
    public void buscarProductos(String nome){
         List<Producto> listaProductos = null;
        try {
            listaProductos = manejoproductos.ObtenerProductosPorNombre(nome);
            if(listaProductos == null){
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
            }
            DefaultTableModel modelo = (DefaultTableModel) TablaProductos.getModel();
            modelo.setRowCount(0);
            for (Producto p : listaProductos) {
                ImageIcon icono = null;
                String rutaImagen = p.getImagen(); 
                System.out.println("ruta de productos "+rutaImagen);

                if (rutaImagen != null && !rutaImagen.isEmpty()) {
                    URL imageUrl = getClass().getResource(rutaImagen);
                    if (imageUrl != null) {
                        icono = new ImageIcon(imageUrl);
                        // Opcional: Escalar el icono
                    } else {
                        System.err.println("No se encontró la imagen en la ruta del paquete: " + rutaImagen);
                        // Cargar icono por defecto si es necesario
                    }
                }
                
                ImageIcon iconoDefault = null;
                    URL defaultImageUrl = getClass().getResource("/imagenes/Agregar.png"); // Ruta de la imagen por defecto
                    if (defaultImageUrl != null) {
                        iconoDefault = new ImageIcon(defaultImageUrl);
                        // Opcional: Escalar el icono por defecto si es necesario
                    } else {
                        System.err.println("No se encontró la imagen por defecto.");
                    }
                

                Object[] fila = {
                        p.getIdProducto(),
                        icono, // Intentamos guardar el ImageIcon
                        p.getNombre(),
                        "$ " + p.getCostoVenta(),
                        iconoDefault
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
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
        PnlAccion = new javax.swing.JPanel();
        btn1 = new javax.swing.JPanel();
        btnVentas = new UI.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelRound5 = new UI.PanelRound();
        btn2 = new javax.swing.JPanel();
        btnPendientes = new UI.PanelRound();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelRound11 = new UI.PanelRound();
        btn3 = new javax.swing.JPanel();
        btnGanancias = new UI.PanelRound();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panelRound13 = new UI.PanelRound();
        btn4 = new javax.swing.JPanel();
        btnStock = new UI.PanelRound();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelRound15 = new UI.PanelRound();
        pnlVenta = new UI.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        pnlL1 = new javax.swing.JScrollPane();
        lstNombre = new javax.swing.JList<>();
        pnlL2 = new javax.swing.JScrollPane();
        lstCantidad = new javax.swing.JList<>();
        pnlL3 = new javax.swing.JScrollPane();
        lstTotal = new javax.swing.JList<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cboTipoVenta = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        btnV1 = new UI.PanelRound();
        btnGenVen = new javax.swing.JButton();
        btnV2 = new UI.PanelRound();
        btnCanVen = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        lblPrecioTotal = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        pnlListaNovisible = new javax.swing.JScrollPane();
        lstID = new javax.swing.JList<>();
        btnV3 = new UI.PanelRound();
        btnEliminar = new javax.swing.JButton();
        btnV5 = new UI.PanelRound();
        btnCanVen3 = new javax.swing.JButton();
        panelTabla = new javax.swing.JScrollPane();
        TablaProductos = new javax.swing.JTable();
        panelRound2 = new UI.PanelRound();
        jSeparator1 = new javax.swing.JSeparator();
        txtNombreProducto = new javax.swing.JTextField();
        panelRound3 = new UI.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        panelRound4 = new UI.PanelRound();
        jLabel20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnIng, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
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
        jLabel3.setText("Inicio");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, -1));

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(250, 176, 47));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FERRETERÍA \"JUQUILITA\"");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 575, 27));

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

        PnlAccion.setBackground(new java.awt.Color(255, 255, 255));
        PnlAccion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn1.setOpaque(false);
        btn1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVentas.setBackground(new java.awt.Color(250, 176, 47));
        btnVentas.setRoundBottomLeft(50);
        btnVentas.setRoundBottomRight(50);
        btnVentas.setRoundTopLeft(50);
        btnVentas.setRoundTopRight(50);
        btnVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVentasMouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Ventas del dia");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dinero 64.png"))); // NOI18N

        javax.swing.GroupLayout btnVentasLayout = new javax.swing.GroupLayout(btnVentas);
        btnVentas.setLayout(btnVentasLayout);
        btnVentasLayout.setHorizontalGroup(
            btnVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnVentasLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        btnVentasLayout.setVerticalGroup(
            btnVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnVentasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        btn1.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        panelRound5.setBackground(new java.awt.Color(153, 153, 153));
        panelRound5.setRoundBottomLeft(50);
        panelRound5.setRoundBottomRight(50);
        panelRound5.setRoundTopLeft(50);
        panelRound5.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
        );
        panelRound5Layout.setVerticalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        btn1.add(panelRound5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn2.setOpaque(false);
        btn2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPendientes.setBackground(new java.awt.Color(250, 176, 47));
        btnPendientes.setRoundBottomLeft(50);
        btnPendientes.setRoundBottomRight(50);
        btnPendientes.setRoundTopLeft(50);
        btnPendientes.setRoundTopRight(50);
        btnPendientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPendientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPendientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPendientesMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Pedidos pendientes");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carretilla.png"))); // NOI18N

        javax.swing.GroupLayout btnPendientesLayout = new javax.swing.GroupLayout(btnPendientes);
        btnPendientes.setLayout(btnPendientesLayout);
        btnPendientesLayout.setHorizontalGroup(
            btnPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPendientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(btnPendientesLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPendientesLayout.setVerticalGroup(
            btnPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPendientesLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        btn2.add(btnPendientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        panelRound11.setBackground(new java.awt.Color(153, 153, 153));
        panelRound11.setRoundBottomLeft(50);
        panelRound11.setRoundBottomRight(50);
        panelRound11.setRoundTopLeft(50);
        panelRound11.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound11Layout = new javax.swing.GroupLayout(panelRound11);
        panelRound11.setLayout(panelRound11Layout);
        panelRound11Layout.setHorizontalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
        );
        panelRound11Layout.setVerticalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        btn2.add(panelRound11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn3.setOpaque(false);
        btn3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGanancias.setBackground(new java.awt.Color(250, 176, 47));
        btnGanancias.setRoundBottomLeft(50);
        btnGanancias.setRoundBottomRight(50);
        btnGanancias.setRoundTopLeft(50);
        btnGanancias.setRoundTopRight(50);
        btnGanancias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGananciasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGananciasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGananciasMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Ganancias semanales");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ganancias.png"))); // NOI18N

        javax.swing.GroupLayout btnGananciasLayout = new javax.swing.GroupLayout(btnGanancias);
        btnGanancias.setLayout(btnGananciasLayout);
        btnGananciasLayout.setHorizontalGroup(
            btnGananciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGananciasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(btnGananciasLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnGananciasLayout.setVerticalGroup(
            btnGananciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGananciasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        btn3.add(btnGanancias, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        panelRound13.setBackground(new java.awt.Color(153, 153, 153));
        panelRound13.setRoundBottomLeft(50);
        panelRound13.setRoundBottomRight(50);
        panelRound13.setRoundTopLeft(50);
        panelRound13.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound13Layout = new javax.swing.GroupLayout(panelRound13);
        panelRound13.setLayout(panelRound13Layout);
        panelRound13Layout.setHorizontalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
        );
        panelRound13Layout.setVerticalGroup(
            panelRound13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        btn3.add(panelRound13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn4.setOpaque(false);
        btn4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnStock.setBackground(new java.awt.Color(250, 176, 47));
        btnStock.setRoundBottomLeft(50);
        btnStock.setRoundBottomRight(50);
        btnStock.setRoundTopLeft(50);
        btnStock.setRoundTopRight(50);
        btnStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnStockMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnStockMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Stock limitado");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/stock.png"))); // NOI18N

        javax.swing.GroupLayout btnStockLayout = new javax.swing.GroupLayout(btnStock);
        btnStock.setLayout(btnStockLayout);
        btnStockLayout.setHorizontalGroup(
            btnStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(btnStockLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnStockLayout.setVerticalGroup(
            btnStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        btn4.add(btnStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        panelRound15.setBackground(new java.awt.Color(153, 153, 153));
        panelRound15.setRoundBottomLeft(50);
        panelRound15.setRoundBottomRight(50);
        panelRound15.setRoundTopLeft(50);
        panelRound15.setRoundTopRight(50);

        javax.swing.GroupLayout panelRound15Layout = new javax.swing.GroupLayout(panelRound15);
        panelRound15.setLayout(panelRound15Layout);
        panelRound15Layout.setHorizontalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
        );
        panelRound15Layout.setVerticalGroup(
            panelRound15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        btn4.add(panelRound15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        pnlVenta.setBackground(new java.awt.Color(204, 204, 204));
        pnlVenta.setRoundBottomLeft(40);
        pnlVenta.setRoundBottomRight(40);
        pnlVenta.setRoundTopLeft(40);
        pnlVenta.setRoundTopRight(40);

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Detalle de venta");

        pnlL1.setOpaque(false);

        lstNombre.setBackground(new java.awt.Color(204, 204, 204));
        lstNombre.setBorder(null);
        lstNombre.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        pnlL1.setViewportView(lstNombre);

        pnlL2.setOpaque(false);

        lstCantidad.setBackground(new java.awt.Color(204, 204, 204));
        lstCantidad.setBorder(null);
        lstCantidad.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        pnlL2.setViewportView(lstCantidad);

        pnlL3.setOpaque(false);

        lstTotal.setBackground(new java.awt.Color(204, 204, 204));
        lstTotal.setBorder(null);
        lstTotal.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        pnlL3.setViewportView(lstTotal);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel13.setText("Nombre del Producto");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel14.setText("Cantidad");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel15.setText("Total");

        cboTipoVenta.setBackground(new java.awt.Color(250, 176, 47));
        cboTipoVenta.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        cboTipoVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Venta Rapida", "Pedido" }));
        cboTipoVenta.setBorder(null);
        cboTipoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoVentaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel16.setText("Tipo de venta :");

        btnV1.setBackground(new java.awt.Color(250, 176, 47));
        btnV1.setRoundBottomLeft(50);
        btnV1.setRoundBottomRight(50);
        btnV1.setRoundTopLeft(50);
        btnV1.setRoundTopRight(50);
        btnV1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnV1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnV1MouseExited(evt);
            }
        });

        btnGenVen.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btnGenVen.setText("Generar Venta");
        btnGenVen.setBorder(null);
        btnGenVen.setContentAreaFilled(false);
        btnGenVen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenVen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGenVenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGenVenMouseExited(evt);
            }
        });
        btnGenVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenVenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnV1Layout = new javax.swing.GroupLayout(btnV1);
        btnV1.setLayout(btnV1Layout);
        btnV1Layout.setHorizontalGroup(
            btnV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGenVen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnV1Layout.setVerticalGroup(
            btnV1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGenVen, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnV2.setBackground(new java.awt.Color(255, 255, 255));
        btnV2.setRoundBottomLeft(50);
        btnV2.setRoundBottomRight(50);
        btnV2.setRoundTopLeft(50);
        btnV2.setRoundTopRight(50);
        btnV2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnV2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnV2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnV2MouseExited(evt);
            }
        });

        btnCanVen.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btnCanVen.setText("Cancelar Venta");
        btnCanVen.setBorder(null);
        btnCanVen.setContentAreaFilled(false);
        btnCanVen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCanVen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCanVenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCanVenMouseExited(evt);
            }
        });
        btnCanVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanVenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnV2Layout = new javax.swing.GroupLayout(btnV2);
        btnV2.setLayout(btnV2Layout);
        btnV2Layout.setHorizontalGroup(
            btnV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCanVen, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnV2Layout.setVerticalGroup(
            btnV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCanVen, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel17.setText("Total a pagar :");

        lblPrecioTotal.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblPrecioTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrecioTotal.setText("-");

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        jLabel19.setText("$");

        lstID.setBackground(new java.awt.Color(204, 204, 204));
        lstID.setBorder(null);
        lstID.setEnabled(false);
        pnlListaNovisible.setViewportView(lstID);

        btnV3.setBackground(new java.awt.Color(255, 255, 255));
        btnV3.setRoundBottomLeft(50);
        btnV3.setRoundBottomRight(50);
        btnV3.setRoundTopLeft(50);
        btnV3.setRoundTopRight(50);
        btnV3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnV3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnV3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnV3MouseExited(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btnEliminar.setText("Eliminar Producto");
        btnEliminar.setBorder(null);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnV3Layout = new javax.swing.GroupLayout(btnV3);
        btnV3.setLayout(btnV3Layout);
        btnV3Layout.setHorizontalGroup(
            btnV3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnV3Layout.setVerticalGroup(
            btnV3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnV5.setBackground(new java.awt.Color(255, 255, 255));
        btnV5.setRoundBottomLeft(50);
        btnV5.setRoundBottomRight(50);
        btnV5.setRoundTopLeft(50);
        btnV5.setRoundTopRight(50);
        btnV5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnV5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnV5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnV5MouseExited(evt);
            }
        });

        btnCanVen3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btnCanVen3.setText("Devolución");
        btnCanVen3.setBorder(null);
        btnCanVen3.setContentAreaFilled(false);
        btnCanVen3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCanVen3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCanVen3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCanVen3MouseExited(evt);
            }
        });
        btnCanVen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanVen3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnV5Layout = new javax.swing.GroupLayout(btnV5);
        btnV5.setLayout(btnV5Layout);
        btnV5Layout.setHorizontalGroup(
            btnV5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCanVen3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnV5Layout.setVerticalGroup(
            btnV5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnV5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCanVen3, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlVentaLayout = new javax.swing.GroupLayout(pnlVenta);
        pnlVenta.setLayout(pnlVentaLayout);
        pnlVentaLayout.setHorizontalGroup(
            pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVentaLayout.createSequentialGroup()
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlVentaLayout.createSequentialGroup()
                                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlL1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlVentaLayout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel14)
                                        .addGap(59, 59, 59))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVentaLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pnlL2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(pnlL3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlVentaLayout.createSequentialGroup()
                                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVentaLayout.createSequentialGroup()
                                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(7, 7, 7)
                                        .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlVentaLayout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addComponent(jLabel19)
                                                .addGap(22, 22, 22)
                                                .addComponent(lblPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(16, 16, 16))
                                            .addComponent(cboTipoVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(pnlListaNovisible, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(pnlVentaLayout.createSequentialGroup()
                                .addComponent(btnV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnV2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnV3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(btnV5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlVentaLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        pnlVentaLayout.setVerticalGroup(
            pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTipoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(pnlListaNovisible, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(7, 7, 7)
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlL2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(pnlL3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlL1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblPrecioTotal)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(pnlVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnV2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnV3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnV5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        panelTabla.setBorder(null);

        TablaProductos.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        TablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "Imagen", "Nombre", "Precio", "Boton +"},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TablaProductos.setShowHorizontalLines(true);
        TablaProductos.getTableHeader().setResizingAllowed(false);
        TablaProductos.getTableHeader().setReorderingAllowed(false);
        panelTabla.setViewportView(TablaProductos);

        panelRound2.setBackground(new java.awt.Color(204, 204, 204));
        panelRound2.setRoundBottomLeft(50);
        panelRound2.setRoundBottomRight(50);
        panelRound2.setRoundTopLeft(50);
        panelRound2.setRoundTopRight(50);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        txtNombreProducto.setBackground(new java.awt.Color(204, 204, 204));
        txtNombreProducto.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtNombreProducto.setForeground(new java.awt.Color(102, 102, 102));
        txtNombreProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombreProducto.setText("Ingresa el nombre de un producto");
        txtNombreProducto.setBorder(null);
        txtNombreProducto.setOpaque(true);
        txtNombreProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreProductoMouseClicked(evt);
            }
        });
        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });
        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNombreProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelRound3.setBackground(new java.awt.Color(204, 204, 204));
        panelRound3.setRoundBottomLeft(70);
        panelRound3.setRoundBottomRight(70);
        panelRound3.setRoundTopLeft(70);
        panelRound3.setRoundTopRight(70);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelRound4.setBackground(new java.awt.Color(204, 204, 204));
        panelRound4.setRoundBottomLeft(70);
        panelRound4.setRoundBottomRight(70);
        panelRound4.setRoundTopLeft(70);
        panelRound4.setRoundTopRight(70);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizarChiquito.png"))); // NOI18N
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PnlAccionLayout = new javax.swing.GroupLayout(PnlAccion);
        PnlAccion.setLayout(PnlAccionLayout);
        PnlAccionLayout.setHorizontalGroup(
            PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlAccionLayout.createSequentialGroup()
                .addGroup(PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlAccionLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PnlAccionLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PnlAccionLayout.createSequentialGroup()
                    .addGroup(PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlAccionLayout.createSequentialGroup()
                            .addGap(610, 610, 610)
                            .addComponent(pnlVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlAccionLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(38, 38, 38)
                            .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(38, 38, 38)
                            .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(61, 61, 61)))
        );
        PnlAccionLayout.setVerticalGroup(
            PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlAccionLayout.createSequentialGroup()
                .addContainerGap(215, Short.MAX_VALUE)
                .addGroup(PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PnlAccionLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addGroup(PnlAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(pnlVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 275, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PnlAccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PnlAccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    public void quitabordes(){
        JScrollBar vs = pnlL1.getVerticalScrollBar();
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
            
            
            JScrollBar vh = pnlL1.getHorizontalScrollBar();
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
            
            
            
            
               JScrollBar vs2 = pnlL2.getVerticalScrollBar();
        vs2.setUI(new FlatScrollBarUI());
        vs2.setPreferredSize(new Dimension(12, 0)); // Ancho de la barra
        vs2.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(204, 204, 204));// Color para el pulgar, ocre -> 250 191 91
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vs2.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vs2.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(204, 204, 204)); // Color del pulgar (gris)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
            
            
            JScrollBar vh2 = pnlL2.getHorizontalScrollBar();
        vh2.setUI(new FlatScrollBarUI());
        vh2.setPreferredSize(new Dimension(0, 12)); // Ancho de la barra
        vh2.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(250, 191, 91)); // Color azul para el pulgar
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vh2.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vh2.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(250, 191, 91)); // Color del pulgar (ocre)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
            
            
            
            JScrollBar vs3 = pnlL3.getVerticalScrollBar();
        vs3.setUI(new FlatScrollBarUI());
        vs3.setPreferredSize(new Dimension(12, 0)); // Ancho de la barra
        vs3.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(204, 204, 204));// Color para el pulgar, ocre -> 250 191 91
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vs3.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vs3.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(204, 204, 204)); // Color del pulgar (gris)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
            
            
            JScrollBar vh3 = pnlL2.getHorizontalScrollBar();
        vh3.setUI(new FlatScrollBarUI());
        vh3.setPreferredSize(new Dimension(0, 12)); // Ancho de la barra
        vh3.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(250, 191, 91)); // Color azul para el pulgar
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vh3.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vh3.setUI(new FlatScrollBarUI() {
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
    
    public void agregar(){
        TablaProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = TablaProductos.rowAtPoint(e.getPoint());
                int columna = TablaProductos.columnAtPoint(e.getPoint());

            if (columna == 4 && fila != -1) {
                String nombreP = (String) TablaProductos.getValueAt(fila, 2);
                int idP = Integer.parseInt(TablaProductos.getValueAt(fila, 0).toString());
                AgregarProductosVenta ap = new AgregarProductosVenta(null, true);
                ap.setIdProducto(idP);
                ap.CambiarNombre(nombreP,idP);
                ap.setVisible(true);

                if (ap.isAceptado()) {
                    int existingIndex = -1;
                    for (int i = 0; i < modeloLista.getSize(); i++) {
                        if (modeloLista.getElementAt(i).equals(nombreP)) {
                            existingIndex = i;
                            break;
                        }
                    }

                    int cantNueva = ap.getNumeroSeleccionado();
                    int id = (int) TablaProductos.getValueAt(fila, 0);
                    String obtPrecioStr = (String) TablaProductos.getValueAt(fila, 3);
                    obtPrecioStr = obtPrecioStr.replace("$", "").replace("-", "").trim();
                    double precioUnitario = 0.0;
                    try {
                        precioUnitario = Double.parseDouble(obtPrecioStr);
                    } catch (NumberFormatException ex) {
                        System.err.println("Error al parsear precio: " + obtPrecioStr);
                        return;
                    }

                    if (existingIndex != -1) {
                        // Producto ya existe, actualizar cantidad y total
                        String cantidadExistenteStr = modeloLista2.getElementAt(existingIndex).split(" ")[0]; // Obtener la cantidad
                        int cantidadExistente = 0;
                        try {
                            cantidadExistente = Integer.parseInt(cantidadExistenteStr);
                        } catch (NumberFormatException ex) {
                            System.err.println("Error al parsear cantidad existente: " + cantidadExistenteStr);
                            return;
                        }
                        int nuevaCantidadTotal = cantidadExistente + cantNueva;

                        Producto p;
                        String unidadMedida = "";
                        try {
                            p = manejoproductos.ObtenerProductoPorid(id);
                            unidadMedida = p.getUnidadMedida();
                        } catch (SQLException ex) {
                            Logger.getLogger(inicio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String mcNueva = (nuevaCantidadTotal == 1) ? nuevaCantidadTotal + " - " + unidadMedida : nuevaCantidadTotal + " - " + unidadMedida + "s";
                        modeloLista2.setElementAt(mcNueva, existingIndex);

                        double nuevoTotal = nuevaCantidadTotal * precioUnitario;
                        modeloLista3.setElementAt("$ - " + String.format("%.2f", nuevoTotal), existingIndex);
                        modeloLista4.setElementAt(idP, existingIndex);
                    } else {
                        // Producto nuevo, agregar a las listas
                        modeloLista.addElement(nombreP);

                        Producto p;
                        String unidadMedida = "";
                        try {
                            p = manejoproductos.ObtenerProductoPorid(id);
                            unidadMedida = p.getUnidadMedida();
                        } catch (SQLException ex) {
                            Logger.getLogger(inicio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String mcNueva = (cantNueva == 1) ? cantNueva + " - " + unidadMedida : cantNueva + " - " + unidadMedida + "s";
                        modeloLista2.addElement(mcNueva);

                        double nuevoTotal = cantNueva * precioUnitario;
                        modeloLista3.addElement("$ - " + String.format("%.2f", nuevoTotal));
                        modeloLista4.addElement(idP);
                    }
                    actualizarSumaTotal(); // Recalcular la suma total
            }}}
        });
    }
    
    private void actualizarSumaTotal() {
        double suma = 0.0;
        for (int i = 0; i < modeloLista3.getSize(); i++) {
            String valorStr = modeloLista3.getElementAt(i).replace("$", "").replace("-", "").trim();
            try {
                double valor = Double.parseDouble(valorStr);
                suma += valor;
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear valor para la suma: " + valorStr);
            }
        }
        lblPrecioTotal.setText( String.format("%.2f", suma)); 
    }
    
    private void eliminarElementoSincronizado(int index) {
        if (index >= 0 && index < modeloLista.getSize()) {
            modeloLista.remove(index);
        }
        if (index >= 0 && index < modeloLista2.getSize()) {
            modeloLista2.remove(index);
        }
        if (index >= 0 && index < modeloLista3.getSize()) {
            modeloLista3.remove(index);
        }
        if (index >= 0 && index < modeloLista4.getSize()) {
            modeloLista4.remove(index);
        }
    }

    public void removerTodo(){
        modeloLista.removeAllElements();
        modeloLista2.removeAllElements();
        modeloLista3.removeAllElements();
        modeloLista4.removeAllElements();
        lblPrecioTotal.setText("-");
    }
    
    public void removerProductos(){
        int size = modeloLista4.getSize();
        
        for(int i = size - 1; i >= 0; i--){
        actualizarInventarioTrasEliminacion(i);
        modeloLista.removeAllElements();
        modeloLista2.removeAllElements();
        modeloLista3.removeAllElements();
        modeloLista4.removeAllElements();
        }
        lblPrecioTotal.setText("-");
    }
   
         public void actualizarInventarioTrasEliminacion(int index) {
        if (index >= 0 && index < modeloLista.getSize() && index < modeloLista2.getSize()) {
            int idProducto = modeloLista4.getElementAt(index);
            String cantidadTexto = modeloLista2.getElementAt(index);

            try {
                MostrarInventario inventarioSeleccionado = manejoinventario.obtenerPorIdProducto(idProducto);

                if (inventarioSeleccionado != null) {
                    int cantidadActualInventario = inventarioSeleccionado.getCantidad();
                    String[] partes = cantidadTexto.split(" - ");
                    if (partes.length > 0) {
                        try {
                            int cantidadEliminada = Integer.parseInt(partes[0]);
                            int nuevaCantidadInventario = cantidadActualInventario + cantidadEliminada;                         
                            manejoinventario.actualizarCantidadPorIdProd(idProducto, nuevaCantidadInventario);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    } 
                } 

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 
    }
    
    public void imprimirDatosDeListas() {
    System.out.println("--- Datos de las Listas ---");
    int numElementos = modeloLista.getSize(); // Usamos el tamaño del modelo de nombres como referencia

    if (modeloLista2.getSize() != numElementos || modeloLista3.getSize() != numElementos || modeloLista4.getSize() != numElementos) {
        System.err.println("¡Advertencia! Las listas tienen diferente número de elementos. La impresión podría ser incorrecta.");
    }

    for (int i = 0; i < numElementos; i++) {
        String nombre = "";
        String cantidad = "";
        String total = "";
        Integer id = null;

        if (i < modeloLista.getSize()) {
            nombre = modeloLista.getElementAt(i);
        }
        if (i < modeloLista2.getSize()) {
            cantidad = modeloLista2.getElementAt(i);
        }
        if (i < modeloLista3.getSize()) {
            total = modeloLista3.getElementAt(i);
        }
        if (i < modeloLista4.getSize()) {
            id = modeloLista4.getElementAt(i);
        }

        System.out.println("Índice: " + i +
                           ", ID: " + id +
                           ", Nombre: " + nombre +
                           ", Cantidad: " + cantidad +
                           ", Total: " + total);
    }
    System.out.println("--------------------------");
}
    
    private void btnIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniActionPerformed
        // TODO add your handling code here:
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

    private void btnStockMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockMouseExited
        btnStock.setBackground(new Color(250,176,47)); //[250,176,47]
    }//GEN-LAST:event_btnStockMouseExited

    private void btnStockMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockMouseEntered
        btnStock.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnStockMouseEntered

    private void btnGananciasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGananciasMouseExited
        btnGanancias.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnGananciasMouseExited

    private void btnGananciasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGananciasMouseEntered
        btnGanancias.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnGananciasMouseEntered

    private void btnPendientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPendientesMouseExited
        btnPendientes.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnPendientesMouseExited

    private void btnPendientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPendientesMouseEntered
        btnPendientes.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnPendientesMouseEntered

    private void btnVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseExited
        btnVentas.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnVentasMouseExited

    private void btnVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseEntered
        btnVentas.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnVentasMouseEntered

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void cboTipoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoVentaActionPerformed
        
        String obtTipoProd = cboTipoVenta.getSelectedItem().toString();
        String TipoProd;
        if(obtTipoProd.equals("Venta Rapida")){TipoProd = "Ferreteria";}
        else{TipoProd = "Construcción";}
        cargarProductosPorTipo(TipoProd);
        
        
        
    }//GEN-LAST:event_cboTipoVentaActionPerformed

    private void btnGenVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenVenActionPerformed
        
        String sel = cboTipoVenta.getSelectedItem().toString();
        
        imprimirDatosDeListas();
        if (lstNombre.getModel().getSize() == 0 || lblPrecioTotal.getText().equals("-")) {
        JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún producto");
        return;
        }else{
            Double cm = Double.parseDouble(lblPrecioTotal.getText());
        
        if(sel.equals("Venta Rapida")){
            PagarVenta pv = new PagarVenta(null, true);
            pv.definirCantidadMin(cm);
            pv.obtenerTotal(lblPrecioTotal.getText());
            pv.setListaIDsInventario(lstID);
            pv.setListaCantidades(lstCantidad);
            pv.setVisible(true);
            if(pv.isAceptado()){
            removerTodo();
            }
        }else{
            PagarPedido pp = new PagarPedido(null, true);
            pp.definirCantidadMin(cm);
            pp.obtenerTotal(lblPrecioTotal.getText());
            pp.setListaIDsInventario(lstID);
            pp.setListaCantidades(lstCantidad);
            pp.setVisible(true);
            removerTodo();
            if(pp.isAceptado()){
            removerTodo();
            }
        }
        }
        
    }//GEN-LAST:event_btnGenVenActionPerformed

    private void btnCanVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanVenActionPerformed
        if (lstNombre.getModel().getSize() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos seleccionados");
            return;
        }

        
        removerProductos();
    }//GEN-LAST:event_btnCanVenActionPerformed

    private void btnGenVenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenVenMouseEntered
       btnV1.setBackground(new Color(228,143,5)); //[250,176,47]
    }//GEN-LAST:event_btnGenVenMouseEntered

    private void btnGenVenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenVenMouseExited
        btnV1.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnGenVenMouseExited

    private void btnCanVenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCanVenMouseEntered
        btnV2.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnCanVenMouseEntered

    private void btnCanVenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCanVenMouseExited
        btnV2.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnCanVenMouseExited

    private void btnV1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV1MouseEntered
         btnV1.setBackground(new Color(228,143,5)); //[250,176,47]
    }//GEN-LAST:event_btnV1MouseEntered

    private void btnV1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV1MouseExited
        btnV1.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnV1MouseExited

    private void btnV2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnV2MouseClicked

    private void btnV2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV2MouseEntered
         btnV2.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnV2MouseEntered

    private void btnV2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV2MouseExited
        btnV2.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnV2MouseExited

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnV3.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        btnV3.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (lstNombre.getModel().getSize() == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en la lista de compra");
            return;
        }



        int selectedIndexNombre = lstNombre.getSelectedIndex();
                int selectedIndexCantidad = lstCantidad.getSelectedIndex();
                int selectedIndexTotal = lstTotal.getSelectedIndex();

                int selectedIndex = -1; // Índice seleccionado

                // Determinar cuál lista tiene una selección
                if (selectedIndexNombre != -1) {
                    selectedIndex = selectedIndexNombre;
                } else if (selectedIndexCantidad != -1) {
                    selectedIndex = selectedIndexCantidad;
                } else if (selectedIndexTotal != -1) {
                    selectedIndex = selectedIndexTotal;
                }

                if (selectedIndex != -1) {
                    actualizarInventarioTrasEliminacion(selectedIndex);
                    eliminarElementoSincronizado(selectedIndex);
                    actualizarSumaTotal();
                    // Deseleccionar en todas las listas después de eliminar
                    lstNombre.clearSelection();
                    lstCantidad.clearSelection();
                    lstTotal.clearSelection();
                    lstID.clearSelection();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un elemento de alguna de las listas para eliminar");
                }
        
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnV3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnV3MouseClicked

    private void btnV3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV3MouseEntered
        btnV3.setBackground(new Color(233,229,229));
    }//GEN-LAST:event_btnV3MouseEntered

    private void btnV3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV3MouseExited
        btnV3.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnV3MouseExited

    private void btnStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockMouseClicked
        Inventario i = new Inventario();
        i.cargarDatos(2);
        i.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnStockMouseClicked

    private void btnPendientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPendientesMouseClicked
        Pedidos p = new Pedidos();
        p.cargarDatos(2);
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPendientesMouseClicked

    private void btnGananciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGananciasMouseClicked
        Ingresos i = new Ingresos();
        i.cargarDatos(1);
        i.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnGananciasMouseClicked

    private void txtNombreProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreProductoMouseClicked
        txtNombreProducto.setText("");
        txtNombreProducto.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtNombreProductoMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if(txtNombreProducto.getText().equals("Ingresa el nombre de un producto")||txtNombreProducto.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor, Ingresa el nombre de un producto");
        }else{
        buscarProductos(txtNombreProducto.getText());}
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        cargarDatos(1);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void btnCanVen3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCanVen3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCanVen3MouseEntered

    private void btnCanVen3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCanVen3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCanVen3MouseExited

    private void btnCanVen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanVen3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCanVen3ActionPerformed

    private void btnV5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnV5MouseClicked

    private void btnV5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnV5MouseEntered

    private void btnV5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnV5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnV5MouseExited

    private void btnVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseClicked
        Ventas v = new Ventas();
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaBase = java.sql.Date.valueOf(fechaActual);
        v.buscarVentaPorFecha(fechaBase);
        v.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVentasMouseClicked

    private void txtNombreProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyTyped
        char c = evt.getKeyChar();
                if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                    evt.consume(); // Ignorar el carácter
                }
    }//GEN-LAST:event_txtNombreProductoKeyTyped

    private void txtNombreProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            return;
        }
        try {
            DefaultTableModel modelo = (DefaultTableModel) TablaProductos.getModel();
            modelo.setRowCount(0);
            
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelo);
            TablaProductos.setRowSorter(sorter);
            
            txtNombreProducto.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    filtrar();
                }

                public void removeUpdate(DocumentEvent e) {
                    filtrar();
                }

                public void changedUpdate(DocumentEvent e) {
                    filtrar();
                }

                 private void filtrar() {
                 String texto = txtNombreProducto.getText();
                if (texto.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1)); // columna 1 = "Nombre"
                }
            }
        });
            cargarDatos(1);
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_txtNombreProductoKeyPressed

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
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlAccion;
    private javax.swing.JTable TablaProductos;
    private javax.swing.JPanel btn1;
    private javax.swing.JPanel btn2;
    private javax.swing.JPanel btn3;
    private javax.swing.JPanel btn4;
    private javax.swing.JButton btnCanVen;
    private javax.swing.JButton btnCanVen3;
    private javax.swing.JButton btnEliminar;
    private UI.PanelRound btnGanancias;
    private javax.swing.JButton btnGenVen;
    private javax.swing.JButton btnIng;
    private javax.swing.JButton btnIni;
    private javax.swing.JButton btnInv;
    private javax.swing.JButton btnPed;
    private UI.PanelRound btnPendientes;
    private javax.swing.JButton btnProv;
    private javax.swing.JButton btnSalir;
    private UI.PanelRound btnStock;
    private UI.PanelRound btnV1;
    private UI.PanelRound btnV2;
    private UI.PanelRound btnV3;
    private UI.PanelRound btnV5;
    private javax.swing.JButton btnVen;
    private UI.PanelRound btnVentas;
    private javax.swing.JComboBox<String> cboTipoVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPrecioTotal;
    private javax.swing.JList<String> lstCantidad;
    private javax.swing.JList<Integer> lstID;
    private javax.swing.JList<String> lstNombre;
    private javax.swing.JList<String> lstTotal;
    private UI.PanelRound panelRound11;
    private UI.PanelRound panelRound13;
    private UI.PanelRound panelRound15;
    private UI.PanelRound panelRound2;
    private UI.PanelRound panelRound3;
    private UI.PanelRound panelRound4;
    private UI.PanelRound panelRound5;
    private javax.swing.JScrollPane panelTabla;
    private javax.swing.JScrollPane pnlL1;
    private javax.swing.JScrollPane pnlL2;
    private javax.swing.JScrollPane pnlL3;
    private javax.swing.JScrollPane pnlListaNovisible;
    private UI.PanelRound pnlVenta;
    private javax.swing.JTextField txtNombreProducto;
    // End of variables declaration//GEN-END:variables

    
}
