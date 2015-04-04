package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class FeatureUtils {
	
	private final static List<String> COMMON_PREFIXES = Arrays.asList(new String[]{"παπα", "μητρο", "μητροπολ", "χατζη", "δελη", "δελλα", "δελα", "καρα"});
	private final static List<String> COMMON_SUFFIXES = Arrays.asList(new String[]{"οπουλος", "οπουλου", "οπουλο", "ακης", "ακη", "ιδης", "ιδη", "ιδου", "αδης",
			"αδη", "αδου", "ωνης", "ωνη", "ακος", "ακου", "ακης", "ακη", "εας", "εα", "ογλου", "ατος", "ατου", "ατο"});
	private final static List<String> COMMON_FIRST_NAMES = Arrays.asList(new String[]{"αγγελος","ανθιμος","αννα","αννα-μαρια","αντα","αντη","αρτεμις","αρτεμης","αβρααμ",
			"αγαπη","αγαθαγγελος","αγαθη","αγγελικη","αγγελικα","αγλαϊα","αγνη","αδαμαντινη","αδριανα","αδριανος","αθανασιος","αθανασια","αθηνα","αικατερινη","αιμιλιος",
			"αιμιλια","ακριβη","ακυλας","αλεξανδρος","αλεξης","αλεξιος","αλεξανδρα","αλεξια","αλκιβιαδης","αμβροσιος","αναργυρος","ανεστης","αναστασιος","αναστασια",
			"ανδρεας","ανδριανη","ανδρονικος","αντιγονη","αντριαννα","αντωνια","αντωνης","αντωνιος","αποστολης","απολλων","αποστολος","αργυρω","αργυρης","αργυριος",
			"αριαδνη","αριστειδης","αριστοβουλος","αρσενιος","αρτεμης","αρτεμιος","ασκληπιος","ασπασια","αυγουστινος","αφροδιτη","αχιλλεας","βαια","βαιος","βικτωρας",
			"βαγγελης","βαλεντινη","βαρβαρα","βαρθολομαιος","βαρναβας","βασιλειος","βασιλης","βασιλικη","βερονικη","βικτωρια","βλασης","βλασιος","γαβριηλ","γερασιμος",
			"γεωργια","γεωργιος","γιαννα","γιαννης","γιωργος","γλυκερια","γρηγορια","γρηγορης","γρηγοριος","δεσποινα","δημητρα","δαμιανη","δαμιανος","δανιηλ","δαυιδ",
			"δημητρης","δημητριος","διομηδης","διονυσια","διονυσης","διονυσιος","δομνικη","δωροθεα","δωροθεος","ειρηνη","ειρηναιος","ελενη","ελευθεριος","ελευθερια",
			"ελισαβετ","ελπιδα","ελπινικη","εμμανουελα","εμμανουηλ","ερασμια","ερατω","ερμης","ερμιονη","ευαγγελος","ευαγγελια","ευανθια","ευγενιος","ευγενια","ευδοξια",
			"ευθαλια","ευθυμια","ευθυμης","ευθυμιος","ευλαμπιος","ευλαμπια","ευσεβιος","ευσταθιος","ευσταθια","ευστρατιος","ευστρατια","ευτερπη","ευτυχια","ευτυχης",
			"ευτυχιος","ευφημια","ευφροσυνη","ζηνων","ζηνωνας","ζησης","ζαχαρενια","ζαχαριας","ζηνοβια","ζωη","ηλιας","ησαϊας","θαλεια","θεκλα","θανασης","θεανω",
			"θεμιστοκλης","θεοδοσια","θεοδοσιος","θεοδωρα","θεολογος","θεοφανης","θεοδωρος","θεοφιλος","θωμας","θωμαη","θοδωρος","ιακωβος","ιασωνας","ιγνατιος","ιερεμιας",
			"ιεροθεος","ιορδανης","ιουλια","ιουνια","ισιδωρος","ισμηνη","ιωαννα","ιωαννης","ιωακειμ","ιωσηφ","κηρικος","καλλινικος","καλλιστη","καλλιροη","καλλιοπη",
			"κασσιανη","κασσιανη","κατερινα","κλειω","κλεονικη","κλεοπατρα","κοραλια","κορνηλιος","κοσμας","κυπριανη","κυπριανος","κυριακος","κυριακη","κωνσταντινα",
			"κωνσταντινος","κυριλλος","κωστας","λαζαρος","λαμπρος","λεων","λια","λαυρεντιος","λευτερης","λεωνιδας","λουκας","λουκια","λουκιανη","λουκιανος","λυδια",
			"μαξιμος","μαρθα","μαριος","μαρκος","μαγδαληνη","μακαριος","μανολης","μανωλης","μαρια","μαριεττα","μαρινα","μαργαριτα","μαριανθη","μαρκελλα","ματθαιος",
			"μεθοδιος","μελετιος","μελπομενη","μερκουριος","μηνας","μιλτιαδης","μιχαηλ","μπαμπης","μωϋσης","νεστωρας","νικη","ναθαναηλ","ναταλια","νεκταριος","νεκταρια",
			"νεοφυτος","νικητα","νικητας","νικηφορος","νικολεττα","νικοδημος","νικολαος","νικολας","ξενη","ξανθιππη","ξενοφων","ουρανια","ουρσουλα","πανος","πετρος",
			"παλλαδιος","παναγιωτα","παναγιωτης","πανδωρα","παντελης","παρασκευας","παρασκευη","παρθενα","παρθενιος","πασχαλης","παυλος","πελαγια","περικλης","πηγη",
			"πηνελοπη","πλατωνας","πολυμνια","πολυνικη","πολυξενη","πολυκαρπος","πορφυριος","πουλχερια","προκοπης","προκοπιος","προδρομος","ρανια","ραφαηλ","ρεβεκκα",
			"ρεμπεκα","σαββας","σεργιος","σιμων","σιμωνας","σαββουλα","σαμουηλ","σαπφω","σαραντης","σαχαριας","σεραφειμ","σμαραγδα","σοφια","σπυριδωνας","σπυριδουλα",
			"σπυρος","σταθης","στελα","στελιος","στεφανος","σταματης","σταματιος","σταματια","σταυρουλα","σταυρος","στρατος","στυλιανη","στυλιανος","συμεων","σωκρατης",
			"σωτηρης","σωτηρια","συλβια","τασος","τιτος","ταξιαρχης","τασια","τασα","τασω","τατιανα","τατιανη","τατιανος","τερψιχορη","τζενη","τιμοθεος","τριανταφυλλος",
			"τρυφωνας","φανης","φιλιππος","φανη","φανουρια","φανουρης","φιλημονας","φιλοθεη","φροσω","φωκας","φωτεινη","φωντας","φωτης","χαρης","χαρις","χαραλαμπια",
			"χαρουλα","χαιδω","χαραλαμπος","χαρικλεια","χαριτωνας","χαριτινη","χρηστος","χριστινα","χριστοδουλος","χριστοφορος","χρυσανθη","χρυσοστομος","χρυσανθος",
			"χρυσσα","ολγα"});
	private final static List<String> COMMON_LAST_CHARACTER = Arrays.asList(new String[]{"ς", "ν", "α", "ε", "ο", "η", "ι", "ο", "υ", "ω"});
	private final static List<String> COMMON_SING_ADJECTIVE_ENDING = Arrays.asList(new String[]{"κος", "κου", "κη", "κης", "κο", "υς", "υ", "ες"});
	private final static List<String> COMMON_PLURAL_NOUN_ENDING = Arrays.asList(new String[]{"οι", "ους", "ων", "ις"});
	private final static List<String> COMMON_GENITIVE_ENDING = Arrays.asList(new String[]{"ου", "ης"});
	private final static List<String> COMMON_LEGAL_TYPES = Arrays.asList(new String[]{"ΑΕ", "ΕΠΕ", "ΑΤΕ", "ΑΒΕΕ", "ΑΕΒΕ", "ΟΕ", "ΕΕ", "SA", "ΣΙΑ", "ΥΙΟΣ", "ΥΙΟΙ"});
	private final static List<String> MINISTRY_LIKE = Arrays.asList(new String[]{"υπουργειο", "χρηματιστηριο", "αφοι"});
	
	
	/**
	 * (no.1–7)
	 * Returns seven Boolean features 
	 * that indicate whether or not t−3, . . . , t3 are commas
	 *  
	 * @return
	 */
	public static List<Boolean> commaFeatures(int currentIdx, List<Token> tokenList){
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? tokenList.get(i).getContent().equals(",") : false);
		}
		
		return featureList;
	}
	
	/**
	 * (no.8–14) 
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 are full stops
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> fullStopFeatures(int currentIdx, List<Token> tokenList){
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? tokenList.get(i).getContent().equals(".") : false);
		}
		
		return featureList;
	}
	
	/**
	 * (no.15–21)
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 are dashes
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> dashFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? tokenList.get(i).getContent().equals("-") : false);
		}
		
		return featureList;
	}
	
	/**
	 * (no.22-28)
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 are slashes
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> slashFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? tokenList.get(i).getContent().equals("/") : false);
		}
		
		return featureList;
	}
	
	/**
	 * (no.29-35)
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 are numbers
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> isNumberFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? CheckFeatureUtils.isNumeric(tokenList.get(i).getContent()) : false);
		}
		
		return featureList;
	}
	
	/**
	 * (no.36-42)
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 are written in Greek characters
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> greekFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? CheckFeatureUtils.isGreek(tokenList.get(i).getContent()) : false);
		}
		
		return featureList;
	}
	
	/**
	 * (no.43-49)
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 are written in Latin characters
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> latinFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? CheckFeatureUtils.isLatin(tokenList.get(i).getContent()) : false);
		}
		
		return featureList;
	}
	
	/**
	 * (no.50-56)
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 have their first character as a capital
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> firstCapitalFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			if (isInRange(tokenList, i) && StringUtils.isNotEmpty(tokenList.get(i).getContent())) {
				featureList.add(Character.isUpperCase(tokenList.get(i).getContent().codePointAt(0)));
			} else {
				featureList.add(false);
			}
		}
		
		return featureList;
	}
	
	/**
	 * (no.57-63)
	 * Returns seven Boolean features
	 * that indicate whether or not t−3, . . . , t3 have their first character as a capital
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> allCapitalFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			featureList.add(isInRange(tokenList, i) ? StringUtils.isAllUpperCase(tokenList.get(i).getContent()) : false ); 
		}
		
		return featureList;
	}
	
	/**
	 * (no.64-70)
	 * Returns seven Number features
	 * that indicate the number of characters that t−3, . . . , t3 contain normalized to [−1, 1]
	 * the value −1 corresponds to a length of 1 character, +0.9 to 10 characters, 
	 * and +1 to more than ten characters.
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Double> lengthNormalizedFeatures(int currentIdx, List<Token> tokenList) {
		List<Double> featureList = new ArrayList<Double>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			if (isInRange(tokenList, i)) {
				Integer length = StringUtils.length(tokenList.get(i).getContent());
				Double value = null;
				// no token with length 0 exists (would have been removed on token creation)
				if (length >= 1 && length <= 10) {
					value = normalizeInNewRange(Double.valueOf(1), Double.valueOf(10), Double.valueOf(-1), Double.valueOf(0.9), Double.valueOf(length));
				} else if (length > 10) {
					value = Double.valueOf(1);
				}
				featureList.add(value);
			} else {
				featureList.add(null); 
			}
			
		}
		
		return featureList;
	}

	/**
	 * (no.71-77)
	 * Returns a list of 7 boolean values indicating whether
	 * t-3, . . . , t3 have prefixes that are common in Greek 
	 * surnames, such as "παπα", "καρα", "χατζη"
	 * 
	 * @param j
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> surnamePrefixesFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean startsWith = false;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					for (String prefix : COMMON_PREFIXES) {
						if (token.startsWith(prefix)) {
							startsWith = true;
							break;
						}
					}	
				}
			}
			featureList.add(startsWith);
		}
		
		return featureList;
	}

	/**
	 * (no.78-84)
	 * Returns a list of 7 boolean values indicating whether
	 * t-3, . . . , t3 have suffixes that are common in Greek 
	 * surnames, such as "ακης", "ιδης", "οπουλος"
	 * 
	 * @param j
	 * @param tokenList
	 * @return
	 */
	public static  List<Boolean> surnameSuffixesFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean endsWith = false;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					for (String suffix : COMMON_SUFFIXES) {
						if (token.endsWith(suffix)) {
							endsWith = true;
							break;
						}
					}
				}
			}
			featureList.add(endsWith);
		}
		
		return featureList;
	}

	/**
	 * (no.85-91)
	 * 350 common Greek first names
	 * 1 if token present in that list
	 * Τhe value of that feature indicates the length of the longest prefix 
	 * of t0 that matches the corresponding prefix of the alphabetically closest entry 
	 * of the list, normalized to [−1, 1] (−1 for no match, 1 for the longest possible match). 
	 * For instance, if t0 is "μιλτιαδου" and the closest entry of the list is "Μιλτιαδης", 
	 * the longest matching prefix is 7 characters long.
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Double> firstNameFeatures(int currentIdx, List<Token> tokenList) {
		List<Double> featureList = new ArrayList<Double>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		for (int i = fromIndex; i <= toIndex; i ++) {
			//Double match = Double.valueOf(-1);
			Double match = null;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					if (COMMON_FIRST_NAMES.contains(token)) {
						match = Double.valueOf(1);
					} else {
						int maxMatch = -1;
						String maxMatchName = "";
						for (String name: COMMON_FIRST_NAMES) {
							int mostCommonPrefix = CheckFeatureUtils.sharedPrefixLength(token, name);
							if (maxMatch < mostCommonPrefix) {
								maxMatch = mostCommonPrefix;
								maxMatchName = name;
							}
						}
						match = normalizeInNewRange(Double.valueOf(0), Double.valueOf(maxMatchName.length()), Double.valueOf(-1), Double.valueOf(1), Double.valueOf(maxMatch));
					}
				}
			}
			featureList.add(match);
		}
		return featureList;
	}

	/**
	 * (no.92-98)
	 * Returns a list of 7 boolean values indicating whether
	 * t−3, . . . , t3 end in "ς" (final sigma), ν, or a vowel
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> lastCharacterFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean endsWith = false;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					for (String suffix : COMMON_LAST_CHARACTER) {
						if (token.endsWith(suffix)) {
							endsWith = true;
							break;
						}
					}
				}
			}
			featureList.add(endsWith);
		}
		
		return featureList;
	}

	/**
	 * (no.99-105)
	 * Returns a list of 7 boolean values indicating whether
	 * t−3, . . . , t3 end with some common singular adjective 
	 * endings (e.g., "κος", "κου")
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> signularAdjectiveEndingsFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean endsWith = false;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					for (String suffix : COMMON_SING_ADJECTIVE_ENDING) {
						if (token.endsWith(suffix)) {
							endsWith = true;
							break;
						}
					}
				}
			}
			featureList.add(endsWith);
		}
		
		return featureList;
	}

	/**
	 * (no.106-112)
	 * Returns a list of 7 boolean values indicating whether
	 * t−3, . . . , t3 end with common plural noun and adjective endings (e.g., "οι", "ους")
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> pluralNounEndingsFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean endsWith = false;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					for (String suffix : COMMON_PLURAL_NOUN_ENDING) {
						if (token.endsWith(suffix)) {
							endsWith = true;
							break;
						}
					}
				}
			}
			featureList.add(endsWith);
		}
		
		return featureList;
	}

	/**
	 * (no.113-119)
	 * Returns a list of 7 boolean values indicating whether
	 * t−3, . . . , t3 end with common plural noun and adjective endings (e.g., "ου", "ης")
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> genitiveEndingsFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean endsWith = false;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					for (String suffix : COMMON_GENITIVE_ENDING) {
						if (token.endsWith(suffix)) {
							endsWith = true;
							break;
						}
					}
				}
			}
			featureList.add(endsWith);
		}
		
		return featureList;
	}

	/**
	 * (no.120-126)
	 * Returns a list of 7 boolean values indicating whether
	 * t−3, . . . , t3 end in "ς"
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> finalSigmaFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean endsWith = false;
			if (isInRange(tokenList, i)) {
				String token = tokenList.get(i).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					if (token.endsWith("ς")) {
						endsWith = true;
					}
				}
			}
			featureList.add(endsWith);
		}
		
		return featureList;
	}

	/**
	 * (no.127-133)
	 * Returns a list of 7 Double values indicating 
	 * abbreviations of legal types of companies that are
	 * common in Greek organization names (e.g., "A.E.", "E.P.E.") in a window of ±10 
	 * tokens around each one of t−3, . . . , t3. The values of these features depend on the
	 * distance of the closest legal type abbreviation from ti: if the abbreviation starts 10
	 * tokens or more before ti, the value is −1; if it starts 10 tokens or more after ti,
	 * the value is 1; and if it starts in a window ±10 around ti, the feature takes a value
	 * in (−1, 1) proportional to its distance.
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Double> organisationDistanceFeatures(int currentIdx, List<Token> tokenList, List<Integer> organisationPositions) {
		List<Double> featureList = new ArrayList<Double>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		for (int i = fromIndex; i <= toIndex; i ++) {
			Double value = null;
			if (isInRange(tokenList, i)){
				//returns pos as -1 if no legal type found on this article
				int pos = getPositionOfClosestLegalType(i, tokenList, organisationPositions);
				if(pos == -1){
					//do nothing, value will be null
				} else if(pos<i){
					Double distance = Double.valueOf(Math.abs(pos-i));
					if (distance>=10){
						value = Double.valueOf(-1);
					} else {
						value = (-1)-(normalizeInNewRange(Double.valueOf(0), Double.valueOf(9), Double.valueOf(-1), Double.valueOf(0), distance));
					}
				} else {
					Double distance = Double.valueOf(Math.abs(pos-i));
					if (Math.abs(pos-i)>=10){
						value = Double.valueOf(1);
					} else {
						value = normalizeInNewRange(Double.valueOf(0), Double.valueOf(9), Double.valueOf(0), Double.valueOf(1), distance);
					}
				}
			}
			featureList.add(value);
		}
		return featureList;
	}

	/**
	 * (no.134-140)
	 * Returns a list of 7 boolean values indicating whether
	 * t−3, . . . , t3 is one of (e.g., "υπουργειο", "χρηματιστηριο")
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> ministryFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean previousStartsWithMinistry = false;
			if (isInRange(tokenList, i-1)) {
				String token = tokenList.get(i-1).getContent().toLowerCase();
				if (CheckFeatureUtils.isGreek(token)) {
					token = StringUtils.stripAccents(token);
					for (String prefix : MINISTRY_LIKE) {
						if (token.startsWith(prefix)) {
							previousStartsWithMinistry = true;
							break;
						}
					}
				}
			}
			featureList.add(previousStartsWithMinistry);
		}
		
		return featureList;
	}

	/**
	 * (no.141-147)
	 * Returns a list of 7 boolean values indicating whether
	 * t−3, . . . , t3 is the last token of a sentence
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> lastTokenFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean isLastToken = false;
			if (isInRange(tokenList, i)) {
				if (tokenList.get(i).isEndOfSentence()) {
					isLastToken = true;
				}
			}
			featureList.add(isLastToken);
		}
		
		return featureList;
	}

	/**
	 * (no.148-154)
	 * Returns a list of 7 boolean values indicating whether
	 * t-3, . . . , t3 are parts of the title of a news article
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> newsArticleFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		int fromIndex = currentIdx - 3;
		int toIndex = currentIdx + 3;
		
		for (int i = fromIndex; i <= toIndex; i ++) {
			boolean isTitle = false;
			if (isInRange(tokenList, i)) {
				isTitle = tokenList.get(i).isTitle();
			}
			featureList.add(isTitle);
		}
		
		return featureList;
	}

	/**
	 * (no.155-161)
	 * Returns a list of 7 boolean values indicating whether
	 * the distance of t0 from the first token of a continuous sequence 
	 * of person tokens (in the case of the persons) or organization tokens 
	 * (in the case of the organizations) that directly precede t0;
	 * for example, in the person name "Γεώργιος Αλέξανδρος Μαγκάκης" the distance of
	 * the last token from the first one is 3. These features (155–161) are all Boolean, and
	 * they show if the distance is <= 1, 2, 3, . . . , 7 tokens.
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static List<Boolean> distanceFromStartNameFeatures(int currentIdx, List<Token> tokenList) {
		List<Boolean> featureList = new ArrayList<Boolean>();
		
		int distance = 0;
		int index = 1;
		while(isInRange(tokenList, currentIdx-index)){
			if(tokenList.get(currentIdx-index).isPersonName()){
				distance = index;
			}
			index++;
		}
		
		if(distance == 1){
			featureList.add(true);featureList.add(true);featureList.add(true);featureList.add(true);
			featureList.add(true);featureList.add(true);featureList.add(true);
		} else if(distance == 2){
			featureList.add(false);featureList.add(true);featureList.add(true);featureList.add(true);
			featureList.add(true);featureList.add(true);featureList.add(true);
		} else if(distance == 3){
			featureList.add(false);featureList.add(false);featureList.add(true);featureList.add(true);
			featureList.add(true);featureList.add(true);featureList.add(true);
		} else if(distance == 4){
			featureList.add(false);featureList.add(false);featureList.add(false);featureList.add(true);
			featureList.add(true);featureList.add(true);featureList.add(true);
		} else if(distance == 5){
			featureList.add(false);featureList.add(false);featureList.add(false);featureList.add(false);
			featureList.add(true);featureList.add(true);featureList.add(true);
		} else if(distance == 6){
			featureList.add(false);featureList.add(false);featureList.add(false);featureList.add(false);
			featureList.add(false);featureList.add(true);featureList.add(true);
		} else if(distance == 7){
			featureList.add(false);featureList.add(false);featureList.add(false);featureList.add(false);
			featureList.add(false);featureList.add(false);featureList.add(true);
		} else {
			featureList.add(false);featureList.add(false);featureList.add(false);featureList.add(false);
			featureList.add(false);featureList.add(false);featureList.add(false);
		}
		
		return featureList;
	}

	/**
	 * (no.162)
	 * Returns a boolean indication whether or not t0 is directly preceded by "κ."
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static Boolean misterFeatures(int currentIdx, List<Token> tokenList) {
		Boolean foundMister = false;
		
		if(isInRange(tokenList, currentIdx) && isInRange(tokenList, currentIdx-1) && isInRange(tokenList, currentIdx-2) &&
				tokenList.get(currentIdx-1).getContent().equals(".") && tokenList.get(currentIdx-2).getContent().equals("κ")){
			foundMister = true;
		}
		
		return foundMister;
	}

	/**
	 * (no.163)
	 * Returns a boolean indication whether or not t0 is preceded by "κ.κ." or "κκ."
	 * in a window of 10 tokens to the left of t0.
	 * 
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static Boolean pluralMisterFeatures(int currentIdx, List<Token> tokenList) {
		Boolean foundMister = false;
		int fromIndex = currentIdx - 10;
		int toIndex = currentIdx;
		
		for (int i = fromIndex; i < toIndex; i ++) {
			if (isInRange(tokenList, i) && i<currentIdx) {
				if (tokenList.get(i).getContent().equals("κ")) {
					if (isInRange(tokenList, i + 1) && i+1<currentIdx) {
						if (tokenList.get(i + 1).getContent().equals(".")) {
							if(isInRange(tokenList, i+2) && i+2<currentIdx){
								if (tokenList.get(i + 2).getContent().equals("κ")) {
									if(isInRange(tokenList, i+3) && i+3<currentIdx){
										if (tokenList.get(i + 3).getContent().equals(".")) {
											foundMister = true;
											break;
										}
									}
								}
							}
						}
					}
				} else if (tokenList.get(i).getContent().equals("κκ")){
					if (isInRange(tokenList, i + 1) && i+1<currentIdx) {
						if (tokenList.get(i + 1).getContent().equals(".")) {
							foundMister = true;
							break;
						}
						
					}
				}
			}
		}
		return foundMister;
	}

	/**
	 * (no.164-166 && 170-172)  
	 * The returned value is the frequency of t−1, as stored in
	 * the corresponding list and normalized to (−1, 1], or −1 if t−1 is not present in the 
	 * corresponding list.
	 * 
	 * @param map
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static Double isInList(Map<String, Long> map, int currentIdx, List<Token> tokenList) {
		Double feature = null;
		if (currentIdx > 0) {
			String checkToken = tokenList.get(currentIdx - 1).getContent().toLowerCase();
			if (map.containsKey(checkToken)) {
				Double normalizedMin = Double.valueOf(-0.9);
				Double normalizedMax = Double.valueOf(1);
				Double maxValue = Double.valueOf(Collections.max(map.values()).longValue());
				Double minValue = Double.valueOf(Collections.min(map.values()).longValue());

				feature = normalizeInNewRange(minValue, maxValue, normalizedMin, normalizedMax,	Double.valueOf(map.get(checkToken)));
			} else {
				feature = Double.valueOf(-1);
			}
		}
		return feature;
	}
	
	/**
	 * (no.167-169 && 173-175)
	 * The returned value is the sum of the frequencies of the particular tokens t−7, . . . , t−1 
	 * that precede t0, as recorded in the corresponding list, normalized to [−1, 1].
	 * A feature value of 1 corresponds to a sum-value equal to, or greater than twice
	 * the average sum value, as estimated from the training corpus, −1 corresponds to a
	 * zero sum-value, and intermediate sum-values are distributed uniformly in (−1, 1).
	 * If any of t−7, . . . , t−1 does not belong to the same sentence as t0, that token is ignored,
	 * i.e., its frequency does not contribute to the sum.
	 * @param map
	 * @param currentIdx
	 * @param tokenList
	 * @return
	 */
	public static Double areInList(Map<String, Long> map, int currentIdx, List<Token> tokenList) {
		Double value = null;
		List<String> tokensOfInterest = new ArrayList<String>();
		
		for(int i=1; i<=7; ++i){
			if(!included(tokenList, currentIdx-i, tokensOfInterest)){
				break;
			}
		}
		// now I have all of the interesting tokens available
		long sum = 0;
		for(String token : tokensOfInterest)
			if(map.containsKey(token))
				sum += map.get(token);
		
		long listSum = 0;
		for(Long freq : map.values()){
			listSum += freq;
		}
		double avgSum = listSum / map.size();
		
		if(sum == 0){
			value = Double.valueOf(-1);
		} else if (sum >= 2*avgSum){
			value = Double.valueOf(1);
		} else {
			Double normalizedMin = Double.valueOf(-1);
			Double normalizedMax = Double.valueOf(1);
			Double minValue = 0.0;
			Double maxValue = 2*avgSum;
			
			value = normalizeInNewRange(minValue, maxValue, normalizedMin, normalizedMax, Double.valueOf(sum));
		}
		
		return value;
	}
	
	/**
	 * Given an article it returns the positions where an organization name starts
	 * @param tokenList
	 * @return
	 */
	public static List<Integer> getOrganizationPositions(List<Token> tokenList){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i=0; i<tokenList.size(); ++i){
			if(isLegalType(i, tokenList)){
				positions.add(i);
			}
		}
		return positions;
	}
	
	//help functions ahead
	
	/**
	 * If the token specified is within range and is not end of sentence it is included
	 * @param tokenList
	 * @param idx
	 * @param tokensOfInterest
	 * @return
	 */
	private static boolean included(List<Token> tokenList, int idx, List<String> tokensOfInterest){
		if(isInRange(tokenList, idx)){
			if(!tokenList.get(idx).isEndOfSentence()){
				String newToken = tokenList.get(idx).getContent().toLowerCase();
				tokensOfInterest.add(newToken);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the given index can be used to retrieve an item from the given list
	 * 
	 * @param list
	 * @param index
	 * @return
	 */
	private static boolean isInRange(List<Token> list, int index) {
		return index >= 0 && index < list.size();
	}
	
	/**
	 * Convert a number range to another range, maintaining ratio
	 * 
	 * @param oldMin
	 * @param oldMax
	 * @param newMin
	 * @param newMax
	 * @param oldValue
	 * @return
	 */
	private static Double normalizeInNewRange(Double oldMin, Double oldMax, Double newMin, Double newMax, Double oldValue) {
		Double newValue = null;
		Double oldRange = (oldMax - oldMin);
		// if old range is zero then all values would be equal to oldMin. So, the new value will be the newMin.
		if (oldRange == 0) {
			newValue = newMin;
		} else {
			Double newRange = (newMax - newMin);  
			newValue = (((oldValue - oldMin) * newRange) / oldRange) + newMin;
		}
		// round the double value to the third decimal
		return round(newValue, 3);
	}
	
	/**
	 * Rounds a double at the given number of decimals
	 * @param value
	 * @param places
	 * @return
	 */
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	/**
	 * Returns the position of the closest legal type to position currentIdx
	 * If no legal type exists in the article it returns -1
	 * @param currentIdx
	 * @param tokenList
	 * @param organisationPositions
	 * @return
	 */
	private static int getPositionOfClosestLegalType(int currentIdx, List<Token> tokenList, List<Integer> organisationPositions){
		int pos = -1;
		if(!isInRange(tokenList, currentIdx)){
			return pos;
		}
		int closestDistance = Integer.MAX_VALUE;
		
		for(Integer orgPos : organisationPositions){
			if(Math.abs(currentIdx-orgPos) < closestDistance){
				closestDistance = Math.abs(currentIdx-orgPos);
				pos = orgPos;
			}
		}
		return pos;
	}
	
	/**
	 * Returns true if a legal type starts at position pos
	 * @param pos
	 * @param tokenList
	 * @return
	 */
	//Α.Ε. - Ο.Ε. - Ε.Ε. - S.A. - Ε.Π.Ε. - Α.Τ.Ε. - Α.Β.Ε.Ε. - Α.Ε.Β.Ε.
	private static boolean isLegalType(int pos, List<Token> tokenList){
		// check if token at position pos is a legal type
		if(COMMON_LEGAL_TYPES.contains(tokenList.get(pos).getContent().toUpperCase())){
			return true;
		}
		// now check for Α.Ε. - Ο.Ε. - Ε.Ε. - S.A.
		if((pos+3) < tokenList.size()){
			String one = tokenList.get(pos).getContent().toUpperCase();
			String two = tokenList.get(pos+1).getContent().toUpperCase();
			String three = tokenList.get(pos+2).getContent().toUpperCase();
			String four = tokenList.get(pos+3).getContent().toUpperCase();
			if(one.equals("Α") && two.equals(".") && three.equals("Ε") && four.equals(".")){
				return true;
			} else if(one.equals("Ο") && two.equals(".") && three.equals("Ε") && four.equals(".")){
				return true;
			} else if(one.equals("Ε") && two.equals(".") && three.equals("Ε") && four.equals(".")){
				return true;
			} else if(one.equals("S") && two.equals(".") && three.equals("A") && four.equals(".")){
				return true;
			}
		}
		// now check Ε.Π.Ε. - Α.Τ.Ε.
		if((pos+5) < tokenList.size()){
			String one = tokenList.get(pos).getContent().toUpperCase();
			String two = tokenList.get(pos+1).getContent().toUpperCase();
			String three = tokenList.get(pos+2).getContent().toUpperCase();
			String four = tokenList.get(pos+3).getContent().toUpperCase();
			String five = tokenList.get(pos+4).getContent().toUpperCase();
			String six = tokenList.get(pos+5).getContent().toUpperCase();
			if(one.equals("Ε") && two.equals(".") && three.equals("Π") && four.equals(".") && five.equals("Ε") && six.equals(".")){
				return true;
			} else if(one.equals("Α") && two.equals(".") && three.equals("Τ") && four.equals(".") && five.equals("Ε") && six.equals(".")){
				return true;
			}
		}
		// now check Α.Β.Ε.Ε. - Α.Ε.Β.Ε.
		if((pos+7) < tokenList.size()){
			String one = tokenList.get(pos).getContent().toUpperCase();
			String two = tokenList.get(pos+1).getContent().toUpperCase();
			String three = tokenList.get(pos+2).getContent().toUpperCase();
			String four = tokenList.get(pos+3).getContent().toUpperCase();
			String five = tokenList.get(pos+4).getContent().toUpperCase();
			String six = tokenList.get(pos+5).getContent().toUpperCase();
			String seven = tokenList.get(pos+6).getContent().toUpperCase();
			String eight = tokenList.get(pos+7).getContent().toUpperCase();
			if(one.equals("Α") && two.equals(".") && three.equals("Β") && four.equals(".") && five.equals("Ε") && six.equals(".") && seven.equals("Ε") && eight.equals(".")){
				return true;
			} else if(one.equals("Α") && two.equals(".") && three.equals("Ε") && four.equals(".") && five.equals("Β") && six.equals(".") && seven.equals("Ε") && eight.equals(".")){
				return true;
			}
		}
		
		return false;
	}
}
