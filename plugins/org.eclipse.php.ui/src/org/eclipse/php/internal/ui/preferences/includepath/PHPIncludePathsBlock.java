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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BuildPathBasePage;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.viewsupport.ImageDisposer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;
import org.eclipse.php.internal.core.includepath.IIncludepathListener;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.IChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * This class is responsible for the inner side of Include path property page.
 * Extends most of the dltk build path behavior with a few adaptations to the
 * include path needs
 * 
 * @author Eden K., 2008
 * 
 */
public class PHPIncludePathsBlock extends AbstractIncludepathsBlock {

	/**
	 * @author nir.c Wrapper composite, that un/register itself to any
	 *         includepath changes
	 */
	private final class IncludePathComposite extends Composite implements
			IIncludepathListener, IChangeListener {

		private IncludePathComposite(Composite parent, int style) {
			super(parent, style);
			IncludePathManager.getInstance().registerIncludepathListener(this);
		}

		@Override
		public void dispose() {
			if (fSourceContainerPage instanceof PHPIncludePathSourcePage) {
				PHPSourceContainerWorkbookPage page = (PHPSourceContainerWorkbookPage) fSourceContainerPage;
				page.unregisterAddedElementListener(this);
			}
			IncludePathManager.getInstance()
					.unregisterIncludepathListener(this);
			super.dispose();
		}

		public void refresh(IProject project) {
			PHPIncludePathsBlock.this.updateUI();
		}

		public void update(boolean changed) {
			try {
				configureScriptProject(new NullProgressMonitor());
				PHPIncludePathsBlock.this.updateUI();
			} catch (OperationCanceledException e) {
				PHPCorePlugin.log(e);
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}

		}
	}

	public PHPIncludePathsBlock(IRunnableContext runnableContext,
			IStatusChangeListener context, int pageToShow, boolean useNewPage,
			IWorkbenchPreferenceContainer pageContainer) {
		super(runnableContext, context, pageToShow, useNewPage, pageContainer);
	}

	protected void initContainerElements() {
		BuildPathAdapter adapter = new BuildPathAdapter();
		String[] buttonLabels = new String[] {
				NewWizardMessages.BuildPathsBlock_buildpath_up_button,
				NewWizardMessages.BuildPathsBlock_buildpath_down_button };
		fBuildPathList = new ListDialogField(null, buttonLabels,
				new PHPIPListLabelProvider());
		fBuildPathList.setDialogFieldListener(adapter);
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

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected boolean supportZips() {
		return true;
	}

	// -------- UI creation ---------
	public Control createControl(Composite parent) {

		fSWTWidget = parent;
		Composite composite = new IncludePathComposite(parent, SWT.NONE);

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

		fSourceContainerPage = new PHPIncludePathSourcePage(fBuildPathList);
		((PHPSourceContainerWorkbookPage) fSourceContainerPage)
				.registerAddedElementListener((IChangeListener) composite);

		item.setData(fSourceContainerPage);
		item.setControl(fSourceContainerPage.getControl(folder));

		IWorkbench workbench = DLTKUIPlugin.getDefault().getWorkbench();
		Image projectImage = workbench.getSharedImages().getImage(
				IDE.SharedImages.IMG_OBJ_PROJECT);
		fProjectsPage = new PHPProjectsWorkbookPage(fBuildPathList,
				fPageContainer);
		fProjectsPage
				.setTitle(PHPUIMessages.IncludePathProjectsPage_Folders_Label);
		item = new TabItem(folder, SWT.NONE);
		item.setText(NewWizardMessages.BuildPathsBlock_tab_projects);
		item.setImage(projectImage);
		item.setData(fProjectsPage);
		item.setControl(fProjectsPage.getControl(folder));
		fLibrariesPage = new PHPLibrariesWorkbookPage(this.supportZips(),
				fBuildPathList, fPageContainer);
		fLibrariesPage
				.setTitle(PHPUIMessages.IncludePathLibrariesPage_Folders_Label);
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
		setTitle(PHPUIMessages.IncludePathOrderPage_Folders_Label);
		item = new TabItem(folder, SWT.NONE);
		item.setText(PHPUIMessages.BuildPathsBlock_tab_order);
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

		// TODO - add listener to include path manager changes
		// upon change call updateUI();
		// and release listener on dispose

		Dialog.applyDialogFont(composite);
		return composite;
	}

	@Override
	protected void updateBuildPathStatus() {
		// disable checking for nested folders errors
	}

	public void configureScriptProject(IProgressMonitor monitor)
			throws CoreException, OperationCanceledException {
		updateBuildPath();
		flush(fBuildPathList.getElements(), getScriptProject(), monitor);
		initializeTimeStamps();
		updateUI();
	}

	/**
	 * The purpose of this method is to adapt the build path according to the
	 * entries added to the include path. If the user added to the include path
	 * source folders that are not in (or contained in) the build path, he will
	 * not get code completion and other functionality for this sources. THe
	 * user is prompted and asked if he wants to add the relevant sources to the
	 * build path as well see bug#255930
	 */
	private void updateBuildPath() {
		PHPIncludePathSourcePage includePathSourcePage = (PHPIncludePathSourcePage) fSourceContainerPage;

		boolean shouldAddToBuildPath = includePathSourcePage
				.shouldAddToBuildPath();
		if (!shouldAddToBuildPath) {
			return;
		}

		// get the source elements that the user added in the source tab
		List<BPListElement> addedElements = includePathSourcePage
				.getAddedElements();
		List<IBuildpathEntry> buildPathEntries = new ArrayList<IBuildpathEntry>();

		// in case there are any, the user is prompted with a question
		if (addedElements.size() > 0) {
			for (BPListElement listElement : addedElements) {
				if (!BuildPathUtils.isContainedInBuildpath(
						listElement.getPath(), fCurrScriptProject)) {
					buildPathEntries.add(listElement.getBuildpathEntry());
				}
			}

			// if the user chose to, the relevant entries are added to the
			// buildpath
			try {
				BuildPathUtils.addEntriesToBuildPath(fCurrScriptProject,
						buildPathEntries);
			} catch (ModelException e) {
				Logger.logException("Failed adding entries to build path", e); //$NON-NLS-1$
			}
		}
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

			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			List<IBuildpathEntry> newBuildPathEntries = new ArrayList<IBuildpathEntry>();
			List<IBuildpathEntry> newIncludePathEntries = new ArrayList<IBuildpathEntry>();

			// go over the dialog entries. collect all of the source entries for
			// the include path array
			// and the rest for the build path array
			for (Iterator<IBuildpathEntry> iter = buildpathEntries.iterator(); iter
					.hasNext();) {
				BPListElement entry = (BPListElement) iter.next();
				newIncludePathEntries.add(entry.getBuildpathEntry());
				if (entry.getEntryKind() != IBuildpathEntry.BPE_SOURCE) {
					newBuildPathEntries.add(entry.getBuildpathEntry());
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=307982
					// Add project in build path,press ok,then edit the
					// project's access rules will throw exception.
					BuildPathUtils.removeEntryFromBuildPath(javaProject,
							entry.getBuildpathEntry());
				}

				IResource res = entry.getResource();
				if (res instanceof IFolder && entry.getLinkTarget() == null
						&& !res.exists()) {
					CoreUtility.createFolder((IFolder) res, true, true,
							new SubProgressMonitor(monitor, 1));
				} else {
					monitor.worked(1);
				}
			}
			if (newBuildPathEntries.size() > 0) {
				// BuildPathUtils.removeEntryFromBuildPath(scriptProject,
				// buildpathEntry)
				BuildPathUtils.addEntriesToBuildPath(javaProject,
						newBuildPathEntries);
			}
			IncludePathManager.getInstance().addEntriesToIncludePath(project,
					newIncludePathEntries);

		} finally {
			monitor.done();
		}
	}

	/**
	 * Initializes the include path for the given project. Multiple calls to
	 * init are allowed, but all existing settings will be cleared and replace
	 * by the given or default paths.
	 * 
	 * @param jproject
	 *            The java project to configure. Does not have to exist.
	 * @param outputLocation
	 *            The output location to be set in the page. If
	 *            <code>null</code> is passed, jdt default settings are used, or
	 *            - if the project is an existing script project- the output
	 *            location of the existing project
	 * @param buildpathEntries
	 *            The include path entries to be set in the page. If
	 *            <code>null</code> is passed, jdt default settings are used, or
	 *            - if the project is an existing script project - the buildpath
	 *            entries of the existing project
	 */
	public void init(IScriptProject jproject, IBuildpathEntry[] buildpathEntries) {
		fCurrScriptProject = jproject;

		List<BPListElement> newBuildpath = new ArrayList<BPListElement>();
		IProject project = fCurrScriptProject.getProject();

		IncludePath[] includePathEntries = IncludePathManager.getInstance()
				.getIncludePaths(project);
		for (IncludePath entry : includePathEntries) {
			Object includePathEntry = entry.getEntry();

			if (includePathEntry instanceof IBuildpathEntry) {
				IBuildpathEntry bpEntry = (IBuildpathEntry) includePathEntry;
				newBuildpath.add(BPListElement.createFromExisting(bpEntry,
						fCurrScriptProject));
			} else {
				IResource resource = (IResource) includePathEntry;
				newBuildpath.add(new BPListElement(fCurrScriptProject,
						IBuildpathEntry.BPE_SOURCE, resource.getFullPath(),
						resource, false));
			}

		}

		// inits the dialog field
		fBuildPathDialogField.enableButton(project.exists());
		fBuildPathList.setElements(newBuildpath);
		// fBuildPathList.setCheckedElements(newBuildpath);
		initializeTimeStamps();
		updateUI();
	}

}
