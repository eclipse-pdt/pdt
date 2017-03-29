/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import org.eclipse.php.internal.debug.ui.launching.ZendDebuggerExeLaunchSettingsSection;
import org.eclipse.swt.widgets.Composite;

public class PHPUnitZDLaunchSettingsSection extends ZendDebuggerExeLaunchSettingsSection {

	@Override
	protected void createBreakpointGroup(Composite parent) {
		// Do not create breakpoint group
	}

}
