/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.sourcelookup;

import java.io.File;

import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.Openable;
import org.eclipse.dltk.internal.core.util.HandleFactory;
import org.eclipse.dltk.internal.ui.search.DLTKSearchScopeFactory;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.zend.model.PHPStackFrame;

/**
 * The PHP source lookup participant knows how to translate a PHP stack frame
 * into a source file name
 */
public class PHPSourceLookupParticipant extends AbstractSourceLookupParticipant {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.internal.core.sourcelookup.ISourceLookupParticipant
	 * #getSourceName(java.lang.Object)
	 */
	public String getSourceName(Object object) throws CoreException {
		if (object instanceof PHPStackFrame) {
			return ((PHPStackFrame) object).getSourceName();
		}
		if (object instanceof DBGpStackFrame) {
			String src = ((DBGpStackFrame) object).getSourceName();
			if (src == null) {
				src = ((DBGpStackFrame) object).getQualifiedFile();
				IPath p = new Path(src);
				src = p.lastSegment();
			}
			return src;
		}
		return null;
	}

	public Object[] findSourceElements(Object object) throws CoreException {
		Object[] sourceElements = EMPTY;
		try {
			sourceElements = super.findSourceElements(object);
		} catch (Throwable e) {
			// Check if the lookup failed because the source is outside the
			// workspace.
		}

		if (sourceElements == EMPTY) {
			// If the lookup returned an empty elements array, check if the
			// source is outside the workspace.
			String fileName = null;
			if (object instanceof PHPStackFrame) {
				fileName = ((PHPStackFrame) object).getSourceName();
			} else if (object instanceof DBGpStackFrame) {
				fileName = ((DBGpStackFrame) object).getQualifiedFile();
			}

			if (fileName != null) {
				HandleFactory fac = new HandleFactory();
				IDLTKSearchScope scope = DLTKSearchScopeFactory.getInstance()
						.createWorkspaceScope(true,
								PHPLanguageToolkit.getDefault());
				IPath localPath = EnvironmentPathUtils.getFile(
						LocalEnvironment.getInstance(), new Path(fileName))
						.getFullPath();
				Openable openable = fac.createOpenable(localPath.toString(),
						scope);
				if (openable instanceof IStorage) {
					return new Object[] { openable };
				}

				File file = new File(fileName);
				if (file.exists()) {
					return new Object[] { new LocalFile(file) };
				}
				return EMPTY;
			}
		}
		return sourceElements;
	}

}
