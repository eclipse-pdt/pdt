/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;

public interface IProcessableProposal extends IScriptCompletionProposal {

	ProposalProcessorManager getProposalProcessorManager();

	void setProposalProcessorManager(ProposalProcessorManager mgr);

}
