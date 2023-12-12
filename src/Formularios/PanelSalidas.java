package Formularios;

import Clases.InventarioInicial;
import Clases.MovimientoProducto;
import Clases.Producto;
import ConexionBD.EntradaDAO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ConexionBD.InventarioDAO;
import ConexionBD.ProductoDAO;
import ConexionBD.SalidaDAO;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author DARLIN
 */
public class PanelSalidas extends javax.swing.JPanel {
    InventarioInicial inventario = new InventarioInicial();
    Producto producto = new Producto();
    ProductoDAO productoSQL = new ProductoDAO();
    MovimientoProducto entrada = new MovimientoProducto();
    EntradaDAO entradaSQL = new EntradaDAO();
    InventarioDAO inventarioSQL = new InventarioDAO();
    MovimientoProducto salida = new MovimientoProducto();
    SalidaDAO salidaSQL = new SalidaDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    int codigoSalida = -1;
    
    public PanelSalidas() {
        initComponents();
        modelo = new DefaultTableModel();
        modelo.addColumn("# Movimiento");
        modelo.addColumn("Fecha");
        modelo.addColumn("Código Producto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Cantidad");
        this.tablaSalidas.setModel(modelo);
        LimpiarTabla();
        ListarSalidas();
        deshabilitarCampos();
        this.tablaSalidas.setEnabled(false);
        centrarColumnasTabla();
    }
    
    private void centrarColumnasTabla(){
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaSalidas.getColumnCount(); i++) {
            tablaSalidas.getColumnModel().getColumn(i).setCellRenderer(modelocentrar);
        }
    }
    
    private void deshabilitarCampos(){
        codigoIngresado.setEnabled(false);
        descripcionIngresada.setEnabled(false);
        fechaIngresada.setEnabled(false);
        porcionesIngresadas.setEnabled(false);
        botonRegistrar.setEnabled(false);
        botonEliminar.setEnabled(false);
        
        codigoIngresado.setText("");
        descripcionIngresada.setText("");
        porcionesIngresadas.setText("");
        fechaIngresada.setCalendar(null);
    }
    
    private void habilitarCampos(){
        codigoIngresado.setEnabled(true);
        codigoIngresado.requestFocus();
        descripcionIngresada.setEnabled(true);
        fechaIngresada.setEnabled(true);
        porcionesIngresadas.setEnabled(true);
        botonRegistrar.setEnabled(true);
    }
    
    @SuppressWarnings("deprecation")
    private void RegistrarSalida(){
        boolean fechaCorrecta = false;
        boolean cantidadCorrecta = false;
        
        if(!"".equals(codigoIngresado.getText()) && !"".equals(descripcionIngresada.getText()) && !"".equals(porcionesIngresadas.getText())){
            inventario = inventarioSQL.BuscarProducto(codigoIngresado.getText());
            producto = productoSQL.BuscarProducto(codigoIngresado.getText());
            entrada = entradaSQL.BuscarEntrada(codigoIngresado.getText());
            salida.setTipoMovimiento("Salida");
            salida.setCodigoProducto(codigoIngresado.getText());
            salida.setDescripcion(descripcionIngresada.getText());
            
            try {
                Date fecha = fechaIngresada.getDate();
                long tiempo = fecha.getTime();
                java.sql.Date fechaObtenida = new java.sql.Date(tiempo);
                salida.setFecha(fechaObtenida);
                fechaCorrecta = true;
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una fecha válida", "Error al Ingresar Datos", JOptionPane.ERROR_MESSAGE);
            }
            
            int cantidadPorciones = Integer.parseInt(porcionesIngresadas.getText());
            if(cantidadPorciones > inventario.getStock()){
                JOptionPane.showMessageDialog(null, "No hay suficiente stock \nStock Actual: "+inventario.getStock(), "Ingresar Datos", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    salida.setPorciones(cantidadPorciones);
                    cantidadCorrecta = true;
                }catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad entera, no decimales: "+e.toString(), "Ingresar Datos", JOptionPane.WARNING_MESSAGE);
                }
            }
            
            salida.setCantidad(cantidadPorciones * entrada.getCantidad() / entrada.getPorciones());
            if(fechaCorrecta && cantidadCorrecta){
                salidaSQL.RegistrarSalida(salida);
                JOptionPane.showMessageDialog(null, "Salida de Producto Registrada");
                deshabilitarCampos();
            }
        }
    }
    
    private void ListarSalidas(){
        @SuppressWarnings("unchecked")
        ArrayList<MovimientoProducto> ListaEntradas = salidaSQL.ListarSalidas();
        modelo = (DefaultTableModel) tablaSalidas.getModel();
        Object[] objeto = new Object[5];
        for (int i = 0; i < ListaEntradas.size(); i++) {
            objeto[0] = ListaEntradas.get(i).getCodigoMovimiento();
            objeto[1] = ListaEntradas.get(i).getFecha();
            objeto[2] = ListaEntradas.get(i).getCodigoProducto();
            objeto[3] = ListaEntradas.get(i).getDescripcion();
            objeto[4] = ListaEntradas.get(i).getCantidad();
            modelo.addRow(objeto);
        }
        tablaSalidas.setModel(modelo);
    }
    
    private void LimpiarTabla(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    private void ReportePDF(){
        JFileChooser guardarArchivo = new JFileChooser();
        FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("Archivos PDF", "pdf");
        guardarArchivo.setFileFilter(filtroPDF);

        int seleccion = guardarArchivo.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String filePath = guardarArchivo.getSelectedFile().getAbsolutePath();

            // Agrega ".pdf" a la ruta si no termina con esa extensión
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(filePath));
                documento.open();
                Image img = Image.getInstance(getClass().getResource("/Imagenes/jesedsalud.png"));
                Paragraph fecha = new Paragraph();

                Font blanco = new Font(Font.getFamily("Microsoft YaHei"), 14, Font.BOLD, BaseColor.WHITE);
                Font negro = new Font(Font.getFamily("Microsoft YaHei"), 12, Font.BOLD, BaseColor.BLACK);

                Date fechaActual = new Date();
                fecha.add(Chunk.NEWLINE);
                fecha.add("Fecha: "+ new SimpleDateFormat("dd-MM-YYYY").format(fechaActual)+"\n\n");

                PdfPTable encabezado = new PdfPTable(4);
                encabezado.setWidthPercentage(100);
                encabezado.getDefaultCell().setBorder(0);
                float[] columnaEncabezado = new float[]{30f, 10f, 100f, 40f};
                encabezado.setWidths(columnaEncabezado);
                encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

                encabezado.addCell(img);
                encabezado.addCell("");
                encabezado.addCell("REPORTE DE SALIDAS DE PRODUCTOS");
                encabezado.addCell(fecha);
                documento.add(encabezado);

                Paragraph salidas = new Paragraph();
                salidas.add(Chunk.NEWLINE);
                salidas.add("Detalle de las salidas: \n\n");
                documento.add(salidas);

                PdfPTable tablaDeSalidas = new PdfPTable(4);
                tablaDeSalidas.setWidthPercentage(100);
                tablaDeSalidas.getDefaultCell().setBorder(0);
                
                float[] columnaSalidas = new float[]{30f, 30f, 100f, 30f};
                tablaDeSalidas.setWidths(columnaSalidas);
                tablaDeSalidas.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                PdfPCell fechaSalida = new PdfPCell(new Phrase("Fecha", blanco));
                PdfPCell codigoProducto = new PdfPCell(new Phrase("Código Producto", blanco));
                PdfPCell descripcion = new PdfPCell(new Phrase("Descripción", blanco));
                PdfPCell cantidad = new PdfPCell(new Phrase("Cantidad", blanco));

                BaseColor colorCeldas = new BaseColor(181, 12, 97);
                fechaSalida.setBackgroundColor(colorCeldas);
                codigoProducto.setBackgroundColor(colorCeldas);
                descripcion.setBackgroundColor(colorCeldas);
                cantidad.setBackgroundColor(colorCeldas);

                tablaDeSalidas.addCell(fechaSalida);
                tablaDeSalidas.addCell(codigoProducto);
                tablaDeSalidas.addCell(descripcion);
                tablaDeSalidas.addCell(cantidad);

                for (int i = 0; i < tablaSalidas.getRowCount(); i++) {
                    String fechaTabla = tablaSalidas.getValueAt(i, 1).toString();
                    String codigoProductoTabla = tablaSalidas.getValueAt(i, 2).toString();
                    String descripcionTabla = tablaSalidas.getValueAt(i, 3).toString();
                    String cantidadTabla = tablaSalidas.getValueAt(i, 4).toString();

                    PdfPCell fechaPDF = new PdfPCell(new Phrase(fechaTabla, negro));
                    PdfPCell codigoProductoPDF = new PdfPCell(new Phrase(codigoProductoTabla, negro));
                    PdfPCell descripcionPDF = new PdfPCell(new Phrase(descripcionTabla, negro));
                    PdfPCell cantidadPDF = new PdfPCell(new Phrase(cantidadTabla, negro));
                    
                    tablaDeSalidas.addCell(fechaPDF);
                    tablaDeSalidas.addCell(codigoProductoPDF);
                    tablaDeSalidas.addCell(descripcionPDF);
                    tablaDeSalidas.addCell(cantidadPDF);
                }

                documento.add(tablaDeSalidas);

                documento.close();
                File pdfFile = new File(filePath);
                Desktop.getDesktop().open(pdfFile);

                JOptionPane.showMessageDialog(null, "PDF generado y abierto correctamente en: " + filePath, "Reporte PDF", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | IOException e) {
                JOptionPane.showMessageDialog(null, "No se pudo generar reporte en PDF", "Reporte PDF", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void ReporteExcel(){
        JFileChooser guardarArchivo = new JFileChooser();
        FileNameExtensionFilter filtroExcel = new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx");
        guardarArchivo.setFileFilter(filtroExcel);

        int seleccion = guardarArchivo.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String filePath = guardarArchivo.getSelectedFile().getAbsolutePath();

            // Agrega ".xlsx" a la ruta si no termina con esa extensión
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte Salidas Productos");
 
            try {
                CellStyle estiloTitulo = libro.createCellStyle();
                estiloTitulo.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
                estiloTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                estiloTitulo.setAlignment(HorizontalAlignment.CENTER);
                estiloTitulo.setVerticalAlignment(VerticalAlignment.CENTER);
                org.apache.poi.ss.usermodel.Font fuenteTitulo = libro.createFont();
                fuenteTitulo.setFontName("Microsoft YaHei");
                fuenteTitulo.setBold(true);
                fuenteTitulo.setColor(IndexedColors.WHITE.getIndex());
                fuenteTitulo.setFontHeightInPoints((short) 14);
                estiloTitulo.setFont(fuenteTitulo);

                Row filaTitulo = hoja.createRow(0);
                Cell celdaTitulo = filaTitulo.createCell(0);
                celdaTitulo.setCellStyle(estiloTitulo);
                celdaTitulo.setCellValue("Reporte Salidas de Productos");

                hoja.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));

                String[] cabeceraTabla = new String[]{"#Movimiento","Fecha","Código Producto", "Descripción", "Cantidad"};

                CellStyle headerStyle = libro.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);
                headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);
                headerStyle.setBorderBottom(BorderStyle.THIN);

                org.apache.poi.ss.usermodel.Font font = libro.createFont();
                font.setFontName("Microsoft YaHei");
                font.setBold(true);
                font.setColor(IndexedColors.WHITE.getIndex());
                font.setFontHeightInPoints((short) 12);
                headerStyle.setFont(font);

                Row filaEncabezados = hoja.createRow(2);

                for (int i = 0; i < cabeceraTabla.length; i++) {
                    Cell celdaEnzabezado = filaEncabezados.createCell(i);
                    celdaEnzabezado.setCellStyle(headerStyle);
                    celdaEnzabezado.setCellValue(cabeceraTabla[i]);
                }

                int numFilaDatos = 3;

                CellStyle datosEstilo = libro.createCellStyle();
                datosEstilo.setBorderBottom(BorderStyle.THIN);
                datosEstilo.setBorderLeft(BorderStyle.THIN);
                datosEstilo.setBorderRight(BorderStyle.THIN);
                datosEstilo.setBorderBottom(BorderStyle.THIN);

                int numCol = tablaSalidas.getColumnCount();
                for (int i = 0; i < tablaSalidas.getRowCount(); i++) {
                    Row filaDatos = hoja.createRow(numFilaDatos);
                    for (int j = 0; j < numCol; j++) {
                        Cell celdaDatos = filaDatos.createCell(j);
                        celdaDatos.setCellStyle(datosEstilo);
                        celdaDatos.setCellValue(tablaSalidas.getValueAt(i, j).toString());
                    }
                    numFilaDatos++;
                }
                
                hoja.autoSizeColumn(0);
                hoja.autoSizeColumn(1);
                hoja.autoSizeColumn(2);
                hoja.autoSizeColumn(3);
                hoja.autoSizeColumn(4);
                
                hoja.setZoom(100);
                FileOutputStream fileOut = new FileOutputStream(filePath);
                libro.write(fileOut);
                fileOut.close();

                // Abre el archivo Excel con la aplicación predeterminada
                File excelFile = new File(filePath);
                Desktop.getDesktop().open(excelFile);
                JOptionPane.showMessageDialog(null, "Reporte en Excel generado correctamente en: " + filePath, "Reporte Excel", JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException | IOException  e) {
                JOptionPane.showMessageDialog(null, "Error al generar o abrir Excel", "Reporte Excel", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        codigoIngresado = new javax.swing.JTextField();
        descripcionIngresada = new javax.swing.JTextField();
        porcionesIngresadas = new javax.swing.JTextField();
        botonNuevo = new javax.swing.JButton();
        botonRegistrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaSalidas = new javax.swing.JTable();
        fechaIngresada = new com.toedter.calendar.JDateChooser();
        botonEliminar = new javax.swing.JButton();
        botonReportePDF = new javax.swing.JButton();
        botonReporteExcel = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        jLabel1.setText("SALIDA DE PRODUCTOS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Llene la información respectiva para la salida de los productos.");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel3.setText("Código del Producto:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel4.setText("Descripción:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, -1, -1));

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel5.setText("Cantidad Porción:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, -1, -1));

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel6.setText("Fecha:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        codigoIngresado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoIngresadoKeyPressed(evt);
            }
        });
        jPanel1.add(codigoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 120, -1));
        jPanel1.add(descripcionIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 330, -1));

        porcionesIngresadas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                porcionesIngresadasKeyPressed(evt);
            }
        });
        jPanel1.add(porcionesIngresadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 90, -1));

        botonNuevo.setBackground(new java.awt.Color(255, 255, 254));
        botonNuevo.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_nuevo.png"))); // NOI18N
        botonNuevo.setText("Nuevo");
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(botonNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, -1, -1));

        botonRegistrar.setBackground(new java.awt.Color(255, 255, 254));
        botonRegistrar.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_grabar.png"))); // NOI18N
        botonRegistrar.setText("Registrar Salida");
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(botonRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 490, -1, -1));

        tablaSalidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaSalidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSalidasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaSalidas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 870, 180));

        fechaIngresada.setBackground(new java.awt.Color(255, 255, 254));
        jPanel1.add(fechaIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 150, -1));

        botonEliminar.setBackground(new java.awt.Color(255, 255, 254));
        botonEliminar.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_eliminar.png"))); // NOI18N
        botonEliminar.setText("Eliminar");
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(botonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 490, -1, -1));

        botonReportePDF.setBackground(new java.awt.Color(255, 255, 254));
        botonReportePDF.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonReportePDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        botonReportePDF.setText("Reporte PDF");
        botonReportePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReportePDFActionPerformed(evt);
            }
        });
        jPanel1.add(botonReportePDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, -1, -1));

        botonReporteExcel.setBackground(new java.awt.Color(255, 255, 254));
        botonReporteExcel.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonReporteExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excel.png"))); // NOI18N
        botonReporteExcel.setText("Reporte Excel");
        botonReporteExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReporteExcelActionPerformed(evt);
            }
        });
        jPanel1.add(botonReporteExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
        RegistrarSalida();
        LimpiarTabla();
        ListarSalidas();
        inventarioSQL.ActualizarEntradas();
        inventarioSQL.ActualizarSalidas();
    }//GEN-LAST:event_botonRegistrarActionPerformed

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        habilitarCampos();
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        if(codigoSalida == -1){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro de la tabla", "Eliminar Registro", JOptionPane.WARNING_MESSAGE);
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?");
            if(pregunta==0){
                salidaSQL.EliminarSalida(codigoSalida);
                LimpiarTabla();
                ListarSalidas();
                deshabilitarCampos();
            }
        }
        botonNuevo.setEnabled(true);
    }//GEN-LAST:event_botonEliminarActionPerformed

    private void tablaSalidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSalidasMouseClicked
        botonNuevo.setEnabled(false);
        botonRegistrar.setEnabled(false);
        botonEliminar.setEnabled(true);
        int filaSeleccionada = tablaSalidas.rowAtPoint(evt.getPoint());
        codigoSalida = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
    }//GEN-LAST:event_tablaSalidasMouseClicked

    private void codigoIngresadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoIngresadoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(!"".equals(codigoIngresado.getText())){
                String codigoProducto = codigoIngresado.getText();
                producto = productoSQL.BuscarProducto(codigoProducto);
                if(producto.getDescripcion() != null){
                    descripcionIngresada.setText(producto.getDescripcion());
                    fechaIngresada.requestFocus();
                } else {
                    deshabilitarCampos();
                    habilitarCampos();
                    codigoIngresado.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el código del producto", "Buscar Datos", JOptionPane.WARNING_MESSAGE);
                codigoIngresado.requestFocus();
            }
        }
    }//GEN-LAST:event_codigoIngresadoKeyPressed

    private void porcionesIngresadasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcionesIngresadasKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            botonRegistrar.doClick();
        }
    }//GEN-LAST:event_porcionesIngresadasKeyPressed

    private void botonReportePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReportePDFActionPerformed
        ReportePDF();
    }//GEN-LAST:event_botonReportePDFActionPerformed

    private void botonReporteExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReporteExcelActionPerformed
        ReporteExcel();
    }//GEN-LAST:event_botonReporteExcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JButton botonReporteExcel;
    private javax.swing.JButton botonReportePDF;
    private javax.swing.JTextField codigoIngresado;
    private javax.swing.JTextField descripcionIngresada;
    private com.toedter.calendar.JDateChooser fechaIngresada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField porcionesIngresadas;
    private javax.swing.JTable tablaSalidas;
    // End of variables declaration//GEN-END:variables
}
