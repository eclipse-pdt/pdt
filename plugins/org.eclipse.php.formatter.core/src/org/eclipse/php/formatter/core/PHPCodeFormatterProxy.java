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

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.format.IContentFormatter2;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;

@Deprecated
public class PHPCodeFormatterProxy implements IContentFormatter, IContentFormatter2, IFormatterProcessorFactory {

	private PHPCodeFormatter formatter = new PHPCodeFormatter();

	public void format(IDocument document, IRegion region) {
		formatter.format(document, region);

	}

	public IFormattingStrategy getFormattingStrategy(String contentType) {
		return formatter.getFormattingStrategy(contentType);
	}

	public ICodeFormattingProcessor getCodeFormattingProcessor(IDocument document, PHPVersion phpVersion,
			boolean useShortTags, IRegion region) throws Exception {
		return formatter.getCodeFormattingProcessor(document, phpVersion, useShortTags, region);
	}

	public void setDefaultProject(IProject project) {
		formatter.setDefaultProject(project);
	}

	public void setIsPasting(boolean isPasting) {
		formatter.setIsPasting(isPasting);
	}

	public void format(IDocument document, IRegion region, PHPVersion phpVersion) {
		formatter.format(document, region, phpVersion);
	}

}
