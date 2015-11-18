package q1_ranking_enRango;

import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import Utils.Utils;


/**
 * titulo##fechaAparece##fechaDesaparece
 * 
 * Este Job busca el ranking de las notas que más tiempo estuvieron online
 * en un rango de días. Ejemplo: [2015.09.22 - 2015.10.28]
 * 
 * 
 * @author javi
 *
 */

public class RankingTitulos extends Configured implements Tool {
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new RankingTitulos(), args);
	}

	
	//TODO: agregar que se reciban por parámetro
	private static final String DIASEMANA = "";
	private static final String INICIO = "2015.10.12 12:00:00";
	private static final String FIN = "2015.11.15 22:00:00";
	private static final String SEPARADOR = "##";

	@Override
	public int run(String[] args) throws Exception {
		long init = new Date().getTime();
		// Para ejecutar desde eclipse..
		args = new String[2];
		args[0] = "/media/javi/06668E5C668E4C7D/Ubuntu_Share/javi/datasets/diarioOnLine/input/titulos/";
		args[1] = "/media/javi/06668E5C668E4C7D/Ubuntu_Share/javi/datasets/diarioOnLine/outputRankingTitulos";

		if (args.length != 2) {
			throw new Exception("Los parametros deben ser 2: input y ouput");
		}
		Configuration conf = new Configuration();
		conf.set("inicio", INICIO);
		conf.set("fin", FIN);
		conf.set("separador", SEPARADOR);
		conf.set("diaSemana", DIASEMANA);
		
		
		Job job = Job.getInstance(conf, "WordCount");
		job.setJarByClass(RankingTitulos.class);

		/*
		 * Even if your MapReduce application reads and writes uncompressed
		 * data, it may ben- efit from compressing the intermediate output of
		 * the map phase. Since the map output is written to disk and
		 * transferred across the network to the reducer nodes, by using a fast
		 * compressor such as LZO, LZ4, or Snappy, you can get performance gains
		 * simply because the volume of data to transfer is reduced.
		 */
		// Se consigue agregando las siguientes 2 lineas!
//		conf.setBoolean("mapreduce.map.output.compress", true);
//		conf.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);


		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(RankingTitulosMapper.class);
//		job.setCombinerClass(WordCountReducer.class);
		job.setReducerClass(RankingTitulosReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);

		/*
		 * To compress the output of a MapReduce job, in the job configuration,
		 * set the mapred.output.compress property to true and the
		 * mapred.output.compression.codec property to the classname of the
		 * compression codec you want to use. Alternatively, you can use the
		 * static convenience methods on FileOutputFormat to set these
		 * properties
		 */
		// FileOutputFormat.setCompressOutput(job, true);
		// FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

		if (job.waitForCompletion(true)) {
			long fin = new Date().getTime();
			System.out.println("Tardó: " + Utils.getTime((fin - init)/1000));
		}
		return 0;
	}
}
