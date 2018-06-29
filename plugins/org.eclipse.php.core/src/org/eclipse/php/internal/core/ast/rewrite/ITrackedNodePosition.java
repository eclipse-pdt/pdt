/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.rewrite;

/**
 * A tracked node position is returned when a rewrite change is requested to be
 * tracked.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * 
 * @see ASTRewrite#track(org.eclipse.jdt.core.dom.ASTNode)
 * @since 3.0
 */
public interface ITrackedNodePosition {

	/**
	 * Returns the original or modified start position of the tracked node depending
	 * if called before or after the rewrite is applied. <code>-1</code> is returned
	 * for removed nodes.
	 * 
	 * @return the original or modified start position of the tracked node
	 */
	public int getStartPosition();

	/**
	 * Returns the original or modified length of the tracked node depending if
	 * called before or after the rewrite is applied. <code>-1</code> is returned
	 * for removed nodes.
	 * 
	 * @return the original or modified length of the tracked node
	 */
	public int getLength();

}
