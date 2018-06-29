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
	@Override
	public String getDocumentBase() {
		return docBase;
	}

	/**
	 * Return the path. (context root)
	 *
	 * @return java.lang.String
	 */
	@Override
	public String getPath() {
		return path;
	}

	/**
	 * Return the memento.
	 *
	 * @return java.lang.String
	 */
	@Override
	public String getMemento() {
		return memento;
	}

	/**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof WebModule)) {
			return false;
		}

		WebModule wm = (WebModule) obj;
		if (!getDocumentBase().equals(wm.getDocumentBase())) {
			return false;
		}
		if (!getPath().equals(wm.getPath())) {
			return false;
		}
		if (!getMemento().equals(wm.getMemento())) {
			return false;
		}
		return true;
	}
}