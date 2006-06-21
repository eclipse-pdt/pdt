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

import org.eclipse.wst.html.core.internal.provisional.HTMLFormatContraints;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

/**
 * Look for documentation at HTMLFormatterNoPHP
 * 
 * @author guy.g
 *
 */
public interface IHTMLFormatterNoPHPWrapper extends IStructuredFormatter{

	public void runFormatNode(IDOMNode node, HTMLFormatContraints contraints);
	
	public String runGetBreakSpaces(Node node);

	public boolean runCanInsertBreakBefore(Node node);
	
	public void runInsertBreakBefore(IDOMNode node, HTMLFormatContraints contraints);
	
	public boolean runCanInsertBreakAfter(Node node);
	
	public void runInsertBreakAfter(IDOMNode node, HTMLFormatContraints contraints);
	
	public void runReplaceSource(IDOMModel model, int offset, int length, String source);
	
	public void runSetWidth(HTMLFormatContraints contraints, String source);
}
