/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/

package org.eclipse.php.internal.debug.ui.launching;

import java.io.File;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.*;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPExeLaunchShortcut implements ILaunchShortcut2 {

	/**
	 * PHPExeLaunchShortcut constructor.
	 */
	public PHPExeLaunchShortcut() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.jface.viewers
	 * .ISelection, java.lang.String)
	 */
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			searchAndLaunch(((IStructuredSelection) selection).toArray(), mode,
					getPHPExeLaunchConfigType());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.ui.IEditorPart,
	 * java.lang.String)
	 */
	public void launch(IEditorPart editor, String mode) {
		IEditorInput input = editor.getEditorInput();
		IFile file = (IFile) input.getAdapter(IFile.class);
		if (file == null) {
			IPath path = null;
			try {
				if (input instanceof IStorageEditorInput) {
					IStorageEditorInput editorInput = (IStorageEditorInput) input;
					IStorage storage = editorInput.getStorage();
					path = storage.getFullPath();
				} else if (input instanceof IURIEditorInput) {
					path = URIUtil.toPath(((IURIEditorInput) input).getURI());
				} else if (input instanceof NonExistingPHPFileEditorInput) {
					// handle untitled document debugging
					// first save the file to the disk and after that set the
					// document as dirty
					if (editor instanceof ITextEditor) {
						ITextEditor textEditor = (ITextEditor) editor;
						final TextFileDocumentProvider documentProvider = (TextFileDocumentProvider) textEditor
								.getDocumentProvider();
						final IDocument document = documentProvider
								.getDocument(input);
						documentProvider.saveDocument(null, input, document,
								true);
						// set document dirty
						document.replace(0, 0, ""); //$NON-NLS-1$
					}
					path = ((NonExistingPHPFileEditorInput) input)
							.getPath(input);// Untitled
					// dummy
					// path
				}
				if (path != null) {
					File systemFile = new File(path.toOSString());
					if (systemFile.exists()) {
						searchAndLaunch(new Object[] { systemFile }, mode,
								getPHPExeLaunchConfigType());
					}
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		} else {
			searchAndLaunch(new Object[] { file }, mode,
					getPHPExeLaunchConfigType());
		}
	}

	protected ILaunchConfigurationType getPHPExeLaunchConfigType() {
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm
				.getLaunchConfigurationType(IPHPDebugConstants.PHPEXELaunchType);
	}

	public static void searchAndLaunch(Object[] search, String mode,
			ILaunchConfigurationType configType) {
		int entries = search == null ? 0 : search.length;
		for (int i = 0; i < entries; i++) {
			try {
				String phpPathString = null;
				String phpFileLocation = null;
				IProject project = null;
				Object obj = search[i];
				IResource res = null;
				if (obj instanceof IModelElement) {
					IModelElement elem = (IModelElement) obj;

					if (elem instanceof ISourceModule) {
						res = ((ISourceModule) elem).getCorrespondingResource();
					} else if (elem instanceof IType) {
						res = ((IType) elem).getUnderlyingResource();
					} else if (elem instanceof IMethod) {
						res = ((IMethod) elem).getUnderlyingResource();
					}
					if (res instanceof IFile) {
						obj = (IFile) res;
					}
				}
				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					res = file;
					project = file.getProject();
					IContentType contentType = Platform.getContentTypeManager()
							.getContentType(
									ContentTypeIdForPHP.ContentTypeID_PHP);
					if (contentType.isAssociatedWith(file.getName())) {
						if (new File(file.getFullPath().toOSString()).exists()) {
							phpPathString = file.getFullPath().toOSString();
						} else {
							phpPathString = file.getFullPath().toString();
						}
						IPath location = file.getLocation();
						// check for non null values - EFS issues
						if (location != null) {
							phpFileLocation = location.toOSString();
						} else {
							phpFileLocation = file.getFullPath().toString();
						}
					}
				} else if (obj instanceof File) {
					File systemFile = (File) obj;
					phpPathString = systemFile.getAbsolutePath();
					phpFileLocation = phpPathString;
				}

				if (phpPathString == null) {
					// Could not find target to launch
					throw new CoreException(new Status(IStatus.ERROR,
							PHPDebugUIPlugin.ID, IStatus.OK,
							PHPDebugUIMessages.launch_failure_no_target, null));
				}

				PHPexeItem defaultEXE = getDefaultPHPExe(project);
				String phpExeName = (defaultEXE != null) ? defaultEXE
						.getExecutable().getAbsolutePath().toString() : null;

				if (phpExeName == null) {
					MessageDialog.openError(PHPDebugUIPlugin
							.getActiveWorkbenchShell(),
							PHPDebugUIMessages.launch_noexe_msg_title,
							PHPDebugUIMessages.launch_noexe_msg_text);
					PreferencesUtil
							.createPreferenceDialogOn(
									PHPDebugUIPlugin.getActiveWorkbenchShell(),
									"org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage", //$NON-NLS-1$
									null, null).open();
					return;
				}

				// Launch the app
				ILaunchConfiguration config = findLaunchConfiguration(project,
						phpPathString, phpFileLocation, defaultEXE, mode,
						configType, res);
				if (config != null) {
					DebugUITools.launch(config, mode);
				} else {
					// Could not find launch configuration
					throw new CoreException(new Status(IStatus.ERROR,
							PHPDebugUIPlugin.ID, IStatus.OK,
							PHPDebugUIMessages.launch_failure_no_config, null));
				}
			} catch (CoreException ce) {
				final IStatus stat = ce.getStatus();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						ErrorDialog
								.openError(
										PHPDebugUIPlugin
												.getActiveWorkbenchShell(),
										PHPDebugUIMessages.launch_failure_msg_title,
										PHPDebugUIMessages.launch_failure_exec_msg_text,
										stat);
					}
				});
			}
		}
	}

	// Returns the default php executable name for the current project.
	// In case the project does not have any special settings, return the
	// workspace default.
	private static PHPexeItem getDefaultPHPExe(IProject project) {
		PHPexeItem defaultItem = PHPDebugPlugin.getPHPexeItem(project);
		if (defaultItem != null) {
			return defaultItem;
		}
		return PHPDebugPlugin.getWorkspaceDefaultExe();
	}

	// Creates a preferences scope for the given project.
	// This scope will be used to search for preferences values.
	private static IScopeContext[] createPreferenceScopes(IProject project) {
		if (project != null) {
			return new IScopeContext[] { new ProjectScope(project),
					new InstanceScope(), new DefaultScope() };
		}
		return new IScopeContext[] { new InstanceScope(), new DefaultScope() };
	}

	/**
	 * Locate a configuration to relaunch for the given type. If one cannot be
	 * found, create one.
	 * 
	 * @param res
	 * 
	 * @return a re-useable config or <code>null</code> if none
	 */
	protected static ILaunchConfiguration findLaunchConfiguration(
			IProject phpProject, String phpPathString,
			String phpFileFullLocation, PHPexeItem defaultEXE, String mode,
			ILaunchConfigurationType configType, IResource res) {
		ILaunchConfiguration config = null;

		try {
			ILaunchConfiguration[] configs = DebugPlugin.getDefault()
					.getLaunchManager().getLaunchConfigurations(configType);

			int numConfigs = configs == null ? 0 : configs.length;
			for (int i = 0; i < numConfigs; i++) {
				String fileName = configs[i].getAttribute(
						IPHPDebugConstants.ATTR_FILE, (String) null);
				String exeName = configs[i].getAttribute(
						IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION,
						(String) null);
				String iniPath = configs[i].getAttribute(
						IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
				PHPexeItem item = PHPexes.getInstance().getItemForFile(exeName,
						iniPath);

				if (phpPathString.equals(fileName)/* && defaultEXE.equals(item) */) {
					config = configs[i];
					break;
				}
			}

			if (config == null) {
				config = createConfiguration(phpProject, phpPathString,
						phpFileFullLocation, defaultEXE, configType, res);
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
		return config;
	}

	/**
	 * Create & return a new configuration
	 * 
	 * @param res
	 */
	protected static ILaunchConfiguration createConfiguration(
			IProject phpProject, String phpPathString,
			String phpFileFullLocation, PHPexeItem defaultEXE,
			ILaunchConfigurationType configType, IResource res)
			throws CoreException {
		ILaunchConfiguration config = null;
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null,
				getNewConfigurationName(phpPathString));

		// Set the delegate class according to selected executable.
		wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, defaultEXE
				.getDebuggerID());
		AbstractDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry
				.getDebuggerConfiguration(defaultEXE.getDebuggerID());
		wc.setAttribute(
				PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS,
				debuggerConfiguration.getScriptLaunchDelegateClass());
		wc.setAttribute(IPHPDebugConstants.ATTR_FILE, phpPathString);
		wc.setAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH,
				phpFileFullLocation);
		wc.setAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, defaultEXE
				.getExecutable().getAbsolutePath().toString());
		String iniPath = defaultEXE.getINILocation() != null ? defaultEXE
				.getINILocation().toString() : null;
		wc.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, iniPath);
		wc.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, PHPDebugPlugin
				.getDebugInfoOption());
		wc.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
				PHPProjectPreferences.getStopAtFirstLine(phpProject));
		if (res != null) {
			wc.setMappedResources(new IResource[] { res });
		}
		config = wc.doSave();

		return config;
	}

	/**
	 * Returns a name for a newly created launch configuration according to the
	 * given file name. In case the name generation fails, return the
	 * "New_configuration" string.
	 * 
	 * @param fileName
	 *            The original file name that this shortcut shoul execute.
	 * @return The new configuration name, or "New_configuration" in case it
	 *         fails for some reason.
	 */
	protected static String getNewConfigurationName(String fileName) {
		String configurationName = PHPDebugUIMessages.PHPExeLaunchShortcut_0; 
		try {
			IPath path = Path.fromOSString(fileName);

			NonExistingPHPFileEditorInput editorInput = NonExistingPHPFileEditorInput
					.findEditorInput(path);
			if (editorInput != null) {
				path = new Path(editorInput.getName());
			}

			String fileExtention = path.getFileExtension();
			String lastSegment = path.lastSegment();
			if (lastSegment != null) {
				if (fileExtention != null) {
					lastSegment = lastSegment.replaceFirst("." + fileExtention, //$NON-NLS-1$
							""); //$NON-NLS-1$
				}
				configurationName = lastSegment;
			}
		} catch (Exception e) {
			Logger.log(Logger.WARNING_DEBUG,
					"Could not generate configuration name for " + fileName //$NON-NLS-1$
							+ ".\nThe default name will be used.", e); //$NON-NLS-1$
		}
		return DebugPlugin.getDefault().getLaunchManager()
				.generateUniqueLaunchConfigurationNameFrom(configurationName);
	}

	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		return null;
	}

	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse
	 * .ui.IEditorPart)
	 */
	public IResource getLaunchableResource(IEditorPart editorpart) {
		return getLaunchableResource(editorpart.getEditorInput());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse
	 * .jface.viewers.ISelection)
	 */
	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			if (ss.size() == 1) {
				Object element = ss.getFirstElement();
				if (element instanceof IAdaptable) {
					return getLaunchableResource((IAdaptable) element);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the resource containing the Java element associated with the
	 * given adaptable, or <code>null</code>.
	 * 
	 * @param adaptable
	 *            adaptable object
	 * @return containing resource or <code>null</code>
	 */
	private IResource getLaunchableResource(IAdaptable adaptable) {
		IModelElement je = (IModelElement) adaptable
				.getAdapter(IModelElement.class);
		if (je != null) {
			return je.getResource();
		}
		return null;
	}
}
