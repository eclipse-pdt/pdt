/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IConfirmQuery;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IReorgQueries;
import org.eclipse.dltk.internal.corext.refactoring.reorg.ReorgUtils;
import org.eclipse.dltk.internal.corext.util.Resources;

class ReadOnlyResourceFinder {
	private ReadOnlyResourceFinder() {
	}

	static boolean confirmDeleteOfReadOnlyElements(
			IModelElement[] modelElements, IResource[] resources,
			IReorgQueries queries) throws CoreException {
		String queryTitle = RefactoringCoreMessages.ReadOnlyResourceFinder_0;
		String question = RefactoringCoreMessages.ReadOnlyResourceFinder_1;
		return ReadOnlyResourceFinder.confirmOperationOnReadOnlyElements(
				queryTitle, question, modelElements, resources, queries);
	}

	static boolean confirmMoveOfReadOnlyElements(IModelElement[] modelElements,
			IResource[] resources, IReorgQueries queries) throws CoreException {
		String queryTitle = RefactoringCoreMessages.ReadOnlyResourceFinder_2;
		String question = RefactoringCoreMessages.ReadOnlyResourceFinder_3;
		return ReadOnlyResourceFinder.confirmOperationOnReadOnlyElements(
				queryTitle, question, modelElements, resources, queries);
	}

	private static boolean confirmOperationOnReadOnlyElements(
			String queryTitle, String question, IModelElement[] modelElements,
			IResource[] resources, IReorgQueries queries) throws CoreException {
		boolean hasReadOnlyResources = ReadOnlyResourceFinder
				.hasReadOnlyResourcesAndSubResources(modelElements, resources);
		if (hasReadOnlyResources) {
			IConfirmQuery query = queries.createYesNoQuery(queryTitle, false,
					IReorgQueries.CONFIRM_READ_ONLY_ELEMENTS);
			return query.confirm(question);
		}
		return true;
	}

	private static boolean hasReadOnlyResourcesAndSubResources(
			IModelElement[] modelElements, IResource[] resources)
			throws CoreException {
		return (hasReadOnlyResourcesAndSubResources(resources) || hasReadOnlyResourcesAndSubResources(modelElements));
	}

	private static boolean hasReadOnlyResourcesAndSubResources(
			IModelElement[] modelElements) throws CoreException {
		for (int i = 0; i < modelElements.length; i++) {
			if (hasReadOnlyResourcesAndSubResources(modelElements[i]))
				return true;
		}
		return false;
	}

	private static boolean hasReadOnlyResourcesAndSubResources(
			IModelElement modelElement) throws CoreException {
		switch (modelElement.getElementType()) {
		case IModelElement.SOURCE_MODULE:
			IResource resource = ReorgUtils.getResource(modelElement);
			return (resource != null && Resources.isReadOnly(resource));
		case IModelElement.SCRIPT_FOLDER:
			IResource packResource = ReorgUtils.getResource(modelElement);
			if (packResource == null)
				return false;
			IScriptFolder pack = (IScriptFolder) modelElement;
			if (Resources.isReadOnly(packResource))
				return true;
			Object[] nonScript = pack.getForeignResources();
			for (int i = 0; i < nonScript.length; i++) {
				Object object = nonScript[i];
				if (object instanceof IResource
						&& hasReadOnlyResourcesAndSubResources((IResource) object))
					return true;
			}
			return hasReadOnlyResourcesAndSubResources(pack.getChildren());
		case IModelElement.PROJECT_FRAGMENT:
			IProjectFragment root = (IProjectFragment) modelElement;
			if (root.isArchive())
				return false;
			IResource pfrResource = ReorgUtils.getResource(modelElement);
			if (pfrResource == null)
				return false;
			if (Resources.isReadOnly(pfrResource))
				return true;
			Object[] nonScript1 = root.getForeignResources();
			for (int i = 0; i < nonScript1.length; i++) {
				Object object = nonScript1[i];
				if (object instanceof IResource
						&& hasReadOnlyResourcesAndSubResources((IResource) object))
					return true;
			}
			return hasReadOnlyResourcesAndSubResources(root.getChildren());

		case IModelElement.FIELD:
			// case IModelElement.IMPORT_CONTAINER:
			// case IModelElement.IMPORT_DECLARATION:
			// case IModelElement.INITIALIZER:
		case IModelElement.METHOD:
			// case IModelElement.PACKAGE_DECLARATION:
		case IModelElement.TYPE:
			return false;
		default:
			Assert.isTrue(false);// not handled here
			return false;
		}
	}

	private static boolean hasReadOnlyResourcesAndSubResources(
			IResource[] resources) throws CoreException {
		for (int i = 0; i < resources.length; i++) {
			if (hasReadOnlyResourcesAndSubResources(resources[i]))
				return true;
		}
		return false;
	}

	private static boolean hasReadOnlyResourcesAndSubResources(
			IResource resource) throws CoreException {
		if (resource.isLinked()) // we don't want to count these because we
									// never actually delete linked resources
			return false;
		if (Resources.isReadOnly(resource))
			return true;
		if (resource instanceof IContainer)
			return hasReadOnlyResourcesAndSubResources(((IContainer) resource)
					.members());
		return false;
	}
}
