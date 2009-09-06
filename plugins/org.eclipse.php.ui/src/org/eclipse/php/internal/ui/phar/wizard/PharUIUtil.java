package org.eclipse.php.internal.ui.phar.wizard;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.ui.IDLTKStatusConstants;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PharUIUtil {

	public static boolean askForOverwritePermission(final Shell parent,
			IPath filePath, boolean isOSPath) {
		if (parent == null)
			return false;
		return queryDialog(parent,
				PharPackagerMessages.JarPackage_confirmReplace_title,
				Messages.format(
						PharPackagerMessages.JarPackage_confirmReplace_message,
						BasicElementLabels.getPathLabel(filePath, isOSPath)));
	}

	private static boolean queryDialog(final Shell parent, final String title,
			final String message) {
		Display display = parent.getDisplay();
		if (display == null || display.isDisposed())
			return false;
		final boolean[] returnValue = new boolean[1];
		Runnable runnable = new Runnable() {
			public void run() {
				returnValue[0] = MessageDialog.openQuestion(parent, title,
						message);
			}
		};
		display.syncExec(runnable);
		return returnValue[0];
	}

	public static boolean askToCreateDirectory(final Shell parent,
			File directory) {
		if (parent == null)
			return false;
		return queryDialog(parent,
				PharPackagerMessages.JarPackage_confirmCreate_title,
				Messages.format(
						PharPackagerMessages.JarPackage_confirmCreate_message,
						BasicElementLabels.getPathLabel(directory)));
	}

	/**
	 * Creates a <code>CoreException</code> with the given parameters.
	 * 
	 * @param message
	 *            a string with the message
	 * @param ex
	 *            the exception to be wrapped or <code>null</code> if none
	 * @return a CoreException
	 */
	public static CoreException createCoreException(String message, Exception ex) {
		if (message == null)
			message = ""; //$NON-NLS-1$
		return new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID,
				IDLTKStatusConstants.INTERNAL_ERROR, message, ex));
	}

}
