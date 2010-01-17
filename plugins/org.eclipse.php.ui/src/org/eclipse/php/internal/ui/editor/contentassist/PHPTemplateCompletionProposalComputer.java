/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;

public class PHPTemplateCompletionProposalComputer extends
		PHPCompletionProposalComputer {

	public PHPTemplateCompletionProposalComputer() {
	}

	@Override
	protected List computeScriptCompletionProposals(int offset,
			ScriptContentAssistInvocationContext context,
			IProgressMonitor monitor) {

		return Collections.EMPTY_LIST;

	}
}
