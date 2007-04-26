/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
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
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPKeywordData;

/*
 * Compares between two PHP proposal elements - according to the type
 * @author Roy, 2006
 */
public class PHPProposalComperator implements Comparator {

	private static final int CLASSES = 1;
	private static final int FUNCTIONS = 2;
	private static final int CONSTANTS = 3;
	private static final int KEYWORDS = 4;
	private static final int OTHERS = 0;

	public int compare(Object o1, Object o2) {
		// type checking
		if (!(o1 instanceof ICompletionProposal && o2 instanceof ICompletionProposal)) {
			throw new IllegalArgumentException("PHPProposalComperator can get only ICompletionProposal");
		}
		
		ICompletionProposal element1 = (ICompletionProposal) o1;
		ICompletionProposal element2 = (ICompletionProposal) o2;
		
		int o1Type = getType(o1);
		int o2Type = getType(o2);
		
		int diff = o1Type - o2Type; // the difference between the two types
		return diff != 0 ? diff : compareDisplayString(element1, element2) ;
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

	private int getType(Object object) {
		if (object instanceof CodeDataCompletionProposal) {
			CodeData codeData = ((CodeDataCompletionProposal) object).getCodeData();
			if (codeData instanceof PHPClassData) {
				return CLASSES;
			} else if (codeData instanceof PHPFunctionData){
				return FUNCTIONS;
			} else if (codeData instanceof PHPKeywordData) {
				return KEYWORDS;
			} else {
				return CONSTANTS;
			}
		} 
		return OTHERS;
	}
}
