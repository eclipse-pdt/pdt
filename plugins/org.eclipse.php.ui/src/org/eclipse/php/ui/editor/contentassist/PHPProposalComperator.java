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

package org.eclipse.php.ui.editor.contentassist;

import java.util.Comparator;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;

/*
 * Compares between two PHP proposal elements - according to the type
 * @author Roy, 2006
 */
public class PHPProposalComperator implements Comparator {

	private static final int CLASSES = 1;
	private static final int FUNCTIONS = 2;
	private static final int CONSTANTS = 3;
	private static final int OTHERS = 4;

	public int compare(Object o1, Object o2) {
		assert o1 instanceof ICompletionProposal && o2 instanceof ICompletionProposal;
		ICompletionProposal element1 = (ICompletionProposal) o1;
		ICompletionProposal element2 = (ICompletionProposal) o2;
		
		int o1Type = getType(o1);
		int o2Type = getType(o2);
		
		int diff = o1Type - o2Type; // the difference between the two types
		return diff != 0 ? diff : element1.getDisplayString().compareToIgnoreCase(element2.getDisplayString()) ;
	}

	private int getType(Object object) {
		if (object instanceof CodeDataCompletionProposal) {
			CodeData codeData = ((CodeDataCompletionProposal) object).getCodeData();
			return codeData instanceof PHPClassData ? CLASSES : codeData instanceof PHPFunctionData ? FUNCTIONS : CONSTANTS;
		} 
		return OTHERS;
	}
}
