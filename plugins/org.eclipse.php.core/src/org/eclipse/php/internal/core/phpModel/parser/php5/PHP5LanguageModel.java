/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser.php5;

import java.util.Arrays;

import org.eclipse.php.internal.core.phpModel.parser.PHPCodeDataFactory;
import org.eclipse.php.internal.core.phpModel.parser.PHPLanguageManager;
import org.eclipse.php.internal.core.phpModel.parser.PHPLanguageModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPKeywordData;

public class PHP5LanguageModel extends PHPLanguageModel {

	public static String ID = "PHP5LanguageModel";

	protected PHPKeywordData[] keyword;

	public PHP5LanguageModel(PHPLanguageManager languageManager) {
		super(languageManager);
		initKeywords();
	}

	public String getID() {
		return ID;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public PHPKeywordData[] getKeywordData() {
		return keyword;
	}

	public String getPHPVersion() {
		return PHPVersion.PHP5;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initKeywords() {
		keyword = new PHPKeywordData[] { PHPCodeDataFactory.createPHPKeywordData("exit", "()", 1), PHPCodeDataFactory.createPHPKeywordData("die", "()", 1), PHPCodeDataFactory.createPHPKeywordData("function", " ", 1), PHPCodeDataFactory.createPHPKeywordData("const", " ", 1),
			PHPCodeDataFactory.createPHPKeywordData("return", " ", 1), PHPCodeDataFactory.createPHPKeywordData("if", " ()", 2), PHPCodeDataFactory.createPHPKeywordData("endif", ";", 1), PHPCodeDataFactory.createPHPKeywordData("elseif", " ()", 2), PHPCodeDataFactory.createPHPKeywordData("else", " ", 1),
			PHPCodeDataFactory.createPHPKeywordData("while", " ()", 2), PHPCodeDataFactory.createPHPKeywordData("endwhile", ";", 1), PHPCodeDataFactory.createPHPKeywordData("do", " ", 1), PHPCodeDataFactory.createPHPKeywordData("for", " ()", 2),
			PHPCodeDataFactory.createPHPKeywordData("endfor", ";", 1), PHPCodeDataFactory.createPHPKeywordData("foreach", " ()", 2), PHPCodeDataFactory.createPHPKeywordData("endforeach", ";", 1), PHPCodeDataFactory.createPHPKeywordData("declare", "()", 1),
			PHPCodeDataFactory.createPHPKeywordData("enddeclare;", ";", 1), PHPCodeDataFactory.createPHPKeywordData("as", " ", 1), PHPCodeDataFactory.createPHPKeywordData("switch", " ()", 2), PHPCodeDataFactory.createPHPKeywordData("endswitch", ";", 1),
			PHPCodeDataFactory.createPHPKeywordData("case", " :", 2), PHPCodeDataFactory.createPHPKeywordData("default", ":", 1), PHPCodeDataFactory.createPHPKeywordData("break", "", 0), PHPCodeDataFactory.createPHPKeywordData("continue", "", 0),
			PHPCodeDataFactory.createPHPKeywordData("echo", " ", 1), PHPCodeDataFactory.createPHPKeywordData("print", " ", 1), PHPCodeDataFactory.createPHPKeywordData("class", " ", 1), PHPCodeDataFactory.createPHPKeywordData("extends", " ", 1),
			PHPCodeDataFactory.createPHPKeywordData("new", " ", 1), PHPCodeDataFactory.createPHPKeywordData("var", " ", 1), PHPCodeDataFactory.createPHPKeywordData("eval", "()", 1), PHPCodeDataFactory.createPHPKeywordData("include", "()", 1),
			PHPCodeDataFactory.createPHPKeywordData("include_once", "()", 1), PHPCodeDataFactory.createPHPKeywordData("require", "()", 1), PHPCodeDataFactory.createPHPKeywordData("require_once", "()", 1), PHPCodeDataFactory.createPHPKeywordData("global", " ", 1),
			PHPCodeDataFactory.createPHPKeywordData("isset", "()", 1), PHPCodeDataFactory.createPHPKeywordData("empty", "()", 1), PHPCodeDataFactory.createPHPKeywordData("static", " ", 1), PHPCodeDataFactory.createPHPKeywordData("unset", "()", 1),
			PHPCodeDataFactory.createPHPKeywordData("list", "()", 1), PHPCodeDataFactory.createPHPKeywordData("array", "()", 1), PHPCodeDataFactory.createPHPKeywordData("parent", "::", 2), PHPCodeDataFactory.createPHPKeywordData("true", "", 0),
			PHPCodeDataFactory.createPHPKeywordData("false", "", 0), PHPCodeDataFactory.createPHPKeywordData("null", "", 0), PHPCodeDataFactory.createPHPKeywordData("and", " ", 1), PHPCodeDataFactory.createPHPKeywordData("or", " ", 1), PHPCodeDataFactory.createPHPKeywordData("xor", " ", 1),
			PHPCodeDataFactory.createPHPKeywordData("clone", " ", 1), PHPCodeDataFactory.createPHPKeywordData("self", "::", 2), PHPCodeDataFactory.createPHPKeywordData("try", " {", 2), PHPCodeDataFactory.createPHPKeywordData("catch", " ()", 2),
			PHPCodeDataFactory.createPHPKeywordData("throw", " ", 1), PHPCodeDataFactory.createPHPKeywordData("instanceof", " ", 1), PHPCodeDataFactory.createPHPKeywordData("interface", " ", 1), PHPCodeDataFactory.createPHPKeywordData("implements", " ", 1),
			PHPCodeDataFactory.createPHPKeywordData("abstract", " ", 1), PHPCodeDataFactory.createPHPKeywordData("final", " ", 1), PHPCodeDataFactory.createPHPKeywordData("private", " ", 1), PHPCodeDataFactory.createPHPKeywordData("protected", " ", 1),
			PHPCodeDataFactory.createPHPKeywordData("public", " ", 1) };
		Arrays.sort(keyword);
	}

}