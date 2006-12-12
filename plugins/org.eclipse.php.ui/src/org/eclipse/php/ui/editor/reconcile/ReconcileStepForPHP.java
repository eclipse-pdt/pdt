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
package org.eclipse.php.ui.editor.reconcile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcileResult;
import org.eclipse.php.core.documentModel.DOMModelForPHP;
import org.eclipse.php.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.ui.internal.Logger;
import org.eclipse.wst.sse.ui.internal.reconcile.ReconcileAnnotationKey;
import org.eclipse.wst.sse.ui.internal.reconcile.StructuredReconcileStep;
import org.eclipse.wst.sse.ui.internal.reconcile.TemporaryAnnotation;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.operations.LocalizedMessage;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class ReconcileStepForPHP extends StructuredReconcileStep {

	protected IReconcileResult[] reconcileModel(DirtyRegion dirtyRegion, IRegion subRegion) {

		IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead(getStructuredDocument());
		IReconcileResult[] results = EMPTY_RECONCILE_RESULT_SET;
		try {
			if (sModel != null) {

				DOMModelForPHP model = (DOMModelForPHP) sModel;

				//@GINO: Updata the FileData because the content has changed
				//this might not be the best way to do this
				model.updateFileData();

				PHPFileData fileData = model.getFileData();
				if (fileData == null) {
					return EMPTY_RECONCILE_RESULT_SET;
				}
				IPHPMarker[] markers = fileData.getMarkers();

				ArrayList annotations = new ArrayList();
				if (markers != null) {
					for (int i = 0; markers.length > i; i++) {
						IReconcileResult annotation = createAnnotation(markers[i]);
						if (annotation != null) {
							annotations.add(annotation);
						}
					}
				}

				results = (IReconcileResult[]) annotations.toArray(new IReconcileResult[annotations.size()]);
			}
		} finally {
			if (sModel != null)
				sModel.releaseFromRead();
		}
		return results;
	}

	/**
	 * Converts a map of IValidatorForReconcile to List to annotations based
	 * on those messages
	 * 
	 * @param messages
	 * @return
	 */
	protected IReconcileResult[] createAnnotations(List messageList) {
		List annotations = new ArrayList();
		for (int i = 0; i < messageList.size(); i++) {
			IMessage validationMessage = (IMessage) messageList.get(i);

			int offset = validationMessage.getOffset();

			if (offset < 0)
				continue;

			String messageText = null;
			try {
				messageText = validationMessage.getText(validationMessage.getClass().getClassLoader());
			} catch (Exception t) {
				Logger.logException("exception reporting message from validator", t); //$NON-NLS-1$
				continue;
			}
			String type = TemporaryAnnotation.ANNOT_INFO;
			switch (validationMessage.getSeverity()) {
				case IMessage.HIGH_SEVERITY:
					type = TemporaryAnnotation.ANNOT_ERROR;
					break;
				case IMessage.NORMAL_SEVERITY:
					type = TemporaryAnnotation.ANNOT_WARNING;
					break;
				case IMessage.LOW_SEVERITY:
					type = TemporaryAnnotation.ANNOT_WARNING;
					break;
				case IMessage.ERROR_AND_WARNING:
					type = TemporaryAnnotation.ANNOT_WARNING;
					break;
			}

			int length = validationMessage.getLength();
			if (length >= 0) {
				Position p = new Position(offset, length);
				IStructuredDocument structuredDocument = getStructuredDocument();
				ReconcileAnnotationKey key = createKey(structuredDocument.getRegionAtCharacterOffset(offset), ReconcileAnnotationKey.TOTAL);
				annotations.add(new TemporaryAnnotation(p, type, messageText, key));
			}
		}

		return (IReconcileResult[]) annotations.toArray(new IReconcileResult[annotations.size()]);
	}

	protected IReconcileResult createAnnotation(IPHPMarker marker) {
		String descr = marker.getDescription();
		UserData userData = marker.getUserData();
		int offset = userData.getStartPosition();
		int endPos = userData.getEndPosition();
		int length = endPos - offset;
		Message mess = new LocalizedMessage(IMessage.HIGH_SEVERITY, descr);

		if (offset < 0 || length < 0) {
			return null;
		}

		String messageText = null;
		try {
			messageText = mess.getText(mess.getClass().getClassLoader());
		} catch (Exception t) {
			Logger.logException("exception reporting message from validator", t); //$NON-NLS-1$
			return null;
		}

		String type = TemporaryAnnotation.ANNOT_INFO;
		String markerType = marker.getType();
		if (markerType.equals(IPHPMarker.TASK)) {
			// TODO :
			// type = TemporaryAnnotation.ANNOT_TASK;
			type = TemporaryAnnotation.ANNOT_INFO;
		} else if (markerType.equals(IPHPMarker.ERROR)) {
			type = TemporaryAnnotation.ANNOT_ERROR;
		} else if (markerType.equals(IPHPMarker.WARNING)) {
			type = TemporaryAnnotation.ANNOT_WARNING;
		}

		Position p = new Position(offset, length);
		IStructuredDocument structuredDocument = getStructuredDocument();
		ReconcileAnnotationKey key = createKey(structuredDocument.getRegionAtCharacterOffset(offset), ReconcileAnnotationKey.TOTAL);
		return new TemporaryAnnotation(p, type, messageText, key);
	}
}
