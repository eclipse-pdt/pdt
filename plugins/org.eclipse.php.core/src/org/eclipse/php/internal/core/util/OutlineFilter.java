/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import java.util.*;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

public class OutlineFilter {

	public static IModelElement[] filterChildrenForOutline(Object parent, IModelElement[] children) {
		return filterDuplicatePublicVars(parent, filter(children));
	}

	public static IModelElement[] filter(IModelElement[] children) {
		List<IModelElement> v = new ArrayList<IModelElement>();
		for (int i = 0; i < children.length; i++) {
			if (!matches(children[i])) {
				v.add(children[i]);
			}
		}
		return v.toArray(new IModelElement[v.size()]);
	}

	public static IModelElement[] filterDuplicatePublicVars(Object parent, IModelElement[] children) {
		// public variables can only exist in ISourceModule
		if (!(parent instanceof ISourceModule)) {
			return children;
		}
		Set<IModelElement> result = new TreeSet<IModelElement>(new Comparator<IModelElement>() {
			public int compare(IModelElement o1, IModelElement o2) {
				// filter duplications of variables
				if (o1 instanceof IField && o2 instanceof IField
						&& PHPModelUtils.isSameField((IField) o1, (IField) o2)) {
					return 0;
				}
				return 1;
			}
		});
		for (int i = 0; i < children.length; i++) {
			result.add(children[i]);
		}
		return result.toArray(new IModelElement[result.size()]);
	}

	public static boolean matches(IModelElement element) {
		if (element.getElementType() == IModelElement.METHOD) {
			String name = element.getElementName();
			return (name != null && name.indexOf('<') >= 0);
		}
		// Filter out non-class variables:
		IModelElement parent = element.getParent();
		if (parent != null) {
			int parentType = parent.getElementType();
			if (element.getElementType() == IModelElement.FIELD) {
				if (!(parentType == IModelElement.METHOD) && !(parentType == IModelElement.TYPE)) {
					return false;
				}
				if (parentType == IModelElement.METHOD) {
					IField field = (IField) element;
					try {
						for (IModelElement modelElement : field.getChildren()) {
							if (modelElement.getElementType() == IModelElement.METHOD
									|| modelElement.getElementType() == IModelElement.TYPE) {
								return false;
							}
						}
					} catch (ModelException e) {
						PHPCorePlugin.log(e);
					}
					return true;
				}
			}
		} else if (element.getElementType() == IModelElement.IMPORT_CONTAINER) {
			return true;
		}
		return false;
	}
}
