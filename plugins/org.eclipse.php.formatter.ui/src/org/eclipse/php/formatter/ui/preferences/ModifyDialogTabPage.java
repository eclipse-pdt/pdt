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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.formatter.ui.FormatterUIPlugin;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public abstract class ModifyDialogTabPage {

	/**
	 * This is the default listener for any of the Preference classes. It is
	 * added by the respective factory methods and updates the page's preview on
	 * each change.
	 */
	protected final Observer fUpdater = new Observer() {
		public void update(Observable o, Object arg) {
			updatePreferences();
			doUpdatePreview();
			notifyValuesModified();
		}
	};

	/**
	 * The base class of all Preference classes. A preference class provides a
	 * wrapper around one or more SWT widgets and handles the input of values
	 * for some key. On each change, the new value is written to the map and the
	 * listeners are notified.
	 */
	protected abstract class Preference extends Observable {
		/**
		 * Returns the main control of a preference, which is mainly used to
		 * manage the focus. This may be <code>null</code> if the preference
		 * doesn't have a control which is able to have the focus.
		 * 
		 * @return The main control
		 */
		public abstract Control getControl();

		protected void setChanged() {
			super.setChanged();
			notifyObservers();
		}
	}

	/**
	 * Wrapper around a checkbox and a label.
	 */
	protected final class CheckboxPreference extends Preference {
		private final Button fCheckbox;

		public CheckboxPreference(Composite composite, int numColumns,
				String text) {
			if (text == null)
				throw new IllegalArgumentException(
						FormatterMessages.ModifyDialogTabPage_error_msg_values_text_unassigned);

			fCheckbox = new Button(composite, SWT.CHECK);
			fCheckbox.setText(text);
			fCheckbox.setLayoutData(createGridData(numColumns,
					GridData.FILL_HORIZONTAL, SWT.DEFAULT));
			fCheckbox.setFont(composite.getFont());

			fCheckbox.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					setChanged();
				}
			});
		}

		public void setIsChecked(boolean isChecked) {
			fCheckbox.setSelection(isChecked);
		}

		public boolean isChecked() {
			return fCheckbox.getSelection();
		}

		public void setEnabled(boolean isEnabled) {
			fCheckbox.setEnabled(isEnabled);
		}

		public boolean isEnabled() {
			return fCheckbox.isEnabled();
		}

		public Control getControl() {
			return fCheckbox;
		}
	}

	/**
	 * Wrapper around a Combo box.
	 */
	protected final class ComboPreference extends Preference {
		private final String[] fItems;
		private final Combo fCombo;

		public ComboPreference(Composite composite, int numColumns,
				String text, String[] items) {
			if (items == null || text == null)
				throw new IllegalArgumentException(
						FormatterMessages.ModifyDialogTabPage_error_msg_values_items_text_unassigned);

			fItems = items;
			createLabel(numColumns - 1, composite, text);
			fCombo = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY);
			fCombo.setFont(composite.getFont());
			fCombo.setItems(items);
			fCombo.setLayoutData(createGridData(1,
					GridData.HORIZONTAL_ALIGN_FILL,
					fCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x));

			fCombo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					setChanged();
				}
			});
		}

		public void setSelectedItem(String item) {
			int index = 0;
			boolean found = false;
			for (; index < fItems.length; index++) {
				if (fItems[index].equals(item)) {
					found = true;
					break;
				}
			}
			if (found) {
				fCombo.select(index);
			}
		}

		public String getSelectedItem() {
			int index = fCombo.getSelectionIndex();
			return fItems[index];
		}

		public int getSelectionIndex() {
			return fCombo.getSelectionIndex();
		}

		public Control getControl() {
			return fCombo;
		}
	}

	/**
	 * Wrapper around a textfied which requests an integer input of a given
	 * range.
	 */
	protected final class NumberPreference extends Preference {

		private final int fMinValue, fMaxValue;
		private final Text fNumberText;

		protected int fSelected;
		protected int fOldSelected;

		public NumberPreference(Composite composite, int numColumns,
				String text, int minValue, int maxValue) {

			createLabel(numColumns - 1, composite, text,
					GridData.FILL_HORIZONTAL);

			fNumberText = new Text(composite, SWT.SINGLE | SWT.BORDER
					| SWT.RIGHT);
			fNumberText.setFont(composite.getFont());
			final int length = Integer.toString(maxValue).length() + 3;
			fNumberText.setLayoutData(createGridData(1,
					GridData.HORIZONTAL_ALIGN_END,
					fPixelConverter.convertWidthInCharsToPixels(length)));

			fMinValue = minValue;
			fMaxValue = maxValue;

			fNumberText.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					NumberPreference.this.focusGained();
				}

				public void focusLost(FocusEvent e) {
					NumberPreference.this.focusLost();
				}
			});

			fNumberText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					fieldModified();
				}
			});
		}

		private IStatus createErrorStatus() {
			return new Status(
					IStatus.ERROR,
					FormatterUIPlugin.PLUGIN_ID,
					0,
					Messages.format(
							FormatterMessages.ModifyDialogTabPage_NumberPreference_error_invalid_value,
							new String[] { Integer.toString(fMinValue),
									Integer.toString(fMaxValue) }), null);
		}

		protected void focusGained() {
			fOldSelected = fSelected;
			fNumberText.setSelection(0, fNumberText.getCharCount());
		}

		protected void focusLost() {
			updateStatus(null);
			final String input = fNumberText.getText();
			if (!validInput(input))
				fSelected = fOldSelected;
			else
				fSelected = Integer.parseInt(input);
			if (fSelected != fOldSelected) {
				setChanged();
				fNumberText.setText(Integer.toString(fSelected));
			}
		}

		protected void fieldModified() {
			final String trimInput = fNumberText.getText().trim();
			final boolean valid = validInput(trimInput);

			updateStatus(valid ? null : createErrorStatus());

			if (valid) {
				final int number = Integer.parseInt(trimInput);
				if (fSelected != number) {
					fSelected = number;
					setChanged();
				}
			}
		}

		private boolean validInput(String trimInput) {
			boolean isValid = true;

			try {
				int number = Integer.parseInt(trimInput);
				if (number < fMinValue || number > fMaxValue) {
					isValid = false;
				}
			} catch (NumberFormatException x) {
				isValid = false;
			}

			return isValid;
		}

		public void setValue(int value) {
			fNumberText.setText(String.valueOf(value));
		}

		public int getValue() {
			return fSelected;
		}

		public Control getControl() {
			return fNumberText;
		}
	}

	/**
	 * Wrapper around a textfied which requests an integer input of a given
	 * range.
	 */
	protected final class StringPreference extends Preference {

		/**
		 * Validates the input.
		 * <p>
		 * The default implementation declares all non-<code>null</code> values
		 * as valid.
		 * </p>
		 * 
		 * @since 3.6
		 */
		protected class Validator {
			boolean isValid(String input) {
				return input != null;
			}
		}

		private final Label fLabel;

		// private final Text fText;

		private IInputValidator fInputValidator;

		private final Text fNumberText;

		protected String fSelected;
		protected String fOldSelected;

		public StringPreference(Composite composite, int numColumns,
				String text, IInputValidator inputValidator) {

			fInputValidator = inputValidator;

			fLabel = new Label(composite, SWT.NONE);
			fLabel.setFont(composite.getFont());
			fLabel.setText(text);

			fLabel.setLayoutData(createGridData(numColumns - 1,
					GridData.HORIZONTAL_ALIGN_BEGINNING, SWT.DEFAULT));

			fNumberText = new Text(composite, SWT.SINGLE | SWT.BORDER);
			fNumberText.setFont(composite.getFont());

			final int length = 30;
			fNumberText.setLayoutData(createGridData(1,
					GridData.HORIZONTAL_ALIGN_BEGINNING,
					fPixelConverter.convertWidthInCharsToPixels(length)));

			// createLabel(numColumns - 1, composite, text,
			// GridData.FILL_HORIZONTAL);
			//
			// fNumberText = new Text(composite, SWT.SINGLE | SWT.BORDER
			// | SWT.RIGHT);
			// fNumberText.setFont(composite.getFont());
			// // final int length = Integer.toString(maxValue).length() + 3;
			// fNumberText.setLayoutData(createGridData(1,
			// GridData.HORIZONTAL_ALIGN_END,
			// fPixelConverter.convertWidthInCharsToPixels(length)));

			fNumberText.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					StringPreference.this.focusGained();
				}

				public void focusLost(FocusEvent e) {
					StringPreference.this.focusLost();
				}
			});

			fNumberText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					fieldModified();
				}
			});
		}

		public void setEnabled(boolean isEnabled) {
			fNumberText.setEnabled(isEnabled);
		}

		public boolean isEnabled() {
			return fNumberText.isEnabled();
		}

		protected void focusGained() {
			fOldSelected = fSelected;
			fNumberText.setSelection(0, fNumberText.getCharCount());
		}

		// protected void focusLost() {
		// updateStatus(null);
		// final String input = fNumberText.getText();
		// if (!validInput(input))
		// fSelected = fOldSelected;
		// else
		// fSelected = input;
		// if (fSelected != fOldSelected) {
		// setChanged();
		// fNumberText.setText(fSelected);
		// }
		// }

		// protected void fieldModified() {
		// final String trimInput = fNumberText.getText().trim();
		// final boolean valid = validInput(trimInput);
		//
		// updateStatus(valid ? null : createErrorStatus());
		//
		// if (valid) {
		// final int number = Integer.parseInt(trimInput);
		// if (fSelected != number) {
		// fSelected = number;
		// setChanged();
		// }
		// }
		// }
		// private boolean validInput(String trimInput) {
		// boolean isValid = true;
		//
		// try {
		// int number = Integer.parseInt(trimInput);
		// if (number < fMinValue || number > fMaxValue) {
		// isValid = false;
		// }
		// } catch (NumberFormatException x) {
		// isValid = false;
		// }
		//
		// return isValid;
		// }

		public void setValue(String value) {
			fNumberText.setText(value);
		}

		private IStatus createErrorStatus(String errorText) {
			return new Status(IStatus.ERROR, FormatterUIPlugin.PLUGIN_ID, 0,
					errorText, null);

		}

		protected void focusLost() {
			updateStatus(null);
			final String input = fNumberText.getText();
			if (fInputValidator != null
					&& fInputValidator.isValid(input) != null)
				fSelected = fOldSelected;
			else
				fSelected = input;
			if (fSelected != fOldSelected) {
				setChanged();
				fNumberText.setText(fSelected);
			}
		}

		protected void fieldModified() {
			final String text = fNumberText.getText();
			final String errorText = fInputValidator != null ? fInputValidator
					.isValid(text) : null;
			if (errorText == null) {
				updateStatus(null);
				if (fSelected != text) {
					fSelected = text;
					setChanged();
				}
			} else
				updateStatus(createErrorStatus(errorText));
		}

		// private void saveSelected() {
		// getPreferences().put(getKey(), fSelected);
		// setChanged();
		// notifyObservers();
		// }

		public String getValue() {
			return fSelected;
		}

		public Control getControl() {
			return fNumberText;
		}
	}

	/**
	 * This class provides the default way to preserve and re-establish the
	 * focus over multiple modify sessions. Each ModifyDialogTabPage has its own
	 * instance, and it should add all relevant controls upon creation, always
	 * in the same sequence. This established a mapping of controls to indexes,
	 * which allows to restore the focus in a later session. The index is saved
	 * in the dialog settings, and there is only one common preference for all
	 * tab pages. It is always the currently active tab page which stores its
	 * focus index.
	 */
	protected final static class DefaultFocusManager extends FocusAdapter {

		private final static String PREF_LAST_FOCUS_INDEX = FormatterUIPlugin.PLUGIN_ID
				+ "formatter_page.modify_dialog_tab_page.last_focus_index"; //$NON-NLS-1$ 

		private final IDialogSettings fDialogSettings;

		private final Map fItemMap;
		private final List fItemList;

		private int fIndex;

		public DefaultFocusManager() {
			fDialogSettings = FormatterUIPlugin.getDefault()
					.getDialogSettings();
			fItemMap = new HashMap();
			fItemList = new ArrayList();
			fIndex = 0;
		}

		public void focusGained(FocusEvent e) {
			fDialogSettings.put(PREF_LAST_FOCUS_INDEX,
					((Integer) fItemMap.get(e.widget)).intValue());
		}

		public void add(Control control) {
			control.addFocusListener(this);
			fItemList.add(fIndex, control);
			fItemMap.put(control, new Integer(fIndex++));
		}

		public void add(Preference preference) {
			final Control control = preference.getControl();
			if (control != null)
				add(control);
		}

		public boolean isUsed() {
			return fIndex != 0;
		}

		public void restoreFocus() {
			int index = 0;
			try {
				index = fDialogSettings.getInt(PREF_LAST_FOCUS_INDEX);
				// make sure the value is within the range
				if ((index >= 0) && (index <= fItemList.size() - 1)) {
					((Control) fItemList.get(index)).setFocus();
				}
			} catch (NumberFormatException ex) {
				// this is the first time
			}
		}

		public void resetFocus() {
			fDialogSettings.put(PREF_LAST_FOCUS_INDEX, -1);
		}
	}

	/**
	 * The default focus manager. This widget knows all widgets which can have
	 * the focus and listens for focusGained events, on which it stores the
	 * index of the current focus holder. When the dialog is restarted,
	 * <code>restoreFocus()</code> sets the focus to the last control which had
	 * it.
	 * 
	 * The standard Preference object are managed by this focus manager if they
	 * are created using the respective factory methods. Other SWT widgets can
	 * be added in subclasses when they are created.
	 */
	protected final DefaultFocusManager fDefaultFocusManager;

	/**
	 * Constant array for boolean selection
	 */
	protected static String[] FALSE_TRUE = { CodeFormatterConstants.FALSE,
			CodeFormatterConstants.TRUE };

	/**
	 * A pixel converter for layout calculations
	 */
	protected PixelConverter fPixelConverter;

	/**
	 * The map where the current settings are stored.
	 */
	protected final CodeFormatterPreferences codeFormatterPreferences;

	/**
	 * The modify dialog where we can display status messages.
	 */
	private final ModifyDialog fModifyDialog;

	/*
	 * Create a new <code>ModifyDialogTabPage</code>
	 */
	public ModifyDialogTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences preferences) {
		this.codeFormatterPreferences = preferences;
		fModifyDialog = modifyDialog;
		fDefaultFocusManager = new DefaultFocusManager();
	}

	/**
	 * Create the contents of this tab page.
	 * <p>
	 * Subclasses should implement <code>doCreatePreferences</code> and
	 * <code>doCreatePreview</code> may also be overridden as necessary.
	 * </p>
	 * 
	 * @param parent
	 *            The parent composite
	 * @return Created content control
	 */
	public Composite createContents(Composite parent) {
		final int numColumns = 4;

		if (fPixelConverter == null) {
			fPixelConverter = new PixelConverter(parent);
		}

		final SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.setFont(parent.getFont());

		Composite scrollContainer = new Composite(sashForm, SWT.NONE);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		scrollContainer.setLayoutData(gridData);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		scrollContainer.setLayout(layout);

		ScrolledComposite scroll = new ScrolledComposite(scrollContainer,
				SWT.V_SCROLL | SWT.H_SCROLL);
		scroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);

		final Composite settingsContainer = new Composite(scroll, SWT.NONE);
		settingsContainer.setFont(sashForm.getFont());

		scroll.setContent(settingsContainer);

		settingsContainer.setLayout(new PageLayout(scroll, 400, 400));
		settingsContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));

		Composite settingsPane = new Composite(settingsContainer, SWT.NONE);
		settingsPane
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		layout = new GridLayout(numColumns, false);
		layout.verticalSpacing = (int) (1.5 * fPixelConverter
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING));
		layout.horizontalSpacing = fPixelConverter
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.marginHeight = fPixelConverter
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = fPixelConverter
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		settingsPane.setLayout(layout);
		doCreatePreferences(settingsPane, numColumns);

		settingsContainer.setSize(settingsContainer.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));

		scroll.addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent e) {
			}

			public void controlResized(ControlEvent e) {
				settingsContainer.setSize(settingsContainer.computeSize(
						SWT.DEFAULT, SWT.DEFAULT));
			}
		});

		Label sashHandle = new Label(scrollContainer, SWT.SEPARATOR
				| SWT.VERTICAL);
		gridData = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		sashHandle.setLayoutData(gridData);

		final Composite previewPane = new Composite(sashForm, SWT.NONE);
		previewPane.setLayout(createGridLayout(numColumns, true));
		previewPane.setFont(sashForm.getFont());
		doCreatePreviewPane(previewPane, numColumns);

		initializePage();

		sashForm.setWeights(new int[] { 3, 3 });
		return sashForm;
	}

	/**
	 * This method is called after all controls have been alloated, including
	 * the preview. It can be used to set the preview text and to create
	 * listeners.
	 * 
	 */
	protected abstract void initializePage();

	/**
	 * Create the left side of the modify dialog. This is meant to be
	 * implemented by subclasses.
	 * 
	 * @param composite
	 *            Composite to create in
	 * @param numColumns
	 *            Number of columns to use
	 */
	protected abstract void doCreatePreferences(Composite composite,
			int numColumns);

	/**
	 * Create the right side of the modify dialog. By default, the preview is
	 * displayed there. Subclasses can override this method in order to
	 * customize the right-hand side of the dialog.
	 * 
	 * @param composite
	 *            Composite to create in
	 * @param numColumns
	 *            Number of columns to use
	 * @return Created composite
	 */
	protected Composite doCreatePreviewPane(Composite composite, int numColumns) {

		createLabel(numColumns, composite,
				FormatterMessages.ModifyDialogTabPage_preview_label_text);

		final PhpPreview preview = doCreatePhpPreview(composite);
		fDefaultFocusManager.add(preview.getControl());

		final GridData gd = createGridData(numColumns, GridData.FILL_BOTH, 0);
		gd.widthHint = 0;
		gd.heightHint = 0;
		preview.getControl().setLayoutData(gd);

		return composite;
	}

	/**
	 * To be implemented by subclasses. This method should return an instance of
	 * JavaPreview. Currently, the choice is between CompilationUnitPreview
	 * which contains a valid compilation unit, or a SnippetPreview which
	 * formats several independent code snippets and displays them in the same
	 * window.
	 * 
	 * @param parent
	 *            Parent composite
	 * @return Created preview
	 */
	protected abstract PhpPreview doCreatePhpPreview(Composite parent);

	/**
	 * This is called when the page becomes visible. Common tasks to do include:
	 * <ul>
	 * <li>Updating the preview.</li>
	 * <li>Setting the focus</li>
	 * </ul>
	 */
	final public void makeVisible() {
		fDefaultFocusManager.resetFocus();
		doUpdatePreview();
	}

	/**
	 * Update the preview. To be implemented by subclasses.
	 */
	protected abstract void doUpdatePreview();

	/**
	 *
	 */
	protected abstract void updatePreferences();

	protected void notifyValuesModified() {
		fModifyDialog.valuesModified();
	}

	/**
	 * Each tab page should remember where its last focus was, and reset it
	 * correctly within this method. This method is only called after
	 * initialization on the first tab page to be displayed in order to restore
	 * the focus of the last session.
	 */
	public void setInitialFocus() {
		if (fDefaultFocusManager.isUsed()) {
			fDefaultFocusManager.restoreFocus();
		}
	}

	/**
	 * Set the status field on the dialog. This can be used by tab pages to
	 * report inconsistent input. The OK button is disabled if the kind is
	 * IStatus.ERROR.
	 * 
	 * @param status
	 *            Status describing the current page error state
	 */
	protected void updateStatus(IStatus status) {
		fModifyDialog.updateStatus(status);
	}

	/*
	 * Create a GridLayout with the default margin and spacing settings, as well
	 * as the specified number of columns.
	 */
	protected GridLayout createGridLayout(int numColumns, boolean margins) {
		final GridLayout layout = new GridLayout(numColumns, false);
		layout.verticalSpacing = fPixelConverter
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = fPixelConverter
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		if (margins) {
			layout.marginHeight = fPixelConverter
					.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
			layout.marginWidth = fPixelConverter
					.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		} else {
			layout.marginHeight = 0;
			layout.marginWidth = 0;
		}
		return layout;
	}

	/*
	 * Convenience method to create a GridData.
	 */
	protected static GridData createGridData(int numColumns, int style,
			int widthHint) {
		final GridData gd = new GridData(style);
		gd.horizontalSpan = numColumns;
		gd.widthHint = widthHint;
		return gd;
	}

	/*
	 * Convenience method to create a label.
	 */
	protected static Label createLabel(int numColumns, Composite parent,
			String text) {
		return createLabel(numColumns, parent, text, GridData.FILL_HORIZONTAL);
	}

	/*
	 * Convenience method to create a label
	 */
	protected static Label createLabel(int numColumns, Composite parent,
			String text, int gridDataStyle) {
		final Label label = new Label(parent, SWT.WRAP);
		label.setFont(parent.getFont());
		label.setText(text);
		label.setLayoutData(createGridData(numColumns, gridDataStyle,
				SWT.DEFAULT));
		return label;
	}

	/*
	 * Convenience method to create a group
	 */
	protected Group createGroup(int numColumns, Composite parent, String text) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(parent.getFont());
		group.setLayoutData(createGridData(numColumns,
				GridData.FILL_HORIZONTAL, SWT.DEFAULT));

		final GridLayout layout = new GridLayout(numColumns, false);
		layout.verticalSpacing = fPixelConverter
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = fPixelConverter
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.marginHeight = fPixelConverter
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);

		group.setLayout(layout);
		group.setText(text);
		return group;
	}

	/*
	 * Convenience method to create a NumberPreference. The widget is registered
	 * as a potential focus holder, and the default updater is added.
	 */
	protected NumberPreference createNumberPref(Composite composite,
			int numColumns, String name, int minValue, int maxValue) {
		final NumberPreference pref = new NumberPreference(composite,
				numColumns, name, minValue, maxValue);
		fDefaultFocusManager.add(pref);
		pref.addObserver(fUpdater);
		return pref;
	}

	/*
	 * Convenience method to create a ComboPreference. The widget is registered
	 * as a potential focus holder, and the default updater is added.
	 */
	protected ComboPreference createComboPref(Composite composite,
			int numColumns, String name, String[] items) {
		final ComboPreference pref = new ComboPreference(composite, numColumns,
				name, items);
		fDefaultFocusManager.add(pref);
		pref.addObserver(fUpdater);
		return pref;
	}

	/*
	 * Convenience method to create a CheckboxPreference. The widget is
	 * registered as a potential focus holder, and the default updater is added.
	 */
	protected CheckboxPreference createCheckboxPref(Composite composite,
			int numColumns, String name) {
		final CheckboxPreference pref = new CheckboxPreference(composite,
				numColumns, name);
		fDefaultFocusManager.add(pref);
		pref.addObserver(fUpdater);
		return pref;
	}

	/*
	 * Convenience method to create a StringPreference. The widget is registered
	 * as a potential focus holder, and the default updater is added.
	 * 
	 * @since 3.6
	 */
	protected StringPreference createStringPref(Composite composite,
			int numColumns, String name, String key,
			IInputValidator inputValidator) {
		StringPreference pref = new StringPreference(composite, numColumns,
				name, inputValidator);
		fDefaultFocusManager.add(pref);
		pref.addObserver(fUpdater);
		return pref;
	}

	/*
	 * Create a nice phpdoc comment for some string.
	 */
	protected static String createPreviewHeader(String title) {
		return "/**\n* " + title + "\n*/\n"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Layout used for the settings part. Makes sure to show scrollbars if
	 * necessary. The settings part needs to be layouted on resize.
	 */
	private static class PageLayout extends Layout {

		private final ScrolledComposite fContainer;
		private final int fMinimalWidth;
		private final int fMinimalHight;

		private PageLayout(ScrolledComposite container, int minimalWidth,
				int minimalHight) {
			fContainer = container;
			fMinimalWidth = minimalWidth;
			fMinimalHight = minimalHight;
		}

		@Override
		public Point computeSize(Composite composite, int wHint, int hHint,
				boolean force) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
				return new Point(wHint, hHint);
			}

			int x = fMinimalWidth;
			int y = fMinimalHight;
			Control[] children = composite.getChildren();
			for (int i = 0; i < children.length; i++) {
				Point size = children[i].computeSize(SWT.DEFAULT, SWT.DEFAULT,
						force);
				x = Math.max(x, size.x);
				y = Math.max(y, size.y);
			}

			Rectangle area = fContainer.getClientArea();
			if (area.width > x) {
				fContainer.setExpandHorizontal(true);
			} else {
				fContainer.setExpandHorizontal(false);
			}

			if (area.height > y) {
				fContainer.setExpandVertical(true);
			} else {
				fContainer.setExpandVertical(false);
			}

			if (wHint != SWT.DEFAULT) {
				x = wHint;
			}
			if (hHint != SWT.DEFAULT) {
				y = hHint;
			}

			return new Point(x, y);
		}

		@Override
		public void layout(Composite composite, boolean force) {
			Rectangle rect = composite.getClientArea();
			Control[] children = composite.getChildren();
			for (int i = 0; i < children.length; i++) {
				children[i].setSize(rect.width, rect.height);
			}
		}
	}

}
