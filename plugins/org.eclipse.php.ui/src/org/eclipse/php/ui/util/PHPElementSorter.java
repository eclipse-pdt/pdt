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
package org.eclipse.php.ui.util;

import java.text.Collator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassConstData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.PHPIncludeFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPModifier;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData.PHPSuperClassNameData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData.PHPFunctionParameter;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.outline.PHPOutlineContentProvider.GroupNode;
import org.eclipse.php.ui.preferences.ui.MembersOrderPreferenceCache;
import org.eclipse.php.ui.projectOutline.ProjectOutlineContentProvider.OutlineNode;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Sorter for PHP elements. Ordered by element category, then by element name. 
 * Package fragment roots are sorted as ordered on the classpath.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 2.0
 */
public class PHPElementSorter extends ViewerSorter {

	int flags;
	boolean usingCategories = true;
	boolean usingLocation = false;

	private static final int PROJECTS = 1;
	private static final int PACKAGEFRAGMENTROOTS = 2;
	private static final int PACKAGEFRAGMENT = 3;

	private static final int RESOURCEFOLDERS = 4;

	private static final int COMPILATIONUNITS = 5;
	private static final int CLASSFILES = 6;

	private static final int RESOURCES = 8;
	private static final int STORAGE = 9;

	private static final int PACKAGE_DECL = 10;
	private static final int IMPORT_CONTAINER = 11;
	private static final int IMPORT_DECLARATION = 12;

	// Includes all categories ordered using the OutlineSortOrderPage:
	// types, initializers, methods & fields
	private static final int MEMBERSOFFSET = 16;
	private static final int OUTLINE_NODES = 15;

	private static final int JAVAELEMENTS = 50;
	private static final int OTHERS = 51;

	private MembersOrderPreferenceCache fMemberOrderCache;

	/**
	 * Constructor.
	 */
	public PHPElementSorter() {
		super(null); // delay initialization of collator
		fMemberOrderCache = PHPUiPlugin.getDefault().getMemberOrderPreferenceCache();
	}

	/**
	 * @deprecated Bug 22518. Method never used: does not override ViewerSorter#isSorterProperty(Object, String).
	 * Method could be removed, but kept for API compatibility.
	 * 
	 * @param element the element
	 * @param property the property
	 * @return always <code>true</code>
	 */
	public boolean isSorterProperty(Object element, Object property) {
		return true;
	}

	/*
	 * @see ViewerSorter#category
	 */
	public int category(Object element) {
		if (element instanceof PHPCodeData) {
			if (element instanceof PHPFunctionData) {
				PHPFunctionData function = (PHPFunctionData) element;
				if (function.getContainer() instanceof PHPClassData && ("__construct".equals(function.getName()) || "__destruct".equals(function.getName()))) {
					return getMemberCategory(MembersOrderPreferenceCache.CONSTRUCTORS_INDEX);
				}
				if (PHPModifier.isStatic(((PHPFunctionData) element).getModifiers())) {
					return getMemberCategory(MembersOrderPreferenceCache.STATIC_FUNCTIONS_INDEX);
				}
				return getMemberCategory(MembersOrderPreferenceCache.FUNCTIONS_INDEX);
			}
			if (element instanceof PHPClassData) {
				return getMemberCategory(MembersOrderPreferenceCache.CLASS_INDEX);
			}
			if (element instanceof PHPConstantData || element instanceof PHPClassConstData) {
				return getMemberCategory(MembersOrderPreferenceCache.CONSTANTS_INDEX);
			}
			if (element instanceof PHPIncludeFileData) {
				return getMemberCategory(MembersOrderPreferenceCache.INCLUDEFILES_INDEX);
			}

			if (element instanceof PHPClassVarData) {
				if (PHPModifier.isStatic(((PHPClassVarData) element).getModifiers())) {
					return getMemberCategory(MembersOrderPreferenceCache.STATIC_VARS_INDEX);
				}
				return getMemberCategory(MembersOrderPreferenceCache.VARS_INDEX);
			}

			if (element instanceof PHPSuperClassNameData) {
				return MEMBERSOFFSET;
			}
		}

		if (element instanceof PHPProjectModel) {
			return PROJECTS;
		}
		if (element instanceof PHPFileData) {
			return COMPILATIONUNITS;
		}
		if (element instanceof IFile) {
			return RESOURCES;
		}
		if (element instanceof IProject) {
			return PROJECTS;
		}
		if (element instanceof IContainer) {
			return RESOURCEFOLDERS;
		}
		if (element instanceof IStorage) {
			return STORAGE;
		}
		if (element instanceof OutlineNode || element instanceof GroupNode) {
			return OUTLINE_NODES;
		}
		return OTHERS;
	}

	private int getMemberCategory(int kind) {
		int offset = fMemberOrderCache.getCategoryIndex(kind);
		return offset + MEMBERSOFFSET;
	}

	/*
	 * @see ViewerSorter#compare
	 */
	public int compare(Viewer viewer, Object e1, Object e2) {

		int cat1 = category(e1);
		int cat2 = category(e2);

		if (usingCategories) {
			if (cat1 != cat2)
				return cat1 - cat2;
		}

		if (cat1 == PROJECTS || cat1 == RESOURCES || cat1 == RESOURCEFOLDERS || cat1 == STORAGE || cat1 == OTHERS) {
			String name1 = getNonPHPElementLabel(viewer, e1);
			String name2 = getNonPHPElementLabel(viewer, e2);
			if (name1 == name2)
				return 0;
			if (name1 != null) {
				return getCollator().compare(name1, name2);
			}
			return -1; // can't compare
		}

		// if it is an outline node (classes, functions, constants) sort by type
		if (cat1 == OUTLINE_NODES && cat2 == OUTLINE_NODES) {
			return ((Comparable) e1).compareTo(e2);
		}

		int vis = 0;
		if (e1 instanceof PHPCodeData) {
			if (fMemberOrderCache.isSortByVisibility()) {
				int flags1 = getVisibilityCode(e1);
				int flags2 = getVisibilityCode(e2);
				vis = fMemberOrderCache.getVisibilityIndex(flags1) - fMemberOrderCache.getVisibilityIndex(flags2);
				if (usingCategories) {
					if (vis != 0) {
						return vis;
					}
				}
			}
		}

		boolean currentUsingLocation = false;

		UserData userData1 = null;
		UserData userData2 = null;

		if (usingLocation && e1 instanceof PHPCodeData && e2 instanceof PHPCodeData) {
			currentUsingLocation = true;
			PHPCodeData code1 = (PHPCodeData) e1;
			PHPCodeData code2 = (PHPCodeData) e2;
			if (PHPModelUtil.getPHPFileContainer(code1) != PHPModelUtil.getPHPFileContainer(code2)) {
				currentUsingLocation = false;
			} else {
				userData1 = code1.getUserData();
				userData2 = code2.getUserData();

				if (userData1 == null || userData2 == null)
					currentUsingLocation = false;
			}
		}
		if (currentUsingLocation) {
			return userData1.getStartPosition() - userData2.getStartPosition();
		}
		if (e1 instanceof PHPSuperClassNameData) {
			if (!(e1 instanceof PHPSuperClassNameData)) {
				return -1;
			}
		} else if (e2 instanceof PHPSuperClassNameData) {
			return +1;
		}

		String name1 = getElementName(e1);
		String name2 = getElementName(e2);

		int cmp = getCollator().compare(name1, name2);
		if (cmp != 0) {
			return cmp;
		}

		// from here names are equal:

		if (e1 instanceof PHPFunctionData && e2 instanceof PHPFunctionData) {
			PHPFunctionParameter[] params1 = ((PHPFunctionData) e1).getParameters();
			PHPFunctionParameter[] params2 = ((PHPFunctionData) e2).getParameters();
			int len = Math.min(params1.length, params2.length);
			for (int i = 0; i < len; i++) {
				String classType1 = params1[i].getClassType();
				String classType2 = params2[i].getClassType();
				if (classType1 == null) {
					if (classType2 == null)
						return 0;
					return 1;
				} else if (classType2 == null)
					return -1;
			}
			return params1.length - params2.length;
		}
		if (!usingCategories) { // process categories after names, if not categorized:
			if (cat1 != cat2)
				return cat1 - cat2;
			return vis;
		}
		return 0;
	}

	private int getVisibilityCode(Object e) {
		if (e instanceof PHPClassData) {
			PHPClassData cls = (PHPClassData) e;
			return cls.getModifiers();
		} else if (e instanceof PHPFunctionData) {
			PHPFunctionData function = (PHPFunctionData) e;
			return function.getModifiers();
		} else if (e instanceof PHPClassVarData) {
			PHPClassVarData var = (PHPClassVarData) e;
			return var.getModifiers();
		}
		return 0;
	}

	private String getNonPHPElementLabel(Viewer viewer, Object element) {

		if (element instanceof IAdaptable) {
			IWorkbenchAdapter adapter = (IWorkbenchAdapter) ((IAdaptable) element).getAdapter(IWorkbenchAdapter.class);
			if (adapter != null) {
				return adapter.getLabel(element);
			}
		}
		if (viewer instanceof ContentViewer) {
			IBaseLabelProvider prov = ((ContentViewer) viewer).getLabelProvider();
			if (prov instanceof ILabelProvider) {
				return ((ILabelProvider) prov).getText(element);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerSorter#getCollator()
	 */
	public final Collator getCollator() {
		if (collator == null) {
			collator = Collator.getInstance();
		}
		return collator;
	}

	private String getElementName(Object element) {
		if (element instanceof PHPCodeData) {
			return ((PHPCodeData) element).getName();
		}
		return element.toString();
	}

	/**
	 * @return the usingCategories
	 */
	public boolean isUsingCategories() {
		return usingCategories;
	}

	/**
	 * @param usingCategories the usingCategories to set
	 */
	public void setUsingCategories(boolean usingCategories) {
		this.usingCategories = usingCategories;
	}

	/**
	 * @return the usingLocation
	 */
	public boolean isUsingLocation() {
		return usingLocation;
	}

	/**
	 * @param usingLocation the usingLocation to set
	 */
	public void setUsingLocation(boolean usingLocation) {
		this.usingLocation = usingLocation;
	}
}
