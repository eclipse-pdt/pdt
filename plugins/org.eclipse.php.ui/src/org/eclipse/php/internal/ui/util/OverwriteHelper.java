/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IConfirmQuery;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IReorgQueries;
import org.eclipse.dltk.internal.corext.refactoring.reorg.ReorgUtils;
import org.eclipse.dltk.internal.corext.refactoring.util.ResourceUtil;
import org.eclipse.dltk.internal.corext.util.Messages;

class OverwriteHelper {
	private Object fDestination;
	private IFile[] fFiles = new IFile[0];
	private IFolder[] fFolders = new IFolder[0];
	private ISourceModule[] fCus = new ISourceModule[0];
	private IProjectFragment[] fRoots = new IProjectFragment[0];
	private IScriptFolder[] fScriptFolders = new IScriptFolder[0];

	public void setFiles(IFile[] files) {
		Assert.isNotNull(files);
		fFiles = files;
	}

	public void setFolders(IFolder[] folders) {
		Assert.isNotNull(folders);
		fFolders = folders;
	}

	public void setCus(ISourceModule[] cus) {
		Assert.isNotNull(cus);
		fCus = cus;
	}

	public void setProjectFragments(IProjectFragment[] roots) {
		Assert.isNotNull(roots);
		fRoots = roots;
	}

	public void setPackages(IScriptFolder[] fragments) {
		Assert.isNotNull(fragments);
		fScriptFolders = fragments;
	}

	public IFile[] getFilesWithoutUnconfirmedOnes() {
		return fFiles;
	}

	public IFolder[] getFoldersWithoutUnconfirmedOnes() {
		return fFolders;
	}

	public ISourceModule[] getCusWithoutUnconfirmedOnes() {
		return fCus;
	}

	public IProjectFragment[] getProjectFragmentsWithoutUnconfirmedOnes() {
		return fRoots;
	}

	public IScriptFolder[] getPackagesWithoutUnconfirmedOnes() {
		return fScriptFolders;
	}

	public void confirmOverwritting(IReorgQueries reorgQueries,
			IModelElement destination) {
		Assert.isNotNull(destination);
		fDestination = destination;
		confirmOverwritting(reorgQueries);
	}

	public void confirmOverwritting(IReorgQueries reorgQueries,
			IResource destination) {
		Assert.isNotNull(destination);
		Assert.isNotNull(reorgQueries);
		fDestination = destination;
		confirmOverwritting(reorgQueries);
	}

	private void confirmOverwritting(IReorgQueries reorgQueries) {
		IConfirmQuery overwriteQuery = reorgQueries
				.createYesYesToAllNoNoToAllQuery(
						RefactoringCoreMessages.OverwriteHelper_0, true,
						IReorgQueries.CONFIRM_OVERWRITTING);
		IConfirmQuery skipQuery = reorgQueries.createSkipQuery(
				RefactoringCoreMessages.OverwriteHelper_2,
				IReorgQueries.CONFIRM_SKIPPING);
		confirmFileOverwritting(overwriteQuery);
		confirmFolderOverwritting(skipQuery);
		confirmCuOverwritting(overwriteQuery);
		confirmProjectFragmentOverwritting(skipQuery);
		confirmPackageOverwritting(overwriteQuery);
	}

	private void confirmProjectFragmentOverwritting(IConfirmQuery overwriteQuery) {
		List toNotOverwrite = new ArrayList(1);
		for (int i = 0; i < fRoots.length; i++) {
			IProjectFragment root = fRoots[i];
			if (canOverwrite(root)
					&& !skip(root.getElementName(), overwriteQuery))
				toNotOverwrite.add(root);
		}
		IProjectFragment[] roots = (IProjectFragment[]) toNotOverwrite
				.toArray(new IProjectFragment[toNotOverwrite.size()]);
		fRoots = ArrayTypeConverter.toProjectFragmentArray(ReorgUtils.setMinus(
				fRoots, roots));
	}

	private void confirmCuOverwritting(IConfirmQuery overwriteQuery) {
		List cusToNotOverwrite = new ArrayList(1);
		for (int i = 0; i < fCus.length; i++) {
			ISourceModule cu = fCus[i];
			if (canOverwrite(cu) && !overwrite(cu, overwriteQuery))
				cusToNotOverwrite.add(cu);
		}
		ISourceModule[] cus = (ISourceModule[]) cusToNotOverwrite
				.toArray(new ISourceModule[cusToNotOverwrite.size()]);
		fCus = ArrayTypeConverter.toCuArray(ReorgUtils.setMinus(fCus, cus));
	}

	private void confirmFolderOverwritting(IConfirmQuery overwriteQuery) {
		List foldersToNotOverwrite = new ArrayList(1);
		for (int i = 0; i < fFolders.length; i++) {
			IFolder folder = fFolders[i];
			if (canOverwrite(folder) && !skip(folder.getName(), overwriteQuery))
				foldersToNotOverwrite.add(folder);
		}
		IFolder[] folders = (IFolder[]) foldersToNotOverwrite
				.toArray(new IFolder[foldersToNotOverwrite.size()]);
		fFolders = ArrayTypeConverter.toFolderArray(ReorgUtils.setMinus(
				fFolders, folders));
	}

	private void confirmFileOverwritting(IConfirmQuery overwriteQuery) {
		List filesToNotOverwrite = new ArrayList(1);
		for (int i = 0; i < fFiles.length; i++) {
			IFile file = fFiles[i];
			if (canOverwrite(file) && !overwrite(file, overwriteQuery))
				filesToNotOverwrite.add(file);
		}
		IFile[] files = (IFile[]) filesToNotOverwrite
				.toArray(new IFile[filesToNotOverwrite.size()]);
		fFiles = ArrayTypeConverter.toFileArray(ReorgUtils.setMinus(fFiles,
				files));
	}

	private void confirmPackageOverwritting(IConfirmQuery overwriteQuery) {
		List toNotOverwrite = new ArrayList(1);
		for (int i = 0; i < fScriptFolders.length; i++) {
			IScriptFolder pack = fScriptFolders[i];
			if (canOverwrite(pack) && !overwrite(pack, overwriteQuery))
				toNotOverwrite.add(pack);
		}
		IScriptFolder[] packages = (IScriptFolder[]) toNotOverwrite
				.toArray(new IScriptFolder[toNotOverwrite.size()]);
		fScriptFolders = ArrayTypeConverter.toPackageArray(ReorgUtils.setMinus(
				fScriptFolders, packages));
	}

	private boolean canOverwrite(IScriptFolder pack) {
		Assert.isTrue(fDestination instanceof IProjectFragment);
		IProjectFragment destination = (IProjectFragment) fDestination;
		return !destination.equals(pack.getParent())
				&& destination.getScriptFolder(pack.getElementName()).exists();
	}

	private boolean canOverwrite(IResource resource) {
		if (resource == null)
			return false;
		IResource destinationResource = ResourceUtil.getResource(fDestination);
		if (destinationResource.equals(resource.getParent()))
			return false;
		if (destinationResource instanceof IContainer) {
			IContainer container = (IContainer) destinationResource;
			IResource member = container.findMember(resource.getName());
			if (member == null || !member.exists())
				return false;
			if (member instanceof IContainer) {
				try {
					if (((IContainer) member).members().length == 0)
						return false;
				} catch (CoreException e) {
					return true;
				}
			}
			return true;
		}
		return false;
	}

	private boolean canOverwrite(IProjectFragment root) {
		Assert.isTrue(fDestination instanceof IScriptProject);
		IScriptProject destination = (IScriptProject) fDestination;
		IFolder conflict = destination.getProject().getFolder(
				root.getElementName());
		try {
			return !destination.equals(root.getParent()) && conflict.exists()
					&& conflict.members().length > 0;
		} catch (CoreException e) {
			return true;
		}
	}

	private boolean canOverwrite(ISourceModule cu) {
		if (fDestination instanceof IScriptFolder) {
			IScriptFolder destination = (IScriptFolder) fDestination;
			return !destination.equals(cu.getParent())
					&& destination.getSourceModule(cu.getElementName())
							.exists();
		} else {
			return canOverwrite(ReorgUtils.getResource(cu));
		}
	}

	private static boolean overwrite(IResource resource,
			IConfirmQuery overwriteQuery) {
		return overwrite(resource.getName(), overwriteQuery);
	}

	private static boolean overwrite(IModelElement element,
			IConfirmQuery overwriteQuery) {
		return overwrite(element.getElementName(), overwriteQuery);
	}

	private static boolean overwrite(String name, IConfirmQuery overwriteQuery) {
		String question = Messages.format(
				RefactoringCoreMessages.OverwriteHelper_1, name);
		return overwriteQuery.confirm(question);
	}

	private static boolean skip(String name, IConfirmQuery overwriteQuery) {
		String question = Messages.format(
				RefactoringCoreMessages.OverwriteHelper_3, name);
		return overwriteQuery.confirm(question);
	}
}
