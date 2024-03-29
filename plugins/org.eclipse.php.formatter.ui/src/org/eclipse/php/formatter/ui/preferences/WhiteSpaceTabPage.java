/*******************************************************************************
 * Copyright (c) 2013-2019 Zend Techologies Ltd.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.ui.preferences;

import java.util.*;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.profiles.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.formatter.ui.FormatterUIPlugin;
import org.eclipse.php.formatter.ui.preferences.WhiteSpaceOptions.InnerNode;
import org.eclipse.php.formatter.ui.preferences.WhiteSpaceOptions.Node;
import org.eclipse.php.formatter.ui.preferences.WhiteSpaceOptions.OptionNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.part.PageBook;

public class WhiteSpaceTabPage extends ModifyDialogTabPage {

	/**
	 * Encapsulates a view of the options tree which is structured by
	 * syntactical element.
	 */

	private final class SyntaxComponent
			implements ISelectionChangedListener, ICheckStateListener, IDoubleClickListener {

		private final String PREF_NODE_KEY = FormatterUIPlugin.PLUGIN_ID + "formatter_page.white_space_tab_page.node"; //$NON-NLS-1$

		private final List<Node> fIndexedNodeList;
		private final List<Node> fTree;

		private ContainerCheckedTreeViewer fTreeViewer;
		private Composite fComposite;

		private Node fLastSelected = null;

		public SyntaxComponent() {
			fIndexedNodeList = new ArrayList<>();
			fTree = WhiteSpaceOptions.createAltTree(fworkingValues);
			WhiteSpaceOptions.makeIndexForNodes(fTree, fIndexedNodeList);
		}

		public void createContents(final int numColumns, final Composite parent) {
			fComposite = new Composite(parent, SWT.NONE);
			fComposite.setLayoutData(createGridData(numColumns, GridData.HORIZONTAL_ALIGN_FILL, SWT.DEFAULT));
			fComposite.setLayout(createGridLayout(numColumns, false));

			createLabel(numColumns, fComposite, FormatterMessages.WhiteSpaceTabPage_insert_space);

			fTreeViewer = new ContainerCheckedTreeViewer(fComposite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
			fTreeViewer.setContentProvider(new ITreeContentProvider() {
				@Override
				public Object[] getElements(Object inputElement) {
					return ((Collection<?>) inputElement).toArray();
				}

				@Override
				public Object[] getChildren(Object parentElement) {
					return ((Node) parentElement).getChildren().toArray();
				}

				@Override
				public Object getParent(Object element) {
					return ((Node) element).getParent();
				}

				@Override
				public boolean hasChildren(Object element) {
					return ((Node) element).hasChildren();
				}

				@Override
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				}

				@Override
				public void dispose() {
				}
			});
			fTreeViewer.setLabelProvider(new LabelProvider());
			fTreeViewer.getControl().setLayoutData(
					createGridData(numColumns, GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL, SWT.DEFAULT));
			fDefaultFocusManager.add(fTreeViewer.getControl());
		}

		public void initialize() {
			fTreeViewer.addCheckStateListener(this);
			fTreeViewer.addSelectionChangedListener(this);
			fTreeViewer.addDoubleClickListener(this);
			fTreeViewer.setInput(fTree);
			restoreSelection();
			refreshState();
		}

		public void refreshState() {
			final ArrayList<Node> checked = new ArrayList<>(100);
			for (Iterator<Node> iter = fTree.iterator(); iter.hasNext();) {
				(iter.next()).getCheckedLeafs(checked);
			}
			fTreeViewer.setGrayedElements(new Object[0]);
			fTreeViewer.setCheckedElements(checked.toArray());
			setPreviewText();
			doUpdatePreview();
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			if (selection.isEmpty()) {
				return;
			}
			final Node node = (Node) selection.getFirstElement();
			if (node == fLastSelected) {
				return;
			}
			fDialogSettings.put(PREF_NODE_KEY, node.index);
			fLastSelected = node;
			setPreviewText();
			doUpdatePreview();
		}

		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			final Node node = (Node) event.getElement();
			node.setChecked(event.getChecked());
			doUpdatePreview();
			notifyValuesModified();
		}

		public void restoreSelection() {
			int index;
			try {
				index = fDialogSettings.getInt(PREF_NODE_KEY);
			} catch (NumberFormatException ex) {
				index = -1;
			}
			if (index < 0 || index > fIndexedNodeList.size() - 1) {
				index = 0;
			}
			final Node node = fIndexedNodeList.get(index);
			if (node != null) {
				fTreeViewer.expandToLevel(node, 0);
				fTreeViewer.setSelection(new StructuredSelection(new Node[] { node }));
				fLastSelected = node;
			}
		}

		@Override
		public void doubleClick(DoubleClickEvent event) {
			final ISelection selection = event.getSelection();
			if (selection instanceof IStructuredSelection) {
				final Node node = (Node) ((IStructuredSelection) selection).getFirstElement();
				fTreeViewer.setExpandedState(node, !fTreeViewer.getExpandedState(node));
			}
		}

		public Control getControl() {
			return fComposite;
		}

		private void setPreviewText() {
			String previewText = ""; //$NON-NLS-1$
			if (fLastSelected != null) {
				previewText = "<?php\n"; //$NON-NLS-1$
				List<String> snippets = fLastSelected.getSnippets();
				for (int i = 0; i < snippets.size(); i++) {
					previewText += snippets.get(i);
				}
				previewText += "\n?>"; //$NON-NLS-1$
			}
			fPreview.setPreviewText(previewText);
		}
	}

	private final class PHPElementComponent implements ISelectionChangedListener, ICheckStateListener {

		private final String PREF_INNER_INDEX = FormatterUIPlugin.PLUGIN_ID
				+ "formatter_page.white_space.php_view.inner"; //$NON-NLS-1$
		private final String PREF_OPTION_INDEX = FormatterUIPlugin.PLUGIN_ID
				+ "formatter_page.white_space.php_view.option"; //$NON-NLS-1$

		private final ArrayList<Node> fIndexedNodeList;
		private final ArrayList<Node> fTree;

		private InnerNode fLastSelected;

		private TreeViewer fInnerViewer;
		private CheckboxTableViewer fOptionsViewer;

		private Composite fComposite;

		public PHPElementComponent() {
			fIndexedNodeList = new ArrayList<>();
			fTree = WhiteSpaceOptions.createTreeByPHPElement(fworkingValues);
			WhiteSpaceOptions.makeIndexForNodes(fTree, fIndexedNodeList);
		}

		public void createContents(int numColumns, Composite parent) {

			fComposite = new Composite(parent, SWT.NONE);
			fComposite.setLayoutData(createGridData(numColumns, GridData.HORIZONTAL_ALIGN_FILL, SWT.DEFAULT));
			fComposite.setLayout(createGridLayout(numColumns, false));

			createLabel(numColumns, fComposite, FormatterMessages.WhiteSpaceTabPage_insert_space,
					GridData.HORIZONTAL_ALIGN_BEGINNING);

			final SashForm sashForm = new SashForm(fComposite, SWT.VERTICAL);
			sashForm.setLayoutData(createGridData(numColumns, GridData.FILL_BOTH, SWT.DEFAULT));

			fInnerViewer = new TreeViewer(sashForm, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);

			fInnerViewer.setContentProvider(new ITreeContentProvider() {
				@Override
				public Object[] getElements(Object inputElement) {
					return ((Collection<?>) inputElement).toArray();
				}

				@Override
				public Object[] getChildren(Object parentElement) {
					final List<Node> children = ((Node) parentElement).getChildren();
					final ArrayList<Node> innerChildren = new ArrayList<>();
					for (final Iterator<Node> iter = children.iterator(); iter.hasNext();) {
						final Node o = iter.next();
						if (o instanceof InnerNode) {
							innerChildren.add(o);
						}
					}
					return innerChildren.toArray();
				}

				@Override
				public Object getParent(Object element) {
					if (element instanceof InnerNode) {
						return ((InnerNode) element).getParent();
					}
					return null;
				}

				@Override
				public boolean hasChildren(Object element) {
					final List<Node> children = ((Node) element).getChildren();
					for (final Iterator<Node> iter = children.iterator(); iter.hasNext();) {
						if (iter.next() instanceof InnerNode) {
							return true;
						}
					}
					return false;
				}

				@Override
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				}

				@Override
				public void dispose() {
				}
			});

			fInnerViewer.setLabelProvider(new LabelProvider());

			final GridData innerGd = createGridData(numColumns, GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_VERTICAL,
					SWT.DEFAULT);
			innerGd.heightHint = fPixelConverter.convertHeightInCharsToPixels(3);
			fInnerViewer.getControl().setLayoutData(innerGd);

			fOptionsViewer = CheckboxTableViewer.newCheckList(sashForm, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL);
			fOptionsViewer.setContentProvider(new ArrayContentProvider());
			fOptionsViewer.setLabelProvider(new LabelProvider());

			final GridData optionsGd = createGridData(numColumns,
					GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_VERTICAL, SWT.DEFAULT);
			optionsGd.heightHint = fPixelConverter.convertHeightInCharsToPixels(3);
			fOptionsViewer.getControl().setLayoutData(optionsGd);

			fDefaultFocusManager.add(fInnerViewer.getControl());
			fDefaultFocusManager.add(fOptionsViewer.getControl());

			fInnerViewer.setInput(fTree);
		}

		public void refreshState() {
			if (fLastSelected != null) {
				innerViewerChanged(fLastSelected);
			}
		}

		public void initialize() {
			fInnerViewer.addSelectionChangedListener(this);
			fOptionsViewer.addSelectionChangedListener(this);
			fOptionsViewer.addCheckStateListener(this);
			restoreSelections();
			refreshState();
		}

		private void restoreSelections() {
			Node node;
			final int innerIndex = getValidatedIndex(PREF_INNER_INDEX);
			node = fIndexedNodeList.get(innerIndex);
			if (node instanceof InnerNode) {
				fInnerViewer.expandToLevel(node, 0);
				fInnerViewer.setSelection(new StructuredSelection(new Object[] { node }));
				fLastSelected = (InnerNode) node;
			}

			final int optionIndex = getValidatedIndex(PREF_OPTION_INDEX);
			node = fIndexedNodeList.get(optionIndex);
			if (node instanceof OptionNode) {
				fOptionsViewer.setSelection(new StructuredSelection(new Object[] { node }));
			}

		}

		private int getValidatedIndex(String key) {
			int index;
			try {
				index = fDialogSettings.getInt(key);
			} catch (NumberFormatException ex) {
				index = 0;
			}
			if (index < 0 || index > fIndexedNodeList.size() - 1) {
				index = 0;
			}
			return index;
		}

		public Control getControl() {
			return fComposite;
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			final IStructuredSelection selection = (IStructuredSelection) event.getSelection();

			if (selection.isEmpty() || !(selection.getFirstElement() instanceof Node)) {
				return;
			}

			final Node selected = (Node) selection.getFirstElement();

			if (selected == null || selected == fLastSelected) {
				return;
			}

			if (event.getSource() == fInnerViewer && selected instanceof InnerNode) {
				fLastSelected = (InnerNode) selected;
				fDialogSettings.put(PREF_INNER_INDEX, selected.index);
				innerViewerChanged((InnerNode) selected);
			} else if (event.getSource() == fOptionsViewer && selected instanceof OptionNode) {
				fDialogSettings.put(PREF_OPTION_INDEX, selected.index);
			}
		}

		private void innerViewerChanged(InnerNode selectedNode) {

			final List<Node> children = selectedNode.getChildren();

			final ArrayList<Node> optionsChildren = new ArrayList<>();
			for (final Iterator<Node> iter = children.iterator(); iter.hasNext();) {
				final Node o = iter.next();
				if (o instanceof OptionNode) {
					optionsChildren.add(o);
				}
			}

			fOptionsViewer.setInput(optionsChildren.toArray());

			for (final Iterator<Node> iter = optionsChildren.iterator(); iter.hasNext();) {
				final OptionNode child = (OptionNode) iter.next();
				fOptionsViewer.setChecked(child, child.getChecked());
			}

			String previewText = "<?php\n"; //$NON-NLS-1$
			List<String> snippets = selectedNode.getSnippets();
			for (int i = 0; i < snippets.size(); i++) {
				previewText += snippets.get(i);
			}
			previewText += "\n?>"; //$NON-NLS-1$

			fPreview.setPreviewText(previewText);
			doUpdatePreview();
		}

		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			final OptionNode option = (OptionNode) event.getElement();
			if (option != null) {
				option.setChecked(event.getChecked());
			}
			doUpdatePreview();
			notifyValuesModified();
		}
	}

	/**
	 * This component switches between the two view and is responsible for
	 * delegating the appropriate update requests.
	 */
	private final class SwitchComponent extends SelectionAdapter {
		private final String PREF_VIEW_KEY = FormatterUIPlugin.PLUGIN_ID + "formatter_page.white_space_tab_page.view"; //$NON-NLS-1$
		private final String[] fItems = new String[] { FormatterMessages.WhiteSpaceTabPage_sort_by_php_element,
				FormatterMessages.WhiteSpaceTabPage_sort_by_syntax_element };

		private Combo fSwitchCombo;
		private PageBook fPageBook;
		private final SyntaxComponent fSyntaxComponent;
		private final PHPElementComponent fPHPElementComponent;

		public SwitchComponent() {
			fSyntaxComponent = new SyntaxComponent();
			fPHPElementComponent = new PHPElementComponent();
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			final int index = fSwitchCombo.getSelectionIndex();
			if (index == 0) {
				fDialogSettings.put(PREF_VIEW_KEY, false);
				fPHPElementComponent.refreshState();
				fPageBook.showPage(fPHPElementComponent.getControl());
			} else if (index == 1) {
				fDialogSettings.put(PREF_VIEW_KEY, true);
				fSyntaxComponent.refreshState();
				fPageBook.showPage(fSyntaxComponent.getControl());
			}
		}

		public void createContents(int numColumns, Composite parent) {

			fPageBook = new PageBook(parent, SWT.NONE);
			fPageBook.setLayoutData(createGridData(numColumns, GridData.FILL_BOTH, SWT.DEFAULT));

			fPHPElementComponent.createContents(numColumns, fPageBook);
			fSyntaxComponent.createContents(numColumns, fPageBook);

			fSwitchCombo = new Combo(parent, SWT.READ_ONLY);
			final GridData gd = createGridData(numColumns, GridData.HORIZONTAL_ALIGN_END, SWT.DEFAULT);
			fSwitchCombo.setLayoutData(gd);
			fSwitchCombo.setItems(fItems);
		}

		public void initialize() {
			fSwitchCombo.addSelectionListener(this);
			fPHPElementComponent.initialize();
			fSyntaxComponent.initialize();
			restoreSelection();
		}

		private void restoreSelection() {
			final boolean selectSyntax = fDialogSettings.getBoolean(PREF_VIEW_KEY);
			if (selectSyntax) {
				fSyntaxComponent.refreshState();
				fSwitchCombo.setText(fItems[1]);
				fPageBook.showPage(fSyntaxComponent.getControl());
			} else {
				fPHPElementComponent.refreshState();
				fSwitchCombo.setText(fItems[0]);
				fPageBook.showPage(fPHPElementComponent.getControl());
			}
		}
	}

	private final SwitchComponent fSwitchComponent;
	protected final IDialogSettings fDialogSettings;

	protected CodeFormatterPreview fPreview;

	private Map<String, Object> fworkingValues;

	private boolean isInitialized = false;

	/**
	 * Create a new white space dialog page.
	 * 
	 * @param modifyDialog
	 * @param workingValues
	 */
	public WhiteSpaceTabPage(ModifyDialog modifyDialog, CodeFormatterPreferences codeFormatterPreferences) {
		super(modifyDialog, codeFormatterPreferences);
		fworkingValues = codeFormatterPreferences.getMap();
		fDialogSettings = FormatterUIPlugin.getDefault().getDialogSettings();
		fSwitchComponent = new SwitchComponent();
	}

	@Override
	protected void doCreatePreferences(Composite composite, int numColumns) {
		fSwitchComponent.createContents(numColumns, composite);

		isInitialized = true;
	}

	@Override
	protected void initializePage() {
		fSwitchComponent.initialize();
	}

	@Override
	protected PHPPreview doCreatePHPPreview(Composite parent) {
		fPreview = new CodeFormatterPreview(codeFormatterPreferences, parent);
		return fPreview;
	}

	@Override
	protected void doUpdatePreview() {
		if (fPreview != null) {
			setPreferencesValues(fworkingValues);
			fPreview.update();
		}
	}

	@Override
	protected void updatePreferences() {
		if (isInitialized) {
			setPreferencesValues(fworkingValues);
		}
	}

	public void setPreferencesValues(Map<String, Object> preferences) {
		codeFormatterPreferences.insert_space_after_opening_paren_in_while = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_WHILE);
		codeFormatterPreferences.insert_space_before_opening_paren_in_while = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_WHILE);
		codeFormatterPreferences.insert_space_before_closing_paren_in_while = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_WHILE);

		codeFormatterPreferences.insert_space_before_opening_paren_in_switch = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_SWITCH);
		codeFormatterPreferences.insert_space_after_opening_paren_in_switch = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_SWITCH);
		codeFormatterPreferences.insert_space_before_closing_paren_in_switch = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_SWITCH);
		codeFormatterPreferences.insert_space_before_opening_brace_in_switch = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_SWITCH);
		codeFormatterPreferences.insert_space_after_switch_default = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_DEFAULT);
		codeFormatterPreferences.insert_space_after_switch_case_value = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CASE);
		codeFormatterPreferences.insert_space_before_opening_brace_in_block = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_BLOCK);
		codeFormatterPreferences.insert_space_after_closing_brace_in_block = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_BRACE_IN_BLOCK);
		codeFormatterPreferences.insert_space_before_semicolon = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON);

		codeFormatterPreferences.insert_space_before_assignment = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ASSIGNMENT_OPERATOR);
		codeFormatterPreferences.insert_space_after_assignment = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ASSIGNMENT_OPERATOR);
		codeFormatterPreferences.insert_space_before_binary_operation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_BINARY_OPERATOR);
		codeFormatterPreferences.insert_space_after_binary_operation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_BINARY_OPERATOR);
		codeFormatterPreferences.insert_space_before_postfix_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_POSTFIX_OPERATOR);
		codeFormatterPreferences.insert_space_after_postfix_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_POSTFIX_OPERATOR);
		codeFormatterPreferences.insert_space_before_concatenation_operation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CONCATENATION_OPERATOR);
		codeFormatterPreferences.insert_space_after_concatenation_operation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CONCATENATION_OPERATOR);
		codeFormatterPreferences.insert_space_before_prefix_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_PREFIX_OPERATOR);
		codeFormatterPreferences.insert_space_after_prefix_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_PREFIX_OPERATOR);
		codeFormatterPreferences.insert_space_before_unary_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_UNARY_OPERATOR);
		codeFormatterPreferences.insert_space_after_unary_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_UNARY_OPERATOR);

		codeFormatterPreferences.insert_space_before_cast_type = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CAST);
		codeFormatterPreferences.insert_space_after_cast_type = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CAST);
		codeFormatterPreferences.insert_space_after_cast_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_PAREN_IN_CAST);

		codeFormatterPreferences.insert_space_after_conditional_colon = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLON_IN_CONDITIONAL);
		codeFormatterPreferences.insert_space_before_conditional_colon = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CONDITIONAL);
		codeFormatterPreferences.insert_space_after_conditional_question_mark = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_QUESTION_IN_CONDITIONAL);
		codeFormatterPreferences.insert_space_before_conditional_question_mark = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_QUESTION_IN_CONDITIONAL);

		codeFormatterPreferences.insert_space_before_opening_paren_in_catch = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_CATCH);
		codeFormatterPreferences.insert_space_after_opening_paren_in_catch = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CATCH);
		codeFormatterPreferences.insert_space_before_closing_paren_in_catch = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CATCH);

		codeFormatterPreferences.insert_space_before_comma_in_implements = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_SUPERINTERFACES);
		codeFormatterPreferences.insert_space_after_comma_in_implements = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_SUPERINTERFACES);
		codeFormatterPreferences.insert_space_before_opening_brace_in_class = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_TYPE_DECLARATION);

		codeFormatterPreferences.insert_space_before_opening_paren_in_function = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_INVOCATION);
		codeFormatterPreferences.insert_space_after_opening_paren_in_function = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_INVOCATION);
		codeFormatterPreferences.insert_space_before_comma_in_function = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_INVOCATION_ARGUMENTS);
		codeFormatterPreferences.insert_space_after_comma_in_function = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_INVOCATION_ARGUMENTS);
		codeFormatterPreferences.insert_space_before_closing_paren_in_function = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_INVOCATION);
		codeFormatterPreferences.insert_space_between_empty_paren_in_function = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_INVOCATION);
		codeFormatterPreferences.insert_space_before_arrow_in_method_invocation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_METHOD_INVOCATION);
		codeFormatterPreferences.insert_space_after_arrow_in_method_invocation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_METHOD_INVOCATION);
		codeFormatterPreferences.insert_space_before_coloncolon_in_method_invocation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_METHOD_INVOCATION);
		codeFormatterPreferences.insert_space_after_coloncolon_in_method_invocation = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_METHOD_INVOCATION);

		codeFormatterPreferences.insert_space_before_arrow_in_field_access = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FIELD_ACCESS);
		codeFormatterPreferences.insert_space_after_arrow_in_field_access = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FIELD_ACCESS);
		codeFormatterPreferences.insert_space_before_coloncolon_in_field_access = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_FIELD_ACCESS);
		codeFormatterPreferences.insert_space_after_coloncolon_in_field_access = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_FIELD_ACCESS);

		codeFormatterPreferences.insert_space_before_open_paren_in_for = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOR);
		codeFormatterPreferences.insert_space_after_open_paren_in_for = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOR);
		codeFormatterPreferences.insert_space_before_close_paren_in_for = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOR);
		codeFormatterPreferences.insert_space_before_comma_in_for = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_FOR);
		codeFormatterPreferences.insert_space_after_comma_in_for = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_FOR);
		codeFormatterPreferences.insert_space_before_semicolon_in_for = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON_IN_FOR);
		codeFormatterPreferences.insert_space_after_semicolon_in_for = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_SEMICOLON_IN_FOR);

		codeFormatterPreferences.insert_space_before_open_paren_in_foreach = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOREACH);
		codeFormatterPreferences.insert_space_after_open_paren_in_foreach = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOREACH);
		codeFormatterPreferences.insert_space_before_close_paren_in_foreach = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOREACH);
		codeFormatterPreferences.insert_space_before_arrow_in_foreach = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FOREACH);
		codeFormatterPreferences.insert_space_after_arrow_in_foreach = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FOREACH);

		codeFormatterPreferences.insert_space_before_arrow_in_yield = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_YIELD);
		codeFormatterPreferences.insert_space_after_arrow_in_yield = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_YIELD);

		codeFormatterPreferences.insert_space_before_comma_in_class_variable = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS);
		codeFormatterPreferences.insert_space_after_comma_in_class_variable = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS);
		codeFormatterPreferences.insert_space_before_comma_in_class_constant = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS);
		codeFormatterPreferences.insert_space_after_comma_in_class_constant = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS);

		codeFormatterPreferences.insert_space_before_opening_bracket_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACKET_IN_ARRAY_REFERENCE);
		codeFormatterPreferences.insert_space_after_opening_bracket_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_BRACKET_IN_ARRAY_REFERENCE);
		codeFormatterPreferences.insert_space_before_closing_bracket_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_BRACKET_IN_ARRAY_REFERENCE);
		codeFormatterPreferences.insert_space_between_empty_brackets = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_BRACKETS_IN_ARRAY_TYPE_REFERENCE);

		codeFormatterPreferences.insert_space_before_opening_paren_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_ARRAY_CREATION);
		codeFormatterPreferences.insert_space_after_opening_paren_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_ARRAY_CREATION);
		codeFormatterPreferences.insert_space_before_closing_paren_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_ARRAY_CREATION);
		codeFormatterPreferences.insert_space_before_list_comma_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ARRAY_CREATION);
		codeFormatterPreferences.insert_space_after_list_comma_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ARRAY_CREATION);
		codeFormatterPreferences.insert_space_before_arrow_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_ARRAY_CREATION);
		codeFormatterPreferences.insert_space_after_arrow_in_array = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_ARRAY_CREATION);

		codeFormatterPreferences.insert_space_before_opening_paren_in_list = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_LIST);
		codeFormatterPreferences.insert_space_after_opening_paren_in_list = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_LIST);
		codeFormatterPreferences.insert_space_before_closing_paren_in_list = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_LIST);
		codeFormatterPreferences.insert_space_before_comma_in_list = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_LIST);
		codeFormatterPreferences.insert_space_after_comma_in_list = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_LIST);

		codeFormatterPreferences.insert_space_before_opening_paren_in_function_declaration = getBooleanValue(
				preferences, CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_DECLARATION);
		codeFormatterPreferences.insert_space_after_opening_paren_in_function_declaration = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_DECLARATION);
		codeFormatterPreferences.insert_space_between_empty_paren_in_function_declaration = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_DECLARATION);
		codeFormatterPreferences.insert_space_before_closing_paren_in_function_declaration = getBooleanValue(
				preferences, CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_DECLARATION);
		codeFormatterPreferences.insert_space_before_comma_in_function_declaration = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_DECLARATION_PARAMETERS);
		codeFormatterPreferences.insert_space_after_comma_in_function_declaration = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_DECLARATION_PARAMETERS);
		codeFormatterPreferences.insert_space_before_opening_brace_in_function = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_METHOD_DECLARATION);

		codeFormatterPreferences.insert_space_before_opening_paren_in_if = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_IF);
		codeFormatterPreferences.insert_space_after_opening_paren_in_if = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_IF);
		codeFormatterPreferences.insert_space_before_closing_paren_in_if = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_IF);

		codeFormatterPreferences.insert_space_before_opening_paren_in_declare = getBooleanValue(preferences,
				"codeFormatterPreferences.insert_space_before_opening_paren_in_declare"); //$NON-NLS-1$
		codeFormatterPreferences.insert_space_after_opening_paren_in_declare = getBooleanValue(preferences,
				"codeFormatterPreferences.insert_space_after_opening_paren_in_declare"); //$NON-NLS-1$
		codeFormatterPreferences.insert_space_before_closing_paren_in_declare = getBooleanValue(preferences,
				"codeFormatterPreferences.insert_space_before_closing_paren_in_declare"); //$NON-NLS-1$

		codeFormatterPreferences.insert_space_before_comma_in_static = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_STATIC);
		codeFormatterPreferences.insert_space_after_comma_in_static = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_STATIC);
		codeFormatterPreferences.insert_space_before_comma_in_global = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_GLOBAL);
		codeFormatterPreferences.insert_space_after_comma_in_global = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_GLOBAL);
		codeFormatterPreferences.insert_space_before_comma_in_echo = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ECHO);
		codeFormatterPreferences.insert_space_after_comma_in_echo = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ECHO);

		codeFormatterPreferences.insert_space_after_open_paren_in_parenthesis_expression = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_PARENTHESIZED_EXPRESSION);
		codeFormatterPreferences.insert_space_before_close_paren_in_parenthesis_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_PARENTHESIZED_EXPRESSION);

		codeFormatterPreferences.insert_space_after_reference_symbol_in_referenced_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_REFERENCE_SYMBOL_IN_REFERENCED_EXPRESSION);

		codeFormatterPreferences.insert_space_after_arrow_in_match_arm = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_MATCH_ARM);
		codeFormatterPreferences.insert_space_before_arrow_in_match_arm = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_MATCH_ARM);

		codeFormatterPreferences.insert_space_after_comma_in_match = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MATCH);
		codeFormatterPreferences.insert_space_before_comma_in_match = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MATCH);

		codeFormatterPreferences.insert_space_after_comma_in_match_arm_conditions = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MATCH_ARM_CONDITIONS);
		codeFormatterPreferences.insert_space_before_comma_in_match_arm_conditions = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MATCH_ARM_CONDITIONS);

		codeFormatterPreferences.insert_space_before_opening_paren_in_match = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_MATCH);
		codeFormatterPreferences.insert_space_after_opening_paren_in_match = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_MATCH);

		codeFormatterPreferences.insert_space_before_closing_paren_in_match = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_MATCH);
		codeFormatterPreferences.insert_space_before_opening_brace_in_match = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_MATCH);

		codeFormatterPreferences.insert_space_after_attribute_group_start = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ATTRIBUTE_GROUP_START);
		codeFormatterPreferences.insert_space_before_attribute_group_end = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ATTRIBUTE_GROUP_END);

		codeFormatterPreferences.insert_space_after_comma_in_attribute_group = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ATTRIBUTE_GROUP);
		codeFormatterPreferences.insert_space_before_comma_in_attribute_group = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ATTRIBUTE_GROUP);

		codeFormatterPreferences.insert_space_after_colon_in_enum_type = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLON_IN_ENUM_TYPE);
		codeFormatterPreferences.insert_space_before_colon_in_enum_type = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_ENUM_TYPE);

		codeFormatterPreferences.insert_space_after_equal_in_enum_case = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_EQUAL_IN_ENUM_CASE);
		codeFormatterPreferences.insert_space_before_equal_in_enum_case = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_EQUAL_IN_ENUM_CASE);
	}

	private boolean getBooleanValue(Map<String, Object> preferences, String key) {
		return CodeFormatterPreferences.TRUE.equals(preferences.get(key));
	}
}
