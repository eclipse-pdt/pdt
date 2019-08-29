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
 *   Dawid Pakuła [469267]
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.List;

import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class ClassHighlighting extends AbstractSemanticHighlighting {
	public final static String SELF = "self"; //$NON-NLS-1$
	public final static String PARENT = "parent"; //$NON-NLS-1$
	public final static String CLASS = "class"; //$NON-NLS-1$

	protected class ClassApply extends AbstractSemanticApply {

		@Override
		public boolean visit(InterfaceDeclaration interfaceDeclaration) {
			highlight(interfaceDeclaration.getName());
			for (Identifier identifier : interfaceDeclaration.interfaces()) {
				if (identifier instanceof NamespaceName) {
					highlightNamespaceType((NamespaceName) identifier);
				} else {
					highlight(identifier);
				}
			}
			return true;
		}

		@Override
		public boolean visit(ClassDeclaration clazz) {
			highlight(clazz.getName());
			Expression superClass = clazz.getSuperClass();
			if (superClass instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) superClass);
			} else if (superClass != null) {
				highlight(superClass);
			}
			for (Identifier identifier : clazz.interfaces()) {
				if (identifier instanceof NamespaceName) {
					highlightNamespaceType((NamespaceName) identifier);
				} else {
					highlight(identifier);
				}
			}
			return true;
		}

		@Override
		public boolean visit(TraitDeclaration trait) {
			highlight(trait.getName());
			Expression superClass = trait.getSuperClass();
			if (superClass instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) superClass);
			} else if (superClass != null) {
				highlight(superClass);
			}
			for (Identifier identifier : trait.interfaces()) {
				if (identifier instanceof NamespaceName) {
					highlightNamespaceType((NamespaceName) identifier);
				} else {
					highlight(identifier);
				}
			}
			return true;
		}

		@Override
		public boolean visit(ClassInstanceCreation clazz) {
			Expression name = clazz.getClassName().getName();
			if (name instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) name, true);
			} else if (name instanceof Identifier) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=496045
				// See also DeprecatedHighlighting#visit(ClassName)
				if (SELF.equalsIgnoreCase(((Identifier) name).getName())
						|| CLASS.equalsIgnoreCase(((Identifier) name).getName())
						|| PARENT.equalsIgnoreCase(((Identifier) name).getName())) {
					return true;
				}
				highlight(name);
			}
			return true;
		}

		@Override
		public boolean visit(InstanceOfExpression instanceOfExpression) {
			Expression name = instanceOfExpression.getClassName().getName();
			if (name instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) name);
			} else if (name instanceof Identifier) {
				highlight(name);
			}
			return true;
		}

		@Override
		public boolean visit(FormalParameter param) {
			Expression type = param.getParameterType();
			if (type instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) type);
			} else if (type instanceof Identifier) {
				if (!PHPSimpleTypes.isHintable(((Identifier) type).getName(), param.getAST().apiLevel())) {
					highlight(type);
				}
			}
			return true;
		}

		@Override
		public boolean visit(FieldsDeclaration fieldsDeclaration) {
			Expression type = fieldsDeclaration.getFieldsType();
			if (type instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) type);
			} else if (type instanceof Identifier) {
				if (!PHPSimpleTypes.isHintable(((Identifier) type).getName(), fieldsDeclaration.getAST().apiLevel())) {
					highlight(type);
				}
			}
			return true;
		}

		@Override
		public boolean visit(FunctionDeclaration functionDeclaration) {
			if (functionDeclaration.getReturnType() == null) {
				return true;
			}
			Identifier type = functionDeclaration.getReturnType();
			if (type instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) type);
			} else if (type != null) {
				if (!PHPSimpleTypes.isHintable(type.getName(), functionDeclaration.getAST().apiLevel())) {
					highlight(type);
				}
			}
			return true;
		}

		@Override
		public boolean visit(TraitUseStatement node) {
			List<NamespaceName> traitList = node.getTraitList();
			for (NamespaceName namespaceName : traitList) {
				highlightNamespaceType(namespaceName);
			}
			List<TraitStatement> tsList = node.getTsList();
			for (TraitStatement traitStatement : tsList) {
				if (traitStatement instanceof TraitAliasStatement) {
					TraitAliasStatement statement = (TraitAliasStatement) traitStatement;
					if (statement.getAlias().getTraitMethod() instanceof FullyQualifiedTraitMethodReference) {
						FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) statement
								.getAlias().getTraitMethod();
						highlightNamespaceType(reference.getClassName());
					}

				} else if (traitStatement instanceof TraitPrecedenceStatement) {
					TraitPrecedenceStatement statement = (TraitPrecedenceStatement) traitStatement;
					FullyQualifiedTraitMethodReference reference = statement.getPrecedence().getMethodReference();
					highlightNamespaceType(reference.getClassName());
					traitList = statement.getPrecedence().getTrList();
					for (NamespaceName namespaceName : traitList) {
						highlightNamespaceType(namespaceName);
					}
				}
			}
			return false;
		}

		@Override
		public boolean visit(CatchClause catchStatement) {
			catchStatement.getClassNames().stream().forEach(e -> {
				if (e instanceof NamespaceName) {
					highlightNamespaceType((NamespaceName) e);
				} else if (e instanceof Identifier) {
					highlight(e);
				}
			});
			return true;
		}

		@Override
		public boolean visit(StaticConstantAccess classConstant) {
			highlightStatic(classConstant);
			return true;
		}

		@Override
		public boolean visit(StaticFieldAccess staticMember) {
			highlightStatic(staticMember);
			return true;
		}

		@Override
		public boolean visit(StaticMethodInvocation staticMethodInvocation) {
			highlightStatic(staticMethodInvocation);
			return true;
		}

		/**
		 * @see DeprecatedHighlighting#highlightStatic(StaticDispatch)
		 */
		private void highlightStatic(StaticDispatch dispatch) {
			Expression className = dispatch.getClassName();
			if (className instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) className, true);
			} else if (className instanceof Identifier) {
				if (!SELF.equalsIgnoreCase(((Identifier) className).getName())
						&& !PARENT.equalsIgnoreCase(((Identifier) className).getName())) {
					highlight(className);
				}
			}
		}

		private void highlightNamespaceType(NamespaceName name) {
			highlightNamespaceType(name, false);
		}

		/**
		 * @see DeprecatedHighlighting#highlightLastNamespaceSegment(NamespaceName)
		 */
		private void highlightNamespaceType(NamespaceName name, boolean excludeSelf) {
			List<Identifier> segments = name.segments();
			if (segments.size() > 0) {
				Identifier segment = segments.get(segments.size() - 1);

				if (segments.size() > 1 || name.isGlobal()
						|| !(PHPSimpleTypes.isHintable(segment.getName(), name.getAST().apiLevel())
								|| (excludeSelf && SELF.equalsIgnoreCase(segment.getName()))
								|| PARENT.equalsIgnoreCase(segment.getName()))) {
					highlight(segment);
				}
			}
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new ClassApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(true).setDefaultTextColor(0, 80, 50);
	}

	@Override
	public String getDisplayName() {
		return Messages.ClassHighlighting_0;
	}
}