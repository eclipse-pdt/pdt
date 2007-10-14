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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.wst.html.core.internal.validate.HTMLValidationAdapterFactory;
import org.eclipse.wst.html.internal.validation.HTMLValidationReporter;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.FileBufferModelManager;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapterFactory;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.validate.ValidationAdapter;
import org.eclipse.wst.sse.core.internal.validate.ValidationMessage;
import org.eclipse.wst.sse.ui.internal.reconcile.validator.ISourceValidator;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.w3c.dom.Text;

/**
 * Validates the syntax of PHP document
 * @author Roy, 2007
 */
public class PHPValidator implements IValidator, ISourceValidator {

	/**
	 * Objective document to validate  
	 */
	private IDocument document;
	private IFile file;

	public void connect(IDocument document) {
		if (document == null) {
			throw new IllegalStateException(PHPUIMessages.getString("PHPValidator.0")); //$NON-NLS-1$
		}

		this.document = document;

		if (!(document instanceof IStructuredDocument)) {
			throw new IllegalStateException(PHPUIMessages.getString("PHPValidator.1")); //$NON-NLS-1$
		}

		// check for read only files (such as include path files, etc.)
		if (FileBufferModelManager.getInstance().calculateId(document) == null) {
			this.document = null;
			return;
		}

		final IStructuredModel model = StructuredModelManager.getModelManager().getModelForRead((IStructuredDocument) document);
		if (model == null) {
			throw new IllegalStateException(PHPUIMessages.getString("PHPValidator.2")); //$NON-NLS-1$
		}

		try {
			// get the 
			assert model instanceof DOMModelForPHP;
			final DOMModelForPHP phpDomModel = (DOMModelForPHP) model;

			this.file = phpDomModel.getIFile();
			if (file == null) {
				throw new IllegalStateException(PHPUIMessages.getString("PHPValidator.3") + phpDomModel.getBaseLocation()); //$NON-NLS-1$
			}

		} finally {
			if (model != null) {
				model.releaseFromRead();
			}
		}

	}

	public void disconnect(IDocument document) {
		this.document = null;
	}

	public void validate(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		if (helper == null || document == null || file == null) {
			return;
		}
		if ((reporter != null) && (reporter.isCancelled() == true)) {
			throw new OperationCanceledException();
		}

		// get project model (or external one)
		PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
		if (ExternalFilesRegistry.getInstance().isEntryExist(file)) {
			projectModel = PHPWorkspaceModelManager.getDefaultPHPProjectModel();
		}

		// ignore the case wbefore the project is created - exit
		if (projectModel == null) {
			return;
		}

		// update model
		projectModel.fileWasChanged(file, document);

		// now after the file data is updated - update the annotations
		String fileName = ""; //$NON-NLS-1$
		final IPath filePath = file.getFullPath();
		if (filePath != null) {
			fileName = filePath.toString();
		}
		final PHPFileData fileData = projectModel.getFileData(file.getFullPath().toString());

		if (fileData == null) {
			return;
		}

		final IPHPMarker[] markers = fileData.getMarkers();
		if (markers == null) {
			return;
		}

		IStructuredModel model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
		if (model == null)
			return; // error

		try {

			// get the PHP node
			final IndexedRegion ir = getNode(dirtyRegion, model);

			if (ir instanceof INodeNotifier) {

				INodeAdapterFactory factory = HTMLValidationAdapterFactory.getInstance();
				ValidationAdapter adapter = (ValidationAdapter) factory.adapt((INodeNotifier) ir);
				if (adapter == null)
					return; // error

				if (reporter != null) {
					HTMLValidationReporter validationReporter = null;

					validationReporter = getReporter(reporter, file, (IDOMModel) model);
					validationReporter.clear();
					adapter.setReporter(validationReporter);

					String args[] = new String[] { fileName };

					for (IPHPMarker marker : markers) {
						if (marker.getType().equals(IPHPMarker.TASK)) {
							continue;
						}

						// get the error information
						final UserData userData = marker.getUserData();
						final String description = marker.getDescription();
						final int offset = userData.getStartPosition();
						final int length = userData.getEndPosition() - offset;
						final ValidationMessage message = new ValidationMessage(description, offset, length, ValidationMessage.ERROR);

						validationReporter.report(message);
					}
				}
			}

		} finally {
			if (model != null) {
				model.releaseFromRead();
			}
		}
	}

	protected HTMLValidationReporter getReporter(IReporter reporter, IFile file, IDOMModel model) {
		return new HTMLValidationReporter(this, reporter, file, model);
	}

	private IndexedRegion getNode(IRegion dirtyRegion, IStructuredModel model) {
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

		return ir;
	}

	//see org.eclipse.wst.html.internal.validation.HTMLValidator.getCoveringNode
	private IndexedRegion getCoveringNode(IRegion dirtyRegion) {
		IndexedRegion largestRegion = null;
		if (document instanceof IStructuredDocument) {
			IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
			try {
				if (sModel != null) {
					IStructuredDocumentRegion[] regions = ((IStructuredDocument) document).getStructuredDocumentRegions(dirtyRegion.getOffset(), dirtyRegion.getLength());
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

	protected IndexedRegion getLargest(IStructuredDocumentRegion[] sdRegions) {

		if (sdRegions == null || sdRegions.length == 0)
			return null;

		IndexedRegion currentLargest = getCorrespondingNode(sdRegions[0]);
		for (int i = 0; i < sdRegions.length; i++) {
			if (!sdRegions[i].isDeleted()) {
				IndexedRegion corresponding = getCorrespondingNode(sdRegions[i]);

				if (currentLargest instanceof Text)
					currentLargest = corresponding;

				if (corresponding != null) {
					if (!(corresponding instanceof Text)) {
						if (corresponding.getStartOffset() <= currentLargest.getStartOffset() && corresponding.getEndOffset() >= currentLargest.getEndOffset())
							currentLargest = corresponding;
					}
				}

			}
		}
		return currentLargest;
	}

	protected IndexedRegion getCorrespondingNode(IStructuredDocumentRegion sdRegion) {
		IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
		IndexedRegion indexedRegion = null;
		try {
			if (sModel != null)
				indexedRegion = sModel.getIndexedRegion(sdRegion.getStart());
		} finally {
			if (sModel != null)
				sModel.releaseFromRead();
		}
		return indexedRegion;
	}

	public void cleanup(IReporter reporter) {
		// nothing to do here
	}

	public void validate(IValidationContext helper, IReporter reporter) throws ValidationException {
		// TODO Auto-generated method stub
	}

	/**
	 * basic test for validation step 
	 * @param file
	 * @return
	 */
	protected boolean shouldValidate(IFile file) {
		IResource resource = file;
		do {
			if (resource.isDerived() || resource.isTeamPrivateMember() || !resource.isAccessible() || resource.getName().charAt(0) == '.') {
				return false;
			}
			resource = resource.getParent();
		} while ((resource.getType() & IResource.PROJECT) == 0);
		return true;
	}
}
