/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - PDT Port
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.text.PHPWordFinder;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.QuickMenuCreator;
import org.eclipse.ui.menus.IMenuService;

public class PHPQuickMenuCreator extends QuickMenuCreator {
	protected String id;
	protected PHPStructuredEditor editor;

	public PHPQuickMenuCreator(String id) {
		this.id = id;
	}

	public void setEditor(PHPStructuredEditor editor) {
		this.editor = editor;
	}

	@Override
	protected Point computeMenuLocation(StyledText text) {
		if (editor == null || text != editor.getViewer().getTextWidget())
			return super.computeMenuLocation(text);
		return computeWordStart();
	}

	@Override
	protected void fillMenu(IMenuManager menu) {
		if (id != null && menu instanceof ContributionManager) {
			PlatformUI.getWorkbench().getService(IMenuService.class)
					.populateContributionManager((ContributionManager) menu, "popup:" + id); //$NON-NLS-1$
		}

	}

	private Point computeWordStart() {
		ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
		IRegion textRegion = PHPWordFinder.findWord(editor.getViewer().getDocument(), selection.getOffset());
		if (textRegion == null)
			return null;

		IRegion widgetRegion = modelRange2WidgetRange(textRegion);
		if (widgetRegion == null)
			return null;

		int start = widgetRegion.getOffset();

		StyledText styledText = editor.getViewer().getTextWidget();
		Point result = styledText.getLocationAtOffset(start);
		result.y += styledText.getLineHeight(start);

		if (!styledText.getClientArea().contains(result)) {
			return null;
		}

		return result;
	}

	private IRegion modelRange2WidgetRange(IRegion region) {
		ISourceViewer viewer = editor.getViewer();
		if (viewer instanceof ITextViewerExtension5) {
			ITextViewerExtension5 extension = (ITextViewerExtension5) viewer;
			return extension.modelRange2WidgetRange(region);
		}

		IRegion visibleRegion = viewer.getVisibleRegion();
		int start = region.getOffset() - visibleRegion.getOffset();
		int end = start + region.getLength();
		if (end > visibleRegion.getLength()) {
			end = visibleRegion.getLength();
		}

		return new Region(start, end - start);
	}
}
