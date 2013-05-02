/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.refactoring.reorg.RefactoringModifications;
import org.eclipse.dltk.internal.corext.refactoring.util.ModelElementUtil;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.*;

/**
 * A modification collector for delete operations.
 * <p>
 * Note that the field <code>fPackagesToDelete</code> contains the actual
 * package when executing <code>handleScriptFolderDelete</code>. This is part of
 * the algorithm to check if a parent folder can be deleted.
 * </p>
 */
public class DeleteModifications extends RefactoringModifications {

	private List fDelete;
	private List fPackagesToDelete;

	public DeleteModifications() {
		fDelete = new ArrayList();
		fPackagesToDelete = new ArrayList();
	}

	public void delete(IResource resource) {
		fDelete.add(resource);
	}

	public void delete(IResource[] resources) {
		for (int i = 0; i < resources.length; i++) {
			delete(resources[i]);
		}
	}

	public void delete(IModelElement[] elements) throws CoreException {
		for (int i = 0; i < elements.length; i++) {
			delete(elements[i]);
		}
	}

	public void delete(IModelElement element) throws CoreException {
		switch (element.getElementType()) {
		case IModelElement.SCRIPT_MODEL:
			return;
		case IModelElement.SCRIPT_PROJECT:
			fDelete.add(element);
			if (element.getResource() != null)
				getResourceModifications().addDelete(element.getResource());
			return;
		case IModelElement.PROJECT_FRAGMENT:
			fDelete.add(element);
			IResource resource = element.getResource();
			// Flag an resource change even if we have an archive. If it is
			// internal (we have a underlying resource then we have a resource
			// change.
			if (resource != null)
				getResourceModifications().addDelete(resource);
			return;
		case IModelElement.SCRIPT_FOLDER:
			fDelete.add(element);
			fPackagesToDelete.add(element);
			return;
		case IModelElement.SOURCE_MODULE:
			fDelete.add(element);
			try {
				IType[] types = ((ISourceModule) element).getTypes();
				fDelete.addAll(Arrays.asList(types));
			} catch (ModelException e) {
				// Ignore content retrieve errors
				DLTKUIPlugin.log(e);
			}
			if (element.getResource() != null)
				getResourceModifications().addDelete(element.getResource());
			return;
		case IModelElement.TYPE:
			fDelete.add(element);
			IType type = (IType) element;
			ISourceModule unit = type.getSourceModule();
			// TODO: Looks like a bug:
			// unit.getElementName().endsWith(type.getElementName())
			if (type.getDeclaringType() == null
					&& unit.getElementName().endsWith(type.getElementName())) {
				if (unit.getTypes().length == 1) {
					fDelete.add(unit);
					if (unit.getResource() != null)
						getResourceModifications()
								.addDelete(unit.getResource());
				}
			}
			return;
		default:
			fDelete.add(element);
		}

	}

	public List postProcess() throws CoreException {
		ArrayList resourcesCollector = new ArrayList();
		for (Iterator iter = fPackagesToDelete.iterator(); iter.hasNext();) {
			IScriptFolder pack = (IScriptFolder) iter.next();
			handleScriptFolderDelete(pack, resourcesCollector);
		}
		return resourcesCollector;
	}

	public void buildDelta(IResourceChangeDescriptionFactory deltaFactory) {
		for (Iterator iter = fDelete.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IResource) {
				deltaFactory.delete((IResource) element);
			}
		}
		getResourceModifications().buildDelta(deltaFactory);
	}

	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			RefactoringProcessor owner, String[] natures,
			SharableParticipants shared) {
		List result = new ArrayList();
		for (Iterator iter = fDelete.iterator(); iter.hasNext();) {
			result.addAll(Arrays.asList(ParticipantManager
					.loadDeleteParticipants(status, owner, iter.next(),
							new DeleteArguments(), natures, shared)));
		}
		result.addAll(Arrays.asList(getResourceModifications().getParticipants(
				status, owner, natures, shared)));
		return (RefactoringParticipant[]) result
				.toArray(new RefactoringParticipant[result.size()]);
	}

	/**
	 * This method collects file and folder deletion for notifying participants.
	 * Participants will get notified of * deletion of the package (in any case)
	 * * deletion of files within the package if only the files are deleted
	 * without the package folder ("package cleaning") * deletion of the package
	 * folder if it is not only cleared and if its parent is not removed as
	 * well.
	 * 
	 * @param resourcesCollector
	 * 
	 */
	private void handleScriptFolderDelete(IScriptFolder pack,
			ArrayList resourcesCollector) throws CoreException {
		final IContainer container = (IContainer) pack.getResource();
		if (container == null)
			return;

		final IResource[] members = container.members();

		/*
		 * Check whether this package is removed completely or only cleared. The
		 * default package can never be removed completely.
		 */
		if (!pack.isRootFolder() && canRemoveCompletely(pack)) {
			// This package is removed completely, which means its folder will
			// be
			// deleted as well. We only notify participants of the folder
			// deletion
			// if the parent folder of this folder will not be deleted as well:
			boolean parentIsMarked = false;
			final IScriptFolder parent = ModelElementUtil
					.getParentSubpackage(pack);
			if (parent == null) {
				// "Parent" is the default package which will never be
				// deleted physically
				parentIsMarked = false;
			} else {
				// Parent is marked if it is in the list
				parentIsMarked = fPackagesToDelete.contains(parent);
			}

			if (parentIsMarked) {
				// Parent is marked, but is it really deleted or only cleared?
				if (canRemoveCompletely(parent)) {
					// Parent can be removed completely, so we do not add
					// this folder to the list.
				} else {
					// Parent cannot be removed completely, but as this folder
					// can be removed, we notify the participant
					resourcesCollector.add(container);
					getResourceModifications().addDelete(container);
				}
			} else {
				// Parent will not be removed, but we will
				resourcesCollector.add(container);
				getResourceModifications().addDelete(container);
			}
		} else {
			// This package is only cleared because it has subpackages
			// (=subfolders)
			// which are not deleted. As the package is only cleared, its folder
			// will not be removed and so we must notify the participant of the
			// deleted children.
			for (int m = 0; m < members.length; m++) {
				IResource member = members[m];
				if (member instanceof IFile) {
					IFile file = (IFile) member;
					if ("class".equals(file.getFileExtension()) && file.isDerived()) //$NON-NLS-1$
						continue;
					IDLTKLanguageToolkit toolkit = DLTKLanguageManager
							.getLanguageToolkit(pack);
					if (DLTKCore.DEBUG) {
						System.err
								.println(Messages.DeleteModifications_1); 
					}
					if (pack.isRootFolder()
							&& (toolkit == null || (toolkit != null && (!DLTKContentTypeManager
									.isValidResourceForContentType(toolkit,
											file)))))
						continue;
					resourcesCollector.add(member);
					getResourceModifications().addDelete(member);
				}
				if (!pack.isRootFolder() && member instanceof IFolder) {
					// Normally, folder children of packages are packages
					// as well, but in case they have been removed from the
					// build
					// path, notify the participant
					IScriptFolder frag = (IScriptFolder) DLTKCore
							.create(member);
					if (frag == null) {
						resourcesCollector.add(member);
						getResourceModifications().addDelete(member);
					}
				}
			}
		}
	}

	/**
	 * Returns true if this initially selected package is really deletable (if
	 * it has non-selected sub packages, it may only be cleared).
	 */
	private boolean canRemoveCompletely(IScriptFolder pack)
			throws ModelException {
		final IScriptFolder[] subPackages = ModelElementUtil
				.getPackageAndSubpackages(pack);
		for (int i = 0; i < subPackages.length; i++) {
			if (!(subPackages[i].equals(pack))
					&& !(fPackagesToDelete.contains(subPackages[i])))
				return false;
		}
		return true;
	}
}
