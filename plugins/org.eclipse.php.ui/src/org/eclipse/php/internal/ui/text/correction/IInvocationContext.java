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
package org.eclipse.php.internal.ui.text.correction;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;

/**
 * Context information for quick fix and quick assist processors.
 * <p>
 * Note: this interface is not intended to be implemented.
 * </p>
 * 
 * @since 3.0
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IInvocationContext {

	/**
	 * @return Returns the current compilation unit.
	 */
	ISourceModule getCompilationUnit();

	/**
	 * @return Returns the offset of the current selection
	 */
	int getSelectionOffset();

	/**
	 * @return Returns the length of the current selection
	 */
	int getSelectionLength();

	/**
	 * Returns an AST of the compilation unit, possibly only a partial AST
	 * focused on the selection offset (see
	 * {@link org.eclipse.jdt.core.dom.ASTParser#setFocalPosition(int)}). The
	 * returned AST is shared and therefore protected and cannot be modified.
	 * The client must check the AST API level and do nothing if they are given
	 * an AST they can't handle. (see
	 * {@link org.eclipse.jdt.core.dom.AST#apiLevel()}).
	 * 
	 * @return Returns the root of the AST corresponding to the current
	 *         compilation unit.
	 */
	Program getASTRoot();

	/**
	 * Convenience method to evaluate the AST node covering the current
	 * selection.
	 * 
	 * @return Returns the node that covers the location of the problem
	 */
	ASTNode getCoveringNode();

	/**
	 * Convenience method to evaluate the AST node that is covered by the
	 * current selection.
	 * 
	 * @return Returns the node that is covered by the location of the problem
	 */
	ASTNode getCoveredNode();

}
