/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.documentModel.encoding;

import org.eclipse.jface.text.IDocument;
import org.eclipse.wst.sse.core.internal.document.IDocumentCharsetDetector;

public class PHPDocumentCharsetDetector extends PHPResourceEncodingDetector implements IDocumentCharsetDetector {

	@Override
	public void set(IDocument document) {
		// @GINO: Do nothing for now
	}

}
