/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views.coverage;

import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;

/**
 * Code coverage view content provider.
 */
public class CodeCoverageContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY = new Object[0];
	private static ICodeCoverageFilter[] filters;
	private Map<Object, Object[]> cachedChildren;
	private Map<Object, CodeCoverageResult> cachedCoverage;
	private Map<VirtualPath, CodeCoverageData> coveredFilesMap;
	private Map<VirtualPath, Object> fileDataMap;
	private Set<VirtualPath> remoteFiles;
	private StandardModelElementContentProvider provider;
	private IProject project;

	public CodeCoverageContentProvider() {
		provider = new StandardModelElementContentProvider(true);
		initializeFilters();
	}

	private static void initializeFilters() {
		if (filters == null) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor("org.eclipse.php.debug.ui.phpCodeCoverageFilter"); //$NON-NLS-1$
			List<ICodeCoverageFilter> filtersList = new LinkedList<ICodeCoverageFilter>();
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("filter")) { //$NON-NLS-1$
					try {
						filtersList.add((ICodeCoverageFilter) element.createExecutableExtension("class")); //$NON-NLS-1$
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
			filters = (ICodeCoverageFilter[]) filtersList.toArray(new ICodeCoverageFilter[filtersList.size()]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	public Object[] getChildren(final Object element) {
		if (cachedChildren.containsKey(element)) {
			return cachedChildren.get(element);
		}
		if (element instanceof IFile || element instanceof ISourceModule) {
			return EMPTY;
		}
		if (element instanceof IProject && project != null && element != project) {
			return EMPTY;
		}
		final Object[] children = provider.getChildren(element);
		final List<Object> filteredChildrenList = new ArrayList<Object>(children.length);
		for (int i = 0; i < children.length; ++i) {
			final Object child = children[i];
			if (child instanceof IFile || child instanceof ISourceModule) {
				if (!isFiltered(child)) {
					filteredChildrenList.add(child);
				}
			} else if (hasChildren(child)) {
				filteredChildrenList.add(child);
			}
		}
		final Object[] result = filteredChildrenList.toArray();
		cachedChildren.put(element, result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	public Object[] getElements(final Object inputElement) {
		if (coveredFilesMap.isEmpty()) {
			return new Object[] {};
		}
		cachedChildren = new HashMap<Object, Object[]>(0);
		cachedCoverage = new HashMap<Object, CodeCoverageResult>(0);
		remoteFiles = new HashSet<VirtualPath>();
		// Filter out non-relevant elements:
		HashMap<VirtualPath, Object> filteredDataMap = new HashMap<VirtualPath, Object>();
		for (VirtualPath path : fileDataMap.keySet()) {
			boolean isFiltered = false;
			for (ICodeCoverageFilter filter : filters) {
				if (filter.isFiltered(path)) {
					isFiltered = true;
					break;
				}
			}
			if (!isFiltered) {
				filteredDataMap.put(path, fileDataMap.get(path));
			}
		}
		remoteFiles.addAll(filteredDataMap.keySet());
		Object[] elements = getChildren(inputElement);
		Object[] newElements = new Object[elements.length + remoteFiles.size()];
		System.arraycopy(elements, 0, newElements, 0, elements.length);
		int c = elements.length;
		Iterator<VirtualPath> i = remoteFiles.iterator();
		while (i.hasNext()) {
			VirtualPath key = i.next();
			newElements[c++] = filteredDataMap.get(key);
		}
		return newElements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	public Object getParent(final Object element) {
		return provider.getParent(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.ui.StandardPHPElementContentProvider#hasChildrenInternal
	 * (java.lang.Object)
	 */
	public boolean hasChildren(final Object element) {
		boolean hasChildren = provider.hasChildren(element);
		if (!hasChildren) {
			return false;
		}
		if (element instanceof IFile || element instanceof ISourceModule) {
			return false;
		}
		if (element instanceof IProject && project != null && element != project) {
			return false;
		}
		hasChildren = false;
		final Object[] children = getChildren(element);
		for (int i = 0; i < children.length; ++i) {
			hasChildren |= hasChildren(children[i]) | !isFiltered(children[i]);
		}
		return hasChildren;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		provider.inputChanged(viewer, oldInput, newInput);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		provider.dispose();
	}

	public CodeCoverageResult getCodeCoverageResult(final Object element) {
		if (cachedCoverage.containsKey(element)) {
			return cachedCoverage.get(element);
		}
		final CodeCoverageResult coverageResult;
		CodeCoverageData coverageData = null;
		if (element instanceof IModelElement) {
			ISourceModule sourceModule = PHPToolkitUtil.getSourceModule((IModelElement) element);
			if (sourceModule != null) {
				coverageData = coveredFilesMap.get(new VirtualPath(sourceModule.getResource().getName()));
			}
		} else if (element instanceof IFile) {
			coverageData = coveredFilesMap.get(new VirtualPath(((IFile) element).getFullPath().toString()));
		} else {
			coverageData = coveredFilesMap.get(new VirtualPath(element.toString()));
		}
		if (coverageData != null) {
			final int linesNum = coverageData.getLinesNum();
			int linesCovered = 0;
			int significantLines = 0;
			final byte[] coverageBitmask = coverageData.getCoverageBitmask();
			for (int i = 1; i <= linesNum; ++i) {
				if (coverageBitmask.length > i / 8) {
					if ((coverageBitmask[i / 8] >> i % 8 & 0x1) == (byte) 1) {
						++linesCovered;
					}
				}
			}
			final byte[] significanceBitmask = coverageData.getSignificanceBitmask();
			if (significanceBitmask != null) {
				for (int i = 1; i <= linesNum; ++i) {
					if (significanceBitmask.length > i / 8) {
						if ((significanceBitmask[i / 8] >> i % 8 & 0x1) == (byte) 1) {
							++significantLines;
						} else if ((coverageBitmask[i / 8] >> i % 8 & 0x1) == (byte) 1) {
							++significantLines;
							// --linesCovered;
						}
					}
				}
			} else {
				significantLines = -1;
			}
			coverageResult = new CodeCoverageResult(linesNum, linesCovered, significantLines, 1);
		} else if (element instanceof IModelElement) {
			// TODO calculate inner elements coverage
			coverageResult = new CodeCoverageResult(0, 0, -1, 0);
		} else {
			coverageResult = new CodeCoverageResult(0, 0, -1, 0);
			final Object[] children = getChildren(element);
			for (int i = 0; i < children.length; ++i) {
				coverageResult.addCoverageResult(getCodeCoverageResult(children[i]));
			}
			cachedCoverage.put(element, coverageResult);
		}
		return coverageResult;
	}

	public CodeCoverageData getCoverageData(final String remoteFile) {
		return coveredFilesMap.get(new VirtualPath(remoteFile));
	}

	public CodeCoverageData getCoverageData(final ISourceModule fileData) {
		return coveredFilesMap.get(new VirtualPath(fileData.getPath().toString()));
	}

	public CodeCoverageData getCoverageData(final IFile file) {
		return coveredFilesMap.get(new VirtualPath(file.getFullPath().toString()));
	}

	public void setCoveredFiles(final CodeCoverageData[] coveredFiles) {
		if (coveredFiles == null) {
			coveredFilesMap = new HashMap<VirtualPath, CodeCoverageData>(0);
			return;
		}
		coveredFilesMap = new HashMap<VirtualPath, CodeCoverageData>(coveredFiles.length);
		fileDataMap = new HashMap<VirtualPath, Object>(coveredFiles.length);
		for (CodeCoverageData element : coveredFiles) {
			final ISourceModule fileData = (ISourceModule) element.getAdapter(ISourceModule.class);
			if (fileData != null) {
				VirtualPath key = new VirtualPath(fileData.getPath().toString());
				coveredFilesMap.put(key, element);
				fileDataMap.put(key, fileData);
			} else {
				String localPath = element.getLocalFileName();
				IFile localFile = ResourcesPlugin.getWorkspace().getRoot().getFile(Path.fromOSString(localPath));
				if (localFile.exists()) {
					VirtualPath key = new VirtualPath(localFile.getFullPath().toString());
					coveredFilesMap.put(key, element);
					fileDataMap.put(key, localFile);
				} else {
					VirtualPath key = new VirtualPath(localPath);
					coveredFilesMap.put(key, element);
					fileDataMap.put(key, localPath);
				}
			}
		}
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	private boolean isFiltered(final Object child) {
		if (child instanceof IModelElement) {
			final ISourceModule fileData = PHPToolkitUtil.getSourceModule((IModelElement) child);
			if (fileData != null) {
				String name = fileData.getResource().getName();
				if (coveredFilesMap.containsKey(new VirtualPath(name))) {
					remoteFiles.remove(name);
					return false;
				}
			}
		}
		if (child instanceof IFile) {
			IFile localFile = (IFile) child;
			if (coveredFilesMap.containsKey(new VirtualPath(localFile.getFullPath().toString()))) {
				remoteFiles.remove(localFile.getFullPath().toString());
				return false;
			}
		}
		return true;
	}
}