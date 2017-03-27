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
package org.eclipse.php.internal.debug.ui.views.coverage;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.editor.PHPSourceViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

/**
 * Code coverage text viewer.
 */
public class CodeCoverageTextViewer extends PHPSourceViewer implements IPropertyChangeListener {

	private static final String COVERED_LINES_COLOR_DEF = "org.eclipse.php.debug.ui.themePHPDebug.codeCoverageCoveredLinesBg"; //$NON-NLS-1$
	private static final String UNCOVERED_LINES_COLOR_DEF = "org.eclipse.php.debug.ui.themePHPDebug.codeCoverageUncoveredLinesBg"; //$NON-NLS-1$

	private IThemeManager fThemeManager;
	private Color fCoveredLineBackground;
	private Color fUncoveredLineBackground;
	private Color fUncoveredSignificantBackground;
	private byte[] fCoverageBitmask = {};
	private byte[] fSignificanceBitmask = {};

	public CodeCoverageTextViewer(Composite parent, int style) {
		super(parent, style);
		setEditable(false);
		fThemeManager = PlatformUI.getWorkbench().getThemeManager();
		fThemeManager.addPropertyChangeListener(this);
		fCoveredLineBackground = fThemeManager.getCurrentTheme().getColorRegistry().get(COVERED_LINES_COLOR_DEF);
		fUncoveredLineBackground = fThemeManager.getCurrentTheme().getColorRegistry().get(UNCOVERED_LINES_COLOR_DEF);
		fUncoveredSignificantBackground = composeColors(fUncoveredLineBackground, fCoveredLineBackground, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse
	 * .jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (COVERED_LINES_COLOR_DEF.equals(event.getProperty())) {
			fCoveredLineBackground = fThemeManager.getCurrentTheme().getColorRegistry().get(COVERED_LINES_COLOR_DEF);
			refresh();
		} else if (UNCOVERED_LINES_COLOR_DEF.equals(event.getProperty())) {
			fUncoveredLineBackground = fThemeManager.getCurrentTheme().getColorRegistry()
					.get(UNCOVERED_LINES_COLOR_DEF);
			refresh();
		}
		super.propertyChange(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	public void dispose() {
		fThemeManager.removePropertyChangeListener(this);
		fUncoveredSignificantBackground.dispose();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.editor.PHPSourceViewer#applyStyles()
	 */
	public void applyStyles() {
		super.applyStyles();
		StyledText text = getTextWidget();
		int linesNum = text.getLineCount();
		for (int line = 0; line < linesNum; line++) {
			if (isLineCovered(line)) {
				text.setLineBackground(line, 1, fCoveredLineBackground);
			} else {
				if (isLineSignificant(line))
					text.setLineBackground(line, 1, fUncoveredLineBackground);
			}
		}
	}

	public void setCoverageBitmask(byte[] coverageBitmask) {
		fCoverageBitmask = coverageBitmask;
	}

	public void setSingificanceBitmask(byte[] coverageBitmask) {
		fSignificanceBitmask = coverageBitmask;
	}

	public void goToNextLine() {
		StyledText text = getTextWidget();
		int line = text.getLineAtOffset(text.getCaretOffset());
		int numLines = text.getLineCount();
		do {
			++line;
		} while ((!isLineCovered(line - 1) /* || !isLineSignificant(line - 1) */) && line < numLines);
		if (line < numLines) {
			text.setSelection(text.getOffsetAtLine(line - 1), text.getOffsetAtLine(line));
		} else {
			MessageDialog.openInformation(getShell(), PHPDebugUIMessages.CodeCoverageTextViewer_1,
					PHPDebugUIMessages.CodeCoverageTextViewer_2);
		}
	}

	public void goToPreviousLine() {
		StyledText text = getTextWidget();
		int line = text.getLineAtOffset(text.getCaretOffset());
		do {
			--line;
		} while ((!isLineCovered(line - 1) /* || !isLineSignificant(line - 1) */) && line > 0);
		if (line > 0) {
			text.setSelection(text.getOffsetAtLine(line - 1), text.getOffsetAtLine(line));
		} else {
			MessageDialog.openInformation(getShell(), PHPDebugUIMessages.CodeCoverageTextViewer_3,
					PHPDebugUIMessages.CodeCoverageTextViewer_4);
		}
	}

	/**
	 * Checks whether the specified line is covered.
	 * 
	 * @param int
	 *            line (beginning from 1)
	 * @return <code>true</code> if the specified line is covered,
	 *         <code>false</code> otherwise.
	 * @throws IllegalArgumentException
	 *             when the specified line is out of coverage bitmask bounds.
	 */
	private boolean isLineCovered(int line) {
		line++;
		if (fCoverageBitmask.length > line / 8) {
			return ((fCoverageBitmask[line / 8] >> (line % 8)) & 0x1) == (byte) 1;
		}
		return false;
	}

	private boolean isLineSignificant(int line) {
		line++;
		if (fSignificanceBitmask.length > line / 8) {
			return ((fSignificanceBitmask[line / 8] >> (line % 8)) & 0x1) == (byte) 1;
		}
		return false;
	}

	private static Color composeColors(Color c1, Color c2, int proportion) {
		int divider = proportion + 1;
		Color newColor = new Color(c1.getDevice(), (proportion * c1.getRed() + c2.getRed()) / divider,
				(proportion * c1.getGreen() + c2.getGreen()) / divider,
				(proportion * c1.getBlue() + c2.getBlue()) / divider);
		return newColor;
	}
}
