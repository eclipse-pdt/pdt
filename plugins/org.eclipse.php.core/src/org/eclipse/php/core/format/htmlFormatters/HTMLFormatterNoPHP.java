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

import org.eclipse.wst.html.core.internal.format.HTMLFormatter;
import org.eclipse.wst.html.core.internal.provisional.HTMLFormatContraints;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

/**
 * This class was created in order for HTMLFormatter to ignore php nodes when formatting html.
 * All the code is at HTMLFormatterNoPHPBase so that all the other formatters can access it too.
 * The interface IHTMLFormatterNoPHPWrapper was created in order for HTMLFormatterNoPHPBase to activate
 * protected methods inside the formatters
 *  
 * @author guy.g
 *
 */
public class HTMLFormatterNoPHP extends HTMLFormatter implements IHTMLFormatterNoPHPWrapper{
	
	//refering to the implementation in base
	protected void formatChildNodes(IDOMNode node, HTMLFormatContraints contraints){
		HTMLFormatterNoPHPBase.formatChildNodes(this, node, contraints);
	}

	protected void insertBreakAfter(IDOMNode node, HTMLFormatContraints contraints){
		HTMLFormatterNoPHPBase.insertBreakAfter(this, node, contraints);
	}

	protected void insertBreakBefore(IDOMNode node, HTMLFormatContraints contraints) {
		HTMLFormatterNoPHPBase.insertBreakBefore(this, node, contraints);
	}
	
	//imlementing IHTMLFormatterNoPHPWrapper
	public boolean runCanInsertBreakAfter(Node node) {
		return super.canInsertBreakAfter(node);
	}

	public boolean runCanInsertBreakBefore(Node node) {
		return super.canInsertBreakBefore(node);
	}

	public void runFormatNode(IDOMNode node, HTMLFormatContraints contraints) {
		super.formatNode(node, contraints);
		
	}

	public String runGetBreakSpaces(Node node) {
		return super.getBreakSpaces(node);
	}

	public void runInsertBreakAfter(IDOMNode node, HTMLFormatContraints contraints) {
		super.insertBreakAfter(node, contraints);
	}

	public void runInsertBreakBefore(IDOMNode node, HTMLFormatContraints contraints) {
		super.insertBreakBefore(node, contraints);
	}

	public void runReplaceSource(IDOMModel model, int offset, int length, String source) {
		super.replaceSource(model, offset, length, source);
	}

	public void runSetWidth(HTMLFormatContraints contraints, String source) {
		super.setWidth(contraints, source);		
	}

}
