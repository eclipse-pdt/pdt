package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

public class TraitAlias extends Expression {

	public TraitAlias(int start, int end, AST ast, Expression traitMethod,
			int modifier, String functionName) {
		super(start, end, ast);
		this.traitMethod = traitMethod;
		this.modifier = modifier;
		this.functionName = functionName;
	}

	public TraitAlias(AST ast) {
		super(ast);
	}

	private Expression traitMethod;
	private int modifier;

	/**
	 * functionName could be null
	 */
	private String functionName;

	public static final ChildPropertyDescriptor TRAIT_METHOD = new ChildPropertyDescriptor(
			TraitAlias.class,
			"traitMethod", Expression.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor MODIFIER = new SimplePropertyDescriptor(
			TraitAlias.class, "modifier", Integer.class, OPTIONAL); //$NON-NLS-1$
	public static final SimplePropertyDescriptor FUNCTION_NAME = new SimplePropertyDescriptor(
			TraitAlias.class, "functionName", String.class, OPTIONAL); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				1);
		propertyList.add(TRAIT_METHOD);
		propertyList.add(MODIFIER);
		propertyList.add(FUNCTION_NAME);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public Expression getTraitMethod() {
		return traitMethod;
	}

	public void setTraitMethod(Expression traitMethod) {
		if (traitMethod == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.traitMethod;
		preReplaceChild(oldChild, traitMethod, TRAIT_METHOD);
		this.traitMethod = traitMethod;
		postReplaceChild(oldChild, traitMethod, TRAIT_METHOD);
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		preValueChange(MODIFIER);
		this.modifier = modifier;
		postValueChange(MODIFIER);
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		preValueChange(FUNCTION_NAME);
		this.functionName = functionName;
		postValueChange(FUNCTION_NAME);

	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		traitMethod.accept(visitor);

	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		traitMethod.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		traitMethod.traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitAlias"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		traitMethod.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</TraitAlias>"); //$NON-NLS-1$
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
	ASTNode clone0(AST target) {
		Expression traitMethod = ASTNode.copySubtree(target, getTraitMethod());
		final TraitAlias result = new TraitAlias(this.getStart(),
				this.getEnd(), target, traitMethod, modifier, functionName);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

}
