/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.task.ITodoTaskPreferences;
import org.eclipse.dltk.compiler.task.TodoTaskAstParser;
import org.eclipse.dltk.validators.core.IBuildParticipant;

public class PHPTodoTaskAstParser extends TodoTaskAstParser implements
		IBuildParticipant {

	public PHPTodoTaskAstParser(ITodoTaskPreferences preferences) {
		super(preferences);
	}
	
	@Override
	protected int findCommentStart(char[] content, int begin, int end) {
		// TODO - this code recognizes only tasks starting with  //
		// need to add handling for multiple line comments  
		
		begin = skipSpaces(content, begin, end);
		if (begin + 1 < end && content[begin] == '/' && content[begin+1] == '/') {
			return begin + 2;
		} else if(begin < end && content[begin] == '#') {
			return begin + 1; 
		} else {
			return -1;
		}						
	}
	private static int skipSpaces(char[] content, int pos, final int end) {
		while (pos < end && Character.isWhitespace(content[pos])) {
			++pos;
		}
		return pos;
	}

}
