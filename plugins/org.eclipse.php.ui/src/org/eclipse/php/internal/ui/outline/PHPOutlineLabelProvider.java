package org.eclipse.php.internal.ui.outline;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
*

* Provisional API: This class/interface is part of an interim API that is still under development and expected to
* change significantly before reaching stability. It is being made available at this early stage to solicit feedback
* from pioneering adopters on the understanding that any code that uses this API will almost certainly be broken
* (repeatedly) as the API evolves.
*/
public class PHPOutlineLabelProvider extends XMLLabelProvider {
	ModelElementLabelProvider fLabelProvider = null;
	
	
	public Image getImage(Object o) {
		if (o instanceof IModelElement) {
			return getModelElementLabelProvider().getImage(o);
		}
		return super.getImage(o);
	}
	
	private ModelElementLabelProvider getModelElementLabelProvider() {
		if (fLabelProvider == null) {
			fLabelProvider = new ModelElementLabelProvider();
		}
		return fLabelProvider;
	}
	
	
	public String getText(Object o) {
		if (o instanceof IModelElement) {
			return getModelElementLabelProvider().getText(o);
		}
		return super.getText(o);
	}
}