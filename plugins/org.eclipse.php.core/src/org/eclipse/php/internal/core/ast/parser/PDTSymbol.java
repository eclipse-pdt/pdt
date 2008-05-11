package org.eclipse.php.internal.core.ast.parser;

import java_cup.runtime.Symbol;

public class PDTSymbol {

	public int left;
	public int right;
	public int sym;
	
	public PDTSymbol(Symbol symbol){
		left = symbol.left;
		right = symbol.right;
		sym = symbol.sym;
	}
}
