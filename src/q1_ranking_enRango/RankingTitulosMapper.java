package q1_ranking_enRango;

import java.io.IOException;
import java.util.Date;

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
import funciones.Filtro;

/**
 * Recibe: titulo##fechaAparece##fechaDesaparece
 * 
 * Emite: <segsOnLine,titulos> (filtrando por rango de fecha que recibe por
 * parámetro)
 * 
 * @author javi
 *
 */

public class RankingTitulosMapper extends
		Mapper<LongWritable, Text, IntWritable, Text> {

	private String separadorCampos;
	private Filtro filtro; 

	@Override
	protected void setup(Context context) throws IOException {
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
		
		Integer segsOnline = Utils.getDiferenciaEnSegundos(inicio, fin);

//		String cleanWord = limpiarPalabra(words[Nota_Campos.TITULO.pos()], false,
//				stopSpecialWordsSet, caseSensitiveWordsSet, stopWordsSet);
		
		//SI ES EL RANKING DE LOS TITULOS, NO TENGO QUE LIMPIAR LAS PALABRAS
//		if (!cleanWord.equals(""))
			context.write(new IntWritable(segsOnline), new Text(titulo));
		
	}

	
}