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
package org.eclipse.php.internal.core.typeinference.context;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.TraitDeclaration;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPThisClassType;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPTraitType;

/**
 * This abstract AST visitor finds type inference context
 * 
 * @author michael
 */
public abstract class ContextFinder extends ASTVisitor {

	protected Stack<IContext> contextStack = new Stack<IContext>();
	private ISourceModule sourceModule;

	IType declaringType;
	IType realType;

	public ContextFinder(ISourceModule sourceModule) {
		this.sourceModule = sourceModule;
	}

	public ContextFinder(ISourceModule sourceModule, IType realType,
			IType declaringType) {
		this.sourceModule = sourceModule;
		this.declaringType = declaringType;
		this.realType = realType;
	}

	/**
	 * This method must return found context
	 * 
	 * @return
	 */
	public IContext getContext() {
		return null;
	}

	public boolean visit(ModuleDeclaration node) throws Exception {
		contextStack.push(new FileContext(sourceModule, node));

		boolean visitGeneral = visitGeneral(node);
		if (!visitGeneral) {
			contextStack.pop();
		}
		return visitGeneral;
	}

	public boolean visit(TypeDeclaration node) throws Exception {
		if (node instanceof NamespaceDeclaration) {
			if (!((NamespaceDeclaration) node).isGlobal()) {
				FileContext fileContext = (FileContext) contextStack.peek();
				fileContext.setNamespace(node.getName());
			}
		} else {
			ISourceModuleContext parentContext = (ISourceModuleContext) contextStack
					.peek();
			PHPClassType instanceType;
			if (parentContext instanceof INamespaceContext
					&& ((INamespaceContext) parentContext).getNamespace() != null) {
				if (node instanceof TraitDeclaration) {
					instanceType = new PHPTraitType(
							((INamespaceContext) parentContext).getNamespace(),
							node.getName());
				} else {
					instanceType = new PHPClassType(
							((INamespaceContext) parentContext).getNamespace(),
							node.getName());
				}
			} else {
				if (node instanceof TraitDeclaration) {
					if (declaringType != null
							&& realType != null
							&& declaringType.getElementName().equals(
									node.getName())) {
						if (realType.getParent() instanceof IType) {
							IType ns = (IType) realType.getParent();
							instanceType = new PHPThisClassType(
									ns.getElementName(),
									realType.getElementName(), realType);
						} else {
							instanceType = new PHPThisClassType(
									realType.getElementName(), realType);
						}
					} else {
						instanceType = new PHPTraitType(node.getName());
					}
				} else {
					instanceType = new PHPClassType(node.getName());
				}
			}

			contextStack.push(new TypeContext(parentContext, instanceType));

			boolean visitGeneral = visitGeneral(node);
			if (!visitGeneral) {
				contextStack.pop();
			}
			return visitGeneral;
		}

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
		ModuleDeclaration rootNode = ((ISourceModuleContext) parent)
				.getRootNode();

		contextStack.push(new MethodContext(parent, sourceModule, rootNode,
				node, argumentsList.toArray(new String[argumentsList.size()]),
				argTypes.toArray(new IEvaluatedType[argTypes.size()])));

		boolean visitGeneral = visitGeneral(node);
		if (!visitGeneral) {
			contextStack.pop();
		}
		return visitGeneral;
	}

	public boolean endvisit(ModuleDeclaration node) throws Exception {
		contextStack.pop();
		endvisitGeneral(node);
		return true;
	}

	public boolean endvisit(TypeDeclaration node) throws Exception {
		if (!(node instanceof NamespaceDeclaration)) {
			contextStack.pop();
		}
		endvisitGeneral(node);
		return true;
	}

	public boolean endvisit(MethodDeclaration node) throws Exception {
		contextStack.pop();
		endvisitGeneral(node);
		return true;
	}
}
