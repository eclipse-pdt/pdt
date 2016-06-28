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

import org.eclipse.php.composer.core.internal.model.adapters.PackagistDetailedPackageDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
@JsonDeserialize(using = PackagistDetailedPackageDeserializer.class)
public class PackagistDetailedPackage extends PackagistPackage {

}
