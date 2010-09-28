package org.eclipse.php.internal.ui.editor.configuration;

import java.util.AbstractCollection;
import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.wst.sse.ui.internal.Logger;

public class PresentationCollector extends AbstractCollection {
	private final TextPresentation fPresentation;
	private int lastOffset;

	/**
	 * @param presentation
	 *            - the Presentation being added to
	 * @param applyOnAdd
	 */
	PresentationCollector(TextPresentation presentation) {
		super();
		Assert.isNotNull(presentation);
		fPresentation = presentation;
		lastOffset = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object o) {
		StyleRange range = (StyleRange) o;
		if (lastOffset > range.start) {
			IllegalArgumentException e = new IllegalArgumentException(
					"Overlapping start in StyleRange " + range.start + ":" + range.length); //$NON-NLS-1$ //$NON-NLS-2$
			Logger.logException(e);
			throw e;
		}
		// only log exception when range's length is less than 0
		if (range.length < 0) {
			IllegalArgumentException e = new IllegalArgumentException(
					"Negative length StyleRange " + range.start + ":" + range.length); //$NON-NLS-1$ //$NON-NLS-2$
			Logger.logException(e);
			return false;
		}
		lastOffset = range.start + range.length;
		fPresentation.addStyleRange(range);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#iterator()
	 */
	public Iterator iterator() {
		return fPresentation.getNonDefaultStyleRangeIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#size()
	 */
	public int size() {
		throw new UnsupportedOperationException();
	}
}
