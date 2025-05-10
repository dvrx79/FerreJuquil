package Modelo;

import java.sql.Date;

/**
 *
 * @author salin
 */
public class MostrarPedido {
    private String estEntrega;
    private String estPago;
    private Date fecha;
    private Double monto;
    private String direccion;
    private String nombreCliente;
    private String numero;
    private int idVenta;
    private int idPedido;
    private String nombreRecibe;  // Nuevo campo
    private Date fechaEntrega;      // Nuevo campo

    public MostrarPedido() {
    }

    // Constructor actualizado con los nuevos campos
    public MostrarPedido(String estEntrega, String estPago, Date fecha, Double monto, 
                        String direccion, String nombreCliente, String numero, 
                        int idVenta, int idPedido, String nombreRecibe, Date fechaEntrega) {
        this.estEntrega = estEntrega;
        this.estPago = estPago;
        this.fecha = fecha;
        this.monto = monto;
        this.direccion = direccion;
        this.nombreCliente = nombreCliente;
        this.numero = numero;
        this.idVenta = idVenta;
        this.idPedido = idPedido;
        this.nombreRecibe=nombreRecibe;
        this.fechaEntrega=fechaEntrega;
    }

    // Getters y Setters para los nuevos campos
    public String getNombreRecibe() {
        return nombreRecibe;
    }

    public void setNombreRecibe(String nombreRecibe) {
        this.nombreRecibe = nombreRecibe;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    // Resto de getters y setters (se mantienen igual)
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getEstEntrega() {
        return estEntrega;
    }

    public void setEstEntrega(String estEntrega) {
        this.estEntrega = estEntrega;
    }

    public String getEstPago() {
        return estPago;
    }

    public void setEstPago(String estPago) {
        this.estPago = estPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}