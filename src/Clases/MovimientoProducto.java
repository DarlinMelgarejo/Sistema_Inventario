package Clases;

import java.util.Date;

/**
 *
 * @author DARLIN
 */
public class MovimientoProducto {
    int codigoMovimiento;
    String tipoMovimiento;
    String codigoProducto;
    String descripcion;
    Date fecha;
    int cantidad;
    int porciones;

    public MovimientoProducto() {
    }

    public MovimientoProducto(int codigoMovimiento, String tipoMovimiento, String codigoProducto, String descripcion, Date fecha, int cantidad, int porciones) {
        this.codigoMovimiento = codigoMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.porciones = porciones;
    }

    public int getCodigoMovimiento() {
        return codigoMovimiento;
    }

    public void setCodigoMovimiento(int codigoMovimiento) {
        this.codigoMovimiento = codigoMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }
}
