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
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a fields declaration
 * <pre>e.g.<pre> var $a, $b;
 * public $a = 3;
 * final private static $var;
 */
public class FieldsDeclaration extends BodyDeclaration {

	private final ASTNode.NodeList<SingleFieldDeclaration> fields = new ASTNode.NodeList<SingleFieldDeclaration>(FIELDS_PROPERTY);

	/**
	 * The structural property of this node type.
	 */
	public static final ChildListPropertyDescriptor FIELDS_PROPERTY = 
		new ChildListPropertyDescriptor(FieldsDeclaration.class, "fields", SingleFieldDeclaration.class, CYCLE_RISK); //$NON-NLS-1$
	public static final SimplePropertyDescriptor MODIFIER_PROPERTY = 
		new SimplePropertyDescriptor(FieldsDeclaration.class, "modifier", Integer.class, OPTIONAL); //$NON-NLS-1$
	
	@Override
	public final SimplePropertyDescriptor getModifierProperty() {
		return MODIFIER_PROPERTY;
	}
	
	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> properyList = new ArrayList<StructuralPropertyDescriptor>(1);
		properyList.add(FIELDS_PROPERTY);
		properyList.add(MODIFIER_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(properyList);
	}
	
	public FieldsDeclaration(int start, int end, AST ast, int modifier, List variablesAndDefaults) {
		super(start, end, ast, modifier);

		if (variablesAndDefaults == null || variablesAndDefaults.size() == 0) {
			throw new IllegalArgumentException();
		}

		for (Iterator iter = variablesAndDefaults.iterator(); iter.hasNext();) {
			final Object next = iter.next();
			if (next instanceof SingleFieldDeclaration) {
				this.fields.add ((SingleFieldDeclaration) next);
			} else {
				ASTNode[] element = (ASTNode[]) next;
				SingleFieldDeclaration field = createField(ast, (Variable) element[0], (Expression) element[1]);
				this.fields.add(field);
			}
		}
	}
	
	public FieldsDeclaration(AST ast) {
		super(ast);
	}	
	
	private SingleFieldDeclaration createField(AST ast, Variable name, Expression value) {
		int start = name.getStart();
		int end = value == null ? name.getEnd() : value.getEnd();
		final SingleFieldDeclaration result = new SingleFieldDeclaration(start, end, ast, name, value);
		return result;
	}

	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		for (ASTNode node : this.fields) {
			node.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (ASTNode node : this.fields) {
			node.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (ASTNode node : this.fields) {
			node.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FieldsDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifierString()).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (SingleFieldDeclaration node : this.fields) {
			buffer.append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$
			node.getName().toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
			Expression expr = node.getValue();
			if (expr != null) {
				expr.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</FieldsDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.FIELD_DECLARATION;
	}

	/**
	 * The list of single fields that are declared
	 * 
	 * @return List of single fields
	 */
	public List<SingleFieldDeclaration> fields() {
		return this.fields;
	}
	
	public Expression[] getInitialValues() {
		Expression[] result = new Expression[this.fields.size()];
		int i = 0;
		for (SingleFieldDeclaration field : this.fields) {
			result[i++] = field.getValue();
		}
		return result;
	}

	public Variable[] getVariableNames() {
		Variable[] result = new Variable[this.fields.size()];
		int i = 0;
		for (SingleFieldDeclaration field : this.fields) {
			result[i++] = field.getName();
		}
		return result;	
	}

	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == FIELDS_PROPERTY) {
			return fields();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
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
		final List fields = ASTNode.copySubtrees(target, fields());
		final int modifier = getModifier();
		final FieldsDeclaration result = new FieldsDeclaration(getStart(), getEnd(), target, modifier, fields);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
	/**
	 * Resolves and returns the binding for this field	
	 * 
	 * @return the binding, or <code>null</code> if the binding cannot be 
	 *    resolved
	 */
	public final IVariableBinding resolveTypeBinding() {
		return this.ast.getBindingResolver().resolveVariable(this);
	}
}
