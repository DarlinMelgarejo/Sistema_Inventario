package Formularios;
import Clases.Producto;
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
import ConexionBD.ProductoDAO;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
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
public class PanelProductos extends javax.swing.JPanel {
    int cantidadProductos = 0;
    ButtonGroup grupoRadioBotones = new ButtonGroup();
    Producto producto = new Producto();
    ProductoDAO productoSQL = new ProductoDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    String codigoProducto = "";
    
    public PanelProductos() {
        initComponents();
        grupoRadioBotones.add(radioBotonMateriales);
        grupoRadioBotones.add(radioBotonInstrumentos);
        grupoRadioBotones.add(radioBotonEquipos);
        grupoRadioBotones.add(radioBotonInstrumentosCirugia);
        grupoRadioBotones.add(radioBotonMoviliario);
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Vendedor");
        modelo.addColumn("Marca");
        modelo.addColumn("Unidad/Medida");
        modelo.addColumn("Costo Unitario");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Fecha Vencimiento");
        modelo.addColumn("Area");
        modelo.addColumn("Categoria");
        this.tablaProductos.setModel(modelo);
        this.tablaProductos.setEnabled(false);
        deshabilitarCampos();
        LimpiarTabla();
        ListarProductos();
        centrarColumnasTabla();
        verificarFechaVencimiento();
    }

    private void verificarFechaVencimiento(){
        cantidadProductos = productoSQL.CantidadVencimientoProductos();
        if(cantidadProductos > 0){
            JOptionPane.showMessageDialog(null, "Se encontraron productos con fecha de vencimiento cercana. Revise los productos al darle click al boton 'Alertas'\nCantidad: "+cantidadProductos, "Alerta", JOptionPane.WARNING_MESSAGE);
            FrameVencimiento fv = new FrameVencimiento();
            fv.setVisible(true);
        }
    }
    
    private void centrarColumnasTabla(){
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaProductos.getColumnCount(); i++) {
            tablaProductos.getColumnModel().getColumn(i).setCellRenderer(modelocentrar);
        }
    }
    
    private void deshabilitarCampos(){
        codigoIngresado.setEnabled(false);
        descripcionIngresada.setEnabled(false);
        vendedorIngresado.setEnabled(false);
        marcaIngresada.setEnabled(false);
        unidadMedidaIngresada.setEnabled(false);
        costoIngresado.setEnabled(false);
        cantidadIngresada.setEnabled(false);
        fechaIngresada.setEnabled(false);
        areaSeleccionada.setEnabled(false);
        radioBotonMateriales.setEnabled(false);
        radioBotonInstrumentos.setEnabled(false);
        radioBotonEquipos.setEnabled(false);
        radioBotonInstrumentosCirugia.setEnabled(false);
        radioBotonMoviliario.setEnabled(false);
        botonGuardar.setEnabled(false);
        botonModificar.setEnabled(false);
        botonEliminar.setEnabled(false);
        
        codigoIngresado.setText("");
        descripcionIngresada.setText("");
        vendedorIngresado.setText("");
        marcaIngresada.setText("");
        unidadMedidaIngresada.setText("");
        costoIngresado.setText("");
        cantidadIngresada.setText("");
        fechaIngresada.setCalendar(null);
    }
    
    private void habilitarCampos(){
        codigoIngresado.setEnabled(true);
        codigoIngresado.requestFocus();
        
        descripcionIngresada.setEnabled(true);
        vendedorIngresado.setEnabled(true);
        marcaIngresada.setEnabled(true);
        unidadMedidaIngresada.setEnabled(true);
        costoIngresado.setEnabled(true);
        cantidadIngresada.setEnabled(true);
        fechaIngresada.setEnabled(true);
        areaSeleccionada.setEnabled(true);
        radioBotonMateriales.setEnabled(true);
        radioBotonInstrumentos.setEnabled(true);
        radioBotonEquipos.setEnabled(true);
        radioBotonInstrumentosCirugia.setEnabled(true);
        radioBotonMoviliario.setEnabled(true);
        botonGuardar.setEnabled(true);
    }
    
    private String categoriaSeleccionada(){
        String categoria;
        if (radioBotonMateriales.isSelected()){
            categoria = "Materiales";
        } else if (radioBotonEquipos.isSelected()){
            categoria = "Equipos";
        }else if (radioBotonInstrumentos.isSelected()){
            categoria = "Instrumentos";
        } else if (radioBotonMoviliario.isSelected()){
            categoria = "Moviliario";
        } else if (radioBotonInstrumentosCirugia.isSelected()){
            categoria = "Instrumentos Cirugía";
        } else {
            categoria = "Sin categoria";
        }
        return categoria;
    }
    
    private void devolverBoton(String nombreBoton){
        switch (nombreBoton) {
            case "Materiales":
                radioBotonMateriales.setSelected(true);
                break;
            case "Equipos":
                radioBotonEquipos.setSelected(true);
                break;
            case "Instrumentos":
                radioBotonInstrumentos.setSelected(true);
                break;
            case "Moviliario":
                radioBotonMoviliario.setSelected(true);
                break;
            case "Instrumentos Cirugía":
                radioBotonInstrumentosCirugia.setSelected(true);
                break;
            default:
                break;
        }
        
    }
    
    @SuppressWarnings("deprecation")
    private void RegistrarProducto(){
        boolean costoCorrecto = false;
        boolean cantidadCorrecta = false;
        boolean fechaCorrecta = false;
        if(!"".equals(codigoIngresado.getText()) && !"".equals(descripcionIngresada.getText()) && !"".equals(vendedorIngresado.getText()) && !"".equals(marcaIngresada.getText()) && !"".equals(unidadMedidaIngresada.getText()) && !"".equals(costoIngresado.getText()) && !"".equals(cantidadIngresada.getText())){
            producto.setCodigo(codigoIngresado.getText());
            producto.setDescripcion(descripcionIngresada.getText());
            producto.setVendedor(vendedorIngresado.getText());
            producto.setMarca(marcaIngresada.getText());
            producto.setUnidadMedida(unidadMedidaIngresada.getText());
            try {
                producto.setCostoUnitario(Double.parseDouble(costoIngresado.getText()));
                costoCorrecto = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Formato de costo inválido: "+e.toString(), "Ingresar Datos", JOptionPane.WARNING_MESSAGE);
            }
            
            try {
                producto.setCantidad(Integer.parseInt(cantidadIngresada.getText()));
                cantidadCorrecta = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Formato de cantidad inválido: "+e.toString(), "Ingresar Datos", JOptionPane.WARNING_MESSAGE);
            }
            
            try {
                Date fecha = fechaIngresada.getDate();
                long tiempo = fecha.getTime();
                java.sql.Date fechaObtenida = new java.sql.Date(tiempo);
                producto.setFechaVencimiento(fechaObtenida);
                fechaCorrecta = true;
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una fecha válida", "Ingresar Datos", JOptionPane.ERROR_MESSAGE);
            }
            
            producto.setAreaDestinada(areaSeleccionada.getSelectedItem().toString());
            producto.setCategoria(categoriaSeleccionada());
            if(costoCorrecto == true && cantidadCorrecta == true && fechaCorrecta == true){
                productoSQL.RegistrarProducto(producto);
                JOptionPane.showMessageDialog(null, "Producto Registrado");
                deshabilitarCampos();
            }
        }
    }
    
    private void ListarProductos(){
        @SuppressWarnings("unchecked")
        ArrayList<Producto> ListaProductos = productoSQL.ListarProducto();
        modelo = (DefaultTableModel) tablaProductos.getModel();
        Object[] objeto = new Object[10];
        for (int i = 0; i < ListaProductos.size(); i++) {
            objeto[0] = ListaProductos.get(i).getCodigo();
            objeto[1] = ListaProductos.get(i).getDescripcion();
            objeto[2] = ListaProductos.get(i).getVendedor();
            objeto[3] = ListaProductos.get(i).getMarca();
            objeto[4] = ListaProductos.get(i).getUnidadMedida();
            objeto[5] = "S/"+ListaProductos.get(i).getCostoUnitario();
            objeto[6] = ListaProductos.get(i).getCantidad();
            objeto[7] = ListaProductos.get(i).getFechaVencimiento();
            objeto[8] = ListaProductos.get(i).getAreaDestinada();
            objeto[9] = ListaProductos.get(i).getCategoria();
            modelo.addRow(objeto);
        }
        tablaProductos.setModel(modelo);
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
            Sheet hoja = libro.createSheet("Reporte Productos");
 
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
                celdaTitulo.setCellValue("Reporte de Productos");

                hoja.addMergedRegion(new CellRangeAddress(0, 1, 0, 9));

                String[] cabeceraTabla = new String[]{"Código", "Descripción", "Vendedor", "Marca", "Unidad/Medida", "Costo Unitario", "Cantidad x Caja","Fecha Vencimiento", "Area", "Categoría"};

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

                int numCol = tablaProductos.getColumnCount();
                for (int i = 0; i < tablaProductos.getRowCount(); i++) {
                    Row filaDatos = hoja.createRow(numFilaDatos);
                    for (int j = 0; j < numCol; j++) {
                        Cell celdaDatos = filaDatos.createCell(j);
                        celdaDatos.setCellStyle(datosEstilo);
                        celdaDatos.setCellValue(tablaProductos.getValueAt(i, j).toString());
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
                hoja.autoSizeColumn(9);

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

            // Código para generar el archivo PDF
            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(filePath));
                documento.open();
                
                Image img = Image.getInstance(getClass().getResource("/Imagenes/jesedsalud.png"));
                Paragraph fecha = new Paragraph();
                com.itextpdf.text.Font blanco = new Font(Font.getFamily("Microsoft YaHei"), 6, Font.BOLD, BaseColor.WHITE);
                com.itextpdf.text.Font negro = new Font(Font.getFamily("Microsoft YaHei"), 6, Font.BOLD, BaseColor.BLACK);
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
                encabezado.addCell("REPORTE DE PRODUCTOS");
                encabezado.addCell(fecha);
                documento.add(encabezado);

                Paragraph entradas = new Paragraph();
                entradas.add(Chunk.NEWLINE);
                entradas.add("Detalle de los productos: \n\n");
                documento.add(entradas);

                PdfPTable tablaDeProductos = new PdfPTable(9);
                tablaDeProductos.setWidthPercentage(100);
                tablaDeProductos.getDefaultCell().setBorder(0);
                //30f, 80f, 30f, 30f, 50f, 30f, 60f, 80f, 60f
                float[] columnaEntradas = new float[]{25f, 100f, 30f, 30f, 60f, 20f, 35f, 75f, 30f};
                tablaDeProductos.setWidths(columnaEntradas);
                tablaDeProductos.setHorizontalAlignment(Element.ALIGN_LEFT);
                PdfPCell codigo = new PdfPCell(new Phrase("Código Producto", blanco));
                PdfPCell descripcion = new PdfPCell(new Phrase("Descripción", blanco));
                PdfPCell vendedor = new PdfPCell(new Phrase("Vendedor", blanco));
                PdfPCell marca = new PdfPCell(new Phrase("Marca", blanco));
                PdfPCell unidadMedida = new PdfPCell(new Phrase("Unidad Medida", blanco));
                PdfPCell costoUnitario = new PdfPCell(new Phrase("Costo", blanco));
                PdfPCell fechaVencimiento = new PdfPCell(new Phrase("Fecha Vencimiento", blanco));
                PdfPCell area = new PdfPCell(new Phrase("Área", blanco));
                PdfPCell categoria = new PdfPCell(new Phrase("Categoría", blanco));

                BaseColor colorCeldas = new BaseColor(181, 12, 97);
            
                codigo.setBackgroundColor(colorCeldas);
                descripcion.setBackgroundColor(colorCeldas);
                vendedor.setBackgroundColor(colorCeldas);
                marca.setBackgroundColor(colorCeldas);
                unidadMedida.setBackgroundColor(colorCeldas);
                costoUnitario.setBackgroundColor(colorCeldas);
                fechaVencimiento.setBackgroundColor(colorCeldas);
                area.setBackgroundColor(colorCeldas);
                categoria.setBackgroundColor(colorCeldas);

                tablaDeProductos.addCell(codigo);
                tablaDeProductos.addCell(descripcion);
                tablaDeProductos.addCell(vendedor);
                tablaDeProductos.addCell(marca);
                tablaDeProductos.addCell(unidadMedida);
                tablaDeProductos.addCell(costoUnitario);
                tablaDeProductos.addCell(fechaVencimiento);
                tablaDeProductos.addCell(area);
                tablaDeProductos.addCell(categoria);

                for (int i = 0; i < tablaProductos.getRowCount(); i++) {
                    String codigoProductoTabla = tablaProductos.getValueAt(i, 0).toString();
                    String descripcionTabla = tablaProductos.getValueAt(i, 1).toString();
                    String vendedorTabla = tablaProductos.getValueAt(i, 2).toString();
                    String marcaTabla = tablaProductos.getValueAt(i, 3).toString();
                    String unidadMedidaTabla = tablaProductos.getValueAt(i, 4).toString();
                    String costoTabla = tablaProductos.getValueAt(i, 5).toString();
                    String fechaVencimientoTabla = tablaProductos.getValueAt(i, 7).toString();
                    String areaTabla = tablaProductos.getValueAt(i, 8).toString();
                    String categoriaTabla = tablaProductos.getValueAt(i, 9).toString();

                    PdfPCell codigoPDF = new PdfPCell(new Phrase(codigoProductoTabla, negro));
                    PdfPCell descripcionPDF = new PdfPCell(new Phrase(descripcionTabla, negro));
                    PdfPCell vendedorPDF = new PdfPCell(new Phrase(vendedorTabla, negro));
                    PdfPCell marcaPDF = new PdfPCell(new Phrase(marcaTabla, negro));
                    PdfPCell unidadMedidaPDF = new PdfPCell(new Phrase(unidadMedidaTabla, negro));
                    PdfPCell costoUnitarioPDF = new PdfPCell(new Phrase(costoTabla, negro));
                    PdfPCell fechaVencimientoPDF = new PdfPCell(new Phrase(fechaVencimientoTabla, negro));
                    PdfPCell areaPDF = new PdfPCell(new Phrase(areaTabla, negro));
                    PdfPCell categoriaPDF = new PdfPCell(new Phrase(categoriaTabla, negro));

                    tablaDeProductos.addCell(codigoPDF);
                    tablaDeProductos.addCell(descripcionPDF);
                    tablaDeProductos.addCell(vendedorPDF);
                    tablaDeProductos.addCell(marcaPDF);
                    tablaDeProductos.addCell(unidadMedidaPDF);
                    tablaDeProductos.addCell(costoUnitarioPDF);
                    tablaDeProductos.addCell(fechaVencimientoPDF);
                    tablaDeProductos.addCell(areaPDF);
                    tablaDeProductos.addCell(categoriaPDF);
                }

                documento.add(tablaDeProductos);

                documento.close();

                // Abrir el archivo PDF con la aplicación predeterminada
                File pdfFile = new File(filePath);
                Desktop.getDesktop().open(pdfFile);

                JOptionPane.showMessageDialog(null, "PDF generado correctamente en: " + filePath, "Reporte PDF", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | HeadlessException | IOException e) {
                JOptionPane.showMessageDialog(null, "Error al generar o abrir el PDF", "Reporte PDF", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        codigoIngresado = new javax.swing.JTextField();
        descripcionIngresada = new javax.swing.JTextField();
        costoIngresado = new javax.swing.JTextField();
        vendedorIngresado = new javax.swing.JTextField();
        marcaIngresada = new javax.swing.JTextField();
        unidadMedidaIngresada = new javax.swing.JTextField();
        botonNuevo = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        areaSeleccionada = new javax.swing.JComboBox<>();
        radioBotonMoviliario = new javax.swing.JRadioButton();
        radioBotonEquipos = new javax.swing.JRadioButton();
        radioBotonInstrumentos = new javax.swing.JRadioButton();
        radioBotonMateriales = new javax.swing.JRadioButton();
        radioBotonInstrumentosCirugia = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        botonReportePDF = new javax.swing.JButton();
        fechaIngresada = new com.toedter.calendar.JDateChooser();
        botonReporteExcel = new javax.swing.JButton();
        botonAlertas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cantidadIngresada = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei", 1, 18)); // NOI18N
        jLabel10.setText("Registro de Productos");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 17, -1, -1));

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel11.setText("Llene la información respectiva de los productos.");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 53, -1, -1));

        jLabel12.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel12.setText("Código de Producto:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 88, -1, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel13.setText("Descripción:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 88, -1, -1));

        jLabel14.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel14.setText("Fecha Vencimiento:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, -1, -1));

        jLabel15.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel15.setText("Vendedor:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 90, -1, -1));

        jLabel16.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel16.setText("Marca:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 90, -1, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel17.setText("Unidad/Medida:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel18.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel18.setText("Costo Unitario:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, -1, -1));
        jPanel1.add(codigoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 123, 130, -1));
        jPanel1.add(descripcionIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 123, 320, -1));
        jPanel1.add(costoIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 151, -1));
        jPanel1.add(vendedorIngresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 120, 140, -1));
        jPanel1.add(marcaIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, 130, -1));
        jPanel1.add(unidadMedidaIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 120, -1));

        botonNuevo.setBackground(new java.awt.Color(255, 255, 254));
        botonNuevo.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_nuevo.png"))); // NOI18N
        botonNuevo.setText("Nuevo");
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(botonNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, -1, -1));

        botonGuardar.setBackground(new java.awt.Color(255, 255, 254));
        botonGuardar.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_grabar.png"))); // NOI18N
        botonGuardar.setText("Guardar");
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(botonGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 480, -1, -1));

        botonModificar.setBackground(new java.awt.Color(255, 255, 254));
        botonModificar.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_modificar.png"))); // NOI18N
        botonModificar.setText("Modificar");
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });
        jPanel1.add(botonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 480, -1, -1));

        botonEliminar.setBackground(new java.awt.Color(255, 255, 254));
        botonEliminar.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ic_eliminar.png"))); // NOI18N
        botonEliminar.setText("Eliminar");
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(botonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 480, -1, -1));

        jLabel19.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel19.setText("Area:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 160, -1, 23));

        jLabel20.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel20.setText("Categoría:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        areaSeleccionada.setBackground(new java.awt.Color(255, 255, 254));
        areaSeleccionada.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        areaSeleccionada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "OPERATORIA (RESTAURACIONES)", "CIRUGIA", "ORTODONCIA", "ODONTOPEDIATRIA", "RADIOLOGIA", "REHABILITACION GENERAL", "LIMPIEZA", "ADMINISTRATIVA", "MARKETING", "FOTOGRAFIA", "INMOVILIARIO", "PERIDONCIA", "INPLANTOLOGIA", "PREVENTIVO", "BIOSEGURIDAD (ESTERILIZACION)", "MANTENIMIENTO EQUIPOS" }));
        jPanel1.add(areaSeleccionada, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 190, 210, -1));

        radioBotonMoviliario.setBackground(new java.awt.Color(255, 255, 255));
        radioBotonMoviliario.setText("Moviliario");
        jPanel1.add(radioBotonMoviliario, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 230, -1, -1));

        radioBotonEquipos.setBackground(new java.awt.Color(255, 255, 255));
        radioBotonEquipos.setText("Equipos");
        jPanel1.add(radioBotonEquipos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, -1, -1));

        radioBotonInstrumentos.setBackground(new java.awt.Color(255, 255, 255));
        radioBotonInstrumentos.setText("Instrumentos");
        jPanel1.add(radioBotonInstrumentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        radioBotonMateriales.setBackground(new java.awt.Color(255, 255, 255));
        radioBotonMateriales.setText("Materiales");
        jPanel1.add(radioBotonMateriales, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, -1));

        radioBotonInstrumentosCirugia.setBackground(new java.awt.Color(255, 255, 255));
        radioBotonInstrumentosCirugia.setText("Instrumentos/Cirugía");
        jPanel1.add(radioBotonInstrumentosCirugia, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, -1));

        tablaProductos.setBackground(new java.awt.Color(255, 255, 254));
        tablaProductos.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaProductos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 280, 910, 170));

        botonReportePDF.setBackground(new java.awt.Color(255, 255, 254));
        botonReportePDF.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonReportePDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        botonReportePDF.setText("Reporte PDF");
        botonReportePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReportePDFActionPerformed(evt);
            }
        });
        jPanel1.add(botonReportePDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        fechaIngresada.setBackground(new java.awt.Color(255, 255, 254));
        jPanel1.add(fechaIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 190, 140, -1));

        botonReporteExcel.setBackground(new java.awt.Color(255, 255, 254));
        botonReporteExcel.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonReporteExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excel.png"))); // NOI18N
        botonReporteExcel.setText("Reporte Excel");
        botonReporteExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReporteExcelActionPerformed(evt);
            }
        });
        jPanel1.add(botonReporteExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, -1, -1));

        botonAlertas.setBackground(new java.awt.Color(255, 255, 254));
        botonAlertas.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        botonAlertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/alerta.png"))); // NOI18N
        botonAlertas.setText("Alertas");
        botonAlertas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAlertasActionPerformed(evt);
            }
        });
        jPanel1.add(botonAlertas, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel1.setText("Cantidad:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, -1, -1));

        cantidadIngresada.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jPanel1.add(cantidadIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 190, 130, -1));

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

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        deshabilitarCampos();
        habilitarCampos();
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        RegistrarProducto();
        LimpiarTabla();
        ListarProductos();
    }//GEN-LAST:event_botonGuardarActionPerformed

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        if("".equals(codigoProducto)){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro de la tabla", "Eliminar Registro", JOptionPane.WARNING_MESSAGE);
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?");
            if(pregunta==0){
                String codigo = codigoIngresado.getText();
                productoSQL.EliminarProducto(codigo);
                LimpiarTabla();
                ListarProductos();
                deshabilitarCampos();
            }
        }
        
        botonNuevo.setEnabled(true);
    }//GEN-LAST:event_botonEliminarActionPerformed

    @SuppressWarnings("deprecation")
    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        if("".equals(codigoProducto)){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro de la tabla", "Modificar Registro", JOptionPane.WARNING_MESSAGE);
        } else {
            boolean costoCorrecto = false;
            boolean cantidadCorrecta = false;
            boolean fechaCorrecta = false;
            Date fecha = fechaIngresada.getDate();
            long tiempo = fecha.getTime();
            java.sql.Date fechaObtenida = new java.sql.Date(tiempo);
            if(!"".equals(codigoIngresado.getText()) && !"".equals(descripcionIngresada.getText()) && !"".equals(vendedorIngresado.getText()) && !"".equals(marcaIngresada.getText()) && !"".equals(unidadMedidaIngresada.getText()) && !"".equals(costoIngresado.getText()) && !"".equals(cantidadIngresada.getText())){
                producto.setDescripcion(descripcionIngresada.getText());
                producto.setVendedor(vendedorIngresado.getText());
                producto.setMarca(marcaIngresada.getText());
                producto.setUnidadMedida(unidadMedidaIngresada.getText());

                try {
                    producto.setCostoUnitario(Double.parseDouble(costoIngresado.getText()));
                    costoCorrecto = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Formato de costo inválido: "+e.toString(), "Error al Ingresar Datos", JOptionPane.WARNING_MESSAGE);
                }

                try {
                    producto.setCantidad(Integer.parseInt(cantidadIngresada.getText()));
                    cantidadCorrecta = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Formato de cantidad inválido: "+e.toString(), "Ingresar Datos", JOptionPane.WARNING_MESSAGE);
                }
                
                try {
                    producto.setFechaVencimiento(fechaObtenida);
                    fechaCorrecta = true;
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una fecha válida", "Error al Ingresar Datos", JOptionPane.ERROR_MESSAGE);
                }

                producto.setAreaDestinada(areaSeleccionada.getSelectedItem().toString());
                producto.setCategoria(categoriaSeleccionada());
                producto.setCodigo(codigoIngresado.getText());
                if(costoCorrecto == true && cantidadCorrecta == true && fechaCorrecta == true){
                    productoSQL.ModificarProducto(producto);
                    JOptionPane.showMessageDialog(null, "Producto Modificado");
                    LimpiarTabla();
                    ListarProductos();
                    deshabilitarCampos();
                }
            }
        }
        
        botonNuevo.setEnabled(true);
    }//GEN-LAST:event_botonModificarActionPerformed

    private void botonReportePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReportePDFActionPerformed
        ReportePDF();
    }//GEN-LAST:event_botonReportePDFActionPerformed

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
        botonNuevo.setEnabled(false);
        botonModificar.setEnabled(true);
        botonEliminar.setEnabled(true);
        
        Pattern filtro = Pattern.compile("\\d+(\\.\\d+)?");
        habilitarCampos();
        codigoIngresado.setEnabled(false);
        botonGuardar.setEnabled(false);
        int filaSeleccionada = tablaProductos.rowAtPoint(evt.getPoint());
        java.sql.Date fechaObtenida = (java.sql.Date) tablaProductos.getValueAt(filaSeleccionada, 7);
        long tiempo = fechaObtenida.getTime();
        Date fecha = new Date(tiempo);
        codigoProducto = modelo.getValueAt(filaSeleccionada, 0).toString();
        
        codigoIngresado.setText(tablaProductos.getValueAt(filaSeleccionada, 0).toString());
        descripcionIngresada.setText(tablaProductos.getValueAt(filaSeleccionada, 1).toString());
        vendedorIngresado.setText(tablaProductos.getValueAt(filaSeleccionada, 2).toString());
        marcaIngresada.setText(tablaProductos.getValueAt(filaSeleccionada, 3).toString());
        unidadMedidaIngresada.setText(tablaProductos.getValueAt(filaSeleccionada, 4).toString());
        Matcher convertir = filtro.matcher((CharSequence) tablaProductos.getValueAt(filaSeleccionada, 5));
        if (convertir.find()){
            costoIngresado.setText(convertir.group());
        }
        cantidadIngresada.setText(tablaProductos.getValueAt(filaSeleccionada, 6).toString());
        fechaIngresada.setDate(fecha);
        areaSeleccionada.setSelectedItem(tablaProductos.getValueAt(filaSeleccionada, 7));
        devolverBoton(tablaProductos.getValueAt(filaSeleccionada, 8).toString());
    }//GEN-LAST:event_tablaProductosMouseClicked

    private void botonReporteExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReporteExcelActionPerformed
        ReporteExcel();
    }//GEN-LAST:event_botonReporteExcelActionPerformed

    private void botonAlertasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAlertasActionPerformed
        FrameVencimiento fv = new FrameVencimiento();
        fv.setVisible(true);
    }//GEN-LAST:event_botonAlertasActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> areaSeleccionada;
    private javax.swing.JButton botonAlertas;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonReporteExcel;
    private javax.swing.JButton botonReportePDF;
    private javax.swing.JTextField cantidadIngresada;
    private javax.swing.JTextField codigoIngresado;
    private javax.swing.JTextField costoIngresado;
    private javax.swing.JTextField descripcionIngresada;
    private com.toedter.calendar.JDateChooser fechaIngresada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField marcaIngresada;
    private javax.swing.JRadioButton radioBotonEquipos;
    private javax.swing.JRadioButton radioBotonInstrumentos;
    private javax.swing.JRadioButton radioBotonInstrumentosCirugia;
    private javax.swing.JRadioButton radioBotonMateriales;
    private javax.swing.JRadioButton radioBotonMoviliario;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField unidadMedidaIngresada;
    private javax.swing.JTextField vendedorIngresado;
    // End of variables declaration//GEN-END:variables
}