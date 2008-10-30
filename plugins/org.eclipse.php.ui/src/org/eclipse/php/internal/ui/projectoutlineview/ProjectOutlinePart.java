/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ModelElementSorter;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.viewsupport.ProblemTreeViewer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Project Outline for the php perspective, it is based on the {@link ScriptExplorerPart} 
 * Registration to the page part (editor) is done to change the project context  
 * @author Roy, 2008
 * @version 2.0 (by NirC, 2008)
 */
public class ProjectOutlinePart extends ScriptExplorerPart implements IPartListener {

	@Override
	public ProjectOutlineContentProvider createContentProvider() {
		boolean showCUChildren = DLTKUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_SOURCE_MODULE_CHILDREN);

		return new ProjectOutlineContentProvider(showCUChildren) {
			protected IPreferenceStore getPreferenceStore() {
				return DLTKUIPlugin.getDefault().getPreferenceStore();
			}
		};

	}

	@Override
	public String getTitleToolTip() {
		return PHPUIMessages.getString("PHPProjectOutline.title.tooltip");
	}

	@Override
	protected ScriptExplorerLabelProvider createLabelProvider() {
		final IPreferenceStore store = DLTKUIPlugin.getDefault().getPreferenceStore();

		return new ProjectOutlineLabelProvider(getContentProvider(), store);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		// register this view to the page 
		getSite().getPage().addPartListener(this);

		selectProject(null);
	}

	@Override
	protected void setComparator() {
		getTreeViewer().setComparator(new ModelElementSorter()/*new ViewerSorter(){
					@Override
					public int compare(Viewer viewer, Object object1, Object object2) {
						if (object1 instanceof IModelElement && object2 instanceof IModelElement) {
							IModelElement left = (IModelElement) object1;
							IModelElement right = (IModelElement) object2;
							int result = left.getElementName().compareToIgnoreCase(right.getElementName());
							if (result != 0)
								return result;
							return (left.getPath() + left.getElementName()).compareToIgnoreCase(right.getPath() + right.getElementName());
						}
						return super.compare(viewer, object1, object2);
					}
					
				}*/);

	}

	private void setInputAsEditor(IScriptProject scriptProject) {
		final TreeViewer treeViewer = getTreeViewer();
		if (null == treeViewer.getInput() || !treeViewer.getInput().equals(scriptProject)) {
			treeViewer.setInput(scriptProject);
		}
	}

	private void setInputAsEditor(IEditorPart editor) {
		setInputAsEditor((IScriptProject) getInput(editor));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart#dispose()
	 */
	@Override
	public void dispose() {
		// unregister to the page
		getSite().getPage().removePartListener(this);

		super.dispose();
	}

	private Object getInput(IEditorPart editor) {

		final IEditorInput editorInput = editor.getEditorInput();
		final IFile file = (IFile) editorInput.getAdapter(IFile.class);
		if (file != null) {
			final IProject project = file.getProject();
			return DLTKCore.create(project);
		}
		return null;
	}

	public void partActivated(IWorkbenchPart part) {
		selectProject(part);

	}

	private void selectProject(IWorkbenchPart part) {
		IEditorPart editor = getSite().getPage().getActiveEditor();

		if (editor != null) {
			setInputAsEditor(editor);
		} else if (part instanceof ScriptExplorerPart) {
			setInputAsExplorerProject(part);
			return;
		}
	}

	private void setInputAsExplorerProject(IWorkbenchPart part) {
		final TreeSelection input = (TreeSelection) ((ScriptExplorerPart) part).getTreeViewer().getSelection();
		IScriptProject scriptProject = null;
		//getting selected project (from ScriptExplorerPart selection.
		if (input.getFirstElement() instanceof IModelElement) {
			scriptProject = (IScriptProject) ((IModelElement) input.getFirstElement()).getAncestor(IModelElement.SCRIPT_PROJECT);
		}

		if (null == scriptProject) {
			// If no project is selected - choosing the first project
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			if (null != projects && projects.length != 0) {
				scriptProject = DLTKCore.create(projects[0]);
			}
		}

		setInputAsEditor(scriptProject);
		return;

	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
		selectProject(part);
	}

	
	/**
	 * Enable lazy loading for group elements
	 */
	protected ProblemTreeViewer createViewer(Composite composite) {
		return new ProjectOutlineProblemTreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	protected class ProjectOutlineProblemTreeViewer extends PackageExplorerProblemTreeViewer {

		public ProjectOutlineProblemTreeViewer(Composite parent, int style) {
			super(parent, style);
		}

		/**
		 * Always return true for elements to reduce model queries 
		 */
		protected boolean evaluateExpandableWithFilters(Object parent) {
			return false;
		}
	}
}
