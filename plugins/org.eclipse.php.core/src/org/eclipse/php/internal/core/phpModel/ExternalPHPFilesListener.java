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

/**
 * A listenr for changes occured in the {@link ExternalPhpFilesRegistry}.
 * 
 * @author shalom
 */
public interface ExternalPHPFilesListener {
	
	/**
	 * Notifies that an external file was added to the external PHP files registry.
	 * 
	 * @param iFilePath	The String representation of the IFile's path
	 * @param localPath	The String representation of the real File's path from file system
	 */
	public void externalFileAdded(String iFilePath, String localPath);
	
	/**
	 * Notifies that an external file was removed from the external PHP files registry.
	 * 
	 * @param iFilePath	The String representation of the IFile's path
	 * @param localPath	The String representation of the real File's path from file system
	 */
	public void externalFileRemoved(String iFilePath, String localPath);
}
