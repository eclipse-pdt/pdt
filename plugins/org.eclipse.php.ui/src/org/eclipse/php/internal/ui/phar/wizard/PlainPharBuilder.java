package org.eclipse.php.internal.ui.phar.wizard;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.core.phar.*;
import org.eclipse.swt.widgets.Shell;

public class PlainPharBuilder extends PharBuilder {

	public static final String BUILDER_ID = "org.eclipse.php.ui.plain_phar_builder"; //$NON-NLS-1$

	private PharPackage fJarPackage;
	// private PharWriter3 fJarWriter;
	IFileExporter fileExporter;

	/**
	 * {@inheritDoc}
	 */
	public String getId() {
		return BUILDER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	public IStubProvider getStubProvider() {
		return new StubProvider();
	}

	/**
	 * {@inheritDoc}
	 */
	public void open(PharPackage jarPackage, Shell displayShell,
			MultiStatus statusMsg) throws CoreException {
		super.open(jarPackage, displayShell, statusMsg);
		fJarPackage = jarPackage;
		Assert.isTrue(fJarPackage.isValid(),
				"The PHAR package specification is invalid"); //$NON-NLS-1$
		if (!canCreateJar(displayShell))
			throw new OperationCanceledException();

		try {
			fileExporter = PharExportHelper.createFileExporter(fJarPackage);
		} catch (IOException ex) {
			throw PharUIUtil.createCoreException(ex.getLocalizedMessage(), ex);
		}
		// fJarWriter= new PharWriter3(fJarPackage, displayShell);
	}

	/**
	 * Checks if the JAR file can be overwritten. If the JAR package setting
	 * does not allow to overwrite the JAR then a dialog will ask the user
	 * again.
	 * 
	 * @param parent
	 *            the parent for the dialog, or <code>null</code> if no dialog
	 *            should be presented
	 * @return <code>true</code> if it is OK to create the JAR
	 */
	protected boolean canCreateJar(Shell parent) {
		File file = fJarPackage.getAbsolutePharLocation().toFile();
		if (file.exists()) {
			if (!file.canWrite())
				return false;
			if (fJarPackage.allowOverwrite())
				return true;
			return parent != null
					&& PharUIUtil.askForOverwritePermission(parent, fJarPackage
							.getAbsolutePharLocation(), true);
		}

		// Test if directory exists
		String path = file.getAbsolutePath();
		int separatorIndex = path.lastIndexOf(File.separator);
		if (separatorIndex == -1) // i.e.- default directory, which is fine
			return true;
		File directory = new File(path.substring(0, separatorIndex));
		if (!directory.exists()) {
			if (PharUIUtil.askToCreateDirectory(parent, directory))
				return directory.mkdirs();
			else
				return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeFile(IFile resource, IPath destinationPath)
			throws CoreException {
		try {
			fileExporter.write(resource, destinationPath.toString());
		} catch (IOException ex) {
			throw PharUIUtil.createCoreException(ex.getLocalizedMessage(), ex);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	public void writeStub(IStub stub, IProgressMonitor progressMonitor)
			throws CoreException {
		try {
			fileExporter.writeStub(stub);
		} catch (IOException ex) {
			throw PharUIUtil.createCoreException(ex.getLocalizedMessage(), ex);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void close() throws CoreException {
		if (fileExporter != null) {
			try {

				fileExporter.finished();
			} catch (IOException ex) {
				throw PharUIUtil
						.createCoreException(ex.getLocalizedMessage(), ex);
			}
		}
	}

	public void writeSignature(IProgressMonitor monitor) throws CoreException {
		if (fileExporter != null) {
			try {

				// if (fJarPackage.isUseSignature()) {
				fileExporter.writeSignature();
				// }
			} catch (IOException ex) {
				throw PharUIUtil
						.createCoreException(ex.getLocalizedMessage(), ex);
			}
		}

	}

	public void writeFile(IFolder resource, IPath destinationPath)
			throws CoreException {
		try {
			fileExporter.write(resource, destinationPath.toString());
		} catch (IOException ex) {
			throw PharUIUtil.createCoreException(ex.getLocalizedMessage(), ex);
		}
	}

}
