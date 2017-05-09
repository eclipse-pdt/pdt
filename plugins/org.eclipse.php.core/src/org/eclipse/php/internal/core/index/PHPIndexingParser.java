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
package org.eclipse.php.internal.core.index;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.index2.IIndexingParser;
import org.eclipse.dltk.core.index2.IIndexingRequestor;
import org.eclipse.php.internal.core.Logger;

public class PHPIndexingParser implements IIndexingParser {

	public void parseSourceModule(ISourceModule module, IIndexingRequestor requestor) {

		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(module);
		if (moduleDeclaration != null) {
			try {
				moduleDeclaration.traverse(new PHPIndexingVisitor(requestor, module));
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

}
