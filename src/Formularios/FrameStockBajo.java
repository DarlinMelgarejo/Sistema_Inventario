package Formularios;

import Clases.InventarioFinal;
import ConexionBD.InventarioDAO;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * @author DARLIN
 */

public class FrameStockBajo extends javax.swing.JFrame {
    InventarioDAO inventarioSQL = new InventarioDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public FrameStockBajo() {
        this.setUndecorated(true);
        initComponents();
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Area");
        modelo.addColumn("Categoria");
        modelo.addColumn("Stock");
        this.tablaPrincipal.setModel(modelo);
        this.tablaPrincipal.setEnabled(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/logo.png")).getImage());
        this.setLocationRelativeTo(null);
        LimpiarTabla();
        ListarInventario();
        CentrarColumnasTabla();
    }

    
    private void CentrarColumnasTabla(){
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaPrincipal.getColumnCount(); i++) {
            tablaPrincipal.getColumnModel().getColumn(i).setCellRenderer(modelocentrar);
        }
    }
    
    private void ListarInventario(){
        @SuppressWarnings("unchecked")
        ArrayList<InventarioFinal> ListaInventario = inventarioSQL.ListarProductosStockBajo();
        modelo = (DefaultTableModel) tablaPrincipal.getModel();
        Object[] objeto = new Object[5];
        for (int i = 0; i < ListaInventario.size(); i++) {
            objeto[0] = ListaInventario.get(i).getCodigoProducto();
            objeto[1] = ListaInventario.get(i).getDescripcionProducto();
            objeto[2] = ListaInventario.get(i).getAreaProducto();
            objeto[3] = ListaInventario.get(i).getCategoriaProducto();
            objeto[4] = ListaInventario.get(i).getStock();
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
        jLabel1.setText("PRODUCTOS CON BAJO STOCK");

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
        jLabel3.setText("A continuación se muestran los productos con stock más bajo.");

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
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonCerrar))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(botonCerrar))
                .addGap(17, 17, 17)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameStockBajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameStockBajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameStockBajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameStockBajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameStockBajo().setVisible(true);
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
