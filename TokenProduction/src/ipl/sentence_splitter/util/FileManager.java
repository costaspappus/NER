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

import java.io.*;

import ipl.sentence_splitter.core.Tokenizer;

/**
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 */
public class FileManager {

  /**
   * Returns the contents of file <code>filename</code>.
   *
   * @param filename String
   * @return String
   */
  public static String read(String filename)
  {
    String text = "";
    try
    {
      File file = new File(filename);
      BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "UTF-8"));
      String line = reader.readLine();
      boolean first = true;
      while(line != null)
      {
    	  if(first)
    	  {
    		  text += line;
    		  first = false;
    	  }
    	  else
    	  {
    		  text += "\n" + line;
    	  }
    	  line = reader.readLine();
      }
      reader.close();
      
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    //if first character is not a text character remove it
    if((int)text.charAt(0) == 65279)
    {
    	text = text.substring(1, text.length());
    }
    return text;
  } // read

  /**
   * Returns the contents of <code>file</code>.
   *
   * @param file File
   * @return String
   */
  public static String read(File file) {
    String text = "";

    try {
      if (!file.exists() || !file.isFile()) {
        System.err.println("Error reading the file " + file.getName());
        System.exit(1);
      } // if

      byte buff[] = new byte[(int) file.length()];
      FileInputStream fis = new FileInputStream(file);
      fis.read(buff);
      fis.close();
      text = new String(buff);
    } // try
    catch (IOException e) {
      e.printStackTrace();
    } // catch

    return text;
  } // read

  /**
   * Writes the tokenizer's tokens to file <code>filename</code>.
   * Sets \y and \n tags.
   *
   * @param tokenizer Tokenizer
   * @param filename String
   */
  public static String get_tagged_text(Tokenizer tokenizer)
  {
    String text = "";
    int size = tokenizer.size();
    for (int i = 0; i < size; i++) {
      String curr;
      if (tokenizer.get(i).getToken().compareTo(".") == 0) {
        if (tokenizer.get(i).isEndOfPeriod()) {
          curr = tokenizer.get(i).getPrevChar() + tokenizer.get(i).getToken() + "\\y";
        } // if
        else {
          curr = tokenizer.get(i).getPrevChar() + tokenizer.get(i).getToken() + "\\n";
        } // else
      } // if
      else {
        curr = tokenizer.get(i).getPrevChar() + tokenizer.get(i).getToken();
      } // else
      text += curr;
    } // for

    text += "";
    return text;
  }

  /**
   * Writes the tokenizer's tokens to file <code>filename</code>.
   * Sets <sentence> and </sentence> tags.
   *
   * @param tokenizer Tokenizer
   * @param filename String
   */
  public static void writeTags(Tokenizer tokenizer, String filename) {
    String text = "<article>\n";
    int size = tokenizer.size();
    for (int i = 0; i < size; i++) {
      String curr = "";
      if (i == 0) {
        curr = "<sentence>";
      } // if
      else if (tokenizer.get(i-1).isEndOfPeriod()) {
        curr += tokenizer.get(i).getPrevChar() + "<sentence>";
      } // if
      else {
        curr += tokenizer.get(i).getPrevChar();
      } // else
      if (tokenizer.get(i).getToken().compareTo(".") == 0) {
        if (tokenizer.get(i).isEndOfPeriod()) {
          curr += tokenizer.get(i).getToken() + "</sentence>";
        } // if
        else {
          curr += tokenizer.get(i).getToken();
        } // else
      } // if
      else {
        curr += tokenizer.get(i).getToken();
      } // else
      text += curr;
    } // for

    text += "\n</article>";

    try {
      FileOutputStream fos = new FileOutputStream(filename);
      fos.write(text.getBytes());
      fos.close();
    } // try
    catch (IOException e) {
      e.printStackTrace();
    } // catch
  } // writeTags

} // end of class FileManager
