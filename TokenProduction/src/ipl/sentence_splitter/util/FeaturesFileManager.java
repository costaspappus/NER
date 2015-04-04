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

/**
 * <p>Description: Manages the file containing the feature vectors.
 * The file format corresponds to libSVM file format.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 */
public class FeaturesFileManager {

  /**
   * The file output stream.
   */
  private FileOutputStream fos;

  /**
   * @param outputFilename String
   */
  public FeaturesFileManager(String outputFilename) {
    try {
      File outf = new File(outputFilename);
      fos = new FileOutputStream(outf);
    } // try
    catch (IOException e) {
      e.printStackTrace();
    } // catch
  } // constructor: FeaturesFileManager

  /**
   * Writes a feature vector to the file stream.
   *
   * @param featVector String[]
   * @throws Exception
   */
  public void setData(String[] featVector) {
    String features = featVector[0];
    int l = featVector.length;
    for (int i = 1; i < l; i++) {
      features += " " + i + ":" + featVector[i];
    } // for
    features += "\n";

    try {
      fos.write(features.getBytes());
    } // try
    catch (IOException e) {
      e.printStackTrace();
    } // catch
  } // setData

  /**
   * Closes the output file stream.
   */
  public void close() {
    try {
      fos.close();
    } // try
    catch (IOException e) {
      e.printStackTrace();
    } // catch
  } // close

} // end of class FeaturesFileManager
