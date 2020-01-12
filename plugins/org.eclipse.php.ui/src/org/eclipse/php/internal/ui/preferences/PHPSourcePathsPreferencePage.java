package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.preferences.PreferencesMessages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.preferences.sourcepath.SourcePathBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class PHPSourcePathsPreferencePage extends PropertyAndPreferencePage {
	private final static String PROPERTY_ID = "org.eclipse.php.ui.propertyPages.PHPSourcePathsPreferencePage"; //$NON-NLS-1$

	private SourcePathBlock fSourceBlock = null;

	@Override
	protected Control createPreferenceContent(Composite composite) {
		noDefaultAndApplyButton();

		IProject project = getProject();
		Control result;
		try {
			if (project == null || !project.isAccessible() || !PHPToolkitUtil.isPHPProject(project)) {
				result = createInvalid(composite);
			} else {
				result = createWith(composite);
			}
		} catch (CoreException e) {
			result = createInvalid(composite);
			Logger.logException(e);
		}
		Dialog.applyDialogFont(result);
		return result;
	}

	private Control createWith(Composite composite) {
		fSourceBlock = new SourcePathBlock(composite, getProject());
		return null;
	}

	private Control createInvalid(Composite composite) {
		Label label = new Label(composite, SWT.LEFT);
		label.setText(PreferencesMessages.BuildPathsPropertyPage_no_script_project_message);

		fSourceBlock = null;
		setValid(true);
		return label;
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return false;
	}

	@Override
	protected String getPreferencePageID() {
		return null;
	}

	@Override
	protected String getPropertyPageID() {
		return PROPERTY_ID;
	}

}
