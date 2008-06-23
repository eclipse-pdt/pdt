/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project.options;


/**
 * This listener listens to changes for a specific option in a set project
 * 
 * It is invoked when the option is changed and it gets the old and new options - they can be object, but most likely strings. 
 *
 */
public interface IPhpProjectOptionChangeListener {
	
	public void notifyOptionChanged(Object oldOption, Object newOption);
}
