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

import java.util.Collection;

import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;

public class DeprecatedHighlighting extends AbstractSemanticHighlighting {

	protected class DeprecatedApply extends AbstractSemanticApply {

		@Override
		public boolean visit(Program program) {
			try {
				IModelElement[] children = getSourceModule().getChildren();
				for (IModelElement child : children) {
					if (ModelUtils.isDeprecated(child)) {
						highlight(((IMember) child).getNameRange());
					}

					IModelElement[] children1 = ((IParent) child).getChildren();
					for (IModelElement child1 : children1) {
						if (ModelUtils.isDeprecated(child1)) {
							highlight(((IMember) child1).getNameRange());
						}
					}
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
			return true;
		}

		@Override
		public boolean visit(ClassName classConst) {
			if (classConst.getName() instanceof Identifier) {
				String className = ((Identifier) classConst.getName())
						.getName();
				Collection<IType> types = getModelCache().getTypes(className,
						null);
				if (types != null) {
					for (IType type : types) {
						if (ModelUtils.isDeprecated(type)) {
							highlight(classConst);
							break;
						}
					}
				}
			}
			return true;
		}

		public boolean visit(FieldAccess fieldAccess) {
			IField field = ModelUtils.getField(fieldAccess);
			if (field != null && ModelUtils.isDeprecated(field)) {
				highlight(fieldAccess.getMember());
			}
			return true;
		}

		public boolean visit(MethodInvocation methodInv) {
			IMethod method = ModelUtils.getMethod(methodInv);
			if (method != null && ModelUtils.isDeprecated(method)) {
				highlight(methodInv.getMethod().getFunctionName());
			}
			return true;
		}

		public boolean visit(FunctionInvocation funcInv) {
			if (!(funcInv.getParent() instanceof MethodInvocation)) {
				Collection<IMethod> functions = getModelCache()
						.getGlobalFunctions(
								ModelUtils.getFunctionName(funcInv
										.getFunctionName()), null);
				if (functions != null) {
					for (IMethod function : functions) {
						if (ModelUtils.isDeprecated(function)) {
							highlight(funcInv.getFunctionName());
							break;
						}
					}
				}
			}
			return true;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new DeprecatedApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setStrikethroughByDefault(true);
	}

	@Override
	public int getPriority() {
		return 12;
	}

	public String getDisplayName() {
		return "Deprecated members";
	}
}