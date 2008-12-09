package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListLabelProvider;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class PHPIPListLabelProvider extends BPListLabelProvider {

	protected ImageDescriptor getCPListElementBaseImage(BPListElement cpentry) {

		if (cpentry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
			return DLTKPluginImages.getDescriptor(DLTKPluginImages.IMG_OBJS_LIBRARY);
		} else if (cpentry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
			//handling Folder special case - need to decide if it's in the build path or not.
			ImageDescriptor folderBaseImage = getFolderBaseImage(cpentry.getResource());
			if(null != folderBaseImage)
				return folderBaseImage ;
		}
		return super.getCPListElementBaseImage(cpentry);

	}

	private static ImageDescriptor getFolderBaseImage(IResource resource) {
		IModelElement modelElement = DLTKCore.create(resource);

		if (null != modelElement) {
			if (modelElement instanceof IScriptFolder)
				return PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT;
		}else{
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
		        ISharedImages.IMG_OBJ_FOLDER); 
		}
		return null;
	}
}
