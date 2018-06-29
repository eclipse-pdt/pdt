/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.wizards.templates;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.IModelElement;

public class TestCaseClassTemplate extends TestCaseTemplate {

	private static class Method {
		protected boolean isStatic;
		protected String name;

		public Method(final String name, final boolean isStatic) {
			this.name = name;
			this.isStatic = isStatic;
		}

		@Override
		public boolean equals(final Object o) {
			if (o instanceof Method) {
				final Method method = (Method) o;
				return name.equals(method.getName()) && isStatic == method.getIsStatic();
			}
			return false;
		}

		public boolean getIsStatic() {
			return isStatic;
		}

		public String getName() {
			return name;
		}
	}

	private static final String METHOD_DYNAMIC_STRUCT = "DynamicMethod"; //$NON-NLS-1$

	private static final String METHOD_NAME = "MethodName"; //$NON-NLS-1$
	private static final String METHOD_NAME_CAMELIZED = "MethodNameCamelized"; //$NON-NLS-1$
	private static final String METHOD_STATIC_STRUCT = "StaticMethod"; //$NON-NLS-1$
	private static final String METHODS_STRUCT = "Methods"; //$NON-NLS-1$

	private static final String TEMPLATE_PATH = "resources/templates/ZendPHPUnitClassTest.tpl.php"; //$NON-NLS-1$

	private ArrayList<Method> methods;

	public void addMethod(final String name, final boolean isStatic) {
		if (methods == null) {
			methods = new ArrayList<>();
		}
		methods.add(new Method(name, isStatic));
	}

	public void compileMethods() {
		extract(INPUT, METHODS_STRUCT, null);

		if (methods == null || methods.isEmpty()) {
			set(METHODS_STRUCT, ""); //$NON-NLS-1$
			return;
		}

		extract(METHODS_STRUCT, METHOD_DYNAMIC_STRUCT, null);
		extract(METHODS_STRUCT, METHOD_STATIC_STRUCT, null);

		set(METHODS_STRUCT, ""); //$NON-NLS-1$

		for (Method method : methods) {
			String methodName = method.getName();
			set(METHOD_NAME, methodName);
			set(METHOD_NAME_CAMELIZED, methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
			compile(method.getIsStatic() ? METHOD_STATIC_STRUCT : METHOD_DYNAMIC_STRUCT, METHODS_STRUCT, true);
		}
	}

	@Override
	protected String getTemplatePath() {
		return TEMPLATE_PATH;
	}

	@Override
	public void setMasterElement(IModelElement masterElement, String masterElementName, IProject project) {
		if (masterElementName != null && masterElementName.length() > 0) {
			set("MasterElementNameVar", //$NON-NLS-1$
					masterElementName.substring(0, 1).toLowerCase() + masterElementName.substring(1));
		} else {
			set("MasterElementNameVar", ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		super.setMasterElement(masterElement, masterElementName, project);
	}
}
