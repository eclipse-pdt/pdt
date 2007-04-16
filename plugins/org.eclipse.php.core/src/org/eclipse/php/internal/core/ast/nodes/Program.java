/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import java.util.*;

import org.eclipse.php.internal.core.ast.locator.Locator;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * The AST root node for PHP program (meaning a PHP file).
 * The program holds array of statements such as Class, Function and evaluation statement.
 * The program also holds the PHP file comments.
 * 
 * @author Moshe S. & Roy G. 2007
 */
public class Program extends ASTNode {

	/**
	 * Statements array of php program
	 */
	private final Statement[] statements;

	/**
	 * Map of <Integer, Comment>
	 */
	private final Map comments;

	private Program(int start, int end, Statement[] statements, final Map comments) {
		super(start, end);

		assert statements != null && comments != null;
		this.statements = statements;
		this.comments = comments;

		for (int i = 0; i < statements.length; i++) {
			statements[i].setParent(this);
		}
		for (Iterator iter = getComments().iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.setParent(this);
		}
	}

	public Program(int start, int end, List statements, List commentList) {
		this(start, end, (Statement[]) statements.toArray(new Statement[statements.size()]), createCommentsMap(commentList));
	}

	/**
	 * @return the program comments
	 */
	public Collection getComments() {
		return comments.values();
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		for (int i = 0; i < statements.length; i++) {
			statements[i].accept(visitor);
		}
		for (Iterator iter = getComments().iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.accept(visitor);
		}
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		for (int i = 0; i < statements.length; i++) {
			statements[i].traverseTopDown(visitor);
		}
		for (Iterator iter = getComments().iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.traverseTopDown(visitor);
		}
	}

	public void traverseBottomUp(Visitor visitor) {
		for (int i = 0; i < statements.length; i++) {
			statements[i].traverseBottomUp(visitor);
		}
		for (Iterator iter = getComments().iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.traverseBottomUp(visitor);
		}
		accept(visitor);
	}

	/**
	 * create program node in XML format.
	 */
	public void toString(StringBuffer buffer, String tab) {
		buffer.append("<Program");
		appendInterval(buffer);
		buffer.append(">\n").append(TAB).append("<Statements>\n");
		for (int i = 0; statements != null && i < statements.length; i++) {
			statements[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n");
		}
		buffer.append(TAB).append("</Statements>\n").append(TAB).append("<Comments>\n");
		for (Iterator iter = getComments().iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.toString(buffer, TAB + TAB + tab);
			buffer.append("\n");
		}
		buffer.append(TAB).append("</Comments>\n").append("</Program>");
	}

	private static Map createCommentsMap(List commentList) {
		final Map comments = new TreeMap();
		for (Iterator iter = commentList.iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comments.put(new Integer(comment.getEnd()), comment);
		}
		return comments;
	}

	public int getType() {
		return ASTNode.PROGRAM;
	}

	public Statement[] getStatements() {
		return statements;
	}

	public ASTNode getElementAt(int offset) {
		return Locator.locateNode(this, offset);
	}
}
