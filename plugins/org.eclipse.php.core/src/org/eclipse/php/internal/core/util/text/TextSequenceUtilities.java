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
package org.eclipse.php.internal.core.util.text;

import javax.swing.text.Segment;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public final class TextSequenceUtilities {

	private TextSequenceUtilities() {
	}

	public static TextSequence createTextSequence(
			IStructuredDocumentRegion source) {
		return createTextSequence(source, 0, source.getLength());
	}

	public static TextSequence createTextSequence(
			IStructuredDocumentRegion source, int startOffset, int length) {

		String s = ""; //$NON-NLS-1$
		try {
			s = source.getParentDocument().get(startOffset, length);
		} catch (BadLocationException e) {
		}
		Segment segment = new Segment(s.toCharArray(), 0, s.length());
		return new SimpleTextSequence(source, segment, 0, segment.count,
				startOffset);
	}

	public static String getType(TextSequence textSequence, int index) {
		int sourceOffset = textSequence.getOriginalOffset(index);
		return getTypeByAbsoluteOffset(textSequence, sourceOffset);
	}

	/**
	 * @param textSequence
	 * @param sourceOffset
	 * @return
	 */
	public static String getTypeByAbsoluteOffset(TextSequence textSequence,
			int sourceOffset) {
		IStructuredDocumentRegion source = textSequence.getSource();
		if (source.getEndOffset() == sourceOffset && source.getEndOffset() > 0) {
			sourceOffset--;
		}
		ITextRegion tRegion = source.getRegionAtCharacterOffset(sourceOffset);
		if (tRegion == null)
			return null;
		if (tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			try {
				return ((IPhpScriptRegion) tRegion)
						.getPhpTokenType(sourceOffset - source.getStart()
								- tRegion.getStart());
			} catch (BadLocationException e) {
				assert false;
				return null;
			}
		}
		if (tRegion != null) {
			return tRegion.getType();
		}
		return null;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	private static abstract class AbstractTextSequence implements TextSequence {

		IStructuredDocumentRegion source;

		Segment segment;

		int segmentOriginalStart;

		protected AbstractTextSequence(IStructuredDocumentRegion source,
				Segment segment, int segmentOriginalStart) {
			this.source = source;
			this.segment = segment;
			this.segmentOriginalStart = segmentOriginalStart;
		}

		public IStructuredDocumentRegion getSource() {
			return source;
		}

		public char charAt(int index) {
			return segment.array[getSegmentOffset(index)];
		}

		public CharSequence subSequence(int start, int end) {
			return subTextSequence(start, end);
		}

		protected abstract int getSegmentOffset(int index);

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	private static class SimpleTextSequence extends AbstractTextSequence
			implements TextSequence {

		private final int offset;

		private final int length;

		SimpleTextSequence(IStructuredDocumentRegion source, Segment segment,
				int offset, int length, int segmentOriginalStart) {
			super(source, segment, segmentOriginalStart);
			this.offset = offset;
			this.length = length;
		}

		public int getOriginalOffset(int index) {
			return segmentOriginalStart + offset + index;
		}

		@Override
		protected int getSegmentOffset(int index) {
			return segment.offset + offset + index;
		}

		public int length() {
			return length;
		}

		public TextSequence subTextSequence(int start, int end) {
			return new SimpleTextSequence(source, segment, offset + start, end
					- start, segmentOriginalStart);
		}

		public TextSequence cutTextSequence(int start, int end) {
			if (start == 0) {
				return subTextSequence(end, length);
			}
			if (end == length) {
				return subTextSequence(0, start);
			}

			int[] newIndexes = new int[] { offset, start, offset + end,
					length - end };
			return new CompositeTextSequence(source, segment, newIndexes,
					segmentOriginalStart);
		}

		@Override
		public String toString() {
			return new String(segment.array, segment.offset + offset, length);
		}

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	private static class CompositeTextSequence extends AbstractTextSequence
			implements TextSequence {

		private final int[] indexes;

		private int length = -1;

		CompositeTextSequence(IStructuredDocumentRegion source,
				Segment segment, int[] indexes, int segmentOriginalStart) {
			super(source, segment, segmentOriginalStart);
			this.indexes = indexes;
		}

		public int length() {
			if (length == -1) {
				int rv = 0;
				for (int i = 1; i < indexes.length; i += 2) {
					rv += indexes[i];
				}
				length = rv;
			}
			return length;
		}

		public int getOriginalOffset(int index) {
			int rv = segmentOriginalStart;
			for (int i = 0; i < indexes.length; i += 2) {
				if (index - indexes[i + 1] < 0) {
					rv += indexes[i] + index;
					break;
				}
				index -= indexes[i + 1];
			}
			return rv;
		}

		@Override
		protected int getSegmentOffset(int index) {
			int rv = segment.offset;
			for (int i = 0; i < indexes.length; i += 2) {
				if (index - indexes[i + 1] < 0) {
					rv += indexes[i] + index;
					break;
				}
				index -= indexes[i + 1];
			}
			return rv;
		}

		public TextSequence subTextSequence(int start, int end) {
			if (start == 0 && end == length()) {
				return this;
			}
			int startPart = 0;
			int endPart = 0;
			int startPartLength = 0;
			for (int i = 0; i < indexes.length; i += 2) {
				if (startPartLength + indexes[i + 1] > start) {
					startPart = i >> 1;
					break;
				}
				startPartLength += indexes[i + 1];
			}
			int endPartLength = startPartLength;
			for (int i = startPart << 1; i < indexes.length; i += 2) {
				if (endPartLength + indexes[i + 1] >= end) {
					endPart = i >> 1;
					break;
				}
				endPartLength += indexes[i + 1];
			}
			int newNumberOfParts = endPart - startPart + 1;
			if (newNumberOfParts == 1) {
				int newStart = indexes[(startPart << 1)] + start
						- startPartLength;
				int newLength = end - start;
				return new SimpleTextSequence(source, segment, newStart,
						newLength, segmentOriginalStart);
			}

			int[] newIndexes = new int[newNumberOfParts << 1];
			// set indexes at start Part
			newIndexes[0] = indexes[(startPart << 1)] + start - startPartLength;
			newIndexes[1] = indexes[(startPart << 1) + 1]
					- (start - startPartLength);
			// set indexes after start Part and before last part

			for (int i = 2; i < newIndexes.length - 3; i++) {
				newIndexes[i] = indexes[i + (startPart << 1)];
				newIndexes[i + 1] = indexes[i + (startPart << 1) + 1];
			}

			// set indexes at end Part
			newIndexes[newIndexes.length - 2] = indexes[(endPart << 1)];
			newIndexes[newIndexes.length - 1] = end - endPartLength;

			return new CompositeTextSequence(source, segment, newIndexes,
					segmentOriginalStart);
		}

		public TextSequence cutTextSequence(int start, int end) {
			int startPart = 0;
			int endPart = 0;
			int startPartLength = 0;
			for (int i = 0; i < indexes.length; i += 2) {
				if (startPartLength + indexes[i + 1] > start) {
					startPart = i >> 1;
					break;
				}
				startPartLength += indexes[i + 1];
			}
			int endPartLength = startPartLength;
			for (int i = startPart << 1; i < indexes.length; i += 2) {
				if (endPartLength + indexes[i + 1] >= end) {
					endPart = i >> 1;
					break;
				}
				endPartLength += indexes[i + 1];
			}
			int numberOfParts = indexes.length >> 1;
			int newNumberOfParts = startPart + numberOfParts - endPart + 1;
			int[] newIndexes = new int[newNumberOfParts << 1];
			int part;
			// set indexes before start Part
			for (part = 0; part < startPart; part++) {
				newIndexes[(part << 1)] = indexes[(part << 1)];
				newIndexes[(part << 1) + 1] = indexes[(part << 1) + 1];
			}
			// set indexes at start part
			newIndexes[(part << 1)] = indexes[(startPart << 1)];
			newIndexes[(part << 1) + 1] = start - startPartLength;
			// set indexes at end part
			part++;
			newIndexes[(part << 1)] = indexes[(endPart << 1)]
					+ (end - endPartLength);
			newIndexes[(part << 1) + 1] = indexes[(endPart << 1) + 1]
					- (end - endPartLength);
			// set indexes after end part
			part++;
			int diff = numberOfParts - newNumberOfParts;
			for (; part + diff < numberOfParts; part++) {
				newIndexes[(part << 1)] = indexes[((part + diff) << 1)];
				newIndexes[(part << 1) + 1] = indexes[((part + diff) << 1) + 1];
			}

			return new CompositeTextSequence(source, segment, newIndexes,
					segmentOriginalStart);
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer(length());
			for (int i = 0; i < indexes.length; i += 2) {
				buffer.append(segment.array, segment.offset + indexes[i],
						indexes[i + 1]);
			}
			return buffer.toString();
		}
	}

}
