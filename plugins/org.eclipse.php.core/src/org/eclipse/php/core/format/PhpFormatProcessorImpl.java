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
package org.eclipse.php.core.format;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.w3c.dom.Node;

public class PhpFormatProcessorImpl extends HTMLFormatProcessorImpl {

	// saving the paramenters of the formatting areas 
	//so that if we are required to format only part of the php - we'll have the data.

	private int start;
	private int length;

	private IStructuredFormatter fFormatter;

	protected String getFileExtension() {
		return "php"; //$NON-NLS-1$
	}

	protected IStructuredFormatter getFormatter(Node node) {
		if (fFormatter == null) {
			fFormatter = new PhpFormatter();
			((PhpFormatter) fFormatter).setProcessor(this);
		}
		return fFormatter;
	}

	protected void refreshFormatPreferences() {
		super.refreshFormatPreferences();
	}

	public void formatDocument(IDocument document, int start, int length) throws IOException, CoreException {

		if (document == null)
			return;

		if ((start >= 0) && (length >= 0)) {
			// the following if is the reason for not using the super (except for saving the start and length)
			// the bug in the super is that in a multipass formatter the document length is changed and thus
			// the test (start + length <= document.getLength()) is not valid because it will always fail
			if ((start + length > document.getLength())) {
				if (start > document.getLength()) {
					return;
				}
				length = document.getLength() - start;
			}
			this.start = start;
			this.length = length;
			IStructuredModel structuredModel = null;
			// OutputStream outputStream = null;
			try {
				// setup structuredModel
				// Note: We are getting model for edit. Will save model if
				// model changed.
				structuredModel = StructuredModelManager.getModelManager().getExistingModelForEdit(document);

				// format
				formatModel(structuredModel, start, length);

				// save model if needed
				if (!structuredModel.isSharedForEdit() && structuredModel.isSaveNeeded())
					structuredModel.save();
			} finally {
				// ensureClosed(outputStream, null);
				// release from model manager
				if (structuredModel != null)
					structuredModel.releaseFromEdit();
			}
		}
	}

	public void formatModel(IStructuredModel structuredModel, int start, int length) {
		this.start = start;
		this.length = length;
		super.formatModel(structuredModel, start, length);
	}

	protected int getLength() {
		return length;
	}

	protected int getStart() {
		return start;
	}
}
