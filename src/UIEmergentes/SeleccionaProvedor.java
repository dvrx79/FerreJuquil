/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package UIEmergentes;

import ManejoTablas.ManejoProductos;
import ManejoTablas.ManejoProveedores;
import Modelo.MostrarProducto;
import Modelo.MostrarProveedor;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import com.formdev.flatlaf.ui.FlatScrollBarUI;

/**
 *
 * @author yael
 */
public class SeleccionaProvedor extends javax.swing.JDialog {
  private ManejoProveedores manejoproveedores = new ManejoProveedores();
    private ManejoProductos manejoproductos = new ManejoProductos();
    private MostrarProveedor proveedor;

    public SeleccionaProvedor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        proveedor = null;
        
        // Configuración básica
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo6.png")).getImage());
        setLocationRelativeTo(parent);
        
        // Configuración de la tabla
        configurarTabla();
        
        // Cargar datos iniciales
        cargarProveedores();
    }
    
    private void configurarTabla() {
        // Configuración visual de la tabla
        tablaProveedores.setRowHeight(30);
        panelTabla.getViewport().setBackground(Color.WHITE);
        
        // Configurar header
        JTableHeader header = tablaProveedores.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.BOLD, 16));
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Renderer para header
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.BLACK);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaProveedores.getTableHeader().setDefaultRenderer(headerRenderer);
        
        // Renderer para celdas
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? new Color(204, 204, 255) : Color.WHITE);
                c.setForeground(Color.BLACK);
                if (column == 1 || column == 2) {
                    setHorizontalAlignment(JLabel.CENTER);
                } else {
                    setHorizontalAlignment(JLabel.LEFT);
                }
                return c;
            }
        };
        
        // Aplicar renderer a todas las columnas
        for (int i = 0; i < tablaProveedores.getColumnCount(); i++) {
            tablaProveedores.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        
        // Configurar anchos de columnas
        tablaProveedores.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaProveedores.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaProveedores.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaProveedores.getColumnModel().getColumn(3).setPreferredWidth(250);
        
        // Configurar scrollbars
        configurarScrollBars();
    }
    
    private void configurarScrollBars() {
        // Configuración común para scrollbars
        UIManager.put("ScrollBar.thumb", new Color(204, 204, 204));
        UIManager.put("ScrollBar.track", new Color(30, 30, 30));
        
        // Configurar scrollbar vertical
        JScrollBar vs = panelTabla.getVerticalScrollBar();
        vs.setUI(new FlatScrollBarUI());
        vs.setPreferredSize(new Dimension(12, 0));
        
        // Configurar scrollbar horizontal
        JScrollBar hs = panelTabla.getHorizontalScrollBar();
        hs.setUI(new FlatScrollBarUI());
        hs.setPreferredSize(new Dimension(0, 12));
    }
    
private void cargarProveedores() {
    try {
        DefaultTableModel modelo = (DefaultTableModel) tablaProveedores.getModel();
        modelo.setRowCount(0);
        
        List<MostrarProveedor> proveedores = manejoproveedores.obtenerProveedores();
        
        for (MostrarProveedor prov : proveedores) {
            modelo.addRow(new Object[]{
                prov.getNombre(),
                prov.getTelefono(),
                prov.getCorreo(),
                prov.getDireccion(),
                prov.getEstado() 
            });
        }
        
    
        if (modelo.getColumnCount() < 5) {
            modelo.addColumn("Estado");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error al cargar proveedores: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelRound1 = new UI.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        btnC2 = new UI.PanelRound();
        btnCan = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panelTabla = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(250, 176, 47));
        panelRound1.setRoundBottomLeft(45);
        panelRound1.setRoundBottomRight(45);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Selecciona un nuevo proveedor");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jPanel1.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, -1));

        btnC2.setBackground(new java.awt.Color(250, 176, 47));
        btnC2.setRoundBottomLeft(40);
        btnC2.setRoundBottomRight(40);
        btnC2.setRoundTopLeft(40);
        btnC2.setRoundTopRight(40);
        btnC2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnC2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnC2MouseExited(evt);
            }
        });

        btnCan.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btnCan.setText("ACEPTAR");
        btnCan.setBorder(null);
        btnCan.setContentAreaFilled(false);
        btnCan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCanMouseExited(evt);
            }
        });
        btnCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnC2Layout = new javax.swing.GroupLayout(btnC2);
        btnC2.setLayout(btnC2Layout);
        btnC2Layout.setHorizontalGroup(
            btnC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnC2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnCan)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        btnC2Layout.setVerticalGroup(
            btnC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnC2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCan, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(btnC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, -1, -1));

        jButton2.setBackground(new java.awt.Color(0, 102, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Nuevo Proveedor");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 210, -1));

        tablaProveedores.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Numero", "Correo", "Dirección", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProveedores.setGridColor(new java.awt.Color(0, 0, 0));
        tablaProveedores.setRowSelectionAllowed(false);
        tablaProveedores.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tablaProveedores.setShowGrid(false);
        tablaProveedores.setShowHorizontalLines(true);
        tablaProveedores.setShowVerticalLines(true);
        tablaProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProveedoresMouseClicked(evt);
            }
        });
        panelTabla.setViewportView(tablaProveedores);

        jPanel1.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 620, 160));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnCanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCanMouseEntered
        //[250,176,47]
    }//GEN-LAST:event_btnCanMouseEntered

    private void btnCanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCanMouseExited

    }//GEN-LAST:event_btnCanMouseExited

    private void btnCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanActionPerformed
        if (proveedor == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un proveedor", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            this.dispose();
        }
    }//GEN-LAST:event_btnCanActionPerformed

    private void btnC2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnC2MouseEntered

    }//GEN-LAST:event_btnC2MouseEntered

    private void btnC2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnC2MouseExited

    }//GEN-LAST:event_btnC2MouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        java.awt.Frame parent = (java.awt.Frame) this.getParent();
        AgregarProvedor agregarProv = new AgregarProvedor(parent, true);
        agregarProv.setLocationRelativeTo(this);
        agregarProv.setVisible(true);
        
        cargarProveedores();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tablaProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProveedoresMouseClicked
        int fila = tablaProveedores.getSelectedRow();
        if (fila >= 0) {
            String nombre = tablaProveedores.getValueAt(fila, 0).toString();
            String telefono = tablaProveedores.getValueAt(fila, 1).toString();
            String correo = tablaProveedores.getValueAt(fila, 2).toString();
            String direccion = tablaProveedores.getValueAt(fila, 3).toString();
            String estado = tablaProveedores.getValueAt(fila, 4).toString();
            
            proveedor = new MostrarProveedor(nombre, telefono, correo, direccion,estado);
        }
    }
public MostrarProveedor getProveedorSeleccionado() {
        return this.proveedor;
    }//GEN-LAST:event_tablaProveedoresMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SeleccionaProvedor dialog = new SeleccionaProvedor(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.PanelRound btnC2;
    private javax.swing.JButton btnCan;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private UI.PanelRound panelRound1;
    private javax.swing.JScrollPane panelTabla;
    private javax.swing.JTable tablaProveedores;
    // End of variables declaration//GEN-END:variables
}
