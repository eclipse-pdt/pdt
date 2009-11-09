/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ArchiveProjectFragment;
import org.eclipse.dltk.internal.core.ExternalScriptFolder;
import org.eclipse.dltk.internal.core.ModelStatus;
import org.eclipse.dltk.internal.core.MultiOperation;
import org.eclipse.dltk.internal.core.util.Messages;

/**
 * This operation deletes a collection of resources and all of their children.
 * It does not delete resources which do not belong to the Script Model (eg GIF
 * files).
 */
public class DeleteResourceElementsOperation extends MultiOperation {
	/**
	 * When executed, this operation will delete the given elements. The
	 * elements to delete cannot be <code>null</code> or empty, and must have a
	 * corresponding resource.
	 */
	protected DeleteResourceElementsOperation(
			IModelElement[] elementsToProcess, boolean force) {
		super(elementsToProcess, force);
	}

	/**
	 * Deletes the direct children of <code>frag</code> corresponding to its
	 * kind (K_SOURCE or K_BINARY), and deletes the corresponding folder if it
	 * is then empty.
	 */
	private void deleteScriptFolder(IScriptFolder frag) throws ModelException {
		IResource res = frag.getResource();
		if (res != null) {
			// collect the children to remove
			IModelElement[] childrenOfInterest = frag.getChildren();
			if (childrenOfInterest.length > 0) {
				IResource[] resources = new IResource[childrenOfInterest.length];
				// remove the children
				for (int i = 0; i < childrenOfInterest.length; i++) {
					resources[i] = childrenOfInterest[i]
							.getCorrespondingResource();
				}
				deleteResources(resources, force);
			}
			// Discard non-java resources
			Object[] nonScriptResources = frag.getForeignResources();
			int actualResourceCount = 0;
			for (int i = 0, max = nonScriptResources.length; i < max; i++) {
				if (nonScriptResources[i] instanceof IResource)
					actualResourceCount++;
			}
			IResource[] actualNonScriptResources = new IResource[actualResourceCount];
			for (int i = 0, max = nonScriptResources.length, index = 0; i < max; i++) {
				if (nonScriptResources[i] instanceof IResource)
					actualNonScriptResources[index++] = (IResource) nonScriptResources[i];
			}
			deleteResources(actualNonScriptResources, force);
			// delete remaining files in this package (.class file in the case
			// where Proj=src=bin)
			// IResource[] remainingFiles;
			// try {
			// remainingFiles = ((IContainer) res).members();
			// } catch (CoreException ce) {
			// throw new ModelException(ce);
			// }
			// boolean isEmpty = true;
			// for (int i = 0, length = remainingFiles.length; i < length; i++)
			// {
			// // IResource file = remainingFiles[i];
			// // if (file instanceof IFile ) {
			// // this.deleteResource(file, IResource.FORCE |
			// // IResource.KEEP_HISTORY);
			// // } else {
			// isEmpty = false;
			// // }
			// }
			if (!frag.isRootFolder()/*
									 * don't delete default package's folder:
									 * see
									 * https://bugs.eclipse.org/bugs/show_bug
									 * .cgi?id=38450
									 */) {
				// delete recursively empty folders
				IResource fragResource = frag.getResource();
				if (fragResource != null) {
					deleteEmptyScriptFolder(frag, false, fragResource
							.getParent());
				}
			}
		}
	}

	/**
	 * @see MultiOperation
	 */
	protected String getMainTaskName() {
		return Messages.operation_deleteResourceProgress;
	}

	/**
	 * @see MultiOperation This method delegate to <code>deleteResource</code>
	 *      or <code>deleteScriptFolder</code> depending on the type of
	 *      <code>element</code>.
	 */
	protected void processElement(IModelElement element) throws ModelException {
		switch (element.getElementType()) {
		case IModelElement.SOURCE_MODULE:
			deleteResource(element.getResource(), force ? IResource.FORCE
					| IResource.KEEP_HISTORY : IResource.KEEP_HISTORY);
			break;
		case IModelElement.SCRIPT_FOLDER:
			deleteScriptFolder((IScriptFolder) element);
			break;
		default:
			throw new ModelException(new ModelStatus(
					IModelStatusConstants.INVALID_ELEMENT_TYPES, element));
		}
		// ensure the element is closed
		if (element instanceof IOpenable) {
			((IOpenable) element).close();
		}
	}

	/**
	 * @see MultiOperation
	 */
	protected void verify(IModelElement element) throws ModelException {
		if (element == null || !element.exists())
			error(IModelStatusConstants.ELEMENT_DOES_NOT_EXIST, element);
		int type = element.getElementType();
		if (type <= IModelElement.PROJECT_FRAGMENT
				|| type > IModelElement.SOURCE_MODULE)
			error(IModelStatusConstants.INVALID_ELEMENT_TYPES, element);
		else if (type == IModelElement.SCRIPT_FOLDER
				&& (element instanceof ArchiveProjectFragment || element instanceof ExternalScriptFolder))
			error(IModelStatusConstants.INVALID_ELEMENT_TYPES, element);
		IResource resource = element.getResource();
		if (resource instanceof IFolder) {
			if (resource.isLinked()) {
				error(IModelStatusConstants.INVALID_RESOURCE, element);
			}
		}
	}
}
