/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.composer.ui.parts.StructuredViewerPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class StructuredViewerSection extends ComposerSection {

	protected StructuredViewerPart viewerPart;

	private boolean doSelection;

	/**
	 * Constructor for StructuredViewerSection.
	 * 
	 * @param formPage
	 */
	public StructuredViewerSection(ComposerFormPage formPage, Composite parent, int style, String[] buttonLabels) {
		this(formPage, parent, style, true, buttonLabels);
	}

	/**
	 * Constructor for StructuredViewerSection.
	 * 
	 * @param formPage
	 */
	public StructuredViewerSection(ComposerFormPage formPage, Composite parent, int style, boolean titleBar,
			String[] buttonLabels) {
		super(formPage, parent, style, titleBar);
		viewerPart = createViewerPart(buttonLabels);
		viewerPart.setMinimumSize(50, 50);
		FormToolkit toolkit = formPage.getManagedForm().getToolkit();
		createClient(getSection(), toolkit);
		doSelection = true;
	}

	protected void createViewerPartControl(Composite parent, int style, int span, FormToolkit toolkit) {
		viewerPart.createControl(parent, style, span, toolkit);
		MenuManager popupMenuManager = new MenuManager();
		IMenuListener listener = new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager mng) {
				fillContextMenu(mng);
			}
		};
		popupMenuManager.addMenuListener(listener);
		popupMenuManager.setRemoveAllWhenShown(true);
		Control control = viewerPart.getControl();
		Menu menu = popupMenuManager.createContextMenu(control);
		control.setMenu(menu);
		registerPopupMenu(popupMenuManager);

	}

	/**
	 * If the context menu for this section should be registered, do it here with
	 * the appropriate id etc. By default do nothing.
	 * 
	 * @param popupMenuManager
	 *            the menu manager to be registered
	 */
	protected void registerPopupMenu(MenuManager popupMenuManager) {
		// do nothing by default
	}

	protected Composite createClientContainer(Composite parent, int span, FormToolkit toolkit) {
		Composite container = toolkit.createComposite(parent);
		container.setLayout(FormLayoutFactory.createSectionClientGridLayout(false, span));
		return container;
	}

	protected abstract StructuredViewerPart createViewerPart(String[] buttonLabels);

	protected void fillContextMenu(IMenuManager manager) {
	}

	protected void buttonSelected(int index) {
	}

	protected ISelection getViewerSelection() {
		return viewerPart.getViewer().getSelection();
	}

	protected void doPaste(Object targetObject, Object[] sourceObjects) {
		// NO-OP
		// Children will override to provide fuctionality
	}

	protected boolean canPaste(Object targetObject, Object[] sourceObjects) {
		return false;
	}

	@Override
	public void setFocus() {
		viewerPart.getControl().setFocus();
	}

	public StructuredViewerPart getStructuredViewerPart() {
		return this.viewerPart;
	}

	/**
	 * <p>
	 * Given the index of TreeViewer item and the size of the array of its immediate
	 * siblings, gets the index of the desired new selection as follows:
	 * <ul>
	 * <li>if this is the only item, return -1 (meaning select the parent)</li>
	 * <li>if this is the last item, return the index of the predecessor</li>
	 * <li>otherwise, return the index of the successor</li>
	 * </p>
	 * 
	 * @param thisIndex
	 *            the item's index
	 * @param length
	 *            the array length
	 * @return new selection index or -1 for parent
	 */
	protected int getNewSelectionIndex(int thisIndex, int length) {
		if (thisIndex == length - 1) {
			return thisIndex - 1;
		}
		return thisIndex + 1;
	}

	protected int getArrayIndex(Object[] array, Object object) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(object)) {
				return i;
			}
		}
		return -1;
	}

	protected void doSelect(boolean select) {
		doSelection = select;
	}

	protected boolean canSelect() {
		return doSelection;
	}

}
