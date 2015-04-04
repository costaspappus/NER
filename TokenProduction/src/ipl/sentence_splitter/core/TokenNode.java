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

/**
 * <p>Description: Class containing a token's features.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @version 0.1
 */
public class TokenNode {

  /**
   * The token string.
   */
  private String token;

  /**
   * True if token is end of period.
   */
  private boolean endOfPeriod;

  /**
   * The whitespace character before the token.
   */
  private String prevChar;

  /**
   * @param token String
   * @param endOfPeriod boolean
   * @param prevChar String
   */
  public TokenNode(String token, boolean endOfPeriod, String prevChar) {
    this.token = token;
    this.endOfPeriod = endOfPeriod;
    this.prevChar = prevChar;
  } // constructor: TokenNode

  /**
   * Returns the token.
   *
   * @return String
   */
  public String getToken() {
    return this.token;
  } // getToken

  /**
   * Returns true if token is end of period, otherwise returns false.
   *
   * @return boolean
   */
  public boolean isEndOfPeriod() {
    return this.endOfPeriod;
  } // isEndOfPeriod

  /**
   * Returns the whitespace character before the token.
   *
   * @return String
   */
  public String getPrevChar() {
    return this.prevChar;
  } // getPrevChar

  /**
   * Sets the value of token.
   *
   * @param token String
   */
  public void setToken(String token) {
    this.token = token;
  } // setToken

  /**
   * Sets the value of endOfPeriod.
   *
   * @param endperiod boolean
   */
  public void setEndOfPeriod(boolean endperiod) {
    this.endOfPeriod = endperiod;
  } // setEndOfPeriod

  /**
   * Sets the value of prevChar.
   *
   * @param pChar String
   */
  public void setPrevChar(String pChar) {
    this.prevChar = pChar;
  } // setToken

} // end of class TokenNode
