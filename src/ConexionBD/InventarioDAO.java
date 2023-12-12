package ConexionBD;

import ConexionBD.Conectar;
import Clases.InventarioInicial;
import Clases.InventarioFinal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * @author DARLIN
 */
public class InventarioDAO {
    Conectar conectar = new Conectar();
    Connection conexion;
    PreparedStatement PS;
    ResultSet RS;
    
    public ArrayList ListarInventario(){
        @SuppressWarnings("unchecked")
        ArrayList<InventarioFinal> ListaInventario = new ArrayList();
        String sql = "SELECT * FROM VistaInventario";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()) {                
                InventarioFinal inventario = new InventarioFinal();
                inventario.setCodigoProducto(RS.getString("productoID"));
                inventario.setDescripcionProducto(RS.getString("descripcion"));
                inventario.setAreaProducto(RS.getString("areaProducto"));
                inventario.setCategoriaProducto(RS.getString("categoriaProducto"));
                inventario.setCostoUnitario(RS.getDouble("costoUnitario"));
                inventario.setEntradas(RS.getInt("entradas"));
                inventario.setSalidas(RS.getInt("salidas"));
                inventario.setStock(RS.getInt("stock"));
                inventario.setCostoTotal(RS.getDouble("costoTotal"));
                ListaInventario.add(inventario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo listar el inventario: "+e.toString(), "Listar Inventario", JOptionPane.ERROR_MESSAGE);
        } finally {
            conectar.desconectar();
        }
        return ListaInventario;
    }
    
    public void ActualizarEntradas(){
        String IDProducto;
        int entradas;
        String sqlAgruparEntradas = "SELECT IDProducto, SUM(porciones) AS entradas FROM MovimientosProductos WHERE tipoMovimiento = 'Entrada' GROUP BY IDProducto ASC;";
        String sqlActualizarEntradas = "UPDATE InventarioProductos SET entradas = ? WHERE IDProducto = ?";
            try {
                conexion = conectar.getConnection();
                PS = conexion.prepareStatement(sqlAgruparEntradas);
                RS = PS.executeQuery();
                while (RS.next()) {     
                    IDProducto = RS.getString("IDProducto");
                    entradas = RS.getInt("entradas");
                    PS = conexion.prepareStatement(sqlActualizarEntradas);
                    PS.setDouble(1, entradas);
                    PS.setString(2, IDProducto);
                    PS.execute();
            }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar inventario: "+e.toString(), "Modificar Inventario", JOptionPane.ERROR_MESSAGE);
            } finally {
                conectar.desconectar();
            }
    }
    
    public void ActualizarSalidas(){
        String IDProducto;
        int salidas;
        String sqlAgruparSalidas = "SELECT IDProducto, SUM(porciones) AS salidas FROM MovimientosProductos WHERE tipoMovimiento = 'Salida' GROUP BY IDProducto ASC;";
        String sqlActualizarSalidas = "UPDATE InventarioProductos SET salidas = ? WHERE IDProducto = ?";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sqlAgruparSalidas);
            RS = PS.executeQuery();
            while (RS.next()) {     
                IDProducto = RS.getString("IDProducto");
                salidas = RS.getInt("salidas");
                PS = conexion.prepareStatement(sqlActualizarSalidas);
                PS.setDouble(1, salidas);
                PS.setString(2, IDProducto);
                PS.execute();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar inventario: "+e.toString(), "Modificar Inventario", JOptionPane.ERROR_MESSAGE);
        } finally {
            conectar.desconectar();
        }
    }
    
//    public void ActualizarCostoTotal(){
//        String codigoProducto;
//        int stock;
//        double costoTotal;
//        String sqlObtenerDatos = "SELECT * FROM VistaStockCostoTotal;";
//        String sqlActualizar = "UPDATE InventarioProductos SET stock=?, costoTotal=? WHERE IDProducto=?";
//        try {
//            conexion = conectar.getConnection();
//            PS = conexion.prepareStatement(sqlObtenerDatos);
//            RS = PS.executeQuery();
//            while (RS.next()) {     
//                codigoProducto = RS.getString("IDProducto");
//                stock = RS.getInt("Stock");
//                costoTotal = RS.getDouble("CostoTotal");
//                PS = conexion.prepareStatement(sqlActualizar);
//                PS.setInt(1, stock);
//                PS.setDouble(2, costoTotal);
//                PS.setString(3, codigoProducto);
//                PS.execute();
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "No se pudo actualizar inventario: "+e.toString(), "Modificar Inventario", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            conectar.desconectar();
//        }
//    }
    
    public InventarioInicial BuscarProducto(String productoID){
        InventarioInicial inventario = new InventarioInicial();
        String sql = "SELECT * FROM InventarioProductos WHERE IDProducto=?";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            PS.setString(1, productoID);
            RS = PS.executeQuery();
            if (RS.next()){
                inventario.setCodigoInventario(RS.getInt("inventarioID"));
                inventario.setCodigoProducto(RS.getString("IDProducto"));
                inventario.setEntradas(RS.getInt("entradas"));
                inventario.setSalidas(RS.getInt("salidas"));
                inventario.setStock(RS.getInt("stock"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el producto: "+e.toString(), "Buscar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return inventario;
    }
    
    public ArrayList BuscarInventario(String busqueda, String buscar){
        @SuppressWarnings("unchecked")
        ArrayList<InventarioFinal> BusquedaInventario = new ArrayList();
        String sql = "SELECT * FROM VistaInventario WHERE "+busqueda+" LIKE '%"+buscar+"%'";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()){
                InventarioFinal inventario = new InventarioFinal();
                inventario.setCodigoProducto(RS.getString("productoID"));
                inventario.setDescripcionProducto(RS.getString("descripcion"));
                inventario.setAreaProducto(RS.getString("areaProducto"));
                inventario.setCategoriaProducto(RS.getString("categoriaProducto"));
                inventario.setCostoUnitario(RS.getDouble("costoUnitario"));
                inventario.setEntradas(RS.getInt("entradas"));
                inventario.setSalidas(RS.getInt("salidas"));
                inventario.setStock(RS.getInt("stock"));
                inventario.setCostoTotal(RS.getDouble("costoTotal"));
                BusquedaInventario.add(inventario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el producto: "+e.toString(), "Buscar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return BusquedaInventario;
    }
    
    public int CantidadStockBajo() {
        int cantidad = 0;
        String sql = "SELECT COUNT(productoID) AS cantidadStockBajo FROM VistaInventario WHERE stock <=2;";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();

            if (RS.next()) {
                cantidad = RS.getInt("cantidadStockBajo");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo ejecutar la consulta: " + e.toString(), "Buscar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return cantidad;
    }
    
    public ArrayList ListarProductosStockBajo(){
        @SuppressWarnings("unchecked")
        ArrayList<InventarioFinal> ListaInventario = new ArrayList();
        String sql = "SELECT * FROM VistaInventario WHERE stock <=2;";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()) {                
                InventarioFinal inventario = new InventarioFinal();
                inventario.setCodigoProducto(RS.getString("productoID"));
                inventario.setDescripcionProducto(RS.getString("descripcion"));
                inventario.setAreaProducto(RS.getString("areaProducto"));
                inventario.setCategoriaProducto(RS.getString("categoriaProducto"));
                inventario.setCostoUnitario(RS.getDouble("costoUnitario"));
                inventario.setEntradas(RS.getInt("entradas"));
                inventario.setSalidas(RS.getInt("salidas"));
                inventario.setStock(RS.getInt("stock"));
                inventario.setCostoTotal(RS.getDouble("costoTotal"));
                ListaInventario.add(inventario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo listar el inventario: "+e.toString(), "Listar Inventario", JOptionPane.ERROR_MESSAGE);
        } finally {
            conectar.desconectar();
        }
        return ListaInventario;
    }
    
    public ArrayList OrganizarInventario(String area, String categoria){
        @SuppressWarnings("unchecked")
        ArrayList<InventarioFinal> BusquedaInventario = new ArrayList();
        String sql = "SELECT * FROM VistaInventario WHERE areaProducto = '"+area+"' AND categoriaProducto = '"+categoria+"';";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()){
                InventarioFinal inventario = new InventarioFinal();
                inventario.setCodigoProducto(RS.getString("productoID"));
                inventario.setDescripcionProducto(RS.getString("descripcion"));
                inventario.setAreaProducto(RS.getString("areaProducto"));
                inventario.setCategoriaProducto(RS.getString("categoriaProducto"));
                inventario.setCostoUnitario(RS.getDouble("costoUnitario"));
                inventario.setEntradas(RS.getInt("entradas"));
                inventario.setSalidas(RS.getInt("salidas"));
                inventario.setStock(RS.getInt("stock"));
                inventario.setCostoTotal(RS.getDouble("costoTotal"));
                BusquedaInventario.add(inventario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el producto: "+e.toString(), "Buscar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return BusquedaInventario;
    }
}
