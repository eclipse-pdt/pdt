/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.changes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.osgi.util.NLS;

public class RenameConfigurationChange extends Change {

	private IResource fResource;
	private IPath fDest;
	private IPath fSource;
	private String fName;
	private String fNewName;
	private Map<ILaunchConfiguration, Map<String, String>> fConfigurationChanges;

	/**
	 * The constructor gets also a new name in case the move it's actually a rename
	 * operation
	 * 
	 * @param source
	 * @param dest
	 * @param resName
	 * @param newName
	 */
	public RenameConfigurationChange(IPath source, IPath dest, String resName, String newName) {

		fSource = source;
		fDest = dest;
		fName = resName;
		fNewName = newName;

		IPath resourcePath = source.append(resName);
		if (source.segmentCount() < 1) {
			fResource = ResourcesPlugin.getWorkspace().getRoot().getProject(resName);
		} else {
			fResource = ResourcesPlugin.getWorkspace().getRoot().getFile(resourcePath);
			if (!fResource.exists()) {
				fResource = ResourcesPlugin.getWorkspace().getRoot().getFolder(resourcePath);
			}
		}

	}

	@Override
	public Object getModifiedElement() {
		return fResource;
	}

	@Override
	public String getName() {
		return NLS.bind(Messages.RenameConfigurationChange_0, fResource.getName());
	}

	@Override
	public void initializeValidationData(IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {

		final IPath dest = fDest.append(fNewName);

		// Collect launch configurations:
		fConfigurationChanges = new HashMap<>();
		ILaunchConfiguration[] launchConfigurations = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurations();
		for (ILaunchConfiguration launchConfiguration : launchConfigurations) {
			Map<String, Object> attributes = launchConfiguration.getAttributes();
			Map<String, String> changes = new HashMap<>();
			for (Entry<String, Object> attribute : attributes.entrySet()) {
				Object attributeValue = attribute.getValue();
				if (!(attributeValue instanceof String)) {
					continue;
				}
				String attributeString = (String) attributeValue;
				String attributeName = attribute.getKey();

				// resource is renamed/moved:
				IPath attributeValuePath = new Path(attributeString);
				if (fResource.getFullPath().isPrefixOf(attributeValuePath)) {
					String newValue;
					if (fResource instanceof IContainer) {
						newValue = dest
								.append(attributeValuePath.removeFirstSegments(
										attributeValuePath.matchingFirstSegments(fResource.getFullPath())))
								.makeAbsolute().toString();
					} else {
						newValue = dest.makeAbsolute().toString();
					}

					changes.put(attributeName, newValue);
				}
				if (fResource.getLocation() != null && fResource.getLocation().isPrefixOf(attributeValuePath)) {
					String newValue = null;
					if (fResource instanceof IContainer) {
						IPath projectPath = fResource.getFullPath();
						IPath fileSystemPath = fResource.getLocation().removeLastSegments(projectPath.segmentCount());

						newValue = fileSystemPath.append(dest).append(attributeValuePath
								.removeFirstSegments(attributeValuePath.matchingFirstSegments(fResource.getLocation())))
								.toString();
					} else {
						IPath projectPath = fResource.getFullPath();
						IPath fileSystemPath = fResource.getLocation().removeLastSegments(projectPath.segmentCount());
						newValue = fileSystemPath.append(dest).toString();
					}
					changes.put(attributeName, newValue);
				}
			}
			if (changes.size() > 0) {
				fConfigurationChanges.put(launchConfiguration, changes);
			}
		}
		for (Entry<ILaunchConfiguration, Map<String, String>> configurationChange : fConfigurationChanges.entrySet()) {
			ILaunchConfiguration configuration = configurationChange.getKey();
			ILaunchConfigurationWorkingCopy configurationCopy;
			if (configuration instanceof ILaunchConfigurationWorkingCopy) {
				configurationCopy = (ILaunchConfigurationWorkingCopy) configuration;
			} else {
				configurationCopy = configuration.getWorkingCopy();
			}
			for (Entry<String, String> change : configurationChange.getValue().entrySet()) {
				configurationCopy.setAttribute(change.getKey(), change.getValue());
			}
			configurationCopy.doSave();
		}

		return new RenameConfigurationChange(fDest, fSource, fNewName, fName);
	}

}
