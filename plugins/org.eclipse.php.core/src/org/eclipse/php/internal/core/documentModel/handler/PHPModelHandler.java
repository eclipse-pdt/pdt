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
package org.eclipse.php.internal.core.documentModel.handler;

import org.eclipse.php.internal.core.documentModel.encoding.PHPDocumentCharsetDetector;
import org.eclipse.php.internal.core.documentModel.loader.PHPDocumentLoader;
import org.eclipse.php.internal.core.documentModel.loader.PHPModelLoader;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.wst.sse.core.internal.document.IDocumentCharsetDetector;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.AbstractModelHandler;
import org.eclipse.wst.sse.core.internal.provisional.IModelLoader;

public class PHPModelHandler extends AbstractModelHandler {

	/**
	 * Needs to match what's in plugin registry. In fact, can be overwritten at
	 * run time with what's in registry! (so should never be 'final')
	 */
	private static String ModelHandlerID = "org.eclipse.php.core.documentModel.handler"; //$NON-NLS-1$

	public PHPModelHandler() {
		super();
		setId(ModelHandlerID);
		setAssociatedContentTypeId(ContentTypeIdForPHP.ContentTypeID_PHP);
	}

	public IModelLoader getModelLoader() {
		return new PHPModelLoader();
	}

	public IDocumentCharsetDetector getEncodingDetector() {
		return new PHPDocumentCharsetDetector();
	}

	public IDocumentLoader getDocumentLoader() {
		return new PHPDocumentLoader();
	}
}
