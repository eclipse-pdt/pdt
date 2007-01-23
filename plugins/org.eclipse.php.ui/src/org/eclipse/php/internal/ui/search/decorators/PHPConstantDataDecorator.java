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
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPConstantData;

/**
 * PHPClassDataDecorator is a simple proxy decorator that implements PHPConstantData. 
 * This class is needed for identifying the source of this proxy as a PHPConstantData.
 * 
 * @author shalom
 */
public class PHPConstantDataDecorator extends PHPDataDecorator implements PHPConstantData {

	private PHPConstantData source;

	public PHPConstantDataDecorator(PHPConstantData source, IProject project, boolean isLeaf) {
		super(source, project, isLeaf);
		this.source = source;
	}

	public PHPConstantDataDecorator(PHPConstantData source, IProject project) {
		super(source, project);
		this.source = source;
	}

	public String getValue() {
		return source.getValue();
	}

}
