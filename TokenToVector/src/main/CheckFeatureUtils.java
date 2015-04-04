package main;

public class CheckFeatureUtils {

	/**
	 * Checks whether the given string is a number
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether the given name is written Greek
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isGreek(String name) {
		return name.matches("\\p{InGreek}+(?:['\\-]\\p{InGreek}+)*|\\d+");
	}
	
	/**
	 * Checks whether the given name is written Latin
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isLatin(String name) {
	    return name.matches("\\p{IsLatin}+(?:['\\-]\\p{IsLatin}+)*|\\d+");
	}
	
	/**
     * Returns the length of the longest shared prefix of the two
     * input strings.
     *
     * @param a First string.
     * @param b Second string.
     * @return The length of the longest shared prefix of the two
     * strings.
     */
    public static int sharedPrefixLength(String a, String b) {
        int end = java.lang.Math.min(a.length(),b.length());
        for (int i = 0; i < end; ++i) 
            if (a.charAt(i) != b.charAt(i))
                return i;
        return end;
    }
	
}
