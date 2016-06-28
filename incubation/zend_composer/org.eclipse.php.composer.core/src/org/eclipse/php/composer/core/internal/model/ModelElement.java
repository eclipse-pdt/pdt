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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.composer.core.model.IModelElement;
import org.eclipse.php.composer.core.model.ModelContainer;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ModelElement implements IModelElement {

	private transient ModelContainer model;

	@JsonIgnore
	protected Map<String, Object> additionalAttributes = new HashMap<String, Object>();

	@Override
	public void setModel(ModelContainer model) {
		this.model = model;
	}

	protected void updateModel() {
		if (model != null) {
			model.serialize();
		}
	}

	@Override
	public Map<String, Object> getParameters() {
		return additionalAttributes;
	}

	@Override
	public void setParameter(String name, Object value) {
		additionalAttributes.put(name, value);
	}

	@Override
	public void setParameters(Map<String, Object> parameters) {
		this.additionalAttributes = parameters;
		updateModel();
	}

}
