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
package org.eclipse.php.internal.ui.preferences.util;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.internal.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.internal.core.util.WeakObject;

/**
 * WeakPreferencesPropagatorListener
 * 
 * @deprecated (Not effective - candidate for removal)
 */
public class WeakPreferencesPropagatorListener extends WeakObject implements
		IPreferencesPropagatorListener {

	private Object target;
	private String key;
	private static ReferenceQueue q = new ReferenceQueue();
	private static Class parameterTypes[] = new Class[] {
			IPreferencesPropagatorListener.class, String.class };
	private static Object parameterValues[] = new Object[] { null, null };
	private static String removeListenerMethodName = "removePropagatorListener"; //$NON-NLS-1$

	public static WeakPreferencesPropagatorListener create(
			IPreferencesPropagatorListener l, String key, Object target) {
		removeRedundantReferences();
		return new WeakPreferencesPropagatorListener(l, key, target);
	}

	public static void removeRedundantReferences() {
		WeakPreferencesPropagatorListener r = (WeakPreferencesPropagatorListener) q
				.poll();
		while (r != null) {
			removeRedundantReference(r);
			r = (WeakPreferencesPropagatorListener) q.poll();
		}
	}

	private static void removeRedundantReference(
			WeakPreferencesPropagatorListener listener) {
		try {
			Method setMethod = listener.target.getClass().getMethod(
					removeListenerMethodName, parameterTypes);
			parameterValues[0] = listener;
			parameterValues[1] = listener.key;
			setMethod.invoke(listener.target, parameterValues);
		} catch (Exception exc) {
			PHPCorePlugin.log(exc);
		}
	}

	/** Creates new WeakPropertyChangeListener */
	protected WeakPreferencesPropagatorListener(
			IPreferencesPropagatorListener l, String key, Object target) {
		super(l, q);
		this.key = key;
		this.target = target;
	}

	public void preferencesEventOccured(PreferencesPropagatorEvent event) {
		IPreferencesPropagatorListener l = (IPreferencesPropagatorListener) this
				.get();
		if (l != null) {
			l.preferencesEventOccured(event);
		} else {
			removeRedundantReference(this);
		}
	}

	public IProject getProject() {
		IPreferencesPropagatorListener l = (IPreferencesPropagatorListener) this
				.get();
		if (l != null) {
			return l.getProject();
		} else {
			removeRedundantReference(this);
		}
		return null;
	}

}