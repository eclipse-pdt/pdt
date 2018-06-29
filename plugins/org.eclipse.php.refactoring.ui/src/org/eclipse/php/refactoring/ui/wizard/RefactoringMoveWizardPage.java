/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import java.lang.reflect.Array;

import org.eclipse.core.resources.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.refactoring.core.move.PHPMoveProcessor;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.model.WorkbenchViewerComparator;

/**
 * Move wizard page. Responsible for the creation of the wizard content.
 * 
 * @author Eden K., 2007
 * 
 */
public class RefactoringMoveWizardPage extends UserInputWizardPage {

	private TreeViewer fViewer;
	private Button fReferenceCheckbox;

	public RefactoringMoveWizardPage() {
		super(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.0")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite result = new Composite(parent, SWT.NONE);
		setControl(result);
		result.setLayout(new GridLayout());

		IResource[] initialSelections = new IResource[] { getPHPMoveProcessor().getDestination() };

		verifyDestination(initialSelections, true);

		addLabel(result);

		fViewer = createViewer(result);

		IResource target = initialSelections[0];
		if (target instanceof IFile) {
			target = target.getParent();
		}

		if (target != null) {
			fViewer.setSelection(new StructuredSelection(target), true);
		}
		fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				viewerSelectionChanged(event);
			}
		});

		addUpdateReferenceComponent(result);
		// TODO add fully qualified names checkbox (? enhancement)

		Dialog.applyDialogFont(result);

	}

	private Control addLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		String text = PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.1"); //$NON-NLS-1$
		label.setText(text);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, false, false));
		return label;
	}

	private TreeViewer createViewer(Composite parent) {
		TreeViewer treeViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = convertWidthInCharsToPixels(40);
		gd.heightHint = convertHeightInCharsToPixels(15);
		treeViewer.getTree().setLayoutData(gd);

		treeViewer.getTree().setLayoutData(gd);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		treeViewer.setComparator(new WorkbenchViewerComparator());
		treeViewer.setInput(ResourcesPlugin.getWorkspace());
		treeViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IProject) {
					IProject project = (IProject) element;
					return project.isAccessible();
				} else if (element instanceof IFolder) {
					return true;
				}
				return false;
			}
		});
		// treeViewer.addSelectionChangedListener(new
		// ISelectionChangedListener() {
		// public void selectionChanged(SelectionChangedEvent event) {
		// validatePage();
		// }
		// });
		// if (resourcesToMove.length > 0) {
		// fDestinationField.setSelection(new
		// StructuredSelection(resourcesToMove[0].getParent()));
		// }
		// treeViewer.setLabelProvider(new
		// PHPElementLabelProvider(PHPElementLabelProvider.SHOW_SMALL_ICONS));
		// treeViewer.setContentProvider(new ContainerContentProvider());
		// treeViewer.setSorter(new PHPElementSorter());
		// treeViewer.setInput(PHPWorkspaceModelManager.getInstance());
		return treeViewer;
	}

	public TreeViewer getTreeViewer() {
		return fViewer;
	}

	private void viewerSelectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection ss = (IStructuredSelection) selection;
		verifyDestination(ss.getFirstElement(), false);
	}

	private final void verifyDestination(Object selected, boolean initialVerification) {
		try {
			RefactoringStatus status = verifyDestination(selected);
			if (initialVerification) {
				setPageComplete(status.isOK());
			} else {
				setPageComplete(status);
			}
		} catch (Exception e) {
			Logger.logException(e);
			setPageComplete(false);
		}
	}

	protected RefactoringStatus verifyDestination(Object selected) throws Exception {
		PHPMoveProcessor processor = getPHPMoveProcessor();
		final RefactoringStatus refactoringStatus;

		if (selected.getClass().isArray() && Array.getLength(selected) == 1) {
			try {
				selected = Array.get(selected, 0);
			} catch (Exception e) {

			}
		}

		if (selected instanceof IContainer) {
			refactoringStatus = processor.setDestination((IContainer) selected);
		} else {
			refactoringStatus = RefactoringStatus
					.createFatalErrorStatus(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.2")); //$NON-NLS-1$
		}

		return refactoringStatus;
	}

	private PHPMoveProcessor getPHPMoveProcessor() {
		return getRefactoring().getAdapter(PHPMoveProcessor.class);
	}

	private void addUpdateReferenceComponent(Composite result) {
		final PHPMoveProcessor processor = getPHPMoveProcessor();

		fReferenceCheckbox = new Button(result, SWT.CHECK);
		fReferenceCheckbox.setText(PHPRefactoringUIMessages.getString("RefactoringMoveWizardPage.3")); //$NON-NLS-1$
		fReferenceCheckbox.setSelection(processor.getUpdateReferences());
		fReferenceCheckbox.setEnabled(true);
		fReferenceCheckbox.setSelection(true);
		processor.setUpdateReferences(true);
		getRefactoringWizard().setForcePreviewReview(true);

		fReferenceCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				processor.setUpdateReferences(((Button) e.widget).getSelection());
				getRefactoringWizard().setForcePreviewReview(processor.getUpdateReferences());
			}
		});
	}

}
