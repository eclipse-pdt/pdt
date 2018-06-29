/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;
import org.eclipse.php.internal.debug.ui.views.coverage.CodeCoverageSection;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.engine.IProfileSessionListener;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * Code coverage summary view.
 */
public class CodeCoverageSummaryView extends AbstractProfilerView implements IProfileSessionListener {

	private CodeCoverageSection fCodeCoverageSection;
	private ProfilerDB input;

	public CodeCoverageSummaryView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		createCodeCoverageForm(parent);
		ProfileSessionsManager.addProfileSessionListener(this);
		setInput(ProfileSessionsManager.getCurrent());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.CODE_COVERAGE_SUMMARY_VIEW);
	}

	private ViewForm createCodeCoverageForm(final Composite parent) {
		final ViewForm codeCoverageForm = new ViewForm(parent, SWT.NONE);
		fCodeCoverageSection = new CodeCoverageSection(codeCoverageForm, this, null /* codeCoverageToolBar */);
		codeCoverageForm.setContent(fCodeCoverageSection.getComposite());
		return codeCoverageForm;
	}

	public void showCodeCoverage(final CodeCoverageData[] coveredFiles) {
		Display.getCurrent().asyncExec(new Runnable() {
			@Override
			public void run() {
				fCodeCoverageSection.showCodeCoverage(coveredFiles);
			}
		});
	}

	@Override
	public void setFocus() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		fCodeCoverageSection.dispose();
	}

	@Override
	public ProfilerDB getInput() {
		return input;
	}

	@Override
	public void setInput(ProfilerDB profilerDB) {
		if (profilerDB != null) {
			List<CodeCoverageData> codeCoverageDatas = new ArrayList<>();
			ProfilerFileData[] files = profilerDB.getFiles();
			for (int i = 0; i < files.length; i++) {
				CodeCoverageData codeCoverageData = files[i].getCodeCoverageData();
				if (codeCoverageData != null) {
					codeCoverageDatas.add(codeCoverageData);
				}
			}
			showCodeCoverage(codeCoverageDatas.toArray(new CodeCoverageData[codeCoverageDatas.size()]));
		} else {
			showCodeCoverage(null);
		}
		this.input = profilerDB;
	}

	@Override
	public void currentSessionChanged(final ProfilerDB current) {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				setInput(current);
			}
		});
	}

	@Override
	public void profileSessionAdded(ProfilerDB db) {
	}

	@Override
	public void profileSessionRemoved(ProfilerDB db) {
	}
}
