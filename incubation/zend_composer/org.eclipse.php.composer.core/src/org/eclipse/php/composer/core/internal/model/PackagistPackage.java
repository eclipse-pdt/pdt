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
package org.eclipse.php.composer.core.internal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackagistPackage extends Package {

	public PackagistPackage() {
		super(""); //$NON-NLS-1$
	}

}
