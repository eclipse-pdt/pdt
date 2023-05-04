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
				highlightIdentifier(identifier, false);
			}
			return true;
		}

		@Override
		public boolean visit(ClassDeclaration clazz) {
			highlight(clazz.getName());
			highlightIdentifier(clazz.getSuperClass(), false);
			for (Identifier identifier : clazz.interfaces()) {
				highlightIdentifier(identifier, false);
			}
			return true;
		}

		@Override
		public boolean visit(TraitDeclaration trait) {
			highlight(trait.getName());
			highlightIdentifier(trait.getSuperClass(), false);
			for (Identifier identifier : trait.interfaces()) {
				highlightIdentifier(identifier, false);
			}
			return true;
		}

		@Override
		public boolean visit(EnumDeclaration en) {
			highlight(en.getName());
			for (Identifier identifier : en.interfaces()) {
				highlightIdentifier(identifier, false);
			}
			return true;
		}

		@Override
		public boolean visit(ClassInstanceCreation clazz) {
			Expression name = clazz.getClassName().getName();
			if (name instanceof Identifier) {
				highlightIdentifier(name, false);
			}
			return true;
		}

		@Override
		public boolean visit(InstanceOfExpression instanceOfExpression) {
			Expression name = instanceOfExpression.getClassName().getName();
			highlightIdentifier(name, false);
			return true;
		}

		@Override
		public boolean visit(FormalParameter param) {
			Expression type = param.getParameterType();
			highlightIdentifier(type, false);
			return true;
		}

		@Override
		public boolean visit(FieldsDeclaration fieldsDeclaration) {
			Expression type = fieldsDeclaration.getFieldsType();
			highlightIdentifier(type, false);
			return true;
		}

		@Override
		public boolean visit(FunctionDeclaration functionDeclaration) {
			if (functionDeclaration.getReturnType() == null) {
				return true;
			}
			Identifier type = functionDeclaration.getReturnType();
			highlightIdentifier(type, false);
			return true;
		}

		@Override
		public boolean visit(TraitUseStatement node) {
			List<NamespaceName> traitList = node.getTraitList();
			for (NamespaceName namespaceName : traitList) {
				highlightIdentifier(namespaceName, false);
			}
			List<TraitStatement> tsList = node.getTsList();
			for (TraitStatement traitStatement : tsList) {
				if (traitStatement instanceof TraitAliasStatement) {
					TraitAliasStatement statement = (TraitAliasStatement) traitStatement;
					if (statement.getAlias().getTraitMethod() instanceof FullyQualifiedTraitMethodReference) {
						FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) statement
								.getAlias().getTraitMethod();
						highlightIdentifier(reference.getClassName(), false);
					}

				} else if (traitStatement instanceof TraitPrecedenceStatement) {
					TraitPrecedenceStatement statement = (TraitPrecedenceStatement) traitStatement;
					FullyQualifiedTraitMethodReference reference = statement.getPrecedence().getMethodReference();
					highlightIdentifier(reference.getClassName(), false);
					traitList = statement.getPrecedence().getTrList();
					for (NamespaceName namespaceName : traitList) {
						highlightIdentifier(namespaceName, false);
					}
				}
			}
			return false;
		}

		@Override
		public boolean visit(CatchClause catchStatement) {
			catchStatement.getClassNames().stream().forEach(e -> {
				highlightIdentifier(e, false);
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
			highlightIdentifier(className, false);
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

		private void highlightIdentifier(ASTNode node, boolean excludeSelf) {
			if (!(node instanceof Identifier)) {
				return;
			}
			Identifier identifier = (Identifier) node;
			if (identifier instanceof DNFType) {
				highlightDNF((DNFType) identifier, excludeSelf);
			} else if (identifier instanceof NamespaceName) {
				highlightNamespaceType((NamespaceName) identifier, excludeSelf);
			} else if (!excludeSelf && SELF.equalsIgnoreCase(identifier.getName())) {
				//
			} else if (!PHPSimpleTypes.isHintable(identifier.getName(), identifier.getAST().apiLevel())
					&& !PARENT.equalsIgnoreCase(identifier.getName())
					&& !CLASS.equalsIgnoreCase(identifier.getName())) {
				highlight(identifier);
			}
		}

		private void highlightDNF(DNFType type, boolean excludeSelf) {
			for (Identifier i : type.elements()) {
				highlightIdentifier(i, excludeSelf);
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