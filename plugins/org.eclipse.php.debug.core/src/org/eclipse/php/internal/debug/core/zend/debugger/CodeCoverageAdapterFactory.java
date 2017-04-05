/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPToolkitUtil;

/**
 * Code coverage adapter factory.
 */
public class CodeCoverageAdapterFactory implements IAdapterFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
	 * java.lang.Class)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if (adaptableObject.getClass() == adapterType)
			return adaptableObject;
		if (adapterType == ISourceModule.class) {
			if (adaptableObject instanceof CodeCoverageData) {
				final CodeCoverageData codeCoverageData = (CodeCoverageData) adaptableObject;
				String localFileName = codeCoverageData.getLocalFileName();
				return PHPToolkitUtil.getSourceModule(localFileName);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class[] getAdapterList() {
		return new Class[] { ISourceModule.class };
	}

}
