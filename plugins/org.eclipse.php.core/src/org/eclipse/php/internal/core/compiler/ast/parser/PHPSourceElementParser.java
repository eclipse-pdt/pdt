/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPSourceElementParser extends AbstractSourceElementParser {

	private char[] contents;
	private char[] filename;

	public void parseSourceModule(char[] contents, ISourceModuleInfo astCache, char[] filename) {
		this.contents = contents;
		this.filename = filename;

		super.parseSourceModule(contents, astCache, filename);
	}

	protected SourceElementRequestVisitor createVisitor() {
		return new PHPSourceElementRequestor(getRequestor(), contents, filename);
	}

	protected String getNatureId() {
		return PHPNature.ID;
	}
}
