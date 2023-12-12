package ConexionBD;

import Clases.MovimientoProducto;
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
public class SalidaDAO {
    Conectar conectar = new Conectar();
    Connection conexion;
    PreparedStatement PS;
    ResultSet RS;
    
    public boolean RegistrarSalida(MovimientoProducto salida){
        String sql = "INSERT INTO MovimientosProductos(tipoMovimiento, IDProducto, descripcion, fecha, cantidad, porciones) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            PS.setString(1, salida.getTipoMovimiento());
            PS.setString(2, salida.getCodigoProducto());
            PS.setString(3, salida.getDescripcion());
            PS.setDate(4,(Date)salida.getFecha());
            PS.setInt(5, salida.getCantidad());
            PS.setInt(6, salida.getPorciones());
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar la salida: "+e.toString(), "Registrar Salidas", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }
    
    public ArrayList ListarSalidas(){
        @SuppressWarnings("unchecked")
        ArrayList<MovimientoProducto> ListaSalidas = new ArrayList();
        String sql = "SELECT * FROM MovimientosProductos WHERE tipoMovimiento = 'Salida';";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()) {                
                MovimientoProducto salida = new MovimientoProducto();
                salida.setCodigoMovimiento(RS.getInt("movimientoID"));
                salida.setTipoMovimiento(RS.getString("tipoMovimiento"));
                salida.setCodigoProducto(RS.getString("IDProducto"));
                salida.setDescripcion(RS.getString("descripcion"));
                salida.setFecha(RS.getDate("fecha"));
                salida.setCantidad(RS.getInt("cantidad"));
                salida.setPorciones(RS.getInt("porciones"));
                ListaSalidas.add(salida);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo listar las entradas de productos: "+e.toString(), "Listar Producto", JOptionPane.ERROR_MESSAGE);
        }
        return ListaSalidas;
    }
    
    public boolean EliminarSalida(int salidaID){
        String sql = "DELETE FROM MovimientosProductos WHERE movimientoID=?";
        try {
            PS = conexion.prepareStatement(sql);
            PS.setInt(1, salidaID);
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro: "+e.toString(), "Eliminar Salida", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }  
}
