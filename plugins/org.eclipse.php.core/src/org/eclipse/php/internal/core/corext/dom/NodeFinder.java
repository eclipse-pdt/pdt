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
package org.eclipse.php.internal.core.corext.dom;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;
import org.eclipse.php.internal.core.corext.ISourceRange;

/**
 * For a give range finds the node covered and the node covering.
 * 
 * @since 2.1
 */
public class NodeFinder extends ApplyAll {

	/**
	 * A visitor that maps a selection to a given ASTNode. The result node is
	 * determined as follows:
	 * <ul>
	 * <li>first the visitor tries to find a node with the exact start and
	 * length</li>
	 * <li>if no such node exists than the node that encloses the range defined
	 * by start and end is returned.</li>
	 * <li>if the length is zero than also nodes are considered where the node's
	 * start or end position matches <code>start</code>.</li>
	 * <li>otherwise <code>null</code> is returned.</li>
	 * </ul>
	 * 
	 * @param root
	 *            the root node from which the search starts
	 * @param start
	 *            the start offset
	 * @param length
	 *            the length
	 * 
	 * @return the result node
	 * 
	 * @since 2.1
	 */
	public static ASTNode perform(ASTNode root, int start, int length) {
		NodeFinder finder = new NodeFinder(start, length);
		root.accept(finder);
		ASTNode result = finder.getCoveredNode();
		if (result == null || result.getStart() != start
				|| result.getLength() != length) {
			return finder.getCoveringNode();
		}
		return result;
	}

	public static ASTNode perform(ASTNode root, ISourceRange range) {
		return perform(root, range.getOffset(), range.getLength());
	}

	/**
	 * A visitor that maps a selection to a given ASTNode. The result node is
	 * determined as follows:
	 * <ul>
	 * <li>first the visitor tries to find a node that is covered by
	 * <code>start</code> and <code>length</code> where either
	 * <code>start</code> and <code>length</code> exactly matches the node or
	 * where the text covered before and after the node only consists of white
	 * spaces or comments.</li>
	 * <li>if no such node exists than the node that encloses the range defined
	 * by start and end is returned.</li>
	 * <li>if the length is zero than also nodes are considered where the node's
	 * start or end position matches <code>start</code>.</li>
	 * <li>otherwise <code>null</code> is returned.</li>
	 * </ul>
	 * 
	 * @param root
	 *            the root node from which the search starts
	 * @param start
	 *            the start offset
	 * @param length
	 *            the length
	 * @param source
	 *            the source of the compilation unit
	 * 
	 * @return the result node
	 * @throws JavaModelException
	 *             if an error occurs in the Java model
	 * 
	 * @since 3.0
	 */
	/*
	 * public static ASTNode perform(ASTNode root, int start, int length,
	 * ITypeRoot source) throws JavaModelException { NodeFinder finder= new
	 * NodeFinder(start, length); root.accept(finder); ASTNode result=
	 * finder.getCoveredNode(); if (result == null) return null; Selection
	 * selection= Selection.createFromStartLength(start, length); if
	 * (selection.covers(result)) { IBuffer buffer= source.getBuffer(); if
	 * (buffer != null) { IScanner scanner= ToolFactory.createScanner(false,
	 * false, false, false); scanner.setSource(buffer.getText(start,
	 * length).toCharArray()); try { int token= scanner.getNextToken(); if
	 * (token != ITerminalSymbols.TokenNameEOF) { int tStart=
	 * scanner.getCurrentTokenStartPosition(); if (tStart ==
	 * result.getStartPosition() - start) { scanner.resetTo(tStart +
	 * result.getLength(), length - 1); token= scanner.getNextToken(); if (token
	 * == ITerminalSymbols.TokenNameEOF) return result; } } } catch
	 * (InvalidInputException e) { } } } return finder.getCoveringNode(); }
	 */

	private int fStart;
	private int fEnd;

	private ASTNode fCoveringNode;
	private ASTNode fCoveredNode;

	public NodeFinder(int offset, int length) {
		fStart = offset;
		fEnd = offset + length;
	}

	protected boolean apply(ASTNode node) {
		int nodeStart = node.getStart();
		int nodeEnd = nodeStart + node.getLength();
		if (nodeEnd < fStart || fEnd < nodeStart) {
			return false;
		}
		if (nodeStart <= fStart && fEnd <= nodeEnd) {
			fCoveringNode = node;
		}
		if (fStart <= nodeStart && nodeEnd <= fEnd) {
			if (fCoveringNode == node) { // nodeStart == fStart && nodeEnd ==
											// fEnd
				fCoveredNode = node;
				return true; // look further for node with same length as parent
			} else if (fCoveredNode == null) { // no better found
				fCoveredNode = node;
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns the covered node. If more than one nodes are covered by the
	 * selection, the returned node is first covered node found in a top-down
	 * traversal of the AST
	 * 
	 * @return ASTNode
	 */
	public ASTNode getCoveredNode() {
		return fCoveredNode;
	}

	/**
	 * Returns the covering node. If more than one nodes are covering the
	 * selection, the returned node is last covering node found in a top-down
	 * traversal of the AST
	 * 
	 * @return ASTNode
	 */
	public ASTNode getCoveringNode() {
		return fCoveringNode;
	}

}
