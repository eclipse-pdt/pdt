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

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.internal.core.SourceMethod;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public abstract class TraitMemberHighlighting extends
		AbstractSemanticHighlighting {

	protected class TraitMemberApply extends AbstractSemanticApply {

		private int visitField = 0;

		@Override
		public boolean visit(FieldAccess fieldAccess) {
			if (!isMethod()) {
				visitField++;
			}
			return true;
		}

		public boolean visit(Variable var) {
			if (!isMethod()) {
				if (visitField > 0 && !var.isDollared()) {

					IModelElement[] elements = null;
					boolean processed = false;
					try {
						elements = (getSourceModule()).codeSelect(
								var.getStart(), var.getLength());
						if (elements != null && elements.length > 0) {
							processed = true;
							for (IModelElement iModelElement : elements) {
								if ((iModelElement instanceof SourceField)) {
									SourceField sourceMethod = (SourceField) iModelElement;
									if (sourceMethod.getDeclaringType() != null
											&& PHPFlags.isTrait(sourceMethod
													.getDeclaringType()
													.getFlags())) {
										highlight(var);
										break;
									}
								}
							}
						}
					} catch (ModelException e) {
					}
					if (!processed) {
						// highlight(var);
					}
				}
			}
			return true;
		}

		@Override
		public void endVisit(FieldAccess fieldAccess) {
			if (!isMethod()) {
				visitField--;
			}
		}

		@Override
		public void endVisit(Program program) {
			if (!isMethod()) {
				if (visitField < 0) {
					throw new IllegalStateException("visitField is negative: "
							+ visitField);
				}
			}
		}

		@Override
		public boolean visit(MethodDeclaration classMethodDeclaration) {
			if (isMethod()) {
				if (classMethodDeclaration.getParent().getParent() instanceof TraitDeclaration) {
					Identifier functionName = classMethodDeclaration
							.getFunction().getFunctionName();
					highlight(functionName);
				}
			}
			return true;
		}

		@Override
		public boolean visit(SingleFieldDeclaration fieldDecl) {
			if (!isMethod()) {
				ClassDeclaration cd = null;
				ASTNode parent = fieldDecl.getParent();
				while (parent != null) {
					if (parent instanceof ClassDeclaration) {
						cd = (ClassDeclaration) parent;
						break;
					}
					parent = parent.getParent();
				}
				if ((cd instanceof TraitDeclaration)) {
					highlight(fieldDecl.getName());
				}
			}
			return true;
		}

		/**
		 * Mark foo() on: $a->foo();
		 */
		public boolean visit(MethodInvocation methodInvocation) {
			if (isMethod()) {
				checkDispatch(methodInvocation.getMethod().getFunctionName()
						.getName());
			}
			return true;
		}

		/**
		 * @param dispatch
		 * @throws RuntimeException
		 */
		private void checkDispatch(ASTNode node) {
			if (node.getType() == ASTNode.IDENTIFIER) {
				// ((Identifier)node).resolveBinding()
				IModelElement[] elements = null;
				boolean processed = false;
				try {
					elements = (getSourceModule()).codeSelect(node.getStart(),
							node.getLength());
					if (elements != null && elements.length > 0) {
						processed = true;
						for (IModelElement iModelElement : elements) {
							if (iModelElement instanceof SourceMethod) {
								SourceMethod sourceMethod = (SourceMethod) iModelElement;
								if (sourceMethod.getDeclaringType() != null
										&& PHPFlags.isTrait(sourceMethod
												.getDeclaringType().getFlags())) {
									highlight(node);
									break;
								}
							}
						}
					}
				} catch (ModelException e) {
				}
				if (!processed) {
					// highlight(node);
				}

			}
			if (node.getType() == ASTNode.VARIABLE) {
				Variable id = (Variable) node;
				checkDispatch(id.getName());
			}
		}

		public boolean visit(TraitUseStatement node) {
			ISourceModule sourceModule = getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil
					.getModuleDeclaration(sourceModule, null);
			FileContext context = new FileContext(sourceModule,
					moduleDeclaration, node.getStart());
			List<TraitStatement> tsList = node.getTsList();
			for (TraitStatement traitStatement : tsList) {
				if (traitStatement instanceof TraitAliasStatement) {
					TraitAliasStatement statement = (TraitAliasStatement) traitStatement;
					if (statement.getAlias().getTraitMethod() instanceof FullyQualifiedTraitMethodReference) {
						FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) statement
								.getAlias().getTraitMethod();

						IEvaluatedType type = PHPClassType.fromTraitName(
								PHPModelUtils.getFullName(reference
										.getClassName()), sourceModule,
								traitStatement.getStart());
						IType[] modelElements = PHPTypeInferenceUtils
								.getModelElements(type, context,
										traitStatement.getStart());
						if (modelElements != null && modelElements.length > 0) {
							for (IType iType : modelElements) {
								boolean shouldBreak = false;
								try {
									IModelElement[] children = iType
											.getChildren();
									for (IModelElement iModelElement : children) {
										if (iModelElement.getElementName()
												.equals(reference
														.getFunctionName()
														.getName())) {
											if ((iModelElement instanceof IMethod)
													&& isMethod()) {
												highlight(reference
														.getFunctionName());
											} else if (!(iModelElement instanceof IMethod)
													&& !isMethod()) {
												highlight(reference
														.getFunctionName());
											}
											shouldBreak = true;
											break;
										}
									}
								} catch (ModelException e) {
									e.printStackTrace();
								}
								if (shouldBreak) {
									break;
								}
							}
						}
					} else {
						Identifier method = (Identifier) statement.getAlias()
								.getTraitMethod();
						List<NamespaceName> traitList = node.getTraitList();
						for (NamespaceName namespaceName : traitList) {
							boolean shouldBreak = false;
							IEvaluatedType type = PHPClassType.fromTraitName(
									PHPModelUtils.getFullName(namespaceName),
									sourceModule, traitStatement.getStart());
							IType[] modelElements = PHPTypeInferenceUtils
									.getModelElements(type, context,
											traitStatement.getStart());
							if (modelElements != null
									&& modelElements.length > 0) {
								for (IType iType : modelElements) {
									try {
										IModelElement[] children = iType
												.getChildren();
										for (IModelElement iModelElement : children) {
											if (iModelElement.getElementName()
													.equals(method.getName())) {
												if ((iModelElement instanceof IMethod)
														&& isMethod()) {
													highlight(method);
												} else if (!(iModelElement instanceof IMethod)
														&& !isMethod()) {
													highlight(method);
												}
												shouldBreak = true;
												break;
											}
										}
									} catch (ModelException e) {
										e.printStackTrace();
									}
									if (shouldBreak) {
										break;
									}
								}
							}
							if (shouldBreak) {
								break;
							}
						}
					}

				} else if (traitStatement instanceof TraitPrecedenceStatement) {
					TraitPrecedenceStatement statement = (TraitPrecedenceStatement) traitStatement;
					FullyQualifiedTraitMethodReference reference = statement
							.getPrecedence().getMethodReference();

					IEvaluatedType type = PHPClassType
							.fromTraitName(PHPModelUtils.getFullName(reference
									.getClassName()), sourceModule,
									traitStatement.getStart());
					IType[] modelElements = PHPTypeInferenceUtils
							.getModelElements(type, context,
									traitStatement.getStart());
					if (modelElements != null && modelElements.length > 0) {
						for (IType iType : modelElements) {
							boolean shouldBreak = false;
							try {
								IModelElement[] children = iType.getChildren();
								for (IModelElement iModelElement : children) {
									if (iModelElement.getElementName().equals(
											reference.getFunctionName()
													.getName())) {
										if ((iModelElement instanceof IMethod)
												&& isMethod()) {
											highlight(reference
													.getFunctionName());
										} else if (!(iModelElement instanceof IMethod)
												&& !isMethod()) {
											highlight(reference
													.getFunctionName());
										}
										shouldBreak = true;
										break;
									}
								}
							} catch (ModelException e) {
								e.printStackTrace();
							}
							if (shouldBreak) {
								break;
							}
						}
					}
				}
			}
			return false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new TraitMemberApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(0, 80, 50);
	}

	// public String getDisplayName() {
	// return "Traits";
	// }

	protected abstract boolean isMethod();
}