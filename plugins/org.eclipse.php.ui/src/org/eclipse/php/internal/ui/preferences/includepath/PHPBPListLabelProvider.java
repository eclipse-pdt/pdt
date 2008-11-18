package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListLabelProvider;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.resource.ImageDescriptor;

public class PHPBPListLabelProvider extends BPListLabelProvider {

	protected ImageDescriptor getCPListElementBaseImage(BPListElement cpentry) {

		switch (cpentry.getEntryKind()) {
		case IBuildpathEntry.BPE_SOURCE:
			if (cpentry.getPath().segmentCount() == 1) {
				return fProjectImage;
			} else {
				return DLTKPluginImages
						.getDescriptor(DLTKPluginImages.IMG_OBJS_PACKFRAG_ROOT);
			}
		case IBuildpathEntry.BPE_LIBRARY:
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_LIBRARY);
		case IBuildpathEntry.BPE_PROJECT:
			return fProjectImage;
		case IBuildpathEntry.BPE_CONTAINER:
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_LIBRARY);
		default:
			return null;
		}
	}
}
