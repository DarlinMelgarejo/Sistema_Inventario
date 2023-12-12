package Formularios;

import Clases.Usuario;
import com.formdev.flatlaf.FlatLightLaf;
import ConexionBD.LoginDAO;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
/**
 * @author DARLIN
 */
public class FrameLogin extends javax.swing.JFrame {
    
    public FrameLogin() {
        initComponents();
        this.setTitle("Iniciar Sesión");
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        this.setLocationRelativeTo(null);
        //this.setExtendedState(FrameLogin.MAXIMIZED_BOTH);
        nombreUsuario.requestFocus();
    }
    
    private void logear(){
        String nombreIngresado = nombreUsuario.getText();
        String contraseñaIngresada = String.valueOf(contraseñaUsuario.getPassword());
        
        if(!"".equals(nombreIngresado) && !"".equals(contraseñaIngresada)){
            Usuario usuario = new Usuario();
            LoginDAO login = new LoginDAO();
            usuario = login.usuario(nombreIngresado, contraseñaIngresada);
            if(usuario.getNombreUsuario() != null && usuario.getContraseña() != null){
                FramePrincipal fp = new FramePrincipal(usuario);
                fp.setVisible(true);
                this.dispose();
            }else {
                JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos");
                nombreUsuario.setText("");
                contraseñaUsuario.setText("");
                nombreUsuario.requestFocus();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        etiquetaLogo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JTextField();
        contraseñaUsuario = new javax.swing.JPasswordField();
        botonIniciar = new javax.swing.JButton();
        etiquetaRegistrarse = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(181, 12, 97));

        panelLogin.setBackground(new java.awt.Color(255, 255, 255));
        panelLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 16)); // NOI18N
        jLabel1.setText("Usuario:");
        panelLogin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        etiquetaLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo-clinica.png"))); // NOI18N
        panelLogin.add(etiquetaLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 16)); // NOI18N
        jLabel2.setText("Contraseña:");
        panelLogin.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        nombreUsuario.setFont(new java.awt.Font("Microsoft YaHei", 0, 16)); // NOI18N
        panelLogin.add(nombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 300, 40));

        contraseñaUsuario.setFont(new java.awt.Font("Microsoft YaHei", 0, 16)); // NOI18N
        contraseñaUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contraseñaUsuarioKeyPressed(evt);
            }
        });
        panelLogin.add(contraseñaUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 300, 40));

        botonIniciar.setBackground(new java.awt.Color(181, 12, 97));
        botonIniciar.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        botonIniciar.setForeground(new java.awt.Color(255, 255, 255));
        botonIniciar.setText("Iniciar Sesión");
        botonIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIniciarActionPerformed(evt);
            }
        });
        panelLogin.add(botonIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 300, 40));

        etiquetaRegistrarse.setBackground(new java.awt.Color(255, 255, 255));
        etiquetaRegistrarse.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 16)); // NOI18N
        etiquetaRegistrarse.setForeground(new java.awt.Color(181, 12, 97));
        etiquetaRegistrarse.setText("Registrarse");
        etiquetaRegistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaRegistrarseMouseClicked(evt);
            }
        });
        panelLogin.add(etiquetaRegistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(467, Short.MAX_VALUE)
                .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(443, 443, 443))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
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
    }// </editor-fold>//GEN-END:initComponents

    private void botonIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarActionPerformed
        logear();
    }//GEN-LAST:event_botonIniciarActionPerformed

    private void contraseñaUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contraseñaUsuarioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            botonIniciar.doClick();
        }
    }//GEN-LAST:event_contraseñaUsuarioKeyPressed

    private void etiquetaRegistrarseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaRegistrarseMouseClicked
        FrameRegistrar fr = new FrameRegistrar();
        fr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_etiquetaRegistrarseMouseClicked
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo al inicializar FLATLAF");
            System.out.println("Fallo al inicializar FlatLaf");
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameLogin().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonIniciar;
    private javax.swing.JPasswordField contraseñaUsuario;
    private javax.swing.JLabel etiquetaLogo;
    private javax.swing.JLabel etiquetaRegistrarse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nombreUsuario;
    private javax.swing.JPanel panelLogin;
    // End of variables declaration//GEN-END:variables
}
