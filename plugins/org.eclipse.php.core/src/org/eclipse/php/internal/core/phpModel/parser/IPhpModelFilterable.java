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
package org.eclipse.php.internal.core.phpModel.parser;

import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public interface IPhpModelFilterable extends IPhpModel {

	void setFilter(IPhpModelFilter filter);

	CodeData[] getFilteredClasses(String fileName, String className);

	CodeData[] getFilteredFunctions(String fileName, String className);

	CodeData[] getFilteredConstants(String fileName, String className);

	public interface IPhpModelFilter extends IWorkspaceModelListener {
		boolean select(IPhpModelFilterable model, CodeData element, Object data);
	}
}
