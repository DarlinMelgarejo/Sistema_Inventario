package ConexionBD;

import ConexionBD.Conectar;
import Clases.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author DARLIN
 */
public class LoginDAO {
    Connection conexion;
    PreparedStatement PS;
    ResultSet RS;
    Conectar conectar = new Conectar();
    
    public Usuario usuario(String nombreUsuario, String contraseñaUsuario){
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM Usuarios WHERE nombreUsuario = ? AND contraseñaUsuario = ?";
        try {
            conexion = conectar.getConnection();
            PS = conexion.prepareStatement(sql);
            PS.setString(1, nombreUsuario);
            PS.setString(2, contraseñaUsuario);
            RS = PS.executeQuery();
            if(RS.next()){
                usuario.setUsuarioID(RS.getInt("usuarioID"));
                usuario.setNombres(RS.getString("nombres"));
                usuario.setApellidos(RS.getString("apellidos"));
                usuario.setPuesto(RS.getString("puestoCargo"));
                usuario.setNombreUsuario(RS.getString("nombreUsuario"));
                usuario.setContraseña(RS.getString("contraseñaUsuario"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos: "+e.toString(), "Error al Iniciar Sesión", JOptionPane.ERROR_MESSAGE);
        } 
        
        return  usuario;
    }
}
