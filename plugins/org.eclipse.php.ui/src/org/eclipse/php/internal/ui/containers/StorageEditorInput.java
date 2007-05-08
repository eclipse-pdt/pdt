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
package org.eclipse.php.internal.ui.containers;

 
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

public abstract class StorageEditorInput extends PlatformObject implements IStorageEditorInput {

	/**
	 * Storage associated with this editor input
	 */
	private IStorage fStorage;
	
	/**
	 * Constructs an editor input on the given storage
	 */
	public StorageEditorInput(IStorage storage) {
		fStorage = storage;
	}
	
	/**
	 * @see IStorageEditorInput#getStorage()
	 */
	public IStorage getStorage() {
		return fStorage;
	}

	/**
	 * @see IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return PHPUiPlugin.getImageDescriptor(PHPPluginImages.IMG_OBJS_CUNIT);
	}

	/**
	 * @see IEditorInput#getName()
	 */
	public String getName() {
		return getStorage().getName();
	}

	/**
	 * @see IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 * @see IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return getStorage().getFullPath().toString();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object) {
		return object instanceof StorageEditorInput &&
		 getStorage().equals(((StorageEditorInput)object).getStorage());
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getStorage().hashCode();
	}

}
