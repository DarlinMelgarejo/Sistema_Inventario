package Formularios;

import Clases.Usuario;
import ConexionBD.UsuarioDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author DARLIN
 */
public class PanelUsuarios extends javax.swing.JPanel {
    Usuario usuario = new Usuario();
    UsuarioDAO usuarioSQL = new UsuarioDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    int codigoUsuario = -1;
    
    public PanelUsuarios() {
        initComponents();
        modelo.addColumn("Código");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Cargo/Puesto");
        modelo.addColumn("Nombre Usuario");
        modelo.addColumn("Contraseña");
        this.tablaUsuarios.setModel(modelo);
        LimpiarTabla();
        ListarUsuarios();
        this.tablaUsuarios.setEnabled(false);
        botonEliminar.setEnabled(false);
    }

    private void ListarUsuarios(){
        @SuppressWarnings("unchecked")
        ArrayList<Usuario> ListaUsuarios = usuarioSQL.ListarUsuarios();
        modelo = (DefaultTableModel) tablaUsuarios.getModel();
        Object[] objeto = new Object[6];
        for (int i = 0; i < ListaUsuarios.size(); i++) {
            objeto[0] = ListaUsuarios.get(i).getUsuarioID();
            objeto[1] = ListaUsuarios.get(i).getNombres();
            objeto[2] = ListaUsuarios.get(i).getApellidos();
            objeto[3] = ListaUsuarios.get(i).getPuesto();
            objeto[4] = ListaUsuarios.get(i).getNombreUsuario();
            objeto[5] = ListaUsuarios.get(i).getContraseña();
            modelo.addRow(objeto);
        }
        tablaUsuarios.setModel(modelo);
    }
    
    private void LimpiarTabla(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        botonEliminar = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        jLabel1.setText("USUARIOS REGISTRADOS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Puede visualizar la información de los usuarios registrados");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        tablaUsuarios.setBackground(new java.awt.Color(255, 255, 254));
        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuarios);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 860, 320));

        botonEliminar.setBackground(new java.awt.Color(255, 255, 254));
        botonEliminar.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_eliminar.png"))); // NOI18N
        botonEliminar.setText("Eliminar");
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(botonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 480, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tablaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosMouseClicked
        botonEliminar.setEnabled(true);
        int fila = tablaUsuarios.rowAtPoint(evt.getPoint());
        codigoUsuario = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tablaUsuariosMouseClicked

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        if(codigoUsuario == -1){
          JOptionPane.showMessageDialog(null, "Debe seleccionar un registro de la tabla", "Eliminar Registro", JOptionPane.WARNING_MESSAGE);
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?");
            if(pregunta==0){
                usuarioSQL.EliminarUsuario(codigoUsuario);
                LimpiarTabla();
                ListarUsuarios();
            }
        }
        botonEliminar.setEnabled(true);
    }//GEN-LAST:event_botonEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaUsuarios;
    // End of variables declaration//GEN-END:variables
}
