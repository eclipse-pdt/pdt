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
package org.eclipse.php.internal.core.phpModel;

import org.eclipse.php.internal.core.phpModel.parser.IPhpModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPKeywordData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableData;

public interface IPHPLanguageModel extends IPhpModel {

	public String getPHPVersion();
	
	public PHPKeywordData[] getKeywordData();

	public PHPVariableData[] getServerVariables();

	public PHPVariableData[] getSessionVariables();

	public PHPVariableData[] getPHPVariables();

}
