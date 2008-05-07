package org.eclipse.php.internal.core.ast.parser;

import java_cup.runtime.Symbol;

public class PDTSymbol {

	public int right;
	public int left;
	public int sym;
	
	public PDTSymbol(Symbol next_token) {
		right = next_token.right;
		left = next_token.left;
		sym = next_token.sym;
	}

}
