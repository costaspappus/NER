/**
 * Sentence Splitter.
 * Copyright (C) 2005  G. Lucarelli
 *
 * This file is part of Sentence Splitter.
 *
 * Sentence Splitter is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Sentence Splitter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package ipl.sentence_splitter.core;

import java.util.regex.*;

/**
 * <p>Description: Manages the feature vectors of a text.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 */
public class FeaturesManager
{

  /**
   * The tokens.
   */
  private Tokenizer tokens;

  /**
   * The position of current token.
   */
  private int offset;

  /**
   *
   * @param text String
   */
  public FeaturesManager(String text)
  {
    this.offset = 0;
    this.tokens = new Tokenizer(text);
  } // contructor: FeaturesManager

  /**
   * Returns the list of tokens.
   *
   * @return Tokenizer
   */
  public Tokenizer getTokens()
  {
    return tokens;
  } // getTokens

  /**
   * Returns the <code>offset</code>.
   *
   * @return int
   */
  public int getOffset()
  {
    return offset;
  } // getOffset

  /**
   * Resets the <code>offset</code>.
   */
  public void reset()
  {
    offset = 0;
  } // reset

  //////////////////////////////////////////////////////////////////////////////
  //           The following functions help to features retrieval.            //
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Returns the position of first dot before <code>from</code>.
   *
   * @param from int
   * @return int
   */
  private int findPrevious(int from) {
    int i = from;
    while ((i >= 0) && (tokens.get(i).getToken().compareTo(".") != 0)) {
      i--;
    } // while
    return i;
  } // findPrevious

  /**
   * Returns the position of first dot after <code>from</code>.
   *
   * @param from int
   * @return int
   */
  private int findNext(int from) {
    int i = from;
    while ((i < tokens.size()) && (tokens.get(i).getToken().compareTo(".") != 0)) {
      i++;
    } // while
    return i;
  } // findNext

  /**
   * Returns 1 if the token at position <code>pos</code> ends to vowel,
   * otherwise returns -1.
   *
   * @param pos int
   * @return String
   */
  private String endsToVowel(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= tokens.size()) {
      return "1";
    } // if

    char ch = tokens.get(pos).getToken().charAt(tokens.get(pos).getToken().length() - 1) ;
    /*if ( (ch == 'á') || (ch == 'å') || (ch == 'ç') || (ch == 'é') || (ch == 'ï') ||
        (ch == 'õ') || (ch == 'ù') ||
        (ch == 'Ü') || (ch == 'Ý') || (ch == 'Þ') || (ch == 'ß') || (ch == 'ü') ||
        (ch == 'ý') || (ch == 'þ'))*/
    if ( (ch == '\u03b1') || (ch == '\u03b5') || (ch == '\u03b7') || (ch == '\u03b9') || (ch == '\u03bf') ||
            (ch == '\u03c5') || (ch == '\u03c9') ||
            (ch == '\u03ac') || (ch == '\u03ad') || (ch == '\u03ae') || (ch == '\u03af') || (ch == '\u03cc') ||
            (ch == '\u03cd') || (ch == '\u03ce')){
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // endsToVowel

  /**
   * Returns 1 if the token at position <code>pos</code> ends to <b>í</b>,
   * otherwise returns -1.
   *
   * @param pos int
   * @return String
   */
  private String endsToGreekN(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= tokens.size()) {
      return "1";
    } // if

    char ch = tokens.get(pos).getToken().charAt(tokens.get(pos).getToken().length() - 1) ;
    if (ch == '\u03bd') {
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // endsToGreekN

  /**
   * Returns 1 if the token at position <code>pos</code> ends to <b>ò</b>,
   * otherwise returns -1.
   *
   * @param pos int
   * @return String
   */
  private String endsToGreekFinalS(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= tokens.size()) {
      return "1";
    } // if

    char ch = tokens.get(pos).getToken().charAt(tokens.get(pos).getToken().length() - 1) ;
    if (ch == '\u03c2') {
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // endsToGreekFinalS

  /**
   * Computes the distance <code>dist</code> of current dot from previous dot.
   * Returns:
   * <br><br>
   * <table width="250">
   * <tr>
   *   <td width="60%"><code>dist</code> in [-1, 1)</td>
   *   <td width="40%">if <code>dist</code> < 15</td>
   * </tr>
   * <tr>
   *   <td width="60%">1</td>
   *   <td width="40%">if <code>dist</code> >= 15</td>
   * </tr>
   * </table>
   *
   * @return String
   */
  private String distanceFromPreviousDot() {
    double dist = offset - findPrevious(offset - 1);

    if (dist >= 15) {
      return "1";
    } // if
    else {
      return Double.toString(((dist / 15) * 2) - 1);
    } // else
  } // distanceFromPreviousDot

  /**
   * Computes the distance <code>dist</code> of current dot from next dot.
   * Returns:
   * <br><br>
   * <table width="250">
   * <tr>
   *   <td width="60%"><code>dist</code> in [-1, 1)</td>
   *   <td width="40%">if <code>dist</code> < 15</td>
   * </tr>
   * <tr>
   *   <td width="60%">1</td>
   *   <td width="40%">if <code>dist</code> >= 15</td>
   * </tr>
   * </table>
   *
   * @return String
   */
  private String distanceFromNextDot() {
    double dist = findNext(offset + 1) - offset;

    if (dist >= 15) {
      return "1";
    } // if
    else {
      return Double.toString(((dist / 15) * 2) - 1);
    } // else
  } // distanceFromNextDot

  /**
   * Returns 1 if token at position <code>pos</code> is greek word, otherwise
   * returns -1.
   *
   * @param pos int
   * @return String
   */
  private String isGreekWord(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= this.tokens.size()) {
      return "1";
    } // if

    String grWord = "\\p{InGreek}*";
    String tok = tokens.get(pos).getToken();

    if (Pattern.matches(grWord, tok)) {
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // isGreekWord

  /**
   * Returns 1 if token at position <code>pos</code> is english word, otherwise
   * returns -1.
   *
   * @param pos int
   * @return String
   */
  private String isEnglishWord(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= this.tokens.size()) {
      return "1";
    } // if

    String enWord = "[a-zA-Z]*";
    String tok = tokens.get(pos).getToken();

    if (Pattern.matches(enWord, tok)) {
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // isEnglishWord

  /**
   * Returns 1 if token at position <code>pos</code> is a number, otherwise
   * returns -1.
   *
   * @param pos int
   * @return String
   */
  private String isNumber(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= this.tokens.size()) {
      return "1";
    } // if

    String number = "[0-9]*";
    String tok = tokens.get(pos).getToken();

    if (Pattern.matches(number, tok)) {
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // isNumber

  /**
   * Returns the length of token at position <code>pos</code>. Returns:
   * <br><br>
   * <table width="250">
   * <tr>
   *   <td width="60%"><code>dist</code> in [-1, 1)</td>
   *   <td width="40%">if <code>dist</code> < 10</td>
   * </tr>
   * <tr>
   *   <td width="60%">1</td>
   *   <td width="40%">if <code>dist</code> >= 10</td>
   * </tr>
   * </table>
   *
   * @param pos int
   * @return String
   */
  private String length(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= this.tokens.size()) {
      return "1";
    } // if

    String tok = tokens.get(pos).getToken();
    double l = tok.length();

    if (l >= 10) {
      return "1";
    } // if
    else {
      return Double.toString(((l / 10) * 2) - 1);
    } // else
  } // length

  /**
   * Returns 1 if the first character of token at position <code>pos</code> is
   * capitalized, otherwise returns -1.
   *
   * @param pos int
   * @return String
   */
  private String isFirstCapital(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= this.tokens.size()) {
      return "1";
    } // if

    String tok = tokens.get(pos).getToken();

    if (tok.substring(0, 1).compareTo(tok.substring(0, 1).toUpperCase()) == 0) {
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // isFirstCapital

  /**
   * Returns 1 if token at position <code>pos</code> is capitalized, otherwise
   * returns -1.
   *
   * @param pos int
   * @return String
   */
  private String isCapitalized(int pos) {
    if (pos < 0) {
      return "-1";
    } // if
    if (pos >= this.tokens.size()) {
      return "1";
    } // if

    String tok = tokens.get(pos).getToken();

    if (tok.compareTo(tok.toUpperCase()) == 0) {
      return "1";
    } // if
    else {
      return "-1";
    } // else
  } // isCapitalized

  /**
   * Returns 1 if token at position <code>offset</code> is end of period,
   * otherwise returns -1.
   *
   * @return String
   */
  private String getCategory() {
    if (!tokens.get(offset).isEndOfPeriod()) {
      return "-1";
    } // if
    else {
      return "1";
    } // else
  } // getCategory

  //////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Returns the features vector of token at position <code>offset</code>.
   * The first position of features vector shows if the token is end of period.
   *
   * @return String[]
   */
  public String[] getNextFeaturesVector() {
    if ((offset = findNext(offset + 1)) >= tokens.size()) {
      return null;
    } // if

    String gw, ew;

    String featuresVector[] = new String[18];
    int featuresNum = 0;
    featuresVector[featuresNum++] = getCategory();

    featuresVector[featuresNum++] = endsToVowel(offset - 1);
    featuresVector[featuresNum++] = endsToGreekN(offset - 1);
    featuresVector[featuresNum++] = endsToGreekFinalS(offset - 1);
    featuresVector[featuresNum++] = distanceFromPreviousDot();
    featuresVector[featuresNum++] = distanceFromNextDot();

    // -1
    gw = isGreekWord(offset - 1);
    featuresVector[featuresNum++] = gw;
    ew = isEnglishWord(offset - 1);
    featuresVector[featuresNum++] = ew;
    featuresVector[featuresNum++] = isNumber(offset - 1);
    featuresVector[featuresNum++] = length(offset - 1);
    if ((gw.compareTo("1") == 0) || (ew.compareTo("1") == 0)) {
      featuresVector[featuresNum++] = isFirstCapital(offset - 1);
      featuresVector[featuresNum++] = isCapitalized(offset - 1);
    } // if
    else {
      featuresVector[featuresNum++] = "-1";
      featuresVector[featuresNum++] = "-1";
    } // else

    // +1
    gw = isGreekWord(offset + 1);
    featuresVector[featuresNum++] = gw;
    ew = isEnglishWord(offset + 1);
    featuresVector[featuresNum++] = ew;
    featuresVector[featuresNum++] = isNumber(offset + 1);
    featuresVector[featuresNum++] = length(offset + 1);
    if ((gw.compareTo("1") == 0) || (ew.compareTo("1") == 0)) {
      featuresVector[featuresNum++] = isFirstCapital(offset + 1);
      featuresVector[featuresNum++] = isCapitalized(offset + 1);
    } // if
    else {
      featuresVector[featuresNum++] = "-1";
      featuresVector[featuresNum++] = "-1";
    } // else

    return featuresVector;
  } // getNextFeaturesVector

} // end of class FeaturesManager
