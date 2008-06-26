/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.internal.resources.ICoreConstants;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.containers.LocalFileStorageEditorInput;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.swt.SWT;
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
		return MessageFormat.format(PHPUIMessages.getString("EditorUtility_concatModifierStrings"), new String[] { modifierString, newModifierString });
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
	public static IModelElement getActiveEditorPHPInput() {
		final IWorkbenchPage page = PHPUiPlugin.getActivePage();
		if (page != null) {
			final IEditorPart part = page.getActiveEditor();
			if (part != null) {
				final IEditorInput editorInput = part.getEditorInput();
				if (editorInput != null)
					return (IModelElement) editorInput.getAdapter(IModelElement.class);
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
		if (input instanceof IModelElement) {
			return getEditorInput((IModelElement) input, null, null);
		}

		if (input instanceof IFile)
			return new FileEditorInput((IFile) input);

		if (input instanceof IEditorInput)
			return (IEditorInput) input;

		return null;
	}

	private static IEditorInput getEditorInput(final IModelElement element, final IProject project, final String incDir) {
		final IResource resource = element.getResource();
		if (resource instanceof IFile)
			return new FileEditorInput((IFile) resource);

		return null;
	}

	private static IEditorInput getEditorInput(IModelElement element) {
		// [Taken from:  org.eclipse.dltk.internal.ui.editor.EditorUtility]
		while (element != null) {
			if (element instanceof IExternalSourceModule) {
				ISourceModule unit = ((ISourceModule) element).getPrimary();
				if (unit instanceof IStorage) {
					return new ExternalStorageEditorInput((IStorage) unit);
				}

			} else if (element instanceof ISourceModule) {
				ISourceModule unit = ((ISourceModule) element).getPrimary();
				IResource resource = unit.getResource();
				if (resource instanceof IFile)
					return new FileEditorInput((IFile) resource);
			}
			element = element.getParent();
		}
		return null;
		// TODO - Handle external model elements (for external files)
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

		return getEditor(input);
	}

	public static IEditorPart getEditor(IEditorInput input) {
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
	 * Opens a PHP editor for an element such as <code>IModelElement</code>, <code>IFile</code>, <code>IStorage</code> or <code>IEditorInput</code>.
	 * The editor is activated by default.
	 * @return the IEditorPart or null if wrong element type or opening failed
	 */
	public static IEditorPart openInEditor(final Object inputElement) throws PartInitException {
		return openInEditor(inputElement, true);
	}

	/**
	 * Opens a PHP editor for an element (IModelElement, IFile, IStorage...)
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

		if (input != null) {
			if (input instanceof NonExistingPHPFileEditorInput) {
				return openInEditor(input, PHPUiConstants.PHP_UNTITLED_EDITOR_ID, activate);
			}
			return openInEditor(input, getEditorID(input), activate);
		}

		return null;
	}

	public static IEditorPart revealInEditor(IEditorPart part, int lineNumber) {
		if (lineNumber > 0) {
			if (part instanceof ITextEditor) {
				ITextEditor textEditor = (ITextEditor) part;
				// If a line number was given, go to it
				try {
					lineNumber = lineNumber - 1;
					IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
					textEditor.selectAndReveal(document.getLineOffset(lineNumber), document.getLineLength(lineNumber));
				} catch (BadLocationException e) {
					// invalid text position -> do nothing
				}
			}
		}
		return part;
	}

	/**
	 * Opens a PHP editor for file and line number
	 * 
	 * @throws CoreException 
	 */
	public static IEditorPart openInEditor(final String fileName, int lineNumber) throws CoreException {

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceRoot root = workspace.getRoot();

		try {
			Path errorFilePath = new Path(fileName);
			if (errorFilePath.segmentCount() > 1 && errorFilePath.segment(errorFilePath.segmentCount() - 2).equalsIgnoreCase("Untitled_Documents")) {
				IEditorPart editor = openInEditor(new NonExistingPHPFileEditorInput(errorFilePath), PHPUiConstants.PHP_UNTITLED_EDITOR_ID, true);
				return revealInEditor(editor, lineNumber);
			}
		} catch (RuntimeException e) { // if new Path() fails - do nothing
		}

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

		if (file == null) {
			return null;
		}

		if (!file.exists()) {
			File localFile = new File(fileName);
			if (localFile.exists()) {
				IEditorInput editorInput = null;

				// If this file is external - put it into the external files registry
//				if (!ExternalFilesRegistry.getInstance().isEntryExist(path.toOSString())) {
//					IFile localIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
//					if (!localIFile.exists()) {
//						IFile externalFile = ExternalFileWrapper.createFile(fileName);
//						ExternalFilesRegistry.getInstance().addFileEntry(fileName, externalFile);
//					}
//				}

				// If this is external file:
//				if (ExternalFilesRegistry.getInstance().isEntryExist(path.toOSString())) {
//					editorInput = new FileStoreEditorInput(new LocalFile(localFile));
//				} else {
					LocalFileStorage fileStorage = new LocalFileStorage(localFile);
					fileStorage.setProject(file.getProject());
					editorInput = new LocalFileStorageEditorInput(fileStorage);
//				}

//				if (editorInput != null) {
					final IWorkbenchPage p = PHPUiPlugin.getActivePage();
					if (p != null) {
						IEditorPart part = openInEditor(editorInput, getEditorID(editorInput), true);
						return revealInEditor(part, lineNumber);
					}
					return null;
//				}
			}
		}

		if (!file.exists()) {
			return null;
		}

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
	 * (based on DLTK model)
	 */
	public static void revealInEditor(IEditorPart part, IModelElement element) {
		if (element == null)
			return;
/*		if (part instanceof ScriptEditor) {
			((ScriptEditor) part).setSelection(element);
			if (DLTKCore.DEBUG) {
				System.err.println("Add revealInEditor set selection"); //$NON-NLS-1$
			}
			return;
		}*/
		// Support for non-Script editor
		try {
			ISourceRange range = null;
			if (element instanceof IExternalSourceModule) {

			} else if (element instanceof ISourceModule) {
				range = null;
			}
			// else if (element instanceof IClassFile)
			// range= null;
			// else if (element instanceof ILocalVariable)
			// range= ((ILocalVariable)element).getNameRange();
			else if (element instanceof IMember)
				range = ((IMember) element).getNameRange();
			// else if (element instanceof ITypeParameter)
			// range= ((ITypeParameter)element).getNameRange();
			else if (element instanceof ISourceReference)
				range = ((ISourceReference) element).getSourceRange();
			if (range != null)
				revealInEditor(part, range.getOffset(), range.getLength());
		} catch (ModelException e) {
			// don't reveal
		}
	}


	
	/**
	 * Selects a PHP Element in an editor
	 * 
	 * @param editor
	 * @return the php editor (if exists) from the given editor
	 * NOTE: editors that wants to work with PHP editor actions must implement the getAdapter() method
	 *       this way the actions pick the php editor...
	 */
	public static final PHPStructuredEditor getPHPStructuredEditor(final IWorkbenchPart editor) {
		return editor != null ? (PHPStructuredEditor) editor.getAdapter(PHPStructuredEditor.class) : null;
	}

	/**
	 * Returns PHP editor which corresponds to ITextViewer
	 * @return php editor, or <code>null</code> if no editor found
	 */
	public static final PHPStructuredEditor getPHPStructuredEditor(final ITextViewer textViewer) {
		IWorkbenchPage workbenchpage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		// Check active editor, first:
		IEditorPart activeEditorPart = workbenchpage.getActiveEditor();
		if (activeEditorPart instanceof PHPStructuredEditor) {
			PHPStructuredEditor phpStructuredEditor = (PHPStructuredEditor) activeEditorPart;
			if (phpStructuredEditor.getTextViewer().getDocument() == textViewer.getDocument()) {
				return phpStructuredEditor;
			}
		}

		// Check other editors:
		IEditorReference[] editorReferences = workbenchpage.getEditorReferences();
		for (int i = 0; i < editorReferences.length; i++) {
			IEditorReference editorReference = editorReferences[i];
			IEditorPart editorpart = editorReference.getEditor(false);
			if (editorpart instanceof PHPStructuredEditor) {
				PHPStructuredEditor phpStructuredEditor = (PHPStructuredEditor) editorpart;
				if (phpStructuredEditor.getTextViewer().getDocument() == textViewer.getDocument()) {
					return phpStructuredEditor;
				}
			}
		}
		return null;
	}

	/**
	 * Gets a list of File full paths as strings and open them in the editor
	 * works for both workspace and non- workspace files 
	 * @param filesToOpen
	 * @param window
	 */
	public static void openFilesInEditor(List filesToOpen, IWorkbenchWindow window) {
		PHPOpenExternalFileAction action = new PHPOpenExternalFileAction();
		action.init(window);
		action.run(filesToOpen);
	}

	/**
	 * @see openFilesInEditor(List, IWorkbenchWindow)
	 * @param filesToOpen
	 */
	public static void openFilesInEditor(List filesToOpen) {
		openFilesInEditor(filesToOpen, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	/**
	 * Open a file by the given path at the line specified
	 * @param filePath
	 * @param lineNumber
	 * @param window
	 */
	public static void openFileInEditor(String filePath, int lineNumber, IWorkbenchWindow window) {
		PHPOpenExternalFileAction action = new PHPOpenExternalFileAction();
		action.init(window);
		action.run(filePath, lineNumber);
	}

	/**
	 * Open a file by the given path at the line specified
	 * @param filePath
	 * @param lineNumber
	 */
	public static void openFileInEditor(String filePath, int lineNumber) {
		openFileInEditor(filePath, lineNumber, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	public static IResource getResourceFromEditorInput(IEditorInput input) {
		IResource resource = null;
		IPath externalPath = null;
		if (input instanceof IFileEditorInput) {
			// This is the existing workspace file
			final IFileEditorInput fileInput = (IFileEditorInput) input;
			resource = fileInput.getFile();
		} else if (input instanceof IStorageEditorInput) {
			final IStorageEditorInput editorInput = (IStorageEditorInput) input;
			IStorage storage = null;

			try {
				storage = editorInput.getStorage();
			} catch (CoreException e) {
				Logger.logException(e);
				return null;
			}

			if (storage instanceof ZipEntryStorage) {
				resource = ((ZipEntryStorage) storage).getProject();
			} else if (storage instanceof LocalFileStorage) {
				// don't create external resource, it's wrong! Include paths should not have a resource.
			} else {
				// This is, probably, a remote storage:
				externalPath = storage.getFullPath();
//				resource = ExternalFileWrapper.createFile(externalPath.toOSString());
			}
		} else if (input instanceof IURIEditorInput || input instanceof NonExistingPHPFileEditorInput) {
			// External file editor input. It's usually used when opening PHP file
			// via "File -> Open File" menu option, or using D&D:
			//OR
			// When we are dealing with an Untitled PHP document and the underlying PHP file
			// does not really exist, but is still considered as an "External" file.
			if (input instanceof NonExistingPHPFileEditorInput) {
				externalPath = ((NonExistingPHPFileEditorInput) input).getPath();
			} else {
				externalPath = URIUtil.toPath(((IURIEditorInput) input).getURI());
			}
//			resource = ExternalFileWrapper.createFile(externalPath.toOSString());
		}

		return resource;
	}
	
	/**
	 * Returns the given editor's input as PHP element.
	 *
	 * @param editor the editor
	 * @param primaryOnly if <code>true</code> only primary working copies will be returned
	 * @return the given editor's input as <code>ITypeRoot</code> or <code>null</code> if none
	 * @since 3.2
	 */
	public static IModelElement getEditorInputPhpElement(IEditorPart editor, boolean primaryOnly) {
		Assert.isNotNull(editor);
		IEditorInput editorInput= editor.getEditorInput();
		if (editorInput == null)
			return null;
		
		IModelElement je= DLTKUIPlugin.getEditorInputModelElement(editorInput);
		if (je != null || primaryOnly)
			return je;

		return  DLTKUIPlugin.getDefault().getWorkingCopyManager().getWorkingCopy(editorInput, false);
	}	

}
