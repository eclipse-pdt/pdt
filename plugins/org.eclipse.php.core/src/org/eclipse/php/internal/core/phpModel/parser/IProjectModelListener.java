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
package org.eclipse.php.internal.core.phpModel.parser;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;

public interface IProjectModelListener {

	public void fileAdded(IFile file);

	public void fileRemoved(IFile file);

	public void fileChanged(IFile file, IDocument sDocument);

}
