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

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.NamespaceNode;
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
			PHPUIMessages.PHPProjectOutline_nodes_classes), GROUP_NAMESPACES(
			PHPPluginImages.DESC_OBJ_PHP_NAMESPACES_GROUP.createImage(),
			PHPUIMessages.PHPProjectOutline_nodes_namespaces), GROUP_CONSTANTS(
			PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP.createImage(),
			PHPUIMessages.PHPProjectOutline_nodes_constants), GROUP_FUNCTIONS(
			PHPPluginImages.DESC_OBJ_PHP_FUNCTIONS_GROUP.createImage(),
			PHPUIMessages.PHPProjectOutline_nodes_functions);

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

			IProject project = ProjectOutlineContentProvider.scripProject
					.getProject();
			try {
				if (!project.isAccessible() || !project.hasNature(PHPNature.ID)) {
					return NO_CHILDREN;
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IDLTKSearchScope scope = SearchEngine.createSearchScope(
					ProjectOutlineContentProvider.scripProject,
					IDLTKSearchScope.SOURCES);

			TreeSet<IModelElement> childrenList = new TreeSet<IModelElement>(
					new Comparator<IModelElement>() {
						public int compare(IModelElement o1, IModelElement o2) {
							int res = o1.getElementName().compareTo(
									o2.getElementName());
							if (res == 0) {
								String label1 = ScriptElementLabels
										.getDefault()
										.getElementLabel(
												o1,
												ScriptElementLabels.T_FULLY_QUALIFIED);
								String label2 = ScriptElementLabels
										.getDefault()
										.getElementLabel(
												o2,
												ScriptElementLabels.T_FULLY_QUALIFIED);
								return label1.compareTo(label2);
							}
							return res;

						}
					});
			switch (this) {
			case GROUP_NAMESPACES:
				IType[] namespaces = PhpModelAccess.getDefault().findTypes(
						null, MatchRule.PREFIX, Modifiers.AccNameSpace, 0,
						scope, null);
				Map<String, List<IType>> nsByName = new HashMap<String, List<IType>>();
				if (namespaces != null) {
					for (IType namespace : namespaces) {
						String namespaceName = namespace.getElementName();
						List<IType> nsList = nsByName.get(namespaceName);
						if (nsList == null) {
							nsList = new LinkedList<IType>();
							nsByName.put(namespaceName, nsList);
						}
						nsList.add(namespace);
					}
				}
				for (String namespaceName : nsByName.keySet()) {
					List<IType> nsList = nsByName.get(namespaceName);
					childrenList.add(new NamespaceNode(
							ProjectOutlineContentProvider.scripProject,
							namespaceName, nsList.toArray(new IType[nsList
									.size()])));
				}

				break;

			case GROUP_CLASSES:
				IType[] findTypes = PhpModelAccess.getDefault().findTypes(null,
						MatchRule.PREFIX, 0, Modifiers.AccNameSpace, scope,
						null);
				if (findTypes != null) {
					childrenList.addAll(Arrays.asList(findTypes));
				}
				break;

			case GROUP_FUNCTIONS:
				IMethod[] findMethods = PhpModelAccess.getDefault()
						.findMethods(null, MatchRule.PREFIX,
								Modifiers.AccGlobal, 0, scope, null);
				if (findMethods != null) {
					childrenList.addAll(Arrays.asList(findMethods));
				}
				break;

			case GROUP_CONSTANTS:
				// find all constants
				IField[] findFields = PhpModelAccess.getDefault().findFields(
						null, MatchRule.PREFIX, Modifiers.AccConstant, 0,
						scope, null);
				if (findFields != null) {
					for (IField field : findFields) {
						try {
							IModelElement element = field;
							if (field.getParent() instanceof ISourceModule) {
								element = ((ISourceModule) field.getParent())
										.getElementAt(field.getNameRange()
												.getOffset());
							}
							if (element != null
									&& element.getParent() instanceof ISourceModule) {
								// display constants defined in GLOBAL scope
								childrenList.add(element);
							}
						} catch (ModelException e) {
						}
					}
				}
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