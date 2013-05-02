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
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class FieldHighlighting extends AbstractSemanticHighlighting {

	protected class FielApply extends AbstractSemanticApply {

		private int visitField = 0;

		@Override
		public boolean visit(SingleFieldDeclaration fieldDecl) {
			ClassDeclaration cd = null;
			ASTNode parent = fieldDecl.getParent();
			while (parent != null) {
				if (parent instanceof ClassDeclaration) {
					cd = (ClassDeclaration) parent;
					break;
				}
				parent = parent.getParent();
			}
			highlight(fieldDecl.getName());
			return true;
		}

		@Override
		public boolean visit(FunctionInvocation method) {
			boolean visit = !(visitField > 0);
			if (!visit) {
				for (Expression parameter : method.parameters()) {
					parameter.accept(this);
				}
			}
			return visit;
		}

		@Override
		public boolean visit(FieldAccess fieldAccess) {
			visitField++;
			return true;
		}

		public boolean visit(Variable var) {
			if (visitField > 0 && !var.isDollared()) {
				highlight(var);
			}
			return true;
		}

		@Override
		public void endVisit(FieldAccess fieldAccess) {
			visitField--;
		}

		@Override
		public void endVisit(Program program) {
			if (visitField < 0) {
				throw new IllegalStateException("visitField is negative: " //$NON-NLS-1$
						+ visitField);
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
														.getName())
												&& (iModelElement instanceof IField)) {
											highlight(reference
													.getFunctionName());
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
													.equals(method.getName())
													&& (iModelElement instanceof IField)) {
												highlight(method);
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
													.getName())
											&& ((iModelElement instanceof IField))) {
										highlight(reference.getFunctionName());
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
		return new FielApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setDefaultTextColor(0, 0, 192);
	}

	public String getDisplayName() {
		return Messages.FieldHighlighting_0;
	}
}
