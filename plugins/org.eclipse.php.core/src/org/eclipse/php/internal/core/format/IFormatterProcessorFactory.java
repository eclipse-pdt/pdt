/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.PHPVersion;

/**
 * Implementors of this interface should supply an
 * {@link ICodeFormattingProcessor} for PHP code formatting.
 * 
 * @author shalom
 */
public interface IFormatterProcessorFactory {

	/**
	 * Returns an {@link ICodeFormattingProcessor}.
	 * 
	 * @param document
	 * @param phpVersion
	 *            the PHP version
	 * @param useShortTags
	 *            use short tags
	 * @param region
	 *            An {@link IRegion}
	 * @return An ICodeFormattingProcessor that will format the PHP code.
	 * @throws Exception
	 */
	public ICodeFormattingProcessor getCodeFormattingProcessor(IDocument document, PHPVersion phpVersion,
			boolean useShortTags, IRegion region) throws Exception;
}
