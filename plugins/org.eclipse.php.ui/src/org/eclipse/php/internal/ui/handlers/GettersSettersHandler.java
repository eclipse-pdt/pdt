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
import java.util.*;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.AddGetterSetterOperation;
import org.eclipse.php.internal.ui.actions.CodeGenerationSettings;
import org.eclipse.php.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.php.internal.ui.dialogs.GettersSettersDialog;
import org.eclipse.php.internal.ui.dialogs.GettersSettersDialog.GetterSetterEntry;
import org.eclipse.php.internal.ui.dialogs.GettersSettersDialog.GetterSetterLabelProvider;
import org.eclipse.php.internal.ui.dialogs.GettersSettersDialog.GettersSettersContentProvider;
import org.eclipse.php.internal.ui.dialogs.GettersSettersDialog.GettersSettersSelectionStatusValidator;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.php.ui.util.CodeGenerationUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This is a handler for GettersSettersAction class. Allows contributing to
 * extension 'org.eclipse.ui.menus'.
 * 
 */
public class GettersSettersHandler extends AbstractHandler {

	private static final String DIALOG_TITLE = Messages.GettersSettersHandler_0;
	private IWorkbenchWindow fWindow;
	private boolean fSort;
	private boolean fGenerateComment;
	private boolean fFinal;
	private int fVisibility;
	private int fNumEntries;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		fWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		Object selection = HandlerUtil.getCurrentSelection(event);

		// The action is run from a view.
		if (selection instanceof IStructuredSelection) {
			execute((IStructuredSelection) selection);
		}

		// The action is run from a editor.
		if (selection instanceof ITextSelection) {
			execute(event, (ITextSelection) selection);
		}

		return null;
	}

	private void execute(IStructuredSelection selection) throws ExecutionException {
		Object object = selection.getFirstElement();

		if (object instanceof IModelElement) {
			IModelElement element = (IModelElement) object;

			if (element.getOpenable() instanceof ExternalSourceModule) {
				MessageDialog.openError(fWindow.getShell(),
						Messages.bind(DIALOG_TITLE, new String[] { Messages.GettersSettersHandler_1 }),
						Messages.GettersSettersHandler_2);
				return;
			}

			try {
				IEditorPart editor = CodeGenerationUtils.openInEditor(element, true);
				if (editor instanceof PHPStructuredEditor) {
					run((ISourceModule) ((PHPStructuredEditor) editor).getModelElement(), element,
							(PHPStructuredEditor) editor);
				}
			} catch (PartInitException e) {
				MessageDialog.openError(fWindow.getShell(),
						Messages.bind(DIALOG_TITLE, new String[] { Messages.GettersSettersHandler_1 }),
						Messages.GettersSettersHandler_2);
				throw new ExecutionException("Editor open Error", e); //$NON-NLS-1$
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

		if (!(source instanceof ISourceModule) || source instanceof ExternalSourceModule) {
			MessageDialog.openError(fWindow.getShell(),
					Messages.bind(DIALOG_TITLE, new String[] { Messages.GettersSettersHandler_1 }),
					Messages.GettersSettersHandler_2);
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

	/**
	 * The method opens the input dialog and generates the code.
	 * 
	 * @param source
	 *            the source module.
	 * @param element
	 *            the current selected element.
	 * @param editor
	 *            the editor
	 */
	private void run(ISourceModule source, IModelElement element, TextEditor editor) throws ModelException {
		IType type = CodeGenerationUtils.getType(element);

		// The current selection is not in a class.
		if (type == null) {
			MessageDialog.openError(fWindow.getShell(),
					Messages.bind(DIALOG_TITLE, new String[] { Messages.GettersSettersHandler_1 }),
					Messages.GettersSettersHandler_2);
			return;
		}

		int flags = type.getFlags();

		// The current selection is a interface.
		if (PHPFlags.isInterface(flags)) {
			MessageDialog.openInformation(fWindow.getShell(),
					Messages.bind(DIALOG_TITLE, new String[] { Messages.GettersSettersHandler_1 }),
					Messages.GettersSettersHandler_4);
			return;
		}

		resetNumEntries();

		Map<IField, GetterSetterEntry[]> entries = createGetterSetterMapping(type);

		// No fields are eligible to generate getter/setter.
		if (entries.isEmpty()) {
			MessageDialog.openInformation(fWindow.getShell(),
					Messages.bind(DIALOG_TITLE, new String[] { Messages.GettersSettersHandler_1 }),
					Messages.GettersSettersHandler_5);
			return;
		}

		GettersSettersDialog dialog = new GettersSettersDialog(fWindow.getShell(), new GetterSetterLabelProvider(),
				new GettersSettersContentProvider(entries), type, editor);

		dialog.setTitle(Messages.bind(DIALOG_TITLE, type.getElementName()));
		String message = Messages.GettersSettersHandler_9;
		dialog.setMessage(message);
		dialog.setValidator(createValidator(fNumEntries));
		dialog.setProject(source.getScriptProject().getProject());

		dialog.setContainerMode(true);
		dialog.setSize(60, 18);
		dialog.setInput(type);
		if (element instanceof IField) {
			dialog.setInitialSelections(new Object[] { element });
			dialog.setExpandedElements(new Object[] { element });

		}

		final Set<IField> keySet = new LinkedHashSet<>(entries.keySet());
		int dialogResult = dialog.open();
		if (dialogResult != Window.OK) {
			return;
		}
		Object[] result = dialog.getResult();
		if (result == null) {
			return;
		}
		fSort = dialog.getSortOrder();
		fFinal = dialog.isFinal();
		fVisibility = dialog.getVisibilityModifier();
		fGenerateComment = dialog.getGenerateComment();
		IField[] getterFields, setterFields, getterSetterFields;
		if (fSort) {
			getterFields = getGetterFields(result, keySet);
			setterFields = getSetterFields(result, keySet);
			getterSetterFields = new IField[0];
		} else {
			getterFields = getGetterOnlyFields(result, keySet);
			setterFields = getSetterOnlyFields(result, keySet);
			getterSetterFields = getGetterSetterFields(result, keySet);
		}

		IProject project = source.getScriptProject().getProject();

		Program program = null;
		IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		program = CodeGenerationUtils.getASTRoot(source, document, project);

		final IRewriteTarget target = editor.getAdapter(IRewriteTarget.class);
		if (target != null) {
			target.setRedraw(false);
			target.beginCompoundChange();
		}

		try {
			if (program != null) {
				generate(type, getterFields, setterFields, getterSetterFields, program, document,
						dialog.getElementPosition());
			}
		} finally {
			if (target != null) {
				target.setRedraw(true);
				target.endCompoundChange();
			}
		}

	}

	private void resetNumEntries() {
		fNumEntries = 0;
	}

	private void incNumEntries() {
		fNumEntries++;
	}

	/**
	 * Generates the code.
	 * 
	 * @param type
	 * @param getterFields
	 * @param setterFields
	 * @param getterSetterFields
	 * @param program
	 * @param document
	 * @param elementPosition
	 */
	private void generate(IType type, IField[] getterFields, IField[] setterFields, IField[] getterSetterFields,
			Program program, IDocument document, IMethod elementPosition) {
		try {
			CodeGenerationSettings settings = new CodeGenerationSettings();
			settings.createComments = fGenerateComment;

			AddGetterSetterOperation op = new AddGetterSetterOperation(type, getterFields, setterFields,
					getterSetterFields, program, document, elementPosition, settings, true);
			setOperationStatusFields(op);

			IRunnableContext context = PHPUiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
			if (context == null) {
				context = new BusyIndicatorRunnableContext();
			}

			PlatformUI.getWorkbench().getProgressService().runInUI(context,
					new WorkbenchRunnableAdapter(op, op.getSchedulingRule()), op.getSchedulingRule());

		} catch (InvocationTargetException e) {
			String message = Messages.GettersSettersHandler_6;
			MessageDialog.openError(fWindow.getShell(),
					Messages.bind(DIALOG_TITLE, new String[] { Messages.GettersSettersHandler_1 }), message);

		} catch (InterruptedException e) {
			// operation canceled
		} finally {
		}

	}

	private void setOperationStatusFields(AddGetterSetterOperation op) {
		// Set the status fields corresponding to the visibility and modifiers
		// set
		int flags = fVisibility;
		if (fFinal) {
			flags |= Flags.AccFinal;
		}
		op.setSort(fSort);
		op.setVisibility(flags);
	}

	// returns a list of fields with setter entries checked
	private static IField[] getSetterFields(Object[] result, Set<IField> set) {
		List<IField> list = new ArrayList<>();
		Object each = null;
		GetterSetterEntry entry = null;
		for (int i = 0; i < result.length; i++) {
			each = result[i];
			if (each instanceof GetterSetterEntry) {
				entry = (GetterSetterEntry) each;
				if (!entry.isGetter) {
					list.add(entry.field);
				}
			}
		}
		list = reorderFields(list, set);
		return list.toArray(new IField[list.size()]);
	}

	// returns a list of fields with getter entries checked
	private static IField[] getGetterFields(Object[] result, Set<IField> set) {
		List<IField> list = new ArrayList<>();
		Object each = null;
		GetterSetterEntry entry = null;
		for (int i = 0; i < result.length; i++) {
			each = result[i];
			if ((each instanceof GetterSetterEntry)) {
				entry = (GetterSetterEntry) each;
				if (entry.isGetter) {
					list.add(entry.field);
				}
			}
		}
		list = reorderFields(list, set);
		return list.toArray(new IField[list.size()]);
	}

	// returns a list of fields with only getter entries checked
	private static IField[] getGetterOnlyFields(Object[] result, Set<IField> set) {
		List<IField> list = new ArrayList<>(0);
		Object each = null;
		GetterSetterEntry entry = null;
		boolean getterSet = false;
		for (int i = 0; i < result.length; i++) {
			each = result[i];
			if (each instanceof GetterSetterEntry) {
				entry = (GetterSetterEntry) each;
				if (entry.isGetter) {
					list.add(entry.field);
					getterSet = true;
				}
				if (!entry.isGetter && getterSet == true) {
					list.remove(entry.field);
					getterSet = false;
				}
			} else
				getterSet = false;
		}
		list = reorderFields(list, set);
		return list.toArray(new IField[list.size()]);
	}

	// returns a list of fields with only setter entries checked
	private static IField[] getSetterOnlyFields(Object[] result, Set<IField> set) {
		List<IField> list = new ArrayList<>(0);
		Object each = null;
		GetterSetterEntry entry = null;
		boolean getterSet = false;
		for (int i = 0; i < result.length; i++) {
			each = result[i];
			if (each instanceof GetterSetterEntry) {
				entry = (GetterSetterEntry) each;
				if (entry.isGetter) {
					getterSet = true;
				}
				if (!entry.isGetter && getterSet != true) {
					list.add(entry.field);
					getterSet = false;
				}
			} else
				getterSet = false;
		}
		list = reorderFields(list, set);
		return list.toArray(new IField[list.size()]);
	}

	// returns a list of fields with both entries checked
	private static IField[] getGetterSetterFields(Object[] result, Set<IField> set) {
		List<IField> list = new ArrayList<>();
		Object each = null;
		GetterSetterEntry entry = null;
		boolean getterSet = false;
		for (int i = 0; i < result.length; i++) {
			each = result[i];
			if (each instanceof GetterSetterEntry) {
				entry = (GetterSetterEntry) each;
				if (entry.isGetter) {
					getterSet = true;
				}
				if ((!entry.isGetter) && (getterSet == true)) {
					list.add(entry.field);
					getterSet = false;
				}
			} else
				getterSet = false;
		}
		list = reorderFields(list, set);
		return list.toArray(new IField[list.size()]);
	}

	private static List<IField> reorderFields(List<IField> collection, Set<IField> set) {
		final List<IField> list = new ArrayList<>(collection.size());
		for (final Iterator<IField> iterator = set.iterator(); iterator.hasNext();) {
			final IField field = iterator.next();
			if (collection.contains(field)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * @param type
	 *            the type
	 * @return map IField -> GetterSetterEntry[]
	 * @throws ModelException
	 *             if the type does not exist or if an exception occurs while
	 *             accessing its corresponding resource
	 */
	private Map<IField, GetterSetterEntry[]> createGetterSetterMapping(IType type) throws ModelException {
		IField[] fields = type.getFields();
		Map<IField, GetterSetterEntry[]> result = new LinkedHashMap<>();
		for (IField field : fields) {
			int flags = field.getFlags();

			if (PHPFlags.isConstant(flags)) {
				continue;
			}

			List<GetterSetterEntry> l = new ArrayList<>(2);
			if (CodeGenerationUtils.getGetter(field) == null) {
				l.add(new GetterSetterEntry(field, true, Flags.isFinal(flags), PHPFlags.isConstant(flags)));
				incNumEntries();
			}

			if (CodeGenerationUtils.getSetter(field) == null) {
				l.add(new GetterSetterEntry(field, false, Flags.isFinal(flags), PHPFlags.isConstant(flags)));
				incNumEntries();
			}

			if (!l.isEmpty()) {
				result.put(field, l.toArray(new GetterSetterEntry[l.size()]));
			}
		}
		return result;
	}

	private static ISelectionStatusValidator createValidator(int entries) {
		return new GettersSettersSelectionStatusValidator(entries);
	}

}