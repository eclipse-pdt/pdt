/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.composer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.ComposerPackages;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.composer.core.buildpath.BuildPathManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.composer.internal.core.resources.ComposerProject;
import org.eclipse.php.composer.ui.actions.*;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.job.ComposerJob;
import org.eclipse.php.composer.ui.job.UpdateDevJob;
import org.eclipse.php.composer.ui.job.UpdateJob;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.IMessage;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

public class ComposerFormEditor extends SharedHeaderFormEditor {

	public static final String ID = "org.eclipse.php.composer.ui.editor.composer.ComposerEditor"; //$NON-NLS-1$
	public static final String MSG_PARSE_ERROR = "org.eclipse.php.composer.ui.editor.composer.ParseException"; //$NON-NLS-1$

	protected boolean dirty = false;
	protected ComposerPackage composerPackage = null;
	protected IDocumentProvider documentProvider;

	private ISharedImages sharedImages = null;
	private IProject project;
	private IComposerProject composerProject;
	private BuildPathManager buildPathManager;

	private IAction installAction = null;
	private IAction installDevAction = null;
	private IAction updateAction = null;
	private IAction updateNoDevAction = null;
	private IAction selfUpdateAction = null;

	private int jsonEditorIndex;
	private int lastPageIndex = -1;

	protected OverviewPage overviewPage;
	protected DependenciesPage dependenciesPage;
	protected ConfigurationPage configurationPage;
	protected AutoloadPage autoloadPage;
	protected StructuredTextEditor jsonEditor;
	protected DependencyGraphPage graphPage;

	protected IToolBarManager toolbarManager;

	private IFile jsonFile;
	private String jsonDump = null;

	private boolean saving = false;
	private boolean pageChanging = false;

	private boolean validJson = true;
	private boolean newDepSinceLastSave = false;
	private boolean newDevDepSinceLastSave = false;

	public ComposerFormEditor() {
		super();
		jsonEditor = new StructuredTextEditor();
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		// try {
		// jsonEditor.setInput(input);
		documentProvider = jsonEditor.getDocumentProvider();
		// documentProvider.connect(input);
		documentProvider.getDocument(getEditorInput()).addDocumentListener(new IDocumentListener() {
			@Override
			public void documentChanged(DocumentEvent event) {
				ComposerFormEditor.this.documentChanged(event);
			}

			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {
				ComposerFormEditor.this.documentAboutToBeChanged(event);
			}
		});
		// } catch (CoreException e) {
		// Logger.logException(e);
		// }
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		jsonEditor.init(site, input);
		super.init(site, input);

		if (input instanceof IFileEditorInput) {
			jsonFile = ((IFileEditorInput) input).getFile();
			if (jsonFile != null) {
				project = jsonFile.getProject();
				composerProject = new ComposerProject(project);
				buildPathManager = new BuildPathManager(composerProject);

				setPartName(project.getName());
				ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {
					@Override
					public void resourceChanged(IResourceChangeEvent event) {
						ComposerFormEditor.this.resourceChanged(event);
					}
				});
			}
		}

		composerPackage = new ComposerPackage();
		composerPackage.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				Logger.debug("Property change: " + e.getPropertyName() + ", oldValue: " + e.getOldValue() //$NON-NLS-1$ //$NON-NLS-2$
						+ ", newValue: " + e.getNewValue()); //$NON-NLS-1$

				if (e.getPropertyName().startsWith("require")) { //$NON-NLS-1$
					newDepSinceLastSave = true;
				}

				if (e.getPropertyName().startsWith("require-dev")) { //$NON-NLS-1$
					newDevDepSinceLastSave = true;
				}

				setDirty(true);
			}
		});
	}

	@Override
	protected void createHeaderContents(IManagedForm headerForm) {
		super.createHeaderContents(headerForm);
		ScrolledForm header = headerForm.getForm();
		header.setText(Messages.ComposerFormEditor_Title);

		FormToolkit toolkit = headerForm.getToolkit();
		toolkit.decorateFormHeading(header.getForm());

		toolbarManager = (ToolBarManager) header.getToolBarManager();
	}

	@Override
	protected void createPages() {
		// create pages
		overviewPage = new OverviewPage(this, OverviewPage.ID, Messages.ComposerFormEditor_OverviewPageName);
		dependenciesPage = new DependenciesPage(this, DependenciesPage.ID,
				Messages.ComposerFormEditor_DependenciesPageName);
		configurationPage = new ConfigurationPage(this, ConfigurationPage.ID,
				Messages.ComposerFormEditor_ConfigurationPageName);
		autoloadPage = new AutoloadPage(this, AutoloadPage.ID, Messages.ComposerFormEditor_AutoloadPageName);
		graphPage = new DependencyGraphPage(this, DependencyGraphPage.ID,
				Messages.ComposerFormEditor_DependencyGraphPageName);

		// add them
		super.createPages();

		// contribute toolbar items
		for (Object pageObj : pages) {
			if (pageObj instanceof ComposerFormPage && pageObj != jsonEditor) {
				ComposerFormPage page = (ComposerFormPage) pageObj;
				page.contributeToToolbar(toolbarManager, getHeaderForm());
			}
		}
		contributeToToolbar(toolbarManager);

		// parse json
		jsonDump = documentProvider.getDocument(getEditorInput()).get();
		parse(jsonDump);
		setDirty(false);
		newDepSinceLastSave = false;
		newDevDepSinceLastSave = false;

		if (!validJson) {
			setActivePage(jsonEditorIndex);

			IManagedForm headerForm = getHeaderForm();
			if (headerForm != null) {
				headerForm.getMessageManager().update();
			}
		}
	}

	@Override
	protected void addPages() {
		try {
			addPage(overviewPage);
			addPage(dependenciesPage);
			addPage(autoloadPage);
			addPage(configurationPage);
			addPage(graphPage);
			jsonEditorIndex = addPage(jsonEditor, getEditorInput());
			setPageText(jsonEditorIndex, jsonEditor.getTitle());
		} catch (PartInitException e) {
			Logger.logException(e);
		}
	}

	@Override
	protected void pageChange(int newPageIndex) {
		// change page first
		super.pageChange(newPageIndex);

		pageChanging = true;

		// change to json editor
		if (isJsonEditor()) {
			if (validJson) {
				IDocument document = documentProvider.getDocument(getEditorInput());
				String json = composerPackage.toJson();
				document.set(json);
				setDirty(jsonDump != null && !jsonDump.equals(json));
				jsonDump = json;
			}

			getHeaderForm().getForm().setText(jsonEditor.getTitle());
		}

		// change from json editor
		if (lastPageIndex != -1 && lastPageIndex == jsonEditorIndex) {
			String json = documentProvider.getDocument(jsonEditor.getEditorInput()).get();
			if (jsonDump != null && !jsonDump.equals(json)) {
				boolean dirty = isDirty();
				parse(json);
				setDirty(dirty);
			}
		}

		lastPageIndex = newPageIndex;
		pageChanging = false;
	}

	protected void contributeToToolbar(IToolBarManager manager) {
		// this does not work for some reasons? how to make it working and get
		// rid of the action package?
		// IMenuService menuService = (IMenuService)
		// getSite().getService(IMenuService.class);
		// menuService.populateContributionManager(manager,
		// "toolbar:org.eclipse.php.composer.ui.editor.toolbar");

		manager.add(getInstallAction());
		manager.add(getInstallDevAction());
		manager.add(new Separator());
		manager.add(getUpdateNoDevAction());
		manager.add(getUpdateAction());
		manager.add(new Separator());
		manager.add(getSelfUpdateAction());
		manager.update(true);
	}

	@Override
	public void dispose() {
		toolbarManager = null;

		super.dispose();
	}

	protected ISharedImages getSharedImages() {
		if (sharedImages == null) {
			sharedImages = getSite().getPage().getWorkbenchWindow().getWorkbench().getSharedImages();
		}

		return sharedImages;
	}

	protected IAction getInstallAction() {
		if (installAction == null) {
			installAction = new InstallAction(project, getSite());
		}

		return installAction;
	}

	protected IAction getInstallDevAction() {
		if (installDevAction == null) {
			installDevAction = new InstallDevAction(project, getSite());
		}

		return installDevAction;
	}

	protected IAction getUpdateAction() {
		if (updateAction == null) {
			updateAction = new UpdateDevAction(project, getSite());
		}

		return updateAction;
	}

	protected IAction getUpdateNoDevAction() {
		if (updateNoDevAction == null) {
			updateNoDevAction = new UpdateAction(project, getSite());
		}

		return updateNoDevAction;
	}

	protected IAction getSelfUpdateAction() {
		if (selfUpdateAction == null) {
			selfUpdateAction = new SelfUpdateAction(project, getSite());
		}

		return selfUpdateAction;
	}

	public void doSave(IProgressMonitor monitor) {
		try {
			saving = true;
			IDocument document = documentProvider.getDocument(getEditorInput());

			if (isJsonEditor()) {
				parse(document.get());
			} else {
				validateJson(document.get());
			}

			if (!isJsonEditor() && isValidJson()) {
				document.set(composerPackage.toJson());
			}

			// write
			documentProvider.aboutToChange(getEditorInput());
			documentProvider.saveDocument(monitor, getEditorInput(), document, true);
			documentProvider.changed(getEditorInput());

			jsonDump = document.get();

			setDirty(false);
			saving = false;

			// save actions
			PreferencesSupport prefSupport = new PreferencesSupport(ComposerPlugin.ID);

			Boolean buildpath = prefSupport.getProjectSpecificBooleanPreferencesValue(
					ComposerPreferenceConstants.SAVEACTION_BUILDPATH, false, project);
			Boolean update = prefSupport.getProjectSpecificBooleanPreferencesValue(
					ComposerPreferenceConstants.SAVEACTION_UPDATE, false, project);

			update = update && (newDepSinceLastSave || newDevDepSinceLastSave);

			if (update) {
				ComposerJob job;

				if (newDevDepSinceLastSave || hasDevDepsInstalled()) {
					job = new UpdateDevJob(project);
				} else {
					job = new UpdateJob(project);
				}

				job.setUser(false);
				job.schedule();

				newDepSinceLastSave = false;
				newDevDepSinceLastSave = false;
			}

			if (buildpath && !update) {
				buildPathManager.update();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean hasDevDepsInstalled() {
		// is there like a more performant way to keep this list?
		// keep in mind a composer update command can be run from outside
		// eclipse
		if (composerPackage.getRequireDev().size() > 0) {
			VersionedPackage dev = composerPackage.getRequireDev().toArray()[0];
			ComposerPackages installed = composerProject.getInstalledPackages();
			return installed.has(dev.getName());
		}
		return false;
	}

	public void doSaveAs() {
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	private void documentAboutToBeChanged(DocumentEvent event) {
	}

	private void documentChanged(DocumentEvent event) {
		String contents = event.getDocument().get();

		// changes happen outside eclipse
		if (!pageChanging && !saving) {
			if (isJsonEditor()) {
				IDocument document = documentProvider.getDocument(getEditorInput());
				if (document.get() != null && !document.get().equals(contents)) {
					document.set(contents);
				}
			} else {
				parse(contents);
			}
			setDirty(false);
		}

		// changes in eclipse
		if (!saving && jsonDump != null && !jsonDump.equals(contents)) {
			setDirty(true);
		}
	}

	private boolean isJsonEditor() {
		return getActiveEditor() == jsonEditor;
	}

	private void parse(String contents) {
		try {
			composerPackage.fromJson(contents);
			setValidJson(true);
		} catch (ParseException e) {
			setValidJson(false, e);
		}
	}

	private void validateJson(String contents) {
		try {
			new ComposerPackage(contents);
			setValidJson(true);
		} catch (ParseException e) {
			setValidJson(false, e);
		}
	}

	private void setValidJson(boolean valid) {
		setValidJson(valid, null);
	}

	private void setValidJson(boolean valid, ParseException e) {
		validJson = valid;
		if (valid) {
			removeMessage(MSG_PARSE_ERROR);
		} else {
			addMessage(MSG_PARSE_ERROR, NLS.bind(Messages.ComposerFormEditor_ParseErrorMessage, e.getMessage()),
					IMessage.ERROR);
		}

		// change enabled status for pages
		if (pages != null) {
			for (Object pageObj : pages) {
				if (pageObj instanceof ComposerFormPage && pageObj != jsonEditor) {
					ComposerFormPage page = (ComposerFormPage) pageObj;
					page.setEnabled(valid);
				}
			}
		}
	}

	public boolean isValidJson() {
		return validJson;
	}

	private void addMessage(String id, String message, int type) {
		addMessage(id, message, type, null);
	}

	private void addMessage(String id, String message, int type, Object data) {
		IManagedForm headerForm = getHeaderForm();
		if (headerForm != null) {
			headerForm.getMessageManager().addMessage(id, message, data, type);
		}
	}

	private void removeMessage(String id) {
		IManagedForm headerForm = getHeaderForm();
		if (headerForm != null) {
			headerForm.getMessageManager().removeMessage(id);
		}
	}

	public boolean isDirty() {
		return this.dirty;
	}

	public void setDirty(boolean value) {
		this.dirty = value;
		editorDirtyStateChanged();
	}

	public IProject getProject() {
		return project;
	}

	public ComposerPackage getComposerPackge() {
		return composerPackage;
	}

	/**
	 * Based on org.eclipse.m2e.editor.pom.MavenPomEditor
	 */
	private void resourceChanged(IResourceChangeEvent event) {

		if (jsonFile == null) {
			return;
		}

		// handle project delete
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE || event.getType() == IResourceChangeEvent.PRE_DELETE) {
			if (jsonFile.getProject().equals(event.getResource())) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						close(false);
					}
				});
			}
			return;
		}

		// handle json delete
		class RemovedResourceDeltaVisitor implements IResourceDeltaVisitor {
			boolean removed = false;

			public boolean visit(IResourceDelta delta) throws CoreException {
				if (delta.getResource() != null && delta.getResource().equals(jsonFile)
						&& (delta.getKind() & (IResourceDelta.REMOVED)) != 0) {
					removed = true;
					return false;
				}
				return true;
			}
		}
		;

		try {
			RemovedResourceDeltaVisitor visitor = new RemovedResourceDeltaVisitor();
			event.getDelta().accept(visitor);
			if (visitor.removed) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						close(true);
					}
				});
			}
		} catch (CoreException ex) {
			Logger.logException(ex);
		}
	}

}
