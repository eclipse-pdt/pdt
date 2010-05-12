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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

public class PHPTemplatesPreferencePage extends TemplatePreferencePage {

	public PHPTemplatesPreferencePage() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
		setTemplateStore(PHPUiPlugin.getDefault().getTemplateStore());
		setContextTypeRegistry(PHPUiPlugin.getDefault()
				.getTemplateContextRegistry());
	}

	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IPHPHelpContextIds.TEMPLATES_PREFERENCES);
		getControl().notifyListeners(SWT.Help, new Event());
	}

	@Override
	protected boolean isShowFormatterSetting() {
		return false;
	}

	@Override
	protected Template editTemplate(Template template, boolean edit,
			boolean isNameModifiable) {
		EditTemplateDialog dialog = new PHPEditTemplateDialog(getShell(),
				template, edit, isNameModifiable, getContextTypeRegistry());
		if (dialog.open() == Window.OK) {
			return dialog.getTemplate();
		}
		return null;
	}

	protected static class PHPEditTemplateDialog extends EditTemplateDialog {

		public PHPEditTemplateDialog(Shell parent, Template template,
				boolean edit, boolean isNameModifiable,
				ContextTypeRegistry registry) {
			super(parent, template, edit, isNameModifiable, registry);
		}

		@Override
		protected SourceViewer createViewer(Composite parent) {
			SourceViewer viewer = new SourceViewer(parent, null, null, false,
					SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
			SourceViewerConfiguration configuration = new SourceViewerConfiguration() {
				public IContentAssistant getContentAssistant(
						ISourceViewer sourceViewer) {

					ContentAssistant assistant = new ContentAssistant();
					assistant.enableAutoActivation(true);
					assistant.enableAutoInsert(true);
					assistant.setContentAssistProcessor(getTemplateProcessor(),
							IDocument.DEFAULT_CONTENT_TYPE);
					assistant.setProposalSelectorBackground(Display
							.getCurrent().getSystemColor(
									SWT.COLOR_INFO_BACKGROUND));
					return assistant;
				}
			};
			viewer.configure(configuration);
			return viewer;
		}
	}
}
