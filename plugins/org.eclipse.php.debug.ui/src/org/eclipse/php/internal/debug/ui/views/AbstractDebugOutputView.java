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
package org.eclipse.php.internal.debug.ui.views;

import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.ui.part.ViewPart;

/**
 * Abstract class for views that are intended to present debug output content.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public abstract class AbstractDebugOutputView extends ViewPart {

	protected interface IUpdater {

		/**
		 * Updates view with the use of PHP debug target output.
		 * 
		 * @param target
		 */
		void update(IPHPDebugTarget target);

	}

	private final Map<IPHPDebugTarget, IUpdater> fUpdaters = new WeakHashMap<IPHPDebugTarget, IUpdater>();
	protected final DebugViewHelper fDebugViewHelper;

	/**
	 * Creates new view for presenting debug output content.
	 */
	public AbstractDebugOutputView() {
		fDebugViewHelper = new DebugViewHelper();
	}

	/**
	 * Updates view content with the use of incoming PHP debug target.
	 * 
	 * @param target
	 */
	public synchronized void update(IPHPDebugTarget target) {
		getUpdater(target).update(target);
	}

	/**
	 * Implementors of this method should create and return a new instance of
	 * updater that handles the debug output for corresponding PHP debug target.
	 * 
	 * @return updater
	 */
	protected abstract IUpdater createUpdater();

	private IUpdater getUpdater(IPHPDebugTarget target) {
		IUpdater updater = fUpdaters.get(target);
		if (updater == null) {
			updater = createUpdater();
			fUpdaters.put(target, updater);
		}
		return updater;
	}

}
