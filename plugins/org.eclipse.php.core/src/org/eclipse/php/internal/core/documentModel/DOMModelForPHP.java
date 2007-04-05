/***********************************************************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html Contributors: Zend and IBM - Initial implementation
 **********************************************************************************************************************/
package org.eclipse.php.internal.core.documentModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.documentModel.dom.DOMDocumentForPHP;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelParser;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelUpdater;
import org.eclipse.php.internal.core.phpModel.ExternalPhpFilesRegistry;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.wst.html.core.internal.document.DOMStyleModelImpl;
import org.eclipse.wst.xml.core.internal.document.XMLModelParser;
import org.eclipse.wst.xml.core.internal.document.XMLModelUpdater;
import org.w3c.dom.Document;

/*
 * The PHPModel will support both the DOM style interface and PHP specific API's.
 */
public class DOMModelForPHP extends DOMStyleModelImpl {

	public static final boolean FREQUENT_MODEL_UPDATE = true;

	/*
	 * This is modeled after what is done for JSP
	 */
	protected Document internalCreateDocument() {
		DOMDocumentForPHP document = new DOMDocumentForPHP();
		document.setModel(this);
		return document;
	}

	protected XMLModelParser createModelParser() {
		return new PHPDOMModelParser(this);
	}

	protected XMLModelUpdater createModelUpdater() {
		return new PHPDOMModelUpdater(this);
	}

	// @GINO: PHP API's coming soon

	/*
	 * Always get the latest version of FileData
	 */
	public PHPFileData getFileData() {
		return getFileData(false);

	}

	/**
	 * @param forceCreation - if we want to create a model project as well
	 * @return fileData
	 */
	public PHPFileData getFileData(boolean forceCreation) {
		PHPFileData fileData = null;
		IFile file = getIFile();

		if (file != null) {
			if (projectModel != null) {
				fileData = projectModel.getFileData(file.getFullPath().toString());
			}
		}
		if (fileData == null) {
			fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(getBaseLocation(), forceCreation);
			if (fileData != null) {
				return fileData;
			}
		}

		// external file
		if (ExternalPhpFilesRegistry.getInstance().isEntryExist(file.getFullPath().toString())) {
			fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(getBaseLocation());
			return fileData;
		}

		return fileData;
	}

	PHPProjectModel projectModel;

	public PHPProjectModel getProjectModel() {
		if (projectModel != null) {
			return projectModel;
		}

		IFile iFile = getIFile();
		if (ExternalPhpFilesRegistry.getInstance().isEntryExist(iFile.getFullPath().toString())) {
			return PHPWorkspaceModelManager.getDefaultPHPProjectModel();
		}

		PHPFileData fileData = getFileData();
		if (fileData != null)
			projectModel = PHPModelUtil.getProjectModelForFile(fileData);
		else
			projectModel = null;
		return projectModel;
	}

	public void updateFileData() {

		IFile file = getIFile();

		if (file != null) {
			PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());

			if (projectModel != null && file.exists()) {
				projectModel.fileWasChanged(file, getStructuredDocument());
			}

			// external file
			else if (ExternalPhpFilesRegistry.getInstance().isEntryExist(file.getFullPath().toString())) {
				projectModel = PHPWorkspaceModelManager.getDefaultPHPProjectModel();
				projectModel.fileWasChanged(file, getStructuredDocument());
			}
		}
	}

	public void changedModel() {
		if (FREQUENT_MODEL_UPDATE)
			updateFileData();
		super.changedModel();
	}

	// returns the IFile corresponding with this model
	public IFile getIFile() {

		String path = getBaseLocation();
		if (path == null || path.length() == 0) {
			Object id = getId();
			if (id == null)
				return null;
			path = id.toString();
		}
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile result = (IFile) root.findMember(new Path(path));
		if (result != null) {
			return result;
		}
		result = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
		return result;
	}
}
