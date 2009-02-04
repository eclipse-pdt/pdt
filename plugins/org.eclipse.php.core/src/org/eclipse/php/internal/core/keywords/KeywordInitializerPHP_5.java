package org.eclipse.php.internal.core.keywords;

import java.util.Collection;

import org.eclipse.php.internal.core.keywords.PHPKeywords.Context;
import org.eclipse.php.internal.core.keywords.PHPKeywords.KeywordData;

/**
 * Keywords initializer for PHP 5.1/5.2
 */
public class KeywordInitializerPHP_5 extends KeywordInitializerPHP_4 {

	public void initialize(Collection<KeywordData> list) {
		super.initialize(list);
		
		list.add(new KeywordData("abstract", WHITESPACE_SUFFIX, 1, Context.CLASS_BODY));
		list.add(new KeywordData("catch", WHITESPACE_PARENTESES_SUFFIX, 2));
		list.add(new KeywordData("clone", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("final", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("implements", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("instanceof", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("interface", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("private", WHITESPACE_SUFFIX, 1, Context.CLASS_BODY));
		list.add(new KeywordData("protected", WHITESPACE_SUFFIX, 1, Context.CLASS_BODY));
		list.add(new KeywordData("public", WHITESPACE_SUFFIX, 1, Context.CLASS_BODY));
		list.add(new KeywordData("self", PAAMAYIM_NEKUDOTAYIM_SUFFIX, 2));
		list.add(new KeywordData("throw", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("try", OPEN_BLOCK_SUFFIX, 2));
	}
	
	public void initializeSpecific(Collection<KeywordData> list) {
	}
}
