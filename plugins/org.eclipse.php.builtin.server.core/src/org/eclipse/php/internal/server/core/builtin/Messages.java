/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import org.eclipse.osgi.util.NLS;

/**
 * Translated messages.
 */
public class Messages extends NLS {
	public static String copyingTask;
	public static String errorCopyingFile;
	public static String deletingTask;
	public static String portUnknown;
	public static String loadingTask;
	public static String errorCouldNotLoadConfiguration;
	public static String savingTask;
	public static String errorPublish;
	public static String errorCouldNotSaveConfiguration;
	public static String updatingConfigurationTask;
	public static String canAddModule;
	public static String portServer;
	public static String runtimeDirPrepared;
	public static String publishConfigurationTask;
	public static String publishContextConfigTask;
	public static String savingContextConfigTask;
	public static String checkingContextTask;
	public static String serverPostProcessingComplete;
	public static String errorPublishConfiguration;
	public static String errorPublishServer;
	public static String cleanupServerTask;
	public static String detectingRemovedProjects;
	public static String deletingContextFilesTask;
	public static String deletingContextFile;
	public static String deletedContextFile;
	public static String errorCouldNotDeleteContextFile;
	public static String errorCleanupServer;
	public static String publisherPublishTask;
	public static String errorNoConfiguration;
	public static String errorConfigurationProjectClosed;
	public static String errorWebModulesOnly;
	public static String errorNoRuntime;
	public static String publishServerTask;
	public static String errorPortInvalid;
	public static String errorPortInUse;
	public static String errorPortsInUse;
	public static String errorDuplicateContextRoot;
	public static String errorCouldNotLoadContextXml;
	public static String errorXMLServiceNotFound;
	public static String errorXMLNoService;
	public static String errorXMLEngineNotFound;
	public static String errorXMLHostNotFound;
	public static String errorXMLContextNotFoundPath;
	public static String errorXMLContextMangerNotFound;
	public static String errorXMLContextNotFoundPath32;
	public static String errorXMLNullContextArg;
	public static String errorNoPublishNotSupported;
	public static String errorPublishContextNotFound;
	public static String errorPublishCouldNotRemoveModule;
	public static String errorPublishLoaderJarNotFound;
	public static String errorPublishURLConvert;
	public static String errorPublishCantDeleteLoaderJar;
	public static String errorPublishCatalinaProps;
	public static String errorPublishPathDup;
	public static String errorPublishPathConflict;
	public static String errorPublishPathMissing;

	public static String configurationEditorActionModifyPort;
	public static String configurationEditorActionModifyMimeMapping;
	public static String configurationEditorActionAddMimeMapping;
	public static String configurationEditorActionAddWebModule;
	public static String configurationEditorActionModifyWebModule;
	public static String configurationEditorActionRemoveMimeMapping;
	public static String configurationEditorActionRemoveWebModule;
	public static String configurationEditorActionEditWebModulePath;
	public static String serverEditorActionSetDebugMode;
	public static String serverEditorActionSetSecure;
	public static String serverEditorActionSetServerDirectory;
	public static String serverEditorActionSetDeployDirectory;
	public static String serverEditorActionSetServeWithoutPublish;
	public static String serverEidtorActionSetSeparateContextFiles;
	public static String serverEditorActionSetModulesReloadableByDefault;

	public static String warningCantReadDirectory;
	public static String errorPhpExeNotFoundOrNotExecutable;
	public static String errorPhpIniNotFoundOrNotReadable;

	public static String AbstractPHPServerRunner_0;
	public static String DefaultPHPServerRunner_0__0____1___2;
	public static String DefaultPHPServerRunner_0__0__at_localhost__1__1;
	public static String DefaultPHPServerRunner_Launching_server____1;
	public static String DefaultPHPServerRunner_Constructing_command_line____2;
	public static String DefaultPHPServerRunner_Starting_server____3;

	public static String PHPRuntimeLocator_Processing_search_results;
	public static String PHPRuntimeLocator_Searching_with_found;
	public static String PHPRuntimeLocator_Fetching_php_exe_info;
	public static String PHPRuntimeLocator_14;

	static {
		NLS.initializeMessages("org.eclipse.php.internal.server.core.builtin.Messages", Messages.class); //$NON-NLS-1$
	}
}