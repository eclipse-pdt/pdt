/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableData;

/**
 * PHPVariableDataDecorator is a simple proxy decorator that implements PHPVariableData. 
 * This class is needed for identifying the source of this proxy as a PHPVariableData.
 * 
 * @author shalom
 */
public class PHPVariableDataDecorator extends PHPDataDecorator implements PHPVariableData {
	
	private PHPVariableData source;

	public PHPVariableDataDecorator(PHPVariableData source, IProject project, boolean isLeaf) {
		super(source, project, isLeaf);
		this.source = source;
	}

	public PHPVariableDataDecorator(PHPVariableData source, IProject project) {
		super(source, project);
		this.source = source;
	}

	public boolean isGlobal() {
		return source.isGlobal();
	}

}
