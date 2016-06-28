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

import org.eclipse.php.composer.core.internal.model.Dist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Wojciech Galanciak, 2013
 *
 */
@JsonDeserialize(as=Dist.class)
@JsonSerialize(as=Dist.class)
public interface IDist {

	String getUrl();

	String getType();

	void setUrl(String url);

	void setType(String type);

	@JsonIgnore
	boolean isDirty();

}