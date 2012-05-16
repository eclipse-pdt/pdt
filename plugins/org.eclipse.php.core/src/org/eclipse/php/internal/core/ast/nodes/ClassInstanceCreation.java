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
package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a class instantiation. This class holds the class name as an
 * expression and array of constructor parameters
 * 
 * <pre>e.g.
 * 
 * <pre>
 * new MyClass(),
 * new $a('start'),
 * new foo()(1, $a)
 */
public class ClassInstanceCreation extends VariableBase {

	private ClassName className;
	private ASTNode.NodeList<Expression> ctorParams = new ASTNode.NodeList<Expression>(
			CTOR_PARAMS_PROPERTY);
	private ChainingInstanceCall chainingInstanceCall;
	/**
	 * The structural property of this node type.
	 */
	public static final ChildPropertyDescriptor CLASSNAME_PROPERTY = new ChildPropertyDescriptor(
			ClassInstanceCreation.class,
			"className", ClassName.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor CTOR_PARAMS_PROPERTY = new ChildListPropertyDescriptor(
			ClassInstanceCreation.class,
			"ctorParams", Expression.class, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor CHAINING_INSTANCE_CALL_PROPERTY = new ChildPropertyDescriptor(
			ClassInstanceCreation.class,
			"className", ClassName.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				2);
		propertyList.add(CLASSNAME_PROPERTY);
		propertyList.add(CTOR_PARAMS_PROPERTY);
		propertyList.add(CHAINING_INSTANCE_CALL_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public ClassInstanceCreation(int start, int end, AST ast,
			ClassName className, Expression[] ctorParams) {
		super(start, end, ast);
		if (className == null || ctorParams == null) {
			throw new IllegalArgumentException();
		}

		setClassName(className);
		for (Expression expression : ctorParams) {
			this.ctorParams.add(expression);
		}
	}

	public ClassInstanceCreation(AST ast) {
		super(ast);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public ClassInstanceCreation(int start, int end, AST ast,
			ClassName className, List ctorParams) {
		this(start, end, ast, className, ctorParams == null ? null
				: (Expression[]) ctorParams.toArray(new Expression[ctorParams
						.size()]));
	}

	public void childrenAccept(Visitor visitor) {
		className.accept(visitor);
		for (ASTNode node : this.ctorParams) {
			node.accept(visitor);
		}
		// if (chainingInstanceCall != null) {
		// chainingInstanceCall.accept(visitor);
		// }
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		className.traverseTopDown(visitor);
		for (ASTNode node : this.ctorParams) {
			node.traverseTopDown(visitor);
		}
		// if (chainingInstanceCall != null) {
		// chainingInstanceCall.traverseTopDown(visitor);
		// }
	}

	public void traverseBottomUp(Visitor visitor) {
		className.traverseBottomUp(visitor);
		for (ASTNode node : this.ctorParams) {
			node.traverseBottomUp(visitor);
		}
		// if (chainingInstanceCall != null) {
		// chainingInstanceCall.traverseBottomUp(visitor);
		// }
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ClassInstanceCreation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		className.toString(buffer, TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("<ConstructorParameters>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (ASTNode node : this.ctorParams) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</ConstructorParameters>\n"); //$NON-NLS-1$
		// if (chainingInstanceCall != null) {
		// chainingInstanceCall.toString(buffer, TAB + tab);
		//			buffer.append("\n"); //$NON-NLS-1$
		// }
		buffer.append(tab).append("</ClassInstanceCreation>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CLASS_INSTANCE_CREATION;
	}

	/**
	 * Class name of this instance creation node
	 * 
	 * @return class name
	 */
	public ClassName getClassName() {
		return className;
	}

	/**
	 * Sets the class name of this instansiation.
	 * 
	 * @param classname
	 *            the new class name
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setClassName(ClassName classname) {
		if (classname == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.className;
		preReplaceChild(oldChild, classname, CLASSNAME_PROPERTY);
		this.className = classname;
		postReplaceChild(oldChild, classname, CLASSNAME_PROPERTY);
	}

	public ChainingInstanceCall getChainingInstanceCall() {
		return chainingInstanceCall;
	}

	public void setChainingInstanceCall(
			ChainingInstanceCall chainingInstanceCall) {
		this.chainingInstanceCall = chainingInstanceCall;
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == CLASSNAME_PROPERTY) {
			if (get) {
				return getClassName();
			} else {
				setClassName((ClassName) child);
				return null;
			}
		} else if (property == CHAINING_INSTANCE_CALL_PROPERTY) {
			if (get) {
				return getChainingInstanceCall();
			} else {
				setChainingInstanceCall((ChainingInstanceCall) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	public List internalGetChildListProperty(
			ChildListPropertyDescriptor property) {
		if (property == CTOR_PARAMS_PROPERTY) {
			return ctorParams();
		}
		return super.internalGetChildListProperty(property);
	}

	/**
	 * @deprecated use {@link #ctoParams()}
	 */
	public Expression[] getCtorParams() {
		return ctorParams.toArray(new Expression[this.ctorParams.size()]);
	}

	/**
	 * List of expressions that were given to the the constructor
	 * 
	 * @return list of expressions that were given to the the constructor
	 */
	public List<Expression> ctorParams() {
		return this.ctorParams;
	}

	/*
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final List params = ASTNode.copySubtrees(target, ctorParams());
		final ClassName cn = ASTNode.copySubtree(target, getClassName());
		final ClassInstanceCreation result = new ClassInstanceCreation(
				this.getStart(), this.getEnd(), target, cn, params);
		return result;
	}

	public ClassInstanceCreation cloneWithNewStart(int offset) {
		AST target = getAST();
		final List params = ASTNode.copySubtrees(target, ctorParams());
		final ClassName cn = ASTNode.copySubtree(target, getClassName());
		final ClassInstanceCreation result = new ClassInstanceCreation(offset,
				this.getEnd(), target, cn, params);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	/**
	 * Resolves and returns the binding for the constructor invoked by this
	 * expression.
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be
	 *         resolved
	 */
	public IMethodBinding resolveConstructorBinding() {
		return this.ast.getBindingResolver().resolveConstructor(this);
	}

}
