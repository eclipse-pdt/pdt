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
package org.eclipse.php.internal.core.resources;


/**
 * A listenr for changes occured in the {@link ExternalFilesRegistry}.
 * 
 * @author shalom
 */
public interface ExternalFilesRegistryListener {
	
	/**
	 * Notifies that an external file was added to the external PHP files registry.
	 * 
	 * @param localPath	The String representation of the real File's path from file system
	 */
	public void externalFileAdded(String localPath);
	
	/**
	 * Notifies that an external file was removed from the external PHP files registry.
	 * 
	 * @param localPath	The String representation of the real File's path from file system
	 */
	public void externalFileRemoved(String localPath);
}
