/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.util.text.TextSequence;


/**
 * This context represents state when staying in a global statement. That means we are going to complete:
 * keywords and all global elements in this context.
 * 
 * @author michael
 */
public abstract class AbstractGlobalStatementContext extends StatementContext {
	
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		// global statement context may contain only identifier characters:
		TextSequence statementText = getStatementText();
		PHPVersion phpVersion = getPhpVersion();
		for (int i = 0; i < statementText.length(); ++i) {
			char ch = statementText.charAt(i);
			if (!Character.isJavaIdentifierPart(ch) && (phpVersion.isLessThan(PHPVersion.PHP5_3) || ch != NamespaceReference.NAMESPACE_SEPARATOR)) {
				return false;
			}
		}
		
		return true;
	}

	public boolean isExclusive() {
		return true;
	}
}
