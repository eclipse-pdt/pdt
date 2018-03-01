/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * A progress bar with a red/green indication for success or failure.
 */
public class ProgressBar extends Canvas {
	private static final int DEFAULT_HEIGHT = 18;
	private static final int DEFAULT_WIDTH = 160;

	private int fColorBarWidth = 0;
	private int fCurrentTickCount = 0;
	private boolean fError;
	private Color fFailureColor;
	private int fMaxTickCount = 0;
	private Color fOKColor;
	private boolean fStopped = false;
	private Color fStoppedColor;

	public ProgressBar(final Composite parent) {
		super(parent, SWT.NONE);

		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				fColorBarWidth = scale(fCurrentTickCount);
				redraw();
			}
		});
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(final PaintEvent e) {
				paint(e);
			}
		});
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(final DisposeEvent e) {
				fFailureColor.dispose();
				fOKColor.dispose();
				fStoppedColor.dispose();
			}
		});
		final Display display = parent.getDisplay();
		fFailureColor = new Color(display, 159, 63, 63);
		fOKColor = new Color(display, 95, 191, 95);
		fStoppedColor = new Color(display, 120, 120, 120);
	}

	@Override
	public Point computeSize(final int wHint, final int hHint, final boolean changed) {
		checkWidget();
		final Point size = new Point(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		if (wHint != SWT.DEFAULT) {
			size.x = wHint;
		}
		if (hHint != SWT.DEFAULT) {
			size.y = hHint;
		}
		return size;
	}

	private void drawBevelRect(final GC gc, final int x, final int y, final int w, final int h, final Color topleft,
			final Color bottomright) {
		gc.setForeground(topleft);
		gc.drawLine(x, y, x + w - 1, y);
		gc.drawLine(x, y, x, y + h - 1);

		gc.setForeground(bottomright);
		gc.drawLine(x + w, y, x + w, y + h);
		gc.drawLine(x, y + h, x + w, y + h);
	}

	private void paint(final PaintEvent event) {
		final GC gc = event.gc;
		final Display disp = getDisplay();

		final Rectangle rect = getClientArea();
		gc.fillRectangle(rect);
		drawBevelRect(gc, rect.x, rect.y, rect.width - 1, rect.height - 1,
				disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW),
				disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));

		setStatusColor(gc);
		fColorBarWidth = Math.min(rect.width - 2, fColorBarWidth);
		gc.fillRectangle(1, 1, fColorBarWidth, rect.height - 2);
	}

	private void paintStep(int startX, final int endX) {
		final GC gc = new GC(this);
		setStatusColor(gc);
		final Rectangle rect = getClientArea();
		startX = Math.max(1, startX);
		gc.fillRectangle(startX, 1, endX - startX, rect.height - 2);
		gc.dispose();
	}

	public void reset() {
		fError = false;
		fStopped = false;
		fCurrentTickCount = 0;
		fColorBarWidth = 0;
		fMaxTickCount = 0;
		redraw();
	}

	private int scale(final int value) {
		if (fMaxTickCount > 0) {
			final Rectangle r = getClientArea();
			if (r.width != 0) {
				return Math.max(0, value * (r.width - 2) / fMaxTickCount);
			}
		}
		return value;
	}

	public void setMaximum(final int max) {
		fMaxTickCount = max;
	}

	private void setStatusColor(final GC gc) {
		if (fStopped) {
			gc.setBackground(fStoppedColor);
		} else if (fError) {
			gc.setBackground(fFailureColor);
		} else if (fStopped) {
			gc.setBackground(fStoppedColor);
		} else {
			gc.setBackground(fOKColor);
		}
	}

	public void step(final boolean failed, final int tickCount) {
		fCurrentTickCount = tickCount;

		int x = fColorBarWidth;

		fColorBarWidth = scale(fCurrentTickCount);

		if (!fError && failed) {
			fError = true;
			x = 1;
		}
		if (fCurrentTickCount == fMaxTickCount) {
			fColorBarWidth = getClientArea().width - 1;
		}
		paintStep(x, fColorBarWidth);
	}

	public void stopped() {
		fStopped = true;
		redraw();
	}

}
