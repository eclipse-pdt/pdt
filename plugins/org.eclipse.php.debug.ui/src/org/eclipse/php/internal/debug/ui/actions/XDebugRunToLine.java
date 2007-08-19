/* Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Mikhail Khodjaiants (QNX) - https://bugs.eclipse.org/bugs/show_bug.cgi?id=83464
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.model.ISuspendResume;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.debug.internal.ui.actions.ActionMessages;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.debug.ui.contexts.IDebugContextManager;
import org.eclipse.debug.ui.contexts.IDebugContextService;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.*;

/**
 * A run to line action that can be contributed to a an editor or view. The action
 * will perform the "run to line" operation for parts that provide
 * an appropriate <code>IRunToLineTarget</code> adapter.
 * <p>
 * Clients may reference/contribute this class as an action delegate
 * in plug-in XML. This class is not intended to be subclassed.
 * </p>
 * <p>
 * Since 3.1, this action also implements {@link org.eclipse.ui.IViewActionDelegate}.
 * </p>
 * @since 3.0
 */
public class XDebugRunToLine implements IEditorActionDelegate, IActionDelegate2, IViewActionDelegate {

	private IWorkbenchPart fActivePart = null;
	private IRunToLineTarget fPartTarget = null;
	private IAction fAction = null;
	private DebugContextListener fContextListener = new DebugContextListener();
	private ISuspendResume fTargetElement = null;

	class DebugContextListener implements IDebugContextListener {

		protected void contextActivated(ISelection selection) {
			fTargetElement = null;
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				if (ss.size() == 1) {
					Object object = ss.getFirstElement();
					if (object instanceof ISuspendResume) {
						fTargetElement = (ISuspendResume) object;
					}
				}
			}
			update();
		}

		public void debugContextChanged(DebugContextEvent event) {
			contextActivated(event.getContext());
		}

	}

	/*(non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#dispose()
	 */
	public void dispose() {
		DebugUITools.getDebugContextManager().getContextService(fActivePart.getSite().getWorkbenchWindow()).removeDebugContextListener(fContextListener);
		fActivePart = null;
		fPartTarget = null;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (fPartTarget != null && fTargetElement != null) {
			try {
				fPartTarget.runToLine(fActivePart, fActivePart.getSite().getSelectionProvider().getSelection(), fTargetElement);
			} catch (CoreException e) {
				DebugUIPlugin.errorDialog(fActivePart.getSite().getWorkbenchWindow().getShell(), ActionMessages.RunToLineAction_0, ActionMessages.RunToLineAction_1, e.getStatus()); // 
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.fAction = action;
		update();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.IUpdate#update()
	 */
	public void update() {
		if (fAction == null) {
			return;
		}
		Runnable r = new Runnable() {
			public void run() {
				boolean enabled = false;
				if (fPartTarget != null && fTargetElement != null) {
					IWorkbenchPartSite site = fActivePart.getSite();
					if (site != null) {
						ISelectionProvider selectionProvider = site.getSelectionProvider();
						if (selectionProvider != null) {
							ISelection selection = selectionProvider.getSelection();
							enabled = fTargetElement.isSuspended() && fPartTarget.canRunToLine(fActivePart, selection, fTargetElement);
						}
					}
				}
				fAction.setEnabled(enabled);
			}
		};
		DebugUIPlugin.getStandardDisplay().asyncExec(r);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	public void init(IAction action) {
		this.fAction = action;
		if (action != null) {
			action.setText(ActionMessages.RunToLineActionDelegate_4);
			action.setImageDescriptor(DebugUITools.getImageDescriptor(IInternalDebugUIConstants.IMG_LCL_RUN_TO_LINE));
			action.setDisabledImageDescriptor(DebugUITools.getImageDescriptor(IInternalDebugUIConstants.IMG_DLCL_RUN_TO_LINE));
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction, org.eclipse.swt.widgets.Event)
	 */
	public void runWithEvent(IAction action, Event event) {
		run(action);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		init(action);
		bindTo(targetEditor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	public void init(IViewPart view) {
		bindTo(view);
	}

	/**
	 * Binds this action to operate on the given part's run to line adapter.
	 *  
	 * @param part
	 */
	private void bindTo(IWorkbenchPart part) {
		IDebugContextManager manager = DebugUITools.getDebugContextManager();
		if (fActivePart != null && !fActivePart.equals(part)) {
			manager.getContextService(fActivePart.getSite().getWorkbenchWindow()).removeDebugContextListener(fContextListener);
		}
		fPartTarget = null;
		fActivePart = part;
		if (part != null) {
			IWorkbenchWindow workbenchWindow = part.getSite().getWorkbenchWindow();
			IDebugContextService service = manager.getContextService(workbenchWindow);
			service.addDebugContextListener(fContextListener);
			if (part instanceof org.eclipse.php.internal.ui.editor.PHPStructuredEditor) {
				fPartTarget = new XDebugRunToLineTarget();
			} else {
				fPartTarget = (IRunToLineTarget) part.getAdapter(IRunToLineTarget.class);
				if (fPartTarget == null) {
					IAdapterManager adapterManager = Platform.getAdapterManager();
					// TODO: we could restrict loading to cases when the debugging context is on
					if (adapterManager.hasAdapter(part, IRunToLineTarget.class.getName())) {
						fPartTarget = (IRunToLineTarget) adapterManager.loadAdapter(part, IRunToLineTarget.class.getName());
					}
				}
			}
			ISelection activeContext = service.getActiveContext();
			fContextListener.contextActivated(activeContext);
		}
		update();
	}
}