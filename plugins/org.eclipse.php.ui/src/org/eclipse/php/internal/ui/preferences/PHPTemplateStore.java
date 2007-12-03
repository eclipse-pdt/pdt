/**
 * Copyright (c) 2006 Zend Technologies
 *
 */
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.*;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;

/**
 * @author seva
 *
 * Added to fix bug #141301. May be fixed in Eclipse in the future.
 *
 */
public class PHPTemplateStore extends ContributionTemplateStore {

	public PHPTemplateStore(ContextTypeRegistry registry, IPreferenceStore store, String key) {
		super(registry, store, key);
	}

	public void add(TemplatePersistenceData data) {
		Template template = data.getTemplate();
		if (template.getName().equals("")) { //$NON-NLS-1$
			String title = PHPUIMessages.getString("PHPTemplateStore_error_title");
			String message = PHPUIMessages.getString("PHPTemplateStore_error_message_nameEmpty");
			MessageDialog.openError(Display.getCurrent().getActiveShell(), title, message);
			return;
		}

		super.add(data);
	}

	public static CompiledTemplate compileTemplate(ContextTypeRegistry contextTypeRegistry, Template template) {
		String string = null;
		int offset = 0;
		if (template != null) {
			IDocument document = new Document();
			TemplateContext context = new DocumentTemplateContext(contextTypeRegistry.getContextType(template.getContextTypeId()), document, 0, 0);
			try {
				TemplateBuffer buffer = context.evaluate(template);
				string = buffer.getString();
				TemplateVariable[] variables = buffer.getVariables();
				for (int i = 0; i != variables.length; i++) {
					TemplateVariable variable = variables[i];
					if ("cursor".equals(variable.getName())) {
						offset = variable.getOffsets()[0];
					}
				}

			} catch (Exception e) {
				Logger.log(Logger.WARNING_DEBUG, "Could not create template for new PHP", e); //$NON-NLS-1$
			}
		}
		return new CompiledTemplate(string, offset);
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
