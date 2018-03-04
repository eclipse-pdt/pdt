/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

import java.text.MessageFormat;
import java.util.Set;

public class OrganizeIncludesUtils {

	/**
	 * @param elements
	 *            (CodeData)
	 * @return
	 */
	static StringBuffer concatenateElementLabels(Set/* <CodeData> */<?> elements) {
		StringBuffer editGroupNameSuffix = new StringBuffer();
		// for (Iterator j = elements.iterator(); j.hasNext();) {
		// CodeData element = (CodeData) j.next();
		// String elementTypeLabel = getElementTypeLabel(element);
		// editGroupNameSuffix.append(elementTypeLabel).append("
		// ").append(element.getName()); //$NON-NLS-1$
		// if (j.hasNext())
		// editGroupNameSuffix.append(", "); //$NON-NLS-1$
		// }
		return editGroupNameSuffix;
	}

	/**
	 * @param element
	 */
	// private static String getElementTypeLabel(CodeData element) {
	// String elementTypeLabel = ""; //$NON-NLS-1$
	// if (element instanceof PHPClassData) {
	// int modifiers = ((PHPClassData) element).getModifiers();
	// if (PHPModifier.isInterface(modifiers)) {
	// elementTypeLabel = "interface"; //$NON-NLS-1$
	// } else {
	// elementTypeLabel = "class"; //$NON-NLS-1$
	// }
	// } else if (element instanceof PHPFunctionData)
	// elementTypeLabel = "function"; //$NON-NLS-1$
	// else if (element instanceof PHPConstantData)
	// elementTypeLabel = "constant"; //$NON-NLS-1$
	// return elementTypeLabel;
	// }
	//
	// /**
	// * @param fileData
	// * @return first global include
	// */
	// private static PHPIncludeFileData getFirstGlobalInclude(PHPFileData
	// fileData) {
	// PHPIncludeFileData firstGlobalInclude = null;
	//
	// PHPIncludeFileData[] includeFiles = fileData.getIncludeFiles();
	// List scopedElements = new ArrayList();
	// scopedElements.addAll(Arrays.asList(fileData.getClasses()));
	// scopedElements.addAll(Arrays.asList(fileData.getFunctions()));
	//
	// for (PHPIncludeFileData element : includeFiles) {
	// for (Iterator j = scopedElements.iterator(); j.hasNext();) {
	// CodeData scopedElement = (CodeData) j.next();
	// if (element.getUserData().getStartPosition() >
	// scopedElement.getUserData().getStartPosition() &&
	// element.getUserData().getEndPosition() >
	// scopedElement.getUserData().getEndPosition()) {
	// continue; // inside scoped element
	// }
	// firstGlobalInclude = element;
	// break;
	// }
	// if (firstGlobalInclude != null)
	// break;
	// }
	// return firstGlobalInclude;
	// }
	//
	// /**
	// * @param fileData
	// * @return first include offset to be
	// */
	// static int getFirstIncludeOffset(PHPFileData fileData) {
	// PHPIncludeFileData firstGlobalInclude = getFirstGlobalInclude(fileData);
	// if (firstGlobalInclude != null)
	// return firstGlobalInclude.getUserData().getStartPosition();
	//
	// PHPBlock[] phpBlocks = fileData.getPHPBlocks();
	// if (phpBlocks.length == 0)
	// return -1;
	// for (PHPBlock element : phpBlocks) {
	// UserData startTag = element.getPHPStartTag();
	// if (startTag.getEndPosition() - startTag.getStartPosition() == 3)
	// // <?= is not really good
	// continue;
	// if (startTag.getStopPosition() - startTag.getStartPosition() == 2)
	// // <? the next line is outside
	// return startTag.getEndPosition() + 1;
	// // <?php the next line is inside (bug?)
	// return startTag.getEndPosition();
	// }
	// return phpBlocks[0].getPHPStartTag().getEndPosition() + 1; // rare!
	// }
	//
	// static HashSet getAllIncludes(FileNetwork network, PHPFileData fileData)
	// {
	// return new HashSet(Arrays.asList(network.getIncludedFiles(fileData)));
	// }
	//
	// static Set<String> getDirectIncludes(FileNetwork network, PHPFileData
	// fileData) {
	// assert network != null : "Network should not be null";
	// FileNode fileNode = network.getNode(fileData);
	// Collection<FileNode> includedNodes = fileNode.getAllIncluded();
	// Set<String> includedFiles = new HashSet<String>();
	// for (Object element : includedNodes) {
	// FileNode includedNode = (FileNode) element;
	// includedFiles.add(includedNode.getFile().getName());
	// }
	// return includedFiles;
	// }

	static String getEditGroupName(String prefix, String includeLocation, Set<?> elements) {
		if (elements == null || elements.size() == 0) {
			return getEditGroupName(prefix, includeLocation);
		}
		StringBuffer editGroupNameSuffix = OrganizeIncludesUtils.concatenateElementLabels(elements);
		return getEditGroupName(prefix, includeLocation, editGroupNameSuffix.toString());
	}

	private static String getEditGroupName(String prefix, String includeLocation) {
		return MessageFormat.format("{0} {1}.", new Object[] { prefix, //$NON-NLS-1$
				includeLocation });
	}

	/**
	 * @param prefix
	 * @param includeLocation
	 * @param suffix
	 * @return
	 */
	static String getEditGroupName(String prefix, String includeLocation, String suffix) {
		return MessageFormat.format("{0} {1} - {2}.", new Object[] { prefix, //$NON-NLS-1$
				includeLocation, suffix });
	}

}
