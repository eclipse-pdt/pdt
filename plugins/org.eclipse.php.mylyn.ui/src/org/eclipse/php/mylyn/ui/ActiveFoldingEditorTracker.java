/*******************************************************************************
 * Copyright (c) 2004, 2015 Tasktop Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *     Dawid Pakuła - PDT port
 *******************************************************************************/

package org.eclipse.php.mylyn.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.mylyn.monitor.ui.AbstractEditorTracker;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.mylyn.internal.ui.editor.ActiveFoldingListener;
import org.eclipse.ui.IEditorPart;

/**
 * @author Mik Kersten
 */
public class ActiveFoldingEditorTracker extends AbstractEditorTracker {

	protected Map<PHPStructuredEditor, ActiveFoldingListener> editorListenerMap = new HashMap<>();

	@Override
	public void editorOpened(IEditorPart part) {
		if (part instanceof PHPStructuredEditor) {
			registerEditor((PHPStructuredEditor) part);
		}
	}

	@Override
	public void editorClosed(IEditorPart part) {
		if (part instanceof PHPStructuredEditor) {
			unregisterEditor((PHPStructuredEditor) part);
		}
	}

	public void registerEditor(final PHPStructuredEditor editor) {
		if (editorListenerMap.containsKey(editor)) {
			return;
		} else {
			ActiveFoldingListener listener = new ActiveFoldingListener(editor);
			editorListenerMap.put(editor, listener);
		}
	}

	public void unregisterEditor(PHPStructuredEditor editor) {
		ActiveFoldingListener listener = editorListenerMap.get(editor);
		if (listener != null) {
			listener.dispose();
		}
		editorListenerMap.remove(editor);
	}

	/**
	 * For testing.
	 */
	public Map<PHPStructuredEditor, ActiveFoldingListener> getEditorListenerMap() {
		return editorListenerMap;
	}

	@Override
	protected void editorBroughtToTop(IEditorPart part) {
		// ignore
	}

}
