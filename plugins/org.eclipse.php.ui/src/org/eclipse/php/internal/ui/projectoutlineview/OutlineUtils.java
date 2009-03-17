package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.Logger;

public class OutlineUtils {

	protected static boolean isConstant(IModelElement modelElement) {
		
		if (modelElement instanceof IField && modelElement.getParent() instanceof ISourceModule) {
			int flags = 0;
			try {
				flags = ((IField) modelElement).getFlags();
			} catch (ModelException e) {
				Logger.logException(e);
			}
			if ((flags & Modifiers.AccConstant) != 0)
				return true;
		}
		return false;
	}

	protected static boolean isGlobalClass(IModelElement modelElement) {
		try {
			if (modelElement instanceof IType && !PHPFlags.isNamespace(((IType)modelElement).getFlags()) && modelElement.getParent() instanceof ISourceModule) {
				return true;
			}
		} catch (ModelException e) {
		}
		return false;
	}

	protected static boolean isGlobalFunction(IModelElement modelElement) {
		if (modelElement instanceof IMethod && modelElement.getParent() instanceof ISourceModule) {
			return true;
		}
		return false;
	}
}
