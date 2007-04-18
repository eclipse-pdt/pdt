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
package org.eclipse.php.internal.ui.compare;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.eclipse.compare.internal.MergeViewerAction;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.jface.action.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
/**
 * Extends the JFace SourceViewer with some convenience methods.
 */
public class MergeSourceViewer extends StructuredTextViewer
						implements ISelectionChangedListener, ITextListener, IMenuListener {
								
	public static final String UNDO_ID= "undo"; //$NON-NLS-1$
	public static final String REDO_ID= "redo"; //$NON-NLS-1$
	public static final String CUT_ID= "cut"; //$NON-NLS-1$
	public static final String COPY_ID= "copy"; //$NON-NLS-1$
	public static final String PASTE_ID= "paste"; //$NON-NLS-1$
	public static final String DELETE_ID= "delete"; //$NON-NLS-1$
	public static final String SELECT_ALL_ID= "selectAll"; //$NON-NLS-1$
	public static final String SAVE_ID= "save"; //$NON-NLS-1$

	class TextOperationAction extends MergeViewerAction {
		
		private int fOperationCode;
		
		TextOperationAction(int operationCode, boolean mutable, boolean selection, boolean content) {
			super(mutable, selection, content);
			fOperationCode= operationCode;
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
	private boolean fEnabled= true;
	private HashMap fActions= new HashMap();
	private IDocument fRememberedDocument;
	
	private boolean fAddSaveAction= true;
	
	public MergeSourceViewer(Composite parent, ResourceBundle bundle) {
		this(parent, SWT.NONE, bundle);
	}
	
	public MergeSourceViewer(Composite parent, int style, ResourceBundle bundle) {
		super(parent, null, null, false, style | SWT.H_SCROLL | SWT.V_SCROLL);
		
		fResourceBundle= bundle;
		
		MenuManager menu= new MenuManager();
		menu.setRemoveAllWhenShown(true);
		menu.addMenuListener(this);

		StyledText te = getTextWidget();
		te.setMenu(menu.createContextMenu(te));
	}
		
	public void rememberDocument(IDocument doc) {
//		if (doc != null && fRememberedDocument != null) {
//			System.err.println("MergeSourceViewer.rememberDocument: fRememberedDocument != null: shouldn't happen"); //$NON-NLS-1$
//		}
		fRememberedDocument= doc;
	}
	
	public IDocument getRememberedDocument() {
		return fRememberedDocument;
	}
	
	public void hideSaveAction() {
		fAddSaveAction= false;
	}
	
	public void setFont(Font font) {
		StyledText te= getTextWidget();
		if (te != null)
			te.setFont(font);
	}
	
	public void setBackgroundColor(Color color) {
		StyledText te= getTextWidget();
		if (te != null)
			te.setBackground(color);
	}
	
	public void setEnabled(boolean enabled) {
		if (enabled != fEnabled) {
			fEnabled= enabled;
			StyledText c= getTextWidget();
			if (c != null) {
				c.setEnabled(enabled);
				Display d= c.getDisplay();
				c.setBackground(enabled ? d.getSystemColor(SWT.COLOR_LIST_BACKGROUND) : null);
			}
		}
	}
	
	public boolean getEnabled() {
		return fEnabled;
	}

	public void setRegion(Position region) {
		fRegion= region;
	}
	
	public Position getRegion() {
		return fRegion;
	}
	
	public boolean isControlOkToUse() {
		StyledText t= getTextWidget();
		return t != null && !t.isDisposed();
	}
				
	public void setSelection(Position position) {
		if (position != null)
			setSelectedRange(position.getOffset(), position.getLength());
	}
	
	public void setLineBackground(Position position, Color c) {
		StyledText t= getTextWidget();
		if (t != null && !t.isDisposed()) {
			Point region= new Point(0, 0);
			getLineRange(position, region);
		
			region.x-= getDocumentRegionOffset();
		
			try {
				t.setLineBackground(region.x, region.y, c);
			} catch (IllegalArgumentException ex) {
				// silently ignored
			}
		}
	}
	
	public void resetLineBackground() {
		StyledText t= getTextWidget();
		if (t != null && !t.isDisposed()) {
			int lines= getLineCount();
			t.setLineBackground(0, lines, null);
		}
	}
	
	/*
	 * Returns number of lines in document region.
	 */
	public int getLineCount() {
		IRegion region= getVisibleRegion();

		int length= region.getLength();
		if (length == 0)
			return 0;
		
		IDocument doc= getDocument();
		int startLine= 0;
		int endLine= 0;

		int start= region.getOffset();
		try {
			startLine= doc.getLineOfOffset(start);
		} catch(BadLocationException ex) {
			// silently ignored
		}
		try {
			endLine= doc.getLineOfOffset(start+length);
		} catch(BadLocationException ex) {
			// silently ignored
		}
		
		return endLine-startLine+1;
	}
	
	public int getViewportLines() {
		StyledText te= getTextWidget();
		Rectangle clArea= te.getClientArea();
		if (!clArea.isEmpty())
			return clArea.height / te.getLineHeight();
		return 0;
	}

	public int getViewportHeight() {
		StyledText te= getTextWidget();
		Rectangle clArea= te.getClientArea();
		if (!clArea.isEmpty())
			return clArea.height;
		return 0;
	}
	
	/*
	 * Returns lines
	 */
	public int getDocumentRegionOffset() {
		int start= getVisibleRegion().getOffset();
		IDocument doc= getDocument();
		if (doc != null) {
			try {
				return doc.getLineOfOffset(start);
			} catch(BadLocationException ex) {
				// silently ignored
			}
		}
		return 0;
	}
	
	public int getVerticalScrollOffset() {
		StyledText st= getTextWidget();
		int lineHeight= st.getLineHeight();
		return getTopInset() - ((getDocumentRegionOffset()*lineHeight) + st.getTopPixel());
	}

	/*
	 * Returns the start line and the number of lines which correspond to the given position.
	 * Starting line number is 0 based.
	 */
	public Point getLineRange(Position p, Point region) {
		
		IDocument doc= getDocument();
		
		if (p == null || doc == null) {
			region.x= 0;
			region.y= 0;
			return region;
		}
		
		int start= p.getOffset();
		int length= p.getLength();
		
		int startLine= 0;
		try {
			startLine= doc.getLineOfOffset(start);
		} catch (BadLocationException e) {
			// silently ignored
		}
		
		int lineCount= 0;
		
		if (length == 0) {
//			// if range length is 0 and if range starts a new line
//			try {
//				if (start == doc.getLineStartOffset(startLine)) {
//					lines--;
//				}
//			} catch (BadLocationException e) {
//				lines--;
//			}
			
		} else {
			int endLine= 0;
			try {
				endLine= doc.getLineOfOffset(start + length - 1);	// why -1?
			} catch (BadLocationException e) {
				// silently ignored
			}
			lineCount= endLine-startLine+1;
		}
				
		region.x= startLine;
		region.y= lineCount;
		return region;
	}
	
	/*
	 * Scroll TextPart to the given line.
	 */
	public void vscroll(int line) {

		int srcViewSize= getLineCount();
		int srcExtentSize= getViewportLines();

		if (srcViewSize > srcExtentSize) {

			if (line < 0)
				line= 0;

			int cp= getTopIndex();
			if (cp != line)
				setTopIndex(line + getDocumentRegionOffset());
		}
	}
	
	public void addAction(String actionId, MergeViewerAction action) {
		fActions.put(actionId, action);
	}
	
	public MergeViewerAction getAction(String actionId) {
		MergeViewerAction action= (MergeViewerAction) fActions.get(actionId);
		if (action == null) {
			action= createAction(actionId);
			if (action == null)
				return null;
			
			if (action.isContentDependent())
				addTextListener(this);
			if (action.isSelectionDependent())
				addSelectionChangedListener(this);
				
			Utilities.initAction(action, fResourceBundle, "action." + actionId + ".");			 //$NON-NLS-1$ //$NON-NLS-2$
			fActions.put(actionId, action);
		}
		if (action.isEditableDependent() && !isEditable())
			return null;
		return action;
	}
	
	protected MergeViewerAction createAction(String actionId) {
		if (UNDO_ID.equals(actionId))
			return new TextOperationAction(UNDO, true, false, true);
		if (REDO_ID.equals(actionId))
			return new TextOperationAction(REDO, true, false, true);
		if (CUT_ID.equals(actionId))
			return new TextOperationAction(CUT, true, true, false);
		if (COPY_ID.equals(actionId))
			return new TextOperationAction(COPY, false, true, false);
		if (PASTE_ID.equals(actionId))
			return new TextOperationAction(PASTE, true, false, false);
		if (DELETE_ID.equals(actionId))
			return new TextOperationAction(DELETE, true, false, false);
		if (SELECT_ALL_ID.equals(actionId))
			return new TextOperationAction(SELECT_ALL, false, false, false);
		return null;
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		Iterator e= fActions.values().iterator();
		while (e.hasNext()) {
			MergeViewerAction action= (MergeViewerAction) e.next();
			if (action.isSelectionDependent())
				action.update();
		}
	}
					
	public void textChanged(TextEvent event) {
		Iterator e= fActions.values().iterator();
		while (e.hasNext()) {
			MergeViewerAction action= (MergeViewerAction) e.next();
			if (action.isContentDependent())
				action.update();
		}
	}
		
	/*
	 * Allows the viewer to add menus and/or tools to the context menu.
	 */
	public void menuAboutToShow(IMenuManager menu) {
		
		menu.add(new Separator("undo")); //$NON-NLS-1$
		addMenu(menu, UNDO_ID);
		addMenu(menu, REDO_ID);
	
		menu.add(new Separator("ccp")); //$NON-NLS-1$
		addMenu(menu, CUT_ID);
		addMenu(menu, COPY_ID);
		addMenu(menu, PASTE_ID);
		addMenu(menu, DELETE_ID);
		addMenu(menu, SELECT_ALL_ID);

		menu.add(new Separator("edit")); //$NON-NLS-1$
		menu.add(new Separator("find")); //$NON-NLS-1$
		//addMenu(menu, FIND_ID);
		
		menu.add(new Separator("save")); //$NON-NLS-1$
		if (fAddSaveAction)
			addMenu(menu, SAVE_ID);
		
		menu.add(new Separator("rest")); //$NON-NLS-1$
	}
	
	private void addMenu(IMenuManager menu, String actionId) {
		IAction action= getAction(actionId);
		if (action != null)
			menu.add(action);
	}
		
	protected void handleDispose() {
		
		removeTextListener(this);
		removeSelectionChangedListener(this);
		
		super.handleDispose();
	}
}
