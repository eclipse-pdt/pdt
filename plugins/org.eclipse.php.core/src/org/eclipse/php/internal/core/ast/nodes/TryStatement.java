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
 * Represents the try statement 
 * <pre>e.g.<pre> 
 * try { 
 *   statements...
 * } catch (Exception $e) { 
 *   statements...
 * } catch (AnotherException $ae) { 
 *   statements...
 * }
 */
public class TryStatement extends Statement {

	private Block tryStatement;
	private ASTNode.NodeList<CatchClause> catchClauses = new ASTNode.NodeList<CatchClause>(CATCH_CLAUSES_PROPERTY);
	
	/**
	 * The structural property of this node type.
	 * @since 3.0
	 */
	public static final ChildPropertyDescriptor BODY_PROPERTY = 
		new ChildPropertyDescriptor(TryStatement.class, "tryStatement", Block.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor CATCH_CLAUSES_PROPERTY = 
		new ChildListPropertyDescriptor(TryStatement.class, "catchClauses", CatchClause.class, CYCLE_RISK); //$NON-NLS-1$

	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(2);
		propertyList.add(BODY_PROPERTY);
		propertyList.add(CATCH_CLAUSES_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}

	/**
	 * Returns a list of structural property descriptors for this node type.
	 * Clients must not modify the result.
	 * 
	 * @param apiLevel the API level; one of the
	 * <code>AST.JLS*</code> constants
	 * @return a list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor})
	 * @since 3.0
	 */
	public static List<StructuralPropertyDescriptor> propertyDescriptors(int apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
	private TryStatement(int start, int end, AST ast, Block tryStatement, CatchClause[] catchClauses) {
		super(start, end, ast);

		if (tryStatement == null || catchClauses == null) {
			throw new IllegalArgumentException();
		}
		setBody(tryStatement);
		for (int i = 0; i < catchClauses.length; i++) {
			this.catchClauses.add(catchClauses[i]);
		}
	}

	public TryStatement(int start, int end, AST ast, Block tryStatement, List catchClauses) {
		this(start, end, ast, tryStatement, catchClauses == null ? null : (CatchClause[]) catchClauses.toArray(new CatchClause[catchClauses.size()]));
	}

	public TryStatement(AST ast) {
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
		tryStatement.accept(visitor);
		for(ASTNode catchClause : this.catchClauses) {
			catchClause.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		tryStatement.traverseTopDown(visitor);
		for(ASTNode catchClause : this.catchClauses) {
			catchClause.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		tryStatement.traverseBottomUp(visitor);
		for(ASTNode catchClause : this.catchClauses) {
			catchClause.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TryStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		tryStatement.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		for(ASTNode catchClause : this.catchClauses) {
			catchClause.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</TryStatement>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.TRY_STATEMENT;
	}

	/**
	 * @deprecated use {@link #catchClauses()}
	 */
	public CatchClause[] getCatchClauses() {
		return (CatchClause[]) catchClauses.toArray(new CatchClause[catchClauses.size()]);
	}

	/**
	 * @deprecated use {@link #getBody()}
	 */
	public Block getTryStatement() {
		return tryStatement;
	}

	/**
	 * Returns the body of this try statement.
	 * 
	 * @return the try body
	 */ 
	public Block getBody() {
		return this.tryStatement;
	}
	
	/**
	 * Sets the body of this try statement.
	 * 
	 * @param body the block node
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * <li>a cycle in would be created</li>
	 * </ul>
	 */ 
	public void setBody(Block body) {
		if (body == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.tryStatement;
		preReplaceChild(oldChild, body, BODY_PROPERTY);
		this.tryStatement = body;
		postReplaceChild(oldChild, body, BODY_PROPERTY);
	}

	/**
	 * Returns the live ordered list of catch clauses for this try statement.
	 * 
	 * @return the live list of catch clauses
	 *    (element type: <code>CatchClause</code>)
	 */ 
	public List<CatchClause> catchClauses() {
		return this.catchClauses;
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
		Block body = ASTNode.copySubtree(target, getBody());
		final List catchs = ASTNode.copySubtrees(target, this.catchClauses);
		final TryStatement result = new TryStatement(this.getStart(), this.getEnd(), target, body, catchs);
		return result;
	}


	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
	
	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == BODY_PROPERTY) {
			if (get) {
				return getBody();
			} else {
				setBody((Block) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}
	
	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	final List internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == CATCH_CLAUSES_PROPERTY) {
			return catchClauses();
		}
		// allow default implementation to flag the error
		return super.internalGetChildListProperty(property);
	}	
}
