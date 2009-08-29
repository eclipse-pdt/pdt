/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	 *             if access to any resource described by the JAR package has
	 *             failed
	 */
	IStub create(PharPackage jarPackage) throws CoreException;
}
