/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.AbstractProposalSorter;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class PHPAlphabeticSorter extends AbstractProposalSorter {

	private final CompletionProposalComparator fComparator = new CompletionProposalComparator();

	public PHPAlphabeticSorter() {
		fComparator.setOrderAlphabetically(false);
	}

	@Override
	public int compare(ICompletionProposal p1, ICompletionProposal p2) {
		return fComparator.compare(p1, p2);
	}

}
