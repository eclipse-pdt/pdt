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

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.format.*;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredPartitioning;
import org.eclipse.wst.sse.ui.internal.format.StructuredFormattingStrategy;

public class PHPCodeFormatterProxy implements IContentFormatter,
		IContentFormatter2, IFormatterProcessorFactory {

	private IContentFormatter formatter;

	public PHPCodeFormatterProxy() {
		final boolean licensed = true;
		if (licensed) {
			formatter = new PHPCodeFormatter();
		} else {
			formatter = new MultiPassContentFormatter(
					IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING,
					IHTMLPartitions.HTML_DEFAULT);
			((MultiPassContentFormatter) formatter)
					.setMasterStrategy(new StructuredFormattingStrategy(
							new PhpFormatProcessorImpl()));
		}

	}

	public void format(IDocument document, IRegion region) {
		formatter.format(document, region);

	}

	public IFormattingStrategy getFormattingStrategy(String contentType) {
		return formatter.getFormattingStrategy(contentType);
	}

	public ICodeFormattingProcessor getCodeFormattingProcessor(
			IDocument document, PHPVersion phpVersion, boolean useShortTags,
			IRegion region) throws Exception {
		if (formatter instanceof IFormatterProcessorFactory) {
			return ((IFormatterProcessorFactory) formatter)
					.getCodeFormattingProcessor(document, phpVersion,
							useShortTags, region);
		}
		return new DefaultCodeFormattingProcessor(new HashMap());
	}

	public void setDefaultProject(IProject project) {
		if (formatter instanceof IFormatterProcessorFactory) {
			((IFormatterProcessorFactory) formatter).setDefaultProject(project);
		}
	}

	public void setIsPasting(boolean isPasting) {
		if (formatter instanceof IFormatterProcessorFactory) {
			((IFormatterProcessorFactory) formatter).setIsPasting(isPasting);
		}
	}

	public void format(IDocument document, IRegion region, PHPVersion phpVersion) {
		// TODO Auto-generated method stub
		if (formatter instanceof PHPCodeFormatter) {
			PHPCodeFormatter phpCodeFormatter = (PHPCodeFormatter) formatter;
			phpCodeFormatter.format(document, region, phpVersion);
		} else {
			formatter.format(document, region);
		}
	}

}
