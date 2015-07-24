/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.ui.actions.ActionUtil;
import org.eclipse.dltk.internal.ui.actions.OpenActionUtil;
import org.eclipse.dltk.internal.ui.actions.SelectionConverter;
import org.eclipse.dltk.internal.ui.callhierarchy.SearchUtil;
import org.eclipse.dltk.internal.ui.search.DLTKSearchQuery;
import org.eclipse.dltk.internal.ui.search.DLTKSearchScopeFactory;
import org.eclipse.dltk.internal.ui.search.SearchMessages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.dltk.ui.search.ElementQuerySpecification;
import org.eclipse.dltk.ui.search.QuerySpecification;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.wst.html.core.internal.document.ElementStyleImpl;

/**
 * Abstract class for Script search actions.
 * <p>
 * Note: This class is for internal use only. Clients should not use this class.
 * </p>
 * 
 *
 */
public abstract class FindAction extends SelectionDispatchAction {

	// A dummy which can't be selected in the UI
	private static final IModelElement RETURN_WITHOUT_BEEP = DLTKCore
			.create(DLTKUIPlugin.getWorkspace().getRoot());

	private Class[] fValidTypes;
	private final IDLTKLanguageToolkit toolkit;
	private PHPStructuredEditor fEditor;

	FindAction(IWorkbenchSite site) {
		super(site);
		this.toolkit = PHPLanguageToolkit.getDefault();
		fValidTypes = getValidTypes();
		init();
	}

	FindAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		setEnabled(SelectionConverter.canOperateOn(fEditor));
	}

	/**
	 * Called once by the constructors to initialize label, tooltip, image and
	 * help support of the action. To be overridden by implementors of this
	 * action.
	 */
	abstract void init();

	/**
	 * Called once by the constructors to get the list of the valid input types
	 * of the action. To be overridden by implementors of this action.
	 * 
	 * @return the valid input types of the action
	 */
	abstract Class[] getValidTypes();

	private boolean canOperateOn(IStructuredSelection sel) {
		return sel != null && !sel.isEmpty()
				&& canOperateOn(getModelElement(sel, true));
	}

	boolean canOperateOn(IModelElement element) {
		if (element == null || fValidTypes == null || fValidTypes.length == 0
				|| !ActionUtil.isOnBuildPath(element))
			return false;

		for (int i = 0; i < fValidTypes.length; i++) {
			if (fValidTypes[i].isInstance(element)) {
				if (element.getElementType() == IModelElement.SCRIPT_FOLDER)
					return hasChildren((IScriptFolder) element);
				else
					return true;
			}
		}
		return false;
	}

	private boolean hasChildren(IScriptFolder ScriptFolder) {
		try {
			return ScriptFolder.hasChildren();
		} catch (ModelException ex) {
			return false;
		}
	}

	private IModelElement getTypeIfPossible(IModelElement o, boolean silent) {
		switch (o.getElementType()) {
		case IModelElement.SOURCE_MODULE:
			if (silent)
				return o;
			else
				return findType((ISourceModule) o, silent);
		default:
			return o;
		}
	}

	IModelElement getModelElement(IStructuredSelection selection,
			boolean silent) {
		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
			IModelElement elem = null;
			if (firstElement instanceof ElementStyleImpl) { // selection from
															// WTP
				return null;
			}
			if (firstElement instanceof IModelElement)
				elem = (IModelElement) firstElement;
			else if (firstElement instanceof IAdaptable)
				elem = (IModelElement) ((IAdaptable) firstElement)
						.getAdapter(IModelElement.class);
			if (elem != null) {
				return getTypeIfPossible(elem, silent);
			}

		}
		return null;
	}

	private void showOperationUnavailableDialog() {
		MessageDialog.openInformation(getShell(),
				SearchMessages.DLTKElementAction_operationUnavailable_title,
				getOperationUnavailableMessage());
	}

	String getOperationUnavailableMessage() {
		return NLS.bind(
				SearchMessages.DLTKElementAction_operationUnavailable_generic,
				PHPLanguageToolkit.getDefault());
	}

	private IModelElement findType(ISourceModule cu, boolean silent) {
		IType[] types = null;
		try {
			types = cu.getTypes();
		} catch (ModelException ex) {
			if (ScriptModelUtil.isExceptionToBeLogged(ex))
				ExceptionHandler.log(ex,
						SearchMessages.DLTKElementAction_error_open_message);
			if (silent)
				return RETURN_WITHOUT_BEEP;
			else
				return null;
		}
		if (types.length == 1 || (silent && types.length > 0))
			return types[0];
		if (silent)
			return RETURN_WITHOUT_BEEP;
		if (types.length == 0)
			return null;
		String title = SearchMessages.DLTKElementAction_typeSelectionDialog_title;
		String message = SearchMessages.DLTKElementAction_typeSelectionDialog_message;
		int flags = (ModelElementLabelProvider.SHOW_DEFAULT);

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), new ModelElementLabelProvider(flags));
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setElements(types);

		if (dialog.open() == Window.OK)
			return (IType) dialog.getFirstResult();
		else
			return RETURN_WITHOUT_BEEP;
	}

	/*
	 * Method declared on SelectionChangedAction.
	 */
	public void run(IStructuredSelection selection) {
		IModelElement element = getModelElement(selection, false);
		if (element == null && selection instanceof ITextSelection) {
			run((ITextSelection) selection);
			return;
		}
		if (element == null || !element.exists()) {
			showOperationUnavailableDialog();
			return;
		} else if (element == RETURN_WITHOUT_BEEP)
			return;

		run(element);
	}

	/*
	 * Method declared on SelectionChangedAction.
	 */
	public void run(ITextSelection selection) {
		if (!ActionUtil.isProcessable(getShell(), fEditor))
			return;
		try {
			String title = SearchMessages.SearchElementSelectionDialog_title;
			String message = SearchMessages.SearchElementSelectionDialog_message;

			IModelElement[] elements = SelectionConverter
					.codeResolveForked(fEditor, true);
			if (elements.length > 0 && canOperateOn(elements[0])) {
				IModelElement element = elements[0];
				if (elements.length > 1)
					element = OpenActionUtil.selectModelElement(elements,
							getShell(), title, message);
				if (element != null)
					run(element);
			} else
				showOperationUnavailableDialog();
		} catch (InvocationTargetException ex) {
			String title = SearchMessages.Search_Error_search_title;
			String message = SearchMessages.Search_Error_codeResolve;
			ExceptionHandler.handle(ex, getShell(), title, message);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	/*
	 * Method declared on SelectionChangedAction.
	 */
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(canOperateOn(selection));
	}

	/*
	 * Method declared on SelectionChangedAction.
	 */
	public void selectionChanged(ITextSelection selection) {
	}

	/**
	 * Executes this action for the givenscriptelement.
	 * 
	 * @param element
	 *            Thescriptelement to be found.
	 */
	public void run(IModelElement element) {

		if (!ActionUtil.isProcessable(getShell(), element))
			return;

		// will return true except for debugging purposes.
		try {
			performNewSearch(element);
		} catch (ModelException ex) {
			ExceptionHandler.handle(ex, getShell(),
					SearchMessages.Search_Error_search_notsuccessful_title,
					SearchMessages.Search_Error_search_notsuccessful_message);
		}
	}

	private void performNewSearch(IModelElement element) throws ModelException {
		DLTKSearchQuery query = new DLTKSearchQuery(createQuery(element));
		if (query.canRunInBackground()) {
			/*
			 * This indirection with Object as parameter is needed to prevent
			 * the loading of the Search plug-in: the Interpreter verifies the
			 * method call and hence loads the types used in the method
			 * signature, eventually triggering the loading of a plug-in (in
			 * this case ISearchQuery results in Search plug-in being loaded).
			 */
			SearchUtil.runQueryInBackground(query);
		} else {
			IProgressService progressService = PlatformUI.getWorkbench()
					.getProgressService();
			/*
			 * This indirection with Object as parameter is needed to prevent
			 * the loading of the Search plug-in: the Interpreter verifies the
			 * method call and hence loads the types used in the method
			 * signature, eventually triggering the loading of a plug-in (in
			 * this case it would be ISearchQuery).
			 */
			IStatus status = SearchUtil.runQueryInForeground(progressService,
					query);
			if (status
					.matches(IStatus.ERROR | IStatus.INFO | IStatus.WARNING)) {
				ErrorDialog.openError(getShell(),
						SearchMessages.Search_Error_search_title,
						SearchMessages.Search_Error_search_message, status);
			}
		}
	}

	protected final IDLTKLanguageToolkit getLanguageToolkit() {
		return toolkit;
	}

	QuerySpecification createQuery(IModelElement element)
			throws ModelException {
		DLTKSearchScopeFactory factory = DLTKSearchScopeFactory.getInstance();
		IDLTKSearchScope scope = factory.createWorkspaceScope(true,
				getLanguageToolkit());
		String description = factory.getWorkspaceScopeDescription(true);
		return new ElementQuerySpecification(element, getLimitTo(), scope,
				description);
	}

	abstract int getLimitTo();

	IType getType(IModelElement element) {
		if (element == null)
			return null;

		IType type = null;
		if (element.getElementType() == IModelElement.TYPE)
			type = (IType) element;
		else if (element instanceof IMember)
			type = ((IMember) element).getDeclaringType();
		// else if (element instanceof ILocalVariable) {
		// type= (IType)element.getAncestor(IModelElement.TYPE);
		// }
		return type;
	}

	PHPStructuredEditor getEditor() {
		return fEditor;
	}

}
