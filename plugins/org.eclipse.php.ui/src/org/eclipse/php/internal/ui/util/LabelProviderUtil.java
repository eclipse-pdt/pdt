/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
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
