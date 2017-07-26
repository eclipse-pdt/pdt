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
package org.eclipse.php.internal.ui.wizards.fields;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * A list with a button bar. Typical buttons are 'Add', 'Remove', 'Up' and
 * 'Down'. List model is independend of widget creation. DialogFields controls
 * are: Label, List and Composite containing buttons.
 */
public class TreeListDialogField extends DialogField {
	protected TreeViewer fTree;
	protected ILabelProvider fLabelProvider;
	protected TreeViewerAdapter fTreeViewerAdapter;
	protected List<Object> fElements;
	protected ViewerSorter fViewerSorter;
	protected ViewerComparator fViewerComparator;

	protected String[] fButtonLabels;
	private Button[] fButtonControls;

	private boolean[] fButtonsEnabled;

	private int fRemoveButtonIndex;
	private int fUpButtonIndex;
	private int fDownButtonIndex;

	private Label fLastSeparator;

	Tree fTreeControl;
	private Composite fButtonsControl;
	private ISelection fSelectionWhenEnabled;

	private ITreeListAdapter fTreeAdapter;

	private Object fParentElement;
	private int fTreeExpandLevel;

	/**
	 * @param adapter
	 *            Can be <code>null</code>.
	 */
	public TreeListDialogField(ITreeListAdapter adapter, String[] buttonLabels, ILabelProvider lprovider) {
		super();
		fTreeAdapter = adapter;

		fLabelProvider = lprovider;
		fTreeViewerAdapter = new TreeViewerAdapter();
		fParentElement = this;

		fElements = new ArrayList<>(10);

		fButtonLabels = buttonLabels;
		if (fButtonLabels != null) {
			int nButtons = fButtonLabels.length;
			fButtonsEnabled = new boolean[nButtons];
			for (int i = 0; i < nButtons; i++) {
				fButtonsEnabled[i] = true;
			}
		}

		fTree = null;
		fTreeControl = null;
		fButtonsControl = null;

		fRemoveButtonIndex = -1;
		fUpButtonIndex = -1;
		fDownButtonIndex = -1;

		fTreeExpandLevel = 0;
	}

	/**
	 * Sets the index of the 'remove' button in the button label array passed in
	 * the constructor. The behaviour of the button marked as the 'remove'
	 * button will then behandled internally. (enable state, button invocation
	 * behaviour)
	 */
	public void setRemoveButtonIndex(int removeButtonIndex) {
		Assert.isTrue(removeButtonIndex < fButtonLabels.length);
		fRemoveButtonIndex = removeButtonIndex;
	}

	/**
	 * Sets the index of the 'up' button in the button label array passed in the
	 * constructor. The behaviour of the button marked as the 'up' button will
	 * then behandled internally. (enable state, button invocation behaviour)
	 */
	public void setUpButtonIndex(int upButtonIndex) {
		Assert.isTrue(upButtonIndex < fButtonLabels.length);
		fUpButtonIndex = upButtonIndex;
	}

	/**
	 * Sets the index of the 'down' button in the button label array passed in
	 * the constructor. The behaviour of the button marked as the 'down' button
	 * will then be handled internally. (enable state, button invocation
	 * behaviour)
	 */
	public void setDownButtonIndex(int downButtonIndex) {
		Assert.isTrue(downButtonIndex < fButtonLabels.length);
		fDownButtonIndex = downButtonIndex;
	}

	/**
	 * Sets the viewerSorter.
	 * 
	 * @param viewerSorter
	 *            The viewerSorter to set
	 */
	public void setViewerSorter(ViewerSorter viewerSorter) {
		fViewerSorter = viewerSorter;
	}

	public void setViewerComparator(ViewerComparator viewerComparator) {
		fViewerComparator = viewerComparator;
	}

	public void setTreeExpansionLevel(int level) {
		fTreeExpandLevel = level;
		if (isOkToUse(fTreeControl) && fTreeExpandLevel > 0) {
			fTree.expandToLevel(level);
		}
	}

	// ------ adapter communication

	private void buttonPressed(int index) {
		if (!managedButtonPressed(index) && fTreeAdapter != null) {
			fTreeAdapter.customButtonPressed(this, index);
		}
	}

	/**
	 * Checks if the button pressed is handled internally
	 * 
	 * @return Returns true if button has been handled.
	 */
	protected boolean managedButtonPressed(int index) {
		if (index == fRemoveButtonIndex) {
			remove();
		} else if (index == fUpButtonIndex) {
			up();
		} else if (index == fDownButtonIndex) {
			down();
		} else {
			return false;
		}
		return true;
	}

	// ------ layout helpers

	/*
	 * @see DialogField#doFillIntoGrid
	 */
	@Override
	public Control[] doFillIntoGrid(Composite parent, int nColumns) {
		PixelConverter converter = new PixelConverter(parent);

		assertEnoughColumns(nColumns);

		Label label = getLabelControl(parent);
		GridData gd = gridDataForLabel(1);
		gd.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(gd);

		Control list = getTreeControl(parent);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = false;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = nColumns - 2;
		gd.widthHint = converter.convertWidthInCharsToPixels(50);
		gd.heightHint = converter.convertHeightInCharsToPixels(6);

		list.setLayoutData(gd);

		Composite buttons = getButtonBox(parent);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = false;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 1;
		buttons.setLayoutData(gd);

		return new Control[] { label, list, buttons };
	}

	/*
	 * @see DialogField#getNumberOfControls
	 */
	@Override
	public int getNumberOfControls() {
		return 3;
	}

	/**
	 * Sets the minimal width of the buttons. Must be called after widget
	 * creation.
	 */
	public void setButtonsMinWidth(int minWidth) {
		if (fLastSeparator != null) {
			((GridData) fLastSeparator.getLayoutData()).widthHint = minWidth;
		}
	}

	// ------ ui creation

	/**
	 * Returns the tree control. When called the first time, the control will be
	 * created.
	 * 
	 * @param parent
	 *            The parent composite when called the first time, or
	 *            <code>null</code> after.
	 */
	public Control getTreeControl(Composite parent) {
		if (fTreeControl == null) {
			assertCompositeNotNull(parent);

			fTree = createTreeViewer(parent);

			fTreeControl = (Tree) fTree.getControl();
			fTreeControl.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					handleKeyPressed(e);
				}
			});
			fTree.setContentProvider(fTreeViewerAdapter);
			fTree.setLabelProvider(fLabelProvider);
			fTree.addSelectionChangedListener(fTreeViewerAdapter);
			fTree.addDoubleClickListener(fTreeViewerAdapter);

			fTree.setInput(fParentElement);
			fTree.expandToLevel(fTreeExpandLevel);

			if (fViewerSorter != null) {
				fTree.setComparator(fViewerSorter);
			}

			fTreeControl.setEnabled(isEnabled());
			if (fSelectionWhenEnabled != null) {
				postSetSelection(fSelectionWhenEnabled);
			}
		}
		return fTreeControl;
	}

	/**
	 * Returns the internally used table viewer.
	 */
	public TreeViewer getTreeViewer() {
		return fTree;
	}

	/*
	 * Subclasses may override to specify a different style.
	 */
	protected int getTreeStyle() {
		return SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL;
	}

	protected TreeViewer createTreeViewer(Composite parent) {
		Tree tree = new Tree(parent, getTreeStyle());
		tree.setFont(parent.getFont());
		return new TreeViewer(tree);
	}

	protected Button createButton(Composite parent, String label, SelectionListener listener) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		button.setText(label);
		button.addSelectionListener(listener);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.widthHint = SWTUtil.getButtonWidthHint(button);

		button.setLayoutData(gd);
		return button;
	}

	private Label createSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.NONE);
		separator.setFont(parent.getFont());
		separator.setVisible(false);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.heightHint = 4;
		separator.setLayoutData(gd);
		return separator;
	}

	/**
	 * Returns the composite containing the buttons. When called the first time,
	 * the control will be created.
	 * 
	 * @param parent
	 *            The parent composite when called the first time, or
	 *            <code>null</code> after.
	 */
	public Composite getButtonBox(Composite parent) {
		if (fButtonsControl == null) {
			assertCompositeNotNull(parent);

			SelectionListener listener = new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					doButtonSelected(e);
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					doButtonSelected(e);
				}
			};

			Composite contents = new Composite(parent, SWT.NONE);
			contents.setFont(parent.getFont());

			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			contents.setLayout(layout);

			if (fButtonLabels != null) {
				fButtonControls = new Button[fButtonLabels.length];
				for (int i = 0; i < fButtonLabels.length; i++) {
					String currLabel = fButtonLabels[i];
					if (currLabel != null) {
						fButtonControls[i] = createButton(contents, currLabel, listener);
						fButtonControls[i].setEnabled(isEnabled() && fButtonsEnabled[i]);
					} else {
						fButtonControls[i] = null;
						createSeparator(contents);
					}
				}
			}

			fLastSeparator = createSeparator(contents);

			updateButtonState();
			fButtonsControl = contents;
		}

		return fButtonsControl;
	}

	private void doButtonSelected(SelectionEvent e) {
		if (fButtonControls != null) {
			for (int i = 0; i < fButtonControls.length; i++) {
				if (e.widget == fButtonControls[i]) {
					buttonPressed(i);
					return;
				}
			}
		}
	}

	/**
	 * Handles key events in the table viewer. Specifically when the delete key
	 * is pressed.
	 */
	protected void handleKeyPressed(KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0) {
			if (fRemoveButtonIndex != -1 && isButtonEnabled(fTree.getSelection(), fRemoveButtonIndex)) {
				managedButtonPressed(fRemoveButtonIndex);
				return;
			}
		}
		fTreeAdapter.keyPressed(this, event);
	}

	// ------ enable / disable management

	/*
	 * @see DialogField#dialogFieldChanged
	 */
	@Override
	public void dialogFieldChanged() {
		super.dialogFieldChanged();
		updateButtonState();
	}

	/*
	 * Updates the enable state of the all buttons
	 */
	protected void updateButtonState() {
		if (fButtonControls != null) {
			ISelection sel = fTree.getSelection();
			for (int i = 0; i < fButtonControls.length; i++) {
				Button button = fButtonControls[i];
				if (isOkToUse(button)) {
					button.setEnabled(isButtonEnabled(sel, i));
				}
			}
		}
	}

	protected boolean containsAttributes(List<Object> selected) {
		for (int i = 0; i < selected.size(); i++) {
			if (!fElements.contains(selected.get(i))) {
				return true;
			}
		}
		return false;
	}

	protected boolean getManagedButtonState(ISelection sel, int index) {
		List<Object> selected = getSelectedElements();
		boolean hasAttributes = containsAttributes(selected);
		if (index == fRemoveButtonIndex) {
			return !selected.isEmpty() && !hasAttributes;
		} else if (index == fUpButtonIndex) {
			return !sel.isEmpty() && !hasAttributes && canMoveUp(selected);
		} else if (index == fDownButtonIndex) {
			return !sel.isEmpty() && !hasAttributes && canMoveDown(selected);
		}
		return true;
	}

	/*
	 * @see DialogField#updateEnableState
	 */
	@Override
	protected void updateEnableState() {
		super.updateEnableState();

		boolean enabled = isEnabled();
		if (isOkToUse(fTreeControl)) {
			if (!enabled) {
				fSelectionWhenEnabled = fTree.getSelection();
				selectElements(null);
			} else {
				selectElements(fSelectionWhenEnabled);
				fSelectionWhenEnabled = null;
			}
			fTreeControl.setEnabled(enabled);
		}
		updateButtonState();
	}

	/**
	 * Sets a button enabled or disabled.
	 */
	public void enableButton(int index, boolean enable) {
		if (fButtonsEnabled != null && index < fButtonsEnabled.length) {
			fButtonsEnabled[index] = enable;
			updateButtonState();
		}
	}

	private boolean isButtonEnabled(ISelection sel, int index) {
		boolean extraState = getManagedButtonState(sel, index);
		return isEnabled() && extraState && fButtonsEnabled[index];
	}

	// ------ model access

	/**
	 * Sets the elements shown in the list.
	 */
	public void setElements(List<Object> elements) {
		fElements = new ArrayList<>(elements);
		refresh();
		if (isOkToUse(fTreeControl)) {
			fTree.expandToLevel(fTreeExpandLevel);
		}
		dialogFieldChanged();
	}

	/**
	 * Gets the elements shown in the list. The list returned is a copy, so it
	 * can be modified by the user.
	 */
	public List<Object> getElements() {
		return new ArrayList<>(fElements);
	}

	/**
	 * Gets the element shown at the given index.
	 */
	public Object getElement(int index) {
		return fElements.get(index);
	}

	/**
	 * Gets the index of an element in the list or -1 if element is not in list.
	 */
	public int getIndexOfElement(Object elem) {
		return fElements.indexOf(elem);
	}

	/**
	 * Replace an element.
	 */
	public void replaceElement(Object oldElement, Object newElement) throws IllegalArgumentException {
		int idx = fElements.indexOf(oldElement);
		if (idx != -1) {
			fElements.set(idx, newElement);
			if (isOkToUse(fTreeControl)) {
				List<Object> selected = getSelectedElements();
				if (selected.remove(oldElement)) {
					selected.add(newElement);
				}
				boolean isExpanded = fTree.getExpandedState(oldElement);
				fTree.remove(oldElement);
				fTree.add(fParentElement, newElement);
				if (isExpanded) {
					fTree.expandToLevel(newElement, fTreeExpandLevel);
				}
				selectElements(new StructuredSelection(selected));
			}
			dialogFieldChanged();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds an element at the end of the tree list.
	 */
	public void addElement(Object element) {
		if (fElements.contains(element)) {
			return;
		}
		fElements.add(element);
		if (isOkToUse(fTreeControl)) {
			fTree.add(fParentElement, element);
			fTree.expandToLevel(element, fTreeExpandLevel);
		}
		dialogFieldChanged();
	}

	/**
	 * Adds elements at the end of the tree list.
	 */
	public void addElements(List<?> elements) {
		int nElements = elements.size();

		if (nElements > 0) {
			// filter duplicated
			ArrayList<Object> elementsToAdd = new ArrayList<>(nElements);

			for (int i = 0; i < nElements; i++) {
				Object elem = elements.get(i);
				if (!fElements.contains(elem)) {
					elementsToAdd.add(elem);
				}
			}
			fElements.addAll(elementsToAdd);
			if (isOkToUse(fTreeControl)) {
				fTree.add(fParentElement, elementsToAdd.toArray());
				for (int i = 0; i < elementsToAdd.size(); i++) {
					fTree.expandToLevel(elementsToAdd.get(i), fTreeExpandLevel);
				}
			}
			dialogFieldChanged();
		}
	}

	/**
	 * Adds an element at a position.
	 */
	public void insertElementAt(Object element, int index) {
		if (fElements.contains(element)) {
			return;
		}
		fElements.add(index, element);
		if (isOkToUse(fTreeControl)) {
			fTree.add(fParentElement, element);
			if (fTreeExpandLevel != -1) {
				fTree.expandToLevel(element, fTreeExpandLevel);
			}
		}

		dialogFieldChanged();
	}

	/**
	 * Adds an element at a position.
	 */
	public void removeAllElements() {
		if (fElements.size() > 0) {
			fElements.clear();
			refresh();
			dialogFieldChanged();
		}
	}

	/**
	 * Removes an element from the list.
	 */
	public void removeElement(Object element) throws IllegalArgumentException {
		if (fElements.remove(element)) {
			if (isOkToUse(fTreeControl)) {
				fTree.remove(element);
			}
			dialogFieldChanged();
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Removes elements from the list.
	 */
	public void removeElements(List<Object> elements) {
		if (elements.size() > 0) {
			fElements.removeAll(elements);
			if (isOkToUse(fTreeControl)) {
				fTree.remove(elements.toArray());
			}
			dialogFieldChanged();
		}
	}

	/**
	 * Gets the number of elements
	 */
	public int getSize() {
		return fElements.size();
	}

	public void selectElements(ISelection selection) {
		fSelectionWhenEnabled = selection;
		if (isOkToUse(fTreeControl)) {
			fTree.setSelection(selection, true);
		}
	}

	public void selectFirstElement() {
		Object element = null;
		if (fViewerSorter != null) {
			Object[] arr = fElements.toArray();
			fViewerSorter.sort(fTree, arr);
			if (arr.length > 0) {
				element = arr[0];
			}
		} else {
			if (fElements.size() > 0) {
				element = fElements.get(0);
			}
		}
		if (element != null) {
			selectElements(new StructuredSelection(element));
		}
	}

	public void postSetSelection(final ISelection selection) {
		if (isOkToUse(fTreeControl)) {
			Display d = fTreeControl.getDisplay();
			d.asyncExec(() -> {
				if (isOkToUse(fTreeControl)) {
					selectElements(selection);
				}
			});
		}
	}

	/**
	 * Refreshes the tree.
	 */
	@Override
	public void refresh() {
		super.refresh();
		if (isOkToUse(fTreeControl)) {
			fTree.refresh();
		}
	}

	/**
	 * Refreshes the tree.
	 */
	public void refresh(Object element) {
		if (isOkToUse(fTreeControl)) {
			fTree.refresh(element);
		}
	}

	/**
	 * Updates the element.
	 */
	public void update(Object element) {
		if (isOkToUse(fTreeControl)) {
			fTree.update(element, null);
		}
	}

	// ------- list maintenance

	private List<Object> moveUp(List<Object> elements, List<Object> move) {
		int nElements = elements.size();
		List<Object> res = new ArrayList<>(nElements);
		Object floating = null;
		for (int i = 0; i < nElements; i++) {
			Object curr = elements.get(i);
			if (move.contains(curr)) {
				res.add(curr);
			} else {
				if (floating != null) {
					res.add(floating);
				}
				floating = curr;
			}
		}
		if (floating != null) {
			res.add(floating);
		}
		return res;
	}

	private void moveUp(List<Object> toMoveUp) {
		if (toMoveUp.size() > 0) {
			setElements(moveUp(fElements, toMoveUp));
			fTree.reveal(toMoveUp.get(0));
		}
	}

	private void moveDown(List<Object> toMoveDown) {
		if (toMoveDown.size() > 0) {
			setElements(reverse(moveUp(reverse(fElements), toMoveDown)));
			fTree.reveal(toMoveDown.get(toMoveDown.size() - 1));
		}
	}

	private List<Object> reverse(List<Object> p) {
		List<Object> reverse = new ArrayList<>(p.size());
		for (int i = p.size() - 1; i >= 0; i--) {
			reverse.add(p.get(i));
		}
		return reverse;
	}

	private void remove() {
		removeElements(getSelectedElements());
	}

	private void up() {
		moveUp(getSelectedElements());
	}

	private void down() {
		moveDown(getSelectedElements());
	}

	private boolean canMoveUp(List<Object> selectedElements) {
		if (isOkToUse(fTreeControl)) {
			int nSelected = selectedElements.size();
			int nElements = fElements.size();
			for (int i = 0; i < nElements && nSelected > 0; i++) {
				if (!selectedElements.contains(fElements.get(i))) {
					return true;
				}
				nSelected--;
			}
		}
		return false;
	}

	private boolean canMoveDown(List<Object> selectedElements) {
		if (isOkToUse(fTreeControl)) {
			int nSelected = selectedElements.size();
			for (int i = fElements.size() - 1; i >= 0 && nSelected > 0; i--) {
				if (!selectedElements.contains(fElements.get(i))) {
					return true;
				}
				nSelected--;
			}
		}
		return false;
	}

	/**
	 * Returns the selected elements.
	 */
	public List<Object> getSelectedElements() {
		ArrayList<Object> result = new ArrayList<>();
		if (isOkToUse(fTreeControl)) {
			ISelection selection = fTree.getSelection();
			if (selection instanceof IStructuredSelection) {
				Iterator<?> iter = ((IStructuredSelection) selection).iterator();
				while (iter.hasNext()) {
					result.add(iter.next());
				}
			}
		}
		return result;
	}

	public void expandElement(Object element, int level) {
		if (isOkToUse(fTreeControl)) {
			fTree.expandToLevel(element, level);
		}
	}

	// ------- TreeViewerAdapter

	private class TreeViewerAdapter implements ITreeContentProvider, ISelectionChangedListener, IDoubleClickListener {

		private final Object[] NO_ELEMENTS = new Object[0];

		// ------- ITreeContentProvider Interface ------------

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// will never happen
		}

		public boolean isDeleted(Object element) {
			return false;
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object obj) {
			return fElements.toArray();
		}

		@Override
		public Object[] getChildren(Object element) {
			if (fTreeAdapter != null) {
				return fTreeAdapter.getChildren(TreeListDialogField.this, element);
			}
			return NO_ELEMENTS;
		}

		@Override
		public Object getParent(Object element) {
			if (!fElements.contains(element) && fTreeAdapter != null) {
				return fTreeAdapter.getParent(TreeListDialogField.this, element);
			}
			return fParentElement;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (fTreeAdapter != null) {
				return fTreeAdapter.hasChildren(TreeListDialogField.this, element);
			}
			return false;
		}

		// ------- ISelectionChangedListener Interface ------------

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			doListSelected(event);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.
		 * eclipse .jface.viewers.DoubleClickEvent)
		 */
		@Override
		public void doubleClick(DoubleClickEvent event) {
			doDoubleClick(event);
		}

	}

	protected void doListSelected(SelectionChangedEvent event) {
		updateButtonState();
		if (fTreeAdapter != null) {
			fTreeAdapter.selectionChanged(this);
		}
	}

	protected void doDoubleClick(DoubleClickEvent event) {
		if (fTreeAdapter != null) {
			fTreeAdapter.doubleClicked(this);
		}
	}

}
