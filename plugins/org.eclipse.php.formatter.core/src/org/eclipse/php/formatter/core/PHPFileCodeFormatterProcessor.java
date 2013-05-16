/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.w3c.dom.Node;

/**
 * The purpose of this class is to register to
 * "org.eclipse.wst.sse.core.formatProcessors" extension point. This extension
 * point is used by
 * {@link org.eclipse.wst.sse.ui.internal.actions.FormatActionDelegate} which is
 * registered as popup action in PHP Explorer to resources. This is the reason
 * only formatFile() is implemented.
 * 
 * @author guy.g
 * 
 */
public class PHPFileCodeFormatterProcessor implements
		IStructuredFormatProcessor {

	PHPCodeFormatterProxy formatter = new PHPCodeFormatterProxy();

	public void formatDocument(IDocument document, int start, int length)
			throws IOException, CoreException {

	}

	public void formatFile(IFile file) throws IOException, CoreException {
		if (file == null) {
			return;
		}

		IStructuredModel structuredModel = null;

		structuredModel = StructuredModelManager.getModelManager()
				.getModelForEdit(file);
		if (structuredModel == null) {
			return;
		}
		try {
			// setup structuredModel
			// Note: We are getting model for edit. Will save model if model
			// changed.
			IStructuredDocument structuredDocument = structuredModel
					.getStructuredDocument();

			IRegion region = new Region(0, structuredDocument.getLength());

			formatter.format(structuredDocument, region);
			// save model if needed - used for closed files
			if (!structuredModel.isSharedForEdit()
					&& structuredModel.isSaveNeeded()) {
				structuredModel.save();
			}
		} finally {
			// release from model manager
			if (structuredModel != null) {
				structuredModel.releaseFromEdit();
			}
		}

	}

	public void formatModel(IStructuredModel structuredModel) {

	}

	public void formatModel(IStructuredModel structuredModel, int start,
			int length) {

	}

	public void formatNode(Node node) {

	}

	public void setProgressMonitor(IProgressMonitor monitor) {

	}

}
