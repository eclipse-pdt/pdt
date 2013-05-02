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
package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.IDLTKStatusConstants;
import org.eclipse.dltk.internal.ui.actions.ActionUtil;
import org.eclipse.dltk.internal.ui.callhierarchy.CallHierarchyMessages;
import org.eclipse.dltk.internal.ui.callhierarchy.CallHierarchyUI;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.actions.SelectionDispatchAction;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.IBinding;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.IWorkbenchSite;

/**
 * This action opens a call hierarchy on the selected method.
 * <p>
 * The action is applicable to selections containing elements of type
 * <code>IMethod</code>.
 */
public class OpenCallHierarchyAction extends SelectionDispatchAction {

	private PHPStructuredEditor fEditor;

	/**
	 * Creates a new <code>OpenCallHierarchyAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type
	 * <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the site providing context information for this action
	 */
	public OpenCallHierarchyAction(IWorkbenchSite site) {
		super(site);
		setText(CallHierarchyMessages.OpenCallHierarchyAction_label);
		setToolTipText(CallHierarchyMessages.OpenCallHierarchyAction_tooltip);
		setDescription(CallHierarchyMessages.OpenCallHierarchyAction_description);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IJavaHelpContextIds.CALL_HIERARCHY_OPEN_ACTION);
	}

	/**
	 * Creates a new <code>OpenCallHierarchyAction</code>. The action requires
	 * that the selection provided by the given selection provider is of type
	 * <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the site providing context information for this action
	 * @param provider
	 *            a special selection provider which is used instead of the
	 *            site's selection provider or <code>null</code> to use the
	 *            site's selection provider
	 * 
	 * 
	 * @deprecated Use {@link #setSpecialSelectionProvider(ISelectionProvider)}
	 *             instead. This API will be removed after 3.2 M5.
	 */
	public OpenCallHierarchyAction(IWorkbenchSite site,
			ISelectionProvider provider) {
		this(site);
		setSpecialSelectionProvider(provider);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 */
	public OpenCallHierarchyAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		setEnabled(SelectionConverter.canOperateOn(fEditor));
	}

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void selectionChanged(final ITextSelection selection) {

		IJobManager jobManager = Job.getJobManager();
		if (jobManager.find(PHPUiPlugin.OPEN_CALL_HIERARCHY_ACTION_FAMILY_NAME).length > 0) {
			jobManager
					.cancel(PHPUiPlugin.OPEN_CALL_HIERARCHY_ACTION_FAMILY_NAME);
		}

		Job job = new Job(PHPUiPlugin.OPEN_CALL_HIERARCHY_ACTION_FAMILY_NAME) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				setEnabled(isEnabled(selection));
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return getName().equals(family);
			}
		};
		job.setSystem(true);
		job.setPriority(Job.DECORATE);
		job.schedule();

	}

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void selectionChanged(IStructuredSelection selection) {
		if (selection instanceof ITextSelection) {
			selectionChanged((ITextSelection) selection);
		} else {
			setEnabled(isEnabled(selection));
		}
	}

	private boolean isEnabled(IStructuredSelection selection) {
		if (selection.size() != 1)
			return false;
		Object input = selection.getFirstElement();
		if (!(input instanceof IModelElement) && (input instanceof IAdaptable))
			input = ((IAdaptable) input).getAdapter(IModelElement.class);
		if (!(input instanceof IModelElement))
			return false;

		switch (((IModelElement) input).getElementType()) {
		case IModelElement.METHOD:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Returns true if the given selection is for an {@link IModelElement} that
	 * is a TYPE (e.g. Class or Interface).
	 */
	private boolean isEnabled(ITextSelection selection) {
		if (fEditor == null || selection == null)
			return false;
		if (fEditor.getModelElement() instanceof ISourceModule) {
			ISourceModule sourceModule = (ISourceModule) fEditor
					.getModelElement();
			IModelElement element = getSelectionModelElement(
					selection.getOffset(), selection.getLength(), sourceModule);
			if (element == null) {
				return false;
			}
			switch (element.getElementType()) {
			case IModelElement.METHOD:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void run(ITextSelection selection) {
		ISourceModule input = SelectionConverter.getInput(fEditor);
		if (!ActionUtil.isProcessable(getShell(), input))
			return;

		try {
			IModelElement[] elements = SelectionConverter
					.codeResolveOrInputForked(fEditor);
			if (elements == null)
				return;
			List candidates = new ArrayList(elements.length);
			for (int i = 0; i < elements.length; i++) {
				IModelElement[] resolvedElements = CallHierarchyUI
						.getCandidates(elements[i]);
				if (resolvedElements != null)
					candidates.addAll(Arrays.asList(resolvedElements));
			}
			if (candidates.isEmpty()) {
				IModelElement enclosingMethod = getEnclosingMethod(input,
						selection);
				if (enclosingMethod != null) {
					candidates.add(enclosingMethod);
				}
			}
			run((IModelElement[]) candidates
					.toArray(new IModelElement[candidates.size()]));
		} catch (InvocationTargetException e) {
			ExceptionHandler.handle(e, getShell(), getErrorDialogTitle(), ""); //$NON-NLS-1$
		} catch (InterruptedException e) {
			// cancelled
		}
	}

	private IModelElement getEnclosingMethod(IModelElement input,
			ITextSelection selection) {
		IModelElement enclosingElement = null;
		try {
			switch (input.getElementType()) {
			// case IModelElement.CLASS_FILE :
			// IClassFile classFile= (IClassFile)
			// input.getAncestor(IModelElement.CLASS_FILE);
			// if (classFile != null) {
			// enclosingElement= classFile.getElementAt(selection.getOffset());
			// }
			// break;
			case IModelElement.SOURCE_MODULE:
				ISourceModule cu = (ISourceModule) input
						.getAncestor(IModelElement.SOURCE_MODULE);
				if (cu != null) {
					enclosingElement = cu.getElementAt(selection.getOffset());
				}
				break;
			}
			if (enclosingElement != null
					&& enclosingElement.getElementType() == IModelElement.METHOD) {
				return enclosingElement;
			}
		} catch (ModelException e) {
			DLTKUIPlugin.log(e);
		}

		return null;
	}

	/**
	 * Returns an {@link IModelElement} from the given selection. In case that
	 * the element is not resolvable, return null.
	 * 
	 * @param selection
	 * @param sourceModule
	 * @return The {@link IModelElement} or null.
	 */
	protected IModelElement getSelectionModelElement(int offset, int length,
			ISourceModule sourceModule) {
		IModelElement element = null;
		try {
			Program ast = SharedASTProvider.getAST(sourceModule,
					SharedASTProvider.WAIT_NO, null);
			if (ast != null) {
				ASTNode selectedNode = NodeFinder.perform(ast, offset, length);
				if (selectedNode != null
						&& selectedNode.getType() == ASTNode.IDENTIFIER) {
					IBinding binding = ((Identifier) selectedNode)
							.resolveBinding();
					if (binding != null) {
						element = binding.getPHPElement();
					}
				}
			}
		} catch (Exception e) {
			// Logger.logException(e);
		}
		if (element == null) {
			// try to get the top level
			try {
				element = sourceModule.getElementAt(offset);
			} catch (ModelException e) {
			}
		}
		return element;
	}

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void run(IStructuredSelection selection) {
		if (selection instanceof ITextSelection) {
			run((ITextSelection) selection);
		} else {
			if (selection.size() != 1)
				return;
			Object input = selection.getFirstElement();
			if (!(input instanceof IModelElement)) {
				IStatus status = createStatus(Messages.OpenCallHierarchyAction_0);
				ErrorDialog.openError(getShell(), getErrorDialogTitle(), "", //$NON-NLS-1$
						status);
				return;
			}
			IModelElement element = (IModelElement) input;
			if (!ActionUtil.isProcessable(getShell(), element))
				return;
			List result = new ArrayList(1);
			IStatus status = compileCandidates(result, element);
			if (status.isOK()) {
				run((IModelElement[]) result.toArray(new IModelElement[result
						.size()]));
			} else {
				openErrorDialog(status);
			}
			// ISourceModule sourceModule = (ISourceModule) input;
			// String fileName = sourceModule.getElementName();
			// IModelElement element = DLTKCore.create(ResourcesPlugin
			// .getWorkspace().getRoot().getFile(
			// Path.fromOSString(fileName)));
			// if (element instanceof ISourceModule) {
			// int offset = 0;
			// try {
			// offset = sourceModule.getSourceRange().getOffset();
			// } catch (ModelException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }// getUserData().getStopPosition();
			// IModelElement modelElement = getSelectionModelElement(offset,
			// 1, (ISourceModule) element);
			// if (modelElement != null) {
			// if (!ActionUtil.isProcessable(getShell(), modelElement)) {
			// return;
			// }
			// run(new IModelElement[] { modelElement });
			// }
			// }
		}
	}

	private int openErrorDialog(IStatus status) {
		String message = ""; //$NON-NLS-1$
		String dialogTitle = getErrorDialogTitle();
		return ErrorDialog.openError(getShell(), dialogTitle, message, status);
	}

	private static String getErrorDialogTitle() {
		return ""; //$NON-NLS-1$
	}

	public void run(IModelElement[] elements) {
		if (elements.length == 0) {
			getShell().getDisplay().beep();
			return;
		}
		CallHierarchyUI.open(elements, getSite().getWorkbenchWindow(),
				getCallHierarchyID());
	}

	private static IStatus compileCandidates(List result, IModelElement elem) {
		IStatus ok = new Status(IStatus.OK, DLTKUIPlugin.getPluginId(), 0,
				"", null); //$NON-NLS-1$
		switch (elem.getElementType()) {
		case IModelElement.METHOD:
			result.add(elem);
			return ok;
		}
		return createStatus(""); //$NON-NLS-1$
	}

	private static IStatus createStatus(String message) {
		return new Status(IStatus.INFO, DLTKUIPlugin.getPluginId(),
				IDLTKStatusConstants.INTERNAL_ERROR, message, null);
	}

	public String getCallHierarchyID() {
		return "org.eclipse.dltk.callhierarchy.view"; //$NON-NLS-1$
	}
}
