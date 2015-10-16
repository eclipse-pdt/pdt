/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ICallHierarchyFactory;
import org.eclipse.dltk.core.ICallProcessor;
import org.eclipse.dltk.core.ICalleeProcessor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.php.internal.core.search.PHPCallProcessor;
import org.eclipse.php.internal.core.search.PHPCalleeProcessor;

public class PHPCallHierarchyFactory implements ICallHierarchyFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.core.ICallHierarchyFactory#createCalleeProcessor(org
	 * .eclipse.dltk.core.IMethod, org.eclipse.core.runtime.IProgressMonitor,
	 * org.eclipse.dltk.core.search.IDLTKSearchScope)
	 */
	@Override
	public ICalleeProcessor createCalleeProcessor(IMethod method, IProgressMonitor monitor, IDLTKSearchScope scope) {
		return new PHPCalleeProcessor(method, monitor, scope);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.core.ICallHierarchyFactory#createCallProcessor()
	 */
	@Override
	public ICallProcessor createCallProcessor() {
		return new PHPCallProcessor();
	}

}
