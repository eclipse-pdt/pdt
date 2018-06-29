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
package org.eclipse.php.refactoring.core.move;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.*;
import org.eclipse.ltk.internal.core.refactoring.resource.ResourceProcessors;
import org.eclipse.php.refactoring.core.PHPRefactoringCoreMessages;

/**
 * The processor is the class responsible for most of the refactoring phases.
 * 
 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor
 * @author Eden K., 2007
 * 
 */
public class PHPMoveProcessor extends MoveProcessor {

	private IContainer fResourceDestination;
	private IResource[] fSourceResources = {};
	private boolean fUpdateReferences;
	private MoveDelegate fDelegate;

	/**
	 * basic constructor for move processor
	 * 
	 * @param resources
	 */
	public PHPMoveProcessor(IResource[] resources) {
		super();
		fDelegate = new MoveDelegate(this);
		fSourceResources = resources;
	}

	/**
	 * Instantiate the processor and builds an array of Resources representing the
	 * resources selected by the user fot the move operation
	 * 
	 * @param selection
	 */
	public PHPMoveProcessor(IStructuredSelection selection) {
		this(getResources(selection));

	}

	public PHPMoveProcessor(IResource resource) {
		this(new IResource[] { resource });
	}

	public static IResource[] getResources(IStructuredSelection sel) {
		List<Object> resources = new ArrayList<>(sel.size());
		for (Iterator<?> e = sel.iterator(); e.hasNext();) {
			Object next = e.next();
			if (next instanceof IResource) {
				resources.add(next);
				continue;
			} else if (next instanceof IAdaptable) {
				Object resource = ((IAdaptable) next).getAdapter(IResource.class);
				if (resource != null) {
					resources.add(resource);
					continue;
				}
			} else if (next != null) {
				IAdapterManager adapterManager = Platform.getAdapterManager();

				ResourceMapping mapping = adapterManager.getAdapter(next, ResourceMapping.class);

				if (mapping != null) {

					ResourceTraversal[] traversals = null;
					try {
						traversals = mapping.getTraversals(ResourceMappingContext.LOCAL_CONTEXT,
								new NullProgressMonitor());
					} catch (CoreException exception) {
					}

					if (traversals != null) {
						for (int i = 0; i < traversals.length; i++) {
							IResource[] traversalResources = traversals[i].getResources();
							if (traversalResources != null) {
								for (int j = 0; j < traversalResources.length; j++) {
									resources.add(traversalResources[j]);
								} // for
							} // if
						} // for
					} // if
				} // if
			}
		}
		return resources.toArray(new IResource[resources.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws OperationCanceledException {
		return fDelegate.checkInitialConditions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor,
	 * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		return fDelegate.checkFinalConditions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {

		CompositeChange rootChange = new CompositeChange(getProcessorName());
		return fDelegate.createChange(pm, rootChange);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getElements()
	 */
	@Override
	public Object[] getElements() {
		return new Object[] { fSourceResources };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getProcessorName()
	 */
	@Override
	public String getProcessorName() {
		return PHPRefactoringCoreMessages.getString("PHPMoveProcessor.0"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * isApplicable()
	 */
	@Override
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
	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) {

		try {
			String[] affectedNatures = ResourceProcessors.computeAffectedNatures(fSourceResources);
			MoveArguments arguments = new MoveArguments(fResourceDestination, getUpdateReferences());
			List<IResource> resourceList = new ArrayList<>();
			for (IResource fResource : fSourceResources) {
				resourceList.add(fResource);
			}
			return ParticipantManager.loadMoveParticipants(status, this, resourceList, arguments, null, affectedNatures,
					sharedParticipants);
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
	public RefactoringStatus setDestination(IContainer destination) {
		// resetDestinations();
		fResourceDestination = destination;
		return fDelegate.verifyDestination(destination);
	}

	/**
	 * Returns the destination selected by the user
	 * 
	 * @return IResource
	 */
	public IContainer getDestination() {
		return fResourceDestination;
	}

	/**
	 * The current location of the resource(s) that needs to be moved
	 * 
	 * @return array of IResources with the source locations
	 */
	public IResource[] getSourceSelection() {
		return fSourceResources;
	}

	public void setUpdateReferences(boolean update) {
		fUpdateReferences = update;
	}

	/**
	 * @return boolean indicating the user selection of the update references
	 *         checkbox
	 */
	public boolean getUpdateReferences() {
		// if (!canUpdateReferences())
		// return false;
		return fUpdateReferences;
	}

	// /**
	// * Returns a map with a textual description of each change that will be
	// effectuated in each file
	// * @return
	// */
	// public Map getChangesDescriptionMap() {
	// return fDelegate.getChangesDescriptionMap();
	// }

}
