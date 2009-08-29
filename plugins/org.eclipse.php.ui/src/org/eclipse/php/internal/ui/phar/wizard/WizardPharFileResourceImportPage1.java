package org.eclipse.php.internal.ui.phar.wizard;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.internal.core.phar.PharException;
import org.eclipse.php.internal.core.phar.PharFile;
import org.eclipse.php.internal.core.tar.TarException;
import org.eclipse.php.internal.core.tar.TarFile;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.MinimizedFileSystemElement;
import org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceImportPage1;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

@SuppressWarnings("restriction")
public class WizardPharFileResourceImportPage1 extends
		WizardFileSystemResourceImportPage1 implements Listener {

	ILeveledImportStructureProvider structureProvider;

	// constants
	private static final String[] FILE_IMPORT_MASK = {
			"*.phar;*.zip;*.tar;*.tar.gz;*.tgz;*.tar.bz2", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$

	// dialog store id constants
	private final static String STORE_SOURCE_NAMES_ID = "WizardZipFileResourceImportPage1.STORE_SOURCE_NAMES_ID"; //$NON-NLS-1$

	private final static String STORE_OVERWRITE_EXISTING_RESOURCES_ID = "WizardZipFileResourceImportPage1.STORE_OVERWRITE_EXISTING_RESOURCES_ID"; //$NON-NLS-1$

	private final static String STORE_SELECTED_TYPES_ID = "WizardZipFileResourceImportPage1.STORE_SELECTED_TYPES_ID"; //$NON-NLS-1$

	private final String[] fileImportMask;

	/**
	 * Creates an instance of this class
	 * 
	 * @param aWorkbench
	 *            IWorkbench
	 * @param selection
	 *            IStructuredSelection
	 */
	public WizardPharFileResourceImportPage1(IWorkbench aWorkbench,
			IStructuredSelection selection) {
		this(aWorkbench, selection, null);
	}

	/**
	 * Creates an instance of this class
	 * 
	 * @param aWorkbench
	 *            IWorkbench
	 * @param selection
	 *            IStructuredSelection
	 * @param fileImportMask
	 *            != null: override default mask
	 */

	public WizardPharFileResourceImportPage1(IWorkbench aWorkbench,
			IStructuredSelection selection, String[] fileImportMask) {
		super("zipFileImportPage1", aWorkbench, selection); //$NON-NLS-1$

		setTitle(PharImportMessages.ArchiveExport_exportTitle);
		setDescription(PharImportMessages.ArchiveImport_description);

		if (fileImportMask == null)
			this.fileImportMask = FILE_IMPORT_MASK;
		else
			this.fileImportMask = fileImportMask;
	}

	/**
	 * Called when the user presses the Cancel button. Return a boolean
	 * indicating permission to close the wizard.
	 * 
	 * @return boolean
	 */
	public boolean cancel() {
		disposeStructureProvider();
		return true;
	}

	/**
	 * (non-Javadoc) Method declared on IDialogPage.
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IPHPHelpContextIds.PHAR_FILE_IMPORT_WIZARD_PAGE);
	}

	/**
	 * Create the options specification widgets. There is only one in this case
	 * so create no group.
	 * 
	 * @param parent
	 *            org.eclipse.swt.widgets.Composite
	 */
	protected void createOptionsGroup(Composite parent) {

		// overwrite... checkbox
		overwriteExistingResourcesCheckbox = new Button(parent, SWT.CHECK);
		overwriteExistingResourcesCheckbox
				.setText(PharImportMessages.FileImport_overwriteExisting);
		overwriteExistingResourcesCheckbox.setFont(parent.getFont());
	}

	private boolean validateSourceFile(String fileName) {
		if (ArchiveFileManipulations.isPharFile(fileName)) {
			PharFile pharFile = getSpecifiedPharSourceFile(fileName);
			if (pharFile != null) {
				ArchiveFileManipulations.closePharFile(pharFile, getShell());
				return true;
			}
			return false;
		}
		if (ArchiveFileManipulations.isTarFile(fileName)) {
			TarFile tarFile = getSpecifiedTarSourceFile(fileName);
			if (tarFile != null) {
				ArchiveFileManipulations.closeTarFile(tarFile, getShell());
				return true;
			}
			return false;
		}
		ZipFile zipFile = getSpecifiedZipSourceFile(fileName);
		if (zipFile != null) {
			ArchiveFileManipulations.closeZipFile(zipFile, getShell());
			return true;
		}
		return false;
	}

	private PharFile getSpecifiedPharSourceFile(String fileName) {
		if (fileName.length() == 0) {
			return null;
		}

		try {
			return new PharFile(new File(fileName));
		} catch (PharException e) {
			displayErrorDialog(e.getLocalizedMessage());
		} catch (IOException e) {
			displayErrorDialog(PharImportMessages.ZipImport_couldNotRead);
		}

		sourceNameField.setFocus();
		return null;
	}

	/**
	 * Answer a boolean indicating whether the specified source currently exists
	 * and is valid (ie.- proper format)
	 */
	private boolean ensureZipSourceIsValid() {
		ZipFile specifiedFile = getSpecifiedZipSourceFile();
		if (specifiedFile == null) {
			return false;
		}
		return ArchiveFileManipulations.closeZipFile(specifiedFile, getShell());
	}

	private boolean ensureTarSourceIsValid() {
		TarFile specifiedFile = getSpecifiedTarSourceFile();
		if (specifiedFile == null) {
			return false;
		}
		return ArchiveFileManipulations.closeTarFile(specifiedFile, getShell());
	}

	private boolean ensurePharSourceIsValid() {
		PharFile specifiedFile = getSpecifiedPharSourceFile();
		if (specifiedFile == null) {
			return false;
		}
		return ArchiveFileManipulations
				.closePharFile(specifiedFile, getShell());
	}

	/**
	 * Answer a boolean indicating whether the specified source currently exists
	 * and is valid (ie.- proper format)
	 */
	protected boolean ensureSourceIsValid() {
		if (ArchiveFileManipulations.isPharFile(sourceNameField.getText())) {
			return ensurePharSourceIsValid();
		}
		if (ArchiveFileManipulations.isTarFile(sourceNameField.getText())) {
			return ensureTarSourceIsValid();
		}
		return ensureZipSourceIsValid();
	}

	/**
	 * The Finish button was pressed. Try to do the required work now and answer
	 * a boolean indicating success. If <code>false</code> is returned then the
	 * wizard will not close.
	 * 
	 * @return boolean
	 */
	public boolean finish() {
		if (!super.finish()) {
			return false;
		}

		disposeStructureProvider();
		return true;
	}

	/**
	 * Closes the structure provider and sets the field to <code>null</code>.
	 * 
	 * @since 3.4
	 */
	private void disposeStructureProvider() {
		ArchiveFileManipulations.closeStructureProvider(structureProvider,
				getShell());
		structureProvider = null;
	}

	/**
	 * Returns a content provider for <code>FileSystemElement</code>s that
	 * returns only files as children.
	 */
	protected ITreeContentProvider getFileProvider() {
		return new WorkbenchContentProvider() {
			public Object[] getChildren(Object o) {
				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					AdaptableList l = element.getFiles(structureProvider);
					return l.getChildren(element);
				}
				return new Object[0];
			}
		};
	}

	/**
	 * Answer the root FileSystemElement that represents the contents of the
	 * currently-specified .zip file. If this FileSystemElement is not currently
	 * defined then create and return it.
	 */
	protected MinimizedFileSystemElement getFileSystemTree() {
		disposeStructureProvider();
		// TODO FOR PHAR
		if (ArchiveFileManipulations.isPharFile(sourceNameField.getText())) {
			PharFile sourceTarFile = getSpecifiedPharSourceFile();
			if (sourceTarFile == null) {
				return null;
			}
			structureProvider = new PharLeveledStructureProvider(sourceTarFile);
			return selectFiles(structureProvider.getRoot(), structureProvider);
		}

		if (ArchiveFileManipulations.isTarFile(sourceNameField.getText())) {
			TarFile sourceTarFile = getSpecifiedTarSourceFile();
			if (sourceTarFile == null) {
				return null;
			}
			structureProvider = new TarLeveledStructureProvider(sourceTarFile);
			return selectFiles(structureProvider.getRoot(), structureProvider);
		}

		ZipFile sourceFile = getSpecifiedZipSourceFile();
		if (sourceFile == null) {
			return null;
		}

		structureProvider = new ZipLeveledStructureProvider(sourceFile);
		return selectFiles(structureProvider.getRoot(), structureProvider);
	}

	/**
	 * Returns a content provider for <code>FileSystemElement</code>s that
	 * returns only folders as children.
	 */
	protected ITreeContentProvider getFolderProvider() {
		return new WorkbenchContentProvider() {
			public Object[] getChildren(Object o) {
				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					AdaptableList l = element.getFolders(structureProvider);
					return l.getChildren(element);
				}
				return new Object[0];
			}

			public boolean hasChildren(Object o) {
				if (o instanceof MinimizedFileSystemElement) {
					// MinimizedFileSystemElement element =
					// (MinimizedFileSystemElement) o;
					// if (element.isPopulated()) {
					// return getChildren(element).length > 0;
					// }

					// If we have not populated then wait until asked
					return true;
				}
				return false;
			}
		};
	}

	/**
	 * Answer the string to display as the label for the source specification
	 * field
	 */
	protected String getSourceLabel() {
		return PharImportMessages.ArchiveImport_fromFile;
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 */
	protected ZipFile getSpecifiedZipSourceFile() {
		return getSpecifiedZipSourceFile(sourceNameField.getText());
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 */
	private ZipFile getSpecifiedZipSourceFile(String fileName) {
		if (fileName.length() == 0) {
			return null;
		}

		try {
			return new ZipFile(fileName);
		} catch (ZipException e) {
			displayErrorDialog(PharImportMessages.ZipImport_badFormat);
		} catch (IOException e) {
			displayErrorDialog(PharImportMessages.ZipImport_couldNotRead);
		}

		sourceNameField.setFocus();
		return null;
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 */
	protected TarFile getSpecifiedTarSourceFile() {
		return getSpecifiedTarSourceFile(sourceNameField.getText());
	}

	protected PharFile getSpecifiedPharSourceFile() {
		return getSpecifiedPharSourceFile(sourceNameField.getText());
	}

	/**
	 * Answer a handle to the zip file currently specified as being the source.
	 * Return null if this file does not exist or is not of valid format.
	 */
	private TarFile getSpecifiedTarSourceFile(String fileName) {
		if (fileName.length() == 0) {
			return null;
		}

		try {
			return new TarFile(fileName);
		} catch (TarException e) {
			displayErrorDialog(PharImportMessages.TarImport_badFormat);
		} catch (IOException e) {
			displayErrorDialog(PharImportMessages.ZipImport_couldNotRead);
		}

		sourceNameField.setFocus();
		return null;
	}

	/**
	 * Open a FileDialog so that the user can specify the source file to import
	 * from
	 */
	protected void handleSourceBrowseButtonPressed() {
		String selectedFile = queryZipFileToImport();

		if (selectedFile != null) {
			// Be sure it is valid before we go setting any names
			if (!selectedFile.equals(sourceNameField.getText())
					&& validateSourceFile(selectedFile)) {
				setSourceName(selectedFile);
				selectionGroup.setFocus();
			}
		}
	}

	/**
	 * Import the resources with extensions as specified by the user
	 */
	protected boolean importResources(List fileSystemObjects) {
		// TODO FOR PHAR
		ILeveledImportStructureProvider importStructureProvider = null;
		if (ArchiveFileManipulations.isPharFile(sourceNameField.getText())) {
			if (ensurePharSourceIsValid()) {
				PharFile pharFile = getSpecifiedPharSourceFile();
				importStructureProvider = new PharLeveledStructureProvider(
						pharFile);
			}
		} else if (ArchiveFileManipulations
				.isTarFile(sourceNameField.getText())) {
			if (ensureTarSourceIsValid()) {
				TarFile tarFile = getSpecifiedTarSourceFile();
				importStructureProvider = new TarLeveledStructureProvider(
						tarFile);
			}
		} else if (ensureZipSourceIsValid()) {
			ZipFile zipFile = getSpecifiedZipSourceFile();
			importStructureProvider = new ZipLeveledStructureProvider(zipFile);
		}

		if (importStructureProvider == null) {
			return false;
		}

		ImportOperation operation = new ImportOperation(getContainerFullPath(),
				importStructureProvider.getRoot(), importStructureProvider,
				this, fileSystemObjects);

		operation.setContext(getShell());
		if (!executeImportOperation(operation))
			return false;

		ArchiveFileManipulations.closeStructureProvider(
				importStructureProvider, getShell());
		return true;
	}

	/**
	 * Initializes the specified operation appropriately.
	 */
	protected void initializeOperation(ImportOperation op) {
		op.setOverwriteResources(overwriteExistingResourcesCheckbox
				.getSelection());
	}

	/**
	 * Opens a file selection dialog and returns a string representing the
	 * selected file, or <code>null</code> if the dialog was canceled.
	 */
	protected String queryZipFileToImport() {
		FileDialog dialog = new FileDialog(sourceNameField.getShell(), SWT.OPEN
				| SWT.SHEET);
		dialog.setFilterExtensions(this.fileImportMask);
		dialog.setText(PharImportMessages.ArchiveImportSource_title);

		String currentSourceString = sourceNameField.getText();
		int lastSeparatorIndex = currentSourceString
				.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1) {
			dialog.setFilterPath(currentSourceString.substring(0,
					lastSeparatorIndex));
		}

		return dialog.open();
	}

	/**
	 * Repopulate the view based on the currently entered directory.
	 */
	protected void resetSelection() {

		super.resetSelection();
		setAllSelections(true);
	}

	/**
	 * Use the dialog store to restore widget values to the values that they
	 * held last time this wizard was used to completion
	 */
	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] sourceNames = settings.getArray(STORE_SOURCE_NAMES_ID);
			if (sourceNames == null) {
				return; // ie.- no settings stored
			}

			// set filenames history
			for (int i = 0; i < sourceNames.length; i++) {
				sourceNameField.add(sourceNames[i]);
			}

			// radio buttons and checkboxes
			overwriteExistingResourcesCheckbox.setSelection(settings
					.getBoolean(STORE_OVERWRITE_EXISTING_RESOURCES_ID));
		}
	}

	/**
	 * Since Finish was pressed, write widget values to the dialog store so that
	 * they will persist into the next invocation of this wizard page.
	 * 
	 * Note that this method is identical to the one that appears in the
	 * superclass. This is necessary because proper overriding of instance
	 * variables is not occurring.
	 */
	protected void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			// update source names history
			String[] sourceNames = settings.getArray(STORE_SOURCE_NAMES_ID);
			if (sourceNames == null) {
				sourceNames = new String[0];
			}

			sourceNames = addToHistory(sourceNames, sourceNameField.getText());
			settings.put(STORE_SOURCE_NAMES_ID, sourceNames);

			// update specific types to import history
			String[] selectedTypesNames = settings
					.getArray(STORE_SELECTED_TYPES_ID);
			if (selectedTypesNames == null) {
				selectedTypesNames = new String[0];
			}

			settings.put(STORE_OVERWRITE_EXISTING_RESOURCES_ID,
					overwriteExistingResourcesCheckbox.getSelection());
		}
	}

	/**
	 * Answer a boolean indicating whether self's source specification widgets
	 * currently all contain valid values.
	 */
	protected boolean validateSourceGroup() {

		// If there is nothing being provided to the input then there is a
		// problem
		if (structureProvider == null) {
			setMessage(SOURCE_EMPTY_MESSAGE);
			enableButtonGroup(false);
			return false;
		}

		List resourcesToExport = selectionGroup.getAllWhiteCheckedItems();
		if (resourcesToExport.size() == 0) {
			setErrorMessage(PharImportMessages.FileImport_noneSelected);
			return false;
		}

		enableButtonGroup(true);
		setErrorMessage(null);
		return true;
	}
}
