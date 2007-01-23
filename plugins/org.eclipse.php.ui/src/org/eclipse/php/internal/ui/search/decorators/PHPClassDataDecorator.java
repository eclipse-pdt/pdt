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
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;

/**
 * PHPClassDataDecorator is a simple proxy decorator that implements PHPClassData. 
 * This class is needed for identifying the source of this proxy as a PHPClassData.
 * 
 * @author shalom
 */
public class PHPClassDataDecorator extends PHPDataDecorator implements PHPClassData {

	private PHPClassData source;

	public PHPClassDataDecorator(PHPClassData source, IProject project) {
		super(source, project);
		this.source = source;
	}

	public PHPClassDataDecorator(PHPClassData source, IProject project, boolean isLeaf) {
		super(source, project, isLeaf);
		this.source = source;
	}

	public PHPSuperClassNameData getSuperClassData() {
		return source.getSuperClassData();
	}

	public PHPInterfaceNameData[] getInterfacesNamesData() {
		return source.getInterfacesNamesData();
	}

	public PHPClassVarData[] getVars() {
		return source.getVars();
	}

	public PHPClassConstData[] getConsts() {
		return source.getConsts();
	}

	public PHPFunctionData[] getFunctions() {
		return source.getFunctions();
	}

	public boolean hasConstructor() {
		return source.hasConstructor();
	}

	public PHPFunctionData getConstructor() {
		return source.getConstructor();
	}

	public int getModifiers() {
		return source.getModifiers();
	}

}
