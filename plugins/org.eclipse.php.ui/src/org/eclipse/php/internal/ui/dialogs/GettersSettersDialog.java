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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.ui.util.CodeGenerationUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.editors.text.TextEditor;

public class GettersSettersDialog extends PHPSourceActionDialog {

	private static final int SELECT_GETTERS_ID = IDialogConstants.CLIENT_ID + 1;
	private static final int SELECT_SETTERS_ID = IDialogConstants.CLIENT_ID + 2;

	private SettersForFinalFieldsFilter fSettersForFinalFieldsFilter;
	private ConstantFieldsFilter fConstantFieldFilter;

	private boolean fSortOrder = true;

	public GettersSettersDialog(Shell parent, ILabelProvider labelProvider,
			GettersSettersContentProvider contentProvider, IType textSelection, TextEditor editor) {
		super(parent, labelProvider, contentProvider, textSelection, editor);
		fSettersForFinalFieldsFilter = new SettersForFinalFieldsFilter(contentProvider);
		fConstantFieldFilter = new ConstantFieldsFilter();
	}

	@Override
	protected CheckboxTreeViewer createTreeViewer(Composite parent) {
		CheckboxTreeViewer treeViewer = super.createTreeViewer(parent);
		treeViewer.addFilter(fSettersForFinalFieldsFilter);
		treeViewer.addFilter(fConstantFieldFilter);
		return treeViewer;
	}

	private Composite addSortOrder(Composite composite) {
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.GettersSettersAction_56);
		GridData gd = new GridData(GridData.FILL_BOTH);
		label.setLayoutData(gd);

		final Combo combo = new Combo(composite, SWT.READ_ONLY);
		combo.setItems(new String[] { Messages.GettersSettersAction_57, Messages.GettersSettersAction_58 });
		final int methodIndex = 1; // Hard-coded. Change this if the
		// list gets more complicated.
		// http://bugs.eclipse.org/bugs/show_bug.cgi?id=38400
		int sort = getSortOrder() ? 1 : 0;
		combo.setText(combo.getItem(sort));
		gd = new GridData(GridData.FILL_BOTH);
		combo.setLayoutData(gd);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setSortOrder(combo.getSelectionIndex() == methodIndex);
			}
		});
		return composite;
	}

	@Override
	protected Composite createSelectionButtons(Composite composite) {
		Composite buttonComposite = super.createSelectionButtons(composite);

		GridLayout layout = new GridLayout();
		buttonComposite.setLayout(layout);

		createButton(buttonComposite, SELECT_GETTERS_ID, Messages.GettersSettersAction_0, false);
		createButton(buttonComposite, SELECT_SETTERS_ID, Messages.GettersSettersAction_38, false);

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 1;

		return buttonComposite;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		switch (buttonId) {
		case SELECT_GETTERS_ID: {
			getTreeViewer().setCheckedElements(getGetterSetterElements(true));
			updateOKStatus();
			break;
		}
		case SELECT_SETTERS_ID: {
			getTreeViewer().setCheckedElements(getGetterSetterElements(false));
			updateOKStatus();
			break;
		}
		}
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.dialogs.SourceActionDialog#createLinkControl
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createLinkControl(Composite composite) {
		Link link = new Link(composite, SWT.WRAP);
		link.setText(Messages.GettersSettersDialog_0);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openCodeTempatePage("org.eclipse.php.ui.editor.templates.php.codetemplates.gettercomment"); //$NON-NLS-1$
			}
		});
		link.setToolTipText(""); //$NON-NLS-1$

		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.widthHint = convertWidthInCharsToPixels(40); // only expand
		link.setLayoutData(gridData);
		return link;
	}

	@Override
	protected Composite createInsertPositionCombo(Composite composite) {
		Composite entryComposite = super.createInsertPositionCombo(composite);
		addSortOrder(entryComposite);
		addVisibilityAndModifiersChoices(entryComposite);
		return entryComposite;
	}

	private Object[] getGetterSetterElements(boolean isGetter) {
		Object[] allFields = fContentProvider.getElements(null);
		Set<GetterSetterEntry> result = new HashSet<>();
		for (Object object : allFields) {
			IField field = (IField) object;
			GetterSetterEntry[] entries = getEntries(field);
			for (GetterSetterEntry entry : entries) {
				if (entry.isGetter == isGetter) {
					result.add(entry);
				}
			}
		}
		return result.toArray();
	}

	private GetterSetterEntry[] getEntries(IField field) {
		List<Object> result = Arrays.asList(fContentProvider.getChildren(field));
		return result.toArray(new GetterSetterEntry[result.size()]);
	}

	public boolean getSortOrder() {
		return fSortOrder;
	}

	public void setSortOrder(boolean sort) {
		if (fSortOrder != sort) {
			fSortOrder = sort;
			if (getTreeViewer() != null) {
				getTreeViewer().refresh();
			}
		}
	}

	private static class SettersForFinalFieldsFilter extends ViewerFilter {

		private final GettersSettersContentProvider fContentProvider;

		public SettersForFinalFieldsFilter(GettersSettersContentProvider contentProvider) {
			fContentProvider = contentProvider;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof GetterSetterEntry) {
				GetterSetterEntry getterSetterEntry = (GetterSetterEntry) element;
				return getterSetterEntry.isGetter || !getterSetterEntry.isFinal;
			} else if (element instanceof IField) {
				Object[] children = fContentProvider.getChildren(element);
				for (Object element2 : children) {
					GetterSetterEntry curr = (GetterSetterEntry) element2;
					if (curr.isGetter || !curr.isFinal) {
						return true;
					}
				}
				return false;
			}
			return true;
		}
	}

	private static class ConstantFieldsFilter extends ViewerFilter {

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof GetterSetterEntry) {
				GetterSetterEntry getterSetterEntry = (GetterSetterEntry) element;
				return !getterSetterEntry.isConstant;
			} else if (element instanceof IField) {
				IField field = (IField) element;
				try {
					int flags = field.getFlags();
					if (!PHPFlags.isConstant(flags)) {
						return true;
					}
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
				}
			}
			return false;
		}
	}

	public static class GettersSettersContentProvider implements ITreeContentProvider {

		private static final Object[] EMPTY = new Object[0];

		private Viewer fViewer;

		private Map<IField, GetterSetterEntry[]> fGetterSetterEntries;

		public GettersSettersContentProvider(Map<IField, GetterSetterEntry[]> entries) {
			fGetterSetterEntries = entries;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			fViewer = viewer;
		}

		public Viewer getViewer() {
			return fViewer;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IField) {
				IField parentField = (IField) parentElement;
				int flags;
				try {
					flags = parentField.getFlags();
					if (!PHPFlags.isConstant(flags)) {
						return fGetterSetterEntries.get(parentElement);
					}
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
				}
			}
			return EMPTY;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof IMember) {
				return ((IMember) element).getDeclaringType();
			}
			if (element instanceof GetterSetterEntry) {
				return ((GetterSetterEntry) element).field;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return fGetterSetterEntries.keySet().toArray();
		}

		@Override
		public void dispose() {

		}
	}

	public static class GetterSetterLabelProvider extends ModelElementLabelProvider {

		@Override
		public String getText(Object element) {
			if (element instanceof GetterSetterEntry) {
				GetterSetterEntry entry = (GetterSetterEntry) element;
				try {
					if (entry.isGetter) {
						return CodeGenerationUtils.getGetterName(entry.field) + "()"; //$NON-NLS-1$
					} else {
						return CodeGenerationUtils.getSetterName(entry.field) + '(' + entry.field.getElementName()
								+ ')';
					}
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
					return ""; //$NON-NLS-1$
				}
			}
			return super.getText(element);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof GetterSetterEntry) {
				return PHPPluginImages.get(PHPPluginImages.IMG_FIELD_PUBLIC);
			}
			return super.getImage(element);
		}
	}

	public static class GettersSettersSelectionStatusValidator implements ISelectionStatusValidator {

		private static int fEntries;

		public GettersSettersSelectionStatusValidator(int entries) {
			fEntries = entries;
		}

		@Override
		public IStatus validate(Object[] selection) {
			if (selection == null || selection.length == 0) {
				return new Status(IStatus.ERROR, PHPUiPlugin.ID, ""); //$NON-NLS-1$
			}

			Set<String> map = new HashSet<>(selection.length);
			int count = 0;
			for (Object element : selection) {
				try {
					if (element instanceof GetterSetterEntry) {
						IField getsetField = ((GetterSetterEntry) element).field;
						if (((GetterSetterEntry) element).isGetter) {
							if (!map.add(CodeGenerationUtils.getGetterName(getsetField))) {
								return new Status(IStatus.WARNING, PHPUiPlugin.ID, Messages.GettersSettersDialog_3);
							}
						} else {
							String key = createSignatureKey(CodeGenerationUtils.getSetterName(getsetField),
									getsetField);
							if (!map.add(key)) {
								return new Status(IStatus.WARNING, PHPUiPlugin.ID, Messages.GettersSettersDialog_3);
							}
						}
						count++;
					}
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
				}
			}

			if (count == 0) {
				return new Status(IStatus.ERROR, PHPUiPlugin.ID, ""); //$NON-NLS-1$
			}
			String message = MessageFormat.format(Messages.SourceActions_ValidatorText,
					new Object[] { String.valueOf(count), String.valueOf(fEntries) });
			return new Status(IStatus.INFO, PHPUiPlugin.ID, message);
		}

		/**
		 * Creates a key used in hash maps for a method signature
		 * (gettersettername+arguments(fqn)).
		 * 
		 * @param methodName
		 *            the method name
		 * @param field
		 *            the filed
		 * @return the signature
		 * @throws ModelException
		 *             if getting the field's type signature fails
		 */
		private String createSignatureKey(String methodName, IField field) throws ModelException {
			StringBuilder buffer = new StringBuilder();
			buffer.append(methodName);
			buffer.append("#"); //$NON-NLS-1$
			String fieldType = field.getDeclaringType().getElementName();
			buffer.append(fieldType);

			return buffer.toString();
		}

	}

	public static class GetterSetterEntry {
		public final IField field;
		public final boolean isGetter;
		public final boolean isFinal;
		public final boolean isConstant;

		public GetterSetterEntry(IField field, boolean isGetterEntry, boolean isFinal, boolean isConstant) {
			this.field = field;
			this.isGetter = isGetterEntry;
			this.isFinal = isFinal;
			this.isConstant = isConstant;
		}
	}

}
