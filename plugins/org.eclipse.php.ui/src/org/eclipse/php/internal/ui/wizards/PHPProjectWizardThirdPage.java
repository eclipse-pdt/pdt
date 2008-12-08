package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.preferences.includepath.PHPBuildPathsBlock;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;

public class PHPProjectWizardThirdPage extends PHPProjectWizardSecondPage {

	@Override
	public void initPage() {
	}

	public PHPProjectWizardThirdPage(PHPProjectWizardFirstPage mainPage) {
		super(mainPage);
	}

	@Override
	protected BuildpathsBlock createBuildpathBlock(IStatusChangeListener listener) {
		PHPBuildPathsBlock buildPathsBlock = new PHPBuildPathsBlock(new BusyIndicatorRunnableContext(), listener, 0, useNewSourcePage(), null);
		return buildPathsBlock;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			try {
				updateProject(new NullProgressMonitor());
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	@Override
	protected void updateProject(IProgressMonitor monitor) throws CoreException, InterruptedException {
		fCurrProject = fFirstPage.getProjectHandle();
		IScriptProject scriptProject = DLTKCore.create(fCurrProject);
		if (scriptProject != null) {
			init(scriptProject, scriptProject.getRawBuildpath(), true);
		} else {
			throw (new IllegalStateException());
		}
	}

	@Override
	public void performFinish(IProgressMonitor monitor) throws CoreException, InterruptedException {
		if (getContainer().getCurrentPage() == this) {
			BuildpathsBlock fBuildPathsBlock = getBuildPathsBlock();
			if (fBuildPathsBlock.hasChangesInDialog()) {
				fBuildPathsBlock.configureScriptProject(monitor);
			}
		}
	}

}
