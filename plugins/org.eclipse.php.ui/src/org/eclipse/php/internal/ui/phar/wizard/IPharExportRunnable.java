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
package org.eclipse.php.internal.ui.phar.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.operation.IRunnableWithProgress;

public interface IPharExportRunnable extends IRunnableWithProgress {

	/**
	 * Returns the current status of this operation. The result is a status
	 * object which may contain individual nested status objects.
	 * <p>
	 * Clients may call this method during the operation and add additional
	 * status information.
	 * </p>
	 * 
	 * @return the status of this operation
	 */
	public IStatus getStatus();
}
