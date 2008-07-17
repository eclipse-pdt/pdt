/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.template.php;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

import org.eclipse.jdt.core.IJavaProject;

import org.eclipse.jdt.internal.corext.codemanipulation.StubUtility;


/**
 *  Variable resolver for variable <code>exception_variable_name</code>. Resolves to
 *  an unused exception name.
 * 
 *  @since 3.4
 */
public class ExceptionVariableNameResolver extends TemplateVariableResolver {

	protected String[] resolveAll(TemplateContext context) {
		if (context instanceof PhpContext) {
			PhpContext jc= (PhpContext) context;
			IJavaProject javaProject= jc.getJavaProject();
			String exceptionVariableName= StubUtility.getExceptionVariableName(javaProject);
			return StubUtility.getLocalNameSuggestions(jc.getJavaProject(), exceptionVariableName, 0, jc.computeExcludes());
		}
		return new String[0];
	}

}
