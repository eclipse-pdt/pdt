/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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

import org.eclipse.dltk.annotations.Nullable;
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
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;
import org.eclipse.php.internal.core.typeinference.context.FileContext;

public class ASTUtils {

	private static final Pattern VAR_COMMENT_PATTERN1 = Pattern.compile(
			"(.*?@var\\p{javaWhitespace}+)([$][^$\\p{javaWhitespace}]+)(\\p{javaWhitespace}+)([^$\\p{javaWhitespace}]+).*", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	private static final Pattern VAR_COMMENT_PATTERN2 = Pattern.compile(
			"(.*?@var\\p{javaWhitespace}+)([^$\\p{javaWhitespace}]+)(\\p{javaWhitespace}+)([$][^$\\p{javaWhitespace}]+).*", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);

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
		Matcher m = null;
		String types = null, varName = null;
		int typeStart = -1, varStart = -1, varEnd = -1;
		boolean foundMatch = false;

		if ((m = VAR_COMMENT_PATTERN1.matcher(content)).matches()) {
			types = m.group(4);
			varName = m.group(2);
			varStart = start + m.group(1).length();
			varEnd = varStart + varName.length();
			typeStart = varEnd + m.group(3).length();
			foundMatch = true;
		} else if ((m = VAR_COMMENT_PATTERN2.matcher(content)).matches()) {
			types = m.group(2);
			varName = m.group(4);
			typeStart = start + m.group(1).length();
			varStart = typeStart + types.length() + m.group(3).length();
			varEnd = varStart + varName.length();
			foundMatch = true;
		}

		if (foundMatch) {
			List<TypeReference> typeReferences = new LinkedList<TypeReference>();

			if (types != null) {
				int pipeIdx = types.indexOf(Constants.TYPE_SEPERATOR_CHAR);
				while (pipeIdx >= 0) {
					String typeName = types.substring(0, pipeIdx);
					int typeEnd = typeStart + typeName.length();
					if (typeName.length() > 0) {
						typeReferences.add(new TypeReference(typeStart, typeEnd, typeName));
					}
					types = types.substring(pipeIdx + 1);
					typeStart += pipeIdx + 1;
					pipeIdx = types.indexOf(Constants.TYPE_SEPERATOR_CHAR);
				}
				String typeName = types;
				int typeEnd = typeStart + typeName.length();
				if (typeName.length() > 0) {
					typeReferences.add(new TypeReference(typeStart, typeEnd, typeName));
				}
			}

			VariableReference varReference = new VariableReference(varStart, varEnd, varName);
			VarComment varComment = new VarComment(start, end, varReference,
					typeReferences.toArray(new TypeReference[typeReferences.size()]));
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
		if (len > 1 && (name.charAt(0) == '\'' && name.charAt(len - 1) == '\''
				|| name.charAt(0) == '"' && name.charAt(len - 1) == '"')) {
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
	@Nullable
	public static ASTNode findMinimalNode(@Nullable ModuleDeclaration unit, int start, int end) {
		if (unit == null) {
			return null;
		}

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
						if ((s.sourceStart() >= result.sourceStart()) && (s.sourceEnd() <= result.sourceEnd())) {
							// now we could not handle ConstantReference in
							// StaticConstantAccess
							if (s instanceof ConstantReference && (result instanceof StaticConstantAccess)) {
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
	public static ASTNode findMaximalNodeEndingAt(ModuleDeclaration unit, final int boundaryOffset) {

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
	public static ASTNode[] restoreWayToNode(ModuleDeclaration module, final ASTNode node) {

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
	 *            Module declaration AST node
	 * @param target
	 *            AST node to find context for
	 */
	public static IContext findContext(final ISourceModule sourceModule, final ModuleDeclaration unit,
			final ASTNode target) {

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
	public static IContext findContext(final ISourceModule sourceModule, final ModuleDeclaration unit,
			final int offset) {

		ContextFinder visitor = new ContextFinder(sourceModule) {
			private IContext context;

			public IContext getContext() {
				return context;
			}

			public boolean visitGeneral(ASTNode node) throws Exception {
				if (!(node instanceof ASTError) && node.sourceStart() <= offset && node.sourceEnd() >= offset) {
					if (!contextStack.isEmpty()) {
						context = contextStack.peek();
					}
				}
				if (node.sourceEnd() < offset || node.sourceStart() > offset) {
					// node is before or after our search
					return false;
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
		if (visitor.getContext() == null) {
			/*
			 * offset can be bigger than unit.end when sourceunit have syntax
			 * error on end
			 */
			Logger.log(Logger.WARNING_DEBUG, "Context is null"); //$NON-NLS-1$
			return new FileContext(sourceModule, unit, offset);
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
	public static Declaration findDeclarationAfterPHPdoc(ModuleDeclaration moduleDeclaration, final int offset) {

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

			@Override
			public boolean visit(Statement s) throws Exception {
				if (s.getKind() == ASTNodeKinds.FIELD_DECLARATION) {
					if (!found && s.sourceStart() > offset) {
						decl[0] = (Declaration) s;
						found = true;
						return false;
					}
				}
				return super.visit(s);
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
	public static FieldDeclaration getConstantDeclaration(CallExpression callExpression) {
		String name = callExpression.getName();
		if ("define".equalsIgnoreCase(name)) { //$NON-NLS-1$
			CallArgumentsList args = callExpression.getArgs();
			if (args != null && args.getChilds() != null && !args.getChilds().isEmpty()) {
				ASTNode argument = (ASTNode) args.getChilds().get(0);
				if (argument instanceof Scalar) {
					String constant = ASTUtils.stripQuotes(((Scalar) argument).getValue());
					FieldDeclaration fieldDeclaration = new FieldDeclaration(constant, argument.sourceStart(),
							argument.sourceEnd(), callExpression.sourceStart(), callExpression.sourceEnd());
					fieldDeclaration.setModifier(Modifiers.AccGlobal | Modifiers.AccConstant | Modifiers.AccFinal);
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
	public static UsePart findUseStatementByAlias(ModuleDeclaration moduleDeclaration, String aliasName, int offset) {

		FindUseStatementByAliasASTVisitor visitor = new FindUseStatementByAliasASTVisitor(aliasName, offset);

		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getResult();
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
	public static UsePart findUseStatementByNamespace(ModuleDeclaration moduleDeclaration, final String namespace,
			final int offset) {

		FindUseStatementByNamespaceASTVisitor visitor = new FindUseStatementByNamespaceASTVisitor(namespace, offset);

		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getResult();
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
	public static UseStatement[] getUseStatements(ModuleDeclaration moduleDeclaration, final int offset) {

		GetUseStatementsASTVisitor visitor = new GetUseStatementsASTVisitor(offset);

		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getResult();
	}
}
