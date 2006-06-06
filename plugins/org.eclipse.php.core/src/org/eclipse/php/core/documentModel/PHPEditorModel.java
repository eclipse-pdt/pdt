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
package org.eclipse.php.core.documentModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.documentModel.dom.PHPDOMDocument;
import org.eclipse.php.core.documentModel.dom.PHPDOMModelParser;
import org.eclipse.php.core.documentModel.dom.PHPDOMModelUpdater;
import org.eclipse.php.core.documentModel.dom.PHPModelNotifier;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.wst.html.core.internal.document.DOMStyleModelImpl;
import org.eclipse.wst.sse.core.internal.provisional.events.NewDocumentEvent;
import org.eclipse.wst.sse.core.internal.provisional.events.StructuredDocumentRegionsReplacedEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegionList;
import org.eclipse.wst.sse.core.internal.text.CoreNodeList;
import org.eclipse.wst.xml.core.internal.document.XMLModelNotifier;
import org.eclipse.wst.xml.core.internal.document.XMLModelNotifierImpl;
import org.eclipse.wst.xml.core.internal.document.XMLModelParser;
import org.eclipse.wst.xml.core.internal.document.XMLModelUpdater;
import org.w3c.dom.Document;

/*
 * The PHPModel will support both the DOM style interface and PHP specific API's.
 */
public class PHPEditorModel extends DOMStyleModelImpl {

	public static final boolean FREQUENT_MODEL_UPDATE = true;
	private XMLModelNotifier notifier;

	/*
	 * This is modeled after what is done for JSP
	 */
	protected Document internalCreateDocument() {
		PHPDOMDocument document = new PHPDOMDocument();
		document.setModel(this);
		return document;
	}

	protected XMLModelParser createModelParser() {
		return new PHPDOMModelParser(this);
	}

	protected XMLModelUpdater createModelUpdater() {
		return new PHPDOMModelUpdater(this);
	}

	//@GINO: PHP API's coming soon

	/*
	 * Always get the latest version of FileData
	 */
	public PHPFileData getFileData() {

		PHPFileData fileData = null;

		IFile file = getIFile();

		if (file != null) {
			PHPProjectModel projectModel = getProjectModel();

			if (projectModel != null) {

				fileData = projectModel.getFileData(file.getFullPath().toString());
			}

		}

		return fileData;

	}

	//	protected PHPBrowserModel cachedBrowserModel = null;

	public PHPProjectModel getProjectModel() {

		//		if (cachedBrowserModel == null) {
		IFile file = getIFile();

		if (file != null) {
			return PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());

		}

		//		}

		return null;
	}

	public void updateFileData() {

		IFile file = getIFile();

		if (file != null) {
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());

			if (projectModel != null) {
				projectModel.fileWasChanged(file, getStructuredDocument());
			}

		}

	}

	public void changedModel() {
		if (FREQUENT_MODEL_UPDATE)
			updateFileData();
		super.changedModel();
	}

	//returns the IFile corresponding with this model
	private IFile getIFile() {

		String path = getBaseLocation();
		if (path == null || path.length() == 0) {
			Object id = getId();
			if (id == null)
				return null;
			path = id.toString();
		}
		// @GINO: will probably not worked for linked resources
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = (IFile) root.findMember(new Path(path));
		return file;
	}

	
	
	
	public XMLModelNotifier getModelNotifier() {
		if (this.notifier == null) {
			this.notifier = new PHPModelNotifier();
		}
		return this.notifier;
	}

	/**
	 * When PHP regions are replace do step by step 
	 */
	public void nodesReplaced(StructuredDocumentRegionsReplacedEvent event) {

		super.nodesReplaced(event);
		
		/*
		// TRICKY : change DOM model by : newModel(null), newModel(new NewDocumentEvent((IStructuredDocument) event.getDocument(), event.getOriginalRequester()));
		
		// get event properties
		assert event.getDocument() instanceof IStructuredDocument;
		final IStructuredDocument document = (IStructuredDocument) event.getDocument();
		final Object originalRequester = event.getOriginalRequester();
		final int offset = event.getOffset();
		final int eventLength = event.getLength();		

		// first remove old regions
		final IStructuredDocumentRegionList oldStructuredDocumentRegions = event.getOldStructuredDocumentRegions();
		int length = oldStructuredDocumentRegions == null ? 0 : oldStructuredDocumentRegions.getLength();
		for (int i =0; i < length; i++) {
			// remove the deleted region from the list and apply the super method to remove it from dom
			final IStructuredDocumentRegion item = oldStructuredDocumentRegions.item(i);
			item.setNext(null);
			final CoreNodeList coreNodeList = new CoreNodeList(item);
			final StructuredDocumentRegionsReplacedEvent structuredDocumentRegionsReplacedEvent = new StructuredDocumentRegionsReplacedEvent(document, originalRequester, coreNodeList, null, null, offset, eventLength);

			super.nodesReplaced(structuredDocumentRegionsReplacedEvent);
			getModelNotifier().endChanging();
		}

		// then you can add them all
		final IStructuredDocumentRegionList newStructuredDocumentRegions = event.getNewStructuredDocumentRegions();
		final StructuredDocumentRegionsReplacedEvent newEvent = new StructuredDocumentRegionsReplacedEvent(document, originalRequester, null, newStructuredDocumentRegions, null, 0, 0);
		super.nodesReplaced(newEvent);
		
		*/
				
	}

}
