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
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassVarData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.PHPModifier;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData.PHPFunctionParameter;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.ui.MembersOrderPreferenceCache;
import org.eclipse.php.ui.projectOutline.ProjectOutlineContentProvider.OutlineNode;
import org.eclipse.ui.model.IWorkbenchAdapter;


/**
 * Sorter for Java elements. Ordered by element category, then by element name. 
 * Package fragment roots are sorted as ordered on the classpath.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 2.0
 */
public class PHPElementSorter extends ViewerSorter {

	private static final int PROJECTS = 1;
	private static final int PACKAGEFRAGMENTROOTS = 2;
	private static final int PACKAGEFRAGMENT = 3;

	private static final int COMPILATIONUNITS = 4;
	private static final int CLASSFILES = 5;

	private static final int RESOURCEFOLDERS = 7;
	private static final int RESOURCES = 8;
	private static final int STORAGE = 9;

	private static final int PACKAGE_DECL = 10;
	private static final int IMPORT_CONTAINER = 11;
	private static final int IMPORT_DECLARATION = 12;

	// Includes all categories ordered using the OutlineSortOrderPage:
	// types, initializers, methods & fields
	private static final int MEMBERSOFFSET = 15;
	private static final int OUTLINE_NODES = 16;

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
				if (false) {//function.isConstructor()) {
					return getMemberCategory(MembersOrderPreferenceCache.CONSTRUCTORS_INDEX);
				}
				int flags = function.getModifiers();
				if (PHPModifier.isStatic(flags))
					return getMemberCategory(MembersOrderPreferenceCache.STATIC_FUNCTIONS_INDEX);
				else
					return getMemberCategory(MembersOrderPreferenceCache.FUNCTIONS_INDEX);

			} else if (element instanceof PHPClassVarData) {
				PHPClassVarData var = (PHPClassVarData) element;
				int flags = var.getModifiers();
				if (PHPModifier.isStatic(flags))
					return getMemberCategory(MembersOrderPreferenceCache.STATIC_VARS_INDEX);
				else
					return getMemberCategory(MembersOrderPreferenceCache.VARS_INDEX);

			} else if (element instanceof PHPClassVarData) {
				return getMemberCategory(MembersOrderPreferenceCache.CLASS_INDEX);
			} else if (element instanceof PHPClassVarData) {
				return getMemberCategory(MembersOrderPreferenceCache.CLASS_INDEX);
			} else if (element instanceof PHPProjectModel)
				return PROJECTS;
			else if (element instanceof PHPClassData)
				return CLASSFILES;
			else if (element instanceof PHPFileData)
				return COMPILATIONUNITS;
			return JAVAELEMENTS;
		} else if (element instanceof IFile) {
			return RESOURCES;
		} else if (element instanceof IProject) {
			return PROJECTS;
		} else if (element instanceof IContainer) {
			return RESOURCEFOLDERS;
		} else if (element instanceof IStorage) {
			return STORAGE;
		} else if (element instanceof OutlineNode) {
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

		if (cat1 != cat2)
			return cat1 - cat2;

		if (cat1 == PROJECTS || cat1 == RESOURCES || cat1 == RESOURCEFOLDERS || cat1 == STORAGE || cat1 == OTHERS) {
			String name1 = getNonPHPElementLabel(viewer, e1);
			String name2 = getNonPHPElementLabel(viewer, e2);
			if (name1 != null && name2 != null) {
				return getCollator().compare(name1, name2);
			}
			return 0; // can't compare
		}
		
		// if it is an outline node (classes, functions, constants) sort by type
		if (cat1 == OUTLINE_NODES && cat2 == OUTLINE_NODES) {
			assert e1 instanceof OutlineNode && e2 instanceof OutlineNode; 
			return ((Comparable) e1).compareTo(e2);	
		}

		if (e1 instanceof PHPCodeData) {
			if (fMemberOrderCache.isSortByVisibility()) {
				int flags1 = getVisibilityCode(e1);
				int flags2 = getVisibilityCode(e2);
				int vis = fMemberOrderCache.getVisibilityIndex(flags1) - fMemberOrderCache.getVisibilityIndex(flags2);
				if (vis != 0) {
					return vis;
				}
			}
		}
		
		String name1 = getElementName(e1);
		String name2 = getElementName(e2);

		if (e1 instanceof PHPClassData) { // handle anonymous types
			if (name1.length() == 0) {
				if (name2.length() == 0) {
					return getCollator().compare(((PHPClassData) e1).getSuperClassData().getName(), ((PHPClassData) e2).getSuperClassData().getName());
				} else {
					return 1;
				}
			} else if (name2.length() == 0) {
				return -1;
			}
		}

		int cmp = getCollator().compare(name1, name2);
		if (cmp != 0) {
			return cmp;
		}

		if (e1 instanceof PHPFunctionData) {
			PHPFunctionParameter[] params1 = ((PHPFunctionData) e1).getParameters();
			PHPFunctionParameter[] params2 = ((PHPFunctionData) e2).getParameters();
			int len = Math.min(params1.length, params2.length);
			for (int i = 0; i < len; i++) {
				cmp = getCollator().compare(params1[i].getClassType(), params2[i].getClassType());
				if (cmp != 0) {
					return cmp;
				}
			}
			return params1.length - params2.length;
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
		} else {
			return element.toString();
		}
	}
}
