package org.eclipse.php.core.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.wst.validation.internal.DisabledResourceManager;

public class LibraryFolderUtil {

	public static boolean inLibraryFolder(IModelElement element) {
		IResource resource = element.getResource();
		if (resource == null)
			return false;

		if (resource.getType() == IResource.FILE) {
			resource = resource.getParent();
		}

		while (resource.getType() == IResource.FOLDER) {
			if (DisabledResourceManager.getDefault().isDisabled(resource)) {
				return true;
			}
			resource = resource.getParent();
		}

		return false;
	}

}
