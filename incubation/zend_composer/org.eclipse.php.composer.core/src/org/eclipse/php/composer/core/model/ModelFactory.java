/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import org.eclipse.php.composer.core.internal.model.*;
import org.eclipse.php.composer.core.internal.model.Package;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ModelFactory {

	public static IRepository createRepository(RepositoryType type) {
		return new Repository(type);
	}

	public static IRepositories createRepositories() {
		return new Repositories();
	}

	public static IRepositoryPackage createRepositoryPackage() {
		return new RepositoryPackage();
	}

	public static IPackage createPackage(String name) {
		return new Package(name);
	}

	public static IDist createDist(String url, String type) {
		IDist dist = new Dist();
		dist.setUrl(url);
		dist.setType(type);
		return dist;
	}

	public static ISource createSource(String url, String type, String reference) {
		ISource source = new Source();
		source.setUrl(url);
		source.setType(type);
		source.setReference(reference);
		return source;
	}

}
