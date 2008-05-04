/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.BindingUtility;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

/**
 * @author Roy, 2008
 *
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
			this.compilerNodeToASTNode = new HashMap<Integer, ASTNode>();
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
			// Cache?
			return new TypeBinding(this, new PHPClassType(type.getElementName()), type);
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
	public IEvaluatedType getEvaluatedType(int offset, int length) {
		return bindingUtil.getType(offset, length);
	}

	/**
	 * Returns an {@link IModelElement} array according to the offset and the length.
	 * <p>
	 * The default implementation of this method returns <code>null</code>.
	 * Subclasses may reimplement.
	 * </p>
	 */
	public IModelElement[] getModelElements(int offset, int length) {
		return bindingUtil.getModelElement(offset, length);
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
		return getMethodBinding(sourceModule.getMethod(method.getFunction().getFunctionName().getName()));
	}

	/**
	 * @return the resolved type of the given expression, null if can't evaluate
	 */
	@Override
	ITypeBinding resolveExpressionType(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Can not resolve null expression");
		}
		int start = expression.getStart();
		int length = expression.getLength();
		IEvaluatedType type = getEvaluatedType(start, length);
		IModelElement[] modelElement = getModelElements(start, length);
		return new TypeBinding(this, type, modelElement);
		/*
		 * TODO handle caching
			IModelElement element;
			try {
				element = this.sourceModule.getElementAt(expression.getStart());
			} catch (ModelException e) {
				// will throw an error, in next command
				element = null;
			}
			if (element == null) {
				element = this.sourceModule;
			}
			
			final String handleIdentifier = element.getHandleIdentifier() + "_" +   ;
			IBinding binding = this.bindingTables.bindingKeysToBindings.get(handleIdentifier);
			
			if (binding != null) {
				return (ITypeBinding) binding;
			}
		
			type = this.bindingUtil.getType(expression.getStart(), expression.getLength());
			binding = new TypeBinding(type);
			
			this.bindingTables.bindingKeysToBindings.put(handleIdentifier, binding);
			return (ITypeBinding) binding;
		*/
	}
}
