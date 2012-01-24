package org.eclipse.php.internal.core.util;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

public class OutlineFilter {

	public static IModelElement[] filterChildrenForOutline(Object parent,
			IModelElement[] children) {
		return filterDuplicatePublicVars(parent, filter(children));
	}

	public static IModelElement[] filter(IModelElement[] children) {
		boolean initializers = false;
		for (int i = 0; i < children.length; i++) {
			if (matches(children[i])) {
				initializers = true;
				break;
			}
		}

		if (!initializers) {
			return children;
		}

		Vector<IModelElement> v = new Vector<IModelElement>();
		for (int i = 0; i < children.length; i++) {
			if (matches(children[i])) {
				continue;
			}
			v.addElement(children[i]);
		}

		IModelElement[] result = new IModelElement[v.size()];
		v.copyInto(result);
		return result;
	}

	public static IModelElement[] filterDuplicatePublicVars(Object parent,
			IModelElement[] children) {
		// public variables can only exist in ISourceModule
		if (!(parent instanceof ISourceModule)) {
			return children;
		}
		Set<IModelElement> result = new TreeSet<IModelElement>(
				new Comparator<IModelElement>() {
					public int compare(IModelElement o1, IModelElement o2) {
						// filter duplications of variables
						if (o1 instanceof IField
								&& o2 instanceof IField
								&& PHPModelUtils.isSameField((IField) o1,
										(IField) o2)) {
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
			if (element.getElementType() == IModelElement.FIELD
					&& !(parentType == IModelElement.METHOD)
					&& !(parentType == IModelElement.TYPE)) {
				return false;
			} else if (parentType == IModelElement.METHOD)
				return true;
		} else if (element.getElementType() == IModelElement.IMPORT_CONTAINER) {
			return true;
		}
		return false;
	}
}
