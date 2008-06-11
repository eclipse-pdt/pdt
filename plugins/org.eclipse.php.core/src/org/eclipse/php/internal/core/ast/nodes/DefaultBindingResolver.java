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
/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.BindingUtility;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

/**
 * @author Roy, 2008
 * TODO : caching is a must have for this resolver
 */
public class DefaultBindingResolver extends BindingResolver {


	/*
	 * Holds on binding tables that can be shared by several ASTs.
	 */
	protected static class BindingTables {

		/**
		 * This map is used to get a binding from its binding key.
		 */
		Map<String, IBinding> bindingKeysToBindings;

		/**
		 * This map is used to keep the correspondence between new ast nodes and the
		 * compiler nodes 
		 */
		Map<Integer, org.eclipse.dltk.ast.ASTNode> compilerNodeToASTNode;

		BindingTables() {
			this.compilerNodeToASTNode = new HashMap<Integer, org.eclipse.dltk.ast.ASTNode>();
			this.bindingKeysToBindings = new HashMap<String, IBinding>();
		}

	}

	/**
	 * The shared binding tables accros ASTs.
	 */
	BindingTables bindingTables;

	/**
	 * Shared source module for this binding resolver, used when resolving types
	 */
	private final ISourceModule sourceModule;

	/**
	 * The working copy owner that defines the context in which this resolver is creating the bindings.
	 */
	WorkingCopyOwner workingCopyOwner;

	/**
	 * Used to resolve types of expressions
	 */
	private BindingUtility bindingUtil;

	/**
	 * @param sourceModule of this resolver
	 */
	public DefaultBindingResolver(ISourceModule sourceModule, WorkingCopyOwner owner) {
		this.sourceModule = sourceModule;
		this.workingCopyOwner = owner;
		this.bindingUtil = new BindingUtility(this.sourceModule);
		this.bindingTables = new BindingTables();
	}

	/**
	 * Returns the new type binding corresponding to the given type. 
	 * 
	 * <p>
	 * The default implementation of this method returns <code>null</code>.
	 * Subclasses may reimplement.
	 * </p>
	 *
	 * @param type the given type
	 * @return the new type binding
	 */
	ITypeBinding getTypeBinding(IType type) {
		if (type != null) {
			return new TypeBinding(this, new PHPClassType(type.getElementName()), type);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#getTypeBinding(org.eclipse.dltk.core.IType[])
	 */
	@Override
	ITypeBinding getTypeBinding(IType[] types) {
		if (types != null && types.length > 0) {
			return new TypeBinding(this, new PHPClassType(types[0].getElementName()), types);
		}
		return null;
	}

	/**
	 * Returns the new variable binding corresponding to the given old variable binding.
	 * 
	 * @param field An {@link IField}
	 * @return the new variable binding, or null in case the given field is null.
	 */
	IVariableBinding getVariableBinding(IField field) {
		if (field != null) {
			// Cache?
			return new VariableBinding(this, field);
		}
		return null;
	}

	/**
	 * Returns the new method binding corresponding to the given {@link IMethod}.
	 *
	 * @param method An {@link IMethod}
	 * @return the new method binding
	 */
	public IMethodBinding getMethodBinding(IMethod method) {
		if (method != null) {
			// Cache?
			return new MethodBinding(this, method);
		}
		return null;
	}

	/**
	 * Returns the {@link IEvaluatedType} according to the offset and the length.
	 */
	protected IEvaluatedType getEvaluatedType(int offset, int length) {
		try {
			return bindingUtil.getType(offset, length);
		} catch (ModelException e) {
			Logger.log(IStatus.ERROR, e.toString());
			return null;
		}
	}

	/**
	 * Returns an {@link IModelElement} array according to the offset and the length.
	 * The result is filtered using the 'File-Network'.
	 * @param offset
	 * @param length
	 * 
	 * @see #getModelElements(int, int, boolean)
	 * @see BindingUtility#getModelElement(int, int)
	 */
	public IModelElement[] getModelElements(int offset, int length) {
		return getModelElements(offset, length, true);
	}

	/**
	 * Returns an {@link IModelElement} array according to the offset and the length.
	 * Use the filter flag to indicate whether the 'File-Network' should be used to filter the
	 * results.
	 * @param offset
	 * @param length
	 * @param filter Indicate whether to use the File-Network in order to filter the results.
	 * 
	 * @see #getModelElements(int, int)
	 * @see BindingUtility#getModelElement(int, int, boolean)
	 */
	public IModelElement[] getModelElements(int offset, int length, boolean filter) {
		try {
			return bindingUtil.getModelElement(offset, length, filter);
		} catch (ModelException e) {
			Logger.log(IStatus.ERROR, e.toString());
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveName(org.eclipse.php.internal.core.ast.nodes.Identifier)
	 */
	IBinding resolveName(Identifier name) {
		return resolveExpressionType(name);
	}

	/**
	 * Resolves the given method declaration and returns the binding for it.
	 * <p>
	 * The implementation of <code>MethodDeclaration.resolveBinding</code>
	 * forwards to this method. How the method resolves is often a function of
	 * the context in which the method declaration node is embedded as well as
	 * the method declaration subtree itself.
	 * </p>
	 * <p>
	 * The default implementation of this method returns <code>null</code>.
	 * Subclasses may reimplement.
	 * </p>
	 *
	 * @param method the method or constructor declaration of interest
	 * @return the binding for the given method declaration, or
	 *    <code>null</code> if no binding is available
	 */
	IMethodBinding resolveMethod(MethodDeclaration method) {
		if (method == null || method.getFunction() == null) {
			throw new IllegalArgumentException("Can not resolve null expression");
		}
		
		try {
			return getMethodBinding((IMethod) sourceModule.getElementAt(method.getStart()));
			
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Returns the resolved type of the given expression. The results are NOT filtered by the
	 * File-Network.
	 * 
	 * @return the resolved type of the given expression, null if can't evaluate.
	 */
	ITypeBinding resolveExpressionType(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Can not resolve null expression");
		}
		int start = expression.getStart();
		int length = expression.getLength();
		IEvaluatedType type = getEvaluatedType(start, length);
		IModelElement[] modelElement = getModelElements(start, length, false);
		return new TypeBinding(this, type, modelElement);

	}
	
	/* (non-Javadoc) 
	 * @see BindingResolver#resolveInclude(Include)
	 */ 
	IBinding resolveInclude(Include includeDeclaration) {
		return new IncludeBinding(sourceModule, includeDeclaration);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#findDeclaringNode(org.eclipse.php.internal.core.ast.nodes.IBinding)
	 */
	@Override
	org.eclipse.php.internal.core.ast.nodes.ASTNode findDeclaringNode(IBinding binding) {
		// TODO Auto-generated method stub
		return super.findDeclaringNode(binding);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#findDeclaringNode(java.lang.String)
	 */
	@Override
	org.eclipse.php.internal.core.ast.nodes.ASTNode findDeclaringNode(String bindingKey) {
		// TODO Auto-generated method stub
		return super.findDeclaringNode(bindingKey);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#getTypeBinding(org.eclipse.php.internal.core.ast.nodes.FieldsDeclaration)
	 */
	@Override
	ITypeBinding getTypeBinding(SingleFieldDeclaration fieldDeclaration) {
		IModelElement[] modelElements;
		try {
			modelElements = this.bindingUtil.getModelElement(fieldDeclaration.getStart(), fieldDeclaration.getLength());
		} catch (ModelException e) {
			Logger.log(IStatus.ERROR, e.toString());
			return null;
		}

		if (modelElements.length > 0) {
			for (IModelElement type : modelElements) {
				if (type.getElementType() == IModelElement.TYPE) {
					return new TypeBinding(this, new PHPClassType(type.getElementName()), modelElements);
				}
			}
		}
		return super.getTypeBinding(fieldDeclaration);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#getTypeBinding(org.eclipse.dltk.ti.types.IEvaluatedType)
	 */
	@Override
	ITypeBinding getTypeBinding(IEvaluatedType referenceBinding) {
		// TODO Auto-generated method stub
		return super.getTypeBinding(referenceBinding);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveConstantExpressionValue(org.eclipse.php.internal.core.ast.nodes.Expression)
	 */
	@Override
	Object resolveConstantExpressionValue(Expression expression) {
		// TODO Auto-generated method stub
		return super.resolveConstantExpressionValue(expression);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveConstructor(org.eclipse.php.internal.core.ast.nodes.ClassInstanceCreation)
	 */
	@Override
	IMethodBinding resolveConstructor(ClassInstanceCreation expression) {
		// TODO Auto-generated method stub		
		return super.resolveConstructor(expression);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveConstructor(org.eclipse.php.internal.core.ast.nodes.MethodInvocation)
	 */
	@Override
	IMethodBinding resolveConstructor(MethodInvocation expression) {
		// TODO Auto-generated method stub
		return super.resolveConstructor(expression);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveField(org.eclipse.php.internal.core.ast.nodes.FieldAccess)
	 */
	@Override
	IVariableBinding resolveField(FieldAccess fieldAccess) {
		final VariableBase member = fieldAccess.getMember();
		if (member.getType() == ASTNode.VARIABLE) {
			Variable var = (Variable) member;
			if (!var.isDollared() && var.getName().getType() == ASTNode.IDENTIFIER) {
				Identifier id = (Identifier) var.getName();
				final String fieldName = "$" + id.getName();
				final ITypeBinding type = fieldAccess.getDispatcher().resolveTypeBinding();
				return Bindings.findFieldInHierarchy(type, fieldName);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveField(org.eclipse.php.internal.core.ast.nodes.StaticConstantAccess)
	 */
	@Override
	IVariableBinding resolveField(StaticConstantAccess constantAccess) {
		final Identifier constName = constantAccess.getConstant();
		final ITypeBinding type = constantAccess.getClassName().resolveTypeBinding();
		return Bindings.findFieldInHierarchy(type, constName.getName());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveField(org.eclipse.php.internal.core.ast.nodes.StaticFieldAccess)
	 */
	@Override
	IVariableBinding resolveField(StaticFieldAccess fieldAccess) {
		final VariableBase member = fieldAccess.getField();
		if (member.getType() == ASTNode.VARIABLE) {
			Variable var = (Variable) member;
			if (var.isDollared() && var.getName().getType() == ASTNode.IDENTIFIER) {
				Identifier id = (Identifier) var.getName();
				final String fieldName = "$" + id.getName();
				final ITypeBinding type = fieldAccess.getClassName().resolveTypeBinding();
				return Bindings.findFieldInHierarchy(type, fieldName);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveFunction(org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration)
	 */
	@Override
	IFunctionBinding resolveFunction(FunctionDeclaration function) {
		// TODO Auto-generated method stub
		return super.resolveFunction(function);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveFunction(org.eclipse.php.internal.core.ast.nodes.FunctionInvocation)
	 */
	@Override
	IFunctionBinding resolveFunction(FunctionInvocation function) {
		// TODO Auto-generated method stub
		return super.resolveFunction(function);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveMethod(org.eclipse.php.internal.core.ast.nodes.MethodInvocation)
	 */
	@Override
	IMethodBinding resolveMethod(MethodInvocation method) {
		// TODO Auto-generated method stub
		return super.resolveMethod(method);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveMethod(org.eclipse.php.internal.core.ast.nodes.StaticMethodInvocation)
	 */
	@Override
	IMethodBinding resolveMethod(StaticMethodInvocation method) {
		// TODO Auto-generated method stub
		return super.resolveMethod(method);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveType(org.eclipse.php.internal.core.ast.nodes.TypeDeclaration)
	 */
	@Override
	ITypeBinding resolveType(TypeDeclaration type) {
		// TODO Auto-generated method stub
		return super.resolveType(type);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveTypeParameter(org.eclipse.php.internal.core.ast.nodes.FormalParameter)
	 */
	@Override
	ITypeBinding resolveTypeParameter(FormalParameter typeParameter) {
		// TODO Auto-generated method stub
		return super.resolveTypeParameter(typeParameter);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveVariable(org.eclipse.php.internal.core.ast.nodes.FieldsDeclaration)
	 */
	@Override
	IVariableBinding resolveVariable(FieldsDeclaration variable) {
		// TODO Auto-generated method stub
		return super.resolveVariable(variable);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveVariable(org.eclipse.php.internal.core.ast.nodes.Variable)
	 */
	@Override
	IVariableBinding resolveVariable(Variable variable) {
		// TODO Auto-generated method stub
		return super.resolveVariable(variable);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.BindingResolver#resolveWellKnownType(java.lang.String)
	 */
	@Override
	ITypeBinding resolveWellKnownType(String name) {
		// TODO Auto-generated method stub
		return super.resolveWellKnownType(name);
	}
}
