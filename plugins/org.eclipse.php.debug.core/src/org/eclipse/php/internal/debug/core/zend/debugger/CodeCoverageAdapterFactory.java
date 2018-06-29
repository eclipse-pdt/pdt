/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
	 * java.lang.Class)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if (adaptableObject.getClass() == adapterType) {
			return adaptableObject;
		}
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
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class[] getAdapterList() {
		return new Class[] { ISourceModule.class };
	}

}
