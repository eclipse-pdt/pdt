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
package org.eclipse.php.internal.core.ast.rewrite;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.AST;

import java_cup.runtime.Symbol;

/**
 * A class that holds all the relevant PHP4/PHP5 Symbols that are in use in the
 * {@link ASTRewriteAnalyzer}.
 * 
 * @author shalom
 */
public class SymbolsProvider {

	// Symbols ids.
	public static final int DOT_SYMBOL_ID = 0;
	public static final int LESS_ID = 1;
	public static final int GREATER_ID = 2;
	public static final int RBRACKET_ID = 3;
	public static final int LBRACKET_ID = 4;
	public static final int RBRACE_ID = 5;
	public static final int LBRACE_ID = 6;
	public static final int RPAREN_ID = 7;
	public static final int LPAREN_ID = 8;
	public static final int INTERFACE_ID = 9; // PHP 5 only
	public static final int CLASS_ID = 10;
	public static final int RETURN_ID = 11;
	public static final int BREAK_ID = 12;
	public static final int CONTINUE_ID = 13;
	public static final int DO_ID = 14;
	public static final int WHILE_ID = 15;
	public static final int SEMICOLON_ID = 16;
	public static final int THROW_ID = 17; // PHP 5 only
	public static final int NEW_ID = 18;
	public static final int ELSE_ID = 19;
	public static final int IMPLEMENTS_ID = 20;
	public static final int END_IF_ID = 21;
	public static final int END_FOR_ID = 22;
	public static final int END_WHILE_ID = 23;
	public static final int END_FOREACH_ID = 24;
	public static final int END_SWITCH_ID = 25;
	public static final int OBJECT_OP_SYMBOL_ID = 26;
	public static final int YIELD_ID = 27;

	public static final Symbol ERROR_SYMBOL = new Symbol(Integer.MIN_VALUE);

	// Pre-defined PHP 7.1 Symbols.
	private static final Symbol OBJECT_OP_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_OBJECT_OPERATOR);
	private static final Symbol DOT_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_NEKUDA);
	private static final Symbol LESS_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_RGREATER);
	private static final Symbol GREATER_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_LGREATER);
	private static final Symbol RBRACKET_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_CLOSE_RECT);
	private static final Symbol LBRACKET_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_OPEN_RECT);
	private static final Symbol RBRACE_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_CURLY_CLOSE);
	private static final Symbol LBRACE_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_CURLY_OPEN);
	private static final Symbol LPAREN_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_OPEN_PARENTHESE);
	private static final Symbol RPAREN_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_CLOSE_PARENTHESE);
	private static final Symbol INTERFACE_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_INTERFACE);
	private static final Symbol CLASS_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_CLASS);
	private static final Symbol RETURN_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_RETURN);
	private static final Symbol BREAK_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_BREAK);
	private static final Symbol CONTINUE_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_CONTINUE);
	private static final Symbol DO_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_DO);
	private static final Symbol WHILE_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_WHILE);
	private static final Symbol SEMICOLON_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_SEMICOLON);
	private static final Symbol THROW_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_THROW);
	private static final Symbol NEW_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_NEW);
	private static final Symbol ELSE_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ELSE);
	private static final Symbol IMPLEMENTS_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_IMPLEMENTS);
	private static final Symbol END_IF_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ENDIF);
	private static final Symbol END_FOR_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ENDFOR);
	private static final Symbol END_WHILE_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ENDWHILE);
	private static final Symbol END_FOREACH_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ENDFOREACH);
	private static final Symbol END_SWITCH_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ENDSWITCH);
	private static final Symbol YIELD_SYMBOL_PHP71 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_YIELD);

	// Pre-defined PHP 7 Symbols.
	private static final Symbol OBJECT_OP_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_OBJECT_OPERATOR);
	private static final Symbol DOT_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_NEKUDA);
	private static final Symbol LESS_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_RGREATER);
	private static final Symbol GREATER_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_LGREATER);
	private static final Symbol RBRACKET_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_CLOSE_RECT);
	private static final Symbol LBRACKET_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_OPEN_RECT);
	private static final Symbol RBRACE_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_CURLY_CLOSE);
	private static final Symbol LBRACE_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_CURLY_OPEN);
	private static final Symbol LPAREN_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_OPEN_PARENTHESE);
	private static final Symbol RPAREN_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_CLOSE_PARENTHESE);
	private static final Symbol INTERFACE_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_INTERFACE);
	private static final Symbol CLASS_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_CLASS);
	private static final Symbol RETURN_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_RETURN);
	private static final Symbol BREAK_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_BREAK);
	private static final Symbol CONTINUE_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_CONTINUE);
	private static final Symbol DO_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_DO);
	private static final Symbol WHILE_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_WHILE);
	private static final Symbol SEMICOLON_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_SEMICOLON);
	private static final Symbol THROW_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_THROW);
	private static final Symbol NEW_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_NEW);
	private static final Symbol ELSE_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ELSE);
	private static final Symbol IMPLEMENTS_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_IMPLEMENTS);
	private static final Symbol END_IF_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ENDIF);
	private static final Symbol END_FOR_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ENDFOR);
	private static final Symbol END_WHILE_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ENDWHILE);
	private static final Symbol END_FOREACH_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ENDFOREACH);
	private static final Symbol END_SWITCH_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ENDSWITCH);
	private static final Symbol YIELD_SYMBOL_PHP7 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_YIELD);

	// Pre-defined PHP 56 Symbols.
	private static final Symbol OBJECT_OP_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_OBJECT_OPERATOR);
	private static final Symbol DOT_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_NEKUDA);
	private static final Symbol LESS_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_RGREATER);
	private static final Symbol GREATER_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_LGREATER);
	private static final Symbol RBRACKET_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_CLOSE_RECT);
	private static final Symbol LBRACKET_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_OPEN_RECT);
	private static final Symbol RBRACE_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_CURLY_CLOSE);
	private static final Symbol LBRACE_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_CURLY_OPEN);
	private static final Symbol LPAREN_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_OPEN_PARENTHESE);
	private static final Symbol RPAREN_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_CLOSE_PARENTHESE);
	private static final Symbol INTERFACE_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_INTERFACE);
	private static final Symbol CLASS_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_CLASS);
	private static final Symbol RETURN_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_RETURN);
	private static final Symbol BREAK_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_BREAK);
	private static final Symbol CONTINUE_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_CONTINUE);
	private static final Symbol DO_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_DO);
	private static final Symbol WHILE_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_WHILE);
	private static final Symbol SEMICOLON_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_SEMICOLON);
	private static final Symbol THROW_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_THROW);
	private static final Symbol NEW_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_NEW);
	private static final Symbol ELSE_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ELSE);
	private static final Symbol IMPLEMENTS_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_IMPLEMENTS);
	private static final Symbol END_IF_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ENDIF);
	private static final Symbol END_FOR_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ENDFOR);
	private static final Symbol END_WHILE_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ENDWHILE);
	private static final Symbol END_FOREACH_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ENDFOREACH);
	private static final Symbol END_SWITCH_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ENDSWITCH);
	private static final Symbol YIELD_SYMBOL_PHP56 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_YIELD);

	// Pre-defined PHP 55 Symbols.
	private static final Symbol OBJECT_OP_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_OBJECT_OPERATOR);
	private static final Symbol DOT_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_NEKUDA);
	private static final Symbol LESS_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_RGREATER);
	private static final Symbol GREATER_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_LGREATER);
	private static final Symbol RBRACKET_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_CLOSE_RECT);
	private static final Symbol LBRACKET_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_OPEN_RECT);
	private static final Symbol RBRACE_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_CURLY_CLOSE);
	private static final Symbol LBRACE_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_CURLY_OPEN);
	private static final Symbol LPAREN_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_OPEN_PARENTHESE);
	private static final Symbol RPAREN_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_CLOSE_PARENTHESE);
	private static final Symbol INTERFACE_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_INTERFACE);
	private static final Symbol CLASS_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_CLASS);
	private static final Symbol RETURN_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_RETURN);
	private static final Symbol BREAK_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_BREAK);
	private static final Symbol CONTINUE_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_CONTINUE);
	private static final Symbol DO_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_DO);
	private static final Symbol WHILE_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_WHILE);
	private static final Symbol SEMICOLON_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_SEMICOLON);
	private static final Symbol THROW_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_THROW);
	private static final Symbol NEW_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_NEW);
	private static final Symbol ELSE_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ELSE);
	private static final Symbol IMPLEMENTS_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_IMPLEMENTS);
	private static final Symbol END_IF_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ENDIF);
	private static final Symbol END_FOR_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ENDFOR);
	private static final Symbol END_WHILE_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ENDWHILE);
	private static final Symbol END_FOREACH_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ENDFOREACH);
	private static final Symbol END_SWITCH_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ENDSWITCH);
	private static final Symbol YIELD_SYMBOL_PHP55 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_YIELD);

	// Pre-defined PHP 54 Symbols.
	private static final Symbol OBJECT_OP_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_OBJECT_OPERATOR);
	private static final Symbol DOT_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_NEKUDA);
	private static final Symbol LESS_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_RGREATER);
	private static final Symbol GREATER_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_LGREATER);
	private static final Symbol RBRACKET_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_CLOSE_RECT);
	private static final Symbol LBRACKET_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_OPEN_RECT);
	private static final Symbol RBRACE_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_CURLY_CLOSE);
	private static final Symbol LBRACE_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_CURLY_OPEN);
	private static final Symbol LPAREN_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_OPEN_PARENTHESE);
	private static final Symbol RPAREN_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_CLOSE_PARENTHESE);
	private static final Symbol INTERFACE_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_INTERFACE);
	private static final Symbol CLASS_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_CLASS);
	private static final Symbol RETURN_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_RETURN);
	private static final Symbol BREAK_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_BREAK);
	private static final Symbol CONTINUE_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_CONTINUE);
	private static final Symbol DO_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_DO);
	private static final Symbol WHILE_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_WHILE);
	private static final Symbol SEMICOLON_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_SEMICOLON);
	private static final Symbol THROW_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_THROW);
	private static final Symbol NEW_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_NEW);
	private static final Symbol ELSE_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ELSE);
	private static final Symbol IMPLEMENTS_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_IMPLEMENTS);
	private static final Symbol END_IF_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ENDIF);
	private static final Symbol END_FOR_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ENDFOR);
	private static final Symbol END_WHILE_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ENDWHILE);
	private static final Symbol END_FOREACH_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ENDFOREACH);
	private static final Symbol END_SWITCH_SYMBOL_PHP54 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ENDSWITCH);

	// Pre-defined PHP 53 Symbols.
	private static final Symbol OBJECT_OP_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_OBJECT_OPERATOR);
	private static final Symbol DOT_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_NEKUDA);
	private static final Symbol LESS_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_RGREATER);
	private static final Symbol GREATER_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_LGREATER);
	private static final Symbol RBRACKET_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_CLOSE_RECT);
	private static final Symbol LBRACKET_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_OPEN_RECT);
	private static final Symbol RBRACE_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_CURLY_CLOSE);
	private static final Symbol LBRACE_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_CURLY_OPEN);
	private static final Symbol LPAREN_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_OPEN_PARENTHESE);
	private static final Symbol RPAREN_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_CLOSE_PARENTHESE);
	private static final Symbol INTERFACE_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_INTERFACE);
	private static final Symbol CLASS_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_CLASS);
	private static final Symbol RETURN_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_RETURN);
	private static final Symbol BREAK_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_BREAK);
	private static final Symbol CONTINUE_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_CONTINUE);
	private static final Symbol DO_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_DO);
	private static final Symbol WHILE_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_WHILE);
	private static final Symbol SEMICOLON_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_SEMICOLON);
	private static final Symbol THROW_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_THROW);
	private static final Symbol NEW_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_NEW);
	private static final Symbol ELSE_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ELSE);
	private static final Symbol IMPLEMENTS_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_IMPLEMENTS);
	private static final Symbol END_IF_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ENDIF);
	private static final Symbol END_FOR_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ENDFOR);
	private static final Symbol END_WHILE_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ENDWHILE);
	private static final Symbol END_FOREACH_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ENDFOREACH);
	private static final Symbol END_SWITCH_SYMBOL_PHP53 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ENDSWITCH);

	// Pre-defined PHP 5 Symbols.
	private static final Symbol OBJECT_OP_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_OBJECT_OPERATOR);
	private static final Symbol DOT_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_NEKUDA);
	private static final Symbol LESS_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_RGREATER);
	private static final Symbol GREATER_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_LGREATER);
	private static final Symbol RBRACKET_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_CLOSE_RECT);
	private static final Symbol LBRACKET_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_OPEN_RECT);
	private static final Symbol RBRACE_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_CURLY_CLOSE);
	private static final Symbol LBRACE_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_CURLY_OPEN);
	private static final Symbol LPAREN_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_OPEN_PARENTHESE);
	private static final Symbol RPAREN_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_CLOSE_PARENTHESE);
	private static final Symbol INTERFACE_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_INTERFACE);
	private static final Symbol CLASS_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_CLASS);
	private static final Symbol RETURN_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_RETURN);
	private static final Symbol BREAK_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_BREAK);
	private static final Symbol CONTINUE_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_CONTINUE);
	private static final Symbol DO_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_DO);
	private static final Symbol WHILE_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_WHILE);
	private static final Symbol SEMICOLON_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_SEMICOLON);
	private static final Symbol THROW_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_THROW);
	private static final Symbol NEW_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_NEW);
	private static final Symbol ELSE_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ELSE);
	private static final Symbol IMPLEMENTS_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_IMPLEMENTS);
	private static final Symbol END_IF_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ENDIF);
	private static final Symbol END_FOR_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ENDFOR);
	private static final Symbol END_WHILE_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ENDWHILE);
	private static final Symbol END_FOREACH_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ENDFOREACH);
	private static final Symbol END_SWITCH_SYMBOL_PHP5 = new Symbol(
			org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ENDSWITCH);

	/**
	 * Returns a {@link Symbol} that holds a sym id that is computed by
	 * identifying the correct id for the given php version. The given symID
	 * should be one of this class ID constants, and the PHP version String
	 * should be one of the {@link AST} defined PHP constants. The returned
	 * Symbol will always be a non-null Symbol. In case the Symbol was not
	 * recognized, an {@link SymbolsProvider#ERROR_SYMBOL} will be returned.
	 * 
	 * @param symID
	 *            Symbol ID - One of this class constants.
	 * @param phpVersion
	 *            PHP version String.
	 * @return A Symbol reference for the given id and php version.
	 */
	public static Symbol getSymbol(int symID, PHPVersion phpVersion) {
		if (PHPVersion.PHP5 == phpVersion) {
			switch (symID) {
			case DOT_SYMBOL_ID:
				return DOT_SYMBOL_PHP5;
			case LESS_ID:
				return LESS_SYMBOL_PHP5;
			case GREATER_ID:
				return GREATER_SYMBOL_PHP5;
			case RBRACKET_ID:
				return RBRACKET_SYMBOL_PHP5;
			case LBRACKET_ID:
				return LBRACKET_SYMBOL_PHP5;
			case RBRACE_ID:
				return RBRACE_SYMBOL_PHP5;
			case LBRACE_ID:
				return LBRACE_SYMBOL_PHP5;
			case RPAREN_ID:
				return RPAREN_SYMBOL_PHP5;
			case LPAREN_ID:
				return LPAREN_SYMBOL_PHP5;
			case INTERFACE_ID:
				return INTERFACE_SYMBOL_PHP5;
			case CLASS_ID:
				return CLASS_SYMBOL_PHP5;
			case RETURN_ID:
				return RETURN_SYMBOL_PHP5;
			case BREAK_ID:
				return BREAK_SYMBOL_PHP5;
			case CONTINUE_ID:
				return CONTINUE_SYMBOL_PHP5;
			case DO_ID:
				return DO_SYMBOL_PHP5;
			case WHILE_ID:
				return WHILE_SYMBOL_PHP5;
			case SEMICOLON_ID:
				return SEMICOLON_SYMBOL_PHP5;
			case THROW_ID:
				return THROW_SYMBOL_PHP5;
			case NEW_ID:
				return NEW_SYMBOL_PHP5;
			case ELSE_ID:
				return ELSE_SYMBOL_PHP5;
			case IMPLEMENTS_ID:
				return IMPLEMENTS_SYMBOL_PHP5;
			case END_FOR_ID:
				return END_FOR_SYMBOL_PHP5;
			case END_FOREACH_ID:
				return END_FOREACH_SYMBOL_PHP5;
			case END_IF_ID:
				return END_IF_SYMBOL_PHP5;
			case END_WHILE_ID:
				return END_WHILE_SYMBOL_PHP5;
			case END_SWITCH_ID:
				return END_SWITCH_SYMBOL_PHP5;
			case OBJECT_OP_SYMBOL_ID:
				return OBJECT_OP_SYMBOL_PHP5;
			default:
				break;
			}
		}
		if (PHPVersion.PHP5_3 == phpVersion) {
			switch (symID) {
			case DOT_SYMBOL_ID:
				return DOT_SYMBOL_PHP53;
			case LESS_ID:
				return LESS_SYMBOL_PHP53;
			case GREATER_ID:
				return GREATER_SYMBOL_PHP53;
			case RBRACKET_ID:
				return RBRACKET_SYMBOL_PHP53;
			case LBRACKET_ID:
				return LBRACKET_SYMBOL_PHP53;
			case RBRACE_ID:
				return RBRACE_SYMBOL_PHP53;
			case LBRACE_ID:
				return LBRACE_SYMBOL_PHP53;
			case RPAREN_ID:
				return RPAREN_SYMBOL_PHP53;
			case LPAREN_ID:
				return LPAREN_SYMBOL_PHP53;
			case INTERFACE_ID:
				return INTERFACE_SYMBOL_PHP53;
			case CLASS_ID:
				return CLASS_SYMBOL_PHP53;
			case RETURN_ID:
				return RETURN_SYMBOL_PHP53;
			case BREAK_ID:
				return BREAK_SYMBOL_PHP53;
			case CONTINUE_ID:
				return CONTINUE_SYMBOL_PHP53;
			case DO_ID:
				return DO_SYMBOL_PHP53;
			case WHILE_ID:
				return WHILE_SYMBOL_PHP53;
			case SEMICOLON_ID:
				return SEMICOLON_SYMBOL_PHP53;
			case THROW_ID:
				return THROW_SYMBOL_PHP53;
			case NEW_ID:
				return NEW_SYMBOL_PHP53;
			case ELSE_ID:
				return ELSE_SYMBOL_PHP53;
			case IMPLEMENTS_ID:
				return IMPLEMENTS_SYMBOL_PHP53;
			case END_FOR_ID:
				return END_FOR_SYMBOL_PHP53;
			case END_FOREACH_ID:
				return END_FOREACH_SYMBOL_PHP53;
			case END_IF_ID:
				return END_IF_SYMBOL_PHP53;
			case END_WHILE_ID:
				return END_WHILE_SYMBOL_PHP53;
			case END_SWITCH_ID:
				return END_SWITCH_SYMBOL_PHP53;
			case OBJECT_OP_SYMBOL_ID:
				return OBJECT_OP_SYMBOL_PHP53;
			default:
				break;
			}
		}
		if (PHPVersion.PHP5_4 == phpVersion) {
			switch (symID) {
			case DOT_SYMBOL_ID:
				return DOT_SYMBOL_PHP54;
			case LESS_ID:
				return LESS_SYMBOL_PHP54;
			case GREATER_ID:
				return GREATER_SYMBOL_PHP54;
			case RBRACKET_ID:
				return RBRACKET_SYMBOL_PHP54;
			case LBRACKET_ID:
				return LBRACKET_SYMBOL_PHP54;
			case RBRACE_ID:
				return RBRACE_SYMBOL_PHP54;
			case LBRACE_ID:
				return LBRACE_SYMBOL_PHP54;
			case RPAREN_ID:
				return RPAREN_SYMBOL_PHP54;
			case LPAREN_ID:
				return LPAREN_SYMBOL_PHP54;
			case INTERFACE_ID:
				return INTERFACE_SYMBOL_PHP54;
			case CLASS_ID:
				return CLASS_SYMBOL_PHP54;
			case RETURN_ID:
				return RETURN_SYMBOL_PHP54;
			case BREAK_ID:
				return BREAK_SYMBOL_PHP54;
			case CONTINUE_ID:
				return CONTINUE_SYMBOL_PHP54;
			case DO_ID:
				return DO_SYMBOL_PHP54;
			case WHILE_ID:
				return WHILE_SYMBOL_PHP54;
			case SEMICOLON_ID:
				return SEMICOLON_SYMBOL_PHP54;
			case THROW_ID:
				return THROW_SYMBOL_PHP54;
			case NEW_ID:
				return NEW_SYMBOL_PHP54;
			case ELSE_ID:
				return ELSE_SYMBOL_PHP54;
			case IMPLEMENTS_ID:
				return IMPLEMENTS_SYMBOL_PHP54;
			case END_FOR_ID:
				return END_FOR_SYMBOL_PHP54;
			case END_FOREACH_ID:
				return END_FOREACH_SYMBOL_PHP54;
			case END_IF_ID:
				return END_IF_SYMBOL_PHP54;
			case END_WHILE_ID:
				return END_WHILE_SYMBOL_PHP54;
			case END_SWITCH_ID:
				return END_SWITCH_SYMBOL_PHP54;
			case OBJECT_OP_SYMBOL_ID:
				return OBJECT_OP_SYMBOL_PHP54;
			default:
				break;
			}
		}

		if (PHPVersion.PHP5_5 == phpVersion) {
			switch (symID) {
			case DOT_SYMBOL_ID:
				return DOT_SYMBOL_PHP55;
			case LESS_ID:
				return LESS_SYMBOL_PHP55;
			case GREATER_ID:
				return GREATER_SYMBOL_PHP55;
			case RBRACKET_ID:
				return RBRACKET_SYMBOL_PHP55;
			case LBRACKET_ID:
				return LBRACKET_SYMBOL_PHP55;
			case RBRACE_ID:
				return RBRACE_SYMBOL_PHP55;
			case LBRACE_ID:
				return LBRACE_SYMBOL_PHP55;
			case RPAREN_ID:
				return RPAREN_SYMBOL_PHP55;
			case LPAREN_ID:
				return LPAREN_SYMBOL_PHP55;
			case INTERFACE_ID:
				return INTERFACE_SYMBOL_PHP55;
			case CLASS_ID:
				return CLASS_SYMBOL_PHP55;
			case RETURN_ID:
				return RETURN_SYMBOL_PHP55;
			case BREAK_ID:
				return BREAK_SYMBOL_PHP55;
			case CONTINUE_ID:
				return CONTINUE_SYMBOL_PHP55;
			case DO_ID:
				return DO_SYMBOL_PHP55;
			case WHILE_ID:
				return WHILE_SYMBOL_PHP55;
			case SEMICOLON_ID:
				return SEMICOLON_SYMBOL_PHP55;
			case THROW_ID:
				return THROW_SYMBOL_PHP55;
			case NEW_ID:
				return NEW_SYMBOL_PHP55;
			case ELSE_ID:
				return ELSE_SYMBOL_PHP55;
			case IMPLEMENTS_ID:
				return IMPLEMENTS_SYMBOL_PHP55;
			case END_FOR_ID:
				return END_FOR_SYMBOL_PHP55;
			case END_FOREACH_ID:
				return END_FOREACH_SYMBOL_PHP55;
			case END_IF_ID:
				return END_IF_SYMBOL_PHP55;
			case END_WHILE_ID:
				return END_WHILE_SYMBOL_PHP55;
			case END_SWITCH_ID:
				return END_SWITCH_SYMBOL_PHP55;
			case OBJECT_OP_SYMBOL_ID:
				return OBJECT_OP_SYMBOL_PHP55;
			case YIELD_ID:
				return YIELD_SYMBOL_PHP55;
			default:
				break;
			}
		}

		if (PHPVersion.PHP5_6 == phpVersion) {
			switch (symID) {
			case DOT_SYMBOL_ID:
				return DOT_SYMBOL_PHP56;
			case LESS_ID:
				return LESS_SYMBOL_PHP56;
			case GREATER_ID:
				return GREATER_SYMBOL_PHP56;
			case RBRACKET_ID:
				return RBRACKET_SYMBOL_PHP56;
			case LBRACKET_ID:
				return LBRACKET_SYMBOL_PHP56;
			case RBRACE_ID:
				return RBRACE_SYMBOL_PHP56;
			case LBRACE_ID:
				return LBRACE_SYMBOL_PHP56;
			case RPAREN_ID:
				return RPAREN_SYMBOL_PHP56;
			case LPAREN_ID:
				return LPAREN_SYMBOL_PHP56;
			case INTERFACE_ID:
				return INTERFACE_SYMBOL_PHP56;
			case CLASS_ID:
				return CLASS_SYMBOL_PHP56;
			case RETURN_ID:
				return RETURN_SYMBOL_PHP56;
			case BREAK_ID:
				return BREAK_SYMBOL_PHP56;
			case CONTINUE_ID:
				return CONTINUE_SYMBOL_PHP56;
			case DO_ID:
				return DO_SYMBOL_PHP56;
			case WHILE_ID:
				return WHILE_SYMBOL_PHP56;
			case SEMICOLON_ID:
				return SEMICOLON_SYMBOL_PHP56;
			case THROW_ID:
				return THROW_SYMBOL_PHP56;
			case NEW_ID:
				return NEW_SYMBOL_PHP56;
			case ELSE_ID:
				return ELSE_SYMBOL_PHP56;
			case IMPLEMENTS_ID:
				return IMPLEMENTS_SYMBOL_PHP56;
			case END_FOR_ID:
				return END_FOR_SYMBOL_PHP56;
			case END_FOREACH_ID:
				return END_FOREACH_SYMBOL_PHP56;
			case END_IF_ID:
				return END_IF_SYMBOL_PHP56;
			case END_WHILE_ID:
				return END_WHILE_SYMBOL_PHP56;
			case END_SWITCH_ID:
				return END_SWITCH_SYMBOL_PHP56;
			case OBJECT_OP_SYMBOL_ID:
				return OBJECT_OP_SYMBOL_PHP56;
			case YIELD_ID:
				return YIELD_SYMBOL_PHP56;
			default:
				break;
			}
		}

		if (PHPVersion.PHP7_0 == phpVersion) {
			switch (symID) {
			case DOT_SYMBOL_ID:
				return DOT_SYMBOL_PHP7;
			case LESS_ID:
				return LESS_SYMBOL_PHP7;
			case GREATER_ID:
				return GREATER_SYMBOL_PHP7;
			case RBRACKET_ID:
				return RBRACKET_SYMBOL_PHP7;
			case LBRACKET_ID:
				return LBRACKET_SYMBOL_PHP7;
			case RBRACE_ID:
				return RBRACE_SYMBOL_PHP7;
			case LBRACE_ID:
				return LBRACE_SYMBOL_PHP7;
			case RPAREN_ID:
				return RPAREN_SYMBOL_PHP7;
			case LPAREN_ID:
				return LPAREN_SYMBOL_PHP7;
			case INTERFACE_ID:
				return INTERFACE_SYMBOL_PHP7;
			case CLASS_ID:
				return CLASS_SYMBOL_PHP7;
			case RETURN_ID:
				return RETURN_SYMBOL_PHP7;
			case BREAK_ID:
				return BREAK_SYMBOL_PHP7;
			case CONTINUE_ID:
				return CONTINUE_SYMBOL_PHP7;
			case DO_ID:
				return DO_SYMBOL_PHP7;
			case WHILE_ID:
				return WHILE_SYMBOL_PHP7;
			case SEMICOLON_ID:
				return SEMICOLON_SYMBOL_PHP7;
			case THROW_ID:
				return THROW_SYMBOL_PHP7;
			case NEW_ID:
				return NEW_SYMBOL_PHP7;
			case ELSE_ID:
				return ELSE_SYMBOL_PHP7;
			case IMPLEMENTS_ID:
				return IMPLEMENTS_SYMBOL_PHP7;
			case END_FOR_ID:
				return END_FOR_SYMBOL_PHP7;
			case END_FOREACH_ID:
				return END_FOREACH_SYMBOL_PHP7;
			case END_IF_ID:
				return END_IF_SYMBOL_PHP7;
			case END_WHILE_ID:
				return END_WHILE_SYMBOL_PHP7;
			case END_SWITCH_ID:
				return END_SWITCH_SYMBOL_PHP7;
			case OBJECT_OP_SYMBOL_ID:
				return OBJECT_OP_SYMBOL_PHP7;
			case YIELD_ID:
				return YIELD_SYMBOL_PHP7;
			default:
				break;
			}
		}

		if (PHPVersion.PHP7_1 == phpVersion) {
			switch (symID) {
			case DOT_SYMBOL_ID:
				return DOT_SYMBOL_PHP71;
			case LESS_ID:
				return LESS_SYMBOL_PHP71;
			case GREATER_ID:
				return GREATER_SYMBOL_PHP71;
			case RBRACKET_ID:
				return RBRACKET_SYMBOL_PHP71;
			case LBRACKET_ID:
				return LBRACKET_SYMBOL_PHP71;
			case RBRACE_ID:
				return RBRACE_SYMBOL_PHP71;
			case LBRACE_ID:
				return LBRACE_SYMBOL_PHP71;
			case RPAREN_ID:
				return RPAREN_SYMBOL_PHP71;
			case LPAREN_ID:
				return LPAREN_SYMBOL_PHP71;
			case INTERFACE_ID:
				return INTERFACE_SYMBOL_PHP71;
			case CLASS_ID:
				return CLASS_SYMBOL_PHP71;
			case RETURN_ID:
				return RETURN_SYMBOL_PHP71;
			case BREAK_ID:
				return BREAK_SYMBOL_PHP71;
			case CONTINUE_ID:
				return CONTINUE_SYMBOL_PHP71;
			case DO_ID:
				return DO_SYMBOL_PHP71;
			case WHILE_ID:
				return WHILE_SYMBOL_PHP71;
			case SEMICOLON_ID:
				return SEMICOLON_SYMBOL_PHP71;
			case THROW_ID:
				return THROW_SYMBOL_PHP71;
			case NEW_ID:
				return NEW_SYMBOL_PHP71;
			case ELSE_ID:
				return ELSE_SYMBOL_PHP71;
			case IMPLEMENTS_ID:
				return IMPLEMENTS_SYMBOL_PHP71;
			case END_FOR_ID:
				return END_FOR_SYMBOL_PHP71;
			case END_FOREACH_ID:
				return END_FOREACH_SYMBOL_PHP71;
			case END_IF_ID:
				return END_IF_SYMBOL_PHP71;
			case END_WHILE_ID:
				return END_WHILE_SYMBOL_PHP71;
			case END_SWITCH_ID:
				return END_SWITCH_SYMBOL_PHP71;
			case OBJECT_OP_SYMBOL_ID:
				return OBJECT_OP_SYMBOL_PHP71;
			case YIELD_ID:
				return YIELD_SYMBOL_PHP71;
			default:
				break;
			}
		}
		return ERROR_SYMBOL;
	}

	/**
	 * Returns the sym integer for the given modifier ('public', 'final',
	 * 'protected' etc.).
	 * 
	 * @param modifier
	 *            The modifier string
	 * @param phpVersion
	 *            The relevant PHP version.
	 * @return The sym id or the ERROR_SYMBOL.sym in case of an error.
	 */
	public static int getModifierSym(String modifier, PHPVersion phpVersion) {
		if (PHPVersion.PHP7_1 == phpVersion) {
			if (modifier.equals("public")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_PUBLIC;
			} else if (modifier.equals("private")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_PRIVATE;
			} else if (modifier.equals("protected")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_PROTECTED;
			} else if (modifier.equals("static")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_STATIC;
			} else if (modifier.equals("abstract")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ABSTRACT;
			} else if (modifier.equals("final")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_FINAL;
			}
		} else if (PHPVersion.PHP7_0 == phpVersion) {
			if (modifier.equals("public")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_PUBLIC;
			} else if (modifier.equals("private")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_PRIVATE;
			} else if (modifier.equals("protected")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_PROTECTED;
			} else if (modifier.equals("static")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_STATIC;
			} else if (modifier.equals("abstract")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ABSTRACT;
			} else if (modifier.equals("final")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_FINAL;
			}
		} else if (PHPVersion.PHP5_6 == phpVersion) {
			if (modifier.equals("public")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_PUBLIC;
			} else if (modifier.equals("private")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_PRIVATE;
			} else if (modifier.equals("protected")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_PROTECTED;
			} else if (modifier.equals("static")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_STATIC;
			} else if (modifier.equals("abstract")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ABSTRACT;
			} else if (modifier.equals("final")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_FINAL;
			}
		} else if (PHPVersion.PHP5_5 == phpVersion) {
			if (modifier.equals("public")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_PUBLIC;
			} else if (modifier.equals("private")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_PRIVATE;
			} else if (modifier.equals("protected")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_PROTECTED;
			} else if (modifier.equals("static")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_STATIC;
			} else if (modifier.equals("abstract")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ABSTRACT;
			} else if (modifier.equals("final")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_FINAL;
			}
		} else if (PHPVersion.PHP5_4 == phpVersion) {
			if (modifier.equals("public")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_PUBLIC;
			} else if (modifier.equals("private")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_PRIVATE;
			} else if (modifier.equals("protected")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_PROTECTED;
			} else if (modifier.equals("static")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_STATIC;
			} else if (modifier.equals("abstract")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ABSTRACT;
			} else if (modifier.equals("final")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_FINAL;
			}
		} else if (PHPVersion.PHP5_3 == phpVersion) {
			if (modifier.equals("public")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_PUBLIC;
			} else if (modifier.equals("private")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_PRIVATE;
			} else if (modifier.equals("protected")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_PROTECTED;
			} else if (modifier.equals("static")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_STATIC;
			} else if (modifier.equals("abstract")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ABSTRACT;
			} else if (modifier.equals("final")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_FINAL;
			}
		} else if (PHPVersion.PHP5 == phpVersion) {
			if (modifier.equals("public")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_PUBLIC;
			} else if (modifier.equals("private")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_PRIVATE;
			} else if (modifier.equals("protected")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_PROTECTED;
			} else if (modifier.equals("static")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_STATIC;
			} else if (modifier.equals("abstract")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ABSTRACT;
			} else if (modifier.equals("final")) { //$NON-NLS-1$
				return org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_FINAL;
			}
		}
		return ERROR_SYMBOL.sym;
	}
}
