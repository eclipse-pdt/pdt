/*******************************************************************************
 * Copyright (c) 2009, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;

public class DebugBrowserView extends ViewPart implements ISelectionListener {

	public static final String ID_PHPBrowserOutput = "org.eclipse.debug.ui.PHPBrowserOutput"; //$NON-NLS-1$

	private Browser swtBrowser;

	private IPHPDebugTarget fTarget;
	private int fUpdateCount;
	private IDebugEventSetListener terminateListener;
	private DebugViewHelper debugViewHelper;
	private DebugViewPartListener fPartListener;

	/**
	 *
	 */
	public DebugBrowserView() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
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
			swtBrowser = new Browser(container, SWT.NONE);
			swtBrowser.setLayoutData(gridData);
		} catch (SWTError error) {
			swtBrowser = null;
			Label label = new Label(container, SWT.WRAP);
			label.setText(PHPDebugUIMessages.DebugBrowserView_swtBrowserNotAvailable0);
			label.setLayoutData(gridData);
		}

		debugViewHelper = new DebugViewHelper();

		terminateListener = new IDebugEventSetListener() {
			IPHPDebugTarget target;

			public void handleDebugEvents(DebugEvent[] events) {
				if (events != null) {
					int size = events.length;
					for (int i = 0; i < size; i++) {
						Object obj = events[i].getSource();

						if (!(obj instanceof IPHPDebugTarget))
							continue;

						if (events[i].getKind() == DebugEvent.TERMINATE) {
							target = (IPHPDebugTarget) obj;
							Job job = new UIJob(
									PHPDebugUIMessages.PHPDebugUIPlugin_0) { //$NON-NLS-1$
								public IStatus runInUIThread(
										IProgressMonitor monitor) {
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

		DebugPlugin.getDefault().addDebugEventListener(terminateListener);
		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW, this);

		if (fPartListener == null) {
			fPartListener = new DebugViewPartListener();
			getSite().getPage().addPartListener(fPartListener);
		}
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(parent, IPHPHelpContextIds.BROWSER_OUTPUT_VIEW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#setFocus()
	 */
	public void setFocus() {
	}

	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService()
				.removeSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW, this);
		DebugPlugin.getDefault().removeDebugEventListener(terminateListener);

		if (swtBrowser != null && !swtBrowser.isDisposed()) {
			swtBrowser.dispose();
		}
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
	 * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IPHPDebugTarget target = debugViewHelper.getSelectionElement(selection);
		update(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
	 * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void update(IPHPDebugTarget target) {
		if (swtBrowser != null && !swtBrowser.isDisposed()) {
			IPHPDebugTarget oldTarget = fTarget;
			int oldcount = fUpdateCount;
			fTarget = target;

			DebugOutput debugOutput = null;
			if (fTarget != null) {
				if ((fTarget.isSuspended()) || (fTarget.isTerminated())
						|| fTarget.isWaiting()) {
					debugOutput = fTarget.getOutputBuffer();
					fUpdateCount = debugOutput.getUpdateCount();

					// check if output hasn't been updated
					if (fTarget == oldTarget && fUpdateCount == oldcount) {
						return;
					}
				} else {
					// Not Suspended or Terminated

					// the following is a fix for bug
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=205688
					// if the target is not suspended or terminated fTarget
					// should get back its old value
					// so that in the next time the function is called it will
					// not consider this target
					// as it was already set to the view
					fTarget = oldTarget;
					return;
				}
			}

			if (debugOutput != null) {
				String contentType = debugOutput.getContentType();
				if (contentType != null && !contentType.startsWith("text")) { //$NON-NLS-1$
					return; // we don't show garbage anymore
				}
				String output = debugOutput.toString();
				// Skip headers
				int startIdx = output.indexOf("\r\n\r\n"); //$NON-NLS-1$
				if (startIdx == -1) {
					startIdx = output.indexOf("\r\n"); //$NON-NLS-1$
				}
				if (startIdx != -1) {
					output = output.substring(startIdx + 2);
				}
				swtBrowser.setText(output);
			}
		}
	}

	/**
	 * Part listener that reenables updating when the view appears.
	 */
	private class DebugViewPartListener implements IPartListener2 {
		/**
		 * 
		 * @see org.eclipse.ui.IPartListener2#partVisible(IWorkbenchPartReference)
		 */
		public void partVisible(IWorkbenchPartReference ref) {
			IWorkbenchPart part = ref.getPart(false);
			if (part == DebugBrowserView.this) {
				IPHPDebugTarget target = debugViewHelper
						.getSelectionElement(null);
				update(target);
			}
		}

		/**
		 * @see org.eclipse.ui.IPartListener2#partHidden(IWorkbenchPartReference)
		 */
		public void partHidden(IWorkbenchPartReference ref) {

		}

		/**
		 * @see org.eclipse.ui.IPartListener2#partActivated(IWorkbenchPartReference)
		 */
		public void partActivated(IWorkbenchPartReference ref) {
		}

		/**
		 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(IWorkbenchPartReference)
		 */
		public void partBroughtToTop(IWorkbenchPartReference ref) {
		}

		/**
		 * @see org.eclipse.ui.IPartListener2#partClosed(IWorkbenchPartReference)
		 */
		public void partClosed(IWorkbenchPartReference ref) {
		}

		/**
		 * @see org.eclipse.ui.IPartListener2#partDeactivated(IWorkbenchPartReference)
		 */
		public void partDeactivated(IWorkbenchPartReference ref) {
		}

		/**
		 * @see org.eclipse.ui.IPartListener2#partOpened(IWorkbenchPartReference)
		 */
		public void partOpened(IWorkbenchPartReference ref) {
		}

		/**
		 * @see org.eclipse.ui.IPartListener2#partInputChanged(IWorkbenchPartReference)
		 */
		public void partInputChanged(IWorkbenchPartReference ref) {
		}

	}
}
