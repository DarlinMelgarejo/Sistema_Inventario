package Clases;

/**
 *
 * @author DARLIN
 */
public class Usuario {
    int usuarioID;
    String nombres;
    String apellidos;
    String puesto;
    String nombreUsuario;
    String contraseña;

    public Usuario() {
    }

    public Usuario(int usuarioID, String nombres, String apellidos, String puesto, String nombreUsuario, String contraseña) {
        this.usuarioID = usuarioID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.puesto = puesto;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
}
