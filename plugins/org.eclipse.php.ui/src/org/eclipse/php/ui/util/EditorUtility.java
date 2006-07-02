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
package org.eclipse.php.ui.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.Logger;
import org.eclipse.php.core.containers.LocalFileStorage;
import org.eclipse.php.core.containers.ZipEntryStorage;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPIncludePathModel;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.containers.LocalFileStorageEditorInput;
import org.eclipse.php.ui.containers.ZipEntryStorageEditorInput;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
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

	public static boolean isEditorInput(Object element, IEditorPart editor) {
		if (editor != null) {
			return editor.getEditorInput().equals(getEditorInput(element));
		}
		return false;
	}

	/**
	 * Tests if a CU is currently shown in an editor
	 * @return the IEditorPart if shown, null if element is not open in an editor
	 */
	public static IEditorPart isOpenInEditor(Object inputElement) {
		IEditorInput input = null;

		input = getEditorInput(inputElement);

		if (input != null) {
			IWorkbenchPage p = PHPUiPlugin.getActivePage();
			if (p != null) {
				return p.findEditor(input);
			}
		}

		return null;
	}

	/**
	 * Opens a PHP editor for file and line number
	 * 
	 * @param file name
	 * @param line number
	 * @throws CoreException 
	 */
	public static void openInEditor(String fileName, int lineNumber) throws CoreException {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();

		Path path = new Path(fileName);
		IFile file = root.getFile(path);
		if (file == null) { // full path specified
			file = root.getFileForLocation(path);
		}

		IMarker marker = file.createMarker(IMarker.TEXT);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		//marker.setAttribute(IDE.EDITOR_ID_ATTR, PHPUiConstants.PHP_EDITOR_ID);
		IWorkbenchPage p = PHPUiPlugin.getActivePage();
		if (p != null) {
			IDE.openEditor(p, marker);
		}
		marker.delete();
	}

	/**
	 * Opens a PHP editor for an element such as <code>PHPCodeData</code>, <code>IFile</code>, or <code>IStorage</code>.
	 * The editor is activated by default.
	 * @return the IEditorPart or null if wrong element type or opening failed
	 */
	public static IEditorPart openInEditor(Object inputElement) throws PartInitException {
		return openInEditor(inputElement, true);
	}

	/**
	 * Opens a PHP editor for an element (PHPCodeData, IFile, IStorage...)
	 * @return the IEditorPart or null if wrong element type or opening failed
	 */
	public static IEditorPart openInEditor(Object inputElement, boolean activate) throws PartInitException {

		if (inputElement instanceof IFile)
			return openInEditor((IFile) inputElement, activate);

		IEditorInput input = getEditorInput(inputElement);
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput) input;
			return openInEditor(fileInput.getFile(), activate);
		}

		if (input != null)
			return openInEditor(input, getEditorID(input), activate);

		return null;
	}

	/**
	 * Selects a PHP Element in an editor
	 */
	public static void revealInEditor(IEditorPart part, PHPCodeData element) {
		if (element == null)
			return;

		if (part instanceof PHPStructuredEditor) {
			((PHPStructuredEditor) part).setSelection(element, true);
			return;
		}

		int offset = -1;
		int length = 0;
		if (element instanceof PHPCodeData) {
			UserData userData = ((PHPCodeData) element).getUserData();
			if (userData != null) {
				offset = userData.getStartPosition();
				length = userData.getEndPosition() - userData.getStartPosition() + 1;
			}
		}
		if (offset >= 0)
			revealInEditor(part, offset, length);
	}

	/**
	 * Selects and reveals the given region in the given editor part.
	 */
	public static void revealInEditor(IEditorPart part, IRegion region) {
		if (part != null && region != null)
			revealInEditor(part, region.getOffset(), region.getLength());
	}

	/**
	 * Selects and reveals the given offset and length in the given editor part.
	 */
	public static void revealInEditor(IEditorPart editor, final int offset, final int length) {
		if (editor instanceof ITextEditor) {
			((ITextEditor) editor).selectAndReveal(offset, length);
			return;
		}

		// Support for non-text editor - try IGotoMarker interface
		if (editor instanceof IGotoMarker) {
			final IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				final IGotoMarker gotoMarkerTarget = (IGotoMarker) editor;
				WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
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
				} catch (InvocationTargetException ex) {
					// reveal failed
				} catch (InterruptedException e) {
					Assert.isTrue(false, "this operation can not be canceled"); //$NON-NLS-1$
				}
			}
			return;
		}

		if (editor != null && editor.getEditorSite().getSelectionProvider() != null) {
			IEditorSite site = editor.getEditorSite();
			if (site == null)
				return;

			ISelectionProvider provider = editor.getEditorSite().getSelectionProvider();
			if (provider == null)
				return;

			provider.setSelection(new TextSelection(offset, length));
		}
	}

	public static IEditorPart openInEditor(IFile file, boolean activate) throws PartInitException {
		if (file != null) {
			IWorkbenchPage p = PHPUiPlugin.getActivePage();
			if (p != null) {
				IEditorPart editorPart = IDE.openEditor(p, file, activate);
				initializeHighlightRange(editorPart);
				return editorPart;
			}
		}
		return null;
	}

	private static IEditorPart openInEditor(IEditorInput input, String editorID, boolean activate) throws PartInitException {
		if (input != null) {
			IWorkbenchPage p = PHPUiPlugin.getActivePage();
			if (p != null) {
				IEditorPart editorPart = p.openEditor(input, editorID, activate);
				initializeHighlightRange(editorPart);
				return editorPart;
			}
		}
		return null;
	}

	private static void initializeHighlightRange(IEditorPart editorPart) {
		if (editorPart instanceof ITextEditor) {
			IAction toggleAction = editorPart.getEditorSite().getActionBars().getGlobalActionHandler(ITextEditorActionDefinitionIds.TOGGLE_SHOW_SELECTED_ELEMENT_ONLY);
			boolean enable = toggleAction != null;
			enable = enable && toggleAction.isEnabled() && toggleAction.isChecked();
			if (enable) {
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
	}

	public static String getEditorID(IEditorInput input) {
		IEditorDescriptor editorDescriptor;
		try {
			if (input instanceof LocalFileStorageEditorInput) {
				LocalFileStorageEditorInput localFileInput = (LocalFileStorageEditorInput) input;
				LocalFileStorage fileStorage = (LocalFileStorage) localFileInput.getStorage();
				if (fileStorage != null) {
					File file = fileStorage.getFile();
					if (file != null) {
						String id = getEditorId(file);
						if (id != null) {
							return id;
						}
					}
				}
			}
			editorDescriptor = IDE.getEditorDescriptor(input.getName());
		} catch (PartInitException e) {
			return null;
		}

		if (editorDescriptor != null)
			return editorDescriptor.getId();

		return null;
	}

	private static String getEditorId(File file) {
		IWorkbench workbench = PHPUiPlugin.getDefault().getWorkbench();
		IEditorRegistry editorRegistry = workbench.getEditorRegistry();
		IEditorDescriptor descriptor = editorRegistry.getDefaultEditor(file.getName(), getContentType(file));
		if (descriptor != null)
			return descriptor.getId();
		return null;
	}

	private static IContentType getContentType(File file) {
		if (file == null)
			return null;

		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
			return Platform.getContentTypeManager().findContentTypeFor(stream, file.getName());
		} catch (IOException x) {
			Logger.logException(x);
			return null;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException x) {
				Logger.logException(x);
			}
		}
	}

	private static IEditorInput getEditorInput(PHPCodeData element, IProject project, String incDir) {

		IResource resource = PHPModelUtil.getResource(element);
		if (resource == null || !resource.exists() || !resource.getProject().equals(project)) {
			Object source = PHPModelUtil.getExternalResource(element, project);
			if (source instanceof File) {
				LocalFileStorage fileStorage = new LocalFileStorage((File) source);
				fileStorage.setProject(project);
				fileStorage.setIncBaseDirName(incDir);
				return new LocalFileStorageEditorInput(fileStorage);
			}
			if (source instanceof ZipFile) {
				return createZipEntryStorageEditorInput((ZipFile) source, element, project);
			}
		}
		if (resource instanceof IFile) {
			return new FileEditorInput((IFile) resource);
		}

		return null;
	}

	private static ZipEntryStorageEditorInput createZipEntryStorageEditorInput(ZipFile zipFile, PHPCodeData element, IProject project) {
		ZipInputStream is = null;
		ZipEntry ze = null;

		try {
			File f = new File(zipFile.getName());
			is = new ZipInputStream(new FileInputStream(f));
			ze = is.getNextEntry();
			PHPFileData fileData = null;
			if (element instanceof PHPFileData) {
				fileData = (PHPFileData) element;
			} else {
				fileData = PHPModelUtil.getPHPFileContainer((PHPCodeData) element);
			}
			String phpFileName = fileData.getName();
			phpFileName = phpFileName.substring(phpFileName.indexOf(File.separatorChar) + 1); // removing the name of the zip file from the file name

			while (ze != null && !ze.getName().equals(phpFileName)) {
				ze = is.getNextEntry();
			}
			if (ze == null) {
				return null;
			}

			ZipEntryStorage zipEntryStorage = new ZipEntryStorage(zipFile, ze);
			zipEntryStorage.setProject(project);
			return new ZipEntryStorageEditorInput(zipEntryStorage);
		} catch (FileNotFoundException e) {
			Logger.logException(e);
			return null;
		} catch (IOException io) {
			Logger.logException(io);
			return null;
		}
	}

	public static IEditorInput getEditorInput(Object input) {
		IProject project = null;
		if (input instanceof TreeItem) {
			project = getProject((TreeItem) input);
			String incDir = getIncludeDirectory((TreeItem) input);
			return getEditorInput((PHPCodeData) ((TreeItem) input).getData(), project, incDir);
		}

		if (input instanceof PHPCodeData)
			return getEditorInput((PHPCodeData) input, null, null);

		if (input instanceof IFile)
			return new FileEditorInput((IFile) input);

		return null;
	}

	private static String getIncludeDirectory(TreeItem input) {
		if (!(input.getData() instanceof PHPCodeData)) {
			return null;
		}
		PHPCodeData codeData = (PHPCodeData) input.getData();
		while ((codeData != null) && (!(codeData instanceof PHPFileData))) {
			codeData = codeData.getContainer();
			input = input.getParentItem();
		}
		while (input != null && (!(input.getData() instanceof PHPIncludePathModel))) {
			input = input.getParentItem();
		}

		if (input == null) {
			return null;
		}
		return input.getText();
	}

	private static IProject getProject(TreeItem input) {
		if (!(input.getData() instanceof PHPCodeData)) {
			return null;
		}
		PHPCodeData codeData = (PHPCodeData) input.getData();
		while ((codeData != null) && (!(codeData instanceof PHPFileData))) {
			codeData = codeData.getContainer();
			input = input.getParentItem();
		}
		while (input != null && (!(input.getData() instanceof IProject))) {
			input = input.getParentItem();
		}

		if (input == null) {
			return null;
		}
		return ((IProject) input.getData());
	}

	/**
	 * If the current active editor edits a php element return it, else
	 * return null
	 */
	public static PHPCodeData getActiveEditorPHPInput() {
		IWorkbenchPage page = PHPUiPlugin.getActivePage();
		if (page != null) {
			IEditorPart part = page.getActiveEditor();
			if (part != null) {
				IEditorInput editorInput = part.getEditorInput();
				if (editorInput != null) {
					return (PHPCodeData) editorInput.getAdapter(PHPCodeData.class);
				}
			}
		}
		return null;
	}

	public static PHPProjectModel getPHPProject(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IProject project = ((IFileEditorInput) input).getFile().getProject();
			if (project != null) {
				return PHPWorkspaceModelManager.getInstance().getModelForProject(project);
			}
		}
		return null;
	}

	/**
	 * Maps the localized modifier name to a code in the same
	 * manner as #findModifier.
	 *
	 * @param modifierName the modifier name
	 * @return the SWT modifier bit, or <code>0</code> if no match was found
	 * @since 2.1.1
	 */
	public static int findLocalizedModifier(String modifierName) {
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
	 * Returns the modifier string for the given SWT modifier
	 * modifier bits.
	 *
	 * @param stateMask	the SWT modifier bits
	 * @return the modifier string
	 * @since 2.1.1
	 */
	public static String getModifierString(int stateMask) {
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

	/**
	 * Appends to modifier string of the given SWT modifier bit
	 * to the given modifierString.
	 *
	 * @param modifierString	the modifier string
	 * @param modifier			an int with SWT modifier bit
	 * @return the concatenated modifier string
	 * @since 2.1.1
	 */
	private static String appendModifierString(String modifierString, int modifier) {
		if (modifierString == null)
			modifierString = ""; //$NON-NLS-1$
		String newModifierString = Action.findModifierString(modifier);
		if (modifierString.length() == 0)
			return newModifierString;
		return MessageFormat.format(PHPUIMessages.EditorUtility_concatModifierStrings, new String[] { modifierString, newModifierString });
	}
}
