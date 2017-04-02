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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.templates.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore.CompiledTemplate;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.viewsupport.ProjectTemplateStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.ui.StructuredTextViewerConfiguration;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;

/**
 * Templates page in new file wizard. Allows users to select a new file template
 * to be applied in new file.
 * 
 */
public abstract class NewGenericFileTemplatesWizardPage extends WizardPage {

	/**
	 * Content provider for templates
	 */
	private class TemplateContentProvider implements IStructuredContentProvider {
		/** The template store. */
		private ProjectTemplateStore fStore;

		/*
		 * @see IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
			fStore = null;
		}

		/*
		 * @see IStructuredContentProvider#getElements(Object)
		 */
		@Override
		public Object[] getElements(Object input) {
			return fStore.getTemplates(NewGenericFileTemplatesWizardPage.this.getTemplateContextTypeId());
		}

		/*
		 * @see IContentProvider#inputChanged(Viewer, Object, Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			fStore = (ProjectTemplateStore) newInput;
		}
	}

	/**
	 * Label provider for templates.
	 */
	private class TemplateLabelProvider extends LabelProvider implements ITableLabelProvider {

		/*
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java
		 * .lang.Object, int)
		 */
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/*
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.
		 * lang.Object, int)
		 */
		@Override
		public String getColumnText(Object element, int columnIndex) {
			Template template = (Template) element;

			switch (columnIndex) {
			case 0:
				return template.getName();
			case 1:
				return template.getDescription();
			default:
				return ""; //$NON-NLS-1$
			}
		}
	}

	/** Last selected template name */
	protected String fLastSelectedTemplateName;
	/** The viewer displays the pattern of selected template. */
	private SourceViewer fPatternViewer;
	/** The table presenting the templates. */
	private TableViewer fTableViewer;
	/** Template store used by this wizard page */
	private ProjectTemplateStore fTemplateStore;
	/** Checkbox for using templates. */
	protected Button fUseTemplateButton;
	private IProject fProject;

	public NewGenericFileTemplatesWizardPage(String title, String description) {
		super("NewGenericTemplatesWizardPage", title, null); //$NON-NLS-1$
		setDescription(description);
	}

	/**
	 * Correctly resizes the table so no phantom columns appear
	 * 
	 * @param parent
	 *            the parent control
	 * @param buttons
	 *            the buttons
	 * @param table
	 *            the table
	 * @param column1
	 *            the first column
	 * @param column2
	 *            the second column
	 * @param column3
	 *            the third column
	 */
	private void configureTableResizing(final Composite parent, final Table table, final TableColumn column1,
			final TableColumn column2) {
		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = parent.getClientArea();
				Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * table.getBorderWidth();
				if (preferredSize.y > area.height) {
					// Subtract the scrollbar width from the total column
					// width
					// if a vertical scrollbar will be required
					Point vBarSize = table.getVerticalBar().getSize();
					width -= vBarSize.x;
				}

				Point oldSize = table.getSize();
				if (oldSize.x > width) {
					// table is getting smaller so make the columns
					// smaller first and then resize the table to
					// match the client area width
					column1.setWidth(width / 2);
					column2.setWidth(width / 2);
					table.setSize(width, area.height);
				} else {
					// table is getting bigger so make the table
					// bigger first and then make the columns wider
					// to match the client area width
					table.setSize(width, area.height);
					column1.setWidth(width / 2);
					column2.setWidth(width / 2);
				}
			}
		});
	}

	@Override
	public void createControl(Composite ancestor) {
		Composite parent = new Composite(ancestor, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parent.setLayout(layout);

		// create checkbox for user to use HTML Template
		fUseTemplateButton = new Button(parent, SWT.CHECK);
		fUseTemplateButton.setText(this.getUseTemplateMessage());
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		fUseTemplateButton.setLayoutData(data);
		fUseTemplateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableTemplates();
			}
		});

		// create composite for Templates table
		Composite innerParent = new Composite(parent, SWT.NONE);
		GridLayout innerLayout = new GridLayout();
		innerLayout.numColumns = 2;
		innerLayout.marginHeight = 0;
		innerLayout.marginWidth = 0;
		innerParent.setLayout(innerLayout);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		innerParent.setLayoutData(gd);

		// Create linked text to just to templates preference page
		Link link = new Link(innerParent, SWT.NONE);
		link.setText(getTemplatesLocationMessage());
		data = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		link.setLayoutData(data);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				linkClicked();
			}
		});

		// create table that displays templates
		Table table = new Table(innerParent, SWT.BORDER | SWT.FULL_SELECTION);

		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(2);
		data.heightHint = convertHeightInCharsToPixels(10);
		data.horizontalSpan = 2;
		table.setLayoutData(data);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		TableColumn column1 = new TableColumn(table, SWT.NONE);
		column1.setText(Messages.NewGenericFileTemplatesWizardPage_0);

		TableColumn column2 = new TableColumn(table, SWT.NONE);
		column2.setText(Messages.NewGenericFileTemplatesWizardPage_1);

		fTableViewer = new TableViewer(table);
		fTableViewer.setLabelProvider(new TemplateLabelProvider());
		fTableViewer.setContentProvider(new TemplateContentProvider());

		fTableViewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object object1, Object object2) {
				if (object1 instanceof Template && object2 instanceof Template) {
					Template left = (Template) object1;
					Template right = (Template) object2;
					int result = left.getName().compareToIgnoreCase(right.getName());
					if (result != 0)
						return result;
					return left.getDescription().compareToIgnoreCase(right.getDescription());
				}
				return super.compare(viewer, object1, object2);
			}

			@Override
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});

		fTableViewer.addSelectionChangedListener(e -> updateViewerInput());

		// create viewer that displays currently selected template's contents
		fPatternViewer = doCreateViewer(parent);

		configureTableResizing(innerParent, table, column1, column2);

		resetTableViewerInput();
		Dialog.applyDialogFont(parent);
		setControl(parent);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.NEW);
	}

	@Override
	public void performHelp() {
		String helpId = getNewFileWizardTemplatePageHelpId();
		if (helpId == null) {
			helpId = IPHPHelpContextIds.CREATING_A_PHP_FILE_WITHIN_A_PROJECT;
		}
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), helpId);
		super.performHelp();
	}

	public void resetTableViewerInput() {
		IProject newProject = getProject();
		if ((fProject == null && fProject != newProject) || (fProject != null && !fProject.equals(newProject))) {
			fProject = newProject;
			fTemplateStore = getTemplateStore();
			fTableViewer.setInput(fTemplateStore);
			loadLastSavedPreferences();
		}
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			resetTableViewerInput();
		}
		super.setVisible(visible);
	}

	protected abstract ProjectTemplateStore getTemplateStore();

	protected abstract String getNewFileWizardTemplatePageHelpId();

	/**
	 * Creates, configures and returns a source viewer to present the template
	 * pattern on the preference page. Clients may override to provide a custom
	 * source viewer featuring e.g. syntax coloring.
	 * 
	 * @param parent
	 *            the parent control
	 * @return a configured source viewer
	 */
	private SourceViewer createViewer(Composite parent) {
		SourceViewerConfiguration sourceViewerConfiguration = new StructuredTextViewerConfiguration() {
			StructuredTextViewerConfiguration baseConfiguration = new PHPStructuredTextViewerConfiguration();

			@Override
			public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
				return baseConfiguration.getConfiguredContentTypes(sourceViewer);
			}

			@Override
			public LineStyleProvider[] getLineStyleProviders(ISourceViewer sourceViewer, String partitionType) {
				return baseConfiguration.getLineStyleProviders(sourceViewer, partitionType);
			}
		};
		SourceViewer viewer = new StructuredTextViewer(parent, null, null, false,
				SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		((StructuredTextViewer) viewer).getTextWidget()
				.setFont(JFaceResources.getFont("org.eclipse.wst.sse.ui.textfont")); //$NON-NLS-1$
		IStructuredModel scratchModel = StructuredModelManager.getModelManager()
				.createUnManagedStructuredModelFor(ContentTypeIdForPHP.ContentTypeID_PHP);
		IDocument document = scratchModel.getStructuredDocument();
		viewer.configure(sourceViewerConfiguration);
		viewer.setDocument(document);
		return viewer;
	}

	private SourceViewer doCreateViewer(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.NewGenericFileTemplatesWizardPage_2);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		SourceViewer viewer = createViewer(parent);
		viewer.setEditable(false);

		Control control = viewer.getControl();
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		data.heightHint = convertHeightInCharsToPixels(5);
		control.setLayoutData(data);

		return viewer;
	}

	/**
	 * Enable/disable controls in page based on fUseTemplateButton's current
	 * state.
	 */
	protected void enableTemplates() {
		boolean enabled = fUseTemplateButton.getSelection();

		if (!enabled) {
			// save last selected template
			Template template = getSelectedTemplate();
			if (template != null)
				fLastSelectedTemplateName = template.getName();
			else
				fLastSelectedTemplateName = ""; //$NON-NLS-1$

			fTableViewer.setSelection(null);
		} else {
			setSelectedTemplate(fLastSelectedTemplateName);
		}

		fTableViewer.getControl().setEnabled(enabled);
		fPatternViewer.getControl().setEnabled(enabled);
	}

	/**
	 * Return the template preference page id
	 * 
	 * @return
	 */
	protected abstract String getPreferencePageId();

	/**
	 * Get the currently selected template.
	 * 
	 * @return
	 */
	private Template getSelectedTemplate() {
		Template template = null;
		IStructuredSelection selection = (IStructuredSelection) fTableViewer.getSelection();

		if (selection.size() == 1) {
			template = (Template) selection.getFirstElement();
		}
		return template;
	}

	/**
	 * Returns template string to insert.
	 * 
	 * @return String to insert or null if none is to be inserted
	 */
	public CompiledTemplate compileTemplate() {
		Template template = getSelectedTemplate();
		return PHPTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template);
	}

	public CompiledTemplate compileTemplate(String containerName, String fileName) {
		Template template = getSelectedTemplate();
		return PHPTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template, containerName, fileName);
	}

	public CompiledTemplate compileTemplate(String containerName, String fileName, String lineDelimiter) {
		Template template = getSelectedTemplate();
		return PHPTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template, containerName, fileName,
				lineDelimiter);
	}

	public TemplateProposal createTemplateProposal() {
		TemplateProposal proposal = null;
		Template template = getSelectedTemplate();
		if (template != null) {
			TemplateContextType contextType = getTemplatesContextTypeRegistry()
					.getContextType(getTemplateContextTypeId());
			TemplateContext context = new DocumentTemplateContext(contextType, new Document(), 0, 0);
			proposal = new TemplateProposal(template, context, new Region(0, 0), null);
		}

		return proposal;
	}

	void linkClicked() {
		String pageId = getPreferencePageId();
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(getShell(), pageId, new String[] { pageId },
				null);
		dialog.open();
		fTableViewer.refresh();
	}

	/**
	 * Load the last template name used in New HTML File wizard.
	 */
	protected void loadLastSavedPreferences() {
		String templateName = getPreferenceStore().getString(PreferenceConstants.NEW_PHP_FILE_TEMPLATE);
		if (templateName == null || templateName.length() == 0) {
			fLastSelectedTemplateName = ""; //$NON-NLS-1$
			fUseTemplateButton.setSelection(false);
		} else {
			fLastSelectedTemplateName = templateName;
			fUseTemplateButton.setSelection(true);
		}
		enableTemplates();
	}

	protected IProject getProject() {
		IWizard wizard = getWizard();
		IProject project = null;
		if (wizard instanceof PHPFileCreationWizard) {
			project = ((PHPFileCreationWizard) wizard).getProject();
		}
		return project;
	}

	protected abstract IPreferenceStore getPreferenceStore();

	/**
	 * Save template name used for next call to New HTML File wizard.
	 */
	void saveLastSavedPreferences() {
		String templateName = ""; //$NON-NLS-1$

		Template template = getSelectedTemplate();
		if (template != null) {
			templateName = template.getName();
		}

		getPreferenceStore().setValue(PreferenceConstants.NEW_PHP_FILE_TEMPLATE, templateName);
	}

	/**
	 * Select a template in the table viewer given the template name. If
	 * template name cannot be found or templateName is null, just select first
	 * item in table. If no items in table select nothing.
	 * 
	 * @param templateName
	 */
	private void setSelectedTemplate(String templateName) {
		Object template = null;

		if (templateName != null && templateName.length() > 0) {
			// pick the last used template
			template = fTemplateStore.findTemplate(templateName, getTemplateContextTypeId());
		}

		// no record of last used template so just pick first element
		if (template == null) {
			// just pick first element
			template = fTableViewer.getElementAt(0);
		}

		if (template != null) {
			IStructuredSelection selection = new StructuredSelection(template);
			fTableViewer.setSelection(selection, true);
		}
	}

	/**
	 * Updates the pattern viewer.
	 */
	void updateViewerInput() {
		Template template = getSelectedTemplate();
		if (template != null) {
			fPatternViewer.getDocument().set(template.getPattern());
		}
	}

	protected abstract String getTemplateContextTypeId();

	protected abstract String getUseTemplateMessage();

	protected abstract String getTemplatesLocationMessage();

	protected abstract ContextTypeRegistry getTemplatesContextTypeRegistry();
}
