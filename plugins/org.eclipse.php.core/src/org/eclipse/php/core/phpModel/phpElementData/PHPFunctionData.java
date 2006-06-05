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

public interface PHPFunctionData extends PHPCodeData {

	public PHPFunctionParameter[] getParameters();

	public String getReturnType();

	public int getModifiers();

	public static interface PHPFunctionParameter extends PHPVariableData {

		public boolean isReference();

		public boolean isConst();

		public String getClassType();

		public void setClassType(String classType);

		public String getDefaultValue();

	}

}