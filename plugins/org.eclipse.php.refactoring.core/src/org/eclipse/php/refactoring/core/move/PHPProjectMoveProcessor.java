/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.move;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.*;
import org.eclipse.ltk.internal.core.refactoring.resource.ResourceProcessors;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.changes.PHPProjectMoveChange;

/**
 * The processor is the class responsible for most of the refactoring phases.
 * 
 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor
 * @author Eden K., 2007
 * 
 */
public class PHPProjectMoveProcessor extends MoveProcessor {

	private String fResourceDestination;
	private IProject fProject;
	private boolean fUpdateReferences;

	// private MoveDelegate fDelegate;

	/**
	 * basic constructor for move processor
	 * 
	 * @param resources
	 */
	public PHPProjectMoveProcessor(IProject project) {
		super();
		fProject = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor,
	 * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return new PHPProjectMoveChange(URIUtil.toURI(fResourceDestination),
				fProject.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getElements()
	 */
	public Object[] getElements() {
		return new Object[] { fProject };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getIdentifier()
	 */
	public String getIdentifier() {
		return getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getProcessorName()
	 */
	public String getProcessorName() {
		return PhpRefactoringCoreMessages.getString("PHPMoveProcessor.0"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * isApplicable()
	 */
	public boolean isApplicable() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * loadParticipants(org.eclipse.ltk.core.refactoring.RefactoringStatus,
	 * org.eclipse.ltk.core.refactoring.participants.SharableParticipants)
	 */
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) {

		try {
			String[] affectedNatures = ResourceProcessors
					.computeAffectedNatures(fProject);
			MoveArguments arguments = new MoveArguments(fResourceDestination,
					getUpdateReferences());
			List list = new ArrayList();
			list.add(fProject);
			return ParticipantManager.loadMoveParticipants(status, this, list,
					arguments, null, affectedNatures, sharedParticipants);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return new RefactoringParticipant[0];
	}

	/**
	 * Sets the destination selected by the user indicating where the selected
	 * resources should be moved to
	 * 
	 * @param destination
	 * @return the status
	 */
	public RefactoringStatus setDestination(String destination) {
		fResourceDestination = destination;
		return new RefactoringStatus();
	}

	/**
	 * Returns the destination selected by the user
	 * 
	 * @return IResource
	 */
	public String getDestination() {
		return fResourceDestination;
	}

	public void setUpdateReferences(boolean update) {
		fUpdateReferences = update;
	}

	/**
	 * @return boolean indicating the user selection of the update references
	 *         checkbox
	 */
	public boolean getUpdateReferences() {
		return fUpdateReferences;
	}

}
