package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IBuildpathEntry;
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
	


}
