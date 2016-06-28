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
package org.eclipse.php.composer.core.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.core.model.IPackages;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class Packages extends ModelElement implements IPackages {

	private List<IPackage> packages;

	public Packages() {
		this.packages = new ArrayList<IPackage>();
	}

	@Override
	public List<IPackage> getPackages() {
		return packages;
	}

	@Override
	public void removePackage(IPackage p, boolean update) {
		IPackage toRemove = null;
		for (IPackage pkg : packages) {
			if (pkg.getName().equals(p.getName())) {
				toRemove = pkg;
				break;
			}
		}
		if (toRemove != null) {
			packages.remove(toRemove);
		}
		if (update) {
			updateModel();
		}
	}

	@Override
	public void addPackage(IPackage p, boolean update) {
		if (!packages.contains(p)) {
			packages.add(p);
		}
		if (update) {
			updateModel();
		}
	}

	@Override
	public void setPackages(List<IPackage> packages) {
		this.packages = packages;
	}

}
