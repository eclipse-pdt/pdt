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
 * A Web module.
 */
public class WebModule implements IPHPWebModule {
	private String docBase;
	private String path;
	private String memento;

	/**
	 * WebModule constructor comment.
	 * 
	 * @param path
	 *            a path
	 * @param docBase
	 *            a document base
	 */
	public WebModule(String path, String docBase, String memento) {
		super();
		this.path = path;
		this.docBase = docBase;
		this.memento = memento;
	}

	/**
	 * Get the document base.
	 *
	 * @return java.lang.String
	 */
	public String getDocumentBase() {
		return docBase;
	}

	/**
	 * Return the path. (context root)
	 *
	 * @return java.lang.String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Return the memento.
	 *
	 * @return java.lang.String
	 */
	public String getMemento() {
		return memento;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof WebModule))
			return false;

		WebModule wm = (WebModule) obj;
		if (!getDocumentBase().equals(wm.getDocumentBase()))
			return false;
		if (!getPath().equals(wm.getPath()))
			return false;
		if (!getMemento().equals(wm.getMemento()))
			return false;
		return true;
	}
}