/*******************************************************************************
 * Copyright 1996-1999 by Scott Hudson, Frank Flannery, C. Scott Ananian
 *******************************************************************************/
package org.eclipse.php.core.phpModel.javacup.runtime;

/**
 * Defines the Scanner interface, which CUP uses in the default
 * implementation of <code>lr_parser.scan()</code>.  Integration
 * of scanners implementing <code>Scanner</code> is facilitated.
 *
 * @version last updated 23-Jul-1999
 * @author David MacMahon <davidm@smartsc.com>
 */

/* *************************************************
  Interface Scanner
  
  Declares the next_token() method that should be
  implemented by scanners.  This method is typically
  called by lr_parser.scan().
 ***************************************************/
public interface Scanner {
    public Symbol next_token() throws java.lang.Exception;
}
