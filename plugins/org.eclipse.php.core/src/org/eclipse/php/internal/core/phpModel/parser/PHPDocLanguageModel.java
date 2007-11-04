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
package org.eclipse.php.internal.core.phpModel.parser;

import java.util.Arrays;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocTagData;

public class PHPDocLanguageModel {

	private static final PHPDocTagData[] phpDocTags;

	public static PHPDocTagData[] getPhpDocTagData() {
		return phpDocTags;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static CodeData[] getPHPDocTags(String startsWith) {
		return ModelSupport.getCodeDataStartingWith(phpDocTags, startsWith);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static {
		phpDocTags = new PHPDocTagData[] { PHPDocCodeDataFactory.createPHPDocTagData("abstract", "abstract"), PHPDocCodeDataFactory.createPHPDocTagData("access", "access"), PHPDocCodeDataFactory.createPHPDocTagData("author", "author"),
			PHPDocCodeDataFactory.createPHPDocTagData("category", "category"), PHPDocCodeDataFactory.createPHPDocTagData("copyright", "copyright"), PHPDocCodeDataFactory.createPHPDocTagData("deprecated", "deprecated,"), PHPDocCodeDataFactory.createPHPDocTagData("example", "example"),
			PHPDocCodeDataFactory.createPHPDocTagData("final", "final"), PHPDocCodeDataFactory.createPHPDocTagData("filesource", "filesource"), PHPDocCodeDataFactory.createPHPDocTagData("global", "global"), PHPDocCodeDataFactory.createPHPDocTagData("ignore", "ignore"),
			PHPDocCodeDataFactory.createPHPDocTagData("internal", "internal"), PHPDocCodeDataFactory.createPHPDocTagData("license", "license"), PHPDocCodeDataFactory.createPHPDocTagData("link", "link"), PHPDocCodeDataFactory.createPHPDocTagData("method", "method"),
			PHPDocCodeDataFactory.createPHPDocTagData("name", "name"), PHPDocCodeDataFactory.createPHPDocTagData("package", "package"), PHPDocCodeDataFactory.createPHPDocTagData("param", "param"), PHPDocCodeDataFactory.createPHPDocTagData("property", "property"),
			PHPDocCodeDataFactory.createPHPDocTagData("return", "return"), PHPDocCodeDataFactory.createPHPDocTagData("see", "see"), PHPDocCodeDataFactory.createPHPDocTagData("since", "since"), PHPDocCodeDataFactory.createPHPDocTagData("static", "static"),
			PHPDocCodeDataFactory.createPHPDocTagData("staticvar", "staticvar"), PHPDocCodeDataFactory.createPHPDocTagData("subpackage", "subpackage"), PHPDocCodeDataFactory.createPHPDocTagData("todo", "todo"), PHPDocCodeDataFactory.createPHPDocTagData("tutorial", "tutorial"),
			PHPDocCodeDataFactory.createPHPDocTagData("uses", "uses"), PHPDocCodeDataFactory.createPHPDocTagData("var", "var"), PHPDocCodeDataFactory.createPHPDocTagData("version", "version") };
		Arrays.sort(phpDocTags);
	}
}
