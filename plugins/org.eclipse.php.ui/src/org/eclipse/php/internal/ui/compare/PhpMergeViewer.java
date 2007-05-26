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
package org.eclipse.php.internal.ui.compare;

import java.util.ArrayList;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.swt.widgets.Composite;

/**
 * Description: The viewer enables the file comparing with syntax coloring  
 * @author Roy, 2007
 */
public class PhpMergeViewer extends TextMergeViewer {

	private ArrayList fSourceViewer;

	public PhpMergeViewer(Composite parent, CompareConfiguration configuration) {
		super(parent, configuration);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.compare.contentmergeviewer.TextMergeViewer#configureTextViewer(org.eclipse.jface.text.TextViewer)
	 */
	protected void configureTextViewer(TextViewer textViewer) {
		if (textViewer instanceof SourceViewer) {
			if (fSourceViewer == null)
				fSourceViewer = new ArrayList();
			fSourceViewer.add(textViewer);
			
			((SourceViewer) textViewer).configure(new PHPStructuredTextViewerConfiguration());
		}
	}
	
	public IDocumentPartitioner getDocumentPartitioner() {
		return new PHPStructuredTextPartitioner();
	}
}
