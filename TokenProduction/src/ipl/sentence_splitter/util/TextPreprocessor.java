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

package ipl.sentence_splitter.util;

/**
 * <p>Description: Contains auxiliary functions, which preprocess a text.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 */
public class TextPreprocessor {

  /**
   * Removes whitespaces from <code>text</code>, except from " " and "\n".
   *
   * @param text String
   * @return String
   */
  public static String removeNoisySpaces(String text) {
    String tmp;
    int start, end;

    for (int i = 0; i < text.length(); i++) {
      if ((Character.isWhitespace(text.charAt(i))) &&
          (text.charAt(i) != '\u00a0') &&
          (text.charAt(i) != '\n')) {
        if (i == 0) {
          tmp = text.substring(1, text.length());
        } // if
        else {
          start = i;
          end = start + 1;
          tmp = text.substring(0, start);
          tmp = tmp.concat(text.substring(end, text.length()));
        } // else
        text = tmp;
      } // if
    } // for
    return text;
  } // removeNoisySpaces

} // end of class TextPreprocessor
