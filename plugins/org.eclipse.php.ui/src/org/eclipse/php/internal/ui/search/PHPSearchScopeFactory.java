/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.IWorkingSetSelectionDialog;

/**
 * A PHPSearchScopeFactory.
 * 
 * @author shalom
 */
public class PHPSearchScopeFactory {

	private static final IPHPSearchScope EMPTY_SCOPE = PHPSearchEngine.createPHPSearchScope(IPHPSearchConstants.CLASS, new Object[0]);
	private static final Set EMPTY_SET = new HashSet(0);
	private static PHPSearchScopeFactory instance;

	// A private constructor
	private PHPSearchScopeFactory() {

	}

	/**
	 * Returns an instance of this PHPSearchScopeFactory.
	 * 
	 * @return An instance of this PHPSearchScopeFactory.
	 */
	public static PHPSearchScopeFactory getInstance() {
		if (instance == null) {
			instance = new PHPSearchScopeFactory();
		}
		return instance;
	}

	/**
	 * Returns a workspace PHP search scope.
	 * 
	 * @return IPHPSearchScope for the workspace.
	 */
	public IPHPSearchScope createWorkspaceSearchScope(int searchFor) {
		return PHPSearchEngine.createWorkspaceScope(searchFor);
	}

	/**
	 * Creates a PHP search scope.
	 * @param searchScope 
	 * 
	 * @param workingSets 
	 * @return
	 */
	public IPHPSearchScope createWorkingSetSearchScope(int searchFor, IWorkingSet[] workingSets) {
		if (workingSets == null || workingSets.length < 1)
			return EMPTY_SCOPE;

		Set phpElements = new HashSet(workingSets.length * 10);
		for (int i = 0; i < workingSets.length; i++)
			addPHPElements(phpElements, workingSets[i]);
		return createPHPSearchScope(searchFor, phpElements);
	}

	/**
	 * Creates a PHP search scope according to a user selection.
	 * 
	 * @param selection
	 * @return
	 */
	public IPHPSearchScope createSelectedPHPProjectSearchScope(int searchFor, ISelection selection) {
		IEditorInput input = getActiveEditorInput();
		if (input != null) {
			return PHPSearchScopeFactory.getInstance().internalCreateProjectScope(searchFor, input);
		}
		return internalCreateProjectScope(searchFor, selection);
	}

	/**
	 * 
	 * @param selection
	 * @return
	 */
	public IPHPSearchScope createSelectedPHPSearchScope(int searchFor, ISelection selection) {
		Set phpElements;
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			Iterator iter = ((IStructuredSelection) selection).iterator();
			phpElements = new HashSet(((IStructuredSelection) selection).size());
			while (iter.hasNext()) {
				Object selectedElement = iter.next();
				if (selectedElement instanceof IResource)
					addPHPElements(phpElements, (IResource) selectedElement);
				else if (selectedElement instanceof IAdaptable) {
					IResource resource = (IResource) ((IAdaptable) selectedElement).getAdapter(IResource.class);
					if (resource != null)
						addPHPElements(phpElements, resource);
				}
			}
		} else {
			phpElements = EMPTY_SET;
		}
		return createPHPSearchScope(searchFor, phpElements);
	}

	private IPHPSearchScope createPHPSearchScope(int searchFor, Set phpElements) {
		if (phpElements.isEmpty())
			return EMPTY_SCOPE;
		Object[] elementArray = phpElements.toArray(new Object[phpElements.size()]);
		return PHPSearchEngine.createPHPSearchScope(searchFor, elementArray);
	}

	private void addPHPElements(Set phpElements, IWorkingSet workingSet) {
		if (workingSet == null) {
			return;
		}

		IAdaptable[] elements = workingSet.getElements();
		for (int i = 0; i < elements.length; i++) {
			IResource resource = (IResource) elements[i].getAdapter(IResource.class);
			if (resource != null) {
				phpElements.add(resource);
			}
			// else we don't know what to do with it, ignore.
		}
	}

	private void addPHPElements(Set phpElements, IResource resource) {
		if (resource == null) {
			return;
		}
		phpElements.add(resource);
	}

	/**
	 * Returns the IProjects that are PHP nature and in the php search scope.
	 * 
	 * @param scope The scope of the search.
	 * @return All the IProjects that are in the scope and has a Nature of PHP
	 * @see #getProjects(IPHPSearchScope)
	 */
	public IProject[] getPHPProjects(IPHPSearchScope scope) {
		IProject[] projects = scope.getAllEnclosingProjects();
		HashSet temp = new HashSet();
		for (int i = 0; i < projects.length; i++) {
			try {
				if (!projects[i].isAccessible() || !projects[i].hasNature(PHPNature.ID)) {
					continue;
				}
			} catch (CoreException e) {
				PHPUiPlugin.log(e);
				continue;
			}
			temp.add(projects[i]);
		}
		return (IProject[]) temp.toArray(new IProject[temp.size()]);
	}

	/**
	 * Ask the user to select the working sets. 
	 * 
	 * @return The selected IWorkingSets.
	 */
	public IWorkingSet[] queryWorkingSets() {
		Shell shell = PHPUiPlugin.getActiveWorkbenchShell();
		if (shell == null)
			return null;
		IWorkingSetSelectionDialog dialog = PlatformUI.getWorkbench().getWorkingSetManager().createWorkingSetSelectionDialog(shell, true);
		if (dialog.open() == Window.OK) {
			IWorkingSet[] workingSets = dialog.getSelection();
			if (workingSets.length > 0)
				return workingSets;
		}
		return null;
	}

	private IPHPSearchScope internalCreateProjectScope(int searchFor, IEditorInput input) {
		IAdaptable inputElement = getEditorInputElement(input);
		StructuredSelection selection;
		if (inputElement != null) {
			selection = new StructuredSelection(inputElement);
		} else {
			selection = StructuredSelection.EMPTY;
		}
		return internalCreateProjectScope(searchFor, selection);
	}

	private IAdaptable getEditorInputElement(IEditorInput input) {
		// TODO -- See what's getting inside the method.
		//		IAdaptable inputElement= null;
		//		if (input instanceof PHPCodeData) {
		//			inputElement= ((PHPCodeData)input).getName();
		//		} else if (input instanceof IFileEditorInput) {
		//			inputElement= ((IFileEditorInput)input).getFile();
		//		}
		//		return inputElement;
		return null;
	}

	// Returns the active editor's IEditorInput
	private IEditorInput getActiveEditorInput() {
		IWorkbenchPage page = PHPUiPlugin.getActivePage();
		if (page != null) {
			IEditorPart editor = page.getActiveEditor();
			if (editor != null && editor.equals(page.getActivePart())) {
				return editor.getEditorInput();
			}
		}
		return null;
	}

	private IPHPSearchScope internalCreateProjectScope(int searchFor, ISelection selection) {
		Set phpProjects = getPHPProjects(selection);
		return createPHPSearchScope(searchFor, phpProjects);
	}

	private Set getPHPProjects(ISelection selection) {
		Set phpProjects;
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			Iterator iter = ((IStructuredSelection) selection).iterator();
			phpProjects = new HashSet(((IStructuredSelection) selection).size());

			while (iter.hasNext()) {
				Object selectedElement = iter.next();
				IResource resource = null;
				if (selectedElement instanceof IResource) {
					resource = (IResource) selectedElement;
				} else if (selectedElement instanceof IAdaptable) {
					resource = (IResource) ((IAdaptable) selectedElement).getAdapter(IResource.class);
				}
				if (resource != null) {
					IProject project = resource.getProject();
					try {
						if (project.hasNature(PHPNature.ID)) {
							phpProjects.add(project);
						}
					} catch (CoreException e) {
						PHPUiPlugin.log(e);
					}
				}
			}
		} else {
			phpProjects = EMPTY_SET;
		}
		return phpProjects;
	}

	/**
	 * Returns all the accessible IProjects in the search scope.
	 * 
	 * @param scope
	 * @return An array of all the IProject in the scope.
	 * @see #getPHPProjects(IPHPSearchScope)
	 */
	public IProject[] getProjects(IPHPSearchScope scope) {
		IProject[] projects = scope.getAllEnclosingProjects();
		HashSet temp = new HashSet();
		for (int i = 0; i < projects.length; i++) {
			if (projects[i].isAccessible())
				temp.add(projects[i]);
		}
		return (IProject[]) temp.toArray(new IProject[temp.size()]);
	}
}
