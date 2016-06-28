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
package org.eclipse.php.composer.core.model;

import java.util.List;

import org.eclipse.php.composer.core.internal.model.PackagistPackage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackagistSearchRoot {

	private int total;
	private String next;

	@JsonDeserialize(contentAs = PackagistPackage.class)
	private List<IPackage> results;

	public String getNext() {
		return next;
	}

	public int getTotal() {
		return total;
	}

	public List<IPackage> getResults() {
		return results;
	}

}
