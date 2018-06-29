/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
package org.eclipse.php.mylyn.ui;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.mylyn.context.ui.IContextUiStartup;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PDTMylynPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.mylyn.ui"; //$NON-NLS-1$

	// The shared instance
	private static PDTMylynPlugin plugin;

	private ActiveFoldingEditorTracker editorTracker;

	/**
	 * The constructor
	 */
	public PDTMylynPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		lazystop();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static PDTMylynPlugin getDefault() {
		return plugin;
	}

	private void installEditorTracker(IWorkbench workbench) {
		editorTracker = new ActiveFoldingEditorTracker();
		editorTracker.install(workbench);

		// update editors that are already opened
		for (IWorkbenchWindow w : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			IWorkbenchPage page = w.getActivePage();
			if (page != null) {
				IEditorReference[] references = page.getEditorReferences();
				for (IEditorReference reference : references) {
					IEditorPart part = reference.getEditor(false);
					if (part != null && part instanceof PHPStructuredEditor) {
						editorTracker.registerEditor((PHPStructuredEditor) part);
					}
				}
			}
		}
	}

	private void lazyStart() {
		installEditorTracker(PlatformUI.getWorkbench());
	}

	private void lazystop() {
		uninstallEditorTracker(PlatformUI.getWorkbench());
	}

	private void uninstallEditorTracker(IWorkbench workbench) {
		// update editors that are already opened
		if (editorTracker == null) {
			return;
		}
		for (IWorkbenchWindow w : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			IWorkbenchPage page = w.getActivePage();
			if (page != null) {
				IEditorReference[] references = page.getEditorReferences();
				for (IEditorReference reference : references) {
					IEditorPart part = reference.getEditor(false);
					if (part != null && part instanceof PHPStructuredEditor) {
						editorTracker.unregisterEditor((PHPStructuredEditor) part);
					}
				}
			}
		}
	}

	public static class PDTUIBridgeStartup implements IContextUiStartup {

		@Override
		public void lazyStartup() {
			PDTMylynPlugin.getDefault().lazyStart();
		}

	}

}
