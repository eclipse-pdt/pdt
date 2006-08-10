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
package org.eclipse.php.core.phpModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.core.phpModel.parser.IPhpModel;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPDocTagData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.PHPIncludeFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPKeywordData;
import org.eclipse.php.core.phpModel.phpElementData.PHPProjectModelVisitor;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData.PHPInterfaceNameData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData.PHPSuperClassNameData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData.PHPFunctionParameter;

public class PHPModelUtil {

	static class FindElementVisitor implements PHPProjectModelVisitor {
		PHPCodeData foundElement;
		int offset;

		FindElementVisitor(final int offset) {
			this.offset = offset;
		}

		boolean checkInside(final PHPCodeData codeData) {
			final UserData userData = codeData.getUserData();
			final boolean found = userData.getStartPosition() <= offset && userData.getEndPosition() >= offset;
			if (found)
				foundElement = codeData;
			return found;
		}

		public void visit(final PHPClassConstData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPClassData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPClassVarData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPCodeData phpCodeData) {
			checkInside(phpCodeData);
		}

		public void visit(final PHPConstantData codeData) {
			checkInside(codeData);

		}

		public void visit(final PHPDocTagData codeData) {

		}

		public void visit(final PHPFileData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPFunctionData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPFunctionParameter codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPIncludeFileData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPInterfaceNameData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPKeywordData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPSuperClassNameData codeData) {
			checkInside(codeData);
		}

		public void visit(final PHPVariableData codeData) {
			checkInside(codeData);
		}

	}

	public static class PHPContainerStringConverter {
		public static Object toContainer(final String sPath) {
			final IPath path = Path.fromPortableString(sPath);
			if (path == null)
				return null;
			IFile file = null;
			try {
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			} catch (final IllegalArgumentException e) {
			}
			if (file != null) {
				final PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(file, false);
				if (fileData != null)
					return fileData;
			}
			IFolder folder = null;
			try {
				folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
			} catch (final IllegalArgumentException e) {
			}
			if (folder != null)
				return folder;
			IProject project = null;
			try {
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(path.segments()[0]);
			} catch (final IllegalArgumentException e) {
			}
			return project;
		}

		public static String toString(Object phpElement) {
			if (phpElement instanceof PHPCodeData)
				while (phpElement != null && !(phpElement instanceof PHPFileData))
					phpElement = ((PHPCodeData) phpElement).getContainer();
			if (phpElement == null)
				return "";
			IResource res;
			IPath path;
			if ((res = getResource(phpElement)) != null && (path = res.getFullPath()) != null)
				return path.toPortableString();
			return "";
		}
	}

	public static PHPCodeData getElementAt(final PHPCodeData elementData, final int offset) {
		throw new RuntimeException("test me");
		//		PHPCodeData element=null;
		//		
		//		UserData userData= elementData.getUserData();
		//		if (offset>=userData.getStartPosition() && offset<=userData.getEndPosition())
		//		{
		//			element = elementData;
		//			FindElementVisitor visitor = new FindElementVisitor(offset);
		//			elementData.accept( visitor);
		//			element = visitor.foundElement;
		//		}
		//					
		//		
		//		return element;
	}

	public static Object getExternalResource(final Object element, final IProject project) {
		if (!(element instanceof PHPCodeData))
			return null;
		String fileName = ((PHPCodeData) element).getUserData().getFileName();
		final File file = new File(fileName);
		if (file != null && file.exists())
			return file;
		PHPFileData fileData = null;
		if (element instanceof PHPFileData)
			fileData = (PHPFileData) element;
		else
			fileData = getPHPFileContainer((PHPCodeData) element);
		if (fileData == null)
			return null;
		PHPProjectModel projectModel = null;
		if (project == null) {
			fileName = fileData.getName();
			final PHPProjectModel[] models = PHPWorkspaceModelManager.getInstance().listModels();
			for (int i = 0; i < models.length; i++)
				if (models[i].getFileData(fileName) == fileData) {
					projectModel = models[i];
					break;
				}

		} else
			projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);

		if (projectModel == null)
			return null;
		return projectModel.getExternalResource(fileData);
	}

	public static PHPClassData[] getMatchingClasses(final IPhpModel model, final String className) {
		final CodeData[] classes = model.getClasses(className);
		PHPClassData aClass;
		final ArrayList matchingClasses = new ArrayList(1);
		for (int i = 0; i < classes.length; ++i) {
			aClass = (PHPClassData) classes[i];
			if (className.equalsIgnoreCase(aClass.getName()))
				matchingClasses.add(aClass);
		}
		return (PHPClassData[]) matchingClasses.toArray(new PHPClassData[] {});
	}

	public static PHPCodeData[] getMatchingElements(final IPhpModel model, final String elementName) {
		final PHPClassData[] matchingClasses = getMatchingClasses(model, elementName);
		final PHPFunctionData[] matchingFunctions = getMatchingFunctions(model, elementName);
		final int nMatchingClasses = matchingClasses.length;
		final int nMatchingFunctions = matchingFunctions.length;
		if (nMatchingClasses + nMatchingFunctions == 0)
			return new PHPCodeData[] {};
		final ArrayList matchingElements = new ArrayList(nMatchingClasses + nMatchingFunctions);
		if (nMatchingClasses > 0)
			matchingElements.addAll(Arrays.asList(matchingClasses));
		if (nMatchingFunctions > 0)
			matchingElements.addAll(Arrays.asList(matchingFunctions));
		return (PHPCodeData[]) matchingElements.toArray(new PHPCodeData[] {});
	}

	public static PHPFunctionData[] getMatchingFunctions(final IPhpModel model, final String functionName) {
		final CodeData[] functions = model.getFunctions(functionName);
		PHPFunctionData aFunction;
		final ArrayList matchingFunctions = new ArrayList(1);
		for (int i = 0; i < functions.length; ++i) {
			aFunction = (PHPFunctionData) functions[i];
			if (functionName.equalsIgnoreCase(aFunction.getName()))
				matchingFunctions.add(aFunction);
		}
		return (PHPFunctionData[]) matchingFunctions.toArray(new PHPFunctionData[] {});
	}

	public static int getModifier(final PHPCodeData member) {
		if (member instanceof PHPClassData) {
			final PHPClassData cls = (PHPClassData) member;
			return cls.getModifiers();
		}
		if (member instanceof PHPFunctionData) {
			final PHPFunctionData func = (PHPFunctionData) member;
			return func.getModifiers();
		}
		if (member instanceof PHPClassVarData) {
			final PHPClassVarData var = (PHPClassVarData) member;
			return var.getModifiers();
		}

		return 0;
	}

	public static Object getParent(final Object element) {

		// try to map resources to the containing source folder
		if (element instanceof IResource) {
			if (element instanceof IProject)
				return PHPWorkspaceModelManager.getInstance();
			final IResource parent = ((IResource) element).getParent();
			if (parent instanceof IProject)
				return PHPWorkspaceModelManager.getInstance().getModelForProject((IProject) parent);

			return parent;
		} else if (element instanceof PHPCodeData) {
			final PHPCodeData parent = ((PHPCodeData) element).getContainer();
			// for source folders that are contained in a project source folder
			// we have to skip the source folder root as the parent.

			if (parent == null && element instanceof PHPFileData) {
				final IResource resource = getResource(element);
				final IResource parentResource = resource.getParent();
				//				if (parentResource instanceof IProject)
				//					return PHPModelManager.getInstance().getModelForProject((IProject)parentResource).PHPBrowserModel();
				return parentResource;
			}
			return parent;
		} else if (element instanceof PHPProjectModel)
			return PHPWorkspaceModelManager.getInstance();

		return null;
	}

	public static PHPFileData getPHPFile(final IFile file) {
		final PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
		if (projectModel != null) {
			final PHPFileData fileData = projectModel.getFileData(file.getFullPath().toString());
			if (fileData != null)
				return fileData;
		}
		return null;
	}

	public static PHPFileData getPHPFileContainer(final PHPCodeData element) {
		PHPCodeData parent = element.getContainer();
		while (parent != null && !(parent instanceof PHPFileData))
			parent = parent.getContainer();
		if (parent == null) {
			final UserData userData = element.getUserData();
			if (userData != null) {
				final IPath path = new Path(userData.getFileName());
				if (ResourcesPlugin.getWorkspace().getRoot().findMember(path) != null)
					parent = PHPWorkspaceModelManager.getInstance().getModelForFile(path.toString());
			}
		}
		return (PHPFileData) parent;
	}

	public static IContainer getPHPFolderRoot(final PHPCodeData element) {
		final IResource resource = getResource(element);
		return resource != null ? resource.getProject() : null;

	}

	public static IResource getResource(final Object element) {
		if (element instanceof PHPCodeData) {
			PHPCodeData parent = null;
			if (!(element instanceof PHPFileData))
				parent = getPHPFileContainer((PHPCodeData) element);
			final PHPFileData fileData = (PHPFileData) parent;
			String filename = null;
			final PHPCodeData codeData = (PHPCodeData) element;
			if (fileData != null)
				filename = fileData.getName();
			else if (codeData.isUserCode()) {
				codeData.getContainer();
				final UserData userData = codeData.getUserData();
				filename = userData.getFileName();
			} else
				return null; //no resource
			final Path path = new Path(filename);
			if (path.segmentCount() < 2) // path doesnt include project name, return null
				return null;
			final IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			return resource;
		} else if (element instanceof PHPProjectModel) {
			final PHPProjectModel projectModel = (PHPProjectModel) element;
			final IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(projectModel);
			return project;
		} else if (element instanceof IResource)
			return (IResource) element;
		return null;
	}

	public static PHPClassData getSuperClass(final PHPClassData classData) {
		final PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
		if (superClassNameData == null)
			return null;
		final String superClassName = superClassNameData.getName();
		if (superClassName == null)
			return null;
		final PHPFileData fileData = getPHPFileContainer(classData);
		String fileName = null;
		if (fileData != null)
			fileName = fileData.getName();
		if (fileName.equals(""))
			fileName = null;
		PHPClassData superClassData = null;
		IPhpModel model = null;
		IProject project = null;
		// first try to find in the same user model:
		final IResource resource = getResource(classData);
		if (resource != null) {
			project = resource.getProject();
			model = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
			if (model != null) {
				superClassData = model.getClass(resource.getName(), superClassName);
				if (superClassData != null)
					return superClassData;
			}
		}
		// then look up in other models by file:
		final PHPProjectModel[] models = PHPWorkspaceModelManager.getInstance().listModels();
		for (int i = 0; i < models.length; i++)
			if (models[i].getFileData(fileName) == fileData && (superClassData = models[i].getClass(fileName, superClassName)) != null)
				return superClassData;
		// an then just all the models:
		for (int i = 0; i < models.length; i++)
			if ((superClassData = models[i].getClass(fileName, superClassName)) != null)
				return superClassData;
		return null;
	}

	public static boolean hasChildren(final PHPCodeData element) {
		if (element instanceof PHPFunctionData)
			return false;
		else if (element instanceof PHPClassData) {
			final PHPClassData classData = (PHPClassData) element;
			return classData.getFunctions().length > 0 || classData.getVars().length > 0 || classData.getConsts().length > 0;
		} else if (element instanceof PHPVariableData || element instanceof PHPKeywordData || element instanceof PHPConstantData || element instanceof PHPClassConstData)
			return false;
		return true;

	}

	public static boolean hasPhpExtention(final IFile file) {
		final IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		final String[] validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
		final String fileName = file.getName();
		final int index = fileName.lastIndexOf('.');
		if (index == -1)
			return false;
		final String ext = fileName.substring(index + 1);
		for (int i = 0; i < validExtensions.length; i++)
			if (ext.equals(validExtensions[i]))
				return true;
		return false;
	}

	public static boolean hasSuperClass(final PHPClassData classData, final PHPClassData superClassData) {
		final PHPSuperClassNameData currentSuperClassNameData = classData.getSuperClassData();
		if (currentSuperClassNameData == null)
			return false;
		final String currentSuperClassName = currentSuperClassNameData.getName();
		if (currentSuperClassName == null)
			return false;
		PHPClassData currentSuperClassData = classData;
		while ((currentSuperClassData = getSuperClass(currentSuperClassData)) != null)
			if (currentSuperClassData == superClassData)
				return true;
		return false;
	}

	/**
	 * recursively checks if the given class extends the superclass
	 * Currently only can detect for files that are inside the project
	 * 
	 * @param classData
	 * @param superClassName
	 * @return
	 */
	public static boolean hasSuperClass(final PHPClassData classData, final String superClassName) {
		final PHPSuperClassNameData currentSuperClassNameData = classData.getSuperClassData();
		if (currentSuperClassNameData == null)
			return false;
		String currentSuperClassName = currentSuperClassNameData.getName();
		if (currentSuperClassName == null)
			return false;
		if (currentSuperClassNameData.getName().compareToIgnoreCase(superClassName) == 0)
			return true;
		PHPClassData currentSuperClassData = classData;
		while ((currentSuperClassData = getSuperClass(currentSuperClassData)) != null) {
			if ((currentSuperClassName = currentSuperClassData.getName()) == null)
				return false;
			if (currentSuperClassName.compareToIgnoreCase(superClassName) == 0)
				return true;
		}
		return false;
	}

	public static boolean isExternal(final Object target) {
		return false;
	}

	public static boolean isPhpFile(final IFile file) {
		IContentDescription contentDescription;
		try {
			contentDescription = file.getContentDescription();
		} catch (final CoreException e) {
			PHPCorePlugin.log(e);
			return false;
		}
		if (contentDescription == null) {
			if (hasPhpExtention(file))
				PHPCorePlugin.logErrorMessage("content description null!");
			return false;
		}

		if (!ContentTypeIdForPHP.ContentTypeID_PHP.equals(contentDescription.getContentType().getId()))
			return false;

		return true;
	}

	public static boolean isReadOnly(final Object target) {
		return false;
	}
}
