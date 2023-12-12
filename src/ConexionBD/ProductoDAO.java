package ConexionBD;

import Clases.Producto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author DARLIN
 */
public class ProductoDAO {
    Conectar conectar = new Conectar();
    Connection conexion;
    PreparedStatement PS;
    ResultSet RS;
    
    public boolean RegistrarProducto(Producto producto){
        String sql = "CALL RegistrarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            PS.setString(1, producto.getCodigo());
            PS.setString(2, producto.getDescripcion());
            PS.setString(3, producto.getVendedor());
            PS.setString(4, producto.getMarca());
            PS.setString(5, producto.getUnidadMedida());
            PS.setDouble(6, producto.getCostoUnitario());
            PS.setInt(7, producto.getCantidad());
            PS.setDate(8, (Date)producto.getFechaVencimiento());
            PS.setString(9, producto.getAreaDestinada());
            PS.setString(10, producto.getCategoria());
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el producto: "+e.toString(), "Registrar Producto", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }
    
    public ArrayList ListarProducto(){
        @SuppressWarnings("unchecked")
        ArrayList<Producto> ListaProductos = new ArrayList();
        String sql = "SELECT * FROM Productos";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()) {                
                Producto producto = new Producto();
                producto.setCodigo(RS.getString("productoID"));
                producto.setDescripcion(RS.getString("descripcion"));
                producto.setVendedor(RS.getString("vendedor"));
                producto.setMarca(RS.getString("marca"));
                producto.setUnidadMedida(RS.getString("unidadMedida"));
                producto.setCostoUnitario(RS.getDouble("costoUnitario"));
                producto.setCantidad(RS.getInt("cantidad"));
                producto.setFechaVencimiento(RS.getDate("fechaVencimiento"));
                producto.setAreaDestinada(RS.getString("areaProducto"));
                producto.setCategoria(RS.getString("categoriaProducto"));
                ListaProductos.add(producto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo listar los productos: "+e.toString(), "Listar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return ListaProductos;
    }

    public boolean ModificarProducto(Producto producto){
        String sqlProductos = "UPDATE Productos SET descripcion=?, vendedor=?, marca=?, unidadMedida=?, costoUnitario=?, cantidad=?, fechaVencimiento=?, areaProducto=?, categoriaProducto=? WHERE productoID=?";
        try {
            PS = conexion.prepareStatement(sqlProductos);
            PS.setString(1, producto.getDescripcion());
            PS.setString(2, producto.getVendedor());
            PS.setString(3, producto.getMarca());
            PS.setString(4, producto.getUnidadMedida());
            PS.setDouble(5, producto.getCostoUnitario());
            PS.setDouble(6, producto.getCantidad());
            PS.setDate(7, (Date)producto.getFechaVencimiento());
            PS.setString(8, producto.getAreaDestinada());
            PS.setString(9, producto.getCategoria());
            PS.setString(10, producto.getCodigo());
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el producto: "+e.toString(), "Modificar Producto", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }
    
    public boolean EliminarProducto(String productoID){
        String sql = "CALL EliminarProducto(?)";
        try {
            PS = conexion.prepareStatement(sql);
            PS.setString(1, productoID);
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro: "+e.toString(), "Eliminar Producto", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }
    
    public Producto BuscarProducto(String productoID){
        Producto producto = new Producto();
        String sqlProducto = "SELECT * FROM Productos WHERE productoID=?";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sqlProducto);
            PS.setString(1, productoID);
            RS = PS.executeQuery();
            if (RS.next()){
                producto.setCodigo(RS.getString("productoID"));
                producto.setDescripcion(RS.getString("descripcion"));
                producto.setVendedor(RS.getString("vendedor"));
                producto.setMarca(RS.getString("marca"));
                producto.setUnidadMedida(RS.getString("unidadMedida"));
                producto.setCostoUnitario(RS.getDouble("costoUnitario"));
                producto.setCantidad(RS.getInt("cantidad"));
                producto.setFechaVencimiento(RS.getDate("fechaVencimiento"));
                producto.setAreaDestinada(RS.getString("areaProducto"));
                producto.setCategoria(RS.getString("categoriaProducto"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el producto: "+e.toString(), "Buscar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return producto;
    }
    
    public int CantidadVencimientoProductos() {
        int cantidad = 0;
        String sql = "SELECT COUNT(productoID) AS cantidadVencimiento FROM Productos WHERE fechaVencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 3 MONTH);";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();

            if (RS.next()) {
                cantidad = RS.getInt("cantidadVencimiento");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo ejecutar la consulta: " + e.toString(), "Buscar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return cantidad;
    }
    
    public ArrayList ListarProductosVencimiento(){
        @SuppressWarnings("unchecked")
        ArrayList<Producto> ListaProductos = new ArrayList();
        String sql = "SELECT * FROM Productos WHERE fechaVencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 3 MONTH);";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()) {                
                Producto producto = new Producto();
                producto.setCodigo(RS.getString("productoID"));
                producto.setDescripcion(RS.getString("descripcion"));
                producto.setVendedor(RS.getString("vendedor"));
                producto.setMarca(RS.getString("marca"));
                producto.setUnidadMedida(RS.getString("unidadMedida"));
                producto.setCostoUnitario(RS.getDouble("costoUnitario"));
                producto.setFechaVencimiento(RS.getDate("fechaVencimiento"));
                producto.setAreaDestinada(RS.getString("areaProducto"));
                producto.setCategoria(RS.getString("categoriaProducto"));
                ListaProductos.add(producto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo listar los productos: "+e.toString(), "Listar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return ListaProductos;
    }
}

