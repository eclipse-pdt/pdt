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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.viewsupport.BindingLabelProvider;
import org.eclipse.php.ui.util.CodeGenerationUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.editors.text.TextEditor;

public class OverrideMethodDialog extends PHPSourceActionDialog {

	private static class OverrideMethodContentProvider implements ITreeContentProvider {

		private final Object[] fEmpty = new Object[0];

		private IMethodBinding[] fMethods;

		private IDialogSettings fSettings;

		private boolean fShowTypes;

		private Object[] fTypes;

		private static final String SETTINGS_SECTION = "OverrideMethodDialog"; //$NON-NLS-1$

		private static final String SETTINGS_SHOWTYPES = "showtypes"; //$NON-NLS-1$

		/**
		 * Constructor for OverrideMethodContentProvider.
		 */
		public OverrideMethodContentProvider() {
			IDialogSettings dialogSettings = PHPUiPlugin.getDefault().getDialogSettings();
			fSettings = dialogSettings.getSection(SETTINGS_SECTION);
			if (fSettings == null) {
				fSettings = dialogSettings.addNewSection(SETTINGS_SECTION);
				fSettings.put(SETTINGS_SHOWTYPES, true);
			}
			fShowTypes = fSettings.getBoolean(SETTINGS_SHOWTYPES);
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ITypeBinding) {
				ArrayList<IMethodBinding> result = new ArrayList<>(fMethods.length);
				for (IMethodBinding method : fMethods) {
					if (method.getDeclaringClass().equals((IBinding) parentElement)) {
						result.add(method);
					}
				}
				return result.toArray();
			}
			return fEmpty;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return fShowTypes ? fTypes : fMethods;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof IMethodBinding) {
				return ((IMethodBinding) element).getDeclaringClass();
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		public void init(IMethodBinding[] methods, ITypeBinding[] types) {
			fMethods = methods;
			fTypes = types;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private static class OverrideMethodComparator extends ViewerComparator {

		private ITypeBinding[] fAllTypes = new ITypeBinding[0];

		public OverrideMethodComparator(ITypeBinding curr) {
			if (curr != null) {
				ITypeBinding[] superTypes = Bindings.getAllSuperTypes(curr);
				fAllTypes = new ITypeBinding[superTypes.length + 1];
				fAllTypes[0] = curr;
				System.arraycopy(superTypes, 0, fAllTypes, 1, superTypes.length);
			}
		}

		@Override
		public int compare(Viewer viewer, Object first, Object second) {
			if (first instanceof ITypeBinding && second instanceof ITypeBinding) {
				final ITypeBinding left = (ITypeBinding) first;
				final ITypeBinding right = (ITypeBinding) second;
				if (right.isSubTypeCompatible(left)) {
					return +1;
				} else if (left.isSubTypeCompatible(right)) {
					return -1;
				}
				return 0;
			} else {
				return super.compare(viewer, first, second);
			}
		}
	}

	private static class OverrideMethodValidator implements ISelectionStatusValidator {
		private static int fNumMethods;

		public OverrideMethodValidator(int entries) {
			fNumMethods = entries;
		}

		@Override
		public IStatus validate(Object[] selection) {
			int count = 0;
			for (Object element : selection) {
				if (element instanceof IMethodBinding) {
					count++;
				}
			}
			if (count == 0) {
				return new Status(IStatus.ERROR, PHPUiPlugin.ID, ""); //$NON-NLS-1$
			}
			return new Status(IStatus.INFO, PHPUiPlugin.ID,
					MessageFormat.format(Messages.OverrideMethodDialog_7, count, fNumMethods));
		}
	}

	private Program fUnit = null;

	public OverrideMethodDialog(Shell shell, TextEditor editor, IType type, boolean isSubType) throws ModelException {
		super(shell, new BindingLabelProvider(), new OverrideMethodContentProvider(), type, editor);

		IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		fUnit = CodeGenerationUtils.getASTRoot(type.getSourceModule(), document, type.getScriptProject().getProject());

		ITypeBinding binding = getTypeBinding(fUnit, type);

		List<IMethodBinding> toImplement = new ArrayList<>();
		List<IMethodBinding> overridable = new ArrayList<>();
		if (binding != null) {
			final IMethodBinding[] methods = CodeGenerationUtils.getOverridableMethods(fUnit.getAST(), binding, false);
			for (final IMethodBinding cur : methods) {
				if (Bindings.isVisibleInHierarchy(cur)) {
					overridable.add(cur);
				}
			}
		}
		for (IMethodBinding element : overridable) {
			if (Flags.isAbstract(element.getModifiers())) {
				toImplement.add(element);
			}
		}

		IMethodBinding[] toImplementArray = toImplement.toArray(new IMethodBinding[toImplement.size()]);
		setInitialSelections(toImplementArray);

		ArrayList<ITypeBinding> expanded = new ArrayList<>(toImplementArray.length);
		for (int i = 0; i < toImplementArray.length; i++) {
			if (!expanded.contains(toImplementArray[i].getDeclaringClass())) {
				expanded.add(toImplementArray[i].getDeclaringClass());
			}
		}

		ArrayList<ITypeBinding> types = new ArrayList<>(overridable.size());
		for (IMethodBinding o : overridable) {
			if (!types.contains(o.getDeclaringClass())) {
				types.add(o.getDeclaringClass());
			}
		}

		ITypeBinding[] typesArrays = types.toArray(new ITypeBinding[types.size()]);
		OverrideMethodComparator comparator = new OverrideMethodComparator(binding);
		if (expanded.isEmpty() && typesArrays.length > 0) {
			comparator.sort(null, typesArrays);
			expanded.add(typesArrays[0]);
		}
		setExpandedElements(expanded.toArray());

		((OverrideMethodContentProvider) getContentProvider()).init(overridable.toArray(new IMethodBinding[0]),
				typesArrays);

		setTitle(Messages.OverrideMethodDialog_8);
		setMessage(null);
		setValidator(new OverrideMethodValidator(overridable.size()));
		setComparator(comparator);
		setContainerMode(true);
		setSize(60, 18);
		setInput(new Object());
	}

	private ITypeBinding getTypeBinding(Program fUnit, IType type) throws ModelException {
		IEvaluatedType evaluatedType = PHPClassType.fromIType(type);
		return new TypeBinding(fUnit.getAST().getBindingResolver(), evaluatedType, type);
	}

	@Override
	protected Control createLinkControl(Composite composite) {
		Link link = new Link(composite, SWT.WRAP);
		link.setText(Messages.OverrideMethodDialog_9);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openCodeTempatePage("org.eclipse.php.ui.editor.templates.php.codetemplates.overridecomment"); //$NON-NLS-1$
			}
		});
		link.setToolTipText(Messages.OverrideMethodDialog_11);

		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.widthHint = convertWidthInCharsToPixels(40); // only expand
		link.setLayoutData(gridData);
		return link;
	}

	@Override
	protected CheckboxTreeViewer createTreeViewer(Composite composite) {
		initializeDialogUnits(composite);
		ViewForm pane = new ViewForm(composite, SWT.BORDER | SWT.FLAT);
		CheckboxTreeViewer treeViewer = super.createTreeViewer(pane);
		pane.setContent(treeViewer.getControl());
		GridLayout paneLayout = new GridLayout();
		paneLayout.marginHeight = 0;
		paneLayout.marginWidth = 0;
		paneLayout.numColumns = 1;
		pane.setLayout(paneLayout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = convertWidthInCharsToPixels(55);
		gd.heightHint = convertHeightInCharsToPixels(15);
		pane.setLayoutData(gd);
		treeViewer.getTree().setFocus();
		return treeViewer;
	}

	public boolean hasMethodsToOverride() {
		return getContentProvider().getElements(null).length > 0;
	}

	public Program getCompilationUnit() {
		return fUnit;
	}

}
