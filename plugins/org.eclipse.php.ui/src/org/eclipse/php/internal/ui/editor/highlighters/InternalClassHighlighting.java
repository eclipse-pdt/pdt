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
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.search.AbstractOccurrencesFinder;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.swt.graphics.RGB;

public class InternalClassHighlighting extends AbstractSemanticHighlighting {

	protected class InternalClassApply extends AbstractSemanticApplyWithNS {

		public InternalClassApply(ISourceModule sourceModule) {
			super(sourceModule);
		}

		@Override
		public boolean visit(CatchClause catchStatement) {
			List<Expression> classNames = catchStatement.getClassNames();
			for (Expression className : classNames) {
				if (className instanceof Identifier) {
					dealIdentifier((Identifier) className);
				}
			}
			return true;
		}

		@Override
		public boolean visit(StaticConstantAccess staticDispatch) {
			Expression className = staticDispatch.getClassName();
			if (className instanceof Identifier) {
				dealIdentifier((Identifier) className);
			}
			return false;
		}

		@Override
		public boolean visit(StaticFieldAccess staticDispatch) {
			Expression className = staticDispatch.getClassName();
			if (className instanceof Identifier) {
				dealIdentifier((Identifier) className);
			}
			return false;
		}

		@Override
		public boolean visit(StaticMethodInvocation staticDispatch) {
			Expression className = staticDispatch.getClassName();
			if (className instanceof Identifier) {
				dealIdentifier((Identifier) className);
			}
			return true;
		}

		@Override
		public boolean visit(ClassName className) {
			if (className.getName() instanceof Identifier) {
				Identifier identifier = (Identifier) className.getName();
				dealIdentifier(identifier);
			}
			return false;
		}

		@Override
		public boolean visit(ClassDeclaration classDeclaration) {
			checkSuper(classDeclaration.getSuperClass(), classDeclaration.interfaces());
			return true;
		}

		@Override
		public boolean visit(TraitDeclaration traitDeclaration) {
			checkSuper(traitDeclaration.getSuperClass(), traitDeclaration.interfaces());
			return true;
		}

		/**
		 * Checks if the supers are with the name of the class
		 * 
		 * @param superClass
		 * @param interfaces
		 */
		private void checkSuper(Expression superClass, List<Identifier> interfaces) {
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
			NamespaceDeclaration namespace = fCurrentNamespace;
			if (identifier instanceof NamespaceName) {
				NamespaceName namespaceName = (NamespaceName) identifier;
				if (namespaceName.isGlobal()) {
					namespace = null;
				}
			}
			String fullName = AbstractOccurrencesFinder.getFullName(identifier, fLastUseParts, namespace);
			FileContext context = new FileContext(getSourceModule(),
					SourceParserUtil.getModuleDeclaration(getSourceModule(), null), identifier.getStart());

			IModelElement[] elements = PHPTypeInferenceUtils.getModelElements(
					PHPClassType.fromTypeName(fullName, getSourceModule(), identifier.getStart()), context,
					identifier.getAST().getBindingResolver().getModelAccessCache());
			/*
			 * PhpModelAccess.getDefault().findTypes( fullName, MatchRule.EXACT,
			 * 0, 0, createSearchScope(), null);
			 */
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
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(new RGB(0, 0, 192));
	}

	@Override
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
