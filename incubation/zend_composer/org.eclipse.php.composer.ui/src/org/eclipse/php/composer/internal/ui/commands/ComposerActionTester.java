/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.commands;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.internal.core.documentModel.dom.ElementImplForPhp;

/**
 * Property tester for testing which Composer action should be available.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ComposerActionTester extends PropertyTester {

	private static final String IS_INSTALLED = "isInstalled"; //$NON-NLS-1$
	private static final String HAS_AUTOLOAD = "hasAutoload"; //$NON-NLS-1$
	private static final String HAS_SUPPORT = "hasSupport"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		IProject project = null;
		if (receiver instanceof IScriptProject) {
			project = ((IScriptProject) receiver).getProject();
		} else if (receiver instanceof IFile) {
			if (ComposerService.isComposerJson((IFile) receiver)) {
				project = ((IFile) receiver).getProject();
			}
		}
		if (receiver instanceof ElementImplForPhp) {
			IModelElement element = ((ElementImplForPhp) receiver)
					.getModelElement();
			if (element != null) {
				IResource res = element.getResource();
				if (res != null) {
					project = res.getProject();
				}
			}
		}

		if (project != null) {
			boolean result = ComposerService.isComposerProject(project);
			if (HAS_SUPPORT.equals(property)) {
				// do nothing, just skip other conditions
			} else if (HAS_AUTOLOAD.equals(property)) {
				IContainer vendor = ComposerService.getVendor(project);
				if (vendor.exists()) {
					IFile autoload = vendor.getFile(new Path(
							InjectAutoloadHandler.AUTOLOAD_PHP));
					result = result && autoload.exists();
				} else {
					result = result && false;
				}

			} else if (IS_INSTALLED.equals(property)) {
				result = result && ComposerService.isInstalled(project);
			}
			return result == (Boolean) expectedValue;
		}
		return false;
	}
}
