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
package org.eclipse.php.internal.core.documentModel.provisional.contenttype;

import org.eclipse.php.internal.core.PHPCorePlugin;

public class ContentTypeIdForPHP {
	/**
	 * The value of the contenttype id field must match what is specified in
	 * plugin.xml file. Note: this value is intentially set with default
	 * protected method so it will not be inlined.
	 */
	public final static String ContentTypeID_PHP = getConstantString();

	/**
	 * Don't allow instantiation.
	 */
	private ContentTypeIdForPHP() {
		super();
	}

	static String getConstantString() {
		return PHPCorePlugin.ID + ".phpsource"; //$NON-NLS-1$
	}
}