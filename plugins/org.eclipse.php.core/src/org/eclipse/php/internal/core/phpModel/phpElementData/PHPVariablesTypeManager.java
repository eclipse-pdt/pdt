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
package org.eclipse.php.internal.core.phpModel.phpElementData;


import java.util.Map;

import org.eclipse.php.internal.core.phpModel.parser.PHPCodeContext;

public interface PHPVariablesTypeManager {

	public PHPVariableTypeData getVariableTypeData(PHPCodeContext context, String variableName, int line);

	public PHPVariableTypeData getVariableTypeDataByPosition(PHPCodeContext context, String variableName, int position);

	public PHPVariableData[] getVariables(PHPCodeContext context);

	public PHPVariableData getVariable(PHPCodeContext context, String variableName);

	public Map getContextsToVariables();

	public Map getVariablesInstansiation();
}