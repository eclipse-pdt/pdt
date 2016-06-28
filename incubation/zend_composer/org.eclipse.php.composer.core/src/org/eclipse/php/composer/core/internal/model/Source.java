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

import org.eclipse.php.composer.core.model.ISource;

/**
 * @author Wojciech Galanciak, 2013
 *
 */
public class Source extends Dist implements ISource {

	protected String reference;

	protected boolean isDirty;

	@Override
	public String getReference() {
		return reference;
	}

	@Override
	public void setReference(String reference) {
		this.reference = reference;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

}