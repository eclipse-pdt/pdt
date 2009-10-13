/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;

public class PHPFileSourceContainer extends AbstractSourceContainer {

	private File fFile;
	private LocalFileStorage fStorage;

	public PHPFileSourceContainer(File file) {
		fFile = file;
		fStorage = new LocalFileStorage(fFile);
	}

	public Object[] findSourceElements(String name) throws CoreException {
		return new Object[] { fStorage };
	}

	public String getName() {
		return fFile.getName();
	}

	public ISourceContainerType getType() {
		return null;
	}

}
