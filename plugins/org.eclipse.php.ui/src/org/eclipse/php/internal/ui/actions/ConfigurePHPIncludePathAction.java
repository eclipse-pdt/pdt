/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider.IncludePathContainer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ConfigurePHPIncludePathAction extends Action implements
		ISelectionChangedListener {

	private final IWorkbenchSite fSite;
	private IProject fProject;

	public ConfigurePHPIncludePathAction(IWorkbenchSite site) {
		super(PHPUIMessages.ConfigureIncludePathAction_label,
				DLTKPluginImages.DESC_ELCL_CONFIGURE_BUILDPATH);
		setToolTipText(PHPUIMessages.ConfigureIncludePathAction_tooltip);
		setDisabledImageDescriptor(DLTKPluginImages.DESC_DLCL_CONFIGURE_BUILDPATH);
		fSite = site;
	}

	private Shell getShell() {
		return fSite.getShell();
	}

	public void run() {
		if (fProject != null) {
			// TODO retrieve the page id via project nature
			PreferenceDialog dialog = PreferencesUtil.createPropertyDialogOn(
					getShell(), fProject, null, null, null);
			// search for the language specific page
			final List elements = dialog.getPreferenceManager().getElements(
					PreferenceManager.PRE_ORDER);
			for (Iterator i = elements.iterator(); i.hasNext();) {
				final IPreferenceNode node = (IPreferenceNode) i.next();
				final String nodeId = node.getId();
				if (nodeId.endsWith("IncludepathProperties")) { //$NON-NLS-1$
					// recreate dialog and select page found
					dialog.close();
					dialog = PreferencesUtil.createPropertyDialogOn(getShell(),
							fProject, nodeId, null, null);
					break;
				}
			}
			dialog.open();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(final SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			setEnabled(canHandle((IStructuredSelection) selection));
		} else {
			setEnabled(canHandle(StructuredSelection.EMPTY));
		}
	}

	private boolean canHandle(IStructuredSelection elements) {
		if (elements.size() != 1)
			return false;

		Object firstElement = elements.getFirstElement();
		fProject = getProjectFromSelectedElement(firstElement);
		return fProject != null;
	}

	private IProject getProjectFromSelectedElement(Object firstElement) {
		if (firstElement instanceof IModelElement) {
			IModelElement element = (IModelElement) firstElement;
			IProjectFragment root = ScriptModelUtil.getProjectFragment(element);

			if (root != null && root != element && root.isArchive()) {
				return null;
			}
			IScriptProject project = element.getScriptProject();
			if (project != null) {
				return project.getProject();
			}
			return null;
		} else if (firstElement instanceof IncludePath) {
			return ((IncludePath) firstElement).getProject();
		} else if (firstElement instanceof IncludePathContainer) {
			return ((BuildPathContainer) firstElement).getScriptProject()
					.getProject();
		} else if (firstElement instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) firstElement)
					.getAdapter(IResource.class);
			if (res != null) {
				return res.getProject();
			}
		}
		return null;
	}

}
