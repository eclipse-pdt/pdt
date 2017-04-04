/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PHPCodeTemplatePreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.wst.jsdt.internal.ui.refactoring.IVisibilityChangeListener;

/**
 * This class is used for creating the dialog for Source actions (based on the
 * JDT source dialog).
 * 
 */
public class PHPSourceActionDialog extends CheckedTreeSelectionDialog {

	private static final String SETTINGS_SECTION_METHODS = "PHPSourceActionDialog.methods"; //$NON-NLS-1$

	private static final String SETTINGS_VISIBILITY_MODIFIER = "PHPSourceActionDialog.VisibilityModifier"; //$NON-NLS-1$

	private static final String SETTINGS_FINAL_MODIFIER = "PHPSourceActionDialog.FinalModifier"; //$NON-NLS-1$

	private static final String SETTINGS_COMMENTS = "PHPSourceActionDialog.Comments"; //$NON-NLS-1$

	private static final String SETTINGS_INSERT_POSITION = "PHPSourceActionDialog.InsertPosition"; //$NON-NLS-1$

	private static final int INSERT_FIRST_INDEX = 0;
	private static final int INSERT_LAST_INDEX = 1;
	private static final int INSERT_POSITION_FROM_EDITOR = 2;

	private final List<IMethod> fInsertPositions;
	private final List<String> fLabels;
	private int fCurrentPositionIndex;

	ITreeContentProvider fContentProvider;
	private int fVisibilityModifier;
	private boolean fFinal;
	private IProject fProject;

	private boolean fGenerateComment;
	private final int fWidth, fHeight;
	private final String fCommentString;
	private boolean fEnableInsertPosition = true;

	protected IDialogSettings fSettings;

	protected boolean fHasUserChangedPositionIndex;

	private TextEditor fEditor;

	public PHPSourceActionDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider,
			IType type, TextEditor editor) {
		super(parent, labelProvider, contentProvider);
		fEditor = editor;
		fContentProvider = contentProvider;
		fCommentString = Messages.GettersSettersAction_27;
		setEmptyListMessage(Messages.GettersSettersAction_28);

		fWidth = 60;
		fHeight = 18;

		IDialogSettings dialogSettings = PHPUiPlugin.getDefault().getDialogSettings();
		String sectionId = SETTINGS_SECTION_METHODS;
		fSettings = dialogSettings.getSection(sectionId);
		if (fSettings == null) {
			fSettings = dialogSettings.addNewSection(sectionId);
		}

		fInsertPositions = new ArrayList<>();
		fLabels = new ArrayList<>();

		// generate a list of possible insertion locations
		IMethod[] methods = null;
		try {
			methods = type.getMethods();
		} catch (ModelException e1) {
		}

		if (methods == null) {
			return;
		}

		fInsertPositions.add(methods.length > 0 ? methods[0] : null); // first
																		// //$NON-NLS-1$
		fInsertPositions.add(null); // last

		fLabels.add(Messages.GettersSettersAction_29);
		fLabels.add(Messages.GettersSettersAction_30);

		for (int i = 0; i < methods.length; i++) {
			IMethod curr = methods[i];
			StringBuilder methodLabel = new StringBuilder();
			methodLabel.append(curr.getElementName());
			methodLabel.append("("); //$NON-NLS-1$
			String[] param = null;
			try {
				param = curr.getParameterNames();
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
			if (param != null) {
				for (int j = 0; j < param.length; j++) {
					methodLabel.append("$"); //$NON-NLS-1$
					methodLabel.append(param[j]);
					if (j < param.length - 1) {
						methodLabel.append(", "); //$NON-NLS-1$
					}
				}
			}
			methodLabel.append(')');
			fLabels.add(Messages.GettersSettersAction_31 + '\'' + methodLabel.toString() + '\'');

			if (i < methods.length - 1) {
				fInsertPositions.add(methods[i + 1]);
			}
		}
		fInsertPositions.add(null);

		try {
			restoreWidgetsValue(methods);
		} catch (ModelException e) {

		}

	}

	public void setProject(IProject project) {
		fProject = project;

	}

	public IProject getProject() {
		return fProject;
	}

	protected void restoreWidgetsValue(IMethod[] methods) throws ModelException {
		fVisibilityModifier = asInt(fSettings.get(SETTINGS_VISIBILITY_MODIFIER), Flags.AccPublic);
		fFinal = asBoolean(fSettings.get(SETTINGS_FINAL_MODIFIER), false);
		fGenerateComment = asBoolean(fSettings.get(SETTINGS_COMMENTS), true);

		int storedPositionIndex = asInt(fSettings.get(SETTINGS_INSERT_POSITION), INSERT_POSITION_FROM_EDITOR);
		if (storedPositionIndex == INSERT_POSITION_FROM_EDITOR) {
			int indexAfterCursor = getElementAfterCursorPosition(fEditor, methods);
			if (indexAfterCursor == -1 || indexAfterCursor == 0) {
				fCurrentPositionIndex = INSERT_FIRST_INDEX;
			} else if (indexAfterCursor > 0) {
				fCurrentPositionIndex = indexAfterCursor + 1;
			}
		} else {
			fCurrentPositionIndex = storedPositionIndex <= INSERT_FIRST_INDEX ? INSERT_FIRST_INDEX : INSERT_LAST_INDEX;
		}
	}

	private int getElementAfterCursorPosition(TextEditor editor, IModelElement[] members) throws ModelException {
		if (editor == null) {
			return -1;
		}
		int offset = ((ITextSelection) editor.getSelectionProvider().getSelection()).getOffset();

		for (int i = 0; i < members.length; i++) {
			IMember curr = (IMember) members[i];
			ISourceRange range = curr.getSourceRange();
			if (offset < range.getOffset()) {
				return i;
			}
		}
		return members.length;
	}

	protected Composite addVisibilityAndModifiersChoices(Composite buttonComposite) {
		// Add visibility and modifiers buttons
		IVisibilityChangeListener visibilityChangeListener = new IVisibilityChangeListener() {
			@Override
			public void visibilityChanged(int newVisibility) {
				setVisibility(newVisibility);
			}

			@Override
			public void modifierChanged(int modifier, boolean isChecked) {
				switch (modifier) {
				case Flags.AccFinal: {
					setFinal(isChecked);
					return;
				}

				default:
					return;
				}
			}
		};

		int initialVisibility = getVisibilityModifier();
		int[] availableVisibilities = new int[] { Flags.AccPublic, Flags.AccProtected, Flags.AccPrivate };
		return createVisibilityControlAndModifiers(buttonComposite, visibilityChangeListener, availableVisibilities,
				initialVisibility);
	}

	protected Composite createVisibilityControlAndModifiers(Composite parent,
			final IVisibilityChangeListener visibilityChangeListener, int[] availableVisibilities,
			int correctVisibility) {
		Composite visibilityComposite = createVisibilityControl(parent, visibilityChangeListener, availableVisibilities,
				correctVisibility);

		Button finalCheckboxButton = new Button(visibilityComposite, SWT.CHECK);
		finalCheckboxButton.setText(Messages.GettersSettersAction_37);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		finalCheckboxButton.setLayoutData(gd);
		finalCheckboxButton.setData(Flags.AccFinal);
		finalCheckboxButton.setEnabled(true);
		finalCheckboxButton.setSelection(isFinal());
		finalCheckboxButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				visibilityChangeListener.modifierChanged(((Integer) event.widget.getData()).intValue(),
						((Button) event.widget).getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				widgetSelected(event);
			}
		});

		return visibilityComposite;
	}

	protected Composite createVisibilityControl(Composite parent,
			final IVisibilityChangeListener visibilityChangeListener, int[] availableVisibilities,
			int correctVisibility) {
		List<Integer> allowedVisibilities = convertToIntegerList(availableVisibilities);
		if (allowedVisibilities.size() == 1) {
			return null;
		}

		Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.GettersSettersAction_33); // $NON-NLS-1$
		GridData gd = new GridData(GridData.FILL_BOTH);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 4;
		group.setLayout(layout);

		String[] labels = new String[] { Messages.GettersSettersAction_34, Messages.GettersSettersAction_35,
				Messages.GettersSettersAction_36, };
		Integer[] data = new Integer[] { Flags.AccPublic, Flags.AccProtected, Flags.AccPrivate };
		Integer initialVisibility = correctVisibility;
		for (int i = 0; i < labels.length; i++) {
			Button radio = new Button(group, SWT.RADIO);
			Integer visibilityCode = data[i];
			radio.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
			radio.setText(labels[i]);
			radio.setData(visibilityCode);
			radio.setSelection(visibilityCode.equals(initialVisibility));
			radio.setEnabled(allowedVisibilities.contains(visibilityCode));
			radio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					visibilityChangeListener.visibilityChanged(((Integer) event.widget.getData()).intValue());
				}
			});
		}
		return group;
	}

	private List<Integer> convertToIntegerList(int[] array) {
		List<Integer> result = new ArrayList<>(array.length);
		for (int element : array) {
			result.add(element);
		}
		return result;
	}

	public boolean isFinal() {
		return fFinal;
	}

	public int getVisibilityModifier() {
		return fVisibilityModifier;
	}

	protected void setVisibility(int visibility) {
		fVisibilityModifier = visibility;
	}

	private void setFinal(boolean value) {
		fFinal = value;
	}

	protected boolean asBoolean(String string, boolean defaultValue) {
		if (string != null) {
			return StringConverter.asBoolean(string, defaultValue);
		}
		return defaultValue;
	}

	protected int asInt(String string, int defaultValue) {
		if (string != null) {
			return StringConverter.asInt(string, defaultValue);
		}
		return defaultValue;
	}

	@Override
	public void create() {
		super.create();

		// select the first checked element, or if none are checked, the first
		// element
		CheckboxTreeViewer treeViewer = getTreeViewer();
		TreeItem[] items = treeViewer.getTree().getItems();
		if (items.length > 0) {
			Object revealedElement = items[0];

			for (TreeItem item : items) {
				if (item.getChecked()) {
					revealedElement = item.getData();
					break;
				}
			}
			treeViewer.setSelection(new StructuredSelection(revealedElement));
			treeViewer.reveal(revealedElement);
		}
	}

	public void setGenerateComment(boolean comment) {
		fGenerateComment = comment;
	}

	public boolean getGenerateComment() {
		return fGenerateComment;
	}

	protected ITreeContentProvider getContentProvider() {
		return fContentProvider;
	}

	protected Control createLinkControl(Composite composite) {
		return null; // No link as default
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData gd;

		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);

		Label messageLabel = createMessageArea(composite);
		if (messageLabel != null) {
			gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			gd.horizontalSpan = 2;
			messageLabel.setLayoutData(gd);
		}

		Composite inner = new Composite(composite, SWT.NONE);
		GridLayout innerLayout = new GridLayout();
		innerLayout.numColumns = 2;
		innerLayout.marginHeight = 0;
		innerLayout.marginWidth = 0;
		inner.setLayout(innerLayout);
		inner.setFont(parent.getFont());

		CheckboxTreeViewer treeViewer = createTreeViewer(inner);

		gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = convertWidthInCharsToPixels(fWidth);
		gd.heightHint = convertHeightInCharsToPixels(fHeight);
		treeViewer.getControl().setLayoutData(gd);

		Composite buttonComposite = createSelectionButtons(inner);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		buttonComposite.setLayoutData(gd);

		gd = new GridData(GridData.FILL_BOTH);
		inner.setLayoutData(gd);

		Composite insertPositionComposite = createInsertPositionCombo(composite);
		insertPositionComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite commentComposite = createCommentSelection(composite);
		commentComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Control linkControl = createLinkControl(composite);
		if (linkControl != null) {
			linkControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		gd = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gd);

		applyDialogFont(composite);

		return composite;
	}

	@Override
	protected Composite createSelectionButtons(Composite composite) {
		Composite buttonComposite = super.createSelectionButtons(composite);

		GridLayout layout = new GridLayout();
		buttonComposite.setLayout(layout);

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 1;

		return buttonComposite;
	}

	protected Composite createInsertPositionCombo(Composite composite) {
		Composite selectionComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		selectionComposite.setLayout(layout);

		addOrderEntryChoices(selectionComposite);
		return selectionComposite;
	}

	private Composite addOrderEntryChoices(Composite buttonComposite) {
		Label enterLabel = new Label(buttonComposite, SWT.NONE);
		enterLabel.setText(Messages.GettersSettersAction_32);
		if (!fEnableInsertPosition) {
			enterLabel.setEnabled(false);
		}
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		enterLabel.setLayoutData(gd);

		final Combo enterCombo = new Combo(buttonComposite, SWT.READ_ONLY);
		if (!fEnableInsertPosition) {
			enterCombo.setEnabled(false);
		}
		fillWithPossibleInsertPositions(enterCombo);

		gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = convertWidthInCharsToPixels(fWidth);
		enterCombo.setLayoutData(gd);
		enterCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = enterCombo.getSelectionIndex();
				// Add persistence only if first or last method:
				// http://bugs.eclipse.org/bugs/show_bug.cgi?id=38400
				setInsertPosition(index);
				fHasUserChangedPositionIndex = true;
			}
		});

		return buttonComposite;
	}

	private void fillWithPossibleInsertPositions(Combo combo) {
		combo.setItems(fLabels.toArray(new String[fLabels.size()]));
		combo.select(fCurrentPositionIndex);
	}

	protected void openCodeTempatePage(String id) {
		Map<String, String> arg = new HashMap<>();
		arg.put(PHPCodeTemplatePreferencePage.DATA_SELECT_TEMPLATE, id);
		PreferencesUtil
				.createPropertyDialogOn(getShell(), getProject(), PHPCodeTemplatePreferencePage.PROP_ID, null, arg)
				.open();
	}

	/***
	 * Set insert position valid input is 0 for the first position, 1 for the
	 * last position, > 1 for all else.
	 */
	private void setInsertPosition(int insert) {
		fCurrentPositionIndex = insert;
	}

	/*
	 * Determine where in the file to enter the newly created methods.
	 */
	public IMethod getElementPosition() {
		return fInsertPositions.get(fCurrentPositionIndex);
	}

	public int getInsertOffset() throws ModelException {
		IModelElement elementPosition = getElementPosition();
		if (elementPosition instanceof ISourceReference) {
			return ((ISourceReference) elementPosition).getSourceRange().getOffset();
		}
		return -1;
	}

	protected Composite createCommentSelection(Composite composite) {
		Composite commentComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		commentComposite.setLayout(layout);
		commentComposite.setFont(composite.getFont());

		Button commentButton = new Button(commentComposite, SWT.CHECK);
		commentButton.setText(fCommentString);

		commentButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isSelected = (((Button) e.widget).getSelection());
				setGenerateComment(isSelected);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		commentButton.setSelection(getGenerateComment());
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 2;
		commentButton.setLayoutData(gd);

		return commentComposite;
	}

	@Override
	public boolean close() {
		fSettings.put(SETTINGS_VISIBILITY_MODIFIER, StringConverter.asString(fVisibilityModifier));
		fSettings.put(SETTINGS_FINAL_MODIFIER, StringConverter.asString(fFinal));
		fSettings.put(SETTINGS_COMMENTS, fGenerateComment);
		if (fHasUserChangedPositionIndex) {
			if (fCurrentPositionIndex == INSERT_FIRST_INDEX || fCurrentPositionIndex == INSERT_LAST_INDEX) {
				fSettings.put(SETTINGS_INSERT_POSITION, StringConverter.asString(fCurrentPositionIndex));
			} else if (fEditor != null) {
				fSettings.put(SETTINGS_INSERT_POSITION, StringConverter.asString(INSERT_POSITION_FROM_EDITOR));
			}
		}

		return super.close();
	}

}
