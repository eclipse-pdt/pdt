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

/** This class contains version and authorship information. 
 *  It contains only static data elements and basically just a central 
 *  place to put this kind of information so it can be updated easily
 *  for each release.  
 *
 *  Version numbers used here are broken into 3 parts: major, minor, and 
 *  update, and are written as v<major>.<minor>.<update> (e.g. v0.10a).  
 *  Major numbers will change at the time of major reworking of some 
 *  part of the system.  Minor numbers for each public release or 
 *  change big enough to cause incompatibilities.  Finally update
 *  letter will be incremented for small bug fixes and changes that
 *  probably wouldn't be noticed by a user.  
 *
 * @version last updated: 12/22/97 [CSA]
 * @author  Frank Flannery
 */

public class version {
  /** The major version number. */
  public static final int major = 0;

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** The minor version number. */
  public static final int minor = 10;

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** The update letter. */
  public static final char update = 'j';

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** String for the current version. */
  public static final String version_str = "v" + major + "." + minor + update;

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Full title of the system */
  public static final String title_str = "CUP " + version_str;

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Name of the author */
  public static final String author_str = "Scott E. Hudson and Frank Flannery";

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** The command name normally used to invoke this program */ 
  public static final String program_name = "java_cup";
}
