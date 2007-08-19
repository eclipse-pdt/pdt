/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;

public interface IDELayer {

	// required for Source Lookup
	public Object sourceNotFound(Object debugElement);

	public ISourceContainer getSourceContainer(IProject resource, ILaunchConfiguration launchConfig);

	// required for Launch Listener
	public String getSystemDebugProperty();

	// required for EXE launcher
	public String getEXELocationAttrName();

	public String getEXEFileAttrName();

	public String getEXEOverrideFirstLineAttrName();

	public String getEXEFirstLineAttrName();

	// required for EXE and Web launcher
	public boolean getStopAtFirstLine(IProject project);

	public Preferences getPrefs();
}
