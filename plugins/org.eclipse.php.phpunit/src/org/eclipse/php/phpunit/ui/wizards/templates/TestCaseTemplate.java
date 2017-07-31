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
package org.eclipse.php.phpunit.ui.wizards.templates;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.ui.preferences.includepath.IncludePathUtils;

abstract public class TestCaseTemplate extends TestTemplate {

	private static final String MASTER_ELEMENT_NAME = "MasterElementName"; //$NON-NLS-1$
	private static final String MASTER_ELEMENT_STATIC_METHOD = "MasterElementStaticMethod"; //$NON-NLS-1$
	private static final String MASTER_ELEMENT_DYNAMIC_METHOD = "MasterElementDynamicMethod"; //$NON-NLS-1$
	private static final String MASTER_ELEMENT_DESTRUCTOR = "MasterElementDestructor"; //$NON-NLS-1$
	private static final String MASTER_ELEMENT_CALL = "MasterElementCall"; //$NON-NLS-1$
	private static final String MASTER_ELEMENT_CONSTRUCTOR = "MasterElementConstructor"; //$NON-NLS-1$
	private static final String MASTER_ELEMENT_DEFINITION = "MasterElementDefinition"; //$NON-NLS-1$

	@SuppressWarnings("null")
	public void setMasterElement(final IModelElement masterElement, final String masterElementName,
			final IProject project) {
		final boolean empty = masterElement != null;
		if (empty) {
			final IScriptProject sProject = DLTKCore.create(project);
			final IPath relativeLocationFromIncludePath = IncludePathUtils.getRelativeLocationFromIncludePath(sProject,
					masterElement);
			String path = null;
			if (!relativeLocationFromIncludePath.isEmpty()) {
				path = relativeLocationFromIncludePath.toOSString();
			}
			setMasterElementParams(masterElement.getElementName(), path);
		} else {
			setMasterElementParams(masterElementName, null);
		}
	}

	void setMasterElementParams(final String name, final String relativeLocation) {
		extract(INPUT, MASTER_ELEMENT_DEFINITION, MASTER_ELEMENT_DEFINITION);
		extract(INPUT, MASTER_ELEMENT_CONSTRUCTOR, MASTER_ELEMENT_CONSTRUCTOR);
		extract(INPUT, MASTER_ELEMENT_CALL, MASTER_ELEMENT_CALL);
		extract(INPUT, MASTER_ELEMENT_DESTRUCTOR, MASTER_ELEMENT_DESTRUCTOR);
		extract(INPUT, MASTER_ELEMENT_DYNAMIC_METHOD, MASTER_ELEMENT_DYNAMIC_METHOD);
		extract(INPUT, MASTER_ELEMENT_STATIC_METHOD, MASTER_ELEMENT_STATIC_METHOD);
		if ("".equals(name)) { //$NON-NLS-1$
			set(MASTER_ELEMENT_DEFINITION, ""); //$NON-NLS-1$
			set(MASTER_ELEMENT_CONSTRUCTOR, ""); //$NON-NLS-1$
			set(MASTER_ELEMENT_CALL, ""); //$NON-NLS-1$
			set(MASTER_ELEMENT_DESTRUCTOR, ""); //$NON-NLS-1$
			set(MASTER_ELEMENT_DYNAMIC_METHOD, ""); //$NON-NLS-1$
			set(MASTER_ELEMENT_STATIC_METHOD, ""); //$NON-NLS-1$
		} else {
			compile(MASTER_ELEMENT_DEFINITION, MASTER_ELEMENT_DEFINITION, false);
			compile(MASTER_ELEMENT_CONSTRUCTOR, MASTER_ELEMENT_CONSTRUCTOR, false);
			compile(MASTER_ELEMENT_CALL, MASTER_ELEMENT_CALL, false);
			compile(MASTER_ELEMENT_DESTRUCTOR, MASTER_ELEMENT_DESTRUCTOR, false);
			compile(MASTER_ELEMENT_DYNAMIC_METHOD, MASTER_ELEMENT_DYNAMIC_METHOD, false);
			compile(MASTER_ELEMENT_STATIC_METHOD, MASTER_ELEMENT_STATIC_METHOD, false);
		}
		set(MASTER_ELEMENT_NAME, name);
		if (relativeLocation != null)
			addRequire(relativeLocation);
	}
}
