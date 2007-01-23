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
package org.eclipse.php.internal.debug.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.PHPDebugTarget;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PHPDebugUIPlugin extends AbstractUIPlugin {

    //The shared instance.
    private static PHPDebugUIPlugin plugin;

	private ImageDescriptorRegistry fImageDescriptorRegistry;

    public static final String ID="org.eclipse.php.debug.ui";
	public static final int INTERNAL_ERROR = 10001;
    private ShowViewListener showViewListener;
    /**
     * The constructor.
     */
    public PHPDebugUIPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        showViewListener = new ShowViewListener();
        DebugPlugin.getDefault().addDebugEventListener(showViewListener);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        if (showViewListener != null) {
        	DebugPlugin.getDefault().removeDebugEventListener(showViewListener);
        }
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static PHPDebugUIPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the PHP debug ID.
     */
    public static String getID() {
        return ID;
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path.
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.php.debug.ui", path);
    }

    /**
	 * Returns the image descriptor registry used for this plugin.
	 */
	public static ImageDescriptorRegistry getImageDescriptorRegistry() {
		if (getDefault().fImageDescriptorRegistry == null) {
			getDefault().fImageDescriptorRegistry = new ImageDescriptorRegistry();
		}
		return getDefault().fImageDescriptorRegistry;
	}
	
    /**
     * Returns the active workbench window
     * 
     * @return the active workbench window
     */
    public static IWorkbenchWindow getActiveWorkbenchWindow() {
        return getDefault().getWorkbench().getActiveWorkbenchWindow();
    }

    public static IWorkbenchPage getActivePage() {
        IWorkbenchWindow w = getActiveWorkbenchWindow();
        if (w != null) {
            return w.getActivePage();
        }
        return null;
    }

    /**
     * Returns the active workbench shell or <code>null</code> if none
     * 
     * @return the active workbench shell or <code>null</code> if none
     */
    public static Shell getActiveWorkbenchShell() {
        IWorkbenchWindow window = getActiveWorkbenchWindow();
        if (window != null) {
            return window.getShell();
        }
        return null;
    }
    
	public static Display getStandardDisplay() {
		Display display;
		display= Display.getCurrent();
		if (display == null)
			display= Display.getDefault();
		return display;		
	}
	
    public static void showView(String viewID) {
        IWorkbenchWindow window = getDefault().getWorkbench().getActiveWorkbenchWindow();
        if (window != null){
            IWorkbenchPage page = window.getActivePage();
            IPerspectiveDescriptor descriptor= page.getPerspective();
            if (descriptor.getId().indexOf("php") != -1){
                if (page != null) {
                    IViewPart part = page.findView(viewID);
                    if (part == null) {               
                        try {
                            page.showView(viewID);
                        } catch (PartInitException e) {
                            ErrorDialog.openError(window.getShell(), PHPDebugUIMessages.ShowView_errorTitle, //$NON-NLS-1$
                                    e.getMessage(), e.getStatus());
                        }
                    } else {
//                    page.bringToTop(part);
                    }
                }
            }
        }
    }
    
    public static void log(IStatus status) {
		getDefault().getLog().log(status);
	} 
	
	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, "PHP ui plugin internal error", e)); //$NON-NLS-1$
	}
    
    public static IProject getProject(String projectName){
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = workspaceRoot.getProjects();
        IProject project = null;
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getName().equals(projectName)) {
                project = projects[i];
                break;
            }
        }
        return project;
    }
    
    private class ShowViewListener implements IDebugEventSetListener {
        public void handleDebugEvents(DebugEvent[] events) {
            if (events != null) {
                int size = events.length;
                for (int i = 0; i < size; i++) {
                    DebugEvent event = (DebugEvent)events[i];
                    int kind = event.getKind();
                    if (event.getKind() == DebugEvent.CREATE) {
                        Object obj = events[i].getSource();
                    
                        if(!(obj instanceof PHPDebugTarget))
                            continue;
                        if (PHPDebugPlugin.getOpenDebugViewsOption()){
                            Job job = new org.eclipse.ui.progress.UIJob("debug output") {
                                public IStatus runInUIThread(IProgressMonitor monitor) {
                                    showView(PHPDebugPerspectiveFactory.ID_PHPBrowserOutput);
                                    showView(PHPDebugPerspectiveFactory.ID_PHPDebugOutput);
                                    showView(IConsoleConstants.ID_CONSOLE_VIEW);           
                                    return Status.OK_STATUS;
                                }
                            };
                            job.schedule();  
                        }
                    }
                    if (event.getKind() == DebugEvent.MODEL_SPECIFIC){
                        Object obj = events[i].getSource();
                        
                        if(!(obj instanceof PHPDebugTarget))
                            continue;
                        final Object data = events[i].getData();
                        if (data instanceof IStatus){
                            Job job = new org.eclipse.ui.progress.UIJob("debug output") {
                                public IStatus runInUIThread(IProgressMonitor monitor) {
                                       IStatus status = (IStatus)data;
                                       Shell shell = getActiveWorkbenchShell();
                                       ErrorDialog.openError(shell, null, null, status);
                                       return Status.OK_STATUS;
                                }
                            };
                            job.schedule();
                        }
                        
                    }
                }
            }
        }
    };

}
