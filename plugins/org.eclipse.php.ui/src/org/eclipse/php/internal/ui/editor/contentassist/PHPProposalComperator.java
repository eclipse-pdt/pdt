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

package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.Comparator;

import org.eclipse.jface.text.contentassist.ICompletionProposal;

/*
 * Compares between two PHP proposal elements - according to the type
 * @author Roy, 2006
 */
public class PHPProposalComperator implements Comparator {

	public int compare(Object o1, Object o2) {
		// type checking
		if (!(o1 instanceof ICompletionProposal && o2 instanceof ICompletionProposal)) {
			throw new IllegalArgumentException("PHPProposalComperator can get only ICompletionProposal"); //$NON-NLS-1$
		}
		
		ICompletionProposal element1 = (ICompletionProposal) o1;
		ICompletionProposal element2 = (ICompletionProposal) o2;
		
		return compareDisplayString(element1, element2) ;
	}

	/**
	 * fixed bug 179509
	 * Compare strings in ICompletionProposal with the exception that string start with '_' come after 'a-z'
	 */
	private int compareDisplayString(ICompletionProposal element1, ICompletionProposal element2) {
		int result = 0;
		String displayString1 = element1.getDisplayString();
		String displayString2 = element2.getDisplayString();
		if (displayString1.charAt(0) == '_' && displayString2.charAt(0) != '_') {
			result = 1;
		} else if (displayString2.charAt(0) == '_' && displayString1.charAt(0) != '_') {
			result = -1;
		} else {
			result = displayString1.compareToIgnoreCase(displayString2);
		}		
		return result;
	}

}
