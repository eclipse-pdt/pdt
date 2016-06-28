/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.composer.core.*;
import org.eclipse.php.composer.core.PackageVersion.Suffix;
import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.core.validation.VersionConstraintValidator;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.progress.UIJob;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class AddDependencyPage extends WizardPage {

	private static final int MATCHES_TYPE = 0;
	private static final int RANGE_TYPE = 1;
	private static final String DEV_MASTER_VERSION = "dev-master"; //$NON-NLS-1$

	private Composite versionsStackComposite;
	private StackLayout versionsStack;
	private Combo versionTypeCombo;

	private Composite matchesContainer;
	private Combo minConstraintCombo;
	private Combo maxConstraintCombo;
	private Combo maxCombo;
	private Combo minCombo;

	private Composite equalsContainer;
	private ComboViewer equalsConstraintCombo;
	private Combo equalsCombo;

	private Combo typeCombo;

	private Text searchText;
	private TableViewer resultViewer;

	private List<IPackage> existingPackages;
	private IPackage selectedPackage;
	private IPackageSearchService searchService;

	private DependencySearchContentProvider contentProvider;
	private UIJob searchJob;

	private Boolean inProgress = false;

	/**
	 * Create the wizard.
	 * 
	 * @param serachService
	 * @param existingPackages
	 * @param selectedPackage
	 */
	public AddDependencyPage(IPackageSearchService serachService,
			java.util.List<IPackage> existingPackages, IPackage selectedPackage) {
		super("addDependencyPage"); //$NON-NLS-1$
		setImageDescriptor(ComposerUIPlugin
				.getImageDescriptor(ComposerUIPlugin.IMAGE_DEPENDENCY_WIZ));
		if (selectedPackage == null) {
			setTitle(Messages.AddDependencyPage_Title);
			setDescription(Messages.AddDependencyPage_Desc);
		} else {
			setTitle(Messages.AddDependencyPage_ModifyTitle);
			setDescription(Messages.AddDependencyPage_ModifyDesc);
		}
		this.searchService = serachService;

		this.existingPackages = existingPackages;
		this.selectedPackage = selectedPackage;
		if (selectedPackage != null) {
			existingPackages.remove(selectedPackage);
		}
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		createSearchGroup(container);

		createTypeGroup(container);

		createVersionsGroup(container);

		updateDetailsEnablement(false);

		initializeValues();
		initalizeListeners();

		setPageComplete(validatePage());
	}

	private void initializeValues() {
		if (selectedPackage != null) {
			String versionConstraint = selectedPackage.getVersionConstraint();
			searchText.setEnabled(false);
			contentProvider.getPackages().add(selectedPackage);
			refreshSearchViewer();
			resultViewer.getTable().select(0);

			// System.out.println(versionConstraint);
			String[] typeValues = typeCombo.getItems();
			String type = selectedPackage.getType().getName();
			for (int i = 0; i < typeValues.length; i++) {
				if (typeValues[i].equals(type)) {
					typeCombo.select(i);
					break;
				}
			}

			equalsCombo.removeAll();
			minCombo.removeAll();
			maxCombo.removeAll();

			java.util.List<String> versions = selectedPackage.getVersions();
			if (versions != null) {
				for (String version : versions) {
					// e.g. * 1.0.0 - remove "* " prefix
					if (version.startsWith("* ")) { //$NON-NLS-1$
						version = version.substring(2);
					}
					equalsCombo.add(version);
					minCombo.add(version);
					maxCombo.add(version);
				}
				if (versions.size() > 0) {
					equalsCombo.select(0);
					minCombo.select(0);
					maxCombo.select(0);
				}
			}

			updateVersionGroup(versionConstraint);
			updateDetailsEnablement(true);
		}
	}

	private void createTypeGroup(Composite container) {
		Composite typeContainer = new Composite(container, SWT.NONE);
		typeContainer.setLayout(new GridLayout(2, false));
		typeContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));

		Label typeLabel = new Label(typeContainer, SWT.NONE);
		typeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		typeLabel.setText(Messages.AddDependencyPage_Type);

		typeCombo = new Combo(typeContainer, SWT.READ_ONLY);
		typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		PackageType[] types = PackageType.values();
		for (PackageType type : types) {
			typeCombo.add(type.getName());
		}
		typeCombo.select(0);
	}

	private void createVersionsGroup(Composite container) {
		Group versionGroup = new Group(container, SWT.NONE);
		versionGroup.setLayout(new GridLayout(2, false));
		versionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 2, 1));
		versionGroup.setText(Messages.AddDependencyPage_VersionGroup);

		versionTypeCombo = new Combo(versionGroup, SWT.READ_ONLY);
		versionTypeCombo.setItems(new String[] {
				Messages.AddDependencyPage_Equals,
				Messages.AddDependencyPage_Matches });
		versionTypeCombo.select(MATCHES_TYPE);
		versionTypeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (versionTypeCombo.getSelectionIndex() == MATCHES_TYPE) {
					versionsStack.topControl = equalsContainer;
				} else {
					versionsStack.topControl = matchesContainer;
				}
				versionsStackComposite.layout();
			}
		});

		versionsStackComposite = new Composite(versionGroup, SWT.NONE);
		versionsStack = new StackLayout();
		versionsStackComposite.setLayout(versionsStack);
		versionsStackComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, true, 1, 1));

		equalsContainer = new Composite(versionsStackComposite, SWT.NONE);
		equalsContainer.setLayout(new GridLayout(3, false));

		Label equalsLabel = new Label(equalsContainer, SWT.NONE);
		equalsLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		equalsLabel.setText(Messages.AddDependencyPage_EqualsVersion);

		equalsConstraintCombo = new ComboViewer(equalsContainer, SWT.READ_ONLY);
		equalsConstraintCombo.setContentProvider(ArrayContentProvider
				.getInstance());
		equalsConstraintCombo.setLabelProvider(new LabelProvider());
		equalsConstraintCombo.setInput(VersionConstraint.values());
		equalsConstraintCombo.setSelection(new StructuredSelection(
				VersionConstraint.NONE));
		equalsConstraintCombo.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		equalsCombo = new Combo(equalsContainer, SWT.NONE);
		equalsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		equalsCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (equalsCombo.getText().equals(DEV_MASTER_VERSION)) {
					equalsConstraintCombo.setSelection(new StructuredSelection(
							VersionConstraint.NONE));
				}
			}
		});

		versionsStack.topControl = equalsContainer;
		versionsStackComposite.layout();

		matchesContainer = new Composite(versionsStackComposite, SWT.NONE);
		matchesContainer.setLayout(new GridLayout(6, false));

		Label minLabel = new Label(matchesContainer, SWT.NONE);
		minLabel.setText(Messages.AddDependencyPage_MinMatches);

		minConstraintCombo = new Combo(matchesContainer, SWT.READ_ONLY);
		fillRangeConstraints(minConstraintCombo);
		minConstraintCombo.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.CENTER, false, false, 1, 1));

		minCombo = new Combo(matchesContainer, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 100;
		minCombo.setLayoutData(gridData);

		Label maxLabel = new Label(matchesContainer, SWT.NONE);
		maxLabel.setText(Messages.AddDependencyPage_MaxMatches);

		maxConstraintCombo = new Combo(matchesContainer, SWT.READ_ONLY);
		fillRangeConstraints(maxConstraintCombo);
		maxConstraintCombo.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.CENTER, false, false, 1, 1));

		maxCombo = new Combo(matchesContainer, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 100;
		maxCombo.setLayoutData(gridData);
	}

	private void initalizeListeners() {
		ModifyListener listener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				setPageComplete(validatePage());
			}
		};

		equalsConstraintCombo.getCombo().addModifyListener(listener);
		equalsCombo.addModifyListener(listener);
		minCombo.addModifyListener(listener);
		maxCombo.addModifyListener(listener);
	}

	private void updateDetailsEnablement(boolean enabled) {
		typeCombo.setEnabled(enabled);
		versionTypeCombo.setEnabled(enabled);
		equalsConstraintCombo.getControl().setEnabled(enabled);
		equalsCombo.setEnabled(enabled);
		maxConstraintCombo.setEnabled(enabled);
		minCombo.setEnabled(enabled);
		maxCombo.setEnabled(enabled);
	}

	private void fillRangeConstraints(Combo combo) {
		VersionConstraint[] values = VersionConstraint.values();
		for (VersionConstraint r : values) {
			if (r == VersionConstraint.NONE || r == VersionConstraint.APPROX) {
				continue;
			}
			combo.add(r.getSymbol());
		}
		combo.select(0);
	}

	private void createSearchGroup(Composite container) {
		searchText = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.SEARCH
				| SWT.ICON_CANCEL);
		searchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		searchText.setFocus();

		searchJob = new UIJob(container.getDisplay(), "Search UI Job") { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				doSearch();
				return Status.OK_STATUS;
			}
		};
		searchJob.setSystem(true);
		searchJob.setUser(false);

		searchText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				applyFilter();
			}
		});
		searchText.addVerifyListener(new VerifyListener() {

			@Override
			public void verifyText(VerifyEvent arg0) {
				if (inProgress) {
					arg0.doit = false;
				}
			}
		});

		this.contentProvider = new DependencySearchContentProvider(
				searchService) {
			@Override
			protected void notifyEndOfList() {
				getShell().getDisplay().asyncExec(new Runnable() {

					@Override
					public void run() {
						doSearchNextPage();
					}
				});
			}
		};

		resultViewer = new TableViewer(container, SWT.SINGLE | SWT.BORDER
				| SWT.VIRTUAL);
		resultViewer.setContentProvider(contentProvider);
		IStyledLabelProvider lp = new DependencySearchLabelProvider();
		resultViewer
				.setLabelProvider(new DelegatingStyledCellLabelProvider(lp));
		resultViewer.setUseHashlookup(true);
		resultViewer.setInput(Collections.emptyList());

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd.heightHint = 250;
		gd.widthHint = 200;
		resultViewer.getControl().setLayoutData(gd);
		resultViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) resultViewer
								.getSelection();
						if (selection.isEmpty()
								|| contentProvider.getNumberOfElements() == 0) {
							updateDetailsEnablement(false);
							return;
						}
						selectedPackage = (IPackage) selection
								.getFirstElement();
						updateDetailsEnablement(true);
						updateDetails();
						setPageComplete(validatePage());
					}
				});
	}

	private void applyFilter() {
		if (!searchText.isEnabled()) {
			return;
		}
		searchJob.cancel();

		searchJob.schedule(400);
	}

	private void doSearch() {
		if (searchText == null || searchText.isDisposed()) {
			return;
		}

		final String patterValue = searchText.getText();
		resultViewer.getTable().deselectAll();

		IRunnableWithProgress searchTask = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				try {
					monitor.beginTask(Messages.AddDependencyPage_SearchingJob,
							IProgressMonitor.UNKNOWN);

					contentProvider.search(patterValue, monitor);
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			run(searchTask);
			refreshSearchViewer();

			setErrorMessage(null);

			updateDetailsEnablement(false);
			if (resultViewer.getTable().getItemCount() == 0) {
				setErrorMessage(Messages.AddDependencyPage_NotFoundError);
				setPageComplete(false);
			}
		} catch (InvocationTargetException e) {
			ComposerUIPlugin.logError(e.getCause());
			setErrorMessage(Messages.AddDependencyPage_SearchError);
			setPageComplete(false);
		} catch (InterruptedException e) {
			ComposerUIPlugin.logError(e);
		}
	}

	private void doSearchNextPage() {
		if (searchText.getText().isEmpty() || !contentProvider.hasNextPage()) {
			return;
		}

		IRunnableWithProgress nextPageTask = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				try {
					monitor.beginTask(Messages.AddDependencyPage_SearchingJob,
							IProgressMonitor.UNKNOWN);

					contentProvider.nextPage(monitor);
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			run(nextPageTask);
		} catch (InvocationTargetException e) {
			ComposerUIPlugin.logError(e.getCause());
			setErrorMessage(Messages.AddDependencyPage_SearchError);
		} catch (InterruptedException e) {
			ComposerUIPlugin.logError(e);
		}

		refreshSearchViewer();
	}

	/**
	 * Refresh viewer in lazy way.
	 */
	protected void refreshSearchViewer() {
		resultViewer.setItemCount(contentProvider.getNumberOfElements());
		resultViewer.refresh();
	}

	private void updateDetails() {
		try {
			IRunnableWithProgress updateTask = new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					try {
						monitor.beginTask(
								Messages.AddDependencyPage_DetailsJob,
								IProgressMonitor.UNKNOWN);
						String versionConstraint = selectedPackage
								.getVersionConstraint();
						selectedPackage = searchService.getPackageDetails(
								selectedPackage.getName(), monitor);
						if (selectedPackage != null) {
							selectedPackage
									.setVersionConstraint(versionConstraint);
						}
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					} finally {
						monitor.done();
					}
				}
			};
			run(updateTask);
			final java.util.List<String> versions = selectedPackage
					.getVersions();

			selectedPackage.setType(PackageType.byName(typeCombo.getText()));

			equalsCombo.removeAll();
			minCombo.removeAll();
			maxCombo.removeAll();
			int toSelect = -1;
			for (int i = 0; i < versions.size(); i++) {
				String version = versions.get(i);
				PackageVersion v = PackageVersion.byName(version);
				if (toSelect == -1 && v.getSuffix() == Suffix.NONE
						&& v.getMajor() >= 0 && v.getMinor() >= 0
						&& v.getBuild() >= 0) {
					toSelect = i;
				}
				// e.g. * 1.0.0 - remove "* " prefix
				if (version.startsWith("* ")) { //$NON-NLS-1$
					version = version.substring(2);
				}
				equalsCombo.add(version);
				minCombo.add(version);
				maxCombo.add(version);

				setProposedConstraint(version);
			}
			if (versions.size() > 0) {
				if (toSelect == -1) {
					toSelect = 0;
				}
				equalsCombo.select(toSelect);
				minCombo.select(toSelect);
				maxCombo.select(toSelect);
			}

			updateVersionGroup(selectedPackage.getVersionConstraint());

			setErrorMessage(null);
		} catch (InvocationTargetException e) {
			ComposerUIPlugin.logError(e.getCause());
			setErrorMessage(Messages.AddDependencyPage_DetaislError);
			setPageComplete(false);
			updateDetailsEnablement(false);
		} catch (InterruptedException e) {
			ComposerUIPlugin.logError(e);
		}
	}

	private void setProposedConstraint(String version) {
		IStructuredSelection selection = (IStructuredSelection) equalsConstraintCombo
				.getSelection();
		selection = new StructuredSelection(VersionConstraint.NONE);
		equalsConstraintCombo.setSelection(selection);
	}

	private void updateVersionGroup(String versionConstraint) {
		if (versionConstraint == null) {
			return;
		}
		PackageVersionRange versionRange = PackageVersionRange
				.getRange(versionConstraint);
		if (versionRange.getDownVersion() != null) {
			if (versionRange.getUpVersion() == null) {
				versionTypeCombo.select(MATCHES_TYPE);
				versionsStack.topControl = equalsContainer;
				versionsStackComposite.layout();
				equalsConstraintCombo.setSelection(new StructuredSelection(
						versionRange.getDownRelation()));
				equalsCombo.setText(versionRange.getDownVersion());
			} else {
				versionTypeCombo.select(RANGE_TYPE);
				versionsStack.topControl = matchesContainer;
				versionsStackComposite.layout();
				minConstraintCombo.setText(versionRange.getDownRelation()
						.getSymbol());
				maxConstraintCombo.setText(versionRange.getUpRelation()
						.getSymbol());
				minCombo.setText(versionRange.getDownVersion());
				maxCombo.setText(versionRange.getUpVersion());
			}
		}
	}

	private boolean validatePage() {
		setErrorMessage(null);
		if (selectedPackage != null) {
			if (isAlreadyAdded(selectedPackage)) {
				setErrorMessage(MessageFormat.format(
						Messages.AddDependencyPage_DuplicateError,
						selectedPackage.getName(), selectedPackage.getType()
								.getName()));
			}
			if (versionTypeCombo.getSelectionIndex() == MATCHES_TYPE) {
				VersionConstraintValidator constraintValidator = new VersionConstraintValidator();

				String version = equalsCombo.getText();
				if (version.trim().isEmpty()) {
					setErrorMessage(Messages.AddDependencyPage_EqualsVersionError);
					return false;
				}
				String message = constraintValidator
						.validate(getVersionConstraint());
				if (message != null) {
					setErrorMessage(message);
					return false;
				}
			}
			if (versionTypeCombo.getSelectionIndex() == RANGE_TYPE) {
				String version = minCombo.getText();
				if (version.trim().isEmpty()) {
					setErrorMessage(Messages.AddDependencyPage_MinVersionError);
					return false;
				}
				version = maxCombo.getText();
				if (version.trim().isEmpty()) {
					setErrorMessage(Messages.AddDependencyPage_MaxVersionError);
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	protected IPackage getPackage() {
		selectedPackage.setVersionConstraint(getVersionConstraint());
		selectedPackage.setType(PackageType.byName(typeCombo.getText()));
		return selectedPackage;
	}

	private String getVersionConstraint() {
		StringBuilder versionConstraint = new StringBuilder();
		if (versionTypeCombo.getSelectionIndex() == MATCHES_TYPE) {
			VersionConstraint symbol = VersionConstraint
					.bySymbol(equalsConstraintCombo.getCombo().getText());
			switch (symbol) {
			case NONE:
				break;
			default:
				versionConstraint.append(symbol.getSymbol());
				break;
			}
			versionConstraint.append(equalsCombo.getText());
		} else if (versionTypeCombo.getSelectionIndex() == RANGE_TYPE) {
			VersionConstraint symbol = VersionConstraint
					.bySymbol(minConstraintCombo.getText());
			switch (symbol) {
			case NONE:
				break;
			default:
				versionConstraint.append(symbol.getSymbol());
				break;
			}
			versionConstraint.append(minCombo.getText());
			versionConstraint.append(","); //$NON-NLS-1$
			symbol = VersionConstraint.bySymbol(maxConstraintCombo.getText());
			switch (symbol) {
			case NONE:
				break;
			default:
				versionConstraint.append(symbol.getSymbol());
				break;
			}
			versionConstraint.append(maxCombo.getText());
		}
		return versionConstraint.toString();
	}

	private boolean isAlreadyAdded(IPackage p) {
		for (IPackage exisitngPackage : existingPackages) {
			if (exisitngPackage.getName().equals(p.getName())
					&& exisitngPackage.getType() == p.getType()) {
				return true;
			}
		}

		return false;
	}

	private void run(IRunnableWithProgress task)
			throws InvocationTargetException, InterruptedException {
		try {
			inProgress = true;
			getContainer().run(true, false, task);
		} finally {
			inProgress = false;
		}
	}

}
