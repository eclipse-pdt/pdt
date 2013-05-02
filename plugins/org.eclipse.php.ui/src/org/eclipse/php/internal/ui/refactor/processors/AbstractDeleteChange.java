/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.refactoring.base.DLTKChange;
import org.eclipse.ltk.core.refactoring.Change;

abstract class AbstractDeleteChange extends DLTKChange {

	protected abstract Change doDelete(IProgressMonitor pm)
			throws CoreException;

	/*
	 * non java-doc
	 * 
	 * @see IChange#perform(ChangeContext, IProgressMonitor)
	 */
	public final Change perform(IProgressMonitor pm) throws CoreException {
		try {
			pm.beginTask(RefactoringCoreMessages.AbstractDeleteChange_deleting,
					1);
			Change undo = doDelete(pm);
			return undo;
		} finally {
			pm.done();
		}
	}

	protected static void saveFileIfNeeded(IFile file, IProgressMonitor pm)
			throws CoreException {
		ITextFileBuffer buffer = FileBuffers.getTextFileBufferManager()
				.getTextFileBuffer(file.getFullPath(), LocationKind.NORMALIZE);
		if (buffer != null && buffer.isDirty() && buffer.isStateValidated()
				&& buffer.isSynchronized()) {
			pm.beginTask("", 2); //$NON-NLS-1$ 
			buffer.commit(new SubProgressMonitor(pm, 1), false);
			file.refreshLocal(IResource.DEPTH_ONE,
					new SubProgressMonitor(pm, 1));
			pm.done();
		} else {
			pm.beginTask("", 1); //$NON-NLS-1$ 
			pm.worked(1);
			pm.done();
		}
	}
}
