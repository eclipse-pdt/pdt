/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

public class UnixChmodUtil {

	public static final int S_IRWXU = 00700; /* Read, write and execute by user */
	public static final int S_IRUSR = 00400; /* Read by user */
	public static final int S_IWUSR = 00200; /* Write by user */
	public static final int S_IXUSR = 00100; /* Execute by user */
	public static final int S_IRWXG = 00070; /* Read, write and execute by group */
	public static final int S_IRGRP = 00040; /* Read by group */
	public static final int S_IWGRP = 00020; /* Write by group */
	public static final int S_IXGRP = 00010; /* Execute by group */
	public static final int S_IRWXO = 00007; /* Read, write and execute by others */
	public static final int S_IROTH = 00004; /* Read by others */
	public static final int S_IWOTH = 00002; /* Write by others */
	public static final int S_IXOTH = 00001; /* Execute by others */

	/**
	 * Changes permissions to the specified file.
	 * 
	 * @param String
	 *            file path
	 * @param int permissions mode (use bitwise <code>|</code> to designate
	 *        different modes).
	 * @return boolean <code>true</code> if succeeded changing permissions,
	 *         <code>false</code> otherwise.
	 */
	public static boolean chmod(String file, int mode) {
		if (System.getProperty("os.name").startsWith("Windows")) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new IllegalAccessError(Messages.UnixChmodUtil_0); 
		}

		boolean status = true;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process p = runtime.exec(new String[] {
					"chmod", Integer.toString(mode, 8), file }); //$NON-NLS-1$
			status = (p.waitFor() == 0);
		} catch (Exception e) {
			status = false;
		}

		return status;
	}
}
