/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.projectoutlineview;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.model.PhpModelAccess;
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

	GROUP_CLASSES(PHPPluginImages.DESC_OBJ_PHP_CLASSES_GROUP.createImage(),
			PHPUIMessages.getString("PHPProjectOutline.nodes.classes")), GROUP_NAMESPACES(
			PHPPluginImages.DESC_OBJ_PHP_NAMESPACES_GROUP.createImage(),
			PHPUIMessages.getString("PHPProjectOutline.nodes.namespaces")), GROUP_CONSTANTS(
			PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP.createImage(),
			PHPUIMessages.getString("PHPProjectOutline.nodes.constants")), GROUP_FUNCTIONS(
			PHPPluginImages.DESC_OBJ_PHP_FUNCTIONS_GROUP.createImage(),
			PHPUIMessages.getString("PHPProjectOutline.nodes.functions"));

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

			IDLTKSearchScope scope = SearchEngine.createSearchScope(
					ProjectOutlineContentProvider.scripProject,
					IDLTKSearchScope.SOURCES);

			TreeSet<IModelElement> childrenList = new TreeSet<IModelElement>(
					new Comparator<IModelElement>() {
						public int compare(IModelElement o1, IModelElement o2) {
							int res = o1.getElementName().compareTo(
									o2.getElementName());
							if (res == 0) {
								return (o1.getPath().toOSString() + o1
										.getElementName()).compareTo(o2
										.getPath().toOSString()
										+ o2.getElementName());
							}
							return res;

						}
					});
			switch (this) {
			case GROUP_NAMESPACES:
				childrenList.addAll(Arrays.asList(PhpModelAccess.getDefault()
						.findTypes(null, MatchRule.PREFIX,
								new int[] { Modifiers.AccNameSpace }, scope,
								null)));
				break;

			case GROUP_CLASSES:
				childrenList.addAll(Arrays.asList(PhpModelAccess.getDefault()
						.findTypes(null, MatchRule.PREFIX,
								new int[] { ~Modifiers.AccNameSpace }, scope,
								null)));
				break;

			case GROUP_FUNCTIONS:
				childrenList
						.addAll(Arrays.asList(PhpModelAccess.getDefault()
								.findMethods(null, MatchRule.PREFIX,
										new int[] { Modifiers.AccGlobal },
										scope, null)));
				break;

			case GROUP_CONSTANTS:
				childrenList.addAll(Arrays.asList(PhpModelAccess.getDefault()
						.findFields(
								null,
								MatchRule.PREFIX,
								new int[] { Modifiers.AccConstant,
										Modifiers.AccGlobal }, scope, null)));
				break;
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