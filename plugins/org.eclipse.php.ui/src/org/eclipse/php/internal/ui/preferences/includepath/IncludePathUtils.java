package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.ui.PHPUIMessages;
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
	 * Prompts the user with a message dialog asking if he would like to remove the selected entrie(s) 
	 * also from the build path
	 * @param shell
	 * @return the user choice (yes/no)
	 */
	public static boolean openRemoveFromBuildPathDialog(Shell shell){
		
		if (shell == null || fPrompting) {
			return false;
		}
		fPrompting = true;
		// Activate the shell if necessary so the prompt is visible
		if (shell.getMinimized()) {
			shell.setMinimized(false);
		}

		boolean answer = MessageDialog.openQuestion(shell, PHPUIMessages.getString("IncludePath.RemoveEntryTitle"), PHPUIMessages.getString("IncludePath.RemoveEntryMessage")); //$NON-NLS-1$ ////$NON-NLS-2$
				
		synchronized (IncludePathUtils.class) {
			fPrompting = false;
			IncludePathUtils.class.notifyAll();
		}
		return answer;
	}
	
	/**
	 * Returns whether the given path is "under" the buildpath definitions
	 * Meaning if one of the entries in the build path has the same path or contains this resource
	 * @param project
	 * @param resourcePath
	 * @return
	 */
	public static boolean isContainedInBuildpath(IPath resourcePath, IScriptProject project) {
		
		boolean result = false;
		
		if(resourcePath == null){
			return false;
		}
		
				
		IBuildpathEntry[] buildpath = null;
		try {
			buildpath = project.getRawBuildpath();
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			return false;
		}	
		

		// go over the build path entries and for each one of the "sources"
		// check if they are the same as the given include path entry or if they contain it 
		for (IBuildpathEntry buildpathEntry : buildpath) {
			if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_SOURCE){
				IPath buildPathEntryPath = buildpathEntry.getPath();
				if (resourcePath.isPrefixOf(buildPathEntryPath) || resourcePath.toString().equals(buildPathEntryPath.toString())){
					result = true;
				}
			}
		}
		
		return result;
	}

}
