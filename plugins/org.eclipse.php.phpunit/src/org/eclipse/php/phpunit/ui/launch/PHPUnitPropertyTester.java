/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.ui.IFileEditorInput;

public class PHPUnitPropertyTester extends PropertyTester {

	private static final String PROPERTY = "testablePHP"; //$NON-NLS-1$

	/**
	 * true only if receiver is a PHP model element and is project or folder, or
	 * file with extension xml/php/php4/php5, or a method
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!PROPERTY.equals(property)) {
			return false;
		}

		if (!isModelElement(receiver)) {
			return false;
		}

		IResource res = getReceiverResource(receiver);

		if (res instanceof IProject) {
			try {
				return PHPToolkitUtil.isPHPProject((IProject) res);
			} catch (CoreException e) {
				return false;
			}
		} else if (res instanceof IFile) {
			String ext = ((IFile) res).getFileExtension();
			if (ext == null) {
				return false;
			}
			ext = ext.toLowerCase();

			String[] acceptedExts = new String[] { "xml", "php", "php5", "php4" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			if (ext != null && Arrays.asList(acceptedExts).contains(ext)) {
				return true;
			}
		} else { // always can run PHPUnit project or folder
			return true;
		}

		return false;
	}

	private IResource getReceiverResource(Object receiver) {
		if (receiver instanceof List) {
			List<?> list = (List<?>) receiver;
			if (list.size() == 1) {
				receiver = list.get(0);
			}
		}

		if (receiver instanceof IFileEditorInput) {
			IFileEditorInput ei = (IFileEditorInput) receiver;
			receiver = ei.getFile();
		}

		if (receiver instanceof ISourceModule) {
			return ((ISourceModule) receiver).getResource();
		}

		if (receiver instanceof IFile) {
			return (IResource) receiver;
		}

		if (receiver instanceof IProject) {
			return (IProject) receiver;
		}

		if (receiver instanceof IScriptProject) {
			return ((IScriptProject) receiver).getProject();
		}

		return null;
	}

	private boolean isModelElement(Object receiver) {
		if (receiver instanceof List) {
			List<?> list = (List<?>) receiver;
			for (Object item : list) {
				if (!(item instanceof IModelElement)) {
					if (item instanceof IFileEditorInput) {
						IFileEditorInput fileEditorInput = (IFileEditorInput) item;
						return PHPToolkitUtil.isPHPFile(fileEditorInput.getFile());
					} else if (item instanceof IResource) {
						IResource folder = (IResource) item;
						IProject project = folder.getProject();
						try {
							return PHPToolkitUtil.isPHPProject(project);
						} catch (CoreException e) {
							PHPCorePlugin.log(e);
						}
					}
					return false;
				}
			}
		}
		return true;
	}

}
