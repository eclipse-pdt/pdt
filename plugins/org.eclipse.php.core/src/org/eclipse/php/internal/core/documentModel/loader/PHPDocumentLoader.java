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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.documentModel.parser.PhpStructuredDocumentReParser;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.wst.html.core.internal.encoding.HTMLDocumentLoader;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.encoding.CodedReaderCreator;
import org.eclipse.wst.sse.core.internal.encoding.EncodingMemento;
import org.eclipse.wst.sse.core.internal.encoding.IContentDescriptionExtended;
import org.eclipse.wst.sse.core.internal.encoding.util.Logger;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.provisional.document.IEncodedDocument;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocument;

public class PHPDocumentLoader extends HTMLDocumentLoader {

	private static final boolean DEBUG = false;
	private CodedReaderCreator fCodedReaderCreator;

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

	protected CodedReaderCreator getCodedReaderCreator() {
		if (fCodedReaderCreator == null) {
			fCodedReaderCreator = new PHPCodedReaderCreator();
		}
		return fCodedReaderCreator;
	}
	
	class PHPCodedReaderCreator extends CodedReaderCreator {
		protected EncodingMemento createMemento(IContentDescription contentDescription) {
			EncodingMemento result;
			String appropriateDefault = contentDescription.getContentType().getDefaultCharset();
			String detectedCharset = (String) contentDescription.getProperty(IContentDescriptionExtended.DETECTED_CHARSET);
			String unSupportedCharset = (String) contentDescription.getProperty(IContentDescriptionExtended.UNSUPPORTED_CHARSET);
			String javaCharset = contentDescription.getCharset();
			
			// Set default workbench encoding:
			if (detectedCharset == null && appropriateDefault == null) {
				detectedCharset = javaCharset = appropriateDefault = ResourcesPlugin.getEncoding();
			}
			
			// integrity checks for debugging
			if (javaCharset == null) {
				Logger.log(Logger.INFO_DEBUG, "charset equaled null!"); //$NON-NLS-1$
			} else if (javaCharset.length() == 0) {
				Logger.log(Logger.INFO_DEBUG, "charset equaled emptyString!"); //$NON-NLS-1$
			}
			byte[] BOM = (byte[]) contentDescription.getProperty(IContentDescription.BYTE_ORDER_MARK);
			//result = (EncodingMemento)
			// contentDescription.getProperty(IContentDescriptionExtended.ENCODING_MEMENTO);
			result = createEncodingMemento(BOM, javaCharset, detectedCharset, unSupportedCharset, appropriateDefault, null);
			if (!result.isValid()) {
				result.setAppropriateDefault(appropriateDefault);
				// integrity check for debugging "invalid" cases.
				// the apprriate default we have, should equal what's in the
				// detected field. (not sure this is always required)
				if (DEBUG) {
					if (appropriateDefault != null && !appropriateDefault.equals(detectedCharset)) {
						Logger.log(Logger.INFO_DEBUG, "appropriate did not equal detected, as expected for invalid charset case"); //$NON-NLS-1$
					}
				}
			}
			return result;
		}
	}
}
