/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.util.*;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.pathmapper.IPathEntryFilter;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.ResolveBlackList.Type;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class PathEntrySelector implements IPathEntryFilter {

	public PathEntrySelector() {
	}

	public PathEntry[] filter(PathEntry[] entries,
			final VirtualPath remotePath, final IDebugTarget debugTarget) {
		entries = removeDuplicate(entries);
		final List<PathEntry> l = new LinkedList<PathEntry>();
		final PathEntry[] mostMatchEntries = getMostMatchEntries(entries,
				remotePath);
		PathEntry matchByConfig = null;
		if (mostMatchEntries.length == 0 || mostMatchEntries.length > 1) {
			matchByConfig = getMatchFromLaunchConfiguration(entries,
					debugTarget.getLaunch().getLaunchConfiguration());
		}
		if (mostMatchEntries.length == 1) {
			l.add(mostMatchEntries[0]);
		} else if (matchByConfig != null) {
			l.add(matchByConfig);
		} else {
			Runnable r = new Runnable() {
				public void run() {
					// grab usable shell from somewhere:
					Shell shell = Display.getDefault().getActiveShell();
					if (shell == null) {
						IWorkbenchWindow window = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow();
						if (window == null) {
							IWorkbenchWindow windows[] = PlatformUI
									.getWorkbench().getWorkbenchWindows();
							window = windows[0];
						}
						if (window != null) {
							shell = window.getShell();
						}
					}
					PathEntry entry = runFilterDialog(shell, remotePath,
							mostMatchEntries, debugTarget);
					if (entry != null) {
						l.add(entry);
					}
				}
			};
			if (Display.getCurrent() != null) {
				r.run();
			} else {
				Display.getDefault().syncExec(r);
			}
		}
		return l.toArray(new PathEntry[l.size()]);
	}

	private PathEntry getMatchFromLaunchConfiguration(PathEntry[] entries,
			ILaunchConfiguration launchConfiguration) {
		try {
			String projectName = launchConfiguration.getAttribute(
					IPHPDebugConstants.PHP_Project, (String) null);
			if (projectName != null) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(projectName);
				for (PathEntry pathEntry : entries) {
					Object container = pathEntry.getContainer();
					if (container instanceof IContainer) {
						IProject p = ((IContainer) container).getProject();
						if (p != null && p.equals(project)) {
							return pathEntry;
						}
					}
				}
			}
		} catch (CoreException e) {
			// log exception and continue debugging
			PHPDebugUIPlugin.log(e);
		}
		return null;
	}

	private PathEntry[] removeDuplicate(PathEntry[] entries) {
		Set<PathEntry> set = new HashSet<PathEntry>();
		for (int i = 0; i < entries.length; i++) {
			set.add(entries[i]);
		}
		return set.toArray(new PathEntry[set.size()]);
	}

	private PathEntry[] getMostMatchEntries(PathEntry[] entries,
			VirtualPath remotePath) {
		if (remotePath.getSegmentsCount() == 1) {
			return entries;
		}
		Map<Integer, List<PathEntry>> map = new HashMap<Integer, List<PathEntry>>();
		int mostMatchSegmentsNumber = 1;
		for (int i = 0; i < entries.length; i++) {
			PathEntry pathEntry = entries[i];
			VirtualPath virtualPath = pathEntry.getAbstractPath();
			int matchSegmentsNumber = getMatchSegmentsNumber(virtualPath,
					remotePath);
			if (matchSegmentsNumber > mostMatchSegmentsNumber) {
				mostMatchSegmentsNumber = matchSegmentsNumber;
			}
			List<PathEntry> list = map.get(matchSegmentsNumber);
			if (list == null) {
				list = new ArrayList<PathEntry>();
				map.put(matchSegmentsNumber, list);
			}
			list.add(pathEntry);

		}
		List<PathEntry> mostMatchList = map.get(mostMatchSegmentsNumber);
		return mostMatchList.toArray(new PathEntry[mostMatchList.size()]);
	}

	private int getMatchSegmentsNumber(VirtualPath virtualPath,
			VirtualPath remotePath) {
		int i = virtualPath.getSegmentsCount();
		if (i > remotePath.getSegmentsCount()) {
			i = remotePath.getSegmentsCount();
		}
		int result = 0;
		// compare last i segments.
		for (int j = i - 1; j >= 0; j--) {
			if (virtualPath.getSegments()[j
					+ (virtualPath.getSegmentsCount() - i)].equals(remotePath
					.getSegments()[j + (remotePath.getSegmentsCount() - i)])) {
				result++;
			}
		}
		return result;
	}

	protected PathEntrySelectionDialog createSelectionDialog(Shell shell,
			VirtualPath remotePath, PathEntry[] entries) {
		return new PathEntrySelectionDialog(shell, remotePath, entries);
	}

	protected PathEntry runFilterDialog(Shell shell, VirtualPath remotePath,
			PathEntry[] entries, IDebugTarget debugTarget) {
		PathEntrySelectionDialog selectDialog = createSelectionDialog(shell,
				remotePath, entries);
		if (selectDialog.open() == Window.OK) {
			// Path entry was chosen:
			PathEntry result = selectDialog.getResult();
			if (result != null) {
				return result;
			}

			// Ignore path was chosen:
			VirtualPath ignorePath = selectDialog.getIgnoreResult();
			result = new PathEntry(ignorePath, PathEntry.Type.SERVER, null);
			if (debugTarget instanceof PHPDebugTarget) {
				PHPDebugTarget phpDebugTarget = (PHPDebugTarget) debugTarget;
				phpDebugTarget.getContextManager().addToResolveBlacklist(
						ignorePath, Type.RECURSIVE);
			}
			return result;
		} else {
			try {
				debugTarget.terminate();
			} catch (DebugException e) {
			}
		}
		return null;
	}
}
