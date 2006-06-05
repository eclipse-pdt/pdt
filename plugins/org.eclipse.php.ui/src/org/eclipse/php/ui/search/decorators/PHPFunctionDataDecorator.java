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
package org.eclipse.php.ui.search.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;

/**
 * PHPFunctionDataDecorator is a simple proxy decorator that implements PHPFunctionData. 
 * This class is needed for identifying the source of this proxy as a PHPFunctionData.
 * 
 * @author shalom
 */
public class PHPFunctionDataDecorator extends PHPDataDecorator implements PHPFunctionData {

	private PHPFunctionData source;

	public PHPFunctionDataDecorator(PHPFunctionData source, IProject project) {
		super(source, project);
		this.source = source;
	}

	public PHPFunctionDataDecorator(PHPFunctionData source, IProject project, boolean isLeaf) {
		super(source, project, isLeaf);
		this.source = source;
	}

	public PHPFunctionParameter[] getParameters() {
		return source.getParameters();
	}

	public String getReturnType() {
		return source.getReturnType();
	}

	public int getModifiers() {
		return source.getModifiers();
	}

}
