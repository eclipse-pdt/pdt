/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.projectoutlineview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * Enum class , which holds the "Project Outline" view, first level of nodes,
 * and provides methods for getting relevant children (using 'mixin' model)
 * 
 * @author nir.c
 */
public enum ProjectOutlineGroups {

	GROUP_CLASSES(PHPPluginImages.DESC_OBJ_PHP_CLASSES_GROUP.createImage(), PHPUIMessages.getString("PHPProjectOutline.nodes.classes") ), 
	GROUP_CONSTANTS(PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP.createImage(), PHPUIMessages.getString("PHPProjectOutline.nodes.constants") ), 
	GROUP_FUNCTIONS(PHPPluginImages.DESC_OBJ_PHP_FUNCTIONS_GROUP.createImage(), PHPUIMessages.getString("PHPProjectOutline.nodes.functions") );

	private static final String ASTRIX_STRING = "*";
	private final Image image;
	private final String text;

	protected static final Object[] NO_CHILDREN = new Object[0];

	ProjectOutlineGroups(Image image, String text) {
		this.image = image;
		this.text = text;
	}

	public Image getImage() {
		return image;
	}

	public String getText() {
		return text;
	}

	protected Object[] getChildren() {
		if (ProjectOutlineContentProvider.scripProject != null) {
			ArrayList<IProjectFragment> projectFragments = new ArrayList<IProjectFragment>();

			// getting project's fragments
			try {
				for (IProjectFragment projectFragment : (((IScriptProject) ProjectOutlineContentProvider.scripProject).getProjectFragments())) {
					if (projectFragment != null && !projectFragment.isExternal()) { //adding only non-external resources
						projectFragments.add(projectFragment);
					}
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
			PHPMixinModel mixinModel = PHPMixinModel.getInstance((IScriptProject) ProjectOutlineContentProvider.scripProject);
			// foreach fragment getting its children, 
			// and then merging them into last fragments list

			TreeSet<IModelElement> childrenList = new TreeSet<IModelElement>(new Comparator<IModelElement>() {
				public int compare(IModelElement o1, IModelElement o2) {
					if (0 != o1.getElementName().compareTo(o2.getElementName())) {
						return (o1.getPath().toOSString() + o1.getElementName()).compareTo(o2.getPath().toOSString() + o2.getElementName());
					}
					return 0;

				}
			});

			for (IProjectFragment projectFragment : projectFragments) {
				IDLTKSearchScope scope = SearchEngine.createSearchScope(projectFragment);
				IModelElement[] children = null;

				switch (this) {
					case GROUP_CLASSES:
						children = mixinModel.getClass(ASTRIX_STRING, scope);
						break;

					case GROUP_FUNCTIONS:
						children = mixinModel.getFunction(ASTRIX_STRING, scope);
						break;

					case GROUP_CONSTANTS:
						children = mixinModel.getConstant(ASTRIX_STRING, null, scope);
						break;
				}

				childrenList.addAll(Arrays.asList(children));
			}

			return childrenList.toArray();
		}

		return NO_CHILDREN;
	}

	public boolean hasChildren() {
		Object[] children = getChildren();
		if (null == children || children.length == 0) {
			return false;
		}
		return true;
	}

}