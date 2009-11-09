/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import java.net.URI;
import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.internal.corext.refactoring.util.ModelElementUtil;
import org.eclipse.dltk.internal.corext.refactoring.util.ResourceUtil;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.ui.IWorkingSet;

public class ReorgUtils {
	public static boolean containsOnlyProjects(List elements) {
		if (elements.isEmpty())
			return false;
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			if (!isProject(iter.next()))
				return false;
		}
		return true;
	}

	public static boolean isProject(Object element) {
		return (element instanceof IScriptProject)
				|| (element instanceof IProject);
	}

	public static boolean isInsideSourceModule(IModelElement element) {
		return !(element instanceof ISourceModule)
				&& hasAncestorOfType(element, IModelElement.SOURCE_MODULE);
	}

	public static boolean hasAncestorOfType(IModelElement element, int type) {
		return element.getAncestor(type) != null;
	}

	public static ISourceModule getSourceModule(IModelElement modelElement) {
		if (modelElement instanceof ISourceModule)
			return (ISourceModule) modelElement;
		return (ISourceModule) modelElement
				.getAncestor(IModelElement.SOURCE_MODULE);
	}

	public static boolean isDeletedFromEditor(IModelElement elem) {
		if (!isInsideSourceModule(elem))
			return false;
		if (elem instanceof IMember) {// && ((IMember)elem).isBinary())
			return false;
		}
		ISourceModule cu = ReorgUtils.getSourceModule(elem);
		if (cu == null)
			return false;
		ISourceModule wc = cu;
		// TODO have to understand if this method is needed any longer. Since
		// with the new working copy support a element is never deleted from
		// the editor if we have a primary working copy.
		if (cu.equals(wc))
			return false;
		IModelElement wcElement = ScriptModelUtil.findInSourceModule(wc, elem);
		return wcElement == null || !wcElement.exists();
	}

	public static String getName(IModelElement element) throws ModelException {
		String pattern = createNamePattern(element);
		String[] args = createNameArguments(element);
		return Messages.format(pattern, args);
	}

	public static String getName(IResource resource) {
		String pattern = createNamePattern(resource);
		String[] args = createNameArguments(resource);
		return Messages.format(pattern, args);
	}

	private static String[] createNameArguments(IResource resource) {
		return new String[] { resource.getName() };
	}

	private static String createNamePattern(IResource resource) {
		switch (resource.getType()) {
		case IResource.FILE:
			return RefactoringCoreMessages.ReorgUtils_0;
		case IResource.FOLDER:
			return RefactoringCoreMessages.ReorgUtils_1;
		case IResource.PROJECT:
			return RefactoringCoreMessages.ReorgUtils_2;
		default:
			Assert.isTrue(false);
			return null;
		}
	}

	private static String createNamePattern(IModelElement element)
			throws ModelException {
		switch (element.getElementType()) {
		case IModelElement.SOURCE_MODULE:
			if (element instanceof ExternalSourceModule) {
				return RefactoringCoreMessages.ReorgUtils_21;
			} else {
				return RefactoringCoreMessages.ReorgUtils_4;
			}
		case IModelElement.FIELD:
			return RefactoringCoreMessages.ReorgUtils_5;
			// case IModelElement.IMPORT_CONTAINER:
			// return RefactoringCoreMessages.ReorgUtils_6;
			// case IModelElement.IMPORT_DECLARATION:
			// return RefactoringCoreMessages.ReorgUtils_7;
		case IModelElement.SCRIPT_PROJECT:
			return RefactoringCoreMessages.ReorgUtils_9;
		case IModelElement.METHOD:
			if (((IMethod) element).isConstructor())
				return RefactoringCoreMessages.ReorgUtils_10;
			else
				return RefactoringCoreMessages.ReorgUtils_11;
			// case IModelElement.PACKAGE_DECLARATION:
			// return RefactoringCoreMessages.ReorgUtils_12;
		case IModelElement.SCRIPT_FOLDER:
			if (ModelElementUtil.isDefaultPackage(element))
				return RefactoringCoreMessages.ReorgUtils_13;
			else
				return org.eclipse.php.internal.ui.refactor.processors.Messages.ReorgUtils_14;
		case IModelElement.PROJECT_FRAGMENT:
			if (isSourceFolder(element))
				return RefactoringCoreMessages.ReorgUtils_15;
			// if (isClassFolder(element))
			// return RefactoringCoreMessages.ReorgUtils_16;
			return RefactoringCoreMessages.ReorgUtils_17;
		case IModelElement.TYPE:
			// IType type= (IType)element;
			return RefactoringCoreMessages.ReorgUtils_18;
		default:
			Assert.isTrue(false);
			return null;
		}
	}

	private static String[] createNameArguments(IModelElement element)
			throws ModelException {
		switch (element.getElementType()) {
		case IModelElement.SOURCE_MODULE:
			if (element instanceof ExternalSourceModule) {
				return new String[] { ((ExternalSourceModule) element)
						.getFullPath().toString() };
			} else {
				return new String[] { element.getElementName() };
			}
		case IModelElement.FIELD:
			return new String[] { element.getElementName() };
			// case IModelElement.IMPORT_CONTAINER:
			// return new String[0];
			// case IModelElement.IMPORT_DECLARATION:
			// return new String[]{element.getElementName()};
		case IModelElement.SCRIPT_PROJECT:
			return new String[] { element.getElementName() };
		case IModelElement.METHOD:
			return new String[] { element.getElementName() };
			// case IModelElement.PACKAGE_DECLARATION:
			// if (ScriptElementUtil.isDefaultPackage(element))
			// return new String[0];
			// else
			// return new String[]{element.getElementName()};
		case IModelElement.SCRIPT_FOLDER:
			return new String[] { element.getElementName() };
		case IModelElement.PROJECT_FRAGMENT:
			return new String[] { element.getElementName() };
		case IModelElement.TYPE:
			// IType type= (IType)element;
			// String name= type.getElementName();
			return new String[] { element.getElementName() };
		default:
			Assert.isTrue(false);
			return null;
		}
	}

	public static boolean isSourceFolder(IModelElement modelElement)
			throws ModelException {
		return (modelElement instanceof IProjectFragment)
				&& ((IProjectFragment) modelElement).getKind() == IProjectFragment.K_SOURCE;
	}

	public static IResource[] getResources(List elements) {
		List resources = new ArrayList(elements.size());
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IResource)
				resources.add(element);
		}
		return (IResource[]) resources.toArray(new IResource[resources.size()]);
	}

	public static IResource getResource(IModelElement element) {
		if (element instanceof ISourceModule)
			return ((ISourceModule) element).getPrimary().getResource();
		else
			return element.getResource();
	}

	public static IResource[] getResources(IModelElement[] elements) {
		List resultArray = new ArrayList();
		for (int i = 0; i < elements.length; i++) {
			IResource res = ReorgUtils.getResource(elements[i]);
			if (res != null) {
				resultArray.add(res);
			}
		}
		return (IResource[]) resultArray.toArray(new IResource[resultArray
				.size()]);
	}

	public static IResource[] getNotLinked(IResource[] resources) {
		Collection result = new ArrayList(resources.length);
		for (int i = 0; i < resources.length; i++) {
			IResource resource = resources[i];
			if (resource != null && !result.contains(resource)
					&& !resource.isLinked())
				result.add(resource);
		}
		return (IResource[]) result.toArray(new IResource[result.size()]);
	}

	public static IResource[] getNotNulls(IResource[] resources) {
		Collection result = new ArrayList(resources.length);
		for (int i = 0; i < resources.length; i++) {
			IResource resource = resources[i];
			if (resource != null && !result.contains(resource))
				result.add(resource);
		}
		return (IResource[]) result.toArray(new IResource[result.size()]);
	}

	public static Map groupBySourceModule(List modelElements) {
		Map result = new HashMap();
		for (Iterator iter = modelElements.iterator(); iter.hasNext();) {
			IModelElement element = (IModelElement) iter.next();
			ISourceModule cu = ReorgUtils.getSourceModule(element);
			if (cu != null) {
				if (!result.containsKey(cu))
					result.put(cu, new ArrayList(1));
				((List) result.get(cu)).add(element);
			}
		}
		return result;
	}

	public static List getElementsOfType(IModelElement[] modelElements, int type) {
		List result = new ArrayList(modelElements.length);
		for (int i = 0; i < modelElements.length; i++) {
			if (isOfType(modelElements[i], type))
				result.add(modelElements[i]);
		}
		return result;
	}

	private static boolean isOfType(IModelElement element, int type) {
		return element.getElementType() == type;// this is _not_ a mask
	}

	private static boolean isOfType(IResource resource, int type) {
		return resource != null && isFlagSet(resource.getType(), type);
	}

	private static boolean isFlagSet(int flags, int flag) {
		return (flags & flag) != 0;
	}

	public static IModelElement[] setMinus(IModelElement[] setToRemoveFrom,
			IModelElement[] elementsToRemove) {
		Set setMinus = new HashSet(setToRemoveFrom.length
				- setToRemoveFrom.length);
		setMinus.addAll(Arrays.asList(setToRemoveFrom));
		setMinus.removeAll(Arrays.asList(elementsToRemove));
		return (IModelElement[]) setMinus.toArray(new IModelElement[setMinus
				.size()]);
	}

	public static IModelElement[] union(IModelElement[] set1,
			IModelElement[] set2) {
		List union = new ArrayList(set1.length + set2.length);// use lists to
		// avoid
		// sequence
		// problems
		addAll(set1, union);
		addAll(set2, union);
		return (IModelElement[]) union.toArray(new IModelElement[union.size()]);
	}

	public static IResource[] union(IResource[] set1, IResource[] set2) {
		List union = new ArrayList(set1.length + set2.length);// use lists to
		// avoid
		// sequence
		// problems
		addAll(ReorgUtils.getNotNulls(set1), union);
		addAll(ReorgUtils.getNotNulls(set2), union);
		return (IResource[]) union.toArray(new IResource[union.size()]);
	}

	private static void addAll(Object[] array, List list) {
		for (int i = 0; i < array.length; i++) {
			if (!list.contains(array[i]))
				list.add(array[i]);
		}
	}

	public static IResource[] setMinus(IResource[] setToRemoveFrom,
			IResource[] elementsToRemove) {
		Set setMinus = new HashSet(setToRemoveFrom.length
				- setToRemoveFrom.length);
		setMinus.addAll(Arrays.asList(setToRemoveFrom));
		setMinus.removeAll(Arrays.asList(elementsToRemove));
		return (IResource[]) setMinus.toArray(new IResource[setMinus.size()]);
	}

	public static IModelElement[] getModelElements(List elements) {
		List resources = new ArrayList(elements.size());
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IModelElement)
				resources.add(element);
		}
		return (IModelElement[]) resources.toArray(new IModelElement[resources
				.size()]);
	}

	public static ISourceModule[] getSourceModules(IModelElement[] modelElements) {
		ISourceModule[] result = new ISourceModule[modelElements.length];
		for (int i = 0; i < modelElements.length; i++) {
			result[i] = getSourceModule(modelElements[i]);
		}
		return result;
	}

	public static IWorkingSet[] getWorkingSets(List elements) {
		List result = new ArrayList(1);
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IWorkingSet) {
				result.add(element);
			}
		}
		return (IWorkingSet[]) result.toArray(new IWorkingSet[result.size()]);
	}

	public static void splitIntoModelElementsAndResources(Object[] elements,
			List modelElementResult, List resourceResult) {
		for (int i = 0; i < elements.length; i++) {
			Object element = elements[i];
			if (element instanceof IModelElement) {
				modelElementResult.add(element);
			} else if (element instanceof IResource) {
				IResource resource = (IResource) element;
				IModelElement jElement = DLTKCore.create(resource);
				if (jElement != null && jElement.exists())
					modelElementResult.add(jElement);
				else
					resourceResult.add(resource);
			}
		}
	}

	public static boolean containsElementOrParent(Set elements,
			IModelElement element) {
		if (elements.contains(element))
			return true;
		IModelElement parent = element.getParent();
		while (parent != null) {
			if (elements.contains(parent))
				return true;
			parent = parent.getParent();
		}
		return false;
	}

	public static boolean containsElementOrParent(Set elements,
			IResource element) {
		if (elements.contains(element))
			return true;
		IResource parent = element.getParent();
		while (parent != null) {
			if (elements.contains(parent))
				return true;
			IModelElement parentAsScriptElement = DLTKCore.create(parent);
			if (parentAsScriptElement != null && parentAsScriptElement.exists()
					&& elements.contains(parentAsScriptElement))
				return true;
			parent = parent.getParent();
		}
		return false;
	}

	public static boolean hasElementsNotOfType(IResource[] resources,
			int typeMask) {
		for (int i = 0; i < resources.length; i++) {
			IResource resource = resources[i];
			if (resource != null && !isOfType(resource, typeMask))
				return true;
		}
		return false;
	}

	public static boolean hasElementsNotOfType(IModelElement[] modelElements,
			int type) {
		for (int i = 0; i < modelElements.length; i++) {
			IModelElement element = modelElements[i];
			if (element != null && !isOfType(element, type))
				return true;
		}
		return false;
	}

	public static boolean canBeDestinationForLinkedResources(IResource resource) {
		return resource.isAccessible() && resource instanceof IProject;
	}

	public static boolean canBeDestinationForLinkedResources(
			IModelElement modelElement) {
		if (modelElement instanceof IProjectFragment) {
			return isProjectFragmentCorrespondingToProject((IProjectFragment) modelElement);
		} else if (modelElement instanceof IScriptProject) {
			return true;// XXX ???
		} else
			return false;
	}

	private static boolean isProjectFragmentCorrespondingToProject(
			IProjectFragment root) {
		return root.getResource() instanceof IProject;
	}

	public static boolean isArchiveOrExterrnalMember(IModelElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			IModelElement element = elements[i];
			IProjectFragment root = (IProjectFragment) element
					.getAncestor(IModelElement.PROJECT_FRAGMENT);
			if (root != null && (root.isArchive() || root.isExternal()))
				return true;
		}
		return false;
	}

	public static boolean hasElementsOfType(IModelElement[] modelElements,
			int type) {
		for (int i = 0; i < modelElements.length; i++) {
			IModelElement element = modelElements[i];
			if (element != null && isOfType(element, type))
				return true;
		}
		return false;
	}

	public static boolean hasElementsOfType(IModelElement[] modelElements,
			int[] types) {
		for (int i = 0; i < types.length; i++) {
			if (hasElementsOfType(modelElements, types[i]))
				return true;
		}
		return false;
	}

	public static boolean hasElementsOfType(IResource[] resources, int typeMask) {
		for (int i = 0; i < resources.length; i++) {
			IResource resource = resources[i];
			if (resource != null && isOfType(resource, typeMask))
				return true;
		}
		return false;
	}

	public static IFile[] getFiles(IResource[] resources) {
		Set result = getResourcesOfType(resources, IResource.FILE);
		return (IFile[]) result.toArray(new IFile[result.size()]);
	}

	public static Set getResourcesOfType(IResource[] resources, int typeMask) {
		Set result = new HashSet(resources.length);
		for (int i = 0; i < resources.length; i++) {
			if (isOfType(resources[i], typeMask))
				result.add(resources[i]);
		}
		return result;
	}

	public static IFolder[] getFolders(IResource[] resources) {
		Set result = getResourcesOfType(resources, IResource.FOLDER);
		return (IFolder[]) result.toArray(new IFolder[result.size()]);
	}

	public static IProjectFragment getCorrespondingProjectFragment(
			IScriptProject p) throws ModelException {
		IProjectFragment[] roots = p.getProjectFragments();
		for (int i = 0; i < roots.length; i++) {
			if (isProjectFragmentCorrespondingToProject(roots[i]))
				return roots[i];
		}
		return null;
	}

	public static boolean containsLinkedResources(IResource[] resources) {
		for (int i = 0; i < resources.length; i++) {
			if (resources[i] != null && resources[i].isLinked())
				return true;
		}
		return false;
	}

	public static boolean containsLinkedResources(IModelElement[] modelElements) {
		for (int i = 0; i < modelElements.length; i++) {
			IResource res = getResource(modelElements[i]);
			if (res != null && res.isLinked())
				return true;
		}
		return false;
	}

	public static boolean isProjectFragment(IScriptProject scriptProject)
			throws ModelException {
		return getCorrespondingProjectFragment(scriptProject) != null;
	}

	public static boolean areEqualInWorkspaceOrOnDisk(IResource r1, IResource r2) {
		if (r1 == null || r2 == null)
			return false;
		if (r1.equals(r2))
			return true;
		URI r1Location = r1.getLocationURI();
		URI r2Location = r2.getLocationURI();
		if (r1Location == null || r2Location == null)
			return false;
		return r1Location.equals(r2Location);
	}

	public static boolean isParentInWorkspaceOrOnDisk(IScriptFolder pack,
			IProjectFragment root) {
		if (pack == null)
			return false;
		IModelElement packParent = pack.getParent();
		if (packParent == null)
			return false;
		if (packParent.equals(root))
			return true;
		IResource packageResource = ResourceUtil.getResource(pack);
		IResource packageRootResource = ResourceUtil.getResource(root);
		return isParentInWorkspaceOrOnDisk(packageResource, packageRootResource);
	}

	public static boolean isParentInWorkspaceOrOnDisk(IProjectFragment root,
			IScriptProject scriptProject) {
		if (root == null)
			return false;
		IModelElement rootParent = root.getParent();
		if (rootParent == null)
			return false;
		if (rootParent.equals(root))
			return true;
		IResource packageResource = ResourceUtil.getResource(root);
		IResource packageRootResource = ResourceUtil.getResource(scriptProject);
		return isParentInWorkspaceOrOnDisk(packageResource, packageRootResource);
	}

	public static boolean isParentInWorkspaceOrOnDisk(ISourceModule cu,
			IScriptFolder dest) {
		if (cu == null)
			return false;
		IModelElement cuParent = cu.getParent();
		if (cuParent == null)
			return false;
		if (cuParent.equals(dest))
			return true;
		IResource cuResource = ResourceUtil.getResource(cu);
		IResource packageResource = ResourceUtil.getResource(dest);
		return isParentInWorkspaceOrOnDisk(cuResource, packageResource);
	}

	public static boolean isParentInWorkspaceOrOnDisk(IResource res,
			IResource maybeParent) {
		if (res == null)
			return false;
		return areEqualInWorkspaceOrOnDisk(res.getParent(), maybeParent);
	}
}
