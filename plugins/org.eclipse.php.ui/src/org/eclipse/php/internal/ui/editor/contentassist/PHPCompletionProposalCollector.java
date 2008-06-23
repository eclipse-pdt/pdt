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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.swt.graphics.Image;

public class PHPCompletionProposalCollector extends ScriptCompletionProposalCollector {

	public PHPCompletionProposalCollector(ISourceModule cu) {
		super(cu);
	}

	protected ScriptCompletionProposal createOverrideCompletionProposal(IScriptProject scriptProject, ISourceModule compilationUnit, String name, String[] paramTypes, int start, int length, String label, String string) {
		return new PHPOverrideCompletionProposal(scriptProject, compilationUnit, name, paramTypes, start, length, label, string);
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(String completion, int replaceStart, int length, Image image, String displayString, int i) {
		return new PHPCompletionProposal(completion, replaceStart, length, image, displayString, i);
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(String completion, int replaceStart, int length, Image image, String displayString, int i, boolean isInDoc) {
		return new PHPCompletionProposal(completion, replaceStart, length, image, displayString, i, isInDoc);
	}

	protected char[] getVarTrigger() {
		return new char[] { '\t', ' ', '=', ';', '.' };
	}
}
