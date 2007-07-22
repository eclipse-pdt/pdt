package org.eclipse.php.internal.ui.actions;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardRegistry;

public class CreatePHPFileWizardAction extends RunWizardAction {

	public String getWizardId() {
		return "org.eclipse.php.ui.wizards.PHPFileCreationWizard"; //$NON-NLS-1$
	}

	public IWizardRegistry getWizardRegistry() {
		return PlatformUI.getWorkbench().getNewWizardRegistry();
	}
}