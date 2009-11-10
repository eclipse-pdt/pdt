/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.phar.wizard;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IArchive;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.internal.core.ZipArchiveFile;
import org.eclipse.dltk.internal.core.util.Util;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.ui.IDLTKStatusConstants;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.phar.PharArchiveFile;
import org.eclipse.php.internal.core.tar.TarArchiveFile;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Helper methods for various UI tasks
 * 
 */
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

	public static boolean isInvalidPharBuildEntry(BPListElement cpentry) {
		IBuildpathEntry entry = cpentry.getBuildpathEntry();
		return isInvalidPharBuildEntry(entry);

	}

	public static boolean isInvalidPharBuildEntry(IBuildpathEntry entry) {
		if (entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY
				&& PHPToolkitUtil.isPharFileName(entry.getPath().toString())) {
			try {
				File pharFile = null;
				IPath path = entry.getPath();
				String extension = path.getFileExtension();
				if (EnvironmentPathUtils.isFull(path)) {
					path = EnvironmentPathUtils.getLocalPath(path);
					pharFile = path.toFile();
				} else {
					IWorkspaceRoot root = ResourcesPlugin.getWorkspace()
							.getRoot();
					IResource file = root.findMember(path);
					pharFile = Util
							.toLocalFile(file.getLocationURI(), null/*
																	 * no
																	 * progress
																	 * availaible
																	 */);
				}

				if (pharFile == null || !pharFile.exists())
					return true;
				IArchive archive = null;
				if (PHPToolkitUtil.PHAR_EXTENSTION.equals(extension)) {
					archive = new PharArchiveFile(pharFile);
				} else if (PHPToolkitUtil.ZIP_EXTENSTION.equals(extension)) {
					archive = new ZipArchiveFile(pharFile);
				} else if (PHPToolkitUtil.TAR_EXTENSTION.equals(extension)
						|| PHPToolkitUtil.GZ_EXTENSTION.equals(extension)
						|| PHPToolkitUtil.BZ2_EXTENSTION.equals(extension)) {

					archive = new TarArchiveFile(pharFile);
				}
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}

}
