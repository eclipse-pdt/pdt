/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.phpModel.javacup;

/** This class represents one part (either a symbol or an action) of a 
 *  production.  In this base class it contains only an optional label 
 *  string that the user can use to refer to the part within actions.<p>
 *
 *  This is an abstract class.
 *
 * @see     java_cup.production
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public abstract class production_part {

  /*-----------------------------------------------------------*/
  /*--- Constructor(s) ----------------------------------------*/
  /*-----------------------------------------------------------*/
       
  /** Simple constructor. */
  public production_part(String lab)
    {
      _label = lab;
    }

  /*-----------------------------------------------------------*/
  /*--- (Access to) Instance Variables ------------------------*/
  /*-----------------------------------------------------------*/
       
  /** Optional label for referring to the part within an action (null for 
   *  no label). 
   */
  protected String _label;

  /** Optional label for referring to the part within an action (null for 
   *  no label). 
   */
  public String label() {return _label;}

  /*-----------------------------------------------------------*/
  /*--- General Methods ---------------------------------------*/
  /*-----------------------------------------------------------*/
       
  /** Indicate if this is an action (rather than a symbol).  Here in the 
   * base class, we don't this know yet, so its an abstract method.
   */
  public abstract boolean is_action();

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Equality comparison. */
  public boolean equals(production_part other)
    {
      if (other == null) return false;

      /* compare the labels */
      if (label() != null)
	return label().equals(other.label());
      else
	return other.label() == null;
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Generic equality comparison. */
  public boolean equals(Object other)
    {
      if (!(other instanceof production_part))
        return false;
      else
	return equals((production_part)other);
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Produce a hash code. */
  public int hashCode()
    {
      return label()==null ? 0 : label().hashCode();
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Convert to a string. */
  public String toString()
    {
      if (label() != null)
	return label() + ":";
      else
	return " ";
    }

  /*-----------------------------------------------------------*/

}
