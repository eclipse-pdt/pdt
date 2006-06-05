/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.documentModel.loader;

import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.php.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.core.documentModel.parser.PhpStructuredDocumentReParser;
import org.eclipse.php.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.wst.html.core.internal.encoding.HTMLDocumentLoader;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.provisional.document.IEncodedDocument;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocument;

public class PHPDocumentLoader extends HTMLDocumentLoader {

	protected IEncodedDocument newEncodedDocument() {
		IEncodedDocument doc = super.newEncodedDocument();
		assert doc instanceof BasicStructuredDocument;
		((BasicStructuredDocument) doc).setReParser(new PhpStructuredDocumentReParser());

		//doc.setPreferredLineDelimiter( "\n" );
		return doc;
	}

	public RegionParser getParser() {
		PhpSourceParser parser = new PhpSourceParser();
		// for the "static HTML" case, we need to initialize
		// Blocktags here.
		addHTMLishTag(parser, "script"); //$NON-NLS-1$
		addHTMLishTag(parser, "style"); //$NON-NLS-1$
		return parser;
	}

	public IDocumentLoader newInstance() {
		return new PHPDocumentLoader();
	}

	public IDocumentPartitioner getDefaultDocumentPartitioner() {
		return new PHPStructuredTextPartitioner();
	}
}
