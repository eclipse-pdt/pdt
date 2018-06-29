/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class PHPTemplateCompletionProposalComputer extends PHPCompletionProposalComputer {

	public PHPTemplateCompletionProposalComputer() {
	}

	@Override
	protected List<ICompletionProposal> computeScriptCompletionProposals(int offset,
			ScriptContentAssistInvocationContext context, IProgressMonitor monitor) {

		return Collections.emptyList();

	}
}
