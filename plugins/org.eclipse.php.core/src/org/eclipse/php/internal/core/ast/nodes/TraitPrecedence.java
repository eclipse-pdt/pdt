package org.eclipse.php.internal.core.ast.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

public class TraitPrecedence extends Expression {

	public TraitPrecedence(int start, int end, AST ast,
			FullyQualifiedTraitMethodReference methodReference,
			List<NamespaceName> trList) {
		super(start, end, ast);
		setMethodReference(methodReference);

		if (trList != null) {
			this.trList.addAll(trList);
		}
	}

	public TraitPrecedence(AST ast) {
		super(ast);
	}

	private FullyQualifiedTraitMethodReference methodReference;
	private ASTNode.NodeList<NamespaceName> trList = new ASTNode.NodeList<NamespaceName>(
			TRAIT_REFERENCE_LIST);;

	public static final ChildPropertyDescriptor METHOD_REFERENCE = new ChildPropertyDescriptor(
			TraitPrecedence.class,
			"methodReference", FullyQualifiedTraitMethodReference.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	public static final ChildListPropertyDescriptor TRAIT_REFERENCE_LIST = new ChildListPropertyDescriptor(
			TraitPrecedence.class, "trList", NamespaceName.class, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}), or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(
				1);
		propertyList.add(METHOD_REFERENCE);
		propertyList.add(TRAIT_REFERENCE_LIST);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	public FullyQualifiedTraitMethodReference getMethodReference() {
		return methodReference;
	}

	public void setMethodReference(
			FullyQualifiedTraitMethodReference methodReference) {
		if (methodReference == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.methodReference;
		preReplaceChild(oldChild, methodReference, METHOD_REFERENCE);
		this.methodReference = methodReference;
		postReplaceChild(oldChild, methodReference, METHOD_REFERENCE);
	}

	public List<NamespaceName> getTrList() {
		return trList;
	}

	public void setTrList(List<NamespaceName> trList) {
		this.trList.clear();
		if (trList != null) {
			this.trList.addAll(trList);
		}
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}

	public void childrenAccept(Visitor visitor) {
		methodReference.accept(visitor);
		if (trList != null) {
			for (NamespaceName name : trList) {
				name.accept(visitor);
			}
		}

	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		methodReference.traverseTopDown(visitor);
		if (trList != null) {
			for (NamespaceName name : trList) {
				name.traverseTopDown(visitor);
			}
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		methodReference.traverseBottomUp(visitor);
		if (trList != null) {
			for (NamespaceName name : trList) {
				name.traverseBottomUp(visitor);
			}
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitPrecedence"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		methodReference.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		if (trList != null) {
			buffer.append(TAB).append(tab).append("<TraitReferenceList>\n"); //$NON-NLS-1$
			for (NamespaceName name : trList) {
				name.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(TAB).append(tab).append("</TraitReferenceList>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</TraitPrecedence>"); //$NON-NLS-1$
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
		FullyQualifiedTraitMethodReference methodReference = ASTNode
				.copySubtree(target, getMethodReference());
		List<NamespaceName> trList = ASTNode.copySubtrees(target, getTrList());
		final TraitPrecedence result = new TraitPrecedence(this.getStart(),
				this.getEnd(), target, methodReference, trList);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(
			PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property,
			boolean get, ASTNode child) {
		if (property == METHOD_REFERENCE) {
			if (get) {
				return getMethodReference();
			} else {
				setMethodReference((FullyQualifiedTraitMethodReference) child);
				return null;
			}
		}

		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == TRAIT_REFERENCE_LIST) {
			return getTrList();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}

}
