package org.eclipse.php.server.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements ISelectionListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.server.ui"; //$NON-NLS-1$

	// Debug mode identifier
	public static final boolean isDebugMode;
	static {
		String value = Platform.getDebugOption(PLUGIN_ID + "/debug"); //$NON-NLS-1$
		isDebugMode = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}
	public static IStructuredSelection currentSelection;

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		IWorkbench workbench = getWorkbench();
		IWorkbenchWindow iww = workbench.getActiveWorkbenchWindow();
		ISelectionService iss = iww.getSelectionService();

		ISelection s = iss.getSelection();

		if (s instanceof IStructuredSelection)
			currentSelection = ((IStructuredSelection) s);

		iss.addSelectionListener(this);

	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection)
			currentSelection = ((IStructuredSelection) selection);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
