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
package org.eclipse.php.internal.core.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;

/**
 * This class is used for resolving model elements by offset in file.
 * Results are filtered in terms of file network.
 */
public class ModelElementResolver {

	/**
	 * Returns possible model elements which are placed under the given offset in the source module
	 * @param sourceModule
	 * @param offset
	 * @return model elements or <code>null</code> in case no element could be found
	 */
	public static IModelElement[] resolve(ISourceModule sourceModule, int offset) {
		IModelElement[] modelElements = null;

		ContextFinder visitor = new ContextFinder(sourceModule, offset);
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		if (visitor.getNode() != null) {
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			IEvaluatedType evaluatedType = typeInferencer.evaluateType(new ExpressionTypeGoal(visitor.getContext(), visitor.getNode()));
			modelElements = PHPTypeInferenceUtils.getModelElements(evaluatedType, (BasicContext) visitor.getContext());
		}

		return modelElements;
	}

	/**
	 * Finds binding context for the given AST node for internal usages only.
	 */
	private static class ContextFinder extends ASTVisitor {

		private IContext context;
		private ASTNode node;
		private Stack<IContext> contextStack = new Stack<IContext>();
		private int offset;
		private ISourceModule sourceModule;

		public ContextFinder(ISourceModule sourceModule, int offset) {
			this.offset = offset;
			this.sourceModule = sourceModule;
		}

		/**
		 * Returns found context
		 * @return found context
		 */
		public IContext getContext() {
			return context;
		}

		/**
		 * Returns found AST node
		 * @return AST node
		 */
		public ASTNode getNode() {
			return node;
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			if (node.sourceEnd() < offset || node.sourceStart() > offset) {
				return false;
			}
			if (node.sourceStart() <= offset &&node.sourceEnd() >= offset) {
				if (!contextStack.isEmpty()) {
					this.context = contextStack.peek();
					this.node = node;
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
			ModuleDeclaration rootNode = ((ISourceModuleContext)parent).getRootNode();
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
}
