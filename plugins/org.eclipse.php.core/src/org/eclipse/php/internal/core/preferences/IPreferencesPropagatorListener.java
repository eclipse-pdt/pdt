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
package org.eclipse.php.internal.core.preferences;

import java.util.EventListener;

import org.eclipse.core.resources.IProject;

/**
 * A listener for preferences events that are propagated by the
 * PreferencesEventsPropagator. When needed, the listener should also supply an
 * IProject that is related to it.
 * 
 * @author shalom
 */
public interface IPreferencesPropagatorListener extends EventListener {

	/**
	 * Fired when a PreferencesPropagatorEvent occures.
	 * 
	 * @param event
	 *            The PreferencesPropagatorEvent
	 */
	public void preferencesEventOccured(PreferencesPropagatorEvent event);

	/**
	 * Returns the IProject that is related to this listener.
	 * 
	 * @return An IProject
	 */
	public IProject getProject();
}
