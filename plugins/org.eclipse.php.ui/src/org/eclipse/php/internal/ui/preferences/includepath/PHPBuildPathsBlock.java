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

public class PHPBuildPathsBlock extends TempBuildpathsBlock {

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
		
		final Composite container = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = 0;
		container.setLayout(layout);		
		container.setLayoutData(createGridData(GridData.FILL_BOTH, 1, 0));

		fSourceContainerPage = new SourceContainerWorkbookPage(fBuildPathList);
		Control control = fSourceContainerPage.getControl(container);
		control.setLayoutData(createGridData(GridData.FILL_BOTH, 1, 0));
		
		if (fCurrScriptProject != null) {
			fSourceContainerPage.init(fCurrScriptProject);
		}
					
		Dialog.applyDialogFont(container);
		return container;
	}
	
	protected GridData createGridData(int flag, int hspan, int indent) {
		GridData gd = new GridData(flag);
		gd.horizontalIndent = indent;
		gd.horizontalSpan = hspan;
		return gd;
	}
	
	

}
