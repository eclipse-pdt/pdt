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
package org.eclipse.php.ui.text.correction;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;

/**
 * Problem information for quick fix and quick assist processors.
 * <p>
 * Note: this interface is not intended to be implemented.
 * </p>
 * 
 * @since 3.0
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IProblemLocation {

	/**
	 * Returns the start offset of the problem.
	 * 
	 * @return the start offset of the problem
	 */
	int getOffset();

	/**
	 * Returns the length of the problem.
	 * 
	 * @return the length of the problem
	 */
	int getLength();

	/**
	 * Returns the marker type of this problem.
	 * 
	 * @return The marker type of the problem.
	 * @since 3.2
	 */
	String getMarkerType();

	IProblemIdentifier getProblemIdentifier();

	/**
	 * Returns the original arguments recorded into the problem.
	 * 
	 * @return String[] Returns the problem arguments.
	 */
	String[] getProblemArguments();

	/**
	 * Returns if the problem has error severity.
	 * 
	 * @return <code>true</code> if the problem has error severity
	 */
	boolean isError();

	/**
	 * Convenience method to evaluate the AST node covering this problem.
	 * 
	 * @param astRoot
	 *            The root node of the current AST
	 * @return Returns the node that covers the location of the problem
	 */
	ASTNode getCoveringNode(Program astRoot);

	/**
	 * Convenience method to evaluate the AST node covered by this problem.
	 * 
	 * @param astRoot
	 *            The root node of the current AST
	 * @return Returns the node that is covered by the location of the problem
	 */
	ASTNode getCoveredNode(Program astRoot);

}
