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
package org.eclipse.php.internal.ui.editor.input;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.ast.util.Util;
import org.eclipse.ui.editors.text.ILocationProviderExtension;
import org.eclipse.ui.internal.editors.text.NonExistingFileEditorInput;

/**
 * This Editor Input class should be used mainly for Untitled PHP Documents
 * 
 * @see org.eclipse.ui.internal.editors.text.NonExistingFileEditorInput
 */
public class NonExistingPHPFileEditorInput extends NonExistingFileEditorInput
		implements ILocationProviderExtension {

	private static final Map<IPath, NonExistingPHPFileEditorInput> registry = Collections
			.synchronizedMap(new HashMap<IPath, NonExistingPHPFileEditorInput>());

	public NonExistingPHPFileEditorInput(IFileStore fileStore, String namePrefix) {
		super(fileStore, namePrefix);
		registry.put(getPath(this), this);
	}

	@Override
	public IPath getPath(Object element) {
		IPath path = super.getPath(element);
		return path.addFileExtension(Util.defaultPhpExtension());
	}

	@Override
	public String getName() {
		String result = super.getName();
		return result + "." + Util.defaultPhpExtension(); //$NON-NLS-1$
	}

	public URI getURI(Object element) {
		IPath path = getPath(element);
		if (path != null) {
			return URIUtil.toURI(path);
		}
		return null;
	}

	/**
	 * Finds non-existing PHP file editor input by the real path to the
	 * temporary file.
	 * 
	 * @param path
	 * @return
	 */
	public static NonExistingPHPFileEditorInput findEditorInput(IPath path) {
		return registry.get(path);
	}

	/**
	 * Destroys instance of non-existing PHP file editor input by the real path
	 * to the temporary file.
	 * 
	 * @param path
	 * @return
	 */
	public static void dispose(IPath path) {
		registry.remove(path);
	}
}
