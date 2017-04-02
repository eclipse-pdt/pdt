/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.saveparticipant;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.core.manipulation.SourceModuleChange;
import org.eclipse.dltk.ui.editor.saveparticipant.IPostSaveListener;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.saveparticipant.SaveParticipantDescriptor;

public class CodeFormatSaveParticipant implements IPostSaveListener {

	@Override
	public String getName() {
		return "Format source code"; //$NON-NLS-1$
	}

	@Override
	public String getId() {
		return ID;
	}

	public static final String ID = "CodeFormat"; //$NON-NLS-1$

	/**
	 * Preference prefix that is appended to the id of
	 * {@link SaveParticipantDescriptor save participants}.
	 * 
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String EDITOR_SAVE_PARTICIPANT_PREFIX = "editor_save_participant_"; //$NON-NLS-1$

	private boolean formatOnSaveEnabled = false;

	/*
	 * Gets the preferences set for this editor in the Save Actions section
	 */
	public void updateSaveActionsState(@Nullable IProject project) {
		PreferencesSupport prefSupport = new PreferencesSupport(PHPUiPlugin.ID);
		String formatOnSavePref = prefSupport.getPreferencesValue(PreferenceConstants.FORMAT_ON_SAVE, null, project);

		formatOnSaveEnabled = Boolean.parseBoolean(formatOnSavePref);
	}

	@Override
	public boolean isEnabled(ISourceModule compilationUnit) {
		return new PreferencesLookupDelegate(compilationUnit.getScriptProject().getProject()).getBoolean(PHPUiPlugin.ID,
				EDITOR_SAVE_PARTICIPANT_PREFIX + ID);
	}

	@Override
	public boolean needsChangedRegions(ISourceModule compilationUnit) throws CoreException {
		return false;
	}

	@Override
	public void saved(ISourceModule compilationUnit, IRegion[] changedRegions, IProgressMonitor monitor)
			throws CoreException {
		IScriptProject project = compilationUnit.getScriptProject();
		updateSaveActionsState(project != null ? project.getProject() : null);

		if (!formatOnSaveEnabled) {
			return;
		}

		IContentFormatter formatter = PHPUiPlugin.getDefault().getActiveFormatter();

		if (formatter instanceof IFormatterProcessorFactory && project != null) {
			try {
				IDocument document = new Document(compilationUnit.getSource());
				ICodeFormattingProcessor processor = ((IFormatterProcessorFactory) formatter)
						.getCodeFormattingProcessor(document, ProjectOptions.getPHPVersion(project),
								ProjectOptions.useShortTags(project), new Region(0, document.getLength()));
				MultiTextEdit edits = processor.getTextEdits();
				if (edits.hasChildren()) {
					final SourceModuleChange change = new SourceModuleChange(
							"Format " + compilationUnit.getElementName(), compilationUnit); //$NON-NLS-1$
					change.setSaveMode(TextFileChange.LEAVE_DIRTY);
					change.setEdit(edits);
					change.perform(monitor);
				}
			} catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, e.toString(), e));
			}
		}
	}

}
