package org.eclipse.php.internal.ui.phar.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.internal.core.phar.PharPackage;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class PharPackageWizard extends Wizard implements IExportWizard {

	private static String DIALOG_SETTINGS_KEY = "PharPackageWizard"; //$NON-NLS-1$

	private boolean fHasNewDialogSettings;

	PharPackage pharPackage;
	PharPackageWizardPage pharPackageWizardPage;
	private IStructuredSelection fSelection;

	public PharPackageWizard() {
		IDialogSettings workbenchSettings = PHPUiPlugin.getDefault()
				.getDialogSettings();
		IDialogSettings section = workbenchSettings
				.getSection(DIALOG_SETTINGS_KEY);
		if (section == null)
			fHasNewDialogSettings = true;
		else {
			fHasNewDialogSettings = false;
			setDialogSettings(section);
		}
	}

	@Override
	public boolean performFinish() {
		pharPackage.setElements(pharPackageWizardPage
				.getSelectedElementsWithoutContainedChildren());

		if (!executeExportOperation(new PharFileExportOperation(pharPackage,
				getShell())))
			return false;

		// Save the dialog settings
		if (fHasNewDialogSettings) {
			IDialogSettings workbenchSettings = PHPUiPlugin.getDefault()
					.getDialogSettings();
			IDialogSettings section = workbenchSettings
					.addNewSection(DIALOG_SETTINGS_KEY);
			setDialogSettings(section);
		}
		IWizardPage[] pages = getPages();
		for (int i = 0; i < getPageCount(); i++) {
			IWizardPage page = pages[i];
			if (page instanceof IPharWizardPage)
				((IPharWizardPage) page).finish();
		}
		return true;
	}

	/**
	 * Exports the JAR package.
	 * 
	 * @param op
	 *            the op
	 * @return a boolean indicating success or failure
	 */
	protected boolean executeExportOperation(IPharExportRunnable op) {
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException ex) {
			// if (ex.getTargetException() != null) {
			// ExceptionHandler.handle(ex, getShell(),
			// JarPackagerMessages.JarPackageWizard_jarExportError_title,
			// JarPackagerMessages.JarPackageWizard_jarExportError_message);
			// return false;
			// }
		}
		IStatus status = op.getStatus();
		if (!status.isOK()) {
			ErrorDialog.openError(getShell(), "", null, status); //$NON-NLS-1$
			return !(status.matches(IStatus.ERROR));
		}
		return true;
	}

	public void addPages() {
		super.addPages();
		pharPackageWizardPage = new PharPackageWizardPage(pharPackage,
				fSelection);
		addPage(pharPackageWizardPage);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// ignore the selection argument since the main export wizard changed it
		fSelection = getValidSelection();
		pharPackage = new PharPackage();
		setWindowTitle(""); //$NON-NLS-1$
		// setDefaultPageImageDescriptor(JavaPluginImages.DESC_WIZBAN_JAR_PACKAGER);
		setNeedsProgressMonitor(true);
	}

	/**
	 * Gets the current workspace page selection and converts it to a valid
	 * selection for this wizard: - resources and projects are OK - CUs are OK -
	 * Java projects are OK - Source package fragments and source packages
	 * fragement roots are ok - Java elements below a CU are converted to their
	 * CU - all other input elements are ignored
	 * 
	 * @return a valid structured selection based on the current selection
	 */
	protected IStructuredSelection getValidSelection() {
		ISelection currentSelection = PHPUiPlugin.getActiveWorkbenchWindow()
				.getSelectionService().getSelection();
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) currentSelection;
			List selectedElements = new ArrayList(structuredSelection.size());
			Iterator iter = structuredSelection.iterator();
			while (iter.hasNext()) {
				Object selectedElement = iter.next();
				if (selectedElement instanceof IProject)
					addProject(selectedElements, (IProject) selectedElement);
				else if (selectedElement instanceof IResource)
					addResource(selectedElements, (IResource) selectedElement);
				else if (selectedElement instanceof IScriptProject) {
					addProject(selectedElements,
							((IScriptProject) selectedElement).getProject());
				} else if (selectedElement instanceof IModelElement)
					addJavaElement(selectedElements,
							(IModelElement) selectedElement);

			}
			return new StructuredSelection(selectedElements);
		} else
			return StructuredSelection.EMPTY;
	}

	private void addJavaElement(List selectedElements,
			IModelElement selectedElement) {
		// if (selectedElement.getResource().getParent() instanceof IProject) {
		// selectedElements.add(selectedElement.getResource());
		// return;
		// }
		if (selectedElement != null
				&& selectedElement.exists()
				&& (selectedElement.getElementType() == IModelElement.SOURCE_MODULE || selectedElement
						.getElementType() == IModelElement.SCRIPT_FOLDER))
			selectedElements.add(selectedElement);
	}

	private void addResource(List selectedElements, IResource resource) {

		IModelElement je = DLTKCore.create(resource);
		if (je != null && je.exists()
				&& je.getElementType() == IModelElement.SOURCE_MODULE)
			selectedElements.add(je);
		else
			selectedElements.add(resource);
	}

	private void addProject(List selectedElements, IProject project) {
		try {
			if (project.isAccessible() && project.hasNature(PHPNature.ID))
				selectedElements.add(DLTKCore.create(project));
		} catch (CoreException ex) {
			// ignore selected element
		}
	}

}
