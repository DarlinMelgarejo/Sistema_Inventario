package Clases;

/**
 * @author DARLIN
 */
public class InventarioFinal {
    String codigoProducto;
    String descripcionProducto;
    String areaProducto;
    String categoriaProducto;
    double costoUnitario;
    int entradas;
    int salidas;
    int stock;
    double costoTotal;

    public InventarioFinal() {
    }

    public InventarioFinal(String codigoProducto, String descripcionProducto, String areaProducto, String categoriaProducto, double costoUnitario, int entradas, int salidas, int stock, double costoTotal) {
        this.codigoProducto = codigoProducto;
        this.descripcionProducto = descripcionProducto;
        this.areaProducto = areaProducto;
        this.categoriaProducto = categoriaProducto;
        this.costoUnitario = costoUnitario;
        this.entradas = entradas;
        this.salidas = salidas;
        this.stock = stock;
        this.costoTotal = costoTotal;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getAreaProducto() {
        return areaProducto;
    }

    public void setAreaProducto(String areaProducto) {
        this.areaProducto = areaProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
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
