/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła -  initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;

public class BuildpathPropertyTester extends PropertyTester {

	private final String IS_ON_BUILDPATH = "isOnBuildpath"; //$NON-NLS-1$

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_ON_BUILDPATH.equals(property)) {
			if (receiver instanceof IResource) {
				IResource resource = (IResource) receiver;
				return DLTKCore.create(resource.getProject()).isOnBuildpath(resource);
			}
		}
		return false;
	}

}
