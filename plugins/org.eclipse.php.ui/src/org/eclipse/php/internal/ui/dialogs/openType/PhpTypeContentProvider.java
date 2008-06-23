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
/**
 * 
 */
package org.eclipse.php.internal.ui.dialogs.openType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.ui.PHPUiPlugin;


public class PhpTypeContentProvider implements IStructuredContentProvider {

	Object[] elements = null;
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		if (elements == null) {
			ArrayList arrayList = new ArrayList();
			//traverse over all the php projects and get the model for each one.
			IProject[] projects = PHPUiPlugin.getWorkspace().getRoot().getProjects();
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
				addClassesData(projectModel.getClasses(), arrayList);
				addData(projectModel.getFunctions(), arrayList);
				addData(projectModel.getConstants(), arrayList);
			}
			
			elements = arrayList.toArray();
			Arrays.sort(elements, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					return arg0.toString().compareToIgnoreCase(arg1.toString());
				}
			});

		}

		return elements;
	}

	private void addClassesData(CodeData[] classes, ArrayList arrayList) {
		addData(classes, arrayList);
		for (int i = 0; i < classes.length; i++) {
			PHPClassData classData = (PHPClassData)classes[i];
			addData(classData.getConsts(), arrayList);
			addData(classData.getFunctions(), arrayList);
		}
	}

	private void addData(CodeData[] classes, ArrayList arrayList) {
		for (int i = 0; i < classes.length; i++) {
			CodeData codeData = classes[i];
			arrayList.add(codeData);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
