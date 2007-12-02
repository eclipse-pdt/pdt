/***********************************************************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html Contributors: Zend and IBM - Initial implementation
 **********************************************************************************************************************/
package org.eclipse.php.internal.core.documentModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.documentModel.dom.DOMDocumentForPHP;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelParser;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelUpdater;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.wst.html.core.internal.document.DOMStyleModelImpl;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.xml.core.internal.document.XMLModelParser;
import org.eclipse.wst.xml.core.internal.document.XMLModelUpdater;
import org.w3c.dom.Document;

/*
 * The PHPModel will support both the DOM style interface and PHP specific API's.
 */
public class DOMModelForPHP extends DOMStyleModelImpl {

	/*
	 * This is modeled after what is done for JSP
	 */
	@Override
	protected Document internalCreateDocument() {
		DOMDocumentForPHP document = new DOMDocumentForPHP();
		document.setModel(this);
		return document;
	}

	@Override
	protected XMLModelParser createModelParser() {
		return new PHPDOMModelParser(this);
	}

	@Override
	protected XMLModelUpdater createModelUpdater() {
		return new PHPDOMModelUpdater(this);
	}

	/**
	 * Always get the latest version of FileData
	 */
	public PHPFileData getFileData() {
		return getFileData(false);

	}

	/**
	 * Getting the latest fileData
	 * 
	 * @param forceCreation - if we want to create a model project as well
	 * @return fileData
	 * 
	 * IMPORTANT: The fileData will not be created if the projectModel was not initiated first.
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
			fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(getBaseLocation(), projectModel != null && forceCreation);
			if (fileData != null) {
				return fileData;
			}
		}

		// external file
		if (file != null && ExternalFilesRegistry.getInstance().isEntryExist(file)) {
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
		if (ExternalFilesRegistry.getInstance().isEntryExist(iFile)) {
			return PHPWorkspaceModelManager.getDefaultPHPProjectModel();
		}

		PHPFileData fileData = getFileData();
		projectModel = fileData != null ? PHPModelUtil.getProjectModelForFile(fileData) : null;
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
			else if (ExternalFilesRegistry.getInstance().isEntryExist(file)) {
				projectModel = PHPWorkspaceModelManager.getDefaultPHPProjectModel();
				projectModel.fileWasChanged(file, getStructuredDocument());
			}
		}
	}

	// returns the IFile corresponding with this model
	public IFile getIFile() {
		String id = getId();
		if (IModelManager.UNMANAGED_MODEL.equals(id)) {
			//This ID is used in cases where DOMModel is build for text not coming
			//from files. Like a part of a document or a file.
			return null;
		}

		String path = getBaseLocation();
		if (path == null || path.length() == 0) {
			if (id == null) {
				return null;
			}
			path = id.toString();
		}
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile result = (IFile) root.findMember(new Path(path));
		if (result != null) {
			return result;
		}
		if (ExternalFilesRegistry.getInstance().isEntryExist(path)) {
			result = ExternalFilesRegistry.getInstance().getFileEntry(path);
		}
		if (result == null) {
			if (Platform.getOS() != Platform.OS_WIN32) {
				path = path.replace('\\', '/');
			}
			IPath osPath = Path.fromOSString(path);
			if(osPath.segmentCount() >= 2){
				result = ResourcesPlugin.getWorkspace().getRoot().getFile(osPath);
			}
		}
		return result;
	}
}
