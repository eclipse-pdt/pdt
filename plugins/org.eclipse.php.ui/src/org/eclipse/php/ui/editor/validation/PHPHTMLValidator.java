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
package org.eclipse.php.ui.editor.validation;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.Logger;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.internal.validation.HTMLValidator;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.validation.internal.operations.LocalizedMessage;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;

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
			super.validate(dirtyRegion, helper, reporter);
			return;
		} else if (!type.equals(PHPPartitionTypes.PHP_DEFAULT) && !type.equals(PHPPartitionTypes.PHP_QUOTED_STRING)) {
			return;
		}

        IStructuredModel structuredModel = null;
        try {
    		structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
    		if (structuredModel == null) {
    			return; // error
    		}
    		PHPEditorModel model = (PHPEditorModel) structuredModel;
    
    		//@GINO: Updata the FileData because the content has changed
    		//this might not be the best way to do this
    		if (!PHPEditorModel.FREQUENT_MODEL_UPDATE)
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
    			if(marker.getType().equals(IPHPMarker.TASK)){
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
			if (structuredModel != null)structuredModel.releaseFromRead();
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
}
