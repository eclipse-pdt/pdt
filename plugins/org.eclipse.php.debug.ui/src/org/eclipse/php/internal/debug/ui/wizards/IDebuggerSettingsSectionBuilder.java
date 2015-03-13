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
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;

/**
 * Common interface for implementors of debugger settings section builder.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public interface IDebuggerSettingsSectionBuilder {

	/**
	 * Builds and returns debugger settings section composite.
	 * 
	 * @param compositeFragment
	 * @param settingsWorkingCopy
	 * @return debugger settings section composite
	 */
	public IDebuggerSettingsSection build(CompositeFragment compositeFragment,
			IDebuggerSettingsWorkingCopy settingsWorkingCopy);

}
