package org.eclipse.php.internal.ui.actions.filters;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

public class SourceActionFilterNotInEFS implements IActionFilterContributor {

	public boolean testAttribute(Object target, String name, String value) {
		if (target instanceof IModelElement) {
			IModelElement codeData = (IModelElement) target;
			final IResource resource = codeData.getResource();
			
			return resource != null && resource.exists();
		}
		// file is not in EFS, e.g include path
		// TODO what about external files? should be fixed according to the DLTK rules
		return true;
	}

}
