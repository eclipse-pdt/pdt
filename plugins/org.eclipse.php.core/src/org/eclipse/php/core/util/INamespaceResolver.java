/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public interface INamespaceResolver {

	void init(IProject project);

	String resolveNamespace(IPath folder);

	IPath resolveLocation(IPath target, String namespace);

	boolean isSupported();

}
