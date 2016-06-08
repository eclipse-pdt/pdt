/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.test;

import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.wst.validation.ValidationFramework;

public final class RefactoringTestsSupport {

	public static void setUp() {
		ValidationFramework.getDefault().suspendAllValidation(true);
		if (ResourcesPlugin.getWorkspace().isAutoBuilding()) {
			IWorkspaceDescription workspaceDescription = ResourcesPlugin.getWorkspace().getDescription();
			workspaceDescription.setAutoBuilding(false);
			try {
				ResourcesPlugin.getWorkspace().setDescription(workspaceDescription);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

	public static void tearDown() {
		if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
			IWorkspaceDescription workspaceDescription = ResourcesPlugin.getWorkspace().getDescription();
			workspaceDescription.setAutoBuilding(true);
			try {
				ResourcesPlugin.getWorkspace().setDescription(workspaceDescription);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		ValidationFramework.getDefault().suspendAllValidation(false);
	}

}
