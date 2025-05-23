/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import ManejoTablas.ManejoUsuarios;
import Modelo.Usuario;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



/**
 *
 * @author salin
 */
public class Loggin extends javax.swing.JFrame {

    private boolean e1,e2;
    private Usuario usuario;
    private ManejoUsuarios mu;
    /**
     * Creates new form Loggin
     */
    public Loggin() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo6.png")).getImage());
        e1 = false;
        e2 = false;
        mu = new ManejoUsuarios();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFondo = new javax.swing.JPanel();
        panelRound1 = new UI.PanelRound();
        jLabel3 = new javax.swing.JLabel();
        txtobtUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();
        txtobtContra = new javax.swing.JPasswordField();
        btnVerContra = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de Sesión");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFondo.setPreferredSize(new java.awt.Dimension(1280, 720));
        pnlFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(248, 248, 248));
        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);
        panelRound1.setRoundTopLeft(50);
        panelRound1.setRoundTopRight(50);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setText("Contraseña :");

        txtobtUsuario.setBackground(new java.awt.Color(251, 218, 160));
        txtobtUsuario.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        txtobtUsuario.setForeground(new java.awt.Color(102, 102, 102));
        txtobtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtobtUsuario.setText("Ingrese su nombre de usuario");
        txtobtUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtobtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtobtUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtobtUsuarioMousePressed(evt);
            }
        });
        txtobtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtobtUsuarioKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setText("Usuario :");

        btnIngresar.setBackground(new java.awt.Color(0, 0, 0));
        btnIngresar.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setText("Ingresar");
        btnIngresar.setBorder(null);
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        txtobtContra.setBackground(new java.awt.Color(251, 218, 160));
        txtobtContra.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        txtobtContra.setForeground(new java.awt.Color(102, 102, 102));
        txtobtContra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtobtContra.setText("Ingresa tu contraseña");
        txtobtContra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtobtContra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtobtContraMouseClicked(evt);
            }
        });
        txtobtContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtobtContraActionPerformed(evt);
            }
        });
        txtobtContra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtobtContraKeyPressed(evt);
            }
        });

        btnVerContra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ojoA.png"))); // NOI18N
        btnVerContra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btnVerContra.setContentAreaFilled(false);
        btnVerContra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerContraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtobtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(txtobtContra, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVerContra, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
            .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelRound1Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jLabel4)
                    .addContainerGap(320, Short.MAX_VALUE)))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(txtobtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnVerContra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtobtContra, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
            .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelRound1Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jLabel4)
                    .addContainerGap(365, Short.MAX_VALUE)))
        );

        pnlFondo.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 140, 430, 420));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo6.png"))); // NOI18N
        pnlFondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 500, 490));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel2.setText("Inicio de Sesión");
        pnlFondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, -1, -1));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo2.png"))); // NOI18N
        Fondo.setPreferredSize(new java.awt.Dimension(1280, 720));
        pnlFondo.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(pnlFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        String nombreUsuario = txtobtUsuario.getText();
        String contraseña = txtobtContra.getText();
        
        if(nombreUsuario.equals("Ingrese su nombre de usuario") && contraseña.equals("Ingresa tu contraseña")){
            JOptionPane.showMessageDialog(null, "Porfavor, rellena los campos");
            txtobtUsuario.setText("");
            txtobtContra.setText("");
            e1 = false; e2 =false;
        }else if(nombreUsuario.isEmpty() && contraseña.isEmpty()){
            JOptionPane.showMessageDialog(null, "No puede dejar los campos vacios");
        }else if(nombreUsuario.equals("Ingrese su nombre de usuario")){
            JOptionPane.showMessageDialog(null, "Ingrese un usuario");
            txtobtUsuario.setText("");
        }else if(contraseña.equals("Ingresa tu contraseña")){
            JOptionPane.showMessageDialog(null, "Ingrese una contraseña");
            txtobtContra.setText("");
        }else if(nombreUsuario.isEmpty()){
            JOptionPane.showMessageDialog(null, "No pude dejar espacios en blanco, ingrese un usuario");
        }else if(contraseña.isEmpty()){
            JOptionPane.showMessageDialog(null, "No pude dejar espacios en blanco, ingrese una contraseña");
        }
       
        else{
        
        try {
            usuario = mu.getUsuarios(nombreUsuario);
                } catch (SQLException ex) {
            Logger.getLogger(Loggin.class.getName()).log(Level.SEVERE, null, ex);
        }    
            
            
        if(usuario != null && contraseña.equals(usuario.getContra())) {
        inicio i = new inicio();
        i.cargarDatos(1);
        i.setVisible(true);
        this.dispose();
        
        }else{
            JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
            txtobtUsuario.setText("");
            txtobtContra.setText("");
        }}
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void txtobtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtobtUsuarioMouseClicked
        if(!e1){
            txtobtUsuario.setText("");
            txtobtUsuario.setForeground(Color.BLACK);
            e1 = true;
        }
    }//GEN-LAST:event_txtobtUsuarioMouseClicked

    private void txtobtContraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtobtContraMouseClicked
        if(!e2){
            txtobtContra.setText("");
            txtobtContra.setForeground(Color.BLACK);
            e2 = true;
        }
    }//GEN-LAST:event_txtobtContraMouseClicked

    private void btnVerContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerContraActionPerformed
        if(btnVerContra.isSelected()){
        txtobtContra.setEchoChar((char)0);}
      else{
          txtobtContra.setEchoChar('*');
      }
    }//GEN-LAST:event_btnVerContraActionPerformed

    private void txtobtContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtobtContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtobtContraActionPerformed

    private void txtobtContraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtobtContraKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            String nombreUsuario = txtobtUsuario.getText();
            String contraseña = txtobtContra.getText();
        
        if(nombreUsuario.equals("Ingrese su nombre de usuario") && contraseña.equals("Ingresa tu contraseña")){
            JOptionPane.showMessageDialog(null, "Porfavor, rellena los campos");
            txtobtUsuario.setText("");
            txtobtContra.setText("");
            e1 = false; e2 =false;
        }else if(nombreUsuario.isEmpty() && contraseña.isEmpty()){
            JOptionPane.showMessageDialog(null, "No puede dejar los campos vacios");
        }else if(nombreUsuario.equals("Ingrese su nombre de usuario")){
            JOptionPane.showMessageDialog(null, "Ingrese un usuario");
            txtobtUsuario.setText("");
        }else if(contraseña.equals("Ingresa tu contraseña")){
            JOptionPane.showMessageDialog(null, "Ingrese una contraseña");
            txtobtContra.setText("");
        }else if(nombreUsuario.isEmpty()){
            JOptionPane.showMessageDialog(null, "No pude dejar espacios en blanco, ingrese un usuario");
        }else if(contraseña.isEmpty()){
            JOptionPane.showMessageDialog(null, "No pude dejar espacios en blanco, ingrese una contraseña");
        }
       
        else{
        
        try {
            usuario = mu.getUsuarios(nombreUsuario);
                } catch (SQLException ex) {
            Logger.getLogger(Loggin.class.getName()).log(Level.SEVERE, null, ex);
        }    
            
            
        if(usuario != null && contraseña.equals(usuario.getContra())) {
        inicio i = new inicio();
        i.cargarDatos(1);
        i.setVisible(true);
        this.dispose();
        
        }else{
            JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
            txtobtUsuario.setText("");
            txtobtContra.setText("");
        }}
        }
    }//GEN-LAST:event_txtobtContraKeyPressed

    private void txtobtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtobtUsuarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            txtobtContra.setText("");
            txtobtContra.requestFocus();
            txtobtContra.setCaretPosition(0);
        }
    }//GEN-LAST:event_txtobtUsuarioKeyPressed

    private void txtobtUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtobtUsuarioMousePressed
        // TODO add your handling code here:
        txtobtUsuario.setText("");
    }//GEN-LAST:event_txtobtUsuarioMousePressed

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
            java.util.logging.Logger.getLogger(Loggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Loggin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JToggleButton btnVerContra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private UI.PanelRound panelRound1;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPasswordField txtobtContra;
    private javax.swing.JTextField txtobtUsuario;
    // End of variables declaration//GEN-END:variables
}
