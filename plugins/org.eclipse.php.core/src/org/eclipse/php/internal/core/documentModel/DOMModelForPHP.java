/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.documentModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptModel;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalScriptProject;
import org.eclipse.php.internal.core.documentModel.dom.DOMDocumentForPHP;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelParser;
import org.eclipse.php.internal.core.documentModel.dom.PHPDOMModelUpdater;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.wst.html.core.internal.document.DOMStyleModelImpl;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.xml.core.internal.Logger;
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
		
		// Try to fix the path and then try again to look into workspace:
		if (result == null) {
			if (Platform.getOS() != Platform.OS_WIN32) {
				path = path.replace('\\', '/');
			}
			IPath osPath = Path.fromOSString(path);
			if (osPath.segmentCount() > 1) {
				result = (IFile) root.findMember(osPath);
			}
		}
		return result;
	}
	
	/**
	 * Resolves the source module of the given DOM module
	 * @return source module element
	 */
	public IModelElement getModelElement() {
		IFile file = getIFile();
		if (file != null) {
			return DLTKCore.create(file);
		}
		
		return null;
	}
}
