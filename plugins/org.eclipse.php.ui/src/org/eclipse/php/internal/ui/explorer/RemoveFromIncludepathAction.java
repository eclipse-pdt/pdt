/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.corext.buildpath.BuildpathModifier;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.ui.IWorkbenchSite;

public class RemoveFromIncludepathAction extends Action implements ISelectionChangedListener {
	private final IWorkbenchSite fSite;
	private List fSelectedElements;

	// BuildpathContainer iff isEnabled()

	public RemoveFromIncludepathAction(IWorkbenchSite site) {
		super("Remove from Include Path", DLTKPluginImages.DESC_ELCL_REMOVE_FROM_BP);
		setToolTipText(NewWizardMessages.NewSourceContainerWorkbookPage_ToolBar_RemoveFromCP_tooltip);
		fSite = site;
		fSelectedElements = new ArrayList();
	}

	/**
	 * {@inheritDoc}
	 */
	public void run() {

		final IScriptProject project;
		Object object = fSelectedElements.get(0);
		if (object instanceof ExternalProjectFragment) {
			ExternalProjectFragment projFragment = (ExternalProjectFragment) object;
			IScriptProject scriptProject = projFragment.getScriptProject();
			try {
				IncludePathManager.getInstance().removeEntryFromBuildPath(scriptProject, projFragment.getBuildpathEntry());
			} catch (ModelException e) {
				Logger.logException("Could not remove buildPathEntry", e);
			}
		} else if (object instanceof IProjectFragment) {
			IProjectFragment root = (IProjectFragment) object;
			project = root.getScriptProject();
		} else {
			IncludePath includePath = (IncludePath) object;
			try {
				if (includePath.isBuildpath())
					IncludePathManager.getInstance().removeEntryFromIncludePath(includePath.getProject(), (IBuildpathEntry) (includePath.getEntry()));
			} catch (ModelException e) {
				Logger.logException("Could not remove buildPathEntry", e);
			}
		}
	}

	public void selectionChanged(final SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			setEnabled(canHandle((IStructuredSelection) selection));
		} else {
			setEnabled(canHandle(StructuredSelection.EMPTY));
		}
	}

	private boolean canHandle(IStructuredSelection elements) {
		if (elements.size() == 0)
			return false;
		try {
			fSelectedElements.clear();
			for (Iterator iter = elements.iterator(); iter.hasNext();) {
				Object element = iter.next();
				fSelectedElements.add(element);
				if (!(element instanceof IProjectFragment || element instanceof IScriptProject || element instanceof IncludePath))
					return false;
				if (element instanceof IScriptProject) {
					IScriptProject project = (IScriptProject) element;
					if (!BuildpathModifier.isSourceFolder(project))
						return false;
				} else if (element instanceof IProjectFragment) {
					IBuildpathEntry entry = ((IProjectFragment) element).getRawBuildpathEntry();
					if (entry != null && entry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER) {
						return false;
					}
				} else if (element instanceof IncludePath) {
					return true;
				}
			}
			return true;
		} catch (ModelException e) {
		}
		return false;
	}

}
