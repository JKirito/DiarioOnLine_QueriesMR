package funciones;

import java.util.Set;

public class Limpiador {
	
	private Set<String> stopSpecialWordsSet;
	private Set<String> caseSensitiveWordsSet;
	private Set<String> stopWordsSet;
	
	public Limpiador(Set<String> stopSpecialWordsSet, Set<String> caseSensitiveWordsSet,
			Set<String> stopWordsSet) {
		this.stopSpecialWordsSet = stopSpecialWordsSet;
		this.caseSensitiveWordsSet = caseSensitiveWordsSet;
		this.stopWordsSet = stopWordsSet;
	}
	
	/**
	 * 
	 * @param stopSpecialWordsSet
	 * @param caseSensitiveWordsSet
	 * @param stopWordsSet
	 * @return
	 */
	public String limpiarPalabra(String texto) {
		if (texto.trim().isEmpty() || texto.length() <= 1) {
			return "";
		}
		// si primer o último caracter es "raro", lo saco.
		// Puede haber varios caracteres seguidos "raros"
		int caracterFinal = texto.length();
		for (int i = 0; i < caracterFinal; i++)
		{
			String primerCaracter = texto.charAt(i) + "";
			if (stopSpecialWordsSet.contains(primerCaracter))
			{
				primerCaracter = acomodarCaracterEspecial(primerCaracter);
				texto = texto.replace(primerCaracter, "");
				i = 0;
				caracterFinal = texto.length();
			} else
			{
				break;
			}
		}
		if (texto.length() <= 1)
		{
			return "";
		}
		
		for (int i = texto.length() - 1; i >= 0; i--)
		{
			String ultimoCaracter = texto.charAt(i) + "";
			if (stopSpecialWordsSet.contains(ultimoCaracter))
			{
				ultimoCaracter = acomodarCaracterEspecial(ultimoCaracter);
				texto = texto.replace(ultimoCaracter, "");
				i = texto.length();
			} else
			{
				break;
			}
		}
		if (!caseSensitiveWordsSet.contains(texto)) {
			texto = texto.toLowerCase();
		}
		if (stopWordsSet.contains(texto)
				|| stopSpecialWordsSet.contains(texto)) {
			return "";
		}
		return texto;
	}

	private String acomodarCaracterEspecial(String palabra) {
		if (palabra.equals("\\")) {
			palabra += "\\";
		}
		return palabra;
	}

}
