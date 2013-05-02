/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.search;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.search.matching.MatchLocator;
import org.eclipse.dltk.core.search.matching.MatchLocatorParser;
import org.eclipse.dltk.core.search.matching.PatternLocator;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;

public class PHPMatchLocatorParser extends MatchLocatorParser {

	public PHPMatchLocatorParser(MatchLocator locator) {
		super(locator);
	}

	protected void visitTypeDeclaration(TypeDeclaration t) {
		if (t instanceof NamespaceDeclaration
				&& ((NamespaceDeclaration) t).isGlobal()) {
			return;
		}
		super.visitTypeDeclaration(t);
	}

	protected void processStatement(ASTNode node, PatternLocator locator) {
		if (node instanceof FieldDeclaration) {
			locator.match((FieldDeclaration) node, getNodeSet());
		} else if (node instanceof ConstantDeclaration) {
			ConstantDeclaration constDecl = (ConstantDeclaration) node;
			ConstantReference constantName = constDecl.getConstantName();
			FieldDeclaration decl = new FieldDeclarationLocation(constantName
					.getName(), constantName.sourceStart(), constantName
					.sourceEnd(), constDecl.sourceStart(), constDecl
					.sourceEnd());
			decl.setModifiers(Modifiers.AccConstant);
			locator.match(decl, getNodeSet());
		} else if (node instanceof FieldAccess) {
			Expression field = ((FieldAccess) node).getField();
			if (field instanceof SimpleReference) {
				SimpleReference ref = (SimpleReference) field;
				SimpleReferenceLocation refLoc = new SimpleReferenceLocation(
						ref.sourceStart(), ref.sourceEnd(), '$' + ref.getName());
				locator.match(refLoc, getNodeSet());
			}
		} else if (node instanceof StaticFieldAccess) {
			Expression field = ((StaticFieldAccess) node).getField();
			if (field instanceof SimpleReference) {
				SimpleReference ref = (SimpleReference) field;
				SimpleReferenceLocation refLoc = new SimpleReferenceLocation(
						ref.sourceStart(), ref.sourceEnd(), '$' + ref.getName());
				locator.match(refLoc, getNodeSet());
			}
		} else if (node instanceof StaticConstantAccess) {
			ConstantReference constantRef = ((StaticConstantAccess) node)
					.getConstant();
			locator.match(constantRef, getNodeSet());
		}
		/*
		 * else if (node instanceof ConstantReference) {
		 * locator.match((ConstantReference)node, getNodeSet()); }
		 */
		else if (node instanceof Assignment) {
			Expression left = ((Assignment) node).getVariable();
			if (left instanceof FieldAccess) { // class variable ($this->a = .)
				FieldAccess fieldAccess = (FieldAccess) left;
				Expression dispatcher = fieldAccess.getDispatcher();
				if (dispatcher instanceof VariableReference) { // && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
					Expression field = fieldAccess.getField();
					if (field instanceof SimpleReference) {
						SimpleReference ref = (SimpleReference) field;
						FieldDeclaration decl = new FieldDeclarationLocation(
								'$' + ref.getName(), ref.sourceStart(), ref
										.sourceEnd(), node.sourceStart(), node
										.sourceEnd());
						locator.match(decl, getNodeSet());
					}
				}
			} else if (left instanceof VariableReference) {
				FieldDeclaration decl = new FieldDeclarationLocation(
						((VariableReference) left).getName(), left
								.sourceStart(), left.sourceEnd(), node
								.sourceStart(), node.sourceEnd());
				locator.match(decl, getNodeSet());
			}
		} else if (node instanceof ListVariable) {
			recursiveListMatch(node, locator);
		} else if (node instanceof TypeReference) {
			locator.match((TypeReference) node, getNodeSet());
		} else if (node instanceof VariableReference) {
			locator.match((VariableReference) node, getNodeSet());
		} else if (node instanceof CallExpression) {
			FieldDeclaration constantDecl = ASTUtils
					.getConstantDeclaration((CallExpression) node);
			if (constantDecl != null) {
				locator.match(constantDecl, getNodeSet());
			} else {
				locator.match((CallExpression) node, getNodeSet());
			}
		} else if (node instanceof Include) {
			Include include = (Include) node;
			if (include.getExpr() instanceof Scalar) {
				Scalar filePath = (Scalar) include.getExpr();
				CallExpression callExpression = new CallExpressionLocation(
						filePath.sourceStart(), filePath.sourceEnd(), null,
						"include", new CallArgumentsList()); //$NON-NLS-1$
				locator.match(callExpression, getNodeSet());
			}
		} else if (node instanceof Argument) {
			SimpleReference ref = ((Argument) node).getRef();
			FieldDeclaration decl = new FieldDeclarationLocation(ref.getName(),
					ref.sourceStart(), ref.sourceEnd(), node.sourceStart(),
					node.sourceEnd());
			locator.match(decl, getNodeSet());
		} else if (node instanceof ForEachStatement) {
			Expression key = ((ForEachStatement) node).getKey();
			Expression value = ((ForEachStatement) node).getValue();
			if (key instanceof SimpleReference) {
				SimpleReference ref = (SimpleReference) key;
				FieldDeclaration decl = new FieldDeclarationLocation(ref
						.getName(), ref.sourceStart(), ref.sourceEnd(), node
						.sourceStart(), node.sourceEnd());
				locator.match(decl, getNodeSet());
			}
			if (value instanceof SimpleReference) {
				SimpleReference ref = (SimpleReference) value;
				FieldDeclaration decl = new FieldDeclarationLocation(ref
						.getName(), ref.sourceStart(), ref.sourceEnd(), ref
						.sourceStart(), ref.sourceEnd());
				locator.match(decl, getNodeSet());
			}
		} else if (node instanceof CatchClause) {
			VariableReference ref = ((CatchClause) node).getVariable();
			FieldDeclaration decl = new FieldDeclarationLocation(ref.getName(),
					ref.sourceStart(), ref.sourceEnd(), node.sourceStart(),
					node.sourceEnd());
			locator.match(decl, getNodeSet());
		}
	}

	private void recursiveListMatch(ASTNode node, PatternLocator locator) {
		final Collection<? extends Expression> variables = ((ListVariable) node)
				.getVariables();
		for (Expression expression : variables) {
			if (expression instanceof ListVariable) {
				recursiveListMatch(expression, locator);
			} else if (expression instanceof VariableReference) {
				FieldDeclaration decl = new FieldDeclarationLocation(
						((VariableReference) expression).getName(), expression
								.sourceStart(), expression.sourceEnd(),
						expression.sourceStart(), expression.sourceEnd());
				locator.match(decl, getNodeSet());
			}
		}
	}

	public void parseBodies(ModuleDeclaration unit) {
		unit.rebuild();
		super.parseBodies(unit);
	}

	static boolean locationEquals(ASTNode node, Object obj) {
		if (obj == node)
			return true;
		if (obj instanceof ASTNode) {
			return node.locationMatches((ASTNode) obj);
		}
		return false;
	}

	class FieldDeclarationLocation extends FieldDeclaration {

		public FieldDeclarationLocation(String name, int nameStart,
				int nameEnd, int declStart, int declEnd) {
			super(name, nameStart, nameEnd, declStart, declEnd);
		}

		public boolean equals(Object obj) {
			return locationEquals(this, obj);
		}

		public int hashCode() {
			return this.sourceEnd() * 1001 + this.sourceEnd();
		}
	}

	private static final class SimpleReferenceLocation extends SimpleReference {

		private SimpleReferenceLocation(int start, int end, String name) {
			super(start, end, name);
		}

		public boolean equals(Object obj) {
			return locationEquals(this, obj);
		}

		public int hashCode() {
			return this.sourceEnd() * 1001 + this.sourceEnd();
		}
	}

	class CallExpressionLocation extends CallExpression {

		public CallExpressionLocation(ASTNode receiver, String name,
				CallArgumentsList args) {
			super(receiver, name, args);
		}

		public CallExpressionLocation(int start, int end, ASTNode receiver,
				SimpleReference name, CallArgumentsList args) {
			super(start, end, receiver, name, args);
		}

		public CallExpressionLocation(int start, int end, ASTNode receiver,
				String name, CallArgumentsList args) {
			super(start, end, receiver, name, args);
		}

		public boolean equals(Object obj) {
			return locationEquals(this, obj);
		}

		public int hashCode() {
			return this.sourceEnd() * 1001 + this.sourceEnd();
		}
	}
}
