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
package org.eclipse.php.internal.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;

public abstract class PHPElementResourceMapping extends ResourceMapping {

	/* package */PHPElementResourceMapping() {
	}

	public PHPCodeData getPHPElement() {
		Object o = getModelObject();
		if (o instanceof PHPCodeData)
			return (PHPCodeData) o;
		return null;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PHPElementResourceMapping))
			return false;
		return getPHPElement().equals(((PHPElementResourceMapping) obj).getPHPElement());
	}

	public int hashCode() {
		PHPCodeData phpElement = getPHPElement();
		if (phpElement == null)
			return super.hashCode();

		return phpElement.hashCode();
	}

	public String getModelProviderId() {
		return ""; // TODO - Migration to Eclipse 3.2 (Create org.eclipse.core.resources.modelProviders extention ??)
	}

	//---- the factory code ---------------------------------------------------------------

	private static final class PHPModelResourceMapping extends PHPElementResourceMapping {
		private final PHPWorkspaceModelManager fModel;

		private PHPModelResourceMapping(PHPWorkspaceModelManager model) {
			Assert.isNotNull(model);
			fModel = model;
		}

		public Object getModelObject() {
			return fModel;
		}

		public IProject[] getProjects() {
			PHPProjectModel[] projects = null;
			projects = fModel.listModels();

			IProject[] result = new IProject[projects.length];
			for (int i = 0; i < projects.length; i++) {
				result[i] = PHPWorkspaceModelManager.getInstance().getProjectForModel(projects[i]);
			}
			return result;
		}

		public ResourceTraversal[] getTraversals(ResourceMappingContext context, IProgressMonitor monitor) throws CoreException {
			PHPProjectModel[] projects = fModel.listModels();
			ResourceTraversal[] result = new ResourceTraversal[projects.length];
			PHPWorkspaceModelManager model = PHPWorkspaceModelManager.getInstance();
			for (int i = 0; i < projects.length; i++) {
				result[i] = new ResourceTraversal(new IResource[] { model.getProjectForModel(projects[i]) }, IResource.DEPTH_INFINITE, 0);
			}
			return result;
		}
	}

	private static final class PHPProjectResourceMapping extends PHPElementResourceMapping {
		private final PHPProjectModel fProject;

		private PHPProjectResourceMapping(PHPProjectModel project) {
			Assert.isNotNull(project);
			fProject = project;
		}

		public Object getModelObject() {
			return fProject;
		}

		public IProject[] getProjects() {
			return new IProject[] { PHPWorkspaceModelManager.getInstance().getProjectForModel(fProject) };
		}

		public ResourceTraversal[] getTraversals(ResourceMappingContext context, IProgressMonitor monitor) throws CoreException {
			return new ResourceTraversal[] { new ResourceTraversal(new IResource[] { PHPWorkspaceModelManager.getInstance().getProjectForModel(fProject) }, IResource.DEPTH_INFINITE, 0) };
		}
	}

	private static final class CompilationUnitResourceMapping extends PHPElementResourceMapping {
		private final PHPFileData fUnit;

		private CompilationUnitResourceMapping(PHPFileData unit) {
			Assert.isNotNull(unit);
			fUnit = unit;
		}

		public Object getModelObject() {
			return fUnit;
		}

		public IProject[] getProjects() {
			IResource res = PHPModelUtil.getResource(fUnit);
			if (res != null && res.exists()) {
				return new IProject[] { res.getProject() };
			}
			return new IProject[] {};
		}

		public ResourceTraversal[] getTraversals(ResourceMappingContext context, IProgressMonitor monitor) throws CoreException {
			IResource res = PHPModelUtil.getResource(fUnit);
			if (res != null) {
				return new ResourceTraversal[] { new ResourceTraversal(new IResource[] { PHPModelUtil.getResource(fUnit) }, IResource.DEPTH_ONE, 0) };
			}
			return new ResourceTraversal[] {};
		}
	}

	public static ResourceMapping create(Object element) {
		if (element instanceof PHPWorkspaceModelManager)
			return create((PHPWorkspaceModelManager) element);
		else if (element instanceof PHPProjectModel)
			return create((PHPProjectModel) element);
		else if (element instanceof PHPFileData)
			return create((PHPFileData) element);
		else if (element instanceof PHPClassData)
			return create((PHPClassData) element);
		else if (element instanceof PHPFunctionData) {
			return create((PHPFunctionData) element);
		} else
			return null;

	}

	public static ResourceMapping create(final PHPWorkspaceModelManager model) {
		return new PHPModelResourceMapping(model);
	}

	public static ResourceMapping create(final PHPProjectModel project) {
		return new PHPProjectResourceMapping(project);
	}

	public static ResourceMapping create(PHPFileData unit) {
		if (unit == null)
			return null;
		return new CompilationUnitResourceMapping(unit);
	}

	public static ResourceMapping create(PHPClassData clasz) {
		// top level types behave like the File
		PHPCodeData parent = clasz.getContainer();
		if (parent instanceof PHPFileData) {
			return create((PHPFileData) parent);
		}
		return null;
	}

	public static ResourceMapping create(PHPFunctionData function) {
		// top level functions behave like the File
		PHPCodeData parent = function.getContainer();
		if (parent instanceof PHPFileData) {
			return create((PHPFileData) parent);
		}
		return null;
	}

}
