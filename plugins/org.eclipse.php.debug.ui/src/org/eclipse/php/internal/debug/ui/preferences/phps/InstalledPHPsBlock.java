/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.util.VersionUtils;
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
public class InstalledPHPsBlock {

	/**
	 * Content provider to show a list of PHPs
	 */
	class PHPsContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(final Object input) {
			return fPHPexes.toArray();
		}

		@Override
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
		@Override
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
		@Override
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
					String version = phpExe.getVersion();
					if (version == null) {
						return ""; //$NON-NLS-1$
					}
					return version;
				case 3:
					File executable = phpExe.getExecutable();
					if (executable == null) {
						return ""; //$NON-NLS-1$
					}
					return executable.getAbsolutePath();
				}
			}
			return element.toString();
		}

		@Override
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
	/**
	 * VMs being displayed
	 */
	private final List<PHPexeItem> fPHPexes = new ArrayList<>();

	private Button fRemoveButton;
	private Button fSearchButton;
	private Button fSetDefaultButton;

	public InstalledPHPsBlock() {
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

		Table table = new Table(tableComposite, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		fPHPExeList = new CheckboxTableViewer(table);
		fPHPExeList.setLabelProvider(new PHPExeLabelProvider());
		fPHPExeList.setContentProvider(new PHPsContentProvider());
		fPHPExeList.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent evt) {
				enableButtons();
			}
		});

		fPHPExeList.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent e) {
				if (!fPHPExeList.getSelection().isEmpty()) {
					editPHPexe();
				}
			}
		});
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent event) {
				if (fRemoveButton != null && !fRemoveButton.isDisposed() && !fRemoveButton.isEnabled()) {
					return;
				}
				if (event.character == SWT.DEL && event.stateMask == 0) {
					removePHPexes();
				}
			}
		});

		final TableColumn column1 = new TableColumn(table, SWT.NULL);
		column1.setText(PHPDebugUIMessages.InstalledPHPsBlock_0);
		column1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				sortByName();
			}
		});

		final TableColumn column2 = new TableColumn(table, SWT.NULL);
		column2.setText(PHPDebugUIMessages.InstalledPHPsBlock_17);
		column2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				sortByDebugger();
			}
		});

		final TableColumn column3 = new TableColumn(table, SWT.NULL);
		column3.setText(PHPDebugUIMessages.InstalledPHPsBlock_18);
		column3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				sortByVersion();
			}
		});

		final TableColumn column4 = new TableColumn(table, SWT.NULL);
		column4.setText(PHPDebugUIMessages.InstalledPHPsBlock_1);
		column4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				sortByLocation();
			}
		});

		// Create table column layout
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		PixelConverter pixelConverter = new PixelConverter(font);
		tableColumnLayout.setColumnData(column1,
				new ColumnWeightData(20, pixelConverter.convertWidthInCharsToPixels(24)));
		tableColumnLayout.setColumnData(column2,
				new ColumnWeightData(15, pixelConverter.convertWidthInCharsToPixels(18)));
		tableColumnLayout.setColumnData(column3,
				new ColumnWeightData(10, pixelConverter.convertWidthInCharsToPixels(10)));
		tableColumnLayout.setColumnData(column4,
				new ColumnWeightData(35, pixelConverter.convertWidthInCharsToPixels(28)));
		tableComposite.setLayout(tableColumnLayout);

		final Composite buttons = new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);
		buttons.setFont(font);

		fAddButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_3);
		fAddButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event evt) {
				addPHPexe();
			}
		});

		fEditButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_4);
		fEditButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event evt) {
				editPHPexe();
			}
		});

		fRemoveButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_5);
		fRemoveButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event evt) {
				removePHPexes();
			}
		});

		fSetDefaultButton = createPushButton(buttons, PHPDebugUIMessages.InstalledPHPsBlock_setDefault);
		fSetDefaultButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PHPexeItem defaultItem = (PHPexeItem) ((IStructuredSelection) fPHPExeList.getSelection())
						.getFirstElement();
				PHPexes.getInstance().setDefaultItem(defaultItem);
				commitChanges();
				setPHPs(PHPexes.getInstance().getAllItems());
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
			@Override
			public void handleEvent(final Event evt) {
				search();
			}
		});

		fillWithWorkspacePHPs();
		// by default, sort by name
		sortByName();
		enableButtons();
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

	/**
	 * @see IAddPHPexeDialogRequestor#isDuplicateName(String)
	 */
	public boolean isDuplicateName(final String name) {
		for (int i = 0; i < fPHPexes.size(); i++) {
			final PHPexeItem phpExe = fPHPexes.get(i);
			if (phpExe.getName().equals(name)) {
				return true;
			}
		}
		return false;
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

	protected Button createPushButton(final Composite parent, final String label) {
		return SWTUtil.createPushButton(parent, label, null);
	}

	/**
	 * Populates the PHP table with existing PHPs defined in the workspace.
	 */
	protected void fillWithWorkspacePHPs() {
		// fill with PHPs
		final PHPexeItem[] items = PHPexes.getInstance().getAllItems();
		setPHPs(items);
	}

	protected Shell getShell() {
		return getControl().getShell();
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
		if (path == null) {
			return;
		}
		final File rootDir = new File(path);
		final List<File> locations = new ArrayList<>();
		final List<PHPexeItem> found = new ArrayList<>();
		final IRunnableWithProgress r = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				monitor.beginTask(PHPDebugUIMessages.InstalledPHPsBlock_11, IProgressMonitor.UNKNOWN);
				search(rootDir, locations, monitor);
				if (!locations.isEmpty()) {
					monitor.setTaskName(PHPDebugUIMessages.InstalledPHPsBlock_Processing_search_results);
					Iterator<File> iter2 = locations.iterator();
					while (iter2.hasNext()) {
						if (monitor.isCanceled()) {
							break;
						}
						File location = iter2.next();
						PHPexeItem phpExe = new PHPexeItem(null, location, null, null, true);
						if (phpExe.getName() == null) {
							continue;
						}
						String nameCopy = new String(phpExe.getName());
						monitor.subTask(MessageFormat
								.format(PHPDebugUIMessages.InstalledPHPsBlock_Fetching_php_exe_info, nameCopy));
						List<PHPModuleInfo> modules = PHPExeUtil.getModules(phpExe);
						AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry.getDebuggersConfigurations();
						for (AbstractDebuggerConfiguration debugger : debuggers) {
							for (PHPModuleInfo m : modules) {
								if (m.getName().equalsIgnoreCase(debugger.getModuleId())) {
									phpExe.setDebuggerID(debugger.getDebuggerId());
									break;
								}
							}
						}
						if (phpExe.getDebuggerID() == null) {
							phpExe.setDebuggerID(PHPDebuggersRegistry.NONE_DEBUGGER_ID);
						}
						int i = 1;
						// Check for duplicated names
						duplicates: while (true) {
							if (isDuplicateName(nameCopy)) {
								nameCopy = phpExe.getName() + ' ' + '[' + i++ + ']';
								continue duplicates;
							} else {
								for (PHPexeItem item : found) {
									if (nameCopy.equalsIgnoreCase(item.getName())) {
										nameCopy = phpExe.getName() + ' ' + '[' + i++ + ']';
										continue duplicates;
									}
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
				@Override
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
	 * Searches the specified directory recursively for installed PHP executables,
	 * adding each detected executable to the <code>found</code> list. Any
	 * directories specified in the <code>ignore</code> are not traversed.
	 * 
	 * @param directory
	 * @param found
	 * @param types
	 * @param ignore
	 */
	protected void search(final File directory, final List<File> found, final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}
		// Search the root directory
		List<File> foundExecs = findPHPExecutable(directory);
		if (!foundExecs.isEmpty()) {
			found.addAll(foundExecs);
			monitor.setTaskName(
					MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_Searching_with_found, found.size()));
		}
		final String[] names = directory.list();
		if (names == null) {
			return;
		}
		final List<File> subDirs = new ArrayList<>();
		for (String element : names) {
			if (monitor.isCanceled()) {
				return;
			}
			final File file = new File(directory, element);
			if (file.isDirectory()) {
				try {
					monitor.subTask(
							MessageFormat.format(PHPDebugUIMessages.InstalledPHPsBlock_14, file.getCanonicalPath()));
				} catch (final IOException e) {
				}
				if (monitor.isCanceled()) {
					return;
				}
				subDirs.add(file);
			}
		}
		while (!subDirs.isEmpty()) {
			final File subDir = subDirs.remove(0);
			search(subDir, found, monitor);
			if (monitor.isCanceled()) {
				return;
			}
		}
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
			@Override
			public void run() {
				fPHPExeList.refresh();
			}
		});
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

	/**
	 * Sorts by VM location.
	 */
	private void sortByLocation() {
		fPHPExeList.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					return left.getExecutable().getAbsolutePath()
							.compareToIgnoreCase(right.getExecutable().getAbsolutePath());
				}
				return super.compare(viewer, e1, e2);
			}
		});
	}

	/**
	 * Sorts by debugger type.
	 */
	private void sortByDebugger() {
		fPHPExeList.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					String leftDebugger = PHPDebuggersRegistry.getDebuggerName(left.getDebuggerID());
					String rightDebugger = PHPDebuggersRegistry.getDebuggerName(right.getDebuggerID());
					return rightDebugger.compareToIgnoreCase(leftDebugger);
				}
				return super.compare(viewer, e1, e2);
			}
		});
	}

	/**
	 * Sorts by VM name.
	 */
	private void sortByName() {
		fPHPExeList.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}
		});
	}

	/**
	 * Sorts by VM type, and name within type.
	 */
	private void sortByVersion() {
		fPHPExeList.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof PHPexeItem && e2 instanceof PHPexeItem) {
					final PHPexeItem left = (PHPexeItem) e1;
					final PHPexeItem right = (PHPexeItem) e2;
					final String leftVersion = left.getVersion();
					final String rightVersion = right.getVersion();
					if (VersionUtils.greater(leftVersion, rightVersion, 3)) {
						return 1;
					} else if (VersionUtils.equal(leftVersion, rightVersion, 3)) {
						return 0;
					} else {
						return -1;
					}
				}
				return super.compare(viewer, e1, e2);
			}
		});
	}

	/**
	 * Locate a PHP executable file in the PHP location given to this method. The
	 * location should be a directory. The search is done for php and php.exe only.
	 * 
	 * @param phpLocation
	 *            A directory that might hold a PHP executable.
	 * @return A PHP executable file.
	 */
	private static List<File> findPHPExecutable(File phpLocation) {
		List<File> found = new ArrayList<>(0);
		for (String element : PHP_CANDIDATE_BIN) {
			File phpExecFile = new File(phpLocation, element);
			if (phpExecFile.exists() && !phpExecFile.isDirectory()) {
				found.add(phpExecFile);
			}
		}
		return found;
	}

}
