/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.converter;

import org.eclipse.core.databinding.conversion.Converter;

import org.eclipse.php.composer.api.ComposerPackage;

public abstract class ComposerConverter extends Converter {

	protected ComposerPackage composerPackage;

	public ComposerConverter(Object fromType, Object toType) {
		super(fromType, toType);
	}

	/**
	 * @return the composerPackage
	 */
	public ComposerPackage getComposerPackage() {
		return composerPackage;
	}

	/**
	 * @param composerPackage
	 *            the composerPackage to set
	 */
	public void setComposerPackage(ComposerPackage composerPackage) {
		this.composerPackage = composerPackage;
		composerPackageUpdated();
	}

	protected abstract void composerPackageUpdated();
}
