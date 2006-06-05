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

/* Defines integers that represent the associativity of terminals
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */

public class assoc {

  /* various associativities, no_prec being the default value */
  public final static int left = 0;
  public final static int right = 1;
  public final static int nonassoc = 2;
  public final static int no_prec = -1;

}