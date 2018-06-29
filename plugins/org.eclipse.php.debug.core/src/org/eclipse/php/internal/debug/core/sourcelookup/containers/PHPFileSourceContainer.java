/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	@Override
	public Object[] findSourceElements(String name) throws CoreException {
		return new Object[] { fStorage };
	}

	@Override
	public String getName() {
		return fFile.getName();
	}

	@Override
	public ISourceContainerType getType() {
		return null;
	}

}
