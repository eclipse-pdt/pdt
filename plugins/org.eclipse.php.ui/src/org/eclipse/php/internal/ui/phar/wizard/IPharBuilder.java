package org.eclipse.php.internal.ui.phar.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.php.internal.core.phar.IStub;
import org.eclipse.php.internal.core.phar.IStubProvider;
import org.eclipse.php.internal.core.phar.PharPackage;
import org.eclipse.swt.widgets.Shell;

public interface IPharBuilder {

	/**
	 * Returns the unique id of this builder
	 * 
	 * @return the unique id of this builder
	 */
	public String getId();

	/**
	 * Returns the manifest provider to build the manifest
	 * 
	 * @return the manifest provider to build the manifest
	 */
	public IStubProvider getStubProvider();

	/**
	 * Called when building of the JAR starts
	 * 
	 * @param jarPackage
	 *            the package to build
	 * @param shell
	 *            shell to show dialogs, <b>null</b> if no dialog must be shown
	 * @param status
	 *            a status to use to report status to the user
	 * @throws CoreException
	 *             thrown when the JAR could not be opened
	 */
	public void open(PharPackage jarPackage, Shell shell, MultiStatus status)
			throws CoreException;

	/**
	 * Add the given resource to the archive at the given path
	 * 
	 * @param resource
	 *            the file to be written
	 * @param destinationPath
	 *            the path for the file inside the archive
	 * @throws CoreException
	 *             thrown when the file could not be written
	 */
	public void writeFile(IFile resource, IPath destinationPath)
			throws CoreException;

	/**
	 * write the given stub to the archive
	 * 
	 * @param archive
	 *            the archive to add
	 * @param monitor
	 *            a monitor to report progress to
	 */
	public void writeStub(IStub stub, IProgressMonitor monitor)
			throws CoreException;

	/**
	 * write the given stub to the archive
	 * 
	 * @param archive
	 *            the archive to add
	 * @param monitor
	 *            a monitor to report progress to
	 */
	public void writeSignature(IProgressMonitor monitor) throws CoreException;

	/**
	 * Called when building of the JAR finished.
	 * 
	 * @throws CoreException
	 *             thrown when the JAR could not be closed
	 */
	public void close() throws CoreException;

	public void writeFile(IFolder resource, IPath destinationPath)
			throws CoreException;

}
