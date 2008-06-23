/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.pathmapper.IPathEntryFilter;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.ResolveBlackList.Type;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class PathEntrySelector implements IPathEntryFilter {

	public PathEntrySelector() {
	}

	public PathEntry[] filter(final PathEntry[] entries, final VirtualPath remotePath, final IDebugTarget debugTarget) {
		final List<PathEntry> l = new LinkedList<PathEntry>();
		Runnable r = new Runnable() {
			public void run() {
				// grab usable shell from somewhere:
				Shell shell = Display.getDefault().getActiveShell();
				if (shell == null) {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					if (window == null) {
						IWorkbenchWindow windows[] = PlatformUI.getWorkbench().getWorkbenchWindows();
						window = windows[0];
					}
					if (window != null) {
						shell = window.getShell();
					}
				}
				PathEntry entry = runFilterDialog(shell, remotePath, entries, debugTarget);
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
		return l.toArray(new PathEntry[l.size()]);
	}

	protected PathEntrySelectionDialog createSelectionDialog(Shell shell, VirtualPath remotePath, PathEntry[] entries) {
		return new PathEntrySelectionDialog(shell, remotePath, entries);
	}

	protected PathEntry runFilterDialog(Shell shell, VirtualPath remotePath, PathEntry[] entries, IDebugTarget debugTarget) {
		PathEntrySelectionDialog selectDialog = createSelectionDialog(shell, remotePath, entries);
		if (selectDialog.open() == Window.OK) {
			// Path entry was chosen:
			PathEntry result = selectDialog.getResult();
			if (result != null) {
				return result;
			}

			// Ignore path was chosen:
			VirtualPath ignorePath = selectDialog.getIgnoreResult();
			if (debugTarget instanceof PHPDebugTarget) {
				PHPDebugTarget phpDebugTarget = (PHPDebugTarget) debugTarget;
				phpDebugTarget.getContextManager().addToResolveBlacklist(ignorePath, Type.RECURSIVE);
			}
		} else {
			try {
				debugTarget.terminate();
			} catch (DebugException e) {
			}
		}
		return null;
	}
}
