/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.ltk.internal.core.refactoring.resource.ResourceProcessors;
import org.eclipse.php.internal.core.util.collections.BucketMap;

public abstract class AbstraceRenameResourceProcessor extends
		AbstractRenameProcessor<IResource> implements IReferenceUpdating {

	BucketMap<IResource, IBreakpoint> fBreakpoints;
	HashMap<IBreakpoint, Map<String, Object>> fBreakpointAttributes;
	private RenameArguments fRenameArguments;
	/**
	 * holds wether or not we want to change also the references to the
	 * opratedFile
	 */
	boolean isUpdateReferences;

	public AbstraceRenameResourceProcessor(IResource file) {
		super(file);
	}

	@SuppressWarnings("unchecked")
	protected void collectBrakePoint() throws CoreException {
		fBreakpoints = new BucketMap<IResource, IBreakpoint>(6);
		fBreakpointAttributes = new HashMap<IBreakpoint, Map<String, Object>>(6);
		final IBreakpointManager breakpointManager = DebugPlugin.getDefault()
				.getBreakpointManager();
		IMarker[] markers = resource.findMarkers(
				IBreakpoint.LINE_BREAKPOINT_MARKER, true,
				IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			IResource markerResource = marker.getResource();
			IBreakpoint breakpoint = breakpointManager.getBreakpoint(marker);
			if (breakpoint != null) {
				fBreakpoints.add(markerResource, breakpoint);
				fBreakpointAttributes.put(breakpoint, breakpoint.getMarker()
						.getAttributes());
			}
		}
	}

	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		String[] affectedNatures = ResourceProcessors
				.computeAffectedNatures(resource);
		fRenameArguments = new RenameArguments(getNewElementName(),
				getUpdateReferences());
		return ParticipantManager.loadRenameParticipants(status, this,
				resource, fRenameArguments, null, affectedNatures,
				sharedParticipants);
	}

	public boolean getUpdateReferences() {
		return isUpdateReferences;
	}
}