/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - Port from JDT to PDT
 *******************************************************************************/
package org.eclipse.php.internal.ui.workingset;

import java.util.*;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetModel;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.php.internal.ui.actions.SelectionDispatchAction;
import org.eclipse.php.internal.ui.actions.WorkingSetConfigurationDialog;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.IWorkingSetNewWizard;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.ibm.icu.text.Collator;

public final class ConfigureWorkingSetAssignementAction extends SelectionDispatchAction {

	/**
	 * A set of GrayedCheckedModelElements
	 */
	private static final class GrayedCheckedModel {

		private ArrayList<GrayedCheckedModelElement> fElements;

		public GrayedCheckedModel(GrayedCheckedModelElement[] elements) {
			fElements = new ArrayList<GrayedCheckedModelElement>(Arrays.asList(elements));
		}

		public void addElement(GrayedCheckedModelElement element) {
			fElements.add(element);
		}

		public GrayedCheckedModelElement[] getElements() {
			return fElements.toArray(new GrayedCheckedModelElement[fElements.size()]);
		}

		public GrayedCheckedModelElement[] getChecked() {
			ArrayList<GrayedCheckedModelElement> result = new ArrayList<GrayedCheckedModelElement>();
			for (int i = 0; i < fElements.size(); i++) {
				if (fElements.get(i).isChecked())
					result.add(fElements.get(i));
			}
			return result.toArray(new GrayedCheckedModelElement[result.size()]);
		}

		public GrayedCheckedModelElement[] getGrayed() {
			ArrayList<GrayedCheckedModelElement> result = new ArrayList<GrayedCheckedModelElement>();
			for (int i = 0; i < fElements.size(); i++) {
				if (fElements.get(i).isGrayed())
					result.add(fElements.get(i));
			}
			return result.toArray(new GrayedCheckedModelElement[result.size()]);
		}

		public void selectAll() {
			for (int i = 0; i < fElements.size(); i++) {
				fElements.get(i).select();
			}
		}

		public void deselectAll() {
			for (int i = 0; i < fElements.size(); i++) {
				fElements.get(i).deselect();
			}
		}

	}

	/**
	 * Connects a IWorkingSet with its grayed-checked state
	 */
	private final static class GrayedCheckedModelElement {

		private final IWorkingSet fWorkingSet;
		private final int fElementCount;
		private int fCheckCount;

		public GrayedCheckedModelElement(IWorkingSet workingSet, int checkCount, int elementCount) {
			fWorkingSet = workingSet;
			fCheckCount = checkCount;
			fElementCount = elementCount;
		}

		public IWorkingSet getWorkingSet() {
			return fWorkingSet;
		}

		public int getCheckCount() {
			return fCheckCount;
		}

		public boolean isGrayed() {
			return isChecked() && fCheckCount < fElementCount;
		}

		public boolean isChecked() {
			return fCheckCount > 0;
		}

		public void deselect() {
			fCheckCount = 0;
		}

		public void select() {
			fCheckCount = fElementCount;
		}

		public int getElementCount() {
			return fElementCount;
		}

	}

	/**
	 * Content provider for a GrayedCheckedModel input
	 */
	private static final class GrayedCheckedModelContentProvider implements IStructuredContentProvider {
		private GrayedCheckedModelElement[] fElements;

		public Object[] getElements(Object element) {
			return fElements;
		}

		public void dispose() {
			// do nothing
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (newInput instanceof GrayedCheckedModel) {
				fElements = ((GrayedCheckedModel) newInput).getElements();
			} else {
				fElements = new GrayedCheckedModelElement[0];
			}
		}
	}

	/**
	 * Label provider for GrayedCheckedModelElements
	 */
	private class GrayedCheckedModelLabelProvider extends LabelProvider {

		private Map<ImageDescriptor, Image> fIcons;

		public GrayedCheckedModelLabelProvider() {
			fIcons = new Hashtable<ImageDescriptor, Image>();
		}

		@Override
		public void dispose() {
			Iterator<Image> iterator = fIcons.values().iterator();
			while (iterator.hasNext()) {
				Image icon = iterator.next();
				icon.dispose();
			}
			super.dispose();
		}

		@Override
		public Image getImage(Object object) {
			IWorkingSet workingSet = ((GrayedCheckedModelElement) object).getWorkingSet();
			ImageDescriptor imageDescriptor = workingSet.getImageDescriptor();
			if (imageDescriptor == null)
				return null;

			Image icon = fIcons.get(imageDescriptor);
			if (icon == null) {
				icon = imageDescriptor.createImage();
				fIcons.put(imageDescriptor, icon);
			}

			return icon;
		}

		@Override
		public String getText(Object object) {
			GrayedCheckedModelElement modelElement = (GrayedCheckedModelElement) object;
			IWorkingSet workingSet = modelElement.getWorkingSet();
			if (!modelElement.isGrayed()) {
				return BasicElementLabels.getWorkingSetLabel(workingSet);
			} else {
				return Messages.format(WorkingSetMessages.ConfigureWorkingSetAssignementAction_XofY_label,
						new Object[] { BasicElementLabels.getWorkingSetLabel(workingSet), modelElement.getCheckCount(),
								modelElement.getElementCount() });
			}

		}

	}

	private final class WorkingSetModelAwareSelectionDialog extends SelectionDialog {

		/**
		 * Section ID for the WorkingSetModelAwareSelectionDialog class.
		 * 
		 * @since 3.5
		 */
		private static final String DIALOG_SETTINGS_SECTION = "WorkingSetModelAwareSelectionDialog"; //$NON-NLS-1$

		/**
		 * Key associated with the 'Show Only PE Visible Working Sets' check
		 * box.
		 * 
		 * @since 3.5
		 */
		private static final String SETTINGS_SHOW_VISIBLE_ONLY = "ShowVisibleOnly"; //$NON-NLS-1$

		private final class GrayedCheckModelElementSorter extends ViewerSorter {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				GrayedCheckedModelElement w1 = (GrayedCheckedModelElement) e1;
				GrayedCheckedModelElement w2 = (GrayedCheckedModelElement) e2;
				return Collator.getInstance().compare(w1.getWorkingSet().getLabel(), w2.getWorkingSet().getLabel());
			}
		}

		private class Filter extends ViewerFilter {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				GrayedCheckedModelElement model = (GrayedCheckedModelElement) element;
				IWorkingSet set = model.getWorkingSet();
				return accept(set);
			}

			private boolean accept(IWorkingSet set) {
				if (!isValidWorkingSet(set))
					return false;

				ScriptExplorerPart activePart = getActivePackageExplorer();
				if (fWorkingSetModel == null
						|| activePart != null && activePart.getRootMode() == ScriptExplorerPart.PROJECTS_AS_ROOTS)
					return true;

				if (fShowVisibleOnly && !fWorkingSetModel.isActiveWorkingSet(set))
					return false;

				return true;
			}
		}

		private CheckboxTableViewer fTableViewer;
		private boolean fShowVisibleOnly;
		private GrayedCheckedModel fModel;
		private final IAdaptable[] fElements;
		private final ArrayList<IWorkingSet> fCreatedWorkingSets;

		/**
		 * @since 3.5
		 */
		private IDialogSettings fSettings;

		private WorkingSetModelAwareSelectionDialog(Shell shell, GrayedCheckedModel model, IAdaptable[] elements) {
			super(shell);

			setTitle(WorkingSetMessages.ConfigureWorkingSetAssignementAction_WorkingSetAssignments_title);
			fModel = model;
			fElements = elements;
			fCreatedWorkingSets = new ArrayList<IWorkingSet>();
			fSettings = DLTKUIPlugin.getDefault().getDialogSettingsSection(DIALOG_SETTINGS_SECTION);
			if (fSettings.get(SETTINGS_SHOW_VISIBLE_ONLY) == null) {
				fSettings.put(SETTINGS_SHOW_VISIBLE_ONLY, true);
			}
			fShowVisibleOnly = fSettings.getBoolean(SETTINGS_SHOW_VISIBLE_ONLY);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @since 3.5
		 */
		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			// PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
			// IPHPHelpContextIds.WORKING_SET_ASSIGNMENTS_DIALOG);
		}

		public IWorkingSet[] getGrayed() {
			GrayedCheckedModelElement[] grayed = fModel.getGrayed();
			IWorkingSet[] result = new IWorkingSet[grayed.length];
			for (int i = 0; i < grayed.length; i++) {
				result[i] = grayed[i].getWorkingSet();
			}
			return result;
		}

		public IWorkingSet[] getSelection() {
			GrayedCheckedModelElement[] checked = fModel.getChecked();
			IWorkingSet[] result = new IWorkingSet[checked.length];
			for (int i = 0; i < checked.length; i++) {
				result[i] = checked[i].getWorkingSet();
			}
			return result;
		}

		@Override
		protected final Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);

			createMessageArea(composite);
			Composite inner = new Composite(composite, SWT.NONE);
			inner.setLayoutData(new GridData(GridData.FILL_BOTH));
			GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			inner.setLayout(layout);

			Composite tableComposite = new Composite(inner, SWT.NONE);
			tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			tableComposite.setLayout(layout);

			fTableViewer = createTableViewer(tableComposite);
			createShowVisibleOnly(tableComposite);

			createRightButtonBar(inner);

			Dialog.applyDialogFont(composite);
			return composite;
		}

		protected void createRightButtonBar(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			composite.setLayout(layout);

			Button selectAll = new Button(composite, SWT.PUSH);
			selectAll.setText(WorkingSetMessages.ConfigureWorkingSetAssignementAction_SelectAll_button);
			setButtonLayoutData(selectAll);
			selectAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					fTableViewer.setAllChecked(true);

					fModel.selectAll();
					fTableViewer.setGrayedElements(new Object[0]);
					fTableViewer.refresh();
				}
			});

			Button deselectAll = new Button(composite, SWT.PUSH);
			deselectAll.setText(WorkingSetMessages.ConfigureWorkingSetAssignementAction_DeselectAll_button);
			setButtonLayoutData(deselectAll);
			deselectAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					fTableViewer.setAllChecked(false);

					fModel.deselectAll();
					fTableViewer.setGrayedElements(new Object[0]);
					fTableViewer.refresh();
				}
			});

			Button newWorkingSet = new Button(composite, SWT.PUSH);
			newWorkingSet.setText(WorkingSetMessages.ConfigureWorkingSetAssignementAction_New_button);
			setButtonLayoutData(newWorkingSet);
			newWorkingSet.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					IWorkingSetManager manager = PlatformUI.getWorkbench().getWorkingSetManager();

					// can only allow to create java working sets at the moment,
					// see bug 186762
					IWorkingSetNewWizard wizard = manager.createWorkingSetNewWizard(VALID_WORKING_SET_IDS);
					if (wizard == null)
						return;

					WizardDialog dialog = new WizardDialog(getShell(), wizard);
					dialog.create();
					if (dialog.open() == Window.OK) {
						IWorkingSet workingSet = wizard.getSelection();
						if (isValidWorkingSet(workingSet)) {
							manager.addWorkingSet(workingSet);
							addNewWorkingSet(workingSet);
							fCreatedWorkingSets.add(workingSet);
						}
					}
				}
			});
		}

		protected CheckboxTableViewer createTableViewer(Composite parent) {

			final CheckboxTableViewer result = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.MULTI);
			result.addCheckStateListener(new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent event) {
					GrayedCheckedModelElement element = (GrayedCheckedModelElement) event.getElement();
					result.setGrayed(element, false);
					if (event.getChecked()) {
						element.select();
					} else {
						element.deselect();
					}
					result.update(element, null);
				}
			});
			GridData data = new GridData(GridData.FILL_BOTH);
			data.heightHint = convertHeightInCharsToPixels(20);
			data.widthHint = convertWidthInCharsToPixels(50);
			result.getTable().setLayoutData(data);

			result.addFilter(new Filter());
			result.setLabelProvider(new GrayedCheckedModelLabelProvider());
			result.setComparator(new GrayedCheckModelElementSorter());
			result.setContentProvider(new GrayedCheckedModelContentProvider());

			result.setInput(fModel);
			result.setCheckedElements(fModel.getChecked());
			result.setGrayedElements(fModel.getGrayed());

			return result;
		}

		protected void addNewWorkingSet(IWorkingSet workingSet) {
			int checkCount = 0;
			for (int i = 0; i < fElements.length; i++) {
				IAdaptable adapted = adapt(workingSet, fElements[i]);
				if (adapted != null && contains(workingSet, adapted)) {
					checkCount++;
				}
			}

			GrayedCheckedModelElement element = new GrayedCheckedModelElement(workingSet, checkCount, fElements.length);
			fModel.addElement(element);

			fTableViewer.setInput(fModel);
			fTableViewer.refresh();

			fTableViewer.setCheckedElements(fModel.getChecked());
			fTableViewer.setGrayedElements(fModel.getGrayed());

			fTableViewer.setSelection(new StructuredSelection(element));
		}

		private void createShowVisibleOnly(Composite parent) {
			ScriptExplorerPart activePart = getActivePackageExplorer();
			if (fWorkingSetModel == null
					|| activePart != null && activePart.getRootMode() == ScriptExplorerPart.PROJECTS_AS_ROOTS)
				return;

			final Button showVisibleOnly = new Button(parent, SWT.CHECK);
			showVisibleOnly.setText(WorkingSetMessages.ConfigureWorkingSetAssignementAction_OnlyShowVisible_check);
			showVisibleOnly.setSelection(fShowVisibleOnly);
			showVisibleOnly.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, true, false));
			showVisibleOnly.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					fShowVisibleOnly = showVisibleOnly.getSelection();

					fTableViewer.refresh();

					fTableViewer.setCheckedElements(fModel.getChecked());
					fTableViewer.setGrayedElements(fModel.getGrayed());
				}
			});

			Link ppwsLink = new Link(parent, SWT.NONE);
			ppwsLink.setText(WorkingSetMessages.ConfigureWorkingSetAssignementAction_OnlyShowVisible_link);
			ppwsLink.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, true, false));
			ppwsLink.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					List<IWorkingSet> workingSets = new ArrayList<IWorkingSet>(
							Arrays.asList(fWorkingSetModel.getAllWorkingSets()));
					List<IWorkingSet> activeWorkingSets = new ArrayList<IWorkingSet>(
							Arrays.asList(fWorkingSetModel.getActiveWorkingSets()));
					WorkingSetConfigurationDialog dialog = new WorkingSetConfigurationDialog(getShell(),
							workingSets.toArray(new IWorkingSet[workingSets.size()]),
							activeWorkingSets.toArray(new IWorkingSet[activeWorkingSets.size()]));
					dialog.setSelection(fWorkingSetModel.getActiveWorkingSets());
					if (dialog.open() == IDialogConstants.OK_ID) {
						fWorkingSetModel.setActiveWorkingSets(dialog.getSelection());
					}

					recalculateCheckedState(dialog.getNewlyAddedWorkingSets());
				}
			});
		}

		/*
		 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
		 * 
		 * @since 3.5
		 */
		@Override
		protected void buttonPressed(int buttonId) {
			if (IDialogConstants.OK_ID == buttonId)
				fSettings.put(SETTINGS_SHOW_VISIBLE_ONLY, fShowVisibleOnly);
			super.buttonPressed(buttonId);
		}

		private void recalculateCheckedState(List<IWorkingSet> addedWorkingSets) {
			Set<IWorkingSet> checkedWorkingSets = new HashSet<IWorkingSet>();
			GrayedCheckedModelElement[] elements = fModel.getChecked();
			for (int i = 0; i < elements.length; i++)
				checkedWorkingSets.add(elements[i].getWorkingSet());

			if (addedWorkingSets != null)
				checkedWorkingSets.addAll(addedWorkingSets);

			fModel = createGrayedCheckedModel(fElements, getAllWorkingSets(), checkedWorkingSets);

			fTableViewer.setInput(fModel);
			fTableViewer.refresh();
			fTableViewer.setCheckedElements(fModel.getChecked());
			fTableViewer.setGrayedElements(fModel.getGrayed());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void cancelPressed() {
			IWorkingSetManager manager = PlatformUI.getWorkbench().getWorkingSetManager();
			for (int i = 0; i < fCreatedWorkingSets.size(); i++) {
				manager.removeWorkingSet(fCreatedWorkingSets.get(i));
			}

			super.cancelPressed();
		}
	}

	private static final String[] VALID_WORKING_SET_IDS = new String[] { IWorkingSetIds.PHP_ID,
			IWorkingSetIds.RESOURCE_ID, IWorkingSetIds.TASK_ID };

	private WorkingSetModel fWorkingSetModel;
	private final IWorkbenchSite fSite;

	public ConfigureWorkingSetAssignementAction(IWorkbenchSite site) {
		super(site);
		fSite = site;
		setText(WorkingSetMessages.ConfigureWorkingSetAssignementAction_WorkingSets_actionLabel);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IPHPHelpContextIds.ASSIGN_WORKING_SETS_ACTION);
		setEnabled(false);
	}

	public void setWorkingSetModel(WorkingSetModel workingSetModel) {
		fWorkingSetModel = workingSetModel;
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(canEnable(selection));
	}

	private boolean canEnable(IStructuredSelection selection) {
		if (selection.isEmpty())
			return false;

		List<?> list = selection.toList();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (!(object instanceof IResource) && !(object instanceof IModelElement))
				return false;
		}

		return true;
	}

	private IAdaptable[] getSelectedElements(IStructuredSelection selection) {
		ArrayList<Object> result = new ArrayList<Object>();

		List<?> list = selection.toList();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (object instanceof IResource || object instanceof IModelElement) {
				result.add(object);
			}
		}

		return result.toArray(new IAdaptable[result.size()]);
	}

	@Override
	public void run(IStructuredSelection selection) {
		IAdaptable[] elements = getSelectedElements(selection);
		GrayedCheckedModel model = createGrayedCheckedModel(elements, getAllWorkingSets(), null);
		WorkingSetModelAwareSelectionDialog dialog = new WorkingSetModelAwareSelectionDialog(fSite.getShell(), model,
				elements);

		if (elements.length == 1) {
			IAdaptable element = elements[0];
			String elementName;
			if (element instanceof IResource) {
				elementName = BasicElementLabels.getResourceName((IResource) element);
			} else {
				elementName = ScriptElementLabels.getDefault().getElementLabel((IModelElement) element,
						ScriptElementLabels.ALL_DEFAULT);
			}
			dialog.setMessage(Messages.format(
					WorkingSetMessages.ConfigureWorkingSetAssignementAction_DialogMessage_specific, elementName));
		} else {
			dialog.setMessage(Messages.format(
					WorkingSetMessages.ConfigureWorkingSetAssignementAction_DialogMessage_multi, elements.length));
		}
		if (dialog.open() == Window.OK) {
			updateWorkingSets(dialog.getSelection(), dialog.getGrayed(), elements);
			selectAndReveal(elements);
		}
	}

	private static GrayedCheckedModel createGrayedCheckedModel(IAdaptable[] elements, IWorkingSet[] workingSets,
			Set<IWorkingSet> checkedWorkingSets) {
		GrayedCheckedModelElement[] result = new GrayedCheckedModelElement[workingSets.length];

		for (int i = 0; i < workingSets.length; i++) {
			IWorkingSet workingSet = workingSets[i];

			int checkCount = 0;
			for (int j = 0; j < elements.length; j++) {
				if (checkedWorkingSets == null) {
					IAdaptable adapted = adapt(workingSet, elements[j]);
					if (adapted != null && contains(workingSet, adapted))
						checkCount++;
				} else {
					if (checkedWorkingSets.contains(workingSet))
						checkCount++;
				}
			}

			result[i] = new GrayedCheckedModelElement(workingSet, checkCount, elements.length);
		}

		return new GrayedCheckedModel(result);
	}

	private void updateWorkingSets(IWorkingSet[] newWorkingSets, IWorkingSet[] grayedWorkingSets,
			IAdaptable[] elements) {
		HashSet<IWorkingSet> selectedSets = new HashSet<IWorkingSet>(Arrays.asList(newWorkingSets));
		HashSet<IWorkingSet> grayedSets = new HashSet<IWorkingSet>(Arrays.asList(grayedWorkingSets));
		IWorkingSet[] workingSets = getAllWorkingSets();

		for (int i = 0; i < workingSets.length; i++) {
			IWorkingSet workingSet = workingSets[i];
			if (isValidWorkingSet(workingSet) && !selectedSets.contains(workingSet)
					&& !grayedSets.contains(workingSet)) {
				for (int j = 0; j < elements.length; j++) {
					IAdaptable adapted = adapt(workingSet, elements[j]);
					if (adapted != null && contains(workingSet, adapted)) {
						remove(workingSet, adapted);
					}
				}
			}
		}

		for (int i = 0; i < newWorkingSets.length; i++) {
			IWorkingSet set = newWorkingSets[i];
			if (isValidWorkingSet(set) && !grayedSets.contains(set)) {
				boolean checkForYetHiddenWorkingSet = false;
				for (int j = 0; j < elements.length; j++) {
					IAdaptable adapted = adapt(set, elements[j]);
					if (adapted != null && !contains(set, adapted)) {
						add(set, adapted);
						checkForYetHiddenWorkingSet = true;
					}
				}
				if (checkForYetHiddenWorkingSet) {
					IWorkingSet[] activeSets = getActiveWorkingSets();
					if (activeSets != null) {
						List<IWorkingSet> activeWorkingSets = new ArrayList<IWorkingSet>(Arrays.asList(activeSets));
						if (!activeWorkingSets.contains(set))
							activateWorkingSet(set);
					}
				}
			}
		}
	}

	/**
	 * Adds the given working set to the set of currently active working sets.
	 * 
	 * @param workingSet
	 *            working set to be activated
	 * @since 3.5
	 */
	private void activateWorkingSet(IWorkingSet workingSet) {
		WorkingSetModel model = null;
		if (fWorkingSetModel != null) {
			model = fWorkingSetModel;
		} else {
			ScriptExplorerPart activePart = getActivePackageExplorer();
			if (activePart != null) {
				model = activePart.getWorkingSetModel();
			}
		}
		if (model != null) {
			IWorkingSet[] workingSets = model.getActiveWorkingSets();
			IWorkingSet[] activeWorkingSets = new IWorkingSet[workingSets.length + 1];
			System.arraycopy(workingSets, 0, activeWorkingSets, 0, workingSets.length);
			activeWorkingSets[workingSets.length] = workingSet;
			model.setActiveWorkingSets(activeWorkingSets);
		}

	}

	/**
	 * Returns an array of currently active WorkingSets.
	 * 
	 * @return array of active working sets or <code>null</code> if none
	 * @since 3.5
	 */
	private IWorkingSet[] getActiveWorkingSets() {
		if (fWorkingSetModel != null) {
			return fWorkingSetModel.getActiveWorkingSets();
		} else {
			WorkingSetModel model = null;
			ScriptExplorerPart activePart = getActivePackageExplorer();
			if (activePart != null)
				model = activePart.getWorkingSetModel();
			return model == null ? null : model.getActiveWorkingSets();
		}
	}

	private IWorkingSet[] getAllWorkingSets() {
		if (fWorkingSetModel != null) {
			return fWorkingSetModel.getAllWorkingSets();
		} else {
			return PlatformUI.getWorkbench().getWorkingSetManager().getWorkingSets();
		}
	}

	private static boolean isValidWorkingSet(IWorkingSet set) {
		if (set.isAggregateWorkingSet() || !set.isSelfUpdating())
			return false;

		if (!set.isVisible())
			return false;

		if (!set.isEditable())
			return false;

		for (int i = 0; i < VALID_WORKING_SET_IDS.length; i++) {
			if (VALID_WORKING_SET_IDS[i].equals(set.getId()))
				return true;
		}

		return false;
	}

	private static IAdaptable adapt(IWorkingSet set, IAdaptable element) {
		IAdaptable[] adaptedElements = set.adaptElements(new IAdaptable[] { element });
		if (adaptedElements.length != 1)
			return null;

		return adaptedElements[0];
	}

	private static boolean contains(IWorkingSet set, IAdaptable adaptedElement) {
		IAdaptable[] elements = set.getElements();
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].equals(adaptedElement))
				return true;
		}

		return false;
	}

	private static void remove(IWorkingSet workingSet, IAdaptable adaptedElement) {
		HashSet<IAdaptable> set = new HashSet<IAdaptable>(Arrays.asList(workingSet.getElements()));
		set.remove(adaptedElement);
		workingSet.setElements(set.toArray(new IAdaptable[set.size()]));
	}

	private static void add(IWorkingSet workingSet, IAdaptable adaptedElement) {
		IAdaptable[] elements = workingSet.getElements();
		IAdaptable[] newElements = new IAdaptable[elements.length + 1];
		System.arraycopy(elements, 0, newElements, 0, elements.length);
		newElements[elements.length] = adaptedElement;
		workingSet.setElements(newElements);
	}

	private void selectAndReveal(IAdaptable[] elements) {
		ScriptExplorerPart explorer = getActivePackageExplorer();
		if (explorer != null)
			explorer.selectReveal(new StructuredSelection(elements));
	}

	private ScriptExplorerPart getActivePackageExplorer() {
		IWorkbenchPage page = DLTKUIPlugin.getActivePage();
		if (page != null) {
			IWorkbenchPart activePart = page.getActivePart();
			if (activePart instanceof ScriptExplorerPart) {
				return (ScriptExplorerPart) activePart;
			}
		}
		return null;
	}
}
