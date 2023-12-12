package Formularios;

import Clases.Producto;
import ConexionBD.ProductoDAO;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * @author DARLIN
 */

public class FrameVencimiento extends javax.swing.JFrame {
    ProductoDAO productoSQL = new ProductoDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public FrameVencimiento() {
        this.setUndecorated(true);
        initComponents();
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Unidad/Medida");
        modelo.addColumn("Costo Unitario");
        modelo.addColumn("Fecha Vencimiento");
        modelo.addColumn("Area");
        modelo.addColumn("Categoría");
        this.tablaPrincipal.setModel(modelo);
        this.tablaPrincipal.setEnabled(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        this.setLocationRelativeTo(null);
        LimpiarTabla();
        ListarProductosVencimiento();
        CentrarColumnasTabla();
    }

    private void CentrarColumnasTabla(){
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaPrincipal.getColumnCount(); i++) {
            tablaPrincipal.getColumnModel().getColumn(i).setCellRenderer(modelocentrar);
        }
    }
    
    private void ListarProductosVencimiento(){
        @SuppressWarnings("unchecked")
        ArrayList<Producto> ListaProductos = productoSQL.ListarProductosVencimiento();
        modelo = (DefaultTableModel) tablaPrincipal.getModel();
        Object[] objeto = new Object[7];
        for (int i = 0; i < ListaProductos.size(); i++) {
            objeto[0] = ListaProductos.get(i).getCodigo();
            objeto[1] = ListaProductos.get(i).getDescripcion();
            objeto[2] = ListaProductos.get(i).getUnidadMedida();
            objeto[3] = "S/"+ListaProductos.get(i).getCostoUnitario();
            objeto[4] = ListaProductos.get(i).getFechaVencimiento();
            objeto[5] = ListaProductos.get(i).getAreaDestinada();
            objeto[6] = ListaProductos.get(i).getCategoria();
            modelo.addRow(objeto);
        }
        tablaPrincipal.setModel(modelo);
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

        panelPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPrincipal = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        botonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 254));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 24)); // NOI18N
        jLabel1.setText("PRODUCTOS CERCANOS A VENCERSE");

        tablaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaPrincipal);

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel3.setText("A continuación se muestran los productos con fecha de vencimiento más cercana. Se recomienda comprar nuevos productos.");

        botonCerrar.setBackground(new java.awt.Color(255, 255, 254));
        botonCerrar.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        botonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(183, 183, 183)
                        .addComponent(botonCerrar))
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonCerrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jLabel3)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
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

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed

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
                new FrameVencimiento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tablaPrincipal;
    // End of variables declaration//GEN-END:variables
}
