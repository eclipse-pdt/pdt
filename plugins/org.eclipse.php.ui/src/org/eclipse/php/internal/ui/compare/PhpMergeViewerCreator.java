/*******************************************************************************
 * Copyright (c) 2009,2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.sse.core.internal.document.StructuredDocumentFactory;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredPartitioning;
import org.eclipse.wst.sse.core.internal.text.JobSafeStructuredDocument;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

/**
 * Description: Creates the voewer for PHP
 * 
 * @author Roy, 2007
 */
public class PhpMergeViewerCreator implements IViewerCreator {

	@Override
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new org.eclipse.compare.contentmergeviewer.TextMergeViewer(parent, config) {

			@Override
			protected void configureTextViewer(TextViewer textViewer) {
				if (textViewer instanceof SourceViewer) {
					((SourceViewer) textViewer).configure(new PHPStructuredTextViewerConfiguration());
				}
			}

			@Override
			protected String getDocumentPartitioning() {
				return IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING;
			}

			@Override
			public IDocumentPartitioner getDocumentPartitioner() {
				return new PHPStructuredTextPartitioner();
			}

			@Override
			protected void setupDocument(IDocument document) {
				super.setupDocument(createStructuredDocument(document));
			}

			@Override
			protected SourceViewer createSourceViewer(Composite parent, int textOrientation) {
				return new StructuredTextViewer(parent, new CompositeRuler(), null, false,
						textOrientation | SWT.H_SCROLL | SWT.V_SCROLL) {
					@Override
					public void setDocument(IDocument document, IAnnotationModel annotationModel, int modelRangeOffset,
							int modelRangeLength) {
						super.setDocument(createStructuredDocument(document), annotationModel, modelRangeOffset,
								modelRangeLength);
					}
				};
			}

			/*
			 * This method is workaround to use SSE viewer to compare files
			 */
			private IDocument createStructuredDocument(IDocument document) {
				IDocument newDoc = document;
				if (newDoc != null && !(document instanceof IStructuredDocument)) {
					newDoc = StructuredDocumentFactory.getNewStructuredDocumentInstance(new PhpSourceParser());
					newDoc.set(document.get());

					IDocumentPartitioner partitioner = getDocumentPartitioner();
					if (partitioner != null) {
						if (newDoc instanceof JobSafeStructuredDocument) {
							((JobSafeStructuredDocument) newDoc).setDocumentPartitioner(
									"org.eclipse.wst.sse.core.default_structured_text_partitioning", //$NON-NLS-1$
									partitioner);
						} else {
							newDoc.setDocumentPartitioner(partitioner);
						}
						partitioner.connect(newDoc);
					}
				}
				return newDoc;
			}
		};
	}

}
