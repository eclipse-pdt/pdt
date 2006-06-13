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
package org.eclipse.php.debug.ui.preferences.phps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.debug.internal.ui.SWTUtil;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.debug.core.preferences.PHPexeItem;
import org.eclipse.php.debug.core.preferences.PHPexes;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.debug.ui.PHPDebugUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * A composite that displays installed PHP's in a table. PHPs can be 
 * added, removed, edited, and searched for.
 * <p>
 * This block implements ISelectionProvider - it sends selection change events
 * when the checked PHP in the table changes, or when the "use default" button
 * check state changes.
 * </p>
 */
public class InstalledPHPsBlock implements IAddPHPexeDialogRequestor, ISelectionProvider {

	/**
	 * This block's control
	 */
	private Composite fControl;

	/**
	 * VMs being displayed
	 */
	private List fPHPexes = new ArrayList();

	/**
	 * The main list control
	 */
	private CheckboxTableViewer fPHPExeList;

	// Action buttons
	private Button fAddButton;
	private Button fRemoveButton;
	private Button fEditButton;
	private Button fSearchButton;

	// column weights
	private float fWeight1 = 1 / 3F;
	private float fWeight2 = 1 / 3F;

	// ignore column re-sizing when the table is being resized
	private boolean fResizingTable = false;

	// index of column used for sorting
	private int fSortColumn = 0;

	PHPexes phpExes;

	/**
	 * Selection listeners (checked PHP changes)
	 */
	private ListenerList fSelectionListeners = new ListenerList(ListenerList.IDENTITY);

	/**
	 * Previous selection
	 */
	private ISelection fPrevSelection = new StructuredSelection();

	/** 
	 * Content provider to show a list of PHPs
	 */
	class PHPsContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object input) {
			return fPHPexes.toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

	}

	/**
	 * Label provider for installed PHPs table.
	 */
	class VMLabelProvider extends LabelProvider implements ITableLabelProvider {

		/**
		 * @see ITableLabelProvider#getColumnText(Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof PHPexeItem) {
				PHPexeItem phpExe = (PHPexeItem) element;
				switch (columnIndex) {
					case 0:
						return phpExe.getName();
					case 1:
						return phpExe.getLocation().getAbsolutePath();
				}
			}
			return element.toString();
		}

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		fSelectionListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection() {
		return new StructuredSelection(fPHPExeList.getCheckedElements());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		fSelectionListeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			if (!selection.equals(fPrevSelection)) {
				fPrevSelection = selection;
				Object jre = ((IStructuredSelection) selection).getFirstElement();
				if (jre == null) {
					fPHPExeList.setCheckedElements(new Object[0]);
				} else {
					fPHPExeList.setCheckedElements(new Object[] { jre });
					fPHPExeList.reveal(jre);
				}
				fireSelectionChanged();
			}
		}
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param ancestor containing control
	 * @param useManageButton whether to present a single 'manage...' button to
	 *  the user that opens the installed PHPs pref page for PHP management,
	 *  or to provide 'add, remove, edit, and search' buttons.
	 */
	public void createControl(Composite ancestor) {

		Composite parent = new Composite(ancestor, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		Font font = ancestor.getFont();
		parent.setFont(font);
		fControl = parent;

		GridData data;

		Label tableLabel = new Label(parent, SWT.NONE);
		tableLabel.setText(PHPDebugUIMessages.InstalledPHPsBlock_15); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		tableLabel.setLayoutData(data);
		tableLabel.setFont(font);

		Table table = new Table(parent, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);

		data = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(data);
		table.setFont(font);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		TableColumn column1 = new TableColumn(table, SWT.NULL);
		column1.setText(PHPDebugUIMessages.InstalledPHPsBlock_0); //$NON-NLS-1$
		column1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sortByName();
			}
		});

		TableColumn column2 = new TableColumn(table, SWT.NULL);
		column2.setText(PHPDebugUIMessages.InstalledPHPsBlock_1); //$NON-NLS-1$
		column2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sortByLocation();
			}
		});

		fPHPExeList = new CheckboxTableViewer(table);
		fPHPExeList.setLabelProvider(new VMLabelProvider());
		fPHPExeList.setContentProvider(new PHPsContentProvider());
		// by default, sort by name
		sortByName();

		fPHPExeList.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent evt) {
				enableButtons();
			}
		});

		fPHPExeList.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked()) {
					setCheckedPHP((PHPexeItem) event.getElement());
				} else {
					setCheckedPHP(null);
				}
			}
		});

		fPHPExeList.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent e) {
				if (!fPHPExeList.getSelection().isEmpty()) {
					editPHPexe();
				}
			}
		});
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.DEL && event.stateMask == 0) {
					removePHPexes();
				}
			}
		});

		Composite buttons = new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);
		buttons.setFont(font);

		fAddButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_3); //$NON-NLS-1$
		fAddButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				addPHPexe();
			}
		});

		fEditButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_4); //$NON-NLS-1$
		fEditButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				editPHPexe();
			}
		});

		fRemoveButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_5); //$NON-NLS-1$
		fRemoveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				removePHPexes();
			}
		});

		// copied from ListDialogField.CreateSeparator()
		Label separator = new Label(buttons, SWT.NONE);
		separator.setVisible(false);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.heightHint = 4;
		separator.setLayoutData(gd);

		fSearchButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_6); //$NON-NLS-1$
		fSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				search();
			}
		});

		configureTableResizing(parent, buttons, table, column1, column2);

		fillWithWorkspacePHPs();
		enableButtons();
	}

	/**
	 * Fire current selection
	 */
	private void fireSelectionChanged() {
		SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		Object[] listeners = fSelectionListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
			listener.selectionChanged(event);
		}
	}

	/**
	 * Sorts by VM type, and name within type.
	 */
	private void sortByType() {
		fPHPExeList.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof PHPexeItem) && (e2 instanceof PHPexeItem)) {
					PHPexeItem left = (PHPexeItem) e1;
					PHPexeItem right = (PHPexeItem) e2;
					String leftType = left.getName();
					String rightType = right.getName();
					int res = leftType.compareToIgnoreCase(rightType);
					if (res != 0) {
						return res;
					}
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
		fSortColumn = 3;
	}

	/**
	 * Sorts by VM name.
	 */
	private void sortByName() {
		fPHPExeList.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof PHPexeItem) && (e2 instanceof PHPexeItem)) {
					PHPexeItem left = (PHPexeItem) e1;
					PHPexeItem right = (PHPexeItem) e2;
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
		fSortColumn = 1;
	}

	/**
	 * Sorts by VM location.
	 */
	private void sortByLocation() {
		fPHPExeList.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof PHPexeItem) && (e2 instanceof PHPexeItem)) {
					PHPexeItem left = (PHPexeItem) e1;
					PHPexeItem right = (PHPexeItem) e2;
					return left.getLocation().getAbsolutePath().compareToIgnoreCase(right.getLocation().getAbsolutePath());
				}
				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
		fSortColumn = 2;
	}

	private void enableButtons() {
		int selectionCount = ((IStructuredSelection) fPHPExeList.getSelection()).size();
		fEditButton.setEnabled(selectionCount == 1);
		fRemoveButton.setEnabled(selectionCount > 0);
	}

	protected Button createPushButton(Composite parent, String label) {
		return SWTUtil.createPushButton(parent, label, null);
	}

	/**
	 * Correctly resizes the table so no phantom columns appear
	 */
	protected void configureTableResizing(final Composite parent, final Composite buttons, final Table table, final TableColumn column1, final TableColumn column2) {
		parent.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				resizeTable(parent, buttons, table, column1, column2);
			}
		});
		table.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				table.removeListener(SWT.Paint, this);
				resizeTable(parent, buttons, table, column1, column2);
			}
		});
		column1.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				if (column1.getWidth() > 0 && !fResizingTable) {
					fWeight1 = getColumnWeight(0);
				}
			}
		});
		column2.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				if (column2.getWidth() > 0 && !fResizingTable) {
					fWeight2 = getColumnWeight(1);
				}
			}
		});
	}

	private void resizeTable(Composite parent, Composite buttons, Table table, TableColumn column1, TableColumn column2) {
		fResizingTable = true;
		int parentWidth = -1;
		int parentHeight = -1;
		if (parent.isVisible()) {
			Rectangle area = parent.getClientArea();
			parentWidth = area.width;
			parentHeight = area.height;
		} else {
			Point parentSize = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			parentWidth = parentSize.x;
			parentHeight = parentSize.y;
		}
		Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int width = parentWidth - 2 * table.getBorderWidth();
		if (preferredSize.y > parentHeight) {
			// Subtract the scrollbar width from the total column width
			// if a vertical scrollbar will be required
			Point vBarSize = table.getVerticalBar().getSize();
			width -= vBarSize.x;
		}
		width -= buttons.getSize().x;
		Point oldSize = table.getSize();
		if (oldSize.x > width) {
			// table is getting smaller so make the columns
			// smaller first and then resize the table to
			// match the client area width
			column1.setWidth(Math.round(width * fWeight1));
			column2.setWidth(Math.round(width * fWeight2));
			table.setSize(width, parentHeight);
		} else {
			// table is getting bigger so make the table
			// bigger first and then make the columns wider
			// to match the client area width
			table.setSize(width, parentHeight);
			column1.setWidth(Math.round(width * fWeight1));
			column2.setWidth(Math.round(width * fWeight2));
		}
		fResizingTable = false;
	}

	/**
	 * Returns this block's control
	 * 
	 * @return control
	 */
	public Control getControl() {
		return fControl;
	}

	/**
	 * Sets the PHPs to be displayed in this block
	 * 
	 * @param phpExes PHPs to be displayed
	 */
	protected void setPHPs(PHPexeItem[] phpExes) {
		fPHPexes.clear();
		for (int i = 0; i < phpExes.length; i++) {
			fPHPexes.add(phpExes[i]);
		}
		fPHPExeList.setInput(fPHPexes);
		fPHPExeList.refresh();
	}

	/**
	 * Returns the PHPs currently being displayed in this block
	 * 
	 * @return PHPs currently being displayed in this block
	 */
	public PHPexeItem[] getPHPs() {
		return (PHPexeItem[]) fPHPexes.toArray(new PHPexeItem[fPHPexes.size()]);
	}

	/**
	 * Bring up a dialog that lets the user create a new VM definition.
	 */
	private void addPHPexe() {
		AddPHPexeDialog dialog = new AddPHPexeDialog(this, getShell(), phpExes, null);
		dialog.setTitle(PHPDebugUIMessages.InstalledPHPsBlock_7); //$NON-NLS-1$
		if (dialog.open() != Window.OK) {
			return;
		}
		fPHPExeList.refresh();
	}

	/**
	 * @see IAddPHPexeDialogRequestor#vmAdded(PHPexeItem)
	 */
	public void phpExeAdded(PHPexeItem phpExe) {
		fPHPexes.add(phpExe);
		fPHPExeList.refresh();
		if (fPHPexes.size() == 1) {
			setCheckedPHP(phpExe);
		}
	}

	/**
	 * @see IAddPHPexeDialogRequestor#isDuplicateName(String)
	 */
	public boolean isDuplicateName(String name) {
		for (int i = 0; i < fPHPexes.size(); i++) {
			PHPexeItem phpExe = (PHPexeItem) fPHPexes.get(i);
			if (phpExe.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private void editPHPexe() {
		IStructuredSelection selection = (IStructuredSelection) fPHPExeList.getSelection();
		PHPexeItem phpExe = (PHPexeItem) selection.getFirstElement();
		if (phpExe == null) {
			return;
		}
		AddPHPexeDialog dialog = new AddPHPexeDialog(this, getShell(), phpExes, phpExe);
		dialog.setTitle(PHPDebugUIMessages.InstalledPHPsBlock_8); //$NON-NLS-1$
		if (dialog.open() != Window.OK) {
			return;
		}
		fPHPExeList.refresh(phpExe);
	}

	private void removePHPexes() {
		IStructuredSelection selection = (IStructuredSelection) fPHPExeList.getSelection();
		PHPexeItem[] phpExes = new PHPexeItem[selection.size()];
		Iterator iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			phpExes[i] = (PHPexeItem) iter.next();
			i++;
		}
		removePHPs(phpExes);
	}

	/**
	 * Removes the given VMs from the table.
	 * 
	 * @param vms
	 */
	public void removePHPs(PHPexeItem[] phpExes) {
		IStructuredSelection prev = (IStructuredSelection) getSelection();
		for (int i = 0; i < phpExes.length; i++) {
			fPHPexes.remove(phpExes[i]);
			this.phpExes.removeItem(phpExes[i]);
		}
		fPHPExeList.refresh();
		IStructuredSelection curr = (IStructuredSelection) getSelection();
		if (!curr.equals(prev)) {
			PHPexeItem[] installs = getPHPs();
			if (curr.size() == 0 && installs.length == 1) {
				// pick a default VM automatically
				setSelection(new StructuredSelection(installs[0]));
			} else {
				fireSelectionChanged();
			}
		}
	}

	/**
	 * Search for installed VMs in the file system
	 */
	protected void search() {

		// choose a root directory for the search 
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setMessage(PHPDebugUIMessages.InstalledPHPsBlock_9); //$NON-NLS-1$
		dialog.setText(PHPDebugUIMessages.InstalledPHPsBlock_10); //$NON-NLS-1$
		String path = dialog.open();
		if (path == null) {
			return;
		}

		// ignore installed locations
		final Set exstingLocations = new HashSet();
		Iterator iter = fPHPexes.iterator();
		while (iter.hasNext()) {
			exstingLocations.add(((PHPexeItem) iter.next()).getPhpEXE().getParentFile());
		}

		// search
		final File rootDir = new File(path);
		final List locations = new ArrayList();

		IRunnableWithProgress r = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				monitor.beginTask(PHPDebugUIMessages.InstalledPHPsBlock_11, IProgressMonitor.UNKNOWN); //$NON-NLS-1$
				search(rootDir, locations, exstingLocations, monitor);
				monitor.done();
			}
		};

		try {
			ProgressMonitorDialog progress = new ProgressMonitorDialog(getShell());
			progress.run(true, true, r);
		} catch (InvocationTargetException e) {
			PHPDebugUIPlugin.log(e);
		} catch (InterruptedException e) {
			// cancelled
			return;
		}

		if (locations.isEmpty()) {
			MessageDialog.openInformation(getShell(), PHPDebugUIMessages.InstalledPHPsBlock_12, MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_13, new String[] { path })); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			iter = locations.iterator();
			while (iter.hasNext()) {
				File location = (File) iter.next();
				String name = PHPDebugUIMessages.InstalledPHPsBlock_16;
				String nameCopy = new String(name);
				int i = 1;
				while (isDuplicateName(nameCopy)) {
					nameCopy = name + '(' + i++ + ')';
				}
				PHPexeItem phpExe = new PHPexeItem(nameCopy, location, true);
				phpExes.addItem(phpExe);
				phpExeAdded(phpExe);
			}
		}

	}

	protected Shell getShell() {
		return getControl().getShell();
	}

	/**
	 * Searches the specified directory recursively for installed PHP executables, adding each
	 * detected executable to the <code>found</code> list. Any directories specified in
	 * the <code>ignore</code> are not traversed.
	 * 
	 * @param directory
	 * @param found
	 * @param types
	 * @param ignore
	 */
	protected void search(File directory, List found, Set ignore, IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		// Search the root directory
		if (!ignore.contains(directory)) {
			File foundExe = PHPexeItem.findPHPExecutable(directory);
			if (foundExe != null) {
				found.add(foundExe);
			}
		}

		String[] names = directory.list();
		if (names == null) {
			return;
		}
		List subDirs = new ArrayList();
		for (int i = 0; i < names.length; i++) {
			if (monitor.isCanceled()) {
				return;
			}
			File file = new File(directory, names[i]);
			//			PHPexeItem[] vmTypes = phpExes.getEditableItems();
			if (file.isDirectory()) {
				try {
					monitor.subTask(MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_14, new String[] { Integer.toString(found.size()), file.getCanonicalPath() })); //$NON-NLS-1$
				} catch (IOException e) {
				}
				if (!ignore.contains(file)) {
					if (monitor.isCanceled()) {
						return;
					}
					File foundExe = PHPexeItem.findPHPExecutable(file);
					if (foundExe != null) {
						found.add(foundExe);
						ignore.add(file);
					}
					subDirs.add(file);
				}
			}
		}
		while (!subDirs.isEmpty()) {
			File subDir = (File) subDirs.remove(0);
			search(subDir, found, ignore, monitor);
			if (monitor.isCanceled()) {
				return;
			}
		}

	}

	/**
	 * Sets the checked PHP, possible <code>null</code>
	 * 
	 * @param vm PHP or <code>null</code>
	 */
	public void setCheckedPHP(PHPexeItem vm) {
		if (vm == null) {
			setSelection(new StructuredSelection());
		} else {
			setSelection(new StructuredSelection(vm));
		}
	}

	/**
	 * Returns the checked PHP or <code>null</code> if none.
	 * 
	 * @return the checked PHP or <code>null</code> if none
	 */
	public PHPexeItem getCheckedPHP() {
		Object[] objects = fPHPExeList.getCheckedElements();
		if (objects.length == 0) {
			return null;
		}
		return (PHPexeItem) objects[0];
	}

	/**
	 * Persist table settings into the give dialog store, prefixed
	 * with the given key.
	 * 
	 * @param settings dialog store
	 * @param qualifier key qualifier
	 */
	public void saveColumnSettings(IDialogSettings settings, String qualifier) {
		for (int i = 0; i < 2; i++) {
			//persist the first 2 column weights
			settings.put(qualifier + ".column" + i, getColumnWeight(i)); //$NON-NLS-1$
		}
		settings.put(qualifier + ".sortColumn", fSortColumn); //$NON-NLS-1$
	}

	private float getColumnWeight(int col) {
		Table table = fPHPExeList.getTable();
		int tableWidth = table.getSize().x;
		int columnWidth = table.getColumn(col).getWidth();
		if (tableWidth > columnWidth) {
			return ((float) columnWidth) / tableWidth;
		}
		return 1 / 3F;
	}

	/**
	 * Restore table settings from the given dialog store using the
	 * given key.
	 * 
	 * @param settings dialog settings store
	 * @param qualifier key to restore settings from
	 */
	public void restoreColumnSettings(IDialogSettings settings, String qualifier) {
		fWeight1 = restoreColumnWeight(settings, qualifier, 0);
		fWeight2 = restoreColumnWeight(settings, qualifier, 1);
		fPHPExeList.getTable().layout(true);
		try {
			fSortColumn = settings.getInt(qualifier + ".sortColumn"); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			fSortColumn = 1;
		}
		switch (fSortColumn) {
			case 1:
				sortByName();
				break;
			case 2:
				sortByLocation();
				break;
			case 3:
				sortByType();
				break;
		}
	}

	private float restoreColumnWeight(IDialogSettings settings, String qualifier, int col) {
		try {
			return settings.getFloat(qualifier + ".column" + col); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			return 1 / 3F;
		}

	}

	/**
	 * Populates the PHP table with existing PHPs defined in the workspace.
	 */
	protected void fillWithWorkspacePHPs() {
		// fill with PHPs
		PHPexeItem[] items = phpExes.getItems();

		setPHPs(items);
	}

	public InstalledPHPsBlock(PHPexes phpExes) {
		this.phpExes = phpExes;
	}

}
