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

import java.util.EventObject;

/**
 * PHP exe item event.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPexeItemEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final String property;
	private final Object oldValue;
	private final Object newValue;

	/**
	 * Creates new PHP exe item event.
	 * 
	 * @param source
	 * @param property
	 * @param oldValue
	 * @param newValue
	 */
	public PHPexeItemEvent(Object source, String property, Object oldValue, Object newValue) {
		super(source);
		this.property = property;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Returns changed property key.
	 * 
	 * @return changed property key
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Returns old value of a changed property.
	 * 
	 * @return old value of a changed property
	 */
	public Object getOldValue() {
		return oldValue;
	}

	/**
	 * Returns new value of a changed property.
	 * 
	 * @return new value of a changed property
	 */
	public Object getNewValue() {
		return newValue;
	}

}
