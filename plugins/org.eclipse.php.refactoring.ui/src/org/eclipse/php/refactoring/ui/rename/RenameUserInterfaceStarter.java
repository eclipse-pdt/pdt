/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.*;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.php.refactoring.core.rename.INameUpdating;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.part.ISetSelectionTarget;

/**
 * @author Roy, 2007
 * @inspiredby JDT
 */
public class RenameUserInterfaceStarter extends UserInterfaceStarter {

	private static class SelectionState {
		private Display fDisplay;
		private Object fElement;
		private List<IWorkbenchPart> fParts;
		private List<IStructuredSelection> fSelections;

		public SelectionState(Object element) {
			fElement = element;
			fParts = new ArrayList<>();
			fSelections = new ArrayList<>();
			init();
		}

		private void init() {
			IWorkbenchWindow dw = RefactoringUIPlugin.getActiveWorkbenchWindow();
			if (dw == null) {
				return;
			}
			fDisplay = dw.getShell().getDisplay();
			IWorkbenchPage page = dw.getActivePage();
			if (page == null) {
				return;
			}
			IViewReference vrefs[] = page.getViewReferences();
			for (int i = 0; i < vrefs.length; i++) {
				consider(vrefs[i].getPart(false));
			}
			IEditorReference refs[] = page.getEditorReferences();
			for (int i = 0; i < refs.length; i++) {
				consider(refs[i].getPart(false));
			}
		}

		private void consider(IWorkbenchPart part) {
			if (part == null) {
				return;
			}
			ISetSelectionTarget target = null;
			if (!(part instanceof ISetSelectionTarget)) {
				target = part.getAdapter(ISetSelectionTarget.class);
				if (target == null) {
					return;
				}
			} else {
				target = (ISetSelectionTarget) part;
			}
			ISelectionProvider selectionProvider = part.getSite().getSelectionProvider();
			if (selectionProvider == null) {
				return;
			}
			ISelection s = selectionProvider.getSelection();
			if (!(s instanceof IStructuredSelection)) {
				return;
			}
			IStructuredSelection selection = (IStructuredSelection) s;
			if (!selection.toList().contains(fElement)) {
				return;
			}
			fParts.add(part);
			fSelections.add(selection);
		}

		public void restore(Object newElement) {
			if (fDisplay == null) {
				return;
			}
			for (int i = 0; i < fParts.size(); i++) {
				IStructuredSelection currentSelection = fSelections.get(i);
				boolean changed = false;
				final ISetSelectionTarget target = (ISetSelectionTarget) fParts.get(i);
				final IStructuredSelection[] newSelection = new IStructuredSelection[1];
				newSelection[0] = currentSelection;
				if (currentSelection instanceof TreeSelection) {
					TreeSelection treeSelection = (TreeSelection) currentSelection;
					TreePath[] paths = treeSelection.getPaths();
					for (int p = 0; p < paths.length; p++) {
						TreePath path = paths[p];
						if (path.getSegmentCount() > 0 && path.getLastSegment().equals(fElement)) {
							paths[p] = createTreePath(path, newElement);
							changed = true;
						}
					}
					if (changed) {
						newSelection[0] = new TreeSelection(paths, treeSelection.getElementComparer());
					}
				} else {
					Object[] elements = currentSelection.toArray();
					for (int e = 0; e < elements.length; e++) {
						if (elements[e].equals(fElement)) {
							elements[e] = newElement;
							changed = true;
						}
					}
					if (changed) {
						newSelection[0] = new StructuredSelection(elements);
					}
				}
				if (changed) {
					fDisplay.asyncExec(new Runnable() {
						@Override
						public void run() {
							target.selectReveal(newSelection[0]);
						}
					});
				}
			}
		}

		// Method assumes that segment count of path > 0.
		private TreePath createTreePath(TreePath old, Object newElement) {
			int count = old.getSegmentCount();
			Object[] newObjects = new Object[count];
			for (int i = 0; i < count - 1; i++) {
				newObjects[i] = old.getSegment(i);
			}
			newObjects[count - 1] = newElement;
			return new TreePath(newObjects);
		}
	}

	@Override
	public boolean activate(Refactoring refactoring, Shell parent, boolean save) throws CoreException {
		RenameProcessor processor = refactoring.getAdapter(RenameProcessor.class);
		Object[] elements = processor.getElements();
		SelectionState state = elements.length == 1 ? new SelectionState(elements[0]) : null;
		boolean executed = super.activate(refactoring, parent, save);
		INameUpdating nameUpdating = refactoring.getAdapter(INameUpdating.class);
		if (executed && nameUpdating != null && state != null) {
			Object newElement = nameUpdating.getNewElement();
			if (newElement != null) {
				state.restore(newElement);
			}
		}
		return executed;
	}
}
