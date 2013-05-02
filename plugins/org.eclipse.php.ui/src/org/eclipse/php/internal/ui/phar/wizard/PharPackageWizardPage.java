package org.eclipse.php.internal.ui.phar.wizard;

import java.io.File;
import java.util.*;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ModelElementSorter;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.php.internal.core.phar.PharConstants;
import org.eclipse.php.internal.core.phar.PharPackage;
import org.eclipse.php.internal.core.phar.StatusInfo;
import org.eclipse.php.internal.core.phar.digest.Digest;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider;
import org.eclipse.php.internal.ui.explorer.PHPExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;
import org.eclipse.wst.jsdt.ui.ProblemsLabelDecorator;

public class PharPackageWizardPage extends WizardExportResourcesPage implements
		IPharWizardPage {

	// Untyped listener
	private class UntypedListener implements Listener {
		/*
		 * Implements method from Listener
		 */
		public void handleEvent(Event e) {
			if (getControl() == null)
				return;
			update();
		}
	}

	private UntypedListener fUntypedListener = new UntypedListener();

	private static final String SPLASH1 = "/"; //$NON-NLS-1$
	private static final String SPLASH2 = "\\"; //$NON-NLS-1$
	private static final String PAGE_NAME = PharPackagerMessages.JarPackageWizardPage_Title; 
	private static final String STORE_EXPORT_TYPE = PAGE_NAME + ".EXPORT_TYPE"; //$NON-NLS-1$
	private static final String STORE_COMPRESS_TYPE = PAGE_NAME
			+ ".COMPRESS_TYPE"; //$NON-NLS-1$
	private final static String STORE_OVERWRITE = PAGE_NAME + ".OVERWRITE"; //$NON-NLS-1$
	private final static String STORE_STUB_GENERATED = PAGE_NAME
			+ ".STUB_GENERATED"; //$NON-NLS-1$
	private final static String STORE_STUB_PATH = PAGE_NAME + ".STUB_PATH"; //$NON-NLS-1$
	private final static String USE_SIGNATURE = PAGE_NAME + ".USE_SIGNATURE"; //$NON-NLS-1$
	private final static String SIGNATURE_TYPE = PAGE_NAME + ".SIGNATURE_TYPE"; //$NON-NLS-1$
	private final static String STORE_INCLUDE_DIRECTORY_ENTRIES = PAGE_NAME
			+ ".INCLUDE_DIRECTORY_ENTRIES"; //$NON-NLS-1$
	private final String fStoreDestinationNamesId = PAGE_NAME
			+ ".DESTINATION_NAMES_ID"; //$NON-NLS-1$
	private final String EMPTYSTRING = ""; //$NON-NLS-1$
	private IStructuredSelection fInitialSelection;
	private CheckboxTreeAndListGroup fInputGroup;

	// private Button fUseSignatureCheckbox;

	private Button fOverwriteCheckbox;
	private boolean fInitiallySelecting = true;
	private List<Button> signatureButtons;

	// other constants
	private static final int SIZING_SELECTION_WIDGET_WIDTH = 480;
	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 150;

	private Combo fDestinationNamesCombo;
	private Button fDestinationBrowseButton;
	private final PharPackage pharData;

	private Button pharCompressRadio;
	private Button zipCompressRadio;
	private Button tarCompressRadio;

	private Composite exportTypeGroup;
	private Button noneCompressTypePhar;
	private Button zlibCompressTypePhar;
	private Button bzipCompressTypePhar;
	private Composite compressTypeGroup;

	// Manifest Widgets
	private Composite fManifestGroup;
	private Button fGenerateManifestRadioButton;
	private Button fUseManifestRadioButton;
	private Text fManifestFileText;
	private Label fManifestFileLabel;
	private Button fManifestFileBrowseButton;

	private String[] fileNames;

	public PharPackageWizardPage(PharPackage pharData,
			IStructuredSelection fSelection) {
		super(PAGE_NAME, fSelection);
		setTitle(PharPackagerMessages.JarPackageWizardPage_title);
		setDescription(PharPackagerMessages.JarPackageWizardPage_description);
		this.pharData = pharData;
		fInitialSelection = fSelection;
	}

	/*
	 * Method declared on IDialogPage.
	 */
	public void createControl(final Composite parent) {

		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());

		createInputGroup(composite);

		createDestinationGroup(composite);

		createOptionsGroup(composite);

		createLabel(
				composite,
				PharPackagerMessages.JarManifestWizardPage_manifestSource_label,
				false);
		createManifestGroup(composite);

		createSignatureGroup(composite);
		restoreResourceSpecificationWidgetValues(); // ie.- local
		restoreWidgetValues(); // ie.- subclass hook
		if (fInitialSelection != null) {
			setupBasedOnInitialSelections();
		}

		update();
		setPageComplete(determinePageCompletion());
		setErrorMessage(null); // should not initially have error message

		setControl(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IPHPHelpContextIds.PHARPACKAGER_WIZARD_PAGE);
	}

	private void createSignatureGroup(Composite parent) {
		// Composite signatureGroup = new Composite(parent, SWT.NONE);
		// GridLayout layout = new GridLayout();
		// layout.marginHeight = 0;
		// signatureGroup.setLayout(layout);

		// fUseSignatureCheckbox = new Button(signatureGroup, SWT.CHECK |
		// SWT.LEFT);
		// fUseSignatureCheckbox.setText("Use signature");
		// fUseSignatureCheckbox.addListener(SWT.Selection, this);

		{

			Composite signatureTypeGroup = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout();
			layout.horizontalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			layout.numColumns = Digest.DIGEST_MAP.size();
			signatureTypeGroup.setLayout(layout);

			Label label = new Label(signatureTypeGroup, SWT.NONE);
			label.setText(PharPackagerMessages.JarPackageWizardPage_Signature_Type);
			label.setLayoutData(new GridData(
					GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.CENTER,
					false, false, Digest.DIGEST_MAP.size(), 1));
			signatureButtons = new ArrayList<Button>();
			for (Iterator<String> iterator = Digest.DIGEST_MAP.keySet()
					.iterator(); iterator.hasNext();) {
				final String type = (String) iterator.next();
				Button signatureButton = new Button(signatureTypeGroup,
						SWT.RADIO | SWT.LEFT);
				signatureButton.setText(type);

				signatureButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						pharData.setSignature(type);
					}
				});
				signatureButtons.add(signatureButton);
			}
			// pharCompressRadio = new Button(signatureTypeGroup, SWT.RADIO
			// | SWT.LEFT);
			// pharCompressRadio.setText("phar");
			// // pharCompressRadio.setSelection(true);
			// pharCompressRadio.addListener(SWT.Selection, this);
			//
			// zipCompressRadio = new Button(signatureTypeGroup, SWT.RADIO
			// | SWT.LEFT);
			// zipCompressRadio.setText("zip");
			// zipCompressRadio.addListener(SWT.Selection, this);
			//
			// tarCompressRadio = new Button(signatureTypeGroup, SWT.RADIO
			// | SWT.LEFT);
			// tarCompressRadio.setText("tar");
			// tarCompressRadio.addListener(SWT.Selection, this);

		}
	}

	/**
	 * Initializes the JAR package from last used wizard page values.
	 */
	protected void initializeJarPackage() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			fileNames = settings.getArray(fStoreDestinationNamesId);
			if (fileNames == null) {
				fileNames = new String[0];
				return; // ie.- no settings stored
			}

			pharData.setPharLocation(Path.fromOSString(fileNames[0]));

			// source
			pharData.setElements(getSelectedElements());

			// options
			pharData.setCompressType(settings.getInt(STORE_COMPRESS_TYPE));
			pharData.setExportType(settings.getInt(STORE_EXPORT_TYPE));
			pharData.setIncludeDirectoryEntries(settings
					.getBoolean(STORE_INCLUDE_DIRECTORY_ENTRIES));
			pharData.setOverwrite(settings.getBoolean(STORE_OVERWRITE));
			pharData
					.setStubGenerated(settings.getBoolean(STORE_STUB_GENERATED));
			pharData.setStubLocation(Path.fromOSString(settings
					.get(STORE_STUB_PATH)));
			// pharData.setUseSignature(settings.getBoolean(USE_SIGNATURE));
			String signatureType = settings.get(SIGNATURE_TYPE);
			if (signatureType != null && signatureType.trim().length() > 0) {
				pharData.setSignature(signatureType);
			} else {
				pharData.setSignature(Digest.MD5_TYPE);
			}

		}
	}

	/**
	 * Hook method for restoring widget values to the values that they held last
	 * time this wizard was used to completion.
	 */
	protected void restoreWidgetValues() {
		super.restoreWidgetValues();

		initializeJarPackage();

		if (fileNames != null && fileNames.length > 0) {
			fDestinationNamesCombo.setItems(fileNames);
			fDestinationNamesCombo.select(0);
		}
		if (pharData.getExportType() == PharConstants.PHAR) {
			pharCompressRadio.setSelection(true);
		} else if (pharData.getExportType() == PharConstants.TAR) {
			tarCompressRadio.setSelection(true);
		} else if (pharData.getExportType() == PharConstants.ZIP) {
			zipCompressRadio.setSelection(true);
			setZipExportType();
		}

		if (pharData.getCompressType() == PharConstants.NONE_COMPRESSED) {
			noneCompressTypePhar.setSelection(true);
		} else if (pharData.getCompressType() == PharConstants.BZ2_COMPRESSED) {
			bzipCompressTypePhar.setSelection(true);
		} else if (pharData.getCompressType() == PharConstants.GZ_COMPRESSED) {
			zlibCompressTypePhar.setSelection(true);
		}
		// fIncludeDirectoryEntriesCheckbox.setSelection(pharData
		// .areDirectoryEntriesIncluded());
		fOverwriteCheckbox.setSelection(pharData.allowOverwrite());
		if (pharData.isStubGenerated()) {
			fGenerateManifestRadioButton.setSelection(true);
		} else {
			fUseManifestRadioButton.setSelection(true);
			fManifestFileText.setText(pharData.getStubLocation().toString());
		}

		// fUseSignatureCheckbox.setSelection(pharData.isUseSignature());
		if (pharData.getSignature() != null) {
			for (Iterator<Button> iterator = signatureButtons.iterator(); iterator
					.hasNext();) {
				Button button = iterator.next();
				if (button.getText().equals(pharData.getSignature())) {
					button.setSelection(true);
					break;
				}
			}
		} else {
			if (signatureButtons.size() > 0) {
				signatureButtons.get(0).setSelection(true);
			}
		}

		// fUseSignatureCheckbox.sets
		updateEnableState();
	}

	private void setZipExportType() {
		noneCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_no);
		zlibCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_yes);
		bzipCompressTypePhar.setVisible(false);
	}

	/**
	 * Creates a new label with a bold font.
	 * 
	 * @param parent
	 *            the parent control
	 * @param text
	 *            the label text
	 * @param bold
	 *            bold or not
	 * @return the new label control
	 */
	protected Label createLabel(Composite parent, String text, boolean bold) {
		Label label = new Label(parent, SWT.NONE);
		if (bold)
			label.setFont(JFaceResources.getBannerFont());
		label.setText(text);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	/**
	 * Create the export options specification widgets.
	 * 
	 * @param parent
	 *            org.eclipse.swt.widgets.Composite
	 */
	protected void createManifestGroup(Composite parent) {
		fManifestGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		fManifestGroup.setLayout(layout);
		fManifestGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL
						| GridData.GRAB_HORIZONTAL));

		fGenerateManifestRadioButton = new Button(fManifestGroup, SWT.RADIO
				| SWT.LEFT);
		fGenerateManifestRadioButton
				.setText(PharPackagerMessages.JarManifestWizardPage_genetateManifest_text);
		fGenerateManifestRadioButton.addListener(SWT.Selection,
				fUntypedListener);

		fUseManifestRadioButton = new Button(fManifestGroup, SWT.RADIO
				| SWT.LEFT);
		fUseManifestRadioButton
				.setText(PharPackagerMessages.JarManifestWizardPage_useManifest_text);
		fUseManifestRadioButton.addListener(SWT.Selection, fUntypedListener);

		fUseManifestRadioButton.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		Composite existingManifestGroup = new Composite(fManifestGroup,
				SWT.NONE);
		GridLayout existingManifestLayout = new GridLayout();
		existingManifestLayout.marginWidth = 0;
		existingManifestGroup.setLayout(existingManifestLayout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.horizontalIndent = 20;
		existingManifestGroup.setLayoutData(data);
		createManifestFileGroup(existingManifestGroup);
	}

	protected void createManifestFileGroup(Composite parent) {
		// destination specification group
		Composite manifestFileGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginWidth = 0;

		manifestFileGroup.setLayout(layout);
		manifestFileGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL
						| GridData.GRAB_HORIZONTAL));

		fManifestFileLabel = new Label(manifestFileGroup, SWT.NONE);
		fManifestFileLabel
				.setText(PharPackagerMessages.JarManifestWizardPage_manifestFile_text);

		// entry field
		fManifestFileText = new Text(manifestFileGroup, SWT.SINGLE | SWT.BORDER);
		fManifestFileText.setEditable(false);
		fManifestFileText.addListener(SWT.Modify, fUntypedListener);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(40);
		fManifestFileText.setLayoutData(data);

		// browse button
		fManifestFileBrowseButton = new Button(manifestFileGroup, SWT.PUSH);
		fManifestFileBrowseButton
				.setText(PharPackagerMessages.JarManifestWizardPage_manifestFileBrowse_text);
		fManifestFileBrowseButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));
		fManifestFileBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleManifestFileBrowseButtonPressed();
			}
		});
	}

	protected void handleManifestFileBrowseButtonPressed() {
		ElementTreeSelectionDialog dialog = createWorkspaceFileSelectionDialog(
				PharPackagerMessages.JarManifestWizardPage_manifestSelectionDialog_title,
				PharPackagerMessages.JarManifestWizardPage_manifestSelectionDialog_message);
		if (pharData.getStubFile() != null && pharData.isStubAccessible())
			dialog.setInitialSelections(new IResource[] { pharData
					.getStubFile() });
		if (dialog.open() == Window.OK) {
			Object[] resources = dialog.getResult();
			if (resources.length != 1)
				setErrorMessage(PharPackagerMessages.JarManifestWizardPage_error_onlyOneManifestMustBeSelected);
			else {
				//				setErrorMessage(""); 
				if(resources[0] instanceof ISourceModule){
					ISourceModule sm = (ISourceModule)resources[0];
					resources[0] = sm.getResource();
				}
				pharData.setStubLocation(((IResource) resources[0])
						.getFullPath());
				fManifestFileText
						.setText(pharData.getStubLocation().toString());
			}
		}
	}

	/**
	 * Creates and returns a dialog to choose an existing workspace file.
	 * 
	 * @param title
	 *            the title
	 * @param message
	 *            the dialog message
	 * @return the dialog
	 */
	protected ElementTreeSelectionDialog createWorkspaceFileSelectionDialog(
			String title, String message) {
		ScriptExplorerContentProvider treeContentProvider = createContentProvider();
		final IPreferenceStore store = DLTKUIPlugin.getDefault()
				.getPreferenceStore();
		final DecoratingLabelProvider provider = new DecoratingLabelProvider(
				new PHPExplorerLabelProvider(treeContentProvider, store),
				new ProblemsLabelDecorator(null));

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(), provider, treeContentProvider);
		// dialog.setComparator(new JavaElementComparator());
		dialog.setAllowMultiple(false);
		dialog.setValidator(new ISelectionStatusValidator() {
			public IStatus validate(Object[] selection) {
				StatusInfo res = new StatusInfo();
				// only single selection
				if (selection.length == 1 && (selection[0] instanceof IFile || selection[0] instanceof ISourceModule))
					res.setOK();
//				else
//					res.setError(""); 
				return res;
			}
		});
		dialog.addFilter(new EmptyInnerPackageFilter());
		// dialog.addFilter(new LibraryFilter());
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setStatusLineAboveButtons(true);
		dialog.setInput(DLTKCore.create(ResourcesPlugin.getWorkspace()
				.getRoot()));
		return dialog;
	}

	/**
	 * Set the current input focus to self's destination entry field
	 */
	protected void giveFocusToDestination() {
		fDestinationNamesCombo.setFocus();
	}

	/**
	 * This method should only be called inside this class and from test cases.
	 * 
	 * @return the created content provider
	 */
	public ScriptExplorerContentProvider createContentProvider() {
		boolean showCUChildren = DLTKUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.SHOW_SOURCE_MODULE_CHILDREN);
		// if (getRootMode() == ScriptExplorerPart.PROJECTS_AS_ROOTS) {
		return new PHPExplorerContentProvider(showCUChildren) {
			protected IPreferenceStore getPreferenceStore() {
				return DLTKUIPlugin.getDefault().getPreferenceStore();
			}
		};
		// }
	}

	/**
	 * Creates the checkbox tree and list for selecting resources.
	 * 
	 * @param parent
	 *            the parent control
	 */
	protected void createInputGroup(Composite parent) {
		// int labelFlags= JavaElementLabelProvider.SHOW_BASICS
		// | JavaElementLabelProvider.SHOW_OVERLAY_ICONS
		// | JavaElementLabelProvider.SHOW_SMALL_ICONS;

		ScriptExplorerContentProvider treeContentProvider = createContentProvider();
		final IPreferenceStore store = DLTKUIPlugin.getDefault()
				.getPreferenceStore();
		final DecoratingLabelProvider provider = new DecoratingLabelProvider(
				new PHPExplorerLabelProvider(treeContentProvider, store),
				new ProblemsLabelDecorator(null));
		fInputGroup = new CheckboxTreeAndListGroup(parent, DLTKCore
				.create(ResourcesPlugin.getWorkspace().getRoot()),
				treeContentProvider, provider,
				new StandardModelElementContentProvider(), provider, SWT.NONE,
				SIZING_SELECTION_WIDGET_WIDTH, SIZING_SELECTION_WIDGET_HEIGHT) {

			protected void setTreeChecked(final Object element,
					final boolean state) {
				if (fInitiallySelecting && element instanceof IResource) {
					final IResource resource = (IResource) element;
					if (resource.getName().charAt(0) == '.')
						return;
				}
				super.setTreeChecked(element, state);
			}
		};
		fInputGroup.addTreeFilter(new EmptyInnerPackageFilter());
		ModelElementSorter comparator = new ModelElementSorter();
		comparator.setInnerElements(false);
		fInputGroup.setTreeComparator(comparator);
		fInputGroup.setListComparator(comparator);
		fInputGroup.addTreeFilter(new ContainerFilter(
				ContainerFilter.FILTER_NON_CONTAINERS));
		fInputGroup.addTreeFilter(new ViewerFilter() {
			public boolean select(Viewer viewer, Object p, Object element) {
				if (element instanceof IProjectFragment) {
					IProjectFragment root = (IProjectFragment) element;
					return !root.isArchive() && !root.isExternal();
				}
				return true;
			}
		});
		fInputGroup.addListFilter(new ContainerFilter(
				ContainerFilter.FILTER_CONTAINERS));
		fInputGroup.getTree().addListener(SWT.MouseUp, this);
		fInputGroup.getTable().addListener(SWT.MouseUp, this);

//		setAccessibilityText(fInputGroup.getTree(), "");
//		setAccessibilityText(fInputGroup.getTable(), "");

		ICheckStateListener listener = new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				update();
			}
		};

		fInputGroup.addCheckStateListener(listener);
	}

	/**
	 * Create the export options specification widgets.
	 * 
	 * @param parent
	 *            org.eclipse.swt.widgets.Composite
	 */
	protected void createOptionsGroup(Composite parent) {
		Composite optionsGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		optionsGroup.setLayout(layout);

		{

			exportTypeGroup = new Composite(optionsGroup, SWT.NONE);
			layout = new GridLayout();
			layout.horizontalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			layout.numColumns = 3;
			exportTypeGroup.setLayout(layout);

			Label label = new Label(exportTypeGroup, SWT.NONE);
			label.setText(PharPackagerMessages.JarPackageWizardPage_Export_Type);
			label.setLayoutData(new GridData(
					GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.CENTER,
					false, false, 3, 1));

			pharCompressRadio = new Button(exportTypeGroup, SWT.RADIO
					| SWT.LEFT);
			pharCompressRadio.setText(PharPackagerMessages.JarPackageWizardPage_Export_Type_phar);
			// pharCompressRadio.setSelection(true);
			pharCompressRadio.addListener(SWT.Selection, this);

			zipCompressRadio = new Button(exportTypeGroup, SWT.RADIO | SWT.LEFT);
			zipCompressRadio.setText(PharPackagerMessages.JarPackageWizardPage_Export_Type_zip);
			zipCompressRadio.addListener(SWT.Selection, this);

			tarCompressRadio = new Button(exportTypeGroup, SWT.RADIO | SWT.LEFT);
			tarCompressRadio.setText(PharPackagerMessages.JarPackageWizardPage_Export_Type_tar);
			tarCompressRadio.addListener(SWT.Selection, this);

		}

		{

			compressTypeGroup = new Composite(optionsGroup, SWT.NONE);
			layout = new GridLayout();
			layout.horizontalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			layout.numColumns = 3;
			compressTypeGroup.setLayout(layout);

			Label label = new Label(exportTypeGroup, SWT.NONE);
			label.setText(PharPackagerMessages.JarPackageWizardPage_Compress_Type);
			label.setLayoutData(new GridData(
					GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.CENTER,
					false, false, 3, 1));

			noneCompressTypePhar = new Button(compressTypeGroup, SWT.RADIO
					| SWT.LEFT);
			noneCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_Compress_Type_none);
			// noneCompressTypePhar.setSelection(true);
			noneCompressTypePhar.addListener(SWT.Selection, this);

			bzipCompressTypePhar = new Button(compressTypeGroup, SWT.RADIO
					| SWT.LEFT);
			bzipCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_Compress_Type_bz2);
			bzipCompressTypePhar.addListener(SWT.Selection, this);

			zlibCompressTypePhar = new Button(compressTypeGroup, SWT.RADIO
					| SWT.LEFT);
			zlibCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_yes);
			zlibCompressTypePhar.addListener(SWT.Selection, this);
			zlibCompressTypePhar.setLayoutData(new GridData(
					GridData.FILL_HORIZONTAL, GridData.CENTER, true, false, 1,
					1));
		}

		fOverwriteCheckbox = new Button(optionsGroup, SWT.CHECK | SWT.LEFT);
		fOverwriteCheckbox.setText(PharPackagerMessages.JarPackageWizardPage_overwrite_text);
		fOverwriteCheckbox.addListener(SWT.Selection, this);

		// fIncludeDirectoryEntriesCheckbox = new Button(optionsGroup, SWT.CHECK
		// | SWT.LEFT);
		// fIncludeDirectoryEntriesCheckbox.setText("A&dd directory entries");
		// fIncludeDirectoryEntriesCheckbox.addListener(SWT.Selection, this);
	}

	@Override
	protected void createDestinationGroup(Composite parent) {

		initializeDialogUnits(parent);

		// destination specification group
		Composite destinationSelectionGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		destinationSelectionGroup.setLayout(layout);
		destinationSelectionGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));

		String label = PharPackagerMessages.JarPackageWizardPage_PHAR_file;
//		if (label != null) {
			new Label(destinationSelectionGroup, SWT.NONE).setText(label);
//		} else {
//			layout.marginWidth = 0;
//			layout.marginHeight = 0;
//		}

		// destination name entry field
		fDestinationNamesCombo = new Combo(destinationSelectionGroup,
				SWT.SINGLE | SWT.BORDER);
		fDestinationNamesCombo.setVisibleItemCount(30);
		fDestinationNamesCombo.addListener(SWT.Modify, this);
		fDestinationNamesCombo.addListener(SWT.Selection, this);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
//		data.horizontalSpan = label == null ? 2 : 1;
		fDestinationNamesCombo.setLayoutData(data);

//		if (label == null) {
//			setAccessibilityText(fDestinationNamesCombo, "");
//		}

		// destination browse button
		fDestinationBrowseButton = new Button(destinationSelectionGroup,
				SWT.PUSH);
		fDestinationBrowseButton
				.setText(PharPackagerMessages.JarPackageWizardPage_browseButton_text);
		fDestinationBrowseButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));
		Object gd = fDestinationBrowseButton.getLayoutData();
		if (gd instanceof GridData) {
			((GridData) gd).widthHint = getButtonWidthHint(fDestinationBrowseButton);
			((GridData) gd).horizontalAlignment = GridData.FILL;
		}
		fDestinationBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleDestinationBrowseButtonPressed();
			}
		});
	}

	/**
	 * Open an appropriate destination browser so that the user can specify a
	 * source to import from
	 */
	protected void handleDestinationBrowseButtonPressed() {
		FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] {});

		String currentSourceString = getDestinationValue();
		int lastSeparatorIndex = currentSourceString
				.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1) {
			dialog.setFilterPath(currentSourceString.substring(0,
					lastSeparatorIndex));
			dialog.setFileName(currentSourceString.substring(
					lastSeparatorIndex + 1, currentSourceString.length()));
		}
		String selectedFileName = dialog.open();
		if (selectedFileName != null) {
			IContainer[] findContainersForLocation = ResourcesPlugin
					.getWorkspace().getRoot().findContainersForLocation(
							new Path(selectedFileName));
			if (findContainersForLocation.length > 0) {
				selectedFileName = findContainersForLocation[0].getFullPath()
						.makeRelative().toString();
			}
			fDestinationNamesCombo.setText(toLegalPharPath(selectedFileName)
					.toString());
		}
	}

	private IPath toLegalPharPath(String selectedFileName) {
		// TODO Auto-generated method stub
		IPath path = new Path(selectedFileName);
		if (path.segmentCount() > 0 && ensureTargetFileIsValid(path.toFile())
				&& path.getFileExtension() == null) {
			if (pharCompressRadio.getSelection()) {
				if (!PharConstants.PHAR_EXTENSION.equals(path.getFileExtension())) {
					path = path.addFileExtension(PharConstants.PHAR_EXTENSION);
				}
			} else if (zipCompressRadio.getSelection()) {
				if (!PharConstants.PHAR_EXTENSION_ZIP.equals(path.getFileExtension())) {
					path = path.addFileExtension(PharConstants.PHAR_EXTENSION_ZIP);
				}
			} else if (tarCompressRadio.getSelection()) {
				String fileName = path.lastSegment();
				if (noneCompressTypePhar.getSelection()) {
					if (!PharConstants.PHAR_EXTENSION_TAR1.equals(path.getFileExtension())) {
						path = path.addFileExtension(PharConstants.PHAR_EXTENSION_TAR1);
					}
				} else if (zlibCompressTypePhar.getSelection()) {
					if (!fileName.endsWith(PharConstants.PHAR_EXTENSION_TAR2)) {
						path = path.addFileExtension(PharConstants.PHAR_EXTENSION_TAR3);
					}
				} else if (bzipCompressTypePhar.getSelection()) {
					if (!fileName.endsWith(PharConstants.PHAR_EXTENSION_TAR4)) {
						path = path.addFileExtension(PharConstants.PHAR_EXTENSION_TAR5);
					}
				}

			}
		}
		return path;
	}

	/**
	 * Returns a width hint for a button control.
	 * 
	 * @param button
	 *            the button
	 * @return the width hint
	 */
	public static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		PixelConverter converter = new PixelConverter(button);
		int widthHint = converter
				.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT,
				true).x);
	}

	/*
	 * Implements method from IJarPackageWizardPage.
	 */
	public void finish() {
		saveWidgetValues();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings
					.getArray(fStoreDestinationNamesId);
			if (directoryNames == null)
				directoryNames = new String[0];
			directoryNames = addToHistory(directoryNames, getDestinationValue());
			settings.put(fStoreDestinationNamesId, directoryNames);

			// options
			settings.put(STORE_EXPORT_TYPE, pharData.getExportType());
			settings.put(STORE_COMPRESS_TYPE, pharData.getCompressType());
			settings.put(STORE_INCLUDE_DIRECTORY_ENTRIES, pharData
					.areDirectoryEntriesIncluded());
			settings.put(STORE_OVERWRITE, pharData.allowOverwrite());
			settings.put(STORE_STUB_GENERATED, pharData.isStubGenerated());
			settings
					.put(STORE_STUB_PATH, pharData.getStubLocation().toString());
			settings.put(USE_SIGNATURE, pharData.isUseSignature());
			settings.put(SIGNATURE_TYPE, pharData.getSignature());
		}
	}

	/**
	 * Answer the contents of the destination specification widget. If this
	 * value does not have the required suffix then add it first.
	 * 
	 * @return java.lang.String
	 */
	protected String getDestinationValue() {
		String destinationText = fDestinationNamesCombo.getText().trim();
		// if (destinationText.indexOf('.') < 0)
		// destinationText+= getOutputSuffix();
		destinationText = toLegalPharPath(destinationText).toString();
		return destinationText;
	}

	/*
	 * Overrides method from WizardDataTransferPage
	 */
	protected boolean validateDestinationGroup() {
		if (fDestinationNamesCombo.getText().length() == 0) {
			// Clear error
			if (getErrorMessage() != null)
				setErrorMessage(null);
			if (getMessage() != null)
				setMessage(null);
			return false;
		}
		if (pharData.getAbsolutePharLocation().toString().endsWith(SPLASH1)
				|| pharData.getAbsolutePharLocation().toString().endsWith(SPLASH2)) { 
			setErrorMessage(PharPackagerMessages.JarPackageWizardPage_error_exportDestinationMustNotBeDirectory);
			fDestinationNamesCombo.setFocus();
			return false;
		}
		// Check if the Jar is put into the workspace and conflicts with the
		// containers
		// exported. If the workspace isn't on the local files system we are
		// fine since
		// the Phar is always created in the local file system
		IPath workspaceLocation = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation();
		if (workspaceLocation != null
				&& workspaceLocation.isPrefixOf(pharData
						.getAbsolutePharLocation())) {
			int segments = workspaceLocation.matchingFirstSegments(pharData
					.getAbsolutePharLocation());
			IPath path = pharData.getAbsolutePharLocation()
					.removeFirstSegments(segments);
			IResource resource = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(path);
			if (resource != null && resource.getType() == IResource.FILE) {
				// test if included
				if (contains(asResources(pharData.getElements()),
						(IFile) resource)) {
					setErrorMessage(PharPackagerMessages.JarPackageWizardPage_error_cantExportPHARIntoItself);
					return false;
				}
			}
		}
		// Inform user about relative directory
		String currentMessage = getMessage();
		if (!(new File(fDestinationNamesCombo.getText()).isAbsolute())) {
			if (currentMessage == null)
				if (currentMessage == null)
					setMessage(
							PharPackagerMessages.JarPackageWizardPage_info_relativeExportDestination,
							IMessageProvider.INFORMATION);
		} else {
			if (currentMessage != null)
				setMessage(currentMessage);
		}
		return ensureTargetFileIsValid(pharData.getAbsolutePharLocation()
				.toFile());
	}

	/*
	 * Implements method from Listener
	 */
	public void handleEvent(Event e) {
		if (getControl() == null)
			return;
		update();
	}

	protected void updateModel() {

		pharData.setElements(getSelectedElements());
		// destination
		String comboText = fDestinationNamesCombo.getText();

		IPath path = toLegalPharPath(comboText);
		// add surfix

		pharData.setPharLocation(path);
		pharData.setOverwrite(fOverwriteCheckbox.getSelection());
		// pharData.setIncludeDirectoryEntries(fIncludeDirectoryEntriesCheckbox
		// .getSelection());
		if (fGenerateManifestRadioButton.getSelection()) {
			pharData.setStubGenerated(true);
		} else {
			pharData.setStubGenerated(false);

			if (fManifestFileText.getText() != null) {
				pharData.setStubLocation(new Path(fManifestFileText.getText()));
			} else {
				pharData.setStubLocation(new Path("")); //$NON-NLS-1$
				setErrorMessage(PharPackagerMessages.JarPackageWizardPage_error_StubFileNull);
			}
		}

		if (pharCompressRadio.getSelection()) {
			noneCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_Compress_Type_none);
			zlibCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_Compress_Type_gz);
			bzipCompressTypePhar.setVisible(true);

			pharData.setExportType(PharConstants.PHAR);

		} else if (zipCompressRadio.getSelection()) {
			setZipExportType();

			pharData.setExportType(PharConstants.ZIP);

			if (bzipCompressTypePhar.getSelection()) {
				zlibCompressTypePhar.setSelection(true);
				bzipCompressTypePhar.setSelection(false);
			}

		} else if (tarCompressRadio.getSelection()) {
			noneCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_Compress_Type_none);
			zlibCompressTypePhar.setText(PharPackagerMessages.JarPackageWizardPage_Compress_Type_gz);
			bzipCompressTypePhar.setVisible(true);

			pharData.setExportType(PharConstants.TAR);

		}

		if (noneCompressTypePhar.getSelection()) {
			pharData.setCompressType(PharConstants.NONE_COMPRESSED);
		} else if (zlibCompressTypePhar.getSelection()) {
			pharData.setCompressType(PharConstants.GZ_COMPRESSED);
		} else if (bzipCompressTypePhar.getSelection()) {
			pharData.setCompressType(PharConstants.BZ2_COMPRESSED);
		}

		// pharData.setUseSignature(fUseSignatureCheckbox.getSelection());
	}

	/**
	 * Returns a boolean indicating whether the passed File handle is is valid
	 * and available for use.
	 * 
	 * @param targetFile
	 *            the target
	 * @return boolean
	 */
	protected boolean ensureTargetFileIsValid(File targetFile) {
		if (targetFile.exists() && targetFile.isDirectory()
				&& fDestinationNamesCombo.getText().length() > 0) {
			setErrorMessage(PharPackagerMessages.JarPackageWizardPage_error_exportDestinationMustNotBeDirectory);
			fDestinationNamesCombo.setFocus();
			return false;
		}
		if (targetFile.exists()) {
			if (!targetFile.canWrite()) {
				setErrorMessage(PharPackagerMessages.JarPackageWizardPage_error_jarFileExistsAndNotWritable);
				fDestinationNamesCombo.setFocus();
				return false;
			}
		}
		return true;
	}

	protected void update() {
		updateModel();
		updateWidgetEnablements();
		updateEnableState();
		updatePageCompletion();
		compressTypeGroup.layout();
	}

	private void updateEnableState() {
		boolean generate = fGenerateManifestRadioButton.getSelection();
		fManifestFileText.setEnabled(!generate);
		fManifestFileLabel.setEnabled(!generate);
		fManifestFileBrowseButton.setEnabled(!generate);

		// boolean useSignature = fUseSignatureCheckbox.getSelection();
		// for (Iterator<Button> iterator = signatureButtons.iterator();
		// iterator
		// .hasNext();) {
		// Button button = iterator.next();
		// button.setEnabled(useSignature);
		// }
	}

	protected void updatePageCompletion() {
		boolean pageComplete = isPageComplete();
		setPageComplete(pageComplete);
		if (pageComplete)
			setErrorMessage(null);
	}

	static boolean contains(java.util.List resources, IFile file) {
		if (resources == null || file == null)
			return false;

		if (resources.contains(file))
			return true;

		Iterator iter = resources.iterator();
		while (iter.hasNext()) {
			IResource resource = (IResource) iter.next();
			if (resource != null && resource.getType() != IResource.FILE) {
				java.util.List children = null;
				try {
					children = Arrays.asList(((IContainer) resource).members());
				} catch (CoreException ex) {
					// ignore this folder
					continue;
				}
				if (children != null && contains(children, file))
					return true;
			}
		}
		return false;
	}

	/**
	 * Computes and returns the elements as resources. The underlying resource
	 * is used for Java elements.
	 * 
	 * @param elements
	 *            elements for which to retrieve the resources from
	 * @return a List with the selected resources
	 */
	public static java.util.List asResources(Object[] elements) {
		if (elements == null)
			return null;
		java.util.List selectedResources = new ArrayList(elements.length);
		for (int i = 0; i < elements.length; i++) {
			Object element = elements[i];
			if (element instanceof IModelElement) {
				selectedResources.add(((IModelElement) element).getResource());
			} else if (element instanceof IResource)
				selectedResources.add(element);
		}
		return selectedResources;
	}

	/**
	 * Adds an accessibility listener returning the given fixed name.
	 * 
	 * @param control
	 *            the control to add the accessibility support to
	 * @param text
	 *            the name
	 */
	public static void setAccessibilityText(Control control, final String text) {
		control.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = text;
			}
		});
	}

	/*
	 * Overrides method from IJarPackageWizardPage
	 */
	public boolean isPageComplete() {
		boolean complete = validateDestinationGroup();
		complete = validateOptionsGroup() && complete;
		complete = validateStubGroup() && complete;
		if (complete)
			setErrorMessage(null);
		return complete;
	}

	private boolean validateStubGroup() {
		if (!fGenerateManifestRadioButton.getSelection()
				&& pharData.getStubLocation().toString().length() == 0) {
			return false;
		}
		return true;
	}

	/*
	 * Overrides method from WizardDataTransferPage
	 */
	protected boolean validateOptionsGroup() {
		return true;
	}

	protected Iterator getSelectedResourcesIterator() {
		return fInputGroup.getAllCheckedListItems();
	}

	/*
	 * Overrides method from WizardExportResourcePage
	 */
	protected void setupBasedOnInitialSelections() {
		Iterator iterator = fInitialSelection.iterator();
		while (iterator.hasNext()) {
			Object selectedElement = iterator.next();

			if (selectedElement instanceof IResource
					&& !((IResource) selectedElement).isAccessible())
				continue;

			if (selectedElement instanceof IModelElement
					&& !((IModelElement) selectedElement).exists())
				continue;

			if (selectedElement instanceof ISourceModule
					|| selectedElement instanceof IFile)
				fInputGroup.initialCheckListItem(selectedElement);
			else {
				if (selectedElement instanceof IFolder) {
					// Convert resource to Java element if possible
					IModelElement je = DLTKCore
							.create((IResource) selectedElement);
					if (je != null
							&& je.exists()
							&& je.getScriptProject().isOnBuildpath(
									(IResource) selectedElement))
						selectedElement = je;
				}
				try {
					fInputGroup.initialCheckTreeItem(selectedElement);
				} finally {
					fInitiallySelecting = false;
				}
			}
		}

		TreeItem[] items = fInputGroup.getTree().getItems();
		int i = 0;
		while (i < items.length && !items[i].getChecked())
			i++;
		if (i < items.length) {
			fInputGroup.getTree().setSelection(new TreeItem[] { items[i] });
			fInputGroup.getTree().showSelection();
			fInputGroup.populateListViewer(items[i].getData());
		}
	}

	/*
	 * Method declared on IWizardPage.
	 */
	public void setPreviousPage(IWizardPage page) {
		super.setPreviousPage(page);
		if (getControl() != null)
			updatePageCompletion();
	}

	@SuppressWarnings("unchecked")
	Object[] getSelectedElementsWithoutContainedChildren() {
		// Iterator itemIterator = getSelectedResourcesIterator();

		Set elements = new HashSet();
		for (Iterator iterator = getSelectedResourcesIterator(); iterator
				.hasNext();) {
			elements.add(iterator.next());
		}
		Map map = new HashMap();
		for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
			Object element = (Object) iterator.next();
			Object resource = element;
			if (element instanceof IModelElement) {
				try {
					resource = ((IModelElement) element)
							.getCorrespondingResource();
				} catch (ModelException e) {
					continue;
				}
			}
			map.put(resource, element);
		}

		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			Object element = (Object) iterator.next();
			if (element instanceof IResource) {
				if (isDescendent(map, (IResource) element)) {
					elements.remove(map.get(element));
				}
			}
		}
		return elements.toArray();
	}

	/**
	 * Answer a boolean indicating whether the passed child is a descendent of
	 * one or more members of the passed resources collection
	 * 
	 * @return boolean
	 * @param resources
	 *            java.util.Vector
	 * @param child
	 *            org.eclipse.core.resources.IResource
	 */
	protected boolean isDescendent(Map resources, IResource child) {
		if (child.getType() == IResource.PROJECT) {
			return false;
		}

		IResource parent = child.getParent();
		if (resources.containsKey(parent)) {
			return true;
		}

		return isDescendent(resources, parent);
	}

	private Object[] getSelectedElements() {
		return getSelectedResources().toArray();
	}
}
