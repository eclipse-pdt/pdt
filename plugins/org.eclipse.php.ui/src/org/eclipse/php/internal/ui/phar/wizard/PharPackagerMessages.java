/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Ferenc Hechler, ferenc_hechler@users.sourceforge.net - 83258 [jar exporter] Deploy java application as executable jar
 *     Ferenc Hechler, ferenc_hechler@users.sourceforge.net - 219530 [jar application] add Jar-in-Jar ClassLoader option
 *******************************************************************************/
package org.eclipse.php.internal.ui.phar.wizard;

import org.eclipse.osgi.util.NLS;

public final class PharPackagerMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.phar.wizard.PharPackagerMessages"; //$NON-NLS-1$

	public static String AbstractJarDestinationWizardPage_destinationCombo_AccessibilityText;

	public static String CreateJarActionDelegate_jarExport_title;

	public static String CreateJarActionDelegate_jarExportError_message;

	public static String CreateJarActionDelegate_jarExportError_title;

	public static String JarFileExportOperation_canNotExportExternalClassFolder_warning;

	public static String JarFileExportOperation_cantGetRootKind;

	public static String JarFileExportOperation_classFileOnClasspathNotAccessible;

	public static String JarFileExportOperation_classFileWithoutSourceFileAttribute;

	public static String JarFileExportOperation_coreErrorDuringExport;

	public static String JarFileExportOperation_creationOfSomeJARsFailed;

	public static String JarFileExportOperation_OpenZipFileError_message;

	public static String JarFileExportOperation_didNotAddManifestToJar;

	public static String JarFileExportOperation_errorCannotCloseConnection;

	public static String JarFileExportOperation_errorClosingJarPackageDescriptionReader;

	public static String JarFileExportOperation_errorDuringExport;

	public static String JarFileExportOperation_errorDuringProjectBuild;

	public static String JarFileExportOperation_errorReadingFile;

	public static String JarFileExportOperation_errorSavingDescription;

	public static String JarFileExportOperation_errorSavingManifest;

	public static String JarFileExportOperation_CloseZipFileError_message;

	public static String JarFileExportOperation_exportedWithCompileErrors;

	public static String JarFileExportOperation_exportedWithCompileWarnings;

	public static String JarFileExportOperation_exportFinishedWithInfo;

	public static String JarFileExportOperation_exportFinishedWithWarnings;

	public static String JarFileExportOperation_exporting;

	public static String JarFileExportOperation_fileUnsaved;

	public static String JarFileExportOperation_invalidJarLocation;

	public static String JarFileExportOperation_invalidMainClass;

	public static String JarFileExportOperation_jarCreationFailed;

	public static String JarFileExportOperation_jarCreationFailedSeeDetails;

	public static String JarFileExportOperation_jarFileExistsAndNotWritable;

	public static String JarFileExportOperation_javaPackageNotDeterminable;

	public static String JarFileExportOperation_manifestDoesNotExist;

	public static String JarFileExportOperation_missingSourceFileAttributeExportedAll;

	public static String JarFileExportOperation_noExportTypeChosen;

	public static String JarFileExportOperation_noResourcesSelected;

	public static String JarFileExportOperation_notExportedDueToCompileErrors;

	public static String JarFileExportOperation_notExportedDueToCompileWarnings;

	public static String JarFileExportOperation_outputContainerNotAccessible;

	public static String JarFileExportOperation_projectNatureNotDeterminable;

	public static String JarFileExportOperation_resourceNotFound;

	public static String JarFileExportOperation_savingFiles;

	public static String JarManifestWizardPage_description;

	public static String JarManifestWizardPage_error_invalidMainClass;

	public static String JarManifestWizardPage_error_invalidManifestFile;

	public static String JarManifestWizardPage_error_jarPackageWizardError_message;

	public static String JarManifestWizardPage_error_jarPackageWizardError_title;

	public static String JarManifestWizardPage_error_manifestContainerDoesNotExist;

	public static String JarManifestWizardPage_error_manifestMustNotBeExistingContainer;

	public static String JarManifestWizardPage_error_manifestPathMustBeAbsolute;

	public static String JarManifestWizardPage_error_mustContainPackages;

	public static String JarManifestWizardPage_error_noManifestFile;

	public static String JarManifestWizardPage_error_noResourceSelected;

	public static String JarManifestWizardPage_error_onlyOneManifestMustBeSelected;

	public static String JarManifestWizardPage_error_sealedPackagesNotInSelection;

	public static String JarManifestWizardPage_error_unsealedPackagesNotInSelection;

	public static String JarManifestWizardPage_genetateManifest_text;

	public static String JarManifestWizardPage_jarSealed;

	public static String JarManifestWizardPage_jarSealedExceptOne;

	public static String JarManifestWizardPage_jarSealedExceptSome;

	public static String JarManifestWizardPage_mainClass_label;

	public static String JarManifestWizardPage_mainClassBrowseButton_text;

	public static String JarManifestWizardPage_mainClassHeader_label;

	public static String JarManifestWizardPage_mainTypeSelectionDialog_message;

	public static String JarManifestWizardPage_mainTypeSelectionDialog_title;

	public static String JarManifestWizardPage_manifestFile_text;

	public static String JarManifestWizardPage_manifestFileBrowse_text;

	public static String JarManifestWizardPage_manifestSelectionDialog_message;

	public static String JarManifestWizardPage_manifestSelectionDialog_title;

	public static String JarManifestWizardPage_manifestSource_label;

	public static String JarManifestWizardPage_newManifestFile_text;

	public static String JarManifestWizardPage_newManifestFileBrowseButton_text;

	public static String JarManifestWizardPage_nothingSealed;

	public static String JarManifestWizardPage_onePackageSealed;

	public static String JarManifestWizardPage_reuseManifest_text;

	public static String JarManifestWizardPage_saveAsDialog_message;

	public static String JarManifestWizardPage_saveAsDialog_title;

	public static String JarManifestWizardPage_saveManifest_text;

	public static String JarManifestWizardPage_sealedPackagesDetailsButton_text;

	public static String JarManifestWizardPage_sealedPackagesSelectionDialog_message;

	public static String JarManifestWizardPage_sealedPackagesSelectionDialog_title;

	public static String JarManifestWizardPage_sealingHeader_label;

	public static String JarManifestWizardPage_sealJar_text;

	public static String JarManifestWizardPage_sealPackagesButton_text;

	public static String JarManifestWizardPage_somePackagesSealed;

	public static String JarManifestWizardPage_title;

	public static String JarManifestWizardPage_unsealedPackagesSelectionDialog_message;

	public static String JarManifestWizardPage_unsealedPackagesSelectionDialog_title;

	public static String JarManifestWizardPage_unsealPackagesButton_text;

	public static String JarManifestWizardPage_useManifest_text;

	public static String JarManifestWizardPage_warning_noManifestVersion;

	public static String JarOptionsPage_browseButton_text;

	public static String JarOptionsPage_buildIfNeeded;

	public static String JarOptionsPage_description;

	public static String JarOptionsPage_descriptionFile_label;

	public static String JarOptionsPage_error_descriptionContainerDoesNotExist;

	public static String JarOptionsPage_error_descriptionMustBeAbsolute;

	public static String JarOptionsPage_error_descriptionMustNotBeExistingContainer;

	public static String JarOptionsPage_error_invalidDescriptionExtension;

	public static String JarOptionsPage_exportErrors_text;

	public static String JarOptionsPage_exportWarnings_text;

	public static String JarOptionsPage_howTreatProblems_label;

	public static String JarOptionsPage_saveAsDialog_message;

	public static String JarOptionsPage_saveAsDialog_title;

	public static String JarOptionsPage_saveDescription_text;

	public static String JarOptionsPage_title;

	public static String JarOptionsPage_useSourceFoldersHierarchy;

	public static String JarPackage_confirmCreate_message;

	public static String JarPackage_confirmCreate_title;

	public static String JarPackage_confirmReplace_message;

	public static String JarPackage_confirmReplace_title;

	public static String JarPackage_confirmOverwriteFolder_message;

	public static String JarPackage_confirmOverwriteFolder_title;

	public static String JarPackageReader_error_badFormat;

	public static String JarPackageReader_error_duplicateTag;

	public static String JarPackageReader_error_illegalValueForBooleanAttribute;

	public static String JarPackageReader_error_tagHandleIdentifierNotFoundOrEmpty;

	public static String JarPackageReader_error_tagNameNotFound;

	public static String JarPackageReader_error_tagPathNotFound;

	public static String JarPackageReader_error_unknownJarBuilder;

	public static String JarPackageReader_jarPackageReaderWarnings;

	public static String JarPackageReader_warning_javaElementDoesNotExist;

	public static String JarPackageReader_warning_mainClassDoesNotExist;

	public static String JarPackageWizard_jarExport_title;

	public static String JarPackageWizard_jarExportError_message;

	public static String JarPackageWizard_jarExportError_title;

	public static String JarPackageWizard_windowTitle;

	public static String JarPackageWizardPage_browseButton_text;

	public static String JarPackageWizardPage_compress_text;

	public static String JarPackageWizardPage_configure_label;

	public static String JarPackageWizardPage_configure_tooltip;

	public static String JarPackageWizardPage_description;

	public static String JarPackageWizardPage_destination_label;

	public static String JarPackageWizardPage_error_cantExportJARIntoItself;

	public static String JarPackageWizardPage_error_caption;

	public static String JarPackageWizardPage_error_exportDestinationMustNotBeDirectory;

	public static String JarPackageWizardPage_error_jarFileExistsAndNotWritable;

	public static String JarPackageWizardPage_error_label;

	public static String JarPackageWizardPage_error_noExportTypeChecked;

	public static String JarPackageWizardPage_exportClassFiles_text;

	public static String JarPackageWizardPage_exportJavaFiles_text;

	public static String JarPackageWizardPage_exportOutputFolders_text;

	public static String JarPackageWizardPage_includeDirectoryEntries_text;

	public static String JarPackageWizardPage_info_relativeExportDestination;

	public static String JarPackageWizardPage_no_refactorings_selected;

	public static String JarPackageWizardPage_options_label;

	public static String JarPackageWizardPage_overwrite_text;

	public static String JarPackageWizardPage_refactorings_text;

	public static String JarPackageWizardPage_table_accessibility_message;

	public static String JarPackageWizardPage_title;

	public static String JarPackageWizardPage_tree_accessibility_message;

	public static String JarPackageWizardPage_whatToExport_label;

	public static String JarPackageWizardPage_whereToExport_label;

	public static String JarRefactoringDialog_dialog_title;

	public static String JarRefactoringDialog_export_structural;

	public static String JarRefactoringDialog_workspace_caption;

	public static String JarWriter_error_couldNotGetXmlBuilder;

	public static String JarWriter_error_couldNotTransformToXML;

	public static String JarWriter_writeProblem;

	public static String JarWriter_writeProblemWithMessage;

	public static String OpenJarPackageWizardDelegate_error_openJarPackager_message;

	public static String OpenJarPackageWizardDelegate_error_openJarPackager_title;

	public static String OpenJarPackageWizardDelegate_jarDescriptionReaderWarnings_title;

	public static String OpenJarPackageWizardDelegate_onlyJardesc;

	public static String JarPackageWizardPage_Export_Type;

	public static String JarPackageWizardPage_Export_Type_phar;

	public static String JarPackageWizardPage_Export_Type_zip;

	public static String JarPackageWizardPage_Export_Type_tar;

	public static String JarPackageWizardPage_Compress_Type;

	public static String JarPackageWizardPage_Compress_Type_none;

	public static String JarPackageWizardPage_Compress_Type_bz2;

	public static String JarPackageWizardPage_Compress_Type_gz;

	public static String JarPackageWizardPage_Compress_Type_zip;

	public static String JarPackageWizardPage_PHAR_file;

	public static String JarPackageWizardPage_PHAR_file_extension_tar1;

	public static String JarPackageWizardPage_PHAR_file_extension_tar2;

	public static String JarPackageWizardPage_PHAR_file_extension_tar3;

	public static String JarPackageWizardPage_PHAR_file_extension_tar4;

	public static String JarPackageWizardPage_error_exportPharnameMustBeSet;

	public static String JarPackageWizardPage_Signature_Type;

	public static String JarPackageWizardPage_Title;

	public static String JarPackageWizardPage_no;

	public static String JarPackageWizardPage_yes;

	public static String JarPackageWizardPage_error_cantExportPHARIntoItself;

	public static String JarPackageWizardPage_error_StubFileNull;

	public static String PharFileExportOperation_10;

	public static String PharFileExportOperation_11;

	public static String PharFileExportOperation_12;

	public static String PharFileExportOperation_13;

	static {
		NLS.initializeMessages(BUNDLE_NAME, PharPackagerMessages.class);
	}

	private PharPackagerMessages() {
		// Do not instantiate
	}
}
