/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.debugger.Expression;
import org.eclipse.php.internal.debug.core.debugger.ExpressionValue;
import org.eclipse.php.internal.debug.core.debugger.ExpressionsManager;
import org.eclipse.php.internal.debug.core.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.model.PHPStackFrame;
import org.eclipse.php.internal.debug.core.model.PHPThread;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;

/**
 * View of the PHP parameter stack 
 */
public class PHPStackView extends AbstractDebugView implements ISelectionListener {

    private PHPDebugTarget fTarget;

    class StackViewContentProvider implements ITreeContentProvider {

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         */
        public Object[] getChildren(Object element) {
            try {
                if (element instanceof PHPDebugTarget) {
                    IThread[] threads = ((PHPDebugTarget) element).getThreads();
                    if (threads != null) {
                        if (threads[0] != null) {
                            IStackFrame frame = threads[0].getTopStackFrame();
                            if (frame == null)
                                return new Expression[0];
                            Expression[] variables = ((PHPStackFrame) frame).getStackVariables();
                            if (variables == null) {
                            	return new Expression[0];
                            }
                            return variables;
                        }
                    }
                } else if (element instanceof PHPThread) {
                    IStackFrame frame = ((PHPThread) element).getTopStackFrame();
                    if (frame == null)
                        return new Expression[0];
                    Expression[] variables = ((PHPStackFrame) frame).getStackVariables();
                    if (variables == null) {
                    	return new Expression[0];
                    }
                    return variables;
                } else if (element instanceof PHPStackFrame) {
                	Expression[] variables = ((PHPStackFrame) element).getStackVariables();
                	if (variables == null) {
                    	return new Expression[0];
                    }
                    return variables;
                } else if (element instanceof Expression) {
                    Expression eExp = (Expression) element;
                    ExpressionValue value = eExp.getValue();
                    Expression[] eChildren = value.getChildren();
                    if (eChildren == null)
                        return new Expression[0];
                    if (eChildren.length == 0) {
                        ExpressionsManager expressionManager = fTarget.getExpressionManager();
                        expressionManager.update(eExp, 1);
                        value = eExp.getValue();
                        eChildren = value.getChildren();
                        if (eChildren == null)
                            eChildren = new Expression[0];
                    }
                    return eChildren;
                }
            } catch (DebugException e) {
                Logger.logException("StackViewContentProvider unexpected error", e);
            }
            return new Object[0];
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         */
        public Object getParent(Object element) {
            if (element instanceof IDebugTarget) {
                return null;
            } else if (element instanceof IDebugElement) {
                return ((IDebugElement) element).getDebugTarget();
            }
            return null;
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         */
        public boolean hasChildren(Object element) {
            if (element instanceof IDebugElement) {
                return getChildren(element).length > 0;
            } else if (element instanceof Expression) {
                Expression eExp = (Expression) element;
                ExpressionValue value = eExp.getValue();
                Expression[] eChildren = value.getChildren();
                if (eChildren == null)
                    return false;
                return true;
            }
            return false;
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {     	
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    public PHPStackView() {
        super();
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.ui.AbstractDebugView#createViewer(org.eclipse.swt.widgets.Composite)
     */
    protected Viewer createViewer(Composite parent) {
        TreeViewer viewer = new TreeViewer(parent);
        viewer.setLabelProvider(new PHPStackLabelProvider());
        viewer.setContentProvider(new StackViewContentProvider());
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW, this);
        getSite().setSelectionProvider(viewer);
        return viewer;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.ui.AbstractDebugView#getHelpContextId()
     */
    protected String getHelpContextId() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.ui.AbstractDebugView#configureToolBar(org.eclipse.jface.action.IToolBarManager)
     */
    protected void configureToolBar(IToolBarManager tbm) {

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#dispose()
     */
    public void dispose() {
        getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW, this);
        fTarget = null;
        super.dispose();
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        update();
    }

    /**
     * Updates the view for the selected target (if suspended)
     */
    private synchronized void update() {
        IAdaptable adaptable = DebugUITools.getDebugContext();
        fTarget = null;
        IDebugElement element = null;
        if (adaptable != null) {
            element = (IDebugElement) adaptable.getAdapter(IDebugElement.class);
            if (element != null) {
                if (element.getModelIdentifier().equals(IPHPConstants.ID_PHP_DEBUG_CORE)) {
                    fTarget = (PHPDebugTarget) element.getDebugTarget();
                }
            }
        }
        Object input = null;
        if (fTarget != null && fTarget.isSuspended()) {
            input = element;
        }
        getViewer().setInput(input);
        getViewer().refresh();
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.ui.AbstractDebugView#createActions()
     */
    protected void createActions() {

    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.ui.AbstractDebugView#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    protected void fillContextMenu(IMenuManager menu) {
        menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    public void updateObjects() {
        super.updateObjects();
        update();

    }
}
