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
package org.eclipse.php.internal.ui.compare;

import java.util.*;

import org.eclipse.compare.ICompareContainer;
import org.eclipse.compare.internal.MergeViewerAction;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.jface.action.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.*;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.FindReplaceAction;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

/**
 * Extends the JFace SourceViewer with some convenience methods.
 */
public class MergeSourceViewer extends StructuredTextViewer implements
		ISelectionChangedListener, ITextListener, IMenuListener,
		IOperationHistoryListener {

	public static final String UNDO_ID = "undo"; //$NON-NLS-1$
	public static final String REDO_ID = "redo"; //$NON-NLS-1$
	public static final String CUT_ID = "cut"; //$NON-NLS-1$
	public static final String COPY_ID = "copy"; //$NON-NLS-1$
	public static final String PASTE_ID = "paste"; //$NON-NLS-1$
	public static final String DELETE_ID = "delete"; //$NON-NLS-1$
	public static final String SELECT_ALL_ID = "selectAll"; //$NON-NLS-1$
	public static final String SAVE_ID = "save"; //$NON-NLS-1$
	public static final String FIND_ID = "find"; //$NON-NLS-1$

	class TextOperationAction extends MergeViewerAction {

		private int fOperationCode;

		TextOperationAction(int operationCode, boolean mutable,
				boolean selection, boolean content) {
			this(operationCode, null, mutable, selection, content);

		}

		public TextOperationAction(int operationCode,
				String actionDefinitionId, boolean mutable, boolean selection,
				boolean content) {
			super(mutable, selection, content);
			if (actionDefinitionId != null)
				setActionDefinitionId(actionDefinitionId);
			fOperationCode = operationCode;
			update();
		}

		public void run() {
			if (isEnabled())
				doOperation(fOperationCode);
		}

		public boolean isEnabled() {
			return fOperationCode != -1 && canDoOperation(fOperationCode);
		}

		public void update() {
			this.setEnabled(isEnabled());
		}
	}

	private ResourceBundle fResourceBundle;
	private Position fRegion;
	private boolean fEnabled = true;
	private HashMap fActions = new HashMap();
	private IDocument fRememberedDocument;

	private boolean fAddSaveAction = true;
	private boolean isConfigured = false;

	// line number ruler support
	private IPropertyChangeListener fPreferenceChangeListener;
	private boolean fShowLineNumber = false;
	private LineNumberRulerColumn fLineNumberColumn;
	private List textActions = new ArrayList();

	public MergeSourceViewer(Composite parent, int style,
			ResourceBundle bundle, ICompareContainer container) {
		super(parent, new CompositeRuler(), null, false, style | SWT.H_SCROLL
				| SWT.V_SCROLL);

		fResourceBundle = bundle;

		MenuManager menu = new MenuManager();
		menu.setRemoveAllWhenShown(true);
		menu.addMenuListener(this);
		StyledText te = getTextWidget();
		te.setMenu(menu.createContextMenu(te));
		container.registerContextMenu(menu, this);

		// for listening to editor show/hide line number preference value
		fPreferenceChangeListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				MergeSourceViewer.this.handlePropertyChangeEvent(event);
			}
		};
		EditorsUI.getPreferenceStore().addPropertyChangeListener(
				fPreferenceChangeListener);
		fShowLineNumber = EditorsUI
				.getPreferenceStore()
				.getBoolean(
						AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER);
		if (fShowLineNumber) {
			updateLineNumberRuler();
		}

		IOperationHistory history = getHistory();
		if (history != null)
			history.addOperationHistoryListener(this);
	}

	public void rememberDocument(IDocument doc) {
		// if (doc != null && fRememberedDocument != null) {
		//			System.err.println("MergeSourceViewer.rememberDocument: fRememberedDocument != null: shouldn't happen"); //$NON-NLS-1$
		// }
		fRememberedDocument = doc;
	}

	public IDocument getRememberedDocument() {
		return fRememberedDocument;
	}

	public void hideSaveAction() {
		fAddSaveAction = false;
	}

	public void setFont(Font font) {
		StyledText te = getTextWidget();
		if (te != null)
			te.setFont(font);
		if (fLineNumberColumn != null) {
			fLineNumberColumn.setFont(font);
			layoutViewer();
		}
	}

	public void setBackgroundColor(Color color) {
		StyledText te = getTextWidget();
		if (te != null)
			te.setBackground(color);
		if (fLineNumberColumn != null)
			fLineNumberColumn.setBackground(color);
	}

	public void setEnabled(boolean enabled) {
		if (enabled != fEnabled) {
			fEnabled = enabled;
			StyledText c = getTextWidget();
			if (c != null) {
				c.setEnabled(enabled);
				Display d = c.getDisplay();
				c.setBackground(enabled ? d
						.getSystemColor(SWT.COLOR_LIST_BACKGROUND) : null);
			}
		}
	}

	public boolean getEnabled() {
		return fEnabled;
	}

	public void setRegion(Position region) {
		fRegion = region;
	}

	public Position getRegion() {
		return fRegion;
	}

	public boolean isControlOkToUse() {
		StyledText t = getTextWidget();
		return t != null && !t.isDisposed();
	}

	public void setSelection(Position position) {
		if (position != null)
			setSelectedRange(position.getOffset(), position.getLength());
	}

	public void setLineBackground(Position position, Color c) {
		StyledText t = getTextWidget();
		if (t != null && !t.isDisposed()) {
			Point region = new Point(0, 0);
			getLineRange(position, region);

			region.x -= getDocumentRegionOffset();

			try {
				t.setLineBackground(region.x, region.y, c);
			} catch (IllegalArgumentException ex) {
				// silently ignored
			}
		}
	}

	public void resetLineBackground() {
		StyledText t = getTextWidget();
		if (t != null && !t.isDisposed()) {
			int lines = getLineCount();
			t.setLineBackground(0, lines, null);
		}
	}

	/*
	 * Returns number of lines in document region.
	 */
	public int getLineCount() {
		IRegion region = getVisibleRegion();

		int length = region.getLength();
		if (length == 0)
			return 0;

		IDocument doc = getDocument();
		int startLine = 0;
		int endLine = 0;

		int start = region.getOffset();
		try {
			startLine = doc.getLineOfOffset(start);
		} catch (BadLocationException ex) {
			// silently ignored
		}
		try {
			endLine = doc.getLineOfOffset(start + length);
		} catch (BadLocationException ex) {
			// silently ignored
		}

		return endLine - startLine + 1;
	}

	public int getViewportLines() {
		StyledText te = getTextWidget();
		Rectangle clArea = te.getClientArea();
		if (!clArea.isEmpty())
			return clArea.height / te.getLineHeight();
		return 0;
	}

	public int getViewportHeight() {
		StyledText te = getTextWidget();
		Rectangle clArea = te.getClientArea();
		if (!clArea.isEmpty())
			return clArea.height;
		return 0;
	}

	/*
	 * Returns lines
	 */
	public int getDocumentRegionOffset() {
		int start = getVisibleRegion().getOffset();
		IDocument doc = getDocument();
		if (doc != null) {
			try {
				return doc.getLineOfOffset(start);
			} catch (BadLocationException ex) {
				// silently ignored
			}
		}
		return 0;
	}

	public int getVerticalScrollOffset() {
		StyledText st = getTextWidget();
		int lineHeight = st.getLineHeight();
		return getTopInset()
				- ((getDocumentRegionOffset() * lineHeight) + st.getTopPixel());
	}

	/*
	 * Returns the start line and the number of lines which correspond to the
	 * given position. Starting line number is 0 based.
	 */
	public Point getLineRange(Position p, Point region) {

		IDocument doc = getDocument();

		if (p == null || doc == null) {
			region.x = 0;
			region.y = 0;
			return region;
		}

		int start = p.getOffset();
		int length = p.getLength();

		int startLine = 0;
		try {
			startLine = doc.getLineOfOffset(start);
		} catch (BadLocationException e) {
			// silently ignored
		}

		int lineCount = 0;

		if (length == 0) {
			// // if range length is 0 and if range starts a new line
			// try {
			// if (start == doc.getLineStartOffset(startLine)) {
			// lines--;
			// }
			// } catch (BadLocationException e) {
			// lines--;
			// }

		} else {
			int endLine = 0;
			try {
				endLine = doc.getLineOfOffset(start + length - 1); // why -1?
			} catch (BadLocationException e) {
				// silently ignored
			}
			lineCount = endLine - startLine + 1;
		}

		region.x = startLine;
		region.y = lineCount;
		return region;
	}

	/*
	 * Scroll TextPart to the given line.
	 */
	public void vscroll(int line) {

		int srcViewSize = getLineCount();
		int srcExtentSize = getViewportLines();

		if (srcViewSize > srcExtentSize) {

			if (line < 0)
				line = 0;

			int cp = getTopIndex();
			if (cp != line)
				setTopIndex(line + getDocumentRegionOffset());
		}
	}

	public void addAction(String actionId, MergeViewerAction action) {
		fActions.put(actionId, action);
	}

	public IAction getAction(String actionId) {
		IAction action = (IAction) fActions.get(actionId);
		if (action == null) {
			action = createAction(actionId);
			if (action == null)
				return null;
			if (action instanceof MergeViewerAction) {
				MergeViewerAction mva = (MergeViewerAction) action;
				if (mva.isContentDependent())
					addTextListener(this);
				if (mva.isSelectionDependent())
					addSelectionChangedListener(this);

				Utilities.initAction(action, fResourceBundle,
						"action." + actionId + "."); //$NON-NLS-1$ //$NON-NLS-2$
			}
			addAction(actionId, action);

		}
		if (action instanceof MergeViewerAction) {
			MergeViewerAction mva = (MergeViewerAction) action;
			if (mva.isEditableDependent() && !isEditable())
				return null;
		}
		return action;
	}

	protected IAction createAction(String actionId) {
		if (UNDO_ID.equals(actionId))
			return new TextOperationAction(UNDO,
					"org.eclipse.ui.edit.undo", true, false, true); //$NON-NLS-1$
		if (REDO_ID.equals(actionId))
			return new TextOperationAction(REDO,
					"org.eclipse.ui.edit.redo", true, false, true); //$NON-NLS-1$
		if (CUT_ID.equals(actionId))
			return new TextOperationAction(CUT,
					"org.eclipse.ui.edit.cut", true, true, false); //$NON-NLS-1$
		if (COPY_ID.equals(actionId))
			return new TextOperationAction(COPY,
					"org.eclipse.ui.edit.copy", false, true, false); //$NON-NLS-1$
		if (PASTE_ID.equals(actionId))
			return new TextOperationAction(PASTE,
					"org.eclipse.ui.edit.paste", true, false, false); //$NON-NLS-1$
		if (DELETE_ID.equals(actionId))
			return new TextOperationAction(DELETE,
					"org.eclipse.ui.edit.delete", true, false, false); //$NON-NLS-1$
		if (SELECT_ALL_ID.equals(actionId))
			return new TextOperationAction(SELECT_ALL,
					"org.eclipse.ui.edit.selectAll", false, false, false); //$NON-NLS-1$
		return null;
	}

	public void selectionChanged(SelectionChangedEvent event) {
		Iterator e = fActions.values().iterator();
		while (e.hasNext()) {
			Object next = e.next();
			if (next instanceof MergeViewerAction) {
				MergeViewerAction action = (MergeViewerAction) next;
				if (action.isSelectionDependent())
					action.update();
			}
		}
	}

	public void textChanged(TextEvent event) {
		updateContentDependantActions();
	}

	void updateContentDependantActions() {
		Iterator e = fActions.values().iterator();
		while (e.hasNext()) {
			Object next = e.next();
			if (next instanceof MergeViewerAction) {
				MergeViewerAction action = (MergeViewerAction) next;
				if (action.isContentDependent())
					action.update();
			}
		}
	}

	/*
	 * Allows the viewer to add menus and/or tools to the context menu.
	 */
	public void menuAboutToShow(IMenuManager menu) {

		menu.add(new Separator("undo")); //$NON-NLS-1$
		addMenu(menu, UNDO_ID);
		addMenu(menu, REDO_ID);
		menu.add(new GroupMarker("save")); //$NON-NLS-1$
		if (fAddSaveAction)
			addMenu(menu, SAVE_ID);
		menu.add(new Separator("file")); //$NON-NLS-1$

		menu.add(new Separator("ccp")); //$NON-NLS-1$
		addMenu(menu, CUT_ID);
		addMenu(menu, COPY_ID);
		addMenu(menu, PASTE_ID);
		addMenu(menu, DELETE_ID);
		addMenu(menu, SELECT_ALL_ID);

		menu.add(new Separator("edit")); //$NON-NLS-1$
		menu.add(new Separator("find")); //$NON-NLS-1$
		addMenu(menu, FIND_ID);

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		menu.add(new Separator("text")); //$NON-NLS-1$
		for (Iterator iterator = textActions.iterator(); iterator.hasNext();) {
			IAction action = (IAction) iterator.next();
			menu.add(action);
		}

		menu.add(new Separator("rest")); //$NON-NLS-1$

		// update all actions
		// to get undo redo right
		updateActions();
	}

	private void addMenu(IMenuManager menu, String actionId) {
		IAction action = getAction(actionId);
		if (action != null)
			menu.add(action);
	}

	protected void handleDispose() {

		removeTextListener(this);
		removeSelectionChangedListener(this);
		EditorsUI.getPreferenceStore().removePropertyChangeListener(
				fPreferenceChangeListener);

		IOperationHistory history = getHistory();
		if (history != null)
			history.removeOperationHistoryListener(this);

		super.handleDispose();
	}

	/**
	 * update all actions independent of their type
	 * 
	 */
	public void updateActions() {
		Iterator e = fActions.values().iterator();
		while (e.hasNext()) {
			Object next = e.next();
			if (next instanceof MergeViewerAction) {
				MergeViewerAction action = (MergeViewerAction) next;
				action.update();
			}
			if (next instanceof FindReplaceAction) {
				FindReplaceAction action = (FindReplaceAction) next;
				action.update();
			}
		}
	}

	public void configure(SourceViewerConfiguration configuration) {
		if (isConfigured)
			unconfigure();
		isConfigured = true;
		super.configure(configuration);
	}

	/**
	 * specific implementation to support a vertical ruler
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setBounds(int x, int y, int width, int height) {
		if (getControl() instanceof Composite) {
			((Composite) getControl()).setBounds(x, y, width, height);
		} else {
			getTextWidget().setBounds(x, y, width, height);
		}
	}

	/**
	 * handle show/hide line numbers from editor preferences
	 * 
	 * @param event
	 */
	protected void handlePropertyChangeEvent(PropertyChangeEvent event) {

		String key = event.getProperty();

		if (key
				.equals(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER)) {
			boolean b = EditorsUI
					.getPreferenceStore()
					.getBoolean(
							AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER);
			if (b != fShowLineNumber) {
				toggleLineNumberRuler();
			}
		} else if (key
				.equals(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER_COLOR)) {
			updateLineNumberColumnPresentation(true);
		}
	}

	/**
	 * Hides or shows line number ruler column based of preference setting
	 */
	private void updateLineNumberRuler() {
		IVerticalRuler v = getVerticalRuler();
		if (v != null && v instanceof CompositeRuler) {
			CompositeRuler c = (CompositeRuler) v;

			if (!fShowLineNumber) {
				if (fLineNumberColumn != null) {
					c.removeDecorator(fLineNumberColumn);
				}
			} else {
				if (fLineNumberColumn == null) {
					fLineNumberColumn = new LineNumberRulerColumn();
					updateLineNumberColumnPresentation(false);
				}
				c.addDecorator(0, fLineNumberColumn);
			}
		}
	}

	private void updateLineNumberColumnPresentation(boolean refresh) {
		if (fLineNumberColumn == null)
			return;
		RGB rgb = getColorFromStore(
				EditorsUI.getPreferenceStore(),
				AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER_COLOR);
		if (rgb == null)
			rgb = new RGB(0, 0, 0);
		ISharedTextColors sharedColors = getSharedColors();
		fLineNumberColumn.setForeground(sharedColors.getColor(rgb));
		if (refresh) {
			fLineNumberColumn.redraw();
		}
	}

	private void layoutViewer() {
		Control parent = getControl();
		if (parent instanceof Composite && !parent.isDisposed())
			((Composite) parent).layout(true);
	}

	private ISharedTextColors getSharedColors() {
		return EditorsUI.getSharedTextColors();
	}

	private RGB getColorFromStore(IPreferenceStore store, String key) {
		RGB rgb = null;
		if (store.contains(key)) {
			if (store.isDefault(key))
				rgb = PreferenceConverter.getDefaultColor(store, key);
			else
				rgb = PreferenceConverter.getColor(store, key);
		}
		return rgb;
	}

	/**
	 * Toggles line number ruler column.
	 */
	private void toggleLineNumberRuler() {
		fShowLineNumber = !fShowLineNumber;

		updateLineNumberRuler();
	}

	public void addTextAction(IAction textEditorPropertyAction) {
		textActions.add(textEditorPropertyAction);
	}

	public void addAction(String id, IAction action) {
		fActions.put(id, action);
	}

	private IOperationHistory getHistory() {
		if (PlatformUI.getWorkbench() == null) {
			return null;
		}
		return PlatformUI.getWorkbench().getOperationSupport()
				.getOperationHistory();
	}

	public void historyNotification(OperationHistoryEvent event) {
		// This method updates the enablement of all content operations
		// when the undo history changes. It could be localized to UNDO and
		// REDO.
		IUndoContext context = getUndoContext();
		if (context != null && event.getOperation().hasContext(context)) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					updateContentDependantActions();
				}
			});
		}
	}

	private IUndoContext getUndoContext() {
		IUndoManager undoManager = getUndoManager();
		if (undoManager instanceof IUndoManagerExtension)
			return ((IUndoManagerExtension) undoManager).getUndoContext();
		return null;
	}
}
