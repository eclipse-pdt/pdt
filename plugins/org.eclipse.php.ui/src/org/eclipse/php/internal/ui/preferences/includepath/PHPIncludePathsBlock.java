/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.*;
import org.eclipse.dltk.internal.ui.wizards.buildpath.newsourcepage.NewSourceContainerWorkbookPage;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.viewsupport.ImageDisposer;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
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
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * This class is responsible for the inner side of Include path property page.
 * Extends most of the dltk build path behavior with a few adaptations to the include path needs  
 * @author Eden K., 2008
 *
 */
public class PHPIncludePathsBlock extends TempBuildpathsBlock {

	public PHPIncludePathsBlock(IRunnableContext runnableContext, IStatusChangeListener context, int pageToShow, boolean useNewPage, IWorkbenchPreferenceContainer pageContainer) {
		super(runnableContext, context, pageToShow, useNewPage, pageContainer);
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected boolean supportZips() {
		return false;
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
		item.setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_PACKFRAG_ROOT));
		if (fUseNewPage) {
			fSourceContainerPage = new NewSourceContainerWorkbookPage(fBuildPathList, fRunnableContext, getPreferenceStore());
		} else {
			fSourceContainerPage = new PHPIncludePathSourcePage(fBuildPathList);
		}
		item.setData(fSourceContainerPage);
		item.setControl(fSourceContainerPage.getControl(folder));

		IWorkbench workbench = DLTKUIPlugin.getDefault().getWorkbench();
		Image projectImage = workbench.getSharedImages().getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
		fProjectsPage = new ProjectsWorkbookPage(fBuildPathList, fPageContainer);
		item = new TabItem(folder, SWT.NONE);
		item.setText(NewWizardMessages.BuildPathsBlock_tab_projects);
		item.setImage(projectImage);
		item.setData(fProjectsPage);
		item.setControl(fProjectsPage.getControl(folder));
		fLibrariesPage = new LibrariesWorkbookPage(this.supportZips(), fBuildPathList, fPageContainer);
		fLibrariesPage.setScriptProject(getScriptProject());
		item = new TabItem(folder, SWT.NONE);
		item.setText(NewWizardMessages.BuildPathsBlock_tab_libraries);
		item.setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_LIBRARY));
		item.setData(fLibrariesPage);
		item.setControl(fLibrariesPage.getControl(folder));
		// a non shared image
		Image cpoImage = DLTKPluginImages.DESC_TOOL_BUILDPATH_ORDER.createImage();
		composite.addDisposeListener(new ImageDisposer(cpoImage));
		BuildpathOrderingWorkbookPage ordpage = new BuildpathOrderingWorkbookPage(fBuildPathList);
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

	@Override
	protected void updateBuildPathStatus() {
		// disable checking for nested folders
	}

	public void configureScriptProject(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		flush(fBuildPathList.getElements(), getScriptProject(), monitor);
		initializeTimeStamps();
		updateUI();
	}

	/*
	 * Creates the script project and sets the configured build path and output
	 * location. If the project already exists only build paths are updated.
	 */
	public static void flush(List buildpathEntries, IScriptProject javaProject, IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.setTaskName(NewWizardMessages.BuildPathsBlock_operationdesc_Script);
		monitor.beginTask("", buildpathEntries.size() * 4 + 4); //$NON-NLS-1$
		try {
			IProject project = javaProject.getProject();
			IPath projPath = project.getFullPath();
			monitor.worked(1);

			monitor.worked(1);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			List<IBuildpathEntry> newBuildPathEntries = new ArrayList<IBuildpathEntry>();
			List<IBuildpathEntry> newIncludePathEntries = new ArrayList<IBuildpathEntry>();

			// go over the dialog entries. collect all of the source entries for the include path array 
			// and the rest for the build path array
			for (Iterator<IBuildpathEntry> iter = buildpathEntries.iterator(); iter.hasNext();) {
				BPListElement entry = (BPListElement) iter.next();
				if (entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
					newIncludePathEntries.add(entry.getBuildpathEntry());

				} else {
					newBuildPathEntries.add(entry.getBuildpathEntry());
				}

				IResource res = entry.getResource();
				// 1 tick
				if (res instanceof IFolder && entry.getLinkTarget() == null && !res.exists()) {
					CoreUtility.createFolder((IFolder) res, true, true, new SubProgressMonitor(monitor, 1));
				} else {
					monitor.worked(1);
				}
				// 3 ticks
				//				if (entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
				//					monitor.worked(1);
				//					IPath path = entry.getPath();
				//					if (projPath.equals(path)) {
				//						monitor.worked(2);
				//						continue;
				//					}
				//					if (projPath.isPrefixOf(path)) {
				//						path = path
				//								.removeFirstSegments(projPath.segmentCount());
				//					}
				//					IFolder folder = project.getFolder(path);
				//					IPath orginalPath = entry.getOrginalPath();
				//					if (orginalPath == null) {
				//						if (!folder.exists()) {
				//							// New source folder needs to be created
				//							if (entry.getLinkTarget() == null) {
				//								CoreUtility.createFolder(folder, true, true,
				//										new SubProgressMonitor(monitor, 2));
				//							} else {
				//								folder.createLink(entry.getLinkTarget(),
				//										IResource.ALLOW_MISSING_LOCAL,
				//										new SubProgressMonitor(monitor, 2));
				//							}
				//						}
				//					} else {
				//						if (projPath.isPrefixOf(orginalPath)) {
				//							orginalPath = orginalPath
				//									.removeFirstSegments(projPath
				//											.segmentCount());
				//						}
				//						IFolder orginalFolder = project.getFolder(orginalPath);
				//						if (entry.getLinkTarget() == null) {
				//							if (!folder.exists()) {
				//								// Source folder was edited, move to new
				//								// location
				//								IPath parentPath = entry.getPath()
				//										.removeLastSegments(1);
				//								if (projPath.isPrefixOf(parentPath)) {
				//									parentPath = parentPath
				//											.removeFirstSegments(projPath
				//													.segmentCount());
				//								}
				//								if (parentPath.segmentCount() > 0) {
				//									IFolder parentFolder = project
				//											.getFolder(parentPath);
				//									if (!parentFolder.exists()) {
				//										CoreUtility.createFolder(parentFolder,
				//												true, true,
				//												new SubProgressMonitor(monitor,
				//														1));
				//									} else {
				//										monitor.worked(1);
				//									}
				//								} else {
				//									monitor.worked(1);
				//								}
				//								orginalFolder.move(entry.getPath(), true, true,
				//										new SubProgressMonitor(monitor, 1));
				//							}
				//						} else {
				//							if (!folder.exists()
				//									|| !entry.getLinkTarget().equals(
				//											entry.getOrginalLinkTarget())) {
				//								orginalFolder.delete(true,
				//										new SubProgressMonitor(monitor, 1));
				//								folder.createLink(entry.getLinkTarget(),
				//										IResource.ALLOW_MISSING_LOCAL,
				//										new SubProgressMonitor(monitor, 1));
				//							}
				//						}
				//					}
				//				} else {
				//					monitor.worked(3);
				//				}
				//				if (monitor.isCanceled()) {
				//					throw new OperationCanceledException();
				//				}
			}
			addEntriesToBuildPath(javaProject, newBuildPathEntries);
			addEntriesToIncludePath(project, newIncludePathEntries);

		} finally {
			monitor.done();
		}
	}

	/**
	 * Create the include path based on the elements in the source tab of this dialog 
	 * @param scriptProject
	 * @param entries
	 * @throws ModelException
	 */
	private static void addEntriesToIncludePath(IProject project, List<IBuildpathEntry> entries) {
		IncludePathManager includePathManager = IncludePathManager.getInstance();

		List<IncludePath> includePathEntries = new ArrayList<IncludePath>();
		for (IBuildpathEntry buildpathEntry : entries) {
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(buildpathEntry.getPath());
			if (resource != null) {
				includePathEntries.add(new IncludePath(resource));
			}
		}
		// update the include path for this project
		includePathManager.setIncludePath(project, includePathEntries.toArray(new IncludePath[includePathEntries.size()]));
	}

	/**
	 * Recreate the build path based on the current sources in it and the additional projects/libraries
	 * added in this dialog
	 * @param scriptProject
	 * @param entries
	 * @throws ModelException
	 */
	private static void addEntriesToBuildPath(IScriptProject scriptProject, List<IBuildpathEntry> entries) throws ModelException {
		IBuildpathEntry[] rawBuildpath = scriptProject.getRawBuildpath();

		// get the current buildpath entries, in order to add/remove entries
		List<IBuildpathEntry> newRawBuildpath = new ArrayList<IBuildpathEntry>();

		// get all of the source folders from the existing build path 
		for (IBuildpathEntry buildpathEntry : rawBuildpath) {
			if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
				newRawBuildpath.add(buildpathEntry);
			}
		}

		// add all of the non-source entries added in this dialog
		for (IBuildpathEntry buildpathEntry : entries) {
			newRawBuildpath.add(buildpathEntry);
		}

		// set the new updated buildpath for the project		
		scriptProject.setRawBuildpath(newRawBuildpath.toArray(new IBuildpathEntry[newRawBuildpath.size()]), null);

	}

}
