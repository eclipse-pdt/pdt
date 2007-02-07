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

/**
 * PHPClassDataDecorator is a simple proxy decorator that implements PHPClassConstData. 
 * This class is needed for identifying the source of this proxy as a PHPClassConstData.
 * 
 * @author shalom
 */
public class PHPClassConstantDataDecorator extends PHPDataDecorator implements PHPClassConstData {

	private PHPClassConstData source;

	public PHPClassConstantDataDecorator(PHPClassConstData source, IProject project, boolean isLeaf) {
		super(source, project, isLeaf);
		this.source = source;
	}

	public PHPClassConstantDataDecorator(PHPClassConstData source, IProject project) {
		super(source, project);
		this.source = source;
	}

//	public String getValue() {
//		return source.getValue();
//	}

}
