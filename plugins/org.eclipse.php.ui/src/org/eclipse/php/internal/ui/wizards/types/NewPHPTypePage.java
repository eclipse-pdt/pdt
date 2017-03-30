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
package org.eclipse.php.internal.ui.wizards.types;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.dltk.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.dltk.ui.dialogs.ITypeInfoFilterExtension;
import org.eclipse.dltk.ui.dialogs.TypeSelectionExtension;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.php.internal.ui.wizards.BasicPHPWizardPage;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.internal.ui.wizards.fields.IListAdapter;
import org.eclipse.php.internal.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.preferences.WorkingCopyManager;
import org.eclipse.ui.views.navigator.ResourceComparator;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;

public abstract class NewPHPTypePage extends BasicPHPWizardPage implements IDialogFieldListener {

	private static final String BIND_NAMESPACE_AND_LOCATION = "Bind namespace and location";//$NON-NLS-1$
	public static final String REQUIRE_ONCE = "require_once";//$NON-NLS-1$
	public static final String TODOS = "TODOs";//$NON-NLS-1$
	public static final String PHP_DOC_BLOCKS = "PHPDoc Blocks";//$NON-NLS-1$
	public static final String DESTRUCTOR = "Destructor";//$NON-NLS-1$
	public static final String CONSTRUCTOR = "Constructor";//$NON-NLS-1$
	public static final String INHERITED_ABSTRACT_METHODS = "Inherited abstract methods";//$NON-NLS-1$

	private static final Pattern PHP_IDENTIFIER_PATTERN = Pattern
			.compile("[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*"); //$NON-NLS-1$

	protected PHPVersion phpVersion;

	/**
	 * Constant to signal that the created type is a class.
	 * 
	 */
	public static final int CLASS_TYPE = 1;

	/**
	 * Constant to signal that the created type is a interface.
	 * 
	 */
	public static final int INTERFACE_TYPE = 2;

	/**
	 * Constant to signal that the created type is a trait.
	 * 
	 */
	public static final int TRAIT_TYPE = 3;

	// some validation IDs for validating specific sections within the UI
	public static final int VALIDATE_SOURCE_FOLDER = 1;
	protected StatusInfo sourceFolderStatus;
	public static final int VALIDATE_NEW_FILE = 2;
	protected StatusInfo newFileStatus;
	public static final int VALIDATE_EXISTING_FILE = 3;
	protected StatusInfo existingFileStatus;
	public static final int VALIDATE_ELEMENT_NAME = 4;
	protected StatusInfo elementNameStatus;
	public static final int VALIDATE_NAMESPACE = 5;
	protected StatusInfo namespaceStatus;
	protected StatusInfo interfacesStatus;
	protected StatusInfo traitsStatus;

	private String sourceFolder = ""; //$NON-NLS-1$
	protected Text sourceText;
	protected Text newFileText;
	protected Text existingFileText;
	protected Text elementName;
	protected Text namespaceText;
	protected Composite modifiers;
	protected ListDialogField<IType> fSuperInterfacesDialogField;
	private ListDialogField<IType> fTraitsDialogField;
	private Button existingFileBtn;
	private Button firstBlockBtn;
	private Button browseSourceBtn;
	private Button browseExistingFile;
	private Composite checkBoxesCreationComp;
	protected int fTypeKind;
	private boolean isInExistingPHPFile = false;
	private Composite injectLocation;
	private String existingFileName = ""; //$NON-NLS-1$
	protected IType openByInterface = null;
	protected IType openByTrait = null;
	private Button addInterfacesBtn;
	private Button addTraitsBtn;
	private String initialElementName;
	private IPreferenceStore preferenceStore;
	private int removedSegmentNumber;
	private IFile existingFile;
	private Button namespaceCheckbox;

	public NewPHPTypePage(String pageName) {
		super(pageName);
		updateStatus(new StatusInfo());
		sourceFolderStatus = new StatusInfo();
		newFileStatus = new StatusInfo();
		existingFileStatus = new StatusInfo();
		elementNameStatus = new StatusInfo();
		namespaceStatus = new StatusInfo();
	}

	protected String getSuperInterfacesLabel() {
		if (fTypeKind != INTERFACE_TYPE) {
			return Messages.NewPHPTypePage_interfaces;
		}
		return Messages.NewPHPTypePage_extendedInterfaces;
	}

	// the location of where to inject the new element's code
	protected void createLocationSection(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		container.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		container.setLayout(layout);

		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.NewPHPTypePage_sourceFolder);

		sourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		sourceText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		sourceText.setEditable(false);

		// init PHPVersion
		handlePHPVersion();
		sourceText.setText(sourceFolder);
		handlePHPVersion();
		sourceText.addModifyListener(e -> {
			Text textSource = (Text) e.getSource();
			sourceFolder = textSource.getText();
			handlePHPVersion();
			validatePageValues(VALIDATE_SOURCE_FOLDER);
			validatePageValues(VALIDATE_NEW_FILE);
			updateDisabled();
		});

		browseSourceBtn = new Button(container, SWT.PUSH);
		browseSourceBtn.setText(Messages.NewPHPTypePage_browse);
		GridData gd = new GridData();
		gd.verticalAlignment = GridData.BEGINNING;
		gd.widthHint = SWTUtil.getButtonWidthHint(browseSourceBtn);
		browseSourceBtn.setLayoutData(gd);
		browseSourceBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				chooseNewSourceFolder();
				String sourcePath = sourceText.getText();
				if (sourcePath.length() > 0) {
					handlePHPVersion();
				}
				validatePageValues(VALIDATE_SOURCE_FOLDER);
				setDefaultNamespace();
			}
		});

		final Button newFileBtn = new Button(container, SWT.RADIO);
		newFileBtn.setText(Messages.NewPHPTypePage_createNewFile);
		newFileBtn.setSelection(!isInExistingPHPFile);
		newFileBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isInExistingPHPFile) {
					isInExistingPHPFile = false;

					if (TypeWizardConstants.NEW_FILE_DEFAULT_NAME.equals(newFileText.getText())
							|| newFileText.getText().trim().length() == 0) {
						updateNewFilename();
					}

					updateDisabled();
					setDefaultNamespace();
					validatePageValues(VALIDATE_NEW_FILE);
					// fix bug #14458 - Validate existing file
					// In case of switch the radio buttons from 'Add existing
					// file' to 'Create new file' erase the error message.
					validatePageValues(VALIDATE_EXISTING_FILE);
				}
			}
		});

		newFileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		newFileText.setLayoutData(gd);
		newFileText.addModifyListener(e -> validatePageValues(VALIDATE_NEW_FILE));

		new Label(container, SWT.NULL);

		existingFileBtn = new Button(container, SWT.RADIO);
		existingFileBtn.setText(Messages.NewPHPTypePage_addInExistingFile);
		existingFileBtn.setSelection(isInExistingPHPFile);
		existingFileBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isInExistingPHPFile) {
					isInExistingPHPFile = true;
					if (existingFile != null) {
						sourceFolder = existingFile.getParent().getFullPath().toOSString();
						sourceText.setText(sourceFolder);
					}
					updateDisabled();
					setDefaultNamespace();
					validatePageValues(VALIDATE_NEW_FILE);
					validatePageValues(VALIDATE_EXISTING_FILE);
					validatePageValues(VALIDATE_SOURCE_FOLDER);
				}
			}

		});

		existingFileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		existingFileText.setLayoutData(gd);
		existingFileText.setText(existingFileName);
		existingFileText.addModifyListener(e -> {
			validatePageValues(VALIDATE_EXISTING_FILE);
			updateDisabled();
			setDefaultNamespace();
		});

		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseExistingFile = new Button(container, SWT.PUSH);
		browseExistingFile.setText(Messages.NewPHPTypePage_browse);
		gd = new GridData();
		gd.verticalAlignment = GridData.BEGINNING;
		gd.widthHint = SWTUtil.getButtonWidthHint(browseExistingFile);
		browseExistingFile.setLayoutData(gd);

		browseExistingFile.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openPhpFileDialog();
				validatePageValues(VALIDATE_EXISTING_FILE);
				validatePageValues(VALIDATE_SOURCE_FOLDER);
				updateDisabled();
				setDefaultNamespace();
			}

		});
		// the radio buttons
		new Label(container, SWT.NULL);

		injectLocation = new Composite(container, SWT.NULL);
		GridLayout injectLayout = new GridLayout(2, true);
		injectLocation.setLayout(injectLayout);
		injectLayout.marginHeight = 0;
		injectLayout.marginWidth = 0;

		firstBlockBtn = new Button(injectLocation, SWT.RADIO);
		firstBlockBtn.setText(Messages.NewPHPTypePage_firstPHPBlock);
		firstBlockBtn.setSelection(true);
		Button newPHPBlockBtn = new Button(injectLocation, SWT.RADIO);
		newPHPBlockBtn.setText(Messages.NewPHPTypePage_newPHPBlock);

		new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));
	}

	protected void handlePHPVersion() {
		String sourcePath = sourceText.getText();
		if (sourcePath.length() > 0) {
			if (isValidSourcePath(sourcePath)) {
				IProject currentProject = getCurrentProject();
				if (currentProject != null && !currentProject.isAccessible()) {
					phpVersion = null;
				} else {
					phpVersion = ProjectOptions.getPHPVersion(currentProject);
				}
			} else {
				phpVersion = null;
			}
		}
		// use workspace default php version if it is null
		if (phpVersion == null) {
			phpVersion = ProjectOptions.getDefaultPHPVersion();
		}
	}

	protected IScriptProject getProject() {
		String sourcePath = sourceText.getText();
		if (sourcePath.length() > 0 && isValidSourcePath(sourcePath)) {
			IProject currProject = getCurrentProject();
			if (currProject != null) {
				return DLTKCore.create(currProject);
			}
		}
		return null;

	}

	/**
	 * @return the project name the first section of the path
	 */
	protected String getProjectName(String sourcePath) {
		IPath path = new Path(sourcePath);
		if (path.segmentCount() > 1) {
			sourcePath = path.segment(0);
		}

		return sourcePath;
	}

	/**
	 * Handles source path validation and when '\' is entered
	 * 
	 * @param sourcePath
	 * @return
	 */
	protected boolean isValidSourcePath(String path) {
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IPath containerPath = new Path(path.replace('\\', IPath.SEPARATOR));
		containerPath = new Path(containerPath.segment(0));
		IResource resource = workspaceRoot.findMember(containerPath);
		return resource != null;
	}

	/**
	 * Element name section
	 * 
	 * @param container
	 * @param type
	 */
	protected void addElementNameText(Composite container, String type) {
		GridData gd = new GridData();
		Label elementNameLabel = new Label(container, SWT.NULL);
		elementNameLabel.setText(type + Messages.NewPHPTypePage_name);
		elementNameLabel.setLayoutData(gd);

		elementName = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		elementName.setLayoutData(gd);
		elementName.setFocus();
		elementName.addModifyListener(e -> {
			validatePageValues(VALIDATE_ELEMENT_NAME);
			updateNewFilename();
		});
		if (initialElementName != null) {
			elementName.setText(initialElementName);
		}
	}

	protected void addNamespaceText(Composite container) {
		GridData gd = new GridData();
		new Label(container, SWT.NULL);
		namespaceCheckbox = new Button(container, SWT.CHECK);
		namespaceCheckbox.setText(BIND_NAMESPACE_AND_LOCATION);
		namespaceCheckbox.setSelection(true);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		namespaceCheckbox.setLayoutData(gd);
		namespaceCheckbox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				changeSourceFolder();
			}

		});

		Label elementNameLabel = new Label(container, SWT.NULL);
		elementNameLabel.setText(Messages.NewPHPTypePage_0);
		elementNameLabel.setLayoutData(new GridData());

		namespaceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		namespaceText.setLayoutData(gd);
		namespaceText.addModifyListener(e -> changeSourceFolder());
		setDefaultNamespace();
	}

	private void changeSourceFolder() {
		sourceText.setEditable(!namespaceCheckbox.getSelection());
		if (!existingFileBtn.getSelection() && namespaceCheckbox.getSelection()) {
			String sourceFolder = getSourceText();
			IPath sourcePath = new Path(sourceFolder);
			sourcePath = sourcePath.removeLastSegments(sourcePath.segmentCount() - removedSegmentNumber);
			String[] segments = namespaceText.getText().split("\\\\");//$NON-NLS-1$
			for (String segment : segments) {
				sourcePath = sourcePath.append(segment);
			}
			sourceText.setText(sourcePath.toOSString());
		}

		validatePageValues(VALIDATE_NAMESPACE);
	}

	protected void setDefaultNamespace() {
		if (namespaceText == null) {
			return;
		}
		IFile existingFile = getExisitngFile();
		String sourceFolder = getSourceText();
		if (!sourceFolder.isEmpty()) {
			IPath sourcePath = new Path(sourceFolder);
			removedSegmentNumber = firstSegmentsToRemoveForNamespace(sourcePath);
		}
		if (existingFileBtn.getSelection()) {
			if (existingFile != null) {
				String defaultNamespace = getLastNamespace();
				if (defaultNamespace != null) {
					namespaceText.setText(defaultNamespace);
				}
			}
		} else {
			if (!sourceFolder.isEmpty()) {
				IPath sourcePath = new Path(sourceFolder);
				sourcePath = sourcePath.removeFirstSegments(removedSegmentNumber);
				if (sourcePath.segmentCount() > 0) {
					String defaultNamespace = sourcePath.toString().replace("/", //$NON-NLS-1$
							"\\");//$NON-NLS-1$
					if (defaultNamespace.endsWith("\\")) {//$NON-NLS-1$
						defaultNamespace = defaultNamespace.substring(0, defaultNamespace.length() - 1);
					}
					namespaceText.setText(defaultNamespace);
				} else {
					namespaceText.setText("");//$NON-NLS-1$
				}
			}
		}
	}

	private int firstSegmentsToRemoveForNamespace(IPath sourcePath) {
		int lastSegmentIndex = sourcePath.segmentCount() - 1;
		int segments = sourcePath.segmentCount();
		for (int i = lastSegmentIndex; i > 0; i--) {
			if (Character.isLowerCase(sourcePath.segment(i).charAt(0))) {
				break;
			}
			segments--;
		}
		if (segments != sourcePath.segmentCount()) {
			return segments;
		}
		for (int i = lastSegmentIndex; i > 0; i--) {
			if (sourcePath.segment(i).equalsIgnoreCase("src")) { //$NON-NLS-1$
				return segments;
			}
			segments--;
		}
		if (sourcePath.segmentCount() > 1) {
			return 2;
		}
		return 1;
	}

	/**
	 * Modifiers section
	 * 
	 * @param container
	 * @param modifiersNames
	 */
	protected void addElementModifiers(Composite container, String[] modifiersNames) {
		Label modifiersLabel = new Label(container, SWT.NULL);
		modifiersLabel.setText(Messages.NewPHPTypePage_modifiers);
		modifiers = new Composite(container, SWT.NONE);
		int len = modifiersNames.length;
		GridLayout layout = new GridLayout();
		modifiers.setLayout(layout);
		layout.numColumns = len;
		layout.makeColumnsEqualWidth = true;
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		for (int i = 0; i < modifiersNames.length; i++) {
			// fixed bug 14450 - change the modifier from checkboxes to radio
			// buttons
			Button check = new Button(modifiers, SWT.RADIO);
			check.setText(modifiersNames[i]);
			if (i == 0) {
				check.setSelection(true);
			}
		}
		new Label(container, SWT.NULL);
	}

	/**
	 * SuperInterfaces Control
	 * 
	 * @param parent
	 */
	protected void createSuperInterfacesControls(Composite parent) {
		final String INTERFACE = "interface"; //$NON-NLS-1$
		Control[] controls = fSuperInterfacesDialogField.doFillIntoGrid(parent, 3);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		controls[1].setLayoutData(gd);
		final TableViewer tableViewer = fSuperInterfacesDialogField.getTableViewer();
		tableViewer.setColumnProperties(new String[] { INTERFACE });

		tableViewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.F2 && event.stateMask == 0) {
					ISelection selection = tableViewer.getSelection();
					if (!(selection instanceof IStructuredSelection))
						return;
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					tableViewer.editElement(structuredSelection.getFirstElement(), 0);
				}
			}
		});
		gd = (GridData) fSuperInterfacesDialogField.getListControl(null).getLayoutData();
		if (fTypeKind == CLASS_TYPE) {
			gd.heightHint = convertHeightInCharsToPixels(3);
		} else {
			gd.heightHint = convertHeightInCharsToPixels(6);
		}
		gd.grabExcessVerticalSpace = true;
		gd.widthHint = getMaxFieldWidth();
		addInterfacesBtn = (Button) fSuperInterfacesDialogField.getButtonBox(parent).getChildren()[0];
		addInterfacesBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object result = chooseInterfaces();
				if (result != null && result instanceof IType) {
					fSuperInterfacesDialogField.addElement((IType) result);
				}
				fSuperInterfacesDialogField.refresh();
				validateInterfaces(getProject());
			}

		});
	}

	/**
	 * Traits Control
	 * 
	 * @param parent
	 */
	protected void createTraitsControls(Composite parent) {
		final String INTERFACE = "trait"; //$NON-NLS-1$
		Control[] controls = fTraitsDialogField.doFillIntoGrid(parent, 3);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		controls[1].setLayoutData(gd);
		final TableViewer tableViewer = fTraitsDialogField.getTableViewer();
		tableViewer.setColumnProperties(new String[] { INTERFACE });
		tableViewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.F2 && event.stateMask == 0) {
					ISelection selection = tableViewer.getSelection();
					if (!(selection instanceof IStructuredSelection))
						return;
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					tableViewer.editElement(structuredSelection.getFirstElement(), 0);
				}
			}
		});
		gd = (GridData) fTraitsDialogField.getListControl(null).getLayoutData();
		if (fTypeKind == CLASS_TYPE) {
			gd.heightHint = convertHeightInCharsToPixels(3);
		} else {
			gd.heightHint = convertHeightInCharsToPixels(6);
		}
		gd.grabExcessVerticalSpace = true;
		gd.widthHint = getMaxFieldWidth();
		addTraitsBtn = (Button) fTraitsDialogField.getButtonBox(parent).getChildren()[0];
		addTraitsBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object result = chooseTraits();
				if (result != null && result instanceof IType) {
					fTraitsDialogField.addElement((IType) result);
				}
				fTraitsDialogField.refresh();
				validateTraits(getProject());
			}

		});
	}

	protected int getMaxFieldWidth() {
		return convertWidthInCharsToPixels(40);
	}

	/**
	 * Returns the name of the new PHP file to create. Note: Use this property
	 * carefully since it can contain a non EMPTY/NULL value even when the user
	 * request to create the PHP element in an EXISTING PHP file.
	 */
	public String getNewFileName() {
		return newFileText.getText();
	}

	public String getNamespace() {
		if (namespaceText == null) {
			return "";//$NON-NLS-1$
		}
		if (isInFirstPHPBlock()) {
			String lastNamespace = getLastNamespace();
			if (lastNamespace != null) {
				if (!lastNamespace.equals(namespaceText.getText())) {
					return namespaceText.getText();
				} else {
					return ""; //$NON-NLS-1$
				}
			}
		}
		return namespaceText.getText();
	}

	public String getRealNamespace() {
		if (namespaceText == null) {
			return "";//$NON-NLS-1$
		}
		return namespaceText.getText();
	}

	public String getExistingFileName() {
		return existingFileText.getText();
	}

	/**
	 * If the PHP element is about to be inserted in an existing file, this will
	 * return the IFile object of the existing file. Note: Use this property
	 * carefully since it can contain a non EMPTY/NULL value even when the user
	 * request to create the PHP element in a NEW PHP file.
	 */
	public IFile getExisitngFile() {
		String sourceText = getSourceText();
		String existingFileName = getExistingFileName();
		if (existingFileName == null || existingFileName.isEmpty()) {
			return null;
		}
		Path p = new Path(sourceText + File.separatorChar + existingFileName);
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		return wsRoot.getFile(p);
	}

	/**
	 * Denotes whether the new PHP element is inserted in an existing PHP file
	 * (if true) or in a new php file (if false).
	 */
	public boolean isInExistingPHPFile() {
		return existingFileBtn.getSelection();
	}

	/**
	 * Denotes whether the created element will be inserted in the 1st block of
	 * the existing PHP file , OR (if false) as a new PHP block
	 */
	public boolean isInFirstPHPBlock() {
		return firstBlockBtn.getSelection();
	}

	/**
	 * The name of the PHP element
	 */
	public String getElementName() {
		return elementName.getText();
	}

	/**
	 * Set the PHP element name
	 */
	public void setElementName(String elementName) {
		this.initialElementName = elementName;
	}

	/**
	 * Returns a HashMap that represents the state of modifiers for the PHP
	 * element. Key - the string name of the modifier. Value - a Boolean object
	 * , whether the modifier is selected or not.
	 */
	public Map<String, Boolean> getModifiers() {
		if (modifiers == null) {
			return null;
		}

		HashMap<String, Boolean> result = new HashMap<>();
		Button[] modBtns = (Button[]) modifiers.getChildren();

		if (modBtns == null || modBtns.length == 0) {
			return null;
		}

		for (Button element : modBtns) {
			result.put(element.getText(), element.getSelection());
		}
		return result;
	}

	public List<IType> getInterfaces() {
		return fSuperInterfacesDialogField.getElements();
	}

	public List<IType> getTraits() {
		return fTraitsDialogField.getElements();
	}

	/**
	 * This will add a Checboxes composite with the given checkboxes names.
	 * 
	 * @param elementSection
	 * @param checkBoxes
	 * @return
	 */
	protected Composite addCheckBoxesInElementSection(Composite elementSection, String[] checkBoxes) {
		new Label(elementSection, SWT.NULL);
		Composite checkBoxesContainer = new Composite(elementSection, SWT.NULL);
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		checkBoxesContainer.setLayout(layout);

		for (String element : checkBoxes) {
			Button button = new Button(checkBoxesContainer, SWT.CHECK);
			button.setText(element);
		}
		return checkBoxesContainer;
	}

	/**
	 * This will add a Comment creation section with the given checkboxes names.
	 * 
	 * @param elementSection
	 * @param checkBoxes
	 */
	protected void addCheckboxesCreation(Composite elementSection, String[] checkBoxes) {
		GridData gd = new GridData();
		gd.verticalAlignment = GridData.BEGINNING;
		Label whichMethods = new Label(elementSection, SWT.NULL);
		whichMethods.setText(Messages.NewPHPTypePage_checkboxesToCreate);
		whichMethods.setLayoutData(gd);

		checkBoxesCreationComp = new Composite(elementSection, SWT.NULL);
		GridLayout layout = new GridLayout(3, true);
		layout.marginHeight = 3;
		layout.marginWidth = 0;
		checkBoxesCreationComp.setLayout(layout);

		for (String element : checkBoxes) {
			if (element != null) {
				Button button = new Button(checkBoxesCreationComp, SWT.CHECK);
				button.setText(element);
			} else {
				new Label(checkBoxesCreationComp, SWT.NULL);
			}
		}

	}

	public boolean isCheckboxCreationChecked(String name) {
		Control[] btns = checkBoxesCreationComp.getChildren();
		for (Control control : btns) {
			if (control instanceof Button) {
				Button button = (Button) control;
				if (button.getText().equals(name)) {
					return button.getSelection();
				}
			}
		}
		return false;
	}

	/**
	 * Initializes the source folder field with a valid root. The root is
	 * computed from the given PHPlement.
	 * 
	 * @param elem
	 *            the PHP Element used to compute the initial root used as the
	 *            source folder
	 */
	protected void initContainerPage(final IModelElement elem) {
		IContainer initRoot = null;
		if (elem != null) {
			final IResource resource = elem.getResource();
			if (resource != null) {
				initRoot = resource.getParent();
			} else {
				IScriptProject sp = (IScriptProject) elem.getAncestor(IModelElement.SCRIPT_PROJECT);
				if (sp != null) {
					initRoot = sp.getProject();
				}
			}
		}
		setContainer(initRoot);
	}

	public void setContainer(final IContainer root) {
		final String str = root == null ? "" : root.getFullPath().toOSString(); //$NON-NLS-1$
		sourceFolder = str;
	}

	/**
	 * Initialized the page with the current selection
	 * 
	 * @param selection
	 *            The selection
	 */
	public Object init(final IStructuredSelection selection) {
		final IModelElement element;
		final IContainer container;
		final Object result;
		if ((element = getInitialPHPElement(selection)) != null) {
			initContainerPage(element);
			result = element;
		} else if ((container = getInitialContainer(selection)) != null) {
			setContainer(container);
			result = container;
		} else {
			result = null;
		}
		return result;
	}

	protected void initValues() {
		validatePageValues(VALIDATE_SOURCE_FOLDER);
		if (!isInExistingPHPFile) {
			existingFileText.setEnabled(false);
			browseExistingFile.setEnabled(false);
			Control[] buttons = injectLocation.getChildren();
			for (Control element : buttons) {
				element.setEnabled(false);
			}
			if (namespaceText != null) {
				namespaceText.setEnabled(true);
			}
		} else {
			newFileText.setEnabled(false);
			sourceText.setEnabled(false);
			browseSourceBtn.setEnabled(false);
			if (namespaceText != null) {
				namespaceText.setEnabled(isNamespaceEnabled());
			}
		}
		if (!isInExistingPHPFile) {
			validatePageValues(VALIDATE_NEW_FILE);
		}

		validatePageValues(VALIDATE_ELEMENT_NAME);

		// add interface
		if (openByInterface != null) {
			fSuperInterfacesDialogField.addElement(openByInterface);
		}
		if (openByTrait != null) {
			fTraitsDialogField.addElement(openByTrait);
		}

		initGeneratedGroupValues();
		changeButtonEnableStatus();
	}

	protected void initGeneratedGroupValues() {
		preferenceStore = PHPUiPlugin.getDefault().getPreferenceStore();
		Button[] checkboxes = getGeneratedGroupCheckboxes();
		for (Button button : checkboxes) {
			initSelectionStatus(button);
		}
		initSelectionStatus(namespaceCheckbox);
	}

	private void initSelectionStatus(Button button) {
		if (button == null) {
			return;
		}
		String lastCheckboxValue = preferenceStore.getString(getPreferencePrefix() + button.getText());
		if (lastCheckboxValue != null && lastCheckboxValue.trim().length() > 0) {
			button.setSelection(lastCheckboxValue.equals("1"));//$NON-NLS-1$
		} else if (button.getText().equals(INHERITED_ABSTRACT_METHODS)
				|| button.getText().equals(BIND_NAMESPACE_AND_LOCATION)) {
			preferenceStore.setValue(getPreferencePrefix() + button.getText(), "1");//$NON-NLS-1$
			button.setSelection(true);
		}
	}

	public void saveGeneratedGroupValues() {
		Button[] checkboxes = getGeneratedGroupCheckboxes();
		for (Button button : checkboxes) {
			preferenceStore.setValue(getPreferencePrefix() + button.getText(), button.getSelection() ? "1" : "0");//$NON-NLS-1$ //$NON-NLS-2$
		}
		if (namespaceCheckbox != null) {
			preferenceStore.setValue(getPreferencePrefix() + namespaceCheckbox.getText(),
					namespaceCheckbox.getSelection() ? "1" : "0");//$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected Button[] getGeneratedGroupCheckboxes() {
		List<Button> list = new ArrayList<>();
		Control[] btns = null;
		if (checkBoxesCreationComp != null) {
			btns = checkBoxesCreationComp.getChildren();
			for (Control control : btns) {
				if (control instanceof Button) {
					list.add((Button) control);
				}
			}
		}
		return list.toArray(new Button[list.size()]);
	}

	protected String getPreferencePrefix() {
		return ""; //$NON-NLS-1$
	}

	protected void updateDisabled() {
		// new file
		if (!isInExistingPHPFile) {
			existingFileText.setEnabled(false);
			browseExistingFile.setEnabled(false);
			Control[] buttons = injectLocation.getChildren();
			for (Control element : buttons) {
				element.setEnabled(false);
			}
			newFileText.setEnabled(true);
			sourceText.setEnabled(true);
			browseSourceBtn.setEnabled(true);
			if (namespaceText != null) {
				namespaceText.setEnabled(true);
			}
		}
		// existing file
		else {
			newFileText.setEnabled(false);
			sourceText.setEnabled(false);
			browseSourceBtn.setEnabled(false);
			existingFileText.setEnabled(true);
			browseExistingFile.setEnabled(true);
			Control[] buttons = injectLocation.getChildren();
			for (Control element : buttons) {
				element.setEnabled(true);
			}
			if (namespaceText != null) {
				namespaceText.setEnabled(isNamespaceEnabled());
			}
		}
	}

	protected boolean isNamespaceEnabled() {
		ISourceModule existingPHPFile = DLTKCore.createSourceModuleFrom(getExisitngFile());
		if (existingPHPFile != null && existingPHPFile.exists()) {
			try {
				IModelElement[] rootElements = existingPHPFile.getChildren();
				if (rootElements.length > 0 && rootElements[0] instanceof IType) {
					IType firstElement = (IType) rootElements[0];
					if (PHPFlags.isNamespace(firstElement.getFlags())) {
						return true;
					}
				}
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
		return false;

	}

	protected String getLastNamespace() {
		ISourceModule existingPHPFile = DLTKCore.createSourceModuleFrom(getExisitngFile());
		if (existingPHPFile != null && existingPHPFile.exists()) {
			try {
				IModelElement[] rootElements = existingPHPFile.getChildren();
				for (int i = rootElements.length - 1; i >= 0; i--) {
					if (rootElements[i] instanceof IType) {
						IType firstElement = (IType) rootElements[i];
						if (PHPFlags.isNamespace(firstElement.getFlags())) {
							return firstElement.getFullyQualifiedName();
						}
					}
				}
				return "";//$NON-NLS-1$
			} catch (ModelException e) {
				// means that there where a problem with getting a model
				// do not log and just return null
				return null;
			}
		} else {
			return null;
		}
	}

	protected void validatePageValues(int validationCode) {
		switch (validationCode) {
		case VALIDATE_ELEMENT_NAME:
			elementNameStatus = new StatusInfo();
			if (getElementName() == null || getElementName().length() == 0) {
				String message = Messages.NewPHPTypePage_nameMustNotBeEmpty;
				switch (fTypeKind) {
				case CLASS_TYPE:
					message = Messages.NewPHPTypePage_class + message;
					break;
				case INTERFACE_TYPE:
					message = Messages.NewPHPTypePage_interface + message;
					break;
				case TRAIT_TYPE:
					message = Messages.NewPHPTypePage_trait + message;
					break;
				}
				elementNameStatus.setError(message);
			} else if (isInExistingPHPFile()) {
				Path p = new Path(getSourceText() + File.separatorChar + getExistingFileName());
				IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
				IFile file = wsRoot.getFile(p);

				ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
				if (sourceModule != null && sourceModule.exists()) {
					IType[] types = null;
					try {
						types = sourceModule.getTypes();
					} catch (ModelException e) {
						Logger.logException(e);
					}

					if (types != null) {
						// check if the new class/interface already exists in a
						// current file
						for (IType type : types) {
							if (type.getElementName().equalsIgnoreCase(getElementName())) {
								if (fTypeKind == CLASS_TYPE && !PHPFlags.isClass(type.getElementType())) {
									elementNameStatus.setError(Messages.NewPHPTypePage_class + getElementName()
											+ Messages.NewPHPTypePage_alreadyExistsInFile + existingFileName);
									break;
								} else if (fTypeKind == INTERFACE_TYPE && PHPFlags.isInterface(type.getElementType())) {
									elementNameStatus.setError(Messages.NewPHPTypePage_interface + getElementName()
											+ Messages.NewPHPTypePage_alreadyExistsInFile);
									break;
								} else if (fTypeKind == TRAIT_TYPE && PHPFlags.isInterface(type.getElementType())) {
									elementNameStatus.setError(Messages.NewPHPTypePage_trait + getElementName()
											+ Messages.NewPHPTypePage_alreadyExistsInFile);
									break;
								}
							}
						}
					} // do nothing here, no elements exists in the file
				}

			}

			// fix bug 14446 - add PHP identifier validation
			if (getProject() != null && !isValidPhpIdentifier(getElementName())) {
				String message = Messages.NewPHPTypePage_InvalidPhp;
				String nameLabel = Messages.NewPHPTypePage_InvalidPhpName;
				switch (fTypeKind) {
				case CLASS_TYPE:
					message = message + Messages.NewPHPTypePage_class + nameLabel;
					break;
				case INTERFACE_TYPE:
					message = message + Messages.NewPHPTypePage_interface + nameLabel;
					break;
				case TRAIT_TYPE:
					message = message + Messages.NewPHPTypePage_trait + nameLabel;
					break;
				}
				elementNameStatus.setError(message);
			}
			updateStatus(findMostSevereStatus());
			break;
		case VALIDATE_SOURCE_FOLDER:
			// since source folder is a critical location (PHP model)
			// we should check how it affects the wizard pages
			sourceFolderChanged();
			break;
		case VALIDATE_EXISTING_FILE:
			existingFileStatus = new StatusInfo();
			if (isInExistingPHPFile()) {
				if (existingFileText.getText().length() == 0) {
					existingFileStatus.setError(Messages.NewPHPTypePage_existingFileMustNotBeEmpty);
				} else if (!isPathExist(sourceText.getText() + File.separatorChar + existingFileText.getText())) {
					existingFileStatus.setError(Messages.NewPHPTypePage_targetPHPFile + existingFileText.getText()
							+ Messages.NewPHPTypePage_doesNotExist);
				} else if (!isPHPSuffix(existingFileText.getText())) {
					existingFileStatus.setError(Messages.NewPHPTypePage_phpFile + existingFileText.getText()
							+ Messages.NewPHPTypePage_notPhpExtention);
				}
			}
			updateStatus(findMostSevereStatus());
			break;
		case VALIDATE_NEW_FILE:
			newFileStatus = new StatusInfo();
			if (!isInExistingPHPFile()) {
				String fileName = newFileText.getText();
				if (fileName.length() == 0) {
					newFileStatus.setError(Messages.NewPHPTypePage_fileMustnotBeEmpty);
				}

				else if (isPathExist(sourceText.getText() + File.separatorChar + fileName)) {
					newFileStatus.setError(
							Messages.NewPHPTypePage_phpFile + fileName + Messages.NewPHPTypePage_alreadyExists);
				} else if (!isPHPSuffix(fileName)) {
					newFileStatus.setError(
							Messages.NewPHPTypePage_phpFile + fileName + Messages.NewPHPTypePage_notPhpExtention);
				} else if (!isValidFileName(fileName)) {
					newFileStatus.setError(Messages.NewPHPTypePage_phpFile
							+ MessageFormat.format(Messages.NewPHPTypePage_filename_not_valid, fileName));
				}
			}
			updateStatus(findMostSevereStatus());
			break;
		case VALIDATE_NAMESPACE:
			namespaceStatus = new StatusInfo();
			String namespace = getNamespace();
			if (namespace != null && !namespace.isEmpty()) {
				int separator = namespace.indexOf('/', 0);
				if (separator != -1) {
					namespaceStatus.setError(Messages.NewPHPTypePage_invalidSeparator);
				} else {
					if (namespace.endsWith("\\")) {//$NON-NLS-1$
						namespaceStatus.setError(Messages.NewPHPTypePage_emptySublevel);
					} else {
						String[] segments = namespace.split("\\\\");//$NON-NLS-1$
						if (segments.length == 1 && !isValidPhpIdentifier(namespace.trim())) {
							namespaceStatus.setError(Messages.NewPHPTypePage_invalidNamespaceName);
						} else {
							for (String segment : segments) {
								if (segment.isEmpty()) {
									namespaceStatus.setError(Messages.NewPHPTypePage_emptySublevel);
								} else {
									try {
										Integer.parseInt(segment);
										namespaceStatus.setError(Messages.NewPHPTypePage_invalidSublevel);
									} catch (NumberFormatException e) {
										// valid segment
									}
								}
							}
						}
					}
				}
			}
			updateStatus(findMostSevereStatus());
			break;
		}
		String sourcePathStr = getProjectName(getSourceText());
		if (sourcePathStr == null || sourcePathStr.length() == 0) {
			return;
		}

		Path projPath = new Path(sourcePathStr);
		if (projPath.segmentCount() == 1) {
			IProject currentProject = getCurrentProject();
			if (currentProject != null && currentProject.exists()) {
				IScriptProject model = DLTKCore.create(currentProject);
				if (getInterfaces().size() > 0) {
					validateInterfaces(model);
				}
				if (getTraits().size() > 0) {
					validateTraits(model);
				}
			}
		}
	}

	private boolean isValidFileName(String fileName) {
		IStatus status = ResourcesPlugin.getWorkspace().validateName(new String(fileName), IResource.FILE);
		if (!status.isOK()) {
			return false;
		}

		String trimedString = fileName.trim();

		char[] scannedID = trimedString.toCharArray();

		if (CharOperation.contains('$', scannedID)) {
			return false;
		}

		if (CharOperation.startsWith(scannedID, new char[] { '.' })) {
			return false;
		}
		return true;
	}

	// when changing the source folder, we also change the available PHP model.
	// Therfore we must validate that all the interfaces that we already
	// selected exist in
	// the current project/source folder
	protected void validateInterfaces(IScriptProject model) {

		interfacesStatus = new StatusInfo();

		Iterator<IType> iter = getInterfaces().iterator();

		while (iter.hasNext()) {

			interfacesStatus = new StatusInfo();
			IType interfaceObj = (IType) iter.next();

			String interfaceName = interfaceObj.getElementName();

			IDLTKSearchScope scope = SearchEngine.createSearchScope(getProject());
			IType[] classes = PhpModelAccess.getDefault().findTypes(interfaceName, MatchRule.EXACT,
					Modifiers.AccInterface, 0, scope, new NullProgressMonitor());

			// could be null coming from a non PHP project when the user
			// changes source folder manually by typing a location to a Java
			// project for example..
			if (classes == null || classes.length == 0) {
				interfacesStatus.setError(Messages.NewPHPTypePage_interface + interfaceName
						+ Messages.NewPHPTypePage_doesnotExistInProject);
				updateStatus(findMostSevereStatus());
				return;
			}

			boolean foundSiblings = false;
			for (IType element : classes) {
				// check if php file name equals
				if (element.getPath().toOSString().equals(interfaceObj.getPath().toOSString())) {
					interfaceObj = element;
					foundSiblings = true;
					break;
				}
			}

			// not found exact file but same interface name...
			if (!foundSiblings) {
				interfacesStatus.setWarning(Messages.NewPHPClassPage_interface + interfaceName);
				updateStatus(findMostSevereStatus());
				return;
			}

			// check if the interface comes from a PHP file with the same name
			// as the injected one
			ISourceModule sourceModel = interfaceObj.getSourceModule();
			if (sourceModel != null) {
				IPath superClassSrcPath = sourceModel.getPath();
				String superClassSrcFileName = superClassSrcPath.toOSString();
				String currentSrcFileName = null;
				if (isInExistingPHPFile()) {
					currentSrcFileName = getExistingFileName();
				} else {
					currentSrcFileName = getNewFileName();
				}

				// check if the file name are equals and path are different
				if (!superClassSrcPath.removeLastSegments(1).equals(new Path(getSourceText()))
						&& superClassSrcFileName.equals(currentSrcFileName)) {
					interfacesStatus.setError(Messages.NewPHPTypePage_cannotImplementInterface + interfaceName);
					updateStatus(findMostSevereStatus());
					return;
				}
			}
		}
		updateStatus(findMostSevereStatus());
	}

	protected void validateTraits(IScriptProject model) {

		traitsStatus = new StatusInfo();

		Iterator<IType> iter = getTraits().iterator();

		while (iter.hasNext()) {

			traitsStatus = new StatusInfo();
			IType interfaceObj = iter.next();

			String interfaceName = interfaceObj.getElementName();

			IDLTKSearchScope scope = SearchEngine.createSearchScope(getProject());
			IType[] classes = PhpModelAccess.getDefault().findTypes(interfaceName, MatchRule.EXACT,
					Modifiers.AccInterface, 0, scope, new NullProgressMonitor());

			// could be null coming from a non PHP project when the user
			// changes source folder manually by typing a location to a Java
			// project for example..
			if (classes == null || classes.length == 0) {
				traitsStatus.setError(
						Messages.NewPHPTypePage_trait + interfaceName + Messages.NewPHPTypePage_doesnotExistInProject);
				updateStatus(findMostSevereStatus());
				return;
			}

			boolean foundSiblings = false;
			for (IType element : classes) {
				// check if php file name equals
				if (element.getPath().toOSString().equals(interfaceObj.getPath().toOSString())) {
					interfaceObj = element;
					foundSiblings = true;
					break;
				}
			}

			// not found exact file but same interface name...
			if (!foundSiblings) {
				traitsStatus.setWarning(Messages.NewPHPClassPage_trait + interfaceName);
				updateStatus(findMostSevereStatus());
				return;
			}

			// check if the interface comes from a PHP file with the same name
			// as the injected one
			ISourceModule sourceModel = interfaceObj.getSourceModule();
			if (sourceModel != null) {
				IPath superClassSrcPath = sourceModel.getPath();
				String superClassSrcFileName = superClassSrcPath.toOSString();
				String currentSrcFileName = null;
				if (isInExistingPHPFile()) {
					currentSrcFileName = getExistingFileName();
				} else {
					currentSrcFileName = getNewFileName();
				}

				// check if the file name are equals and path are different
				if (!superClassSrcPath.removeLastSegments(1).equals(new Path(getSourceText()))
						&& superClassSrcFileName.equals(currentSrcFileName)) {
					traitsStatus.setError(Messages.NewPHPTypePage_cannotImplementTrait + interfaceName);
					updateStatus(findMostSevereStatus());
					return;
				}
			}
		}
		updateStatus(findMostSevereStatus());
	}

	/**
	 * @return true if the given name is valid PHP file name extension
	 */
	private boolean isPHPSuffix(String filename) {
		IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		return type.isAssociatedWith(filename);
	}

	/**
	 * @return true if the given name is valid PHP identifier
	 */
	private boolean isValidPhpIdentifier(String name) {
		if (name != null && name.length() > 0) {
			// Check if this is a valid PHP identifier:
			if (!PHP_IDENTIFIER_PATTERN.matcher(name).matches()) {
				return false;
			}

			// check if the identifier is keyword
			if (isKeyword(name)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * return true is case the given name is keyword, false otherwize
	 */
	private boolean isKeyword(String name) {

		Collection<String> foundKeywords = PHPKeywords.getInstance(getProject().getProject()).findNamesByPrefix(name);

		for (String keyword : foundKeywords) {
			if (name.equalsIgnoreCase(keyword)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Since source folder changing affects the PHP model, it should be
	 * overriden by this class children.
	 */
	protected void sourceFolderChanged() {
		sourceFolderStatus = new StatusInfo();
		String sourcePath = sourceText.getText();
		if (sourcePath.length() == 0) {
			sourceFolderStatus.setError(Messages.NewPHPTypePage_sourceFolderMustNotBeEmpty);
		} else if (!isValidSourcePath(sourcePath)) {
			sourceFolderStatus
					.setError(Messages.NewPHPTypePage_sourceFolder2 + sourcePath + Messages.NewPHPTypePage_isIllegal);
		}
		// check if project is closed
		else {
			IProject currentProject = getCurrentProject();
			if (currentProject == null || !currentProject.isAccessible()) {
				sourceFolderStatus.setError(
						Messages.NewPHPTypePage_sourceFolder2 + sourcePath + Messages.NewPHPTypePage_isNotAccessible);
			} else {
				IScriptProject model = DLTKCore.create(currentProject);
				if (model == null) {
					sourceFolderStatus.setError(Messages.NewPHPTypePage_sourceFolder + sourcePath
							+ Messages.NewPHPTypePage_doesNotPointToPhpProject);
				}
			}
		}
		updateStatus(findMostSevereStatus());
		// disable Add button issue #15715
		if (addInterfacesBtn != null) {
			if (sourceFolderStatus.getCode() != IStatus.OK) {
				addInterfacesBtn.setEnabled(false);
			} else {
				addInterfacesBtn.setEnabled(true);
			}
		}
		if (addTraitsBtn != null) {
			if (sourceFolderStatus.getCode() != IStatus.OK) {
				addTraitsBtn.setEnabled(false);
			} else {
				addTraitsBtn.setEnabled(true);
			}
		}
	}

	protected boolean isPathExist(String path) {
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IPath containerPath = new Path(path);
		IResource resource = workspaceRoot.findMember(containerPath);
		return resource != null;
	}

	protected IModelElement getInitialPHPElement(final IStructuredSelection selection) {
		if (selection != null && !selection.isEmpty()) {
			Object obj = selection.getFirstElement();
			IModelElement codeData = null;
			if (obj instanceof NodeImpl) {
				obj = ((IAdaptable) obj).getAdapter(IModelElement.class);
			}

			if (obj instanceof ISourceModule) {
				codeData = (ISourceModule) obj;
				while (codeData != null && !(codeData instanceof ISourceModule) && !(codeData instanceof IType)
						&& !(codeData instanceof IMethod)) {
					codeData = codeData.getParent();
				}

				// fixed bug 14462 - find the file element and get the name of
				// the file
				// in case of interface insert the interface to super interfaces
				// list
				if (codeData instanceof ISourceModule) {
					try {
						if (codeData.getCorrespondingResource() == null) {
							isInExistingPHPFile = false;
							return codeData;
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}
					isInExistingPHPFile = true;
					String filename = null;
					try {
						filename = codeData.getCorrespondingResource().getFullPath().toOSString();
					} catch (ModelException e) {
						Logger.logException(e);
						return null;
					}
					int idx = Math.max(filename.lastIndexOf('\\'), filename.lastIndexOf('/'));
					if (idx != -1) {
						existingFileName = filename.substring(idx + 1);
					}
					try {
						existingFile = (IFile) codeData.getCorrespondingResource();
					} catch (ModelException e) {
					}
				} else if (codeData instanceof IType) {
					isInExistingPHPFile = false;
					IType pClassData = (IType) codeData;

					if (Flags.isInterface(pClassData.getElementType())) {
						openByInterface = pClassData;
					} else if (PHPFlags.isTrait(pClassData.getElementType())) {
						openByTrait = pClassData;
					}
				} else {
					isInExistingPHPFile = false;
				}

				return codeData;
			} else if (obj instanceof IFile) {
				isInExistingPHPFile = true;
				existingFileName = ((IFile) obj).getName();
				existingFile = (IFile) obj;
				codeData = DLTKCore.create((IFile) obj);
			}
			if (codeData != null) {
				return codeData;
			}
		}
		return null;

	}

	protected IContainer getInitialContainer(final IStructuredSelection selection) {
		if (selection != null && !selection.isEmpty()) {
			final Object obj = selection.getFirstElement();
			if (obj instanceof IModelElement) {
				IResource resource = ((IModelElement) obj).getResource();
				if (resource != null && !(resource instanceof IContainer)) {
					resource = resource.getParent();
				}
				if (resource == null) {
					IScriptProject sp = (IScriptProject) ((IModelElement) obj)
							.getAncestor(IModelElement.SCRIPT_PROJECT);
					if (sp != null) {
						resource = sp.getProject();
					}
				}
				return (IContainer) resource;
			} else if (obj instanceof IResource) {
				IResource resource = (IResource) obj;
				if (resource != null && !(resource instanceof IContainer)) {
					resource = resource.getParent();
				}
				return (IContainer) resource;
			}
		}
		return null;
	}

	@Override
	public void createControl(Composite parent) {
		String[] addButtons = new String[] { Messages.NewPHPTypePage_add, null, Messages.NewPHPTypePage_remove };
		IListAdapter<IType> listAdapter = new IListAdapter<IType>() {

			@Override
			public void customButtonPressed(ListDialogField<IType> field, int index) {
			}

			@Override
			public void doubleClicked(ListDialogField<IType> field) {
			}

			@Override
			public void selectionChanged(ListDialogField<IType> field) {
			}
		};

		fSuperInterfacesDialogField = new ListDialogField<IType>(listAdapter, addButtons,
				new PHPFullPathLabelProvider()) {
			// override these methods to validate interfaces
			@Override
			public void removeElement(IType element) throws IllegalArgumentException {
				super.removeElement(element);
				validateInterfaces(getProject());
			}

			@Override
			public void removeElements(List<IType> elements) {
				super.removeElements(elements);
				validateInterfaces(getProject());
			}

			@Override
			public void removeAllElements() {
				super.removeAllElements();
				validateInterfaces(getProject());
			}
		};
		fSuperInterfacesDialogField.setTableColumns(new ListDialogField.ColumnsDescription(1, false));
		fSuperInterfacesDialogField.setLabelText(getSuperInterfacesLabel());
		fSuperInterfacesDialogField.setRemoveButtonIndex(2);
		fSuperInterfacesDialogField.setDialogFieldListener(this);

		fTraitsDialogField = new ListDialogField<IType>(listAdapter, addButtons, new PHPFullPathLabelProvider()) {
			// override these methods to validate interfaces
			@Override
			public void removeElement(IType element) throws IllegalArgumentException {
				super.removeElement(element);
				validateInterfaces(getProject());
			}

			@Override
			public void removeElements(List<IType> elements) {
				super.removeElements(elements);
				validateInterfaces(getProject());
			}

			@Override
			public void removeAllElements() {
				super.removeAllElements();
				validateInterfaces(getProject());
			}
		};
		fTraitsDialogField.setTableColumns(new ListDialogField.ColumnsDescription(1, false));
		fTraitsDialogField.setLabelText(Messages.NewPHPClassPage_traits);
		fTraitsDialogField.setRemoveButtonIndex(2);
		fTraitsDialogField.setDialogFieldListener(this);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the Source Folder field.
	 */
	private void chooseNewSourceFolder() {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), false, Messages.NewPHPTypePage_selectNewSourceFolder);
		dialog.showClosedProjects(false);
		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1) {
				sourceText.setText(((Path) result[0]).toOSString());
			}
		}
	}

	private void openPhpFileDialog() {
		IFile[] selected = choosePHPEntries(null, new ArrayList<>());
		if (selected == null || selected.length == 0) {
			return;
		}
		existingFile = selected[0];
		sourceText.setText(existingFile.getParent().getFullPath().toString());
		existingFileText.setText(existingFile.getName());
	}

	// a UI component to select an existing file
	private IFile[] choosePHPEntries(IPath initialSelection, List<Object> usedFiles) {
		Class<?>[] acceptedClasses = new Class[] { IFile.class };
		TypedElementSelectionValidator validator = new TypedElementSelectionValidator(acceptedClasses, true);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource focus = initialSelection != null ? root.findMember(initialSelection) : null;

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog.setValidator(validator);
		dialog.setTitle(Messages.NewPHPTypePage_phpFileSelection);
		dialog.setMessage(Messages.NewPHPTypePage_selectExistingPHPFile);
		dialog.addFilter(new NotUsedPHPFileFilter(usedFiles));
		dialog.setInput(root);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		dialog.setInitialSelection(focus);

		if (dialog.open() == Window.OK) {
			Object[] elements = dialog.getResult();
			IFile[] res = new IFile[elements.length];
			for (int i = 0; i < res.length; i++) {
				res[i] = (IFile) elements[i];
			}
			return res;
		}
		return null;
	}

	class NotUsedPHPFileFilter extends ViewerFilter {

		private List<Object> fExcludes;

		private final IContentType type = Platform.getContentTypeManager()
				.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);

		public NotUsedPHPFileFilter(List<Object> excludedFiles) {
			fExcludes = excludedFiles;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof IFile) {
				if (fExcludes != null && fExcludes.contains(element)) {
					return false;
				}
				IFile file = (IFile) element;
				return type.isAssociatedWith(file.getName());
			}
			return true;
		}
	}

	// Interfaces Selection Control
	protected Object chooseInterfaces() {
		FilteredTypesSelectionDialog dialog = new FilteredTypesSelectionDialog(getShell(), false,
				PlatformUI.getWorkbench().getProgressService(), SearchEngine.createSearchScope(getProject()),
				IDLTKSearchConstants.TYPE, new TypeSelectionExtension() {
					@Override
					public ITypeInfoFilterExtension getFilterExtension() {
						return typeInfoRequestor -> {
							// is interface
							if (Flags.isInterface(typeInfoRequestor.getModifiers())) {
								List<IType> alreadySelectedInterfaces = fSuperInterfacesDialogField.getElements();
								for (IType interfaceName : alreadySelectedInterfaces) {
									if (interfaceName.getElementName()
											.equalsIgnoreCase(typeInfoRequestor.getTypeName())) {
										return false;
									}
								}
								return true;
							}
							return false;
						};
					}
				}, PHPLanguageToolkit.getDefault());
		dialog.setListLabelProvider(new PHPFullPathLabelProvider());
		dialog.setListSelectionLabelDecorator(new PHPFullPathLabelProvider());
		dialog.setDetailsLabelProvider(new StatusLineLabelProvider());

		dialog.setTitle(Messages.NewPHPTypePage_interfacesSelection);
		dialog.setMessage(Messages.NewPHPTypePage_selectInterfaces);
		dialog.setInitialPattern("", //$NON-NLS-1$
				FilteredItemsSelectionDialog.FULL_SELECTION);

		if (dialog.open() == Window.OK) {
			Object[] resultArray = dialog.getResult();
			if ((resultArray != null) && (resultArray.length > 0)) {
				return resultArray[0];
			}
		}
		return null;
	}

	// Interfaces Selection Control
	protected Object chooseTraits() {
		FilteredTypesSelectionDialog dialog = new FilteredTypesSelectionDialog(getShell(), false,
				PlatformUI.getWorkbench().getProgressService(), SearchEngine.createSearchScope(getProject()),
				IDLTKSearchConstants.TYPE, new TypeSelectionExtension() {
					@Override
					public ITypeInfoFilterExtension getFilterExtension() {
						return typeInfoRequestor -> {
							// is interface
							if (PHPFlags.isTrait(typeInfoRequestor.getModifiers())) {
								List<IType> alreadySelectedInterfaces = fTraitsDialogField.getElements();
								for (IType interfaceName : alreadySelectedInterfaces) {
									if (interfaceName.getElementName()
											.equalsIgnoreCase(typeInfoRequestor.getTypeName())) {
										return false;
									}
								}
								return true;
							}
							return false;
						};
					}
				}, PHPLanguageToolkit.getDefault());
		dialog.setListLabelProvider(new PHPFullPathLabelProvider());
		dialog.setListSelectionLabelDecorator(new PHPFullPathLabelProvider());
		dialog.setDetailsLabelProvider(new StatusLineLabelProvider());

		dialog.setTitle(Messages.NewPHPTypePage_traitsSelection);
		dialog.setMessage(Messages.NewPHPTypePage_selectTraits);
		dialog.setInitialPattern("", //$NON-NLS-1$
				FilteredItemsSelectionDialog.FULL_SELECTION);

		if (dialog.open() == Window.OK) {
			Object[] resultArray = dialog.getResult();
			if ((resultArray != null) && (resultArray.length > 0)) {
				return resultArray[0];
			}
		}
		return null;
	}

	public String getSourceText() {
		return sourceText.getText().trim();
	}

	/**
	 * Finds the most severe error (if there is one)
	 */
	protected IStatus findMostSevereStatus() {
		return StatusUtil.getMostSevere(new IStatus[] { sourceFolderStatus, elementNameStatus, newFileStatus,
				existingFileStatus, interfacesStatus, namespaceStatus });
	}

	public PHPVersion getPhpVersion() {
		return phpVersion;
	}

	protected void updateNewFilename() {
		if (newFileText == null || newFileText.isDisposed()) {
			return;
		}
		if (!elementNameStatus.matches(IStatus.ERROR) && !isInExistingPHPFile()) {
			newFileText.setText(getElementName() + ".php"); //$NON-NLS-1$
		}
	}

	protected IProject getCurrentProject() {
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		String projectPath = getSourceText();
		if ((projectPath == null) || (projectPath.length() == 0)) {
			return null;
		}
		if (!isValidSourcePath(projectPath)) {
			return null;
		}
		return workspaceRoot.getProject(getProjectName(projectPath));
	}

	protected String getValue(Key key) {
		IProject currProject = getCurrentProject();
		IScopeContext[] fLookupOrder;
		if (currProject != null) {
			fLookupOrder = new IScopeContext[] { new ProjectScope(currProject), InstanceScope.INSTANCE,
					DefaultScope.INSTANCE };
		} else {
			fLookupOrder = new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		}

		WorkingCopyManager manager = new WorkingCopyManager();
		return key.getStoredValue(fLookupOrder, false, manager);
	}

	protected final static Key getPHPCoreKey(String key) {
		return getKey(PHPCorePlugin.ID, key);
	}

	protected static Key getKey(String plugin, String key) {
		return new Key(plugin, key);
	}

	public Button getButton(String name) {
		Button[] checkboxes = getGeneratedGroupCheckboxes();
		for (Button button : checkboxes) {
			if (button.getText().trim().equals(name)) {
				return button;
			}
		}
		return null;
	}

	@Override
	public void dialogFieldChanged(DialogField field) {
		changeButtonEnableStatus();
	}

	protected void changeButtonEnableStatus() {
		Button button = getButton(REQUIRE_ONCE);
		if (button != null) {
			button.setEnabled(requireOnceShouldEnabled());
		}
	}

	protected boolean requireOnceShouldEnabled() {
		return !fSuperInterfacesDialogField.getElements().isEmpty() || !fTraitsDialogField.getElements().isEmpty();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			elementName.setFocus();
		}
	}
}
