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
package org.eclipse.php.internal.core.compiler.ast.parser.php5;

import java.io.Reader;


import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.compiler.ast.parser.AbstractPHPSourceParser;

public class Php5SourceParser extends AbstractPHPSourceParser {
	
	public Php5SourceParser() {
		super();
	}

	public Php5SourceParser(String fileName) {
		super(fileName);
	}

	public ModuleDeclaration parse(Reader in, IProblemReporter reporter) throws Exception {
		Php5AstLexer lexer = new Php5AstLexer(in);
		Php5AstParser parser = new Php5AstParser(lexer);
		parser.setProblemReporter(reporter);

		return parse(parser);
	}
}
