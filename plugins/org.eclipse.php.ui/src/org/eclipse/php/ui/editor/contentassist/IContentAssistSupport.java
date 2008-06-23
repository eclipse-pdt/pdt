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
package org.eclipse.php.ui.editor.contentassist;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;

public interface IContentAssistSupport {

	public ICompletionProposal[] getCompletionOption(ITextViewer viewer, DOMModelForPHP phpDOMModel, int offset, boolean explicit) throws BadLocationException;

	public char[] getAutoactivationTriggers();
}