package org.eclipse.php.internal.core.phpModel;

import java.util.Collection;

import org.eclipse.php.internal.core.phpModel.PHPKeywords.KeywordData;

/**
 * Keywords initializer for PHP 4.x
 */
public class KeywordInitializerPHP_4 implements IPHPKeywordsInitializer {

	public void initialize(Collection<KeywordData> list) {
		list.add(new KeywordData("and", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("array", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("as", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("break", EMPTY_SUFFIX, 0));
		list.add(new KeywordData("case", WHITESPACE_COLON_SUFFIX, 2));
		list.add(new KeywordData("class", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("const", WHITESPACE_SUFFIX, 1, true));
		list.add(new KeywordData("continue", EMPTY_SUFFIX, 0));
		list.add(new KeywordData("declare", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("default", COLON_SUFFIX, 1));
		list.add(new KeywordData("die", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("do", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("echo", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("elseif", WHITESPACE_PARENTESES_SUFFIX, 2));
		list.add(new KeywordData("else", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("empty", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("enddeclare", SEMICOLON_SUFFIX, 1));
		list.add(new KeywordData("endforeach", SEMICOLON_SUFFIX, 1));
		list.add(new KeywordData("endfor", SEMICOLON_SUFFIX, 1));
		list.add(new KeywordData("endif", SEMICOLON_SUFFIX, 1));
		list.add(new KeywordData("endswitch", SEMICOLON_SUFFIX, 1));
		list.add(new KeywordData("endwhile", SEMICOLON_SUFFIX, 1));
		list.add(new KeywordData("eval", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("exit", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("extends", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("false", EMPTY_SUFFIX, 0));
		list.add(new KeywordData("foreach", WHITESPACE_PARENTESES_SUFFIX, 2));
		list.add(new KeywordData("for", WHITESPACE_PARENTESES_SUFFIX, 2));
		list.add(new KeywordData("function", WHITESPACE_SUFFIX, 1, true));
		list.add(new KeywordData("global", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("if", WHITESPACE_PARENTESES_SUFFIX, 2));
		list.add(new KeywordData("include_once", WS_QUOTES_SEMICOLON_SUFFIX, 2));
		list.add(new KeywordData("include", WS_QUOTES_SEMICOLON_SUFFIX, 2));
		list.add(new KeywordData("isset", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("list", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("new", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("null", EMPTY_SUFFIX, 0));
		list.add(new KeywordData("or", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("parent", PAAMAYIM_NEKUDOTAYIM_SUFFIX, 2));
		list.add(new KeywordData("print", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("require_once", WS_QUOTES_SEMICOLON_SUFFIX, 2));
		list.add(new KeywordData("require", WS_QUOTES_SEMICOLON_SUFFIX, 2));
		list.add(new KeywordData("return", WHITESPACE_SUFFIX, 1));
		list.add(new KeywordData("static", WHITESPACE_SUFFIX, 1, true));
		list.add(new KeywordData("switch", WHITESPACE_PARENTESES_SUFFIX, 2));
		list.add(new KeywordData("true", EMPTY_SUFFIX, 0));
		list.add(new KeywordData("unset", PARENTESES_SUFFIX, 1));
		list.add(new KeywordData("var", WHITESPACE_SUFFIX, 1, true));
		list.add(new KeywordData("while", WHITESPACE_PARENTESES_SUFFIX, 2));
		list.add(new KeywordData("xor", WHITESPACE_SUFFIX, 1));
	}

	public void initializeSpecific(Collection<KeywordData> list) {
		list.add(new KeywordData("old_function", WHITESPACE_SUFFIX, 1));
	}

}
