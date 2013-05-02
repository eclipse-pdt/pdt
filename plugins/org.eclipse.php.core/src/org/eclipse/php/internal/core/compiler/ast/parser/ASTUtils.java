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
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.*;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;

public class ASTUtils {

	private static final Pattern VAR_COMMENT_PATTERN = Pattern
			.compile("(.*)(\\$[^\\s]+)(\\s+)([^\\s]+).*"); //$NON-NLS-1$

	/**
	 * Parses @@var comment using regular expressions
	 * 
	 * @param content
	 *            Content of the @@var comment token
	 * @param start
	 *            Token start position
	 * @param end
	 *            Token end position
	 * @return {@link VarComment}
	 */
	public static VarComment parseVarComment(String content, int start, int end) {
		Matcher m = VAR_COMMENT_PATTERN.matcher(content);
		if (m.matches()) {
			int varStart = start + m.group(1).length();
			String varName = m.group(2);
			int varEnd = varStart + varName.length();

			List<TypeReference> typeReferences = new LinkedList<TypeReference>();
			int typeStart = varEnd + m.group(3).length();
			String types = m.group(4);

			int pipeIdx = types.indexOf('|');
			while (pipeIdx >= 0) {
				String typeName = types.substring(0, pipeIdx);
				int typeEnd = typeStart + typeName.length();
				if (typeName.length() > 0) {
					typeReferences.add(new TypeReference(typeStart, typeEnd,
							typeName));
				}
				types = types.substring(pipeIdx + 1);
				typeStart += pipeIdx + 1;
				pipeIdx = types.indexOf('|');
			}
			String typeName = types;
			int typeEnd = typeStart + typeName.length();
			if (typeName.length() > 0) {
				typeReferences.add(new TypeReference(typeStart, typeEnd,
						typeName));
			}

			VariableReference varReference = new VariableReference(varStart,
					varEnd, varName);
			VarComment varComment = new VarComment(start, end, varReference,
					(TypeReference[]) typeReferences
							.toArray(new TypeReference[typeReferences.size()]));
			return varComment;
		}
		return null;
	}

	/**
	 * Strips single or double quotes from the start and from the end of the
	 * given string
	 * 
	 * @param name
	 *            String
	 * @return
	 */
	public static String stripQuotes(String name) {
		int len = name.length();
		if (len > 1
				&& (name.charAt(0) == '\'' && name.charAt(len - 1) == '\'' || name
						.charAt(0) == '"'
						&& name.charAt(len - 1) == '"')) {
			name = name.substring(1, len - 1);
		}
		return name;
	}

	/**
	 * Finds minimal ast node, that covers given position
	 * 
	 * @param unit
	 * @param position
	 * @return
	 */
	public static ASTNode findMinimalNode(ModuleDeclaration unit, int start,
			int end) {

		class Visitor extends ASTVisitor {

			ASTNode result = null;
			int start, end;

			public Visitor(int start, int end) {
				this.start = start;
				this.end = end;
			}

			public ASTNode getResult() {
				return result;
			}

			public boolean visitGeneral(ASTNode s) throws Exception {
				int realStart = s.sourceStart();
				int realEnd = s.sourceEnd();
				if (s instanceof Block) {
					realStart = realEnd = -42; // never select on blocks
					// ssanders: BEGIN - Modify narrowing logic
				}
				if (realStart <= start && realEnd >= end) {
					if (result != null) {
						if ((s.sourceStart() >= result.sourceStart())
								&& (s.sourceEnd() <= result.sourceEnd())) {
							// now we could not handle ConstantReference in
							// StaticConstantAccess
							if (s instanceof ConstantReference
									&& (result instanceof StaticConstantAccess)) {
								return false;
							}
							result = s;
						}
					} else {
						result = s;
					}
					// ssanders: END
					if (DLTKCore.DEBUG_SELECTION)
						System.out.println("Found " + s.getClass().getName()); //$NON-NLS-1$
				}
				return true;
			}

		}

		Visitor visitor = new Visitor(start, end);

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getResult();
	}

	/**
	 * Finds minimal ast node, that covers given position
	 * 
	 * @param unit
	 * @param position
	 * @return
	 */
	public static ASTNode findMaximalNodeEndingAt(ModuleDeclaration unit,
			final int boundaryOffset) {

		class Visitor extends ASTVisitor {
			ASTNode result = null;

			public ASTNode getResult() {
				return result;
			}

			public boolean visitGeneral(ASTNode s) throws Exception {
				if (s.sourceStart() < 0 || s.sourceEnd() < 0) {
					return true;
				}
				int sourceEnd = s.sourceEnd();
				if (Math.abs(sourceEnd - boundaryOffset) <= 0) {
					result = s;
				}
				return true;
			}

		}

		Visitor visitor = new Visitor();

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return visitor.getResult();
	}

	/**
	 * This method builds list of AST nodes which enclose the given AST node.
	 * 
	 * @param module
	 * @param node
	 * @return
	 */
	public static ASTNode[] restoreWayToNode(ModuleDeclaration module,
			final ASTNode node) {

		final Stack<ASTNode> stack = new Stack<ASTNode>();

		ASTVisitor visitor = new ASTVisitor() {
			boolean found = false;

			public boolean visitGeneral(ASTNode n) throws Exception {
				if (found) {
					return super.visitGeneral(n);
				}
				stack.push(n);
				if (n.equals(node)) {
					found = true;
				}
				return super.visitGeneral(n);
			}

			public void endvisitGeneral(ASTNode n) throws Exception {
				super.endvisitGeneral(n);
				if (found) {
					return;
				}
				stack.pop();
			}
		};

		try {
			module.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}
		return (ASTNode[]) stack.toArray(new ASTNode[stack.size()]);
	}

	/**
	 * Finds type inference context for the given AST node.
	 * 
	 * @param sourceModule
	 *            Source module element
	 * @param unit
	 *            Module decalaration AST node
	 * @param target
	 *            AST node to find context for
	 */
	public static IContext findContext(final ISourceModule sourceModule,
			final ModuleDeclaration unit, final ASTNode target) {

		ContextFinder visitor = new ContextFinder(sourceModule) {
			private IContext context;

			public IContext getContext() {
				return context;
			}

			public boolean visitGeneral(ASTNode node) throws Exception {
				if (node == target) {
					context = contextStack.peek();
					return false;
				}
				return context == null;
			}
		};

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getContext();
	}

	/**
	 * Finds type inference context for the given offset.
	 * 
	 * @param sourceModule
	 *            Source module element
	 * @param unit
	 *            Module decalaration AST node
	 * @param offset
	 *            Offset in the filetarget
	 */
	public static IContext findContext(final ISourceModule sourceModule,
			final ModuleDeclaration unit, final int offset) {

		ContextFinder visitor = new ContextFinder(sourceModule) {
			private IContext context;

			public IContext getContext() {
				return context;
			}

			public boolean visitGeneral(ASTNode node) throws Exception {
				if (!(node instanceof ASTError) && node.sourceStart() <= offset
						&& node.sourceEnd() >= offset) {
					if (!contextStack.isEmpty()) {
						context = contextStack.peek();
					}
				}
				// search inside - we are looking for minimal node
				return true;
			}
		};

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getContext();
	}

	/**
	 * Finds next declaration after the PHP-doc block
	 * 
	 * @param moduleDeclaration
	 *            AST root node
	 * @param offset
	 *            Offset somewhere in the PHP-doc block
	 * @return declaration after the PHP-doc block or <code>null</code> if first
	 *         coming statement is not declaration.
	 */
	public static Declaration findDeclarationAfterPHPdoc(
			ModuleDeclaration moduleDeclaration, final int offset) {

		final Declaration[] decl = new Declaration[1];

		ASTVisitor visitor = new ASTVisitor() {
			boolean found = false;

			public boolean visit(MethodDeclaration m) {
				if (!found && m.sourceStart() > offset) {
					decl[0] = m;
					found = true;
					return false;
				}
				return !found;
			}

			public boolean visit(TypeDeclaration t) {
				if (!found && t.sourceStart() > offset) {
					decl[0] = t;
					found = true;
					return false;
				}
				return !found;
			}

			public boolean visitGeneral(ASTNode n) {
				if (!found && n.sourceStart() > offset) {
					found = true;
					return false;
				}
				return !found;
			}
		};
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return decl[0];
	}

	/**
	 * Creates declaration of constant for the given call expression in case if
	 * it represents define() call expression.
	 * 
	 * @param callExpression
	 *            Call expression
	 * @return constant declaration if the given call expression represents
	 *         define() expression, otherwise <code>null</code>
	 */
	public static FieldDeclaration getConstantDeclaration(
			CallExpression callExpression) {
		String name = callExpression.getName();
		if ("define".equalsIgnoreCase(name)) { //$NON-NLS-1$
			CallArgumentsList args = callExpression.getArgs();
			if (args != null && args.getChilds() != null) {
				ASTNode argument = (ASTNode) args.getChilds().get(0);
				if (argument instanceof Scalar) {
					String constant = ASTUtils.stripQuotes(((Scalar) argument)
							.getValue());
					FieldDeclaration fieldDeclaration = new FieldDeclaration(
							constant, argument.sourceStart(), argument
									.sourceEnd(), callExpression.sourceStart(),
							callExpression.sourceEnd());
					fieldDeclaration.setModifier(Modifiers.AccGlobal
							| Modifiers.AccConstant | Modifiers.AccFinal);
					return fieldDeclaration;
				}
			}
		}
		return null;
	}

	/**
	 * Finds USE statement by the alias name
	 * 
	 * @param moduleDeclaration
	 *            The AST root node
	 * @param aliasName
	 *            The alias name.
	 * @param offset
	 *            Current position in the file (this is needed since we don't
	 *            want to take USE statements placed below current position into
	 *            account)
	 * @return USE statement part node, or <code>null</code> in case relevant
	 *         statement couldn't be found
	 */
	public static UsePart findUseStatementByAlias(
			ModuleDeclaration moduleDeclaration, final String aliasName,
			final int offset) {
		final UsePart[] result = new UsePart[1];
		try {
			moduleDeclaration.traverse(new ASTVisitor() {
				boolean found;

				public boolean visit(Statement s) throws Exception {
					if (s instanceof UseStatement) {
						UseStatement useStatement = (UseStatement) s;
						for (UsePart usePart : useStatement.getParts()) {
							String alias;
							if (usePart.getAlias() != null) {
								alias = usePart.getAlias().getName();
							} else {
								// In case there's no alias - the alias is the
								// last segment of the namespace name:
								alias = usePart.getNamespace().getName();
							}
							if (aliasName.equalsIgnoreCase(alias)) {
								found = true;
								result[0] = usePart;
								break;
							}
						}
					}
					return visitGeneral(s);
				}

				public boolean visitGeneral(ASTNode node) throws Exception {
					if (found || node.sourceStart() > offset) {
						return false;
					}
					return super.visitGeneral(node);
				}
			});
		} catch (Exception e) {
			Logger.logException(e);
		}

		return result[0];
	}

	/**
	 * Finds USE statement according to the given namespace name
	 * 
	 * @param moduleDeclaration
	 *            The AST root node
	 * @param namespace
	 *            Namespace name
	 * @param offset
	 *            Current position in the file (this is needed since we don't
	 *            want to take USE statements placed below current position into
	 *            account)
	 * @return USE statement part node, or <code>null</code> in case relevant
	 *         statement couldn't be found
	 */
	public static UsePart findUseStatementByNamespace(
			ModuleDeclaration moduleDeclaration, final String namespace,
			final int offset) {
		final UsePart[] result = new UsePart[1];
		try {
			moduleDeclaration.traverse(new ASTVisitor() {
				boolean found;

				public boolean visit(Statement s) throws Exception {
					if (s instanceof UseStatement) {
						UseStatement useStatement = (UseStatement) s;
						for (UsePart usePart : useStatement.getParts()) {
							String ns = usePart.getNamespace()
									.getFullyQualifiedName();
							if (namespace.equalsIgnoreCase(ns)) {
								found = true;
								result[0] = usePart;
								break;
							}
						}
					}
					return visitGeneral(s);
				}

				public boolean visitGeneral(ASTNode node) throws Exception {
					if (found || node.sourceStart() > offset) {
						return false;
					}
					return super.visitGeneral(node);
				}
			});
		} catch (Exception e) {
			Logger.logException(e);
		}

		return result[0];
	}

	/**
	 * Returns all USE statements declared before the specified offset
	 * 
	 * @param moduleDeclaration
	 *            The AST root node
	 * @param offset
	 *            Current position in the file (this is needed since we don't
	 *            want to take USE statements placed below current position into
	 *            account)
	 * @return USE statements list
	 */
	public static UseStatement[] getUseStatements(
			ModuleDeclaration moduleDeclaration, final int offset) {
		final List<UseStatement> result = new LinkedList<UseStatement>();
		try {
			moduleDeclaration.traverse(new ASTVisitor() {
				public boolean visit(Statement s) throws Exception {
					if (s instanceof UseStatement) {
						result.add((UseStatement) s);
					}
					return visitGeneral(s);
				}

				public boolean visitGeneral(ASTNode node) throws Exception {
					if (node.sourceStart() > offset) {
						return false;
					}
					return super.visitGeneral(node);
				}
			});
		} catch (Exception e) {
			Logger.logException(e);
		}
		return (UseStatement[]) result.toArray(new UseStatement[result.size()]);
	}
}
