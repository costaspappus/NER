package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SureFire {

	private final static List<String> MINISTRY_LIKE = Arrays.asList(new String[]{"υπουργειο", "χρηματιστηριο", "αφοι"});
	private final static List<String> COMMON_GREEK_VERB_SUFFIXES = Arrays.asList(new String[]{"ωνω", "αι", "ει", "εις"});
	private final static List<String> PERSON_STOP_WORDS = new ArrayList<String>();
	private final static List<String> ORGANIZATION_STOP_WORDS = Arrays.asList(new String[]{"υπαρχει", "οποιος", "οποιες", "σημερα", "παρα", "τωρα", "μεταξυ",
			"επισης", "στους", "αφορα", "τοτε", "τοσο", "μπορει", "εκτος", "ουτε", "ακομη", "ακομα", "παντως", "εχει", "ετσι", "ωστε", "κυριως", "πλεον", "ιδιο",
			"ιδια", "ωστοσο", "μιας", "κανεις", "ενας", "εναν", "πρεπει", "τους", "οχι", "οτι", "οσο", "ως", "το", "τι", "τη", "τα", "σε", "ολα", "μετα", "οι", "να",
			"μη", "με", "κι", "θα", "εν", "περιπου", "μεχρι", "οταν", "μαλιστα", "μεγαλη", "ενος", "οπως", "μαζι", "οπου", "γινει", "των", "του", "τον", "τις",
			"εκατ", "μεσα", "της", "την", "ομως", "λογω", "στο", "στη", "στα", "ολες", "ηταν", "αυτες", "πως", "που", "πιο", "εχουν", "γιατι", "χθες", "κατα",
			"οποιο", "οποια", "νεο", "νεα", "μου", "μια", "μην", "μας", "μια", "δηλαδη", "ειχαν", "αφου", "προς", "πριν", "αυτο", "αυτη", "αυτα", "και", "ειχε",
			"πολυ", "κατι", "καθε", "κανει", "υπαρχουν", "τελευταια", "ενω", "καθως", "εδω", "δυο", "δρχ", "στον", "δισ", "στις", "δεν", "στην", "τροπο", "χωρις",
			"για", "εγινε", "απο", "ειναι", "μονο", "αλλα", "θεση", "αλλη", "ηδη", "πρωτο", "πρωτη", "ενα", ","	});
	
	static{
		PERSON_STOP_WORDS.addAll(ORGANIZATION_STOP_WORDS);
		PERSON_STOP_WORDS.add(",");
	}

	/**
	 * Identifies not person sure-fires. Changes the flag in curr
	 * @param prev
	 * @param curr
	 */
	public static void identifyNotPersonSureFire(Token prev, Token curr){
		if(curr == null){
			return;
		}
		if(prev!=null && prev.isPersonName())
		{
			return;
		}
		String token = curr.getContent();
		if(isNumeric(token) || isNonAlphabeticSymbol(token) || notStartWithCapital(token) || hasGreekVerbSuffix(token)
				|| PERSON_STOP_WORDS.contains(StringUtils.stripAccents(token).toLowerCase())){
			curr.setNotAPersonSureFired(true);
		}
	}

	/**
	 * Identifies not organization sure-fires. Changes the flag in curr
	 * @param curr
	 */
	public static void identifyNotOrganizationSureFire(Token curr){
		if(curr == null){
			return;
		}
		String token = curr.getContent();
		if(isNumeric(token) || isNonAlphabeticSymbol(token) || notStartWithCapitalANDNotSpecialOrgWord(token) || hasGreekVerbSuffix(token)
				|| ORGANIZATION_STOP_WORDS.contains(StringUtils.stripAccents(token).toLowerCase())){
			curr.setNotAnOrganizationSureFired(true);
		}
	}
	
	/**
	 * Returns true if the token has a common Greek verb suffix
	 * @param token
	 * @return
	 */
	private static boolean hasGreekVerbSuffix(String token){
		for(String suffix : COMMON_GREEK_VERB_SUFFIXES){
			if(token.endsWith(suffix)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if the token does not start with capital
	 * @param token
	 * @return
	 */
	private static boolean notStartWithCapital(String token){
		return !Character.isUpperCase(token.codePointAt(0));
	}
	
	/**
	 * Returns true if the token does not start with capital and is not a special organization name
	 * @param token
	 * @return
	 */
	private static boolean notStartWithCapitalANDNotSpecialOrgWord(String token){
		boolean startsWithMinistry = false;
		String temp = token.toLowerCase();
		temp = StringUtils.stripAccents(token);
		for (String prefix : MINISTRY_LIKE) {
			if (temp.startsWith(prefix)) {
				startsWithMinistry = true;
				break;
			}
		}
		return ( !Character.isUpperCase(token.codePointAt(0)) && !startsWithMinistry);
	}
	
	/**
	 * Returns whether or not the token is a non alphabetic symbol
	 * @param token
	 * @return
	 */
	private static boolean isNonAlphabeticSymbol(String token){
		if(token.length() == 1 && token.matches("[^A-Za-zΑ-Ωα-ω]")){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the given string is a number
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
}
