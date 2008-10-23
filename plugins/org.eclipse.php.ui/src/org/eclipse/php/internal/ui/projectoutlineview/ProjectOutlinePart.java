package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.core.resources.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.viewsupport.ProblemTreeViewer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
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
 * @version 1.1 (by NirC, 2008)
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

	/**
	 * This viewer ensures that non-leaves in the hierarchical layout are not
	 * removed by any filters.
	 * 
	 * 
	 */
	/* FIXME : to be remove as DLTK integration build is released (ScriptExplorerPart changes) - NirC
	protected ProblemTreeViewer createViewer(Composite composite) {
		return new ProjectOutlineProblemTreeViewer(composite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
	}
	*/

	@Override
	public String getTitleToolTip() {
		// TODO Auto-generated method stub
		return "Project Outline View";
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

	private void setInputAsEditor(IEditorPart editor) {
		final TreeViewer treeViewer = getTreeViewer();
		if (null == treeViewer.getInput() || !treeViewer.getInput().equals(getInput(editor))) {
			treeViewer.setInput(getInput(editor));
		}
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
		}else if (part instanceof ScriptExplorerPart) {
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
		
		final TreeViewer treeViewer = getTreeViewer();
		if (treeViewer.getInput() == null || !treeViewer.getInput().equals(scriptProject)) {
			treeViewer.setInput(scriptProject);
		}

		return;

	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
		selectProject(part);;
	}
	
	/* FIXME : to be remove as DLTK integration build is released (ScriptExplorerPart changes) - NirC
	protected class ProjectOutlineProblemTreeViewer extends PackageExplorerProblemTreeViewer {
		
		public ProjectOutlineProblemTreeViewer(Composite parent, int style) {
			super(parent, style);
		}

		protected boolean evaluateExpandableWithFilters(Object parent) {
			return false;
		}
	
	}*/
}
