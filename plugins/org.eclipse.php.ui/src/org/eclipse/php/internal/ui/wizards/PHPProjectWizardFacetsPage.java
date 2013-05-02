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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ui.wizards.NewElementWizardPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IPreset;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetsChangedEvent;
import org.eclipse.wst.common.project.facet.core.util.AbstractFilter;
import org.eclipse.wst.common.project.facet.core.util.FilterEvent;
import org.eclipse.wst.common.project.facet.core.util.IFilter;
import org.eclipse.wst.common.project.facet.ui.PresetSelectionPanel;

public class PHPProjectWizardFacetsPage extends NewElementWizardPage implements
		IPHPProjectCreateWizardPage {

	private PHPProjectWizardFirstPage fFirstPage;

	private IFacetedProjectWorkingCopy fpjwc;
	private final IFacetedProjectListener fpjwcListener;
	private IFacetedProjectListener fpjwcListenerForPreset;

	private Composite ppanelParent;

	private PresetSelectionPanel ppanel;

	private TabFolder tabFolder;

	private Composite top;

	/**
	 * Constructor for ScriptProjectWizardSecondPage.
	 */
	public PHPProjectWizardFacetsPage(PHPProjectWizardFirstPage mainPage) {
		super("PHPProjectWizardFacetsPage"); //$NON-NLS-1$
		fFirstPage = mainPage;

		this.fpjwcListener = new IFacetedProjectListener() {
			public void handleEvent(final IFacetedProjectEvent event) {
				final Runnable runnable = new Runnable() {
					public void run() {
						validateFacets();
					}
				};

				getContainer().getShell().getDisplay().asyncExec(runnable);
			}
		};
	}

	/**
	 * taken from org.eclipse.wst.web.ui.internal.wizards.
	 * DataModelFacetCreationWizardPage
	 * 
	 * @param top
	 */
	protected void createPresetPanel(Composite top) {
		final IFilter<IPreset> filter = new AbstractFilter<IPreset>() {
			{
				fpjwcListenerForPreset = new IFacetedProjectListener() {
					public void handleEvent(final IFacetedProjectEvent event) {
						handleProjectFacetsChangedEvent((IProjectFacetsChangedEvent) event);
					}
				};
			}

			public boolean check(final IPreset preset) {
				final IProjectFacetVersion primaryFacetVersion = PHPFacets
						.getCoreVersion();
				return preset.getProjectFacets().contains(primaryFacetVersion);
			}

			private void handleProjectFacetsChangedEvent(
					final IProjectFacetsChangedEvent event) {
				for (IProjectFacetVersion fv : event
						.getFacetsWithChangedVersions()) {
					if (fv.getProjectFacet() == PHPFacets.getCoreVersion()) {
						final IFilterEvent<IPreset> filterEvent = new FilterEvent<IPreset>(
								this, IFilterEvent.Type.FILTER_CHANGED);

						notifyListeners(filterEvent);
					}
				}

				checkTabChanges();
			}
		};

		ppanel = new PresetSelectionPanel(top, fpjwc, filter);

		ppanel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void validateFacets() {
		final IStatus status = this.fpjwc.validate();
		if (status != null && !status.isOK()) {
			setErrorMessage(status.getMessage());
		} else {
			setErrorMessage(null);
		}
	}

	private void checkTabChanges() {
		// TODO
	}

	public void createControl(Composite parent) {
		top = createTopLevelComposite(parent);
		addExtendedControls(top);
		setControl(top);
	}

	private void addExtendedControls(Composite top) {
		this.tabFolder = new TabFolder(top, SWT.NONE);
		this.tabFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// invisible as long as there are additional configuration pages
		this.tabFolder.setVisible(false);
	}

	protected Composite createTopLevelComposite(Composite parent) {
		final Composite top = new Composite(parent, SWT.NONE);
		top.setLayout(new GridLayout(1, true));
		top.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.ppanelParent = new Composite(top, SWT.NONE);
		this.ppanelParent.setLayout(new GridLayout());
		this.ppanelParent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return top;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (this.fpjwc == null) {
			// we are coming from first page. Do not do anything. First page
			// will call initPage
			return;
		}

		if (visible) {
			internalInit();
		} else {
			this.fpjwc.removeListener(this.fpjwcListener);
			this.fpjwc.removeListener(this.fpjwcListenerForPreset);

			IWizardPage currentPage = getContainer().getCurrentPage();
			if (currentPage instanceof IPHPProjectCreateWizardPage) {
				// going forward from facets page to 2nd one (or first page)
				((IPHPProjectCreateWizardPage) currentPage).initPage();
			}
		}
	}

	private void internalInit() {
		this.fpjwc.addListener(this.fpjwcListener,
				IFacetedProjectEvent.Type.VALIDATION_PROBLEMS_CHANGED);
		this.fpjwc.addListener(fpjwcListenerForPreset,
				IFacetedProjectEvent.Type.PROJECT_FACETS_CHANGED);
	}

	public void initPage() {
		final IProject project = this.fFirstPage.getProjectHandle();
		try {
			if (this.ppanel != null && !this.ppanel.isDisposed()) {
				ppanel.setVisible(false);
				ppanel.dispose();
			}
			PHPFacets.createFacetedProject(project,
					this.fFirstPage.getPHPVersionValue(),
					new NullProgressMonitor());
			this.fpjwc = ProjectFacetsManager.create(project)
					.createWorkingCopy();
			createPresetPanel(ppanelParent);
			checkTabChanges();
			top.layout(true);
			internalInit();
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}
	}

}
