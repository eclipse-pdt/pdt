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
package org.eclipse.php.internal.debug.core.launching;

import java.util.List;
import java.util.Vector;

import org.eclipse.ui.console.IHyperlink;

public class PHPHyperLink {

	private List<HyperlinkEntry> fLinks;

    public void addLink(IHyperlink link, String message, int length) {
        if (fLinks == null) {
            fLinks = new Vector<HyperlinkEntry>();
        }
        HyperlinkEntry hLink = new HyperlinkEntry(link, message, length);
        fLinks.add(hLink);
    }

    public HyperlinkEntry getHyperlinkEntry(String message) {
    	if (fLinks != null) {
	        Object[] alinks = fLinks.toArray();
	        for (Object element : alinks) {
	            String linkMessage = ((HyperlinkEntry) element).getMessage();
	            if ((linkMessage.trim()).equals(message.trim())) {
	                return ((HyperlinkEntry) element);
	            }
	        }
    	}
        return null;
    }

    public void dispose() {
        fLinks = null;
    }
}
