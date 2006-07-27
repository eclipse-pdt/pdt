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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.actions.SelectionConverter;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;

public class PHPSearchPage extends DialogPage implements ISearchPage, IPHPSearchConstants {

	public PHPSearchPage() {
		super();
		fPreviousSearchPatterns = new ArrayList();
	}

	public PHPSearchPage(String title) {
		super(title);
		fPreviousSearchPatterns = new ArrayList();
	}

	public PHPSearchPage(String title, ImageDescriptor image) {
		super(title, image);
		fPreviousSearchPatterns = new ArrayList();
	}

	public boolean performAction() {
		return performNewSearch();
	}

	public void setContainer(ISearchPageContainer container) {
		fContainer = container;
	}

	/**
	 * Creates the page's content.
	 */
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		readConfiguration();

		Composite result = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 10;
		result.setLayout(layout);

		Control expressionComposite = createExpression(result);
		expressionComposite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));

		Label separator = new Label(result, SWT.NONE);
		separator.setVisible(false);
		GridData data = new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1);
		data.heightHint = convertHeightInCharsToPixels(1) / 3;
		separator.setLayoutData(data);

		Control searchFor = createSearchFor(result);
		searchFor.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));

		//createParticipants(result);

		SelectionAdapter phpElementInitializer = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (getSearchFor() == fInitialData.getSearchFor()) {
					fPhpElement = fInitialData.getPHPElement();
				} else {
					fPhpElement = null;
				}
//				setLimitTo(getSearchFor(), ALL_OCCURRENCES);
				doPatternModified();
			}
		};

		fSearchFor[CLASS].addSelectionListener(phpElementInitializer);
		fSearchFor[FUNCTION].addSelectionListener(phpElementInitializer);
		fSearchFor[CONSTANT].addSelectionListener(phpElementInitializer);

		setControl(result);

		Dialog.applyDialogFont(result);
		// TODO - Attach the help
		//		PlatformUI.getWorkbench().getHelpSystem().setHelp(result, IJavaHelpContextIds.JAVA_SEARCH_PAGE);	
	}

	private static class SearchPatternData {
		private int searchFor;
		private int limitTo;
		private String pattern;
		private boolean isCaseSensitive;
		private PHPCodeData phpElement;
		private int scope;
		private IWorkingSet[] workingSets;

		public SearchPatternData(int searchFor, int limitTo, boolean isCaseSensitive, String pattern, PHPCodeData phpElement) {
			this(searchFor, limitTo, pattern, isCaseSensitive, phpElement, ISearchPageContainer.WORKSPACE_SCOPE, null);
		}

		public SearchPatternData(int searchFor, int limitTo, String pattern, boolean isCaseSensitive, PHPCodeData phpElement, int scope, IWorkingSet[] workingSets) {
			this.searchFor = searchFor;
			this.limitTo = limitTo;
			this.pattern = pattern;
			this.isCaseSensitive = isCaseSensitive;
			this.phpElement = phpElement;
			this.scope = scope;
			this.workingSets = workingSets;
		}

		public void setPHPElement(PHPCodeData phpElement) {
			this.phpElement = phpElement;
		}

		public PHPCodeData getPHPElement() {
			return phpElement;
		}
		
		public boolean isCaseSensitive() {
			return isCaseSensitive;
		}
		
		public String getPattern() {
			return pattern;
		}

		public int getScope() {
			return scope;
		}

		public int getSearchFor() {
			return searchFor;
		}

		public IWorkingSet[] getWorkingSets() {
			return workingSets;
		}

		public void store(IDialogSettings settings) {
			settings.put("searchFor", searchFor); //$NON-NLS-1$
			settings.put("scope", scope); //$NON-NLS-1$
			settings.put("pattern", pattern); //$NON-NLS-1$
			settings.put("limitTo", limitTo); //$NON-NLS-1$
			// TODO - Check if this is right !!!
			settings.put("phpElement", phpElement != null ? phpElement.getName() : ""); //$NON-NLS-1$ //$NON-NLS-2$
			settings.put("isCaseSensitive", isCaseSensitive); //$NON-NLS-1$
			if (workingSets != null) {
				String[] wsIds = new String[workingSets.length];
				for (int i = 0; i < workingSets.length; i++) {
					wsIds[i] = workingSets[i].getId();
				}
				settings.put("workingSets", wsIds); //$NON-NLS-1$
			} else {
				settings.put("workingSets", new String[0]); //$NON-NLS-1$
			}

		}

		public static SearchPatternData create(IDialogSettings settings) {
			String pattern = settings.get("pattern"); //$NON-NLS-1$
			if (pattern.length() == 0) {
				return null;
			}
			//						PHPCodeData elem= null;
			//						String handleId= settings.get("phpElement"); //$NON-NLS-1$
			//						if (handleId != null && handleId.length() > 0) {
			//							PHPCodeData restored= JavaCore.create(handleId); //$NON-NLS-1$
			//							if (restored != null && isSearchableType(restored) && restored.exists()) {
			//								elem= restored;
			//							}
			//						}
			String[] wsIds = settings.getArray("workingSets"); //$NON-NLS-1$
			IWorkingSet[] workingSets = null;
			if (wsIds != null && wsIds.length > 0) {
				IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
				workingSets = new IWorkingSet[wsIds.length];
				for (int i = 0; workingSets != null && i < wsIds.length; i++) {
					workingSets[i] = workingSetManager.getWorkingSet(wsIds[i]);
					if (workingSets[i] == null) {
						workingSets = null;
					}
				}
			}

			try {
				int searchFor = settings.getInt("searchFor"); //$NON-NLS-1$
				int scope = settings.getInt("scope"); //$NON-NLS-1$
				int limitTo = settings.getInt("limitTo"); //$NON-NLS-1$
				boolean isCaseSensitive = settings.getBoolean("isCaseSensitive"); //$NON-NLS-1$

				return new SearchPatternData(searchFor, limitTo, pattern, isCaseSensitive, null, scope, workingSets);
			} catch (NumberFormatException e) {
				return null;
			}
		}

	}

	private static final int HISTORY_SIZE = 12;

	// Dialog store id constants
	private final static String PAGE_NAME = "PHPSearchPage"; //$NON-NLS-1$
	private final static String STORE_CASE_SENSITIVE = "CASE_SENSITIVE"; //$NON-NLS-1$
	private final static String STORE_HISTORY = "HISTORY"; //$NON-NLS-1$
	private final static String STORE_HISTORY_SIZE = "HISTORY_SIZE"; //$NON-NLS-1$

	private final List fPreviousSearchPatterns;

	private SearchPatternData fInitialData;
	private PHPCodeData fPhpElement;
	private boolean fFirstTime = true;
	private IDialogSettings fDialogSettings;
	private boolean fIsCaseSensitive;

	private Combo fPattern;
	private ISearchPageContainer fContainer;
	private Button fCaseSensitive;

	private Button[] fSearchFor;
	private String[] fSearchForText = { PHPUIMessages.SearchPage_searchFor_class, PHPUIMessages.SearchPage_searchFor_function, PHPUIMessages.SearchPage_searchFor_constant};

	//---- Action Handling ------------------------------------------------

	private boolean performNewSearch() {
		SearchPatternData data = getPatternData();

		// Setup search scope
		IPHPSearchScope scope = null;
		String scopeDescription = ""; //$NON-NLS-1$

		PHPSearchScopeFactory factory = PHPSearchScopeFactory.getInstance();

		int searchScope = getContainer().getSelectedScope();
		switch (searchScope) {
			case ISearchPageContainer.WORKSPACE_SCOPE:
				scopeDescription = PHPUIMessages.WorkspaceScope;
				scope = factory.createWorkspaceSearchScope(getSearchFor());
				break;
			case ISearchPageContainer.SELECTION_SCOPE:
				scopeDescription = PHPUIMessages.SelectionScope;
				scope = factory.createSelectedPHPSearchScope(getSearchFor(), getContainer().getSelection());
				break;
			case ISearchPageContainer.SELECTED_PROJECTS_SCOPE:
				scope = factory.createSelectedPHPProjectSearchScope(getSearchFor(), getContainer().getSelection());
				IProject[] projects = PHPSearchScopeFactory.getInstance().getProjects(scope);
				if (projects.length >= 1) {
					if (projects.length == 1)
						scopeDescription = Messages.format(PHPUIMessages.EnclosingProjectScope, projects[0].getName());
					else
						scopeDescription = Messages.format(PHPUIMessages.EnclosingProjectsScope, projects[0].getName());
				} else
					scopeDescription = Messages.format(PHPUIMessages.EnclosingProjectScope, ""); //$NON-NLS-1$
				break;
			case ISearchPageContainer.WORKING_SET_SCOPE:
				IWorkingSet[] workingSets = getContainer().getSelectedWorkingSets();
				// should not happen - just to be sure
				if (workingSets == null || workingSets.length < 1) {
					return false;
				}
				scopeDescription = Messages.format(PHPUIMessages.WorkingSetScope, SearchUtil.toString(workingSets));
				scope = factory.createWorkingSetSearchScope(getSearchFor(), getContainer().getSelectedWorkingSets());
//				SearchUtil.updateLRUWorkingSets(getContainer().getSelectedWorkingSets());

		}

		QuerySpecification querySpec = null;
		querySpec = new PatternQuerySpecification(data.getPattern(), data.getSearchFor(), data.isCaseSensitive(), scope, scopeDescription);
		data.setPHPElement(null);
		PHPSearchQuery textSearchJob = new PHPSearchQuery(querySpec);
		NewSearchUI.activateSearchResultView();
		NewSearchUI.runQueryInBackground(textSearchJob);
		return true;

		//		return false;
	}

	private String[] getPreviousSearchPatterns() {
		// Search results are not persistent
		int patternCount = fPreviousSearchPatterns.size();
		String[] patterns = new String[patternCount];
		for (int i = 0; i < patternCount; i++)
			patterns[i] = ((SearchPatternData) fPreviousSearchPatterns.get(i)).getPattern();
		return patterns;
	}

	private int getSearchFor() {
		for (int i = 0; i < fSearchFor.length; i++) {
			if (fSearchFor[i].getSelection())
				return i;
		}
		Assert.isTrue(false, "shouldNeverHappen"); //$NON-NLS-1$
		return -1;
	}

	private String getPattern() {
		return fPattern.getText();
	}

	private SearchPatternData findInPrevious(String pattern) {
		for (Iterator iter = fPreviousSearchPatterns.iterator(); iter.hasNext();) {
			SearchPatternData element = (SearchPatternData) iter.next();
			if (pattern.equals(element.getPattern())) {
				return element;
			}
		}
		return null;
	}

	/**
	 * Return search pattern data and update previous searches.
	 * An existing entry will be updated.
	 */
	private SearchPatternData getPatternData() {
		String pattern = getPattern();
		SearchPatternData match = findInPrevious(pattern);
		if (match != null) {
			fPreviousSearchPatterns.remove(match);
		}
		match = new SearchPatternData(getSearchFor(), 
			ALL_OCCURRENCES, 
			pattern, 
			fCaseSensitive.getSelection(), 
			fPhpElement, 
			getContainer().getSelectedScope(), 
			getContainer().getSelectedWorkingSets());

		fPreviousSearchPatterns.add(0, match); // insert on top
		return match;
	}

	/*
	 * Implements method from IDialogPage
	 */
	public void setVisible(boolean visible) {
		if (visible && fPattern != null) {
			if (fFirstTime) {
				fFirstTime = false;
				// Set item and text here to prevent page from resizing
				fPattern.setItems(getPreviousSearchPatterns());
				initSelections();
			}
			fPattern.setFocus();
		}
		updateOKStatus();
		super.setVisible(visible);
	}

	public boolean isValid() {
		return true;
	}

	//---- Widget creation ------------------------------------------------

	/*private Control createParticipants(Composite result) {
	 if (!SearchParticipantsExtensionPoint.hasAnyParticipants())
	 return new Composite(result, SWT.NULL);
	 Button selectParticipants= new Button(result, SWT.PUSH);
	 selectParticipants.setText(PHPUIMessages.getString("SearchPage.select_participants.label")); //$NON-NLS-1$
	 GridData gd= new GridData();
	 gd.verticalAlignment= GridData.VERTICAL_ALIGN_BEGINNING;
	 gd.horizontalAlignment= GridData.HORIZONTAL_ALIGN_END;
	 gd.grabExcessHorizontalSpace= false;
	 gd.horizontalAlignment= GridData.END;
	 gd.horizontalSpan= 2;
	 selectParticipants.setLayoutData(gd);
	 selectParticipants.addSelectionListener(new SelectionAdapter() {
	 public void widgetSelected(SelectionEvent e) {
	 PreferencePageSupport.showPreferencePage(getShell(), "org.eclipse.jdt.ui.preferences.SearchParticipantsExtensionPoint", new SearchParticipantsExtensionPoint()); //$NON-NLS-1$
	 }

	 });
	 return selectParticipants;
	 }*/

	private Control createExpression(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		result.setLayout(layout);

		// Pattern text + info
		Label label = new Label(result, SWT.LEFT);
		label.setText(PHPUIMessages.SearchPage_expression_label);
		label.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1));

		// Pattern combo
		fPattern = new Combo(result, SWT.SINGLE | SWT.BORDER);
		fPattern.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handlePatternSelected();
				updateOKStatus();
			}
		});
		fPattern.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				doPatternModified();
				updateOKStatus();

			}
		});
		GridData data = new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1);
		data.widthHint = convertWidthInCharsToPixels(50);
		fPattern.setLayoutData(data);

		// Ignore case checkbox		
		fCaseSensitive = new Button(result, SWT.CHECK);
		fCaseSensitive.setText(PHPUIMessages.SearchPage_expression_caseSensitive);
		fCaseSensitive.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fIsCaseSensitive = fCaseSensitive.getSelection();
			}
		});
		fCaseSensitive.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 1, 1));

		return result;
	}

	final void updateOKStatus() {
		boolean isValid = isValidSearchPattern();
		getContainer().setPerformActionEnabled(isValid);
	}

	private boolean isValidSearchPattern() {
		if (getPattern().length() == 0) {
			return false;
		}
		// fPhpElement == null
		return true;
		// TODO
//		return SearchPattern.createPattern(getPattern(), getSearchFor(), getLimitTo(), SearchPattern.R_EXACT_MATCH) != null;		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	public void dispose() {
		writeConfiguration();
		super.dispose();
	}

	private void doPatternModified() {
		if (fInitialData != null && getPattern().equals(fInitialData.getPattern()) && fInitialData.getPHPElement() != null && fInitialData.getSearchFor() == getSearchFor()) {
			fCaseSensitive.setEnabled(false);
			fCaseSensitive.setSelection(true);
			fPhpElement = fInitialData.getPHPElement();
		} else {
			fCaseSensitive.setEnabled(true);
			fCaseSensitive.setSelection(fIsCaseSensitive);
			fPhpElement = null;
		}
	}

	private void handlePatternSelected() {
		int selectionIndex= fPattern.getSelectionIndex();
		if (selectionIndex < 0 || selectionIndex >= fPreviousSearchPatterns.size())
			return;
		
		SearchPatternData initialData= (SearchPatternData) fPreviousSearchPatterns.get(selectionIndex);

		setSearchFor(initialData.getSearchFor());
//		setLimitTo(initialData.getSearchFor(), initialData.getLimitTo());

		fPattern.setText(initialData.getPattern());
		fIsCaseSensitive= initialData.isCaseSensitive();
		fPhpElement= initialData.getPHPElement();
		fCaseSensitive.setEnabled(fPhpElement == null);
		fCaseSensitive.setSelection(initialData.isCaseSensitive());

		
		if (initialData.getWorkingSets() != null)
			getContainer().setSelectedWorkingSets(initialData.getWorkingSets());
		else
			getContainer().setSelectedScope(initialData.getScope());
		
		fInitialData= initialData;
	}

	private void setSearchFor(int searchFor) {
		for (int i = 0; i < fSearchFor.length; i++) {
			fSearchFor[i].setSelection(searchFor == i);
		}
	}

	private Control createSearchFor(Composite parent) {
		Group result = new Group(parent, SWT.NONE);
		result.setText(PHPUIMessages.SearchPage_searchFor_label);
		result.setLayout(new GridLayout(3, true));

		fSearchFor = new Button[fSearchForText.length];
		for (int i = 0; i < fSearchForText.length; i++) {
			Button button = new Button(result, SWT.RADIO);
			button.setText(fSearchForText[i]);
			button.setSelection(i == FUNCTION);
			button.setLayoutData(new GridData());
			fSearchFor[i] = button;
		}

		// Fill with dummy radio buttons
		Label filler = new Label(result, SWT.NONE);
		filler.setVisible(false);
		filler.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		return result;
	}

	private void initSelections() {
		ISelection sel = getContainer().getSelection();
		SearchPatternData initData = null;
		if (sel instanceof ITextSelection) {
			IEditorPart activePart = getActiveEditor();
			if (activePart instanceof PHPStructuredEditor) {
				try {
					// TODO - At the time this code was written, the codeResolve always return an empty result. This will probably have to be fixed.
					PHPCodeData[] elements = SelectionConverter.codeResolve((PHPStructuredEditor) activePart);
					if (elements != null && elements.length > 0) {
						initData = determineInitValuesFrom(elements[0]);
					}
				} catch (Exception e) {
					// ignore
				}
			}
			if (initData == null) {
				initData = trySimpleTextSelection((ITextSelection) sel);
			}
		}
		if (initData == null) {
			initData = getDefaultInitValues();
		}

		fInitialData = initData;
		fPhpElement = initData.getPHPElement();
		fCaseSensitive.setSelection(initData.isCaseSensitive());
		fCaseSensitive.setEnabled(fPhpElement == null);

		setSearchFor(initData.getSearchFor());
		fPattern.setText(initData.getPattern());
	}

	private SearchPatternData determineInitValuesFrom(PHPCodeData codeData) {
		// TODO - Once we will get values to this function. For now, the codeResolve always return null and we never get to this point.
		
//		if (codeData instanceof PHPClassData) {
//			return new SearchPatternData(CLASS, ALL_OCCURRENCES, false, codeData.toString(), codeData);
//		}
		return null;
	}
	
	private SearchPatternData trySimpleTextSelection(ITextSelection selection) {
		String selectedText = selection.getText();
		if (selectedText != null && selectedText.length() > 0) {
			int i = 0;
			while (i < selectedText.length() && !isLineDelimiterChar(selectedText.charAt(i))) {
				i++;
			}
			if (i > 0) {
				return new SearchPatternData(FUNCTION, ALL_OCCURRENCES, fIsCaseSensitive, selectedText.substring(0, i), null);
			}
		}
		return null;
	}

	private static boolean isLineDelimiterChar(char ch) {
		return ch == '\n' || ch == '\r';
	}

	private SearchPatternData getDefaultInitValues() {
		if (!fPreviousSearchPatterns.isEmpty()) {
			return (SearchPatternData) fPreviousSearchPatterns.get(0);
		}
		return new SearchPatternData(FUNCTION, ALL_OCCURRENCES, fIsCaseSensitive, "", null); //$NON-NLS-1$
	}

	/**
	 * Returns the search page's container.
	 */
	private ISearchPageContainer getContainer() {
		return fContainer;
	}

	private IEditorPart getActiveEditor() {
		IWorkbenchPage activePage = PHPUiPlugin.getActivePage();
		if (activePage != null) {
			return activePage.getActiveEditor();
		}
		return null;
	}

	//--------------- Configuration handling --------------

	/**
	 * Returns the page settings for this Java search page.
	 * 
	 * @return the page settings to be used
	 */
	private IDialogSettings getDialogSettings() {
		IDialogSettings settings = PHPUiPlugin.getDefault().getDialogSettings();
		fDialogSettings = settings.getSection(PAGE_NAME);
		if (fDialogSettings == null)
			fDialogSettings = settings.addNewSection(PAGE_NAME);
		return fDialogSettings;
	}

	/**
	 * Initializes itself from the stored page settings.
	 */
	private void readConfiguration() {
		IDialogSettings s = getDialogSettings();
		fIsCaseSensitive = s.getBoolean(STORE_CASE_SENSITIVE);

		try {
			int historySize = s.getInt(STORE_HISTORY_SIZE);
			for (int i = 0; i < historySize; i++) {
				IDialogSettings histSettings = s.getSection(STORE_HISTORY + i);
				if (histSettings != null) {
					SearchPatternData data = SearchPatternData.create(histSettings);
					if (data != null) {
						fPreviousSearchPatterns.add(data);
					}
				}
			}
		} catch (NumberFormatException e) {
			// ignore
		}
	}

	/**
	 * Stores it current configuration in the dialog store.
	 */
	private void writeConfiguration() {
		IDialogSettings s = getDialogSettings();
		s.put(STORE_CASE_SENSITIVE, fIsCaseSensitive);

		int historySize = Math.min(fPreviousSearchPatterns.size(), HISTORY_SIZE);
		s.put(STORE_HISTORY_SIZE, historySize);
		for (int i = 0; i < historySize; i++) {
			IDialogSettings histSettings = s.addNewSection(STORE_HISTORY + i);
			SearchPatternData data = ((SearchPatternData) fPreviousSearchPatterns.get(i));
			data.store(histSettings);
		}
	}
}
