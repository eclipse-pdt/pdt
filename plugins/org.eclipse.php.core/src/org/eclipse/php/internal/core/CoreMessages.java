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
package org.eclipse.php.internal.core;


import java.text.MessageFormat;

import org.eclipse.osgi.util.NLS;

public final class CoreMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.internal.core.CoreMessages";//$NON-NLS-1$

	private CoreMessages() {
		// Do not instantiate
	}

	public static String status_cannotUseDeviceOnPath;
	public static String status_coreException;
	public static String status_evaluationError;
	public static String status_IOException;
	public static String status_indexOutOfBounds;
	public static String status_invalidContents;
	public static String status_invalidDestination;
	public static String status_invalidName;
	public static String status_invalidPath;
	public static String status_invalidProject;
	public static String status_invalidResource;
	public static String status_invalidResourceType;
	public static String status_invalidSibling;
	public static String status_nameCollision;
	public static String status_noLocalContents;
	public static String status_OK;
	public static String status_readOnly;
	public static String status_targetException;
	public static String status_updateConflict;
	public static String includePath_cannotNestEntryInEntry;
	public static String includePath_cannotNestEntryInLibrary;
	public static String includePath_cannotReadincludePathFile;
	public static String includePath_cannotReferToItself;
	public static String includePath_closedProject;
	public static String includePath_couldNotWriteincludePathFile;
	public static String includePath_cycle;
	public static String includePath_duplicateEntryPath;
	public static String includePath_illegalContainerPath;
	public static String includePath_illegalEntryInincludePathFile;
	public static String includePath_illegalLibraryPath;
	public static String includePath_illegalLibraryArchive;
	public static String includePath_illegalExternalFolder;
	public static String includePath_illegalProjectPath;
	public static String includePath_illegalSourceFolderPath;
	public static String includePath_illegalVariablePath;
	public static String includePath_invalidincludePathInincludePathFile;
	public static String includePath_invalidContainer;
	public static String includePath_unboundContainerPath;
	public static String includePath_unboundLibrary;
	public static String includePath_unboundProject;
	public static String includePath_settingProgress;
	public static String includePath_unboundSourceFolder;
	public static String includePath_unboundVariablePath;
	public static String includePath_unknownKind;
	public static String includePath_xmlFormatError;
	public static String includePath_duplicateEntryExtraAttribute;
	public static String file_notFound;
	public static String file_badFormat;
	public static String path_nullPath;
	public static String path_mustBeAbsolute;
	public static String cache_invalidLoadFactor;
    public static String zipEntryStorage_error;
    public static String ASTParser_1;
	public static String ExternalFileStore_0;
	public static String FileStoreFactory_0;
	public static String FileUtils_2;
	public static String FileUtils_3;
	public static String FileUtils_4;
	public static String FileUtils_5;
	public static String FullPhpProjectBuildVisitor_0;
	public static String IncludePathEntry_2;
	public static String IncludePathEntry_4;
	public static String IncludePathEntry_5;
	public static String IncludePathEntry_6;
	public static String PHPIncrementalProjectBuilder_0;
	public static String PHPProjectBuildJobWrapper_0;
	public static String PHPProjectOptions_1;
	public static String PHPWorkspaceModelManager_4;
	public static String PHPWorkspaceModelManager_5;
	public static String PHPWorkspaceModelManager_7;
	public static String PHPWorkspaceModelManager_8;
	public static String PHPWorkspaceModelManager_9;

	static {
		NLS.initializeMessages(BUNDLE_NAME, CoreMessages.class);
	}
	
	/**
	 * Bind the given message's substitution locations with the given string values.
	 * 
	 * @param message the message to be manipulated
	 * @return the manipulated String
	 */
	public static String bind(String message) {
		return bind(message, null);
	}
	
	/**
	 * Bind the given message's substitution locations with the given string values.
	 * 
	 * @param message the message to be manipulated
	 * @param binding the object to be inserted into the message
	 * @return the manipulated String
	 */
	public static String bind(String message, Object binding) {
		return bind(message, new Object[] {binding});
	}

	/**
	 * Bind the given message's substitution locations with the given string values.
	 * 
	 * @param message the message to be manipulated
	 * @param binding1 An object to be inserted into the message
	 * @param binding2 A second object to be inserted into the message
	 * @return the manipulated String
	 */
	public static String bind(String message, Object binding1, Object binding2) {
		return bind(message, new Object[] {binding1, binding2});
	}

	/**
	 * Bind the given message's substitution locations with the given string values.
	 * 
	 * @param message the message to be manipulated
	 * @param bindings An array of objects to be inserted into the message
	 * @return the manipulated String
	 */
	public static String bind(String message, Object[] bindings) {
		return MessageFormat.format(message, bindings);
	}
}
