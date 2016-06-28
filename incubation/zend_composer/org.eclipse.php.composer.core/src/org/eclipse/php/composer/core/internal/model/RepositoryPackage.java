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

import org.eclipse.php.composer.core.model.IDist;
import org.eclipse.php.composer.core.model.IRepositoryPackage;
import org.eclipse.php.composer.core.model.ISource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
@JsonInclude(Include.NON_NULL)
public class RepositoryPackage extends ModelElement implements
		IRepositoryPackage {

	private String name;
	private String version;

	private IDist dist;
	private ISource source;

	protected boolean isDirty;

	@Override
	public void setName(String name) {
		this.name = name;
		updateModel();
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public void setDist(IDist dist) {
		this.dist = dist;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public void setSource(ISource source) {
		this.source = source;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public IDist getDist() {
		return dist;
	}

	@Override
	public ISource getSource() {
		return source;
	}

	@Override
	public boolean isDirty() {
		boolean result = isDirty;
		if (!result) {
			result = getDist().isDirty();
			if (!result) {
				result = getSource().isDirty();
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IRepositoryPackage) {
			IRepositoryPackage pkg = (IRepositoryPackage) obj;
			return getName().equals(pkg.getName());
		}
		return false;
	}

}
