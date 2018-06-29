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
package org.eclipse.php.internal.ui.text.correction;

/**
 * A proposal which is able to show a message on the status line of the content
 * assistant in which this proposal is shown.
 * 
 * @see org.eclipse.jface.text.contentassist.IContentAssistantExtension2
 */
public interface IStatusLineProposal {

	/**
	 * The message to show when this proposal is selected by the user in the content
	 * assistant.
	 * 
	 * @return The message to show, or null for no message.
	 */
	public String getStatusMessage();

}
