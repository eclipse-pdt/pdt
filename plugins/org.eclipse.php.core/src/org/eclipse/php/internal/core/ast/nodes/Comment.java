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
 * Represents a PHP comment
 * <pre>e.g.<pre> // this is a single line comment
 * # this is a single line comment
 * /** this is php doc block (end php docblock here)
 */
public class Comment extends ASTNode {

	public final static int TYPE_SINGLE_LINE = 0;
	public final static int TYPE_MULTILINE = 1;
	public final static int TYPE_PHPDOC = 2;

	private int commentType;
	
	/**
	 * The "identifier" structural property of this node type.
	 */
	public static final SimplePropertyDescriptor COMMENT_TYPE_PROPERTY = 
		new SimplePropertyDescriptor(Comment.class, "commentType", Integer.class, MANDATORY); //$NON-NLS-1$
	
	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	static {
		List<StructuralPropertyDescriptor> list = new ArrayList<StructuralPropertyDescriptor>(1);
		list.add(COMMENT_TYPE_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(list);
	}
	

	public Comment(int start, int end, AST ast, int type) {
		super(start, end, ast);
		
		setCommentType(type);
	}

	public Comment(AST ast) {
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
	}

	public void traverseBottomUp(Visitor visitor) {
	}

	public void traverseTopDown(Visitor visitor) {
	}

	public static String getCommentType(int type) {
		switch (type) {
			case TYPE_SINGLE_LINE:
				return "singleLine"; //$NON-NLS-1$
			case TYPE_MULTILINE:
				return "multiLine"; //$NON-NLS-1$
			case TYPE_PHPDOC:
				return "phpDoc"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Comment"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" commentType='").append(getCommentType(commentType)).append("'/>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public int getType() {
		return ASTNode.COMMENT;
	}

	public int getCommentType() {
		return commentType;
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
		final Comment result = new Comment(this.getStart(), this.getEnd(), target, this.getCommentType());
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	final int internalGetSetIntProperty(SimplePropertyDescriptor property, boolean get, int value) {
		if (property == COMMENT_TYPE_PROPERTY) {
			if (get) {
				return getCommentType();
			} else {
				setCommentType((Integer) value);
				return 0;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetIntProperty(property, get, value);
	}

	public final void setCommentType(int value) {
		if (value != TYPE_MULTILINE && value != TYPE_PHPDOC && value != TYPE_SINGLE_LINE) {
			throw new IllegalArgumentException();
		}
		
		preValueChange(COMMENT_TYPE_PROPERTY);
		this.commentType = value;
		postValueChange(COMMENT_TYPE_PROPERTY);
	}
	
}
