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
package org.eclipse.php.internal.debug.ui.preferences.stepFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.internal.core.StepFilterManager;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.*;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionValidator;

/**
 * The preference page for PHP step filtering, located at the node PHP > Debug >
 * Step Filtering
 * 
 * @author yaronm
 */
public class PHPDebugStepFilterPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	public static final String PREFIX = PHPDebugPlugin.ID + '.';
	public static final String PAGE_ID = "org.eclipse.php.debug.ui.PHPStepFilterPreferencePage"; //$NON-NLS-1$

	/**
	 * Content provider for the table. Content consists of instances of
	 * StepFilter.
	 */
	class StepFilterContentProvider implements IStructuredContentProvider {
		public StepFilterContentProvider() {
			initTableState(false);
		}

		public Object[] getElements(Object inputElement) {
			return getAllFiltersFromTable();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}
	}

	private ArrayList<IDebugStepFilterPrefListener> debugStepFilterPrefListeners = new ArrayList<IDebugStepFilterPrefListener>();

	// widgets
	private CheckboxTableViewer fTableViewer;
	private Button fUseStepFiltersButton;
	private Button fAddResourceButton;
	private Button fRemoveFilterButton;
	private Button fAddFilterButton;
	private Button fSelectAllButton;
	private Button fDeselectAllButton;

	/**
	 * Constructor
	 */
	public PHPDebugStepFilterPreferencePage() {
		super();
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
		setTitle(PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_stepFiltering);
		setDescription(PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_stepFiltersAreApplied);
		addPreferenceListener(DebugStepFilterController.getInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				PREFIX + "php_step_filter_preference_page_context"); //$NON-NLS-1$
		// The main composite
		Composite composite = SWTFactory.createComposite(parent, parent
				.getFont(), 1, 1, GridData.FILL_BOTH, 0, 0);
		createStepFilterPreferences(composite);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * handles the filter button being clicked
	 * 
	 * @param event
	 *            the clicked event
	 */
	private void handleFilterViewerKeyPress(KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0) {
			removeFilters();
		}
	}

	/**
	 * Create a group to contain the step filter related widgetry
	 */
	private void createStepFilterPreferences(Composite parent) {
		Composite container = SWTFactory.createComposite(parent, parent
				.getFont(), 2, 1, GridData.FILL_BOTH, 0, 0);
		fUseStepFiltersButton = SWTFactory
				.createCheckButton(
						container,
						PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_useStepFilters,
						null, DebugUITools.isUseStepFilters(), 2);
		fUseStepFiltersButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				setPageEnablement(fUseStepFiltersButton.getSelection());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		SWTFactory
				.createLabel(
						container,
						PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_definedStepFilters,
						2);
		fTableViewer = CheckboxTableViewer.newCheckList(container, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		fTableViewer.getTable().setFont(container.getFont());
		fTableViewer.setLabelProvider(new FilterLabelProvider());
		fTableViewer.setComparator(new FilterViewerComparator());
		fTableViewer.setContentProvider(new StepFilterContentProvider());

		fTableViewer.setInput(getAllPersistedFilters(false));
		fTableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		fTableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				((DebugStepFilter) event.getElement()).setEnabled(event
						.getChecked());
			}
		});
		fTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						if (selection.isEmpty()) {
							fRemoveFilterButton.setEnabled(false);
						} else {
							DebugStepFilter filter = (DebugStepFilter) ((StructuredSelection) selection)
									.getFirstElement();
							if (filter.isReadOnly()) {
								fRemoveFilterButton.setEnabled(false);
							} else {
								fRemoveFilterButton.setEnabled(true);
							}
						}
					}
				});
		fTableViewer.getControl().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				handleFilterViewerKeyPress(event);
			}
		});

		createStepFilterButtons(container);
		fUseStepFiltersButton.setSelection(DebugPlugin.isUseStepFilters());
		setPageEnablement(fUseStepFiltersButton.getSelection());
	}

	/**
	 * initializes the checked state of the filters when the dialog opens
	 */
	private void initTableState(boolean defaults) {
		DebugStepFilter[] filters = getAllPersistedFilters(defaults);
		if (filters == null) {
			return;
		}
		for (int i = 0; i < filters.length; i++) {
			fTableViewer.add(filters[i]);
			fTableViewer.setChecked(filters[i], filters[i].isEnabled());
		}
	}

	/**
	 * Enables or disables the widgets on the page, with the exception of
	 * <code>fUseStepFiltersButton</code> according to the passed boolean
	 * 
	 * @param enabled
	 *            the new enablement status of the page's widgets
	 * @since 3.2
	 */
	protected void setPageEnablement(boolean enabled) {
		fAddFilterButton.setEnabled(enabled);
		fAddResourceButton.setEnabled(enabled);
		fDeselectAllButton.setEnabled(enabled);
		fSelectAllButton.setEnabled(enabled);
		fTableViewer.getTable().setEnabled(enabled);
		fRemoveFilterButton.setEnabled(enabled
				& !fTableViewer.getSelection().isEmpty());
	}

	/**
	 * Creates the button for the step filter options
	 * 
	 * @param container
	 *            the parent container
	 */
	private void createStepFilterButtons(Composite container) {
		initializeDialogUnits(container);
		// button container
		Composite buttonContainer = new Composite(container, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		buttonContainer.setLayoutData(gd);
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.numColumns = 1;
		buttonLayout.marginHeight = 0;
		buttonLayout.marginWidth = 0;
		buttonContainer.setLayout(buttonLayout);
		// Add filter button
		fAddFilterButton = SWTFactory.createPushButton(buttonContainer,
				PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_addFilter,
				null);
		fAddFilterButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				addFilter();
			}
		});
		// Add resource button
		fAddResourceButton = SWTFactory
				.createPushButton(
						buttonContainer,
						PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_addResource,
						null);
		fAddResourceButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				addResource();
			}
		});
		// Remove button
		fRemoveFilterButton = SWTFactory.createPushButton(buttonContainer,
				PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_remove,
				null);
		fRemoveFilterButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				removeFilters();
			}
		});
		fRemoveFilterButton.setEnabled(false);

		Label separator = new Label(buttonContainer, SWT.NONE);
		separator.setVisible(false);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.heightHint = 4;
		separator.setLayoutData(gd);
		// Select All button
		fSelectAllButton = SWTFactory.createPushButton(buttonContainer,
				PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_selectAll,
				null);
		fSelectAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				fTableViewer.setAllChecked(true);
			}
		});
		// De-Select All button
		fDeselectAllButton = SWTFactory
				.createPushButton(
						buttonContainer,
						PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_deselectAll,
						null);
		fDeselectAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				fTableViewer.setAllChecked(false);
			}
		});

	}

	/**
	 * Allows a new filter to be added to the listing
	 */
	private void addFilter() {
		DebugStepFilter newfilter = CreateStepFilterDialog
				.showCreateStepFilterDialog(getShell(),
						getAllFiltersFromTable());
		if (newfilter == null) {
			return;
		}
		fTableViewer.add(newfilter);
		fTableViewer.setChecked(newfilter, true);
		fTableViewer.refresh(newfilter);
	}

	/**
	 * add a new type to the listing of available filters
	 */
	private void addResource() {
		PHPResourceSelectionDialog dialog = new PHPResourceSelectionDialog(
				getShell(),
				ResourcesPlugin.getWorkspace().getRoot(),
				true,
				PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_selectResourceToFilter);
		dialog.setValidator(new FilterSelectionValidator(
				getAllFiltersFromTable()));
		dialog.open();
		Object[] result = dialog.getResult();
		if (result != null && result.length > 0) {
			Object resourceToFilter = result[0];
			String filteredPath = ""; //$NON-NLS-1$
			if (resourceToFilter instanceof IResource) {
				filteredPath = ((IResource) resourceToFilter).getFullPath()
						.toString();
				if (resourceToFilter instanceof IProject) {
					addFilter(filteredPath, true, IStepFilterTypes.PHP_PROJECT);
				} else if (resourceToFilter instanceof IFolder) {
					addFilter(filteredPath, true,
							IStepFilterTypes.PHP_PROJECT_FOLDER);
				} else if (resourceToFilter instanceof IFile) {
					addFilter(filteredPath, true,
							IStepFilterTypes.PHP_PROJECT_FILE);
				}
			} else if (resourceToFilter instanceof IBuildpathEntry) {
				IBuildpathEntry entry = (IBuildpathEntry) resourceToFilter;
				filteredPath = EnvironmentPathUtils.getLocalPath(
						entry.getPath()).toOSString();
				if (entry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {// variable
					addFilter(filteredPath, true,
							IStepFilterTypes.PHP_INCLUDE_PATH_VAR);
				} else if (entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {// library
																					// =
																					// folder
					addFilter(filteredPath, true,
							IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY);
				}

			} else if (resourceToFilter instanceof IncPathFile) {
				IBuildpathEntry entry = ((IncPathFile) resourceToFilter)
						.getBuildpathEntry();
				File file = ((IncPathFile) resourceToFilter).file;
				filteredPath = file.getAbsolutePath();
				if (file.isDirectory()) {
					if (entry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
						addFilter(filteredPath, true,
								IStepFilterTypes.PHP_INCLUDE_PATH_VAR_FOLDER);
					} else if (entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
						addFilter(
								filteredPath,
								true,
								IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY_FOLDER);
					}
				} else {
					if (entry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
						addFilter(filteredPath, true,
								IStepFilterTypes.PHP_INCLUDE_PATH_VAR_FILE);
					} else if (entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
						addFilter(filteredPath, true,
								IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY_FILE);
					}
				}

			}
		}
	}

	/**
	 * Removes the currently selected filters.
	 */
	protected void removeFilters() {
		fTableViewer
				.remove(((IStructuredSelection) fTableViewer.getSelection())
						.toArray());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		ArrayList<String> filtersPersistenceList = new ArrayList<String>();
		DebugStepFilter[] filters = getAllFiltersFromTable();
		for (int i = 0; i < filters.length; i++) {
			filtersPersistenceList.add(filters[i].getType()
					+ DebugStepFilter.FILTER_TOKENS_DELIM
					+ filters[i].isEnabled()
					+ DebugStepFilter.FILTER_TOKENS_DELIM
					+ filters[i].isReadOnly()
					+ DebugStepFilter.FILTER_TOKENS_DELIM
					+ filters[i].getPath()); 
		}
		String pref = serializeList((String[]) filtersPersistenceList
				.toArray(new String[filtersPersistenceList.size()]));
		store.setValue(IPHPDebugConstants.PREF_STEP_FILTERS_LIST, pref);
		DebugPlugin.setUseStepFilters(fUseStepFiltersButton.getSelection());

		notifyPrefListeners();
		return super.performOk();
	}

	private void notifyPrefListeners() {
		DebugStepFilterEvent event = new DebugStepFilterEvent(
				getAllFiltersFromTable(), fUseStepFiltersButton.getSelection());
		Iterator<IDebugStepFilterPrefListener> iter = debugStepFilterPrefListeners
				.iterator();
		while (iter.hasNext()) {
			iter.next().debugStepFilterModified(event);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		boolean stepenabled = DebugPlugin.getDefault().getPluginPreferences()
				.getDefaultBoolean(StepFilterManager.PREF_USE_STEP_FILTERS);
		fUseStepFiltersButton.setSelection(stepenabled);
		setPageEnablement(stepenabled);
		DebugStepFilter[] filters = getAllFiltersFromTable();
		for (DebugStepFilter filter : filters) {
			if (!filter.isReadOnly()) {
				fTableViewer.remove(filter);
			}
		}
		super.performDefaults();
	}

	/**
	 * adds a single filter to the viewer
	 * 
	 * @param filter
	 *            the new filter to add
	 * @param checked
	 *            the checked state of the new filter
	 * @since 3.2
	 */
	protected void addFilter(String path, boolean checked, int type) {
		if (path != null) {
			DebugStepFilter f = new DebugStepFilter(type, checked, false, path);
			fTableViewer.add(f);
			fTableViewer.setChecked(f, checked);
		}
	}

	/**
	 * returns all of the filters from the table, this includes ones that have
	 * not yet been saved
	 * 
	 * @return a possibly empty lits of filters fron the table
	 * @since 3.2
	 */
	protected DebugStepFilter[] getAllFiltersFromTable() {
		TableItem[] items = fTableViewer.getTable().getItems();
		DebugStepFilter[] filters = new DebugStepFilter[items.length];
		for (int i = 0; i < items.length; i++) {
			filters[i] = (DebugStepFilter) items[i].getData();
			filters[i].setEnabled(items[i].getChecked());
		}
		return filters;
	}

	/**
	 * Returns all of the committed filters along with extended filters list
	 * 
	 * @return an array of committed filters
	 * @since 3.2
	 */
	protected DebugStepFilter[] getAllPersistedFilters(boolean defaults) {
		ArrayList<DebugStepFilter> filtersToAdd = new ArrayList<DebugStepFilter>();

		IPreferenceStore store = getPreferenceStore();
		String[] parsedFilters = parseList(store
				.getString(IPHPDebugConstants.PREF_STEP_FILTERS_LIST));
		for (int i = 0; i < parsedFilters.length; i++) {
			String[] tokens = parsedFilters[i]
					.split("\\" + DebugStepFilter.FILTER_TOKENS_DELIM); //$NON-NLS-1$
			if (tokens.length < 3) {
				return new DebugStepFilter[0];
			}
			DebugStepFilter filter = new DebugStepFilter(Integer
					.parseInt(tokens[0]), Boolean.parseBoolean(tokens[1]),
					Boolean.parseBoolean(tokens[2]), tokens[3]);

			filtersToAdd.add(filter);
		}

		DebugStepFilter[] extendedList = DebugStepFilterController
				.getInstance().getExtendedFiltersList();
		for (DebugStepFilter extendedFilter : extendedList) {
			if (!filtersToAdd.contains(extendedFilter)) {
				filtersToAdd.add(extendedFilter);
			}
		}

		DebugStepFilter[] result = new DebugStepFilter[filtersToAdd.size()];
		filtersToAdd.toArray(result);
		return result;

	}

	// Parses the comma separated string into an array of strings
	private String[] parseList(String listString) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(listString,
				DebugStepFilter.FILTERS_PREF_LIST_DELIM); 
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			list.add(token);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	// Serializes the array of strings into one comma
	// separated string.
	private String serializeList(String[] list) {
		if (list == null) {
			return ""; //$NON-NLS-1$
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < list.length; i++) {
			if (i > 0) {
				buffer.append(DebugStepFilter.FILTERS_PREF_LIST_DELIM); 
			}
			buffer.append(list[i]);
		}
		return buffer.toString();
	}

	class FilterSelectionValidator implements ISelectionValidator {

		private DebugStepFilter[] existingFilters;

		public FilterSelectionValidator(DebugStepFilter[] existingFilters) {
			this.existingFilters = existingFilters;
		}

		public String isValid(Object selection) {
			if (selection != null) {
				Path selectedPath = (Path) selection;
				for (DebugStepFilter filter : existingFilters) {
					if (selectedPath.equals(new Path(filter.getPath()))) {
						return PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_filterColon
								+ selectedPath
								+ PHPDebugUIMessages.PHPDebugStepFilterPreferencePage_alreadyExists;
					}
				}
			}
			return null;
		}
	}

	public void addPreferenceListener(IDebugStepFilterPrefListener listener) {
		debugStepFilterPrefListeners.add(listener);
	}

	public void removePreferenceListener(IDebugStepFilterPrefListener listener) {
		debugStepFilterPrefListeners.remove(listener);
	}
}
