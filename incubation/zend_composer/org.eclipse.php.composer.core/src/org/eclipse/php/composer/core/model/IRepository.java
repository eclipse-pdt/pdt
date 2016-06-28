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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public interface IRepository extends IModelElement {

	void setUrl(String url);

	String getUrl();

	void setType(RepositoryType type);

	RepositoryType getType();

	@JsonAnyGetter
	Map<String, Object> getParameters();

	@JsonAnySetter
	void setParameter(String name, Object value);

	@JsonIgnore
	boolean isDirty();
	@JsonIgnore
	String getKey();

	void setParameters(Map<String, Object> parameters);

	void setRepositoryPackage(IRepositoryPackage pkg);

	IRepositoryPackage getRepositoryPackage();

}