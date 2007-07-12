/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.preferences;

import java.text.MessageFormat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
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
			String title = PHPUIMessages.PHPTemplateStore_error_title;
			String message = PHPUIMessages.PHPTemplateStore_error_message_nameEmpty;
			MessageDialog.openError(Display.getCurrent().getActiveShell(), title, message);
			return;
		}

		super.add(data);
	}

}
