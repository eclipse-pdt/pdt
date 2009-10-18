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
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * This class represents an event notifier for PropertyChangeEvents
 */
public abstract class AbstractEventManager extends EventManager {

	public void addPropertyChangeListener(final IPropertyChangeListener listener) {
		addListenerObject(listener);
	}

	/**
	 * Notifies any property change listeners that a property has changed. Only
	 * listeners registered at the time this method is called are notified.
	 * 
	 * @param event
	 *            the property change event
	 * 
	 * @see IPropertyChangeListener#propertyChange
	 */
	protected final void firePropertyChange(final PropertyChangeEvent event) {
		final Object[] list = getListeners();
		for (int i = 0; i < list.length; ++i) {
			((IPropertyChangeListener) list[i]).propertyChange(event);
		}
	}

	/**
	 * Notifies any property change listeners that a property has changed. Only
	 * listeners registered at the time this method is called are notified. This
	 * method avoids creating an event object if there are no listeners
	 * registered, but calls
	 * <code>firePropertyChange(PropertyChangeEvent)</code> if there are.
	 * 
	 * @param propertyName
	 *            the name of the property that has changed
	 * @param oldValue
	 *            the old value of the property, or <code>null</code> if none
	 * @param newValue
	 *            the new value of the property, or <code>null</code> if none
	 * 
	 * @see IPropertyChangeListener#propertyChange
	 */
	protected final void firePropertyChange(final String propertyName,
			final Object oldValue, final Object newValue) {
		if (isListenerAttached()) {
			firePropertyChange(new PropertyChangeEvent(this, propertyName,
					oldValue, newValue));
		}
	}

	public void removePropertyChangeListener(
			final IPropertyChangeListener listener) {
		removeListenerObject(listener);
	}
}