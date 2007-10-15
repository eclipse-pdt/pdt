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
package org.eclipse.php.internal.core.phpModel.phpElementData;

public interface IPHPMarker {

	public static String ERROR = "ERROR"; //$NON-NLS-1$

	public static String WARNING = "WARNING"; //$NON-NLS-1$

	public static String INFO = "INFO"; //$NON-NLS-1$

	public static String TASK = "TASK"; //$NON-NLS-1$

	public String getType();
	
	public String getDescription();
	
	public UserData getUserData();
}
