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
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.debug.core.model.DebugOutput;
import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPThread;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.wst.html.core.internal.encoding.HTMLDocumentLoader;
import org.eclipse.wst.html.ui.StructuredTextViewerConfigurationHTML;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocument;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

/**
 * View of the PHP parameter stack
 */
public class DebugOutputView extends AbstractDebugView implements
		ISelectionListener {

	public static final String ID_PHPDebugOutput = "org.eclipse.debug.ui.PHPDebugOutput"; //$NON-NLS-1$

	private IPHPDebugTarget fTarget;
	private int fUpdateCount;
	private IDebugEventSetListener terminateListener;
	private DebugViewHelper debugViewHelper;
	private StructuredTextViewer fSourceViewer;

	public DebugOutputView() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractDebugView#createViewer(org.eclipse.swt.widgets
	 * .Composite)
	 */
	protected Viewer createViewer(Composite parent) {

		int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI
				| SWT.FULL_SELECTION;
		fSourceViewer = new StructuredTextViewer(parent, null, null, false,
				styles);
		fSourceViewer.setEditable(false);
		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW, this);
		getSite().setSelectionProvider(fSourceViewer.getSelectionProvider());

		setBackgroundColor();

		terminateListener = new IDebugEventSetListener() {
			IPHPDebugTarget target;

			public void handleDebugEvents(DebugEvent[] events) {
				if (events != null) {
					int size = events.length;
					for (int i = 0; i < size; i++) {
						Object obj = events[i].getSource();
						// 386462: [Regression] Debug Output does not refresh
						// depending on the focus
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=386462
						if (!(obj instanceof IPHPDebugTarget || obj instanceof PHPThread))
							continue;

						if (events[i].getKind() == DebugEvent.TERMINATE
								|| events[i].getKind() == DebugEvent.SUSPEND) {
							if (obj instanceof IPHPDebugTarget) {

								target = (IPHPDebugTarget) obj;
							} else {
								target = (IPHPDebugTarget) ((PHPThread) obj)
										.getDebugTarget();
							}
							Job job = new UIJob("debug output") { //$NON-NLS-1$
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

		debugViewHelper = new DebugViewHelper();

		return fSourceViewer;
	}

	private void setBackgroundColor() {
		IPreferenceStore store = EditorsPlugin.getDefault()
				.getPreferenceStore();

		fSourceViewer.getTextWidget().setBackground(getBackgroundColor(store));
		IPropertyChangeListener listener = new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				IPreferenceStore store = EditorsPlugin.getDefault()
						.getPreferenceStore();
				String prop = event.getProperty();
				if (prop.equals(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)
						|| prop.equals(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND)) {
					fSourceViewer.getTextWidget().setBackground(
							getBackgroundColor(store));
				}

			}
		};
		store.addPropertyChangeListener(listener);

	}

	/**
	 * Get background color
	 * 
	 * @return background color
	 */
	private Color getBackgroundColor(IPreferenceStore store) {
		String useDefault = store
				.getString(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT);

		Color dflt = Display.getDefault().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND);

		if ("true".equalsIgnoreCase(useDefault)) { //$NON-NLS-1$
			return dflt;
		}

		String bgColor = store
				.getString(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
		if (bgColor == null || bgColor.equals("")) { //$NON-NLS-1$
			return dflt;
		}

		String[] rgb = bgColor.split(","); //$NON-NLS-1$
		RGB color;
		try {
			color = new RGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]),
					Integer.parseInt(rgb[2]));
		} catch (Throwable ex) {
			return dflt;
		}

		return new Color(Display.getDefault(), color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.AbstractDebugView#getHelpContextId()
	 */
	protected String getHelpContextId() {
		return IPHPHelpContextIds.DEBUG_OUTPUT_VIEW;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractDebugView#configureToolBar(org.eclipse.jface
	 * .action.IToolBarManager)
	 */
	protected void configureToolBar(IToolBarManager tbm) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService()
				.removeSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW, this);
		DebugPlugin.getDefault().removeDebugEventListener(terminateListener);
		// fTarget = null;
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

	private synchronized void update(IPHPDebugTarget target) {
		IPHPDebugTarget oldTarget = fTarget;
		int oldcount = fUpdateCount;
		fTarget = target;
		HTMLDocumentLoader ss = new HTMLDocumentLoader();
		BasicStructuredDocument dd = (BasicStructuredDocument) ss
				.createNewStructuredDocument();
		Object input = dd;
		if (fTarget != null) {
			if ((fTarget.isSuspended()) || (fTarget.isTerminated())
					|| (fTarget.isWaiting())) {
				DebugOutput outputBuffer = fTarget.getOutputBuffer();
				fUpdateCount = outputBuffer.getUpdateCount();

				// check if output hasn't been updated
				if (fTarget == oldTarget && fUpdateCount == oldcount)
					return;

				String output = outputBuffer.toString();
				dd.setText(this, output);
			} else {
				// Not Suspended or Terminated

				// the following is a fix for bug
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=205688
				// if the target is not suspended or terminated fTarget should
				// get back its old value
				// so that in the next time the function is called it will not
				// consider this target
				// as it was already set to the view
				fTarget = oldTarget;
				return;
			}
		}
		try {
			fSourceViewer.setInput(input);
		} catch (Exception e) {
			// Don't handle - it may be NPE in LineStyleProviderForEmbeddedCSS
		}
		fSourceViewer.configure(new StructuredTextViewerConfigurationHTML());
		fSourceViewer.refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.AbstractDebugView#createActions()
	 */
	protected void createActions() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractDebugView#fillContextMenu(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	protected void fillContextMenu(IMenuManager menu) {
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

	}

	public void updateObjects() {
		super.updateObjects();
		// update();

	}

	protected void becomesVisible() {
		super.becomesVisible();
		IPHPDebugTarget target = debugViewHelper.getSelectionElement(null);
		update(target);
	}
}
