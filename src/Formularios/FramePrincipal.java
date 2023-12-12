package Formularios;

import Clases.Usuario;
import ConexionBD.InventarioDAO;
import ConexionBD.ProductoDAO;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author DARLIN
 */
public class FramePrincipal extends javax.swing.JFrame {
    int cantidadProductos = 0;
    ProductoDAO productoSQL = new ProductoDAO();
    InventarioDAO inventarioSQL = new InventarioDAO();
    
    public FramePrincipal() {
        initComponents();
        this.setTitle("Sistema de Inventario");
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        this.setLocationRelativeTo(null);
    }
    
    public FramePrincipal(Usuario usuario){
        if(usuario.getPuesto().equals("Administrador")){
            initComponents();
            this.setTitle("Sistema de Inventario");
            this.setResizable(false);
            this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
            this.setLocationRelativeTo(null);
            etiquetaUsuario.setText(usuario.getNombreUsuario());
            
            
        } else {
            initComponents();
            this.setTitle("Sistema de Inventario");
            this.setResizable(false);
            this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
            this.setLocationRelativeTo(null);
            etiquetaUsuario.setText(usuario.getNombreUsuario());
            botonUsuarios.setEnabled(false);
        }
    }
    private void mostrarPanel(JPanel panel){
        panel.setSize(1000, 550);
        panel.setLocation(0, 0);
        
        contenedor.removeAll();
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }
    
    private void verificarFechaVencimiento(){
        cantidadProductos = productoSQL.CantidadVencimientoProductos();
        if(cantidadProductos > 0){
            JOptionPane.showMessageDialog(null, "Se encontraron productos con fecha de vencimiento cercana. Revise los productos al darle click al boton 'Alertas' en 'Productos'\nCantidad: "+cantidadProductos, "Ingresar Datos", JOptionPane.WARNING_MESSAGE);
            FrameVencimiento fv = new FrameVencimiento();
            fv.setVisible(true);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelLogo = new javax.swing.JPanel();
        etiquetaLogo = new javax.swing.JLabel();
        etiquetaUsuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        botonProductos = new javax.swing.JButton();
        botonEntradas = new javax.swing.JButton();
        botonSalidas = new javax.swing.JButton();
        botonInventario = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        contenedor = new javax.swing.JDesktopPane();
        jButton1 = new javax.swing.JButton();
        botonUsuarios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(181, 12, 97));
        panelPrincipal.setPreferredSize(new java.awt.Dimension(1270, 670));

        panelLogo.setBackground(new java.awt.Color(255, 255, 254));

        etiquetaLogo.setBackground(new java.awt.Color(255, 255, 254));
        etiquetaLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/jesedsalud.png"))); // NOI18N

        etiquetaUsuario.setFont(new java.awt.Font("Microsoft YaHei", 1, 16)); // NOI18N
        etiquetaUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaUsuario.setText("Usuario");

        javax.swing.GroupLayout panelLogoLayout = new javax.swing.GroupLayout(panelLogo);
        panelLogo.setLayout(panelLogoLayout);
        panelLogoLayout.setHorizontalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
            .addComponent(etiquetaUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLogoLayout.setVerticalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogoLayout.createSequentialGroup()
                .addComponent(etiquetaLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiquetaUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sistema de Inventario");

        botonProductos.setBackground(new java.awt.Color(255, 255, 254));
        botonProductos.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        botonProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/paquete.png"))); // NOI18N
        botonProductos.setText("Productos");
        botonProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProductosActionPerformed(evt);
            }
        });

        botonEntradas.setBackground(new java.awt.Color(255, 255, 254));
        botonEntradas.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        botonEntradas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entrar.png"))); // NOI18N
        botonEntradas.setText("Entradas");
        botonEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEntradasActionPerformed(evt);
            }
        });

        botonSalidas.setBackground(new java.awt.Color(255, 255, 254));
        botonSalidas.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        botonSalidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/venta.png"))); // NOI18N
        botonSalidas.setText("Salidas");
        botonSalidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalidasActionPerformed(evt);
            }
        });

        botonInventario.setBackground(new java.awt.Color(255, 255, 254));
        botonInventario.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        botonInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/inventario.png"))); // NOI18N
        botonInventario.setText("Inventario");
        botonInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInventarioActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MENÚ PRINCIPAL");

        contenedor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(255, 255, 254));
        jButton1.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jButton1.setText("Cerrar Sesión");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        botonUsuarios.setBackground(new java.awt.Color(255, 255, 254));
        botonUsuarios.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        botonUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_login.png"))); // NOI18N
        botonUsuarios.setText("Usuarios");
        botonUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonEntradas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonSalidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(panelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 1, Short.MAX_VALUE))))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(39, 39, 39)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(jLabel1)
                        .addGap(227, 227, 227)
                        .addComponent(jButton1))
                    .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(botonProductos)
                        .addGap(18, 18, 18)
                        .addComponent(botonEntradas)
                        .addGap(18, 18, 18)
                        .addComponent(botonSalidas)
                        .addGap(18, 18, 18)
                        .addComponent(botonInventario)
                        .addGap(18, 18, 18)
                        .addComponent(botonUsuarios))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jButton1)))
                        .addGap(21, 21, 21)
                        .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProductosActionPerformed
        PanelProductos panelProductos = new PanelProductos();
        mostrarPanel(panelProductos);
        
    }//GEN-LAST:event_botonProductosActionPerformed

    private void botonEntradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEntradasActionPerformed
        PanelEntradas panelEntradas = new PanelEntradas();
        mostrarPanel(panelEntradas);
    }//GEN-LAST:event_botonEntradasActionPerformed

    private void botonSalidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalidasActionPerformed
        PanelSalidas panelSalidas = new PanelSalidas();
        mostrarPanel(panelSalidas);
    }//GEN-LAST:event_botonSalidasActionPerformed

    private void botonInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInventarioActionPerformed
        PanelInventario panelInventario = new PanelInventario();
        mostrarPanel(panelInventario);
    }//GEN-LAST:event_botonInventarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FrameLogin fl = new FrameLogin();
        fl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonUsuariosActionPerformed
        PanelUsuarios panelUsuarios = new PanelUsuarios();
        mostrarPanel(panelUsuarios);
    }//GEN-LAST:event_botonUsuariosActionPerformed

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
    private javax.swing.JButton botonEntradas;
    private javax.swing.JButton botonInventario;
    private javax.swing.JButton botonProductos;
    private javax.swing.JButton botonSalidas;
    private javax.swing.JButton botonUsuarios;
    private javax.swing.JDesktopPane contenedor;
    private javax.swing.JLabel etiquetaLogo;
    private javax.swing.JLabel etiquetaUsuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel panelLogo;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
