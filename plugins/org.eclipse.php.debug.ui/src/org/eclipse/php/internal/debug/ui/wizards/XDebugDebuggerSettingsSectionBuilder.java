/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.swt.widgets.Composite;

/**
 * XDebug debugger settings section builder.
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugDebuggerSettingsSectionBuilder implements IDebuggerSettingsSectionBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSectionBuilder
	 * #build(org.eclipse.php.internal.ui.wizards.CompositeFragment, org.eclipse.
	 * php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy)
	 */
	@Override
	public IDebuggerSettingsSection build(CompositeFragment compositeFragment, Composite debuggerSettingsComposite,
			IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		switch (settingsWorkingCopy.getKind()) {
		case PHP_SERVER:
			return new XDebugDebuggerServerSettingsSection(compositeFragment, debuggerSettingsComposite,
					settingsWorkingCopy);
		case PHP_EXE:
			return new XDebugDebuggerExeSettingsSection(compositeFragment, debuggerSettingsComposite,
					settingsWorkingCopy);
		default:
			break;
		}
		return null;
	}
}
