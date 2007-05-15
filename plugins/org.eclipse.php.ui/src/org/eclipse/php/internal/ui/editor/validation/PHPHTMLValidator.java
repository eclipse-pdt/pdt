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
package org.eclipse.php.internal.ui.editor.validation;

import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.html.core.internal.validate.HTMLValidationAdapterFactory;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.internal.validation.HTMLValidationReporter;
import org.eclipse.wst.html.internal.validation.HTMLValidator;
import org.eclipse.wst.html.ui.internal.HTMLUIMessages;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.FileBufferModelManager;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapterFactory;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.validate.ValidationAdapter;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.operations.LocalizedMessage;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.xml.core.internal.document.DocumentTypeAdapter;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.Text;

/**
 * @author Robert Goodman
 * This changes HTML syntax highlighting not to syntax check PHP regions. 
 * Having to copy a lot of code since the HTML validator is coded to support subclassing.
 * This file can be removed after moving to WST 1.0
 *
 */

public class PHPHTMLValidator extends HTMLValidator {

	private IDocument fDocument;

	public PHPHTMLValidator() {

	}

	public void validate(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		int offset = dirtyRegion.getOffset();
		ITypedRegion typedRegion;
		try {
			typedRegion = fDocument.getPartition(offset);
		} catch (BadLocationException e) {
			Logger.logException(e);
			return;
		}

		final String type = typedRegion.getType();
		if (type.equals(IHTMLPartitions.HTML_DEFAULT)) {
			IStructuredModel structuredModel = null;
			structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
			DOMModelForPHP domModelForPHP = (DOMModelForPHP) structuredModel;
			IFile documentFile = domModelForPHP.getIFile();
			structuredModel.releaseFromRead();
			//added a test if external file in order not to get exception
			//when getting the IFile instance of the document
			//An exception is thrown when segmentCount == 1 (C:\file.php for example) 
			if (ExternalFilesRegistry.getInstance().isEntryExist(documentFile.getFullPath().toString())) {
				validateHTMLForExternalDocument(dirtyRegion, helper, reporter);
			} else {
				super.validate(dirtyRegion, helper, reporter);
			}
			return;
		} else if (!isPHPPartition(type)) {
			return;
		}

		IStructuredModel structuredModel = null;
		try {
			structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
			if (structuredModel == null) {
				return; // error
			}
			DOMModelForPHP model = (DOMModelForPHP) structuredModel;

			//@GINO: Updata the FileData because the content has changed
			//this might not be the best way to do this
			if (!DOMModelForPHP.FREQUENT_MODEL_UPDATE)
				model.updateFileData();

			PHPFileData fileData = model.getFileData();
			if (fileData == null) {
				return;
			}

			reporter.removeAllMessages(this);
			IPHPMarker[] markers = fileData.getMarkers();

			if (markers == null) {
				return;
			}
			for (int i = 0; markers.length > i; i++) {
				IPHPMarker marker = markers[i];
				if (marker.getType().equals(IPHPMarker.TASK)) {
					continue;
				}
				String descr = marker.getDescription();
				LocalizedMessage mess = new LocalizedMessage(IMessage.HIGH_SEVERITY, descr);
				UserData userData = marker.getUserData();

				final int startPosition = userData.getStartPosition();
				final int length = userData.getEndPosition() - startPosition;

				mess.setOffset(startPosition);
				mess.setLength(length);
				reporter.addMessage(this, mess);
			}
		} finally {
			if (structuredModel != null)
				structuredModel.releaseFromRead();
		}

	}

	public void connect(IDocument document) {
		super.connect(document);
		fDocument = document;
	}

	/**
	 * @see org.eclipse.wst.sse.ui.internal.reconcile.validator.ISourceValidator
	 */
	public void disconnect(IDocument document) {
		super.disconnect(document);
		if (document == fDocument) {
			fDocument = null;
		}
	}

	private boolean isPHPPartition(String type) {
		if (type == null || type == "") {
			return false;
		}
		return ((type.equals(PHPPartitionTypes.PHP_DEFAULT)) || (type.equals(PHPPartitionTypes.PHP_DOC)) || (type.equals(PHPPartitionTypes.PHP_MULTI_LINE_COMMENT)) || (type.equals(PHPPartitionTypes.PHP_QUOTED_STRING)) || (type.equals(PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT)));
	}

	//See org.eclipse.wst.html.internal.validation.HTMLValidator.validate
	//This method is "overriden" to handle External document files that are located in the root of the device
	//such as C:\phpFile.php
	private void validateHTMLForExternalDocument(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		if (helper == null || fDocument == null)
			return;

		if ((reporter != null) && (reporter.isCancelled() == true)) {
			throw new OperationCanceledException();
		}

		IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
		if (model == null)
			return; // error

		try {
			IDOMDocument document = null;
			if (model instanceof IDOMModel) {
				document = ((IDOMModel) model).getDocument();
			}

			if (document == null || !hasHTMLFeature(document))
				return; // ignore

			ITextFileBuffer fb = FileBufferModelManager.getInstance().getBuffer(fDocument);
			if (fb == null)
				return;

			IFile file = ExternalFilesRegistry.getInstance().getFileEntry(fb.getLocation().toString());

			// this will be the wrong region if it's Text (instead of Element)
			// we don't know how to validate Text
			IndexedRegion ir = getCoveringNode(dirtyRegion); //  model.getIndexedRegion(dirtyRegion.getOffset());
			if (ir instanceof Text) {
				while (ir != null && ir instanceof Text) {
					// it's assumed that this gets the IndexedRegion to
					// the right of the end offset
					ir = model.getIndexedRegion(ir.getEndOffset());
				}
			}

			if (ir instanceof INodeNotifier) {

				INodeAdapterFactory factory = HTMLValidationAdapterFactory.getInstance();
				ValidationAdapter adapter = (ValidationAdapter) factory.adapt((INodeNotifier) ir);
				if (adapter == null)
					return; // error

				if (reporter != null) {
					HTMLValidationReporter rep = null;
					rep = getReporter(reporter, file, (IDOMModel) model);
					rep.clear();
					adapter.setReporter(rep);

					String fileName = ""; //$NON-NLS-1$
					IPath filePath = file.getFullPath();
					if (filePath != null) {
						fileName = filePath.toString();
					}
					String args[] = new String[] { fileName };

					Message mess = new LocalizedMessage(IMessage.LOW_SEVERITY, NLS.bind(HTMLUIMessages.MESSAGE_HTML_VALIDATION_MESSAGE_UI_, args));
					mess.setParams(args);
					reporter.displaySubtask(this, mess);
				}
				adapter.validate(ir);
			}
		} finally {
			if (model != null)
				model.releaseFromRead();
		}
	}

	//see org.eclipse.wst.html.internal.validation.HTMLValidator.hasHTMLFeature
	private boolean hasHTMLFeature(IDOMDocument document) {
		DocumentTypeAdapter adapter = (DocumentTypeAdapter) document.getAdapterFor(DocumentTypeAdapter.class);
		if (adapter == null)
			return false;
		return adapter.hasFeature("HTML");//$NON-NLS-1$
	}

	//see org.eclipse.wst.html.internal.validation.HTMLValidator.getCoveringNode
	private IndexedRegion getCoveringNode(IRegion dirtyRegion) {
		IndexedRegion largestRegion = null;
		if (fDocument instanceof IStructuredDocument) {
			IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
			try {
				if (sModel != null) {
					IStructuredDocumentRegion[] regions = ((IStructuredDocument) fDocument).getStructuredDocumentRegions(dirtyRegion.getOffset(), dirtyRegion.getLength());
					largestRegion = getLargest(regions);
				}
			} finally {
				if (sModel != null)
					sModel.releaseFromRead();
			}
		}
		return largestRegion;
	}
}
