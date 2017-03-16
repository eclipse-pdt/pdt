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
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.*;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.ltk.internal.core.refactoring.resource.ResourceProcessors;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.rename.logic.RenameTrait;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

/**
 * Description:
 * 
 * @author Roy, 2007
 */
public class RenameTraitProcessor extends AbstractRenameProcessor<IFile>
		implements ITextUpdating {

	public static final String RENAME_IS_PROCESSING = PhpRefactoringCoreMessages
			.getString("RenameClassNameProcessor.0"); //$NON-NLS-1$
	public static final String CREATING_MODIFICATIONS_LABEL = PhpRefactoringCoreMessages
			.getString("RenameClassNameProcessor.1"); //$NON-NLS-1$
	private static final String CLASS_IS_USED = PhpRefactoringCoreMessages
			.getString("RenameClassNameProcessor.2"); //$NON-NLS-1$
	private static final String ID_RENAME_CLASS = "php.refactoring.ui.rename.className"; //$NON-NLS-1$
	protected static final String ATTRIBUTE_TEXTUAL_MATCHES = "textual"; //$NON-NLS-1$
	public static final String RENAME_CLASS_PROCESSOR_NAME = PhpRefactoringCoreMessages
			.getString("RenameTraitNameProcessor.3"); //$NON-NLS-1$

	/**
	 * The original identifier node we want to rename
	 */
	private final ASTNode identifier;

	/**
	 * holds whether or not we want to change also the inlined text
	 */
	private boolean isUpdateTextualMatches;
	private IType[] types;

	public RenameTraitProcessor(IFile operatedFile, ASTNode locateNode) {
		super(operatedFile); //$NON-NLS-1$
		this.identifier = locateNode;
		IModelElement[] elements = null;
		try {
			elements = identifier.getProgramRoot().getSourceModule()
					.codeSelect(getOffset(), 0);
		} catch (ModelException e) {
			elements = new IModelElement[0];
		}
		types = getTypes(elements);
	}

	private IType[] getTypes(IModelElement[] elements) {
		if (elements == null) {
			return new IType[0];
		}
		List<IType> types = new ArrayList<IType>();
		for (int i = 0; i < elements.length; i++) {
			try {
				if ((elements[i] instanceof IType)
						|| PHPFlags.isTrait(((IType) elements[i]).getFlags())) {
					types.add((IType) elements[i]);
				}
			} catch (ModelException e) {
			}
		}
		return types.toArray(new IType[types.size()]);
	}

	/**
	 * Derive the change
	 */
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		CompositeChange rootChange = new CompositeChange(
				PhpRefactoringCoreMessages
						.getString("RenameClassNameProcessor.4")); //$NON-NLS-1$
		rootChange.markAsSynthetic();

		try {
			pm.beginTask(RenameTraitProcessor.RENAME_IS_PROCESSING,
					participantFiles.size());
			pm.setTaskName(RenameTraitProcessor.CREATING_MODIFICATIONS_LABEL);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			// get target parameters
			final String newElementName = getNewElementName();

			// go over the files and check for global variable usage
			for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
				final IFile file = entry.getKey();
				final Program program = entry.getValue();
				final RenameTrait rename = new RenameTrait(file, identifier,
						getCurrentElementName(), newElementName,
						getUpdateTextualMatches(), types);

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

	public Object[] getElements() {
		return new Object[] { identifier };
	}

	public String getIdentifier() {
		return ID_RENAME_CLASS;
	}

	public String getProcessorName() {
		return RENAME_CLASS_PROCESSOR_NAME;
	}

	public Object getNewElement() {
		return getNewElementName();
	}

	private int getOffset() {
		if (identifier instanceof Identifier) {
			return ((Identifier) identifier).getStart();
		}

		if (identifier instanceof ClassDeclaration) {
			return ((ClassDeclaration) identifier).getName().getStart();
		}

		if (identifier instanceof InterfaceDeclaration) {
			return ((InterfaceDeclaration) identifier).getName().getStart();
		}

		return identifier.getStart();
	}

	public String getCurrentElementName() {
		if (identifier instanceof Identifier) {
			return ((Identifier) identifier).getName();
		}

		if (identifier instanceof ClassDeclaration) {
			return ((ClassDeclaration) identifier).getName().getName();
		}

		if (identifier instanceof InterfaceDeclaration) {
			return ((InterfaceDeclaration) identifier).getName().getName();
		}

		return identifier.toString();
	}

	@Override
	protected void collectReferences(Program program, IProgressMonitor pm) {
		final HashSet<IResource> list = new HashSet<IResource>();

		IScriptProject project = this.identifier.getProgramRoot()
				.getSourceModule().getScriptProject();

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project,
				getSearchFlags(false));

		SearchPattern pattern = null;
		int matchMode = SearchPattern.R_EXACT_MATCH
				| SearchPattern.R_ERASURE_MATCH;

		SearchEngine engine = new SearchEngine();

		pattern = SearchPattern.createPattern(getCurrentElementName(),
				IDLTKSearchConstants.TYPE,
				IDLTKSearchConstants.ALL_OCCURRENCES, matchMode,
				PHPLanguageToolkit.getDefault());
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
		if (PhpElementConciliator.classNameAlreadyExists(program,
				getNewElementName())) {
			final String message = MessageFormat.format(
					RenameTraitProcessor.CLASS_IS_USED,
					new Object[] { key.getName() });
			return RefactoringStatus.createWarningStatus(message);
		}
		return null;

	}

	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		String[] affectedNatures = ResourceProcessors
				.computeAffectedNatures(resource);
		RenameArguments fRenameArguments = new RenameArguments(
				getNewElementName(), false);
		LinkedList<RefactoringParticipant> participants = new LinkedList<RefactoringParticipant>(
				Arrays.asList(ParticipantManager.loadRenameParticipants(status,
						this, identifier, fRenameArguments, null,
						affectedNatures, sharedParticipants)));
		for (IType type : types) {
			participants.addAll(Arrays.asList(ParticipantManager
					.loadRenameParticipants(status, this, type,
							fRenameArguments, null, affectedNatures,
							sharedParticipants)));
		}

		return participants.toArray(new RefactoringParticipant[participants
				.size()]);
	}
}
