package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * @author DARLIN
 */

public class Conectar {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/bd_inventario?autoReconnect=true&useSSL=false";
    
    private Connection conexion;
    
    public Conectar(){
        conexion = null;
    }
    
    public Connection getConnection(){
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al conectar con la base de datos", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return conexion;
    }
    
    public void desconectar(){
        try {
            conexion.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al cerrar la conexion con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}