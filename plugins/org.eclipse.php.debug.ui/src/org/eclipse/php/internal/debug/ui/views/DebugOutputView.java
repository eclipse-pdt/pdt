/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.debug.core.model.DebugOutput;
import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPThread;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.ui.util.PartListenerAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.wst.html.core.internal.encoding.HTMLDocumentLoader;
import org.eclipse.wst.html.ui.StructuredTextViewerConfigurationHTML;
import org.eclipse.wst.sse.core.internal.text.BasicStructuredDocument;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

/**
 * View for presenting debug output as raw text.
 */
public class DebugOutputView extends AbstractDebugOutputView implements ISelectionListener {

	public static final String ID_PHPDebugOutput = "org.eclipse.debug.ui.PHPDebugOutput"; //$NON-NLS-1$

	private final class Updater implements IUpdater {

		private int fUpdateCount;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.php.internal.debug.ui.views.AbstractDebugOutputView.
		 * IUpdater #update(org.eclipse.php.internal.debug.core.model.IPHPDebugTarget)
		 */
		@Override
		public void update(IPHPDebugTarget target) {
			int oldcount = fUpdateCount;
			HTMLDocumentLoader ss = new HTMLDocumentLoader();
			BasicStructuredDocument input = (BasicStructuredDocument) ss.createNewStructuredDocument();
			if (target != null) {
				if ((target.isSuspended()) || (target.isTerminated()) || (target.isWaiting())) {
					DebugOutput debugOutput = target.getOutputBuffer();
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
					input.setText(this, debugOutput.getOutput());
				}
				// Not Suspended or Terminated
				else {
					return;
				}
			}
			try {
				fSourceViewer.setInput(input);
			} catch (Exception e) {
				// Don't handle
			}
			fSourceViewer.refresh();
		}

	}

	/**
	 * Part listener that re-enables updating when the view appears.
	 */
	private final class DebugViewPartListener extends PartListenerAdapter {
		@Override
		public void partVisible(IWorkbenchPartReference ref) {
			IWorkbenchPart part = ref.getPart(false);
			if (part == DebugOutputView.this) {
				IPHPDebugTarget target = fDebugViewHelper.getSelectionElement(null);
				update(target);
			}
		}
	}

	private IDebugEventSetListener fTerminateListener;
	private StructuredTextViewer fSourceViewer;
	private DebugViewPartListener fPartListener;
	private IPropertyChangeListener fPropertyChangeListener;

	public DebugOutputView() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
	 * widgets .Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.FULL_SELECTION;
		fSourceViewer = new StructuredTextViewer(parent, null, null, false, styles);
		fSourceViewer.setEditable(false);
		fSourceViewer.configure(new StructuredTextViewerConfigurationHTML());
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW,
				this);
		getSite().setSelectionProvider(fSourceViewer.getSelectionProvider());
		setBackgroundColor();
		fTerminateListener = new IDebugEventSetListener() {
			@Override
			public void handleDebugEvents(DebugEvent[] events) {
				if (events != null) {
					int size = events.length;
					for (int i = 0; i < size; i++) {
						Object obj = events[i].getSource();
						if (!(obj instanceof IPHPDebugTarget || obj instanceof PHPThread)) {
							continue;
						}
						if (events[i].getKind() == DebugEvent.TERMINATE || events[i].getKind() == DebugEvent.SUSPEND) {
							final IPHPDebugTarget target;
							if (obj instanceof IPHPDebugTarget) {
								target = (IPHPDebugTarget) obj;
							} else {
								target = (IPHPDebugTarget) ((PHPThread) obj).getDebugTarget();
							}
							Job job = new UIJob(PHPDebugUIMessages.PHPDebugUIPlugin_1) {
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
		if (fPartListener == null) {
			fPartListener = new DebugViewPartListener();
			getSite().getPage().addPartListener(fPartListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(IDebugUIConstants.ID_DEBUG_VIEW,
				this);
		DebugPlugin.getDefault().removeDebugEventListener(fTerminateListener);
		if (fPartListener != null) {
			getSite().getPage().removePartListener(fPartListener);
			fPartListener = null;
		}
		if (fPropertyChangeListener != null) {
			EditorsPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(fPropertyChangeListener);
			fPropertyChangeListener = null;
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
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
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

	/**
	 * Get background color
	 * 
	 * @return background color
	 */
	private Color getBackgroundColor(IPreferenceStore store) {
		String useDefault = store.getString(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT);
		Color dflt = Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		if ("true".equalsIgnoreCase(useDefault)) { //$NON-NLS-1$
			return dflt;
		}
		String bgColor = store.getString(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
		if (bgColor == null || bgColor.equals("")) { //$NON-NLS-1$
			return dflt;
		}
		String[] rgb = bgColor.split(","); //$NON-NLS-1$
		RGB color;
		try {
			color = new RGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		} catch (Throwable ex) {
			return dflt;
		}
		return new Color(Display.getDefault(), color);
	}

	private void setBackgroundColor() {
		IPreferenceStore store = EditorsPlugin.getDefault().getPreferenceStore();
		fSourceViewer.getTextWidget().setBackground(getBackgroundColor(store));
		if (fPropertyChangeListener == null) {
			fPropertyChangeListener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					IPreferenceStore store = EditorsPlugin.getDefault().getPreferenceStore();
					String prop = event.getProperty();
					if (prop.equals(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)
							|| prop.equals(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND)) {
						if (fSourceViewer == null || fSourceViewer.getTextWidget() == null
								|| fSourceViewer.getTextWidget().isDisposed()) {
							return;
						}
						fSourceViewer.getTextWidget().setBackground(getBackgroundColor(store));
					}

				}
			};
			store.addPropertyChangeListener(fPropertyChangeListener);
		}

	}

}
