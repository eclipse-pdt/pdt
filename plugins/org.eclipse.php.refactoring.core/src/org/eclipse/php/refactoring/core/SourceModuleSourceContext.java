/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
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
package org.eclipse.php.refactoring.core;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.dltk.internal.corext.refactoring.base.ScriptStatusContext;

public class SourceModuleSourceContext extends ScriptStatusContext {
	private ISourceModule fCUnit;
	private ISourceRange fSourceRange;

	public SourceModuleSourceContext(ISourceModule cunit, ISourceRange range) {
		fCUnit = cunit;
		fSourceRange = range;
		if (fSourceRange == null) {
			fSourceRange = new SourceRange(0, 0);
		}
	}

	@Override
	public boolean isBinary() {
		return false;
	}

	@Override
	public ISourceModule getSourceModule() {
		return fCUnit;
	}

	@Override
	public ISourceRange getSourceRange() {
		return fSourceRange;
	}

	@Override
	public String toString() {
		return getSourceRange() + " in " + super.toString(); //$NON-NLS-1$
	}
}
