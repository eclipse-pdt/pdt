/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.editors;

import java.io.IOException;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.php.composer.core.model.ModelContainer;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;

import com.fasterxml.jackson.core.JsonParseException;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class JsonContentFormatter implements IContentFormatter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.formatter.IContentFormatter#format(org.eclipse
	 * .jface.text.IDocument, org.eclipse.jface.text.IRegion)
	 */
	public void format(IDocument document, IRegion region) {
		ModelContainer container = new ModelContainer();
		try {
			container.deserialize(document);
			document.set(container.serializeToString());
		} catch (JsonParseException e) {
			ComposerUIPlugin.logError(e);
		} catch (IOException e) {
			ComposerUIPlugin.logError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.formatter.IContentFormatter#getFormattingStrategy
	 * (java.lang.String)
	 */
	public IFormattingStrategy getFormattingStrategy(String contentType) {
		return null;
	}

}
