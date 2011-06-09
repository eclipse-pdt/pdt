package org.eclipse.php.internal.ui.util;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;

public class LabelProviderUtil {

	public static String getVariableName(IPath path, int entryKind) {
		switch (entryKind) {
		case IBuildpathEntry.BPE_LIBRARY:
			String[] variableNames = DLTKCore.getBuildpathVariableNames();
			for (String name : variableNames) {
				IPath variablePath = DLTKCore.getBuildpathVariable(name);
				if (EnvironmentPathUtils.isFull(path)) {
					path = EnvironmentPathUtils.getLocalPath(path);
				}
				if (path.equals(variablePath)) {
					return name;
				}
			}
		}
		return null;
	}

}
