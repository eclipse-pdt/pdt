/*******************************************************************************
 * Copyright (c) 2005, 2015, 2016 Zend Technologies and others.
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
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.core.libfolders.RenameLibraryFolderChange;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.changes.*;
import org.eclipse.php.refactoring.core.move.MoveUtils;
import org.eclipse.php.refactoring.core.rename.logic.RenameIncludeFolder;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;
import org.eclipse.text.edits.MultiTextEdit;

public class RenameFolderProcessor extends AbstraceRenameResourceProcessor implements IReferenceUpdating {
	public static final String RENAME_FOLDER_PROCESSOR_NAME = PhpRefactoringCoreMessages
			.getString("RenameResourceProcessor.0"); //$NON-NLS-1$
	private static final String REFACTORING_ACTION_INTERNAL_ERROR = PhpRefactoringCoreMessages
			.getString("RenameProcessorBase.internalerror"); //$NON-NLS-1$
	private static final String ID_RENAME_FOLDER = "php.refactoring.ui.rename.folder"; //$NON-NLS-1$
	private Map<String, String> attributes = new HashMap<String, String>();
	/**
	 * holds wether or not we want to change also the inlined text
	 */
	private boolean isUpdateTextualMatches;

	private ArrayList<IBuildpathEntry> newBuildEntries;
	private ArrayList<IBuildpathEntry> newIncludePathEntries;
	private List<IBuildpathEntry> oldBuildEntries;
	private List<IBuildpathEntry> oldIncludePath;

	public RenameFolderProcessor(IContainer container) {
		super(container);
		attributes.put(IReferenceUpdating.NEEDUPDATECLASSNAME, Boolean.FALSE.toString());
	}

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

		CompositeChange rootChange = new NoReverseCompositeChange(RENAME_FOLDER_PROCESSOR_NAME);
		rootChange.markAsSynthetic();

		try {
			pm.beginTask(PhpRefactoringCoreMessages.getString("RenameFolderProcessor.RenamingFile"), 100); //$NON-NLS-1$

			if (pm.isCanceled())
				throw new OperationCanceledException();

			if (getUpdateReferences()) {
				createRenameTextChanges(pm, rootChange);
				createRenameReferenceChange(pm, rootChange);
				createRenameLibraryFolderChange(rootChange);
			} else {
				createFileRenameChange(rootChange);
			}
		} finally {
			pm.done();
		}
		return rootChange;
	}

	private void createRenameLibraryFolderChange(CompositeChange rootChange) {
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();

		if (lfm.isInLibraryFolder(resource)) {
			IFolder folder = (IFolder) resource;
			IFolder newFolder = resource.getParent().getFolder(Path.fromPortableString(fNewElementName));

			RenameLibraryFolderChange change = new RenameLibraryFolderChange(folder, newFolder);
			rootChange.add(change);
		}
	}

	private void createRenameReferenceChange(IProgressMonitor pm, CompositeChange rootChange) throws CoreException {
		pm.beginTask(PhpRefactoringCoreMessages.getString("RenameFolderProcessor.0"), 0); //$NON-NLS-1$
		pm.setTaskName(PhpRefactoringCoreMessages.getString("RenameFolderProcessor.1")); //$NON-NLS-1$

		IPath source = resource.getFullPath().removeLastSegments(1);
		String oldName = resource.getName();
		IPath dest = getNewFilePath();

		RenameConfigurationChange confChange = new RenameConfigurationChange(source.removeLastSegments(0),
				dest.removeLastSegments(0), oldName, fNewElementName);
		rootChange.add(confChange);

		if (pm.isCanceled())
			throw new OperationCanceledException();

		createFileRenameChange(rootChange);
		if (resource instanceof IProject) {
			IProject[] referencing = ((IProject) resource).getReferencingProjects();
			if (referencing != null && referencing.length > 0) {
				ProjectReferenceChange change = new ProjectReferenceChange(resource.getName(), getNewElementName(),
						referencing);

				rootChange.add(change);
			}
		}

		collectBuildPath();
		RenameBuildAndIcludePathChange biChange = new RenameBuildAndIcludePathChange(source, dest, oldName,
				fNewElementName, oldBuildEntries, newBuildEntries, oldIncludePath, newIncludePathEntries);

		if (newBuildEntries.size() > 0 || newIncludePathEntries.size() > 0) {
			rootChange.add(biChange);
		}

		collectBrakePoint();
		if (fBreakpoints.getKeys().size() > 0) {
			RenameBreackpointChange breakePointchange = new RenameBreackpointChange(source, dest, oldName,
					fNewElementName, fBreakpoints, fBreakpointAttributes);
			rootChange.add(breakePointchange);
		}
	}

	private void collectBuildPath() throws ModelException {
		IProject project = resource.getProject();

		IScriptProject projrct = DLTKCore.create(project);
		IPath filePath = resource.getFullPath();

		oldBuildEntries = Arrays.asList(projrct.readRawBuildpath());

		newBuildEntries = new ArrayList<IBuildpathEntry>();

		newBuildEntries.addAll(oldBuildEntries);

		for (int i = 0; i < oldBuildEntries.size(); i++) {
			IBuildpathEntry fEntryToChange = oldBuildEntries.get(i);
			IPath entryPath = fEntryToChange.getPath();

			int mattchedPath = entryPath.matchingFirstSegments(filePath);

			IPath truncatedPath = entryPath.uptoSegment(mattchedPath);

			IPath remaingPath = entryPath.removeFirstSegments(mattchedPath);
			IPath newPath;
			if (mattchedPath == filePath.segmentCount()) {
				newPath = truncatedPath.removeLastSegments(1).append(fNewElementName).append(remaingPath);
				IBuildpathEntry newEntry = RefactoringUtility.createNewBuildpathEntry(fEntryToChange, newPath, filePath,
						fNewElementName);

				newBuildEntries.remove(fEntryToChange);
				newBuildEntries.add(newEntry);
			} else {
				IBuildpathEntry newEntry = RefactoringUtility.createNewBuildpathEntry(fEntryToChange,
						fEntryToChange.getPath(), filePath, fNewElementName);

				newBuildEntries.remove(fEntryToChange);
				newBuildEntries.add(newEntry);
			}

		}

		oldIncludePath = new ArrayList<IBuildpathEntry>();

		newIncludePathEntries = new ArrayList<IBuildpathEntry>();
		List<IncludePath> includePathEntries = Arrays.asList(IncludePathManager.getInstance().getIncludePaths(project));

		for (IncludePath entry : includePathEntries) {
			Object includePathEntry = entry.getEntry();
			IResource resource = null;
			if (!(includePathEntry instanceof IBuildpathEntry)) {
				resource = (IResource) includePathEntry;
				IPath entryPath = resource.getFullPath();

				IBuildpathEntry oldEntry = RefactoringUtility.createNewBuildpathEntry(IBuildpathEntry.BPE_SOURCE,
						entryPath);
				oldIncludePath.add((IBuildpathEntry) oldEntry);

				if (filePath.isPrefixOf(entryPath) || entryPath.equals(filePath)) {
					int mattchedPath = entryPath.matchingFirstSegments(filePath);
					IPath truncatedPath = entryPath.uptoSegment(mattchedPath);
					IPath remaingPath = entryPath.removeFirstSegments(mattchedPath);
					IPath newPath;
					if (mattchedPath == filePath.segmentCount()) {
						newPath = truncatedPath.removeLastSegments(1).append(fNewElementName).append(remaingPath);
					} else {
						newPath = truncatedPath.append(fNewElementName).append(remaingPath);
					}
					IBuildpathEntry newEntry = RefactoringUtility.createNewBuildpathEntry(IBuildpathEntry.BPE_SOURCE,
							newPath);
					newIncludePathEntries.add(newEntry);
				} else {
					IBuildpathEntry newEntry = RefactoringUtility.createNewBuildpathEntry(IBuildpathEntry.BPE_SOURCE,
							entryPath);
					newIncludePathEntries.add(newEntry);
				}
			} else {
				newIncludePathEntries.add((IBuildpathEntry) includePathEntry);
				oldIncludePath.add((IBuildpathEntry) includePathEntry);

			}
		}

	}

	/**
	 * Derive the change
	 */
	private void createRenameTextChanges(IProgressMonitor pm, CompositeChange rootChange)
			throws CoreException, OperationCanceledException {

		try {
			pm.beginTask(RenameClassProcessor.RENAME_IS_PROCESSING, 1);
			pm.setTaskName(RenameClassProcessor.CREATING_MODIFICATIONS_LABEL);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			// get target parameters
			String newElementName = getNewElementName();

			// If update class and update reference are all false, ignore the
			// loop.
			if (getUpdateReferences()) {
				for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
					final IFile file = entry.getKey();
					final Program program = entry.getValue();
					final RenameIncludeFolder rename = new RenameIncludeFolder(file, getCurrentElementName(),
							newElementName, this.resource.getFullPath(), false, getUpdateReferences());

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
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();

		if (!checkReadOnlyAndNull(resource)) {
			status.merge(RefactoringStatus.createFatalErrorStatus(
					NLS.bind(PhpRefactoringCoreMessages.getString("RenameFileProcessor.7"), resource))); //$NON-NLS-1$
		}
		try {
			boolean hasExternalDependencies = false;

			participantFiles = new HashMap<IFile, Program>();

			if (resource instanceof IContainer) {

				IContainer container = (IContainer) resource;

				Set<IFile> phpFilesSet = new HashSet<IFile>();
				MoveUtils.getAllPHPFiles(new IResource[] { container }, phpFilesSet);
				for (IFile file : phpFilesSet) {
					ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
					IProject project = file.getProject();
					PHPVersion version = ProjectOptions.getPHPVersion(project);

					ASTParser newParser = ASTParser.newParser(version, sourceModule);
					Program program = newParser.createAST(null);
					participantFiles.put(file, program);

					collectReferences(program, pm);
				}
			}

			if (hasExternalDependencies) {
				final String message = PhpRefactoringCoreMessages.getString("AbstractRenameProcessor.1"); //$NON-NLS-1$
				return RefactoringStatus.createWarningStatus(message);
			}

			return new RefactoringStatus();
		} catch (Exception e) {
			final String exceptionMessage = e.getMessage();
			final String formattedString = REFACTORING_ACTION_INTERNAL_ERROR
					.concat(exceptionMessage == null ? "" : exceptionMessage); //$NON-NLS-1$
			return RefactoringStatus.createFatalErrorStatus(formattedString);

		}
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

	private IPath getNewFilePath() {
		return resource.getFullPath().removeLastSegments(1);
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
		return ID_RENAME_FOLDER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getProcessorName()
	 */
	@Override
	public String getProcessorName() {
		return RENAME_FOLDER_PROCESSOR_NAME;
	}

	@Override
	public String getCurrentElementName() {
		return resource.getName();
	}

	public void setUpdateRefernces(boolean update) {
		isUpdateReferences = update;
	}

	public boolean canEnableTextUpdating() {
		return false;
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

	private class NoReverseCompositeChange extends CompositeChange {

		public NoReverseCompositeChange(String name) {
			super(name);
		}

		public NoReverseCompositeChange(String name, Change[] array) {
			super(name, array);
		}

		@Override
		protected Change createUndoChange(Change[] childUndos) {
			List<Change> undos = Arrays.asList(childUndos);
			Collections.reverse(undos);

			return new NoReverseCompositeChange(getName(), undos.toArray(new Change[undos.size()]));
		}
	}
}
