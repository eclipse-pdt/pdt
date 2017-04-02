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

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;

public class DeprecatedHighlighting extends AbstractSemanticHighlighting {

	protected class DeprecatedApply extends AbstractSemanticApply {

		@Override
		public boolean visit(Program program) {
			try {
				getSourceModule().accept(new IModelElementVisitor() {

					@Override
					public boolean visit(IModelElement element) {
						if (element instanceof IMember) {

							try {
								if (ModelUtils.isDeprecated(element)) {
									highlight(((IMember) element).getNameRange());
								}
							} catch (ModelException e) {
								Logger.logException(e);
							}
						}
						return true;
					}
				});

			} catch (ModelException e) {
				Logger.logException(e);
			}
			return true;
		}

		@Override
		public boolean visit(ClassName classConst) {
			if (classConst.getName() instanceof Identifier) {

				String className = ((Identifier) classConst.getName()).getName();
				IModelAccessCache cache = classConst.getAST().getBindingResolver().getModelAccessCache();
				try {
					IType[] types = PHPModelUtils.getTypes(className, getSourceModule(), classConst.getStart(), cache,
							new NullProgressMonitor());
					if (types != null) {
						for (IType type : types) {
							if (ModelUtils.isDeprecated(type)) {
								highlight(classConst);
								break;
							}
						}
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}

			}
			return true;
		}

		@Override
		public boolean visit(StaticConstantAccess staticConstantAccess) {
			ITypeBinding type = staticConstantAccess.getClassName().resolveTypeBinding();

			if (type != null && ModelUtils.isDeprecated(type.getPHPElement())) {
				highlight(staticConstantAccess.getClassName());
			}

			String fieldName = staticConstantAccess.getConstant().getName();
			if (type != null && fieldName != null) {
				IVariableBinding[] fields = type.getDeclaredFields();
				for (IVariableBinding field : fields) {
					if (field.getName().equalsIgnoreCase(fieldName)) {
						if (ModelUtils.isDeprecated(field.getPHPElement())) {
							highlight(staticConstantAccess.getConstant());
						}
						break;
					}
				}
			}

			return super.visit(staticConstantAccess);
		}

		@Override
		public boolean visit(StaticFieldAccess staticFieldAccess) {
			ITypeBinding type = staticFieldAccess.getClassName().resolveTypeBinding();

			if (type != null && ModelUtils.isDeprecated(type.getPHPElement())) {
				highlight(staticFieldAccess.getClassName());
			}

			String fieldName = null;
			if (staticFieldAccess.getField().getName() instanceof Identifier) {
				fieldName = ((Identifier) staticFieldAccess.getField().getName()).getName();
			}

			if (type != null && fieldName != null) {
				IVariableBinding[] fields = type.getDeclaredFields();
				for (IVariableBinding field : fields) {
					if (field.getName().substring(1).equalsIgnoreCase(fieldName)) {
						if (ModelUtils.isDeprecated(field.getPHPElement())) {
							highlight(staticFieldAccess.getField());
						}
					}
				}
			}
			return super.visit(staticFieldAccess);
		}

		@Override
		public boolean visit(FieldAccess fieldAccess) {
			IField field = ModelUtils.getField(fieldAccess);
			if (field != null && ModelUtils.isDeprecated(field)) {
				highlight(fieldAccess.getMember());
			} else if (field != null && field.getParent() instanceof IType
					&& ModelUtils.isDeprecated(field.getParent())) {
				highlight(fieldAccess.getMember());
			}
			return true;
		}

		@Override
		public boolean visit(MethodInvocation methodInv) {
			IMethod method = ModelUtils.getMethod(methodInv);
			if (method != null && ModelUtils.isDeprecated(method)) {
				highlight(methodInv.getMethod().getFunctionName());
			} else if (method != null && method.getParent() instanceof IType
					&& ModelUtils.isDeprecated(method.getParent())) {
				highlight(methodInv.getMethod().getFunctionName());
			}
			return true;
		}

		@Override
		public boolean visit(FunctionInvocation funcInv) {
			if ((funcInv.getParent() instanceof StaticMethodInvocation)) {
				StaticMethodInvocation methodInvocation = (StaticMethodInvocation) funcInv.getParent();
				ITypeBinding type = methodInvocation.getClassName().resolveTypeBinding();

				if (type != null && ModelUtils.isDeprecated(type.getPHPElement())) {
					highlight(methodInvocation.getClassName());
				}

				IMethod method = ModelUtils.getMethod(methodInvocation);
				if (method != null && ModelUtils.isDeprecated(method)) {
					highlight(methodInvocation.getMethod().getFunctionName());

				}
			} else if (!(funcInv.getParent() instanceof MethodInvocation)) {
				IModelAccessCache cache = funcInv.getAST().getBindingResolver().getModelAccessCache();
				if (cache != null) {
					String functionName = ModelUtils.getFunctionName(funcInv.getFunctionName());
					// functionName will be null if the function call looks like
					// ${func}(&$this),the ${func} is type of ReflectionVariable
					if (functionName != null) {
						Collection<IMethod> functions = cache.getGlobalFunctions(getSourceModule(), functionName, null);
						if (functions != null) {
							for (IMethod function : functions) {
								if (ModelUtils.isDeprecated(function)) {
									highlight(funcInv.getFunctionName());
									break;
								}
							}
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
	protected void initDefaultPreferences() {
		getStyle().setStrikethroughByDefault(true).setEnabledByDefault(true);
	}

	@Override
	public int getPriority() {
		return 120;
	}

	@Override
	public String getDisplayName() {
		return Messages.DeprecatedHighlighting_0;
	}
}