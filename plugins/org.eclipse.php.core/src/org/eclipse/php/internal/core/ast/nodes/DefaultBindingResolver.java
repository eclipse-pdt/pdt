/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.BindingUtility;

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
	}

	/**
	 * @return the resolved type of the given expression, null if can't evaluate
	 */
	@Override
	ITypeBinding resolveExpressionType(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Can not resolve null expression");
		}
		
		IEvaluatedType type = this.bindingUtil.getType(expression.getStart(), expression.getLength());
		return new TypeBinding(type);

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
