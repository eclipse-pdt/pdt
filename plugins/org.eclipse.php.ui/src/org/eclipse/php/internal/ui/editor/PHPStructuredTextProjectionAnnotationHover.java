/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
package org.eclipse.php.internal.ui.editor;

import java.util.Iterator;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.*;
import org.eclipse.jface.text.source.projection.IProjectionPosition;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.php.internal.ui.editor.hover.PHPSourceViewerInformationControl;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.sse.ui.internal.StructuredTextAnnotationHover;

/**
 * @author seva, 2007 Copies behavior of ProjectionAnnotationHover for
 *         PHPStructuredTextViewer, but replacing control creater to return
 *         PHPSourceViewerInformationControl.
 * 
 */
public class PHPStructuredTextProjectionAnnotationHover extends
		StructuredTextAnnotationHover implements IAnnotationHoverExtension {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#canHandleMouseCursor()
	 */
	public boolean canHandleMouseCursor() {
		// TODO Auto-generated method stub
		return false;
	}

	private IInformationControlCreator fInformationControlCreator;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#getHoverControlCreator()
	 */
	public IInformationControlCreator getHoverControlCreator() {
		if (fInformationControlCreator == null) {
			fInformationControlCreator = new IInformationControlCreator() {
				public IInformationControl createInformationControl(Shell parent) {
					return new PHPSourceViewerInformationControl(parent);
				}
			};
		}
		return fInformationControlCreator;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#getHoverInfo(org.eclipse.jface.text.source.ISourceViewer,
	 *      org.eclipse.jface.text.source.ILineRange, int)
	 */
	public Object getHoverInfo(ISourceViewer sourceViewer,
			ILineRange lineRange, int visibleLines) {
		return getProjectionTextAtLine(sourceViewer, lineRange.getStartLine(),
				visibleLines);
	}

	private String getProjectionTextAtLine(ISourceViewer viewer, int line,
			int visibleLines) {

		IAnnotationModel model = null;
		if (viewer instanceof ISourceViewerExtension2) {
			ISourceViewerExtension2 viewerExtension = (ISourceViewerExtension2) viewer;
			IAnnotationModel visual = viewerExtension
					.getVisualAnnotationModel();
			if (visual instanceof IAnnotationModelExtension) {
				IAnnotationModelExtension modelExtension = (IAnnotationModelExtension) visual;
				model = modelExtension
						.getAnnotationModel(ProjectionSupport.PROJECTION);
			}
		}

		if (model != null) {
			try {
				IDocument document = viewer.getDocument();
				Iterator e = model.getAnnotationIterator();
				while (e.hasNext()) {
					ProjectionAnnotation annotation = (ProjectionAnnotation) e
							.next();
					if (!annotation.isCollapsed())
						continue;

					Position position = model.getPosition(annotation);
					if (position == null)
						continue;

					if (isCaptionLine(annotation, position, document, line))
						return getText(document, position.getOffset(), position
								.getLength(), visibleLines);

				}
			} catch (BadLocationException x) {
			}
		}

		return null;
	}

	private String getText(IDocument document, int offset, int length,
			int numberOfLines) throws BadLocationException {
		int endOffset = offset + length;

		try {
			int endLine = document.getLineOfOffset(offset)
					+ Math.max(0, numberOfLines - 1);
			IRegion lineInfo = document.getLineInformation(endLine);
			endOffset = Math.min(endOffset, lineInfo.getOffset()
					+ lineInfo.getLength());
		} catch (BadLocationException x) {
		}

		return document.get(offset, endOffset - offset);
	}

	private boolean isCaptionLine(ProjectionAnnotation annotation,
			Position position, IDocument document, int line) {
		if (position.getOffset() > -1 && position.getLength() > -1) {
			try {
				int captionOffset;
				if (position instanceof IProjectionPosition)
					captionOffset = ((IProjectionPosition) position)
							.computeCaptionOffset(document);
				else
					captionOffset = 0;
				int startLine = document.getLineOfOffset(position.getOffset()
						+ captionOffset);
				return line == startLine;
			} catch (BadLocationException x) {
			}
		}
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#getHoverLineRange(org.eclipse.jface.text.source.ISourceViewer,
	 *      int)
	 */
	public ILineRange getHoverLineRange(ISourceViewer viewer, int lineNumber) {
		return new LineRange(lineNumber, 1);
	}

}