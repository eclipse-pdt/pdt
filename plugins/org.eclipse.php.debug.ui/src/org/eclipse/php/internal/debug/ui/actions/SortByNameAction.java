package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.internal.ui.actions.AbstractDebugActionDelegate;
import org.eclipse.debug.ui.IDebugView;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.swt.custom.BusyIndicator;

public class SortByNameAction extends AbstractDebugActionDelegate {
	private static final String ID = "org.eclipse.php.debug.ui.SortByName"; //$NON-NLS-1$

	public SortByNameAction() {
	}

	@Override
	public void init(IAction action) {
		super.init(action);
		initLabel();
	}

	@Override
	protected void setAction(IAction action) {
		super.setAction(action);
		initLabel();
	}

	protected String getActionId() {
		return ID;
	}

	@Override
	protected void doAction(Object element) throws DebugException {
		PHPProjectPreferences.changeSortByNameStatus();
		initLabel();
		if (getView() instanceof IDebugView) {
			final Viewer viewer = ((IDebugView) getView()).getViewer();
			if (viewer.getControl().isDisposed()) {
				return;
			}

			BusyIndicator.showWhile(viewer.getControl().getDisplay(),
					new Runnable() {
						public void run() {
							viewer.refresh();
						}
					});
		}

	}

	private void initLabel() {
		if (!PHPProjectPreferences.isSortByName()) {
			getAction().setText(Messages.SortByNameAction_1);
		} else {
			getAction().setText(Messages.SortByNameAction_2);
		}
	}

}
