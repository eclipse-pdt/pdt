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

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree.Node;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.changes.ProgramFileChange;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;
import org.eclipse.text.edits.MultiTextEdit;

/**
 * Description: Life cycle processor for PHP rename refactoring
 * 
 * @author Roy, 2007
 */
public abstract class AbstractRenameProcessor<R extends IResource> extends RenameProcessor implements INameUpdating {

	private static final String REFACTORING_ACTION_INTERNAL_ERROR = PhpRefactoringCoreMessages
			.getString("RenameProcessorBase.internalerror"); //$NON-NLS-1$

	private static final String IS_NOT_A_VALID_PHP_IDENTIFIER = PhpRefactoringCoreMessages
			.getString("RenameProcessorBase.0"); //$NON-NLS-1$

	private static final RefactoringParticipant[] EMPTY_REFACTORING_PARTICIPANTS = new RefactoringParticipant[0];

	protected String fNewElementName;

	protected Map<IFile, Program> participantFiles;

	/**
	 * map of sibling changes by file received from parent processor.
	 */
	// protected Map<IFile, TextFileChange> siblingChanges;

	protected final R resource;

	public AbstractRenameProcessor(R file) {
		// node can be null if the renamed element is a folder
		// assert head != null;
		assert file != null;
		this.resource = file;
	}

	/**
	 * Finds existing or create a new change for the given file
	 * 
	 * @param file
	 * @param program
	 * @return the change
	 */
	protected TextFileChange acquireChange(final IFile file, final Program program) {
		ProgramFileChange change = new ProgramFileChange(file.getName(), file, program);
		change.setEdit(new MultiTextEdit());
		change.setTextType("php"); //$NON-NLS-1$
		return change;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		boolean hasExternalDependencies = false;

		try {

			participantFiles = new HashMap<IFile, Program>();

			if (resource instanceof IFile && PHPToolkitUtil.isPhpFile((IFile) resource)) {

				IFile file = (IFile) resource;

				ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
				IProject project = file.getProject();
				PHPVersion version = ProjectOptions.getPHPVersion(project);

				ASTParser newParser = ASTParser.newParser(version, sourceModule);
				Program program = newParser.createAST(null);
				participantFiles.put(file, program);

				collectReferences(program, pm);
			}
			// }
		} catch (Exception e) {
			final String exceptionMessage = e.getMessage();
			final String formattedString = REFACTORING_ACTION_INTERNAL_ERROR
					.concat(exceptionMessage == null ? "" : exceptionMessage); //$NON-NLS-1$
			return RefactoringStatus.createFatalErrorStatus(formattedString);
		}

		if (hasExternalDependencies) {
			final String message = PhpRefactoringCoreMessages.getString("AbstractRenameProcessor.1"); //$NON-NLS-1$
			return RefactoringStatus.createWarningStatus(message);
		}

		return new RefactoringStatus();
	}

	protected void collectReferences(Program program, IProgressMonitor pm) {
		Collection<Node> references = getReferencingFiles(program);
		if (references != null) {
			for (Iterator<Node> it = references.iterator(); it.hasNext();) {
				Node node = it.next();
				IFile file = (IFile) node.getFile().getResource();
				try {
					participantFiles.put(file, RefactoringUtility.getProgramForFile(file));
				} catch (Exception e) {
				}
			}
		}

		references = getReferencedFiles(program);
		if (references != null) {
			for (Iterator<Node> it = references.iterator(); it.hasNext();) {
				Node node = it.next();
				IFile file = (IFile) node.getFile().getResource();
				try {
					participantFiles.put(file, RefactoringUtility.getProgramForFile(file));
				} catch (Exception e) {
				}
			}
		}

	}

	protected static int getSearchFlags(boolean includeInterp) {
		int flags = IDLTKSearchScope.SOURCES | IDLTKSearchScope.APPLICATION_LIBRARIES;
		if (includeInterp)
			flags |= IDLTKSearchScope.SYSTEM_LIBRARIES;
		return flags;
	}

	protected Collection<Node> getReferencedFiles(Program program) {
		ISourceModule sourceModule = program.getSourceModule();
		if (sourceModule != null) {
			ReferenceTree tree = FileNetworkUtility.buildReferencedFilesTree(sourceModule, null);

			if (tree != null && tree.getRoot() != null) {
				return tree.getRoot().getChildren();
			}
		}
		return Collections.emptyList();

	}

	protected Collection<Node> getReferencingFiles(Program program) {
		ISourceModule sourceModule = program.getSourceModule();
		if (sourceModule != null) {
			ReferenceTree tree = FileNetworkUtility.buildReferencingFilesTree(sourceModule, null);

			if (tree != null && tree.getRoot() != null) {
				return tree.getRoot().getChildren();
			}
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor,
	 * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws CoreException, OperationCanceledException {

		RefactoringStatus result = RefactoringStatus.create(Status.OK_STATUS);

		final SubProgressMonitor subProgressMonitor = new SubProgressMonitor(pm, 100);
		subProgressMonitor.beginTask(PhpRefactoringCoreMessages.getString("RenameProcessorBase.1"), //$NON-NLS-1$
				participantFiles.size());

		for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
			final IFile key = entry.getKey();
			final Program program = entry.getValue();

			RefactoringStatus status = getRefactoringStatus(key, program);
			result.merge(status);
			subProgressMonitor.worked(1);
		}
		subProgressMonitor.done();
		return result;
	}

	/**
	 * Should be overridden by clients - checks for conflicts
	 * 
	 * @param key
	 * @param program
	 * @return a factoring status given an AST representation
	 */
	public abstract RefactoringStatus getRefactoringStatus(IFile key, Program program);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.refactoring.core.rename.INameUpdating#
	 * getCurrentElementName ()
	 */
	public abstract String getCurrentElementName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.refactoring.core.rename.INameUpdating#getNewElement()
	 */
	public abstract Object getNewElement() throws CoreException;

	public void setNewElementName(String newName) {
		Assert.isNotNull(newName);
		fNewElementName = newName;
	}

	public String getNewElementName() {
		return fNewElementName;
	}

	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status, SharableParticipants sharedParticipants)
			throws CoreException {
		return AbstractRenameProcessor.EMPTY_REFACTORING_PARTICIPANTS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.refactoring.core.rename.INameUpdating#checkNewElementName
	 * (java.lang.String)
	 */
	public RefactoringStatus checkNewElementName(String newName) throws CoreException {
		if (!isValidIdentifier(newName)) {
			return getFatalError(newName);
		}

		return new RefactoringStatus();
	}

	/**
	 * @param newName
	 * @return true of the given string is valid PHP identifier
	 */
	public static final boolean isValidIdentifier(String newName) {
		if (newName == null || newName.length() == 0
				|| !Character.isLetter(newName.charAt(0)) && newName.charAt(0) != '_') {
			return false;
		}

		final int length = newName.length();
		for (int i = 1; i < length; i++) {
			if (!Character.isJavaIdentifierPart(newName.charAt(i))) {
				return false;
			}
			if (newName.charAt(i) == '$') {
				// Seva: in addition to java rules, PHP doesn't allow dollar
				// signs inside element names
				return false;
			}
		}
		return true;
	}

	/**
	 * @param newName
	 * @return
	 */
	protected static final RefactoringStatus getFatalError(String newName) {
		return RefactoringStatus
				.createFatalErrorStatus(newName + AbstractRenameProcessor.IS_NOT_A_VALID_PHP_IDENTIFIER);
	}

	/**
	 * @param file
	 * @return the program node for a given file
	 */
	public Program getProgram(IFile file) {
		return participantFiles.get(file);
	}

	/**
	 * Check the validity of the input source.
	 */
	@Override
	public boolean isApplicable() throws CoreException {
		if (resource == null)
			return false;

		if (!resource.isAccessible())
			return false;

		ResourceAttributes attributes = resource.getResourceAttributes();
		if (attributes == null)
			return false;

		return !resource.getResourceAttributes().isReadOnly();
	}
}
