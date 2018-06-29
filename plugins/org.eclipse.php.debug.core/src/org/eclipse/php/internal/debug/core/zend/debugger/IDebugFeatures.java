/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

public interface IDebugFeatures {

	public static final int START_PROCESS_FILE_NOTIFICATION = 1;
	public static final int GET_CWD = 2;
	public static final int GET_CALL_STACK_LITE = 3;

	/**
	 * Checks whether current debug session is capable of the given feature
	 * 
	 * @param feature
	 * @return
	 */
	public boolean canDo(int feature);
}
