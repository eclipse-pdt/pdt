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

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * processors who implement this interface has the abilty to allow recieving a heads up about the 
 * ISourceViewer.CONTENTASSIST_PROPOSALS operation; notifing the processors that the next request for completion is an explicit
 * request and not triggers request. 
 */
public interface IContentAssistProcessorForPHP extends IContentAssistProcessor {

	public void explicitActivationRequest();
	
	public IContentAssistSupport createContentAssistSupport();
}
