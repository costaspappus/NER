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
 * <p>Description: Manages the files of a directory.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 */
public class DirectoryManager {

  /**
   * Contains the files of the directory.
   */
  private File fileList[];

  /**
   * Indicates the last file that has been processed.
   */
  private int offset;

  /**
   * @param strDir String
   */
  public DirectoryManager(String strDir) {
    File directory = new File(strDir);
    if (!directory.exists() || !directory.isDirectory()) {
      System.err.println("Error reading the directory " + strDir);
      System.exit(1);
    } // if
    else {
      fileList = directory.listFiles();
    } // else

    offset = 0;
  } // Files

  /**
   * Get the next file for processing.
   *
   * @return File
   */
  public File getNextFile() {
    if (offset >= fileList.length) {
      return null;
    } // if
    else {
      return fileList[offset++];
    } // else
  } // getNextFile

  /**
   * Resets the private variable <code>offset</code>.
   */
  public void reset() {
    offset = 0;
  } // reset

} // DirectoryManager
