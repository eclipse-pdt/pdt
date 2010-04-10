package org.eclipse.php.internal.ui.providers;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;

public class PHPElementImageProvider extends ScriptElementImageProvider {
	@Override
	public ImageDescriptor getScriptImageDescriptor(IModelElement element,
			int flags) {
		int adornmentFlags = computeAdornmentFlags(element, flags);
		Point size = useSmallSize(flags) ? SMALL_SIZE : BIG_SIZE;
		ImageDescriptor descr = getBaseImageDescriptor(element, flags);
		if (descr != null) {
			return new PHPElementImageDescriptor(descr, adornmentFlags, size,
					element);
		} else {
			return null;
		}
	}
}
