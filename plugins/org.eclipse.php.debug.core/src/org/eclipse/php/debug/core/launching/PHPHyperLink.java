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
package org.eclipse.php.debug.core.launching;

import java.util.List;
import java.util.Vector;

import org.eclipse.ui.console.IHyperlink;

public class PHPHyperLink {

    private List fLinks;

    public void addLink(IHyperlink link, String message, int length) {
        if (fLinks == null) {
            fLinks = new Vector();
        }
        HyperlinkEntry hLink = new HyperlinkEntry(link, message, length);
        fLinks.add(hLink);
    }

    public HyperlinkEntry getHyperlinkEntry(String message) {
    	if (fLinks != null) {
	        Object[] alinks = fLinks.toArray();
	        for (int i = 0; i < alinks.length; i++) {
	            String linkMessage = ((HyperlinkEntry) alinks[i]).getMessage();
	            if ((linkMessage.trim()).equals(message.trim())) {
	                return ((HyperlinkEntry) alinks[i]);
	            }
	        }
    	}
        return null;

    }

    public void dispose() {
        fLinks = null;
    }
}
