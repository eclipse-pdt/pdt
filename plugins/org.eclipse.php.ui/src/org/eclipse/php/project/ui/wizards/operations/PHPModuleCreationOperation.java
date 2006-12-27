/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.project.ui.wizards.operations;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.php.ui.wizards.BasicPHPWizardPageExtended;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.operations.IProjectCreationPropertiesNew;

public class PHPModuleCreationOperation extends AbstractDataModelOperation implements IProjectCreationPropertiesNew {

	private final List wizardPags;
	
	public PHPModuleCreationOperation(IDataModel dataModel, List wizardPages) {
		super(dataModel);
		this.wizardPags = wizardPages;		
	}

	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			IProgressMonitor subMonitor = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
			IProjectDescription desc = (IProjectDescription) model.getProperty(PROJECT_DESCRIPTION);
			IProject project = (IProject) model.getProperty(PROJECT);
			if (!project.exists()) {
				project.create(desc, subMonitor);
			}

			for (Iterator iter = wizardPags.iterator(); iter.hasNext();) {
				BasicPHPWizardPageExtended element = (BasicPHPWizardPageExtended) iter.next();
				element.postPerformFinish(project);
				element.flushPreferences();		
			}
			
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			subMonitor = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);

			project.open(subMonitor);

			String[] natureIds = (String[]) model.getProperty(PROJECT_NATURES);
			if (null != natureIds) {
				desc = project.getDescription();
				desc.setNatureIds(natureIds);
				project.setDescription(desc, monitor);
			}

			try {
				PHPNature nature = (PHPNature) project.getNature(PHPNature.ID);
				PHPProjectOptions options = nature.getOptions();
				String defaultEncodeing = model.getStringProperty(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING);
				options.setOption(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING, defaultEncodeing);
				String context = model.getStringProperty(PHPCoreConstants.PHPOPTION_CONTEXT_ROOT);
				options.setOption(PHPCoreConstants.PHPOPTION_CONTEXT_ROOT, context);
				IIncludePathEntry[] includePath = (IIncludePathEntry[]) model.getProperty(PHPCoreConstants.PHPOPTION_INCLUDE_PATH);
				options.setRawIncludePath(includePath, (SubProgressMonitor)monitor);
//				options.saveChanges(monitor);
                
                if (model.isPropertySet(Keys.PHP_VERSION)) {
                    String version = model.getStringProperty(Keys.PHP_VERSION);
                    PhpVersionProjectPropertyHandler.setVersion(version, project);
                    boolean useASPTags = model.getBooleanProperty(Keys.EDITOR_USE_ASP_TAGS);
                    UseAspTagsHandler.setUseAspTagsAsPhp(useASPTags, project);
                }
                
			} catch (CoreException e) {
				e.printStackTrace();
			}

			if (monitor.isCanceled())
				throw new OperationCanceledException();

		} catch (CoreException e) {
			Logger.logException(e);
		} finally {
			monitor.done();
		}
		if (monitor.isCanceled())
			throw new OperationCanceledException();
		return OK_STATUS;
	}

	public boolean canUndo() {
		return false;
	}

	public boolean canRedo() {
		return false;
	}

}
