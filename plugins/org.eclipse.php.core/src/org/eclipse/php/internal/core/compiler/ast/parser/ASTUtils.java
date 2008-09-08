/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.ASTError;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class ASTUtils {
	
	/**
	 * Strips single or double quotes from the start and from the end of the given string
	 * @param name String
	 * @return
	 */
	public static String stripQuotes(String name) {
		int len = name.length();
		if (len > 1 && (name.charAt(0) == '\'' && name.charAt(len - 1) == '\'' || name.charAt(0) == '"' && name.charAt(len - 1) == '"')) {
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
	public static ASTNode findMinimalNode(ModuleDeclaration unit, int start, int end) {

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
				} else if (s instanceof TypeDeclaration) {
					TypeDeclaration declaration = (TypeDeclaration) s;
					realStart = declaration.sourceStart();
					realEnd = declaration.sourceEnd();
				} else if (s instanceof MethodDeclaration) {
					MethodDeclaration declaration = (MethodDeclaration) s;
					realStart = declaration.sourceStart();
					realEnd = declaration.sourceEnd();
				}
				if (realStart <= start && realEnd >= end) {
					if (result != null) {
						if ((s.sourceStart() >= result.sourceStart()) && (s.sourceEnd() <= result.sourceEnd()))
							result = s;
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
	 * @param sourceModule Source module element
	 * @param unit Module decalaration AST node 
	 * @param target AST node to find context for
	 */
	public static IContext findContext(final ISourceModule sourceModule, final ModuleDeclaration unit, final ASTNode target) {

		class Visitor extends ASTVisitor {

			private IContext context;
			private Stack<IContext> contextStack = new Stack<IContext>();

			/**
			 * Returns found context
			 * @return found context
			 */
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

			public boolean visit(ModuleDeclaration node) throws Exception {
				contextStack.push(new BasicContext(sourceModule, node));
				return visitGeneral(node);
			}

			public boolean visit(TypeDeclaration node) throws Exception {
				contextStack.push(new InstanceContext((ISourceModuleContext) contextStack.peek(), new PHPClassType(node.getName())));
				return visitGeneral(node);
			}

			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) throws Exception {
				List<String> argumentsList = new LinkedList<String>();
				List<IEvaluatedType> argTypes = new LinkedList<IEvaluatedType>();
				List<Argument> args = node.getArguments();
				for (Argument a : args) {
					argumentsList.add(a.getName());
					argTypes.add(UnknownType.INSTANCE);
				}
				IContext parent = contextStack.peek();
				ModuleDeclaration rootNode = ((ISourceModuleContext) parent).getRootNode();
				contextStack.push(new MethodContext(parent, sourceModule, rootNode, node, argumentsList.toArray(new String[argumentsList.size()]), argTypes.toArray(new IEvaluatedType[argTypes.size()])));
				return visitGeneral(node);
			}

			public boolean endvisit(ModuleDeclaration node) throws Exception {
				contextStack.pop();
				endvisitGeneral(node);
				return true;
			}

			public boolean endvisit(TypeDeclaration node) throws Exception {
				contextStack.pop();
				endvisitGeneral(node);
				return true;
			}

			public boolean endvisit(MethodDeclaration node) throws Exception {
				contextStack.pop();
				endvisitGeneral(node);
				return true;
			}
		}

		Visitor visitor = new Visitor();

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
	 * @param sourceModule Source module element
	 * @param unit Module decalaration AST node 
	 * @param offset Offset in the filetarget
	 */
	public static IContext findContext(final ISourceModule sourceModule, final ModuleDeclaration unit, final int offset) {

		class Visitor extends ASTVisitor {

			private IContext context;
			private Stack<IContext> contextStack = new Stack<IContext>();

			/**
			 * Returns found context
			 * @return found context
			 */
			public IContext getContext() {
				return context;
			}

			public boolean visitGeneral(ASTNode node) throws Exception {
				if (!(node instanceof ASTError) && node.sourceStart() <= offset && node.sourceEnd() >= offset) {
					if (!contextStack.isEmpty()) {
						context = contextStack.peek();
					}
				}
				// search inside - we are looking for minimal node
				return true;
			}

			public boolean visit(ModuleDeclaration node) throws Exception {
				contextStack.push(new BasicContext(sourceModule, node));
				return visitGeneral(node);
			}

			public boolean visit(TypeDeclaration node) throws Exception {
				contextStack.push(new InstanceContext((ISourceModuleContext) contextStack.peek(), new PHPClassType(node.getName())));
				return visitGeneral(node);
			}

			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) throws Exception {
				List<String> argumentsList = new LinkedList<String>();
				List<IEvaluatedType> argTypes = new LinkedList<IEvaluatedType>();
				List<Argument> args = node.getArguments();
				for (Argument a : args) {
					argumentsList.add(a.getName());
					argTypes.add(UnknownType.INSTANCE);
				}
				IContext parent = contextStack.peek();
				ModuleDeclaration rootNode = ((ISourceModuleContext) parent).getRootNode();
				contextStack.push(new MethodContext(parent, sourceModule, rootNode, node, argumentsList.toArray(new String[argumentsList.size()]), argTypes.toArray(new IEvaluatedType[argTypes.size()])));
				return visitGeneral(node);
			}

			public boolean endvisit(ModuleDeclaration node) throws Exception {
				contextStack.pop();
				endvisitGeneral(node);
				return true;
			}

			public boolean endvisit(TypeDeclaration node) throws Exception {
				contextStack.pop();
				endvisitGeneral(node);
				return true;
			}

			public boolean endvisit(MethodDeclaration node) throws Exception {
				contextStack.pop();
				endvisitGeneral(node);
				return true;
			}
		}

		Visitor visitor = new Visitor();

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getContext();
	}
	
	/**
	 * Finds next declaration after the PHP-doc block
	 * @param moduleDeclaration AST root node
	 * @param offset Offset somewhere in the PHP-doc block
	 * @return declaration after the PHP-doc block or <code>null</code> if first coming statement is not declaration.
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
	 * Creates declaration of constant for the given call expression in case if it represents define() call expression.
	 * @param callExpression Call expression
	 * @return constant declaration if the given call expression represents define() expression, otherwise <code>null</code>
	 */
	public static FieldDeclaration getConstantDeclaration(CallExpression callExpression) {
		String name = callExpression.getName();
		if ("define".equalsIgnoreCase(name)) {//$NON-NLS-0$
			CallArgumentsList args = callExpression.getArgs();
			if (args != null && args.getChilds() != null) {
				ASTNode argument = (ASTNode) args.getChilds().get(0);
				if (argument instanceof Scalar) {
					String constant = ASTUtils.stripQuotes(((Scalar)argument).getValue());
					return new FieldDeclaration(constant, argument.sourceStart(), argument.sourceEnd(), callExpression.sourceStart(), callExpression.sourceEnd());
				}
			}
		}
		return null;
	}
}
