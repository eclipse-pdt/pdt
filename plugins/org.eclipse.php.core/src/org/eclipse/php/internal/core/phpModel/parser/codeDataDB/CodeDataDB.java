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
package org.eclipse.php.internal.core.phpModel.parser.codeDataDB;

import java.util.Collection;
import java.util.List;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public interface CodeDataDB {

	public void clear();

	public Collection<CodeData> getCodeData(String name);

	public void addCodeData(CodeData codeData);

	public void removeCodeData(CodeData codeData);

	public List<CodeData> asList();

}