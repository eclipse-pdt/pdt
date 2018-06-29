/*******************************************************************************
 * Copyright (c) 2009, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.corext.changes;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.ltk.core.refactoring.MultiStateTextFileChange;
import org.eclipse.osgi.util.NLS;

/**
 * Multi state compilation unit change for composite refactorings.
 * 
 * @since 3.2
 */
public final class MultiStateCompilationUnitChange extends MultiStateTextFileChange {

	/** The compilation unit */
	private final ISourceModule fUnit;

	/**
	 * Creates a new multi state compilation unit change.
	 * 
	 * @param name
	 *            the name of the change
	 * @param unit
	 *            the compilation unit
	 */
	public MultiStateCompilationUnitChange(final String name, final ISourceModule unit) {
		super(name, (IFile) unit.getResource());

		fUnit = unit;

		setTextType("java"); //$NON-NLS-1$
	}

	/*
	 * @see org.eclipse.ltk.core.refactoring.Change#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public final Object getAdapter(final Class adapter) {

		if (ISourceModule.class.equals(adapter)) {
			return fUnit;
		}

		return super.getAdapter(adapter);
	}

	/**
	 * Returns the compilation unit.
	 * 
	 * @return the compilation unit
	 */
	public final ISourceModule getCompilationUnit() {
		return fUnit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return NLS.bind("{0} - {1}", //$NON-NLS-1$
				new String[] { BasicElementLabels.getFileName(fUnit),
						BasicElementLabels.getPathLabel(fUnit.getParent().getPath(), false) });
	}

}
