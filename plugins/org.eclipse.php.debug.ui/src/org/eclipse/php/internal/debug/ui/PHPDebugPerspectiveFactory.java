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


import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

/**
 * The debug perspective factory.
 */
public class PHPDebugPerspectiveFactory implements IPerspectiveFactory {

    public static final String ID_NAVIGATOR_FOLDER_VIEW= "org.eclipse.php.debug.ui.NavigatorFolderView"; //$NON-NLS-1$
    public static final String ID_TOOLS_FOLDER_VIEW= "org.eclipse.php.debug.ui.ToolsFolderView"; //$NON-NLS-1$
    public static final String ID_CONSOLE_FOLDER_VIEW= "org.eclipse.php.debug.ui.ConsoleFolderView"; //$NON-NLS-1$
    public static final String ID_Debug_INFO_FOLDER = "org.eclipse.php.debug.ui.debugInfoFolder"; //$NON-NLS-1$
    public static final String ID_PHPDebugOutput = "org.eclipse.debug.ui.PHPDebugOutput"; //$NON-NLS-1$
    public static final String ID_PHPBrowserOutput = "org.eclipse.debug.ui.PHPBrowserOutput"; //$NON-NLS-1$
    
    
    public static final String PERSPECTIVE_ID = "org.eclipse.php.debug.ui.PHPDebugPerspective"; //$NON-NLS-1$

	/**
	 * @see IPerspectiveFactory#createInitialLayout(IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout) {

        IFolderLayout outlineFolder= layout.createFolder(ID_Debug_INFO_FOLDER, IPageLayout.RIGHT, (float) 0.75, layout.getEditorArea());
        outlineFolder.addView(ID_PHPDebugOutput);
        outlineFolder.addView(ID_PHPBrowserOutput);
        
		IFolderLayout consoleFolder = layout.createFolder(ID_CONSOLE_FOLDER_VIEW, IPageLayout.BOTTOM, (float)0.75, layout.getEditorArea());
		consoleFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		consoleFolder.addView(IPageLayout.ID_TASK_LIST);
		consoleFolder.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		consoleFolder.addPlaceholder(IPageLayout.ID_PROP_SHEET);
		
		IFolderLayout navFolder= layout.createFolder(ID_NAVIGATOR_FOLDER_VIEW, IPageLayout.TOP, (float) 0.45, layout.getEditorArea());
		navFolder.addView(IDebugUIConstants.ID_DEBUG_VIEW);
		navFolder.addPlaceholder(IPageLayout.ID_RES_NAV);
		
		IFolderLayout toolsFolder= layout.createFolder(ID_TOOLS_FOLDER_VIEW, IPageLayout.RIGHT, (float) 0.50, ID_NAVIGATOR_FOLDER_VIEW);
		toolsFolder.addView(IDebugUIConstants.ID_VARIABLE_VIEW);
		toolsFolder.addView(IDebugUIConstants.ID_BREAKPOINT_VIEW);
        toolsFolder.addPlaceholder("org.eclipse.debug.ui.PHPStackView");
		toolsFolder.addPlaceholder(IDebugUIConstants.ID_EXPRESSION_VIEW);
		toolsFolder.addPlaceholder(IDebugUIConstants.ID_REGISTER_VIEW);
		
//		IFolderLayout outlineFolder= layout.createFolder(ID_OUTLINE_FOLDER_VIEW, IPageLayout.RIGHT, (float) 0.75, layout.getEditorArea());
//		outlineFolder.addView(IPageLayout.ID_OUTLINE);
		
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
		
		layout.addPerspectiveShortcut("org.eclipse.php.perspective"); //$NON-NLS-N$
		layout.addPerspectiveShortcut(PERSPECTIVE_ID); //$NON-NLS-N$
				
		layout.addShowViewShortcut(ID_PHPDebugOutput);
		layout.addShowViewShortcut(ID_PHPBrowserOutput);
		layout.addShowViewShortcut("org.eclipse.debug.ui.PHPStackView"); //$NON-NLS-N$
		
		setContentsOfShowViewMenu(layout);
	}
	
	/** 
	 * Sets the intial contents of the "Show View" menu.
	 */
	protected void setContentsOfShowViewMenu(IPageLayout layout) {
		layout.addShowViewShortcut(IDebugUIConstants.ID_DEBUG_VIEW);
		layout.addShowViewShortcut(IDebugUIConstants.ID_VARIABLE_VIEW);
		layout.addShowViewShortcut(IDebugUIConstants.ID_BREAKPOINT_VIEW);
		layout.addShowViewShortcut(IDebugUIConstants.ID_EXPRESSION_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
	}
}
