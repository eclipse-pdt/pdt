/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import org.eclipse.wst.html.core.internal.format.HTMLTextFormatter;
import org.eclipse.wst.html.core.internal.provisional.HTMLFormatContraints;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

/**
 * 
 * @author moshe, 2006
 */
@SuppressWarnings("deprecation")
public class HTMLTextFormatterForPhpCode extends HTMLTextFormatter {

	@Override
	protected void formatText(IDOMNode node, HTMLFormatContraints contraints,
			int mode) {
		Node prev = node.getPreviousSibling();
		Node next = node.getNextSibling();
		if ((prev != null && "PHP".equals(prev.getNodeName())) //$NON-NLS-1$
				|| (next != null && "PHP".equals(next.getNodeName()))) { //$NON-NLS-1$
			return;
		}
		// TODO Auto-generated method stub
		super.formatText(node, contraints, mode);
	}

	@Override
	protected boolean canInsertBreakAfter(Node node) {
		if ("PHP".equals(node.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		Node next = node.getNextSibling();
		if (next != null && "PHP".equals(next.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		return super.canInsertBreakAfter(node);
	}

	@Override
	protected boolean canInsertBreakBefore(Node node) {
		if ("PHP".equals(node.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		Node prev = node.getPreviousSibling();
		if (prev != null && "PHP".equals(prev.getNodeName())) { //$NON-NLS-1$
			return false;
		}
		return super.canInsertBreakBefore(node);
	}

}
