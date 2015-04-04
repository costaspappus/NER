package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {

	public static int articleToStartFrom = 0;
	public static int articlesToEndTo = 393/*tokens.size()*/;
	public static boolean testingPersons = false;
	public static List<String> perOrder = Arrays.asList(new String[]{"164","11","156","169","94","67","95","157","158","66","159","160","171","161","144","155","162","167","53","88","170","74","75","51","87","93","82","65","83","12","81","86","10","64","175","85","115","39","166","76","101","68","122","89","58","59","38","46","172","145","55","47","90","80","69","60","109","110","149","54","48","141","37","150","52","29","2","8","4","117","137","121","113","43","103","1","63","73","143","111","6","151","70","45","119","3","154","104","34","153","49","50","152","118","40","5","56","107","84","17","126","44","30","26","77","19","24","136","138","27","31","132","28","35","32","36","33","92","25","9","133","173","134","13","7","135","168","14","23","130","22","21","20","15","18","16","131","127","129","78","100","79","102","71","105","106","99","148","147","98","142","96","174","97","146","72","139","41","125","123","124","91","108","128","42","165","120","57","116","163","112","62","61","114","140","176"});
	public static List<String> orgOrder = Arrays.asList(new String[]{"164","175","170","10","174","143","168","66","94","38","173","67","172","171","115","167","46","39","88","86","87","58","158","59","157","61","159","69","122","160","137","101","161","156","51","93","40","2","162","148","149","45","74","85","165","169","76","65","53","96","150","138","153","41","8","47","154","83","102","9","62","104","63","29","95","136","166","152","54","77","26","57","90","24","151","92","1","114","98","111","60","5","145","142","141","89","42","97","36","6","20","23","116","12","28","30","22","75","72","19","35","4","34","139","33","37","135","7","147","43","3","134","133","32","31","18","132","21","16","14","17","15","13","146","144","140","27","25","11","44","49","163","82","112","80","81","79","131","73","113","155","84","110","109","100","99","108","103","105","106","91","107","71","70","117","128","55","127","129","52","130","50","48","56","126","68","119","118","125","120","64","121","123","124","78","176"});
	
	public static List<String> defaultOrder = Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160","161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176"});
	
	public static String filePath = "C:\\Users\\Konstantinos Pappas\\workspace\\TokenToVector\\resources\\shuffledTokens.ser";
	
	// Person Lists
	// stores tokens (contents) with length 1-2 that directly precede person tokens in the training corpus and their frequencies
	private static Map<String, Long> p_1_1_2;//OK
	// stores tokens (contents) with length 3-4 that directly precede person tokens in the training corpus and their frequencies
	private static Map<String, Long> p_1_3_4;//OK
	// stores tokens (contents) with length more than 4 that directly precede person tokens in the training corpus and their frequencies
	private static Map<String, Long> p_1_4;//OK

	// stores tokens (contents) with length 1-2 that precede person tokens up to 7 positions behind in the training corpus and their frequencies
	private static Map<String, Long> p_7_1_2;//OK
	// stores tokens (contents) with length 3-4 that precede person tokens up to 7 positions behind in the training corpus and their frequencies
	private static Map<String, Long> p_7_3_4;//OK
	// stores tokens (contents) with length more than 4 that precede person tokens up to 7 positions behind in the training corpus and their frequencies
	private static Map<String, Long> p_7_4;//OK
	
	// Organization Lists
	// stores tokens (contents) with length 1-2 that directly precede organization tokens in the training corpus and their frequencies
	private static Map<String, Long> r_1_1_2;//OK
	// stores tokens (contents) with length 3-4 that directly precede organization tokens in the training corpus and their frequencies
	private static Map<String, Long> r_1_3_4;//OK
	// stores tokens (contents) with length more than 4 that directly precede organization tokens in the training corpus and their frequencies
	private static Map<String, Long> r_1_4;//OK

	// stores tokens (contents) with length 1-2 that precede organization tokens up to 7 positions behind in the training corpus and their frequencies
	private static Map<String, Long> r_7_1_2;//OK
	// stores tokens (contents) with length 3-4 that precede organization tokens up to 7 positions behind in the training corpus and their frequencies
	private static Map<String, Long> r_7_3_4;//OK
	// stores tokens (contents) with length more than 4 that precede organization tokens up to 7 positions behind in the training corpus and their frequencies
	private static Map<String, Long> r_7_4;//OK
	

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		// deserialize the tokens.ser file
		
		// use buffering
		ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		
		try {
			System.out.println("Reading Tokens..");
			@SuppressWarnings("unchecked")
			List<List<Token>> tokens = (List<List<Token>>) input.readObject();
			
			System.out.println("Initializing Lists...");
			fillLists(tokens);
			
			List<Object> vectors = new ArrayList<Object>();
			
			System.out.println("Creating Vectors...");
			
			for (int i = articleToStartFrom; i < articlesToEndTo; i++) {
				
				if(i>=289 && i<360){
					continue;
				}
				
				List<Token> tokenList = tokens.get(i);
				List<Integer> organisationPositions = FeatureUtils.getOrganizationPositions(tokenList);
				for (int j = 0; j < tokenList.size(); j++) {
					if( (testingPersons && tokenList.get(j).isNotAPersonSureFired()) || 
							(!testingPersons && tokenList.get(j).isNotAnOrganizationSureFired()) ||
							tokenList.get(j).isTemporalExpression() )
							{
						continue;
					}
					List<Object> featureVector = new ArrayList<Object>();
					// 1-7
					featureVector.addAll(FeatureUtils.commaFeatures(j, tokenList)); //OK
					// 8-14
					featureVector.addAll(FeatureUtils.fullStopFeatures(j, tokenList));//OK
					// 15-21
					featureVector.addAll(FeatureUtils.dashFeatures(j, tokenList));//OK
					// 22-28
					featureVector.addAll(FeatureUtils.slashFeatures(j, tokenList));//OK
					// 29-35
					featureVector.addAll(FeatureUtils.isNumberFeatures(j, tokenList));//OK
					// 36-42
					//mention: only Greek characters, no numbers or special characters etc.-----------------------------------------------
					featureVector.addAll(FeatureUtils.greekFeatures(j, tokenList));//OK
					// 43-49
					//mention: only Latin characters, no numbers or special characters etc.-----------------------------------------------
					featureVector.addAll(FeatureUtils.latinFeatures(j, tokenList));//OK
					// 50-56
					//mention: first character must not be a number or special character etc.---------------------------------------------
					featureVector.addAll(FeatureUtils.firstCapitalFeatures(j, tokenList));//OK
					// 57-63
					//mention: token must not contain numbers, special characters etc.----------------------------------------------------
					featureVector.addAll(FeatureUtils.allCapitalFeatures(j, tokenList));//OK
					// 64-70
					//ASSUMPTION: if e.g. t-3 is null I return 'null'---------------------------------------------------------------------
					featureVector.addAll(FeatureUtils.lengthNormalizedFeatures(j, tokenList));//OK
					// 71-77
					// MISSING INFORMATION: the common prefixes are not given in the paper -- had to check source code -------------------
					featureVector.addAll(FeatureUtils.surnamePrefixesFeatures(j, tokenList));//OK
					// 78-84
					// MISSING INFORMATION: the common suffixes are not given in the paper -- had to check source code ------------------- 
					featureVector.addAll(FeatureUtils.surnameSuffixesFeatures(j, tokenList));//OK
					// 85-91
					// MISSING INFORMATION: the common first names are not given in the paper -- had to check source code ----------------
					featureVector.addAll(FeatureUtils.firstNameFeatures(j, tokenList));//OK
					// 92-98
					featureVector.addAll(FeatureUtils.lastCharacterFeatures(j, tokenList));//OK
					// 99-105
					// MISSING INFORMATION: the common adjective endings are not given in the paper -- had to check source code ----------
					featureVector.addAll(FeatureUtils.signularAdjectiveEndingsFeatures(j, tokenList));//OK
					// 106-112
					// MISSING INFORMATION: the common plural noun and adjective endings are not given in the paper -- had to check source code ----------
					featureVector.addAll(FeatureUtils.pluralNounEndingsFeatures(j, tokenList));//OK
					// 113-119
					// MISSING INFORMATION: the paper does not give the genitive endings - had to check source code, which was different than description!!!-----
					featureVector.addAll(FeatureUtils.genitiveEndingsFeatures(j, tokenList));//OK
					// 120-126
					featureVector.addAll(FeatureUtils.finalSigmaFeatures(j, tokenList));//OK
					// 127-133
					// MISSING INFORMATION: the paper does not give the organization abbreviations - had to check source code ------------
					featureVector.addAll(FeatureUtils.organisationDistanceFeatures(j, tokenList, organisationPositions));//OK
					// 134-140
					// MISSING INFORMATION: the paper says that they search for words like υπουγείο and χρηματιστήριο while in the source
					// code I see that are looking for αφοι as well. But not on the current token but on the previous!!! -----------------------------------
					// I implement it like this too.
					featureVector.addAll(FeatureUtils.ministryFeatures(j, tokenList));//OK
					// 141-147
					featureVector.addAll(FeatureUtils.lastTokenFeatures(j, tokenList));//OK
					// 148-154
					// CANNOT IMPLEMENT: they used information from the HTML page that they downloaded. I don't have that information, so --------------------
					// instead I implemented the following: all tokens that are before the end of line character are considered to be
					// part of the title. All articles are assumed to have at some tokens that are titles (e.g. if the article starts
					// with the line change character then I proceed until I actually find some non-zero length tokens)
					featureVector.addAll(FeatureUtils.newsArticleFeatures(j, tokenList));//OK
					// 155-161
					// INCORRECT SCIENCE: in order to calculate this feature the category of the tokens must be known which cannot -----------------------
					// be the case on test instances. They must use the classification info, because tokens are classified sequentially
					featureVector.addAll(FeatureUtils.distanceFromStartNameFeatures(j, tokenList));//OK
					// 162
					featureVector.add(FeatureUtils.misterFeatures(j, tokenList));//OK
					// 163
					featureVector.add(FeatureUtils.pluralMisterFeatures(j, tokenList));//OK
					// 164
					featureVector.add(FeatureUtils.isInList(p_1_1_2, j, tokenList));//OK -- lists constructed at training time
					// 165
					featureVector.add(FeatureUtils.isInList(p_1_3_4, j, tokenList));//OK -- lists constructed at training time
					// 166
					featureVector.add(FeatureUtils.isInList(p_1_4, j, tokenList));//OK -- lists constructed at training time
					// 167
					featureVector.add(FeatureUtils.areInList(p_7_1_2, j, tokenList));//OK -- lists constructed at training time
					// 168
					featureVector.add(FeatureUtils.areInList(p_7_3_4, j, tokenList));//OK -- lists constructed at training time
					// 169
					featureVector.add(FeatureUtils.areInList(p_7_4, j, tokenList));//OK -- lists constructed at training time
					// 170
					featureVector.add(FeatureUtils.isInList(r_1_1_2, j, tokenList));//OK -- lists constructed at training time
					// 171
					featureVector.add(FeatureUtils.isInList(r_1_3_4, j, tokenList));//OK -- lists constructed at training time
					// 172
					featureVector.add(FeatureUtils.isInList(r_1_4, j, tokenList));//OK -- lists constructed at training time
					// 173
					featureVector.add(FeatureUtils.areInList(r_7_1_2, j, tokenList));//OK -- lists constructed at training time
					// 174
					featureVector.add(FeatureUtils.areInList(r_7_3_4, j, tokenList));//OK -- lists constructed at training time
					// 175
					featureVector.add(FeatureUtils.areInList(r_7_4, j, tokenList));//OK -- lists constructed at training time
					
					//class of the vector
					if(testingPersons){
						featureVector.add(tokenList.get(j).isPersonName()?"Y":"N");
					} else {
						featureVector.add(tokenList.get(j).isOrganizationName()?"Y":"N");
					}

					System.out.println("Reordering");
					featureVector = Order.reorder(featureVector, (testingPersons?perOrder:orgOrder));
					
					vectors.add(featureVector);
				}
				System.out.println("Processed File: " + i);
			}
			System.out.println("Processing Complete");
			System.out.println("Found " + vectors.size() + " vectors.");
			System.out.println("Printing");
			
			/*for(int m=60; m<=175; ++m){
				ArffPrinter.printVectors(vectors, articleToStartFrom, articlesToEndTo-1, m, (testingPersons?perOrder:orgOrder));
			}*/
			ArffPrinter.printVectors(vectors, articleToStartFrom, articlesToEndTo-1, 94, defaultOrder);
			
			System.out.println("Operation Completed Successfully");
			
		} finally {
			input.close();
		}
		
	}
	
	/**
	 * Constructs all the static lists
	 * 
	 * @param tokens
	 */
	private static void fillLists(List<List<Token>> tokens) {
		for (int i = articleToStartFrom; i < articlesToEndTo; i++) {

			if(i>=289 && i<360){
				continue;
			}
			
			List<Token> tokenList = tokens.get(i);
			for (int j = 0; j < tokenList.size(); j++) {
				Token token = tokenList.get(j);
				// Fill Person Lists
				if(j > 0 && token.isPersonName()) {
					String prevToken = tokenList.get(j-1).getContent().toLowerCase();
					Integer prevTokenLength = prevToken.length();
					if (prevTokenLength == 1 || prevTokenLength == 2) {
						p_1_1_2 = addTolist(p_1_1_2, prevToken);
					} else if (prevTokenLength == 3 || prevTokenLength == 4) {
						p_1_3_4 = addTolist(p_1_3_4, prevToken);
					} else if (prevTokenLength > 4) {
						p_1_4 = addTolist(p_1_4, prevToken);
					}
					// check the last 7 positions before the personToken
					int checkIdx = j - 1;
					while (checkIdx >= 0 && checkIdx >= j - 7) {
						String currToken = tokenList.get(checkIdx).getContent().toLowerCase();
						Integer currTokenLength = currToken.length();
						if (currTokenLength == 1 || currTokenLength == 2) {
							p_7_1_2 = addTolist(p_7_1_2, currToken);
						} else if (currTokenLength == 3 || currTokenLength == 4) {
							p_7_3_4 = addTolist(p_7_3_4, currToken);
						} else if (currTokenLength > 4) {
							p_7_4 = addTolist(p_7_4, currToken);
						}
						checkIdx--;
					}
				}
				// Fill Organisation Lists
				if(j > 0 && token.isOrganizationName()) {
					String prevToken = tokenList.get(j-1).getContent().toLowerCase();
					Integer prevTokenLength = prevToken.length();
					if (prevTokenLength == 1 || prevTokenLength == 2) {
						r_1_1_2 = addTolist(r_1_1_2, prevToken);
					} else if (prevTokenLength == 3 || prevTokenLength == 4) {
						r_1_3_4 = addTolist(r_1_3_4, prevToken);
					} else if (prevTokenLength > 4) {
						r_1_4 = addTolist(r_1_4, prevToken);
					}
					// check the last 7 positions before the organisationToken
					int checkIdx = j - 1;
					while (checkIdx >= 0 && checkIdx >= j - 7) {
						String currToken = tokenList.get(checkIdx).getContent().toLowerCase();
						Integer currTokenLength = currToken.length();
						if (currTokenLength == 1 || currTokenLength == 2) {
							r_7_1_2 = addTolist(r_7_1_2, currToken);
						} else if (currTokenLength == 3 || currTokenLength == 4) {
							r_7_3_4 = addTolist(r_7_3_4, currToken);
						} else if (currTokenLength > 4) {
							r_7_4 = addTolist(r_7_4, currToken);
						}
						checkIdx--;
					}
				}
			}
		}
		
		applyThresholds();
		/*StringBuilder listPrint = new StringBuilder();
		listPrint.append("Person Lists\n");
		listPrint.append("--- Per t-1 equals 1 or 2 ---\n");
		listPrint.append(p_1_1_2);
		listPrint.append("\n--- Per t-1 equals 3 or 4 ---\n");
		listPrint.append(p_1_3_4);
		listPrint.append("\n--- Per t-1 greater than 4 ---\n");
		listPrint.append(p_1_4);
		listPrint.append("\n--- Per t-7 equals 1 or 2 ---\n");
		listPrint.append(p_7_1_2);
		listPrint.append("\n--- Per t-7 equals 3 or 4 ---\n");
		listPrint.append(p_7_3_4);
		listPrint.append("\n--- Per t-7 greater than 4 ---\n");
		listPrint.append(p_7_4);
		
		listPrint.append("\n\nOrganisation Lists\n");
		listPrint.append("--- Org t-1 equals 1 or 2 ---\n");
		listPrint.append(r_1_1_2);
		listPrint.append("\n--- Org t-1 equals 3 or 4 ---\n");
		listPrint.append(r_1_3_4);
		listPrint.append("\n--- Org t-1 greater than 4 ---\n");
		listPrint.append(r_1_4);
		listPrint.append("\n--- Org t-7 equals 1 or 2 ---\n");
		listPrint.append(r_7_1_2);
		listPrint.append("\n--- Org t-7 equals 3 or 4 ---\n");
		listPrint.append(r_7_3_4);
		listPrint.append("\n--- Org t-7 greater than 4 ---\n");
		listPrint.append(r_7_4);
		listPrint.append("\n\n");
		
		System.out.println(listPrint);*/
		
	}

	/**
	 * A frequency threshold of 3 is applied to the Pt-1 lists, 
	 * i.e., tokens that do not occur at least three times in the training corpus 
	 * immediately before t0 are discarded. 
	 * For the Pt-7,...,t-1 lists,the threshold is 10.
	 */
	private static void applyThresholds() {
		removeItemsUnderThreshold(p_1_1_2, 3);
		removeItemsUnderThreshold(p_1_3_4, 3);
		removeItemsUnderThreshold(p_1_4, 3);
		removeItemsUnderThreshold(p_7_1_2, 10);
		removeItemsUnderThreshold(p_7_3_4, 10);
		removeItemsUnderThreshold(p_7_4, 10);
		
		removeItemsUnderThreshold(r_1_1_2, 3);
		removeItemsUnderThreshold(r_1_3_4, 3);
		removeItemsUnderThreshold(r_1_4, 3);
		removeItemsUnderThreshold(r_7_1_2, 10);
		removeItemsUnderThreshold(r_7_3_4, 10);
		removeItemsUnderThreshold(r_7_4, 10);
	}
	
	
	/**
	 * Removes all items from the map that occur less times than threshold
	 * @param map
	 * @param occurencies
	 */
	private static void removeItemsUnderThreshold(Map<String, Long> map, int threshold) {
		if (map != null && map.size() > 0) {
			Iterator<Map.Entry<String,Long>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry<String,Long> entry = iter.next();
			    if(entry.getValue() < threshold) {
			        iter.remove();
			    }
			}
		}
	}

	/**
	 * adds a new Item to a list. If the list doesn't exist, 
	 * it is created. Item is added marking at the same time its occurrence
	 * 
	 * @param map
	 * @param i
	 * @param j
	 */
	private static Map<String, Long> addTolist(Map<String, Long> map, String tokenContent) {
		if (tokenContent != null && tokenContent.trim().length() > 0) {
			if (map == null) {
				map = new HashMap<String, Long>();
			}
			Long freq = map.containsKey(tokenContent) ? map.get(tokenContent) : Long.valueOf(0);
			freq++;
			
			map.put(tokenContent, freq);
		}
		return map;
	}

}
