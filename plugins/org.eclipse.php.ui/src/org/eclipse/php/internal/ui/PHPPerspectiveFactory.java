/*******************************************************************************
 * Copyright (c) 2009, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

/**
 * 
 * This class sets the Layout for the Rich Client Perspective. The perspective
 * will contain an editor area, property page, outline view, resource navigator,
 * and palette.
 */
public class PHPPerspectiveFactory implements IPerspectiveFactory {

	private static final String TOP_LEFT_LOCATION = "topLeft"; //$NON-NLS-1$
	public static final String TOP_RIGHT_LOCATION = "right"; //$NON-NLS-1$
	private static final String BOTTOM_LOCATION = "bottom"; //$NON-NLS-1$

	public static final String PERSPECTIVE_ID = "org.eclipse.php.perspective"; //$NON-NLS-1$

	// other view id's
	private static final String ID_EXPLORER = "org.eclipse.php.ui.explorer"; //$NON-NLS-1$
	private static final String ID_TYPEHIERARCHY = "org.eclipse.dltk.ui.TypeHierarchy"; //$NON-NLS-1$
	private static final String ID_FUNCTIONS = "org.eclipse.php.ui.functions"; //$NON-NLS-1$
	private static final String ID_PROJECT_OUTLINE = "org.eclipse.php.ui.projectOutline"; //$NON-NLS-1$
	public static final String ID_MVC = "org.zend.php.framework.ui.views.mvc"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_DEBUG_UI_DEBUG_PERSPECTIVE = "org.eclipse.debug.ui.DebugPerspective"; //$	 //$NON-NLS-1$

	public void createInitialLayout(IPageLayout layout) {

		// Adding the default views for the perspective
		addViews(layout);

		layout.addPerspectiveShortcut(PERSPECTIVE_ID);
		layout.addPerspectiveShortcut(ORG_ECLIPSE_DEBUG_UI_DEBUG_PERSPECTIVE);

		layout.addShowViewShortcut(ID_EXPLORER);
		// layout.addShowViewShortcut(ID_TYPEHIERARCHY);
		layout.addShowViewShortcut(ID_FUNCTIONS);
		layout.addShowViewShortcut(ID_PROJECT_OUTLINE);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);

		layout.addShowInPart(ID_EXPLORER);
	}

	/*
	 * This method add the default views that are part of the perspective and
	 * lays them out in relation to each other and the editor area.
	 */
	protected void addViews(IPageLayout layout) {

		String editorArea = layout.getEditorArea();

		// Everything is based off the editor area

		// Top left: Resource Navigator view and PHP Explorer
		IFolderLayout topLeft = layout.createFolder(TOP_LEFT_LOCATION,
				IPageLayout.LEFT, 0.22f, editorArea);
		topLeft.addView(ID_EXPLORER);
		// topLeft.addView(ID_TYPEHIERARCHY);

		// Bottom: Attributes view, Problem View, Task List, placeholder for
		// Design View Log
		IFolderLayout bottom = layout.createFolder(BOTTOM_LOCATION,
				IPageLayout.BOTTOM, 0.75f, editorArea);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		// bottom.addView(IPageLayout.ID_TASK_LIST);
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		bottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);

		IFolderLayout outlineFolder = layout.createFolder(TOP_RIGHT_LOCATION,
				IPageLayout.RIGHT, (float) 0.75, editorArea); 
		outlineFolder.addView(IPageLayout.ID_OUTLINE);
		outlineFolder.addPlaceholder(ID_PROJECT_OUTLINE);
		outlineFolder.addPlaceholder(ID_MVC);
		outlineFolder.addPlaceholder(ID_FUNCTIONS);
	}
}
