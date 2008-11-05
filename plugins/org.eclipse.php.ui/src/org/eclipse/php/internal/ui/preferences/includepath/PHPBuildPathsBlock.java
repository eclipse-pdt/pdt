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

import org.eclipse.dltk.internal.ui.wizards.buildpath.SourceContainerWorkbookPage;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPBuildPathsBlock extends BuildpathsBlock {

	public PHPBuildPathsBlock(IRunnableContext runnableContext, IStatusChangeListener context, int pageToShow, boolean useNewPage, IWorkbenchPreferenceContainer pageContainer) {
		super(runnableContext, context, pageToShow, useNewPage, pageContainer);
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected boolean supportZips() {
		return false;
	}

	public Control createControl(Composite parent) {
		fSWTWidget = parent;
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;			
		composite.setLayout(layout);

		fSourceContainerPage = new SourceContainerWorkbookPage(fBuildPathList);
		
		Control control = fSourceContainerPage.getControl(parent);

		if (fCurrScriptProject != null) {
			fSourceContainerPage.init(fCurrScriptProject);
		}
		Dialog.applyDialogFont(composite);
		return composite;
	}

}
