/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.AnonymousClassDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.TraitDeclaration;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPNamespaceType;
import org.eclipse.php.internal.core.typeinference.PHPThisClassType;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPTraitType;

/**
 * This abstract AST visitor finds type inference context
 * 
 * @author michael
 */
public abstract class ContextFinder extends ASTVisitor {

	protected Stack<IContext> contextStack = new Stack<>();
	private ISourceModule sourceModule;

	IType declaringType;
	IType realType;

	public ContextFinder(ISourceModule sourceModule) {
		this.sourceModule = sourceModule;
	}

	public ContextFinder(ISourceModule sourceModule, IType realType, IType declaringType) {
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

	@Override
	public boolean visit(ModuleDeclaration node) throws Exception {
		contextStack.push(new FileContext(sourceModule, node));

		boolean visitGeneral = visitGeneral(node);
		if (!visitGeneral) {
			contextStack.pop();
		}
		return visitGeneral;
	}

	@Override
	public boolean visit(TypeDeclaration node) throws Exception {
		if (node instanceof NamespaceDeclaration) {
			ISourceModuleContext fileContext = (ISourceModuleContext) contextStack.peek();
			PHPNamespaceType instanceType;
			if (((NamespaceDeclaration) node).isGlobal()) {
				instanceType = new PHPNamespaceType(null);
			} else {
				instanceType = new PHPNamespaceType(node.getName());
			}
			contextStack.push(new NamespaceContext(fileContext, instanceType));
			boolean visitGeneral = visitGeneral(node);
			if (!visitGeneral) {
				contextStack.pop();
			}
			return visitGeneral;
		} else {
			ISourceModuleContext parentContext = (ISourceModuleContext) contextStack.peek();
			PHPClassType instanceType;
			if (parentContext instanceof INamespaceContext
					&& ((INamespaceContext) parentContext).getNamespace() != null) {
				if (node instanceof TraitDeclaration) {
					instanceType = new PHPTraitType(((INamespaceContext) parentContext).getNamespace(), node.getName());
				} else {
					instanceType = new PHPClassType(((INamespaceContext) parentContext).getNamespace(), node.getName());
				}
			} else {
				if (node instanceof TraitDeclaration) {
					if (declaringType != null && realType != null
							&& declaringType.getElementName().equals(node.getName())) {
						if (realType.getParent() instanceof IType) {
							IType ns = (IType) realType.getParent();
							instanceType = new PHPThisClassType(ns.getElementName(), realType.getElementName(),
									realType);
						} else {
							instanceType = new PHPThisClassType(realType.getElementName(), realType);
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
	}

	public boolean visit(AnonymousClassDeclaration node) throws Exception {
		ISourceModuleContext parentContext = (ISourceModuleContext) contextStack.peek();

		MultiTypeType multiTypeType = new MultiTypeType();
		if (node.getSuperClass() != null) {
			String typeName = node.getSuperClass().getName();
			String namespace = null;
			if (node.getSuperClass() instanceof FullyQualifiedReference) {
				FullyQualifiedReference fqn = (FullyQualifiedReference) node.getSuperClass();
				if (fqn.getNamespace() != null) {
					namespace = fqn.getNamespace().getName();
				}
			}
			// if (namespace == null) {
			// String fullName = PHPModelUtils.getFullName(typeName,
			// methodContext.getSourceModule(),
			// className.start());
			// typeName = PHPModelUtils.extractElementName(fullName);
			// namespace = PHPModelUtils.extractNameSpaceName(fullName);
			// }
			if (namespace != null) {
				multiTypeType.addType(new PHPClassType(namespace, typeName));
			} else {
				multiTypeType.addType(new PHPClassType(typeName));
			}
		}
		if (node.getInterfaceList() != null) {
			for (TypeReference typeReference : node.getInterfaceList()) {
				String typeName = typeReference.getName();
				String namespace = null;
				if (typeReference instanceof FullyQualifiedReference) {
					FullyQualifiedReference fqn = (FullyQualifiedReference) typeReference;
					if (fqn.getNamespace() != null) {
						namespace = fqn.getNamespace().getName();
					}
				}
				// if (namespace == null) {
				// String fullName = PHPModelUtils.getFullName(typeName,
				// methodContext.getSourceModule(),
				// className.start());
				// typeName = PHPModelUtils.extractElementName(fullName);
				// namespace = PHPModelUtils.extractNameSpaceName(fullName);
				// }
				if (namespace != null) {
					multiTypeType.addType(new PHPClassType(namespace, typeName));
				} else {
					multiTypeType.addType(new PHPClassType(typeName));
				}

				multiTypeType.addType(PHPClassType.fromSimpleReference(typeReference));
			}
		}
		contextStack.push(new TypeContext(parentContext, multiTypeType));
		return visitGeneral(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration node) throws Exception {
		List<String> argumentsList = new LinkedList<>();
		List<IEvaluatedType> argTypes = new LinkedList<>();
		List<Argument> args = node.getArguments();
		for (Argument a : args) {
			argumentsList.add(a.getName());
			argTypes.add(UnknownType.INSTANCE);
		}
		IContext parent = contextStack.peek();
		ModuleDeclaration rootNode = ((ISourceModuleContext) parent).getRootNode();

		contextStack.push(new MethodContext(parent, sourceModule, rootNode, node,
				argumentsList.toArray(new String[argumentsList.size()]),
				argTypes.toArray(new IEvaluatedType[argTypes.size()])));

		boolean visitGeneral = visitGeneral(node);
		if (!visitGeneral) {
			contextStack.pop();
		}
		return visitGeneral;
	}

	@Override
	public boolean endvisit(ModuleDeclaration node) throws Exception {
		contextStack.pop();
		endvisitGeneral(node);
		return true;
	}

	@Override
	public boolean endvisit(TypeDeclaration node) throws Exception {
		contextStack.pop();
		endvisitGeneral(node);
		return true;
	}

	@Override
	public boolean endvisit(MethodDeclaration node) throws Exception {
		contextStack.pop();
		endvisitGeneral(node);
		return true;
	}

	public boolean endvisit(AnonymousClassDeclaration node) throws Exception {
		contextStack.pop();
		endvisitGeneral(node);
		return true;
	}

	@Override
	public boolean visit(Expression s) throws Exception {
		if (s instanceof AnonymousClassDeclaration) {
			return visit((AnonymousClassDeclaration) s);
		}
		return super.visit(s);
	}

	@Override
	public boolean endvisit(Expression s) throws Exception {
		if (s instanceof AnonymousClassDeclaration) {
			return endvisit((AnonymousClassDeclaration) s);
		}
		return super.endvisit(s);
	}
}
