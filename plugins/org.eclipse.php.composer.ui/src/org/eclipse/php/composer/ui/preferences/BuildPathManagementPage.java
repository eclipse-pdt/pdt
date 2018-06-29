/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.composer.core.buildpath.BuildPathManager;
import org.eclipse.php.composer.internal.core.resources.ComposerProject;
import org.eclipse.ui.preferences.WizardPropertyPage;
import org.osgi.service.prefs.BackingStoreException;

public class BuildPathManagementPage extends WizardPropertyPage {

	private IScriptProject scriptProject;
	private BuildPathExclusionWizard wizard;

	@Override
	protected IWizard createWizard() {

		BPListElement elem = new BPListElement(scriptProject, IBuildpathEntry.BPE_SOURCE, false);

		ComposerProject composerProject = new ComposerProject(scriptProject.getProject());
		IPath path = scriptProject.getPath().append(composerProject.getVendorDir());
		elem.setPath(path);

		IEclipsePreferences projectPreferences = ComposerPlugin.getDefault()
				.getProjectPreferences(scriptProject.getProject());
		String stored = projectPreferences.get(ComposerPreferenceConstants.BUILDPATH_INCLUDES_EXCLUDES, null);

		if (stored != null) {
			IBuildpathEntry buildpathEntry = scriptProject.decodeBuildpathEntry(stored);
			for (IPath includePath : buildpathEntry.getInclusionPatterns()) {
				elem.addToInclusion(path.append(includePath));
			}
			for (IPath excludePath : buildpathEntry.getExclusionPatterns()) {
				elem.addToExclusions(path.append(excludePath));
			}
		}

		return wizard = new BuildPathExclusionWizard(new BPListElement[] {}, elem);
	}

	@Override
	public void setElement(IAdaptable element) {
		super.setElement(element);

		if (element instanceof IProject) {
			scriptProject = DLTKCore.create((IProject) element);
			return;
		}

		scriptProject = (IScriptProject) element;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void applyChanges() {

		List<BPListElement> elements = wizard.getModifiedElements();
		BPListElement element = elements.get(0);
		IBuildpathEntry buildpathEntry = element.getBuildpathEntry();

		if (buildpathEntry instanceof BuildpathEntry) {
			BuildpathEntry entry = (BuildpathEntry) buildpathEntry;
			String encodeBuildpathEntry = scriptProject.encodeBuildpathEntry(entry);
			IEclipsePreferences projectPreferences = ComposerPlugin.getDefault()
					.getProjectPreferences(scriptProject.getProject());
			projectPreferences.put(ComposerPreferenceConstants.BUILDPATH_INCLUDES_EXCLUDES, encodeBuildpathEntry);

			// update buildpath
			try {
				// store preferences - is this needed here?
				projectPreferences.flush();

				// update buildpath
				ComposerProject composerProject = new ComposerProject(scriptProject.getProject());
				BuildPathManager bpm = new BuildPathManager(composerProject);
				bpm.update();
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}

		}
	}
}
