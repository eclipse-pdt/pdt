/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

/**

 * This class sets the Layout for the Rich Client Perspective. The perspective will
 * contain an editor area, property page, outline view, resource navigator, and palette.
 */
public class PHPPerspectiveFactory implements IPerspectiveFactory {

	static final String TOP_LEFT_LOCATION = "topLeft"; //$NON-NLS-1$
	static final String BOTTOM_LEFT_LOCATION = "bottomLeft"; //$NON-NLS-1$
	static final String TOP_RIGHT_LOCATION = "topRight"; //$NON-NLS-1$
	static final String BOTTOM_LOCATION = "bottom"; //$NON-NLS-1$

	static final String PERSPECTIVE_ID = "org.eclipse.php.perspective"; //$NON-NLS-1$

	//other view id's
	static final String ID_EXPLORER = "org.eclipse.php.ui.explorer"; //$NON-NLS-1$
	static final String ID_FUNCTIONS = "org.eclipse.php.ui.functions"; //$NON-NLS-1$
	static final String ID_PROJECT_OUTLINE = "org.eclipse.php.ui.projectOutline"; //$NON-NLS-1$
	static final String ID_Debug_INFO_FOLDER = "org.eclipse.php.debug.ui.debugInfoFolder"; //$NON-NLS-1$
	static final String ID_PHPDebugOutput = "org.eclipse.debug.ui.PHPDebugOutput"; //$NON-NLS-1$
	static final String ID_PHPBrowserOutput = "org.eclipse.debug.ui.PHPBrowserOutput"; //$NON-NLS-1$

	public void createInitialLayout(IPageLayout layout) {

		//Adding the default views for the perspective
		addViews(layout);

		layout.addPerspectiveShortcut(PERSPECTIVE_ID); 
		layout.addPerspectiveShortcut("org.eclipse.php.debug.ui.PHPDebugPerspective");  //$NON-NLS-1$

		layout.addShowViewShortcut(ID_EXPLORER);
		layout.addShowViewShortcut(ID_FUNCTIONS);
		layout.addShowViewShortcut(ID_PROJECT_OUTLINE);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		
		layout.addShowInPart(ID_EXPLORER);

	}

	/*
	 * This method add the default views that are part of the perspective and lays them
	 * out in relation to each other and the editor area.
	 */
	protected void addViews(IPageLayout layout) {

		String editorArea = layout.getEditorArea();

		//	Everything is based off the editor area

		// remove debug views from PHP prespective. bug #163653
		//IFolderLayout outlineFolder= layout.createFolder(ID_Debug_INFO_FOLDER, IPageLayout.RIGHT, (float) 0.75, editorArea);
		//outlineFolder.addView(ID_PHPDebugOutput);
		//outlineFolder.addView(ID_PHPBrowserOutput);

		// Top left: Resource Navigator view and PHP Explorer
		IFolderLayout topLeft = layout.createFolder(TOP_LEFT_LOCATION, IPageLayout.LEFT, 0.22f, editorArea);
		topLeft.addView(ID_EXPLORER);

		// Bottom left: Outline view
		IFolderLayout bottomLeft = layout.createFolder(BOTTOM_LEFT_LOCATION, IPageLayout.BOTTOM, 0.50f, TOP_LEFT_LOCATION);
		bottomLeft.addView(IPageLayout.ID_OUTLINE);
		bottomLeft.addView(ID_PROJECT_OUTLINE);
		bottomLeft.addView(ID_FUNCTIONS);

		// Bottom: Attributes view, Problem View, Task List, placeholder for Design View Log
		IFolderLayout bottom = layout.createFolder(BOTTOM_LOCATION, IPageLayout.BOTTOM, 0.75f, editorArea);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView(IPageLayout.ID_TASK_LIST);
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		bottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);
	}
}
