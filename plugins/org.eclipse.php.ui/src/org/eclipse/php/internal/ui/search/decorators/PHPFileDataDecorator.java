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
package org.eclipse.php.internal.ui.search.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

/**
 * PHPFileDataDecorator is a simple proxy decorator that implements PHPFileData. 
 * This class is needed for identifying the source of this proxy as a PHPFileData.
 * 
 * @author shalom
 */
public class PHPFileDataDecorator extends PHPDataDecorator implements PHPFileData {

	private PHPFileData source;

	public PHPFileDataDecorator(PHPFileData source, IProject project, boolean isLeaf) {
		super(source, project, isLeaf);
		this.source = source;
	}

	public PHPFileDataDecorator(PHPFileData source, IProject project) {
		super(source, project);
		this.source = source;
	}

	public PHPClassData[] getClasses() {
		return source.getClasses();
	}

	public PHPFunctionData[] getFunctions() {
		return source.getFunctions();
	}

	public PHPVariablesTypeManager getVariableTypeManager() {
		return source.getVariableTypeManager();
	}

	public PHPIncludeFileData[] getIncludeFiles() {
		return source.getIncludeFiles();
	}

	public IPHPMarker[] getMarkers() {
		return source.getMarkers();
	}

	public PHPBlock[] getPHPBlocks() {
		return source.getPHPBlocks();
	}

	public PHPConstantData[] getConstants() {
		return source.getConstants();
	}

	public long getCreationTimeLastModified() {
		return source.getCreationTimeLastModified();
	}

	public String getComparableName() {
		return source.getComparableName();
	}

	public Object getIdentifier() {
		return source.getIdentifier();
	}

	public boolean isValid() {
		return source.isValid();
	}

}
