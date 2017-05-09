package org.eclipse.php.internal.ui.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.php.internal.core.documentModel.parser.PHPSourceParser;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.sse.core.internal.document.StructuredDocumentFactory;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredPartitioning;
import org.eclipse.wst.sse.core.internal.text.JobSafeStructuredDocument;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

public class PHPTextMergeViewer extends TextMergeViewer {

	private boolean useBasic = true;

	public PHPTextMergeViewer(Composite parent, CompareConfiguration configuration) {
		super(parent, configuration);
	}

	@Override
	public void setInput(Object input) {
		if (input instanceof ICompareInput) {
			ICompareInput compareInput = (ICompareInput) input;
			ISharedDocumentAdapter sda = Adapters.adapt(compareInput.getLeft(), ISharedDocumentAdapter.class);
			if (sda != null) {
				this.useBasic = sda.getDocumentKey(compareInput.getLeft()) == null;
			}
		}
		super.setInput(input);

	}

	@Override
	protected void configureTextViewer(TextViewer textViewer) {
		if (textViewer instanceof StructuredTextViewer) {
			((StructuredTextViewer) textViewer).configure(new PHPStructuredTextViewerConfiguration());
		}
	}

	@Override
	protected String getDocumentPartitioning() {
		if (useBasic) {
			return super.getDocumentPartitioning();
		}
		return IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING;
	}

	@Override
	public IDocumentPartitioner getDocumentPartitioner() {
		if (useBasic) {
			return super.getDocumentPartitioner();
		}
		return new PHPStructuredTextPartitioner();
	}

	@Override
	protected void setupDocument(final IDocument document) {
		super.setupDocument(createStructuredDocument(document));
	}

	@Override
	public String getTitle() {
		return Messages.PHPTextMergeViewer_Viewer_Title;
	}

	@Override
	protected SourceViewer createSourceViewer(Composite parent, int textOrientation) {
		if (useBasic) {
			return super.createSourceViewer(parent, textOrientation);
		}
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
		if (useBasic) {
			return document;
		}
		IStructuredDocument newDoc = null;
		if (document instanceof IStructuredDocument) {
			newDoc = (IStructuredDocument) document;
		} else if (document != null) {
			newDoc = StructuredDocumentFactory.getNewStructuredDocumentInstance(new PHPSourceParser());
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

}
