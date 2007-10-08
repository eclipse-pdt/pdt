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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.debug.core.model.DebugOutput;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.wst.html.core.internal.encoding.HTMLDocumentLoader;
import org.eclipse.wst.html.core.internal.provisional.contenttype.ContentTypeIdForHTML;
import org.eclipse.wst.html.core.internal.text.StructuredTextPartitionerForHTML;
import org.eclipse.wst.html.ui.StructuredTextViewerConfigurationHTML;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocument;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.xml.core.internal.parser.XMLStructuredDocumentReParser;

/**
 * View of the PHP parameter stack 
 */
public class DebugOutputView extends AbstractDebugView implements ISelectionListener {

    private PHPDebugTarget fTarget;
    private int fUpdateCount;
    private IDebugEventSetListener terminateListener;
    private DebugViewHelper debugViewHelper;

    public DebugOutputView() {
        super();
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.ui.AbstractDebugView#createViewer(org.eclipse.swt.widgets.Composite)
     */
    protected Viewer createViewer(Composite parent) {
        int styles= SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;
        StructuredTextViewer fSourceViewer= new StructuredTextViewer(parent, null, null, false, styles);
        StructuredTextViewerConfigurationHTML config = new StructuredTextViewerConfigurationHTML();             
        fSourceViewer.configure(config);
        fSourceViewer.setEditable(false);
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW, this);
        getSite().setSelectionProvider(fSourceViewer.getSelectionProvider());

        terminateListener = new IDebugEventSetListener() {
        	PHPDebugTarget target;
			public void handleDebugEvents(DebugEvent[] events) {
				if (events != null) {
					int size = events.length;
					for (int i = 0; i < size; i++) {
						Object obj = events[i].getSource();
						
						if(!(obj instanceof PHPDebugTarget))
							continue;
					
						if ( events[i].getKind() == DebugEvent.TERMINATE) {
							target = (PHPDebugTarget)obj;
							Job job = new UIJob("debug output") {
								public IStatus runInUIThread(IProgressMonitor monitor) {
									update(target);
									return Status.OK_STATUS;
								}
							};
							job.schedule();
						}
					}
				}
			}
		};
		DebugPlugin.getDefault().addDebugEventListener(terminateListener);
     
		debugViewHelper = new DebugViewHelper();
		
        return fSourceViewer;
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
        DebugPlugin.getDefault().removeDebugEventListener(terminateListener);
        //       fTarget = null;
        super.dispose();
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
 
    	PHPDebugTarget target = debugViewHelper.getSelectionElement(selection);
        update(target);
    }
    
    private synchronized void update(PHPDebugTarget target) {
        PHPDebugTarget oldTarget = fTarget;
        int oldcount = fUpdateCount;
        fTarget = target;
        HTMLDocumentLoader ss = new HTMLDocumentLoader();
        BasicStructuredDocument dd = (BasicStructuredDocument)ss.createNewStructuredDocument();
        Object input = dd;
        if (fTarget != null) {
        	if ((fTarget.isSuspended()) || (fTarget.isTerminated())) {
	        	DebugOutput outputBuffer = fTarget.getOutputBufffer();
	        	fUpdateCount = outputBuffer.getUpdateCount();
	 
	        	// check if output hasn't been updated
	        	if (fTarget == oldTarget && fUpdateCount == oldcount) return;
	        	
	            ss = new HTMLDocumentLoader();
	            dd = (BasicStructuredDocument)ss.createNewStructuredDocument();
	            IModelManager mmanager = StructuredModelManager.getModelManager();
	            dd.setParser(mmanager.createStructuredDocumentFor(ContentTypeIdForHTML.ContentTypeID_HTML).getParser()); 
	            String output = outputBuffer.toString();
	            dd.setText(this, output);
	            try {
	                dd.computePartitioning(0,output.length());
	            } catch (BadLocationException e) {
	                Logger.logException("DebugOutputView: ", e);
	            }
	            dd.setDocumentPartitioner(new StructuredTextPartitionerForHTML());
	            dd.setReParser(new XMLStructuredDocumentReParser());
	            dd.reparse(this);
	            input = dd;
        	} else {
        		// Not Suspended or Terminated
        		
        		//the following is a fix for bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=205688
        		//if the target is not suspended or terminated fTarget should get back its old value
        		//so that in the next time the function is called it will not consider this target
        		//as it was already set to the view
        		fTarget = oldTarget;
        		return;
        	}
        }
        StructuredTextViewer viewer = (StructuredTextViewer)getViewer();
        StyledText control = (StyledText)viewer.getControl();
        int top = 0;
        try {
        	top = control.getTopIndex();
        } catch (Exception e1){
            Logger.logException("DebugOutputView: ", e1);
        }
        viewer.setInput(input);
        viewer.refresh();
        control.setTopIndex(top);
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
//        update();

    }

    protected void becomesVisible() {
        super.becomesVisible();
        PHPDebugTarget target = debugViewHelper.getSelectionElement(null);
        update(target);
    }
    
    
}
