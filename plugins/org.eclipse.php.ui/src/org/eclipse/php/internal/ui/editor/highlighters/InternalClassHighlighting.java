/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.search.AbstractOccurrencesFinder;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.swt.graphics.RGB;

public class InternalClassHighlighting extends AbstractSemanticHighlighting {

	protected class InternalClassApply extends AbstractSemanticApplyWithNS {

		public InternalClassApply(ISourceModule sourceModule) {
			super(sourceModule);
		}

		public boolean visit(CatchClause catchStatement) {
			Expression className = catchStatement.getClassName();
			if (className instanceof Identifier) {
				dealIdentifier((Identifier) className);
			}
			return true;
		}

		public boolean visit(StaticConstantAccess staticDispatch) {
			Expression className = staticDispatch.getClassName();
			if (className instanceof Identifier) {
				dealIdentifier((Identifier) className);
			}
			return false;
		}

		public boolean visit(StaticFieldAccess staticDispatch) {
			Expression className = staticDispatch.getClassName();
			if (className instanceof Identifier) {
				dealIdentifier((Identifier) className);
			}
			return false;
		}

		public boolean visit(StaticMethodInvocation staticDispatch) {
			Expression className = staticDispatch.getClassName();
			if (className instanceof Identifier) {
				dealIdentifier((Identifier) className);
			}
			return true;
		}

		public boolean visit(ClassName className) {
			if (className.getName() instanceof Identifier) {
				Identifier identifier = (Identifier) className.getName();
				dealIdentifier(identifier);
			}
			return false;
		}

		public boolean visit(ClassDeclaration classDeclaration) {
			checkSuper(classDeclaration.getSuperClass(),
					classDeclaration.interfaces());
			return true;
		}

		/**
		 * Checks if the supers are with the name of the class
		 * 
		 * @param superClass
		 * @param interfaces
		 */
		private void checkSuper(Expression superClass,
				List<Identifier> interfaces) {
			if (superClass instanceof Identifier) {
				dealIdentifier((Identifier) superClass);
			}

			if (interfaces != null) {
				for (Identifier identifier : interfaces) {
					dealIdentifier(identifier);
				}
			}
		}

		/**
		 * @param identifier
		 */
		private void dealIdentifier(Identifier identifier) {
			String fullName = AbstractOccurrencesFinder.getFullName(identifier,
					fLastUseParts, fCurrentNamespace);
			IModelElement[] elements = PhpModelAccess.getDefault().findTypes(
					fullName, MatchRule.EXACT, 0, 0, createSearchScope(), null);
			if (elements != null && elements.length == 1 && elements[0] != null) {
				if (ModelUtils.isExternalElement(elements[0])) {
					highlight(identifier);
				}
			}
		}

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new InternalClassApply(getSourceModule());
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(
				new RGB(0, 0, 192));
	}

	public String getDisplayName() {
		return Messages.InternalClassHighlighting_0;
	}

	@Override
	public int getPriority() {
		return 110;
	}
	//
	// private boolean isInternalClass(Identifier type) {
	// try {
	// ISourceModule module = getSourceModule();
	// // there is no internal class for external file
	// // https://bugs.eclipse.org/bugs/show_bug.cgi?id=322466
	// if (module.getScriptProject().getProject().getName().trim()
	// .length() == 0) {
	// return false;
	// }
	// IModelElement[] elements = module.codeSelect(type.getStart(),
	// type.getLength());
	// if (elements != null && elements.length == 1 && elements[0] != null) {
	// IModelElement element = (IModelElement) elements[0];
	// return ModelUtils.isExternalElement(element);
	// }
	// } catch (ModelException e) {
	// Logger.logException(e);
	// }
	// return false;
	// }
}
