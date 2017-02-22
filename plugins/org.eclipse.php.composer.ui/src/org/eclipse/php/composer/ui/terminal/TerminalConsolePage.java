/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michał Niewrzał (Rogue Wave Software Inc.) - initial implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.terminal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.tm.internal.terminal.control.ITerminalListener;
import org.eclipse.tm.internal.terminal.control.ITerminalViewControl;
import org.eclipse.tm.internal.terminal.control.TerminalViewControlFactory;
import org.eclipse.tm.internal.terminal.control.actions.AbstractTerminalAction;
import org.eclipse.tm.internal.terminal.control.actions.TerminalActionCopy;
import org.eclipse.tm.internal.terminal.control.actions.TerminalActionCut;
import org.eclipse.tm.internal.terminal.control.actions.TerminalActionPaste;
import org.eclipse.tm.internal.terminal.control.impl.TerminalPlugin;
import org.eclipse.tm.internal.terminal.preferences.ITerminalConstants;
import org.eclipse.tm.internal.terminal.provisional.api.ITerminalConnector;
import org.eclipse.tm.internal.terminal.provisional.api.TerminalState;
import org.eclipse.tm.terminal.view.core.interfaces.ITerminalService.Done;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.progress.UIJob;

class TerminalConsolePage extends Page {

	public static final String TOOLBAR_GROUP_ID = "org.eclipse.php.composer.ui.console.Toolbar"; //$NON-NLS-1$

	private final TerminalConsole terminalConsole;
	private Composite mainComposite;
	private ITerminalViewControl tViewCtrl;

	private MenuManager contextMenuManager;
	private final List<AbstractTerminalAction> contextMenuActions = new ArrayList<AbstractTerminalAction>();

	private Action stopAction = new Action("Stop",
			PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_STOP)) {
		public void run() {
			terminalConsole.getTerminalConnector().disconnect();
		};
	};

	private Action removeInactiveAction = new Action("Close All Inactive Consoles",
			PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_REMOVEALL)) {
		public void run() {
			IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
			Set<IConsole> toRemove = new HashSet<>();
			IConsole[] consoles = consoleManager.getConsoles();
			for (IConsole console : consoles) {
				if (console instanceof TerminalConsole) {
					TerminalConsole terminalConsole = (TerminalConsole) console;
					TerminalConsolePage terminalConsolePage = terminalConsole.getTerminalConsolePage();
					if (terminalConsolePage != null && terminalConsolePage.getTerminalState() == TerminalState.CLOSED) {
						toRemove.add(console);
					}
				}
			}
			consoleManager.removeConsoles(toRemove.toArray(new IConsole[toRemove.size()]));
		};
	};

	private Job connectTerminalJob = new ConnectTerminalJob();
	private Done done;

	private final ITerminalListener listener = new ITerminalListener() {
		@Override
		public void setState(TerminalState state) {
			if (state == TerminalState.CONNECTING || state == TerminalState.CONNECTED) {
				stopAction.setEnabled(true);
			} else if (state == TerminalState.CLOSED) {
				stopAction.setEnabled(false);
				done.done(Status.OK_STATUS);
			}
		}

		@Override
		public void setTerminalTitle(final String title) {
		}
	};

	public TerminalConsolePage(TerminalConsole console, Done done) {
		this.terminalConsole = console;
		this.done = done;
	}

	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		IToolBarManager toolBarManager = pageSite.getActionBars().getToolBarManager();
		toolBarManager.insertBefore(IConsoleConstants.OUTPUT_GROUP, new GroupMarker(TOOLBAR_GROUP_ID));
		toolBarManager.appendToGroup(TOOLBAR_GROUP_ID, stopAction);
		toolBarManager.appendToGroup(TOOLBAR_GROUP_ID, removeInactiveAction);
	}

	@Override
	public void createControl(Composite parent) {
		mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainComposite.setLayout(new FillLayout());

		tViewCtrl = TerminalViewControlFactory.makeControl(listener, mainComposite, new ITerminalConnector[] {});
		tViewCtrl.setConnector(terminalConsole.getTerminalConnector());

		boolean invert = Platform.getPreferencesService().getBoolean(TerminalPlugin.PLUGIN_ID,
				ITerminalConstants.PREF_INVERT_COLORS, false, null);
		tViewCtrl.setInvertedColors(invert);

		contextMenuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		contextMenuManager.add(addAction(new TerminalActionCut(tViewCtrl)));
		contextMenuManager.add(addAction(new TerminalActionCopy(tViewCtrl)));
		contextMenuManager.add(addAction(new TerminalActionPaste(tViewCtrl)));

		contextMenuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				for (AbstractTerminalAction action : contextMenuActions) {
					action.updateAction(true);
				}
			}
		});

		tViewCtrl.getControl().setMenu(contextMenuManager.createContextMenu(tViewCtrl.getControl()));

		connectTerminalJob.schedule();
	}

	private AbstractTerminalAction addAction(AbstractTerminalAction action) {
		contextMenuActions.add(action);
		return action;
	}

	@Override
	public Control getControl() {
		return mainComposite;
	}

	@Override
	public void setFocus() {
		tViewCtrl.setFocus();
	}

	public TerminalConsole getConsole() {
		return terminalConsole;
	}

	@Override
	public void dispose() {
		contextMenuManager.dispose();
		tViewCtrl.disposeTerminal();
		super.dispose();
	}

	public TerminalState getTerminalState() {
		return tViewCtrl.getState();
	}

	public void connectTerminal() {
		if (!tViewCtrl.isConnected()) {
			connectTerminalJob.schedule();
		}
	}

	public void disconnectTerminal() {
		if (tViewCtrl.getState() != TerminalState.CLOSED) {
			tViewCtrl.disconnectTerminal();
		}
	}

	public void setScrollLock(boolean enabled) {
		tViewCtrl.setScrollLock(enabled);
	}

	public boolean getScrollLock() {
		return tViewCtrl.isScrollLock();
	}

	class ConnectTerminalJob extends UIJob {
		public ConnectTerminalJob() {
			super("Connect Terminal"); //$NON-NLS-1$
			setSystem(true);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			if (tViewCtrl != null && !tViewCtrl.isDisposed()) {
				tViewCtrl.clearTerminal();
				tViewCtrl.connectTerminal();
			}
			return Status.OK_STATUS;
		}
	}
}
