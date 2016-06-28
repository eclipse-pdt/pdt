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
package org.eclipse.php.composer.internal.ui.editors;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.php.composer.core.*;
import org.eclipse.php.composer.core.model.*;
import org.eclipse.php.composer.core.validation.IPropertyValidator;
import org.eclipse.php.composer.core.validation.NameValidator;
import org.eclipse.php.composer.core.validation.VersionValidator;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.php.composer.internal.ui.ProjectUtils;
import org.eclipse.php.composer.internal.ui.dialogs.AddDependencyWizardDialog;
import org.eclipse.php.composer.internal.ui.dialogs.AddRepositoryDialog;
import org.eclipse.php.composer.internal.ui.preferences.RepositoriesContentProvider;
import org.eclipse.php.composer.internal.ui.wizards.AddDependencyWizard;
import org.eclipse.php.composer.ui.console.ConsoleCommandExecutor;
import org.eclipse.php.composer.ui.jobs.DumpAutoloadJob;
import org.eclipse.php.composer.ui.jobs.InstallDependenciesJob;
import org.eclipse.php.composer.ui.jobs.UpdateDependenciesJob;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class GeneralPage extends FormPage implements IResourceChangeListener {

	private class PackageDetailsJob extends Job {

		public PackageDetailsJob(IPackage p, IPackageSearchService searchService) {
			super(Messages.GeneralPage_PackageDetailsJobName);
			this.result = p;
			this.searchService = searchService;
		}

		private IPackage result;
		private IPackageSearchService searchService;

		public IPackage getPackage() {
			return result;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				monitor.beginTask(Messages.GeneralPage_PackageDetailsJobTitle, IProgressMonitor.UNKNOWN);
				IPackage p = searchService.getPackageDetails(result.getName(), monitor);
				if (p != null && !monitor.isCanceled()) {
					if (result != null) {
						result.setVersions(p.getVersions());
						monitor.done();
						return Status.OK_STATUS;
					}
				}
				return Status.CANCEL_STATUS;
			} catch (IOException e) {
				return Status.OK_STATUS;
			} finally {
				monitor.done();
			}
		}
	}

	private ComposerRoot model;
	private Table packagesTable;

	private IDocument document;

	private ModelContainer modelContainer;

	private ComposerService composer;

	private MarkerManager markerManager;

	private ImageHyperlink dumpAutoloadLink;
	private ImageHyperlink installUpdateLink;

	private final boolean canEdit;

	/**
	 * Create the form page.
	 * 
	 * @param editor
	 * @param root
	 * @param id
	 * @param title
	 */
	public GeneralPage(ComposerJsonEditor editor, IFileEditorInput editorInput) {
		super(editor, "generalPage", Messages.GeneralPage_General); //$NON-NLS-1$
		this.document = editor.getDocumentProvider().getDocument(editorInput);
		this.modelContainer = new ModelContainer();
		modelContainer.setOutput(new JFaceDocumentStore(document));
		this.model = modelContainer.getModel();
		this.canEdit = isInRoot(editor.getEditorInput());
		this.composer = new ComposerService(editorInput.getFile().getParent(), new ConsoleCommandExecutor());
	}

	@Override
	public void setActive(boolean active) {
		if (active) {
			ScrolledForm form = getManagedForm().getForm();
			try {
				modelContainer.deserialize(document);
			} catch (JsonParseException e) {
				setError(e);
			} catch (IOException e) {
				Throwable cause = e.getCause();
				if (cause instanceof JsonParseException) {
					setError((JsonParseException) cause);
				} else {
					form.setMessage(Messages.GeneralPage_ComposerJsonParseError, IMessageProvider.ERROR);
				}
			}

			if (modelContainer.isLoaded()) {
				if (canEdit) {
					model = modelContainer.getModel();
				} else {
					form.setMessage(Messages.GeneralPage_CannotModifyWarning, IMessageProvider.WARNING);
				}
			}
		}
	}

	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getDelta() == null) {
			return;
		}
		IResourceDelta[] projects = event.getDelta().getAffectedChildren();
		for (IResourceDelta projectDelta : projects) {
			if (projectDelta != null && composer.getRoot().equals(projectDelta.getResource())) {
				IResourceDelta[] deltas = projectDelta.getAffectedChildren();
				for (final IResourceDelta d : deltas) {
					final IResource r = d.getResource();
					if (r != null && ComposerService.COMPOSER_LOCK.equals(r.getName())) {
						if (d.getKind() == IResourceDelta.ADDED) {
							updateAction(Messages.GeneralPage_DependenciesUpdate, ComposerUIPlugin.IMAGE_UPDATE);
						} else if (d.getKind() == IResourceDelta.REMOVED) {
							updateAction(Messages.GeneralPage_DependenciesInstall, ComposerUIPlugin.IMAGE_INSTALL);
						}
						continue;
					}
					if (ComposerService.isComposerJson(r)) {

						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								markerManager.updateMessages(d.getMarkerDeltas());

								if (d.getKind() == IResourceDelta.CHANGED && !isDirty()) {
									refreshModel();
								}
							}
						});
					}
				}
			}
		}
	}

	protected void refreshModel() {
		setActive(true);
	}

	/**
	 * Create contents of the form.
	 * 
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		managedForm.getForm().setImage(ComposerUIPlugin.getDefault().getImage(ComposerUIPlugin.IMAGE_GENERAL));
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(Messages.GeneralPage_General);
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		form.getBody().setLayout(new GridLayout(2, false));

		createGeneralSection(managedForm);

		createDependenciesSection(managedForm);

		createRepositoriesSection(managedForm);

		this.markerManager = new MarkerManager(managedForm.getMessageManager());
		IFile file = (IFile) getEditorInput().getAdapter(IFile.class);
		markerManager.addProblemsFromFile(file);

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	protected void updateAction(final String label, final String image) {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!installUpdateLink.isDisposed()) {
					installUpdateLink.setText(label);
					installUpdateLink.setImage(ComposerUIPlugin.getDefault().getImage(image));
					installUpdateLink.pack(true);
				}
			}
		});
	}

	private void createGeneralSection(IManagedForm managedForm) {
		Section generalSection = managedForm.getToolkit().createSection(managedForm.getForm().getBody(),
				Section.TWISTIE | Section.TITLE_BAR);
		generalSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		managedForm.getToolkit().paintBordersFor(generalSection);
		generalSection.setText(Messages.GeneralPage_GeneralSection);
		generalSection.setExpanded(true);

		Label lblGeneralComposerProperties = managedForm.getToolkit().createLabel(generalSection,
				Messages.GeneralPage_GeneralDesc, SWT.NONE);
		generalSection.setDescriptionControl(lblGeneralComposerProperties);

		Composite generalContainer = managedForm.getToolkit().createComposite(generalSection, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(generalContainer);
		generalSection.setClient(generalContainer);
		generalContainer.setLayout(new GridLayout(2, false));

		createTextInput(generalContainer, Messages.GeneralPage_GeneralName, model.getNameProperty(),
				new NameValidator(Messages.GeneralPage_GeneralName));
		createTextInput(generalContainer, Messages.GeneralPage_GeneralVersion, model.getVersionProperty(),
				new VersionValidator());
	}

	private void createDependenciesSection(IManagedForm managedForm) {
		Section packagesSection = managedForm.getToolkit().createSection(managedForm.getForm().getBody(),
				Section.TWISTIE | Section.TITLE_BAR);
		packagesSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		managedForm.getToolkit().paintBordersFor(packagesSection);
		packagesSection.setText(Messages.GeneralPage_DependenciesSection);
		packagesSection.setExpanded(true);

		Composite packagesContainer = managedForm.getToolkit().createComposite(packagesSection, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(packagesContainer);
		packagesSection.setClient(packagesContainer);
		packagesContainer.setLayout(new GridLayout(2, false));

		installUpdateLink = managedForm.getToolkit().createImageHyperlink(packagesContainer, SWT.NONE);
		if (ComposerService.isInstalled(composer.getRoot())) {
			installUpdateLink.setText(Messages.GeneralPage_DependenciesUpdate);
			installUpdateLink.setImage(ComposerUIPlugin.getDefault().getImage(ComposerUIPlugin.IMAGE_UPDATE));
		} else {
			installUpdateLink.setText(Messages.GeneralPage_DependenciesInstall);
			installUpdateLink.setImage(ComposerUIPlugin.getDefault().getImage(ComposerUIPlugin.IMAGE_INSTALL));
		}
		installUpdateLink.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		installUpdateLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
					return;
				}
				final IProject project = getProjectFromEditor();
				if (project != null && ProjectUtils.checkProject((IContainer) project)) {
					Job opJob = ComposerService.isInstalled(composer.getRoot()) ? new UpdateDependenciesJob(composer)
							: new InstallDependenciesJob(composer);
					opJob.schedule();
				} else {
					// TODO log it
				}
			}
		});
		managedForm.getToolkit().paintBordersFor(installUpdateLink);

		dumpAutoloadLink = managedForm.getToolkit().createImageHyperlink(packagesContainer, SWT.NONE);
		dumpAutoloadLink.setText(Messages.GeneralPage_DumpAutoloadLabel);
		dumpAutoloadLink.setImage(ComposerUIPlugin.getDefault().getImage(ComposerUIPlugin.IMAGE_DUMP_AUTOLOAD));
		dumpAutoloadLink.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		dumpAutoloadLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
					return;
				}
				final IProject project = getProjectFromEditor();
				if (project != null && ProjectUtils.checkProject((IContainer) project)) {
					Job opJob = new DumpAutoloadJob(composer);
					opJob.schedule();
				} else {
					// TODO log it
				}
			}
		});
		managedForm.getToolkit().paintBordersFor(installUpdateLink);

		Label packagesListDescLabel = managedForm.getToolkit().createLabel(packagesContainer,
				Messages.GeneralPage_DependenciesDesc, SWT.NONE);
		packagesListDescLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));

		final TableViewer packagesViewer = new TableViewer(packagesContainer,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		packagesViewer.setContentProvider(ArrayContentProvider.getInstance());

		packagesTable = packagesViewer.getTable();
		packagesTable.setHeaderVisible(true);
		packagesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().paintBordersFor(packagesTable);

		TableViewerColumn nameViewerColumn = new TableViewerColumn(packagesViewer, SWT.NONE);
		nameViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IPackage) element).getName();
			}
		});
		TableColumn nameColumn = nameViewerColumn.getColumn();
		nameColumn.setWidth(150);
		nameColumn.setText(Messages.GeneralPage_DependenciesNameColumn);

		TableViewerColumn typeViewerColumn = new TableViewerColumn(packagesViewer, SWT.NONE);
		typeViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IPackage) element).getType().getName();
			}
		});
		TableColumn typeColumn = typeViewerColumn.getColumn();
		typeColumn.setWidth(100);
		typeColumn.setText(Messages.GeneralPage_DependenciesTypeColumn);

		TableViewerColumn versionViewerColumn = new TableViewerColumn(packagesViewer, SWT.NONE);
		versionViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IPackage) element).getVersionConstraint();
			}
		});
		TableColumn versionColumn = versionViewerColumn.getColumn();
		versionColumn.setWidth(100);
		versionColumn.setText(Messages.GeneralPage_DependenciesVersionColumn);

		packagesViewer.setInput(getAllPackages());

		Composite packagesButtonsContainer = managedForm.getToolkit().createComposite(packagesContainer, SWT.NONE);
		packagesButtonsContainer.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		managedForm.getToolkit().paintBordersFor(packagesButtonsContainer);
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.spacing = 5;
		packagesButtonsContainer.setLayout(layout);

		Button addButton = createButton(packagesButtonsContainer, Messages.GeneralPage_DependenciesAdd);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleAddPackage(packagesViewer);
				updateInstallLinkState(packagesViewer);
			}
		});
		final Button modifyButton = managedForm.getToolkit().createButton(packagesButtonsContainer,
				Messages.GeneralPage_DependenciesModify, SWT.NONE);
		modifyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleModifyPackage(packagesViewer);
			}
		});
		modifyButton.setEnabled(false);
		final Button removeButton = managedForm.getToolkit().createButton(packagesButtonsContainer,
				Messages.GeneralPage_DependenciesRemove, SWT.NONE);

		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleRemovePackage(packagesViewer);
				updateInstallLinkState(packagesViewer);
			}
		});
		removeButton.setEnabled(false);

		packagesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				boolean enabled = !event.getSelection().isEmpty();
				modifyButton.setEnabled(enabled);
				removeButton.setEnabled(enabled);
			}
		});

		Label packagesDescLabel = managedForm.getToolkit().createLabel(packagesSection,
				Messages.GeneralPage_DependenciesSectionDesc, SWT.NONE);
		packagesSection.setDescriptionControl(packagesDescLabel);

		modelContainer.addModelListener(new IModelChangeListener() {
			@Override
			public void modelReloaded() {
				updateInstallLinkState(packagesViewer);

				dumpAutoloadLink.setEnabled(modelContainer.isLoaded() && canEdit);
				packagesTable.setEnabled(modelContainer.isLoaded() && canEdit);

				if (!packagesViewer.getSelection().isEmpty()) {
					modifyButton.setEnabled(modelContainer.isLoaded() && canEdit);
				}

				if (!packagesViewer.getSelection().isEmpty()) {
					removeButton.setEnabled(modelContainer.isLoaded() && canEdit);
				}

				packagesViewer.setInput(getAllPackages());
			}
		});
	}

	private void updateInstallLinkState(TableViewer packagesViewer) {
		installUpdateLink
				.setEnabled(modelContainer.isLoaded() && canEdit && packagesViewer.getTable().getItemCount() != 0);
	}

	private void createRepositoriesSection(IManagedForm managedForm) {
		Section repoSection = managedForm.getToolkit().createSection(managedForm.getForm().getBody(),
				Section.TWISTIE | Section.TITLE_BAR);
		repoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		managedForm.getToolkit().paintBordersFor(repoSection);
		repoSection.setText(Messages.GeneralPage_RepositoriesSection);
		repoSection.setExpanded(true);

		Label repoDescLabel = managedForm.getToolkit().createLabel(repoSection, Messages.GeneralPage_RepositoriesDesc,
				SWT.NONE);
		repoSection.setDescriptionControl(repoDescLabel);

		Composite repoContainer = managedForm.getToolkit().createComposite(repoSection, SWT.NONE);
		managedForm.getToolkit().paintBordersFor(repoContainer);
		repoSection.setClient(repoContainer);
		repoContainer.setLayout(new GridLayout(2, false));

		final TableViewer repoViewer = new TableViewer(repoContainer, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		repoViewer.setContentProvider(new RepositoriesContentProvider());
		repoViewer.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				if (element instanceof IRepository) {
					IRepository repo = (IRepository) element;
					switch (repo.getType()) {
					case PACKAGE:
						IRepositoryPackage rPackage = repo.getRepositoryPackage();
						return rPackage.getName();
					default:
						return repo.getUrl();
					}
				}
				return super.getText(element);
			}
		});
		repoViewer.setInput(model.getRepositories());

		final Table repoTable = repoViewer.getTable();
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gridData.widthHint = 200;
		repoTable.setLayoutData(gridData);
		managedForm.getToolkit().paintBordersFor(repoTable);

		Composite repoButtonsContainer = managedForm.getToolkit().createComposite(repoContainer, SWT.NONE);
		repoButtonsContainer.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		managedForm.getToolkit().paintBordersFor(repoButtonsContainer);
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.spacing = 5;
		repoButtonsContainer.setLayout(layout);

		Button addButton = createButton(repoButtonsContainer, Messages.GeneralPage_RepositoriesAdd);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddRepositoryDialog dialog = new AddRepositoryDialog(getSite().getShell(), model.getRepositories());
				if (dialog.open() == Window.OK) {
					List<IRepository> toAdd = dialog.getRepositories();
					IRepositories repos = model.getRepositoriesProperty();
					for (IRepository repo : toAdd) {
						repos.addRepository(repo, true);
					}
					if (toAdd.size() > 0) {
						repoViewer.setInput(model.getRepositories());
					}
				}
			}
		});
		final Button removeButton = managedForm.getToolkit().createButton(repoButtonsContainer,
				Messages.GeneralPage_RepositoriesRemove, SWT.NONE);

		removeButton.setEnabled(false);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) repoViewer.getSelection();
				Object[] selectedRepos = selection.toArray();
				for (Object repo : selectedRepos) {
					model.getRepositories().removeRepository((IRepository) repo, true);
				}
				if (selectedRepos.length > 0) {
					repoViewer.setInput(model.getRepositories());
				}
			}
		});
		repoViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				boolean enabled = !event.getSelection().isEmpty();
				removeButton.setEnabled(enabled);
			}
		});

		modelContainer.addModelListener(new IModelChangeListener() {
			@Override
			public void modelReloaded() {
				if (!repoViewer.getSelection().isEmpty()) {
					removeButton.setEnabled(modelContainer.isLoaded() && canEdit);
				}

				repoViewer.setInput(model.getRepositories());
				repoTable.setEnabled(modelContainer.isLoaded() && canEdit);
			}
		});
	}

	private Button createButton(Composite parent, String label) {
		final Button button = getManagedForm().getToolkit().createButton(parent, label, SWT.NONE);
		modelContainer.addModelListener(new IModelChangeListener() {
			@Override
			public void modelReloaded() {
				button.setEnabled(modelContainer.isLoaded() && canEdit);
			}
		});
		return button;
	}

	private Text createTextInput(Composite parent, String label, final IProperty property,
			final IPropertyValidator... validators) {
		Label nameLabel = getManagedForm().getToolkit().createLabel(parent, label + ':', SWT.NONE);
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		final Text text = getManagedForm().getToolkit().createText(parent, property.getValue(), SWT.NONE);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		final ControlDecoration decoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent event) {
				property.setValue(text.getText(), true);
				if (validators != null && validators.length > 0) {
					String result = null;
					for (IPropertyValidator validator : validators) {
						result = validator.validate(property);
						if (result != null) {
							break;
						}
					}
					setFieldErrorMessage(result, decoration);
				}
			}
		});
		modelContainer.addModelListener(new IModelChangeListener() {
			@Override
			public void modelReloaded() {
				if (!text.isDisposed()) {
					if (modelContainer.isLoaded() && canEdit) {
						text.setEnabled(true);
						if (property.getValue() != null) {
							text.setText(property.getValue());
						} else {
							text.setText(""); //$NON-NLS-1$
						}
					} else {
						text.setEnabled(false);
					}
				}
			}
		});
		return text;
	}

	private List<IPackage> getAllPackages() {
		List<IPackage> result = new ArrayList<IPackage>();
		result.addAll(getTypedPackages(model.getRequireProperty().getPackages(), PackageType.REQUIRE));
		result.addAll(getTypedPackages(model.getRequireDevProperty().getPackages(), PackageType.REQUIRE_DEV));
		result.addAll(getTypedPackages(model.getConflictProperty().getPackages(), PackageType.CONFLICT));
		result.addAll(getTypedPackages(model.getReplaceProperty().getPackages(), PackageType.REPLACE));
		result.addAll(getTypedPackages(model.getProvideProperty().getPackages(), PackageType.PROVIDE));
		return result;

	}

	private Collection<? extends IPackage> getTypedPackages(List<IPackage> packages, PackageType type) {
		for (IPackage p : packages) {
			p.setType(type);
		}
		return packages;
	}

	private IProject getProjectFromEditor() {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (activeEditor != null) {
			IEditorInput editorInput = activeEditor.getEditorInput();
			IFile composerJson = (IFile) editorInput.getAdapter(IFile.class);
			if (composerJson != null) {
				return composerJson.getProject();
			}
		}
		return null;
	}

	private void handleAddPackage(final TableViewer packagesViewer) {
		IProject project = getProjectFromEditor();
		if (project != null && ProjectUtils.checkProject((IContainer) project)) {
			AddDependencyWizard wizard = new AddDependencyWizard(getSearchService(), getAllPackages());
			WizardDialog dialog = new AddDependencyWizardDialog(getSite().getShell(), wizard);
			dialog.create();
			if (dialog.open() == Window.OK) {
				IPackage toAdd = wizard.getPackage();
				IPackages modelElement = null;
				switch (toAdd.getType()) {
				case REQUIRE:
					modelElement = model.getRequireProperty();
					break;
				case REQUIRE_DEV:
					modelElement = model.getRequireDevProperty();
					break;
				case CONFLICT:
					modelElement = model.getConflictProperty();
					break;
				case PROVIDE:
					modelElement = model.getProvideProperty();
					break;
				case REPLACE:
					modelElement = model.getReplaceProperty();
					break;
				default:
					return;
				}
				modelElement.addPackage(toAdd, true);
				packagesViewer.setInput(getAllPackages());
			}
		}
	}

	private void handleRemovePackage(final TableViewer packagesViewer) {
		IStructuredSelection selection = (IStructuredSelection) packagesViewer.getSelection();
		Object[] selectedRepos = selection.toArray();
		if (selectedRepos.length > 0) {
			for (Object toRemove : selectedRepos) {
				IPackage p = (IPackage) toRemove;
				IPackages packages = getPackages(p.getType());
				packages.removePackage(p, true);
			}
			packagesViewer.setInput(getAllPackages());
		}
	}

	private void handleModifyPackage(final TableViewer packagesViewer) {
		IStructuredSelection selection = (IStructuredSelection) packagesViewer.getSelection();
		Object[] selectedRepos = selection.toArray();
		if (selectedRepos.length > 0) {
			final IPackage toModify = (IPackage) selectedRepos[0];
			final IPackageSearchService searchService = getSearchService();
			final PackageDetailsJob packageDetails = new PackageDetailsJob(toModify, searchService);
			packageDetails.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					int result = event.getResult().getSeverity();
					if (result == IStatus.OK) {
						final AddDependencyWizard wizard = new AddDependencyWizard(searchService, getAllPackages(),
								packageDetails.getPackage());
						final WizardDialog dialog = new AddDependencyWizardDialog(getSite().getShell(), wizard);
						final IPackages oldSection = getPackages(toModify.getType());
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								if (dialog.open() != Dialog.OK) {
									return;
								}

								toModify.setType(wizard.getPackage().getType());
								toModify.setVersionConstraint(wizard.getPackage().getVersionConstraint());

								IPackages newSection = getPackages(toModify.getType());
								if (oldSection == newSection) {
									newSection.addPackage(toModify, true);
								} else {
									oldSection.removePackage(toModify, false);
									newSection.addPackage(toModify, true);
								}
								packagesViewer.setInput(getAllPackages());
							}
						});
					} else if (result == Status.CANCEL) {
						return;
					} else {
						MessageDialog.openError(getSite().getShell(), Messages.GeneralPage_PackageModifyErrorTitle,
								MessageFormat.format(Messages.GeneralPage_PackageModifyErrorMessage,
										toModify.getName()));
					}
				}
			});
			packageDetails.setUser(true);
			packageDetails.schedule();
		}
	}

	private IPackages getPackages(PackageType type) {
		IPackages packages = null;
		switch (type) {
		case REQUIRE:
			packages = model.getRequireProperty();
			break;
		case REQUIRE_DEV:
			packages = model.getRequireDevProperty();
			break;
		case CONFLICT:
			packages = model.getConflictProperty();
			break;
		case PROVIDE:
			packages = model.getProvideProperty();
			break;
		case REPLACE:
			packages = model.getReplaceProperty();
			break;
		}
		return packages;
	}

	private IPackageSearchService getSearchService() {
		IRepositories repositories = model.getRepositories();

		if (repositories == null) {
			return new PackagistSearchService();
		}
		List<IRepository> repositoryList = repositories.getRepositories();
		if (repositoryList.isEmpty()) {
			return new PackagistSearchService();
		}
		if (repositoryList.size() == 1) {
			IRepository repository = repositoryList.get(0);
			if (repository.getUrl().equalsIgnoreCase(PackagistSearchService.PACKAGIST_REPO)
					&& repository.getType() == RepositoryType.COMPOSER) {
				return new PackagistSearchService();
			}
		}
		return new CLISearchService(composer);
	}

	private void setError(JsonParseException e) {
		JsonLocation location = e.getLocation();
		getManagedForm().getForm().setMessage(MessageFormat.format(Messages.GeneralPage_ParseFailedMessage,
				location.getLineNr(), location.getColumnNr()), IMessageProvider.ERROR);
	}

	private boolean isInRoot(IEditorInput editorInput) {
		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput) editorInput;
			IFile composerFile = fileInput.getFile();
			return composerFile.getParent() == composerFile.getProject();
		}
		return false;
	}

	private void setFieldErrorMessage(String message, ControlDecoration decoration) {
		if (message == null) {
			decoration.hide();
			return;
		}
		FieldDecoration img = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		decoration.setImage(img.getImage());
		decoration.setDescriptionText(message);
		decoration.show();
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

}
