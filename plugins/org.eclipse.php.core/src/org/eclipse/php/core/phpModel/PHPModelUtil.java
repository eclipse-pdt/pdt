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
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
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

	public static boolean hasChildren(PHPCodeData element) {
		if (element instanceof PHPFunctionData) {
			return false;
		} else if (element instanceof PHPClassData) {
			PHPClassData classData = (PHPClassData) element;
			return (classData.getFunctions().length > 0 || classData.getVars().length > 0 || classData.getConsts().length > 0);
		} else if (element instanceof PHPVariableData || element instanceof PHPKeywordData || element instanceof PHPConstantData || element instanceof PHPClassConstData) {
			return false;
		}
		return true;

	}

	public static PHPFileData getPHPFileContainer(PHPCodeData element) {
		PHPCodeData parent = element.getContainer();
		while (parent != null && !(parent instanceof PHPFileData))
			parent = parent.getContainer();
		return (PHPFileData) parent;
	}

	public static IResource getResource(Object element) {
		if (element instanceof PHPCodeData) {
			PHPCodeData parent = null;
			if (!(element instanceof PHPFileData))
				parent = getPHPFileContainer((PHPCodeData) element);
			PHPFileData fileData = (PHPFileData) parent;
			String filename = null;
			PHPCodeData codeData = (PHPCodeData) element;
			if (fileData != null) {
				filename = fileData.getName();
			} else if (codeData.isUserCode()) {
				codeData.getContainer();
				UserData userData = codeData.getUserData();
				filename = userData.getFileName();
			} else {
				return null; //no resource
			}
			Path path = new Path(filename);
			if (path.segmentCount() < 2) // path doesnt include project name, return null
				return null;
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			return resource;
		} else if (element instanceof PHPProjectModel) {
			PHPProjectModel projectModel = (PHPProjectModel) element;
			IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel(projectModel);
			return project;
		} else if (element instanceof IResource) {
			return (IResource) element;
		}
		return null;
	}

	public static Object getExternalResource(Object element, IProject project) {
		if (!(element instanceof PHPCodeData)) {
			return null;
		}
		String fileName = ((PHPCodeData) element).getUserData().getFileName();
		File file = new File(fileName);
		if (file != null && file.exists()) {
			return file;
		}
		PHPFileData fileData = null;
		if (element instanceof PHPFileData) {
			fileData = (PHPFileData) element;
		} else {
			fileData = getPHPFileContainer((PHPCodeData) element);
		}
		if (fileData == null) {
			return null;
		}
		PHPProjectModel projectModel = null;
		if (project == null) {
			fileName = fileData.getName();
			PHPProjectModel[] models = PHPWorkspaceModelManager.getInstance().listModels();
			for (int i = 0; i < models.length; i++) {
				if (models[i].getFileData(fileName) == fileData) {
					projectModel = models[i];
					break;
				}
			}

		} else {
			projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		}

		if (projectModel == null) {
			return null;
		}
		return projectModel.getExternalResource(fileData);
	}

	public static IContainer getPHPFolderRoot(PHPCodeData element) {
		IResource resource = getResource(element);
		return (resource != null) ? resource.getProject() : null;

	}

	public static class PHPContainerStringConverter {
		public static String toString(Object phpElement) {
			if (phpElement instanceof PHPCodeData) {
				while (phpElement != null && !(phpElement instanceof PHPFileData)) {
					phpElement = ((PHPCodeData) phpElement).getContainer();
				}
			}
			if (phpElement == null) {
				return "";
			}
			IResource res;
			IPath path;
			if ((res = getResource(phpElement)) != null && (path = res.getFullPath()) != null) {
				return path.toPortableString();
			}
			return "";
		}

		public static Object toContainer(String sPath) {
			IPath path = Path.fromPortableString(sPath);
			if (path == null) {
				return null;
			}
			IFile file = null;
			try {
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			} catch (IllegalArgumentException e) {
			}
			if (file != null) {
				PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(file, false);
				if (fileData != null) {
					return fileData;
				}
			}
			IFolder folder = null;
			try {
				folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
			} catch (IllegalArgumentException e) {
			}
			if (folder != null) {
				return folder;
			}
			IProject project = null;
			try {
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(path.segments()[0]);
			} catch (IllegalArgumentException e) {
			}
			return project;
		}
	}

	public static boolean isReadOnly(Object target) {
		return false;
	}

	public static boolean isExternal(Object target) {
		return false;
	}

	static class FindElementVisitor implements PHPProjectModelVisitor {
		int offset;
		PHPCodeData foundElement;

		FindElementVisitor(int offset) {
			this.offset = offset;
		}

		boolean checkInside(PHPCodeData codeData) {
			UserData userData = codeData.getUserData();
			boolean found = userData.getStartPosition() <= offset && userData.getEndPosition() >= offset;
			if (found)
				foundElement = codeData;
			return found;
		}

		public void visit(PHPCodeData phpCodeData) {
			checkInside(phpCodeData);
		}

		public void visit(PHPFunctionData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPClassData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPSuperClassNameData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPInterfaceNameData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPClassVarData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPClassConstData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPIncludeFileData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPKeywordData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPFunctionParameter codeData) {
			checkInside(codeData);
		}

		public void visit(PHPVariableData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPFileData codeData) {
			checkInside(codeData);
		}

		public void visit(PHPConstantData codeData) {
			checkInside(codeData);

		}

		public void visit(PHPDocTagData codeData) {

		}

	}

	public static Object getParent(Object element) {

		// try to map resources to the containing source folder
		if (element instanceof IResource) {
			if (element instanceof IProject)
				return PHPWorkspaceModelManager.getInstance();
			IResource parent = ((IResource) element).getParent();
			if (parent instanceof IProject)
				return PHPWorkspaceModelManager.getInstance().getModelForProject((IProject) parent);

			return parent;
		} else if (element instanceof PHPCodeData) {
			PHPCodeData parent = ((PHPCodeData) element).getContainer();
			// for source folders that are contained in a project source folder
			// we have to skip the source folder root as the parent.

			if (parent == null && element instanceof PHPFileData) {
				IResource resource = getResource(element);
				IResource parentResource = resource.getParent();
				//				if (parentResource instanceof IProject)
				//					return PHPModelManager.getInstance().getModelForProject((IProject)parentResource).PHPBrowserModel();
				return parentResource;
			}
			return parent;
		} else if (element instanceof PHPProjectModel)
			return PHPWorkspaceModelManager.getInstance();

		return null;
	}

	public static PHPCodeData getElementAt(PHPCodeData elementData, int offset) {
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

	public static int getModifier(PHPCodeData member) {
		if (member instanceof PHPClassData) {
			PHPClassData cls = (PHPClassData) member;
			return cls.getModifiers();
		}
		if (member instanceof PHPFunctionData) {
			PHPFunctionData func = (PHPFunctionData) member;
			return func.getModifiers();
		}
		if (member instanceof PHPClassVarData) {
			PHPClassVarData var = (PHPClassVarData) member;
			return var.getModifiers();
		}

		return 0;
	}

	public static PHPFileData getPHPFile(IFile file) {
		PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject());
		if (projectModel != null) {
			PHPFileData fileData = projectModel.getFileData(file.getFullPath().toString());
			if (fileData != null)
				return fileData;
		}
		return null;
	}

	/**
	 * recursively checks if the given class extends the superclass
	 * Currently only can detect for files that are inside the project
	 * 
	 * @param classData
	 * @param superClassName
	 * @return
	 */
	public static boolean hasSuperClass(PHPClassData classData, String superClassName) {
		PHPSuperClassNameData currentSuperClassNameData = classData.getSuperClassData();
		if (currentSuperClassNameData == null) {
			return false;
		}
		String currentSuperClassName = currentSuperClassNameData.getName();
		if (currentSuperClassName == null) {
			return false;
		}
		String fileName;
		UserData userData = classData.getUserData();
		if (userData == null) {
			fileName = null;
		} else {
			fileName = userData.getFileName();
		}
		if (currentSuperClassNameData.getName().compareToIgnoreCase(superClassName) == 0) {
			return true;
		}
		PHPClassData currentSuperClassData;
		IResource classDataResource = getResource(classData);
		if (((classDataResource = getResource(classData)) == null)) { // the original file is not in project, seemingly
			return false;
		}
		PHPProjectModel model = PHPWorkspaceModelManager.getInstance().getModelForProject(classDataResource.getProject());
		if(model == null) {
			return false; //TODO figure out why.
		}
		while ((currentSuperClassData = model.getClass(fileName, currentSuperClassNameData.getName())) != null) {
			if ((currentSuperClassName = currentSuperClassData.getName()) == null) {
				return false;
			}
			if (currentSuperClassName.compareToIgnoreCase(superClassName) == 0) {
				return true;
			}
			// preparing to the next tick in the loop:
			if ((currentSuperClassNameData = currentSuperClassData.getSuperClassData()) == null) {
				return false;
			}
			userData = currentSuperClassData.getUserData();
			if (userData == null) {
				fileName = null;
			} else {
				fileName = userData.getFileName();
			}
		}
		return false;
	}
	
	public static  boolean isPhpFile(IFile file) {
		IContentDescription contentDescription;
		try {
			contentDescription = file.getContentDescription();
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
			return false;
		}
		if (contentDescription == null) {
			if (hasPhpExtention(file)) {
				PHPCorePlugin.logErrorMessage("content description null!");
			}
			return false;
		}

		if (!ContentTypeIdForPHP.ContentTypeID_PHP.equals(contentDescription.getContentType().getId())) {
			return false;
		}

		return true;
	}
	
	public static boolean hasPhpExtention(IFile file) {
		IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		String[] validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return false;
		}
		String ext = fileName.substring(index + 1);
		for (int i = 0; i < validExtensions.length; i++) {
			if (ext.equals(validExtensions[i])) {
				return true;
			}
		}
		return false;
	}
}
