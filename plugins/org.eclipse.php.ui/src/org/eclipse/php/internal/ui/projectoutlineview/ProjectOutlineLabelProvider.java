package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.projectoutlineview.ProjectOutlineContentProvider.GroupNode;
import org.eclipse.swt.graphics.Image;

public class ProjectOutlineLabelProvider extends ScriptExplorerLabelProvider {

	public ProjectOutlineLabelProvider(ScriptExplorerContentProvider cp, IPreferenceStore store) {
		super(cp, store);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof GroupNode) {
			return ((GroupNode) element).getImage();
			
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GroupNode){
			return getSpecificText(element);
		}
		return super.getText(element);
//		if (text != null) {
//			return decorateText(text, element);
//		}
//		return super.getText(element);
	}
	
	private String getSpecificText(Object element) {
		if (element instanceof GroupNode) {
			return ((GroupNode)element).getText();
		}
		return null;
	}

	

}
