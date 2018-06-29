/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.debug.core.model.DebugOutput;
import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.ui.util.PartListenerAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

/**
 * View for presenting debug output as rendered in a browser.
 */
public class DebugBrowserView extends AbstractDebugOutputView implements ISelectionListener {

	public static final String ID_PHPBrowserOutput = "org.eclipse.debug.ui.PHPBrowserOutput"; //$NON-NLS-1$

	private final class Updater implements IUpdater {

		private int fUpdateCount;

		@Override
		public void update(IPHPDebugTarget target) {
			if (fSWTBrowser != null && !fSWTBrowser.isDisposed()) {
				int oldcount = fUpdateCount;
				DebugOutput debugOutput = null;
				if (target != null) {
					if ((target.isSuspended()) || (target.isTerminated()) || target.isWaiting()) {
						debugOutput = target.getOutputBuffer();
						fUpdateCount = debugOutput.getUpdateCount();
						// check if output hasn't been updated
						if (fUpdateCount == oldcount) {
							return;
						}
						String contentType = debugOutput.getContentType();
						// we don't show garbage anymore
						if (contentType != null && !contentType.startsWith("text")) { //$NON-NLS-1$
							return;
						}
						fSWTBrowser.setText(debugOutput.getOutput());
					}
					// Not Suspended or Terminated
					else {
						return;
					}
				}
			}
		}

	}

	/**
	 * Part listener that re-enables updating when the view appears.
	 */
	private final class DebugViewPartListener extends PartListenerAdapter {
		@Override
		public void partVisible(IWorkbenchPartReference ref) {
			IWorkbenchPart part = ref.getPart(false);
			if (part == DebugBrowserView.this) {
				IPHPDebugTarget target = fDebugViewHelper.getSelectionElement(null);
				update(target);
			}
		}
	}

	private Browser fSWTBrowser;
	private IDebugEventSetListener fTerminateListener;
	private DebugViewPartListener fPartListener;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		RowLayout rowLayout = new RowLayout();
		rowLayout.spacing = 1;
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		try {
			fSWTBrowser = new Browser(container, SWT.NONE);
			fSWTBrowser.setLayoutData(gridData);
		} catch (SWTError error) {
			fSWTBrowser = null;
			Label label = new Label(container, SWT.WRAP);
			label.setText(PHPDebugUIMessages.DebugBrowserView_swtBrowserNotAvailable0);
			label.setLayoutData(gridData);
		}
		fTerminateListener = new IDebugEventSetListener() {
			@Override
			public void handleDebugEvents(DebugEvent[] events) {
				if (events != null) {
					int size = events.length;
					for (int i = 0; i < size; i++) {
						Object obj = events[i].getSource();
						if (!(obj instanceof IPHPDebugTarget)) {
							continue;
						}
						// Update when debug target is done
						if (events[i].getKind() == DebugEvent.TERMINATE) {
							final IPHPDebugTarget target = (IPHPDebugTarget) obj;
							Job job = new UIJob(PHPDebugUIMessages.PHPDebugUIPlugin_0) {
								@Override
								public IStatus runInUIThread(IProgressMonitor monitor) {
									update(target);
									return Status.OK_STATUS;
								}
							};
							job.schedule();
						}
					}
				}
			}
		};
		DebugPlugin.getDefault().addDebugEventListener(fTerminateListener);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW,
				this);
		if (fPartListener == null) {
			fPartListener = new DebugViewPartListener();
			getSite().getPage().addPartListener(fPartListener);
		}
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.BROWSER_OUTPUT_VIEW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW,
				this);
		DebugPlugin.getDefault().removeDebugEventListener(fTerminateListener);
		if (fSWTBrowser != null && !fSWTBrowser.isDisposed()) {
			fSWTBrowser.dispose();
		}
		if (fPartListener != null) {
			fPartListener = new DebugViewPartListener();
			getSite().getPage().removePartListener(fPartListener);
		}
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
	 * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IPHPDebugTarget target = fDebugViewHelper.getSelectionElement(selection);
		update(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.views.AbstractDebugOutputView#
	 * createUpdater ()
	 */
	@Override
	protected IUpdater createUpdater() {
		return new Updater();
	}

}
