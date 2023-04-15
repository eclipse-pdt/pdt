/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
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
package org.eclipse.php.internal.core.compiler.ast.parser.php81;

import java.io.Reader;

import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.compiler.ast.parser.AbstractPHPSourceParser;

public class PHPSourceParser extends AbstractPHPSourceParser {

	public PHPSourceParser() {
		super();
	}

	public PHPSourceParser(String fileName) {
		super(fileName);
	}

	@Override
	public IModuleDeclaration parse(Reader in, IProblemReporter reporter, boolean isSupportingASPTags,
			boolean useShortTags) throws Exception {
		CompilerAstLexer lexer = new CompilerAstLexer(in);
		lexer.setUseAspTagsAsPHP(isSupportingASPTags);
		lexer.setUseShortTags(useShortTags);
		CompilerAstParser parser = new CompilerAstParser(lexer);
		parser.setProblemReporter(reporter);

		return parse(parser);
	}
}
