package org.eclipse.php.internal.ui.phar.wizard;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.phar.PharException;
import org.eclipse.php.internal.core.phar.PharFile;
import org.eclipse.php.internal.core.tar.TarException;
import org.eclipse.php.internal.core.tar.TarFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;

public class ArchiveFileManipulations {

	/**
	 * Determine whether the file with the given filename is in .tar.gz or .tar
	 * format.
	 * 
	 * @param fileName
	 *            file to test
	 * @return true if the file is in tar format
	 */
	public static boolean isTarFile(String fileName) {
		if (fileName.length() == 0) {
			return false;
		}

		TarFile tarFile = null;
		try {
			tarFile = new TarFile(fileName);
		} catch (TarException tarException) {
			return false;
		} catch (IOException ioException) {
			return false;
		} finally {
			if (tarFile != null) {
				try {
					tarFile.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		return true;
	}

	/**
	 * Determine whether the file with the given filename is in .zip or .jar
	 * format.
	 * 
	 * @param fileName
	 *            file to test
	 * @return true if the file is in tar format
	 */
	public static boolean isZipFile(String fileName) {
		if (fileName.length() == 0) {
			return false;
		}

		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(fileName);
		} catch (IOException ioException) {
			return false;
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		return true;
	}

	public static boolean isPharFile(String fileName) {
		if (fileName.length() == 0) {
			return false;
		}

		PharFile pharFile = null;
		try {
			pharFile = new PharFile(new File(fileName));
		} catch (PharException e) {
			return false;
		} catch (IOException ioException) {
			return false;
		} finally {
			if (pharFile != null) {
				try {
					pharFile.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		return true;
	}

	/**
	 * Closes the given structure provider.
	 * 
	 * @param structureProvider
	 *            The structure provider to be closed, can be <code>null</code>
	 * @param shell
	 *            The shell to display any possible Dialogs in
	 */
	public static void closeStructureProvider(
			ILeveledImportStructureProvider structureProvider, Shell shell) {
		if (structureProvider instanceof ZipLeveledStructureProvider) {
			closeZipFile(((ZipLeveledStructureProvider) structureProvider)
					.getZipFile(), shell);
		}
		if (structureProvider instanceof TarLeveledStructureProvider) {
			closeTarFile(((TarLeveledStructureProvider) structureProvider)
					.getTarFile(), shell);
		}
		// TODO FOR PHAR
	}

	/**
	 * Attempts to close the passed zip file, and answers a boolean indicating
	 * success.
	 * 
	 * @param file
	 *            The zip file to attempt to close
	 * @param shell
	 *            The shell to display error dialogs in
	 * @return Returns true if the operation was successful
	 */
	public static boolean closeZipFile(ZipFile file, Shell shell) {
		try {
			file.close();
		} catch (IOException e) {
			displayErrorDialog(NLS.bind(
					DataTransferMessages.ZipImport_couldNotClose, file
							.getName()), shell);
			return false;
		}

		return true;
	}

	/**
	 * Attempts to close the passed tar file, and answers a boolean indicating
	 * success.
	 * 
	 * @param file
	 *            The tar file to attempt to close
	 * @param shell
	 *            The shell to display error dialogs in
	 * @return Returns true if the operation was successful
	 * @since 3.4
	 */
	public static boolean closeTarFile(TarFile file, Shell shell) {
		try {
			file.close();
		} catch (IOException e) {
			displayErrorDialog(NLS.bind(
					DataTransferMessages.ZipImport_couldNotClose, file
							.getName()), shell);
			return false;
		}

		return true;
	}

	/**
	 * Display an error dialog with the specified message.
	 * 
	 * @param message
	 *            the error message
	 */
	protected static void displayErrorDialog(String message, Shell shell) {
		MessageDialog.open(MessageDialog.ERROR, shell, getErrorDialogTitle(),
				message, SWT.SHEET);
	}

	/**
	 * Get the title for an error dialog. Subclasses should override.
	 */
	protected static String getErrorDialogTitle() {
		return IDEWorkbenchMessages.WizardExportPage_internalErrorTitle;
	}

	public static boolean closePharFile(PharFile file, Shell shell) {
		try {
			file.close();
		} catch (IOException e) {
			displayErrorDialog(NLS.bind(
					DataTransferMessages.ZipImport_couldNotClose, file
							.getName()), shell);
			return false;
		}

		return true;
	}
}
