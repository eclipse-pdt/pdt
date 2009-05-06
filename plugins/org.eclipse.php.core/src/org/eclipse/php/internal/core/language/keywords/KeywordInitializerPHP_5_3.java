package org.eclipse.php.internal.core.language.keywords;

import java.util.Collection;

import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * Keywords initializer for PHP 5.3
 */
public class KeywordInitializerPHP_5_3 extends KeywordInitializerPHP_5 {

	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);
		
		list.add(new KeywordData("goto", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("namespace", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("use", WHITESPACE_SUFFIX, 1));
	}

	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
