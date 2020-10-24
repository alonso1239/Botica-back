package com.unfv.api.apirest.Entity;
//usaremos jpa para el proyecto
//vamos a mapear la base de datos


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//para que la clase sea reconocida por jpa se usa @Entity
@Entity
@Table(name="medicamentos", schema="public")
public class MedicamentosEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Si o si debe haber una pk para los metodos crud que vayamos a usar)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Size(max = 13)
	@Column(nullable = false)
	//@NotEmpty
	private String codigo;
	
	//declaramos atributos
	//@Column(name = "nombre")
	//@NotEmpty
	private String nombre;
	
	//@Column(name = "costo")
	
	private BigDecimal costo;
	
	private Integer cantidad;
	
	//@Column(name = "pventa")
	
	private BigDecimal pventa;
	
	//@Column(name = "fecha")
	//@NotEmpty
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	//@Column(name = "descripcion")
	private String descripcion;

	//@Column(name = "stand")
	private String stand;
	
	private String foto;
	
	public MedicamentosEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
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

	
	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}

	//se usa como debug para imprimir partes del objeto
	@Override
	public String toString() {
		return "MedicamentosEntity [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", costo=" + costo
				+ ", cantidad=" + cantidad + ", pventa=" + pventa + ", fecha=" + fecha + ", descripcion=" + descripcion
				+ ", stand=" + stand + ", foto=" + foto + "]";
	}


}
