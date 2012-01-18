package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

public abstract class TraitStatement extends Statement {

	private Expression exp;

	public static final ChildPropertyDescriptor EXP = new ChildPropertyDescriptor(
			TraitStatement.class,
			"exp", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				1);
		propertyList.add(EXP);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public TraitStatement(int start, int end, AST ast, Expression exp) {
		super(start, end, ast);
		setExp(exp);
	}

	public TraitStatement(AST ast) {
		super(ast);
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		if (exp == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.exp;
		preReplaceChild(oldChild, exp, EXP);
		this.exp = exp;
		postReplaceChild(oldChild, exp, EXP);

	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		exp.accept(visitor);

	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		exp.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		exp.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		exp.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</TraitStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FUNCTION_NAME;
	}

	/*
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == EXP) {
			if (get) {
				return getExp();
			} else {
				setExp((Expression) child);
				return null;
			}
		}

		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

}
