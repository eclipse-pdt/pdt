/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.functions;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPLanguageModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public class PHPFunctionsContentProvider implements ITreeContentProvider {

	public static final String CONSTANTS_NODE_NAME = "constants"; //$NON-NLS-1$
	protected static final Object[] NO_CHILDREN = new Object[0];
	private PHPLanguageModel model;

	public PHPFunctionsContentProvider() {
	}

	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	public boolean hasChildren(Object element) {
		// assume CUs and class files are never empty
		if (element instanceof PHPFileData) {
			return true;
		}
		if (element instanceof PHPProjectModel) {
			return true;
		}

		if (element instanceof PHPCodeData) {
			PHPCodeData codeData = (PHPCodeData) element;
			return PHPModelUtil.hasChildren(codeData);
		}
		Object[] children = getChildren(element);
		return (children != null) && children.length > 0;
	}

	public Object getParent(Object element) {
		return null;
	}

	protected boolean exists(Object element) {
		if (element == null) {
			return false;
		}
		if (element instanceof IResource) {
			return ((IResource) element).exists();
		}

		return true;
	}

	protected Object internalGetParent(Object element) {
		return PHPModelUtil.getParent(element);
	}

	private Object[] getClassChildren(PHPClassData classData) {
		ArrayList list = new ArrayList();
		PHPClassConstData[] consts = classData.getConsts();
		if (consts != null)
			for (int i = 0; i < consts.length; ++i) {
				list.add(consts[i]);
			}
		PHPVariableData[] vars = classData.getVars();
		if (vars != null)
			for (int i = 0; i < vars.length; i++) {
				list.add(vars[i]);
			}
		PHPFunctionData[] functions = classData.getFunctions();
		if (functions != null)
			for (int i = 0; i < functions.length; i++) {
				list.add(functions[i]);
			}
		return list.toArray();
	}

	public Object[] getChildren(Object parentElement) {
		if (!exists(parentElement)) {
			return NO_CHILDREN;
		}
		if (parentElement instanceof PHPLanguageModel) {
			//			PHPLanguageModel model = (PHPLanguageModel) parentElement;
			CodeData[] functions = model.getFunctions();
			CodeData[] classes = model.getClasses();
			//			CodeData[] constants = model.getConstants();
			Object[] rootsChildren = NO_CHILDREN;
			if (functions.length > 0 && classes.length > 0 /*&& constants.length > 0*/) {
				rootsChildren = new Object[classes.length + functions.length + 1];
				rootsChildren[0] = CONSTANTS_NODE_NAME;
				System.arraycopy(classes, 0, rootsChildren, 1, classes.length);
				System.arraycopy(functions, 0, rootsChildren, classes.length + 1, functions.length);
			}
			return rootsChildren;
		}
		if (parentElement instanceof PHPClassData) {
			return getClassChildren((PHPClassData) parentElement);
		}
		if (parentElement.equals(CONSTANTS_NODE_NAME)) {
			return model.getConstants();
		}
		return NO_CHILDREN;
	}

	public void dispose() {
		//        PHPModelManager.getInstance().removeModelListener(this);
	}

	public void inputChanged(final Viewer viewer, Object oldInput, Object newInput) {
		if (newInput == null) {
			return;
		}
		model = (PHPLanguageModel) newInput;
	}

}
