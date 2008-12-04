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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.includepath.IIncludepathListener;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPBuildPathsBlock extends BuildpathsBlock {

	/**
	 * @author nir.c
	 * Wrapper composite, that un/register itself to any buildpath changes
	 */
	private final class BuildPathComposite extends Composite implements IElementChangedListener {

		private BuildPathComposite(Composite parent, int style) {
			super(parent, style);
			DLTKCore.addElementChangedListener(this);
		}

		@Override
		public void dispose() {
			DLTKCore.removeElementChangedListener(this);
			super.dispose();
		}

		public void elementChanged(ElementChangedEvent event) {
			PHPBuildPathsBlock.this.updateUI();
		}
	}
	
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

		final Composite container = new BuildPathComposite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = 0;
		container.setLayout(layout);
		container.setLayoutData(createGridData(GridData.FILL_BOTH, 1, 0));

		fSourceContainerPage = new PHPBuildPathSourcePage(fBuildPathList);
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

	public void configureScriptProject(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		adaptIncludePath();
		super.configureScriptProject(monitor);
	}

	/**
	 * The purpose of this method is to adapt the include path according to the entries removed from the build path.
	 * If the user removed from the build path source folders that are in the include path,
	 * he will not get code completion and other functionality for this sources.
	 * The user is prompted and asked if he wants to remove the relevant sources from the include path as well
	 * see bug#255930
	 */
	private void adaptIncludePath() {
		PHPBuildPathSourcePage buildPathSourcePage = (PHPBuildPathSourcePage) fSourceContainerPage;

		boolean shouldRemoveFromIncludePath = buildPathSourcePage.shouldRemoveFromIncludePath();

		if (!shouldRemoveFromIncludePath) {
			return;
		}

		// get the source elements that the user removed in the source tab
		List<BPListElement> removedElements = buildPathSourcePage.getRemovedElements();

		List<IBuildpathEntry> buildPathEntries = new ArrayList<IBuildpathEntry>();
		IProject project = fCurrScriptProject.getProject();

		//in case there are any, the user is prompted with a question 
		if (removedElements.size() > 0) {
			for (BPListElement listElement : removedElements) {
				buildPathEntries.add(listElement.getBuildpathEntry());
			}
			// if the user chose to, the relevant entries are removed from the include path 
			try {
				for (IBuildpathEntry buildpathEntry : buildPathEntries) {
					IncludePathManager.getInstance().removeEntryFromIncludePath(project, buildpathEntry);
				}
			} catch (ModelException e) {
				Logger.logException("Failed adding entries to build path", e); ////$NON-NLS-1$
			}
		}
	}

}
