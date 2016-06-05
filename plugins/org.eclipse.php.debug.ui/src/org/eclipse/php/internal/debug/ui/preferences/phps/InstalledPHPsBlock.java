/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [339547]
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPModuleInfo;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpProxyHandlersManager;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.wizards.ClosableWizardDialog;
import org.eclipse.php.internal.debug.ui.wizards.PHPExeEditDialog;
import org.eclipse.php.internal.debug.ui.wizards.PHPExeWizard;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * A composite that displays installed PHP's in a table. PHPs can be added,
 * removed, edited, and searched for.
 * <p>
 * This block implements ISelectionProvider - it sends selection change events
 * when the checked PHP in the table changes, or when the "use default" button
 * check state changes.
 * </p>
 * 
 * @author seva, shalom
 * 
 */
@SuppressWarnings("restriction")
public class InstalledPHPsBlock {

	/**
	 * Content provider to show a list of PHPs
	 */
	class PHPsContentProvider implements IStructuredContentProvider {

		public void dispose() {
		}

		public Object[] getElements(final Object input) {
			return fPHPexes.toArray();
		}

		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		}

	}

	/**
	 * Label provider for installed PHPs table.
	 */
	class PHPExeLabelProvider extends LabelProvider implements ITableLabelProvider, IFontProvider {

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		public Image getColumnImage(final Object element, final int columnIndex) {
			switch (columnIndex) {
			case 0:
				return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_PHP_EXE);
			}
			return null;
		}

		/**
		 * @see ITableLabelProvider#getColumnText(Object, int)
		 */
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof PHPexeItem) {
				final PHPexeItem phpExe = (PHPexeItem) element;
				switch (columnIndex) {
				case 0:
					if (isDefault(element)) {
						return phpExe.getName() + PHPDebugUIMessages.PHPsPreferencePage_WorkspaceDefault;
					}
					return phpExe.getName();
				case 1:
					String debuggerName = PHPDebuggersRegistry.getDebuggerName(phpExe.getDebuggerID());
					if (debuggerName == null) {
						debuggerName = ""; //$NON-NLS-1$
					}
					return debuggerName;
				case 2:
					File executable = phpExe.getExecutable();
					if (executable == null) {
						return ""; //$NON-NLS-1$
					}
					return executable.getAbsolutePath();
				}
			}
			return element.toString();
		}

		public Font getFont(Object element) {
			if (isDefault(element)) {
				return JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT);
			}
			return null;
		}

		private boolean isDefault(Object element) {
			if (element instanceof PHPexeItem) {
				return ((PHPexeItem) element).isDefault();
			}
			return false;
		}

	}

	private static final String[] PHP_CANDIDATE_BIN = { "php", "php-cli", //$NON-NLS-1$ //$NON-NLS-2$
			"php-cgi", "php.exe", "php-cli.exe", "php-cgi.exe" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	// Action buttons
	private Button fAddButton;

	/**
	 * This block's control
	 */
	private Composite fControl;
	private Button fEditButton;
	/**
	 * The main list control
	 */
	private TableViewer fPHPExeList;

	private TableColumnLayout fPHPExeLayout;

	/**
	 * VMs being displayed
	 */
	private final List<PHPexeItem> fPHPexes = new ArrayList<PHPexeItem>();

	private Button fRemoveButton;

	// ignore column re-sizing when the table is being resized
	private boolean fResizingTable = false;

	private Button fSearchButton;
	private Button fSetDefaultButton;
	/**
	 * Selection listeners (checked PHP changes)
	 */
	private final ListenerList fSelectionListeners = new ListenerList(ListenerList.IDENTITY);

	// index of column used for sorting
	private int fSortColumn = 0;

	// column weights
	private int fWeight1 = 3;
	private int fWeight2 = 2;
	private int fWeight3 = 3;

	public InstalledPHPsBlock() {
	}

	/**
	 * Bring up a dialog that lets the user create a new VM definition.
	 */
	private void addPHPexe() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		NullProgressMonitor monitor = new NullProgressMonitor();
		PHPexeItem phpExeItem = null;
		PHPExeWizard wizard = new PHPExeWizard(PHPexes.getInstance().getAllItems());
		ClosableWizardDialog dialog = new ClosableWizardDialog(shell, wizard);
		if (dialog.open() == Window.CANCEL) {
			monitor.setCanceled(true);
			return;
		}
		phpExeItem = (PHPexeItem) wizard.getRootFragment().getWizardModel().getObject(PHPExeWizard.MODEL);
		fPHPexes.add(phpExeItem);
		PHPexes.getInstance().addItem(phpExeItem);
		fPHPExeList.refresh();
		commitChanges();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		fSelectionListeners.add(listener);
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param ancestor
	 *            containing control the user that opens the installed PHPs pref
	 *            page for PHP management, or to provide 'add, remove, edit, and
	 *            search' buttons.
	 */
	public void createControl(final Composite ancestor) {

		final Composite parent = new Composite(ancestor, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		final Font font = ancestor.getFont();
		parent.setFont(font);
		fControl = parent;

		GridData data;

		final Label tableLabel = new Label(parent, SWT.NONE);
		tableLabel.setText(PHPDebugUIMessages.InstalledPHPsBlock_15);
		data = new GridData();
		data.horizontalSpan = 2;
		tableLabel.setLayoutData(data);
		tableLabel.setFont(font);

		Composite tableComposite = new Composite(parent, SWT.NONE);
		data = new GridData(GridData.FILL_BOTH);
		tableComposite.setLayoutData(data);
		tableComposite.setFont(font);
		fPHPExeLayout = new TableColumnLayout();
		tableComposite.setLayout(fPHPExeLayout);

		fPHPExeList = new TableViewer(tableComposite, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);

		final Table table = fPHPExeList.getTable();
		table.setFont(font);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		final TableColumn column1 = new TableColumn(table, SWT.NULL);
		column1.setText(PHPDebugUIMessages.InstalledPHPsBlock_0);
		column1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				sortByName();
			}
		});
		fPHPExeLayout.setColumnData(column1, new ColumnWeightData(fWeight1));

		final TableColumn column2 = new TableColumn(table, SWT.NULL);
		column2.setText(PHPDebugUIMessages.InstalledPHPsBlock_17);
		column2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				sortByDebugger();
			}
		});
		fPHPExeLayout.setColumnData(column2, new ColumnWeightData(fWeight2));

		final TableColumn column3 = new TableColumn(table, SWT.NULL);
		column3.setText(PHPDebugUIMessages.InstalledPHPsBlock_1);
		column3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				sortByLocation();
			}
		});

		fPHPExeLayout.setColumnData(column3, new ColumnWeightData(fWeight3));

		fPHPExeList.setLabelProvider(new PHPExeLabelProvider());
		fPHPExeList.setContentProvider(new PHPsContentProvider());
		fPHPExeList.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent evt) {
				enableButtons();
			}
		});

		fPHPExeList.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(final DoubleClickEvent e) {
				if (!fPHPExeList.getSelection().isEmpty())
					editPHPexe();
			}
		});
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent event) {
				if (fRemoveButton != null && !fRemoveButton.isDisposed() && !fRemoveButton.isEnabled()) {
					return;
				}
				if (event.character == SWT.DEL && event.stateMask == 0)
					removePHPexes();
			}
		});

		final Composite buttons = new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);
		buttons.setFont(font);

		fAddButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_3);
		fAddButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event evt) {
				addPHPexe();
			}
		});

		fEditButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_4);
		fEditButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event evt) {
				editPHPexe();
			}
		});

		fRemoveButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_5);
		fRemoveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event evt) {
				removePHPexes();
			}
		});

		fSetDefaultButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_setDefault);
		fSetDefaultButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PHPexeItem defaultItem = (PHPexeItem) ((IStructuredSelection) fPHPExeList.getSelection())
						.getFirstElement();
				PHPexes.getInstance().setDefaultItem(defaultItem);
				commitChanges();
				setPHPs(PHPexes.getInstance().getAllItems());
				// Preferences prefs =
				// PHPProjectPreferences.getModelPreferences();
				// prefs.setValue(PHPDebugCorePreferenceNames.DEFAULT_PHP,
				// defaultItem.getName());
			}
		});

		// copied from ListDialogField.CreateSeparator()
		final Label separator = new Label(buttons, SWT.NONE);
		separator.setVisible(false);
		final GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.heightHint = 4;
		separator.setLayoutData(gd);

		fSearchButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_6);
		fSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event evt) {
				search();
			}
		});

		fillWithWorkspacePHPs();
		// by default, sort by the debugger type
		sortByDebugger();
		enableButtons();
	}

	protected Button createPushButton(final Composite parent, final String label) {
		return SWTUtil.createPushButton(parent, label, null);
	}

	private void editPHPexe() {
		final IStructuredSelection selection = (IStructuredSelection) fPHPExeList.getSelection();
		final PHPexeItem phpExe = (PHPexeItem) selection.getFirstElement();
		if (phpExe == null) {
			return;
		}
		PHPExeEditDialog dialog = new PHPExeEditDialog(getShell(), phpExe, PHPexes.getInstance().getAllItems());
		if (dialog.open() != Window.OK) {
			return;
		}
		fPHPExeList.refresh();
		commitChanges();
	}

	private void enableButtons() {
		IStructuredSelection selection = (IStructuredSelection) fPHPExeList.getSelection();
		Object[] elements = selection.toArray();
		boolean canRemoveOrEdit = true;
		for (int i = 0; canRemoveOrEdit && i < elements.length; i++) {
			PHPexeItem item = (PHPexeItem) elements[i];
			canRemoveOrEdit &= item.isEditable();
		}
		final int selectionCount = selection.size();
		fRemoveButton.setEnabled(canRemoveOrEdit && selectionCount > 0);
		PHPexeItem selectedItem = (PHPexeItem) ((IStructuredSelection) fPHPExeList.getSelection()).getFirstElement();
		fSetDefaultButton.setEnabled(selectionCount == 1 && selectedItem != null && !selectedItem.isDefault());
	}

	/**
	 * Populates the PHP table with existing PHPs defined in the workspace.
	 */
	protected void fillWithWorkspacePHPs() {
		// fill with PHPs
		final PHPexeItem[] items = PHPexes.getInstance().getAllItems();
		setPHPs(items);
	}

	private int getColumnWeight(final int col) {
		final Table table = fPHPExeList.getTable();
		final int tableWidth = table.getSize().x;
		final int columnWidth = table.getColumn(col).getWidth();
		if (tableWidth > columnWidth) {
			return (int) ((float) columnWidth / (float) tableWidth * 100);
		}
		return 1;
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
	 * Returns the PHPs currently being displayed in this block
	 * 
	 * @return PHPs currently being displayed in this block
	 */
	public PHPexeItem[] getPHPs() {
		return fPHPexes.toArray(new PHPexeItem[fPHPexes.size()]);
	}

	protected Shell getShell() {
		return getControl().getShell();
	}

	/**
	 * @see IAddPHPexeDialogRequestor#isDuplicateName(String)
	 */
	public boolean isDuplicateName(final String name) {
		for (int i = 0; i < fPHPexes.size(); i++) {
			final PHPexeItem phpExe = fPHPexes.get(i);
			if (phpExe.getName().equals(name))
				return true;
		}
		return false;
	}

	private void removePHPexes() {
		final IStructuredSelection selection = (IStructuredSelection) fPHPExeList.getSelection();
		final PHPexeItem[] phpExes = new PHPexeItem[selection.size()];
		final Iterator<?> iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			phpExes[i] = (PHPexeItem) iter.next();
			i++;
		}
		removePHPs(phpExes);
		commitChanges();
	}

	public void commitChanges() {
		PHPexes.getInstance().save();
	}

	public void removePHPs(final PHPexeItem[] phpExes) {
		for (PHPexeItem element : phpExes) {
			fPHPexes.remove(element);
			PHPexes.getInstance().removeItem(element);
		}
		fPHPExeList.refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#
	 * removeSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		fSelectionListeners.remove(listener);
	}

	/**
	 * Restore table settings from the given dialog store using the given key.
	 * 
	 * @param settings
	 *            dialog settings store
	 * @param qualifier
	 *            key to restore settings from
	 */
	public void restoreColumnSettings(final IDialogSettings settings, final String qualifier) {
		fWeight1 = restoreColumnWeight(settings, qualifier, 0);
		fWeight2 = restoreColumnWeight(settings, qualifier, 1);
		fWeight3 = restoreColumnWeight(settings, qualifier, 2);

		fPHPExeLayout.setColumnData(fPHPExeList.getTable().getColumn(0), new ColumnWeightData(fWeight1));
		fPHPExeLayout.setColumnData(fPHPExeList.getTable().getColumn(1), new ColumnWeightData(fWeight2));
		fPHPExeLayout.setColumnData(fPHPExeList.getTable().getColumn(2), new ColumnWeightData(fWeight3));

		fPHPExeList.getTable().layout(true);
		try {
			fSortColumn = settings.getInt(qualifier + ".sortColumn"); //$NON-NLS-1$
		} catch (final NumberFormatException e) {
			fSortColumn = 1;
		}
		switch (fSortColumn) {
		case 1:
			sortByName();
			break;
		case 2:
			sortByDebugger();
			break;
		case 3:
			sortByLocation();
			break;
		case 4:
			sortByType();
			break;
		}

	}

	private int restoreColumnWeight(final IDialogSettings settings, final String qualifier, final int col) {
		try {
			int res = settings.getInt(qualifier + ".column" + col); //$NON-NLS-1$
			if (res > 0) {
				return res;
			}
		} catch (final NumberFormatException e) {
		}
		switch (col) {
		case 0:
			return fWeight1;
		case 1:
			return fWeight2;
		default:
			return fWeight3;
		}

	}

	/**
	 * Persist table settings into the give dialog store, prefixed with the
	 * given key.
	 * 
	 * @param settings
	 *            dialog store
	 * @param qualifier
	 *            key qualifier
	 */
	public void saveColumnSettings(final IDialogSettings settings, final String qualifier) {
		for (int i = 0; i < 3; i++) {
			settings.put(qualifier + ".column" + i, getColumnWeight(i)); //$NON-NLS-1$
		}
		settings.put(qualifier + ".sortColumn", fSortColumn); //$NON-NLS-1$
	}

	/**
	 * Search for installed VMs in the file system
	 */
	protected void search() {
		// choose a root directory for the search
		final DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setMessage(PHPDebugUIMessages.InstalledPHPsBlock_9);
		dialog.setText(PHPDebugUIMessages.InstalledPHPsBlock_10);
		final String path = dialog.open();
		if (path == null)
			return;
		final File rootDir = new File(path);
		final List<File> locations = new ArrayList<File>();
		final List<PHPexeItem> found = new ArrayList<PHPexeItem>();
		final IRunnableWithProgress r = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				monitor.beginTask(PHPDebugUIMessages.InstalledPHPsBlock_11, IProgressMonitor.UNKNOWN);
				search(rootDir, locations, monitor);
				if (!locations.isEmpty()) {
					monitor.setTaskName(PHPDebugUIMessages.InstalledPHPsBlock_Processing_search_results);
					Iterator<File> iter2 = locations.iterator();
					while (iter2.hasNext()) {
						if (monitor.isCanceled())
							break;
						File location = iter2.next();
						PHPexeItem phpExe = new PHPexeItem(null, location, null, null, true);
						if (phpExe.getName() == null)
							continue;
						String nameCopy = new String(phpExe.getName());
						monitor.subTask(MessageFormat
								.format(PHPDebugUIMessages.InstalledPHPsBlock_Fetching_php_exe_info, nameCopy));
						List<PHPModuleInfo> modules = PHPExeUtil.getModules(phpExe);
						AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry.getDebuggersConfigurations();
						for (AbstractDebuggerConfiguration debugger : debuggers) {
							for (PHPModuleInfo m : modules)
								if (m.getName().equalsIgnoreCase(debugger.getModuleId())) {
									phpExe.setDebuggerID(debugger.getDebuggerId());
									break;
								}
						}
						if (phpExe.getDebuggerID() == null)
							phpExe.setDebuggerID(PHPDebuggersRegistry.NONE_DEBUGGER_ID);
						int i = 1;
						// Check for duplicated names
						duplicates: while (true) {
							if (isDuplicateName(nameCopy)) {
								nameCopy = phpExe.getName() + ' ' + '[' + i++ + ']';
								continue duplicates;
							} else {
								for (PHPexeItem item : found)
									if (nameCopy.equalsIgnoreCase(item.getName())) {
										nameCopy = phpExe.getName() + ' ' + '[' + i++ + ']';
										continue duplicates;
									}
							}
							break duplicates;
						}
						phpExe.setName(nameCopy);
						if (phpExe.getExecutable() != null) {
							found.add(phpExe);
						}
					}
				}
				monitor.done();
			}
		};
		// Perform searching
		try {
			final ProgressMonitorDialog progress = new ProgressMonitorDialog(
					PlatformUI.getWorkbench().getDisplay().getActiveShell()) {
				protected void configureShell(Shell shell) {
					super.configureShell(shell);
					shell.setText(PHPDebugUIMessages.InstalledPHPsBlock_PHP_executables_search);
				};
			};
			progress.run(true, true, r);
		} catch (final InvocationTargetException e) {
			PHPDebugUIPlugin.log(e);
		} catch (final InterruptedException e) {
			// cancelled
			return;
		}
		// Show results
		if (!found.isEmpty()) {
			Comparator<PHPexeItem> sorter = new Comparator<PHPexeItem>() {
				@Override
				public int compare(PHPexeItem a, PHPexeItem b) {
					return b.getVersion().compareTo(a.getVersion());
				}
			};
			Collections.sort(found, sorter);
			PHPsSearchResultDialog searchDialog = new PHPsSearchResultDialog(found,
					MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_Search_result_dialog_message, path));
			searchDialog.open();
			List<PHPexeItem> itemsToAdd = searchDialog.getPHPExecutables();
			for (PHPexeItem item : itemsToAdd) {
				fPHPexes.add(item);
				PHPexes.getInstance().addItem(item);
				PHPexes.getInstance().save();
				DBGpProxyHandlersManager.INSTANCE.registerHandler(item.getUniqueId());
				PHPExeVerifier.verify(PHPexes.getInstance().getAllItems());
			}
			fPHPExeList.refresh();
		} else {
			MessageDialog.openInformation(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
					PHPDebugUIMessages.InstalledPHPsBlock_12,
					MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_13, new Object[] { path }));
		}

	}

	/**
	 * Searches the specified directory recursively for installed PHP
	 * executables, adding each detected executable to the <code>found</code>
	 * list. Any directories specified in the <code>ignore</code> are not
	 * traversed.
	 * 
	 * @param directory
	 * @param found
	 * @param types
	 * @param ignore
	 */
	protected void search(final File directory, final List<File> found, final IProgressMonitor monitor) {
		if (monitor.isCanceled())
			return;
		// Search the root directory
		List<File> foundExecs = findPHPExecutable(directory);
		if (!foundExecs.isEmpty()) {
			found.addAll(foundExecs);
			monitor.setTaskName(
					MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_Searching_with_found, found.size()));
		}
		final String[] names = directory.list();
		if (names == null)
			return;
		final List<File> subDirs = new ArrayList<File>();
		for (String element : names) {
			if (monitor.isCanceled())
				return;
			final File file = new File(directory, element);
			if (file.isDirectory()) {
				try {
					monitor.subTask(
							MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_14, file.getCanonicalPath()));
				} catch (final IOException e) {
				}
				if (monitor.isCanceled())
					return;
				subDirs.add(file);
			}
		}
		while (!subDirs.isEmpty()) {
			final File subDir = subDirs.remove(0);
			search(subDir, found, monitor);
			if (monitor.isCanceled())
				return;
		}
	}

	/**
	 * Locate a PHP executable file in the PHP location given to this method.
	 * The location should be a directory. The search is done for php and
	 * php.exe only.
	 * 
	 * @param phpLocation
	 *            A directory that might hold a PHP executable.
	 * @return A PHP executable file.
	 */
	private static List<File> findPHPExecutable(File phpLocation) {
		List<File> found = new ArrayList<File>(0);
		for (String element : PHP_CANDIDATE_BIN) {
			File phpExecFile = new File(phpLocation, element);
			if (phpExecFile.exists() && !phpExecFile.isDirectory()) {
				found.add(phpExecFile);
			}
		}
		return found;
	}

	/**
	 * Sets the PHPs to be displayed in this block
	 * 
	 * @param phpExes
	 *            PHPs to be displayed
	 */
	protected void setPHPs(final PHPexeItem[] phpExes) {
		fPHPexes.clear();
		for (PHPexeItem element : phpExes) {
			fPHPexes.add(element);
		}
		fPHPExeList.setInput(fPHPexes);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				fPHPExeList.refresh();
			}
		});
	}

	/**
	 * Sorts by VM location.
	 */
	private void sortByLocation() {
		fPHPExeList.setSorter(new ViewerSorter() {
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					return left.getExecutable().getAbsolutePath()
							.compareToIgnoreCase(right.getExecutable().getAbsolutePath());
				}
				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(final Object element, final String property) {
				return true;
			}
		});
		fSortColumn = 2;
	}

	/**
	 * Sorts by debugger type.
	 */
	private void sortByDebugger() {
		fPHPExeList.setSorter(new ViewerSorter() {
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					String leftDebugger = PHPDebuggersRegistry.getDebuggerName(left.getDebuggerID());
					String rightDebugger = PHPDebuggersRegistry.getDebuggerName(right.getDebuggerID());
					return rightDebugger.compareToIgnoreCase(leftDebugger);
				}
				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(final Object element, final String property) {
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
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(final Object element, final String property) {
				return true;
			}
		});
		fSortColumn = 1;
	}

	/**
	 * Sorts by VM type, and name within type.
	 */
	private void sortByType() {
		fPHPExeList.setSorter(new ViewerSorter() {
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					final String leftType = left.getName();
					final String rightType = right.getName();
					final int res = leftType.compareToIgnoreCase(rightType);
					if (res != 0)
						return res;
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(final Object element, final String property) {
				return true;
			}
		});
		fSortColumn = 3;
	}

}
