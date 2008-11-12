/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.dltk.ui.preferences.BuildPathsPropertyPage;
import org.eclipse.dltk.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class IncludePathProperties extends BuildPathsPropertyPage implements IWorkbenchPropertyPage {

	public IncludePathProperties() {
	}

	protected AbstractBuildpathsBlock createBuildPathBlock(IWorkbenchPreferenceContainer pageContainer) {
		return new PHPIncludePathsBlock(new BusyIndicatorRunnableContext(), this, getSettings().getInt(INDEX), false, pageContainer);
	}
}
