package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.projectoutlineview.ProjectOutlineContentProvider.ProjectOutlineGroups;
import org.eclipse.swt.graphics.Image;

public class ProjectOutlineLabelProvider extends ScriptExplorerLabelProvider {

	public ProjectOutlineLabelProvider(ScriptExplorerContentProvider cp, IPreferenceStore store) {
		super(cp, store);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ProjectOutlineGroups) {
			return ((ProjectOutlineGroups) element).getImage();
			
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ProjectOutlineGroups){
			return getSpecificText(element);
		}
		return super.getText(element);
//		if (text != null) {
//			return decorateText(text, element);
//		}
//		return super.getText(element);
	}
	
	private String getSpecificText(Object element) {
		if (element instanceof ProjectOutlineGroups) {
			return ((ProjectOutlineGroups)element).getText();
		}
		return null;
	}

	

}
