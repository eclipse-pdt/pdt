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
package org.eclipse.php.internal.ui.explorer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IReorgPolicy.ICopyPolicy;
import org.eclipse.dltk.internal.corext.refactoring.reorg.IReorgPolicy.IMovePolicy;
import org.eclipse.dltk.internal.corext.refactoring.reorg.ReorgUtils;
import org.eclipse.dltk.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.dltk.internal.ui.refactoring.reorg.ReorgCopyStarter;
import org.eclipse.dltk.internal.ui.refactoring.reorg.ScriptCopyProcessor;
import org.eclipse.dltk.internal.ui.refactoring.reorg.ScriptMoveProcessor;
import org.eclipse.dltk.internal.ui.scriptview.ScriptMessages;
import org.eclipse.dltk.internal.ui.scriptview.SelectionTransferDropAdapter;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.php.internal.ui.actions.ReorgMoveAction;
import org.eclipse.php.internal.ui.util.ReorgPolicyFactory;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

public class PHPSelectionTransferDropAdapter extends
		SelectionTransferDropAdapter {

	public PHPSelectionTransferDropAdapter(StructuredViewer viewer) {
		super(viewer);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean performDrop(Object data) {
		try {
			switch (getCurrentOperation()) {
			case DND.DROP_MOVE:
				handleDropMove(getCurrentTarget());
				break;
			case DND.DROP_COPY:
				handleDropCopy(getCurrentTarget());
				break;
			}
		} catch (ModelException e) {
			ExceptionHandler.handle(e,
					ScriptMessages.SelectionTransferDropAdapter_error_title,
					ScriptMessages.SelectionTransferDropAdapter_error_message);
		} catch (InvocationTargetException e) {
			ExceptionHandler
					.handle(
							e,
							RefactoringMessages.OpenRefactoringWizardAction_refactoring,
							RefactoringMessages.OpenRefactoringWizardAction_exception);
		} catch (InterruptedException e) {
			// ok
		}
		// The drag source listener must not perform any operation
		// since this drop adapter did the remove of the source even
		// if we moved something.
		return false;

	}

	private void handleDropMove(final Object target) throws ModelException,
			InvocationTargetException, InterruptedException {
		List<?> elements = ((IStructuredSelection) getSelection()).toList();
		IResource[] resources = getResources(elements);

		ReorgMoveAction action = new ReorgMoveAction();
		action.init(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		StructuredSelection selection = new StructuredSelection(resources);
		action.selectionChanged(null, selection);
		Object targetContainer = target;
		if (targetContainer instanceof IModelElement) {
			targetContainer = getResource((IModelElement) target);
		}

		if (targetContainer instanceof IContainer) {
			action.setTarget((IContainer) targetContainer);
		} else if (targetContainer instanceof IResource) {
			action.setTarget(((IResource) targetContainer).getParent());
		}
		action.run((Action) null);
	}

	private void handleDropCopy(final Object target) throws ModelException,
			InvocationTargetException, InterruptedException {
		List elements = ((IStructuredSelection) getSelection()).toList();
		IModelElement[] modelElements = ReorgUtils.getModelElements(elements);
		IResource[] resources = getResources(modelElements);
		ReorgCopyStarter starter = null;
		if (target instanceof IModelElement) {
			starter = ReorgCopyStarter.create(modelElements, resources,
					(IModelElement) target);
		} else if (target instanceof IResource) {
			starter = ReorgCopyStarter.create(modelElements, resources,
					(IResource) target);
		}

		if (starter != null)
			starter.run(this.getViewer().getControl().getShell());
	}

	public IResource[] getResources(IModelElement[] elements) {
		List resultArray = new ArrayList();
		for (int i = 0; i < elements.length; i++) {
			IResource res = getResource(elements[i]);
			if (res != null) {
				resultArray.add(res);
			}
		}
		return (IResource[]) resultArray.toArray(new IResource[resultArray
				.size()]);
	}

	public IResource getResource(IModelElement element) {
		if (element instanceof ISourceModule)
			return ((ISourceModule) element).getPrimary().getResource();
		else
			return element.getResource();
	}

	private List fElements;
	private ScriptMoveProcessor fMoveProcessor;
	private int fCanMoveElements;
	private ScriptCopyProcessor fCopyProcessor;
	private int fCanCopyElements;
	private ISelection fSelection;

	private static final long DROP_TIME_DIFF_TRESHOLD = 150;

	// ---- TransferDropTargetListener interface
	// ---------------------------------------

	public Transfer getTransfer() {
		return LocalSelectionTransfer.getInstance();
	}

	public boolean isEnabled(DropTargetEvent event) {
		Object target = event.item != null ? event.item.getData() : null;
		if (target == null)
			return false;
		return target instanceof IModelElement || target instanceof IResource;
	}

	// ---- Actual DND
	// -----------------------------------------------------------------

	public void dragEnter(DropTargetEvent event) {
		clear();
		super.dragEnter(event);
	}

	public void dragLeave(DropTargetEvent event) {
		clear();
		super.dragLeave(event);
	}

	private void clear() {
		setSelectionFeedbackEnabled(false);
		fElements = null;
		fSelection = null;
		fMoveProcessor = null;
		fCanMoveElements = 0;
		fCopyProcessor = null;
		fCanCopyElements = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean validateDrop(Object target, int operation,
			TransferData transferType) {
		return determineOperation(target, operation, transferType,
				DND.DROP_MOVE | DND.DROP_LINK | DND.DROP_COPY) != DND.DROP_NONE;
	}

	/**
	 * {@inheritDoc}
	 */
	protected int determineOperation(Object target, int operation,
			TransferData transferType, int operations) {
		int result = internalDetermineOperation(target, operation, operations);

		if (result == DND.DROP_NONE) {
			setSelectionFeedbackEnabled(false);
		} else {
			setSelectionFeedbackEnabled(true);
		}

		return result;
	}

	private int internalDetermineOperation(Object target, int operation,
			int operations) {

		initializeSelection();

		if (target == null)
			return DND.DROP_NONE;

		IResource[] resources = getResources(fElements);
		// Do not allow to drop on itself, bug 14228
		if (getCurrentLocation() == LOCATION_ON) {
			IModelElement[] javaElements = ReorgUtils
					.getModelElements(fElements);
			if (contains(javaElements, target))
				return DND.DROP_NONE;

			if (contains(resources, target))
				return DND.DROP_NONE;
		}

		if (target instanceof IModelElement) {
			IResource targetResource = ReorgUtils
					.getResource((IModelElement) target);
			if ((targetResource instanceof IContainer)
					&& isParentOfAny((IContainer) targetResource, resources)) {
				return DND.DROP_NONE;
			}

		}

		try {
			switch (operation) {
			case DND.DROP_DEFAULT:
				return handleValidateDefault(target, operations);
			case DND.DROP_COPY:
				return handleValidateCopy(target);
			case DND.DROP_MOVE:
				return handleValidateMove(target);
			}
		} catch (ModelException e) {
			ExceptionHandler.handle(e,
					ScriptMessages.SelectionTransferDropAdapter_error_title,
					ScriptMessages.SelectionTransferDropAdapter_error_message);
		}

		return DND.DROP_NONE;
	}

	private static boolean isParentOfAny(IContainer container, IResource[] roots) {
		for (int i = 0; i < roots.length; i++) {
			if (ReorgUtils.isParentInWorkspaceOrOnDisk(roots[i], container))
				return true;
		}
		return false;
	}

	private IResource[] getResources(List elements) {
		List resources = new ArrayList(elements.size());
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IResource) {
				resources.add(element);
			}
			if (element instanceof IModelElement) {
				resources.add(((IModelElement) element).getResource());
			}
		}
		return (IResource[]) resources.toArray(new IResource[resources.size()]);
	}

	private boolean contains(IResource[] resources, Object target) {
		for (int i = 0; i < resources.length; i++) {
			if (resources[i] != null) {
				if (resources[i].equals(target))
					return true;
			}
		}

		return false;
	}

	private boolean contains(IModelElement[] elements, Object target) {
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].equals(target))
				return true;
		}

		return false;
	}

	protected void initializeSelection() {
		if (fElements != null)
			return;
		ISelection s = LocalSelectionTransfer.getInstance().getSelection();
		if (!(s instanceof IStructuredSelection)) {
			fSelection = StructuredSelection.EMPTY;
			fElements = Collections.EMPTY_LIST;
			return;
		}
		fSelection = s;
		fElements = ((IStructuredSelection) s).toList();
	}

	protected ISelection getSelection() {
		return fSelection;
	}

	private int handleValidateDefault(Object target, int operations)
			throws ModelException {
		if ((operations & DND.DROP_MOVE) != 0) {
			int result = handleValidateMove(target);
			if (result != DND.DROP_NONE)
				return result;
		}

		return handleValidateCopy(target);
	}

	private int handleValidateMove(Object target) throws ModelException {
		if (fMoveProcessor == null) {
			IMovePolicy policy = ReorgPolicyFactory.createMovePolicy(
					getResources(fElements), ReorgUtils
							.getModelElements(fElements));
			if (policy.canEnable())
				fMoveProcessor = new ScriptMoveProcessor(policy);
		}

		if (!canMoveElements())
			return DND.DROP_NONE;

		if (fMoveProcessor == null)
			return DND.DROP_NONE;

		// if (!fMoveProcessor.setDestination(
		// ReorgDestinationFactory.createDestination(target,
		// getCurrentLocation())).isOK())
		// return DND.DROP_NONE;
		if (target instanceof IModelElement) {
			if (!fMoveProcessor.setDestination((IModelElement) target).isOK()) {
				return DND.DROP_NONE;
			}
		}
		if (target instanceof IResource) {
			if (!fMoveProcessor.setDestination((IResource) target).isOK()) {
				return DND.DROP_NONE;
			}
		}

		return DND.DROP_MOVE;
	}

	private boolean canMoveElements() {
		if (fCanMoveElements == 0) {
			fCanMoveElements = 2;
			if (fMoveProcessor == null)
				fCanMoveElements = 1;
		}
		return fCanMoveElements == 2;
	}

	private int handleValidateCopy(Object target) throws ModelException {

		if (fCopyProcessor == null) {
			final ICopyPolicy policy = ReorgPolicyFactory.createCopyPolicy(
					getResources(fElements), ReorgUtils
							.getModelElements(fElements));
			fCopyProcessor = policy.canEnable() ? new ScriptCopyProcessor(
					policy) : null;
		}

		if (!canCopyElements())
			return DND.DROP_NONE;

		if (fCopyProcessor == null)
			return DND.DROP_NONE;
		if (target instanceof IModelElement) {
			if (!fCopyProcessor.setDestination((IModelElement) target).isOK()) {
				return DND.DROP_NONE;
			}
		}
		if (target instanceof IResource) {
			if (!fCopyProcessor.setDestination((IResource) target).isOK()) {
				return DND.DROP_NONE;
			}
		}
		// if (!fCopyProcessor.setDestination(
		// ReorgDestinationFactory.createDestination(target,
		// getCurrentLocation()).getDestination()).isOK())
		// return DND.DROP_NONE;

		return DND.DROP_COPY;
	}

	private boolean canCopyElements() {
		if (fCanCopyElements == 0) {
			fCanCopyElements = 2;
			if (fCopyProcessor == null)
				fCanCopyElements = 1;
		}
		return fCanCopyElements == 2;
	}

	private Shell getShell() {
		return getViewer().getControl().getShell();
	}

	/**
	 * {@inheritDoc}
	 */
	protected int getCurrentLocation() {
		if (getFeedbackEnabled()) {
			return super.getCurrentLocation();
		} else {
			return LOCATION_ON;
		}
	}

}
