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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.swt.widgets.Shell;

/**
 * This class contains various utility methods used for the include path and the build path
 * functionality
 * @author Eden K., 2008
 *
 */
public class IncludePathUtils {

	public static boolean fPrompting = false;
	
	/**
	 * Prompts the user with a confirmation dialog asking for the given message 
	 * @param shell
	 * @return the user choice (yes/no)
	 */
	public static boolean openConfirmationDialog(Shell shell, String title, String message){
		
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
	
	
	public static IPath getRelativeLocationFromIncludePath(IScriptProject project, IPath path){
		if(null != IncludePathManager.isInIncludePath(project.getProject(), path)){
			IBuildpathEntry[] buildpath = null;
			try {
				buildpath = project.getRawBuildpath();
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
				return null;
			}	

			// go over the build path entries and for each one of the "sources"
			// check if they are the same as the given include path entry or if they contain it 
			for (IBuildpathEntry buildpathEntry : buildpath) {
				if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_SOURCE){
					IPath buildPathEntryPath = buildpathEntry.getPath();
					if (buildPathEntryPath.isPrefixOf(path)) {// || path.toString().equals(buildPathEntryPath.toString())){
						return path.makeRelativeTo(buildPathEntryPath);
					}
				}
			}
		}
		return null;
		
	}
	
	public static IPath getRelativeLocationFromIncludePath(IScriptProject project, IModelElement modelElement){
		return getRelativeLocationFromIncludePath(project, modelElement.getPath());
	}
}
