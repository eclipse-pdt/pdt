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

package org.eclipse.php.astview.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.ast.nodes.*;


public class ASTViewContentProvider implements ITreeContentProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
	
	/*(non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object child) {
		if (child instanceof ASTNode) {
			ASTNode node= (ASTNode) child;
			ASTNode parent= node.getParent();
			if (parent != null) {
				StructuralPropertyDescriptor prop= node.getLocationInParent();
				return new NodeProperty(parent, prop);
			}
		} //TODO ('else' block was remarked ! ) (NIRC)
		else if (child instanceof ASTAttribute) {
			return ((ASTAttribute) child).getParent();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parent) {
		if (parent instanceof ASTAttribute) {
			return ((ASTAttribute) parent).getChildren();
		} else if (parent instanceof ASTNode) {
			return getNodeChildren((ASTNode) parent);
		}
		return new Object[0];
	}
	
	private Object[] getNodeChildren(ASTNode node) {
		ArrayList res= new ArrayList();
		
		
		if (node instanceof Expression) {
			Expression expression= (Expression) node;
			try {
				ITypeBinding expressionTypeBinding= expression.resolveTypeBinding();
				res.add(createExpressionTypeBinding(node, expressionTypeBinding));
			} catch (Exception e) {
				
			}
			
		/*	
			// expressions:
			if (expression instanceof Identifier) {
				IBinding binding= ((Identifier) expression).resolveBinding();
				if (binding != expressionTypeBinding)
					res.add(createBinding(expression, binding));
			} else if (expression instanceof MethodInvocation) {
				MethodInvocation methodInvocation= (MethodInvocation) expression;
				IMethodBinding binding= methodInvocation.resolveMethodBinding();
				res.add(createBinding(expression, binding));
				String inferred = "nir" ; //FIXME String.valueOf(methodInvocation.isResolvedTypeInferredFromExpectedType());
				res.add(new GeneralAttribute(expression, "ResolvedTypeInferredFromExpectedType", inferred)); //$NON-NLS-1$
			} else if (expression instanceof ClassInstanceCreation) {
				IMethodBinding binding= ((ClassInstanceCreation) expression).resolveConstructorBinding();
				res.add(createBinding(expression, binding));
			} else if (expression instanceof FieldAccess) {
				IVariableBinding binding= ((FieldAccess) expression).resolveFieldBinding();
				res.add(createBinding(expression, binding));

			} else if (expression instanceof InfixExpression) {
				ITypeBinding binding= ((InfixExpression) expression).resolveTypeBinding();
				res.add(createBinding(expression, binding));

			}
			//Assignment, InfixExpression
			// Expression attributes:
			//FIXME : res.add(new GeneralAttribute(expression, "Boxing: " + expression.resolveBoxing() + "; Unboxing: " + expression.resolveUnboxing())); //$NON-NLS-1$ //$NON-NLS-2$
			res.add(new GeneralAttribute(expression, "ConstantExpressionValue", expression.resolveConstantExpressionValue())); //$NON-NLS-1$
		
		// references:
		} /*else if (node instanceof ConstructorInvocation) {
			IMethodBinding binding= ((ConstructorInvocation) node).resolveConstructorBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof SuperConstructorInvocation) {
			IMethodBinding binding= ((SuperConstructorInvocation) node).resolveConstructorBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof MethodRef) {
			IBinding binding= ((MethodRef) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof MemberRef) {
			IBinding binding= ((MemberRef) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof Type) {
			IBinding binding= ((Type) node).resolveBinding();
			res.add(createBinding(node, binding));
			
		// declarations:
		} else if (node instanceof AbstractTypeDeclaration) {
			IBinding binding= ((AbstractTypeDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof AnnotationTypeMemberDeclaration) {
			IBinding binding= ((AnnotationTypeMemberDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof EnumConstantDeclaration) {
			IBinding binding= ((EnumConstantDeclaration) node).resolveVariable();
			res.add(createBinding(node, binding));
			IBinding binding2= ((EnumConstantDeclaration) node).resolveConstructorBinding();
			res.add(createBinding(node, binding2));
		} else if (node instanceof MethodDeclaration) {
			IBinding binding= ((MethodDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof VariableDeclaration) {
			IBinding binding= ((VariableDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof AnonymousClassDeclaration) {
			IBinding binding= ((AnonymousClassDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof ImportDeclaration) {
			IBinding binding= ((ImportDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof PackageDeclaration) {
			IBinding binding= ((PackageDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof TypeParameter) {
			IBinding binding= ((TypeParameter) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof MemberValuePair) {
			IBinding binding= ((MemberValuePair) node).resolveMemberValuePairBinding();
			res.add(createBinding(node, binding));
		*/}
 		
		
		// adds  children nodes of the current ast node 
		List list= node.structuralPropertiesForType();
		for (int i= 0; i < list.size(); i++) {
			StructuralPropertyDescriptor curr= (StructuralPropertyDescriptor) list.get(i);
			res.add(new NodeProperty(node, curr));
		}
		
		if (node instanceof Program) {
			Program root= (Program) node;
			res.add(new PhpElement(root, root.getSourceModule()));
			res.add(new CommentsProperty(root));
			// res.add(new ProblemsProperty(root)); TODO should add ast problems
			res.add(new SettingsProperty(root));
			//	res.add(new WellKnownTypesProperty(root)); TODO : currently we don't have well known types
		}
		
		return res.toArray();
	}
	
	private Binding createBinding(ASTNode parent, IBinding binding) {
		String label= Binding.getBindingLabel(binding);
		return new Binding(parent, label, binding, true);
	}

	private Object createExpressionTypeBinding(ASTNode parent, ITypeBinding binding) {
		String label= "> (Expression) type binding"; //$NON-NLS-1$
		return new Binding(parent, label, binding, true);
	}
	
	public boolean hasChildren(Object parent) {
		return getChildren(parent).length > 0;
	}
}
