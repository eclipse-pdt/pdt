package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.*;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.TreeListDialogField;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Source page for the include path preference page
 * @author Eden K., 2008
 *
 */
public class PHPIncludePathSourcePage extends TempSourceContainerWorkbookPage {

	// redefine the indexes of the buttons
	protected int IDX_ADD = 0;
	protected int IDX_REMOVE = 1;
	protected int IDX_ADD_LINK = 2;
	protected int IDX_EDIT = 3;
	
	public PHPIncludePathSourcePage(ListDialogField buildpathList) {
		super(buildpathList); 
	}
	
	/**
	 * This is actually the content provider for the folders list.
	 * override the hasChildren method according to the filter
	 * @author Eden K., 2008
	 *
	 */
	protected class PHPSourceContainerAdapter extends SourceContainerAdapter {
		public boolean hasChildren(TreeListDialogField field, Object element) {
			return shouldDisplayElement(element);
		}

	}
	
	/**
	 * Define which elements in the tree should be displayed
	 * @param element
	 * @return
	 */
	private boolean shouldDisplayElement(Object element) {
		if (element instanceof BPListElementAttribute) {
			BPListElementAttribute attribute = (BPListElementAttribute) element;
			String key = attribute.getKey();
			// do not display include and exclude nodes
			if (key.equals(BPListElement.INCLUSION) || key.equals(BPListElement.EXCLUSION)) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.wizards.buildpath.SourceContainerWorkbookPage#initContainerElements()
	 */
	protected void initContainerElements() {
		SourceContainerAdapter adapter = new PHPSourceContainerAdapter();

		String[] buttonLabels;

		buttonLabels = new String[] {
				NewWizardMessages.SourceContainerWorkbookPage_folders_add_button,				
				NewWizardMessages.SourceContainerWorkbookPage_folders_remove_button };

		fFoldersList = new TreeListDialogField(adapter, buttonLabels,
				new BPListLabelProvider());
		fFoldersList.setDialogFieldListener(adapter);
		fFoldersList
				.setLabelText(NewWizardMessages.SourceContainerWorkbookPage_folders_label);

		fFoldersList.setViewerSorter(new BPListElementSorter());
		
	}
	
//	protected void updateFoldersList() {	
//		ArrayList folders= new ArrayList();
//		
//		IncludePath[] includePath = IncludePathManager.getInstance().getIncludePath(fCurrJProject.getProject());
//			
//		List<IncludePath> includePathEntries= Arrays.asList(includePath);
//		for (IncludePath entry : includePathEntries) {
//			if (entry.getEntry() instanceof IBuildpathEntry && ((IBuildpathEntry)entry.getEntry()).getEntryKind() == IBuildpathEntry.BPE_SOURCE){
//				folders.add(entry);
//			}
//		}				
//		fFoldersList.setElements(folders);		
//		
////		for (int i= 0; i < folders.size(); i++) {
////			BPListElement cpe= (BPListElement) folders.get(i);
////			IPath[] ePatterns= (IPath[]) cpe.getAttribute(BPListElement.EXCLUSION);
////			IPath[] iPatterns= (IPath[])cpe.getAttribute(BPListElement.INCLUSION);			
////			if (ePatterns.length > 0 || iPatterns.length > 0) {
////				fFoldersList.expandElement(cpe, 3);
////			}				
////		}
//	}			
	
	/**
	 * Get the original functionality and add a filter 
	 */
	public Control getControl(Composite parent){
		Control control = super.getControl(parent);
		addFilter();
		return control;
	}
	
	
	private void addFilter() {		
		fFoldersList.getTreeViewer().addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return shouldDisplayElement(element);
			}
		});		
	}
	
	
	protected int getIDX_ADD() {
		return IDX_ADD;
	}

	protected int getIDX_ADD_LINK() {
		return IDX_ADD_LINK;
	}

	protected int getIDX_EDIT() {
		return IDX_EDIT;
	}

	protected int getIDX_REMOVE() {
		return IDX_REMOVE;
	}


}
