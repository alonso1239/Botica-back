package com.unfv.api.apirest.Dto;

import java.math.BigDecimal;
import javax.persistence.Column;

public class Medicamentos {

	private String codigo;	
	private String nombre;
	private BigDecimal costo;	
	private Integer cantidad;
	private BigDecimal pventa;		
	private String fecha;
	private String descripcion;
	private String stand;
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public BigDecimal getCosto() {
		return costo;
	}
	
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public BigDecimal getPventa() {
		return pventa;
	}
	
	public void setPventa(BigDecimal pventa) {
		this.pventa = pventa;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getStand() {
		return stand;
	}
	
	public void setStand(String stand) {
		this.stand = stand;
	}
	
	

}
