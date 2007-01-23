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
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPIncludeFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData.PHPFunctionParameter;
import org.eclipse.php.internal.ui.functions.PHPFunctionsContentProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class PHPElementLabels {

	/**
	 * Method names contain parameter types.
	 * e.g. <code>foo(int)</code>
	 */
	public final static int M_PARAMETER_TYPES = 1 << 0;

	/**
	 * Method names contain parameter names.
	 * e.g. <code>foo(index)</code>
	 */
	public final static int M_PARAMETER_NAMES = 1 << 1;

	/**
	 * Method names contain return type (appended)
	 * e.g. <code>foo : int</code>
	 */
	public final static int M_APP_RETURNTYPE = 1 << 3;

	/**
	 * Method names contain return type (appended)
	 * e.g. <code>int foo</code>
	 */
	public final static int M_PRE_RETURNTYPE = 1 << 4;

	/**
	 * Method names are fully qualified.
	 * e.g. <code>java.util.Vector.size</code>
	 */
	public final static int M_FULLY_QUALIFIED = 1 << 5;

	/**
	 * Method names are post qualified.
	 * e.g. <code>size - java.util.Vector</code>
	 */
	public final static int M_POST_QUALIFIED = 1 << 6;

	/**
	 * Type names are post qualified.
	 * e.g. <code>{ ... } - java.util.Map</code>
	 */
	public final static int I_POST_QUALIFIED = 1 << 8;

	/**
	 * Field names contain the declared type (appended)
	 * e.g. <code>fHello : int</code>
	 */
	public final static int F_APP_TYPE_SIGNATURE = 1 << 9;

	/**
	 * Field names contain the declared type (prepended)
	 * e.g. <code>int fHello</code>
	 */
	public final static int F_PRE_TYPE_SIGNATURE = 1 << 10;

	/**
	 * Fields names are fully qualified.
	 * e.g. <code>java.lang.System.out</code>
	 */
	public final static int F_FULLY_QUALIFIED = 1 << 11;

	/**
	 * Fields names are post qualified.
	 * e.g. <code>out - java.lang.System</code>
	 */
	public final static int F_POST_QUALIFIED = 1 << 12;

	/**
	 * Type names are fully qualified.
	 * e.g. <code>java.util.Map.MapEntry</code>
	 */
	public final static int T_FULLY_QUALIFIED = 1 << 13;

	/**
	 * Type names are type container qualified.
	 * e.g. <code>Map.MapEntry</code>
	 */
	public final static int T_CONTAINER_QUALIFIED = 1 << 14;

	/**
	 * Type names are post qualified.
	 * e.g. <code>MapEntry - java.util.Map</code>
	 */
	public final static int T_POST_QUALIFIED = 1 << 15;

	/**
	 * Compilation unit names are fully qualified.
	 * e.g. <code>java.util.Vector.java</code>
	 */
	public final static int CU_QUALIFIED = 1 << 16;

	/**
	 * Compilation unit names are post  qualified.
	 * e.g. <code>Vector.java - java.util</code>
	 */
	public final static int CU_POST_QUALIFIED = 1 << 17;

	/**
	 * Folder names are qualified.
	 * e.g. <code>MyProject/src/java.util</code>
	 */
	public final static int P_QUALIFIED = 1 << 22;

	/**
	 * Folder names are post qualified.
	 * e.g. <code>java.util - MyProject/src</code>
	 */
	public final static int P_POST_QUALIFIED = 1 << 23;

	/**
	 * Folder  Roots contain variable name if from a variable.
	 */
	public final static int ROOT_VARIABLE = 1 << 24;

	/**
	 * Folder  Roots contain the project name if not an archive (prepended).
	 * e.g. <code>MyProject/src</code>
	 */
	public final static int ROOT_QUALIFIED = 1 << 25;

	/**
	 * Folder  Roots contain the project name if not an archive (appended).
	 * e.g. <code>src - MyProject</code>
	 */
	public final static int ROOT_POST_QUALIFIED = 1 << 26;

	/**
	 * Add root path to all elements except Folder  Roots and PHP projects.
	 */
	public final static int APPEND_ROOT_PATH = 1 << 27;

	/**
	 * Add root path to all elements except Folder  Roots and PHP projects.
	 */
	public final static int PREPEND_ROOT_PATH = 1 << 28;

	/**
	 * Folder names are compressed.
	 * e.g. <code>o*.e*.search</code>
	 */
	public final static int P_COMPRESSED = 1 << 29;

	public final static int REFERENCED_ROOT_POST_QUALIFIED = 1 << 30;
	public final static long T_TYPE_PARAMETERS = 1L << 21;
	public final static long CF_POST_QUALIFIED = 1L << 28;
	public final static long CF_QUALIFIED = 1L << 27;
	public final static long I_FULLY_QUALIFIED = 1L << 10;
	public final static long D_QUALIFIED = 1L << 24;
	public final static long D_POST_QUALIFIED = 1L << 25;

	/**
	 * Qualify all elements
	 */
	public final static int ALL_FULLY_QUALIFIED = F_FULLY_QUALIFIED | M_FULLY_QUALIFIED | T_FULLY_QUALIFIED | CU_QUALIFIED | P_QUALIFIED | ROOT_QUALIFIED;

	/**
	 * Post qualify all elements
	 */
	public final static int ALL_POST_QUALIFIED = F_POST_QUALIFIED | M_POST_QUALIFIED | I_POST_QUALIFIED | T_POST_QUALIFIED | CU_POST_QUALIFIED | P_POST_QUALIFIED | ROOT_POST_QUALIFIED;

	/**
	 *  Default options (M_PARAMETER_TYPES enabled)
	 */
	public final static int ALL_DEFAULT = M_PARAMETER_TYPES;

	/**
	 *  Default qualify options (All except Root and Folder)
	 */
	public final static int DEFAULT_QUALIFIED = F_FULLY_QUALIFIED | M_FULLY_QUALIFIED | T_FULLY_QUALIFIED | CU_QUALIFIED;

	/**
	 *  Default post qualify options (All except Root and Folder)
	 */
	public final static int DEFAULT_POST_QUALIFIED = F_POST_QUALIFIED | M_POST_QUALIFIED | I_POST_QUALIFIED | T_POST_QUALIFIED | CU_POST_QUALIFIED;

	/**
	 * User-readable string for separating post qualified names (e.g. " - ").
	 */
	public final static String CONCAT_STRING = " - "; //$NON-NLS-1$
	/**
	 * User-readable string for separating list items (e.g. ", ").
	 */
	public final static String COMMA_STRING = ", "; //$NON-NLS-1$
	/**
	 * User-readable string for separating the return type (e.g. " : ").
	 */
	public final static String DECL_STRING = " :"; //$NON-NLS-1$
	/**
	 * User-readable string for the default folder name (e.g. "(default folder)").
	 */
	public final static String DEFAULT_FOLDER = "(source)"; //$NON-NLS-1$

	//	private static String fgPkgNamePattern= ""; //$NON-NLS-1$
	private static String fgPkgNamePrefix;
	private static String fgPkgNamePostfix;
	private static int fgPkgNameChars;
	private static int fgPkgNameLength = -1;

	/**
	 * Returns the label for a PHP element. Flags as defined above.
	 */
	public static String getElementLabel(Object element, long flags) {
		StringBuffer buf = new StringBuffer(60);
		getElementLabel(element, flags, buf);
		return buf.toString();
	}

	/**
	 * Returns the label for a PHP element. Flags as defined above.
	 */
	public static void getElementLabel(Object element, long flags, StringBuffer buf) {
		IContainer root = null;

		if ((element instanceof PHPCodeData))
			root = PHPModelUtil.getPHPFolderRoot((PHPCodeData) element);
		if (root != null && getFlag(flags, PREPEND_ROOT_PATH)) {
			getPHPFolderRootLabel(root, ROOT_QUALIFIED, buf);
			buf.append(CONCAT_STRING);
		}

		if (element instanceof PHPFunctionData) {
			getMethodLabel((PHPFunctionData) element, flags, buf);
		} else if (element instanceof PHPVariableData) {
			getFieldLabel((PHPVariableData) element, flags, buf);
		} else if (element instanceof PHPClassData) {
			getTypeLabel((PHPClassData) element, flags, buf);
		} else if (element instanceof PHPConstantData) {
			buf.append(((PHPConstantData) element).getName());
		} else if (element instanceof PHPClassConstData) {
			buf.append(((PHPClassConstData) element).getName());
		} else if (element instanceof PHPFileData) {
			getCompilationUnitLabel((PHPFileData) element, flags, buf);
		} else if (element instanceof PHPIncludeFileData) {
			getIncludeFileLabel((PHPIncludeFileData) element, flags, buf);
		} else if (element instanceof IFolder) {
			getPHPFolderLabel((IFolder) element, flags, buf);
		} else if (element instanceof PHPProjectModel) {
			IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) element);
			buf.append(project.getName());
		} else if (element instanceof PHPWorkspaceModelManager) {
			buf.append("PHP Model");
		} else if (element instanceof IResource) {
			buf.append(((IResource) element).getName());
		}

		if (root != null && getFlag(flags, APPEND_ROOT_PATH)) {
			buf.append(CONCAT_STRING);
			getPHPFolderRootLabel(root, ROOT_QUALIFIED, buf);
		}
	}

	private static boolean getFlag(long flags, long flag) {
		return (flags & flag) != 0;
	}

	/**
	 * Appends the label for a php folder to a StringBuffer. Considers the P_* flags.
	 */
	public static void getPHPFolderLabel(IContainer folder, long flags, StringBuffer buf) {
		if (getFlag(flags, P_QUALIFIED)) {
			getPHPFolderRootLabel(folder.getParent(), ROOT_QUALIFIED, buf);
			buf.append('/');
		}
		if (folder instanceof IProject) {
			IPath path = folder.getFullPath();
			IPath projectPath = folder.getProject().getFullPath();
			path = path.removeFirstSegments(projectPath.segmentCount());
			if (path.segmentCount() > 0)
				buf.append(path.toString());
			else
				buf.append(DEFAULT_FOLDER);
		} else if (getFlag(flags, P_COMPRESSED) && fgPkgNameLength >= 0) {
			String name = folder.getName();
			int start = 0;
			int dot = name.indexOf('.', start);
			while (dot > 0) {
				if (dot - start > fgPkgNameLength - 1) {
					buf.append(fgPkgNamePrefix);
					if (fgPkgNameChars > 0)
						buf.append(name.substring(start, Math.min(start + fgPkgNameChars, dot)));
					buf.append(fgPkgNamePostfix);
				} else
					buf.append(name.substring(start, dot + 1));
				start = dot + 1;
				dot = name.indexOf('.', start);
			}
			buf.append(name.substring(start));
		} else {
			buf.append(folder.getName());
		}
		if (getFlag(flags, P_POST_QUALIFIED)) {
			buf.append(CONCAT_STRING);
			getPHPFolderRootLabel(folder.getParent(), ROOT_QUALIFIED, buf);
		}
	}

	/**
	 * Appends the label for a php folder root to a StringBuffer. Considers the ROOT_* flags.
	 */
	public static void getPHPFolderRootLabel(IContainer root, long flags, StringBuffer buf) {
		getFolderLabel(root, flags, buf);
	}

	private static void getFolderLabel(IContainer root, long flags, StringBuffer buf) {
		IResource resource = root;
		boolean rootQualified = getFlag(flags, ROOT_QUALIFIED);
		if (rootQualified) {
			buf.append(root.getFullPath().makeRelative().toString());
		} else {
			if (resource != null)
				buf.append(resource.getProjectRelativePath().toString());
			else
				buf.append(root.getName());
			if (getFlag(flags, ROOT_POST_QUALIFIED)) {
				buf.append(CONCAT_STRING);
				buf.append(root.getParent().getName());
			}
		}
	}

	public static void getMethodLabel(PHPFunctionData method, long flags, StringBuffer buf) {
		// qualification
		//			if (getFlag(flags, M_FULLY_QUALIFIED)) {
		//				getTypeLabel(method.getDeclaringType(), T_FULLY_QUALIFIED | (flags & P_COMPRESSED), buf);
		//				buf.append('.');
		//			}

		//        buf.append("function ");

		buf.append(method.getName());

		// parameters
		if (getFlag(flags, M_PARAMETER_TYPES | M_PARAMETER_NAMES)) {
			PHPFunctionParameter[] parameters = method.getParameters();

			buf.append('(');

			//				String[] types= getFlag(flags, M_PARAMETER_TYPES) ? method.getParameterTypes() : null;
			//				String[] names=  getFlag(flags, M_PARAMETER_NAMES)   ? method.getParameterNames() : null;
			int nParams = (parameters != null && getFlag(flags, M_PARAMETER_TYPES)) ? parameters.length : 0;

			for (int i = 0; i < nParams; i++) {
				if (i > 0) {
					buf.append(COMMA_STRING); //$NON-NLS-1$
					buf.append(" ");
				}
				if (getFlag(flags, M_PARAMETER_TYPES)) {
					String t = parameters[i].getClassType();
					if (t == null) {
						t = "";
					} else {
						t += " ";
					}
					buf.append(t);
				}
				if (getFlag(flags, M_PARAMETER_NAMES)) {
					buf.append("$" + parameters[i].getName());
					String defaultValue = parameters[i].getDefaultValue();
					if (defaultValue != null && !defaultValue.equals("")) {
						buf.append(" = " + defaultValue);
					}
				}

			}
			buf.append(')');
		}

		// return type
		if (getFlag(flags, M_PRE_RETURNTYPE)) {
			String rt = method.getReturnType();
			buf.append(DECL_STRING);
			buf.append(rt);
		}

		//			if (getFlag(flags, M_EXCEPTIONS) && method.exists()) {
		//				String[] types= method.getExceptionTypes();
		//				if (types.length > 0) {
		//					buf.append(" throws "); //$NON-NLS-1$
		//					for (int i= 0; i < types.length; i++) {
		//						if (i > 0) {
		//							buf.append(COMMA_STRING);
		//						}
		//						buf.append(Signature.getSimpleName(Signature.toString(types[i])));
		//					}
		//				}
		//			}

		if (getFlag(flags, M_APP_RETURNTYPE)) {
			String rt = method.getReturnType();
			buf.append(DECL_STRING);
			buf.append(rt);
		}

		// post qualification
		if (getFlag(flags, M_POST_QUALIFIED)) {
			buf.append(CONCAT_STRING);
			PHPCodeData container = method.getContainer();
			PHPClassData containerClass = null;
			if (container != null && container instanceof PHPClassData) {
				containerClass = (PHPClassData) container;
			}
			getTypeLabel(containerClass, T_FULLY_QUALIFIED | (flags & P_COMPRESSED), buf);
		}

	}

	public static void getTypeLabel(PHPClassData type, long flags, StringBuffer buf) {
		if (type == null)
			return;
		if (getFlag(flags, T_FULLY_QUALIFIED)) {
			IFile file = (IFile) PHPModelUtil.getResource(type);
			if (file != null) {
				IContainer folder = file.getParent();
				if (!(folder instanceof IProject)) {
					getPHPFolderLabel(folder, (flags & P_COMPRESSED), buf);
					buf.append('.');
				}
			}

		}

		String typeName = type.getName();
		buf.append(typeName);

		// post qualification
		if (getFlag(flags, T_POST_QUALIFIED)) {
			IFile file = (IFile) PHPModelUtil.getResource(type);
			IContainer folder = file.getParent();
			buf.append(CONCAT_STRING);
			getPHPFolderLabel(folder, (flags & P_COMPRESSED), buf);
		}
	}

	public static void getFieldLabel(PHPVariableData field, long flags, StringBuffer buf) {
		// qualification
		//			if (field.getDeclaringType()!=null && getFlag(flags, F_FULLY_QUALIFIED)) {
		//				getTypeLabel(field.getDeclaringType(), T_FULLY_QUALIFIED | (flags & P_COMPRESSED), buf);
		//				buf.append('.');
		//			}

		buf.append(field.getName());

		// post qualification
		if (getFlag(flags, F_POST_QUALIFIED)) {
			buf.append(CONCAT_STRING);
			PHPCodeData container = field.getContainer();
			PHPClassData containerClass = null;
			if (container != null && container instanceof PHPClassData) {
				containerClass = (PHPClassData) container;
			}
			getTypeLabel(containerClass, T_FULLY_QUALIFIED | (flags & P_COMPRESSED), buf);
		}

	}

	public static void getIncludeFileLabel(PHPIncludeFileData data, long flags, StringBuffer buf) {
		if (getFlag(flags, CU_QUALIFIED)) {
			IFile file = (IFile) PHPModelUtil.getResource(data);
			if (file != null) {
				IContainer folder = file.getParent();
				if (!(folder instanceof IProject)) {
					buf.append(folder.getName());
					buf.append('.');
				}

			}
		}
		buf.append(data.getName());

		if (getFlag(flags, CU_POST_QUALIFIED)) {
			IFile file = (IFile) PHPModelUtil.getResource(data);
			IContainer folder = file.getParent();
			buf.append(CONCAT_STRING);
			getPHPFolderLabel(folder, 0, buf);
		}
	}

	public static void getCompilationUnitLabel(PHPFileData cu, long flags, StringBuffer buf) {
		if (getFlag(flags, CU_QUALIFIED)) {
			IFile file = (IFile) PHPModelUtil.getResource(cu);
			if (file != null) {
				IContainer folder = file.getParent();
				if (!(folder instanceof IProject)) {
					buf.append(folder.getName());
					buf.append('.');
				}

			}
		}

		// try to get the resource for the current PHPFileData.
		// In case it exists, it means it is part of the project - so we want just the name.
		// otherwise it is an include path and we need the full path
		IPath path = new Path(cu.getName());
		IFile resourceFile = null;
		try {
			resourceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		} catch (IllegalArgumentException e) {
			// do nothing - this can happen when the file data is from a zip file under the include path.
		}
		if (resourceFile != null && resourceFile.exists()) {
			buf.append(cu.getComparableName());
		} else {
			int indexOfZip = cu.getName().indexOf(".zip");
			if (indexOfZip != -1) {
				// start the file label after the ".zip/" 				
				//				String name = cu.getName().substring(indexOfZip += 5, cu.getName().length());
				//buf.append(name);
				buf.append(cu.getComparableName());
			} else {
				//				buf.append(cu.getName()); 
				buf.append(cu.getComparableName());
			}
		}

		if (getFlag(flags, CU_POST_QUALIFIED)) {
			IFile file = (IFile) PHPModelUtil.getResource(cu);
			IContainer folder = file.getParent();
			buf.append(CONCAT_STRING);
			getPHPFolderLabel(folder, 0, buf);
		}
	}

	/**
	 * Returns the tooltip text for a PHP element.
	 */
	public static String getElementTooltipText(Object element) {
		StringBuffer buf = new StringBuffer(60);

		if (element instanceof PHPFunctionData) {
			getMethodTooltipText((PHPFunctionData) element, buf);
		} else if (element instanceof PHPClassData) {
			getClassTooltipText((PHPClassData) element, buf);
		} else if (element instanceof PHPConstantData) {
			buf.append(((PHPConstantData) element).getName());
		}
		//		else if (element instanceof PHPVariableData) {
		//			getFieldLabel((PHPVariableData) element, flags, buf);
		//		} else if (element instanceof PHPFileData) {
		//			getCompilationUnitLabel((PHPFileData) element, flags, buf);
		//		} else if (element instanceof IFolder) {
		//			getPHPFolderLabel((IFolder) element, flags, buf);
		//		} else if (element instanceof PHPBrowserModel) {
		//			IProject project = PHPModelManager.getInstance().getProjectForModel((PHPBrowserModel) element);
		//			buf.append(project.getName());
		//		} else if (element instanceof PHPModelManager) {
		//			buf.append("PHP Model");
		//		} else if (element instanceof IResource) {
		//			buf.append(((IResource) element).getName());
		//		}

		return buf.toString();
	}

	public static void getClassTooltipText(PHPClassData classData, StringBuffer buf) {
		if (classData == null) {
			return;
		}
		buf.append(classData.getName());

		if (classData.getDocBlock() != null) {
			buf.append("\n" + classData.getDocBlock().getShortDescription());
		}
	}

	public static void getMethodTooltipText(PHPFunctionData method, StringBuffer buf) {
		buf.append(method.getName());
		// parameters
		buf.append('(');
		PHPFunctionParameter[] parameters = method.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			if (i > 0) {
				buf.append(COMMA_STRING); //$NON-NLS-1$
			}
			buf.append(parameters[i].getClassType());
			buf.append(" $" + parameters[i].getName());
			String defaultValue = parameters[i].getDefaultValue();
			if (!defaultValue.equals("")) {
				buf.append(" = " + defaultValue);
			}

		}
		buf.append(')');

		// return type
		buf.append(DECL_STRING);
		buf.append(method.getReturnType());
		if (method.getDocBlock() != null) {
			buf.append("\n" + method.getDocBlock().getShortDescription());
		}
	}

	public static String getTextLabel(Object obj, long flags) {
		if (obj instanceof PHPCodeData || obj instanceof PHPProjectModel) {
			return getElementLabel(obj, flags);
		} else if (obj instanceof IAdaptable) {
			IWorkbenchAdapter wbadapter = (IWorkbenchAdapter) ((IAdaptable) obj).getAdapter(IWorkbenchAdapter.class);
			if (wbadapter != null) {
				return wbadapter.getLabel(obj);
			}
		} else if (obj != null && obj.equals(PHPFunctionsContentProvider.CONSTANTS_NODE_NAME)) {
			return obj.toString();
		}
		return ""; //$NON-NLS-1$
	}

	public static String getTooltipTextLabel(Object obj) {
		if (obj instanceof PHPCodeData || obj instanceof PHPProjectModel) {
			return getElementTooltipText(obj);
		} else if (obj instanceof IAdaptable) {
			IWorkbenchAdapter wbadapter = (IWorkbenchAdapter) ((IAdaptable) obj).getAdapter(IWorkbenchAdapter.class);
			if (wbadapter != null) {
				return wbadapter.getLabel(obj);
			}
		} else if (obj != null && obj.equals(PHPFunctionsContentProvider.CONSTANTS_NODE_NAME)) {
			return obj.toString();
		}
		return "";
	}

}
