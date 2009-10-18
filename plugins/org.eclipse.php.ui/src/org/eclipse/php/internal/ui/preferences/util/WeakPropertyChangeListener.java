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

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.util.WeakObject;

/**
 * WeakPreferencesPropagatorListener
 * 
 * @deprecated (Not effective - candidate for removal)
 */
public class WeakPropertyChangeListener extends WeakObject implements
		IPropertyChangeListener {

	private Object target;
	private static ReferenceQueue q = new ReferenceQueue();
	private static Class parameterTypes[] = new Class[] { IPropertyChangeListener.class };
	private static Object parameterValues[] = new Object[] { null };
	private static String removeListenerMethodName = "removePropertyChangeListener"; //$NON-NLS-1$

	public static WeakPropertyChangeListener create(IPropertyChangeListener l,
			Object target) {
		removeRedundantReferences();
		return new WeakPropertyChangeListener(l, target);
	}

	public static void removeRedundantReferences() {
		WeakPropertyChangeListener r = (WeakPropertyChangeListener) q.poll();
		while (r != null) {
			removeRedundantReference(r);
			r = (WeakPropertyChangeListener) q.poll();
		}
	}

	private static void removeRedundantReference(
			WeakPropertyChangeListener listener) {
		try {
			Method setMethod = listener.target.getClass().getMethod(
					removeListenerMethodName, parameterTypes);
			parameterValues[0] = listener;
			setMethod.invoke(listener.target, parameterValues);
		} catch (Exception exc) {
			PHPCorePlugin.log(exc);
		}
	}

	/** Creates new WeakPropertyChangeListener */
	protected WeakPropertyChangeListener(IPropertyChangeListener l,
			Object target) {
		super(l, q);
		this.target = target;
	}

	public void propertyChange(PropertyChangeEvent event) {
		IPropertyChangeListener l = (IPropertyChangeListener) this.get();
		if (l != null) {
			l.propertyChange(event);
		} else {
			removeRedundantReference(this);
		}
	}
}