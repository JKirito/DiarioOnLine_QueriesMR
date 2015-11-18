package entities;

import java.util.Date;

public class Nota {

	private String título;
	private Date fechaInicio;
	private Date fechaFin;
	private Dia DiaSemana;
	
	public Nota(String título, Date fechaInicio, Date fechaFin, Dia diaSemana) {
		super();
		this.título = título;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		DiaSemana = diaSemana;
	}
	
	public Nota(String título, Date fechaInicio, Date fechaFin) {
		super();
		this.título = título;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public String getTítulo() {
		return título;
	}

	public void setTítulo(String título) {
		this.título = título;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Dia getDiaSemana() {
		return DiaSemana;
	}

	public void setDiaSemana(Dia diaSemana) {
		DiaSemana = diaSemana;
	}
	
	public static String getTitulo(String linkTitulo) {
		String titulo;
		// El título comienza despues del espacio("link titulo") 
		titulo = linkTitulo.substring(linkTitulo.indexOf(" ")+1);
		return titulo;
	}
	
	public static String getLink(String linkTitulo) {
		return linkTitulo.substring(0,linkTitulo.indexOf(" ")+1);
	}

}
