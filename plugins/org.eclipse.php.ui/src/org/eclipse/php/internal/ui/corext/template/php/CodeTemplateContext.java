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
package org.eclipse.php.internal.ui.corext.template.php;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultLineTracker;
import org.eclipse.jface.text.ILineTracker;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.*;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class CodeTemplateContext extends TemplateContext {

	private String fLineDelimiter;
	private IScriptProject fProject;

	public CodeTemplateContext(String contextTypeName, IScriptProject sp,
			String lineDelim) {
		super(getTemplateContextRegistry().getContextType(contextTypeName));

		fLineDelimiter = lineDelim;
		fProject = sp;
	}

	protected static ContextTypeRegistry getTemplateContextRegistry() {
		return PHPUiPlugin.getDefault().getCodeTemplateContextRegistry();
	}

	public IScriptProject getJavaProject() {
		return fProject;
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.corext.template.TemplateContext#evaluate(org
	 * .eclipse.jdt.internal.corext.template.Template)
	 */
	public TemplateBuffer evaluate(Template template)
			throws BadLocationException, TemplateException {
		// test that all variables are defined
		// Iterator iterator = getContextType().resolvers();
		// while (iterator.hasNext()) {
		// TemplateVariableResolver var = (TemplateVariableResolver) iterator
		// .next();
		// if (var instanceof
		// CodeTemplateContextType.CodeTemplateVariableResolver) {
		// Assert.isNotNull(getVariable(var.getType()),
		//						"Variable " + var.getType() + "not defined"); 
		// }
		// }

		if (!canEvaluate(template))
			return null;

		String pattern = template.getPattern();
		if (fLineDelimiter != null) {
			pattern = changeLineDelimiter(pattern, fLineDelimiter);
		}

		TemplateTranslator translator = new TemplateTranslator();
		TemplateBuffer buffer = translator.translate(pattern);
		getContextType().resolve(buffer, this);
		return buffer;
	}

	private static String changeLineDelimiter(String code, String lineDelim) {
		try {
			ILineTracker tracker = new DefaultLineTracker();
			tracker.set(code);
			int nLines = tracker.getNumberOfLines();
			if (nLines == 1) {
				return code;
			}

			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < nLines; i++) {
				if (i != 0) {
					buf.append(lineDelim);
				}
				IRegion region = tracker.getLineInformation(i);
				String line = code.substring(region.getOffset(), region
						.getOffset()
						+ region.getLength());
				buf.append(line);
			}
			return buf.toString();
		} catch (BadLocationException e) {
			// can not happen
			return code;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.corext.template.TemplateContext#canEvaluate(
	 * org.eclipse.jdt.internal.corext.template.Template)
	 */
	public boolean canEvaluate(Template template) {
		return true;
	}

	/*
	 * public void setCompilationUnitVariables(ICompilationUnit cu) {
	 * setVariable(CodeTemplateContextType.FILENAME, cu.getElementName());
	 * setVariable(CodeTemplateContextType.PACKAGENAME,
	 * cu.getParent().getElementName());
	 * setVariable(CodeTemplateContextType.PROJECTNAME,
	 * cu.getJavaProject().getElementName()); }
	 */
}
