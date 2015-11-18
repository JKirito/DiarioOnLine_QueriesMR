package entities;

// VALOR: POS COLUMNA AL IMPORTAR
// PROBLEMA!! SI CAMBIA UNA COLUMNA DE POSICION/NUMERACION, HAY QUE CAMBIAR TODAS LAS QUE SIGUEN!!! (ESTO ES SOLO PARA LA IMPORTACION)
// NOMBRE: NOMBRE A MOSTRAR PARA ESE CAMPO AL EXPORTAR

//id##link titulo##inicio##fin

public enum Nota_Campos {
	ID(0, "ID"), LINK_TITULO(1, "Link_Título"), INICIO(2, "Fecha apareció"), FIN(3, "Fecha desapareció");

	private final int value;
	private final String name;

	Nota_Campos(int value, String nombreAMostrar) {
		this.value = value;
		this.name = nombreAMostrar;
	}

	public int pos() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
