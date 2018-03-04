/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
