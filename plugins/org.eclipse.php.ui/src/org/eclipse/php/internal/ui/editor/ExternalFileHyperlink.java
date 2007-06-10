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
package org.eclipse.php.internal.ui.editor;

import java.io.File;

import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;

/**
 * Hyperlink for external files.
 */
class ExternalFileHyperlink implements IHyperlink {
	// copies of this class exist in:
	// org.eclipse.wst.xml.ui.internal.hyperlink
	// org.eclipse.wst.html.ui.internal.hyperlink
	// org.eclipse.jst.jsp.ui.internal.hyperlink

	private IRegion fHyperlinkRegion;
	private File fHyperlinkFile;

	public ExternalFileHyperlink(IRegion region, File file) {
		fHyperlinkFile = file;
		fHyperlinkRegion = region;
	}

	public IRegion getHyperlinkRegion() {
		return fHyperlinkRegion;
	}

	public String getTypeLabel() {
		return null;
	}

	public String getHyperlinkText() {
		return null;
	}

	public void open() {
		if (fHyperlinkFile != null) {
			LocalFile fileStore = new LocalFile(fHyperlinkFile);
			IEditorInput input = new FileStoreEditorInput(fileStore);
			try {
				EditorUtility.openInEditor(input);
			} catch (PartInitException e) {
			}
		}
	}
}
