/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import java.io.IOException;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IWorkingCopyManager;
import org.eclipse.dltk.ui.MembersOrderPreferenceCache;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.osgi.service.environment.EnvironmentInfo;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.corext.template.php.CodeTemplateContextType;
import org.eclipse.php.internal.ui.editor.ASTProvider;
import org.eclipse.php.internal.ui.editor.templates.PhpCommentTemplateContextType;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContextType;
import org.eclipse.php.internal.ui.explorer.LibraryFolderChangeListener;
import org.eclipse.php.internal.ui.folding.PHPFoldingStructureProviderRegistry;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.text.PHPTextTools;
import org.eclipse.php.internal.ui.text.correction.CorrectionCommandInstaller;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.php.internal.ui.util.ElementCreationProxy;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.php.internal.ui.util.PHPManualSiteDescriptor;
import org.eclipse.php.internal.ui.viewsupport.ImagesOnFileSystemRegistry;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.texteditor.ConfigurationElementSorter;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.jsdt.core.ITypeRoot;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredPartitioning;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The main plugin class to be used in the desktop.
 */
public class PHPUiPlugin extends AbstractUIPlugin {

	// The shared instance.
	private static PHPUiPlugin plugin;

	// the active formatter for this execution
	private IContentFormatter fActiveFormatter = null;

	public static final String ID = "org.eclipse.php.ui"; //$NON-NLS-1$
	public static final int INTERNAL_ERROR = 10001;

	public static final boolean isDebugMode;

	public static final String PERSPECTIVE_ID = "org.eclipse.php.perspective"; //$NON-NLS-1$
	public static final String FORMATTER_PROCESSOR_ID = "org.eclipse.php.ui.phpFormatterProcessor"; //$NON-NLS-1$
	public static final String FORMATTER_PROCESSOR = "processor"; //$NON-NLS-1$

	static {
		String value = Platform.getDebugOption("org.eclipse.php.ui/debug"); //$NON-NLS-1$
		isDebugMode = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	private ImageDescriptorRegistry fImageDescriptorRegistry;
	protected TemplateStore templateStore = null;
	protected ContextTypeRegistry codeTypeRegistry = null;
	private MembersOrderPreferenceCache fMembersOrderPreferenceCache;
	private PHPFoldingStructureProviderRegistry fFoldingStructureProviderRegistry;
	private PHPEditorTextHoverDescriptor[] fPHPEditorTextHoverDescriptors;
	private PHPManualSiteDescriptor[] fPHPManualSiteDescriptors;
	private ImagesOnFileSystemRegistry fImagesOnFSRegistry;
	private LibraryFolderChangeListener libraryFolderChangeListener;

	/**
	 * The AST provider.
	 * 
	 * @since 3.0
	 */
	private ASTProvider fASTProvider;

	private ColorManager fColorManager;

	private PHPTextTools fTextTools;

	private ContributionContextTypeRegistry fContextTypeRegistry;

	private PHPTemplateStore fCodeTemplateStore;

	private IPreferenceStore fCorePreferenceStore;

	public static String OPEN_CALL_HIERARCHY_ACTION_FAMILY_NAME = PHPUIMessages.PHPUiPlugin_4;
	public static String OPEN_TYPE_HIERARCHY_ACTION_FAMILY_NAME = PHPUIMessages.PHPUiPlugin_5;

	/**
	 * The constructor.
	 */
	public PHPUiPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		initializeAfterStart(context);
	}

	/**
	 * This method is used for later initialization. This trick should release
	 * plug-in start-up.
	 * 
	 * @param context
	 */
	void initializeAfterStart(final BundleContext context) {
		CorrectionCommandInstaller.registerCommands();
		Job job = new Job("") { //$NON-NLS-1$
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				libraryFolderChangeListener = new LibraryFolderChangeListener();
				LibraryFolderManager.getInstance().addListener(libraryFolderChangeListener);

				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						processCommandLine(context);
					}
				});

				if (PlatformUI.isWorkbenchRunning()) {
					new InitializeAfterLoadJob().schedule(); // must be last
					// call in
					// start()
					// method
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule(Job.LONG);
	}

	static void initializeAfterLoad(IProgressMonitor monitor) {
		org.eclipse.dltk.internal.corext.util.OpenTypeHistory.getInstance(PHPUILanguageToolkit.getInstance())
				.checkConsistency(monitor);
	}

	private void processCommandLine(BundleContext context) {
		ServiceTracker environmentTracker = new ServiceTracker(context, EnvironmentInfo.class.getName(), null);
		environmentTracker.open();
		EnvironmentInfo environmentInfo = (EnvironmentInfo) environmentTracker.getService();
		environmentTracker.close();
		if (environmentInfo == null) {
			return;
		}
		String[] args = environmentInfo.getNonFrameworkArgs();
		if (args == null || args.length == 0) {
			return;
		}

		return;
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);

		CorrectionCommandInstaller.unregisterCommands();

		if (libraryFolderChangeListener != null) {
			LibraryFolderManager.getInstance().removeListener(libraryFolderChangeListener);
			libraryFolderChangeListener = null;
		}

		Job.getJobManager().cancel(OPEN_TYPE_HIERARCHY_ACTION_FAMILY_NAME);
		Job.getJobManager().cancel(OPEN_CALL_HIERARCHY_ACTION_FAMILY_NAME);
		fASTProvider = null;
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static PHPUiPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.php.ui", //$NON-NLS-1$
				path);
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

	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static IWorkbenchPage getActivePage() {
		return getDefault().internalGetActivePage();
	}

	public static IEditorPart getActiveEditor() {
		IWorkbenchPage activePage = getActivePage();
		if (activePage != null) {
			return activePage.getActiveEditor();
		}
		return null;
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
			templateStore = new PHPTemplateStore(getTemplateContextRegistry(), getPreferenceStore(),
					PreferenceConstants.TEMPLATES_KEY);

			try {
				templateStore.load();
			} catch (IOException e) {
				Logger.logException(e);
			}
		}
		return templateStore;
	}

	/**
	 * Returns the template store for the code generation templates.
	 * 
	 * @return the template store for the code generation templates
	 * @since 3.0
	 */
	public TemplateStore getCodeTemplateStore() {
		if (fCodeTemplateStore == null) {

			fCodeTemplateStore = new PHPTemplateStore(getCodeTemplateContextRegistry(), getPreferenceStore(),
					PreferenceConstants.CODE_TEMPLATES_KEY);

			try {
				fCodeTemplateStore.load();
			} catch (IOException e) {
				Logger.logException(e);
			}
		}

		return fCodeTemplateStore;
	}

	/**
	 * Returns the template context type registry for the xml plugin.
	 * 
	 * @return the template context type registry for the xml plugin
	 */
	public ContextTypeRegistry getCodeTemplateContextRegistry() {
		if (codeTypeRegistry == null) {
			ContributionContextTypeRegistry registry = new ContributionContextTypeRegistry();

			CodeTemplateContextType.registerContextTypes(registry);

			codeTypeRegistry = registry;
		}

		return codeTypeRegistry;
	}

	/**
	 * Returns the template context type registry for the java plug-in.
	 * 
	 * @return the template context type registry for the java plug-in
	 * @since 3.0
	 */
	public synchronized ContextTypeRegistry getTemplateContextRegistry() {
		if (fContextTypeRegistry == null) {
			ContributionContextTypeRegistry registry = new ContributionContextTypeRegistry();

			registry.addContextType(PhpTemplateContextType.PHP_CONTEXT_TYPE_ID);
			registry.addContextType(PhpTemplateContextType.PHP_STATEMENTS_CONTEXT_TYPE_ID);
			registry.addContextType(PhpTemplateContextType.PHP_TYPE_MEMBERS_CONTEXT_TYPE_ID);
			registry.addContextType(PhpTemplateContextType.PHP_GLOBAL_MEMBERS_CONTEXT_TYPE_ID);
			registry.addContextType(PhpTemplateContextType.PHP_TYPE_METHOD_STATEMENTS_CONTEXT_TYPE_ID);
			registry.addContextType(PhpTemplateContextType.PHP_CLASS_MEMBERS_CONTEXT_TYPE_ID);
			registry.addContextType(PhpCommentTemplateContextType.PHP_COMMENT_CONTEXT_TYPE_ID);

			fContextTypeRegistry = registry;
		}

		return fContextTypeRegistry;
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
	 * Returns the registry of the extensions to the
	 * <code>org.eclipse.php.ui.phpFoldingStructureProvider</code> extension
	 * point.
	 * 
	 * @return the registry of contributed
	 *         <code>IPHPFoldingStructureProvider</code>
	 * @since 3.1
	 */
	public synchronized PHPFoldingStructureProviderRegistry getFoldingStructureProviderRegistry() {
		if (fFoldingStructureProviderRegistry == null)
			fFoldingStructureProviderRegistry = new PHPFoldingStructureProviderRegistry();
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
			fPHPEditorTextHoverDescriptors = PHPEditorTextHoverDescriptor.getContributedHovers();
			ConfigurationElementSorter sorter = new ConfigurationElementSorter() {
				/*
				 * @seeorg.eclipse.ui.texteditor.ConfigurationElementSorter#
				 * getConfigurationElement(java.lang.Object)
				 */
				@Override
				public IConfigurationElement getConfigurationElement(Object object) {
					return ((PHPEditorTextHoverDescriptor) object).getConfigurationElement();
				}
			};
			sorter.sort(fPHPEditorTextHoverDescriptors);

			// Move Best Match hover to front
			for (int i = 0; i < fPHPEditorTextHoverDescriptors.length - 1; i++) {
				if (PreferenceConstants.ID_BESTMATCH_HOVER.equals(fPHPEditorTextHoverDescriptors[i].getId())) {
					PHPEditorTextHoverDescriptor hoverDescriptor = fPHPEditorTextHoverDescriptors[i];
					for (int j = i; j > 0; j--)
						fPHPEditorTextHoverDescriptors[j] = fPHPEditorTextHoverDescriptors[j - 1];
					fPHPEditorTextHoverDescriptors[0] = hoverDescriptor;
					break;
				}

			}
		}

		return fPHPEditorTextHoverDescriptors;
	}

	/**
	 * Resets the PHP editor text hovers contributed to the workbench.
	 * <p>
	 * This will force a rebuild of the descriptors the next time a client asks
	 * for them.
	 * </p>
	 * 
	 * @since 2.1
	 */
	public void resetPHPEditorTextHoverDescriptors() {
		fPHPEditorTextHoverDescriptors = null;
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
	 * Returns the AST provider.
	 * 
	 * @return the AST provider
	 * @since 3.0
	 */
	public synchronized ASTProvider getASTProvider() {
		if (fASTProvider == null)
			fASTProvider = new ASTProvider();

		return fASTProvider;
	}

	/**
	 * Returns color manager
	 * 
	 * @return the color manager
	 * @since 3.0
	 */
	public synchronized ColorManager getColorManager() {
		if (fColorManager == null)
			fColorManager = new ColorManager();

		return fColorManager;
	}

	/**
	 * Returns text tools
	 * 
	 * @return the text tools
	 * @since 3.0
	 */
	public synchronized PHPTextTools getTextTools() {
		if (fTextTools == null)
			fTextTools = new PHPTextTools(true);

		return fTextTools;
	}

	/**
	 * Returns the {@link ITypeRoot} wrapped by the given editor input.
	 * 
	 * @param editorInput
	 *            the editor input
	 * @return the {@link ITypeRoot} wrapped by <code>editorInput</code> or
	 *         <code>null</code> if the editor input does not stand for a
	 *         ITypeRoot
	 * 
	 * @since 3.4
	 */
	public static ISourceModule getEditorInputTypeRoot(IEditorInput editorInput) {
		// Performance: check working copy manager first: this is faster
		ISourceModule cu = DLTKUIPlugin.getDefault().getWorkingCopyManager().getWorkingCopy(editorInput);
		if (cu != null)
			return cu;

		ISourceModule je = (ISourceModule) editorInput.getAdapter(ISourceModule.class);
		return je;
	}

	/**
	 * Returns the working copy manager for the Java UI plug-in.
	 * 
	 * @return the working copy manager for the Java UI plug-in
	 */
	public static IWorkingCopyManager getWorkingCopyManager() {
		return DLTKUIPlugin.getDefault().getWorkingCopyManager();
	}

	/**
	 * Returns the current active formatter. In case of a PHP formatter it
	 * should also implement IFormatterProcessorFactory.
	 * 
	 * @return
	 */
	public IContentFormatter getActiveFormatter() {
		if (fActiveFormatter == null) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(FORMATTER_PROCESSOR_ID);
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement element = elements[i];
				if (FORMATTER_PROCESSOR.equals(element.getName())) {
					ElementCreationProxy ecProxy = new ElementCreationProxy(element, FORMATTER_PROCESSOR_ID);
					fActiveFormatter = (IContentFormatter) ecProxy.getObject();
				}
			}
			if (fActiveFormatter == null) {
				fActiveFormatter = new MultiPassContentFormatter(
						IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING, IHTMLPartitions.HTML_DEFAULT);
			}
		}
		return fActiveFormatter;
	}

	/**
	 * Returns the image registry that keeps its images on the local file
	 * system.
	 * 
	 * @return the image registry
	 */
	public ImagesOnFileSystemRegistry getImagesOnFSRegistry() {
		if (fImagesOnFSRegistry == null) {
			fImagesOnFSRegistry = new ImagesOnFileSystemRegistry();
		}
		return fImagesOnFSRegistry;
	}

	public IPreferenceStore getCorePreferenceStore() {
		if (fCorePreferenceStore == null) {
			fCorePreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PHPCorePlugin.ID);
		}
		return fCorePreferenceStore;
	}

}
