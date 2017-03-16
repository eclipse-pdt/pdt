/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.search.*;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.rename.logic.RenameFunction;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

/**
 * Rename for function name 1. get the identifier 2. initial check � if this
 * identifier can be renamed 3. get the new name 4. check if the variable is
 * already defined 5. go over all the occurrences and create the change,
 * 
 * @author Roy, 2007
 */
public class RenameFunctionProcessor extends AbstractRenameProcessor<IFile>
		implements ITextUpdating {

	private static final String RENAME_IS_PROCESSING = PhpRefactoringCoreMessages
			.getString("RenameFunctionProcessor.0"); //$NON-NLS-1$
	private static final String CREATING_MODIFICATIONS_LABEL = PhpRefactoringCoreMessages
			.getString("RenameFunctionProcessor.1"); //$NON-NLS-1$
	private static final String FUNCTION_IS_USED = PhpRefactoringCoreMessages
			.getString("RenameFunctionProcessor.2"); //$NON-NLS-1$
	private static final String ID_RENAME_FUNCTION = "php.refactoring.ui.rename.function"; //$NON-NLS-1$
	protected static final String ATTRIBUTE_TEXTUAL_MATCHES = "textual"; //$NON-NLS-1$
	public static final String RENAME_FUNCTION_PROCESSOR_NAME = PhpRefactoringCoreMessages
			.getString("RenameFunctionProcessor.3"); //$NON-NLS-1$

	/**
	 * The original identifier node we want to rename
	 */
	private final ASTNode identifier;

	/**
	 * holds wether or not we want to change also the inlined text
	 */
	private boolean isUpdateTextualMatches;

	public RenameFunctionProcessor(IFile operatedFile, ASTNode locateNode) {
		super(operatedFile); //$NON-NLS-1$
		this.identifier = locateNode;
	}

	/**
	 * Derive the change
	 */
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange rootChange = new CompositeChange(
				PhpRefactoringCoreMessages
						.getString("RenameFunctionProcessor.4")); //$NON-NLS-1$
		rootChange.markAsSynthetic();
		try {
			pm.beginTask(RenameFunctionProcessor.RENAME_IS_PROCESSING,
					participantFiles.size());
			pm.setTaskName(RenameFunctionProcessor.CREATING_MODIFICATIONS_LABEL);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			// get target parameters
			final String newElementName = getNewElementName();

			// go over the files and check for global variable usage
			for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
				final IFile file = entry.getKey();
				final Program program = entry.getValue();
				final RenameFunction rename = new RenameFunction(file,
						getCurrentElementName(), newElementName,
						getUpdateTextualMatches());

				// aggregate the changes identifiers
				program.accept(rename);

				if (pm.isCanceled())
					throw new OperationCanceledException();

				pm.worked(1);

				if (rename.hasChanges()) {
					TextFileChange change = acquireChange(file, program);
					rename.updateChange(change);
					rootChange.add(change);
				}
			}

			return rootChange;

		} finally {
			pm.done();
		}
	}

	@Override
	protected void collectReferences(Program program, IProgressMonitor pm) {
		final ArrayList<IResource> list = new ArrayList<IResource>();

		IScriptProject project = this.identifier.getProgramRoot()
				.getSourceModule().getScriptProject();

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project,
				getSearchFlags(false));

		ASTNode node = identifier;
		if (identifier instanceof Identifier) {
			node = identifier.getParent();
		}

		SearchPattern pattern = null;

		int matchMode = SearchPattern.R_EXACT_MATCH
				| SearchPattern.R_ERASURE_MATCH;

		if (isFunctionElement(node)) {
			pattern = SearchPattern.createPattern(getCurrentElementName(),
					IDLTKSearchConstants.METHOD,
					IDLTKSearchConstants.ALL_OCCURRENCES, matchMode,
					PHPLanguageToolkit.getDefault());
			SearchEngine engine = new SearchEngine();
			try {
				engine.search(pattern, new SearchParticipant[] { SearchEngine
						.getDefaultSearchParticipant() }, scope,
						new SearchRequestor() {
							@Override
							public void acceptSearchMatch(SearchMatch match)
									throws CoreException {

								IModelElement element = (IModelElement) match
										.getElement();
								list.add(element.getResource());
							}
						}, new NullProgressMonitor());
			} catch (CoreException e) {
			}
		}

		for (Iterator<IResource> it = list.iterator(); it.hasNext();) {
			IResource file = it.next();
			if (file instanceof IFile) {
				try {
					participantFiles.put((IFile) file,
							RefactoringUtility.getProgramForFile((IFile) file));
				} catch (Exception e) {
				}
			}
		}
	}

	private boolean isFunctionElement(ASTNode node) {
		return node instanceof FunctionDeclaration
				|| node instanceof FunctionInvocation
				|| node instanceof FunctionName;
	}

	public Object[] getElements() {
		return new Object[] { identifier };
	}

	public String getIdentifier() {
		return ID_RENAME_FUNCTION;
	}

	public String getProcessorName() {
		return RENAME_FUNCTION_PROCESSOR_NAME;
	}

	public Object getNewElement() {
		return getNewElementName();
	}

	public String getCurrentElementName() {
		if (identifier instanceof Identifier) {
			return ((Identifier) identifier).getName();
		}

		if (identifier instanceof FunctionDeclaration) {
			return ((FunctionDeclaration) identifier).getFunctionName()
					.getName();
		}
		return identifier.toString();
	}

	public boolean canEnableTextUpdating() {
		return true;
	}

	public String getCurrentElementQualifier() {
		return getCurrentElementName();
	}

	public boolean getUpdateTextualMatches() {
		return isUpdateTextualMatches;
	}

	public void setUpdateTextualMatches(boolean update) {
		isUpdateTextualMatches = update;
	}

	public RefactoringStatus getRefactoringStatus(IFile key, Program program) {
		if (PhpElementConciliator.functionAlreadyExists(program,
				getNewElementName())) {
			final String message = MessageFormat.format(
					RenameFunctionProcessor.FUNCTION_IS_USED,
					new Object[] { key.getName() });
			return RefactoringStatus.createWarningStatus(message);
		}
		return null;

	}

}
