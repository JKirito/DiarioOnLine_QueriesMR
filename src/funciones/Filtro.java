package funciones;

import java.util.Date;

import entities.Dia;
import entities.Nota;

public class Filtro {

	private Date fechaInicio;
	private Date fechaFin;
	private Dia diaSemana;
	
	
	public Filtro(Dia diaSemana, Date fechaInicio, Date fechaFin) {
		super();
		this.diaSemana = diaSemana;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	private boolean filtrarPorDia()
	{
		return this.diaSemana !=null && Dia.isDiaCorrecto(diaSemana.toString());
	}
	
	//TODO: ver! sólo comparo las fechas de los parámetros con las fechainicio de la nota???
	public boolean pasaFiltro(Nota nota)
	{
		boolean pasaFiltros = true;
		
		if(filtrarPorDia())
		{
			if(nota.getDiaSemana().compareTo(diaSemana) != 0)
				pasaFiltros = false;
		}
		
		//Si la fecha en que apareció la nota es anterior a la fechaInicio del parámetro,
		//entonces no pasa el filtro
		if(pasaFiltros && fechaInicio != null && nota.getFechaInicio().compareTo(fechaInicio) < 0)
			pasaFiltros = false;
		
		//Si la fecha en que dejó de estar online la nota es posterior a la fechaFin del parámetro,
		//entonces no pasa el filtro
		if(pasaFiltros && fechaFin != null && nota.getFechaFin().compareTo(fechaFin) > 0)
			pasaFiltros = false;
		
		return pasaFiltros;
	}
	
	
	
}
