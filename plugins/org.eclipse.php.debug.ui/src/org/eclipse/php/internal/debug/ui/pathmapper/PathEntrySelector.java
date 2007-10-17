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

	public synchronized PathEntry[] filter(final PathEntry[] entries, final AbstractPath remotePath) {
		final List<PathEntry> l = new LinkedList<PathEntry>();
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					PathEntrySelectionDialog selectDialog = new PathEntrySelectionDialog(Display.getDefault().getActiveShell(), remotePath, entries);
					if (selectDialog.open() == Window.OK) {
						PathEntry pathEntry = (PathEntry) selectDialog.getFirstResult();
						if (pathEntry != null) {
							l.add(pathEntry);
						}
					}
				} finally {
					synchronized (l) {
						l.notifyAll();
					}
				}
			}
		});
		synchronized (l) {
			try {
				l.wait();
			} catch (InterruptedException e) {
			}
		}
		return l.toArray(new PathEntry[l.size()]);
	}
}
