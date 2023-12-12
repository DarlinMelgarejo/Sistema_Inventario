package ConexionBD;

import ConexionBD.Conectar;
import Clases.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author DARLIN
 */
public class UsuarioDAO {
    Conectar conectar = new Conectar();
    Connection conexion;
    PreparedStatement PS;
    ResultSet RS;
    
    public boolean RegistrarUsuario(Usuario usuario){
        String sql = "INSERT INTO Usuarios(nombres, apellidos, puestoCargo, nombreUsuario, contraseñaUsuario) VALUES (?, ?, ?, ?, ?)";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            PS.setString(1, usuario.getNombres());
            PS.setString(2, usuario.getApellidos());
            PS.setString(3, usuario.getPuesto());
            PS.setString(4, usuario.getNombreUsuario());
            PS.setString(5, usuario.getContraseña());
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el usuario: "+e.toString(), "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
            return false;
        } 
    }
    
    public ArrayList ListarUsuarios(){
        @SuppressWarnings("unchecked")
        ArrayList<Usuario> ListaUsuarios = new ArrayList();
        String sql = "SELECT * FROM Usuarios";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            RS = PS.executeQuery();
            while (RS.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioID(RS.getInt("usuarioID"));
                usuario.setNombres(RS.getString("nombres"));
                usuario.setApellidos(RS.getString("apellidos"));
                usuario.setPuesto(RS.getString("puestoCargo"));
                usuario.setNombreUsuario(RS.getString("nombreUsuario"));
                usuario.setContraseña(RS.getString("contraseñaUsuario"));
                ListaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "No se pudo listar los usuarios: "+e.toString(), "Listar Usuarios", JOptionPane.ERROR_MESSAGE);
        }
        return ListaUsuarios;
    }
    
    public boolean EliminarUsuario(int usuarioID){
        String sql = "DELETE FROM Usuarios WHERE usuarioID = ?";
        try {
            PS = conexion.prepareStatement(sql);
            PS.setInt(1, usuarioID);
            PS.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro: "+e.toString(), "Eliminar Usuario", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            conectar.desconectar();
        }
    }
}
