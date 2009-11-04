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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.preferences.includepath.PHPBuildPathsBlock;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

/**
 * New PHP Project Wizard - 3rd page - Build Paths configuration Consists of the
 * 2nd page (IncludePath) most functionality
 * 
 * @author nir.c
 * 
 */
public class PHPProjectWizardThirdPage extends PHPProjectWizardSecondPage {

	public PHPProjectWizardThirdPage(PHPProjectWizardFirstPage mainPage) {
		super(mainPage);
	}

	@Override
	protected BuildpathsBlock createBuildpathBlock(
			IStatusChangeListener listener) {
		PHPBuildPathsBlock buildPathsBlock = new PHPBuildPathsBlock(
				new BusyIndicatorRunnableContext(), listener, 0,
				useNewSourcePage(), null);
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
	protected void updateProject(IProgressMonitor monitor)
			throws CoreException, InterruptedException {
		IProject projectHandle = fFirstPage.getProjectHandle();
		IScriptProject scriptProject = DLTKCore.create(projectHandle);
		if (scriptProject != null) {
			IBuildpathEntry[] rawBuildpath = new IBuildpathEntry[0];
			if (scriptProject.isOpen()) {
				rawBuildpath = scriptProject.getRawBuildpath();
			}
			init(scriptProject, rawBuildpath, true);
		} else {
			throw (new IllegalStateException());
		}
	}

	@Override
	public void performFinish(IProgressMonitor monitor) throws CoreException,
			InterruptedException {
		if (getContainer().getCurrentPage() == this) {
			BuildpathsBlock fBuildPathsBlock = getBuildPathsBlock();
			if (fBuildPathsBlock.hasChangesInDialog()) {
				fBuildPathsBlock.configureScriptProject(monitor);
			}
		}
	}

	@Override
	protected void setHelpContext(Control control) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control,
				IPHPHelpContextIds.CONFIGURING_BUILD_PATHS);
	}

}
