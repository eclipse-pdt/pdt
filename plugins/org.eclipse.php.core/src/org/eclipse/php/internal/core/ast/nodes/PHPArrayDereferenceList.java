package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

public class PHPArrayDereferenceList extends Expression {

	private final ASTNode.NodeList<DereferenceNode> dereferences = new ASTNode.NodeList<DereferenceNode>(
			DEREFERENCES_PROPERTY);

	public static final ChildListPropertyDescriptor DEREFERENCES_PROPERTY = new ChildListPropertyDescriptor(
			PHPArrayDereferenceList.class,
			"dereferences", DereferenceNode.class, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				1);
		propertyList.add(DEREFERENCES_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public PHPArrayDereferenceList(AST ast) {
		super(ast);
	}

	public PHPArrayDereferenceList(AST ast, List<DereferenceNode> dereferences) {
		super(ast);
		if (dereferences != null) {
			this.dereferences.addAll(dereferences);
		}
	}

	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : dereferences) {
			node.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		for (ASTNode node : dereferences) {
			node.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : dereferences) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<PHPArrayDereferenceList"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : dereferences) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</PHPArrayDereferenceList>"); //$NON-NLS-1$
	}

	@Override
	void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	@Override
	ASTNode clone0(AST target) {
		final List<DereferenceNode> dereferences = ASTNode.copySubtrees(target,
				getDereferences());
		final PHPArrayDereferenceList result = new PHPArrayDereferenceList(
				target, dereferences);

		return result;
	}

	public List<DereferenceNode> getDereferences() {
		return dereferences;
	}

	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == DEREFERENCES_PROPERTY) {
			return getDereferences();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

}
