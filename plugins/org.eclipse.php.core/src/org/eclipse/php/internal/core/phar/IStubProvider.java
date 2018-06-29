/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

import org.eclipse.core.runtime.CoreException;

public interface IStubProvider {

	/**
	 * Creates a Stub as defined by the <code>JarPackage</code>.
	 * 
	 * @param jarPackage
	 *            the JAR package specification
	 * @return the created Stub
	 * @throws CoreException
	 *             if access to any resource described by the JAR package has failed
	 */
	IStub create(PharPackage jarPackage) throws CoreException;
}
