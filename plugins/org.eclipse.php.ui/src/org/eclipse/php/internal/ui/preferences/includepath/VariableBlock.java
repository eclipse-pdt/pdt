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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * TODO adapt into DLTK's mechanism
 */
public class VariableBlock {

	private ListDialogField fVariablesList;
	private Control fControl;
	private boolean fHasChanges;

	private List fSelectedElements;
	private boolean fAskToBuild;
	private boolean fInPreferencePage;

	/**
	 * Constructor for VariableBlock
	 */
	public VariableBlock(boolean inPreferencePage, String initSelection) {

		fSelectedElements = new ArrayList(0);
		fInPreferencePage = inPreferencePage;
		fAskToBuild = true;

		String[] buttonLabels = new String[] {
				PHPUIMessages.VariableBlock_vars_add_button,
				PHPUIMessages.VariableBlock_vars_edit_button,
				PHPUIMessages.VariableBlock_vars_remove_button };

		VariablesAdapter adapter = new VariablesAdapter();

		IPVariableElementLabelProvider labelProvider = new IPVariableElementLabelProvider(
				!inPreferencePage);

		fVariablesList = new ListDialogField(adapter, buttonLabels,
				labelProvider);
		fVariablesList.setDialogFieldListener(adapter);
		fVariablesList.setLabelText(PHPUIMessages.VariableBlock_vars_label);
		fVariablesList.setRemoveButtonIndex(2);

		fVariablesList.enableButton(1, false);

		fVariablesList.setViewerSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof IPVariableElement
						&& e2 instanceof IPVariableElement) {
					return ((IPVariableElement) e1).getName().compareTo(
							((IPVariableElement) e2).getName());
				}
				return super.compare(viewer, e1, e2);
			}
		});
		refresh(initSelection);
	}

	public boolean hasChanges() {
		return fHasChanges;
	}

	public void setChanges(boolean hasChanges) {
		fHasChanges = hasChanges;
	}

	public Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		LayoutUtil.doDefaultLayout(composite,
				new DialogField[] { fVariablesList }, true, 0, 0);
		LayoutUtil.setHorizontalGrabbing(fVariablesList.getListControl(null));

		fControl = composite;
		return composite;
	}

	public void addDoubleClickListener(IDoubleClickListener listener) {
		fVariablesList.getTableViewer().addDoubleClickListener(listener);
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		fVariablesList.getTableViewer().addSelectionChangedListener(listener);
	}

	private Shell getShell() {
		if (fControl != null) {
			return fControl.getShell();
		}
		return PHPUiPlugin.getActiveWorkbenchShell();
	}

	private class VariablesAdapter implements IDialogFieldListener,
			IListAdapter {

		// -------- IListAdapter --------

		public void customButtonPressed(ListDialogField field, int index) {
			switch (index) {
			case 0: /* add */
				editEntries(null);
				break;
			case 1: /* edit */
				List selected = field.getSelectedElements();
				editEntries((IPVariableElement) selected.get(0));
				break;
			}
		}

		public void selectionChanged(ListDialogField field) {
			doSelectionChanged(field);
		}

		public void doubleClicked(ListDialogField field) {
			if (fInPreferencePage) {
				List selected = field.getSelectedElements();
				if (canEdit(selected, containsReserved(selected))) {
					editEntries((IPVariableElement) selected.get(0));
				}
			}
		}

		// ---------- IDialogFieldListener --------

		public void dialogFieldChanged(DialogField field) {
		}

	}

	private boolean containsReserved(List selected) {
		for (int i = selected.size() - 1; i >= 0; i--) {
			if (((IPVariableElement) selected.get(i)).isReserved()) {
				return true;
			}
		}
		return false;
	}

	private static void addAll(Object[] objs, Collection dest) {
		for (int i = 0; i < objs.length; i++) {
			dest.add(objs[i]);
		}
	}

	private boolean canEdit(List selected, boolean containsReserved) {
		return selected.size() == 1 && !containsReserved;
	}

	private void doSelectionChanged(DialogField field) {
		List selected = fVariablesList.getSelectedElements();
		boolean containsReserved = containsReserved(selected);

		// edit
		fVariablesList.enableButton(1, canEdit(selected, containsReserved));
		// remove button
		fVariablesList.enableButton(2, !containsReserved);

		fSelectedElements = selected;
	}

	private void editEntries(IPVariableElement entry) {
		List existingEntries = fVariablesList.getElements();

		VariableCreationDialog dialog = new VariableCreationDialog(getShell(),
				entry, existingEntries);
		if (dialog.open() != Window.OK) {
			return;
		}
		IPVariableElement newEntry = dialog.getIncludePathElement();
		if (entry == null) {
			fVariablesList.addElement(newEntry);
			entry = newEntry;
			fHasChanges = true;
		} else {
			boolean hasChanges = !(entry.getName().equals(newEntry.getName()) && entry
					.getPath().equals(newEntry.getPath()));
			if (hasChanges) {
				fHasChanges = true;
				entry.setName(newEntry.getName());
				entry.setPath(newEntry.getPath());
				fVariablesList.refresh();
			}
		}
		fVariablesList.selectElements(new StructuredSelection(entry));
	}

	public List getSelectedElements() {
		return fSelectedElements;
	}

	public void performDefaults() {
		fVariablesList.removeAllElements();
		String[] reservedName = {}; // IncludePathVariableManager.instance().getReservedVariables();
		for (int i = 0; i < reservedName.length; i++) {
			IPVariableElement elem = new IPVariableElement(reservedName[i],
					Path.EMPTY, true);
			elem.setReserved(true);
			fVariablesList.addElement(elem);
		}
		fHasChanges = true;
	}

	public boolean performOk() {
		ArrayList removedVariables = new ArrayList();
		ArrayList changedVariables = new ArrayList();
		// removedVariables.addAll(Arrays.asList(PHPProjectOptions.getIncludePathVariableNames()));

		// remove all unchanged
		List changedElements = fVariablesList.getElements();
		List unchangedElements = fVariablesList.getElements();

		for (int i = changedElements.size() - 1; i >= 0; i--) {
			IPVariableElement curr = (IPVariableElement) changedElements.get(i);
			if (curr.isReserved()) {
				changedElements.remove(curr);
			} else {
				IPath path = curr.getPath();
				IPath prevPath = null; // PHPProjectOptions.getIncludePathVariable(curr.getName());
				if (prevPath != null && prevPath.equals(path)) {
					changedElements.remove(curr);
				} else {
					changedVariables.add(curr.getName());
					unchangedElements.remove(curr);
				}
			}
			removedVariables.remove(curr.getName());

		}
		int steps = changedElements.size() + removedVariables.size();
		if (steps > 0) {

			boolean needsBuild = false;
			if (fAskToBuild
					&& doesChangeRequireFullBuild(removedVariables,
							changedVariables)) {
				String title = PHPUIMessages.VariableBlock_needsbuild_title;
				String message = PHPUIMessages.VariableBlock_needsbuild_message;

				MessageDialog buildDialog = new MessageDialog(getShell(),
						title, null, message, MessageDialog.QUESTION,
						new String[] { IDialogConstants.YES_LABEL,
								IDialogConstants.NO_LABEL,
								IDialogConstants.CANCEL_LABEL }, 2);
				int res = buildDialog.open();
				if (res != 0 && res != 1) {
					return false;
				}
				needsBuild = (res == 0);
			}

			final VariableBlockRunnable runnable = new VariableBlockRunnable(
					removedVariables, changedElements, unchangedElements,
					needsBuild);
			Job buildJob = new Job(PHPUIMessages.VariableBlock_job_description) {
				protected IStatus run(IProgressMonitor monitor) {
					try {
						runnable.setVariables(monitor);
					} catch (CoreException e) {
						return e.getStatus();
					} catch (OperationCanceledException e) {
						return Status.CANCEL_STATUS;
					} finally {
						monitor.done();
					}
					return Status.OK_STATUS;
				}
			};

			buildJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory()
					.buildRule());
			buildJob.setUser(true);
			buildJob.schedule();
			return true;
		}
		// ProgressMonitorDialog dialog= new ProgressMonitorDialog(getShell());
		// try {
		// dialog.run(true, true, runnable);
		// } catch (InvocationTargetException e) {
		//				ExceptionHandler.handle(e, getShell(), PHPUIMessages.getString("VariableBlock.operation_errror.title"), PHPUIMessages.getString("VariableBlock.operation_errror.message")); 
		// return false;
		// } catch (InterruptedException e) {
		// return false;
		// }
		// }
		return true;
	}

	private boolean doesChangeRequireFullBuild(List removed, List changed) {

		/*
		 * IProject[] projects =
		 * PHPWorkspaceModelManager.getInstance().listProjects(); for (int i =
		 * 0; i < projects.length; i++) { PHPProjectOptions options =
		 * PHPProjectOptions.forProject(projects[i]); IIncludePathEntry[]
		 * entries = options.readRawIncludePath(); for (int k = 0; k <
		 * entries.length; k++) { IIncludePathEntry curr = entries[k]; if
		 * (curr.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) { String var
		 * = curr.getPath().segment(0); if (removed.contains(var) ||
		 * changed.contains(var)) { return true; } } } }
		 */return false;
	}

	private class VariableBlockRunnable implements IRunnableWithProgress {
		private List fToRemove;
		private List fToChange;
		private List fUnchanged;
		private boolean fDoBuild;

		public VariableBlockRunnable(List toRemove, List toChange,
				List unchanged, boolean doBuild) {
			fToRemove = toRemove;
			fToChange = toChange;
			fUnchanged = unchanged;
			fDoBuild = doBuild;

		}

		/*
		 * @see IRunnableWithProgress#run(IProgressMonitor)
		 */
		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			monitor.beginTask(PHPUIMessages.VariableBlock_operation_desc,
					fDoBuild ? 2 : 1);
			try {
				setVariables(monitor);

			} catch (CoreException e) {
				throw new InvocationTargetException(e);
			} catch (OperationCanceledException e) {
				throw new InterruptedException();
			} finally {
				monitor.done();
			}
		}

		public void setVariables(IProgressMonitor monitor) throws CoreException {
			int nVariables = fToChange.size() + fToRemove.size()
					+ fUnchanged.size();

			String[] names = new String[nVariables];
			IPath[] paths = new IPath[nVariables];
			int k = 0;

			for (int i = 0; i < fUnchanged.size(); i++) {
				IPVariableElement curr = (IPVariableElement) fUnchanged.get(i);
				names[k] = curr.getName();
				paths[k] = curr.getPath();
				k++;
			}
			for (int i = 0; i < fToChange.size(); i++) {
				IPVariableElement curr = (IPVariableElement) fToChange.get(i);
				names[k] = curr.getName();
				paths[k] = curr.getPath();
				k++;
			}
			for (int i = 0; i < fToRemove.size(); i++) {
				names[k] = (String) fToRemove.get(i);
				paths[k] = null;
				k++;
			}
			// PHPProjectOptions.setIncludePathVariables(names, paths, new
			// SubProgressMonitor(monitor, 1));

			if (fDoBuild) {
				ResourcesPlugin.getWorkspace().build(
						IncrementalProjectBuilder.FULL_BUILD,
						new SubProgressMonitor(monitor, 1));
			}
		}
	}

	/**
	 * If set to true, a dialog will ask the user to build on variable changed
	 * 
	 * @param askToBuild
	 *            The askToBuild to set
	 */
	public void setAskToBuild(boolean askToBuild) {
		fAskToBuild = askToBuild;
	}

	/**
	 * 
	 */
	public void refresh(String initSelection) {
		IPVariableElement initSelectedElement = null;

		/*
		 * String[] reservedName =
		 * IncludePathVariableManager.instance().getReservedVariables();
		 * ArrayList reserved = new ArrayList(reservedName.length);
		 * addAll(reservedName, reserved);
		 * 
		 * String[] entries = PHPProjectOptions.getIncludePathVariableNames();
		 * ArrayList elements = new ArrayList(entries.length); for (int i = 0; i
		 * < entries.length; i++) { String name = entries[i]; IPVariableElement
		 * elem; IPath entryPath =
		 * PHPProjectOptions.getIncludePathVariable(name); if (entryPath !=
		 * null) { elem = new IPVariableElement(name, entryPath,
		 * reserved.contains(name)); elements.add(elem); if
		 * (name.equals(initSelection)) { initSelectedElement = elem; } } else {
		 * PHPCorePlugin
		 * .logErrorMessage("VariableBlock: IncludePath variable with null value: "
		 * + name); } } fVariablesList.setElements(elements);
		 */
		if (initSelectedElement != null) {
			ISelection sel = new StructuredSelection(initSelectedElement);
			fVariablesList.selectElements(sel);
		} else {
			fVariablesList.selectFirstElement();
		}

		fHasChanges = false;
	}

}
