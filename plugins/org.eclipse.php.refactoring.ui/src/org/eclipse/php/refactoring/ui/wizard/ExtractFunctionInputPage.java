/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.internal.ui.dialogs.TextFieldNavigationHandler;
import org.eclipse.dltk.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.editor.PHPSourceViewer;
import org.eclipse.php.refactoring.core.extract.function.ExtractFunctionRefactoring;
import org.eclipse.php.refactoring.core.extract.function.IParameterListChangeListener;
import org.eclipse.php.refactoring.core.extract.function.ParameterInfo;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ExtractFunctionInputPage extends UserInputWizardPage {

	public static final String PAGE_NAME = "ExtractMethodInputPage";//$NON-NLS-1$

	private ExtractFunctionRefactoring fRefactoring;
	private Text fTextField;
	private boolean fFirstTime;
	private PHPSourceViewer fSignaturePreview;
	private String fSignaturePreviewDocument;
	private IDialogSettings fSettings;

	private static final String DESCRIPTION = RefactoringMessages.ExtractMethodInputPage_description;
	// private static final String THROW_RUNTIME_EXCEPTIONS =
	// "ThrowRuntimeExceptions"; //$NON-NLS-1$
	private static final String GENERATE_PHPDOC = "GeneratePHPdoc"; //$NON-NLS-1$

	public ExtractFunctionInputPage() {
		super(PAGE_NAME);
		setImageDescriptor(RefactoringUIPlugin.imageDescriptorFromPlugin(RefactoringUIPlugin.PLUGIN_ID,
				"icons/full/wizban/compunitrefact_wiz.png"));//$NON-NLS-1$
		setDescription(DESCRIPTION);
		fFirstTime = true;
		fSignaturePreviewDocument = ""; //$NON-NLS-1$
	}

	@Override
	public void createControl(Composite parent) {
		fRefactoring = (ExtractFunctionRefactoring) getRefactoring();
		loadSettings();

		Composite result = new Composite(parent, SWT.NONE);
		setControl(result);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		result.setLayout(layout);
		RowLayouter layouter = new RowLayouter(2);
		GridData gd = null;

		initializeDialogUnits(result);

		Label label = new Label(result, SWT.NONE);
		label.setText(getLabelText());

		fTextField = createTextInputField(result, SWT.BORDER);
		fTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		layouter.perform(label, fTextField, 1);

		// ASTNode[] destinations= fRefactoring.getDestinations();
		// if (destinations.length > 1) {
		// label= new Label(result, SWT.NONE);
		// label.setText(RefactoringMessages.ExtractMethodInputPage_destination_type);
		// final Combo combo= new Combo(result, SWT.READ_ONLY | SWT.DROP_DOWN);
		// combo.setVisibleItemCount(30);
		// for (int i= 0; i < destinations.length; i++) {
		// ASTNode declaration= destinations[i];
		// combo.add(getLabel(declaration));
		// }
		// combo.select(0);
		// combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// combo.addSelectionListener(new SelectionListener() {
		// public void widgetSelected(SelectionEvent e) {
		// fRefactoring.setDestination(combo.getSelectionIndex());
		// }
		// public void widgetDefaultSelected(SelectionEvent e) {
		// // nothing
		// }
		// });
		// }

		if (fRefactoring.isClassMethod()) {
			label = new Label(result, SWT.NONE);
			label.setText(RefactoringMessages.ExtractMethodInputPage_access_Modifiers);

			Composite group = new Composite(result, SWT.NONE);
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			layout = new GridLayout();
			layout.numColumns = 4;
			layout.marginWidth = 0;
			group.setLayout(layout);

			String[] labels = new String[] { RefactoringMessages.ExtractMethodInputPage_public,
					RefactoringMessages.ExtractMethodInputPage_protected,
					RefactoringMessages.ExtractMethodInputPage_default,
					RefactoringMessages.ExtractMethodInputPage_private };
			Integer[] data = new Integer[] { Modifiers.AccPublic, Modifiers.AccProtected, Modifiers.AccDefault,
					Modifiers.AccPrivate };
			Integer visibility = fRefactoring.getVisibility();
			for (int i = 0; i < labels.length; i++) {
				Button radio = new Button(group, SWT.RADIO);
				radio.setText(labels[i]);
				radio.setData(data[i]);
				if (data[i].equals(visibility)) {
					radio.setSelection(true);
				}
				radio.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent event) {
						setVisibility((Integer) event.widget.getData());
					}
				});
			}
			layouter.perform(label, group, 1);
		}

		if (!fRefactoring.getParameterInfos().isEmpty()) {
			ChangeParametersControl cp = new ChangeParametersControl(result, SWT.NONE,
					RefactoringMessages.ExtractMethodInputPage_parameters, new IParameterListChangeListener() {
						@Override
						public void parameterChanged(ParameterInfo parameter) {
							parameterModified();
						}

						@Override
						public void parameterListChanged() {
							parameterModified();
						}

						@Override
						public void parameterAdded(ParameterInfo parameter) {
							updatePreview(getText());
						}
					}, ChangeParametersControl.Mode.EXTRACT_METHOD);
			gd = new GridData(GridData.FILL_BOTH);
			gd.horizontalSpan = 2;
			cp.setLayoutData(gd);
			cp.setInput(fRefactoring.getParameterInfos());
		}

		// Button checkBox= new Button(result, SWT.CHECK);
		// checkBox.setText(RefactoringMessages.ExtractMethodInputPage_throwRuntimeExceptions);
		// checkBox.setSelection(fSettings.getBoolean(THROW_RUNTIME_EXCEPTIONS));
		// checkBox.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e) {
		// setRethrowRuntimeException(((Button)e.widget).getSelection());
		// }
		// });
		// layouter.perform(checkBox);

		Button checkBox = new Button(result, SWT.CHECK);
		if (fRefactoring.isClassMethod()) {
			checkBox.setText(PHPRefactoringUIMessages.getString("ExtractFunctionInputPage_2")); //$NON-NLS-1$
		} else {
			checkBox.setText(PHPRefactoringUIMessages.getString("ExtractFunctionInputPage_1")); //$NON-NLS-1$
		}
		boolean generate = computeGeneratePHPdoc();
		setGeneratePHPdoc(generate);
		checkBox.setSelection(generate);
		checkBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setGeneratePHPdoc(((Button) e.widget).getSelection());
			}
		});
		layouter.perform(checkBox);

		int duplicates = fRefactoring.getNumberOfDuplicates();
		checkBox = new Button(result, SWT.CHECK);
		if (duplicates == 0) {
			checkBox.setText(RefactoringMessages.ExtractMethodInputPage_duplicates_none);
		} else if (duplicates == 1) {
			checkBox.setText(RefactoringMessages.ExtractMethodInputPage_duplicates_single);
		} else {
			checkBox.setText(NLS.bind(RefactoringMessages.ExtractMethodInputPage_duplicates_multi, duplicates));
		}
		boolean enabled = duplicates > 0;

		if (enabled) {
			checkBox.setSelection(fRefactoring.getReplaceDuplicates());
		} else {
			checkBox.setSelection(false);
		}
		checkBox.setEnabled(enabled);

		checkBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fRefactoring.setReplaceDuplicates(((Button) e.widget).getSelection());
			}
		});
		layouter.perform(checkBox);

		label = new Label(result, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layouter.perform(label);

		createSignaturePreview(result, layouter);

		Dialog.applyDialogFont(result);
	}

	// private String getLabel(ASTNode node) {
	// // if (node instanceof AbstractTypeDeclaration) {
	// // return ((AbstractTypeDeclaration)node).getName().getIdentifier();
	// // } else if (node instanceof AnonymousClassDeclaration) {
	// // if (node.getLocationInParent() ==
	// ClassInstanceCreation.ANONYMOUS_CLASS_DECLARATION_PROPERTY) {
	// // ClassInstanceCreation creation=
	// (ClassInstanceCreation)node.getParent();
	// // return Messages.format(
	// // RefactoringMessages.ExtractMethodInputPage_anonymous_type_label,
	// //
	// BasicElementLabels.getJavaElementName(ASTNodes.asString(creation.getType())));
	// // } else if (node.getLocationInParent() ==
	// EnumConstantDeclaration.ANONYMOUS_CLASS_DECLARATION_PROPERTY) {
	// // EnumConstantDeclaration decl=
	// (EnumConstantDeclaration)node.getParent();
	// // return decl.getName().getIdentifier();
	// // }
	// // }
	// return "UNKNOWN"; //$NON-NLS-1$
	// }

	private Text createTextInputField(Composite parent, int style) {
		Text result = new Text(parent, style);
		result.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				textModified(getText());
			}
		});
		TextFieldNavigationHandler.install(result);
		return result;
	}

	private String getText() {
		if (fTextField == null) {
			return null;
		}
		return fTextField.getText();
	}

	private String getLabelText() {
		return RefactoringMessages.ExtractMethodInputPage_label_text;
	}

	private void setVisibility(Integer visibility) {
		fRefactoring.setVisibility(visibility.intValue());
		updatePreview(getText());
	}

	private boolean computeGeneratePHPdoc() {
		boolean result = fRefactoring.getGeneratePHPdoc();
		if (result) {
			return result;
		}
		return fSettings.getBoolean(GENERATE_PHPDOC);
	}

	private void setGeneratePHPdoc(boolean value) {
		fSettings.put(GENERATE_PHPDOC, value);
		fRefactoring.setGeneratePHPdoc(value);
	}

	private void createSignaturePreview(Composite composite, RowLayouter layouter) {
		Label previewLabel = new Label(composite, SWT.NONE);
		previewLabel.setText(RefactoringMessages.ExtractMethodInputPage_signature_preview);
		layouter.perform(previewLabel);

		// IPreferenceStore store=
		// RefactoringUIPlugin.getDefault().getPreferenceStore();
		fSignaturePreview = new PHPSourceViewer(composite, SWT.READ_ONLY | SWT.V_SCROLL | SWT.WRAP /* | SWT.BORDER */);
		// fSignaturePreview.configure(new
		// JavaSourceViewerConfiguration(JavaPlugin.getDefault().getJavaTextTools().getColorManager(),
		// store, null, null));
		// fSignaturePreview.getTextWidget().setFont(JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT));
		fSignaturePreview.getTextWidget().setBackground(composite.getBackground());
		fSignaturePreview.setText(fSignaturePreviewDocument);
		fSignaturePreview.setEditable(false);

		// Layouting problems with wrapped text: see
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=9866
		Control signaturePreviewControl = fSignaturePreview.getTextWidget();

		fSignaturePreview.setLayout(new GridLayout());
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		fSignaturePreview.setLayoutData(gd);

		PixelConverter pixelConverter = new PixelConverter(signaturePreviewControl);
		GridData gdata = new GridData(GridData.FILL_BOTH);
		gdata.widthHint = pixelConverter.convertWidthInCharsToPixels(50);
		gdata.heightHint = pixelConverter.convertHeightInCharsToPixels(2);
		signaturePreviewControl.setLayoutData(gdata);
		layouter.perform(signaturePreviewControl);
	}

	private void updatePreview(String text) {
		if (fSignaturePreview == null) {
			return;
		}

		if (text.length() == 0) {
			text = "someMethodName"; //$NON-NLS-1$
		}

		int top = fSignaturePreview.getTextWidget().getTopPixel();
		String signature;
		try {
			signature = fRefactoring.getSignature(text);
		} catch (IllegalArgumentException e) {
			signature = ""; //$NON-NLS-1$
		}
		fSignaturePreview.setText(signature);
		fSignaturePreview.getTextWidget().setTopPixel(top);
	}

	private void loadSettings() {
		fSettings = getDialogSettings().getSection(ExtractFunctionWizard.DIALOG_SETTING_SECTION);
		if (fSettings == null) {
			fSettings = getDialogSettings().addNewSection(ExtractFunctionWizard.DIALOG_SETTING_SECTION);
			fSettings.put(GENERATE_PHPDOC, true);
		}
	}

	// ---- Input validation
	// ------------------------------------------------------

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			if (fFirstTime) {
				fFirstTime = false;
				setPageComplete(false);
				updatePreview(getText());
				fTextField.setFocus();
			} else {
				setPageComplete(validatePage(true));
			}
		}
		super.setVisible(visible);
	}

	private void textModified(String text) {
		fRefactoring.setMethodName(text);
		RefactoringStatus status = validatePage(true);
		if (!status.hasFatalError()) {
			updatePreview(text);
		} else {
			fSignaturePreviewDocument = ""; //$NON-NLS-1$
		}
		setPageComplete(status);
	}

	private void parameterModified() {
		updatePreview(getText());
		setPageComplete(validatePage(false));
	}

	private RefactoringStatus validatePage(boolean text) {
		RefactoringStatus result = new RefactoringStatus();
		if (text) {
			result.merge(validateMethodName());
			result.merge(validateParameters());
		} else {
			result.merge(validateParameters());
			result.merge(validateMethodName());
		}
		return result;
	}

	private RefactoringStatus validateMethodName() {
		RefactoringStatus result = new RefactoringStatus();
		String text = getText();
		if ("".equals(text)) { //$NON-NLS-1$
			result.addFatalError(RefactoringMessages.ExtractMethodInputPage_validation_emptyMethodName);
			return result;
		}
		result.merge(fRefactoring.checkFunctionName());
		return result;
	}

	private RefactoringStatus validateParameters() {
		RefactoringStatus result = new RefactoringStatus();
		List<?> parameters = fRefactoring.getParameterInfos();
		for (Iterator<?> iter = parameters.iterator(); iter.hasNext();) {
			ParameterInfo info = (ParameterInfo) iter.next();
			if ("".equals(info.getNewName())) { //$NON-NLS-1$
				result.addFatalError(RefactoringMessages.ExtractMethodInputPage_validation_emptyParameterName);
				return result;
			}
		}
		result.merge(fRefactoring.checkParameterNames());
		return result;
	}
}