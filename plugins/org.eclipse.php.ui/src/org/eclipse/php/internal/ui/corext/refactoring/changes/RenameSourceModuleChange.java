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
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.php.internal.ui.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.php.internal.ui.util.Messages;

public final class RenameSourceModuleChange extends AbstractElementRenameChange {

	public RenameSourceModuleChange(ISourceModule unit, String newName) {
		this(unit.getResource().getFullPath(), unit.getElementName(), newName, IResource.NULL_STAMP);
		Assert.isTrue(!unit.isReadOnly(), "source module must not be read-only"); //$NON-NLS-1$
	}

	private RenameSourceModuleChange(IPath resourcePath, String oldName, String newName, long stampToRestore) {
		super(resourcePath, oldName, newName, stampToRestore);

		setValidationMethod(VALIDATE_NOT_READ_ONLY | SAVE_IF_DIRTY);
	}

	@Override
	protected IPath createNewPath() {
		final IPath path = getResourcePath();
		if (path.getFileExtension() != null)
			return path.removeFileExtension().removeLastSegments(1).append(getNewName());
		else
			return path.removeLastSegments(1).append(getNewName());
	}

	@Override
	protected Change createUndoChange(long stampToRestore) {
		return new RenameSourceModuleChange(createNewPath(), getNewName(), getOldName(), stampToRestore);
	}

	@Override
	protected void doRename(IProgressMonitor pm) throws CoreException {
		ISourceModule cu = (ISourceModule) getModifiedElement();
		if (cu != null)
			cu.rename(getNewName(), false, pm);
	}

	@Override
	public String getName() {
		String[] keys = new String[] { getOldName(), getNewName() };
		return Messages.format(RefactoringCoreMessages.RenameSourceModuleChange_name, keys);
	}

}