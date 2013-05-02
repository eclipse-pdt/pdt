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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.ui.dialogs.StatusInfo;
import org.eclipse.dltk.internal.ui.dialogs.StatusUtil;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.dltk.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BuildPathBasePage;
import org.eclipse.dltk.internal.ui.wizards.buildpath.FolderSelectionDialog;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.*;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.viewsupport.ImageDisposer;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.phar.wizard.PharUIUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.ui.views.navigator.ResourceComparator;

public abstract class AbstractIncludepathsBlock extends BuildpathsBlock {
	public static interface IRemoveOldBinariesQuery {
		/**
		 * Do the callback. Returns <code>true</code> if .class files should be
		 * removed from the old output location.
		 * 
		 * @param oldOutputLocation
		 *            The old output location
		 * @return Returns true if .class files should be removed.
		 * @throws OperationCanceledException
		 */
		boolean doQuery(IPath oldOutputLocation)
				throws OperationCanceledException;
	}

	protected IWorkspaceRoot fWorkspaceRoot;
	protected ListDialogField fBuildPathList;
	protected StringButtonDialogField fBuildPathDialogField;
	protected StatusInfo fPathStatus;
	protected StatusInfo fBuildPathStatus;
	protected IScriptProject fCurrScriptProject;
	protected IStatusChangeListener fContext;
	protected Control fSWTWidget;
	protected TabFolder fTabFolder;
	protected int fPageIndex;
	protected BuildPathBasePage fSourceContainerPage;
	protected PHPProjectsWorkbookPage fProjectsPage;
	protected PHPLibrariesWorkbookPage fLibrariesPage;
	protected BuildPathBasePage fCurrPage;
	protected String fUserSettingsTimeStamp;
	protected long fFileTimeStamp;
	protected IRunnableContext fRunnableContext;
	protected boolean fUseNewPage;

	protected final IWorkbenchPreferenceContainer fPageContainer; // null when

	// invoked from
	// a
	// non-property
	// page context

	public AbstractIncludepathsBlock(IRunnableContext runnableContext,
			IStatusChangeListener context, int pageToShow, boolean useNewPage,
			IWorkbenchPreferenceContainer pageContainer) {
		super(runnableContext, context, pageToShow, useNewPage, pageContainer);
		fPageContainer = pageContainer;
		fWorkspaceRoot = DLTKUIPlugin.getWorkspace().getRoot();
		fContext = context;
		fUseNewPage = useNewPage;
		fPageIndex = pageToShow;
		fSourceContainerPage = null;
		fLibrariesPage = null;
		fProjectsPage = null;
		fCurrPage = null;
		fRunnableContext = runnableContext;
		initContainerElements();
		fBuildPathStatus = new StatusInfo();
		fPathStatus = new StatusInfo();
		fCurrScriptProject = null;
	}

	protected void initContainerElements() {
		BuildPathAdapter adapter = new BuildPathAdapter();
		String[] buttonLabels = new String[] {
				NewWizardMessages.BuildPathsBlock_buildpath_up_button,
				NewWizardMessages.BuildPathsBlock_buildpath_down_button,
				/* 2 */null,
				NewWizardMessages.BuildPathsBlock_buildpath_checkall_button,
				NewWizardMessages.BuildPathsBlock_buildpath_uncheckall_button };
		fBuildPathList = new ListDialogField(null, buttonLabels,
				new PHPIPListLabelProvider());
		// fBuildPathList.setDialogFieldListener(adapter);
		fBuildPathList
				.setLabelText(NewWizardMessages.BuildPathsBlock_buildpath_label);
		fBuildPathList.setUpButtonIndex(0);
		fBuildPathList.setDownButtonIndex(1);
		fBuildPathDialogField = new StringButtonDialogField(adapter);
		fBuildPathDialogField
				.setButtonLabel(NewWizardMessages.BuildPathsBlock_buildpath_button);
		fBuildPathDialogField.setDialogFieldListener(adapter);
		fBuildPathDialogField
				.setLabelText(NewWizardMessages.BuildPathsBlock_buildpath_label);
	}

	protected abstract boolean supportZips();

	/**
	 * Update page title. Needs to be called before createControl()
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		fBuildPathList.setLabelText(title);
	}

	// -------- UI creation ---------
	public Control createControl(Composite parent) {
		fSWTWidget = parent;
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		composite.setLayout(layout);
		TabFolder folder = new TabFolder(composite, SWT.NONE);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		folder.setFont(composite.getFont());
		TabItem item;
		item = new TabItem(folder, SWT.NONE);
		item.setText(NewWizardMessages.BuildPathsBlock_tab_source);
		item.setImage(DLTKPluginImages
				.get(DLTKPluginImages.IMG_OBJS_PACKFRAG_ROOT));
		// if (fUseNewPage) {
		// fSourceContainerPage = new NewSourceContainerWorkbookPage(
		// fBuildPathList, fRunnableContext, getPreferenceStore());
		// } else {
		fSourceContainerPage = new PHPSourceContainerWorkbookPage(
				fBuildPathList);
		// }
		item.setData(fSourceContainerPage);
		item.setControl(fSourceContainerPage.getControl(folder));
		IWorkbench workbench = DLTKUIPlugin.getDefault().getWorkbench();
		Image projectImage = workbench.getSharedImages().getImage(
				IDE.SharedImages.IMG_OBJ_PROJECT);
		fProjectsPage = new PHPProjectsWorkbookPage(fBuildPathList,
				fPageContainer);
		item = new TabItem(folder, SWT.NONE);
		item.setText(NewWizardMessages.BuildPathsBlock_tab_projects);
		item.setImage(projectImage);
		item.setData(fProjectsPage);
		item.setControl(fProjectsPage.getControl(folder));
		fLibrariesPage = new PHPLibrariesWorkbookPage(this.supportZips(),
				fBuildPathList, fPageContainer);
		fLibrariesPage.setScriptProject(getScriptProject());
		item = new TabItem(folder, SWT.NONE);
		item.setText(NewWizardMessages.BuildPathsBlock_tab_libraries);
		item.setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_LIBRARY));
		item.setData(fLibrariesPage);
		item.setControl(fLibrariesPage.getControl(folder));
		// a non shared image
		Image cpoImage = DLTKPluginImages.DESC_TOOL_BUILDPATH_ORDER
				.createImage();
		composite.addDisposeListener(new ImageDisposer(cpoImage));
		PHPBuildpathOrderingWorkbookPage ordpage = new PHPBuildpathOrderingWorkbookPage(
				fBuildPathList);
		item = new TabItem(folder, SWT.NONE);
		item.setText(NewWizardMessages.BuildPathsBlock_tab_order);
		item.setImage(cpoImage);
		item.setData(ordpage);
		item.setControl(ordpage.getControl(folder));
		if (fCurrScriptProject != null) {
			fSourceContainerPage.init(fCurrScriptProject);
			fLibrariesPage.init(fCurrScriptProject);
			fProjectsPage.init(fCurrScriptProject);
		}
		folder.setSelection(fPageIndex);
		fCurrPage = (BuildPathBasePage) folder.getItem(fPageIndex).getData();
		folder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tabChanged(e.item);
			}
		});
		fTabFolder = folder;
		Dialog.applyDialogFont(composite);
		return composite;
	}

	private Shell getShell() {
		if (fSWTWidget != null) {
			return fSWTWidget.getShell();
		}
		return DLTKUIPlugin.getActiveWorkbenchShell();
	}

	/**
	 * Initializes the buildpath for the given project. Multiple calls to init
	 * are allowed, but all existing settings will be cleared and replace by the
	 * given or default paths.
	 * 
	 * @param jproject
	 *            The java project to configure. Does not have to exist.
	 * @param outputLocation
	 *            The output location to be set in the page. If
	 *            <code>null</code> is passed, jdt default settings are used, or
	 *            - if the project is an existing script project- the output
	 *            location of the existing project
	 * @param buildpathEntries
	 *            The buildpath entries to be set in the page. If
	 *            <code>null</code> is passed, jdt default settings are used, or
	 *            - if the project is an existing script project - the buildpath
	 *            entries of the existing project
	 */
	public void init(IScriptProject jproject, IBuildpathEntry[] buildpathEntries) {
		fCurrScriptProject = jproject;
		boolean projectExists = false;
		List newBuildpath = null;
		IProject project = fCurrScriptProject.getProject();
		projectExists = (project.exists() && project
				.getFile(".buildpath").exists()); //$NON-NLS-1$ 
		if (projectExists) {
			if (buildpathEntries == null) {
				buildpathEntries = fCurrScriptProject.readRawBuildpath();
			}
		}
		if (buildpathEntries != null) {
			newBuildpath = getExistingEntries(buildpathEntries);
		}
		if (newBuildpath == null) {
			newBuildpath = getDefaultBuildpath(jproject);
		}
		List exportedEntries = new ArrayList();
		for (int i = 0; i < newBuildpath.size(); i++) {
			BPListElement curr = (BPListElement) newBuildpath.get(i);
			if (curr.isExported()
					|| curr.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
				exportedEntries.add(curr);
			}
		}
		// inits the dialog field
		fBuildPathDialogField.enableButton(project.exists());
		fBuildPathList.setElements(newBuildpath);
		// fBuildPathList.setCheckedElements(exportedEntries);
		initializeTimeStamps();
		updateUI();
	}

	protected void updateUI() {
		if (fSWTWidget == null || fSWTWidget.isDisposed()) {
			return;
		}
		if (Display.getCurrent() != null) {
			doUpdateUI();
		} else {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (fSWTWidget == null || fSWTWidget.isDisposed()) {
						return;
					}
					doUpdateUI();
				}
			});
		}
	}

	protected void doUpdateUI() {
		fBuildPathDialogField.refresh();
		fBuildPathList.refresh();
		if (fSourceContainerPage != null) {
			fSourceContainerPage.init(fCurrScriptProject);
			fProjectsPage.init(fCurrScriptProject);
			fLibrariesPage.init(fCurrScriptProject);
		}
		doStatusLineUpdate();
	}

	private String getEncodedSettings() {
		StringBuffer buf = new StringBuffer();
		int nElements = fBuildPathList.getSize();
		buf.append('[').append(nElements).append(']');
		for (int i = 0; i < nElements; i++) {
			BPListElement elem = (BPListElement) fBuildPathList.getElement(i);
			elem.appendEncodedSettings(buf);
		}
		return buf.toString();
	}

	public boolean hasChangesInDialog() {
		String currSettings = getEncodedSettings();
		return !currSettings.equals(fUserSettingsTimeStamp);
	}

	public boolean hasChangesInBuildpathFile() {
		IFile file = fCurrScriptProject.getProject().getFile(".buildpath"); //$NON-NLS-1$ 
		return fFileTimeStamp != file.getModificationStamp();
	}

	public void initializeTimeStamps() {
		IFile file = fCurrScriptProject.getProject().getFile(".buildpath"); //$NON-NLS-1$ 
		fFileTimeStamp = file.getModificationStamp();
		fUserSettingsTimeStamp = getEncodedSettings();
	}

	protected ArrayList getExistingEntries(IBuildpathEntry[] buildpathEntries) {
		ArrayList newBuildpath = new ArrayList();
		for (int i = 0; i < buildpathEntries.length; i++) {
			IBuildpathEntry curr = buildpathEntries[i];
			newBuildpath.add(BPListElement.createFromExisting(curr,
					fCurrScriptProject));
		}
		return newBuildpath;
	}

	// -------- public api --------
	/**
	 * @return Returns the script project. Can return
	 *         <code>null<code> if the page has not
	 * been initialized.
	 */
	public IScriptProject getScriptProject() {
		return fCurrScriptProject;
	}

	/**
	 * @return Returns the current class path (raw). Note that the entries
	 *         returned must not be valid.
	 */
	public IBuildpathEntry[] getRawBuildPath() {
		List elements = fBuildPathList.getElements();
		int nElements = elements.size();
		IBuildpathEntry[] entries = new IBuildpathEntry[elements.size()];
		for (int i = 0; i < nElements; i++) {
			BPListElement currElement = (BPListElement) elements.get(i);
			entries[i] = currElement.getBuildpathEntry();
		}
		return entries;
	}

	public int getPageIndex() {
		return fPageIndex;
	}

	// -------- evaluate default settings --------
	protected abstract IPreferenceStore getPreferenceStore();

	protected List getDefaultBuildpath(IScriptProject jproj) {
		List list = new ArrayList();
		IResource srcFolder;
		IPreferenceStore store = getPreferenceStore();
		if (store != null) {
			String sourceFolderName = store
					.getString(PreferenceConstants.SRC_SRCNAME);
			if (store.getBoolean(PreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ)
					&& sourceFolderName.length() > 0) {
				srcFolder = jproj.getProject().getFolder(sourceFolderName);
			} else {
				srcFolder = jproj.getProject();
			}
			list.add(new BPListElement(jproj, IBuildpathEntry.BPE_SOURCE,
					srcFolder.getFullPath(), srcFolder, false));
		}
		if (DLTKCore.DEBUG) {
			System.err.println("Add default library"); //$NON-NLS-1$ 
		}
		// IBuildpathEntry[] InterpreterEnvironmentEntries=
		// PreferenceConstants.getDefaultInterpreterEnvironmentLibrary();
		// list.addAll(getExistingEntries(InterpreterEnvironmentEntries));
		return list;
	}

	public class BuildPathAdapter implements IStringButtonAdapter,
			IDialogFieldListener {
		// -------- IStringButtonAdapter --------
		public void changeControlPressed(DialogField field) {
			buildPathChangeControlPressed(field);
		}

		// ---------- IDialogFieldListener --------
		public void dialogFieldChanged(DialogField field) {
			buildPathDialogFieldChanged(field);
		}
	}

	private void buildPathChangeControlPressed(DialogField field) {
		if (field == fBuildPathDialogField) {
			IContainer container = chooseContainer();
			if (container != null) {
				fBuildPathDialogField.setText(container.getFullPath()
						.toString());
			}
		}
	}

	private void buildPathDialogFieldChanged(DialogField field) {
		if (field == fBuildPathList) {
			updatePathStatus();
		}
		doStatusLineUpdate();
	}

	// -------- verification -------------------------------
	private void doStatusLineUpdate() {
		if (Display.getCurrent() != null) {
			IStatus res = findMostSevereStatus();
			fContext.statusChanged(res);
		}
	}

	private IStatus findMostSevereStatus() {
		return StatusUtil.getMostSevere(new IStatus[] { fPathStatus,
				fBuildPathStatus });
	}

	/**
	 * Validates the build path.
	 */
	public void updatePathStatus() {
		fPathStatus.setOK();
		List elements = fBuildPathList.getElements();
		BPListElement entryMissing = null;
		int nEntriesMissing = 0;

		BPListElement entryInvalid = null;
		int nEntriesInvalid = 0;

		IBuildpathEntry[] entries = new IBuildpathEntry[elements.size()];
		for (int i = elements.size() - 1; i >= 0; i--) {
			BPListElement currElement = (BPListElement) elements.get(i);

			entries[i] = currElement.getBuildpathEntry();
			if (currElement.isMissing()) {
				nEntriesMissing++;
				if (entryMissing == null) {
					entryMissing = currElement;
				}
			} else if (PharUIUtil.isInvalidPharBuildEntry(currElement)) {
				nEntriesInvalid++;
				if (entryInvalid == null) {
					entryInvalid = currElement;
				}
			}

		}
		if (nEntriesMissing > 0) {
			if (nEntriesMissing == 1) {
				fPathStatus.setWarning(Messages.format(
						NewWizardMessages.BuildPathsBlock_warning_EntryMissing,
						entryMissing.getPath().toString()));
			} else {
				fPathStatus
						.setWarning(Messages
								.format(NewWizardMessages.BuildPathsBlock_warning_EntriesMissing,
										String.valueOf(nEntriesMissing)));
			}
		}
		if (nEntriesInvalid > 0) {
			if (nEntriesInvalid == 1) {
				fPathStatus
						.setError(Messages
								.format(IncludePathMessages.BuildPathsBlock_warning_EntryInvalid,
										entryInvalid.getPath().toString()));
			} else {
				fPathStatus
						.setError(Messages
								.format(IncludePathMessages.BuildPathsBlock_warning_EntriesInvalid,
										String.valueOf(nEntriesInvalid)));
			}
		}

		updateBuildPathStatus();
	}

	protected void updateBuildPathStatus() {
		List elements = fBuildPathList.getElements();
		IBuildpathEntry[] entries = new IBuildpathEntry[elements.size()];
		for (int i = elements.size() - 1; i >= 0; i--) {
			BPListElement currElement = (BPListElement) elements.get(i);
			entries[i] = currElement.getBuildpathEntry();
		}
		IModelStatus status = BuildpathEntry.validateBuildpath(
				fCurrScriptProject, entries);
		if (!status.isOK()) {
			fBuildPathStatus.setError(status.getMessage());
			return;
		}
		fBuildPathStatus.setOK();
	}

	// -------- creation -------------------------------
	public static void createProject(IProject project, URI locationURI,
			IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask(
				NewWizardMessages.BuildPathsBlock_operationdesc_project, 100);
		// create the project
		try {
			if (!project.exists()) {
				IProjectDescription desc = project.getWorkspace()
						.newProjectDescription(project.getName());
				if (locationURI != null
						&& ResourcesPlugin.getWorkspace().getRoot()
								.getLocationURI().equals(locationURI)) {
					locationURI = null;
				}
				desc.setLocationURI(locationURI);
				project.create(desc, new SubProgressMonitor(monitor, 50));
			}
			if (!project.isOpen()) {
				project.open(new SubProgressMonitor(monitor, 50));
			}
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}
	}

	public static void addScriptNature(IProject project,
			IProgressMonitor monitor, String nature) throws CoreException {
		if (monitor != null && monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		if (!DLTKLanguageManager.hasScriptNature(project.getProject())) {
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = nature;
			if (DLTKCore.DEBUG) {
				System.err.println("Add selection of possible natures here"); //$NON-NLS-1$ 
			}
			description.setNatureIds(newNatures);
			project.setDescription(description, monitor);
		} else {
			if (monitor != null) {
				monitor.worked(1);
			}
		}
	}

	public void configureScriptProject(IProgressMonitor monitor)
			throws CoreException, OperationCanceledException {
		flush(fBuildPathList.getElements(), getScriptProject(), monitor);
		initializeTimeStamps();
		updateUI();
	}

	/*
	 * Creates the script project and sets the configured build path and output
	 * location. If the project already exists only build paths are updated.
	 */
	public static void flush(List buildpathEntries, IScriptProject javaProject,
			IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.setTaskName(NewWizardMessages.BuildPathsBlock_operationdesc_Script);
		monitor.beginTask("", buildpathEntries.size() * 4 + 4); //$NON-NLS-1$ 
		try {
			IProject project = javaProject.getProject();
			IPath projPath = project.getFullPath();
			monitor.worked(1);
			// IWorkspaceRoot fWorkspaceRoot =
			// DLTKUIPlugin.getWorkspace().getRoot();
			// create and set the output path first
			monitor.worked(1);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			int nEntries = buildpathEntries.size();
			IBuildpathEntry[] buildpath = new IBuildpathEntry[nEntries];
			int i = 0;
			for (Iterator iter = buildpathEntries.iterator(); iter.hasNext();) {
				BPListElement entry = (BPListElement) iter.next();
				buildpath[i] = entry.getBuildpathEntry();
				i++;
				IResource res = entry.getResource();
				// 1 tick
				if (res instanceof IFolder && entry.getLinkTarget() == null
						&& !res.exists()) {
					CoreUtility.createFolder((IFolder) res, true, true,
							new SubProgressMonitor(monitor, 1));
				} else {
					monitor.worked(1);
				}
				// 3 ticks
				if (entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
					monitor.worked(1);
					IPath path = entry.getPath();
					if (projPath.equals(path)) {
						monitor.worked(2);
						continue;
					}
					if (projPath.isPrefixOf(path)) {
						path = path
								.removeFirstSegments(projPath.segmentCount());
					}
					IFolder folder = project.getFolder(path);
					IPath orginalPath = entry.getOrginalPath();
					if (orginalPath == null) {
						if (!folder.exists()) {
							// New source folder needs to be created
							if (entry.getLinkTarget() == null) {
								CoreUtility.createFolder(folder, true, true,
										new SubProgressMonitor(monitor, 2));
							} else {
								folder.createLink(entry.getLinkTarget(),
										IResource.ALLOW_MISSING_LOCAL,
										new SubProgressMonitor(monitor, 2));
							}
						}
					} else {
						if (projPath.isPrefixOf(orginalPath)) {
							orginalPath = orginalPath
									.removeFirstSegments(projPath
											.segmentCount());
						}
						IFolder orginalFolder = project.getFolder(orginalPath);
						if (entry.getLinkTarget() == null) {
							if (!folder.exists()) {
								// Source folder was edited, move to new
								// location
								IPath parentPath = entry.getPath()
										.removeLastSegments(1);
								if (projPath.isPrefixOf(parentPath)) {
									parentPath = parentPath
											.removeFirstSegments(projPath
													.segmentCount());
								}
								if (parentPath.segmentCount() > 0) {
									IFolder parentFolder = project
											.getFolder(parentPath);
									if (!parentFolder.exists()) {
										CoreUtility.createFolder(parentFolder,
												true, true,
												new SubProgressMonitor(monitor,
														1));
									} else {
										monitor.worked(1);
									}
								} else {
									monitor.worked(1);
								}
								orginalFolder.move(entry.getPath(), true, true,
										new SubProgressMonitor(monitor, 1));
							}
						} else {
							if (!folder.exists()
									|| !entry.getLinkTarget().equals(
											entry.getOrginalLinkTarget())) {
								orginalFolder.delete(true,
										new SubProgressMonitor(monitor, 1));
								folder.createLink(entry.getLinkTarget(),
										IResource.ALLOW_MISSING_LOCAL,
										new SubProgressMonitor(monitor, 1));
							}
						}
					}
				} else {
					monitor.worked(3);
				}
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
			}
			javaProject.setRawBuildpath(buildpath, new SubProgressMonitor(
					monitor, 2));
		} finally {
			monitor.done();
		}
	}

	public static boolean hasClassfiles(IResource resource)
			throws CoreException {
		if (resource.isDerived()) {
			return true;
		}
		if (resource instanceof IContainer) {
			IResource[] members = ((IContainer) resource).members();
			for (int i = 0; i < members.length; i++) {
				if (hasClassfiles(members[i])) {
					return true;
				}
			}
		}
		return false;
	}

	public static void removeOldClassfiles(IResource resource)
			throws CoreException {
		if (resource.isDerived()) {
			resource.delete(false, null);
		} else if (resource instanceof IContainer) {
			IResource[] members = ((IContainer) resource).members();
			for (int i = 0; i < members.length; i++) {
				removeOldClassfiles(members[i]);
			}
		}
	}

	// ---------- util method ------------
	private IContainer chooseContainer() {
		Class[] acceptedClasses = new Class[] { IProject.class, IFolder.class };
		ISelectionStatusValidator validator = new TypedElementSelectionValidator(
				acceptedClasses, false);
		IProject[] allProjects = fWorkspaceRoot.getProjects();
		ArrayList rejectedElements = new ArrayList(allProjects.length);
		IProject currProject = fCurrScriptProject.getProject();
		for (int i = 0; i < allProjects.length; i++) {
			if (!allProjects[i].equals(currProject)) {
				rejectedElements.add(allProjects[i]);
			}
		}
		ViewerFilter filter = new TypedViewerFilter(acceptedClasses,
				rejectedElements.toArray());
		ILabelProvider lp = new WorkbenchLabelProvider();
		ITreeContentProvider cp = new WorkbenchContentProvider();
		IResource initSelection = null;
		FolderSelectionDialog dialog = new FolderSelectionDialog(getShell(),
				lp, cp);
		dialog.setTitle(NewWizardMessages.BuildPathsBlock_ChooseOutputFolderDialog_title);
		dialog.setValidator(validator);
		dialog.setMessage(NewWizardMessages.BuildPathsBlock_ChooseOutputFolderDialog_description);
		dialog.addFilter(filter);
		dialog.setInput(fWorkspaceRoot);
		dialog.setInitialSelection(initSelection);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		if (dialog.open() == Window.OK) {
			return (IContainer) dialog.getFirstResult();
		}
		return null;
	}

	// -------- tab switching ----------
	protected void tabChanged(Widget widget) {
		if (widget instanceof TabItem) {
			TabItem tabItem = (TabItem) widget;
			BuildPathBasePage newPage = (BuildPathBasePage) tabItem.getData();
			if (fCurrPage != null) {
				List selection = fCurrPage.getSelection();
				if (!selection.isEmpty()) {
					newPage.setSelection(selection, false);
				}
			}
			fCurrPage = newPage;
			fPageIndex = tabItem.getParent().getSelectionIndex();
		}
	}

	private int getPageIndex(int entryKind) {
		switch (entryKind) {
		case IBuildpathEntry.BPE_CONTAINER:
		case IBuildpathEntry.BPE_LIBRARY:
		case IBuildpathEntry.BPE_PROJECT:
			return 1;
		case IBuildpathEntry.BPE_SOURCE:
			return 0;
		}
		return 0;
	}

	private BPListElement findElement(IBuildpathEntry entry) {
		for (int i = 0, len = fBuildPathList.getSize(); i < len; i++) {
			BPListElement curr = (BPListElement) fBuildPathList.getElement(i);
			if (curr.getEntryKind() == entry.getEntryKind()
					&& curr.getPath().equals(entry.getPath())) {
				return curr;
			}
		}
		return null;
	}

	public void setElementToReveal(IBuildpathEntry entry, String attributeKey) {
		int pageIndex = getPageIndex(entry.getEntryKind());
		if (fTabFolder == null) {
			fPageIndex = pageIndex;
		} else {
			fTabFolder.setSelection(pageIndex);
			BPListElement element = findElement(entry);
			if (element != null) {
				Object elementToSelect = element;
				if (attributeKey != null) {
					Object attrib = element.findAttributeElement(attributeKey);
					if (attrib != null) {
						elementToSelect = attrib;
					}
				}
				BuildPathBasePage page = (BuildPathBasePage) fTabFolder
						.getItem(pageIndex).getData();
				List selection = new ArrayList(1);
				selection.add(elementToSelect);
				page.setSelection(selection, true);
			}
		}
	}

	public void addElement(IBuildpathEntry entry) {
		int pageIndex = getPageIndex(entry.getEntryKind());
		if (fTabFolder == null) {
			fPageIndex = pageIndex;
		} else {
			fTabFolder.setSelection(pageIndex);
			Object page = fTabFolder.getItem(pageIndex).getData();
			if (page instanceof PHPLibrariesWorkbookPage) {
				BPListElement element = BPListElement.createFromExisting(entry,
						fCurrScriptProject);
				((PHPLibrariesWorkbookPage) page).addElement(element);
			}
		}
	}
}
