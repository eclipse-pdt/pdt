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
 * Zend debugger settings section builder.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerSettingsSectionBuilder implements IDebuggerSettingsSectionBuilder {

	@Override
	public IDebuggerSettingsSection build(CompositeFragment compositeFragment, Composite debuggerSettingsComposite,
			IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		switch (settingsWorkingCopy.getKind()) {
		case PHP_SERVER:
			return new ZendDebuggerServerSettingsSection(compositeFragment, debuggerSettingsComposite,
					settingsWorkingCopy);
		case PHP_EXE:
			return new ZendDebuggerExeSettingsSection(compositeFragment, debuggerSettingsComposite,
					settingsWorkingCopy);
		default:
			break;
		}
		return null;
	}

}
