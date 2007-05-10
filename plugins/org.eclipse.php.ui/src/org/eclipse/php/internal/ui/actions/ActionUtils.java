/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Shell;


public class ActionUtils {

	final private static IContentType contentType = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
	
	public static boolean containsOnlyProjects(List elements) {
		if (elements.isEmpty())
			return false;
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			if (!isProject(iter.next()))
				return false;
		}
		return true;
	}

	public static boolean containsOnly(List elements, Class clazz) {
		if (elements.isEmpty())
			return false;
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			//			if (!clazz.isAssignableFrom(iter.next().getClass()))
			if (!clazz.isAssignableFrom(iter.next().getClass()))
				return false;
		}
		return true;
	}

	public static boolean isProject(Object element) {
		return (element instanceof PHPProjectModel) || (element instanceof IProject);
	}

	public static IResource[] getResources(List elements) {
		return getResources(elements,false);
	}
	
	public static IResource[] getResources(List elements,boolean includePHPFileData) {
		List resources = new ArrayList(elements.size());
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IResource)
				resources.add(element);
			else if (includePHPFileData&& element instanceof PHPFileData)
			{
				resources.add(PHPModelUtil.getResource(element));
			}
		}
		return (IResource[]) resources.toArray(new IResource[resources.size()]);
	}

	public static IResource[] getResources(final Object[] elements) {
		List result = new ArrayList();
		for (int index = 0; index < elements.length; index++) {
			if (elements[index] instanceof IResource)
				result.add(elements[index]);
		}
		return (IResource[]) result.toArray(new IResource[result.size()]);
	}
	
	public static IResource[] getPHPResources(final Object[] elements) {
		List result = new ArrayList();
				
		for (int index = 0; index < elements.length; index++) {
			if (elements[index] instanceof IResource && contentType.isAssociatedWith(((IResource) elements[index]).getName()))
				result.add(elements[index]);
		}
		return (IResource[]) result.toArray(new IResource[result.size()]);
	}
	

	public static Object[] getPHPElements(List elements) {
		return getPHPElements(elements,false);
	}
	
	public static Object[] getPHPElements(List elements, boolean phpFileDataIsResource) {
		List resources = new ArrayList(elements.size());
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof PHPCodeData || element instanceof PHPProjectModel || element instanceof PHPWorkspaceModelManager)
				if (!phpFileDataIsResource || !(element instanceof PHPFileData) )
					resources.add(element);
		}
		return resources.toArray();
	}

	public static Object[] getPHPElements(final Object[] elements) {
		List resources = new ArrayList(elements.length);
		for (int i = 0; i < elements.length; i++) {
			Object element = elements[i];
			if (element instanceof PHPCodeData || element instanceof PHPProjectModel || element instanceof PHPWorkspaceModelManager)
				resources.add(element);
		}
		return resources.toArray();
	}

	public static boolean isPHPSource(PHPCodeData element) {
		IResource resource = PHPModelUtil.getResource(element);
		if (resource == null)
			return false;
		IProject resourceProject = resource.getProject();
		if (resourceProject == null)
			return false;
		IProjectNature nature;
		try {
			nature = resourceProject.getNature(PHPNature.ID);
			if (nature != null)
				return true;
		} catch (CoreException e) {
		}
		return false;
	}

	public static boolean isProcessable(Shell shell, Object element) {
		if (!(element instanceof PHPCodeData))
			return true;

		if (isPHPSource((PHPCodeData) element))
			return true;

		return false;
	}

	public static boolean isProcessable(Shell shell, PHPStructuredEditor editor) {
		if (editor == null)
			return true;
		PHPFileData input = SelectionConverter.getInput(editor);
		// if a PHP editor doesn't have an input of type PHP element
		// then it is for sure not on the build path
		if (input == null) {

			return false;
		}
		return isProcessable(shell, input);
	}

	public static boolean isRenameAvailable(final IResource resource) {
		if (resource == null)
			return false;
		if (!resource.exists())
			return false;
		if (!resource.isAccessible())
			return false;
		return true;
	}

	public static boolean isDeleteAvailable(final IResource resource) {
		if (!resource.exists() || resource.isPhantom())
			return false;
		if (resource.getType() == IResource.ROOT || resource.getType() == IResource.PROJECT)
			return false;
		return true;
	}

	public static boolean isDeleteAvailable(final Object element) {

		if (element instanceof PHPWorkspaceModelManager || element instanceof PHPProjectModel)
			return false;

		if (PHPUiConstants.DISABLE_ELEMENT_REFACTORING && !(element instanceof PHPFileData))
			return false;
		return true;
	}

	public static boolean isDeleteAvailable(final Object[] objects) {
		if (objects.length != 0) {
			final IResource[] resources = getResources(objects);
			final Object[] elements = getPHPElements(objects);
			if (objects.length != resources.length + elements.length)
				return false;
			for (int index = 0; index < resources.length; index++) {
				if (!isDeleteAvailable(resources[index]))
					return false;
			}
			for (int index = 0; index < elements.length; index++) {
				if (!isDeleteAvailable(elements[index]))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public static boolean arePHPElements(final Object[] elements) {
		if (elements!=null) {
			for (int index = 0; index < elements.length; index++) {
				if (elements[index]instanceof PHPCodeData && !(elements[index] instanceof PHPFileData))
					return true;
			}
		}
		return false;
	}

	public static boolean isInsidePHPFile(Object object) {
		return object instanceof PHPCodeData;
	}

}
