package org.eclipse.php.internal.ui.outline;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

/**
*

* Provisional API: This class/interface is part of an interim API that is still under development and expected to
* change significantly before reaching stability. It is being made available at this early stage to solicit feedback
* from pioneering adopters on the understanding that any code that uses this API will almost certainly be broken
* (repeatedly) as the API evolves.
*/
public class PHPOutlineLabelProvider extends XMLLabelProvider {
	
	private ILabelProvider modelElementLabelProvider;

	public PHPOutlineLabelProvider(ILabelProvider modelElementLabelProvider) {
		this.modelElementLabelProvider = modelElementLabelProvider;
	}
	
	public Image getImage(Object o) {
		if (o instanceof IModelElement) {
			return modelElementLabelProvider.getImage(o);
		}
		return super.getImage(o);
	}
	
	public String getText(Object o) {
		if (o instanceof IModelElement) {
			return modelElementLabelProvider.getText(o);
		}
		return super.getText(o);
	}
}