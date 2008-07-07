package org.eclipse.php.internal.ui.projectview;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;


/**
 * Project Explorer for the php perspective, it is based on the {@link ScriptExplorerPart} 
 * Registration to the page part (editor) is done to change the project context  
 * @author Roy, 2008
 */
public class ProjectExplorerPart extends ScriptExplorerPart implements IPartListener {

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
		Object input = getSite().getPage().getActiveEditor();
		if (input instanceof PHPStructuredEditor) {
			PHPStructuredEditor editor = (PHPStructuredEditor) input;
			final IModelElement modelElement = editor.getModelElement();
			if (modelElement != null) {
				return modelElement.getScriptProject();	
			}		
		}
		return DLTKCore.create(DLTKUIPlugin.getWorkspace().getRoot());
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
