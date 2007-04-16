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
package org.eclipse.php.internal.ui.dnd;

import java.util.Arrays;

import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.dnd.*;

/**
 * Adapter to handle file drop from other applications.
 */
public class ExternalFileDropAdapter extends DropTargetAdapter {

	private static ExternalFileDropAdapter instance;

	private ExternalFileDropAdapter() {
	}

	public static ExternalFileDropAdapter getInstance() {
		if (instance == null)
			instance = new ExternalFileDropAdapter();
		return instance;
	}

	public Transfer getTransfer() {
		return FileTransfer.getInstance();
	}

	public void dragEnter(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}

	public void dragOperationChanged(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}

	public void dragOver(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}

	public void drop(DropTargetEvent event) {
		if (event.data instanceof String[]) {
			EditorUtility.openFilesInEditor(Arrays.asList((Object[]) event.data));
		}
	}
}
