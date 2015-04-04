package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ipl.sentence_splitter.*;

/**
 * Given two folders, one containing the clean articles (obtained by MainRemoveTags.java) and one containing the annotated texts,
 * this class will use the sentence splitter to identify end of sentences in the clean texts, combine those marks with the
 * named entity annotations, tokenize the resulting articles and export them in a serialized file. 
 * @author Konstantinos Pappas
 *
 */
public class Main {
	
	public static String datasetPath = "C:\\Users\\Konstantinos Pappas\\workspace\\EECS592\\resources\\CleanFinancialArticles";
	
	public static void main(String[] args)throws Exception{
		
		File dataset = new File(datasetPath);

		List<List<Token>> tokens = new ArrayList<List<Token>>();
		
		//for each clean article
		for(File article : dataset.listFiles()){
			//tag it using the sentence splitter and then add the named entity annotations
			String taggedArticle = CombineArticle.fetch(article);
			//then tokenize it
			tokens.add(tokenizeArticle(taggedArticle));
		}
		
		//clean the tokens:
		//1) remove tokens with 0 length (result of removing tags on previous step)
		//2) remove tokens that are comprised of the 65279 character (result of the copy-paste used to obtain this corpus)
		//3) remove tokens that are line breaks (nothing is mentioned in the paper but, since line breaks are used for formatting, I remove them)
		
		for(int i=0; i<tokens.size(); ++i){
			List<Token> article = tokens.get(i);
			boolean haveSeenWords = false;
			boolean haveMarkedTitle = false;
			for(int j=0; j<article.size();++j){
				Token token = article.get(j);
				//1)
				if(token.getContent().length() == 0){
					article.remove(j);
					j--;
				//2)
				} else if(token.getContent().length() == 1 && (token.getContent().charAt(0)) == 65279){
					article.remove(j);
					j--;
				//3)
				} else if(token.getContent().length() ==1 && (token.getContent().charAt(0)) == 10)
				{
					if(haveSeenWords && !haveMarkedTitle){
						for(int z=0; z<j; ++z){
							article.get(z).setTitle(true);
						}
						haveMarkedTitle = true;
					}
					article.remove(j);
					j--;
				} else {
					haveSeenWords = true;
				}
			}
		}
		
		//absorb all tags (named entities and sentences) in the Token representation
		for(int i=0; i<tokens.size(); ++i){
			fixArticle(tokens.get(i));
		}
		
		//identify not person sure-fires and not organization sure-fires
		for(int i=0; i<tokens.size(); ++i){
			List<Token> article = tokens.get(i);
			for(int j=0; j<article.size(); ++j){
				SureFire.identifyNotPersonSureFire((j>0)?article.get(j-1):null , article.get(j));
				SureFire.identifyNotOrganizationSureFire(article.get(j));
			}
		}
		
		//output the result in a serialized file
	    try{
	        //use buffering
	        ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("C:\\Users\\Konstantinos Pappas\\workspace\\EECS592\\resources\\tokens.ser")));
	        try{
	          output.writeObject(tokens);
	        }
	        finally{
	          output.close();
	        }
	      }  
	      catch(IOException ex){
	        System.err.println("Error writing serializable object");
	      }
	}
	
	/**
	 * Given a list of tokens that represent an article it removes tags and puts the relevant information in the Token object
	 * @param article
	 */
	public static void fixArticle(List<Token> article){
		for(int j=0; j<article.size(); ++j){
			//remove <DOC> tag
			if(article.get(j).getContent().equals("<") && (j+2)<article.size() && article.get(j+1).getContent().equals("DOC")
					 && article.get(j+2).getContent().equals(">")){
				article.remove(j);
				article.remove(j);
				article.remove(j);
				j--;
				continue;
			}
			//remove </DOC> tag
			if(article.get(j).getContent().equals("<") && (j+3)<article.size() && article.get(j+1).getContent().equals("/")
					&& article.get(j+2).getContent().equals("DOC") && article.get(j+3).getContent().equals(">")){
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				j--;
				continue;
			}
			//remove < ENAMEX TYPE = ' ORGANIZATION ' > < / ENAMEX > and update Token
			//remove < ENAMEX TYPE = ' PERSON ' > < / ENAMEX > and update Token
			//remove < ENAMEX TYPE = ' LOCATION ' > < / ENAMEX > and update Token
			if(article.get(j).getContent().equals("<") && (j+8)<article.size() && article.get(j+1).getContent().equals("ENAMEX")
					&& article.get(j+2).getContent().equals("TYPE") && article.get(j+3).getContent().equals("=")
					&& article.get(j+4).getContent().equals("\'")){
				String type = article.get(j+5).getContent();
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				while(! (article.get(j).getContent().equals("<") && (j+4)<article.size() && article.get(j+1).getContent().equals("/")
						&& article.get(j+2).getContent().equals("ENAMEX") && article.get(j+3).getContent().equals(">")) ){
					if(type.equals("ORGANIZATION")){
						article.get(j).setOrganizationName(true);
					} else if(type.equals("PERSON")){
						article.get(j).setPersonName(true);
					} else if(type.equals("LOCATION")){
						article.get(j).setLocationName(true);
					}
					//delete end of sentence marks inside named entities -- we know they are not end of sentences.//I CORRECT THE SENTENCE SPLITTER HERE
					/*if(article.get(j).getContent().equals(".") && article.get(j+1).getContent().equals("\\") &&
							(article.get(j+2).getContent().equals("y") || article.get(j+2).getContent().equals("n"))){
						article.remove(j+1);
						article.remove(j+1);
					}*/
					j++;
				}
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				j--;
				continue;
			}
			//remove < TIMEX TYPE = ' DATE ' > < / TIMEX > and update Token
			if(article.get(j).getContent().equals("<") && (j+8)<article.size() && article.get(j+1).getContent().equals("TIMEX")
					&& article.get(j+2).getContent().equals("TYPE") && article.get(j+3).getContent().equals("=")
					&& article.get(j+4).getContent().equals("\'")){
				String type = article.get(j+5).getContent();
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				while(! (article.get(j).getContent().equals("<") && (j+4)<article.size() && article.get(j+1).getContent().equals("/")
						&& article.get(j+2).getContent().equals("TIMEX") && article.get(j+3).getContent().equals(">")) ){
					if(type.equals("DATE")){
						article.get(j).setTemporalExpression(true);
					}
					//delete end of sentence marks inside temporal expression -- we know they are not end of sentences.//I CORRECT THE SENTENCE SPLITTER HERE
					/*if(article.get(j).equals(".") && article.get(j+1).equals("\\") &&
							(article.get(j+2).equals("y") || article.get(j+2).equals("n"))){
						article.remove(j+1);
						article.remove(j+1);
					}*/
					j++;
				}
				article.remove(j);
				article.remove(j);
				article.remove(j);
				article.remove(j);
				j--;
				continue;
			}
		}
		//second pass to remove sentence splitter marks
		for(int j=0; j<article.size(); ++j){
			//remove .\y and .\n and update Token
			if(article.get(j).getContent().equals(".") && (j+2)<article.size() && article.get(j+1).getContent().equals("\\")
					&& (article.get(j+2).getContent().equals("y") || article.get(j+2).getContent().equals("n"))){
				String type = article.get(j+2).getContent();
				article.remove(j+1);
				article.remove(j+1);
				if(type.equals("y")){
					article.get(j).setEndOfSentence(true);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param taggedArticle the article marked with temporal expressions, organization and person name and sentence splitting etc
	 * @return distinct tokens of this article
	 */
	public static List<Token> tokenizeArticle(String taggedArticle){
		List<Token> tokens = new ArrayList<Token>();
		String[] preTokens = taggedArticle.split(" ");
		
		for(String preToken : preTokens){
			splitToken(preToken, tokens);
		}
		
		return tokens;
	}
	
	/**
	 * Use any non-alphanumeric character as a separate token
	 * @param preToken
	 * @param tokens
	 */
	private static void splitToken(String preToken, List<Token> tokens){
		try{
		boolean splitted = false;
		for(char character : preToken.toCharArray()){
			if(!Character.isLetterOrDigit(character)){
				splitted = true;
				String splitPattern = character+"";
				if(splitPattern.equals(".")){
					splitPattern = "\\.";
				} else if(splitPattern.equals("\\")){
					splitPattern = "\\\\";
				} else if(splitPattern.equals("(")){
					splitPattern = "\\(";
				} else if(splitPattern.equals(")")){
					splitPattern = "\\)";
				} else if(splitPattern.equals("*")){
					splitPattern = "\\*";
				} else if(splitPattern.equals("+")){
					splitPattern = "\\+";
				}
				if(preToken.split(splitPattern).length > 0 && preToken.split(splitPattern)[0].length() > 0){
					tokens.addAll(splitDifferentLanguages(preToken.split(splitPattern)[0]));
				}
				tokens.addAll(splitDifferentLanguages(character+""));
				splitToken(preToken.substring(preToken.indexOf(character)+1), tokens);
				break;
			}
		}
		if(!splitted){
			tokens.add(new Token(preToken));
		}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method: given a string of a token checks and splits subparts that are Greek and English
	 * @param token
	 * @return
	 */
	private static List<Token> splitDifferentLanguages(String token){
		List<Token> subtokens = new ArrayList<Token>();

		Pattern latinCharacter = Pattern.compile("\\w");
		Matcher m = latinCharacter.matcher(token.charAt(0)+"");
		boolean isEnglish = m.find();
		List<Integer> positionsOfInterest = new ArrayList<Integer>();
		positionsOfInterest.add(0);
		for(int i=1; i<token.length(); ++i){
			m = latinCharacter.matcher(token.charAt(i)+"");
			boolean thisIsEnglish = m.find();
			if(isEnglish != thisIsEnglish){
				positionsOfInterest.add(i);
				isEnglish = thisIsEnglish;
			}
		}
		
		for(int i=0; i<positionsOfInterest.size(); ++i){
			subtokens.add(new Token(token.substring(positionsOfInterest.get(i), (i+1)<positionsOfInterest.size()?positionsOfInterest.get(i+1):token.length())));
		}
		
		return subtokens;
	}
	
}
