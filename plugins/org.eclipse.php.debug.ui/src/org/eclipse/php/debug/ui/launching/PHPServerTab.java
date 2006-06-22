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
package org.eclipse.php.debug.ui.launching;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.server.ui.ServerTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class PHPServerTab extends ServerTab {

	public static final String RUN_WITH_DEBUG = "run_with_debug";

	protected Button runWithDebugger;
	protected boolean isChecked = false;

	public PHPServerTab() {
		super();
	}

	public void createExtensionControls(Composite parent) {
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(data);

		runWithDebugger = new Button(composite, SWT.CHECK);
		runWithDebugger.setText(PHPDebugUIMessages.PHPexe_Run_With_Debug_Info);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		runWithDebugger.setLayoutData(gd);

		runWithDebugger.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				isChecked = b.getSelection();
				updateLaunchConfigurationDialog();
			}
		});
	}

	protected void initializeExtensionControls(ILaunchConfiguration configuration) {
		try {
			boolean checked = configuration.getAttribute(RUN_WITH_DEBUG, PHPDebugPlugin.getDebugInfoOption());
			runWithDebugger.setSelection(checked);
		} catch (Exception e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
	}

	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		boolean checked = runWithDebugger.getSelection();
		configuration.setAttribute(RUN_WITH_DEBUG, checked);
	}

	protected boolean isValidExtension(ILaunchConfiguration launchConfig) {
		return true;
	}
    
    protected void createServerSelectionControl(Composite parent) {
        PHPDebugPlugin.createDefaultPHPServer();
        super.createServerSelectionControl(parent);
    }
    
    protected void initializeURLControl(String projectName, String contextRoot, String fileName)
    {
        if(server == null)
            return;
        
        if (server.getName().equals(IPHPConstants.Default_Server_Name)) { // TODO - Use ID instead ?
            IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
            IProject[] projects = workspaceRoot.getProjects();
            IProject project = null;
            for (int i = 0; i < projects.length; i++) {
                if (projects[i].getName().equals(projectName)) {
                    project = projects[i];
                    break;
                }
            }
            String urlString = "";
            if (project == null){
                urlString = PHPDebugPlugin.getWorkspaceURL();
            } else {
                urlString = PHPProjectPreferences.getDefaultServerURL(project);
            }
            
            
            if(urlString.equals(""))
                urlString = "http://localhost";
            
            StringBuffer url = new StringBuffer(urlString);
            
            url.append("/");
            url.append(contextRoot);
            if (contextRoot != "")url.append("/");
            url.append(fileName);
            
            fURL.setText(url.toString());
        } else {
            super.initializeURLControl(contextRoot, fileName);
        }
        

    }
    
    public String[] getRequiredNatures()
    {
    	return LaunchUtil.getRequiredNatures();
    }
    
    public String[] getFileExtensions()
    {
    	return LaunchUtil.getFileExtensions();
    }
}
