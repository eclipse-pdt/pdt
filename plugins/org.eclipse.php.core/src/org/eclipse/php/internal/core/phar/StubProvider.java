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

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

public class StubProvider implements IStubProvider {

	public IStub create(PharPackage jarPackage) throws CoreException {
		Assert.isNotNull(jarPackage);
		Stub manifest = new Stub(jarPackage);
		return manifest;
	}
}
