package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListLabelProvider;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.resource.ImageDescriptor;

public class PHPBPListLabelProvider extends BPListLabelProvider {

	protected ImageDescriptor getCPListElementBaseImage(BPListElement cpentry) {

		if(cpentry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
			return DLTKPluginImages
			.getDescriptor(DLTKPluginImages.IMG_OBJS_LIBRARY);
		} else {
			return super.getCPListElementBaseImage(cpentry);
		}		
	}
}
