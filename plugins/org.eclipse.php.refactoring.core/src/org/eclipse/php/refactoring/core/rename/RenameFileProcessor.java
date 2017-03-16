/*******************************************************************************
 * Copyright (c) 2007, 2015, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.changes.ProgramFileChange;
import org.eclipse.php.refactoring.core.changes.RenameBreackpointChange;
import org.eclipse.php.refactoring.core.changes.RenameConfigurationChange;
import org.eclipse.php.refactoring.core.rename.logic.RenameIncludeAndClassName;
import org.eclipse.text.edits.MultiTextEdit;

/**
 * @author shachar
 * 
 */
public class RenameFileProcessor extends AbstraceRenameResourceProcessor implements IReferenceUpdating {

	private static final String ID_RENAME_FILE = "php.refactoring.ui.rename.file"; //$NON-NLS-1$
	public static final String RENAME_FILE_PROCESSOR_NAME = PhpRefactoringCoreMessages
			.getString("RenameResourceProcessor.0"); //$NON-NLS-1$

	private Map<String, String> attributes = new HashMap<String, String>();
	/**
	 * holds wether or not we want to change also the inlined text
	 */
	private boolean isUpdateTextualMatches;

	private Program program;

	public RenameFileProcessor(IResource file, Program locateNode) {
		super(file);
		this.program = locateNode;
		attributes.put(NEEDUPDATECLASSNAME, Boolean.TRUE.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.refactoring.core.rename.RenameProcessorBase#
	 * getRefactoringStatus (org.eclipse.core.resources.IFile,
	 * org.eclipse.php.internal.core.ast.nodes.Program)
	 */
	@Override
	public RefactoringStatus getRefactoringStatus(IFile key, Program program) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {

		CompositeChange rootChange = new CompositeChange(RENAME_FILE_PROCESSOR_NAME);
		rootChange.markAsSynthetic();

		try {
			pm.beginTask(PhpRefactoringCoreMessages.getString("RenameFileProcessor.RenamingFile"), 100); //$NON-NLS-1$

			if (pm.isCanceled())
				throw new OperationCanceledException();

			if (getUpdateClassName() || getUpdateReferences()) {
				createRenameTextChanges(pm, rootChange);
			}

			createFileRenameChange(rootChange);

			if (getUpdateReferences()) {
				createRenameReferenceChanges(pm, rootChange);
				pm.worked(80);
			}

			pm.worked(20);

		} finally {
			pm.done();
		}
		return rootChange;
	}

	private void createRenameReferenceChanges(IProgressMonitor pm, CompositeChange rootChange)
			throws CoreException, OperationCanceledException {

		IPath dest = getNewContainerPath();
		IPath source = resource.getParent().getFullPath();
		String oldName = resource.getName();

		collectBrakePoint();
		if (fBreakpoints.getKeys().size() > 0) {
			RenameBreackpointChange breakePointchange = new RenameBreackpointChange(source, dest, oldName,
					fNewElementName, fBreakpoints, fBreakpointAttributes);
			rootChange.add(breakePointchange);
		}

		RenameConfigurationChange confChange = new RenameConfigurationChange(source, dest, oldName, fNewElementName);
		rootChange.add(confChange);

	}

	private IPath getNewContainerPath() {
		return resource.getFullPath().removeLastSegments(1);
	}

	/**
	 * Derive the change
	 */
	private void createRenameTextChanges(IProgressMonitor pm, CompositeChange rootChange)
			throws CoreException, OperationCanceledException {

		String fileName = program.getSourceModule().getResource().getName();
		String extension = program.getSourceModule().getResource().getFileExtension();

		int index = fileName.indexOf("." + extension); //$NON-NLS-1$
		String className = null;

		if (index > 0) {
			className = fileName.substring(0, index);
		}
		if (className == null) {
			return;
		}

		try {
			pm.beginTask(RenameClassProcessor.RENAME_IS_PROCESSING, 1);
			pm.setTaskName(RenameClassProcessor.CREATING_MODIFICATIONS_LABEL);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			// get target parameters
			String newElementName = getNewElementName();
			index = newElementName.indexOf("." + extension); //$NON-NLS-1$
			if (index > 0) {
				newElementName = newElementName.substring(0, index);
			}

			// If update class and update reference are all false, ignore the
			// loop.

			for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
				final IFile file = entry.getKey();
				final Program program = entry.getValue();
				final RenameIncludeAndClassName rename = new RenameIncludeAndClassName(file, className, newElementName,
						getUpdateTextualMatches(), getUpdateClassName(), getUpdateReferences(), resource);

				// aggregate the changes identifiers
				program.accept(rename);

				if (pm.isCanceled())
					throw new OperationCanceledException();

				pm.worked(1);

				if (rename.hasChanges()) {
					ProgramFileChange change = new ProgramFileChange(file.getName(), file, program);
					change.setEdit(new MultiTextEdit());
					change.setTextType("php"); //$NON-NLS-1$

					rootChange.add(change);
					rename.updateChange(change);
				}
			}

		} finally {
			pm.done();
		}
	}

	private void createFileRenameChange(CompositeChange rootChange) {
		RenameResourceChange rmChange = new RenameResourceChange(resource.getFullPath(), fNewElementName);

		rootChange.add(rmChange);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.refactoring.core.rename.AbstractRenameProcessor#
	 * checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws OperationCanceledException, CoreException {
		RefactoringStatus status = new RefactoringStatus();

		if (!checkReadOnlyAndNull(resource)) {
			status.merge(RefactoringStatus.createFatalErrorStatus(
					NLS.bind(PhpRefactoringCoreMessages.getString("RenameFileProcessor.7"), resource))); //$NON-NLS-1$
		}
		super.checkInitialConditions(pm);

		return status;
	}

	/**
	 * Check if the supplied resource is read only or null. If it is then ask
	 * the user if they want to continue. Return true if the resource is not
	 * read only or if the user has given permission.
	 * 
	 * @return boolean
	 */
	private boolean checkReadOnlyAndNull(IResource currentResource) {
		if (currentResource == null) {
			return false;
		}
		ResourceAttributes attributes = currentResource.getResourceAttributes();
		if (attributes == null) {
			return false;
		}
		// Do a quick read only check
		if (attributes.isReadOnly()) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor,
	 * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();

		// Checks if one of the resources already exists with the same name in
		// this location
		IPath sourcePath = resource.getFullPath().removeLastSegments(1);

		String newFilePath = sourcePath.toOSString() + File.separatorChar + getNewElementName();

		IResource dest;
		if (sourcePath.segmentCount() < 1) {
			dest = ResourcesPlugin.getWorkspace().getRoot().getProject(getNewElementName());
		} else {
			dest = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePath));
		}
		if (dest.exists()) {
			status.merge(RefactoringStatus
					.createFatalErrorStatus(NLS.bind(PhpRefactoringCoreMessages.getString("RenameFileProcessor.8"), //$NON-NLS-1$
							getNewElementName(), sourcePath.toOSString())));
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getElements()
	 */
	@Override
	public Object[] getElements() {
		return new Object[] { resource };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return ID_RENAME_FILE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getProcessorName()
	 */
	@Override
	public String getProcessorName() {
		return RENAME_FILE_PROCESSOR_NAME;
	}

	@Override
	public String getCurrentElementName() {
		return resource.getName();
	}

	public void setUpdateRefernces(boolean update) {
		isUpdateReferences = update;
	}

	public boolean canEnableTextUpdating() {
		return true;
	}

	public String getCurrentElementQualifier() {
		return resource.getName();
	}

	public boolean getUpdateTextualMatches() {
		return isUpdateTextualMatches;
	}

	public void setUpdateTextualMatches(boolean update) {
		this.isUpdateTextualMatches = update;
	}

	@Override
	public Object getNewElement() throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.refactoring.core.rename.IReferenceUpdating#getAttribute(
	 * java.lang.String)
	 */
	public String getAttribute(String attribute) {
		return attributes.get(attribute);
	}

	private boolean getUpdateClassName() {
		String update = attributes.get(UPDATECLASSNAME);

		if (update != null) {
			return Boolean.valueOf(update);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.refactoring.core.rename.IReferenceUpdating#setAttribute(
	 * java.lang.String, java.lang.String)
	 */
	public void setAttribute(String attribute, String value) {
		attributes.put(attribute, value);
	}

}
