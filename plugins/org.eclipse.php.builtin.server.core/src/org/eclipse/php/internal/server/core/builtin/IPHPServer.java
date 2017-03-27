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

import java.net.URL;

import org.eclipse.wst.server.core.model.IURLProvider;

/**
 *
 */
public interface IPHPServer extends IURLProvider {

	/**
	 * Property which specifies the directory where the server instance exists.
	 * If not specified, instance directory is derived from the textEnvironment
	 * setting.
	 */
	public static final String PROPERTY_DOCUMENT_ROOT_DIR = "documentRootDir";

	/**
	 * Gets the directory where the server instance exists. If not set, the
	 * instance directory is derived from the testEnvironment setting.
	 * 
	 * @return directory where the server instance exists. Returns null if not
	 *         set.
	 */
	public String getDocumentRootDirectory();

	public URL getRootUrl();

}
