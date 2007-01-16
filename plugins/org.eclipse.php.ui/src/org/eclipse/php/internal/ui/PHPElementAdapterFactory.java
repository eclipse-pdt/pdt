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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.php.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.core.documentModel.dom.TextImplForPhp;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.search.PHPSearchPage;
import org.eclipse.php.ui.search.SearchUtil;
import org.eclipse.search.ui.ISearchPageScoreComputer;
import org.eclipse.ui.IContainmentAdapter;
import org.eclipse.ui.IContributorResourceAdapter;
import org.eclipse.ui.ide.IContributorResourceAdapter2;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.views.properties.FilePropertySource;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.ResourcePropertySource;
import org.eclipse.ui.views.tasklist.ITaskListResourceAdapter;

/**
 * Implements basic UI support for Java elements.
 * Implements handle to persistent support for Java elements.
 */
public class PHPElementAdapterFactory implements IAdapterFactory, IContributorResourceAdapter, IContributorResourceAdapter2 {

	/**
	 * @author seva
	 *
	 */
	public static class PHPSearchPageScoreComputer implements ISearchPageScoreComputer {
		public int computeScore(String id, Object element) {
			if (!PHPSearchPage.EXTENSION_POINT_ID.equals(id))
				// Can't decide
				return ISearchPageScoreComputer.UNKNOWN;

			if (element instanceof PHPElementImpl || element instanceof TextImplForPhp)
				return 90;

			return ISearchPageScoreComputer.LOWEST;
		}

	}

	private static Class[] PROPERTIES = new Class[] { IPropertySource.class, IResource.class, IWorkbenchAdapter.class, IContributorResourceAdapter.class, IContributorResourceAdapter2.class, ITaskListResourceAdapter.class, IContainmentAdapter.class };

	/*
	 * Do not use real type since this would cause
	 * the Search plug-in to be loaded.
	 */
	private Object fSearchPageScoreComputer;
	private static PHPWorkbenchAdapter fgJavaWorkbenchAdapter;
	private static ITaskListResourceAdapter fgTaskListAdapter;
	private static PHPElementContainmentAdapter fgJavaElementContainmentAdapter;

	public Class[] getAdapterList() {
		updateLazyLoadedAdapters();
		return PROPERTIES;
	}

	public Object getAdapter(Object element, Class key) {
		if ((element instanceof PHPElementImpl || element instanceof TextImplForPhp) && key == ISearchPageScoreComputer.class) {
			if (fSearchPageScoreComputer == null) {
				createSearchPageScoreComputer();
			}
			return fSearchPageScoreComputer;
		}
		Object phpElement = getPHPElement(element);

		if (IPropertySource.class.equals(key)) {
			return getProperties(phpElement);
		}
		if (IResource.class.equals(key)) {
			return getResource(phpElement);
		}
		if (IWorkbenchAdapter.class.equals(key)) {
			return getJavaWorkbenchAdapter();
		}
		if (IContributorResourceAdapter.class.equals(key)) {
			return this;
		}
		if (IContributorResourceAdapter2.class.equals(key)) {
			return this;
		}
		if (IContainmentAdapter.class.equals(key)) {
			return getJavaElementContainmentAdapter();
		}
		return null;
	}

	private IResource getResource(Object element) {
		return PHPModelUtil.getResource(element);
	}

	public IResource getAdaptedResource(IAdaptable adaptable) {
		Object je = getPHPElement(adaptable);
		if (je != null)
			return getResource(je);

		return null;
	}

	public ResourceMapping getAdaptedResourceMapping(IAdaptable adaptable) {
		Object je = getPHPElement(adaptable);
		if (je != null)
			return PHPElementResourceMapping.create(je);

		return null;
	}

	private Object getPHPElement(Object element) {
		if (element instanceof PHPCodeData || element instanceof PHPProjectModel || element instanceof PHPWorkspaceModelManager)
			return element;
		return null;
	}

	private IPropertySource getProperties(Object element) {
		IResource resource = getResource(element);
		if (resource == null && element instanceof PHPCodeData)
			return new PHPElementProperties((PHPCodeData) element);
		if (resource.getType() == IResource.FILE)
			return new FilePropertySource((IFile) resource);
		return new ResourcePropertySource(resource);
	}

	private static PHPWorkbenchAdapter getJavaWorkbenchAdapter() {
		if (fgJavaWorkbenchAdapter == null)
			fgJavaWorkbenchAdapter = new PHPWorkbenchAdapter();
		return fgJavaWorkbenchAdapter;
	}

	private static PHPElementContainmentAdapter getJavaElementContainmentAdapter() {
		if (fgJavaElementContainmentAdapter == null)
			fgJavaElementContainmentAdapter = new PHPElementContainmentAdapter();
		return fgJavaElementContainmentAdapter;
	}

	private void updateLazyLoadedAdapters() {
		if (fSearchPageScoreComputer == null && SearchUtil.isSearchPlugInActivated())
			createSearchPageScoreComputer();
	}

	private void createSearchPageScoreComputer() {
		fSearchPageScoreComputer = new PHPSearchPageScoreComputer();
	}

}
