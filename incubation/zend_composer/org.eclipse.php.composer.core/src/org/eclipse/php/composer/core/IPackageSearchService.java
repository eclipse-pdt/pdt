/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.model.IPackage;

/**
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public interface IPackageSearchService {

	void search(String pattern, IProgressMonitor monitor) throws IOException;

	void nextPage(IProgressMonitor monitor) throws IOException;

	boolean hasNextPage();

	IPackage getPackageDetails(String name, IProgressMonitor monitor)
			throws IOException;

	List<IPackage> getPackages();

	void reset();

	void dispose();

}
