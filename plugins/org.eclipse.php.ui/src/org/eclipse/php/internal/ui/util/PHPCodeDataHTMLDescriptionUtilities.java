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

import java.io.File;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.phpModel.parser.ModelSupport;
import org.eclipse.php.internal.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public class PHPCodeDataHTMLDescriptionUtilities {

	private static Pattern dolar_pattern = Pattern.compile("\\$"); //$NON-NLS-1$
	private static Pattern unknown_type_pattern = Pattern.compile("unknown_type\\ "); //$NON-NLS-1$
	private static StringBuffer descriptionText = new StringBuffer();
	private static StringBuffer helpBuffer = new StringBuffer();

	public static final String createFunctionDescriptionText(PHPCodeData phpCodeData, PHPProjectModel projectModel) {
		descriptionText.delete(0, descriptionText.length());
		String desc = phpCodeData.getDescription();
		PHPCodeData container = phpCodeData.getContainer();
		String fileName = phpCodeData.isUserCode() ? phpCodeData.getUserData().getFileName() : null;
		boolean isClassFunction = container != null && container instanceof PHPClassData;

		// append the file name
		if (fileName != null) {
			File file = new File(fileName);
			Path path = new Path(fileName);
			if (path.getDevice() != null && !file.exists()) {
				fileName = new Path(fileName).lastSegment();
				descriptionText.append(getLocationTableRow(fileName));
			} else {
				descriptionText.append(getLocationTableRow(fileName));
			}

		}

		// append the class name if it exists
		if (isClassFunction) {
			descriptionText.append(getClassNameTableRow(container.getName(), projectModel));
		}
		// append description if it exists
		if (desc.length() > 0) {
			descriptionText.append(getDescriptionTableRow(desc));
		}
		// append tags information
		if (phpCodeData.getDocBlock() != null) {
			// append parameters info
			Iterator it = phpCodeData.getDocBlock().getTags(PHPDocTag.PARAM);
			if (it.hasNext()) {
				descriptionText.append(getParamTagTableRows(it));
			}
			// append return type
			it = phpCodeData.getDocBlock().getTags(PHPDocTag.RETURN);
			if (it.hasNext()) {
				descriptionText.append(getReturnTagTableRows(it));
			}
			// append throw info
			it = phpCodeData.getDocBlock().getTags(PHPDocTag.THROWS);
			if (it.hasNext()) {
				descriptionText.append(getThrowTagTableRows(it));
			}
			// append see also information
			it = phpCodeData.getDocBlock().getTags(PHPDocTag.SEE);
			if (it.hasNext()) {
				descriptionText.append(getSeeAlsoTagTableRows(it, projectModel, phpCodeData));
			}
			// append deprecated information
			it = phpCodeData.getDocBlock().getTags(PHPDocTag.DEPRECATED);
			if (it.hasNext()) {
				descriptionText.append(getDeprecatedTagTableRow(it));
			}
		}
		// append closing tags
		return descriptionText.toString();
	}

	public static String createClassDescriptionText(PHPClassData classData, PHPProjectModel projectModel) {
		descriptionText.delete(0, descriptionText.length());
		PHPFileData fileData = (PHPFileData) classData.getContainer();
		PHPClassData.PHPSuperClassNameData superClass = classData.getSuperClassData();
		PHPClassData.PHPInterfaceNameData[] implementData = classData.getInterfacesNamesData();

		String fileName = null;
		String superClassName = null;
		String[] implement = null;
		String desc = classData.getDescription();

		fileName = classData.isUserCode() ? classData.getUserData().getFileName() : null;

		if (fileName != null) {
			File file = new File(fileName);
			Path path = new Path(fileName);
			//Do not show the full path of an Untitled PHP Document's file, replace it with the last segment
			if (path.getDevice() != null && !file.exists()) {
				fileName = new Path(fileName).lastSegment();
			}
		}
		if (superClass != null) {
			superClassName = superClass.getName();
		}
		if (implementData != null && implementData.length > 0) {
			implement = new String[implementData.length];
			for (int i = 0; i < implementData.length; i++) {
				PHPClassData.PHPInterfaceNameData phpInterfaceNameData = implementData[i];
				implement[i] = phpInterfaceNameData.getName();
			}
		}
		if (fileName != null) {
			descriptionText.append(getLocationTableRow(fileName));
		}
		if (superClassName != null) {
			descriptionText.append("<br><dt>Extends</dt>"); //$NON-NLS-1$
			descriptionText.append("<dd>"); //$NON-NLS-1$
			descriptionText.append(superClassName);
			descriptionText.append("</dd>"); //$NON-NLS-1$
		}
		if (implement != null) {
			descriptionText.append("<br><dt>Implements</dt>"); //$NON-NLS-1$
			for (String interfaceName : implement) {
				descriptionText.append("<dd>"); //$NON-NLS-1$
				descriptionText.append(interfaceName);
				descriptionText.append("</dd>"); //$NON-NLS-1$
			}
		}
		if (desc.length() > 0) {
			descriptionText.append(getDescriptionTableRow(desc));
		}
		return descriptionText.toString();
	}

	public static String createVariableDescriptionText(PHPVariableData variableData) {
		descriptionText.delete(0, descriptionText.length());
		String desc = variableData.getDescription();

		if (desc.length() > 0) {
			descriptionText.append(getDescriptionTableRow(desc));
		}
		return descriptionText.toString();
	}

	public static String createClassVariableDescriptionText(PHPClassVarData variableData, PHPProjectModel projectModel) {
		descriptionText.delete(0, descriptionText.length());
		String desc = variableData.getDescription();
		PHPCodeData containerClass = variableData.getContainer();
		String className = containerClass != null ? containerClass.getName() : null;
		String fileName = containerClass != null ? variableData.getUserData().getFileName() : null;

		if (fileName != null) {
			File file = new File(fileName);
			Path path = new Path(fileName);
			//Do not show the full path of an Untitled PHP Document's file, replace it with the last segment
			if (path.getDevice() != null && !file.exists()) {
				fileName = new Path(fileName).lastSegment();
				descriptionText.append(getLocationTableRow(fileName));
			} else {
				descriptionText.append(getLocationTableRow(fileName));
			}

		}
		if (className != null) {
			descriptionText.append(getClassNameTableRow(className, projectModel));
		}
		if (desc.length() > 0) {
			descriptionText.append(getDescriptionTableRow(desc));
		}
		return descriptionText.toString();
	}

	public static String createPHPConstantDescriptionText(PHPConstantData codeData) {
		descriptionText.delete(0, descriptionText.length());
		String desc = codeData.getDescription();
		String fileName = codeData.getUserData() != null ? codeData.getUserData().getFileName() : null;
		String value = codeData.getValue();

		if (fileName != null) {
			descriptionText.append(getLocationTableRow(fileName));
		}
		if (value != null && value.length() > 0) {
			descriptionText.append("<br><dt>Value</dt>"); //$NON-NLS-1$
			descriptionText.append("<dd>"); //$NON-NLS-1$
			descriptionText.append(value);
			descriptionText.append("</dd>"); //$NON-NLS-1$
		}
		if (desc.length() > 0) {
			descriptionText.append(getDescriptionTableRow(desc));
		}
		return descriptionText.toString();
	}

	public static String createPHPClassConstDescriptionText(PHPClassConstData codeData, PHPProjectModel projectModel) {
		descriptionText.delete(0, descriptionText.length());
		String desc = codeData.getDescription();
		String className = codeData.getContainer().getName();
		String fileName = codeData.getUserData() != null ? codeData.getUserData().getFileName() : null;
		String value = codeData.getValue();

		if (fileName != null) {
			File file = new File(fileName);
			Path path = new Path(fileName);
			//Do not show the full path of an Untitled PHP Document's file, replace it with the last segment
			if (path.getDevice() != null && !file.exists()) {
				fileName = new Path(fileName).lastSegment();
				descriptionText.append(getLocationTableRow(fileName));
			} else {
				descriptionText.append(getLocationTableRow(fileName));
			}

		}

		if (className != null) {
			descriptionText.append(getClassNameTableRow(className, projectModel));
		}

		if (value != null && value.length() > 0) {
			descriptionText.append("<br><dt>Value</dt>"); //$NON-NLS-1$
			descriptionText.append("<dd>"); //$NON-NLS-1$
			descriptionText.append(value);
			descriptionText.append("</dd>"); //$NON-NLS-1$
		}

		if (desc.length() > 0) {
			descriptionText.append(getDescriptionTableRow(desc));
		}
		return descriptionText.toString();
	}

	private static String getLocationTableRow(String fileName) {
		helpBuffer.delete(0, helpBuffer.length());
		helpBuffer.append("<br><dt>Location</dt> "); //$NON-NLS-1$
		helpBuffer.append("<dd>"); //$NON-NLS-1$
		helpBuffer.append(fileName);
		helpBuffer.append("</dd>"); //$NON-NLS-1$
		return helpBuffer.toString();
	}

	private static String getSeeAlsoTagTableRows(Iterator it, PHPProjectModel projectModel, PHPCodeData phpCodeData) {
		helpBuffer.delete(0, helpBuffer.length());
		helpBuffer.append("<br><dt>See Also</dt>"); //$NON-NLS-1$
		helpBuffer.append("<dd>"); //$NON-NLS-1$

		UserData userData = phpCodeData.getUserData();
		String fileName = userData != null ? userData.getFileName() : null;

		PHPDocTag see = (PHPDocTag) it.next();
		String arg = see.getValue();
		String[] args = arg.split(","); //$NON-NLS-1$

		for (String ref : args) {
			// find a file:
			if (projectModel.getFileData(ref) != null) {
				helpBuffer.append(ref);
				break;
			}
			// find a class:
			if (projectModel.getClass(ref).length > 0) {
				helpBuffer.append(ref);
				break;
			}
			// find a constant:
			if (projectModel.getConstant(ref).length > 0) {
				helpBuffer.append(ref);
				break;
			}
			boolean shouldBreak = false;
			if (phpCodeData instanceof PHPFunctionData && phpCodeData.getContainer() != null && phpCodeData.getContainer() instanceof PHPClassData) {
				if (ref.indexOf('(') != -1) {
					ref = ref.substring(0, ref.indexOf('('));
				}
				if (fileName != null && projectModel.getClassFunctionData(fileName, phpCodeData.getContainer().getName(), ref) != null) {
					helpBuffer.append(ref);
					break;
				}
				if (fileName != null) {
					PHPCodeContext context = ModelSupport.createContext(phpCodeData);
					ref = ref;
					if (ref.startsWith("$")) { //$NON-NLS-1$
						ref = ref.substring(1);
					}
					CodeData[] data = projectModel.getVariables(fileName, context, ref, true);
					for (CodeData codeData : data) {
						if (((PHPCodeData) codeData).getName().equals(ref)) {
							helpBuffer.append(ref);
							shouldBreak = true;
							break;
						}
					}
				}
				if (shouldBreak) {
					break;
				}
				if (projectModel.getFunction(ref) != null && projectModel.getFunction(ref).length > 0) {
					helpBuffer.append(ref);
					break;
				}
			}
			// didn't find it...
			helpBuffer.append(ref);
		}
		helpBuffer.append("</dd>"); //$NON-NLS-1$
		return helpBuffer.toString();
	}

	private static String getDeprecatedTagTableRow(Iterator it) {
		helpBuffer.delete(0, helpBuffer.length());
		helpBuffer.append("<br><dt>Deprecated</dt>"); //$NON-NLS-1$
		helpBuffer.append("<dd>"); //$NON-NLS-1$
		PHPDocTag deprecated = (PHPDocTag) it.next();
		helpBuffer.append(deprecated.getValue());
		helpBuffer.append("</dd>"); //$NON-NLS-1$
		return helpBuffer.toString();
	}

	private static String getReturnTagTableRows(Iterator it) {
		helpBuffer.delete(0, helpBuffer.length());
		helpBuffer.append("<br><dt>Returns</dt>"); //$NON-NLS-1$
		PHPDocTag returns = (PHPDocTag) it.next();
		String arg = returns.getValue();
		helpBuffer.append("<dd>"); //$NON-NLS-1$
		helpBuffer.append(arg);
		helpBuffer.append("</dd>"); //$NON-NLS-1$
		return helpBuffer.toString();
	}

	private static String getThrowTagTableRows(Iterator it) {
		helpBuffer.delete(0, helpBuffer.length());
		helpBuffer.append("<br><dt>Throws</dt>"); //$NON-NLS-1$
		PHPDocTag throwTag = (PHPDocTag) it.next();
		String arg = throwTag.getValue();
		helpBuffer.append("<dd>"); //$NON-NLS-1$
		helpBuffer.append(arg);
		helpBuffer.append("</dd>"); //$NON-NLS-1$
		return helpBuffer.toString();
	}

	private static String getParamTagTableRows(Iterator it) {
		helpBuffer.delete(0, helpBuffer.length());
		while (it.hasNext()) {
			PHPDocTag param = (PHPDocTag) it.next();
			String arg = param.getValue();
			arg = dolar_pattern.matcher(arg).replaceAll(""); //$NON-NLS-1$
			arg = unknown_type_pattern.matcher(arg).replaceAll(""); //$NON-NLS-1$
			if (arg.split(" ").length > 1) { //$NON-NLS-1$
				helpBuffer.append("<dd>"); //$NON-NLS-1$
				helpBuffer.append(arg);
				helpBuffer.append("</dd>"); //$NON-NLS-1$
			}
		}
		if (helpBuffer.toString().length() > 0) {
			String prefix = "<br><dt>Parameters</dt>"; //$NON-NLS-1$
			helpBuffer = helpBuffer.insert(0, prefix.toCharArray(), 0, prefix.length());
		}
		return helpBuffer.toString();
	}

	private static String getDescriptionTableRow(String description) {
		helpBuffer.delete(0, helpBuffer.length());
		description = description.replaceAll("\\n", "<br>"); //$NON-NLS-1$ //$NON-NLS-2$
		helpBuffer.append("<br><dt>Description</dt>"); //$NON-NLS-1$
		helpBuffer.append("<dd>"); //$NON-NLS-1$
		helpBuffer.append(description);
		helpBuffer.append("</dd>"); //$NON-NLS-1$
		return helpBuffer.toString();
	}

	private static String getClassNameTableRow(String className, PHPProjectModel projectModel) {
		helpBuffer.delete(0, helpBuffer.length());
		helpBuffer.append("<br><dt>Class</dt>"); //$NON-NLS-1$
		helpBuffer.append("<dd>"); //$NON-NLS-1$
		helpBuffer.append(className);
		helpBuffer.append("</dd>"); //$NON-NLS-1$
		return helpBuffer.toString();
	}

	public static String getHTMLHyperlinkDescriptionText(CodeData codeData, PHPProjectModel projectModel) {
		if (!(codeData instanceof PHPCodeData)) {
			return ""; //$NON-NLS-1$
		}
		if (codeData instanceof PHPFunctionData) {
			return PHPCodeDataHTMLDescriptionUtilities.createFunctionDescriptionText((PHPCodeData) codeData, projectModel);
		}
		if (codeData instanceof PHPClassData) {
			return PHPCodeDataHTMLDescriptionUtilities.createClassDescriptionText((PHPClassData) codeData, projectModel);
		}
		if (codeData instanceof PHPClassVarData) {
			return PHPCodeDataHTMLDescriptionUtilities.createClassVariableDescriptionText((PHPClassVarData) codeData, projectModel);
		}
		if (codeData instanceof PHPVariableData) {
			return PHPCodeDataHTMLDescriptionUtilities.createVariableDescriptionText((PHPVariableData) codeData);
		}
		if (codeData instanceof PHPConstantData) {
			return PHPCodeDataHTMLDescriptionUtilities.createPHPConstantDescriptionText((PHPConstantData) codeData);
		}
		if (codeData instanceof PHPClassConstData) {
			return PHPCodeDataHTMLDescriptionUtilities.createPHPClassConstDescriptionText((PHPClassConstData) codeData, projectModel);
		}
		return ""; //$NON-NLS-1$
	}
}
