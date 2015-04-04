package main;

import java.io.*;
import java.util.*;

/**
 * This class reads all files in the dataset and removes all marks:
 * <DOC> tag
 * </DOC> tag
 * < ENAMEX TYPE = ' ORGANIZATION ' > < / ENAMEX >
 * < ENAMEX TYPE = ' PERSON ' > < / ENAMEX >
 * < ENAMEX TYPE = ' LOCATION ' > < / ENAMEX >
 * @author Konstantinos Pappas
 *
 */
public class MainRevomeTags {
	
	public static String datasetPath = "C:\\Users\\Konstantinos Pappas\\Desktop\\classes\\EECS592\\project\\solution\\NER_Corpus_Greek";
	
	public static String cleanDatasetPath = "C:\\Users\\Konstantinos Pappas\\Desktop\\classes\\EECS592\\project\\solution\\Clean_NER_Corpus_Greek";
	
	public static void main(String[] args)throws Exception{

		File dataset = new File(datasetPath);

		File cleanDataset = new File(cleanDatasetPath);
		cleanDataset.mkdir();
		
		//for each file in the dataset
		for(File article : dataset.listFiles()){
			//create a new file in the cleanDataset path
			File newFile = new File(cleanDataset + "\\" + article.getName());
			newFile.createNewFile();
			PrintWriter writer = new PrintWriter(newFile.getAbsolutePath(), "UTF-8");
			Scanner sc = new Scanner(new FileInputStream(article), "UTF-8");
			//for each line in the file
			while(sc.hasNextLine()) {
			   String line = sc.nextLine();
			   line = line.replace("<DOC>", "");
			   line = line.replace("</DOC>", "");
			   line = line.replace("<ENAMEX TYPE='ORGANIZATION'>", "");
			   line = line.replace("<ENAMEX TYPE='PERSON'>", "");
			   line = line.replace("<ENAMEX TYPE='LOCATION'>", "");
			   line = line.replace("</ENAMEX>", "");
			   line = line.replace("<TIMEX TYPE='DATE'>", "");
			   line = line.replace("</TIMEX>", "");
			   //write it without tags in the clean file
				writer.println(line);
			}
			writer.flush();
			writer.close();
			sc.close();
		}
		
	}
	
}
