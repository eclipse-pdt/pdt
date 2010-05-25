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
package org.eclipse.php.internal.ui.editor.templates;

import java.util.*;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.dltk.utils.TextUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;

/**
 * The template's context
 */
public class PhpTemplateContext extends ScriptTemplateContext {

	private static final String DOLLAR = "dollar";
	private static final String DOLLAR_SIGN = "$";
	public static final char BLANK = ' ';
	public static final char TAB = '\t';

	public PhpTemplateContext(ScriptTemplateContextType phpTemplateContextType,
			IDocument document, int offset, int length,
			ISourceModule sourceModule) {
		super(phpTemplateContextType, document, offset, length, sourceModule);
	}

	@Override
	public TemplateBuffer evaluate(Template template)
			throws BadLocationException, TemplateException {
		boolean useTab = getPreferences().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.FORMATTER_USE_TABS);
		if (!useTab) {
			String lengthString = getPreferences().getString(PHPCorePlugin.ID,
					PHPCoreConstants.FORMATTER_INDENTATION_SIZE);
			int length = FormatPreferencesSupport.getInstance()
					.getIndentationSize(null);
			if (lengthString != null && lengthString.trim().length() != 0) {
				try {
					length = Integer.parseInt(lengthString);
				} catch (Exception e) {
				}
			}

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				sb.append(" ");
			}
			String newPattern = TextUtils.replace(template.getPattern(), TAB,
					sb.toString());
			template = new Template(template.getName(), template
					.getDescription(), template.getContextTypeId(), newPattern,
					template.isAutoInsertable());

		}
		TemplateBuffer result = super.evaluate(template);
		// 300533: Switching through template keys? in fore and forek template
		// is corrupter
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=300533
		// merge ${dollar}${variable} to single $variable to solve bug 300533
		TemplateVariable[] variables = result.getVariables();
		TemplateVariable dollarVariable = null;
		List<TemplateVariable> nonDollarVariables = new ArrayList<TemplateVariable>();
		for (int i = 0; i < variables.length; i++) {
			if (isDollar(variables[i])) {
				dollarVariable = variables[i];
			} else {
				nonDollarVariables.add(variables[i]);
			}
		}
		List<TemplateVariable> templateVariables = new ArrayList<TemplateVariable>();
		if (dollarVariable != null) {
			Set<Integer> dollarOffsetSet = new HashSet<Integer>();
			for (int i = 0; i < dollarVariable.getOffsets().length; i++) {
				dollarOffsetSet.add(dollarVariable.getOffsets()[i]);
			}
			for (Iterator iterator = nonDollarVariables.iterator(); iterator
					.hasNext();) {
				TemplateVariable templateVariable = (TemplateVariable) iterator
						.next();
				if (templateVariable.getOffsets().length > 0
						&& isbehind(templateVariable, dollarOffsetSet)) {
					int[] offsets = new int[templateVariable.getOffsets().length];
					for (int i = 0; i < templateVariable.getOffsets().length; i++) {
						dollarOffsetSet
								.remove(templateVariable.getOffsets()[i] - 1);
						offsets[i] = templateVariable.getOffsets()[i] - 1;
					}
					String name = DOLLAR_SIGN + templateVariable.getName();
					String defaultValue = name;
					if (templateVariable.getDefaultValue() != null) {
						defaultValue = DOLLAR_SIGN
								+ templateVariable.getDefaultValue();
					}
					templateVariable = new TemplateVariable(templateVariable
							.getVariableType(), name, defaultValue, offsets);
				}
				templateVariables.add(templateVariable);
			}
			if (!dollarOffsetSet.isEmpty()) {
				templateVariables.add(dollarVariable);
			}
			result.setContent(result.getString(), templateVariables
					.toArray(new TemplateVariable[templateVariables.size()]));
		}
		// end
		return result;
	}

	private boolean isbehind(TemplateVariable templateVariable,
			Set<Integer> dollarOffsetSet) {
		for (int i = 0; i < templateVariable.getOffsets().length; i++) {
			if (!dollarOffsetSet.contains(templateVariable.getOffsets()[i] - 1)) {
				return false;
			}
		}
		return true;
	}

	private boolean isDollar(TemplateVariable templateVariable) {
		return templateVariable.isUnambiguous()
				&& DOLLAR.equals(templateVariable.getType());
	}
}
