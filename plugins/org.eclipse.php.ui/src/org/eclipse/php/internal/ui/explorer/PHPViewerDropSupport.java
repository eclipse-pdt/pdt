/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.internal.ui.scriptview.FileTransferDropAdapter;
import org.eclipse.dltk.internal.ui.scriptview.SelectionTransferDropAdapter;
import org.eclipse.jface.util.DelegatingDropAdapter;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

public class PHPViewerDropSupport {

	private final StructuredViewer fViewer;
	private final DelegatingDropAdapter fDelegatingDropAdapter;
	private final SelectionTransferDropAdapter fReorgDropListener;
	private boolean fStarted;

	public PHPViewerDropSupport(StructuredViewer viewer) {
		fViewer = viewer;

		fDelegatingDropAdapter = new DelegatingDropAdapter();
		fReorgDropListener = new PHPSelectionTransferDropAdapter(fViewer);
		fDelegatingDropAdapter.addDropTargetListener(fReorgDropListener);
		fDelegatingDropAdapter
				.addDropTargetListener(new FileTransferDropAdapter(fViewer));
		// fDelegatingDropAdapter.addDropTargetListener(new
		// PluginTransferDropAdapter(fViewer));

		fStarted = false;
	}

	public void addDropTargetListener(TransferDropTargetListener listener) {
		Assert.isLegal(!fStarted);

		fDelegatingDropAdapter.addDropTargetListener(listener);
	}

	public void start() {
		Assert.isLegal(!fStarted);

		int ops = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK
				| DND.DROP_DEFAULT;

		Transfer[] transfers = new Transfer[] {
				LocalSelectionTransfer.getInstance(),
				FileTransfer.getInstance(), PluginTransfer.getInstance() };

		fViewer.addDropSupport(ops, transfers, fDelegatingDropAdapter);

		fStarted = true;
	}

	public void setFeedbackEnabled(boolean enabled) {
		fReorgDropListener.setFeedbackEnabled(enabled);
	}

}
