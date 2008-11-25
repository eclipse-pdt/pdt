package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
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
				if (buildPathEntryPath.isPrefixOf(resourcePath) || resourcePath.toString().equals(buildPathEntryPath.toString())){
					result = true;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Returns whether the given path is in the include definitions
	 * Meaning if one of the entries in the include path has the same path of this resource
	 * @param project
	 * @param resourcePath
	 * @return
	 */
	public static boolean isInIncludePath(IProject project, IPath entryPath) {
		
		boolean result = false;
		
		if(entryPath == null){
			return false;
		}
					
		IncludePathManager includepathManager = IncludePathManager.getInstance();
		IncludePath[] includePathEntries = includepathManager.getIncludePaths(project);
		
		// go over the entries and compare the path.
		// checks if the path for one of the entries equals to the given one
		for (IncludePath entry : includePathEntries) {
			Object includePathEntry = entry.getEntry();
			IPath resourcePath = null;
			if (includePathEntry instanceof IBuildpathEntry) {
				IBuildpathEntry bpEntry = (IBuildpathEntry) includePathEntry;
				resourcePath = bpEntry.getPath();
			} else {
				IResource resource = (IResource) includePathEntry;
				resourcePath = resource.getFullPath();
			}
			
			if(resourcePath != null && resourcePath.toString().equals(entryPath.toString())){
				result = true;
				break;
			}			

		}
		return result;
	}


}
