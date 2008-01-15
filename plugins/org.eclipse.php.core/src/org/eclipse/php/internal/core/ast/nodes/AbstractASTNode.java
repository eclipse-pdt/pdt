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

import org.eclipse.dltk.ast.ASTVisitor;



/**
 * Abstract superclass of all Abstract Syntax Tree (AST) node types. <p>
 * An AST node represents a PHP source code construct, such
 * as a name, type, expression, statement, or declaration.<p>
 * ASTs do not contain cycles. <p>
 *
 * @see Visitable
 * @author Modhe S., Roy G. ,2007
 * </p>
 */
public abstract class AbstractASTNode extends org.eclipse.dltk.ast.ASTNode implements ASTNode {

	private ASTNode parent;

	public AbstractASTNode(int start, int end) {
		super(start, end);
	}

	public int getLength() {
		return sourceEnd() - sourceStart();
	}

	public abstract int getType();

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		toString(buffer, ""); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * Appends the start, length parameters to the buffer
	 */
	protected void appendInterval(StringBuffer buffer) {
		buffer.append(" start='").append(sourceStart()).append("' length='").append(getLength()).append("'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Formats a given string to an XML file
	 * @param input
	 * @return String the formatted string
	 */
	protected static String getXmlStringValue(String input) {
		String escapedString = input;
		escapedString = escapedString.replaceAll("&", "&amp;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedString = escapedString.replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedString = escapedString.replaceAll("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedString = escapedString.replaceAll("'", "&apos;"); //$NON-NLS-1$ //$NON-NLS-2$
		return escapedString;
	}

	public ASTNode getParent() {
		return parent;
	}

	public void setParent(ASTNode parent) {
		this.parent = parent;
	}

	public final void traverse(ASTVisitor visitor) throws Exception {
		// We don't implement it right now, since we are not going to use ASTVisitor from DLTK
	}
}
