package Clases;

import java.util.Date;

/**
 *
 * @author DARLIN
 */
public class Producto {
    String codigo;
    String descripcion;
    String vendedor;
    String marca;
    String unidadMedida;
    double costoUnitario;
    int cantidad;
    Date fechaVencimiento;
    String areaDestinada;
    String categoria;

    public Producto() {
    }

    public Producto(String codigo, String descripcion, String vendedor, String marca, String unidadMedida, double costoUnitario, int cantidad, Date fechaVencimiento, String areaDestinada, String categoria) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.vendedor = vendedor;
        this.marca = marca;
        this.unidadMedida = unidadMedida;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.areaDestinada = areaDestinada;
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getAreaDestinada() {
        return areaDestinada;
    }

    public void setAreaDestinada(String areaDestinada) {
        this.areaDestinada = areaDestinada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}
