/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
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

import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.pathmapper.AbstractPath;
import org.eclipse.php.internal.debug.core.pathmapper.IPathEntryFilter;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.swt.widgets.Display;

public class PathEntrySelector implements IPathEntryFilter {

	public PathEntrySelector() {
	}

	public PathEntry[] filter(final PathEntry[] entries, final AbstractPath remotePath) {
		final List<PathEntry> l = new LinkedList<PathEntry>();
		Runnable r = new Runnable() {
			public void run() {
				PathEntrySelectionDialog selectDialog = new PathEntrySelectionDialog(Display.getDefault().getActiveShell(), remotePath, entries);
				if (selectDialog.open() == Window.OK) {
					PathEntry pathEntry = (PathEntry) selectDialog.getFirstResult();
					if (pathEntry != null) {
						l.add(pathEntry);
					}
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
}
