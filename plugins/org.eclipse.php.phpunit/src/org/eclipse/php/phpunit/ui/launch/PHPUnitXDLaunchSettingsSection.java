/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import org.eclipse.php.internal.debug.ui.launching.XDebugExeLaunchSettingsSection;
import org.eclipse.swt.widgets.Composite;

public class PHPUnitXDLaunchSettingsSection extends XDebugExeLaunchSettingsSection {

	@Override
	protected void createBreakpointGroup(Composite parent) {
		// Do not create breakpoint group
	}

}
