/*******************************************************************************
 * Copyright (c) 2006-2019 Zend Corporation and IBM Corporation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.php.internal.ui.editor.highlighters.ClassHighlighting.ClassApply;

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
		public boolean visit(ClassName className) {
			Expression classNode = className.getName();
			if (classNode instanceof Identifier) {

				String typeName = ((Identifier) classNode).getName();
				IModelAccessCache cache = className.getAST().getBindingResolver().getModelAccessCache();
				try {
					IType[] types = PHPModelUtils.getTypes(typeName, getSourceModule(), className.getStart(), cache,
							new NullProgressMonitor());
					if (types != null) {
						for (IType type : types) {
							if (ModelUtils.isDeprecated(type)) {
								// SemanticHighlightingPresenter.updatePresentation()
								// sorts the "highlighting" areas by
								// ascending position.
								// Any fully-qualified class name highlighting
								// will always be rendered before its class name
								// highlighting (when their start positions
								// differ)...
								// https://bugs.eclipse.org/bugs/show_bug.cgi?id=496045
								// https://bugs.eclipse.org/bugs/show_bug.cgi?id=549957
								// See also
								// ClassHighlighting#visit(ClassInstanceCreation)
								// and
								// DeprecatedHighlighting#highlightStatic(StaticDispatch).
								if (!(ClassHighlighting.SELF.equalsIgnoreCase(typeName)
										|| ClassHighlighting.CLASS.equalsIgnoreCase(typeName)
										|| ClassHighlighting.PARENT.equalsIgnoreCase(typeName))) {
									// We want to highlight all NamespaceName
									// segments, so don't do
									// highlight(classNode) that will only
									// highlight last NamespaceName segment.
									highlight(className);
								}
								if (classNode instanceof NamespaceName) {
									// ...so we must render again the class
									// name "Deprecated Highlighting"
									// on top of the class name
									// "Class Highlighting".
									highlightLastNamespaceSegment((NamespaceName) classNode);
								}
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
				highlightStatic(staticConstantAccess);
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
		public boolean visit(CatchClause catchStatement) {
			catchStatement.getClassNames().stream().forEach(e -> {
				ITypeBinding type = e.resolveTypeBinding();
				if (type != null && ModelUtils.isDeprecated(type.getPHPElement())) {
					highlight(e);
				}
			});
			return true;
		}

		@Override
		public boolean visit(StaticFieldAccess staticFieldAccess) {
			ITypeBinding type = staticFieldAccess.getClassName().resolveTypeBinding();

			if (type != null && ModelUtils.isDeprecated(type.getPHPElement())) {
				highlightStatic(staticFieldAccess);
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
					highlightStatic(methodInvocation);
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

	/**
	 * @see ClassApply#highlightStatic(StaticDispatch)
	 */
	private void highlightStatic(StaticDispatch dispatch) {
		Expression className = dispatch.getClassName();
		if (className instanceof Identifier) {
			if (!ClassHighlighting.SELF.equalsIgnoreCase(((Identifier) className).getName())
					&& !ClassHighlighting.PARENT.equalsIgnoreCase(((Identifier) className).getName())) {
				// We want to highlight all NamespaceName segments,
				// so don't use this.highlight(className) that will only
				// highlight last NamespaceName segment.
				super.highlight(className);
			}
		}
		if (className instanceof NamespaceName) {
			highlightLastNamespaceSegment((NamespaceName) className);
		}
	}

	/**
	 * @see ClassApply#highlightNamespaceType(NamespaceName, boolean)
	 */
	private void highlightLastNamespaceSegment(NamespaceName name) {
		List<Identifier> segments = name.segments();
		if (segments.size() > 0) {
			Identifier segment = segments.get(segments.size() - 1);

			if (segments.size() > 1 || name.isGlobal()) {
				highlight(segment);
			}
		}
	}

	@Override
	protected AbstractSemanticHighlighting highlight(ASTNode node) {
		if (node instanceof NamespaceName) {
			List<Identifier> segments = ((NamespaceName) node).segments();
			if (!segments.isEmpty()) {
				return super.highlight(segments.get(segments.size() - 1));
			}
			return null;
		}
		return super.highlight(node);
	}
}