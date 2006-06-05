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
package org.eclipse.php.core.phpModel.phpElementData;

public class PHPTask extends PHPMarker {

	public static final String RULER_PHP_TASK = "rulerPHPTask";
	private String taskName;
	
	public PHPTask(String taskName, String description, UserData userData) {
		super(IPHPMarker.TASK, description, userData);
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}
		
}