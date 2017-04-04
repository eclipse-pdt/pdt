/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.core.ast.nodes.IMethodBinding;
import org.eclipse.php.core.ast.nodes.ITypeBinding;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.TypeBinding;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.AddUnimplementedMethodsOperation;
import org.eclipse.php.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.php.internal.ui.dialogs.OverrideMethodDialog;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.php.ui.util.CodeGenerationUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This is a handler for OverrideImplementAction class. Allows contributing to
 * extension 'org.eclipse.ui.menus'.
 * 
 */
public class OverrideImplementHandler extends AbstractHandler {

	/** The dialog title */
	private static final String DIALOG_TITLE = Messages.OverrideImplementHandler_0;

	private IWorkbenchWindow fWindow;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		fWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		Object selection = HandlerUtil.getCurrentSelectionChecked(event);

		// Selection are on view.
		if (selection instanceof IStructuredSelection) {
			execute(event, ((IStructuredSelection) selection));
		}

		// Selection are on editor.
		if (selection instanceof ITextSelection) {
			execute(event, (ITextSelection) selection);
		}

		return null;
	}

	private void execute(ExecutionEvent event, IStructuredSelection selection) throws ExecutionException {
		Object object = selection.getFirstElement();

		if (object instanceof IModelElement) {
			IModelElement element = (IModelElement) object;

			if (element.getOpenable() instanceof ExternalSourceModule) {
				MessageDialog.openError(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_1);
				return;
			}

			try {
				IEditorPart editor = CodeGenerationUtils.openInEditor(element, true);
				if (editor instanceof PHPStructuredEditor) {
					run((ISourceModule) ((PHPStructuredEditor) editor).getModelElement(), element,
							(PHPStructuredEditor) editor);
				}
			} catch (PartInitException e) {
				MessageDialog.openError(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_1);
				throw new ExecutionException(Messages.OverrideImplementHandler_2, e);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
	}

	private void execute(ExecutionEvent event, ITextSelection selection) throws ExecutionException {
		PHPStructuredEditor editor = CodeGenerationUtils.getPHPEditor(event);
		if (editor == null) {
			return;
		}
		IModelElement source = editor.getModelElement();
		if (!(source instanceof ISourceModule)) {
			MessageDialog.openError(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_3);
			return;
		}

		if (source instanceof ExternalSourceModule) {
			MessageDialog.openError(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_1);
			return;
		}

		IModelElement element = CodeGenerationUtils.getCurrentModelElement(source, editor, selection);

		// This is a bug of HandlerUtils. When open the file first time, the
		// current selection returns the offset as 0.
		// try to get the element via the editor.
		if (element == null) {
			element = CodeGenerationUtils.getCurrentModelElement(event);
		}
		try {
			run((ISourceModule) source, element, editor);
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
		}
	}

	private void run(ISourceModule source, IModelElement element, TextEditor editor) throws ModelException {
		IType type = CodeGenerationUtils.getType(element);
		if (type == null) {
			MessageDialog.openError(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_4);
			return;
		}

		int flags = type.getFlags();
		if (PHPFlags.isInterface(flags)) {
			MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_5);
			return;
		}

		OverrideMethodDialog dialog = new OverrideMethodDialog(fWindow.getShell(), editor, type, false);
		dialog.setProject(source.getScriptProject().getProject());

		if (!dialog.hasMethodsToOverride()) {
			MessageDialog.openInformation(fWindow.getShell(), Messages.OverrideImplementHandler_6,
					Messages.OverrideImplementHandler_7);

			return;
		}
		if (dialog.open() != Window.OK || dialog.getResult() == null) {
			return;
		}

		final Object[] selected = dialog.getResult();
		ArrayList<Object> methods = new ArrayList<>();
		for (Object elem : selected) {
			if (elem instanceof IMethodBinding) {
				methods.add(elem);
			}
		}
		IMethodBinding[] methodToOverride = methods.toArray(new IMethodBinding[methods.size()]);
		final IRewriteTarget target = editor.getAdapter(IRewriteTarget.class);
		if (target != null) {
			target.setRedraw(false);
			target.beginCompoundChange();
		}

		try {
			Program astRoot = dialog.getCompilationUnit();

			IEvaluatedType evaluatedType = PHPClassType.fromIType(type);
			final ITypeBinding typeBinding = new TypeBinding(astRoot.getAST().getBindingResolver(), evaluatedType,
					type);

			int insertPos = dialog.getInsertOffset();

			IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			AddUnimplementedMethodsOperation operation = createRunnable(astRoot, type, typeBinding, methodToOverride,
					insertPos, dialog.getGenerateComment(), document);
			IRunnableContext context = PHPUiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
			if (context == null) {
				context = new BusyIndicatorRunnableContext();
			}
			PlatformUI.getWorkbench().getProgressService().runInUI(context,
					new WorkbenchRunnableAdapter(operation, operation.getSchedulingRule()),
					operation.getSchedulingRule());
			final String[] created = operation.getCreatedMethods();
			if (created == null || created.length == 0) {
				MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_8);
			}
		} catch (InvocationTargetException exception) {
			MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE, Messages.OverrideImplementHandler_9);
			PHPUiPlugin.log(exception);
		} catch (InterruptedException exception) {
			// Do nothing. Operation has been canceled by user.
		} finally {
			if (target != null) {
				target.setRedraw(true);
				target.endCompoundChange();
			}
		}
	}

	private AddUnimplementedMethodsOperation createRunnable(Program astRoot, IType element, ITypeBinding typeBinding,
			IMethodBinding[] methodToOverride, int insertPos, boolean generateComment, IDocument doc) {
		AddUnimplementedMethodsOperation operation = new AddUnimplementedMethodsOperation(astRoot, element, typeBinding,
				methodToOverride, insertPos, true, doc);
		operation.setCreateComments(generateComment);
		return operation;
	}
}