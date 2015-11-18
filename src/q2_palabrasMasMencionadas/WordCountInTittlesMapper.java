package q2_palabrasMasMencionadas;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import Utils.Utils;
import entities.Dia;
import entities.Nota;
import entities.Nota_Campos;
import funciones.ContextParameters;
import funciones.FileLoader;
import funciones.Filtro;
import funciones.Limpiador;

/**
 * Recibe: titulo##fechaAparece##fechaDesaparece
 * 
 * Emite: <palabra,cantidadVecesAparece> (filtrando los títulos por rango de fecha que recibe por
 * parámetro)
 * 
 * @author javi
 *
 */

public class WordCountInTittlesMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> {

	private Set<String> stopWordsSet;
	private Set<String> stopSpecialWordsSet;
	private Set<String> caseSensitiveWordsSet;
	private String separadorCampos;
	private Filtro filtro; 

	@Override
	protected void setup(Context context) throws IOException {
		stopWordsSet = new HashSet<String>();
		stopSpecialWordsSet = new HashSet<String>();
		caseSensitiveWordsSet = new HashSet<String>();

		stopWordsSet = FileLoader.load("/media/javi/06668E5C668E4C7D/workspace/stopWords/stopWordsList.txt", false);
		stopSpecialWordsSet = FileLoader.load("/media/javi/06668E5C668E4C7D/workspace/stopWords/stopSpecialWordsList.txt", false);
		caseSensitiveWordsSet = FileLoader.load("/media/javi/06668E5C668E4C7D/workspace/stopWords/caseSensitiveWordsList.txt", false);

		Configuration config = context.getConfiguration();
		ContextParameters cp = new ContextParameters(config);
		// Seteo fecha INICIO
		Date fechaInicio = cp.getDate("inicio");
		// Seteo fecha FIN
		Date fechaFin = cp.getDate("fin");;
		// Seteo día de la semana que quiero ver
		Dia diaSemana = cp.getEnumDia("diaSemana");
		
		//Creo el filtro a usar con estos datos
		filtro = new Filtro(diaSemana, fechaInicio, fechaFin);

		// Seteo separador de campos en la nota
		separadorCampos = cp.getString("separador");
	}

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] words = value.toString().split(separadorCampos);
		
		// Verifico que estén las fechas de inicio y fin de las notas
		String fechaFin_campo = null;
		try {
			fechaFin_campo = words[Nota_Campos.FIN.pos()];	
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String fechaInicio_campo = null;
		try {
			fechaInicio_campo = words[Nota_Campos.INICIO.pos()];	
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//Obtengo las fechas inicio/fin, diaSemana y Título
		Date inicio = Utils.convertDateStringToDate2(fechaInicio_campo);
		Date fin = Utils.convertDateStringToDate2(fechaFin_campo);
		Dia diaSemana = Dia.getDia(inicio.getDay());
		String titulo = Nota.getTitulo(words[Nota_Campos.LINK_TITULO.pos()]);

		Nota nota = new Nota(titulo, inicio, fin, diaSemana); 
		
		if(!this.filtro.pasaFiltro(nota))
			return;
		String[] palabrasTitulo = titulo.split(" ");
		Limpiador limp = new Limpiador(stopSpecialWordsSet, caseSensitiveWordsSet, stopWordsSet);
		
		for (int i = 0; i < palabrasTitulo.length; i++)
		{
			String cleanWord = limp.limpiarPalabra(palabrasTitulo[i]);
			if(!cleanWord.equals(""))
				context.write(new Text(cleanWord), new IntWritable(1));
		}

	}

}