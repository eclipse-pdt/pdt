/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.preferences.includepath;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.internal.ui.actions.StatusInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.IStatusChangeListener;
import org.eclipse.php.ui.util.ImageDisposer;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.php.ui.util.PixelConverter;
import org.eclipse.php.ui.wizards.fields.DialogField;
import org.eclipse.php.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class IncludePathBlock {

	private IWorkspaceRoot fWorkspaceRoot;

	private ListDialogField fIncludePathList;

	private StatusInfo fIncludePathStatus;

	private IProject fCurrProject;
	private PHPProjectOptions phpOptions = null;

	private IStatusChangeListener fContext;
	private Control fSWTWidget;
	private TabFolder fTabFolder;

	private int fPageIndex;

	private ProjectsWorkbookPage fProjectsPage;
	private LibrariesWorkbookPage fLibrariesPage;

	private IncludePathBasePage fCurrPage;

	private String fUserSettingsTimeStamp;
	private long fFileTimeStamp;

	private IRunnableContext fRunnableContext;
	private boolean fUseNewPage;

	private final IWorkbenchPreferenceContainer fPageContainer; // null when invoked from a non-property page context

	public IncludePathBlock(IRunnableContext runnableContext, IStatusChangeListener context, int pageToShow, boolean useNewPage, IWorkbenchPreferenceContainer pageContainer) {
		fPageContainer = pageContainer;
		fWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		fContext = context;
		fUseNewPage = useNewPage;

		fPageIndex = pageToShow;

		fLibrariesPage = null;
		fProjectsPage = null;
		fCurrPage = null;
		fRunnableContext = runnableContext;

		IncludePathAdapter adapter = new IncludePathAdapter();

		String[] buttonLabels = new String[] { PHPUIMessages.IncludePathsBlock_includepath_up_button, PHPUIMessages.IncludePathsBlock_includepath_down_button };

		fIncludePathList = new ListDialogField(null, buttonLabels, new IPListLabelProvider());
		fIncludePathList.setDialogFieldListener(adapter);
		fIncludePathList.setLabelText(PHPUIMessages.IncludePathsBlock_includepath_label);
		fIncludePathList.setUpButtonIndex(0);
		fIncludePathList.setDownButtonIndex(1);

		fIncludePathStatus = new StatusInfo();

		fCurrProject = null;
		phpOptions = null;
	}

	// -------- UI creation ---------

	public Control createControl(Composite parent) {
		fSWTWidget = parent;

		PixelConverter converter = new PixelConverter(parent);

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

		IWorkbench workbench = PHPUiPlugin.getDefault().getWorkbench();
		Image projectImage = workbench.getSharedImages().getImage(IDE.SharedImages.IMG_OBJ_PROJECT);

		fProjectsPage = new ProjectsWorkbookPage(fIncludePathList, fPageContainer);
		item = new TabItem(folder, SWT.NONE);
		item.setText(PHPUIMessages.IncludePathsBlock_tab_projects);
		item.setImage(projectImage);
		item.setData(fProjectsPage);
		item.setControl(fProjectsPage.getControl(folder));

		fLibrariesPage = new LibrariesWorkbookPage(fIncludePathList, fPageContainer);
		item = new TabItem(folder, SWT.NONE);
		item.setText(PHPUIMessages.IncludePathsBlock_tab_libraries);
		item.setImage(PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY));
		item.setData(fLibrariesPage);
		item.setControl(fLibrariesPage.getControl(folder));

		// a non shared image
		Image cpoImage = PHPPluginImages.DESC_TOOL_INCLUDEPATH_ORDER.createImage();
		composite.addDisposeListener(new ImageDisposer(cpoImage));

		IncludePathOrderingWorkbookPage ordpage = new IncludePathOrderingWorkbookPage(fIncludePathList);
		item = new TabItem(folder, SWT.NONE);
		item.setText(PHPUIMessages.IncludePathsBlock_tab_order);
		item.setImage(cpoImage);
		item.setData(ordpage);
		item.setControl(ordpage.getControl(folder));

		if (fCurrProject != null) {
			fLibrariesPage.init(fCurrProject);
			fProjectsPage.init(fCurrProject);
		}

		folder.setSelection(fPageIndex);
		fCurrPage = (IncludePathBasePage) folder.getItem(fPageIndex).getData();
		folder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tabChanged(e.item);
			}
		});
		fTabFolder = folder;

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IPHPHelpContextIds.INCLUDE_PATH_BLOCK);
		Dialog.applyDialogFont(composite);
		return composite;
	}

	private Shell getShell() {
		if (fSWTWidget != null) {
			return fSWTWidget.getShell();
		}
		return PHPUiPlugin.getActiveWorkbenchShell();
	}

	/**
	 * Initializes the includepath for the given project. Multiple calls to init are allowed,
	 * but all existing settings will be cleared and replace by the given or default paths.
	 * @param phpProject The  project to configure. Does not have to exist.
	 * @param outputLocation The output location to be set in the page. If <code>null</code>
	 * is passed, jdt default settings are used, or - if the project is an existing  project- the
	 * output location of the existing project 
	 * @param includepathEntries The includepath entries to be set in the page. If <code>null</code>
	 * is passed, jdt default settings are used, or - if the project is an existing  project - the
	 * includepath entries of the existing project
	 */
	public void init(IProject phpProject, IIncludePathEntry[] includepathEntries) {
		fCurrProject = phpProject;
		phpOptions = PHPProjectOptions.forProject(fCurrProject);
		boolean projectExists = false;
		List newIncludePath = null;
		IProject project = fCurrProject.getProject();
		projectExists = (project.exists() && project.getFile(PHPProjectOptions.FILE_NAME).exists()); //$NON-NLS-1$
		if (projectExists) {
			if (includepathEntries == null) {
				includepathEntries = phpOptions.readRawIncludePath();
			}
		}

		if (includepathEntries != null) {
			newIncludePath = getExistingEntries(includepathEntries);
		}
		if (newIncludePath == null) {
			newIncludePath = getDefaultIncludePath(phpProject);
		}

		fIncludePathList.setElements(newIncludePath);

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
		fIncludePathList.refresh();

		if (fProjectsPage != null) {
			fProjectsPage.init(fCurrProject);
			fLibrariesPage.init(fCurrProject);
		}
		doStatusLineUpdate();
	}

	private String getEncodedSettings() {
		StringBuffer buf = new StringBuffer();

		int nElements = fIncludePathList.getSize();
		buf.append('[').append(nElements).append(']');
		for (int i = 0; i < nElements; i++) {
			IPListElement elem = (IPListElement) fIncludePathList.getElement(i);
			elem.appendEncodedSettings(buf);
		}
		return buf.toString();
	}

	public boolean hasChangesInDialog() {
		String currSettings = getEncodedSettings();
		return !currSettings.equals(fUserSettingsTimeStamp);
	}

	public boolean hasChangesInIncludePathFile() {
		IFile file = fCurrProject.getProject().getFile(PHPProjectOptions.FILE_NAME); //$NON-NLS-1$
		return fFileTimeStamp != file.getModificationStamp();
	}

	public void initializeTimeStamps() {
		IFile file = fCurrProject.getProject().getFile(PHPProjectOptions.FILE_NAME); //$NON-NLS-1$
		fFileTimeStamp = file.getModificationStamp();
		fUserSettingsTimeStamp = getEncodedSettings();
	}

	private ArrayList getExistingEntries(IIncludePathEntry[] includepathEntries) {
		ArrayList newIncludePath = new ArrayList();
		for (int i = 0; i < includepathEntries.length; i++) {
			IIncludePathEntry curr = includepathEntries[i];
			newIncludePath.add(IPListElement.createFromExisting(curr, fCurrProject));
		}
		return newIncludePath;
	}

	// -------- public api --------

	/**
	 * @return Returns the  project. Can return <code>null<code> if the page has not
	 * been initialized.
	 */
	public IProject getProject() {
		return fCurrProject;
	}

	/**
	 *  @return Returns the current include path (raw). Note that the entries returned must not be valid.
	 */
	public IIncludePathEntry[] getRawIncludePath() {
		List elements = fIncludePathList.getElements();
		int nElements = elements.size();
		IIncludePathEntry[] entries = new IIncludePathEntry[elements.size()];

		for (int i = 0; i < nElements; i++) {
			IPListElement currElement = (IPListElement) elements.get(i);
			entries[i] = currElement.getIncludePathEntry();
		}
		return entries;
	}

	public int getPageIndex() {
		return fPageIndex;
	}

	// -------- evaluate default settings --------

	private List getDefaultIncludePath(IProject jproj) {
		List list = new ArrayList();
		return list;
	}

	private class IncludePathAdapter implements IDialogFieldListener {

		// -------- IStringButtonAdapter --------
		public void changeControlPressed(DialogField field) {
		}

		// ---------- IDialogFieldListener --------
		public void dialogFieldChanged(DialogField field) {
			includePathDialogFieldChanged(field);
		}
	}

	private void includePathDialogFieldChanged(DialogField field) {
		if (field == fIncludePathList) {
			updateIncludePathStatus();
		}
		doStatusLineUpdate();
	}

	// -------- verification -------------------------------

	private void doStatusLineUpdate() {
		if (Display.getCurrent() != null) {
			IStatus res = fIncludePathStatus;
			fContext.statusChanged(res);
		}
	}

	/**
	 * Validates the include path.
	 */
	public void updateIncludePathStatus() {
		fIncludePathStatus.setOK();

		List elements = fIncludePathList.getElements();

		IPListElement entryMissing = null;
		int nEntriesMissing = 0;
		IIncludePathEntry[] entries = new IIncludePathEntry[elements.size()];

		for (int i = elements.size() - 1; i >= 0; i--) {
			IPListElement currElement = (IPListElement) elements.get(i);

			/*
			 boolean isChecked = fIncludePathList.isChecked(currElement);
			 if (currElement.getEntryKind() == IIncludePathEntry.IPE_SOURCE) {
			 if (!isChecked) {
			 fIncludePathList.setCheckedWithoutUpdate(currElement, true);
			 }
			 } else {
			 currElement.setExported(isChecked);
			 }
			 */

			entries[i] = currElement.getIncludePathEntry();
			if (currElement.isMissing()) {
				nEntriesMissing++;
				if (entryMissing == null) {
					entryMissing = currElement;
				}
			}
		}

		if (nEntriesMissing > 0) {
			if (nEntriesMissing == 1) {
				fIncludePathStatus.setWarning(MessageFormat.format(PHPUIMessages.IncludePathsBlock_warning_EntryMissing, new String[] { entryMissing.getPath().toString() }));
			} else {
				fIncludePathStatus.setWarning(MessageFormat.format(PHPUIMessages.IncludePathsBlock_warning_EntriesMissing, new String[] { String.valueOf(nEntriesMissing) }));
			}
		}

		/*		if (fCurrJProject.hasIncludePathCycle(entries)) {
		 fIncludePathStatus.setWarning(PHPUIMessages.getString("IncludePathsBlock.warning.CycleInIncludePath")); //$NON-NLS-1$
		 }
		 */
	}

	// -------- creation -------------------------------

	public static void createProject(IProject project, IPath locationPath, IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask(PHPUIMessages.IncludePathsBlock_operationdesc_project, 10);

		// create the project
		try {
			if (!project.exists()) {
				IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
				if (Platform.getLocation().equals(locationPath)) {
					locationPath = null;
				}
				desc.setLocation(locationPath);
				project.create(desc, monitor);
				monitor = null;
			}
			if (!project.isOpen()) {
				project.open(monitor);
				monitor = null;
			}
		} finally {
			if (monitor != null) {
				monitor.done();
			}
		}
	}

	public static void addPHPNature(IProject project, IProgressMonitor monitor) throws CoreException {
		if (monitor != null && monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		if (!project.hasNature(PHPNature.ID)) {
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = PHPNature.ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, monitor);
		} else {
			monitor.worked(1);
		}
	}

	public void configurePHPProject(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.setTaskName(PHPUIMessages.IncludePathsBlock_operationdesc_php);
		monitor.beginTask("", 10); //$NON-NLS-1$
		try {

			internalConfigurePHPProject(fIncludePathList.getElements(), monitor);
		} finally {
			monitor.done();
		}
		updateUI();
	}

	public IIncludePathEntry[] getIncludepathEntries() {
		List includePathEntries = fIncludePathList.getElements();
		int nEntries = includePathEntries.size();
		IIncludePathEntry[] includepath = new IIncludePathEntry[nEntries];
		for (int i = 0; i < nEntries; i++) {
			IPListElement entry = ((IPListElement) includePathEntries.get(i));
			includepath[i] = entry.getIncludePathEntry();
		}
		return includepath;
	}

	/*
	 * Creates the PHP project and sets the configured include path and output location.
	 * If the project already exists only include paths are updated.
	 */
	private void internalConfigurePHPProject(List includePathEntries, IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		// 10 monitor steps to go

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		monitor.worked(2);

		int nEntries = includePathEntries.size();
		IIncludePathEntry[] includepath = new IIncludePathEntry[nEntries];

		// create and set the include path
		for (int i = 0; i < nEntries; i++) {
			IPListElement entry = ((IPListElement) includePathEntries.get(i));
			IResource res = entry.getResource();
			if ((res instanceof IFolder) && !res.exists()) {
				createFolder((IFolder) res, true, true, null);
			}

			includepath[i] = entry.getIncludePathEntry();
		}

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		monitor.worked(1);

		phpOptions.setRawIncludePath(includepath, new SubProgressMonitor(monitor, 7));
		initializeTimeStamps();
	}

	private void createFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor) throws CoreException {
		if (!folder.exists()) {
			IContainer parent = folder.getParent();
			if (parent instanceof IFolder) {
				createFolder((IFolder) parent, force, local, null);
			}
			folder.create(force, local, monitor);
		}
	}

	public static boolean hasClassfiles(IResource resource) throws CoreException {
		if (resource.isDerived()) { //$NON-NLS-1$
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

	public static void removeOldClassfiles(IResource resource) throws CoreException {
		if (resource.isDerived()) {
			resource.delete(false, null);
		} else if (resource instanceof IContainer) {
			IResource[] members = ((IContainer) resource).members();
			for (int i = 0; i < members.length; i++) {
				removeOldClassfiles(members[i]);
			}
		}
	}

	// -------- tab switching ----------

	private void tabChanged(Widget widget) {
		if (widget instanceof TabItem) {
			TabItem tabItem = (TabItem) widget;
			IncludePathBasePage newPage = (IncludePathBasePage) tabItem.getData();
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
			case IIncludePathEntry.IPE_CONTAINER:
			case IIncludePathEntry.IPE_LIBRARY:
			case IIncludePathEntry.IPE_VARIABLE:
				return 1;
			case IIncludePathEntry.IPE_PROJECT:
				return 0;
		}
		return 0;
	}

	private IPListElement findElement(IIncludePathEntry entry) {
		for (int i = 0, len = fIncludePathList.getSize(); i < len; i++) {
			IPListElement curr = (IPListElement) fIncludePathList.getElement(i);
			if (curr.getEntryKind() == entry.getEntryKind() && curr.getPath().equals(entry.getPath())) {
				return curr;
			}
		}
		return null;
	}

	public void setElementToReveal(IIncludePathEntry entry, String attributeKey) {
		int pageIndex = getPageIndex(entry.getEntryKind());
		if (fTabFolder == null) {
			fPageIndex = pageIndex;
		} else {
			fTabFolder.setSelection(pageIndex);
			IPListElement element = findElement(entry);
			if (element != null) {
				Object elementToSelect = element;

				if (attributeKey != null) {
					Object attrib = element.findAttributeElement(attributeKey);
					if (attrib != null) {
						elementToSelect = attrib;
					}
				}
				IncludePathBasePage page = (IncludePathBasePage) fTabFolder.getItem(pageIndex).getData();
				List selection = new ArrayList(1);
				selection.add(elementToSelect);
				page.setSelection(selection, true);
			}
		}
	}
}
