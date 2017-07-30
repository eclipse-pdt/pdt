/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import static org.eclipse.php.phpunit.ui.launch.PHPUnitLaunchAttributes.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.filters.ClosedProjectFilter;
import org.eclipse.dltk.internal.ui.filters.DotFileFilter;
import org.eclipse.dltk.internal.ui.filters.LibraryFilter;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.filters.RSEProjectFilter;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.preference.PHPUnitPreferenceKeys;
import org.eclipse.php.phpunit.ui.preference.PHPUnitPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

public class PHPUnitLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	protected static PHPUnitLaunchConfigurationTab theTab;

	public static PHPUnitLaunchConfigurationTab getCurrent() {
		if (theTab == null)
			theTab = new PHPUnitLaunchConfigurationTab();
		return theTab;
	}

	private Button fCodeCoverageButton;
	private Button fReportingButton;
	private Text fPhpunitConfigPath;

	private Button fSingleTestRadioButton;
	private Button fProjButton;
	private Label fProjLabel;
	private Text fProjText;
	private String previousSelectedProjectText;
	private Button fSearchButton;
	private Label fTestFileLabel;
	private Label fTestLabel;
	private Text fTestText;

	private Button fTestContainerRadioButton;
	private IResource fContainerElement;
	private Button fContainerSearchButton;
	private Text fContainerText;

	private Button fUsePharButton;
	private Button fUseComposerButton;

	private IProject fSelectedProject;

	private String fElementPath = ""; //$NON-NLS-1$

	private PHPUnitTestElementFinder testElementFinder = new PHPUnitTestElementFinder();
	private final ILabelProvider fPHPElementLabelProvider = new PHPUnitTreeLabelProvider();

	private final Image fTestIcon = PHPUnitPlugin.createImage("main.png"); //$NON-NLS-1$

	protected static class PHPUnitTreeLabelProvider extends LabelProvider {
		private Image fScriptFolder;
		private Image fClassImage;
		private Image fPHPProject;
		private Image fPHPFile;

		public PHPUnitTreeLabelProvider() {
			fClassImage = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
			fScriptFolder = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_FOLDER);
			fPHPProject = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_PROJECT);
			fPHPFile = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_FILE);
		}

		@Override
		public String getText(Object element) {
			if (element != null) {
				String elementName = ""; //$NON-NLS-1$
				if (element instanceof IModelElement) {
					IModelElement modelElement = (IModelElement) element;
					elementName = modelElement.getElementName();
				}
				return elementName;
			}
			return ""; //$NON-NLS-1$
		}

		@Override
		public Image getImage(Object element) {
			Image result = null;
			if (element != null && element instanceof IModelElement) {

				if (element instanceof IScriptProject) {
					return fPHPProject;
				}
				if (element instanceof IScriptFolder || element instanceof IProjectFragment) {
					result = fScriptFolder;
				}
				if (element instanceof ISourceModule) {
					result = fPHPFile;
				}
				if (element instanceof IType) {
					result = fClassImage;
				}
			}
			return result;
		}
	};

	public PHPUnitLaunchConfigurationTab() {
		theTab = this;
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration config) {
		try {
			boolean isContainer = config.getAttribute(ATTRIBUTE_RUN_CONTAINER, false);
			String container = config.getAttribute(ATTRIBUTE_CONTAINER, StringUtils.EMPTY);
			String containerTypeName = config.getAttribute(ATTRIBUTE_CONTAINER_TYPE, StringUtils.EMPTY);
			String projectName = config.getAttribute(ATTRIBUTE_PROJECT, StringUtils.EMPTY);
			String testClassName = config.getAttribute(ATTRIBUTE_CLASS, StringUtils.EMPTY);
			String testFileName = config.getAttribute(ATTRIBUTE_FILE, StringUtils.EMPTY);

			boolean codeCoverage = config.getAttribute(ATTRIBUTE_CODE_COVERAGE, false);
			boolean xmlReporting = config.getAttribute(ATTRIBUTE_LOG_XML, false);
			String xmlUnitConfig = config.getAttribute(ATTRIBUTE_PHPUNIT_CFG, StringUtils.EMPTY);

			String executionType = config.getAttribute(ATTRIBUTE_EXECUTION_TYPE, PHAR_EXECUTION_TYPE);

			fSingleTestRadioButton.setSelection(!isContainer);
			fTestContainerRadioButton.setSelection(isContainer);
			setEnableSingleTestGroup(!isContainer);
			setEnableContainerTestGroup(isContainer);
			if (isContainer && !projectName.isEmpty()) {
				IResource containerElement = null;
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

				if (!container.isEmpty()) {
					if (SOURCE_CONTAINER.equals(containerTypeName)) {
						final IFile file = project.getFile(container);
						containerElement = file;
					}

					if (FOLDER_CONTAINER.equals(containerTypeName)) {
						final IFolder folder = project.getFolder(container);
						containerElement = folder;
					}
				}

				if (PROJECT_CONTAINER.equals(containerTypeName)) {
					containerElement = project;
				}

				if (containerElement != null) {
					fContainerElement = containerElement;
					fContainerText.setText(fContainerElement.getFullPath().toPortableString());
				}

				if (fContainerElement == null) {
					testModeChanged();
				}
				fTestText.setText(StringUtils.EMPTY);
				fTestFileLabel.setText(StringUtils.EMPTY);
			} else {
				fProjText.setText(projectName);
				fTestText.setText(testClassName);
				fTestFileLabel.setText(testFileName);
				fContainerText.setText(StringUtils.EMPTY);
			}

			fPhpunitConfigPath.setText(xmlUnitConfig);
			fCodeCoverageButton.setSelection(codeCoverage);
			fReportingButton.setSelection(xmlReporting);

			fUsePharButton.setSelection(executionType.equals(PHAR_EXECUTION_TYPE));
			fUseComposerButton.setSelection(!fUsePharButton.getSelection());
		} catch (final CoreException ce) {
		}
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		comp.setLayout(new GridLayout());
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		createElementGroup(comp);
		createAdditionalGroup(comp);
		createExecutionGroup(comp);
	}

	private String choosePHPUnitConfig() {
		String initialPath = fPhpunitConfigPath.getText();
		IContainer root = fSelectedProject;

		String pathStr = OpenFileDialog.openFile(getShell(), root, PHPUnitMessages.PHPUnitLaunchConfigurationTab_2,
				PHPUnitMessages.PHPUnitLaunchConfigurationTab_3, initialPath);

		if (pathStr == null) {
			return StringUtils.EMPTY;
		}

		IPath path = new Path(pathStr);
		return path.removeFirstSegments(1).toOSString();
	}

	private void notifyParameterChanged() {
		validatePage();
		updateLaunchConfigurationDialog();
	}

	/**
	 * @param comp
	 */
	private void createAdditionalGroup(final Composite comp) {
		final Group additionalGroup = new Group(comp, SWT.NULL);
		additionalGroup.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Additional);
		additionalGroup.setLayout(new GridLayout(3, false));
		additionalGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createCodeCoverageSection(additionalGroup);
		createReportingSection(additionalGroup);
	}

	/**
	 * @param comp
	 */
	private void createCodeCoverageSection(final Composite comp) {
		fCodeCoverageButton = new Button(comp, SWT.CHECK);
		fCodeCoverageButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Coverage);
		fCodeCoverageButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		fCodeCoverageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				notifyParameterChanged();
			}
		});

		Link fLink = new Link(comp, SWT.WRAP);
		fLink.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_6);
		fLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(getShell(), PHPUnitPreferencePage.ID,
						new String[] { PHPUnitPreferencePage.ID }, null);
				dialog.open();
			}
		});
	}

	/**
	 * @param comp
	 */
	private void createReportingSection(final Composite comp) {
		fReportingButton = new Button(comp, SWT.CHECK);
		fReportingButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Generate_Report);
		fReportingButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
		fReportingButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				notifyParameterChanged();
			}
		});

		Label label = new Label(comp, SWT.NONE);
		label.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_2);
		fPhpunitConfigPath = new Text(comp, SWT.BORDER);
		fPhpunitConfigPath.addModifyListener(e -> notifyParameterChanged());
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fPhpunitConfigPath.setLayoutData(gd);
		Button browse = new Button(comp, SWT.NONE);
		browse.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_5);
		browse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowsePHPUnitConfigPressed();
			}
		});
	}

	private void createExecutionGroup(final Composite comp) {
		final Group group = new Group(comp, SWT.NULL);
		group.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Execution_parameters);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fUsePharButton = new Button(group, SWT.RADIO);
		fUsePharButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Use_global_phar);
		fUsePharButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true));
		fUsePharButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyParameterChanged();
			}
		});

		Link fLink = new Link(group, SWT.WRAP);
		fLink.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Preferences_link);
		fLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(getShell(), PHPUnitPreferencePage.ID,
						new String[] { PHPUnitPreferencePage.ID }, null);
				dialog.open();
			}
		});

		fUseComposerButton = new Button(group, SWT.RADIO);
		fUseComposerButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Use_composer);
		fUseComposerButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true, 2, 1));
		fUseComposerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyParameterChanged();
			}
		});
	}

	/**
	 * @param comp
	 */
	private void createElementGroup(final Composite comp) {
		final Group elementGroup = new Group(comp, SWT.NULL);
		elementGroup.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Elements);
		elementGroup.setLayout(new GridLayout(3, false));
		elementGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createSingleTestSection(elementGroup);
		createTestContainerSection(elementGroup);
	}

	private void createProjButton(final Composite comp) {
		fProjButton = new Button(comp, SWT.PUSH);
		fProjButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Browse);
		fProjButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent evt) {
				handleProjectButtonPressed();
			}
		});
		setButtonGridData(fProjButton);
	}

	private void createProjLabel(final Composite comp) {
		GridData gd;
		fProjLabel = new Label(comp, SWT.NONE);
		fProjLabel.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Project);
		gd = new GridData();
		gd.horizontalIndent = 25;
		fProjLabel.setLayoutData(gd);
	}

	private void createProjText(final Composite comp) {
		fProjText = new Text(comp, SWT.SINGLE | SWT.BORDER);
		fProjText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fProjText.addModifyListener(evt -> {
			if (previousSelectedProjectText != null && previousSelectedProjectText.length() > 0
					&& !previousSelectedProjectText.equals(fProjText.getText())) {
				testElementFinder.cleareCaches();
			}
			previousSelectedProjectText = fProjText.getText();
			validatePage();
			notifyParameterChanged();
			fSearchButton.setEnabled(fSingleTestRadioButton.getSelection() && fProjText.getText().length() > 0);
		});
	}

	private void createSingleTestSection(final Composite comp) {
		createTestRadioButton(comp);

		createProjLabel(comp);
		createProjText(comp);
		createProjButton(comp);

		createTestLabel(comp);
		createTestText(comp);
		createTestSearchButton(comp);
		new Label(comp, SWT.NONE);

		createTestFileLabel(comp);
	}

	private void createTestContainerSection(final Composite comp) {
		fTestContainerRadioButton = new Button(comp, SWT.RADIO);
		fTestContainerRadioButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Run_Container);
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		fTestContainerRadioButton.setLayoutData(gd);
		fTestContainerRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (fTestContainerRadioButton.getSelection())
					testModeChanged();
			}
		});

		fContainerText = new Text(comp, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 25;
		gd.horizontalSpan = 2;
		fContainerText.setLayoutData(gd);
		fContainerText.addModifyListener(evt -> notifyParameterChanged());

		fContainerSearchButton = new Button(comp, SWT.PUSH);
		fContainerSearchButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Search);
		fContainerSearchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent evt) {
				handleContainerSearchButtonSelected();
			}
		});
		setButtonGridData(fContainerSearchButton);
	}

	private void createTestFileLabel(final Composite comp) {
		fTestFileLabel = new Label(comp, SWT.NONE);
		fTestFileLabel.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
	}

	private void createTestLabel(final Composite comp) {
		fTestLabel = new Label(comp, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		fTestLabel.setLayoutData(gd);
		fTestLabel.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Class);
	}

	private void createTestRadioButton(final Composite comp) {
		fSingleTestRadioButton = new Button(comp, SWT.RADIO);
		fSingleTestRadioButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Test);
		final GridData gd = new GridData();
		gd.horizontalSpan = 3;
		fSingleTestRadioButton.setLayoutData(gd);
		fSingleTestRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (fSingleTestRadioButton.getSelection()) {
					testModeChanged();
				}
			}
		});
	}

	private void createTestSearchButton(final Composite comp) {
		fSearchButton = new Button(comp, SWT.PUSH);
		fSearchButton.setEnabled(fProjText.getText().length() > 0);
		fSearchButton.setText(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Search);
		fSearchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent evt) {
				if (previousSelectedProjectText != null && previousSelectedProjectText.length() > 0
						&& !previousSelectedProjectText.equals(fProjText.getText())) {
					testElementFinder.cleareCaches();
				}

				IScriptProject phpProject = getPHPProject();
				if (!validatePHPProject(phpProject.getProject())) {
					return;
				}

				previousSelectedProjectText = fProjText.getText();

				handleSearchButtonPressed();
			}
		});
		setButtonGridData(fSearchButton);
	}

	private void createTestText(final Composite comp) {
		fTestText = new Text(comp, SWT.SINGLE | SWT.BORDER);
		fTestText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fTestText.addModifyListener(evt -> updateLaunchConfigurationDialog());
		fTestText.setEditable(false);
	}

	/**
	 * Return the IProject corresponding to the project name in the project name
	 * text field, or null if the text does not match a project name.
	 */
	private IScriptProject getPHPProject() {
		final String projectName = fProjText.getText().trim();
		if (projectName.isEmpty()) {
			return null;
		}

		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(fProjText.getText());
		try {
			if (PHPToolkitUtil.isPHPProject(project)) {
				return DLTKCore.create(project);
			}
		} catch (CoreException e) {
			// return null
		}
		return null;
	}

	private String getProjectRelativePath(final IResource element) {
		if (element instanceof IProject) {
			return StringUtils.EMPTY;
		}
		return element.getFullPath().makeRelativeTo(element.getProject().getFullPath()).toOSString();
	}

	private void handleContainerSearchButtonSelected() {
		final IResource phpElement = chooseContainer(fContainerElement);
		if (phpElement != null)
			setContainerElement(phpElement);
	}

	private IResource chooseContainer(final Object initElement) {
		final ITreeContentProvider provider = new WorkbenchContentProvider();
		final ILabelProvider labelProvider = new WorkbenchLabelProvider();
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), labelProvider, provider);
		dialog.setTitle(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Container_Selection);
		dialog.setMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Choose_Container);

		// filter elements
		RSEProjectFilter filter1 = new RSEProjectFilter();
		dialog.addFilter(filter1);

		DotFileFilter filter2 = new DotFileFilter();
		dialog.addFilter(filter2);

		ClosedProjectFilter filter3 = new ClosedProjectFilter();
		dialog.addFilter(filter3);

		dialog.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IFile) {
					IResource resource = (IResource) element;
					return "php".equalsIgnoreCase(resource.getFileExtension()); //$NON-NLS-1$
				}
				return true;
			}
		});

		LibraryFilter filter6 = new LibraryFilter();
		dialog.addFilter(filter6);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));

		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());

		dialog.setAllowMultiple(false);
		dialog.setInitialSelection(initElement);

		if (dialog.open() == Dialog.OK) {
			return (IResource) dialog.getFirstResult();
		}
		return null;
	}

	/**
	 * Show a dialog that lets the user select a project. This in turn provides
	 * context for the main class, allowing the user to key a main class name,
	 * or constraining the search for main classes to the specified project.
	 */
	private void handleProjectButtonPressed() {
		final IProject project = choosePHPProject();
		if (project == null) {
			return;
		}
		final String projectName = project.getName();
		fProjText.setText(projectName);
		validatePHPProject(project);
	}

	private void handleBrowsePHPUnitConfigPressed() {
		String newPath = choosePHPUnitConfig();
		fPhpunitConfigPath.setText(newPath);
	}

	/**
	 * Show a dialog that lists all main classess
	 */
	private void handleSearchButtonPressed() {
		final IScriptProject phpProject = getPHPProject();
		if (phpProject == null) {
			return;
		}
		boolean[] radioSetting = new boolean[2];
		try {
			// fix for 66922 Wrong radio behaviour when switching
			// remember the selected radio button
			radioSetting[0] = fSingleTestRadioButton.getSelection();
			radioSetting[1] = fTestContainerRadioButton.getSelection();
			if (testElementFinder.getPHP_UNIT_SUITE_CACHED() == null
					&& testElementFinder.getPHP_UNIT_CASE_CACHED() == null) {
				getLaunchConfigurationDialog().run(true, false, testElementFinder.search(phpProject));
			}
		} catch (InterruptedException e) {
			setErrorMessage(e.getMessage());
			return;
		} catch (InvocationTargetException e) {
			PHPUnitPlugin.log(e.getTargetException());
			return;
		} finally {
			fSingleTestRadioButton.setSelection(radioSetting[0]);
			fTestContainerRadioButton.setSelection(radioSetting[1]);
		}

		Shell shell = getShell();
		SelectionDialog dialog = new TestSelectionDialog(shell, testElementFinder.getPHP_UNIT_CASES_AND_SUITES());
		dialog.setTitle(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Test_Selection);
		dialog.setMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Choose_Test);
		if (dialog.open() == Window.CANCEL) {
			return;
		}

		Object[] results = dialog.getResult();
		if ((results == null) || (results.length < 1)) {
			return;
		}
		IType type = (IType) results[0];

		if (type != null) {
			fElementPath = type.getPath().toOSString();
			fTestText.setText(type.getElementName());
			fProjText.setText(type.getScriptProject().getElementName());
			IResource typResource = type.getResource();
			if (typResource != null) {
				IPath relativePath = typResource.getFullPath().makeRelativeTo(typResource.getProject().getFullPath());
				fTestFileLabel.setText(relativePath.toPortableString());
				validatePHPProject(typResource.getProject());
			} else {
				fTestFileLabel.setText(StringUtils.EMPTY);
				validatePHPProject(null);
			}
		}
	}

	/*
	 * Realize a Project selection dialog and return the first selected project,
	 * or null if there was none.
	 */
	private IProject choosePHPProject() {
		final ILabelProvider labelProvider = new ModelElementLabelProvider(ModelElementLabelProvider.SHOW_DEFAULT);
		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Project_Selection);
		dialog.setMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Choose_Project);
		IScriptProject[] scriptProjects = getScriptProjects();
		dialog.setElements(scriptProjects);

		dialog.setInitialSelections(scriptProjects);
		if (dialog.open() == Window.OK) {
			IScriptProject scriptProject = (IScriptProject) dialog.getFirstResult();
			return scriptProject.getProject();
		}
		return null;
	}

	private IScriptProject[] getScriptProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if (projects == null) {
			return new IScriptProject[0];
		}

		List<IScriptProject> scriptProjectsList = new ArrayList<>();
		for (IProject project : projects) {
			try {
				if (project.isOpen() && project.hasNature(PHPNature.ID)
						&& !project.hasNature(PHPUiConstants.RSE_TEMP_PROJECT_NATURE_ID)
						&& !project.getName().equals(PHPUiConstants.RSE_TEMP_PROJECT_NAME)) {
					IScriptProject scriptProject = DLTKCore.create(project);
					scriptProjectsList.add(scriptProject);

				}
			} catch (CoreException e) {
				PHPUnitPlugin.log(e);
			}
		}
		return scriptProjectsList.toArray(new IScriptProject[scriptProjectsList.size()]);
	}

	private String findFileToExecute(ILaunchConfiguration config, IProject project) throws CoreException {
		String runType = config.getAttribute(ATTRIBUTE_EXECUTION_TYPE, PHAR_EXECUTION_TYPE);
		if (COMPOSER_EXECUTION_TYPE.equals(runType)) {
			return PHPUnitLaunchUtils.findComposerExecutionFile(project);
		} else if (PHAR_EXECUTION_TYPE.equals(runType)) {
			return PHPUnitPreferenceKeys.getPHPUnitPharPath();
		}
		return null;
	}

	@Override
	public boolean isValid(final ILaunchConfiguration config) {
		return getErrorMessage() == null;
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy config) {
		fSelectedProject = null;
		if (fTestContainerRadioButton.getSelection()) {
			config.setAttribute(ATTRIBUTE_RUN_CONTAINER, true);
			if (fContainerElement != null) {
				fElementPath = fContainerElement.getFullPath().toOSString();
				fSelectedProject = fContainerElement.getProject();
				config.setAttribute(ATTRIBUTE_PROJECT, fSelectedProject.getProject().getName());
				config.setAttribute(ATTRIBUTE_CONTAINER, getProjectRelativePath(fContainerElement));
				String typeName = getContainerType(fContainerElement);
				config.setAttribute(ATTRIBUTE_CONTAINER_TYPE, typeName);
			}
		} else {
			config.setAttribute(ATTRIBUTE_RUN_CONTAINER, false);
			final String pText = fProjText.getText();
			config.setAttribute(ATTRIBUTE_PROJECT, pText);
			if (pText != null) {
				final IScriptProject[] scriptProjects = getScriptProjects();
				for (IScriptProject script : scriptProjects) {
					if (pText.equals(script.getProject().getName())) {
						fSelectedProject = script.getProject();
						break;
					}
				}
			}
			config.setAttribute(ATTRIBUTE_CLASS, fTestText.getText());
			config.setAttribute(ATTRIBUTE_FILE, fTestFileLabel.getText());
			config.setAttribute(ATTRIBUTE_CONTAINER_TYPE, SOURCE_CONTAINER);
		}
		if (StringUtils.isNotEmpty(fElementPath)) {
			config.setAttribute(PHPUnitPlugin.ELEMENT_PATH_ATTR, fElementPath);
			fElementPath = StringUtils.EMPTY;
		}

		config.setAttribute(ATTRIBUTE_CODE_COVERAGE, fCodeCoverageButton.getSelection());
		config.setAttribute(ATTRIBUTE_LOG_XML, fReportingButton.getSelection());
		config.setAttribute(ATTRIBUTE_PHPUNIT_CFG, fPhpunitConfigPath.getText());
		if (fUsePharButton.getSelection()) {
			config.setAttribute(ATTRIBUTE_EXECUTION_TYPE, PHAR_EXECUTION_TYPE);
		} else if (fUseComposerButton.getSelection()) {
			config.setAttribute(ATTRIBUTE_EXECUTION_TYPE, COMPOSER_EXECUTION_TYPE);
		}
		// Set up PHP script launching specific attributes
		String projectName;
		try {
			// Don't stop on a first breakpoint if in debug mode
			config.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, false);
			projectName = config.getAttribute(ATTRIBUTE_PROJECT, ""); //$NON-NLS-1$
			if (StringUtils.isNotEmpty(projectName)) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				config.setAttribute(IPHPDebugConstants.PHP_Project, project.getName());
				String fileToExecute = findFileToExecute(config, project);
				config.setAttribute(IPHPDebugConstants.ATTR_FILE, fileToExecute);
				config.setAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, fileToExecute);
			}
		} catch (CoreException e) {
			PHPUnitPlugin.log(e);
		}

	}

	private String getContainerType(IResource container) {
		if (container instanceof IProject) {
			return PROJECT_CONTAINER;
		}
		if (container instanceof IFolder) {
			return FOLDER_CONTAINER;
		}

		if (container instanceof IFile) {
			return SOURCE_CONTAINER;
		}

		return null;
	}

	private void setButtonGridData(final Button button) {
		final GridData gridData = new GridData();
		button.setLayoutData(gridData);
	}

	private void setContainerElement(final IResource container) {
		fContainerElement = container;
		fContainerText.setText(container.getFullPath().toString());
		validatePage();
		notifyParameterChanged();
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy config) {
		PHPUnitLaunchUtils.initializeDefaults(config);
	}

	private void setEnableContainerTestGroup(final boolean enabled) {
		fContainerSearchButton.setEnabled(enabled);
		fContainerText.setEnabled(enabled);
	}

	private void setEnableSingleTestGroup(final boolean enabled) {
		fProjLabel.setEnabled(enabled);
		fProjText.setEnabled(enabled);
		fProjButton.setEnabled(enabled);
		fTestLabel.setEnabled(enabled);
		fSearchButton.setEnabled(enabled && fProjText.getText().length() > 0);
	}

	private void testModeChanged() {
		if (previousSelectedProjectText != null && previousSelectedProjectText.length() > 0
				&& !previousSelectedProjectText.equals(fProjText.getText())) {
			testElementFinder.cleareCaches();
		}
		previousSelectedProjectText = fProjText.getText();

		boolean isSingleTestMode = fSingleTestRadioButton.getSelection();
		setEnableSingleTestGroup(isSingleTestMode);
		setEnableContainerTestGroup(!isSingleTestMode);
		if (!isSingleTestMode && StringUtils.isEmpty(fContainerText.getText())) {
			final IScriptProject phpProject = getPHPProject();
			if (phpProject != null) {
				setContainerElement(phpProject.getProject());
			}

		}
		validatePage();
		updateLaunchConfigurationDialog();
	}

	private void validatePage() {
		setErrorMessage(null);
		setMessage(null);

		if (fSingleTestRadioButton.getSelection()) {
			IScriptProject scriptProject = getPHPProject();

			if (scriptProject == null) {
				testElementFinder.cleareCaches();
				setErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_No_Project);
				return;
			}
			String projectName = scriptProject.getElementName();
			final IStatus status = ResourcesPlugin.getWorkspace().validatePath(IPath.SEPARATOR + projectName,
					IResource.PROJECT);
			if (!status.isOK()) {
				testElementFinder.cleareCaches();
				setErrorMessage(PHPUnitMessages.format(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Bad_Project_Name,
						projectName));
				return;
			}

			final IProject project = scriptProject.getProject();
			if (!project.exists()) {
				testElementFinder.cleareCaches();
				setErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Project_unavailable);
				return;
			}

			try {
				if (PHPToolkitUtil.isPHPProject(project)) {
					setErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Project_Not_PHP);
					testElementFinder.cleareCaches();
					return;
				}
			} catch (CoreException e) {
			}

			if (validatePHPProject(project.getProject())) {
				final String className = fTestText.getText().trim();
				if (className.length() == 0) {
					setErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_No_Test);
					return;
				} else {
					IDLTKSearchScope searchScope = SearchEngine.createSearchScope(scriptProject);
					IType[] classes = PHPModelAccess.getDefault().findTypes(className, MatchRule.EXACT, 0,
							Modifiers.AccInterface | Modifiers.AccNameSpace | Modifiers.AccAbstract, searchScope, null);
					if (classes.length < 1) {
						setErrorMessage(PHPUnitMessages.format(PHPUnitMessages.PHPUnitValidator_Not_In_Project,
								new String[] { className, projectName }));
					}
				}
			}
		} else {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(fContainerText.getText());
			if (resource == null) {
				setErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_No_Element_To_Test);
				return;
			}
		}

		if (fUseComposerButton.getSelection() && fSelectedProject != null
				&& PHPUnitLaunchUtils.findComposerExecutionFile(fSelectedProject) == null) {
			setErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Unable_find_dependencies);
			return;
		}
	}

	private boolean validatePHPProject(final IProject phpProject) {
		try {
			if (testElementFinder.getPHP_UNIT_SUITE_CACHED() == null
					&& testElementFinder.getPHP_UNIT_CASE_CACHED() == null) {
				getLaunchConfigurationDialog().run(true, false, testElementFinder.search(DLTKCore.create(phpProject)));
			}
		} catch (final InterruptedException | InvocationTargetException e) {
			PHPUnitPlugin.log(e);
		}
		if (testElementFinder.getPHP_UNIT_CASES_AND_SUITES() == null
				|| testElementFinder.getPHP_UNIT_CASES_AND_SUITES().length == 0) {
			setErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_Project_No_Tests);
			fSearchButton.setEnabled(false);
			updateLaunchConfigurationDialog();

			return false;
		}

		fSearchButton.setEnabled(true);
		return true;
	}

	@Override
	public Image getImage() {
		return fTestIcon;
	}

	@Override
	public String getName() {
		return PHPUnitMessages.PHPUnitLaunchConfigurationTab_7;
	}

	@Override
	public void dispose() {
		super.dispose();
		fTestIcon.dispose();
		fPHPElementLabelProvider.dispose();
	}

}
