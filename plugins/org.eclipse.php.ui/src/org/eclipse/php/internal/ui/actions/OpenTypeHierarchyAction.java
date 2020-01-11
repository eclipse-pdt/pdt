/*******************************************************************************
 * Copyright (c) 2005, 2008, 2015, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.actions.ActionUtil;
import org.eclipse.dltk.internal.ui.actions.OpenActionUtil;
import org.eclipse.dltk.internal.ui.browsing.LogicalPackage;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.typehierarchy.TypeHierarchyViewPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.PHPSelectionUtil;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.texteditor.IUpdate;

/**
 * This action opens a type hierarchy on the selected type.
 * <p>
 * The action is applicable to selections containing elements of type
 * <code>IType</code>.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 2.0
 */
public class OpenTypeHierarchyAction extends SelectionDispatchAction implements IUpdate {

	private PHPStructuredEditor fEditor;

	/**
	 * Creates a new <code>OpenTypeHierarchyAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type
	 * <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the site providing context information for this action
	 */
	public OpenTypeHierarchyAction(IWorkbenchSite site) {
		super(site);
		setText(Messages.OpenTypeHierarchyAction_0);
		setToolTipText(Messages.OpenTypeHierarchyAction_0);
		setDescription(Messages.OpenTypeHierarchyAction_0);
		// HELP - PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IPHPHelpContextIds.OPEN_TYPE_HIERARCHY_ACTION);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor
	 *            the PHP editor
	 */
	public OpenTypeHierarchyAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		setEnabled(SelectionConverter.canOperateOn(fEditor));
	}

	@Override
	public void selectionChanged(final ITextSelection selection) {

	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(isEnabled(selection));

	}

	private boolean isEnabled(IStructuredSelection selection) {
		if (selection == null || selection.size() != 1) {
			return false;
		}

		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof IMethod) {
			return ((IMethod) firstElement).getParent() instanceof IType;
		} else {
			return firstElement instanceof IType || firstElement instanceof IField;
		}
	}

	@Override
	public void run(ITextSelection selection) {
		IModelElement input = EditorUtility.getEditorInputModelElement(fEditor, true);
		if (input == null || !ActionUtil.isProcessable(getShell(), input) || !(input instanceof ISourceModule)) {
			return;
		}
		final IModelElement selectionModelElement = PHPSelectionUtil.getSelectionModelElement(selection.getOffset(),
				selection.getLength(), (ISourceModule) input);
		run(new IModelElement[] { selectionModelElement });
	}

	@Override
	public void run(IStructuredSelection selection) {
		if (selection instanceof ITextSelection) {
			run((ITextSelection) selection);
		} else {
			if (selection.size() != 1) {
				return;
			}
			Object input = selection.getFirstElement();

			if (input instanceof LogicalPackage) {
				IScriptFolder[] fragments = ((LogicalPackage) input).getFragments();
				if (fragments.length == 0) {
					return;
				}
				input = fragments[0];
			}

			if (!(input instanceof IModelElement)) {
				IStatus status = createStatus(Messages.OpenTypeHierarchyAction_3);
				ErrorDialog.openError(getShell(), getDialogTitle(), "", //$NON-NLS-1$
						status);
				return;
			}
			IModelElement element = (IModelElement) input;
			if (!ActionUtil.isProcessable(getShell(), element)) {
				return;
			}

			List<IModelElement> result = new ArrayList<>(1);
			IStatus status = compileCandidates(result, element);
			if (status.isOK()) {
				run(result.toArray(new IModelElement[result.size()]));
			} else {
				ErrorDialog.openError(getShell(), getDialogTitle(), "", //$NON-NLS-1$
						status);
			}
		}
	}

	/*
	 * No Javadoc since the method isn't meant to be public but is since the
	 * beginning
	 */
	public void run(IModelElement[] elements) {
		if (elements.length == 0) {
			getShell().getDisplay().beep();
			return;
		}
		open(elements, getSite().getWorkbenchWindow());
	}

	public static TypeHierarchyViewPart open(IModelElement[] candidates, IWorkbenchWindow window) {
		Assert.isNotNull(candidates);
		Assert.isTrue(candidates.length != 0);

		IModelElement input = null;
		if (candidates.length > 1) {
			String title = ""; //$NON-NLS-1$
			String message = ""; //$NON-NLS-1$
			input = OpenActionUtil.selectModelElement(candidates, window.getShell(), title, message);
		} else {
			input = candidates[0];
		}

		if (input == null) {
			return null;
		}

		return openInViewPart(window, input);
	}

	private static TypeHierarchyViewPart openInViewPart(IWorkbenchWindow window, IModelElement input) {
		IWorkbenchPage page = window.getActivePage();
		try {
			TypeHierarchyViewPart result = (TypeHierarchyViewPart) page.findView(DLTKUIPlugin.ID_TYPE_HIERARCHY);
			if (result != null) {
				result.clearNeededRefresh(); // avoid refresh of old hierarchy
				// on 'becomes visible'
			}
			result = (TypeHierarchyViewPart) page.showView(DLTKUIPlugin.ID_TYPE_HIERARCHY);
			result.setInputElement(input);
			return result;
		} catch (CoreException e) {
			ExceptionHandler.handle(e, window.getShell(), "", e //$NON-NLS-1$
					.getMessage());
		}
		return null;
	}

	private static String getDialogTitle() {
		return ""; //$NON-NLS-1$
	}

	private static IStatus createStatus(String message) {
		return new Status(IStatus.INFO, PHPUiPlugin.getPluginId(), PHPUiPlugin.INTERNAL_ERROR, message, null);
	}

	@Override
	public void update() {
		setEnabled(fEditor != null);
	}

	private static IStatus compileCandidates(List<IModelElement> result, IModelElement elem) {
		IStatus ok = new Status(IStatus.OK, PHPUiPlugin.getPluginId(), 0, "", null); //$NON-NLS-1$
		try {
			switch (elem.getElementType()) {
			// case IModelElement.INITIALIZER:
			case IModelElement.METHOD:
			case IModelElement.FIELD:
			case IModelElement.TYPE:
			case IModelElement.PROJECT_FRAGMENT:
			case IModelElement.SCRIPT_PROJECT:
				result.add(elem);
				return ok;
			case IModelElement.SCRIPT_FOLDER:
				if (((IScriptFolder) elem).containsScriptResources()) {
					result.add(elem);
					return ok;
				}
				return createStatus(""); //$NON-NLS-1$
			case IModelElement.PACKAGE_DECLARATION:
				result.add(elem.getAncestor(IModelElement.SCRIPT_FOLDER));
				return ok;
			case IModelElement.SOURCE_MODULE:
				ISourceModule cu = (ISourceModule) elem;
				IType[] types = cu.getTypes();
				if (types.length > 0) {
					result.addAll(Arrays.asList(types));
					return ok;
				}
				return createStatus(""); //$NON-NLS-1$
			}
		} catch (ModelException e) {
			return e.getStatus();
		}
		return createStatus(""); //$NON-NLS-1$
	}
}
