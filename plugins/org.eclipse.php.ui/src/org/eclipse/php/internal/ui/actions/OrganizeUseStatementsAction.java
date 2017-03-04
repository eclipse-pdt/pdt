/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.internal.corext.util.History;
import org.eclipse.dltk.internal.corext.util.QualifiedTypeNameHistory;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.codemanipulation.OrganizeUseStatementsOperation;
import org.eclipse.php.internal.ui.corext.codemanipulation.OrganizeUseStatementsOperation.IChooseImportQuery;
import org.eclipse.php.internal.ui.dialogs.MultiElementListSelectionDialog;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.PHPTypeNameMatchLabelProvider;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.*;
import org.eclipse.ui.progress.IProgressService;

import com.ibm.icu.text.Collator;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 */
public class OrganizeUseStatementsAction extends SelectionDispatchAction {

	private static final OrganizeImportComparator ORGANIZE_IMPORT_COMPARATOR = new OrganizeImportComparator();

	private PHPStructuredEditor fEditor;

	/** <code>true</code> if the query dialog is showing. */
	private boolean fIsQueryShowing = false;

	public static class ObjectDelegate implements IObjectActionDelegate {
		private OrganizeUseStatementsAction fAction;

		@Override
		public void setActivePart(IAction action, IWorkbenchPart targetPart) {
			fAction = new OrganizeUseStatementsAction(targetPart.getSite());
		}

		@Override
		public void run(IAction action) {
			fAction.run();
		}

		@Override
		public void selectionChanged(IAction action, ISelection selection) {
			if (fAction == null)
				action.setEnabled(false);
		}
	}

	private static final class OrganizeImportComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			if (o1.equals(o2))
				return 0;

			History history = QualifiedTypeNameHistory.getDefault();

			int pos1 = history.getPosition(o1);
			int pos2 = history.getPosition(o2);

			if (pos1 == pos2)
				return Collator.getInstance().compare(o1, o2);

			if (pos1 > pos2) {
				return -1;
			} else {
				return 1;
			}
		}

	}

	public OrganizeUseStatementsAction(IWorkbenchSite site) {
		super(site);
		setText(Messages.OrganizeImportsAction_label);
		setToolTipText(Messages.OrganizeImportsAction_tooltip);
		setDescription(Messages.OrganizeImportsAction_description);
	}

	public OrganizeUseStatementsAction(IEditorPart part) {
		this(part.getSite());
		if (part instanceof PHPStructuredEditor) {
			fEditor = (PHPStructuredEditor) part;
		}
	}

	@Override
	public void run(ITextSelection selection) {
		ISourceModule cu = getSourceModule(fEditor);
		if (cu != null) {
			run(cu);
		}
	}

	private static ISourceModule getSourceModule(PHPStructuredEditor fEditor) {
		return EditorUtility.getEditorInputModelElement(fEditor, false);
	}

	@Override
	public void run(IStructuredSelection selection) {
		ISourceModule cu = getSourceModule(fEditor);
		if (cu != null) {
			run(cu);
		}
	}

	public void run(ISourceModule sourceModule) {
		try {
			Program astRoot = SharedASTProvider.getAST(sourceModule, SharedASTProvider.WAIT_ACTIVE_ONLY, null);
			OrganizeUseStatementsOperation op = new OrganizeUseStatementsOperation(sourceModule, astRoot,
					createChooseImportQuery(fEditor));
			IRewriteTarget target = (IRewriteTarget) fEditor.getAdapter(IRewriteTarget.class);
			if (target != null) {
				target.beginCompoundChange();
			}

			IProgressService progressService = PlatformUI.getWorkbench().getProgressService();
			IRunnableContext context = getSite().getWorkbenchWindow();
			IEditingSupport helper = createViewerHelper();
			try {
				registerHelper(helper, fEditor);
				progressService.runInUI(context, new WorkbenchRunnableAdapter(op, op.getScheduleRule()),
						op.getScheduleRule());
				setStatusBarMessage(getOrganizeInfo(op), fEditor);
			} catch (InvocationTargetException e) {
			} catch (InterruptedException e) {
			} finally {
				deregisterHelper(helper, fEditor);
				if (target != null) {
					target.endCompoundChange();
				}
			}
		} catch (ModelException | IOException e) {
			PHPUiPlugin.log(e);
		}
	}

	private String getOrganizeInfo(OrganizeUseStatementsOperation op) {
		int nImportsAdded = op.getNumberOfImportsAdded();
		if (nImportsAdded >= 0) {
			if (nImportsAdded == 1) {
				return Messages.OrganizeImportsAction_summary_added_singular;
			} else {
				return Messages.format(Messages.OrganizeImportsAction_summary_added_plural,
						String.valueOf(nImportsAdded));
			}
		} else {
			if (nImportsAdded == -1) {
				return Messages.OrganizeImportsAction_summary_removed_singular;
			} else {
				return Messages.format(Messages.OrganizeImportsAction_summary_removed_plural,
						String.valueOf(-nImportsAdded));
			}
		}
	}

	private IChooseImportQuery createChooseImportQuery(final PHPStructuredEditor editor) {
		return new IChooseImportQuery() {
			@Override
			public TypeNameMatch[] chooseImports(TypeNameMatch[][] openChoices, ISourceRange[] ranges) {
				return doChooseImports(openChoices, ranges, editor);
			}
		};
	}

	private TypeNameMatch[] doChooseImports(TypeNameMatch[][] openChoices, final ISourceRange[] ranges,
			final PHPStructuredEditor editor) {
		// remember selection
		ISelection sel = editor.getSelectionProvider().getSelection();
		TypeNameMatch[] result = null;
		ILabelProvider labelProvider = new PHPTypeNameMatchLabelProvider(
				PHPTypeNameMatchLabelProvider.SHOW_FULLYQUALIFIED, PHPUILanguageToolkit.getInstance());

		MultiElementListSelectionDialog dialog = new MultiElementListSelectionDialog(getShell(), labelProvider) {
			@Override
			protected void handleSelectionChanged() {
				super.handleSelectionChanged();
				// show choices in editor
				doListSelectionChanged(getCurrentPage(), ranges, editor);
			}
		};
		fIsQueryShowing = true;
		dialog.setTitle(Messages.OrganizeImportsAction_selectiondialog_title);
		dialog.setMessage(Messages.OrganizeImportsAction_selectiondialog_message);
		dialog.setElements(openChoices);
		dialog.setComparator(ORGANIZE_IMPORT_COMPARATOR);
		if (dialog.open() == Window.OK) {
			Object[] res = dialog.getResult();
			result = new TypeNameMatch[res.length];
			for (int i = 0; i < res.length; i++) {
				Object[] array = (Object[]) res[i];
				if (array.length > 0) {
					result[i] = (TypeNameMatch) array[0];
					QualifiedTypeNameHistory.remember(result[i].getFullyQualifiedName());
				}
			}
		}
		// restore selection
		if (sel instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) sel;
			editor.selectAndReveal(textSelection.getOffset(), textSelection.getLength());
		}
		fIsQueryShowing = false;
		return result;
	}

	private void doListSelectionChanged(int page, ISourceRange[] ranges, PHPStructuredEditor editor) {
		if (ranges != null && page >= 0 && page < ranges.length) {
			ISourceRange range = ranges[page];
			editor.selectAndReveal(range.getOffset(), range.getLength());
		}
	}

	private void setStatusBarMessage(String message, PHPStructuredEditor editor) {
		IStatusLineManager manager = editor.getEditorSite().getActionBars().getStatusLineManager();
		manager.setMessage(message);
	}

	private IEditingSupport createViewerHelper() {
		return new IEditingSupport() {
			@Override
			public boolean isOriginator(DocumentEvent event, IRegion subjectRegion) {
				return true; // assume true, since we only register while we are
								// active
			}

			@Override
			public boolean ownsFocusShell() {
				return fIsQueryShowing;
			}

		};
	}

	private void registerHelper(IEditingSupport helper, PHPStructuredEditor editor) {
		ISourceViewer viewer = editor.getViewer();
		if (viewer instanceof IEditingSupportRegistry) {
			IEditingSupportRegistry registry = (IEditingSupportRegistry) viewer;
			registry.register(helper);
		}
	}

	private void deregisterHelper(IEditingSupport helper, PHPStructuredEditor editor) {
		ISourceViewer viewer = editor.getViewer();
		if (viewer instanceof IEditingSupportRegistry) {
			IEditingSupportRegistry registry = (IEditingSupportRegistry) viewer;
			registry.unregister(helper);
		}
	}
}