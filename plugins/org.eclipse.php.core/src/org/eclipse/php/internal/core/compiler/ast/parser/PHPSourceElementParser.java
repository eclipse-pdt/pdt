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
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.php.internal.core.compiler.PHPSourceElementRequestor;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPSourceElementParser extends AbstractSourceElementParser {

	private IModuleSource fSourceModule;

	public void parseSourceModule(IModuleSource module) {
		fSourceModule = module;
		super.parseSourceModule(module);
	}

	protected SourceElementRequestVisitor createVisitor() {
		return new PHPSourceElementRequestor(getRequestor(), fSourceModule);
	}

	protected String getNatureId() {
		return PHPNature.ID;
	}
}
