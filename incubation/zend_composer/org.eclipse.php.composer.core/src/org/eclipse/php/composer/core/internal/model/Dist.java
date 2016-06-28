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

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class Dist extends ModelElement implements IDist {

	protected String url;
	protected String type;

	protected transient boolean isDirty;

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public void setType(String type) {
		this.type = type;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

}
