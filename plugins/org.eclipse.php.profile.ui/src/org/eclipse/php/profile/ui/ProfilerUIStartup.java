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
package org.eclipse.php.profile.ui;

import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.ui.IStartup;

/**
 * This class is intended to perform early startup of a profiler UI plug-in. It
 * adds profile perspective switcher which is responsible for opening associated
 * perspective in case of receiving profiling session result data.
 * 
 * @author Bartlomiej Laczkowski
 *
 */
public class ProfilerUIStartup implements IStartup {

	@Override
	public void earlyStartup() {
		ProfileSessionsManager.addProfileSessionListener(new PHPProfilePerspectiveSwitcher());
	}

}
