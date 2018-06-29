/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.move;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree.Node;
import org.eclipse.php.internal.core.util.collections.BucketMap;
import org.eclipse.php.refactoring.core.PHPRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.RefactoringPlugin;

public class MoveUtils {

	private static final String phpunitFramework = "PHPUnit/Framework"; //$NON-NLS-1$

	private MoveUtils() {
	}

	/**
	 * For a file that is being moved, get the string of an include statement in it
	 * and returns what the new include string should be based on the new location
	 * 
	 * @param sourceFile
	 * @param destinationDirectoryPath
	 * @param value
	 * @param selectedResource
	 * @return the new include string
	 */
	public static String getMovedIncludedString(IFile sourceFile, IPath destinationDirectoryPath, String value,
			IResource[] selectedResource) {
		if (!isRelativeAndExistingResource(sourceFile.getParent(), value)) {
			return value;
		}

		IPath projectPath = sourceFile.getProject().getFullPath();
		// moving in same project
		if (projectPath.isPrefixOf(destinationDirectoryPath)) {

			IPath destPath = destinationDirectoryPath;
			String destFile = sourceFile.getName();
			for (IResource resouce : selectedResource) {
				if (resouce instanceof IFolder
						&& resouce.getProjectRelativePath().isPrefixOf(sourceFile.getProjectRelativePath())) {
					// if the parent folder is moving, append the folder name to
					// the fileName.
					destFile = resouce.getName() + "/" + destFile; //$NON-NLS-1$
					break;
				}
			}

			// remove the fileName;
			destPath = destPath.append(destFile).removeLastSegments(1);

			IPath includingPath = new Path(value);
			IPath destIncludedFilePath = sourceFile.getParent().getFullPath().append(value);

			for (IResource resouce : selectedResource) {
				if (resouce instanceof IFile && resouce.getFullPath().equals(destIncludedFilePath)) {
					// if the parent folder is moving, append the folder name to
					// the fileName.
					destIncludedFilePath = destPath.append(resouce.getName());
					break;
				}
			}

			String fileName = includingPath.lastSegment();
			for (IResource resouce : selectedResource) {
				if (resouce instanceof IFolder && resouce.getProjectRelativePath().isPrefixOf(includingPath)) {
					destIncludedFilePath = destinationDirectoryPath.append(resouce.getName()).append(fileName);
					break;
				}
			}

			return destIncludedFilePath.makeRelativeTo(destPath).toString();

		} else {
			return value;
		}
	}

	private static boolean isRelativeAndExistingResource(IContainer container, String value) {
		if (container != null && value != null) {
			if (!new Path(value).isAbsolute()) {
				IResource resource = container.findMember(value);
				return resource != null && resource.exists();
			} else {
				return false;
			}

		}
		return false;

	}

	/**
	 * @param filePath
	 * @return directory path - the path not inlcuding file name
	 */
	public static IPath getDirectoryPath(IPath filePath) {
		return filePath.removeLastSegments(1);
	}

	/**
	 * When a file is being moved, all the files that include it need to change
	 * their include statements to it based on the new location. This method gets
	 * the moved file, the current including file we are handling and the current
	 * include statement in it, and returns the new include statement
	 * 
	 * @param selectedResource
	 * 
	 */
	public static String getMovedIncludingString(IFile sourceFile, IPath destinationDirectoryPath, IFile includingFile,
			String value, IResource[] selectedResource) {
		return getMovedIncludingString(sourceFile, getDirectoryPath(sourceFile.getFullPath()), destinationDirectoryPath,
				includingFile, value, selectedResource);
	}

	public static String getMovedIncludingString(IFile sourceFile, IPath oldDirectoryPath,
			IPath destinationDirectoryPath, IFile includingFile, String value, IResource[] selectedResource) {
		IPath projectPath = sourceFile.getProject().getFullPath();

		boolean isInSaveProject = false;
		if (includingFile.getProject() == sourceFile.getProject()) {
			isInSaveProject = true;
		}
		// moving in same project
		if (projectPath.isPrefixOf(destinationDirectoryPath)) {
			IPath includePath = new Path(value);

			String fileName = value;
			if (includePath.segmentCount() > 1) {
				fileName = includePath.lastSegment();
				// includePath = includePath.removeLastSegments(1);
			}

			IPath includeFilePath = includingFile.getParent().getProjectRelativePath().append(includePath);

			// Check if the parent folder is moving.
			for (IResource resouce : selectedResource) {
				if (resouce instanceof IFolder && ((resouce.getProjectRelativePath().isPrefixOf(includePath))
						|| resouce.getProjectRelativePath().isPrefixOf(includeFilePath))

				) {
					// if the parent folder is moving, append the folder name to
					// the fileName.
					fileName = resouce.getName() + "/" + fileName; //$NON-NLS-1$
					break;
				}
			}

			IPath path = destinationDirectoryPath.append(fileName);
			IPath includingPath = includingFile.getParent().getFullPath();

			// Check if the including file is moving.
			for (IResource resouce : selectedResource) {
				// the folder of including file is moving
				if (resouce.getProjectRelativePath().isPrefixOf(includingPath)) {
					// if the parent folder is moving, append the folder name to
					// the fileName.
					includePath = destinationDirectoryPath
							.append(includePath.makeRelativeTo(resouce.getProjectRelativePath()));
					break;
				}

				// the including file is moving
				if (resouce.equals(includingFile)) {
					// if the parent folder is moving, append the folder name to
					// the fileName.
					includePath = destinationDirectoryPath;
					break;
				}
			}

			if (value.startsWith("..") || value.startsWith(".")) { //$NON-NLS-1$ //$NON-NLS-2$
				return path.makeRelativeTo(includingPath).toOSString().replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				if (isInSaveProject) {
					return path.makeRelativeTo(includingPath).toOSString().replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					return path.makeRelativeTo(projectPath).toOSString().replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}

			// return path.makeRelativeTo(includingPath).toString();

		} else {
			IPath oldDirectory = oldDirectoryPath.removeFirstSegments(1);
			String fileName = value;
			IPath includePath = new Path(value);
			if (includePath.segmentCount() > 1) {
				fileName = includePath.lastSegment();
			}

			IPath path = destinationDirectoryPath.append(oldDirectory).append(fileName);
			IPath destPath = destinationDirectoryPath;

			if (destPath.isPrefixOf(path)) {
				if (value.startsWith("..") || value.startsWith(".")) { //$NON-NLS-1$ //$NON-NLS-2$
					IPath includingPath = includingFile.getParent().getProjectRelativePath();
					return path.makeRelativeTo(destPath.append(includingPath)).toString().replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					return path.makeRelativeTo(destPath).toOSString().replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}

			return path.removeFirstSegments(1).toOSString();
		}

	}

	/**
	 * Based on the user selection, recursively populates a set of all of the php
	 * files
	 * 
	 * @param sourceResources
	 * @param phpFilesSet
	 */
	public static void getAllPHPFiles(IResource[] sourceResources, Set<IFile> phpFilesSet) {

		for (IResource current : sourceResources) {
			if (current instanceof IFolder) {
				IResource[] fldChildrens;
				IFolder fld = (IFolder) current;
				try {
					fldChildrens = fld.members();
				} catch (CoreException e) {
					RefactoringPlugin.logException("Failed getting folder children in move operation", e); //$NON-NLS-1$
					continue;
				}
				getAllPHPFiles(fldChildrens, phpFilesSet);
			} else if (current instanceof IFile) {
				IFile file = (IFile) current;
				if (PHPToolkitUtil.isPHPFile(file)) {
					phpFilesSet.add(file);
				}
			}
		}
	}

	public static RefactoringStatus checkMove(Collection<IFile> files, IProject srcProject, IContainer destination) {
		RefactoringStatus status = new RefactoringStatus();
		// // collecting included files from source network
		BucketMap<IFile, IFile> errors = new BucketMap<>();
		for (IFile file : files) {
			Collection<Node> allIncludedNodes = getReferencedFiles(file);
			if (allIncludedNodes == null) {
				continue;
			}
			for (Node includedNode : allIncludedNodes) {
				IFile includedFile = (IFile) includedNode.getFile().getResource();
				if (!includedNode.getFile().getParent().getElementName().equals(phpunitFramework)
						&& !files.contains(includedFile)
						&& destination.findMember(includedFile.getProjectRelativePath()) == null) {
					errors.add(file, includedFile);
				}
			}
		}
		for (IFile node : errors.getKeys()) {
			status.addWarning(
					MessageFormat.format(PHPRefactoringCoreMessages.getString("MoveUtils.8"), node.getName())); //$NON-NLS-1$
			for (IFile value : errors.get(node)) {
				status.addWarning(MessageFormat.format(PHPRefactoringCoreMessages.getString("MoveUtils.9"), //$NON-NLS-1$
						node.getProjectRelativePath().toString(), value.getProjectRelativePath().toString()));
			}
		}
		//
		// collecting including files from source network
		errors = new BucketMap<>();

		for (IFile file : files) {
			Collection<Node> allIncludingNodes = getReferencingFiles(file);
			if (allIncludingNodes == null) {
				continue;
			}
			for (Node includingNode : allIncludingNodes) {
				IFile includingFile = (IFile) includingNode.getFile().getResource();

				if (!files.contains(includingFile)) {
					errors.add(file, includingFile);
				}
			}
		}
		for (IFile node : errors.getKeys()) {
			status.addWarning(
					MessageFormat.format(PHPRefactoringCoreMessages.getString("MoveUtils.10"), node.getName())); //$NON-NLS-1$
			for (IFile includingNode : errors.get(node)) {
				status.addWarning(MessageFormat.format(PHPRefactoringCoreMessages.getString("MoveUtils.11"), //$NON-NLS-1$
						node.getProjectRelativePath().toString(), includingNode.getProjectRelativePath().toString()));
			}
		}
		return status;
	}

	public static Collection<Node> getReferencingFiles(IFile file) {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		return getReferencingFiles(sourceModule);
	}

	public static Collection<Node> getReferencingFiles(ISourceModule sourceModule) {
		if (sourceModule != null) {
			ReferenceTree tree = FileNetworkUtility.buildReferencingFilesTree(sourceModule, null);

			if (tree != null && tree.getRoot() != null) {
				return tree.getRoot().getChildren();
			}
		}
		return Collections.emptyList();
	}

	public static Collection<Node> getReferencedFiles(IFile file) {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		return getReferencedFiles(sourceModule);
	}

	public static Collection<Node> getReferencedFiles(ISourceModule sourceModule) {
		if (sourceModule != null) {
			ReferenceTree tree = FileNetworkUtility.buildReferencedFilesTree(sourceModule, null);

			if (tree != null && tree.getRoot() != null) {
				return tree.getRoot().getChildren();
			}
		}
		return Collections.emptyList();

	}

}
