/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.workingset;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.SelectionConverter;
import org.eclipse.php.internal.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.internal.ui.util.PHPElementImageProvider;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.php.internal.ui.util.PHPElementSorter;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.IWorkingSetPage;

/**
 * The PHP working set page allows the user to create
 * and edit a PHP working set.
 * <p>
 * Working set elements are presented as a PHP element tree.
 * </p>
 * 
 * @since 2.0
 */
public class PHPWorkingSetPage extends WizardPage implements IWorkingSetPage {

	final private static String PAGE_TITLE = PHPUIMessages.PHPWorkingSetPage_title;
	final private static String PAGE_ID = "phpWorkingSetPage"; //$NON-NLS-1$

	private Text fWorkingSetName;
	private CheckboxTreeViewer fTree;
	private ITreeContentProvider fTreeContentProvider;

	private boolean fFirstCheck;
	private IWorkingSet fWorkingSet;

	/**
	 * Default constructor.
	 */
	public PHPWorkingSetPage() {
		super(PAGE_ID, PAGE_TITLE, PHPPluginImages.DESC_WIZBAN_ADD_LIBRARY);
		setDescription(PHPUIMessages.PHPWorkingSetPage_workingSet_description);
		fFirstCheck = true;
	}

	/*
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		setControl(composite);

		Label label = new Label(composite, SWT.WRAP);
		label.setText(PHPUIMessages.PHPWorkingSetPage_workingSet_name);
		GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
		label.setLayoutData(gd);

		fWorkingSetName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		fWorkingSetName.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		fWorkingSetName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});
		fWorkingSetName.setFocus();

		label = new Label(composite, SWT.WRAP);
		label.setText(PHPUIMessages.PHPWorkingSetPage_workingSet_content);
		gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
		label.setLayoutData(gd);

		fTree = new CheckboxTreeViewer(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL);
		gd.heightHint = convertHeightInCharsToPixels(15);
		fTree.getControl().setLayoutData(gd);

		fTreeContentProvider = new PHPWorkingSetPageContentProvider();
		fTree.setContentProvider(fTreeContentProvider);

		AppearanceAwareLabelProvider fPHPElementLabelProvider = new AppearanceAwareLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | PHPElementLabels.P_COMPRESSED, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS | PHPElementImageProvider.SMALL_ICONS);

		fTree.setLabelProvider(fPHPElementLabelProvider);
		fTree.setSorter(new PHPElementSorter());
		fTree.setUseHashlookup(true);

		fTree.setInput(PHPWorkspaceModelManager.getInstance());

		fTree.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				handleCheckStateChange(event);
			}
		});

		fTree.addTreeListener(new ITreeViewerListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
			}

			public void treeExpanded(TreeExpansionEvent event) {
				final Object element = event.getElement();
				if (fTree.getGrayed(element) == false)
					BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
						public void run() {
							setSubtreeChecked(element, fTree.getChecked(element), false);
						}
					});
			}
		});

		// Add select / deselect all buttons for bug 46669
		Composite buttonComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonComposite.setLayout(layout);
		buttonComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		Button selectAllButton = new Button(buttonComposite, SWT.PUSH);
		selectAllButton.setText(PHPUIMessages.PHPWorkingSetPage_selectAll_label);
		selectAllButton.setToolTipText(PHPUIMessages.PHPWorkingSetPage_selectAll_toolTip);
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent selectionEvent) {
				fTree.setCheckedElements(fTreeContentProvider.getElements(fTree.getInput()));
				validateInput();
			}
		});
		selectAllButton.setLayoutData(new GridData());
		SWTUtil.setButtonDimensionHint(selectAllButton);

		Button deselectAllButton = new Button(buttonComposite, SWT.PUSH);
		deselectAllButton.setText(PHPUIMessages.PHPWorkingSetPage_deselectAll_label);
		deselectAllButton.setToolTipText(PHPUIMessages.PHPWorkingSetPage_deselectAll_toolTip);
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent selectionEvent) {
				fTree.setCheckedElements(new Object[0]);
				validateInput();
			}
		});
		deselectAllButton.setLayoutData(new GridData());
		SWTUtil.setButtonDimensionHint(deselectAllButton);

		if (fWorkingSet != null)
			fWorkingSetName.setText(fWorkingSet.getName());
		initializeCheckedState();
		validateInput();

		Dialog.applyDialogFont(composite);
		// Set help for the page 
		//		PHPUIHelp.setHelp(fTree, IPHPHelpContextIds.PHP_WORKING_SET_PAGE);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.PHP_WORKING_SET_PAGE);
	}

	/*
	 * Implements method from IWorkingSetPage
	 */
	public IWorkingSet getSelection() {
		return fWorkingSet;
	}

	/*
	 * Implements method from IWorkingSetPage
	 */
	public void setSelection(IWorkingSet workingSet) {
		Assert.isNotNull(workingSet, "Working set must not be null"); //$NON-NLS-1$
		fWorkingSet = workingSet;
		if (getContainer() != null && getShell() != null && fWorkingSetName != null) {
			fFirstCheck = false;
			fWorkingSetName.setText(fWorkingSet.getName());
			initializeCheckedState();
			validateInput();
		}
	}

	/*
	 * Implements method from IWorkingSetPage
	 */
	public void finish() {
		String workingSetName = fWorkingSetName.getText();
		ArrayList elements = new ArrayList(10);
		findCheckedElements(elements, fTree.getInput());
		if (fWorkingSet == null) {
			IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
			fWorkingSet = workingSetManager.createWorkingSet(workingSetName, (IAdaptable[]) elements.toArray(new IAdaptable[elements.size()]));
		} else {
			// Add inaccessible resources
			IAdaptable[] oldItems = fWorkingSet.getElements();
			ArrayList closedWithChildren = new ArrayList(elements.size());
			for (int i = 0; i < oldItems.length; i++) {
				IResource oldResource = null;
				if (oldItems[i] instanceof IResource) {
					oldResource = (IResource) oldItems[i];
				} else {
					oldResource = (IResource) oldItems[i].getAdapter(IResource.class);
				}
				if (oldResource != null && oldResource.isAccessible() == false) {
					IProject project = oldResource.getProject();
					if (elements.contains(project) || closedWithChildren.contains(project)) {
						elements.add(oldItems[i]);
						elements.remove(project);
						closedWithChildren.add(project);
					}
				}
			}
			fWorkingSet.setName(workingSetName);
			fWorkingSet.setElements((IAdaptable[]) elements.toArray(new IAdaptable[elements.size()]));
		}
	}

	private void validateInput() {
		String errorMessage = null; //$NON-NLS-1$
		String newText = fWorkingSetName.getText();

		if (newText.equals(newText.trim()) == false)
			errorMessage = PHPUIMessages.PHPWorkingSetPage_warning_nameWhitespace;
		if (newText.equals("")) { //$NON-NLS-1$
			if (fFirstCheck) {
				setPageComplete(false);
				fFirstCheck = false;
				return;
			} else
				errorMessage = PHPUIMessages.PHPWorkingSetPage_warning_nameMustNotBeEmpty;
		}

		fFirstCheck = false;

		if (errorMessage == null && (fWorkingSet == null || newText.equals(fWorkingSet.getName()) == false)) {
			IWorkingSet[] workingSets = PlatformUI.getWorkbench().getWorkingSetManager().getWorkingSets();
			for (int i = 0; i < workingSets.length; i++) {
				if (newText.equals(workingSets[i].getName())) {
					errorMessage = PHPUIMessages.PHPWorkingSetPage_warning_workingSetExists;
				}
			}
		}
		if (errorMessage == null && !hasCheckedElement())
			errorMessage = PHPUIMessages.PHPWorkingSetPage_warning_resourceMustBeChecked;

		setErrorMessage(errorMessage);
		setPageComplete(errorMessage == null);
	}

	private boolean hasCheckedElement() {
		TreeItem[] items = fTree.getTree().getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getChecked())
				return true;
		}
		return false;
	}

	private void findCheckedElements(List checkedResources, Object parent) {
		Object[] children = fTreeContentProvider.getChildren(parent);
		for (int i = 0; i < children.length; i++) {
			if (fTree.getGrayed(children[i]))
				findCheckedElements(checkedResources, children[i]);
			else if (fTree.getChecked(children[i]))
				checkedResources.add(children[i]);
		}
	}

	void handleCheckStateChange(final CheckStateChangedEvent event) {
		BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
			public void run() {
				IAdaptable element = (IAdaptable) event.getElement();
				boolean state = event.getChecked();
				fTree.setGrayed(element, false);
				if (isExpandable(element))
					setSubtreeChecked(element, state, state); // only check subtree if state is set to true

				updateParentState(element, state);
				validateInput();
			}
		});
	}

	private void setSubtreeChecked(Object parent, boolean state, boolean checkExpandedState) {
		if (!(parent instanceof IAdaptable))
			return;
		IContainer container = (IContainer) ((IAdaptable) parent).getAdapter(IContainer.class);
		if ((!fTree.getExpandedState(parent) && checkExpandedState) || (container != null && !container.isAccessible()))
			return;

		Object[] children = fTreeContentProvider.getChildren(parent);
		for (int i = children.length - 1; i >= 0; i--) {
			Object element = children[i];
			if (state) {
				fTree.setChecked(element, true);
				fTree.setGrayed(element, false);
			} else
				fTree.setGrayChecked(element, false);
			if (isExpandable(element))
				setSubtreeChecked(element, state, true);
		}
	}

	private void updateParentState(Object child, boolean baseChildState) {
		if (child == null)
			return;
		if (child instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) child).getAdapter(IResource.class);
			if (resource != null && !resource.isAccessible())
				return;
		}
		Object parent = fTreeContentProvider.getParent(child);
		if (parent == null)
			return;

		boolean allSameState = true;
		Object[] children = null;
		children = fTreeContentProvider.getChildren(parent);

		for (int i = children.length - 1; i >= 0; i--) {
			if (fTree.getChecked(children[i]) != baseChildState || fTree.getGrayed(children[i])) {
				allSameState = false;
				break;
			}
		}

		fTree.setGrayed(parent, !allSameState);
		fTree.setChecked(parent, !allSameState || baseChildState);

		updateParentState(parent, baseChildState);
	}

	private void initializeCheckedState() {

		BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
			public void run() {
				Object[] elements;
				if (fWorkingSet == null) {
					// Use current part's selection for initialization
					IWorkbenchPage page = PHPUiPlugin.getActivePage();
					if (page == null)
						return;

					IWorkbenchPart part = PHPUiPlugin.getActivePage().getActivePart();
					if (part == null)
						return;

					elements = SelectionConverter.getStructuredSelection(part).toArray();
					for (int i = 0; i < elements.length; i++) {
						if (elements[i] instanceof IFile) {
							PHPFileData phpFile = PHPWorkspaceModelManager.getInstance().getModelForFile((IFile) elements[i], false);
							if (phpFile != null)
								elements[i] = phpFile;
						}
					}
				} else
					elements = fWorkingSet.getElements();

				// Use closed project for elements in closed project
				for (int i = 0; i < elements.length; i++) {
					Object element = elements[i];
					if (element instanceof IResource) {
						IProject project = ((IResource) element).getProject();
						if (!project.isAccessible())
							elements[i] = project;
					}
					if (element instanceof PHPCodeData) {
						IResource resource = PHPModelUtil.getResource(element);
						if (resource != null && !resource.getProject().isAccessible())
							elements[i] = resource.getProject();
					}
				}

				fTree.setCheckedElements(elements);
				for (int i = 0; i < elements.length; i++) {
					Object element = elements[i];
					if (isExpandable(element))
						setSubtreeChecked(element, true, true);

					updateParentState(element, true);
				}
			}
		});
	}

	private boolean isExpandable(Object element) {
		return (element instanceof PHPWorkspaceModelManager || element instanceof IContainer);
	}
}
