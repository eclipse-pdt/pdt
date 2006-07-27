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
package org.eclipse.php.ui.search;

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.ui.PHPElementLabelProvider;
import org.eclipse.php.ui.util.PHPElementSorter;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPSearchResultPage extends AbstractTextSearchViewPage implements IAdaptable {

	private static final String KEY_GROUPING = "org.eclipse.php.search.resultpage.grouping"; //$NON-NLS-1$
	private static final String GROUP_GROUPING= "org.eclipse.php.search.resultpage.grouping"; //$NON-NLS-1$
	
	private GroupAction fGroupTypeAction;
	private GroupAction fGroupFileAction;
	private GroupAction fGroupProjectAction;
	private int fCurrentGrouping;
	private PHPSearchContentProvider fContentProvider;
	private PHPSearchEditorOpener fEditorOpener;

	public PHPSearchResultPage() {
		fEditorOpener = new PHPSearchEditorOpener();
		initGroupingActions();
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

	public void showMatch(Match match, int offset, int length, boolean activate) throws PartInitException {
		IEditorPart editor;
		try {
			editor = fEditorOpener.openMatch(match);
		} catch (Exception e) {
			throw new PartInitException(e.getMessage());
		}

		if (editor != null && activate)
			editor.getEditorSite().getPage().activate(editor);
		Object element = match.getElement();
		if (editor instanceof ITextEditor) {
			ITextEditor textEditor = (ITextEditor) editor;
			textEditor.selectAndReveal(offset, length);
		} else if (editor != null) {
			if (element instanceof IFile) {
				IFile file = (IFile) element;
				showWithMarker(editor, file, offset, length);
			}
		}
	}

	protected void fillToolbar(IToolBarManager tbm) {
		super.fillToolbar(tbm);
		if (getLayout() != FLAG_LAYOUT_FLAT)
			addGroupActions(tbm);
	}
	
	/**
	 * Precondition here: the viewer must be showing a tree with a LevelContentProvider.
	 * @param grouping
	 */
	void setGrouping(int grouping) {
		fCurrentGrouping = grouping;
		StructuredViewer viewer = getViewer();
		
		PHPSearchTreeContentProvider cp = (PHPSearchTreeContentProvider) viewer.getContentProvider();
		cp.setLevel(grouping);
		updateGroupingActions();
		getSettings().put(KEY_GROUPING, fCurrentGrouping);
		getViewPart().updateLabel();
	}

	private void addGroupActions(IToolBarManager mgr) {
		mgr.appendToGroup(IContextMenuConstants.GROUP_VIEWER_SETUP, new Separator(GROUP_GROUPING));
		mgr.appendToGroup(GROUP_GROUPING, fGroupProjectAction);
		mgr.appendToGroup(GROUP_GROUPING, fGroupFileAction);
		mgr.appendToGroup(GROUP_GROUPING, fGroupTypeAction);
		updateGroupingActions();
	}
	
	private void updateGroupingActions() {
		fGroupProjectAction.setChecked(fCurrentGrouping == PHPSearchTreeContentProvider.LEVEL_PROJECT);
		fGroupFileAction.setChecked(fCurrentGrouping == PHPSearchTreeContentProvider.LEVEL_FILE);
		fGroupTypeAction.setChecked(fCurrentGrouping == PHPSearchTreeContentProvider.LEVEL_TYPE);
	}

	private void showWithMarker(IEditorPart editor, IFile file, int offset, int length) throws PartInitException {
		try {
			IMarker marker = file.createMarker(NewSearchUI.SEARCH_MARKER);
			HashMap attributes = new HashMap(4);
			attributes.put(IMarker.CHAR_START, new Integer(offset));
			attributes.put(IMarker.CHAR_END, new Integer(offset + length));
			marker.setAttributes(attributes);
			IDE.gotoMarker(editor, marker);
			marker.delete();
		} catch (CoreException e) {
			throw new PartInitException(PHPUIMessages.PHPSearchResultPage_error_marker, e);
		}
	}

	private void initGroupingActions() {
		fGroupProjectAction = new GroupAction(PHPUIMessages.PHPSearchResultPage_groupby_project, PHPUIMessages.PHPSearchResultPage_groupby_project_tooltip, this, PHPSearchTreeContentProvider.LEVEL_PROJECT);
		PHPPluginImages.setLocalImageDescriptors(fGroupProjectAction, "group_by_project.gif"); //$NON-NLS-1$
		fGroupFileAction = new GroupAction(PHPUIMessages.PHPSearchResultPage_groupby_file, PHPUIMessages.PHPSearchResultPage_groupby_file_tooltip, this, PHPSearchTreeContentProvider.LEVEL_FILE);
		PHPPluginImages.setLocalImageDescriptors(fGroupFileAction, "group_by_file.gif"); //$NON-NLS-1$
		fGroupTypeAction = new GroupAction(PHPUIMessages.PHPSearchResultPage_groupby_type, PHPUIMessages.PHPSearchResultPage_groupby_type_tooltip, this, PHPSearchTreeContentProvider.LEVEL_TYPE);
		PHPPluginImages.setLocalImageDescriptors(fGroupTypeAction, "group_by_type.gif"); //$NON-NLS-1$
	}

	protected StructuredViewer getViewer() {
		// override so that it's visible in the package.
		return super.getViewer();
	}

	protected void elementsChanged(Object[] objects) {
		if (fContentProvider != null)
			fContentProvider.elementsChanged(objects);
	}

	protected void clear() {
		if (fContentProvider != null)
			fContentProvider.clear();
	}

	protected void configureTreeViewer(TreeViewer viewer) {
		viewer.setUseHashlookup(true);
		fContentProvider = new PHPSearchTreeContentProvider(this, fCurrentGrouping);
		viewer.setContentProvider(fContentProvider);
		viewer.setLabelProvider(new PostfixLabelProvider(this, PHPElementLabelProvider.SHOW_DEFAULT));
		viewer.setSorter(new PHPElementSorter());
	}

	protected void configureTableViewer(TableViewer viewer) {
		viewer.setUseHashlookup(true);
		fContentProvider = new PHPSearchTableContentProvider(this);
		viewer.setContentProvider(fContentProvider);
		viewer.setLabelProvider(new PostfixLabelProvider(this, PHPElementLabelProvider.SHOW_DEFAULT));
		viewer.setSorter(new PHPElementSorter());
	}

}