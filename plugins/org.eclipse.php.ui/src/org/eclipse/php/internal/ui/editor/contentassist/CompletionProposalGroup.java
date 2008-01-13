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
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.core.internal.watson.ElementTree;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.php.internal.core.phpModel.parser.ModelSupport;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public abstract class CompletionProposalGroup {

	public static final String ELEMENT_NAME_SEPARATOR = "_"; //$NON-NLS-1$

	public static final String COLLAPSED_PREFIX = "..."; //$NON-NLS-1$

	public static final String COLLAPSED_SUFFIX = "*"; //$NON-NLS-1$

	public static final String PATH_SEPARATOR = Character.toString(IPath.SEPARATOR);

	private static final Path COMPLETION_TREE_ROOT = new Path("ROOT"); //$NON-NLS-1$
	protected int offset;
	protected CodeData[] codeDataProposals;
	protected ICompletionProposal[] completionProposals;
	protected String key;
	protected int segmentsToCut;
	protected int selectionLength;
	protected PHPProjectModel projectModel;
	protected boolean groupOptions;
	protected boolean cutCommonPrefix;

	public CompletionProposalGroup() {
		setData(0, null, null, selectionLength);
		setOffset(0);
	}

	public void setData(int offset, CodeData[] data, String key, int selectionLength, boolean isKeyStrict) {
		setOffset(offset);
		if (isKeyStrict) {
			ArrayList strictData = new ArrayList(1);
			for (int i = 0; i < data.length; ++i)
				if (key.equalsIgnoreCase(data[i].getName())) {
					strictData.add(data[i]);
				}
			data = (CodeData[]) strictData.toArray(new CodeData[strictData.size()]);
		}
		setCodeDataProposals(data);
		this.key = key;
		if (key != null) {
			IPath keyPath = elementNameToPath(key);
			if (cutCommonPrefix) {
				segmentsToCut = keyPath.segmentCount();
				if (segmentsToCut > 0 && !keyPath.hasTrailingSeparator()) {
					--segmentsToCut;
				}
			}
		}

		this.selectionLength = selectionLength;
	}

	public void setData(int offset, CodeData[] data, String key, int selectionLength) {
		setData(offset, data, key, selectionLength, false);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public CodeData[] getCodeDataProposals() {
		return codeDataProposals;
	}

	private void setCodeDataProposals(CodeData[] newProposals) {
		completionProposals = null;
		codeDataProposals = newProposals;
	}

	public ICompletionProposal[] getCompletionProposals(PHPProjectModel projectModel) {
		this.projectModel = projectModel;
		if (completionProposals == null) {
			this.completionProposals = calcCompletionProposals(projectModel);
			for (ICompletionProposal proposal : this.completionProposals) {
				if (proposal instanceof CodeDataCompletionProposal) {
					((CodeDataCompletionProposal) proposal).setOffset(offset);
				}
			}
		}
		return completionProposals;
	}

	/**
	 * Builds tree of all the matching elements
	 * @param sourceFilePath
	 * @return the tree
	 */
	private ElementTree buildCompletionTree(PHPProjectModel model, CodeData[] codeDatas) {
		final ElementTree tree = new ElementTree();
		for (final CodeData element : codeDatas) {
			String elementName = element.getName();
			final IPath namePath = elementNameToPath(elementName);
			treeRecursiveCreateElement(model, tree, COMPLETION_TREE_ROOT.append(namePath), element);
		}
		return tree;
	}

	/**
	 * Creates the element in the tree and creates/updates it's parents
	 * @param tree tree of the elements
	 * @param path path where the element should be placed
	 * @param data data to attach to the element
	 */
	private void treeRecursiveCreateElement(PHPProjectModel model, final ElementTree tree, final IPath path, final CodeData data) {
		if (path.segmentCount() == 1 && !tree.includes(path)) {
			tree.createElement(path, data);
			return;
		}
		final IPath parentPath = path.removeLastSegments(1);
		final IPath grandParentPath = parentPath.removeLastSegments(1);
		if (!tree.includes(parentPath)) {
			treeRecursiveCreateElement(model, tree, parentPath, null);
		}
		if (!tree.includes(path)) {
			if (data != null) {
				tree.createElement(path, data);
			} else {
				tree.createElement(path, null);
			}
		} else {
			if (data != null) {
				Object oldData = tree.getElementData(path);
				if (oldData instanceof LinkedHashSet) {
					((LinkedHashSet<CodeData>) oldData).add(data);
				} else {
					LinkedHashSet<CodeData> newData = new LinkedHashSet<CodeData>(2);
					newData.add((CodeData)oldData);
					newData.add(data);
					tree.setElementData(path, newData);
				}
			}
		}
	}

	private Collection<IPath> calculateProposalPaths(final ElementTree completionTree, final IPath root, boolean hasBrothers) {
		if (completionTree == null)
			return null;
		Collection<IPath> originalPath = Arrays.asList(new IPath[] { root });
		final int childCount = completionTree.getChildCount(root);
		if (childCount == 0) {
			if (completionTree.getElementData(root) != null)
				return originalPath;
			return null;
		}
		final IPath[] childrenRoots = completionTree.getChildren(root);
		boolean allEqual = childCount > 1;
		if (allEqual) {
			for (int i = 0; i < childrenRoots.length; i++) {
				if (i + 1 < childrenRoots.length && !childrenRoots[i].lastSegment().equalsIgnoreCase(childrenRoots[i + 1].lastSegment())) {
					allEqual = false;
					break;
				}
			}
		}
		if (childCount > 1 && hasBrothers)
			return originalPath;
		final Collection<IPath> childCompletions = new ArrayList();
		final boolean childHasBrothers = childCount > 1 && !allEqual;

		for (final IPath childRoot : childrenRoots) {
			final Collection<IPath> childCompletionProposal = calculateProposalPaths(completionTree, childRoot, hasBrothers || childHasBrothers);
			if (childCompletionProposal != null)
				childCompletions.addAll(childCompletionProposal);
		}
		return childCompletions;
	}

	protected ICompletionProposal[] calcCompletionProposals(PHPProjectModel projectModel) {
		if (codeDataProposals == null || codeDataProposals.length == 0) {
			return ContentAssistSupport.EMPTY_CodeDataCompletionProposal_ARRAY;
		}

		CodeData[] codeDatas = ModelSupport.getCodeDataStartingWith(codeDataProposals, key);

		// filter internal code data
		codeDatas = ModelSupport.removeFilteredCodeData(codeDatas, ModelSupport.INTERNAL_CODEDATA_FILTER);

		if (!groupOptions) {
			// this is the default option - just create proposals for each code data and return the array
			ICompletionProposal[] results = new ICompletionProposal[codeDatas.length];
			for (int i = 0; i < results.length; ++i) {
				results[i] = createElementProposal(projectModel, codeDatas[i]);
			}
			return results;
		}

		// else:
		// 1. Create tree of all the elements:
		final ElementTree completionTree = buildCompletionTree(projectModel, codeDatas);

		final Collection<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>(codeDatas.length);
		if (completionTree.getChildCount(completionTree.getRoot()) > 0) {
			final Path root = COMPLETION_TREE_ROOT;
			// 2. Extract only the relevant element and group paths from the tree:
			final Collection<IPath> completionProposalPaths = calculateProposalPaths(completionTree, root, false);
			for (final IPath completionProposalPath : completionProposalPaths) {
				Object elementData = completionTree.getElementData(completionProposalPath);
				Collection<CodeData> elementDatas;
				if (!(elementData instanceof Collection)) {
					elementDatas = Arrays.asList(new CodeData[] { (CodeData) elementData });
				} else {
					elementDatas = (Collection<CodeData>) elementData;
				}

				for (CodeData currentData : elementDatas) {
					if (currentData != null) {
						proposals.add(createElementProposal(projectModel, currentData));
					} else {

						// show directories even if matched:
						IPath replacementPath = completionProposalPath.removeFirstSegments(1);
						String replacement = elementPathToName(replacementPath) + ELEMENT_NAME_SEPARATOR;
						proposals.add(createGroupProposal(completionProposalPath, replacement));
					}
				}
			}
		}

		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	private CodeDataCompletionProposal createElementProposal(PHPProjectModel projectModel, Object elementData) {
		if (!cutCommonPrefix) {
			return createProposal(projectModel, (CodeData) elementData);
		}
		return new PartialProposal(createProposal(projectModel, (CodeData) elementData), segmentsToCut);
	}

	static String elementPathToName(IPath replacementPath) {
		return replacementPath.toString().replaceAll(PATH_SEPARATOR, ELEMENT_NAME_SEPARATOR);
	}

	static Path elementNameToPath(String elementName) {
		return new Path(elementName.replaceAll("([^_]{1})"+ELEMENT_NAME_SEPARATOR, "$1" + PATH_SEPARATOR));
	}

	private TemporaryCompletionProposal createGroupProposal(final IPath completionProposalPath, String replacement) {
		return new TemporaryCompletionProposal(new CompletionProposal(replacement, getOffset() - key.length(), key.length(), replacement.length(), null,
			(segmentsToCut > 0 ? COLLAPSED_PREFIX + ELEMENT_NAME_SEPARATOR : "") + elementPathToName(completionProposalPath.removeFirstSegments(segmentsToCut + 1)) + ELEMENT_NAME_SEPARATOR + COLLAPSED_SUFFIX, null, null)); //$NON-NLS-1$
	}

	abstract protected CodeDataCompletionProposal createProposal(PHPProjectModel projectModel, CodeData codeData);

	/**
	 * @param groupCompletionOptions
	 */
	public void setGroupOptions(boolean groupCompletionOptions) {
		this.groupOptions = groupCompletionOptions;
	}

	public void setCutCommonPrefix(boolean cutCommonPrefix) {
		this.cutCommonPrefix = cutCommonPrefix;
	}

}