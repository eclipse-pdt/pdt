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

import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.ast.locator.Locator;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
import org.eclipse.php.internal.core.ast.parser.ASTParser;
import org.eclipse.php.internal.core.ast.parser.AstLexer;
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
	
	/**
	 * The comment mapper, or <code>null</code> if none; 
	 * initially <code>null</code>.
	 * @since 3.0
	 */
	private DefaultCommentMapper commentMapper = null;

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
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
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
		buffer.append("<Program"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n").append(TAB).append("<Statements>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (int i = 0; statements != null && i < statements.length; i++) {
			statements[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append("</Statements>\n").append(TAB).append("<Comments>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (Iterator iter = getComments().iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append("</Comments>\n").append("</Program>"); //$NON-NLS-1$ //$NON-NLS-2$
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
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	/**
	 * Initializes the internal comment mapper with the given
	 * scanner.
	 * 
	 * @param scanner the scanner
	 * @since 3.0
	 */
	public void initCommentMapper(IDocument document, AstLexer scanner) {
		this.commentMapper = new DefaultCommentMapper((Comment[]) this.getComments().toArray(new Comment[this.getComments().size()]));
		this.commentMapper.initialize(this, scanner, document);
	}
	
	/**
	 * Returns a list of the comments encountered while parsing
	 * this compilation unit.
	 * <p>
	 * Since the Java language allows comments to appear most anywhere
	 * in the source text, it is problematic to locate comments in relation
	 * to the structure of an AST. The one exception is doc comments 
	 * which, by convention, immediately precede type, field, and
	 * method declarations; these comments are located in the AST
	 * by {@link  BodyDeclaration#getJavadoc BodyDeclaration.getJavadoc}.
	 * Other comments do not show up in the AST. The table of comments
	 * is provided for clients that need to find the source ranges of
	 * all comments in the original source string. It includes entries
	 * for comments of all kinds (line, block, and doc), arranged in order
	 * of increasing source position. 
	 * </p>
	 * <p>
	 * Note on comment parenting: The {@link ASTNode#getParent() getParent()}
	 * of a doc comment associated with a body declaration is the body
	 * declaration node; for these comment nodes
	 * {@link ASTNode#getRoot() getRoot()} will return the compilation unit
	 * (assuming an unmodified AST) reflecting the fact that these nodes
	 * are property located in the AST for the compilation unit.
	 * However, for other comment nodes, {@link ASTNode#getParent() getParent()}
	 * will return <code>null</code>, and {@link ASTNode#getRoot() getRoot()}
	 * will return the comment node itself, indicating that these comment nodes
	 * are not directly connected to the AST for the compilation unit. The 
	 * {@link Comment#getAlternateRoot Comment.getAlternateRoot}
	 * method provides a way to navigate from a comment to its compilation
	 * unit.
	 * </p>
	 * <p>
	 * A note on visitors: The only comment nodes that will be visited when
	 * visiting a compilation unit are the doc comments parented by body
	 * declarations. To visit all comments in normal reading order, iterate
	 * over the comment table and call {@link ASTNode#accept(ASTVisitor) accept}
	 * on each element.
	 * </p>
	 * <p>
	 * Clients cannot modify the resulting list.
	 * </p>
	 * 
	 * @return an unmodifiable list of comments in increasing order of source
	 * start position, or <code>null</code> if comment information
	 * for this compilation unit is not available
	 * @see ASTParser
	 * @since 3.0
	 */
	public Collection<ASTNode> getCommentList() {
		return this.comments.values();
	}

	/**
	 * Returns the internal comment mapper.
	 * 
	 * @return the comment mapper, or <code>null</code> if none.
	 * @since 3.0
	 */
	DefaultCommentMapper getCommentMapper() {
		return this.commentMapper;
	}

	/**
	 * Returns the extended source length of the given node. Unlike
	 * {@link ASTNode#getStartPosition()} and {@link ASTNode#getLength()},
	 * the extended source range may include comments and whitespace
	 * immediately before or after the normal source range for the node.
	 * 
	 * @param node the node
	 * @return a (possibly 0) length, or <code>0</code>
	 *    if no source position information is recorded for this node
	 * @see #getExtendedStartPosition(ASTNode)
	 * @since 3.0
	 */
	public int getExtendedLength(ASTNode node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		if (this.commentMapper == null /*|| node.getAST() != getAST()*/) {
			// fall back: use best info available
			return node.getLength();
		} else {
			return this.commentMapper.getExtendedLength(node);
		}
	}

	/**
	 * Returns the extended start position of the given node. Unlike
	 * {@link ASTNode#getStartPosition()} and {@link ASTNode#getLength()},
	 * the extended source range may include comments and whitespace
	 * immediately before or after the normal source range for the node.
	 * 
	 * @param node the node
	 * @return the 0-based character index, or <code>-1</code>
	 *    if no source position information is recorded for this node
	 * @see #getExtendedLength(ASTNode)
	 * @since 3.0
	 */
	public int getExtendedStartPosition(ASTNode node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		if (this.commentMapper == null /*|| node.getAST() != getAST()*/) {
			// fall back: use best info available
			return node.getStart();
		} else {
			return this.commentMapper.getExtendedStartPosition(node);
		}
	}	
	
	
	/**
	 * Return the index in the whole comments list {@link #getCommentList() }
	 * of the first leading comments associated with the given node. 
	 * 
	 * @param node the node
	 * @return 0-based index of first leading comment or -1 if node has no associated
	 * 	comment before its start position.
	 * @since 3.2
	 */
	public int firstLeadingCommentIndex(ASTNode node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		if (this.commentMapper == null /*|| node.getAST() != getAST()*/) {
			return -1;
		}
		return this.commentMapper.firstLeadingCommentIndex(node);
	}

	/**
	 * Return the index in the whole comments list {@link #getCommentList() }
	 * of the last trailing comments associated with the given node. 
	 * 
	 * @param node the node
	 * @return 0-based index of last trailing comment or -1 if node has no
	 * 	associated comment after its end position.
	 * @since 3.2
	 */
	public int lastTrailingCommentIndex(ASTNode node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		if (this.commentMapper == null /*|| node.getAST() != getAST()*/) {
			return -1;
		}
		return this.commentMapper.lastTrailingCommentIndex(node);
	}	
	
}
