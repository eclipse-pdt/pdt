package org.eclipse.php.internal.ui;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.util.LibraryFolderHelper;
import org.eclipse.wst.validation.internal.PrefConstants;
import org.eclipse.wst.validation.internal.PreferencesWrapper;

public class AutoDetectLibraryFolderListener implements IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() != IResourceChangeEvent.POST_CHANGE)
			return;

		IResourceDelta rootDelta = event.getDelta();
		IResourceDelta[] projectDeltas = rootDelta.getAffectedChildren();
		for (IResourceDelta projectDelta : projectDeltas) {
			if ((projectDelta.getFlags() & (IResourceDelta.OPEN | IResourceDelta.DESCRIPTION)) == 0)
				continue;

			IResource resource = projectDelta.getResource();
			if (resource.getType() != IResource.PROJECT)
				continue;

			IProject project = (IProject) resource;
			if (!project.isOpen())
				continue;

			try {
				if (!project.hasNature(PHPNature.ID))
					continue;
			} catch (CoreException e) {
				PHPUiPlugin.log(e);
				continue;
			}

			if (!hasDisabledPreference(project)) {
				// TODO read folders from preferences
				final IModelElement vendorFolder = DLTKCore.create(project
						.getFolder("vendor"));

				if (vendorFolder.exists()) {
					// TODO choose better job name and externalize it
					new WorkspaceJob("Auto-detect library folders") {
						@Override
						public IStatus runInWorkspace(IProgressMonitor monitor)
								throws CoreException {
							IModelElement[] folders = new IModelElement[] { vendorFolder };

							try {
								LibraryFolderHelper.useAsLibraryFolder(folders,
										monitor);
							} catch (OperationCanceledException e) {
								return errorStatus(e);
							} catch (InterruptedException e) {
								return errorStatus(e);
							}
							return Status.OK_STATUS;
						}
					}.schedule();
				}
			}
		}
	}

	@SuppressWarnings("restriction")
	private boolean hasDisabledPreference(IProject project) {
		PreferencesWrapper prefs = PreferencesWrapper.getPreferences(project,
				null);
		return prefs.get(PrefConstants.disabled, null) != null;
	}

	private IStatus errorStatus(Exception e) {
		return new Status(IStatus.ERROR, PHPUiPlugin.ID, "", e);
	}

}
