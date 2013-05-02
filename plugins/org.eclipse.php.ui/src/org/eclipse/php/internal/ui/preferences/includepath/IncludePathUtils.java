/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.swt.widgets.Shell;

/**
 * This class contains various utility methods used for the include path and the
 * build path functionality
 * 
 * @author Eden K., 2008
 * 
 */
public class IncludePathUtils {

	public static boolean fPrompting = false;

	/**
	 * Prompts the user with a confirmation dialog asking for the given message
	 * 
	 * @param shell
	 * @return the user choice (yes/no)
	 */
	public static boolean openConfirmationDialog(Shell shell, String title,
			String message) {

		if (shell == null || fPrompting) {
			return false;
		}
		fPrompting = true;
		// Activate the shell if necessary so the prompt is visible
		if (shell.getMinimized()) {
			shell.setMinimized(false);
		}

		boolean answer = MessageDialog.openQuestion(shell, title, message);

		synchronized (IncludePathUtils.class) {
			fPrompting = false;
			IncludePathUtils.class.notifyAll();
		}
		return answer;
	}

	private static IPath getRelativeLocationFromIncludePath(
			IScriptProject project, IPath path) {
		final IPath inIncludePath = IncludePathManager.isInIncludePath(project
				.getProject(), path);
		if (inIncludePath != null) {
			return path.makeRelativeTo(inIncludePath);
		}
		// else return absolute
		return Path.EMPTY;
	}

	/**
	 * Resolves the model element entry include path relative to the project
	 * include path configuration.
	 * 
	 * @param project
	 * @param modelElement
	 * @return the resolved include path or {@link Path#EMPTY} if not found
	 */
	public static IPath getRelativeLocationFromIncludePath(
			IScriptProject project, IModelElement modelElement) {
		// workspace resource
		if (modelElement.getResource() != null) {
			return getRelativeLocationFromIncludePath(project, modelElement
					.getPath());
		}

		// built in element
		final IProjectFragment projectFragment = ScriptModelUtil
				.getProjectFragment(modelElement);
		final IScriptProject elementProject = modelElement.getScriptProject();
		if (elementProject != null && !elementProject.equals(project)
				&& !projectFragment.isExternal()) {
			// TODO add project dependency
			return Path.EMPTY;
		}

		// library element
		ISourceModule sourceModule = (ISourceModule) modelElement
				.getAncestor(IModelElement.SOURCE_MODULE);
		IScriptFolder folder = (IScriptFolder) modelElement
				.getAncestor(IModelElement.SCRIPT_FOLDER);
		if (sourceModule != null && folder != null) {
			String folderElementName = folder.getElementName();
			StringBuilder sb = new StringBuilder();
			if (folderElementName.length() != 0) {
				sb.append(folderElementName).append("/"); //$NON-NLS-1$
			}
			sb.append(sourceModule.getElementName());
			return new Path(sb.toString());
		}
		return Path.EMPTY;
	}
}
