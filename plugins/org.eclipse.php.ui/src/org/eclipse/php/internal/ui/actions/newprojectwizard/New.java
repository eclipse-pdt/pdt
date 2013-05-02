package org.eclipse.php.internal.ui.actions.newprojectwizard;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.LegacyResourceSupport;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.handlers.WizardHandler;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

/**
 * Default handler for launching new wizards.
 */
public final class New extends WizardHandler {

	/**
	 * The wizard dialog width
	 */
	private static final int SIZING_WIZARD_WIDTH = 500;

	/**
	 * The wizard dialog height
	 */
	private static final int SIZING_WIZARD_HEIGHT = 500;

	/**
	 * The id of the category to show or <code>null</code> to show all the
	 * categories.
	 */
	private String categoryId = null;

	protected String getWizardIdParameterId() {
		return IWorkbenchCommandConstants.FILE_NEW_PARM_WIZARDID;
	}

	protected IWizardRegistry getWizardRegistry() {
		return PlatformUI.getWorkbench().getNewWizardRegistry();
	}

	/**
	 * Returns the id of the category of wizards to show or <code>null</code> to
	 * show all categories.
	 * 
	 * @return String
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the id of the category of wizards to show or <code>null</code> to
	 * show all categories.
	 * 
	 * @param id
	 */
	public void setCategoryId(String id) {
		categoryId = id;
	}

	protected IStructuredSelection getSelectionToUse(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
		if (selection instanceof IStructuredSelection) {
			selectionToPass = (IStructuredSelection) selection;
		} else {
			// @issue the following is resource-specific legacy code
			// Build the selection from the IFile of the editor
			Class resourceClass = LegacyResourceSupport.getResourceClass();
			if (resourceClass != null) {
				IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
						.getActiveWorkbenchWindow(event);
				IWorkbenchPart part = activeWorkbenchWindow.getPartService()
						.getActivePart();
				if (part instanceof IEditorPart) {
					IEditorInput input = ((IEditorPart) part).getEditorInput();
					Object resource = Util.getAdapter(input, resourceClass);
					if (resource != null) {
						selectionToPass = new StructuredSelection(resource);
					}
				}
			}
		}
		return selectionToPass;
	}

	protected void executeHandler(ExecutionEvent event) {
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindow(event);
		if (activeWorkbenchWindow == null) {
			// action has been disposed
			return;
		}

		IWizardCategory root = WorkbenchPlugin.getDefault()
				.getNewWizardRegistry().getRootCategory();
		IWizardDescriptor localphpWizard = root
				.findWizard("com.zend.php.ide.ui.project.wizard.localphp"); //$NON-NLS-1$
		if (localphpWizard == null) {// pdt
			org.eclipse.ui.internal.dialogs.NewWizard wizard = new org.eclipse.ui.internal.dialogs.NewWizard();
			wizard.setCategoryId(categoryId);

			IStructuredSelection selectionToPass = getSelectionToUse(event);
			wizard.init(activeWorkbenchWindow.getWorkbench(), selectionToPass);

			IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
					.getDialogSettings();
			IDialogSettings wizardSettings = workbenchSettings
					.getSection("NewWizardAction"); //$NON-NLS-1$
			if (wizardSettings == null) {
				wizardSettings = workbenchSettings
						.addNewSection("NewWizardAction"); //$NON-NLS-1$
			}
			wizard.setDialogSettings(wizardSettings);
			wizard.setForcePreviousAndNextButtons(true);

			Shell parent = activeWorkbenchWindow.getShell();
			WizardDialog dialog = new WizardDialog(parent, wizard);
			dialog.create();
			dialog.getShell()
					.setSize(
							Math.max(SIZING_WIZARD_WIDTH, dialog.getShell()
									.getSize().x), SIZING_WIZARD_HEIGHT);
			activeWorkbenchWindow
					.getWorkbench()
					.getHelpSystem()
					.setHelp(dialog.getShell(),
							IWorkbenchHelpContextIds.NEW_WIZARD);
			dialog.open();

		} else {// zend studio
			NewWizard wizard = new NewWizard();
			wizard.setCategoryId(categoryId);

			IStructuredSelection selectionToPass = getSelectionToUse(event);
			wizard.init(activeWorkbenchWindow.getWorkbench(), selectionToPass);

			IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
					.getDialogSettings();
			IDialogSettings wizardSettings = workbenchSettings
					.getSection("NewWizardAction"); //$NON-NLS-1$
			if (wizardSettings == null) {
				wizardSettings = workbenchSettings
						.addNewSection("NewWizardAction"); //$NON-NLS-1$
			}
			wizard.setDialogSettings(wizardSettings);
			wizard.setForcePreviousAndNextButtons(true);

			Shell parent = activeWorkbenchWindow.getShell();
			WizardDialog dialog = new WizardDialog(parent, wizard);
			dialog.create();
			dialog.getShell()
					.setSize(
							Math.max(SIZING_WIZARD_WIDTH, dialog.getShell()
									.getSize().x), SIZING_WIZARD_HEIGHT);
			activeWorkbenchWindow
					.getWorkbench()
					.getHelpSystem()
					.setHelp(dialog.getShell(),
							IWorkbenchHelpContextIds.NEW_WIZARD);
			dialog.open();
		}
	}

}
