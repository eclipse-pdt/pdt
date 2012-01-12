package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

public class DereferenceNode extends ASTNode {

	private Expression name;

	/**
	 * The "expression" structural property of this node type.
	 */
	public static final ChildPropertyDescriptor NAME_PROPERTY = new ChildPropertyDescriptor(
			DereferenceNode.class,
			"name", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				1);
		propertyList.add(NAME_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public DereferenceNode(int start, int end, AST ast, Expression indexName) {
		super(start, end, ast);

		if (indexName == null) {
			throw new IllegalArgumentException();
		}

		setName(indexName);
	}

	public DereferenceNode(AST ast) {
		super(ast);
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		name.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		name.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		name.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<DereferenceNode"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		name.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</DereferenceNode>"); //$NON-NLS-1$
	}

	public int getType() {
		return 0;
	}

	/**
	 * Returns the name expression of this index name.
	 * 
	 * @return the expression node
	 */
	public Expression getName() {
		return this.name;
	}

	/**
	 * Sets the expression of this index name expression.
	 * 
	 * @param expression
	 *            the new expression node
	 * @exception IllegalArgumentException
	 *                if:
	 *                <ul>
	 *                <li>the node belongs to a different AST</li>
	 *                <li>the node already has a parent</li>
	 *                <li>a cycle in would be created</li>
	 *                </ul>
	 */
	public void setName(Expression expression) {
		if (expression == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.name;
		preReplaceChild(oldChild, expression, NAME_PROPERTY);
		this.name = expression;
		postReplaceChild(oldChild, expression, NAME_PROPERTY);
	}

	// final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor
	// property,
	// boolean get, ASTNode child) {
	// if (property == NAME_PROPERTY) {
	// if (get) {
	// return getName();
	// } else {
	// setName((Expression) child);
	// return null;
	// }
	// }
	// // allow default implementation to flag the error
	// return super.internalGetSetChildProperty(property, get, child);
	// }

	/*
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final Expression expr = ASTNode.copySubtree(target, getName());
		final DereferenceNode result = new DereferenceNode(this.getStart(),
				this.getEnd(), target, expr);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == NAME_PROPERTY) {
			if (get) {
				return getName();
			} else {
				setName((Expression) child);
				return null;
			}
		}

		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

}
