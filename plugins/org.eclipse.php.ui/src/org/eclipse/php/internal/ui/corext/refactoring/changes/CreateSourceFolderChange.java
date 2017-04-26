/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.refactoring.changes;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourceChange;
import org.eclipse.ltk.core.refactoring.resource.ResourceChange;
import org.eclipse.php.internal.ui.corext.refactoring.RefactoringCoreMessages;

public class CreateSourceFolderChange extends ResourceChange {

	private IScriptFolder fPackageFragment;

	public CreateSourceFolderChange(IScriptFolder pack) {
		fPackageFragment = pack;
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		try {
			pm.beginTask(RefactoringCoreMessages.CreatePackageChange_Creating_package, 1);

			if (fPackageFragment.exists()) {
				return new NullChange();
			} else {
				IProjectFragment root = (IProjectFragment) fPackageFragment.getParent();
				root.createScriptFolder(fPackageFragment.getElementName(), false, pm);

				return new DeleteResourceChange(fPackageFragment.getPath(), true);
			}
		} finally {
			pm.done();
		}
	}

	@Override
	public String getName() {
		return RefactoringCoreMessages.CreatePackageChange_Create_package;
	}

	@Override
	public Object getModifiedElement() {
		return fPackageFragment;
	}

	@Override
	protected IResource getModifiedResource() {
		return fPackageFragment.getResource();
	}
}
