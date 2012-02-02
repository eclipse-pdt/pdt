package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;
import org.eclipse.php.internal.core.compiler.ast.nodes.ASTNodeKinds;

public class ChainingInstanceCall extends Expression {

	private PHPArrayDereferenceList arrayDereferenceList;
	// private ASTNode.NodeList<VariableBase> chainingMethodOrProperty = new
	// ASTNode.NodeList<VariableBase>(
	// CHAINING_METHOD_OR_PROPERTY);
	private List<VariableBase> chainingMethodOrProperty = new LinkedList<VariableBase>();

	public static final ChildPropertyDescriptor ARRAY_DEREFERENCE_LIST = new ChildPropertyDescriptor(
			ChainingInstanceCall.class,
			"arrayDereferenceList", PHPArrayDereferenceList.class, OPTIONAL, CYCLE_RISK); //$NON-NLS-1$
	// public static final ChildListPropertyDescriptor
	// CHAINING_METHOD_OR_PROPERTY = new ChildListPropertyDescriptor(
	// ChainingInstanceCall.class,
	//			"chainingMethodOrProperty", VariableBase.class, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				1);
		propertyList.add(ARRAY_DEREFERENCE_LIST);
		// propertyList.add(CHAINING_METHOD_OR_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public ChainingInstanceCall(int start, int end, AST ast,
			PHPArrayDereferenceList arrayDereferenceList,
			List<VariableBase> chainingMethodOrProperty) {
		super(start, end, ast);

		this.arrayDereferenceList = arrayDereferenceList;
		if (chainingMethodOrProperty != null) {
			this.chainingMethodOrProperty.addAll(chainingMethodOrProperty);
		}
	}

	public ChainingInstanceCall(AST ast,
			PHPArrayDereferenceList arrayDereferenceList,
			List<VariableBase> chainingMethodOrProperty) {
		super(ast);

		setArrayDereferenceList(arrayDereferenceList);
		if (chainingMethodOrProperty != null) {
			this.chainingMethodOrProperty.addAll(chainingMethodOrProperty);
		}
	}

	public List<VariableBase> getChainingMethodOrProperty() {
		return chainingMethodOrProperty;
	}

	public void setChainingMethodOrProperty(
			ASTNode.NodeList<VariableBase> chainingMethodOrProperty) {
		this.chainingMethodOrProperty = chainingMethodOrProperty;
	}

	public void setArrayDereferenceList(
			PHPArrayDereferenceList arrayDereferenceList) {
		ASTNode oldChild = this.arrayDereferenceList;
		preReplaceChild(oldChild, arrayDereferenceList, ARRAY_DEREFERENCE_LIST);
		this.arrayDereferenceList = arrayDereferenceList;
		postReplaceChild(oldChild, arrayDereferenceList, ARRAY_DEREFERENCE_LIST);
	}

	public PHPArrayDereferenceList getArrayDereferenceList() {
		return arrayDereferenceList;
	}

	public int getKind() {
		return ASTNodeKinds.USE_STATEMENT;
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		if (arrayDereferenceList != null) {

			arrayDereferenceList.accept(visitor);
		}
		if (chainingMethodOrProperty != null) {
			for (VariableBase variableBase : chainingMethodOrProperty) {
				variableBase.accept(visitor);
			}
		}

	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		if (arrayDereferenceList != null) {

			arrayDereferenceList.traverseTopDown(visitor);
		}
		if (chainingMethodOrProperty != null) {
			for (VariableBase variableBase : chainingMethodOrProperty) {
				variableBase.traverseTopDown(visitor);
			}
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		if (arrayDereferenceList != null) {

			arrayDereferenceList.traverseBottomUp(visitor);
		}
		if (chainingMethodOrProperty != null) {
			for (VariableBase variableBase : chainingMethodOrProperty) {
				variableBase.traverseBottomUp(visitor);
			}
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ChainingInstanceCall"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		//		buffer.append(TAB).append(tab).append("<arrayDereferenceList>\n"); //$NON-NLS-1$
		if (arrayDereferenceList != null) {
			buffer.append(TAB).append(tab)
					.append("<PHPArrayDereferenceList>\n"); //$NON-NLS-1$
			arrayDereferenceList.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(TAB).append(tab).append("</PHPArrayDereferenceList>"); //$NON-NLS-1$
		}
		if (chainingMethodOrProperty != null) {
			buffer.append(TAB).append(tab)
					.append("<ChainingMethodOrProperty>\n"); //$NON-NLS-1$
			for (VariableBase variableBase : chainingMethodOrProperty) {
				variableBase.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(TAB).append(tab)
					.append("</ChainingMethodOrProperty>"); //$NON-NLS-1$
		}
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ChainingInstanceCall>"); //$NON-NLS-1$
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
		PHPArrayDereferenceList arrayDereferenceList = ASTNode.copySubtree(
				target, getArrayDereferenceList());

		final List<VariableBase> chainingMethodOrProperty = ASTNode
				.copySubtrees(target, getChainingMethodOrProperty());
		final ChainingInstanceCall result = new ChainingInstanceCall(
				this.getStart(), this.getEnd(), target, arrayDereferenceList,
				chainingMethodOrProperty);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	/*
	 * (omit javadoc for this method) Method declared on ASTNode.
	 */
	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		// if (property == CHAINING_METHOD_OR_PROPERTY) {
		// return getChainingMethodOrProperty();
		// }
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == ARRAY_DEREFERENCE_LIST) {
			if (get) {
				return getArrayDereferenceList();
			} else {
				setArrayDereferenceList((PHPArrayDereferenceList) child);
				return null;
			}
		}

		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

}
