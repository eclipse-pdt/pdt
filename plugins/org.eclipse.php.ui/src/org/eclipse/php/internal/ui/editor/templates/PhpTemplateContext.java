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

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.dltk.utils.TextUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;

/**
 * The template's context
 */
public class PhpTemplateContext extends ScriptTemplateContext {

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
		return result;
	}
}
