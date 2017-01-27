/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.actions;

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.php.composer.ui.editor.composer.DependencyGraphPage;

public class ToggleDevAction extends Action {

	public static final String ID = "composer.dpg.toggle"; //$NON-NLS-1$

	private boolean showDev;
	private DependencyGraphPage graphPage;

	public ToggleDevAction(DependencyGraphPage graphPage) {
		super(Messages.ToggleDevAction_Text);
		this.graphPage = graphPage;

		setDescription(Messages.ToggleDevAction_Description);
		setToolTipText(Messages.ToggleDevAction_ToolTipText);
		setId(ID);
		DLTKPluginImages.setLocalImageDescriptors(this, "th_showqualified.png"); //$NON-NLS-1$
	}

	public void run() {
		showDev = !showDev;
		graphPage.applyFilter(showDev);
	}
}
