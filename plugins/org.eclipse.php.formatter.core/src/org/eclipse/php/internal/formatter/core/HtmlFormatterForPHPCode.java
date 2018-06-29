/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.formatter.core;

import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatter;
import org.w3c.dom.Node;

/**
 * @author moshe, 2007
 */
public class HtmlFormatterForPHPCode extends HTMLFormatProcessorImpl {

	@Override
	protected IStructuredFormatter getFormatter(Node node) {
		IStructuredFormatter formatter = HTMLFormatterFactoryForPHPCode.getInstance().createFormatter(node,
				getFormatPreferences());

		return formatter;
	}

}
