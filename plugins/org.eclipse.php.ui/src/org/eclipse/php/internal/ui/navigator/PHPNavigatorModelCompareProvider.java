/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies -  initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.dltk.ui.IModelCompareCategories;
import org.eclipse.dltk.ui.IModelCompareProvider;
import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider.IncludePathContainer;
import org.eclipse.wst.jsdt.ui.ProjectLibraryRoot;

public class PHPNavigatorModelCompareProvider implements IModelCompareProvider {

	@Override
	public CompareResult compare(Object element1, Object element2, int cat1, int cat2) {
		if (element2 instanceof ProjectLibraryRoot) {
			return GREATER;
		}
		if (element1 instanceof ProjectLibraryRoot) {
			return LESS;
		}
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
