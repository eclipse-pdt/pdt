/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.types;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.ScriptFolder;
import org.eclipse.dltk.internal.core.search.DLTKSearchTypeNameMatch;
import org.eclipse.jface.viewers.ILabelDecorator;

public class PHPFullPathLabelProvider extends PHPLabelProvider implements ILabelDecorator {

	public String getText(Object element) {
		if (element != null) {
			String elementName = ""; //$NON-NLS-1$
			String fileName = ""; //$NON-NLS-1$
			if (element instanceof DLTKSearchTypeNameMatch) {
				DLTKSearchTypeNameMatch typeMatch = (DLTKSearchTypeNameMatch) element;
				IType type = typeMatch.getType();
				elementName = type.getElementName();
				fileName = getFullName(type.getSourceModule());
			} else if (element instanceof IType) {
				IType sourceElement = (IType) element;
				elementName = sourceElement.getElementName();
				fileName = getFullName(sourceElement.getSourceModule());
			} else if (element instanceof IMember) {
				IMember sourceElement = (IMember) element;
				elementName = sourceElement.getElementName();
				fileName = getFullName(sourceElement.getSourceModule());
			}

			StringBuilder result = new StringBuilder(elementName);
			result.append(" - "); //$NON-NLS-1$
			result.append(fileName);
			return result.toString();
		}
		return ""; //$NON-NLS-1$
	}

	private String getFullName(ISourceModule sourceModule) {
		IPath path = null;
		if (sourceModule.getParent() instanceof ScriptFolder) {
			path = new Path(sourceModule.getParent().getElementName()).append(new Path(sourceModule.getElementName()));
		} else {
			path = new Path(sourceModule.getElementName());
		}
		return path.toString();
	}

}