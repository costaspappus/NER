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

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.*;

import ipl.sentence_splitter.util.TextPreprocessor;

/**
 * <p>Description: The tokenizer.<br><br>
 * Every token consists of:
 *    <ol>
 *       <li>Greek characters (e.g. "◊·Ò·ÍÙﬁÒ·Ú")</li>
 *       <li>English characters (e.g. "Character")</li>
 *       <li>A number (e.g. "12345")</li>
 *       <li>A punctuation (e.g. ".", ",", "/", "}")</li>
 *    </ol>
 * We ignore whitespaces.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 */
public class Tokenizer {

  /**
   * The list of tokens.
   */
  private Vector<TokenNode> tokens;

  /**
   * Initializes the list of tokens.
   *
   * @param text String
   */
  public Tokenizer(String text) {
    this.tokens = new Vector<TokenNode>();
    text = TextPreprocessor.removeNoisySpaces(text);
    createTokens(text);
  } // constructor: Tokenizer

  /**
   * Returns the token at position <code>offset</code>.
   *
   * @param offset int
   * @return TokenNode
   */
  public TokenNode get(int offset) {
    return tokens.get(offset);
  } // get

  /**
   * Returns the size of the list of tokens.
   *
   * @return int
   */
  public int size() {
    return tokens.size();
  } // size

  /**
   * Converts <code>text</code> to a list of tokens.
   *
   * @param text String
   */
  private void createTokens(String text) {
    int i;
    StringTokenizer sTokenizer = new StringTokenizer(text, " \n", true);
    String tok;
    TokenNode node;
    String grWord = "\\p{InGreek}*";
    String enWord = "[a-zA-Z]*";
    String number = "[0-9]*";
    String prevChar = "";
    // The values of prevCharsType and currCharType are:
    //    0. nothing
    //    1. greek character
    //    2. english character
    //    3. digit
    //    4. dot
    //    5. other
    int prevCharsType, currCharType;
    String prevChars;
    char currChar;

    while (sTokenizer.hasMoreTokens()) {
      tok = sTokenizer.nextToken();
      if ((tok.compareTo(" ") == 0) || (tok.compareTo("\n") == 0)) {
        prevChar = tok;
      } // if
      else {
        // If tok contains only one token, that is if tok is greek word or
        // english word or a number.
        if (Pattern.matches(grWord, tok) || Pattern.matches(enWord, tok) ||
            Pattern.matches(number, tok)) {
          node = new TokenNode(tok, false, prevChar);
          tokens.add(node);
        } // if
        else {
          prevCharsType = 0;
          prevChars = "";
          // For each characer of tok.
          for (i = 0; i < tok.length(); i++) {
            currChar = tok.charAt(i);
            // The type of currChar is...
            if (isGrLetter(currChar)) {
              currCharType = 1;
            } // if
            else if (isEnLetter(currChar)) {
              currCharType = 2;
            } // else if
            else if (isDigit(currChar)) {
              currCharType = 3;
            } // else if
            else if (currChar == '.') {
              currCharType = 4;
            } // else
            else {
              currCharType = 5;
            } // else

            // If currCharType is same as previousCharsType, add currChar to
            // prevChars. This is the case where prevCharsType is:
            //   1. Greek character
            //   2. English character
            //   3. Digit
            if (currCharType == prevCharsType) {
              prevChars = prevChars.concat(String.valueOf(currChar));
            } // if
            else {
              if (prevCharsType != 0) {
                node = new TokenNode(prevChars, false, prevChar);
                prevChar = "";
                tokens.add(node);
              } // if
              if (currCharType == 5) {
                node = new TokenNode(String.valueOf(currChar), false, prevChar);
                prevChar = "";
                tokens.add(node);
                prevChars = "";
                prevCharsType = 0;
              } // if
              else if (currCharType == 4) {
                if ( (i == (tok.length() - 1)) || (i == (tok.length() - 2))) {
                  node = new TokenNode(String.valueOf(currChar), false, prevChar);
                } // if
                else {
                  char ch1 = tok.charAt(i + 1);
                  char ch2 = tok.charAt(i + 2);
                  if ( (ch1 == '\\') && (ch2 == 'y')) {
                    node = new TokenNode(String.valueOf(currChar), true, prevChar);
                    i += 2;
                  } // if
                  else {
                    if ( (ch1 == '\\') && (ch2 == 'n')) {
                      i += 2;
                    } // if
                    node = new TokenNode(String.valueOf(currChar), false, prevChar);
                  } // else
                } // else if

                prevChar = "";
                tokens.add(node);
                prevChars = "";
                prevCharsType = 0;
              } // else if
              // If currCharType is:
              //   1. Greek character
              //   2. English character
              //   3. Digit
              else {
                prevChars = String.valueOf(currChar);
                prevCharsType = currCharType;
              } // else
            } // else
          } // for
          if (prevCharsType != 0) {
            node = new TokenNode(prevChars, false, "");
            tokens.add(node);
          } // if
        } // else
      } // else
    } // while
  } // createTokens

  /**
   * Returns <code>true</code> if <code>ch</code> is greek character, otherwise
   * returns <code>false</code>.
   *
   * @param ch char
   * @return boolean
   */
  private boolean isGrLetter(char ch) {
    /*if ( ( (ch >= '·') && (ch <= '˘')) || ( (ch >= '¡') && (ch <= 'Ÿ')) ||
        (ch == '‹') || (ch == '›') || (ch == 'ﬁ') || (ch == 'ﬂ') ||
        (ch == '¸') || (ch == '˝') || (ch == '˛') ||
        (ch == '¢') || (ch == '∏') || (ch == 'π') || (ch == '∫') ||
        (ch == 'º') || (ch == 'æ') || (ch == 'ø') ||
        (ch == '˙') || (ch == '˚') || (ch == '¿') || (ch == '‡')) {*/
    if ( ( (ch >= '\u03b1') && (ch <= '\u03c9')) || ( (ch >= '\u0391') && (ch <= '\u03a9')) ||
    	(ch == '\u03ac') || (ch == '\u03ad') || (ch == '\u03ae') || (ch == '\u03af') ||
    	(ch == '\u03cc') || (ch == '\u03cd') || (ch == '\u03ce') ||
    	(ch == '\u0386') || (ch == '\u0388') || (ch == '\u0389') || (ch == '\u038a') ||
    	(ch == '\u038c') || (ch == '\u038e') || (ch == '\u038f') ||
    	(ch == '\u03ca') || (ch == '\u03cb') || (ch == '\u0390') || (ch == '\u03b0')) {
     return true;
    } // if
    else {
      return false;
    } // else
  } // isGrLetter

  /**
   * Returns <code>true</code> if <code>ch</code> is english character,
   * otherwise returns <code>false</code>.
   *
   * @param ch char
   * @return boolean
   */
  private boolean isEnLetter(char ch) {
    if ( ( (ch >= 'a') && (ch <= 'z')) || ( (ch >= 'A') && (ch <= 'Z'))) {
      return true;
    } // if
    else {
      return false;
    } // else
  } // isEnLetter

  /**
   * Returns <code>true</code> if <code>ch</code> is digit, otherwise
   * returns <code>false</code>.
   *
   * @param ch char
   * @return boolean
   */
  private boolean isDigit(char ch) {
    if ( (ch >= '0') && (ch <= '9')) {
      return true;
    } // if
    else {
      return false;
    } // else
  } // isDigit

} // end of class Tokenizer
