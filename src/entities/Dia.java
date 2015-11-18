package entities;

public enum Dia {
	DOMINGO(0, "Domingo"), LUNES(1, "Lunes"), MARTES(2, "Martes"), MIERCOLES(3, "Miércoles"), JUEVES(4, "Jueves"),
	VIERNES(5, "Viernes"), SABADO(6, "Sábado");

	private final int value;
	private final String name;

	Dia(int value, String nombreAMostrar) {
		this.value = value;
		this.name = nombreAMostrar;
	}

	public int pos() {
		return this.value;
	}

	public String toString() {
		return this.name;
	}
	
	public static boolean isDiaCorrecto(String diaSemana)
	{
		return Dia.valueOf(diaSemana.toUpperCase()) != null;
	}
	
	public static Dia getDia(int numDia)
	{
		return Dia.values()[numDia];
	}
	
}
