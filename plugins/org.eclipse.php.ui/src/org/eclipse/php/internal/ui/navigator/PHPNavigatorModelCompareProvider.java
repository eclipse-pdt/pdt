/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies -  initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.dltk.ui.IModelCompareCategories;
import org.eclipse.dltk.ui.IModelCompareProvider;
import org.eclipse.php.internal.ui.navigator.PHPExplorerContentProvider.IncludePathContainer;

public class PHPNavigatorModelCompareProvider implements IModelCompareProvider {

	@Override
	public CompareResult compare(Object element1, Object element2, int cat1, int cat2) {
		if (element2 instanceof BuildPathContainer) {
			return GREATER;
		}
		if (element1 instanceof BuildPathContainer) {
			return LESS;
		}
		if (element2 instanceof IncludePathContainer) {
			return GREATER;
		}
		if (element1 instanceof IncludePathContainer) {
			return LESS;
		}

		return null;
	}

	@Override
	public Integer category(Object element) {
		if (element instanceof ISourceModule) {
			return IModelCompareCategories.RESOURCES;
		}
		return null;
	}

}
