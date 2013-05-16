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

import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.w3c.dom.Node;

/**
 * @author moshe, 2007
 */
public class HtmlFormatterForPhpCode extends HTMLFormatProcessorImpl {

	@Override
	protected IStructuredFormatter getFormatter(Node node) {
		IStructuredFormatter formatter = HTMLFormatterFactoryForPhpCode
				.getInstance().createFormatter(node, getFormatPreferences());

		return formatter;
	}

}
