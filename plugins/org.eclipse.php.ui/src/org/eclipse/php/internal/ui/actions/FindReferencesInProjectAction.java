/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.ui.search.DLTKSearchScopeFactory;
import org.eclipse.dltk.internal.ui.search.SearchMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.search.ElementQuerySpecification;
import org.eclipse.dltk.ui.search.QuerySpecification;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IWorkbenchSite;

/**
 * Finds references to the selected element in the enclosing project of the
 * selected element. The action is applicable to selections representing a
 * Script element.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 */
public class FindReferencesInProjectAction extends FindReferencesAction {

	/**
	 * Creates a new <code>FindReferencesInProjectAction</code>. The action
	 * requires that the selection provided by the site's selection provider is
	 * of type <code>IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the site providing context information for this action
	 */
	public FindReferencesInProjectAction(IWorkbenchSite site) {
		super(site);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor
	 *            the Script editor
	 */
	public FindReferencesInProjectAction(PHPStructuredEditor editor) {
		super(editor);
	}

	@SuppressWarnings("rawtypes")
	Class[] getValidTypes() {
		return new Class[] { IField.class, IMethod.class, IType.class,
				ISourceModule.class, IPackageDeclaration.class,
				IScriptFolder.class, ILocalVariable.class };
	}

	void init() {
		setText(SearchMessages.Search_FindReferencesInProjectAction_label);
		setToolTipText(
				SearchMessages.Search_FindReferencesInProjectAction_tooltip);
		setImageDescriptor(DLTKPluginImages.DESC_OBJS_SEARCH_REF);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IJavaHelpContextIds.FIND_REFERENCES_IN_PROJECT_ACTION);
		if (DLTKCore.DEBUG) {
			System.out.println("TODO: Add help support here..."); //$NON-NLS-1$
		}
	}

	QuerySpecification createQuery(IModelElement element)
			throws ModelException {
		DLTKSearchScopeFactory factory = DLTKSearchScopeFactory.getInstance();
		PHPStructuredEditor editor = getEditor();

		IDLTKSearchScope scope;
		String description;
		boolean isInsideInterpreterEnvironment = factory
				.isInsideInterpreter(element);
		if (editor != null) {
			scope = factory.createProjectSearchScope(editor.getEditorInput(),
					isInsideInterpreterEnvironment);
			description = factory.getProjectScopeDescription(
					editor.getEditorInput(), isInsideInterpreterEnvironment);
		} else {
			scope = factory.createProjectSearchScope(element.getScriptProject(),
					isInsideInterpreterEnvironment);
			description = factory.getProjectScopeDescription(
					element.getScriptProject(), isInsideInterpreterEnvironment);
		}
		return new ElementQuerySpecification(element, getLimitTo(), scope,
				description);
	}

}
