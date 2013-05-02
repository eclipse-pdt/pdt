package org.eclipse.php.internal.ui.phar.wizard;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.php.internal.core.phar.PharPackage;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class PharFileExportOperation extends WorkspaceModifyOperation implements
		IPharExportRunnable {

	private static class MessageMultiStatus extends MultiStatus {
		MessageMultiStatus(String pluginId, int code, String message,
				Throwable exception) {
			super(pluginId, code, message, exception);
		}

		/*
		 * allows to change the message
		 */
		protected void setMessage(String message) {
			super.setMessage(message);
		}
	}

	private IPharBuilder fJarBuilder;
	private PharPackage fJarPackage;
	private PharPackage[] fJarPackages;
	private Shell fParentShell;
	private MessageMultiStatus fStatus;
	private Set<IResource> resourceSet = new HashSet<IResource>();

	/**
	 * Creates an instance of this class.
	 * 
	 * @param jarPackage
	 *            the JAR package specification
	 * @param parent
	 *            the parent for the dialog, or <code>null</code> if no dialog
	 *            should be presented
	 */
	public PharFileExportOperation(PharPackage jarPackage, Shell parent) {
		this(new PharPackage[] { jarPackage }, parent);
	}

	/**
	 * Creates an instance of this class.
	 * 
	 * @param jarPackages
	 *            an array with JAR package data objects
	 * @param parent
	 *            the parent for the dialog, or <code>null</code> if no dialog
	 *            should be presented
	 */
	public PharFileExportOperation(PharPackage[] jarPackages, Shell parent) {
		this(parent);
		fJarPackages = jarPackages;
	}

	private PharFileExportOperation(Shell parent) {
		fParentShell = parent;
		fStatus = new MessageMultiStatus(PHPUiPlugin.getPluginId(), IStatus.OK,
				"", null); //$NON-NLS-1$
		// standardModelElementContentProvider= new
		// StandardModelElementContentProvider();
	}

	private void addToStatus(CoreException ex) {
		IStatus status = ex.getStatus();
		String message = ex.getLocalizedMessage();
		if (message == null || message.length() < 1) {
			message = ""; //$NON-NLS-1$
			status = new Status(status.getSeverity(), status.getPlugin(),
					status.getCode(), message, ex);
		}
		fStatus.add(status);
	}

	/**
	 * Adds a new info to the list with the passed information. Normally the
	 * export operation continues after a warning.
	 * 
	 * @param message
	 *            the message
	 * @param error
	 *            the throwable that caused the warning, or <code>null</code>
	 */
	protected void addInfo(String message, Throwable error) {
		fStatus.add(new Status(IStatus.INFO, PHPUiPlugin.getPluginId(), 10001,
				message, error));
	}

	/**
	 * Adds a new warning to the list with the passed information. Normally the
	 * export operation continues after a warning.
	 * 
	 * @param message
	 *            the message
	 * @param error
	 *            the throwable that caused the warning, or <code>null</code>
	 */
	private void addWarning(String message, Throwable error) {
		fStatus.add(new Status(IStatus.WARNING, PHPUiPlugin.getPluginId(),
				10001, message, error));
	}

	/**
	 * Adds a new error to the list with the passed information. Normally an
	 * error terminates the export operation.
	 * 
	 * @param message
	 *            the message
	 * @param error
	 *            the throwable that caused the error, or <code>null</code>
	 */
	private void addError(String message, Throwable error) {
		fStatus.add(new Status(IStatus.ERROR, PHPUiPlugin.getPluginId(), 10001,
				message, error));
	}

	/**
	 * Answers the number of file resources specified by the JAR package.
	 * 
	 * @return int
	 */
	private int countSelectedElements() {
		Set enclosingJavaProjects = new HashSet(10);
		int count = 0;

		int n = fJarPackage.getElements().length;
		for (int i = 0; i < n; i++) {
			Object element = fJarPackage.getElements()[i];

			IScriptProject javaProject = getEnclosingJavaProject(element);
			if (javaProject != null)
				enclosingJavaProjects.add(javaProject);

			IResource resource = null;
			if (element instanceof IModelElement) {
				IModelElement je = (IModelElement) element;
				resource = je.getResource();
				if (resource == null) {
					continue;
				}
			} else if (element instanceof IResource) {
				resource = (IResource) element;
			}
			if (resource != null) {
				if (resource.getType() == IResource.FILE)
					count++;
				else
					count += getTotalChildCount((IContainer) resource);
			}
		}

		return count;
	}

	private int getTotalChildCount(IContainer container) {
		IResource[] members;
		try {
			members = container.members();
		} catch (CoreException ex) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < members.length; i++) {
			if (members[i].getType() == IResource.FILE)
				count++;
			else
				count += getTotalChildCount((IContainer) members[i]);
		}
		return count;
	}

	/**
	 * Exports the passed resource to the JAR file
	 * 
	 * @param element
	 *            the resource or JavaElement to export
	 * @param progressMonitor
	 *            the progress monitor
	 * @throws InterruptedException
	 *             thrown on cancel
	 */
	private void exportElement(Object element, IProgressMonitor progressMonitor)
			throws InterruptedException {
		int leadSegmentsToRemove = 1;
		IProjectFragment pkgRoot = null;
		boolean isInJavaProject = false;
		IResource resource = null;
		// ITypeRoot typeRootElement= null;
		IScriptProject jProject = null;
		if (element instanceof IModelElement) {
			isInJavaProject = true;
			IModelElement je = (IModelElement) element;
			jProject = je.getScriptProject();
			try {
				pkgRoot = jProject.getProjectFragment(je
						.getCorrespondingResource());
			} catch (ModelException e) {
				e.printStackTrace();
			}
			resource = je.getResource();
		} else if (element instanceof IResource) {
			resource = (IResource) element;
		} else {
			return;
		}

		if (!resource.isAccessible()) {
			addWarning("", null); //$NON-NLS-1$
			return;
		}

		if (!isInJavaProject) {
			// check if it's a Java resource
			try {
				isInJavaProject = resource.getProject().hasNature(PHPNature.ID);
			} catch (CoreException ex) {
				addWarning("", ex); //$NON-NLS-1$
				return;
			}
			if (isInJavaProject) {
				// IModelElement je= DLTKCore.create(resource);
				// if (je instanceof ITypeRoot && je.exists()) {
				// exportElement(je, progressMonitor);
				// return;
				// }

				jProject = DLTKCore.create(resource.getProject());
				try {
					IProjectFragment pkgFragment = jProject
							.findProjectFragment(resource.getFullPath()
									.removeLastSegments(1));
					if (pkgFragment != null)
						pkgRoot = pkgFragment;
					else
						pkgRoot = findPackageFragmentRoot(jProject, resource
								.getFullPath().removeLastSegments(1));
				} catch (ModelException ex) {
					addWarning("", ex); //$NON-NLS-1$
					return;
				}
			}
		}

		if (pkgRoot != null && jProject != null) {
			leadSegmentsToRemove = pkgRoot.getPath().segmentCount();
			boolean isOnBuildPath;
			isOnBuildPath = jProject.isOnBuildpath(resource);
			if (!isOnBuildPath
					|| (!pkgRoot.getElementName().equals(
							IProjectFragment.DEFAULT_PACKAGE_ROOT)))
				leadSegmentsToRemove--;
		}

		IPath destinationPath = resource.getFullPath().removeFirstSegments(
				leadSegmentsToRemove);

		if (resource.getType() == IResource.FILE) {

			exportResource(progressMonitor, resource, destinationPath);

			progressMonitor.worked(1);
			ModalContext.checkCanceled(progressMonitor);

		} else {

			if (fJarPackage.areDirectoryEntriesIncluded()) {

				exportResource(progressMonitor, resource,
						destinationPath.append(File.separator));

				progressMonitor.worked(1);
				ModalContext.checkCanceled(progressMonitor);
			}
			exportContainer(progressMonitor, (IContainer) resource);
		}

	}

	private void exportContainer(IProgressMonitor progressMonitor,
			IContainer container) throws InterruptedException {

		IResource[] children = null;
		try {
			children = container.members();
		} catch (CoreException exception) {
			// this should never happen because an #isAccessible check is done
			// before #members is invoked
			addWarning("", exception); //$NON-NLS-1$
		}
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				IResource child = children[i];
				exportElement(child, progressMonitor);
			}
		}
	}

	private IProjectFragment findPackageFragmentRoot(IScriptProject jProject,
			IPath path) throws ModelException {
		if (jProject == null || path == null || path.segmentCount() <= 0)
			return null;
		IProjectFragment pkgRoot = jProject.findProjectFragment(path);
		if (pkgRoot != null)
			return pkgRoot;
		else
			return findPackageFragmentRoot(jProject, path.removeLastSegments(1));
	}

	private void exportResource(IProgressMonitor progressMonitor,
			IResource resource, IPath destinationPath) {

		try {
			if (resource instanceof IFile) {
				progressMonitor.subTask(""); //$NON-NLS-1$
				fJarBuilder.writeFile((IFile) resource, destinationPath);
			} else {
				progressMonitor.subTask(""); //$NON-NLS-1$
				fJarBuilder.writeFile((IFolder) resource, destinationPath);
			}

		} catch (CoreException ex) {
			handleCoreExceptionOnExport(ex);
		}
	}

	/**
	 * Exports the resources as specified by the JAR package.
	 * 
	 * @param progressMonitor
	 *            the progress monitor
	 * @throws InterruptedException
	 *             thrown when cancelled
	 */
	private void exportSelectedElements(IProgressMonitor progressMonitor)
			throws InterruptedException {
		int n = fJarPackage.getElements().length;
		for (int i = 0; i < n; i++) {
			Object element = fJarPackage.getElements()[i];
			exportElement(element, progressMonitor);
		}
	}

	private IScriptProject getEnclosingJavaProject(Object element) {
		if (element instanceof IModelElement) {
			return ((IModelElement) element).getScriptProject();
		} else if (element instanceof IResource) {
			IProject project = ((IResource) element).getProject();
			try {
				if (project.hasNature(PHPNature.ID))
					return DLTKCore.create(project);
			} catch (CoreException ex) {
				addWarning("", ex); //$NON-NLS-1$
			}
		}
		return null;
	}

	/**
	 * Handles core exceptions that are thrown by
	 * {@link IJarBuilder#writeFile(IFile, IPath)}.
	 * 
	 * @param ex
	 *            the core exception
	 * @since 3.5
	 */
	private void handleCoreExceptionOnExport(CoreException ex) {
		Throwable realEx = ex.getStatus().getException();
		if (realEx instanceof IOException && realEx.getMessage() != null
				&& realEx.getMessage().startsWith("duplicate entry:")) // hardcoded message string from java.util.zip.ZipOutputStream.putNextEntry(ZipEntry) //$NON-NLS-1$
			addWarning(ex.getMessage(), realEx);
		else
			addToStatus(ex);
	}

	/**
	 * Returns the status of this operation. The result is a status object
	 * containing individual status objects.
	 * 
	 * @return the status of this operation
	 */
	public IStatus getStatus() {
		String message = null;
		switch (fStatus.getSeverity()) {
		case IStatus.OK:
			message = PharPackagerMessages.PharFileExportOperation_10;
			break;
		case IStatus.INFO:
			message = PharPackagerMessages.PharFileExportOperation_11;
			break;
		case IStatus.WARNING:
			message = PharPackagerMessages.PharFileExportOperation_12;
			break;
		case IStatus.ERROR:
			if (fJarPackages.length > 1)
				message = PharPackagerMessages.PharFileExportOperation_13;
			else
				message = PharPackagerMessages.PharFileExportOperation_13;
			break;
		default:
			// defensive code in case new severity is defined
			message = ""; //$NON-NLS-1$
			break;
		}
		fStatus.setMessage(message);
		return fStatus;
	}

	/**
	 * Exports the resources as specified by the JAR package.
	 * 
	 * @param progressMonitor
	 *            the progress monitor that displays the progress
	 * @throws InvocationTargetException
	 *             thrown when an ecxeption occurred
	 * @throws InterruptedException
	 *             thrown when cancelled
	 * @see #getStatus()
	 */
	protected void execute(IProgressMonitor progressMonitor)
			throws InvocationTargetException, InterruptedException {
		int count = fJarPackages.length;
		progressMonitor.beginTask("", count); //$NON-NLS-1$
		try {
			for (int i = 0; i < count; i++) {
				SubProgressMonitor subProgressMonitor = new SubProgressMonitor(
						progressMonitor, 1,
						SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);
				fJarPackage = fJarPackages[i];
				if (fJarPackage != null)
					singleRun(subProgressMonitor);
			}
		} finally {
			progressMonitor.done();
		}
	}

	private void singleRun(IProgressMonitor progressMonitor)
			throws InvocationTargetException, InterruptedException {
		try {
			if (!preconditionsOK())
				throw new InvocationTargetException(null, ""); //$NON-NLS-1$
			int totalWork = countSelectedElements();
			progressMonitor.beginTask("", totalWork); //$NON-NLS-1$

			fJarBuilder = PharExportHelper.createPlainPharBuilder(fJarPackage);
			// fJarBuilder = new PlainPharBuilder();
			fJarBuilder.open(fJarPackage, fParentShell, fStatus);
			fJarBuilder.writeStub(
					fJarBuilder.getStubProvider().create(fJarPackage),
					progressMonitor);
			exportSelectedElements(progressMonitor);
			fJarBuilder.writeSignature(progressMonitor);
			// if (getStatus().getSeverity() != IStatus.ERROR) {
			// progressMonitor.subTask("");
			// saveFiles();
			// }
		} catch (CoreException ex) {
			addToStatus(ex);
		} finally {
			try {
				if (fJarBuilder != null)
					fJarBuilder.close();
			} catch (CoreException ex) {
				addToStatus(ex);
			}
			progressMonitor.done();
		}
	}

	private boolean preconditionsOK() {
		// if (!fJarPackage.areGeneratedFilesExported() &&
		// !fJarPackage.areJavaFilesExported()) {
		// addError(JarPackagerMessages.JarFileExportOperation_noExportTypeChosen,
		// null);
		// return false;
		// }
		if (fJarPackage.getElements() == null
				|| fJarPackage.getElements().length == 0) {
			addError(
					PharPackagerMessages.JarFileExportOperation_noResourcesSelected,
					null);
			return false;
		}
		if (fJarPackage.getAbsolutePharLocation() == null) {
			addError(
					PharPackagerMessages.JarFileExportOperation_invalidJarLocation,
					null);
			return false;
		}
		File targetFile = fJarPackage.getAbsolutePharLocation().toFile();
		if (targetFile.exists() && !targetFile.canWrite()) {
			addError(
					PharPackagerMessages.JarFileExportOperation_jarFileExistsAndNotWritable,
					null);
			return false;
		}
		if (!fJarPackage.isStubAccessible()) {
			addError(
					PharPackagerMessages.JarFileExportOperation_manifestDoesNotExist,
					null);
			return false;
		}

		return true;
	}

	// private void saveFiles() {
	// // Save the manifest
	// if (fJarPackage.isStubGenerated()) {
	// try {
	// saveManifest();
	// } catch (CoreException ex) {
	// addError(
	// JarPackagerMessages.JarFileExportOperation_errorSavingManifest,
	// ex);
	// } catch (IOException ex) {
	// addError(
	// JarPackagerMessages.JarFileExportOperation_errorSavingManifest,
	// ex);
	// }
	// }
	// }

	// private void saveManifest() throws CoreException, IOException {
	// ByteArrayOutputStream stubOutput = new ByteArrayOutputStream();
	// IStub stub = fJarBuilder.getStubProvider().create(fJarPackage);
	// stub.write(stubOutput);
	// }

}
