/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.AbstractSourceModule;
import org.eclipse.dltk.internal.ui.actions.ActionUtil;
import org.eclipse.dltk.internal.ui.actions.OpenActionUtil;
import org.eclipse.dltk.internal.ui.browsing.LogicalPackage;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.typehierarchy.TypeHierarchyViewPart;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.IBinding;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.editor.SharedASTProvider;
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
public class OpenTypeHierarchyAction extends SelectionDispatchAction implements
		IUpdate {

	private PHPStructuredEditor fEditor;
	private IModelElement lastSelectedElement;

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

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void selectionChanged(final ITextSelection selection) {

		IJobManager jobManager = Job.getJobManager();
		if (jobManager.find(PHPUiPlugin.OPEN_TYPE_HIERARCHY_ACTION_FAMILY_NAME).length > 0) {
			jobManager
					.cancel(PHPUiPlugin.OPEN_TYPE_HIERARCHY_ACTION_FAMILY_NAME);
		}

		Job job = new Job(PHPUiPlugin.OPEN_TYPE_HIERARCHY_ACTION_FAMILY_NAME) {
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
		if (selection == null || selection.size() != 1) {
			setEnabled(false);
		} else if (selection instanceof ITextSelection) {
			selectionChanged((ITextSelection) selection);
		} else if (selection instanceof ITreeSelection) {
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IMethod) {
				setEnabled(((IMethod) firstElement).getParent() instanceof IType);
			} else {
				setEnabled(firstElement instanceof IType
						|| firstElement instanceof IField);
			}
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
				lastSelectedElement = null;
				return false;
			}
			switch (element.getElementType()) {
			case IModelElement.TYPE:
			case IModelElement.FIELD:
			case IModelElement.METHOD:
			case IModelElement.SOURCE_MODULE:
				lastSelectedElement = element;
				return true;
			}
		}
		lastSelectedElement = null;
		return false;
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
				IModelElement[] selected = sourceModule.codeSelect(offset, 1);
				if (selected.length > 0) {
					element = selected[0];
				}
			} catch (ModelException e) {
			}
		}
		return element;
	}

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void run(ITextSelection selection) {
		IModelElement input = EditorUtility.getEditorInputModelElement(fEditor,
				true);
		if (input == null || !ActionUtil.isProcessable(getShell(), input)
				|| !(input instanceof ISourceModule)) {
			return;
		}
		final IModelElement selectionModelElement = getSelectionModelElement(
				selection.getOffset(), selection.getLength(),
				(ISourceModule) input);
		run(new IModelElement[] { selectionModelElement });
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

			if (input instanceof LogicalPackage) {
				IScriptFolder[] fragments = ((LogicalPackage) input)
						.getFragments();
				if (fragments.length == 0)
					return;
				input = fragments[0];
			}

			// || firstElement instanceof PHPSuperClassNameData || firstElement
			// instanceof PHPInterfaceNameData
			if (!(input instanceof IModelElement)) {
				IStatus status = createStatus(Messages.OpenTypeHierarchyAction_3);
				ErrorDialog.openError(getShell(), getDialogTitle(), "", //$NON-NLS-1$
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
				ErrorDialog.openError(getShell(), getDialogTitle(), "", //$NON-NLS-1$
						status);
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
			// }
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

	public static TypeHierarchyViewPart open(IModelElement[] candidates,
			IWorkbenchWindow window) {
		Assert.isNotNull(candidates);
		Assert.isTrue(candidates.length != 0);

		IModelElement input = null;
		if (candidates.length > 1) {
			String title = ""; //$NON-NLS-1$
			String message = ""; //$NON-NLS-1$
			input = OpenActionUtil.selectModelElement(candidates,
					window.getShell(), title, message);
		} else {
			input = candidates[0];
		}

		if (input == null)
			return null;

		return openInViewPart(window, input);
	}

	private static TypeHierarchyViewPart openInViewPart(
			IWorkbenchWindow window, IModelElement input) {
		IWorkbenchPage page = window.getActivePage();
		try {
			TypeHierarchyViewPart result = (TypeHierarchyViewPart) page
					.findView(DLTKUIPlugin.ID_TYPE_HIERARCHY);
			if (result != null) {
				result.clearNeededRefresh(); // avoid refresh of old hierarchy
				// on 'becomes visible'
			}
			result = (TypeHierarchyViewPart) page
					.showView(DLTKUIPlugin.ID_TYPE_HIERARCHY);
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
		return new Status(IStatus.INFO, PHPUiPlugin.getPluginId(),
				PHPUiPlugin.INTERNAL_ERROR, message, null);
	}

	public void update() {
		setEnabled(fEditor != null);
	}

	/**
	 * @return text selection in the editor
	 */
	protected ITextSelection getCurrentSelection() {
		if (fEditor == null) {
			return null;
		}
		ISelectionProvider provider = fEditor.getSelectionProvider();
		if (provider == null) {
			return null;
		}
		ISelection selection = provider.getSelection();
		if (selection instanceof ITextSelection) {
			return (ITextSelection) selection;
		}
		return null;
	}

	private static IStatus compileCandidates(List result, IModelElement elem) {
		IStatus ok = new Status(IStatus.OK, PHPUiPlugin.getPluginId(), 0,
				"", null); //$NON-NLS-1$
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
				// case IModelElement.IMPORT_DECLARATION:
				// IImportDeclaration decl= (IImportDeclaration) elem;
				// if (decl.isOnDemand()) {
				// elem= JavaModelUtil.findTypeContainer(elem.getJavaProject(),
				// Signature.getQualifier(elem.getElementName()));
				// } else {
				// elem= elem.getJavaProject().findType(elem.getElementName());
				// }
				// if (elem != null) {
				// result.add(elem);
				// return ok;
				// }
				// return createStatus(ActionMessages.
				// OpenTypeHierarchyAction_messages_unknown_import_decl);
				// case IJavaElement.CLASS_FILE:
				// result.add(((IClassFile)elem).getType());
				// return ok;
			case IModelElement.SOURCE_MODULE:
				AbstractSourceModule cu = (AbstractSourceModule) elem;
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
