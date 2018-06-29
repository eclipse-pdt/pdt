/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.fieldassist.*;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.refactoring.core.extract.variable.ExtractVariableRefactoring;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ExtractVariableWizard extends RefactoringWizard {

	/* package */static final String DIALOG_SETTING_SECTION = "ExtractVariableWizard"; //$NON-NLS-1$

	public ExtractVariableWizard(ExtractVariableRefactoring ref) {
		super(ref, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(PHPRefactoringUIMessages.getString("ExtractVariableWizard.0")); //$NON-NLS-1$
	}

	/*
	 * non java-doc
	 * 
	 * @see RefactoringWizard#addUserInputPages
	 */
	@Override
	protected void addUserInputPages() {
		addPage(new ExtractTempInputPage(getExtractVariableRefactoring().guessTempNames()));
	}

	private ExtractVariableRefactoring getExtractVariableRefactoring() {
		return (ExtractVariableRefactoring) getRefactoring();
	}

	private static class ExtractTempInputPage extends TextInputWizardPage {

		private static final String COM_ZEND_PHP_REFACTORING_UI_ASSI_DECORATION = "org.eclipse.php.refactoring.ui.AssiDecoration"; //$NON-NLS-1$
		private static final String TRIGGER_KEY = "Ctrl+Space"; //$NON-NLS-1$
		private static final String DECLARE_FINAL = "declareFinal"; //$NON-NLS-1$
		private static final String REPLACE_ALL = "replaceOccurrences"; //$NON-NLS-1$

		private final boolean fInitialValid;
		private static final String DESCRIPTION = PHPRefactoringUIMessages.getString("ExtractVariableWizard.1"); //$NON-NLS-1$
		private String[] fTempNameProposals;
		private IDialogSettings fSettings;

		public ExtractTempInputPage(String[] tempNameProposals) {
			super(DESCRIPTION, true, tempNameProposals.length == 0 ? "" : tempNameProposals[0]); //$NON-NLS-1$
			Assert.isNotNull(tempNameProposals);
			fTempNameProposals = tempNameProposals;
			fInitialValid = tempNameProposals.length > 0;
		}

		@Override
		public void createControl(Composite parent) {
			loadSettings();
			Composite result = new Composite(parent, SWT.NONE);
			setControl(result);
			GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			layout.verticalSpacing = 8;
			result.setLayout(layout);
			RowLayouter layouter = new RowLayouter(2);

			Label label = new Label(result, SWT.NONE);
			label.setText(PHPRefactoringUIMessages.getString("ExtractVariableWizard.2")); //$NON-NLS-1$

			Text text = createTextInputField(result);
			text.selectAll();
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			creatContentAssit(text);

			installContentProposalAdapter(text, new TextContentAdapter());

			layouter.perform(label, text, 1);

			addReplaceAllCheckbox(result, layouter);

			validateTextField(text.getText());

			Dialog.applyDialogFont(result);
		}

		private void creatContentAssit(Text text) {
			ControlDecoration cd = new ControlDecoration(text, SWT.LEFT | SWT.TOP);

			FieldDecoration dec = getCueDecoration();
			cd.setImage(dec.getImage());
			cd.setDescriptionText(dec.getDescription());
			cd.setShowOnlyOnFocus(false);
			cd.show();
		}

		FieldDecoration getCueDecoration() {
			// We use our own decoration which is based on the JFace version.
			FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
			FieldDecoration dec = registry.getFieldDecoration(COM_ZEND_PHP_REFACTORING_UI_ASSI_DECORATION);
			if (dec == null) {
				// Get the standard one. We use its image and our own customized
				// text.
				FieldDecoration standardDecoration = registry
						.getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);
				registry.registerFieldDecoration(COM_ZEND_PHP_REFACTORING_UI_ASSI_DECORATION,
						NLS.bind(PHPRefactoringUIMessages.getString("ExtractVariableWizard.6"), TRIGGER_KEY), //$NON-NLS-1$
						standardDecoration.getImage());
				dec = registry.getFieldDecoration(COM_ZEND_PHP_REFACTORING_UI_ASSI_DECORATION);
			}
			return dec;
		}

		void installContentProposalAdapter(Control control, IControlContentAdapter contentAdapter) {
			boolean propagate = false;
			KeyStroke keyStroke;
			char[] autoActivationCharacters = null;
			int autoActivationDelay = 1000;

			try {
				keyStroke = KeyStroke.getInstance(TRIGGER_KEY);
			} catch (ParseException e) {
				keyStroke = KeyStroke.getInstance(SWT.F10);
			}

			ContentProposalAdapter adapter = new ContentProposalAdapter(control, contentAdapter,
					getContentProposalProvider(), keyStroke, autoActivationCharacters);
			adapter.setAutoActivationDelay(autoActivationDelay);
			adapter.setPropagateKeys(propagate);
			adapter.setFilterStyle(getContentAssistFilterStyle());
			adapter.setProposalAcceptanceStyle(getContentAssistAcceptance());
		}

		private int getContentAssistAcceptance() {
			return ContentProposalAdapter.PROPOSAL_REPLACE;
		}

		private int getContentAssistFilterStyle() {
			return ContentProposalAdapter.FILTER_CHARACTER;
		}

		private IContentProposalProvider getContentProposalProvider() {
			return new IContentProposalProvider() {
				@Override
				public IContentProposal[] getProposals(String contents, int position) {
					IContentProposal[] proposals = new IContentProposal[fTempNameProposals.length];
					for (int i = 0; i < fTempNameProposals.length; i++) {
						final String user = fTempNameProposals[i];
						proposals[i] = new IContentProposal() {
							@Override
							public String getContent() {
								return user;
							}

							@Override
							public String getLabel() {
								return user;
							}

							@Override
							public String getDescription() {
								return null;
							}

							@Override
							public int getCursorPosition() {
								return user.length();
							}
						};
					}
					return proposals;
				}
			};
		}

		private void loadSettings() {
			fSettings = getDialogSettings().getSection(ExtractVariableWizard.DIALOG_SETTING_SECTION);
			if (fSettings == null) {
				fSettings = getDialogSettings().addNewSection(ExtractVariableWizard.DIALOG_SETTING_SECTION);
				fSettings.put(DECLARE_FINAL, false);
				fSettings.put(REPLACE_ALL, true);
			}
			getExtractVariableRefactoring().setReplaceAllOccurrences(fSettings.getBoolean(REPLACE_ALL));
		}

		private void addReplaceAllCheckbox(Composite result, RowLayouter layouter) {
			String title = PHPRefactoringUIMessages.getString("ExtractVariableWizard.3"); //$NON-NLS-1$
			boolean defaultValue = getExtractVariableRefactoring().getReplaceAllOccurrences();
			final Button checkBox = createCheckbox(result, title, defaultValue, layouter);
			getExtractVariableRefactoring().setReplaceAllOccurrences(checkBox.getSelection());
			checkBox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					fSettings.put(REPLACE_ALL, checkBox.getSelection());
					getExtractVariableRefactoring().setReplaceAllOccurrences(checkBox.getSelection());
				}
			});
		}

		@Override
		protected void textModified(String text) {
			getExtractVariableRefactoring().setNewVariableName(text);
			super.textModified(text);
		}

		@Override
		protected RefactoringStatus validateTextField(String text) {
			return getExtractVariableRefactoring().checkNewVariableName(text);
		}

		private ExtractVariableRefactoring getExtractVariableRefactoring() {
			return (ExtractVariableRefactoring) getRefactoring();
		}

		private static Button createCheckbox(Composite parent, String title, boolean value, RowLayouter layouter) {
			Button checkBox = new Button(parent, SWT.CHECK);
			checkBox.setText(title);
			checkBox.setSelection(value);
			layouter.perform(checkBox);
			return checkBox;
		}

		/*
		 * @seeorg.eclipse.jdt.internal.ui.refactoring.TextInputWizardPage#
		 * isInitialInputValid()
		 */
		@Override
		protected boolean isInitialInputValid() {
			return fInitialValid;
		}
	}
}
