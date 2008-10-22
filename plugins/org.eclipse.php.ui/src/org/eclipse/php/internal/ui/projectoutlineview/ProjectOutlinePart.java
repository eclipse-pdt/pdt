package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeViewer;
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

	
	public String getTitleToolTip(Object input) {
		if (null == input)
			return getTitleToolTip();
		
		// TODO ???
		return "NIRC";
	}
	
	@Override
	public String getTitleToolTip() {
		// TODO Auto-generated method stub
		return "Project Outline View";
	}

	@Override
	protected ScriptExplorerLabelProvider createLabelProvider() {
		// TODO Auto-generated method stub
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
		
		setInputAsEditor();
	}

	private void setInputAsEditor() {
		final TreeViewer treeViewer = getTreeViewer();
		treeViewer.setInput(getInput());
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

	private Object getInput() {
		IEditorPart input = getSite().getPage().getActiveEditor();
		IEditorPart editor = (IEditorPart) input;
		if (editor == null) {
			return null;
		}

		final IEditorInput editorInput = editor.getEditorInput();
		final IFile file = (IFile) editorInput.getAdapter(IFile.class);
		if (file != null) {
			final IProject project = file.getProject();
			return DLTKCore.create(project);
		}
		return null;
	}

	public void partActivated(IWorkbenchPart part) {
		setInputAsEditor();
	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
		setInputAsEditor();
	}

}
