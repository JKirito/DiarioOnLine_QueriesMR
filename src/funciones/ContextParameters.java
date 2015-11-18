package funciones;

import java.util.Date;

import org.apache.hadoop.conf.Configuration;

import entities.Dia;
import Utils.Utils;

public class ContextParameters {
	
	private Configuration conf;
	
	public ContextParameters(Configuration config) {
		this.conf = config;
	}

	public Date getDate(String parameterName)
	{
		String fechaString = this.conf.get(parameterName);
		return fechaString != null && !fechaString.isEmpty() ? Utils.convertStringToDate(fechaString) : null;
	}
	
	public Dia getEnumDia(String parameterName)
	{
		String dia = this.conf.get(parameterName);
		dia = dia !=null ? dia.toUpperCase() : null;
		Dia diaSemana = null;
		try {
			diaSemana = Dia.valueOf(dia);
		} catch (Exception e) {
			diaSemana = null;
		}

		return diaSemana;
	}

	public String getString(String parameterName)
	{
		return this.conf.get(parameterName);
	}
}
