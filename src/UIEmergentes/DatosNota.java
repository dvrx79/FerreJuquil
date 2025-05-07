/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UIEmergentes;

/**
 *
 * @author salin
 */
import UIEmergentes.*;
import java.text.DecimalFormat;
import java.util.List;

public class DatosNota {
    
        public String folio;
        public String fecha;
        public List<String[]> productos; // {cantidad, unidad, descripcion, precio unitario, total}
        public String totalLetra;
        public String usoCFDI;
        public String tipoPersona;
        public String subtotal;
        public String descuento;
        public String impuestos;
        public String total;

    public DatosNota() {
    }

    public DatosNota(String fecha, List<String[]> productos, String totalLetra, String usoCFDI, String tipoPersona, String subtotal, String descuento, String impuestos, String total) {
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
    


