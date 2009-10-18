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
package org.eclipse.php.internal.debug.core.preferences;

/**
 * A listener to events when adding/removing PHP Executables
 * 
 * @author yaronm
 */
public interface IPHPExesListener {
	public void phpExeAdded(PHPExesEvent event);

	public void phpExeRemoved(PHPExesEvent event);
}
