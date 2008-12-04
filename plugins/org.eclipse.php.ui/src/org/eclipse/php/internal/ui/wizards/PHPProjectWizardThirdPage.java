package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.php.internal.ui.preferences.includepath.PHPBuildPathsBlock;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;

public class PHPProjectWizardThirdPage extends PHPProjectWizardSecondPage  {


	@Override
	public void initPage() {
		// TODO Auto-generated method stub
	}

	public PHPProjectWizardThirdPage(PHPProjectWizardFirstPage mainPage) {
		super(mainPage);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BuildpathsBlock createBuildpathBlock(IStatusChangeListener listener) {
		return new PHPBuildPathsBlock(new BusyIndicatorRunnableContext(), listener, 0, useNewSourcePage(), null);
	}

	@Override
	protected void updateProject(IProgressMonitor monitor) throws CoreException, InterruptedException {
		// TODO Auto-generated method stub
		super.updateProject(monitor);
	}


	
	

}
