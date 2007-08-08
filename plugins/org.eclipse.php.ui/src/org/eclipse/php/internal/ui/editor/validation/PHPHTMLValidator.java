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

import java.util.*;

import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.parser.ModelListener;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.reconcile.ReconcileStepForPHP;
import org.eclipse.ui.*;
import org.eclipse.wst.html.core.internal.validate.HTMLValidationAdapterFactory;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.html.internal.validation.HTMLValidationReporter;
import org.eclipse.wst.html.internal.validation.HTMLValidator;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.FileBufferModelManager;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapterFactory;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.validate.ValidationAdapter;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.reconcile.ReconcileAnnotationKey;
import org.eclipse.wst.sse.ui.internal.reconcile.TemporaryAnnotation;
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

public class PHPHTMLValidator extends HTMLValidator implements ModelListener {

	private IDocument fDocument;
	private String currentFileName = null;

	public void validate(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		//handle the cases that the connect() run before the structured model was created (look at innerConnect()).

		if (currentFileName == null && fDocument != null) {
			innerConnect();
		}
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
			try {
				structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
				DOMModelForPHP domModelForPHP = (DOMModelForPHP) structuredModel;
				IFile documentFile = domModelForPHP.getIFile();
				//added a test if external file in order not to get exception
				//when getting the IFile instance of the document
				//An exception is thrown when segmentCount == 1 (C:\file.php for example)
				if (ExternalFilesRegistry.getInstance().isEntryExist(documentFile.getFullPath().toString())) {
					validateHTMLForExternalDocument(dirtyRegion, helper, reporter);
				} else {
					super.validate(dirtyRegion, helper, reporter);
				}
			} finally {
				if (structuredModel != null)
					structuredModel.releaseFromRead();
			}

			return;
		}
	}

	public void connect(IDocument document) {
		super.connect(document);
		fDocument = document;

		innerConnect();
	}

	private void innerConnect() {
		IStructuredModel structuredModel = null;
		try {
			structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
			if (structuredModel == null) {
				return;
			}
			currentFileName = structuredModel.getId();

			PHPWorkspaceModelManager.getInstance().addModelListener(this);
			DOMModelForPHP domModelForPHP = (DOMModelForPHP) structuredModel;
			PHPFileData fd = domModelForPHP.getFileData();
			if (fd != null) {
				fileDataChanged(fd);
			}
		} finally {
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}
	}

	private void calcCurrentFileName() {
		IStructuredModel structuredModel = null;
		try {
			structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(fDocument);
			if (structuredModel == null) {
				return;
			}
			currentFileName = structuredModel.getId();

		} finally {
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}
	}

	/**
	 * @see org.eclipse.wst.sse.ui.internal.reconcile.validator.ISourceValidator
	 */
	public void disconnect(IDocument document) {
		super.disconnect(document);
		if (document == fDocument) {
			fDocument = null;
		}
		PHPWorkspaceModelManager.getInstance().removeModelListener(this);
	}

	//See org.eclipse.wst.html.internal.validation.HTMLValidator.validate
	//This method is "overriden" to handle External document files that are located in the root of the device
	//such as C:\phpFile.php
	private void validateHTMLForExternalDocument(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		if (helper == null || fDocument == null)
			return;

		if (reporter != null && reporter.isCancelled() == true) {
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

					Message mess = new LocalizedMessage(IMessage.LOW_SEVERITY, NLS.bind("HTML Syntax : {0}", args));
					mess.setParams(args);
					reporter.displaySubtask(this, mess);
				}
				adapter.validate(ir);
			}
		} finally {
			if (model != null) {
				model.releaseFromRead();
			}
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
				if (sModel != null) {
					sModel.releaseFromRead();
				}
			}
		}
		return largestRegion;
	}

	public void dataCleared() {
		// do nothing
	}

	public void fileDataAdded(PHPFileData fileData) {
		// fixed bug 197413
		// in case of saving the file in different location
		// reassign the currentFileName variable.
		calcCurrentFileName();
	}

	public void fileDataChanged(PHPFileData fileData) {
		if (!fileData.getName().equals(currentFileName)) {
			return;
		}

		IPHPMarker[] markers = fileData.getMarkers();

		if (markers == null) {
			return;
		}
		List messages = new ArrayList();
		for (IPHPMarker marker : markers) {
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
			messages.add(mess);
		}
		ReconcileStepForPHP reconcileStepForPHP = new ReconcileStepForPHP();
		Map annotations = createAnnotations(reconcileStepForPHP, messages);

		// get text viewer and set annotations
		StructuredTextViewer textViewer = getTextViewer();

		if (textViewer == null) {
			return;
		}
		StructuredResourceMarkerAnnotationModel annotationModel = (StructuredResourceMarkerAnnotationModel) textViewer.getAnnotationModel();
		// iterate the exist annotations and remove PHP annotations
		Iterator annotationIt = annotationModel.getAnnotationIterator();
		List annotationToRemove = new ArrayList();
		while (annotationIt.hasNext()) {
			Annotation annotation = (Annotation) annotationIt.next();
			if (annotation instanceof TemporaryAnnotation) {
				TemporaryAnnotation temporaryAnnotation = (TemporaryAnnotation) annotation;
				if (isPhpViewerAnnotation(temporaryAnnotation)) {
					annotationToRemove.add(temporaryAnnotation);
				}
			}
		}
		annotationModel.replaceAnnotations((Annotation[]) annotationToRemove.toArray(new Annotation[0]), annotations);
	}

	private StructuredTextViewer getTextViewer() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchwindow = workbench.getActiveWorkbenchWindow();
		if (workbenchwindow == null) {
			IWorkbenchWindow[] workbenchWindows = workbench.getWorkbenchWindows();
			if (workbenchWindows.length > 0) {
				workbenchwindow = workbenchWindows[0];
			}
		}
		IWorkbenchPage workbenchpage = workbenchwindow.getActivePage();
		if (workbenchpage == null)
			return null;
		IEditorPart editorpart = workbenchpage.getActiveEditor();
		if (editorpart instanceof PHPStructuredEditor) {
			PHPStructuredEditor phpStructuredEditor = (PHPStructuredEditor) editorpart;
			StructuredTextViewer textViewer = phpStructuredEditor.getTextViewer();
			if (fDocument == textViewer.getDocument()) {
				return textViewer;
			}
			// get right viewer by comparing viewer document
			IEditorReference[] editorReferences = workbenchpage.getEditorReferences();
			for (IEditorReference editorReference : editorReferences) {
				editorpart = editorReference.getEditor(false);
				if (editorpart instanceof PHPStructuredEditor) {
					phpStructuredEditor = (PHPStructuredEditor) editorpart;
					textViewer = phpStructuredEditor.getTextViewer();
					if (fDocument == textViewer.getDocument()) {
						return textViewer;
					}
				}
			}
		}
		return null;
	}

	private boolean isPhpViewerAnnotation(TemporaryAnnotation annotation) {
		ReconcileAnnotationKey key = (ReconcileAnnotationKey) annotation.getKey();
		return key.getPartitionType().equals(PHPPartitionTypes.PHP_DEFAULT);
	}

	/**
	 * Converts a map of IValidatorForReconcile to List to annotations based
	 * on those messages
	 *
	 * @param messages
	 * @return
	 */
	public Map createAnnotations(ReconcileStepForPHP reconcileStepForPHP, List messageList) {
		Map annotations = new HashMap();
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

			int length = validationMessage.getLength();
			if (length >= 0) {
				Position p = new Position(offset, length);
				ReconcileAnnotationKey key = createKey(reconcileStepForPHP);
				annotations.put(new TemporaryAnnotation(p, TemporaryAnnotation.ANNOT_ERROR, messageText, key), p);
			}
		}

		return annotations;
	}

	public ReconcileAnnotationKey createKey(ReconcileStepForPHP reconcileStepForPHP) {
		return new ReconcileAnnotationKey(reconcileStepForPHP, PHPPartitionTypes.PHP_DEFAULT, ReconcileAnnotationKey.TOTAL);
	}

	public void fileDataRemoved(PHPFileData fileData) {
		// do nothing
	}

	/**
	 * The getModel function was overriden since this validator was also registered as org.eclipse.wst.validation.validator (not only org.eclipse.wst.sse.ui.sourcevalidation)
	 * The getModel failed since the private method canHandle() were looking for html files.
	 * In the future if the canHandle() will be changed to protected - this function can be removed.
	 * (submitted bug for it #193122)
	 */

	/*	protected IDOMModel getModel(IProject project, IFile file) {
			if (project == null || file == null)
				return null;
			if (!file.exists())
				return null;
			if (!canHandle(file))
				return null;

			IStructuredModel model = null;
			IModelManager manager = StructuredModelManager.getModelManager();
			try {
				file.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
			}
			catch (CoreException e) {
				Logger.logException(e);
			}
			try {
				try {
					model = manager.getModelForRead(file);
				}
				catch (UnsupportedEncodingException ex) {
					// retry ignoring META charset for invalid META charset
					// specification
					// recreate input stream, because it is already partially read
					model = manager.getModelForRead(file, new String(), null);
				}
			}
			catch (UnsupportedEncodingException ex) {
			}
			catch (IOException ex) {
			}
			catch (CoreException e) {
				Logger.logException(e);
			}

			if (model == null)
				return null;
			if (!(model instanceof IDOMModel)) {
				releaseModel(model);
				return null;
			}
			return (IDOMModel) model;
		}

		private IContentTypeManager fContentTypeManager = Platform.getContentTypeManager();
		private IContentType phpContentType = fContentTypeManager.getContentType("org.eclipse.php.core.phpsource");

		private boolean canHandle(IFile file) {
			boolean result = false;
			if (file != null) {
				try {
					IContentDescription contentDescription = file.getContentDescription();
					if (contentDescription != null) {
						IContentType fileContentType = contentDescription.getContentType();
						if (fileContentType.isKindOf(phpContentType)) {
							result = true;
						}
					}
					else if (phpContentType != null) {
						result = phpContentType.isAssociatedWith(file.getName());
					}
				}
				catch (CoreException e) {
					// should be rare, but will ignore to avoid logging "encoding
					// exceptions" and the like here.
					// Logger.logException(e);
				}
			}
			return result;
		}*/
}
