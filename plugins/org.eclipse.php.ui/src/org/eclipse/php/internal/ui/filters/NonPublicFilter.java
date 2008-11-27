package org.eclipse.php.internal.ui.filters;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.Logger;

/**
 * This class filters out the non public elements of a PHP file (default access is considered as public)
 * @author Eden K., 2008
 *
 */
public class NonPublicFilter extends ViewerFilter {

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IMethod) {
			IMethod method = (IMethod) element;
			try {
				if ((method.getFlags() & Modifiers.AccPrivate) != 0 || (method.getFlags() & Modifiers.AccProtected) != 0) {
					return false;
				}
			} catch (ModelException e) {
				Logger.logException("Failed activating NonPublicFilter ", e); //$NON-NLS-1$
			}
		} else if (element instanceof IField) {
			IField field = (IField) element;
			try {
				if ((field.getFlags() & Modifiers.AccPrivate) != 0 || (field.getFlags() & Modifiers.AccProtected) != 0) {
					return false;
				}
			} catch (ModelException e) {
				Logger.logException("Failed activating NonPublicFilter ", e); //$NON-NLS-1$
			}
		}
		return true;
	}

}
