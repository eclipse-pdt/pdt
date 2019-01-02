/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.saveparticipant;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.ui.editor.saveparticipant.IPostSaveListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.OrganizeUseStatementsAction;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.saveparticipant.SaveParticipantDescriptor;

public class OrganizeUseStatmentsSaveParticipant implements IPostSaveListener {

	@Override
	public String getName() {
		return "Organize use statements"; //$NON-NLS-1$
	}

	@Override
	public String getId() {
		return ID;
	}

	public static final String ID = "OrganizeUseStatements"; //$NON-NLS-1$

	/**
	 * Preference prefix that is appended to the id of
	 * {@link SaveParticipantDescriptor save participants}.
	 * 
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String EDITOR_SAVE_PARTICIPANT_PREFIX = "editor_save_participant_"; //$NON-NLS-1$

	private boolean organizeOnSaveEnabled = false;

	/*
	 * Gets the preferences set for this editor in the Save Actions section
	 */
	public void updateSaveActionsState(@Nullable IProject project) {
		PreferencesSupport prefSupport = new PreferencesSupport(PHPUiPlugin.ID);
		String formatOnSavePref = prefSupport.getPreferencesValue(PreferenceConstants.ORGANIZE_ON_SAVE, null, project);

		organizeOnSaveEnabled = Boolean.parseBoolean(formatOnSavePref);
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

		if (!organizeOnSaveEnabled) {
			return;
		}

		IEditorInput input = new FileEditorInput((IFile) compilationUnit.getResource());
		IWorkbenchPage p = PHPUiPlugin.getActivePage();
		if (p == null) {
			return;
		}
		IEditorPart part = p.findEditor(input);
		if (part instanceof PHPStructuredEditor) {
			try {
				ASTParser parser = ASTParser.newParser(compilationUnit);
				Program astRoot = parser.createAST(monitor);

				OrganizeUseStatementsAction action = new OrganizeUseStatementsAction(part);
				action.run(compilationUnit, astRoot);
			} catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, e.toString(), e));
			}
		}
	}

}
