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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.*;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;

/**
 * @author seva
 * 
 *         Added to fix bug #141301. May be fixed in Eclipse in the future.
 * 
 */
public class PHPTemplateStore extends ContributionTemplateStore {

	public PHPTemplateStore(ContextTypeRegistry registry,
			IPreferenceStore store, String key) {
		super(registry, store, key);
	}

	public void add(TemplatePersistenceData data) {
		Template template = data.getTemplate();
		if (template.getName().equals("")) { //$NON-NLS-1$
			String title = PHPUIMessages.PHPTemplateStore_error_title;
			String message = PHPUIMessages.PHPTemplateStore_error_message_nameEmpty;
			MessageDialog.openError(Display.getCurrent().getActiveShell(),
					title, message);
			return;
		}

		super.add(data);
	}

	public static CompiledTemplate compileTemplate(
			ContextTypeRegistry contextTypeRegistry, Template template,
			String containerName, String fileName) {
		return compileTemplate(contextTypeRegistry, template, containerName,
				fileName, null);
	}

	public static CompiledTemplate compileTemplate(
			ContextTypeRegistry contextTypeRegistry, Template template,
			String containerName, String fileName, String lineDelimiter) {
		String string = null;
		int offset = 0;
		if (template != null) {
			IDocument document = new Document();
			DocumentTemplateContext context = getContext(contextTypeRegistry,
					template, containerName, fileName, document);
			if (context instanceof PhpTemplateContext) {
				PhpTemplateContext phpTemplateContext = (PhpTemplateContext) context;
				phpTemplateContext.setLineDelimiter(lineDelimiter);
			}
			TemplateBuffer buffer = null;
			try {
				buffer = context.evaluate(template);
			} catch (BadLocationException e) {
				PHPUiPlugin.log(e);
			} catch (TemplateException e) {
				PHPUiPlugin.log(e);
			}
			if (buffer != null) {
				string = buffer.getString();
				TemplateVariable[] variables = buffer.getVariables();
				for (int i = 0; i != variables.length; i++) {
					TemplateVariable variable = variables[i];
					if ("cursor".equals(variable.getName())) { //$NON-NLS-1$
						offset = variable.getOffsets()[0];
					}
				}
			}
		}
		return new CompiledTemplate(string, offset);
	}

	public static CompiledTemplate compileTemplate(
			ContextTypeRegistry contextTypeRegistry, Template template) {
		return compileTemplate(contextTypeRegistry, template, null, null);
	}

	public static CompiledTemplate compileTemplate(
			ContextTypeRegistry contextTypeRegistry, Template template,
			String lineDelimiter) {
		return compileTemplate(contextTypeRegistry, template, null, null,
				lineDelimiter);
	}

	/**
	 * @param contextTypeRegistry
	 * @param template
	 * @param containerName
	 * @param fileName
	 * @param document
	 * @return
	 */
	private static DocumentTemplateContext getContext(
			ContextTypeRegistry contextTypeRegistry, Template template,
			String containerName, String fileName, IDocument document) {

		if (fileName == null) {
			return new DocumentTemplateContext(
					contextTypeRegistry.getContextType(template
							.getContextTypeId()), document, 0, 0);

		}

		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(containerName + "/" + fileName)); //$NON-NLS-1$
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		TemplateContextType type = contextTypeRegistry.getContextType(template
				.getContextTypeId());
		return ((ScriptTemplateContextType) type).createContext(document, 0, 0,
				sourceModule);
	}

	public static class CompiledTemplate {
		public final String string;
		public final int offset;

		public CompiledTemplate(String string, int offset) {
			this.string = string;
			this.offset = offset;
		}
	}

}
