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
package org.eclipse.php.internal.ui;

import java.io.IOException;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContextTypeIds;
import org.eclipse.php.internal.ui.folding.PHPFoldingStructureProviderRegistry;
import org.eclipse.php.internal.ui.preferences.MembersOrderPreferenceCache;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.php.internal.ui.util.PHPManualDirectorDescriptor;
import org.eclipse.php.internal.ui.util.PHPManualSiteDescriptor;
import org.eclipse.php.internal.ui.util.ProblemMarkerManager;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.ConfigurationElementSorter;
import org.eclipse.wst.xml.ui.internal.Logger;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PHPUiPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static PHPUiPlugin plugin;

	public static final String ID = "org.eclipse.php.ui";
	public static final int INTERNAL_ERROR = 10001;

	public static final boolean isDebugMode;
	static {
		String value = Platform.getDebugOption("org.eclipse.php.ui/debug");
		isDebugMode = value != null && value.equalsIgnoreCase("true");
	}

	private ImageDescriptorRegistry fImageDescriptorRegistry;
	private ProblemMarkerManager fProblemMarkerManager;
	protected TemplateStore templateStore = null;
	protected ContextTypeRegistry contentTypeRegistry = null;
	private MembersOrderPreferenceCache fMembersOrderPreferenceCache;
	private PHPFoldingStructureProviderRegistry fFoldingStructureProviderRegistry;
	private PHPEditorTextHoverDescriptor[] fPHPEditorTextHoverDescriptors;
	private PHPManualSiteDescriptor[] fPHPManualSiteDescriptors;
	private PHPManualDirectorDescriptor[] fPHPManualDirectorDescriptors;

	/**
	 * The constructor.
	 */
	public PHPUiPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static PHPUiPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.php.ui", path);
	}

	public static Shell getActiveWorkbenchShell() {
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		if (window != null) {
			return window.getShell();
		}
		return null;
	}

	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}

	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, message, null));
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, ID, INTERNAL_ERROR, "PHP ui plugin internal error", e)); //$NON-NLS-1$
	}

	public static ImageDescriptorRegistry getImageDescriptorRegistry() {
		return getDefault().internalGetImageDescriptorRegistry();
	}

	private synchronized ImageDescriptorRegistry internalGetImageDescriptorRegistry() {
		if (fImageDescriptorRegistry == null)
			fImageDescriptorRegistry = new ImageDescriptorRegistry();
		return fImageDescriptorRegistry;
	}

	public synchronized ProblemMarkerManager getProblemMarkerManager() {
		if (fProblemMarkerManager == null)
			fProblemMarkerManager = new ProblemMarkerManager();
		return fProblemMarkerManager;
	}

	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static IWorkbenchPage getActivePage() {
		return getDefault().internalGetActivePage();
	}

	private IWorkbenchPage internalGetActivePage() {
		IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return null;
		return getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}

	public static void createStandardGroups(IMenuManager menu) {
		if (!menu.isEmpty())
			return;

		menu.add(new Separator(IContextMenuConstants.GROUP_NEW));
		menu.add(new GroupMarker(IContextMenuConstants.GROUP_GOTO));
		menu.add(new Separator(IContextMenuConstants.GROUP_OPEN));
		menu.add(new GroupMarker(IContextMenuConstants.GROUP_SHOW));
		menu.add(new Separator(IContextMenuConstants.GROUP_REORGANIZE));
		menu.add(new Separator(IContextMenuConstants.GROUP_BUILD));
		menu.add(new Separator(IContextMenuConstants.GROUP_ADDITIONS));
		menu.add(new Separator(IContextMenuConstants.GROUP_PROPERTIES));
	}

	/**
	 * Returns the template store for the xml editor templates.
	 * 
	 * @return the template store for the xml editor templates
	 */
	public TemplateStore getTemplateStore() {
		if (templateStore == null) {
			templateStore = new PHPTemplateStore(getTemplateContextRegistry(), getPreferenceStore(), PreferenceConstants.TEMPLATES_KEY);

			try {
				templateStore.load();
			} catch (IOException e) {
				Logger.logException(e);
			}
		}
		return templateStore;
	}

	/**
	 * Returns the template context type registry for the xml plugin.
	 * 
	 * @return the template context type registry for the xml plugin
	 */
	public ContextTypeRegistry getTemplateContextRegistry() {
		if (contentTypeRegistry == null) {
			ContributionContextTypeRegistry registry = new ContributionContextTypeRegistry();
			registry.addContextType(PHPTemplateContextTypeIds.PHP);
			registry.addContextType(PHPTemplateContextTypeIds.PHPDOC);
			registry.addContextType(PHPTemplateContextTypeIds.NEW_PHP);
			contentTypeRegistry = registry;
		}

		return contentTypeRegistry;
	}

	public synchronized MembersOrderPreferenceCache getMemberOrderPreferenceCache() {
		if (fMembersOrderPreferenceCache == null)
			fMembersOrderPreferenceCache = new MembersOrderPreferenceCache();
		return fMembersOrderPreferenceCache;
	}

	public static String getPluginId() {
		return ID;
	}

	/**
	 * Returns the registry of the extensions to the <code>org.eclipse.php.ui.phpFoldingStructureProvider</code>
	 * extension point.
	 * 
	 * @return the registry of contributed <code>IPHPFoldingStructureProvider</code>
	 * @since 3.1
	 */
	public synchronized PHPFoldingStructureProviderRegistry getFoldingStructureProviderRegistry() {
		if (fFoldingStructureProviderRegistry == null)
			fFoldingStructureProviderRegistry= new PHPFoldingStructureProviderRegistry();
		return fFoldingStructureProviderRegistry;
	}
	
	/**
	 * Returns all PHP editor text hovers contributed to the workbench.
	 * 
	 * @return an array of PHPEditorTextHoverDescriptor
	 * @since 2.1
	 */
	public PHPEditorTextHoverDescriptor[] getPHPEditorTextHoverDescriptors() {
		if (fPHPEditorTextHoverDescriptors == null) {
			fPHPEditorTextHoverDescriptors= PHPEditorTextHoverDescriptor.getContributedHovers();
			ConfigurationElementSorter sorter= new ConfigurationElementSorter() {
				/*
				 * @see org.eclipse.ui.texteditor.ConfigurationElementSorter#getConfigurationElement(java.lang.Object)
				 */
				public IConfigurationElement getConfigurationElement(Object object) {
					return ((PHPEditorTextHoverDescriptor)object).getConfigurationElement();
				}
			};
			sorter.sort(fPHPEditorTextHoverDescriptors);
		
			// Move Best Match hover to front
			for (int i= 0; i < fPHPEditorTextHoverDescriptors.length - 1; i++) {
				if (PreferenceConstants.ID_BESTMATCH_HOVER.equals(fPHPEditorTextHoverDescriptors[i].getId())) {
					PHPEditorTextHoverDescriptor hoverDescriptor= fPHPEditorTextHoverDescriptors[i];
					for (int j= i; j > 0; j--)
						fPHPEditorTextHoverDescriptors[j]= fPHPEditorTextHoverDescriptors[j-1];
					fPHPEditorTextHoverDescriptors[0]= hoverDescriptor;
					break;
				}
				
			}
		}
		
		return fPHPEditorTextHoverDescriptors;
	} 

	/**
	 * Resets the PHP editor text hovers contributed to the workbench.
	 * <p>
	 * This will force a rebuild of the descriptors the next time
	 * a client asks for them.
	 * </p>
	 * 
	 * @since 2.1
	 */
	public void resetPHPEditorTextHoverDescriptors() {
		fPHPEditorTextHoverDescriptors= null;
	}
	
	/**
	 * Returns all PHP manual sites contributed to the workbench.
	 */
	public PHPManualSiteDescriptor[] getPHPManualSiteDescriptors() {
		if (fPHPManualSiteDescriptors == null) {
			fPHPManualSiteDescriptors = PHPManualSiteDescriptor.getContributedSites();
		}
		return fPHPManualSiteDescriptors;
	}
	
	/**
	 * Returns all PHP manual directors contributed to the workbench.
	 */
	public PHPManualDirectorDescriptor[] getPHPManualDirectorDescriptors() {
		if (fPHPManualDirectorDescriptors == null) {
			fPHPManualDirectorDescriptors = PHPManualDirectorDescriptor.getContributedDirectors();
		}
		return fPHPManualDirectorDescriptors;
	}
}
