package q1_ranking_enRango;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import Utils.Utils;

public class RankingTitulosReducer extends Reducer<IntWritable, Text, Text, Text> {

	public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException,
			InterruptedException {
		
		for (Text titulo : values) {
			context.write(titulo, new Text(Utils.getTime(key.get())));
		}
		
	}

}
