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
/**
 * 
 */
package org.eclipse.php.internal.core.filesystem;

import java.io.File;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.php.internal.core.CoreMessages;

/**
 * The FileStoreFactory is a factory for the creation of {@link IFileStore} instances.
 * The createFileStore of this factory determines if the given {@link File} is local or external
 * and then creates the appropriate {@link IFileStore}.
 * 
 * @author Shalom Gibly
 */
public class FileStoreFactory {

	/**
	 * Creates an {@link IFileStore} for the given {@link File}. 
	 * In case that the given file does not exist, the returned {@link IFileStore} is an 
	 * instance of {@link ExternalFileStore}. In any other case, 
	 * the returned store is a {@link LocalFile}.
	 * 
	 * @param file An existing or non existing java.io.File.
	 * @return An instance of {@link IFileStore}.
	 * @throws IllegalArgumentException in case the given file is null.
	 */
	public static IFileStore createFileStore(File file) throws IllegalArgumentException {
		if (file == null) {
			throw new IllegalArgumentException(CoreMessages.getString("FileStoreFactory_0"));
		}
		if (file.exists()) {
			return new LocalFile(file);
		}
		return new ExternalFileStore(file);
	}
}
