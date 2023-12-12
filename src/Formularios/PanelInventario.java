package Formularios;

import Clases.InventarioFinal;
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
import java.awt.Desktop;
import java.awt.HeadlessException;
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
 *
 * @author DARLIN
 */
public class PanelInventario extends javax.swing.JPanel {
    int cantidadInventarioBajo;
    InventarioDAO inventarioSQL = new InventarioDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public PanelInventario() {
        inventarioSQL.ActualizarEntradas();
        inventarioSQL.ActualizarSalidas();
        initComponents();
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Área");
        modelo.addColumn("Categoría");
        modelo.addColumn("Costo Unitario");
        modelo.addColumn("Entradas Porciones");
        modelo.addColumn("Salidas Porciones");
        modelo.addColumn("Stock");
        modelo.addColumn("Costo Total");
        this.tablaInventario.setModel(modelo);
        ListarInventario();
        this.tablaInventario.setEnabled(false);
        centrarColumnasTabla();
        verificarStockBajo();
    }

    private void verificarStockBajo(){
        cantidadInventarioBajo = inventarioSQL.CantidadStockBajo();
        if(cantidadInventarioBajo > 0){
            JOptionPane.showMessageDialog(null, "Se encontraron productos con stock bajo. Revise los productos al darle click al boton 'Alertas'\nCantidad: "+cantidadInventarioBajo, "Alerta", JOptionPane.WARNING_MESSAGE);
            FrameStockBajo fsb = new FrameStockBajo();
            fsb.setVisible(true);
        }
    }
    
    private void centrarColumnasTabla(){
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaInventario.getColumnCount(); i++) {
            tablaInventario.getColumnModel().getColumn(i).setCellRenderer(modelocentrar);
        }
    }
    
    private void ListarInventario(){
        @SuppressWarnings("unchecked")
        ArrayList<InventarioFinal> ListaInventario = inventarioSQL.ListarInventario();
        modelo = (DefaultTableModel) tablaInventario.getModel();
        Object[] objeto = new Object[9];
        for (int i = 0; i < ListaInventario.size(); i++) {
            objeto[0] = ListaInventario.get(i).getCodigoProducto();
            objeto[1] = ListaInventario.get(i).getDescripcionProducto();
            objeto[2] = ListaInventario.get(i).getAreaProducto();
            objeto[3] = ListaInventario.get(i).getCategoriaProducto();
            objeto[4] = "S/"+ListaInventario.get(i).getCostoUnitario();
            objeto[5] = ListaInventario.get(i).getEntradas();
            objeto[6] = ListaInventario.get(i).getSalidas();
            objeto[7] = ListaInventario.get(i).getStock();
            objeto[8] = "S/"+ListaInventario.get(i).getCostoTotal();
            modelo.addRow(objeto);
        }
        tablaInventario.setModel(modelo);
    }
    
    private void LimpiarTabla(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
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
            Sheet hoja = libro.createSheet("Reporte Inventario");
 
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
                celdaTitulo.setCellValue("Reporte de Inventario Productos");

                hoja.addMergedRegion(new CellRangeAddress(0, 1, 0, 8));

                String[] cabeceraTabla = new String[]{"Código", "Descripción", "Area", "Categoria", "Precio Unitario","Entradas", "Salidas", "Stock", "Costo Total"};

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

                int numCol = tablaInventario.getColumnCount();
                for (int i = 0; i < tablaInventario.getRowCount(); i++) {
                    Row filaDatos = hoja.createRow(numFilaDatos);
                    for (int j = 0; j < numCol; j++) {
                        Cell celdaDatos = filaDatos.createCell(j);
                        celdaDatos.setCellStyle(datosEstilo);
                        celdaDatos.setCellValue(tablaInventario.getValueAt(i, j).toString());
                    }
                    numFilaDatos++;
                }
                
                hoja.autoSizeColumn(0);
                hoja.autoSizeColumn(1);
                hoja.autoSizeColumn(2);
                hoja.autoSizeColumn(3);
                hoja.autoSizeColumn(4);
                hoja.autoSizeColumn(5);
                hoja.autoSizeColumn(6);
                hoja.autoSizeColumn(7);
                hoja.autoSizeColumn(8);

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
    
    private void ReportePDF(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Agrega ".pdf" a la ruta si no termina con esa extensión
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // Código para generar el archivo PDF
            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(filePath));
                documento.open();
                Image img = Image.getInstance(getClass().getResource("/Imagenes/jesedsalud.png"));
                Paragraph fecha = new Paragraph();

                Font blanco = new Font(Font.getFamily("Microsoft YaHei"), 6, Font.BOLD, BaseColor.WHITE);
                Font negro = new Font(Font.getFamily("Microsoft YaHei"), 6, Font.BOLD, BaseColor.BLACK);

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
                encabezado.addCell("REPORTE DE INVENTARIO");
                encabezado.addCell(fecha);
                documento.add(encabezado);

                Paragraph entradas = new Paragraph();
                entradas.add(Chunk.NEWLINE);
                entradas.add("Detalle del inventario de  productos: \n\n");
                documento.add(entradas);

                PdfPTable tablaDeInventario = new PdfPTable(9);
                tablaDeInventario.setWidthPercentage(100);
                tablaDeInventario.getDefaultCell().setBorder(0);

                float[] columnaEntradas = new float[]{30f, 100f, 70f, 30f, 40f, 30f, 30f, 30f, 30f};
                tablaDeInventario.setWidths(columnaEntradas);
                tablaDeInventario.setHorizontalAlignment(Element.ALIGN_LEFT);
                PdfPCell codigo = new PdfPCell(new Phrase("Código Producto", blanco));
                PdfPCell descripcion = new PdfPCell(new Phrase("Descripción", blanco));
                PdfPCell area = new PdfPCell(new Phrase("Área", blanco));
                PdfPCell categoria = new PdfPCell(new Phrase("Categoría", blanco));
                PdfPCell precio = new PdfPCell(new Phrase("Precio", blanco));
                PdfPCell entrada = new PdfPCell(new Phrase("Entradas", blanco));
                PdfPCell salida = new PdfPCell(new Phrase("Salidas", blanco));
                PdfPCell stock = new PdfPCell(new Phrase("Stock", blanco));
                PdfPCell costoTotal = new PdfPCell(new Phrase("Costo Total", blanco));

                BaseColor colorCeldas = new BaseColor(181, 12, 97);
                codigo.setBackgroundColor(colorCeldas);
                descripcion.setBackgroundColor(colorCeldas);
                area.setBackgroundColor(colorCeldas);
                categoria.setBackgroundColor(colorCeldas);
                precio.setBackgroundColor(colorCeldas);
                entrada.setBackgroundColor(colorCeldas);
                salida.setBackgroundColor(colorCeldas);
                stock.setBackgroundColor(colorCeldas);
                costoTotal.setBackgroundColor(colorCeldas);

                tablaDeInventario.addCell(codigo);
                tablaDeInventario.addCell(descripcion);
                tablaDeInventario.addCell(area);
                tablaDeInventario.addCell(categoria);
                tablaDeInventario.addCell(precio);
                tablaDeInventario.addCell(entrada);
                tablaDeInventario.addCell(salida);
                tablaDeInventario.addCell(stock);
                tablaDeInventario.addCell(costoTotal);

                for (int i = 0; i < tablaInventario.getRowCount(); i++) {
                    String codigoProductoTabla = tablaInventario.getValueAt(i, 0).toString();
                    String descripcionTabla = tablaInventario.getValueAt(i, 1).toString();
                    String areaTabla = tablaInventario.getValueAt(i, 2).toString();
                    String categoriaTabla = tablaInventario.getValueAt(i, 3).toString();
                    String precioTabla = tablaInventario.getValueAt(i, 4).toString();
                    String entradaTabla = tablaInventario.getValueAt(i, 5).toString();
                    String salidaTabla = tablaInventario.getValueAt(i, 6).toString();
                    String stockTabla = tablaInventario.getValueAt(i, 7).toString();
                    String costoTotalTabla = tablaInventario.getValueAt(i, 8).toString();

                    PdfPCell codigoPDF = new PdfPCell(new Phrase(codigoProductoTabla, negro));
                    PdfPCell descripcionPDF = new PdfPCell(new Phrase(descripcionTabla, negro));
                    PdfPCell areaPDF = new PdfPCell(new Phrase(areaTabla, negro));
                    PdfPCell categoriaPDF = new PdfPCell(new Phrase(categoriaTabla, negro));
                    PdfPCell precioPDF = new PdfPCell(new Phrase(precioTabla, negro));
                    PdfPCell entradaPDF = new PdfPCell(new Phrase(entradaTabla, negro));
                    PdfPCell salidaPDF = new PdfPCell(new Phrase(salidaTabla, negro));
                    PdfPCell stockPDF = new PdfPCell(new Phrase(stockTabla, negro));
                    PdfPCell costoTotalPDF = new PdfPCell(new Phrase(costoTotalTabla, negro));

                    tablaDeInventario.addCell(codigoPDF);
                    tablaDeInventario.addCell(descripcionPDF);
                    tablaDeInventario.addCell(areaPDF);
                    tablaDeInventario.addCell(categoriaPDF);
                    tablaDeInventario.addCell(precioPDF);
                    tablaDeInventario.addCell(entradaPDF);
                    tablaDeInventario.addCell(salidaPDF);
                    tablaDeInventario.addCell(stockPDF);
                    tablaDeInventario.addCell(costoTotalPDF);
                }

                documento.add(tablaDeInventario);
                documento.close();
                
                // Abrir el archivo PDF con la aplicación predeterminada
                File pdfFile = new File(filePath);
                Desktop.getDesktop().open(pdfFile);

                JOptionPane.showMessageDialog(null, "PDF generado y abierto correctamente en: " + filePath, "Reporte PDF", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | IOException e) {
                JOptionPane.showMessageDialog(null, "No se pudo generar reporte en PDF", "Reporte PDF", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    private String RetornarSeleccion(){
        String seleccion = buscarPor.getSelectedItem().toString();
        switch (seleccion) {
            case "Código":
                return "productoID";
            case "Descripción":
                return "descripcion";
            default:
                return "-----";
        }
    }
    
    private void OrganizarProductos(){
        String area = areaOrganizar.getSelectedItem().toString();
        String categoria = categoriaOrganizar.getSelectedItem().toString();
        if(area.equals("--seleccione--") && categoria.equals("--seleccione--")){
            LimpiarTabla();
            ListarInventario();
         } else {
            LimpiarTabla();
            ArrayList<InventarioFinal> ListaOrganizada = inventarioSQL.OrganizarInventario(area, categoria);
            modelo = (DefaultTableModel) tablaInventario.getModel();
            Object[] objeto = new Object[9];
            for (int i = 0; i < ListaOrganizada.size(); i++) {
                objeto[0] = ListaOrganizada.get(i).getCodigoProducto();
                objeto[1] = ListaOrganizada.get(i).getDescripcionProducto();
                objeto[2] = ListaOrganizada.get(i).getAreaProducto();
                objeto[3] = ListaOrganizada.get(i).getCategoriaProducto();
                objeto[4] = "S/" + ListaOrganizada.get(i).getCostoUnitario();
                objeto[5] = ListaOrganizada.get(i).getEntradas();
                objeto[6] = ListaOrganizada.get(i).getSalidas();
                objeto[7] = ListaOrganizada.get(i).getStock();
                objeto[8] = "S/" + ListaOrganizada.get(i).getCostoTotal();
                modelo.addRow(objeto);
            }
            tablaInventario.setModel(modelo);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        buscarPor = new javax.swing.JComboBox<>();
        cajaDeBusqueda = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        botonReportePDF = new javax.swing.JButton();
        botonReporteExcel = new javax.swing.JButton();
        botonAlertas = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        areaOrganizar = new javax.swing.JComboBox<>();
        categoriaOrganizar = new javax.swing.JComboBox<>();
        botonOrganizar = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        jLabel1.setText("INVENTARIO DE PRODUCTOS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Entrada, Salida y Stock de productos.");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaInventario);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 920, 340));

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jLabel3.setText("Buscar por:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        buscarPor.setBackground(new java.awt.Color(255, 255, 254));
        buscarPor.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        buscarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Descripción" }));
        jPanel1.add(buscarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, -1, -1));

        cajaDeBusqueda.setBackground(new java.awt.Color(255, 255, 254));
        cajaDeBusqueda.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        cajaDeBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaDeBusquedaKeyReleased(evt);
            }
        });
        jPanel1.add(cajaDeBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 230, 20));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jLabel4.setText("Escribir:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        botonReportePDF.setBackground(new java.awt.Color(255, 255, 254));
        botonReportePDF.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonReportePDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        botonReportePDF.setText("Reporte PDF");
        botonReportePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReportePDFActionPerformed(evt);
            }
        });
        jPanel1.add(botonReportePDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 120, 150, -1));

        botonReporteExcel.setBackground(new java.awt.Color(255, 255, 254));
        botonReporteExcel.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonReporteExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excel.png"))); // NOI18N
        botonReporteExcel.setText("Reporte Excel");
        botonReporteExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReporteExcelActionPerformed(evt);
            }
        });
        jPanel1.add(botonReporteExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 70, 150, -1));

        botonAlertas.setBackground(new java.awt.Color(255, 255, 254));
        botonAlertas.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonAlertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/alerta.png"))); // NOI18N
        botonAlertas.setText("Alertas");
        botonAlertas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAlertasActionPerformed(evt);
            }
        });
        jPanel1.add(botonAlertas, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 150, -1));

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        jLabel6.setText("Area:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, -1));

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 14)); // NOI18N
        jLabel7.setText("Categoría:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, -1, -1));

        areaOrganizar.setBackground(new java.awt.Color(255, 255, 254));
        areaOrganizar.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        areaOrganizar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--seleccione--", "OPERATORIA (RESTAURACIONES)", "CIRUGIA", "ORTODONCIA", "ODONTOPEDIATRIA", "RADIOLOGIA", "REHABILITACION GENERAL", "LIMPIEZA", "ADMINISTRATIVA", "MARKETING", "FOTOGRAFIA", "INMOVILIARIO", "PERIDONCIA", "INPLANTOLOGIA", "PREVENTIVO", "BIOSEGURIDAD (ESTERILIZACION)", "MANTENIMIENTO EQUIPOS" }));
        jPanel1.add(areaOrganizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 220, -1));

        categoriaOrganizar.setBackground(new java.awt.Color(255, 255, 254));
        categoriaOrganizar.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        categoriaOrganizar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--seleccione--", "Materiales", "Instrumentos", "Equipos", "Instrumentos Cirugía", "Moviliario" }));
        jPanel1.add(categoriaOrganizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 220, -1));

        botonOrganizar.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        botonOrganizar.setText("Organizar");
        botonOrganizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOrganizarActionPerformed(evt);
            }
        });
        jPanel1.add(botonOrganizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, -1, -1));

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

    private void botonReportePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReportePDFActionPerformed
        ReportePDF();
    }//GEN-LAST:event_botonReportePDFActionPerformed

    private void cajaDeBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaDeBusquedaKeyReleased
        if(!"".equals(cajaDeBusqueda.getText())){
            LimpiarTabla();
            String busqueda = RetornarSeleccion();
            String buscar = cajaDeBusqueda.getText();
            @SuppressWarnings("unchecked")
            ArrayList<InventarioFinal> ListaBusqueda = inventarioSQL.BuscarInventario(busqueda, buscar);
            modelo = (DefaultTableModel) tablaInventario.getModel();
            Object[] objeto = new Object[9];
            for (int i = 0; i < ListaBusqueda.size(); i++) {
                objeto[0] = ListaBusqueda.get(i).getCodigoProducto();
                objeto[1] = ListaBusqueda.get(i).getDescripcionProducto();
                objeto[2] = ListaBusqueda.get(i).getAreaProducto();
                objeto[3] = ListaBusqueda.get(i).getCategoriaProducto();
                objeto[4] = "S/" + ListaBusqueda.get(i).getCostoUnitario();
                objeto[5] = ListaBusqueda.get(i).getEntradas();
                objeto[6] = ListaBusqueda.get(i).getSalidas();
                objeto[7] = ListaBusqueda.get(i).getStock();
                objeto[8] = "S/" + ListaBusqueda.get(i).getCostoTotal();
                modelo.addRow(objeto);
            }
            tablaInventario.setModel(modelo);
        } else {
            LimpiarTabla();
            ListarInventario();
        }
    }//GEN-LAST:event_cajaDeBusquedaKeyReleased

    private void botonReporteExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReporteExcelActionPerformed
        ReporteExcel();
    }//GEN-LAST:event_botonReporteExcelActionPerformed

    private void botonAlertasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAlertasActionPerformed
        FrameStockBajo fsb = new FrameStockBajo();
        fsb.setVisible(true);
    }//GEN-LAST:event_botonAlertasActionPerformed

    private void botonOrganizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOrganizarActionPerformed
        OrganizarProductos();
    }//GEN-LAST:event_botonOrganizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> areaOrganizar;
    private javax.swing.JButton botonAlertas;
    private javax.swing.JButton botonOrganizar;
    private javax.swing.JButton botonReporteExcel;
    private javax.swing.JButton botonReportePDF;
    private javax.swing.JComboBox<String> buscarPor;
    private javax.swing.JTextField cajaDeBusqueda;
    private javax.swing.JComboBox<String> categoriaOrganizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaInventario;
    // End of variables declaration//GEN-END:variables
}
