package main;

import ipl.sentence_splitter.Tagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is used to combine marks and tags in an article and return it as a String.
 * @author Konstantinos Pappas
 *
 */
public class CombineArticle {

	public static String taggedDatasetPath = "C:\\Users\\Konstantinos Pappas\\workspace\\EECS592\\resources\\AnnotatedFinancialArticles";
	
	public static String fetch(File article)throws Exception{
		
		//get annotated version in a String
		Scanner scTag = new Scanner(new FileInputStream(taggedDatasetPath + "\\" + article.getName()), "UTF-8");
		String annoArticle = "";
		while(scTag.hasNextLine()) {
			annoArticle += scTag.nextLine();
			annoArticle += "\n";
		}
		annoArticle = annoArticle.trim();
		//get tagged version in a String
		//the tagger is a Greek sentence splitter
		Tagger tagger = new Tagger();
		String taggedArticle = tagger.mark_file(article.getAbsolutePath()).trim();
		
		//combine versions in one String with both annotations
		String result = "";
		int annoIndex = 0;
		int dotPos = 1;
		while(annoIndex < annoArticle.length()){
			char nextAnnoChar = annoArticle.subSequence(annoIndex, annoIndex+1).charAt(0);
			result += nextAnnoChar+"";
			if(nextAnnoChar == '.'){
				result += getDotMark(taggedArticle, dotPos);
				dotPos++;
			}
			annoIndex++;
		}
		return result;
	}
	
	/**
	 * Returns "\\y" or "\\n" depending on whether the dotPos's mark is \y or \n
	 */
	public static String getDotMark(String taggedArticle, int dotPos){
		int counter = 0;
		for(int pos=0; pos<taggedArticle.length(); ++pos){
			char nextChar = taggedArticle.subSequence(pos, pos+1).charAt(0);
			if(nextChar == '.'){
				counter++;
			}
			if(dotPos == counter){
				if(taggedArticle.subSequence(pos+2, pos+3).charAt(0) == 'y'){
					return "\\y";
				} else {
					return "\\n";
				}
			}
		}
		return "\\y";
	}
	
}
