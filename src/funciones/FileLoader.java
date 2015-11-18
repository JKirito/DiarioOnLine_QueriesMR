package funciones;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class FileLoader {

	/**
	 * Warning: El archivo a cargar debe tener una palabra por línea! De otro forma, cada elemento del
	 * Set devueltó serán varias palabras (las que tenga por línea)
	 * 
	 * @param pathFile ruta donde está el archivo a cargar
	 * @param isHDFS indica si la ruta es de un archivo local (false) o HDFS (true)
	 * @return Un set que contienen todas las palabras del archivo que quiere cargar
	 * @throws IOException
	 */
	public static Set<String> load(String pathFile, boolean isHDFS) throws IOException
	{
		Set<String> wordsSet = new HashSet<String>();
		StringSetCreator set = new StringSetCreator();
		
		if(isHDFS)
		{
			FileReaderHDFS fr = new FileReaderHDFS(pathFile);
			InputStream in = fr.getStream();
			wordsSet = set.getStringSetSinCharset(in);
			in.close();
			
			return wordsSet;
		}
		
		File stopWordsFile = new File(pathFile);
		wordsSet = set.getStringSet(stopWordsFile);
		
		return wordsSet;
	}
	
}
