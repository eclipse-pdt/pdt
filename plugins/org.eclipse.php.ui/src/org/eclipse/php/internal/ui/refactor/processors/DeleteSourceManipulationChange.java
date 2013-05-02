/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.refactoring.changes.UndoDeleteResourceChange;
import org.eclipse.dltk.internal.corext.refactoring.util.ModelElementUtil;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ui.ide.undo.ResourceDescription;

public class DeleteSourceManipulationChange extends AbstractDeleteChange {

	private final String fHandle;
	private final boolean fIsExecuteChange;

	public DeleteSourceManipulationChange(ISourceManipulation sm,
			boolean isExecuteChange) {
		Assert.isNotNull(sm);
		fHandle = getScriptElement(sm).getHandleIdentifier();
		fIsExecuteChange = isExecuteChange;
	}

	/*
	 * @see IChange#getName()
	 */
	public String getName() {
		return Messages.format(
				RefactoringCoreMessages.DeleteSourceManipulationChange_0,
				getElementName());
	}

	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException {
		// delete changes don't provide an undo operation
		ISourceManipulation element = getSourceModification();
		if (fIsExecuteChange) {
			if (element instanceof ISourceModule) {
				// don't check anything in this case. We have a warning dialog
				// already presented to the user that the file is dirty.
				return super.isValid(pm, NONE);
			} else {
				return super.isValid(pm, DIRTY);
			}
		} else {
			return super.isValid(pm, READ_ONLY | DIRTY);
		}
	}

	private String getElementName() {
		IModelElement modelElement = getScriptElement(getSourceModification());
		if (ModelElementUtil.isDefaultPackage(modelElement))
			return RefactoringCoreMessages.DeleteSourceManipulationChange_1;
		return modelElement.getElementName();
	}

	/*
	 * @see IChange#getModifiedLanguageElement()
	 */
	public Object getModifiedElement() {
		return DLTKCore.create(fHandle);
	}

	/*
	 * @see DeleteChange#doDelete(IProgressMonitor)
	 */
	protected Change doDelete(IProgressMonitor pm) throws CoreException {
		ISourceManipulation element = getSourceModification();
		// we have to save dirty compilation units before deleting them.
		// Otherwise
		// we will end up showing ghost compilation units in the package
		// explorer
		// since the primary working copy still exists.
		if (element instanceof ISourceModule) {
			pm.beginTask("", 2); //$NON-NLS-1$ 
			ISourceModule unit = (ISourceModule) element;
			saveCUnitIfNeeded(unit, new SubProgressMonitor(pm, 1));
			// element.delete(false, new SubProgressMonitor(pm, 1));

			IResource resource = unit.getResource();
			if (resource != null) {
				ResourceDescription resourceDescription = ResourceDescription
						.fromResource(resource);
				element.delete(false, new SubProgressMonitor(pm, 1));
				resourceDescription.recordStateFromHistory(resource,
						new SubProgressMonitor(pm, 1));
				return new UndoDeleteResourceChange(resourceDescription);
			} else {
				element.delete(false, pm);
			}
			return null;

			// begin fix https://bugs.eclipse.org/bugs/show_bug.cgi?id=66835
		} else if (element instanceof IScriptFolder) {
			ISourceModule[] units = ((IScriptFolder) element)
					.getSourceModules();
			pm.beginTask("", units.length + 1); //$NON-NLS-1$ 
			for (int i = 0; i < units.length; i++) {
				saveCUnitIfNeeded(units[i], new SubProgressMonitor(pm, 1));
			}
			// work around for
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=292651
			// There's an issue when deleting the IScriptFolder by using
			// model.delete method.
			delete(false, new SubProgressMonitor(pm, 1),
					(IScriptFolder) element);
			return new NullChange();
			// end fix https://bugs.eclipse.org/bugs/show_bug.cgi?id=66835
		} else {
			element.delete(false, pm);
			return null;
		}
	}

	public void delete(boolean force, IProgressMonitor monitor,
			IModelElement element) throws ModelException {
		IModelElement[] elements = new IModelElement[] { element };
		if (elements != null && elements.length > 0 && elements[0] != null
				&& elements[0].getElementType() < IModelElement.TYPE) {
			new DeleteResourceElementsOperation(elements, force)
					.runOperation(monitor);
		}
	}

	private ISourceManipulation getSourceModification() {
		return (ISourceManipulation) getModifiedElement();
	}

	private static IModelElement getScriptElement(ISourceManipulation sm) {
		// all known ISourceManipulations are IModelElements
		return (IModelElement) sm;
	}

	private static void saveCUnitIfNeeded(ISourceModule unit,
			IProgressMonitor pm) throws CoreException {
		if (unit.getResource() != null) {
			saveFileIfNeeded((IFile) unit.getResource(), pm);
		}
	}
}
