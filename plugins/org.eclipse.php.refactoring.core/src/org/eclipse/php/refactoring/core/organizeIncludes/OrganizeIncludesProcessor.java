/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.php.refactoring.core.PHPRefactoringCoreMessages;

/**
 * @author seva
 */
public class OrganizeIncludesProcessor extends RefactoringProcessor {

	Collection<IFile> files;

	public OrganizeIncludesProcessor(Collection<IFile> files) {
		this.files = Collections.unmodifiableCollection(files);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor,
	 *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		return status;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		if (files.size() == 0) {
			status.addFatalError(PHPRefactoringCoreMessages.getString("OrganizeIncludesProcessor.Not_Applicable")); //$NON-NLS-1$
		}
		return status;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor monitor) throws OperationCanceledException {
		CompositeChange rootChange = new CompositeChange(
				PHPRefactoringCoreMessages.getString("OrganizeIncludesProcessor.Organizing_Includes")); //$NON-NLS-1$
		rootChange.markAsSynthetic();
		monitor.beginTask(PHPRefactoringCoreMessages.getString("OrganizeIncludesProcessor.Calculating"), files.size()); //$NON-NLS-1$
		for (IFile element : files) {
			if (monitor.isCanceled()) {
				return rootChange;
			}
			IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
			Change fileChange = createFileChange(element, subMonitor);
			if (fileChange != null) {
				rootChange.add(fileChange);
			}
		}
		return rootChange;
	}

	private Change createFileChange(IFile file, IProgressMonitor monitor) {
		OrganizeIncludesProcessorDelegate delegate = new OrganizeIncludesProcessorDelegate(file);
		if (delegate.initializeModel()) {
			Change change = delegate.createChange(monitor);
			delegate.disposeModel();
			return change;
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getElements()
	 */
	@Override
	public Object[] getElements() {
		return files.toArray();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return getClass().getName();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#getProcessorName()
	 */
	@Override
	public String getProcessorName() {
		return "Organize Includes Processor"; //$NON-NLS-1$
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#isApplicable()
	 */
	@Override
	public boolean isApplicable() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#loadParticipants(org.eclipse.ltk.core.refactoring.RefactoringStatus,
	 *      org.eclipse.ltk.core.refactoring.participants.SharableParticipants)
	 */
	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) {
		// No participants
		return new RefactoringParticipant[0];
	}

}
