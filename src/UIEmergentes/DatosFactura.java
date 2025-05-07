/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UIEmergentes;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author salin
 */
public class DatosFactura{
        public String folio;
        public String uuid;
        public String numeroCertificado;
        public String fecha;
        public String nombreCliente;
        public String rfcCliente;
        public String direccionCliente;
        public String ciudadEstadoCP;
        public String telefono;
        public List<String[]> productos; // {cantidad, unidad, descripcion, precio unitario, total}
        public String totalLetra;
        public String usoCFDI;
        public String tipoPersona;
        public String subtotal;
        public String descuento;
        public String impuestos;
        public String total;

    public DatosFactura(String uuid, String numeroCertificado, String fecha, String nombreCliente, String rfcCliente, String direccionCliente, String ciudadEstadoCP, String telefono, List<String[]> productos, String totalLetra, String usoCFDI, String tipoPersona, String subtotal, String descuento, String impuestos, String total) {
        this.uuid = uuid;
        this.numeroCertificado = numeroCertificado;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.rfcCliente = rfcCliente;
        this.direccionCliente = direccionCliente;
        this.ciudadEstadoCP = ciudadEstadoCP;
        this.telefono = telefono;
        this.productos = productos;
        this.totalLetra = totalLetra;
        this.usoCFDI = usoCFDI;
        this.tipoPersona = tipoPersona;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.impuestos = impuestos;
        this.total = total;
    }


    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getRfcCliente() {
        return rfcCliente;
    }

    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getCiudadEstadoCP() {
        return ciudadEstadoCP;
    }

    public void setCiudadEstadoCP(String ciudadEstadoCP) {
        this.ciudadEstadoCP = ciudadEstadoCP;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<String[]> getProductos() {
        return productos;
    }

    public void setProductos(List<String[]> productos) {
        this.productos = productos;
    }

    public String getTotalLetra() {
        return totalLetra;
    }

    public void setTotalLetra(String totalLetra) {
        this.totalLetra = totalLetra;
    }

    public String getUsoCFDI() {
        return usoCFDI;
    }

    public void setUsoCFDI(String usoCFDI) {
        this.usoCFDI = usoCFDI;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(String impuestos) {
        this.impuestos = impuestos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
        
   private String formatearDecimal(double valor) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valor);
    }
        
    }
    
