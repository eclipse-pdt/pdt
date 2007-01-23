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
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.ui.views.framelist.TreeFrame;
import org.eclipse.ui.views.framelist.TreeViewerFrameSource;

class ExplorerFrameSource extends TreeViewerFrameSource {
	private ExplorerPart fExplorer;

	ExplorerFrameSource(ExplorerPart explorer) {
		super(explorer.getViewer());
		fExplorer = explorer;
	}

	protected TreeFrame createFrame(Object input) {
		TreeFrame frame = super.createFrame(input);
		frame.setName(fExplorer.getFrameName(input));
		frame.setToolTipText(fExplorer.getToolTipText(input));
		return frame;
	}

	/**
	 * Also updates the title of the packages explorer
	 */
	protected void frameChanged(TreeFrame frame) {
		super.frameChanged(frame);
		fExplorer.updateTitle();
	}

}
