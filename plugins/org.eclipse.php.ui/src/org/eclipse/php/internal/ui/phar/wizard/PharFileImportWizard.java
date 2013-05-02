package org.eclipse.php.internal.ui.phar.wizard;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PharFileImportWizard extends Wizard implements IImportWizard {
	private IWorkbench workbench;

	private IStructuredSelection selection;

	private WizardPharFileResourceImportPage1 mainPage;

	/**
	 * Creates a wizard for importing resources into the workspace from a zip
	 * file.
	 */
	public PharFileImportWizard() {
		AbstractUIPlugin plugin = WorkbenchPlugin.getDefault();
		IDialogSettings workbenchSettings = plugin.getDialogSettings();
		IDialogSettings section = workbenchSettings
				.getSection("ZipFileImportWizard"); //$NON-NLS-1$
		if (section == null) {
			section = workbenchSettings.addNewSection("ZipFileImportWizard"); //$NON-NLS-1$
		}
		setDialogSettings(section);
	}

	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	public void addPages() {
		super.addPages();
		mainPage = new WizardPharFileResourceImportPage1(workbench, selection,
				getFileImportMask());
		addPage(mainPage);
	}

	/**
	 * Get the file import mask used by the receiver. By default use null so
	 * that there is no mask.
	 * 
	 * @return String[] or <code>null</code>
	 * @since 3.4
	 */
	protected String[] getFileImportMask() {
		return null;
	}

	/*
	 * (non-Javadoc) Method declared on IWorkbenchWizard.
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.workbench = workbench;
		this.selection = currentSelection;
		List selectedResources = IDE.computeSelectedResources(currentSelection);
		if (!selectedResources.isEmpty()) {
			this.selection = new StructuredSelection(selectedResources);
		}

		setWindowTitle(DataTransferMessages.DataTransfer_importTitle);
		setDefaultPageImageDescriptor(IDEWorkbenchPlugin
				.getIDEImageDescriptor("wizban/importzip_wiz.png")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	public boolean performCancel() {
		return mainPage.cancel();
	}

	/*
	 * (non-Javadoc) Method declared on IWizard.
	 */
	public boolean performFinish() {
		return mainPage.finish();
	}
}
