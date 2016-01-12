/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Cache that stores references to resources that are symbolic links in OS file
 * system. This cache is intended to be used whenever a quick check is needed if
 * some resource is a symbolic link.
 * 
 * @author Bartlomiej Laczkowski
 */
public enum PHPSymbolicLinksCache {

	INSTANCE;

	private final class StartupJob extends Job {

		public StartupJob() {
			super(CoreMessages.getString("PHPSymbolicLinksCache_Initializing_symbolic_links_cache")); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			initialize();
			return Status.OK_STATUS;
		}

	}

	private final class UpdaterJob extends Job {

		static final int ADDITION = 1;
		static final int REMOVAL = 2;

		private int kind;
		private List<IResource> resources;

		public UpdaterJob() {
			super(CoreMessages.getString("PHPSymbolicLinksCache_Updating_symbolic_links_cache")); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			switch (kind) {
			case ADDITION: {
				for (IResource resource : resources) {
					ResourceAttributes attributes = resource.getResourceAttributes();
					if (attributes != null && attributes.isSymbolicLink()) {
						addResource(resource);
					}
				}
				break;
			}
			case REMOVAL: {
				for (IResource resource : resources) {
					if (isSymbolicLink(resource)) {
						removeResource(resource);
					}
				}
				break;
			}
			default:
				break;
			}
			return Status.OK_STATUS;
		}

		void perform(int kind, List<IResource> resources) {
			this.kind = kind;
			this.resources = resources;
			schedule();
		}

	}

	private final class ResourceListener implements IResourceChangeListener {

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			final List<IResource> added = new ArrayList<IResource>();
			final List<IResource> removed = new ArrayList<IResource>();
			try {
				delta.accept(new IResourceDeltaVisitor() {
					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						IResource resource = delta.getResource();
						if (resource != null) {
							if (delta.getKind() == IResourceDelta.ADDED) {
								added.add(resource);
							} else if (delta.getKind() == IResourceDelta.REMOVED) {
								removed.add(resource);
							}
						}
						return true;
					}
				}, IResourceDelta.ADDED | IResourceDelta.REMOVED);
				if (!added.isEmpty()) {
					// Update in a separate job
					(new UpdaterJob()).perform(UpdaterJob.ADDITION, added);
				}
				if (!removed.isEmpty()) {
					// Update in a separate job
					(new UpdaterJob()).perform(UpdaterJob.REMOVAL, removed);
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}

		}

	}

	private final Set<IResource> cache = new HashSet<IResource>();

	void startup() {
		// Perform initialization in a separate job.
		(new StartupJob()).schedule();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new ResourceListener(),
				IResourceChangeEvent.POST_CHANGE);
	}

	private synchronized void initialize() {
		try {
			ResourcesPlugin.getWorkspace().getRoot().accept(new IResourceVisitor() {
				@Override
				public boolean visit(IResource resource) throws CoreException {
					ResourceAttributes attributes = resource.getResourceAttributes();
					if (attributes != null && attributes.isSymbolicLink()) {
						cache.add(resource);
					}
					return true;
				}
			});
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	private synchronized void addResource(IResource resource) {
		cache.add(resource);
	}

	private synchronized void removeResource(IResource resource) {
		cache.remove(resource);
	}

	/**
	 * Checks if given resource is a symbolic link in OS file system.
	 * 
	 * @param resource
	 * @return <code>true</code> if given resource is symbolic link,
	 *         <code>false</code> otherwise
	 */
	public synchronized boolean isSymbolicLink(IResource resource) {
		return cache.contains(resource);
	}

}
