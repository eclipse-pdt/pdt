/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences;

/**
 * PHP exe item listener.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IPHPexeItemListener {

	/**
	 * Notifies that PHP exe item has been changed.
	 * 
	 * @param event
	 */
	public void phpExeChanged(PHPexeItemEvent event);

}
