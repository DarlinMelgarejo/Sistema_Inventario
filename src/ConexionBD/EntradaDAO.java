package ConexionBD;

import ConexionBD.Conectar;
import Clases.MovimientoProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author DARLIN
 */
public class EntradaDAO {
    Conectar conectar = new Conectar();
    Connection conexion;
    PreparedStatement PS;
    ResultSet RS;
    
    public boolean RegistrarEntrada(MovimientoProducto entrada){
        String sql = "INSERT INTO MovimientosProductos(tipoMovimiento, IDProducto, descripcion, fecha, cantidad, porciones) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            PS.setString(1, entrada.getTipoMovimiento());
            PS.setString(2, entrada.getCodigoProducto());
            PS.setString(3, entrada.getDescripcion());
            PS.setDate(4,(Date)entrada.getFecha());
            PS.setInt(5, entrada.getCantidad());
            PS.setInt(6, entrada.getPorciones());
            PS.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar la entrada: "+e.toString(), "Registrar Entradas", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }
    
    public ArrayList ListarEntradas(){
        @SuppressWarnings("unchecked")
        ArrayList<MovimientoProducto> ListaEntradas = new ArrayList();
        String sql = "SELECT * FROM MovimientosProductos WHERE tipoMovimiento = 'Entrada';";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()) {                
                MovimientoProducto entrada = new MovimientoProducto();
                entrada.setCodigoMovimiento(RS.getInt("movimientoID"));
                entrada.setCodigoProducto(RS.getString("IDProducto"));
                entrada.setDescripcion(RS.getString("descripcion"));
                entrada.setFecha(RS.getDate("fecha"));
                entrada.setCantidad(RS.getInt("cantidad"));
                entrada.setPorciones(RS.getInt("porciones"));
                ListaEntradas.add(entrada);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo listar las entradas de productos: "+e.toString(), "Listar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return ListaEntradas;
    }
    
    public boolean EliminarEntrada(int entradaID){
        String sql = "DELETE FROM EntradaProductos WHERE movimientoID=?";
        try {
            PS = conexion.prepareStatement(sql);
            PS.setInt(1, entradaID);
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro: "+e.toString(), "Eliminar Entrada", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }
    
    public MovimientoProducto BuscarEntrada(String productoID) {
        MovimientoProducto entrada = new MovimientoProducto();
        String sqlEntrada = "SELECT * FROM MovimientosProductos WHERE IDProducto=? AND tipoMovimiento = 'Entrada';";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sqlEntrada);
            PS.setString(1, productoID);
            RS = PS.executeQuery();
            if (RS.next()){
                entrada.setCodigoMovimiento(RS.getInt("movimientoID"));
                entrada.setTipoMovimiento(RS.getString("tipoMovimiento"));
                entrada.setCodigoProducto(RS.getString("IDProducto"));
                entrada.setDescripcion(RS.getString("descripcion"));
                entrada.setFecha(RS.getDate("fecha"));
                entrada.setCantidad(RS.getInt("cantidad"));
                entrada.setPorciones(RS.getInt("porciones"));
            }
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar la entrada: "+e.toString(), "Buscar Entrada", JOptionPane.ERROR_MESSAGE);
        }
        return entrada;
    }
}
