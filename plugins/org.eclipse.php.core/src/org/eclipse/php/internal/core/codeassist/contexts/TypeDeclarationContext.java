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
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a class or interface
 * declaration. <br/>
 * Examples:
 * 
 * <pre>
 *  1. class |
 *  2. class A |
 *  3. class A extends |
 *  4. class A extends B implements |
 *  5. interface A extends |
 *  6. interface A extends B|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public abstract class TypeDeclarationContext extends DeclarationContext {

	protected static final Pattern EXTENDS_PATTERN = Pattern.compile(
			"\\Wextends\\W", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	protected static final Pattern IMPLEMENTS_PATTERN = Pattern.compile(
			"\\Wimplements", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	private int typeEnd;
	private int typeIdentifierEnd;
	private boolean hasExtends;
	private boolean hasImplements;
	protected Matcher implementsMatcher;
	protected Matcher extendsMatcher;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		typeEnd = PHPTextSequenceUtilities.isInClassDeclaration(statementText);
		if (typeEnd == -1) {
			return false;
		}

		// check if we are in the class identifier part.
		typeIdentifierEnd = 0;
		for (; typeIdentifierEnd < statementText.length(); typeIdentifierEnd++) {
			if (!Character.isLetterOrDigit(statementText
					.charAt(typeIdentifierEnd))) {
				break;
			}
		}
		// we are in class identifier part.
		if (typeIdentifierEnd == statementText.length()) {
			return true;
		}

		extendsMatcher = EXTENDS_PATTERN.matcher(statementText);
		hasExtends = extendsMatcher.find();

		implementsMatcher = IMPLEMENTS_PATTERN.matcher(statementText);
		hasImplements = implementsMatcher.find();

		return true;
	}

	/**
	 * Returns the end offset of word 'class' or 'interface' in type declaration
	 * relative to the statement text.
	 * 
	 * @see #getStatementText()
	 * @return
	 */
	public int getTypeEnd() {
		return typeEnd;
	}

	/**
	 * Returns the end offset of the type identifier relative to the statement
	 * text.
	 * 
	 * @see #getStatementText()
	 * @return
	 */
	public int getTypeIdentifierEnd() {
		return typeIdentifierEnd;
	}

	/**
	 * Whether 'extends' keyword already exists in the type declaration
	 * 
	 * @return
	 */
	public boolean hasExtends() {
		return hasExtends;
	}

	/**
	 * Whether 'implements' keyword already exists in the type declaration
	 * 
	 * @return
	 */
	public boolean hasImplements() {
		return hasImplements;
	}
}
