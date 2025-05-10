/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package UIEmergentes;

import ManejoTablas.*;
import Modelo.*;
import com.formdev.flatlaf.ui.FlatScrollBarUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon; 
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author salin
 */
public class PagarPedido extends javax.swing.JDialog {
    private boolean aceptado;
    private int idCliente;
    private String numeroCliente;
    private JList<Integer> lstID; // Referencia a la JList desde donde obtendrás los IDs
    private DefaultListModel<Integer> modeloListaID; // Modelo de la JList
    private JList<String> lstCant; // Referencia a la JList desde donde obtendrás los IDs
    private DefaultListModel<String> modeloListaCant; // Modelo de la JList
    private HacerVentas hacerventa = new HacerVentas();
    private ManejoInventario manejoinventario = new ManejoInventario();
    private ManejoProductos manejoproductos = new ManejoProductos();
    private ManejoClientes manejoclientes = new ManejoClientes();
    private ManejoPedido manejopedidos = new ManejoPedido();
    private ManejoVentas manejoventas = new ManejoVentas();
    private int idVenta;
    /**
     * Creates new form PagarVenta
     */
    public PagarPedido(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo6.png")).getImage());
        pnlBancario.setVisible(false);
        btnA.setToolTipText("Agregar Cliente");
        btnB.setToolTipText("Buscar Cliente Existente");
        quitabordes();
    }

    public void obtenerTotal(String total){
        lblTotal.setText(total);
        lblTotal2.setText(total);
        calcularCambio();
    }
    
    public void definirCantidadMin(Double n){
        sprRecibido.setModel(new javax.swing.SpinnerNumberModel((Double)n, (Double)n, null, 1.0));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(sprRecibido, decimalFormat.toPattern());
        sprRecibido.setEditor(editor);
        //pcact = n;
    }
    
    private void calcularCambio(){
        Double tot = Double.parseDouble(lblTotal.getText());
        Double rec = Double.parseDouble(sprRecibido.getValue().toString());
        if(rec<tot){lblCambio.setText("-");}
        else{
        Double cambio = rec - tot;
        lblCambio.setText(String.format("%.2f", cambio));
        }
    }
    
    
     private void quitabordes(){
        JScrollBar vs = pnlComentarios.getVerticalScrollBar();
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
            
            
            JScrollBar vh = pnlComentarios.getHorizontalScrollBar();
        vh.setUI(new FlatScrollBarUI());
        vh.setPreferredSize(new Dimension(0, 12)); // Ancho de la barra
        vh.setBackground(new Color(255, 255, 255)); // Fondo de la ScrollBar
        
            UIManager.put("ScrollBar.thumb", new Color(204, 204, 204)); // Color gris para el pulgar
            UIManager.put("ScrollBar.thumbHighlight", new Color(150, 150, 255)); // Efecto de hover (más claro)
            UIManager.put("ScrollBar.thumbDarkShadow", new Color(50, 50, 150)); // Sombra oscura del pulgar
            vh.setForeground(new Color(100, 100, 255)); // Color del pulgar (esto no siempre aplica)

            // Modificar el color del "pulgar" de la ScrollBar con un renderizador
            vh.setUI(new FlatScrollBarUI() {
                protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
                    g.setColor(new Color(204, 204, 204)); // Color del pulgar (gris)
                    g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                }

                protected void paintTrack(Graphics2D g, JComponent c, Rectangle trackBounds) {
                    g.setColor(new Color(30, 30, 30)); // Color del fondo de la ScrollBar
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
    
     }
    
     
      public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
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
        panelRound1 = new UI.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cboForma = new javax.swing.JComboBox<>();
        pnlPagoTardio = new UI.PanelRound();
        btnPagoTardio = new javax.swing.JButton();
        pnlCancelar = new UI.PanelRound();
        btnCancelar = new javax.swing.JButton();
        pnlEfectivo = new UI.PanelRound();
        jLabel7 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblCambio = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        sprRecibido = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        chkFacturar = new javax.swing.JCheckBox();
        pnlBancario = new UI.PanelRound();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblTotal2 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        pnlComentarios = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        chkFacturar2 = new javax.swing.JCheckBox();
        pnlPagado = new UI.PanelRound();
        btnPagado = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnAgregarCliente = new UI.PanelRound();
        btnA = new javax.swing.JLabel();
        btnBuscar = new UI.PanelRound();
        btnB = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(250, 176, 47));
        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ingresa los datos de pago");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Forma de pago :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, -1));

        cboForma.setBackground(new java.awt.Color(250, 176, 47));
        cboForma.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        cboForma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Bancario" }));
        cboForma.setBorder(null);
        cboForma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cboForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFormaActionPerformed(evt);
            }
        });
        jPanel1.add(cboForma, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 230, -1));

        pnlPagoTardio.setBackground(new java.awt.Color(250, 176, 47));
        pnlPagoTardio.setRoundBottomLeft(60);
        pnlPagoTardio.setRoundBottomRight(60);
        pnlPagoTardio.setRoundTopLeft(60);
        pnlPagoTardio.setRoundTopRight(60);

        btnPagoTardio.setBackground(new java.awt.Color(250, 176, 47));
        btnPagoTardio.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnPagoTardio.setText("Pago contra entrega");
        btnPagoTardio.setBorder(null);
        btnPagoTardio.setBorderPainted(false);
        btnPagoTardio.setContentAreaFilled(false);
        btnPagoTardio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPagoTardio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPagoTardioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPagoTardioMouseExited(evt);
            }
        });
        btnPagoTardio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagoTardioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPagoTardioLayout = new javax.swing.GroupLayout(pnlPagoTardio);
        pnlPagoTardio.setLayout(pnlPagoTardioLayout);
        pnlPagoTardioLayout.setHorizontalGroup(
            pnlPagoTardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagoTardioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPagoTardio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPagoTardioLayout.setVerticalGroup(
            pnlPagoTardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagoTardioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPagoTardio, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(pnlPagoTardio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 190, -1));

        pnlCancelar.setBackground(new java.awt.Color(204, 204, 204));
        pnlCancelar.setRoundBottomLeft(60);
        pnlCancelar.setRoundBottomRight(60);
        pnlCancelar.setRoundTopLeft(60);
        pnlCancelar.setRoundTopRight(60);

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCancelarLayout = new javax.swing.GroupLayout(pnlCancelar);
        pnlCancelar.setLayout(pnlCancelarLayout);
        pnlCancelarLayout.setHorizontalGroup(
            pnlCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCancelarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCancelarLayout.setVerticalGroup(
            pnlCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCancelarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(pnlCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 400, -1, -1));

        pnlEfectivo.setBackground(new java.awt.Color(204, 204, 204));
        pnlEfectivo.setRoundBottomLeft(45);
        pnlEfectivo.setRoundBottomRight(45);
        pnlEfectivo.setRoundTopLeft(45);
        pnlEfectivo.setRoundTopRight(45);

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Total a pagar :");

        lblTotal.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("-");

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setText("$");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Recibido :");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setText("$");

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Cambio");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel14.setText("$");

        lblCambio.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblCambio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCambio.setText("-");

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        sprRecibido.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Facturar :");

        javax.swing.GroupLayout pnlEfectivoLayout = new javax.swing.GroupLayout(pnlEfectivo);
        pnlEfectivo.setLayout(pnlEfectivoLayout);
        pnlEfectivoLayout.setHorizontalGroup(
            pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEfectivoLayout.createSequentialGroup()
                .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlEfectivoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEfectivoLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEfectivoLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator2)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlEfectivoLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addGap(42, 42, 42)
                                .addComponent(sprRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))))
                    .addGroup(pnlEfectivoLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addGap(12, 12, 12)
                        .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(122, 122, 122))
            .addGroup(pnlEfectivoLayout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkFacturar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEfectivoLayout.setVerticalGroup(
            pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEfectivoLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTotal)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(sprRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblCambio)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlEfectivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(chkFacturar))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel1.add(pnlEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 500, 230));

        pnlBancario.setBackground(new java.awt.Color(250, 176, 47));
        pnlBancario.setRoundBottomLeft(45);
        pnlBancario.setRoundBottomRight(45);
        pnlBancario.setRoundTopLeft(45);
        pnlBancario.setRoundTopRight(45);

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Total a pagar :");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel16.setText("$");

        lblTotal2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblTotal2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal2.setText("-");

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Notas :");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        pnlComentarios.setViewportView(jTextArea1);

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Facturar :");

        javax.swing.GroupLayout pnlBancarioLayout = new javax.swing.GroupLayout(pnlBancario);
        pnlBancario.setLayout(pnlBancarioLayout);
        pnlBancarioLayout.setHorizontalGroup(
            pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBancarioLayout.createSequentialGroup()
                .addGroup(pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBancarioLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBancarioLayout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlBancarioLayout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addGroup(pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator6)
                                    .addComponent(lblTotal2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))))
                    .addGroup(pnlBancarioLayout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkFacturar2)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        pnlBancarioLayout.setVerticalGroup(
            pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBancarioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(lblTotal2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBancarioLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel18))
                    .addGroup(pnlBancarioLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBancarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(chkFacturar2))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel1.add(pnlBancario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 500, 220));

        pnlPagado.setBackground(new java.awt.Color(250, 176, 47));
        pnlPagado.setRoundBottomLeft(60);
        pnlPagado.setRoundBottomRight(60);
        pnlPagado.setRoundTopLeft(60);
        pnlPagado.setRoundTopRight(60);

        btnPagado.setBackground(new java.awt.Color(250, 176, 47));
        btnPagado.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnPagado.setText("Pagado");
        btnPagado.setBorder(null);
        btnPagado.setBorderPainted(false);
        btnPagado.setContentAreaFilled(false);
        btnPagado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPagado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPagadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPagadoMouseExited(evt);
            }
        });
        btnPagado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPagadoLayout = new javax.swing.GroupLayout(pnlPagado);
        pnlPagado.setLayout(pnlPagadoLayout);
        pnlPagadoLayout.setHorizontalGroup(
            pnlPagadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPagado, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPagadoLayout.setVerticalGroup(
            pnlPagadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPagado, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(pnlPagado, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, -1, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Cliente :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        btnAgregarCliente.setBackground(new java.awt.Color(204, 204, 204));
        btnAgregarCliente.setRoundBottomLeft(50);
        btnAgregarCliente.setRoundBottomRight(50);
        btnAgregarCliente.setRoundTopLeft(50);
        btnAgregarCliente.setRoundTopRight(50);
        btnAgregarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarClienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarClienteMouseExited(evt);
            }
        });

        btnA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar30.png"))); // NOI18N
        btnA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAgregarClienteLayout = new javax.swing.GroupLayout(btnAgregarCliente);
        btnAgregarCliente.setLayout(btnAgregarClienteLayout);
        btnAgregarClienteLayout.setHorizontalGroup(
            btnAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnA, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
        btnAgregarClienteLayout.setVerticalGroup(
            btnAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnA, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(btnAgregarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 40, 40));

        btnBuscar.setBackground(new java.awt.Color(250, 176, 47));
        btnBuscar.setRoundBottomLeft(50);
        btnBuscar.setRoundBottomRight(50);
        btnBuscar.setRoundTopLeft(50);
        btnBuscar.setRoundTopRight(50);

        btnB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar30.png"))); // NOI18N
        btnB.setToolTipText("");
        btnB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnBuscarLayout = new javax.swing.GroupLayout(btnBuscar);
        btnBuscar.setLayout(btnBuscarLayout);
        btnBuscarLayout.setHorizontalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnB, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
        btnBuscarLayout.setVerticalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 40, 40));

        lblCliente.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCliente.setText("-");
        jPanel1.add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 120, 270, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 143, 270, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        aceptado = false;
        System.out.println(idCliente);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnPagoTardioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoTardioActionPerformed
            String sel = cboForma.getSelectedItem().toString();
        if(lblCliente.getText().equals("-")){
            JOptionPane.showMessageDialog(null, "Selecciona un cliente");
        }else{
            
                try {
                    if(sel.equals("Efectivo")){
                        hacerPedidoContraEntregaEfectivo();
                        generarNota();
                        JOptionPane.showMessageDialog(null, "Pedido Generado Exitosamente, Nota guardad en la carpeta Nota de ventas dentro del escritorio");
                        aceptado = true;
                        this.dispose();
                    }else{
                        hacerPedidoContraEntregaBancario();
                        generarNota();
                        JOptionPane.showMessageDialog(null, "Pedido Generado Exitosamente, Nota guardad en la carpeta Nota de ventas dentro del escritorio");
                        aceptado = true;
                        this.dispose();
                    }
                    if(chkFacturar.isSelected() || chkFacturar2.isSelected()){
                        JOptionPane.showMessageDialog(null, "Unicamente se puede generar una factura si se paga inmediatamente");
                    }
             
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la venta");
        }}
    }//GEN-LAST:event_btnPagoTardioActionPerformed

    private void btnBMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBMouseEntered
        btnBuscar.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnBMouseEntered

    private void btnBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBMouseExited
         btnBuscar.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnBMouseExited

    private void btnAgregarClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarClienteMouseEntered
       
    }//GEN-LAST:event_btnAgregarClienteMouseEntered

    private void btnAgregarClienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarClienteMouseExited
       
    }//GEN-LAST:event_btnAgregarClienteMouseExited

    private void btnPagadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagadoMouseEntered
        pnlPagado.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnPagadoMouseEntered

    private void btnPagadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagadoMouseExited
       pnlPagado.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnPagadoMouseExited

    private void btnPagoTardioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoTardioMouseEntered
        pnlPagoTardio.setBackground(new Color(228,143,5));
    }//GEN-LAST:event_btnPagoTardioMouseEntered

    private void btnPagoTardioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoTardioMouseExited
        pnlPagoTardio.setBackground(new Color(250,176,47));
    }//GEN-LAST:event_btnPagoTardioMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        pnlCancelar.setBackground(new Color(153,153,153));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        pnlCancelar.setBackground(new Color(204,204,204));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void cboFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaActionPerformed
        String sel = cboForma.getSelectedItem().toString();
        if(sel.equals("Efectivo")){
            pnlEfectivo.setVisible(true);
            pnlBancario.setVisible(false);
        }else{
            pnlBancario.setVisible(true);
            pnlEfectivo.setVisible(false);
        }
    }//GEN-LAST:event_cboFormaActionPerformed

    private void btnBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBMouseClicked
        BuscarClientes bc = new BuscarClientes(null, true);
        bc.setVisible(true);
        if(bc.isAceptado()){
            lblCliente.setText(bc.getNombreCliente());
            idCliente = bc.getIdCliente();
            System.out.println("ID C: "+idCliente);
        }
    }//GEN-LAST:event_btnBMouseClicked

    private void btnAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAMouseClicked
        AgregarCliente ac = new AgregarCliente(null, true);
        ac.setVisible(true);
        if(ac.isAceptado()){
            try {
                Cliente c = manejoclientes.buscarClientePorId(ac.getIdCliente());
                lblCliente.setText(c.getNombre()+" "+c.getaPaterno()+" "+c.getaMaterno());
                numeroCliente = c.getNumero();
                idCliente = ac.getIdCliente();
            } catch (SQLException ex) {}  
        }
    }//GEN-LAST:event_btnAMouseClicked

    private void btnAMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAMouseEntered
        btnAgregarCliente.setBackground(new Color(153,153,153));
    }//GEN-LAST:event_btnAMouseEntered

    private void btnAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAMouseExited
         btnAgregarCliente.setBackground(new Color(204,204,204));
    }//GEN-LAST:event_btnAMouseExited

    private void btnPagadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagadoActionPerformed
        String sel = cboForma.getSelectedItem().toString();
        if(lblCliente.getText().equals("-")){
            JOptionPane.showMessageDialog(null, "Selecciona un cliente");
        }else{
            
                try {
                    if(sel.equals("Efectivo")){
                        hacerPedido();
                        generarNota();
                        JOptionPane.showMessageDialog(null, "Pedido Generado Exitosamente, Nota guardad en la carpeta Nota de ventas dentro del escritorio");
                        aceptado = true;
                        this.dispose();
                    }else{
                        hacerPedidoBancaria();
                        generarNota();
                        JOptionPane.showMessageDialog(null, "Pedido Generado Exitosamente, Nota guardad en la carpeta Nota de ventas dentro del escritorio");
                        aceptado = true;
                        this.dispose();
                    }
            
             
                    if(chkFacturar.isSelected() || chkFacturar2.isSelected()){
                        generarFactura();
                        JOptionPane.showMessageDialog(null, "Factura guardada en la carpeta FACTURAS localizada en el escritorio");
                    }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la venta");
        }}
    }//GEN-LAST:event_btnPagadoActionPerformed

private void generarFactura() throws SQLException {
    String uuid = "755AD17E-C125-43CE-B4B7-738AFC3A5FBB";
    String numeroCertificado = "20001000000100005868";
    String fecha;
    String nombreCliente;
    String rfCliente;
    String direccionCliente;
    String ciudadEstadoCp;
    String telefono;
    List<String[]> productos = new ArrayList<>();
    String totalLetra;
    String usoCFDI;
    String tipoPersona = "PERSONA FISICA";
    String subtotal;
    String descuento;
    String impuestos;
    String total;

    LocalDate fechaActual = LocalDate.now();
    LocalTime horaActual = LocalTime.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    fecha = fechaActual.format(dateFormatter) + " " + horaActual.format(timeFormatter);

    Cliente cliFac = manejoclientes.buscarClientePorIdFactura(idCliente);
    nombreCliente = cliFac.getNombre() + " " + cliFac.getaPaterno() + " " + cliFac.getaMaterno();
    rfCliente = cliFac.getRfc();
    direccionCliente = cliFac.getDireccion();
    ciudadEstadoCp = cliFac.getColEstCP();
    telefono = cliFac.getNumero();

    List<MostrarProducto> prodFac = manejoproductos.ObtenerProductosPorDetalleVenta(idVenta);
    double subtotalCalculo = 0.0;

    for (MostrarProducto producto : prodFac) {
        float cantidad = producto.getCantidadVendida();  // No redondeamos
        String unidad = producto.getUnidadMedida();
        String descripcion = producto.getDescripcion();
        float costo = producto.getCostoVenta();
        float importe = cantidad * costo;

        subtotalCalculo += importe;

        String[] fila = new String[] {
            String.format("%.2f", cantidad),
            unidad,
            descripcion,
            String.format("%.2f", costo),
            String.format("%.2f", importe)
        };
        productos.add(fila);
    }

    // Aplicar descuento si el cliente ha hecho más de 25 compras
    double calculodesc = (cliFac.getNumeroCompras() > 25) ? subtotalCalculo * 0.10 : 0.0;
    double subtotalReal = subtotalCalculo - calculodesc;
    double iva = subtotalReal * 0.16;
    double totalReal = subtotalReal + iva;

    subtotal = String.format("%.2f", subtotalCalculo);
    descuento = String.format("%.2f", calculodesc);
    impuestos = String.format("%.2f", iva);
    total = String.format("%.2f", totalReal);
    totalLetra = NumeroALetras.convertir(totalReal);

    usoCFDI = manejoventas.obtenerVentaPorId(idVenta).getFormaPago();

    DatosFactura df = new DatosFactura(
        uuid, numeroCertificado, fecha, nombreCliente, rfCliente,
        direccionCliente, ciudadEstadoCp, telefono, productos,
        totalLetra, usoCFDI, tipoPersona, subtotal, descuento, impuestos, total
    );

    GeneradorFacturaPDF gf = new GeneradorFacturaPDF();
    gf.generarFactura(df);
}
private void generarNota() throws SQLException {
    String fecha;
    List<String[]> productos = new ArrayList<>();
    String totalLetra;
    String usoCFDI;
    String tipoPersona = "PERSONA FISICA";
    String subtotal;
    String descuento;
    String impuestos;
    String total;

    LocalDate fechaActual = LocalDate.now();
    LocalTime horaActual = LocalTime.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    fecha = fechaActual.format(dateFormatter) + " " + horaActual.format(timeFormatter);

    List<MostrarProducto> prodFac = manejoproductos.ObtenerProductosPorDetalleVenta(idVenta);
    double subtotalCalculo = 0.0;

    for (MostrarProducto producto : prodFac) {
        float cantidad = producto.getCantidadVendida();  // No redondear
        String unidad = producto.getUnidadMedida();
        String descripcion = producto.getDescripcion();
        float costo = producto.getCostoVenta();
        float importe = cantidad * costo;

        subtotalCalculo += importe;

        String[] fila = new String[] {
            String.format("%.2f", cantidad),
            unidad,
            descripcion,
            String.format("%.2f", costo),
            String.format("%.2f", importe)
        };
        productos.add(fila);
    }

    // No hay descuento en nota
    double calculodesc = 0.0;
    double subtotalReal = subtotalCalculo - calculodesc;
    double iva = subtotalReal * 0.16;
    double totalReal = subtotalReal + iva;

    subtotal = String.format("%.2f", subtotalCalculo);
    descuento = String.format("%.2f", calculodesc);
    impuestos = String.format("%.2f", iva);
    total = String.format("%.2f", totalReal);
    totalLetra = NumeroALetras.convertir(totalReal);

    usoCFDI = manejoventas.obtenerVentaPorId(idVenta).getFormaPago();

    DatosNota df = new DatosNota(
        fecha, productos, totalLetra, usoCFDI,
        tipoPersona, subtotal, descuento, impuestos, total
    );

    GenerarNotaPedido gf = new GenerarNotaPedido();
    gf.generarNota(df);
}

    
    public void setListaIDsInventario(JList<Integer> listaIDs) {
    this.lstID = listaIDs;
    this.modeloListaID = (DefaultListModel<Integer>) lstID.getModel();
    }
    
    public void setListaCantidades(JList<String> listaCant) {
    this.lstCant = listaCant;
    this.modeloListaCant = (DefaultListModel<String>) lstCant.getModel();
    }

     public void hacerPedido() throws SQLException{
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaBase = java.sql.Date.valueOf(fechaActual);
        Double mt = Double.parseDouble(lblTotal.getText());
        String est = "pagado";
        int idPago;
        String nCliente = lblCliente.getText();
        
                
        String eleccion = cboForma.getSelectedItem().toString();
        if(eleccion.equals("Efectivo")){
            idPago = 1;
        }else{
            idPago = 2;
        }
        int idPag = hacerventa.insertarPago(mt, fechaBase, "completado", idPago);
        int idVen = hacerventa.insertarVenta(mt, fechaBase, idPag, idCliente);
        idVenta = idVen;
        System.out.println("id venta: "+idVen);
        
          for (int i = 0; i < modeloListaID.getSize(); i++) {
        int idProd = modeloListaID.getElementAt(i);
        int idInventario = manejoinventario.obtenerIDInventarioPorIdProducto(idProd);
        Producto prod = manejoproductos.ObtenerProductoPorid(idProd);
              System.out.println("id Inventario: "+idInventario+" y id Producto: "+idProd);
        String valorStr = modeloListaCant.getElementAt(i).replace("-", "").replace(prod.getUnidadMedida(), "").replace("s", "").trim();
        int cantidad = Integer.parseInt(valorStr);
            hacerventa.insertarDetalleVenta(idInventario, idVen, cantidad);
            boolean pedidoRealizado = manejopedidos.insertarPedido(cantidad, mt, est, "Pendiente", fechaBase, idVen, null, null); 
        System.out.println(idInventario+" "+idVen+" "+cantidad);
        if (!pedidoRealizado) {
            JOptionPane.showMessageDialog(null, "Error al insertar el pedido");
        }
        }
    }
    
    
    public void hacerPedidoBancaria() throws SQLException{
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaBase = java.sql.Date.valueOf(fechaActual);
        Double mt = Double.parseDouble(lblTotal.getText());
        String est = "pendiente";
        int idPago = 2;
        String nCliente = lblCliente.getText();
        
        if(lblCliente.getText().equals("-")){
            JOptionPane.showMessageDialog(null, "selecciona un cliente");
        }else{
        int idPag = hacerventa.insertarPago(mt, fechaBase, est, idPago);
        int idVen = hacerventa.insertarVenta(mt, fechaBase, idPag, idCliente);
        idVenta = idVen;
        System.out.println("id venta: "+idVen);
        
          for (int i = 0; i < modeloListaID.getSize(); i++) {
        int idProd = modeloListaID.getElementAt(i);
        int idInventario = manejoinventario.obtenerIDInventarioPorIdProducto(idProd);
        Producto prod = manejoproductos.ObtenerProductoPorid(idProd);
              System.out.println("id Inventario: "+idInventario+" y id Producto: "+idProd);
        String valorStr = modeloListaCant.getElementAt(i).replace("-", "").replace(prod.getUnidadMedida(), "").replace("s", "").trim();
        int cantidad = Integer.parseInt(valorStr);
        hacerventa.insertarDetalleVenta(idInventario, idVen, cantidad);
        boolean pedidoRealizado = manejopedidos.insertarPedido(cantidad, mt, est, "Pendiente", fechaBase, idVen, null, null); 
        System.out.println(idInventario+" "+idVen+" "+cantidad);
        if (!pedidoRealizado) {
            JOptionPane.showMessageDialog(null, "Error al insertar el pedido " );
        }
          }
        }
    }
    
    
     public void hacerPedidoContraEntregaEfectivo() throws SQLException{
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaBase = java.sql.Date.valueOf(fechaActual);
        Double mt = Double.parseDouble(lblTotal.getText());
        String est = "pago contra entrega";
        int idPago = 1;
        String nCliente = lblCliente.getText();
        
        if(lblCliente.getText().equals("-")){
            JOptionPane.showMessageDialog(null, "selecciona un cliente");
        }else{
        int idPag = hacerventa.insertarPago(mt, fechaBase, "pendiente", idPago);
        int idVen = hacerventa.insertarVenta(mt, fechaBase, idPag, idCliente);
        idVenta = idVen;
        System.out.println("id venta: "+idVen);
        
          for (int i = 0; i < modeloListaID.getSize(); i++) {
        int idProd = modeloListaID.getElementAt(i);
        int idInventario = manejoinventario.obtenerIDInventarioPorIdProducto(idProd);
        Producto prod = manejoproductos.ObtenerProductoPorid(idProd);
              System.out.println("id Inventario: "+idInventario+" y id Producto: "+idProd);
        String valorStr = modeloListaCant.getElementAt(i).replace("-", "").replace(prod.getUnidadMedida(), "").replace("s", "").trim();
        int cantidad = Integer.parseInt(valorStr);
        hacerventa.insertarDetalleVenta(idInventario, idVen, cantidad);
        boolean pedidoRealizado = manejopedidos.insertarPedido(cantidad, mt, est, "Pendiente", fechaBase, idVen, null, null);
        System.out.println(idInventario+" "+idVen+" "+cantidad);
        if (!pedidoRealizado) {
            JOptionPane.showMessageDialog(null, "Error al insertar el pedido " );
        }
          }
        }
    }
    
    
    
    public void hacerPedidoContraEntregaBancario() throws SQLException{
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaBase = java.sql.Date.valueOf(fechaActual);
        Double mt = Double.parseDouble(lblTotal.getText());
        String est = "pago contra entrega";
        int idPago = 2;
        String nCliente = lblCliente.getText();
        
        if(lblCliente.getText().equals("-")){
            JOptionPane.showMessageDialog(null, "selecciona un cliente");
        }else{
        int idPag = hacerventa.insertarPago(mt, fechaBase, "pendiente", idPago);
        int idVen = hacerventa.insertarVenta(mt, fechaBase, idPag, idCliente);
        idVenta = idVen;
        System.out.println("id venta: "+idVen);
        
          for (int i = 0; i < modeloListaID.getSize(); i++) {
        int idProd = modeloListaID.getElementAt(i);
        int idInventario = manejoinventario.obtenerIDInventarioPorIdProducto(idProd);
        Producto prod = manejoproductos.ObtenerProductoPorid(idProd);
              System.out.println("id Inventario: "+idInventario+" y id Producto: "+idProd);
        String valorStr = modeloListaCant.getElementAt(i).replace("-", "").replace(prod.getUnidadMedida(), "").replace("s", "").trim();
        int cantidad = Integer.parseInt(valorStr);
        hacerventa.insertarDetalleVenta(idInventario, idVen, cantidad);
        boolean pedidoRealizado = manejopedidos.insertarPedido(cantidad, mt, est, "Pendiente", fechaBase, idVen, null, null); // o alguna fecha válida
        System.out.println(idInventario+" "+idVen+" "+cantidad);
        if (!pedidoRealizado) {
            JOptionPane.showMessageDialog(null, "Error al insertar el pedido " );
        }
          }
        }
    }
    
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
            java.util.logging.Logger.getLogger(PagarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PagarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PagarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PagarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PagarPedido dialog = new PagarPedido(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnA;
    private UI.PanelRound btnAgregarCliente;
    private javax.swing.JLabel btnB;
    private UI.PanelRound btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnPagado;
    private javax.swing.JButton btnPagoTardio;
    private javax.swing.JComboBox<String> cboForma;
    private javax.swing.JCheckBox chkFacturar;
    private javax.swing.JCheckBox chkFacturar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblCambio;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotal2;
    private UI.PanelRound panelRound1;
    private UI.PanelRound pnlBancario;
    private UI.PanelRound pnlCancelar;
    private javax.swing.JScrollPane pnlComentarios;
    private UI.PanelRound pnlEfectivo;
    private UI.PanelRound pnlPagado;
    private UI.PanelRound pnlPagoTardio;
    private javax.swing.JSpinner sprRecibido;
    // End of variables declaration//GEN-END:variables
}
