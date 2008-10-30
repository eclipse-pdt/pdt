/**
 * TODO header
 */
package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;


/**
 * TODO description
 * @author nir.c
 *
 */
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
			return ((ProjectOutlineGroups)element).getText();
		}
		return super.getText(element);
	}


}
