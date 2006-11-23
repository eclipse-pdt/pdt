package org.eclipse.php.ui.wizards;

import org.eclipse.core.resources.IProject;

public abstract class BasicPHPWizardPageExtended extends BasicPHPWizardPage {

	protected BasicPHPWizardPageExtended(String pageName) {
		super(pageName);
	}
	
	public void prePerformFinish(){};
	public void performFinish(){};
	public void postPerformFinish(IProject project){};
	

}
