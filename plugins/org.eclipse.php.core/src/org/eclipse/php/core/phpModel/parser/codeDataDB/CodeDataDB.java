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
package org.eclipse.php.core.phpModel.parser.codeDataDB;


import java.util.List;

import org.eclipse.php.core.phpModel.phpElementData.CodeData;

public interface CodeDataDB {

	public void clear();

	public List getCodeData(String name);

	public void addCodeData(CodeData codeData);

	public void removeCodeData(CodeData codeData);

	public List asList();

}