/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
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
package org.eclipse.php.refactoring.core.changes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.util.collections.BucketMap;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;

public class RenameBreackpointChange extends Change {
	private IPath fDest;
	private IPath fSource;
	private String fName;
	private String fNewName;
	private BucketMap<IResource, IBreakpoint> fBreakpoints;
	private Map<IBreakpoint, Map<String, Object>> fBreakpointAttributes;

	/**
	 * The constructor gets also a new name in case the move it's actually a rename
	 * operation
	 * 
	 * @param source
	 * @param dest
	 * @param resName
	 * @param newName
	 * @param fBreakpointAttributes3
	 * @param fBreakpointAttributes2
	 */
	public RenameBreackpointChange(IPath source, IPath dest, String resName, String newName,
			BucketMap<IResource, IBreakpoint> breakpoints, Map<IBreakpoint, Map<String, Object>> breakpointAttributes) {
		fSource = source;
		fDest = dest;
		fName = resName;
		fNewName = newName;
		this.fBreakpoints = breakpoints;
		this.fBreakpointAttributes = breakpointAttributes;
	}

	@Override
	public Object getModifiedElement() {
		return null;
	}

	@Override
	public String getName() {
		return NLS.bind(Messages.RenameBreackpointChange_0, fName);
	}

	@Override
	public void initializeValidationData(IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {

		// Breakpoint change is not undoable;
		if (fBreakpoints == null || fBreakpointAttributes == null) {
			return new RenameBreackpointChange(fDest, fSource, fNewName, fName, null, null);
		}
		// Add the fBreakpoints that got removed after the rename action.

		// WorkspaceJob createMarker = new WorkspaceJob("Creating markers") {
		// @Override
		// public IStatus runInWorkspace(IProgressMonitor monitor) throws
		// CoreException {
		//
		// synchronized (this) {
		final IPath dest = fDest.append(fNewName);
		final IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();

		// if (getResource()!=null && getResource().isAccessible()) { // in case
		// the old file exists (fast undo-redo)
		// return Status.CANCEL_STATUS;
		//
		// }
		// IResource file = workspace.getRoot().findMember(dest);
		// if (file == null) {
		// return Status.CANCEL_STATUS;
		// }
		for (final IResource markerResource : fBreakpoints.getKeys()) {
			Set<IBreakpoint> breakPoints = fBreakpoints.get(markerResource);
			final Path newPath = new Path(markerResource.getFullPath().toString()
					.replaceFirst(fSource.append(fName).toString(), dest.toString()));

			final IResource newMarkerResource = ResourcesPlugin.getWorkspace().getRoot().findMember(newPath);

			for (final IBreakpoint breakpoint : breakPoints) {
				final Map<String, Object> oldAttributesMap = fBreakpointAttributes.get(breakpoint);

				IWorkspaceRunnable wr = new IWorkspaceRunnable() {
					@Override
					public void run(IProgressMonitor monitor) throws CoreException {
						IMarker newMarker = newMarkerResource
								.createMarker("org.eclipse.php.debug.core.PHPConditionalBreakpointMarker"); //$NON-NLS-1$
						// Fix the breakpoint's tooltip string before applying
						// the old
						// attributes to the new marker.

						final Map<String, Object> newAttributesMap = new HashMap<>();

						String oldMessge = (String) oldAttributesMap.get(IMarker.MESSAGE);
						if (oldMessge != null) {
							newAttributesMap.put(IMarker.MESSAGE, oldMessge.replaceFirst(fName, dest.lastSegment()));
						}

						newAttributesMap.put(IMarker.LOCATION, newPath.toPortableString());

						newAttributesMap.put(IMarker.LINE_NUMBER, oldAttributesMap.get(IMarker.LINE_NUMBER));

						newAttributesMap.put(IBreakpoint.ENABLED, oldAttributesMap.get(IBreakpoint.ENABLED));

						newAttributesMap.put(IBreakpoint.PERSISTED, false);
						newAttributesMap.put(IBreakpoint.ID, oldAttributesMap.get(IBreakpoint.ID));
						newMarker.setAttributes(newAttributesMap);

						PHPLineBreakpoint newBreakPoint = createBreakPoint(breakpoint);

						newBreakPoint.setMarker(newMarker);
						newBreakPoint.setPersisted(breakpoint.isPersisted());
						breakpointManager.addBreakpoint(newBreakPoint);

						breakpoint.delete();
					}

				};

				try {
					ResourcesPlugin.getWorkspace().run(wr, getMarkerRule(newMarkerResource), 0, null);
				} catch (CoreException e) {
					throw new DebugException(e.getStatus());
				}
			}
		}
		// }
		// return Status.OK_STATUS;
		// }
		// };

		// createMarker.setRule(workspace.getRoot());
		// createMarker.setSystem(true);
		// createMarker.schedule(1000); // wait for UI refresh which refreshes
		// the markers

		// Breakpoint change is not undoable;
		return new RenameBreackpointChange(fDest, fSource, fNewName, fName, null, null);
	}

	protected PHPLineBreakpoint createBreakPoint(IBreakpoint breakpoint) {
		if (breakpoint instanceof PHPConditionalBreakpoint) {
			return new PHPConditionalBreakpoint();
		}
		return new PHPLineBreakpoint();
	}

	/**
	 * Returns a scheduling rule to use when modifying markers on the given
	 * resource, possibly <code>null</code>.
	 * 
	 * @param resource
	 *            a resource on which a marker will be created, modified, or deleted
	 * @return a scheduling rule to use when modifying markers on the given resource
	 *         possibly <code>null</code>
	 * @since 3.1
	 */
	protected ISchedulingRule getMarkerRule(IResource resource) {
		ISchedulingRule rule = null;
		if (resource != null) {
			IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
			rule = ruleFactory.markerRule(resource);
		}
		return rule;
	}

	private IResource getResource() {
		return ResourcesPlugin.getWorkspace().getRoot().findMember(fSource.append(fName));
	}
}
