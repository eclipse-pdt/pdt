/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.format.htmlFormatters;

import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.w3c.dom.Node;

/**
 * Handles the html sections of PHP files
 * Just do not use the html formater for php content 
 * @author Roy, 2006
 */
public class HTMLFormatProcessorForPhp extends HTMLFormatProcessorImpl {

	protected IStructuredFormatter getFormatter(Node node) {
		IStructuredFormatter formatter = HTMLFormatterNoPHPFactory.getInstance().createFormatter(node, getFormatPreferences());		
		return formatter;
	}
	
	
}
