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
package org.eclipse.php.internal.ui.editor;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.ISourceViewerExtension2;
import org.eclipse.jface.text.source.projection.AnnotationBag;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.eclipse.wst.sse.core.utils.StringUtils;
import org.eclipse.wst.sse.ui.internal.ITemporaryAnnotation;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.StructuredTextAnnotationHover;
import org.eclipse.wst.sse.ui.internal.StructuredTextLineBreakingReader;

/**
 * Override the StructuredTextAnnotationHover Reason: collect all the marker
 * messages instead of one message. All the parent methods are private so we
 * copied them.
 * 
 * @author moshe, 2007
 */
public class PHPStructuredTextAnnotationHover extends
		StructuredTextAnnotationHover {

	/**
	 * Provides a set of convenience methods for creating HTML pages. Taken from
	 * org.eclipse.jdt.internal.ui.text.HTMLPrinter
	 */
	class HTMLPrinter {

		HTMLPrinter() {
		}

		void addBullet(StringBuffer buffer, String bullet) {
			if (bullet != null) {
				buffer.append("<li>"); //$NON-NLS-1$
				buffer.append(bullet);
				buffer.append("</li>"); //$NON-NLS-1$
			}
		}

		void addPageEpilog(StringBuffer buffer) {
			buffer.append("</font></body></html>"); //$NON-NLS-1$
		}

		void addPageProlog(StringBuffer buffer) {
			insertPageProlog(buffer, buffer.length());
		}

		void addParagraph(StringBuffer buffer, Reader paragraphReader) {
			if (paragraphReader != null)
				addParagraph(buffer, read(paragraphReader));
		}

		void addParagraph(StringBuffer buffer, String paragraph) {
			if (paragraph != null) {
				buffer.append("<p>"); //$NON-NLS-1$
				buffer.append(paragraph);
			}
		}

		void addSmallHeader(StringBuffer buffer, String header) {
			if (header != null) {
				buffer.append("<h5>"); //$NON-NLS-1$
				buffer.append(header);
				buffer.append("</h5>"); //$NON-NLS-1$
			}
		}

		String convertToHTMLContent(String content) {
			content = replace(content, '<', "&lt;"); //$NON-NLS-1$
			return replace(content, '>', "&gt;"); //$NON-NLS-1$
		}

		void endBulletList(StringBuffer buffer) {
			buffer.append("</ul>"); //$NON-NLS-1$
		}

		void insertPageProlog(StringBuffer buffer, int position) {
			buffer
					.insert(position,
							"<html><body text=\"#000000\" bgcolor=\"#FFFF88\"><font size=-1>"); //$NON-NLS-1$
		}

		String read(Reader rd) {

			StringBuffer buffer = new StringBuffer();
			char[] readBuffer = new char[2048];

			try {
				int n = rd.read(readBuffer);
				while (n > 0) {
					buffer.append(readBuffer, 0, n);
					n = rd.read(readBuffer);
				}
				return buffer.toString();
			} catch (IOException x) {
			}

			return null;
		}

		private String replace(String text, char c, String s) {

			int previous = 0;
			int current = text.indexOf(c, previous);

			if (current == -1)
				return text;

			StringBuffer buffer = new StringBuffer();
			while (current > -1) {
				buffer.append(text.substring(previous, current));
				buffer.append(s);
				previous = current + 1;
				current = text.indexOf(c, previous);
			}
			buffer.append(text.substring(previous));

			return buffer.toString();
		}

		void startBulletList(StringBuffer buffer) {
			buffer.append("<ul>"); //$NON-NLS-1$
		}
	}

	private HTMLPrinter printer = new HTMLPrinter();

	/**
	 * Returns the distance to the ruler line.
	 */
	private int compareRulerLine(Position position, IDocument document, int line) {

		if (position.getOffset() > -1 && position.getLength() > -1) {
			try {
				int markerLine = document.getLineOfOffset(position.getOffset());
				if (line == markerLine)
					return 1;
				if (markerLine <= line
						&& line <= document.getLineOfOffset(position
								.getOffset()
								+ position.getLength()))
					return 2;
			} catch (BadLocationException x) {
			}
		}

		return 0;
	}

	/*
	 * Formats the message of this hover to fit onto the screen.
	 */
	private String formatHoverText(String text, ISourceViewer sourceViewer) {
		String result = null;
		String lineDelim = new String();
		try {
			lineDelim = sourceViewer.getDocument().getLineDelimiter(0);
		} catch (org.eclipse.jface.text.BadLocationException exception) {
			// skip, just use default
		}
		Display display = sourceViewer.getTextWidget().getDisplay();

		// replace special characters in text with html entity (like <, >, &
		// to &lt;, &gt;, &&;)
		text = StringUtils.convertToHTMLContent(text);

		Reader textReader = new StringReader(text);
		GC gc = new GC(display);
		try {
			StringBuffer buf = new StringBuffer();

			StructuredTextLineBreakingReader reader = new StructuredTextLineBreakingReader(
					textReader, gc, getHoverWidth(display));
			String line = reader.readLine();
			while (line != null) {
				if (buf.length() != 0) {
					buf.append(lineDelim);
				}
				buf.append(line);
				line = reader.readLine();
			}
			result = buf.toString();
		} catch (IOException exception) {
			Logger.logException(exception);
		} finally {
			gc.dispose();
		}
		return result;
	}

	/*
	 * Formats several message as HTML text.
	 */
	private String formatMultipleHoverText(List<String> messages) {

		StringBuffer buffer = new StringBuffer();
		printer.addPageProlog(buffer);
		printer.addParagraph(buffer, SSEUIMessages.Multiple_errors); //$NON-NLS-1$

		printer.startBulletList(buffer);
		Iterator<String> e = messages.iterator();
		while (e.hasNext())
			printer.addBullet(buffer, printer.convertToHTMLContent(e.next()));
		printer.endBulletList(buffer);

		printer.addPageEpilog(buffer);
		return buffer.toString();
	}

	@Override
	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		// get all the marker messages
		List<String> messages = dropDuplicateMessages(getMarkerMessages(
				sourceViewer, lineNumber));

		List<ITemporaryAnnotation> temporaryAnnotations = getTemporaryAnnotationsForLine(
				sourceViewer, lineNumber);
		for (int i = 0; i < temporaryAnnotations.size(); i++) {
			String message = ((Annotation) temporaryAnnotations.get(i))
					.getText();
			if (message != null) {
				boolean duplicated = false;
				for (int j = 0; j < messages.size(); j++)
					duplicated = duplicated || messages.get(j).equals(message);
				if (!duplicated) {
					messages.add(message);
				}
			} else {
				messages.add(((ITemporaryAnnotation) temporaryAnnotations
						.get(i)).toString());
			}
		}
		if (messages.size() > 1)
			return formatMultipleHoverText(messages);
		else if (messages.size() > 0)
			return formatHoverText(messages.get(0).toString(), sourceViewer);
		else
			return null;
	}

	private List<String> dropDuplicateMessages(List<String> messages) {
		Set<String> rt = new HashSet<String>();
		for (Iterator<String> i = messages.iterator(); i.hasNext();) {
			String message = i.next();
			if (!rt.contains(message)) {
				rt.add(message);
			}
		}
		return new ArrayList<String>(rt);
	}

	private int getHoverWidth(Display display) {
		Rectangle displayBounds = display.getBounds();
		int hoverWidth = displayBounds.width
				- (display.getCursorLocation().x - displayBounds.x);
		hoverWidth -= 12; // XXX: Add some space to the border, Revisit
		if (hoverWidth < 200) {
			hoverWidth = 200;
		}
		return hoverWidth;
	}

	/**
	 * Returns all marker messages which includes the ruler's line of activity.
	 */
	private List<String> getMarkerMessages(ISourceViewer viewer, int line) {

		IDocument document = viewer.getDocument();
		IAnnotationModel model = getAnnotationModel(viewer);
		List<String> messages = new ArrayList<String>();

		if (model == null) {
			return messages;
		}
		HashMap messagesAtPosition = new HashMap();
		Iterator e = model.getAnnotationIterator();
		while (e.hasNext()) {
			Object o = e.next();
			if (o instanceof Annotation) {
				Annotation a = (Annotation) o;
				if (compareRulerLine(model.getPosition(a), document, line) == 1) {
					if (a instanceof AnnotationBag) {
						AnnotationBag bag = (AnnotationBag) a;
						Iterator iterator = bag.iterator();
						while (iterator.hasNext()) {
							Annotation annotation = (Annotation) iterator
									.next();
							addText(model, annotation, messages,
									messagesAtPosition);
						}
					} else {
						addText(model, a, messages, messagesAtPosition);
					}

				}
			}
		}
		return messages;
	}

	private void addText(IAnnotationModel model, Annotation annotation,
			List<String> messages, HashMap messagesAtPosition) {
		Position position = model.getPosition(annotation);
		if (position != null
				&& includeAnnotation(annotation, position, messagesAtPosition)) {
			String text = getText(annotation);
			if (text != null) {
				messages.add(text);
			}
		}
	}

	private String getText(Annotation a) {
		String text = null;
		if (a instanceof MarkerAnnotation) {
			IMarker marker = ((MarkerAnnotation) a).getMarker();
			text = marker.getAttribute(IMarker.MESSAGE, (String) null);
		} else {
			text = a.getText();
		}
		return text;
	}

	/**
	 * Returns one marker which includes the ruler's line of activity.
	 */
	private List<ITemporaryAnnotation> getTemporaryAnnotationsForLine(
			ISourceViewer viewer, int line) {

		IDocument document = viewer.getDocument();
		IAnnotationModel model = viewer.getAnnotationModel();

		if (model == null)
			return null;

		List<ITemporaryAnnotation> annotations = new ArrayList<ITemporaryAnnotation>();

		Iterator e = model.getAnnotationIterator();
		while (e.hasNext()) {
			Object o = e.next();
			if (o instanceof ITemporaryAnnotation) {
				ITemporaryAnnotation a = (ITemporaryAnnotation) o;
				Position position = model.getPosition((Annotation) a);
				if (position == null)
					continue;

				if (compareRulerLine(position, document, line) == 1) {
					annotations.add(a);
				}
			}
		}

		return annotations;
	}

	/**
	 * Copy from DefaultAnnotationHover
	 */
	private boolean includeAnnotation(Annotation annotation, Position position,
			HashMap messagesAtPosition) {
		if (!isIncluded(annotation))
			return false;

		String text = annotation.getText();
		return (text != null && !isDuplicateAnnotation(messagesAtPosition,
				position, text));
	}

	/**
	 * Copy from DefaultAnnotationHover
	 */
	private boolean isDuplicateAnnotation(Map messagesAtPosition,
			Position position, String message) {
		if (messagesAtPosition.containsKey(position)) {
			Object value = messagesAtPosition.get(position);
			if (message.equals(value))
				return true;

			if (value instanceof List) {
				List messages = (List) value;
				if (messages.contains(message))
					return true;

				messages.add(message);
			} else {
				ArrayList messages = new ArrayList();
				messages.add(value);
				messages.add(message);
				messagesAtPosition.put(position, messages);
			}
		} else
			messagesAtPosition.put(position, message);
		return false;
	}

	/**
	 * Copy from DefaultAnnotationHover
	 */
	private IAnnotationModel getAnnotationModel(ISourceViewer viewer) {
		if (viewer instanceof ISourceViewerExtension2) {
			ISourceViewerExtension2 extension = (ISourceViewerExtension2) viewer;
			return extension.getVisualAnnotationModel();
		}
		return viewer.getAnnotationModel();
	}

}
