package Formularios;

import Clases.Usuario;
import com.formdev.flatlaf.FlatLightLaf;
import ConexionBD.LoginDAO;
import ConexionBD.UsuarioDAO;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * @author DARLIN
 */
public class FrameRegistrar extends javax.swing.JFrame {
    
    public FrameRegistrar() {
        initComponents();
        this.setTitle("Registrar Nuevo Usuario");
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        this.setLocationRelativeTo(null);
        //this.setExtendedState(FrameRegistrar.MAXIMIZED_BOTH);
        deshabilitarCampos();
    }

    private void deshabilitarCampos(){
        usuarioAdministradorIngresado.requestFocus();
        nombreIngresado.setEnabled(false);
        apellidoIngresado.setEnabled(false);
        cargoPuestoIngresado.setEnabled(false);
        nombreUsuarioIngresado.setEnabled(false);
        contraseñaIngresada.setEnabled(false);
        confirmarContraseñaIngresada.setEnabled(false);
        botonRegistrar.setEnabled(false);
    }
    
    private void habilitarCamposRegistrar(){
        usuarioAdministradorIngresado.setEnabled(false);
        usuarioAdministradorIngresado.setText("");
        contraseñaAdministradorIngresado.setEnabled(false);
        contraseñaAdministradorIngresado.setText("");
        botonConfirmar.setEnabled(false);
        nombreIngresado.setEnabled(true);
        nombreIngresado.requestFocus();
        apellidoIngresado.setEnabled(true);
        cargoPuestoIngresado.setEnabled(true);
        nombreUsuarioIngresado.setEnabled(true);
        contraseñaIngresada.setEnabled(true);
        confirmarContraseñaIngresada.setEnabled(true);
        botonRegistrar.setEnabled(true);
    }
    
    private void confirmarAdministrador(){
        String nombreAdministrador = usuarioAdministradorIngresado.getText();
        String contraseñaAdministrador = String.valueOf(contraseñaAdministradorIngresado.getPassword());
        
        if(!"".equals(nombreAdministrador) && !"".equals(contraseñaAdministrador)){
            Usuario usuario = new Usuario();
            LoginDAO login = new LoginDAO();
            usuario = login.usuario(nombreAdministrador, contraseñaAdministrador);
            if(usuario.getNombreUsuario() != null && usuario.getContraseña() != null && usuario.getPuesto().equals("Administrador")){
                JOptionPane.showMessageDialog(null, "Usuario Correcto", "Confirmar Acceso", JOptionPane.INFORMATION_MESSAGE);
                habilitarCamposRegistrar();
            }else {
                JOptionPane.showMessageDialog(null, "Usuario no autorizado", "Confirmar Acceso", JOptionPane.ERROR_MESSAGE);
                usuarioAdministradorIngresado.setText("");
                contraseñaAdministradorIngresado.setText("");
                usuarioAdministradorIngresado.requestFocus();
            }
        }
    }
    
    private void regitrarUsuario(){
        String nombre = nombreIngresado.getText();
        String apellidos = apellidoIngresado.getText();
        String puestoCargo = cargoPuestoIngresado.getText();
        String nombreUsuario = nombreUsuarioIngresado.getText();
        String contraseña = String.valueOf(contraseñaIngresada.getPassword());
        String confirmarContraseña = String.valueOf(confirmarContraseñaIngresada.getPassword());
        
        
        if(!"".equals(nombre) && !"".equals(apellidos) && !"".equals(puestoCargo) && !"".equals(nombreUsuario) && !"".equals(contraseña) && !"".equals(confirmarContraseña)){
            if(contraseña.equals(confirmarContraseña)){
                Usuario usuario = new Usuario();
                UsuarioDAO registro = new UsuarioDAO();
                usuario.setNombres(nombre);
                usuario.setApellidos(apellidos);
                usuario.setPuesto(puestoCargo);
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setContraseña(contraseña);
                registro.RegistrarUsuario(usuario);

                JOptionPane.showMessageDialog(null, "Usuario Registrado Correctamente", "Registrar Usuario", JOptionPane.INFORMATION_MESSAGE);

                FrameLogin fl = new FrameLogin();
                fl.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "La contraseña no es la misma cuando quiere confirmar", "Registrar Usuario", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos", "Registrar Usuario", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        usuarioAdministradorIngresado = new javax.swing.JTextField();
        contraseñaAdministradorIngresado = new javax.swing.JPasswordField();
        botonConfirmar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        nombreIngresado = new javax.swing.JTextField();
        apellidoIngresado = new javax.swing.JTextField();
        cargoPuestoIngresado = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        nombreUsuarioIngresado = new javax.swing.JTextField();
        contraseñaIngresada = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        confirmarContraseñaIngresada = new javax.swing.JPasswordField();
        botonRegistrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botonVolverLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(181, 12, 97));

        jPanel2.setBackground(new java.awt.Color(255, 255, 254));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 1, 24)); // NOI18N
        jLabel2.setText("Confirme el Usuario del Administrador para registrar un nuevo usuario:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel3.setText("Usuario Administrador:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel4.setText("Contraseña Administrador:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, -1, -1));

        usuarioAdministradorIngresado.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jPanel2.add(usuarioAdministradorIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 230, 30));

        contraseñaAdministradorIngresado.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        contraseñaAdministradorIngresado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contraseñaAdministradorIngresadoKeyPressed(evt);
            }
        });
        jPanel2.add(contraseñaAdministradorIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, 230, 30));

        botonConfirmar.setBackground(new java.awt.Color(181, 12, 97));
        botonConfirmar.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        botonConfirmar.setForeground(new java.awt.Color(255, 255, 254));
        botonConfirmar.setText("Confirmar");
        botonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConfirmarActionPerformed(evt);
            }
        });
        jPanel2.add(botonConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 120, 140, 30));

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 1, 20)); // NOI18N
        jLabel5.setText("Ingrese los datos del nuevo Usuario:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel6.setText("Nombres:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel7.setText("Apellidos:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, -1, -1));

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel8.setText("Cargo/Puesto:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 260, -1, -1));

        nombreIngresado.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jPanel2.add(nombreIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 230, 30));

        apellidoIngresado.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jPanel2.add(apellidoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 230, 30));

        cargoPuestoIngresado.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jPanel2.add(cargoPuestoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, 230, 30));

        jLabel9.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel9.setText("Nombre Usuario:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel10.setText("Contraseña:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, -1, -1));

        nombreUsuarioIngresado.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jPanel2.add(nombreUsuarioIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 230, 30));

        contraseñaIngresada.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jPanel2.add(contraseñaIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, 230, 30));

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        jLabel11.setText("Confirmar Contraseña:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, -1, -1));

        confirmarContraseñaIngresada.setFont(new java.awt.Font("Microsoft YaHei", 0, 18)); // NOI18N
        confirmarContraseñaIngresada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmarContraseñaIngresadaKeyPressed(evt);
            }
        });
        jPanel2.add(confirmarContraseñaIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 410, 230, 30));

        botonRegistrar.setBackground(new java.awt.Color(181, 12, 97));
        botonRegistrar.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        botonRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        botonRegistrar.setText("Registrar");
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });
        jPanel2.add(botonRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 480, 130, -1));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 254));
        jLabel1.setText("REGISTRAR NUEVO USUARIO");

        botonVolverLogin.setBackground(new java.awt.Color(255, 255, 254));
        botonVolverLogin.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        botonVolverLogin.setText("Volver a Login");
        botonVolverLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(botonVolverLogin)
                .addGap(203, 203, 203)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(botonVolverLogin)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
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

    private void botonVolverLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverLoginActionPerformed
        FrameLogin fl = new FrameLogin();
        fl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botonVolverLoginActionPerformed

    private void botonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConfirmarActionPerformed
        confirmarAdministrador();
    }//GEN-LAST:event_botonConfirmarActionPerformed

    private void contraseñaAdministradorIngresadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contraseñaAdministradorIngresadoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            botonConfirmar.doClick();
        }
    }//GEN-LAST:event_contraseñaAdministradorIngresadoKeyPressed

    private void confirmarContraseñaIngresadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmarContraseñaIngresadaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            botonRegistrar.doClick();
        }
    }//GEN-LAST:event_confirmarContraseñaIngresadaKeyPressed

    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
        regitrarUsuario();
    }//GEN-LAST:event_botonRegistrarActionPerformed

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
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
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
    private javax.swing.JTextField apellidoIngresado;
    private javax.swing.JButton botonConfirmar;
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JButton botonVolverLogin;
    private javax.swing.JTextField cargoPuestoIngresado;
    private javax.swing.JPasswordField confirmarContraseñaIngresada;
    private javax.swing.JPasswordField contraseñaAdministradorIngresado;
    private javax.swing.JPasswordField contraseñaIngresada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField nombreIngresado;
    private javax.swing.JTextField nombreUsuarioIngresado;
    private javax.swing.JTextField usuarioAdministradorIngresado;
    // End of variables declaration//GEN-END:variables
}
