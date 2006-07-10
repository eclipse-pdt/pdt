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

import org.eclipse.php.core.phpModel.parser.ModelSupport;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;


public abstract class CompletionProposalGroup {

	protected int offset;
	protected CodeData[] codeDataProposals;
	protected CodeDataCompletionProposal[] completionProposals;
	protected String key;
	protected int selectionLength;
	
	public CompletionProposalGroup() {
		setData(0, null, null, selectionLength);
		setOffset(0);
	}

	public void setData(int offset, CodeData[] data, String key, int selectionLength) {
		setOffset(offset);
		setCodeDataProposals(data);
		this.key = key;
		this.selectionLength = selectionLength;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public CodeData[] getCodeDataProposals() {
		return codeDataProposals;
	}

	private void setCodeDataProposals(CodeData[] newProposals) {
		completionProposals = null;
		codeDataProposals = newProposals;
	}

	public CodeDataCompletionProposal[] getCompletionProposals() {
		if (completionProposals == null) {
			this.completionProposals = calcCompletionProposals();
		}
		return completionProposals;
	}

	protected CodeDataCompletionProposal[] calcCompletionProposals() {
		if (codeDataProposals == null || codeDataProposals.length == 0) {
			return ContentAssistSupport.EMPTY_CodeDataCompletionProposal_ARRAY;
		}

		CodeData[] tmp = ModelSupport.getCodeDataStartingWith(codeDataProposals, key);
		
		// filter internal code data
		tmp = ModelSupport.removeFilteredCodeData(tmp, ModelSupport.INTERNAL_CODEDATA_FILTER);

		CodeDataCompletionProposal[] result = new CodeDataCompletionProposal[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			result[i] = createProposal(tmp[i]);
		}
		return result;
	}
	
	abstract protected CodeDataCompletionProposal createProposal(CodeData codeData);
}