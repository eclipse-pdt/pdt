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
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.internal.ui.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.php.internal.ui.util.Messages;

public class MoveSourceModuleChange extends SourceModuleReorgChange {

	private boolean fUndoable;
	private long fStampToRestore;

	public MoveSourceModuleChange(ISourceModule cu, IScriptFolder dest) {
		super(cu, dest);
		fStampToRestore = IResource.NULL_STAMP;

	}

	private MoveSourceModuleChange(IScriptFolder oldPackage, String cuName, IScriptFolder newPackage,
			long stampToRestore) {
		super(oldPackage.getHandleIdentifier(), newPackage.getHandleIdentifier(),
				oldPackage.getSourceModule(cuName).getHandleIdentifier());
		fStampToRestore = stampToRestore;

	}

	@Override
	public String getName() {
		return Messages.format(RefactoringCoreMessages.MoveSourceModuleChange_name,
				new String[] { BasicElementLabels.getFileName(getCu()), getPackageName(getDestinationPackage()) });
	}

	@Override
	Change doPerformReorg(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		String name;
		String newName = getNewName();
		if (newName == null)
			name = getCu().getElementName();
		else
			name = newName;

		// get current modification stamp
		long currentStamp = IResource.NULL_STAMP;
		IResource resource = getCu().getResource();
		if (resource != null) {
			currentStamp = resource.getModificationStamp();
		}

		IScriptFolder destination = getDestinationPackage();
		fUndoable = !destination.getSourceModule(name).exists();

		// perform the move and restore modification stamp
		getCu().move(destination, null, newName, true, pm);
		if (fStampToRestore != IResource.NULL_STAMP) {
			ISourceModule moved = destination.getSourceModule(name);
			IResource movedResource = moved.getResource();
			if (movedResource != null) {
				movedResource.revertModificationStamp(fStampToRestore);
			}
		}

		if (fUndoable) {
			return new MoveSourceModuleChange(destination, getCu().getElementName(), getOldPackage(), currentStamp);
		} else {
			return null;
		}
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return null;
	}
}
