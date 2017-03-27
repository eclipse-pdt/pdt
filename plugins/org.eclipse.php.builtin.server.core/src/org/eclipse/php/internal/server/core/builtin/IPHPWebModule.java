/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
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