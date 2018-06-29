/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	@Override
	public void parseSourceModule(IModuleSource module) {
		fSourceModule = module;
		super.parseSourceModule(module);
	}

	@Override
	protected SourceElementRequestVisitor createVisitor() {
		return new PHPSourceElementRequestor(getRequestor(), fSourceModule);
	}

	@Override
	protected String getNatureId() {
		return PHPNature.ID;
	}
}
