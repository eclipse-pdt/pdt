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
package org.eclipse.php.core.codeassist;

import org.eclipse.jface.text.IDocument;

/**
 * This is a PHP related completion requestor interface.
 * 
 * @author michael
 */
public interface IPHPCompletionRequestor {

	public IDocument getDocument();
	
	public boolean isExplicit();
}
