package Clases;

/**
 *
 * @author DARLIN
 */
public class InventarioInicial {
    int codigoInventario;
    String codigoProducto;
    int entradas;
    int salidas;
    int stock;
    double costoTotal;

    public InventarioInicial() {
    }

    public InventarioInicial(int codigoInventario, String codigoProducto, int entradas, int salidas, int stock, double costoTotal) {
        this.codigoInventario = codigoInventario;
        this.codigoProducto = codigoProducto;
        this.entradas = entradas;
        this.salidas = salidas;
        this.stock = stock;
        this.costoTotal = costoTotal;
    }

    public int getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(int codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}
