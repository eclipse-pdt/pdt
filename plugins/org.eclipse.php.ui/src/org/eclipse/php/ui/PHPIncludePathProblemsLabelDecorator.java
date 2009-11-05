package org.eclipse.php.ui;

import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.ui.ProblemsLabelDecorator;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.ui.phar.wizard.PharUIUtil;

public class PHPIncludePathProblemsLabelDecorator extends
		ProblemsLabelDecorator {
	@Override
	protected int computeAdornmentFlags(Object obj) {
		if (obj instanceof IncludePath) {
			final Object entry = ((IncludePath) obj).getEntry();
			if (entry instanceof IBuildpathEntry) {
				if (PharUIUtil.isInvalidPharBuildEntry((IBuildpathEntry) entry)) {
					return ScriptElementImageDescriptor.ERROR;
				}
			}
		}
		return 0;
	}
}