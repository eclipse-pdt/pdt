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
package org.eclipse.php.internal.ui.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.internal.resources.ICoreConstants;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPIncludePathModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.containers.LocalFileStorageEditorInput;
import org.eclipse.php.internal.ui.containers.ZipEntryStorageEditorInput;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.input.PHPFileEditorInput;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.TextEditorAction;

public class EditorUtility {

	/**
	 * Appends to modifier string of the given SWT modifier bit
	 * to the given modifierString.
	 *
	 * @param modifierString	the modifier string
	 * @param modifier			an int with SWT modifier bit
	 * @return the concatenated modifier string
	 * @since 2.1.1
	 */
	private static String appendModifierString(String modifierString, final int modifier) {
		if (modifierString == null)
			modifierString = ""; //$NON-NLS-1$
		final String newModifierString = Action.findModifierString(modifier);
		if (modifierString.length() == 0)
			return newModifierString;
		return MessageFormat.format(PHPUIMessages.EditorUtility_concatModifierStrings, new String[] { modifierString, newModifierString });
	}

	private static ZipEntryStorageEditorInput createZipEntryStorageEditorInput(final ZipFile zipFile, final PHPCodeData element, final IProject project) {
		ZipInputStream is = null;
		ZipEntry ze = null;

		try {
			final File f = new File(zipFile.getName());
			is = new ZipInputStream(new FileInputStream(f));
			ze = is.getNextEntry();
			PHPFileData fileData = null;
			if (element instanceof PHPFileData)
				fileData = (PHPFileData) element;
			else
				fileData = PHPModelUtil.getPHPFileContainer(element);
			String phpFileName = fileData.getName();
			phpFileName = phpFileName.substring(phpFileName.indexOf(File.separatorChar) + 1); // removing the name of the zip file from the file name

			while (ze != null && !ze.getName().equals(phpFileName))
				ze = is.getNextEntry();
			if (ze == null)
				return null;

			final ZipEntryStorage zipEntryStorage = new ZipEntryStorage(zipFile, ze);
			zipEntryStorage.setProject(project);
			return new ZipEntryStorageEditorInput(zipEntryStorage);
		} catch (final FileNotFoundException e) {
			Logger.logException(e);
			return null;
		} catch (final IOException io) {
			Logger.logException(io);
			return null;
		}
	}

	/**
	 * Maps the localized modifier name to a code in the same
	 * manner as #findModifier.
	 *
	 * @param modifierName the modifier name
	 * @return the SWT modifier bit, or <code>0</code> if no match was found
	 * @since 2.1.1
	 */
	public static int findLocalizedModifier(final String modifierName) {
		if (modifierName == null)
			return 0;

		if (modifierName.equalsIgnoreCase(Action.findModifierString(SWT.CTRL)))
			return SWT.CTRL;
		if (modifierName.equalsIgnoreCase(Action.findModifierString(SWT.SHIFT)))
			return SWT.SHIFT;
		if (modifierName.equalsIgnoreCase(Action.findModifierString(SWT.ALT)))
			return SWT.ALT;
		if (modifierName.equalsIgnoreCase(Action.findModifierString(SWT.COMMAND)))
			return SWT.COMMAND;

		return 0;
	}

	/**
	 * If the current active editor edits a php element return it, else
	 * return null
	 */
	public static PHPCodeData getActiveEditorPHPInput() {
		final IWorkbenchPage page = PHPUiPlugin.getActivePage();
		if (page != null) {
			final IEditorPart part = page.getActiveEditor();
			if (part != null) {
				final IEditorInput editorInput = part.getEditorInput();
				if (editorInput != null)
					return (PHPCodeData) editorInput.getAdapter(PHPCodeData.class);
			}
		}
		return null;
	}

	private static IContentType getContentType(final File file) {
		if (file == null)
			return null;

		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
			return Platform.getContentTypeManager().findContentTypeFor(stream, file.getName());
		} catch (final IOException x) {
			Logger.logException(x);
			return null;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (final IOException x) {
				Logger.logException(x);
			}
		}
	}

	private static String getEditorId(final File file) {
		final IWorkbench workbench = PHPUiPlugin.getDefault().getWorkbench();
		final IEditorRegistry editorRegistry = workbench.getEditorRegistry();
		final IEditorDescriptor descriptor = editorRegistry.getDefaultEditor(file.getName(), getContentType(file));
		if (descriptor != null)
			return descriptor.getId();
		return null;
	}

	public static String getEditorID(final IEditorInput input) {
		IEditorDescriptor editorDescriptor;
		try {
			if (input instanceof LocalFileStorageEditorInput) {
				final LocalFileStorageEditorInput localFileInput = (LocalFileStorageEditorInput) input;
				final LocalFileStorage fileStorage = (LocalFileStorage) localFileInput.getStorage();
				if (fileStorage != null) {
					final File file = fileStorage.getFile();
					if (file != null) {
						final String id = getEditorId(file);
						if (id != null)
							return id;
					}
				}
			}
			editorDescriptor = IDE.getEditorDescriptor(input.getName());
		} catch (final PartInitException e) {
			return null;
		}

		if (editorDescriptor != null)
			return editorDescriptor.getId();

		return null;
	}

	public static IEditorInput getEditorInput(final Object input) {
		IProject project = null;
		if (input instanceof TreeItem) {
			project = getProject((TreeItem) input);
			final String incDir = getIncludeDirectory((TreeItem) input);
			return getEditorInput((PHPCodeData) ((TreeItem) input).getData(), project, incDir);
		}

		if (input instanceof PHPCodeData)
			return getEditorInput((PHPCodeData) input, null, null);

		if (input instanceof IFile)
			return new FileEditorInput((IFile) input);

		if (input instanceof IEditorInput)
			return (IEditorInput) input;

		return null;
	}

	private static IEditorInput getEditorInput(final PHPCodeData element, final IProject project, final String incDir) {

		final IResource resource = PHPModelUtil.getResource(element);
		if (resource == null || !resource.exists() || !resource.getProject().equals(project)) {
			final Object source = PHPModelUtil.getExternalResource(element, project);
			if (source instanceof File) {
				File externalSource = (File) source;
				Path path = new Path(externalSource.getPath());
				//check if external file
				if (ExternalFilesRegistry.getInstance().isEntryExist(path.toString())) {
					LocalFile locFile = new LocalFile(new File(path.toString()));
					final PHPFileEditorInput externalInput = new PHPFileEditorInput(locFile);
					return externalInput;
				}
				//include path file
				else {
					final LocalFileStorage fileStorage = new LocalFileStorage((File) source);
					fileStorage.setProject(project);
					fileStorage.setIncBaseDirName(incDir);
					return new LocalFileStorageEditorInput(fileStorage);
				}
			}
			if (source instanceof ZipFile)
				return createZipEntryStorageEditorInput((ZipFile) source, element, project);
		}
		if (resource instanceof IFile)
			return new FileEditorInput((IFile) resource);

		return null;
	}

	private static String getIncludeDirectory(TreeItem input) {
		if (!(input.getData() instanceof PHPCodeData))
			return null;
		PHPCodeData codeData = (PHPCodeData) input.getData();
		while (codeData != null && !(codeData instanceof PHPFileData)) {
			codeData = codeData.getContainer();
			input = input.getParentItem();
		}
		while (input != null && !(input.getData() instanceof PHPIncludePathModel))
			input = input.getParentItem();

		if (input == null)
			return null;
		PHPIncludePathModel includePathModel = (PHPIncludePathModel) input.getData();
		if (includePathModel.getType() == PHPIncludePathModel.TYPE_VARIABLE) {
			IPath includePath = IncludePathVariableManager.instance().getIncludePathVariable(includePathModel.getID());
			return includePath.toOSString();
		}
		return new Path(includePathModel.getID()).toOSString();
	}

	/**
	 * Returns the modifier string for the given SWT modifier
	 * modifier bits.
	 *
	 * @param stateMask	the SWT modifier bits
	 * @return the modifier string
	 * @since 2.1.1
	 */
	public static String getModifierString(final int stateMask) {
		String modifierString = ""; //$NON-NLS-1$
		if ((stateMask & SWT.CTRL) == SWT.CTRL)
			modifierString = appendModifierString(modifierString, SWT.CTRL);
		if ((stateMask & SWT.ALT) == SWT.ALT)
			modifierString = appendModifierString(modifierString, SWT.ALT);
		if ((stateMask & SWT.SHIFT) == SWT.SHIFT)
			modifierString = appendModifierString(modifierString, SWT.SHIFT);
		if ((stateMask & SWT.COMMAND) == SWT.COMMAND)
			modifierString = appendModifierString(modifierString, SWT.COMMAND);

		return modifierString;
	}

	public static PHPProjectModel getPHPProject(final IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			final IProject project = ((IFileEditorInput) input).getFile().getProject();
			if (project != null)
				return PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		}
		return null;
	}

	private static IProject getProject(TreeItem input) {
		if (!(input.getData() instanceof PHPCodeData))
			return null;
		PHPCodeData codeData = (PHPCodeData) input.getData();
		while (codeData != null && !(codeData instanceof PHPFileData)) {
			codeData = codeData.getContainer();
			input = input.getParentItem();
		}
		while (input != null && !(input.getData() instanceof IProject))
			input = input.getParentItem();

		if (input == null)
			return null;
		return (IProject) input.getData();
	}

	private static void initializeHighlightRange(final IEditorPart editorPart) {
		if (editorPart instanceof ITextEditor) {
			final IAction toggleAction = editorPart.getEditorSite().getActionBars().getGlobalActionHandler(ITextEditorActionDefinitionIds.TOGGLE_SHOW_SELECTED_ELEMENT_ONLY);
			boolean enable = toggleAction != null;
			enable = enable && toggleAction.isEnabled() && toggleAction.isChecked();
			if (enable)
				if (toggleAction instanceof TextEditorAction) {
					// Reset the action
					((TextEditorAction) toggleAction).setEditor(null);
					// Restore the action
					((TextEditorAction) toggleAction).setEditor((ITextEditor) editorPart);
				} else {
					// Un-check
					toggleAction.run();
					// Check
					toggleAction.run();
				}
		}
	}

	public static boolean isEditorInput(final Object element, final IEditorPart editor) {
		if (editor != null)
			return editor.getEditorInput().equals(getEditorInput(element));
		return false;
	}

	/**
	 * Tests if a CU is currently shown in an editor
	 * @return the IEditorPart if shown, null if element is not open in an editor
	 */
	public static IEditorPart isOpenInEditor(final Object inputElement) {
		IEditorInput input = null;

		input = getEditorInput(inputElement);

		if (input != null) {
			final IWorkbenchPage p = PHPUiPlugin.getActivePage();
			if (p != null)
				return p.findEditor(input);
		}

		return null;
	}

	public static IEditorPart openInEditor(final IEditorInput input, final String editorID, final boolean activate) throws PartInitException {
		if (input != null) {
			final IWorkbenchPage p = PHPUiPlugin.getActivePage();
			if (p != null) {
				final IEditorPart editorPart = p.openEditor(input, editorID, activate);
				initializeHighlightRange(editorPart);
				return editorPart;
			}
		}
		return null;
	}

	public static IEditorPart openInEditor(final IFile file, final boolean activate) throws PartInitException {
		if (file != null) {
			final IWorkbenchPage p = PHPUiPlugin.getActivePage();
			if (p != null) {
				final IEditorPart editorPart = IDE.openEditor(p, file, activate);
				initializeHighlightRange(editorPart);
				return editorPart;
			}
		}
		return null;
	}

	/**
	 * Opens a PHP editor for an element such as <code>PHPCodeData</code>, <code>IFile</code>, <code>IStorage</code> or <code>IEditorInput</code>.
	 * The editor is activated by default.
	 * @return the IEditorPart or null if wrong element type or opening failed
	 */
	public static IEditorPart openInEditor(final Object inputElement) throws PartInitException {
		return openInEditor(inputElement, true);
	}

	/**
	 * Opens a PHP editor for an element (PHPCodeData, IFile, IStorage...)
	 * @return the IEditorPart or null if wrong element type or opening failed
	 */
	public static IEditorPart openInEditor(final Object inputElement, final boolean activate) throws PartInitException {

		if (inputElement instanceof IFile)
			return openInEditor((IFile) inputElement, activate);

		final IEditorInput input = getEditorInput(inputElement);
		if (input instanceof IFileEditorInput) {
			final IFileEditorInput fileInput = (IFileEditorInput) input;
			return openInEditor(fileInput.getFile(), activate);
		}

		if (input != null)
			return openInEditor(input, getEditorID(input), activate);

		return null;
	}

	/**
	 * Opens a PHP editor for file and line number
	 * 
	 * @throws CoreException 
	 */
	public static IEditorPart openInEditor(final String fileName, final int lineNumber) throws CoreException {

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceRoot root = workspace.getRoot();

		IPath path = new Path(fileName);
		IFile file = root.getFileForLocation(path);
		if (file == null) {
			if (path.segmentCount() < ICoreConstants.MINIMUM_FILE_SEGMENT_LENGTH) {
				return null;
			}
			file = root.getFile(path);
			if (file == null) {
				final IProject[] projects = root.getProjects();
				for (int i = 0; i < projects.length; ++i) {
					if (!projects[i].isOpen())
						continue;
					file = projects[i].getFile(path);
					if (file != null)
						break;
				}
			}
		}

		if (file == null || !file.exists())
			return null;

		final IMarker marker = file.createMarker(IMarker.TEXT);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		//marker.setAttribute(IDE.EDITOR_ID_ATTR, PHPUiConstants.PHP_EDITOR_ID);
		final IWorkbenchPage p = PHPUiPlugin.getActivePage();

		IEditorPart editor = null;
		if (p != null)
			editor = IDE.openEditor(p, marker);
		marker.delete();
		return editor;
	}

	/**
	 * Selects and reveals the given offset and length in the given editor part.
	 */
	public static void revealInEditor(final IEditorPart editor, final int offset, final int length) {
		if (editor instanceof ITextEditor) {
			((ITextEditor) editor).selectAndReveal(offset, length);
			return;
		}

		// Support for non-text editor - try IGotoMarker interface
		if (editor instanceof IGotoMarker) {
			final IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				final IGotoMarker gotoMarkerTarget = (IGotoMarker) editor;
				final WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
					protected void execute(IProgressMonitor monitor) throws CoreException {
						IMarker marker = null;
						try {
							marker = ((IFileEditorInput) input).getFile().createMarker(IMarker.TEXT);
							marker.setAttribute(IMarker.CHAR_START, offset);
							marker.setAttribute(IMarker.CHAR_END, offset + length);

							gotoMarkerTarget.gotoMarker(marker);

						} finally {
							if (marker != null)
								marker.delete();
						}
					}
				};

				try {
					op.run(null);
				} catch (final InvocationTargetException ex) {
					// reveal failed
				} catch (final InterruptedException e) {
					Assert.isTrue(false, "this operation can not be canceled"); //$NON-NLS-1$
				}
			}
			return;
		}

		if (editor != null && editor.getEditorSite().getSelectionProvider() != null) {
			final IEditorSite site = editor.getEditorSite();
			if (site == null)
				return;

			final ISelectionProvider provider = editor.getEditorSite().getSelectionProvider();
			if (provider == null)
				return;

			provider.setSelection(new TextSelection(offset, length));
		}
	}

	/**
	 * Selects and reveals the given region in the given editor part.
	 */
	public static void revealInEditor(final IEditorPart part, final IRegion region) {
		if (part != null && region != null)
			revealInEditor(part, region.getOffset(), region.getLength());
	}

	/**
	 * Selects a PHP Element in an editor
	 */
	public static void revealInEditor(final IEditorPart part, final PHPCodeData element) {
		if (element == null)
			return;

		final PHPStructuredEditor phpEditor = EditorUtility.getPHPStructuredEditor(part);
		if (phpEditor != null) {
			phpEditor.setSelection(element, true);
			return;
		}

		int offset = -1;
		int length = 0;
		//		if (element instanceof PHPCodeData) {
		final UserData userData = /*(PHPCodeData)*/element.getUserData();
		if (userData != null) {
			offset = userData.getStartPosition();
			length = userData.getEndPosition() - userData.getStartPosition() + 1;
		}
		//		}
		if (offset >= 0)
			revealInEditor(part, offset, length);
	}

	/**
	 * @param editor
	 * @return the php editor (if exists) from the given editor
	 * NOTE: editors that wants to work with PHP editor actions must implement the getAdapter() method
	 *       this way the actions pick the php editor...
	 */
	public static final PHPStructuredEditor getPHPStructuredEditor(final IWorkbenchPart editor) {
		return editor != null ? (PHPStructuredEditor) editor.getAdapter(PHPStructuredEditor.class) : null;
	}
	
	/**
	 * Gets a list of File full paths as strings and open them in the editor
	 * works for both workspace and non- workspace files 
	 * @param filesToOpen
	 * @param window
	 */
	public static void openFilesInEditor(List filesToOpen, IWorkbenchWindow window){
		PHPOpenExternalFileAction action = new PHPOpenExternalFileAction();
		action.init(window);
		action.run(filesToOpen);
	}
	
	/**
	 * @see openFilesInEditor(List, IWorkbenchWindow)
	 * @param filesToOpen
	 */
	public static void openFilesInEditor(List filesToOpen){
		openFilesInEditor(filesToOpen, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}
	
	
}
