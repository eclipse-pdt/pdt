/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.move;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.refactoring.changes.MoveResourceChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.core.libfolders.RenameLibraryFolderChange;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree.Node;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.util.collections.BucketMap;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.changes.ProgramFileChange;
import org.eclipse.php.refactoring.core.changes.RenameBreackpointChange;
import org.eclipse.php.refactoring.core.changes.RenameBuildAndIcludePathChange;
import org.eclipse.php.refactoring.core.changes.RenameConfigurationChange;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * Delegate object that contains the logic used by the processor
 * 
 * @author Eden K., 2007
 * 
 */
@SuppressWarnings("restriction")
public class MoveDelegate {

	private PHPMoveProcessor fProcessor;
	private IPath fMainDestinationPath;
	private Set<IFile> phpFiles;
	private BucketMap<IResource, IBreakpoint> fBreakpoints;
	private HashMap<IBreakpoint, Map<String, Object>> fBreakpointAttributes;
	private List<IBuildpathEntry> oldBuildEntries;
	private ArrayList<IBuildpathEntry> newBuildEntries;
	private ArrayList<IBuildpathEntry> newIncludePathEntries;
	private ArrayList<IBuildpathEntry> oldIncludePath;

	public MoveDelegate(PHPMoveProcessor processor) {
		fProcessor = processor;

	}

	/**
	 * Checks if it is ok to start with the refactoring
	 * 
	 * @return status
	 * @throws OperationCanceledException
	 */
	public RefactoringStatus checkInitialConditions()
			throws OperationCanceledException {
		IResource[] sourceResources = fProcessor.getSourceSelection();

		phpFiles = new HashSet<IFile>();
		MoveUtils.getAllPHPFiles(sourceResources, phpFiles);
		return new RefactoringStatus();
	}

	/**
	 * Final check before the actual refactoring
	 * 
	 * @return status
	 * @throws OperationCanceledException
	 */
	public RefactoringStatus checkFinalConditions()
			throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		IContainer destination = fProcessor.getDestination();
		IProject sourceProject = fProcessor.getSourceSelection()[0]
				.getProject();
		IProject destinationProject = destination.getProject();
		if (sourceProject != destinationProject)
			status.merge(MoveUtils.checkMove(phpFiles, sourceProject,
					destination));

		// Checks if one of the resources already exists with the same name in
		// the destination
		IPath dest = fProcessor.getDestination().getFullPath();
		IResource[] sourceResources = fProcessor.getSourceSelection();

		for (IResource element : sourceResources) {
			String newFilePath = dest.toOSString() + File.separatorChar
					+ element.getName();
			IResource resource = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(new Path(newFilePath));
			if (resource != null && resource.exists()) {
				status.merge(RefactoringStatus.createFatalErrorStatus(NLS.bind(
						PhpRefactoringCoreMessages.getString("MoveDelegate.6"), element.getName(), dest.toOSString()))); //$NON-NLS-1$
			}
		}
		return status;
	}

	/**
	 * Creates the change for the move action. The change can be a simple change
	 * (the move itslef) or a more complex change (including references update
	 * and includes in the file itself) depending on the user selection (update
	 * references checkbox)
	 * 
	 * @param pm
	 *            - progress monitor
	 * @param rootChange
	 * @return the root change after the additions
	 * @throws OperationCanceledException
	 */
	public Change createChange(IProgressMonitor pm, CompositeChange rootChange)
			throws CoreException, OperationCanceledException {
		fMainDestinationPath = fProcessor.getDestination().getFullPath();
		fProcessor.getSourceSelection();

		if (!fProcessor.getUpdateReferences()) {
			return createSimpleMoveChange(pm, rootChange);
		}
		return createReferenceUpdatingMoveChange(pm, rootChange);
	}

	/**
	 * Adds the move changes to the root change This change is the proper move
	 * change, nothing else
	 * 
	 * @param pm
	 *            - progress monitor
	 * @param rootChange
	 *            - the root change that the new changes are added to
	 * @return the root change after the additions
	 */
	private Change createSimpleMoveChange(final IProgressMonitor pm,
			final CompositeChange rootChange) throws CoreException,
			OperationCanceledException {
		try {
			pm.beginTask(
					PhpRefactoringCoreMessages.getString("MoveDelegate.0"), 100); //$NON-NLS-1$

			IResource[] sourceResources = fProcessor.getSourceSelection();
			createMoveChange(sourceResources, rootChange);
			pm.worked(100);

		} finally {
			pm.done();
		}
		return rootChange;
	}

	/**
	 * Adds the text and move changes to the root change This change is the a
	 * more global change, it includes both the file(s) move, the update of it's
	 * includes and update all the references, all the files that have includes
	 * to the moved file.
	 * 
	 * @param pm
	 *            - progress monitor
	 * @param rootChange
	 *            - the root change that the new changes are added to
	 * @return the root change after the additions
	 */
	private Change createReferenceUpdatingMoveChange(IProgressMonitor pm,
			CompositeChange rootChange) throws CoreException,
			OperationCanceledException {
		try {
			pm.beginTask(
					PhpRefactoringCoreMessages.getString("MoveDelegate.0"), 100); //$NON-NLS-1$

			IResource[] sourceResources = fProcessor.getSourceSelection();

			createTextChanges(new SubProgressMonitor(pm, 80), rootChange,
					phpFiles, sourceResources);
			pm.worked(80);

			// update configuration file.
			createRunConfigurationChange(sourceResources, rootChange);

			// There is a tricky thing here.
			// The resource move must be happened after text change, and run
			// configuration changes(this is because the share file under the
			// project)
			// but before the other changes, e.g. break point and etc.
			createMoveChange(sourceResources, rootChange);

			// update associated break point.
			createBreakPointChange(sourceResources, rootChange);

			createBuildPathChange(sourceResources, rootChange);

			createRenameLibraryFolderChange(sourceResources, rootChange);

			pm.worked(20);

		} finally {
			pm.done();
		}
		return rootChange;
	}

	private void createRenameLibraryFolderChange(IResource[] sourceResources,
			CompositeChange rootChange) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();

		for (IResource resource : sourceResources) {
			if (resource.getType() == IResource.FOLDER
					&& lfm.isInLibraryFolder(resource)) {
				IPath newPath = fMainDestinationPath.append(resource.getName());
				IFolder newFolder = root.getFolder(newPath);

				RenameLibraryFolderChange change = new RenameLibraryFolderChange(
						(IFolder) resource, newFolder);
				rootChange.add(change);
			}
		}
	}

	private void createBuildPathChange(IResource[] sourceResources,
			CompositeChange rootChange) throws ModelException {
		IResource[] uniqueSourceResources = removeDuplicateResources(sourceResources);
		for (IResource element : uniqueSourceResources) {
			// only container need handle build/include path.
			if (element instanceof IContainer) {
				IProject project = element.getProject();

				// if moving to another project
				if (RefactoringUtility.getResource(fMainDestinationPath)
						.getProject() != project) {
					removeBuildPath(element, project);
					IPath path = element.getFullPath().removeLastSegments(1);
					RenameBuildAndIcludePathChange biChange = new RenameBuildAndIcludePathChange(
							path, path, element.getName(), "", oldBuildEntries, //$NON-NLS-1$
							newBuildEntries, oldIncludePath,
							newIncludePathEntries);

					if (newBuildEntries.size() > 0
							|| newIncludePathEntries.size() > 0) {
						rootChange.add(biChange);
					}

				} else {
					updateBuildPath(element, project);
					RenameBuildAndIcludePathChange biChange = new RenameBuildAndIcludePathChange(
							element.getFullPath().removeLastSegments(1),
							fMainDestinationPath, element.getName(),
							element.getName(), oldBuildEntries,
							newBuildEntries, oldIncludePath,
							newIncludePathEntries);
					if (newBuildEntries.size() > 0
							|| newIncludePathEntries.size() > 0) {
						rootChange.add(biChange);
					}
				}
			}
		}

	}

	private void removeBuildPath(IResource resource, IProject project) {

		IScriptProject projrct = DLTKCore.create(project);
		IPath filePath = resource.getFullPath();

		oldBuildEntries = Arrays.asList(projrct.readRawBuildpath());

		newBuildEntries = new ArrayList<IBuildpathEntry>();

		newBuildEntries.addAll(oldBuildEntries);

		for (int i = 0; i < oldBuildEntries.size(); i++) {
			IBuildpathEntry fEntryToChange = oldBuildEntries.get(i);
			IPath entryPath = fEntryToChange.getPath();

			int mattchedPath = entryPath.matchingFirstSegments(filePath);

			if (mattchedPath == filePath.segmentCount()) {
				newBuildEntries.remove(fEntryToChange);
			} else {
				IBuildpathEntry newEntry = RefactoringUtility
						.createNewBuildpathEntry(fEntryToChange,
								fEntryToChange.getPath(), filePath, ""); //$NON-NLS-1$
				newBuildEntries.remove(fEntryToChange);
				newBuildEntries.add(newEntry);
			}
		}

		oldIncludePath = new ArrayList<IBuildpathEntry>();

		newIncludePathEntries = new ArrayList<IBuildpathEntry>();
		List<IncludePath> includePathEntries = Arrays.asList(IncludePathManager
				.getInstance().getIncludePaths(project));

		for (IncludePath entry : includePathEntries) {
			Object includePathEntry = entry.getEntry();
			IResource includeResource = null;
			if (!(includePathEntry instanceof IBuildpathEntry)) {
				includeResource = (IResource) includePathEntry;
				IPath entryPath = includeResource.getFullPath();

				IBuildpathEntry oldEntry = RefactoringUtility
						.createNewBuildpathEntry(IBuildpathEntry.BPE_SOURCE,
								entryPath);
				oldIncludePath.add((IBuildpathEntry) oldEntry);

				if (filePath.isPrefixOf(entryPath)
						|| entryPath.equals(filePath)) {
				} else {
					IBuildpathEntry newEntry = RefactoringUtility
							.createNewBuildpathEntry(
									IBuildpathEntry.BPE_SOURCE, entryPath);
					newIncludePathEntries.add(newEntry);
				}
			} else {
				newIncludePathEntries.add((IBuildpathEntry) includePathEntry);
				oldIncludePath.add((IBuildpathEntry) includePathEntry);

			}
		}
	}

	private void updateBuildPath(IResource resource, IProject project) {
		String newElementName = resource.getName();
		IScriptProject projrct = DLTKCore.create(project);
		IPath filePath = resource.getFullPath();

		oldBuildEntries = Arrays.asList(projrct.readRawBuildpath());

		newBuildEntries = new ArrayList<IBuildpathEntry>();

		newBuildEntries.addAll(oldBuildEntries);

		for (int i = 0; i < oldBuildEntries.size(); i++) {
			IBuildpathEntry fEntryToChange = oldBuildEntries.get(i);
			IPath entryPath = fEntryToChange.getPath();

			int mattchedPath = entryPath.matchingFirstSegments(filePath);

			if (mattchedPath == filePath.segmentCount()) {
				newBuildEntries.remove(fEntryToChange);
			} else {
				IBuildpathEntry newEntry = RefactoringUtility
						.createNewBuildpathEntry(fEntryToChange,
								fEntryToChange.getPath(), filePath, ""); //$NON-NLS-1$

				newBuildEntries.remove(fEntryToChange);
				newBuildEntries.add(newEntry);
			}
		}

		oldIncludePath = new ArrayList<IBuildpathEntry>();

		newIncludePathEntries = new ArrayList<IBuildpathEntry>();
		List<IncludePath> includePathEntries = Arrays.asList(IncludePathManager
				.getInstance().getIncludePaths(project));

		for (IncludePath entry : includePathEntries) {
			Object includePathEntry = entry.getEntry();
			IResource includeResource = null;
			if (!(includePathEntry instanceof IBuildpathEntry)) {
				includeResource = (IResource) includePathEntry;
				IPath entryPath = includeResource.getFullPath();

				IBuildpathEntry oldEntry = RefactoringUtility
						.createNewBuildpathEntry(IBuildpathEntry.BPE_SOURCE,
								entryPath);
				oldIncludePath.add((IBuildpathEntry) oldEntry);

				if (filePath.isPrefixOf(entryPath)
						|| entryPath.equals(filePath)) {
					int mattchedPath = entryPath
							.matchingFirstSegments(filePath);
					IPath truncatedPath = entryPath.uptoSegment(mattchedPath);
					IPath remaingPath = entryPath
							.removeFirstSegments(mattchedPath);
					IPath newPath;
					if (mattchedPath == filePath.segmentCount()) {
						newPath = truncatedPath.removeLastSegments(1)
								.append(newElementName).append(remaingPath);
					} else {
						newPath = truncatedPath.append(newElementName).append(
								remaingPath);
					}
					IBuildpathEntry newEntry = RefactoringUtility
							.createNewBuildpathEntry(
									IBuildpathEntry.BPE_SOURCE, newPath);
					newIncludePathEntries.add(newEntry);
				} else {
					IBuildpathEntry newEntry = RefactoringUtility
							.createNewBuildpathEntry(
									IBuildpathEntry.BPE_SOURCE, entryPath);
					newIncludePathEntries.add(newEntry);
				}
			} else {
				newIncludePathEntries.add((IBuildpathEntry) includePathEntry);
				oldIncludePath.add((IBuildpathEntry) includePathEntry);

			}
		}

	}

	private void createBreakPointChange(IResource[] sourceResources,
			CompositeChange rootChange) throws CoreException {
		IResource[] uniqueSourceResources = removeDuplicateResources(sourceResources);
		for (IResource element : uniqueSourceResources) {
			collectBrakePoint(element);

			RenameBreackpointChange breakePointchanges = new RenameBreackpointChange(
					element.getFullPath().removeLastSegments(1),
					fMainDestinationPath, element.getName(), element.getName(),
					fBreakpoints, fBreakpointAttributes);

			if (fBreakpoints.getKeys().size() > 0) {
				rootChange.add(breakePointchanges);
			}
		}
	}

	protected void collectBrakePoint(IResource resource) throws CoreException {
		fBreakpoints = new BucketMap<IResource, IBreakpoint>(6);
		fBreakpointAttributes = new HashMap<IBreakpoint, Map<String, Object>>(6);
		final IBreakpointManager breakpointManager = DebugPlugin.getDefault()
				.getBreakpointManager();
		IMarker[] markers = resource.findMarkers(
				IBreakpoint.LINE_BREAKPOINT_MARKER, true,
				IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			IResource markerResource = marker.getResource();
			IBreakpoint breakpoint = breakpointManager.getBreakpoint(marker);
			if (breakpoint != null) {
				fBreakpoints.add(markerResource, breakpoint);
				fBreakpointAttributes.put(breakpoint, breakpoint.getMarker()
						.getAttributes());
			}
		}
	}

	private void createRunConfigurationChange(IResource[] sourceResources,
			CompositeChange rootChange) {

		IResource[] uniqueSourceResources = removeDuplicateResources(sourceResources);
		for (IResource element : uniqueSourceResources) {
			RenameConfigurationChange configPointchanges = new RenameConfigurationChange(
					element.getFullPath().removeLastSegments(1),
					fMainDestinationPath, element.getName(), element.getName());

			rootChange.add(configPointchanges);
		}
	}

	/**
	 * Creates the text changes for all the affected files. Updates all the
	 * include statements in the current file and all the includes in the
	 * "including " files. In case of folders, creates the changes recursively
	 * 
	 * @param pm
	 *            - progress monitor
	 * @param rootChange
	 *            - the root change that the new changes are added to
	 * @param sourceResources
	 * @return the root change after the additions
	 * @throws CoreException
	 */
	private Change createTextChanges(IProgressMonitor pm,
			CompositeChange rootChange, Set<IFile> phpFiles,
			IResource[] sourceResources) throws CoreException {
		List<ProgramFileChange> changes = new ArrayList<ProgramFileChange>();
		try {
			pm.beginTask(
					PhpRefactoringCoreMessages.getString("MoveDelegate.1"), 100); //$NON-NLS-1$

			// creat text changes:
			// for each file that will be moved, update its includes
			// and update all the files that include it,

			IResource[] uniqueSourceResources = removeDuplicateResources(sourceResources);

			for (Iterator<IFile> it = phpFiles.iterator(); it.hasNext();) {
				IFile currentMovedResource = it.next();

				Map<IFile, Program> participantFiles = collectReferencingFiles(
						currentMovedResource, pm);

				for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
					final IFile file = entry.getKey();
					if (phpFiles.contains(file)) {
						continue;
					}
					final Program program = entry.getValue();

					final ChangeIncludePath rename = new ChangeIncludePath(
							currentMovedResource, file, fMainDestinationPath,
							false, uniqueSourceResources);
					// aggregate the changes identifiers
					program.accept(rename);

					if (pm.isCanceled())
						throw new OperationCanceledException();

					pm.worked(1);

					if (rename.hasChanges()) {
						ProgramFileChange change = new ProgramFileChange(
								file.getName(), file, program);
						change.setEdit(new MultiTextEdit());
						change.setTextType("php"); //$NON-NLS-1$

						changes.add(change);
						rename.updateChange(change);
					}
				}

				ISourceModule sourceModule = DLTKCore
						.createSourceModuleFrom(currentMovedResource);

				if (sourceModule instanceof ISourceModule) {

					Program program = null;
					try {
						program = ASTUtils
								.createProgramFromSource(sourceModule);
					} catch (Exception e) {
					}

					if (program != null) {
						final ChangeIncludePath rename = new ChangeIncludePath(
								currentMovedResource, currentMovedResource,
								fMainDestinationPath, true,
								uniqueSourceResources);

						// aggregate the changes identifiers
						program.accept(rename);

						if (pm.isCanceled())
							throw new OperationCanceledException();

						pm.worked(1);

						if (rename.hasChanges()) {
							ProgramFileChange change = new ProgramFileChange(
									currentMovedResource.getName(),
									currentMovedResource, program);
							change.setEdit(new MultiTextEdit());
							change.setTextType("php"); //$NON-NLS-1$

							changes.add(change);
							rename.updateChange(change);
						}
					}
				}
			}
			pm.worked(70);

		} finally {
			pm.done();
		}// getChildren()

		Map<IFile, List<TextEdit>> changeMap = new HashMap<IFile, List<TextEdit>>();
		Map<IFile, ProgramFileChange> fileMap = new HashMap<IFile, ProgramFileChange>();
		for (ProgramFileChange programFileChange : changes) {
			List<TextEdit> list = changeMap.get(programFileChange.getFile());
			if (list == null) {
				list = new ArrayList<TextEdit>();
				changeMap.put(programFileChange.getFile(), list);
				fileMap.put(programFileChange.getFile(), programFileChange);
			} else {

			}
			list.addAll(Arrays
					.asList(programFileChange.getEdit().getChildren()));
		}
		for (IFile file : changeMap.keySet()) {
			ProgramFileChange change = new ProgramFileChange(file.getName(),
					file, fileMap.get(file).getProgram());
			change.setEdit(new MultiTextEdit());
			change.setTextType("php"); //$NON-NLS-1$

			List<TextEdit> list = changeMap.get(file);
			Collections.sort(list, new Comparator<TextEdit>() {
				public int compare(TextEdit o1, TextEdit o2) {
					return o2.getOffset() - o1.getOffset();
				}
			});

			for (TextEdit textEdit : list) {
				if (textEdit instanceof ReplaceEdit) {
					ReplaceEdit replaceEdit = (ReplaceEdit) textEdit;
					change.addEdit(new ReplaceEdit(replaceEdit.getOffset(),
							replaceEdit.getLength(), replaceEdit.getText()));
				}
			}
			rootChange.add(change);

		}
		return rootChange;
	}

	private IResource[] removeDuplicateResources(IResource[] sourceResources) {
		// ignore empty array
		if (sourceResources == null || sourceResources.length == 0) {
			return sourceResources;
		}

		ArrayList<IResource> result = new ArrayList<IResource>();

		for (IResource source : sourceResources) {
			if (result.size() == 0) {
				result.add(source);
			} else {
				// check if the resource is parent of any item in the result
				for (IResource existing : result) {
					// if the resource is parent of an existing item in the
					// result.
					// remove the existing item, add the new one.
					if (source.getFullPath().isPrefixOf(existing.getFullPath())) {
						result.remove(existing);
						result.add(source);
					}
				}

				boolean noNeedAdded = false;
				for (IResource existing : result) {
					// if the resource is parent of an existing item in the
					// result.
					// remove the existing item, add the new one.
					if (existing.getFullPath().isPrefixOf(source.getFullPath())) {
						noNeedAdded = true;
					}
				}
				// the source is not in the result after loop
				if (!result.contains(source) && !noNeedAdded) {
					result.add(source);
				}
			}
		}

		IResource[] ret = new IResource[result.size()];

		return result.toArray(ret);
	}

	/**
	 * Add move changes for each for the selected resources
	 * 
	 * @param sourceResources
	 * @param rootChange
	 */
	private void createMoveChange(IResource[] sourceResources,
			CompositeChange rootChange) {
		IResource[] uniqueSourceResources = removeDuplicateResources(sourceResources);
		for (IResource element : uniqueSourceResources) {
			MoveResourceChange moveResource = new MoveResourceChange(element,
					fProcessor.getDestination());
			rootChange.add(moveResource);
		}
	}

	/**
	 * Checks whether the given move included is vaild
	 * 
	 * @param destination
	 * @return a staus indicating whether the included is valid or not
	 */
	public RefactoringStatus verifyDestination(IResource destination) {
		RefactoringStatus status = new RefactoringStatus();

		Assert.isNotNull(destination);
		if (!destination.exists() || destination.isPhantom())
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages
							.getString("MoveDelegate.2")); //$NON-NLS-1$
		if (!destination.isAccessible())
			return RefactoringStatus
					.createFatalErrorStatus(PhpRefactoringCoreMessages
							.getString("MoveDelegate.3")); //$NON-NLS-1$
		Assert.isTrue(destination.getType() != IResource.ROOT);

		IResource[] sourceResources = fProcessor.getSourceSelection();
		for (IResource element : sourceResources) {
			if (destination.equals(element.getParent()))
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages
								.getString("MoveDelegate.4")); //$NON-NLS-1$
			if (destination.equals(element)) {
				return RefactoringStatus
						.createFatalErrorStatus(PhpRefactoringCoreMessages
								.getString("MoveDelegate.5")); //$NON-NLS-1$
			}
		}
		return status;
	}

	private Map<IFile, Program> collectReferencingFiles(IFile sourceFile,
			IProgressMonitor pm) {
		ISourceModule sourceModule = DLTKCore
				.createSourceModuleFrom(sourceFile);

		Map<IFile, Program> participantFiles = new HashMap<IFile, Program>();

		Collection<Node> references = MoveUtils
				.getReferencingFiles(sourceModule);
		if (references != null) {
			for (Iterator<Node> it = references.iterator(); it.hasNext();) {
				Node node = it.next();
				IFile file = (IFile) node.getFile().getResource();
				try {
					participantFiles.put(file,
							RefactoringUtility.getProgramForFile(file));
				} catch (Exception e) {
				}
			}
		}

		return participantFiles;
	}
}
