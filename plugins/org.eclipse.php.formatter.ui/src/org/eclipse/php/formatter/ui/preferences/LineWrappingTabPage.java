/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.ui.preferences;

import java.util.*;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.formatter.ui.FormatterUIPlugin;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * @author yaronm
 */
@SuppressWarnings("restriction")
public class LineWrappingTabPage extends ModifyDialogTabPage {

	/**
	 * Represents a line wrapping category. All members are final.
	 */
	private final static class Category {
		private static final String LINE_WRAP_POLICY_KEY = "_line_wrap_policy";
		private static final String INDENT_POLICY_KEY = "_indent_policy";
		// private static final String FORCE_SPLIT_KEY = "_force_split";

		public final String key;
		public final String name;
		public final String previewText;
		public final List<Category> children;
		public int index;

		public Category(String _key, String _previewText, String _name) {
			this.key = _key;
			this.name = _name;
			this.previewText = _previewText != null ? createPreviewHeader(_name)
					+ _previewText
					: null;
			children = new ArrayList<Category>();
		}

		/**
		 * @param _name
		 *            Category name
		 */
		public Category(String _name) {
			this(null, null, _name);
		}

		public String toString() {
			return name;
		}

		public String getLineWrappingPolicyKey() {
			return key + LINE_WRAP_POLICY_KEY;
		}

		public String getIndentPolicyKey() {
			return key + INDENT_POLICY_KEY;
		}

		public String getForceSplitKey() {
			return key + "_force_split";
		}
	}

	private final static String PREF_CATEGORY_INDEX = FormatterUIPlugin.PLUGIN_ID
			+ "formatter_page.line_wrapping_tab_page.last_category_index"; //$NON-NLS-1$ 

	private final class CategoryListener implements ISelectionChangedListener,
			IDoubleClickListener {

		private final List<Category> fCategoriesList;

		private int fIndex = 0;

		public CategoryListener(List<Category> categoriesTree) {
			fCategoriesList = new ArrayList<Category>();
			flatten(fCategoriesList, categoriesTree);
		}

		private void flatten(List<Category> categoriesList,
				List<Category> categoriesTree) {
			for (final Iterator<Category> iter = categoriesTree.iterator(); iter
					.hasNext();) {
				final Category category = iter.next();
				category.index = fIndex++;
				categoriesList.add(category);
				flatten(categoriesList, category.children);
			}
		}

		public void selectionChanged(SelectionChangedEvent event) {
			if (event != null)
				fSelection = (IStructuredSelection) event.getSelection();

			if (fSelection.size() == 0) {
				disableAll();
				return;
			}

			if (!fOptionsGroup.isEnabled())
				enableDefaultComponents(true);

			fSelectionState.refreshState(fSelection);

			final Category category = (Category) fSelection.getFirstElement();
			fDialogSettings.put(PREF_CATEGORY_INDEX, category.index);

			fOptionsGroup.setText(getGroupLabel(category));
		}

		private String getGroupLabel(Category category) {
			if (fSelection.size() == 1) {
				if (fSelectionState.getElements().size() == 1)
					return Messages.format(
							FormatterMessages.LineWrappingTabPage_group,
							category.name.toLowerCase());
				return Messages.format(
						FormatterMessages.LineWrappingTabPage_multi_group,
						new String[] {
								category.name.toLowerCase(),
								Integer.toString(fSelectionState.getElements()
										.size()) });
			}
			return Messages.format(
					FormatterMessages.LineWrappingTabPage_multiple_selections,
					new String[] { Integer.toString(fSelectionState
							.getElements().size()) });
		}

		private void disableAll() {
			enableDefaultComponents(false);
			fIndentStyleCombo.setEnabled(false);
			fForceSplit.setEnabled(false);
		}

		private void enableDefaultComponents(boolean enabled) {
			fOptionsGroup.setEnabled(enabled);
			fWrappingStyleCombo.setEnabled(enabled);
			fWrappingStylePolicy.setEnabled(enabled);
		}

		public void restoreSelection() {
			int index;
			try {
				index = fDialogSettings.getInt(PREF_CATEGORY_INDEX);
			} catch (NumberFormatException ex) {
				index = -1;
			}
			if (index < 0 || index > fCategoriesList.size() - 1) {
				index = 1; // In order to select a category with preview
							// initially
			}
			final Category category = fCategoriesList.get(index);
			fCategoriesViewer.setSelection(new StructuredSelection(
					new Category[] { category }));
		}

		public void doubleClick(DoubleClickEvent event) {
			final ISelection selection = event.getSelection();
			if (selection instanceof IStructuredSelection) {
				final Category node = (Category) ((IStructuredSelection) selection)
						.getFirstElement();
				fCategoriesViewer.setExpandedState(node,
						!fCategoriesViewer.getExpandedState(node));
			}
		}
	}

	private class SelectionState {
		private List<Category> fElements = new ArrayList<Category>();

		@SuppressWarnings("unchecked")
		public void refreshState(IStructuredSelection selection) {
			fElements.clear();
			Iterator<Category> iterator = selection.iterator();
			evaluateElements(iterator);
			setPreviewText(getPreviewText());
			refreshControls(fElements.get(0));
			updateControlEnablement();
		}

		public List<Category> getElements() {
			return fElements;
		}

		private void evaluateElements(Iterator<Category> iterator) {
			Category category;
			while (iterator.hasNext()) {
				category = iterator.next();
				if (category.children.size() == 0) {
					if (!fElements.contains(category))
						fElements.add(category);
				} else {
					evaluateElements(category.children.iterator());
				}
			}
		}

		private String getPreviewText() {
			Iterator<Category> iterator = fElements.iterator();
			String previewText = "<?php\n"; //$NON-NLS-1$
			while (iterator.hasNext()) {
				Category category = iterator.next();
				previewText = previewText + category.previewText + "\n\n"; //$NON-NLS-1$
			}
			return previewText + "?>";
		}

		private void refreshControls(Category category) {
			updateCombos(category);
			updateButton(category);
			doUpdatePreview();
		}

		private void updateButton(Category category) {
			boolean isSelected = Boolean.valueOf(codeFormatterPreferences
					.getMap().get(category.getForceSplitKey()).toString());
			fForceSplit.setSelection(isSelected);
		}

		private void updateCombos(Category category) {
			fWrappingStyleCombo.select(Integer
					.parseInt((String) codeFormatterPreferences.getMap().get(
							category.getLineWrappingPolicyKey())));
			fIndentStyleCombo.select(Integer
					.parseInt((String) codeFormatterPreferences.getMap().get(
							category.getIndentPolicyKey())));
		}
	}

	protected static final String[] INDENT_NAMES = {
			FormatterMessages.LineWrappingTabPage_indentation_default,
			FormatterMessages.LineWrappingTabPage_indentation_on_column,
			FormatterMessages.LineWrappingTabPage_indentation_by_one };

	protected static final String[] WRAPPING_NAMES = {
			FormatterMessages.LineWrappingTabPage_splitting_do_not_split,
			FormatterMessages.LineWrappingTabPage_splitting_wrap_when_necessary, // COMPACT_SPLIT
			FormatterMessages.LineWrappingTabPage_splitting_always_wrap_first_others_when_necessary, // COMPACT_FIRST_BREAK_SPLIT
			FormatterMessages.LineWrappingTabPage_splitting_wrap_always, // ONE_PER_LINE_SPLIT
			FormatterMessages.LineWrappingTabPage_splitting_wrap_always_indent_all_but_first, // NEXT_SHIFTED_SPLIT
			FormatterMessages.LineWrappingTabPage_splitting_wrap_always_except_first_only_if_necessary };

	private final Category fTypeDeclarationSuperinterfacesCategory = new Category(
			CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_KEY,
			"class Example implements I1, I2, I3 {}", //$NON-NLS-1$
			FormatterMessages.LineWrappingTabPage_implements_clause);

	private final Category fMethodDeclarationsParametersCategory = new Category(
			CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_KEY,
			"class Example {function foo( $a1, $a2, $a3, $a4, $a5, $a6) {}}", //$NON-NLS-1$
			FormatterMessages.LineWrappingTabPage_parameters);

	private final Category fMessageSendArgumentsCategory = new Category(
			CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_KEY,
			"class Example {function foo() { Other::bar( 100, 200, 300, 400, 500, 600, 700, 800, 900 );}}", //$NON-NLS-1$
			FormatterMessages.LineWrappingTabPage_arguments);

	private final Category fAllocationExpressionArgumentsCategory = new Category(
			CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_KEY,
			"class Example {function foo() {return new SomeClass(100, 200, 300, 400, 500, 600, 700, 800, 900 );}}", //$NON-NLS-1$
			FormatterMessages.LineWrappingTabPage_object_allocation);

	private final Category fArrayInitializerExpressionsCategory = new Category(
			CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_KEY,
			"class Example {var $a = array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);}", //$NON-NLS-1$
			FormatterMessages.LineWrappingTabPage_array_init);

	private final Category fBinaryExpressionCategory = new Category(
			CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_KEY,
			"class Example extends AnotherClass {" + //$NON-NLS-1$
					"function foo() {" + //$NON-NLS-1$
					"  $sum= 100 + 200 + 300 + 400 + 500 + 600 + 700 + 800;" + //$NON-NLS-1$
					"  $product= 1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10;" + //$NON-NLS-1$
					"  $val = true && false && true && false && true;" + //$NON-NLS-1$
					"  return product / sum;}}", //$NON-NLS-1$
			FormatterMessages.LineWrappingTabPage_binary_exprs);

	/**
	 * The default preview line width.
	 */
	private static int DEFAULT_PREVIEW_WINDOW_LINE_WIDTH = 40;

	/**
	 * The key to save the user's preview window width in the dialog settings.
	 */
	private static final String PREF_PREVIEW_LINE_WIDTH = FormatterUIPlugin.PLUGIN_ID
			+ "formatter_page.line_wrapping_tab_page.preview_line_width"; //$NON-NLS-1$

	/**
	 * The dialog settings.
	 */
	protected final IDialogSettings fDialogSettings;

	protected TreeViewer fCategoriesViewer;
	protected Label fWrappingStylePolicy;
	protected Combo fWrappingStyleCombo;
	protected Label fIndentStylePolicy;
	protected Combo fIndentStyleCombo;
	protected Button fForceSplit;

	protected CodeFormatterPreview fPreview;

	private NumberPreference fMaxLineWidthPref;
	private NumberPreference fDefaultIndentWrapLines;
	// private NumberPreference fPreviewLineWidth;

	protected Group fOptionsGroup;

	/**
	 * A collection containing the categories tree. This is used as model for
	 * the tree viewer.
	 * 
	 * @see TreeViewer
	 */
	private final List<Category> fCategories;

	/**
	 * The category listener which makes the selection persistent.
	 */
	protected final CategoryListener fCategoryListener;

	/**
	 * The current selection of elements.
	 */
	protected IStructuredSelection fSelection;

	/**
	 * An object containing the state for the UI.
	 */
	SelectionState fSelectionState;

	/**
	 * A special options store wherein the preview line width is kept.
	 */
	protected final Map<String, String> fPreviewPreferences;

	/**
	 * The key for the preview line width.
	 */
	private final String LINE_SPLIT = CodeFormatterConstants.FORMATTER_LINE_SPLIT;

	private boolean isInitialized;

	/**
	 * Create a new line wrapping tab page.
	 * 
	 * @param modifyDialog
	 * @param workingValues
	 */
	public LineWrappingTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences codeFormatterPreferences) {
		super(modifyDialog, codeFormatterPreferences);

		fDialogSettings = FormatterUIPlugin.getDefault().getDialogSettings();

		final String previewLineWidth = fDialogSettings
				.get(PREF_PREVIEW_LINE_WIDTH);
		fPreviewPreferences = new HashMap<String, String>();
		fPreviewPreferences.put(
				LINE_SPLIT,
				previewLineWidth != null ? previewLineWidth : Integer
						.toString(DEFAULT_PREVIEW_WINDOW_LINE_WIDTH));

		fCategories = createCategories();
		fCategoryListener = new CategoryListener(fCategories);
	}

	/**
	 * @return Create the categories tree.
	 */
	protected List<Category> createCategories() {

		final Category classDeclarations = new Category(
				FormatterMessages.LineWrappingTabPage_class_decls);
		classDeclarations.children.add(fTypeDeclarationSuperinterfacesCategory);

		final Category methodDeclarations = new Category(null, null,
				FormatterMessages.LineWrappingTabPage_method_decls);
		methodDeclarations.children.add(fMethodDeclarationsParametersCategory);

		final Category functionCalls = new Category(
				FormatterMessages.LineWrappingTabPage_function_calls);
		functionCalls.children.add(fMessageSendArgumentsCategory);
		functionCalls.children.add(fAllocationExpressionArgumentsCategory);
		final Category expressions = new Category(
				FormatterMessages.LineWrappingTabPage_expressions);
		expressions.children.add(fBinaryExpressionCategory);
		expressions.children.add(fArrayInitializerExpressionsCategory);

		final List<Category> root = new ArrayList<Category>();
		root.add(classDeclarations);
		root.add(methodDeclarations);
		root.add(functionCalls);
		root.add(expressions);

		return root;
	}

	protected void doCreatePreferences(Composite composite, int numColumns) {

		final Group lineWidthGroup = createGroup(numColumns, composite,
				FormatterMessages.LineWrappingTabPage_width_indent);

		fMaxLineWidthPref = createNumberPref(
				lineWidthGroup,
				numColumns,
				FormatterMessages.LineWrappingTabPage_width_indent_option_max_line_width,
				0, 9999);
		fMaxLineWidthPref
				.setValue(codeFormatterPreferences.line_wrap_line_split);

		fDefaultIndentWrapLines = createNumberPref(
				lineWidthGroup,
				numColumns,
				FormatterMessages.LineWrappingTabPage_width_indent_option_default_indent_wrapped,
				0, 9999);
		fDefaultIndentWrapLines
				.setValue(codeFormatterPreferences.line_wrap_wrapped_lines_indentation);

		fCategoriesViewer = new TreeViewer(composite /* categoryGroup */,
				SWT.MULTI | SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
		fCategoriesViewer.setContentProvider(new ITreeContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((Collection<?>) inputElement).toArray();
			}

			public Object[] getChildren(Object parentElement) {
				return ((Category) parentElement).children.toArray();
			}

			public Object getParent(Object element) {
				return null;
			}

			public boolean hasChildren(Object element) {
				return !((Category) element).children.isEmpty();
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			public void dispose() {
			}
		});
		fCategoriesViewer.setLabelProvider(new LabelProvider());
		fCategoriesViewer.setInput(fCategories);

		fCategoriesViewer.setExpandedElements(fCategories.toArray());

		final GridData gd = createGridData(numColumns, GridData.FILL_BOTH,
				SWT.DEFAULT);
		fCategoriesViewer.getControl().setLayoutData(gd);

		fOptionsGroup = createGroup(numColumns, composite, ""); //$NON-NLS-1$

		// label "Select split style:"
		fWrappingStylePolicy = createLabel(
				numColumns,
				fOptionsGroup,
				FormatterMessages.LineWrappingTabPage_wrapping_policy_label_text);

		// combo SplitStyleCombo
		fWrappingStyleCombo = new Combo(fOptionsGroup, SWT.SINGLE
				| SWT.READ_ONLY);
		fWrappingStyleCombo.setItems(WRAPPING_NAMES);
		fWrappingStyleCombo.setLayoutData(createGridData(numColumns,
				GridData.HORIZONTAL_ALIGN_FILL, 0));

		// label "Select indentation style:"
		fIndentStylePolicy = createLabel(
				numColumns,
				fOptionsGroup,
				FormatterMessages.LineWrappingTabPage_indentation_policy_label_text);

		// combo SplitStyleCombo
		fIndentStyleCombo = new Combo(fOptionsGroup, SWT.SINGLE | SWT.READ_ONLY);
		fIndentStyleCombo.setItems(INDENT_NAMES);
		fIndentStyleCombo.setLayoutData(createGridData(numColumns,
				GridData.HORIZONTAL_ALIGN_FILL, 0));

		// button "Force split"
		fForceSplit = new Button(fOptionsGroup, SWT.CHECK);
		fForceSplit.setLayoutData(createGridData(numColumns,
				GridData.HORIZONTAL_ALIGN_FILL, 0));
		fForceSplit
				.setText(FormatterMessages.LineWrappingTabPage_force_split_checkbox_text);

		// selection state object
		fSelectionState = new SelectionState();
		isInitialized = true;
	}

	protected void setPreviewText(String text) {
		fPreview.setPreviewText(text);
	}

	protected void updateControlEnablement() {
		boolean isLineWrapEnabled = fWrappingStyleCombo.getSelectionIndex() != 0;
		fIndentStyleCombo.setEnabled(isLineWrapEnabled);
		fForceSplit.setEnabled(isLineWrapEnabled);
	}

	protected void updatePreferences() {
		if (isInitialized) {
			codeFormatterPreferences.line_wrap_line_split = fMaxLineWidthPref
					.getValue();
			codeFormatterPreferences.line_wrap_wrapped_lines_indentation = fDefaultIndentWrapLines
					.getValue();
		}
	}

	protected PhpPreview doCreatePhpPreview(Composite parent) {
		fPreview = new CodeFormatterPreview(codeFormatterPreferences, parent);
		return fPreview;
	}

	protected void doUpdatePreview() {

	}

	protected void initializePage() {
		fCategoriesViewer.addSelectionChangedListener(fCategoryListener);
		fCategoriesViewer.addDoubleClickListener(fCategoryListener);

		fForceSplit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean isSelected = fForceSplit.getSelection();
				Iterator<Category> iter = fSelectionState.fElements.iterator();
				Map<String, Object> map = codeFormatterPreferences.getMap();
				while (iter.hasNext()) {
					map.put(iter.next().getForceSplitKey(),
							Boolean.toString(isSelected));
				}
				codeFormatterPreferences.setPreferencesValues(map);
				notifyValuesModified();
			}
		});
		fIndentStyleCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int comboSelection = fIndentStyleCombo.getSelectionIndex();
				Iterator<Category> iter = fSelectionState.fElements.iterator();
				Map<String, Object> map = codeFormatterPreferences.getMap();
				while (iter.hasNext()) {
					map.put(iter.next().getIndentPolicyKey(),
							Integer.toString(comboSelection));
				}
				codeFormatterPreferences.setPreferencesValues(map);
				notifyValuesModified();
			}
		});
		fWrappingStyleCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateControlEnablement();
				int comboSelection = fWrappingStyleCombo.getSelectionIndex();
				Iterator<Category> iter = fSelectionState.fElements.iterator();
				Map<String, Object> map = codeFormatterPreferences.getMap();
				while (iter.hasNext()) {
					map.put(iter.next().getLineWrappingPolicyKey(),
							Integer.toString(comboSelection));
				}
				codeFormatterPreferences.setPreferencesValues(map);
				notifyValuesModified();
			}
		});

		fCategoryListener.restoreSelection();

		fDefaultFocusManager.add(fCategoriesViewer.getControl());
		fDefaultFocusManager.add(fWrappingStyleCombo);
		fDefaultFocusManager.add(fIndentStyleCombo);
		fDefaultFocusManager.add(fForceSplit);
	}

	protected void notifyValuesModified() {
		super.notifyValuesModified();
		if (fPreview != null) {
			fPreview.update();
		}
	}
}
