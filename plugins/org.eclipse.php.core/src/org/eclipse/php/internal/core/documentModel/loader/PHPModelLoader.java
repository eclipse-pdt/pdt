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
package org.eclipse.php.internal.core.documentModel.loader;

import java.util.List;

import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.wst.html.core.internal.encoding.HTMLModelLoader;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.provisional.IModelLoader;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PHPModelLoader extends HTMLModelLoader {

	public IDocumentLoader getDocumentLoader() {
		if (documentLoaderInstance == null) {
			documentLoaderInstance = new PHPDocumentLoader();
		}
		return documentLoaderInstance;
	}

	public IModelLoader newInstance() {
		return new PHPModelLoader();
	}

	public List getAdapterFactories() {

		// @GINO: Might want to add new adapter factories here
		return super.getAdapterFactories();
	}

	// Creating the PHPModel
	public IStructuredModel newModel() {
		return new DOMModelForPHP();
	}
}
