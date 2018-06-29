/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

/**
 * A Web module deployed on PHP built-in server.
 */
public interface IPHPWebModule {

	/**
	 * Get the document base.
	 *
	 * @return java.lang.String
	 */
	public String getDocumentBase();

	/**
	 * Return the path. (context root)
	 *
	 * @return java.lang.String
	 */
	public String getPath();

	/**
	 * Return the memento.
	 *
	 * @return java.lang.String
	 */
	public String getMemento();

}