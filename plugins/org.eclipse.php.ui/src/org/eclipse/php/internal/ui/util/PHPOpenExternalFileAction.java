package org.eclipse.php.internal.ui.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * This class behaves like OpenExternalFileAction, with the difference that the run method gets a List
 * of file paths (Strings) to open in the editor, instead of activating a file browse dialog
 * @author Eden K., 2007
 *
 */
public class PHPOpenExternalFileAction extends Action implements IWorkbenchWindowActionDelegate {
	static class FileLabelProvider extends LabelProvider {
		/*
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			if (element instanceof IFile) {
				IPath path = ((IFile) element).getFullPath();
				return path != null ? path.toString() : ""; //$NON-NLS-1$
			}
			return super.getText(element);
		}
	}

	private IWorkbenchWindow fWindow;
	private String fFilterPath;
	private StringBuffer notFound = new StringBuffer();

	public PHPOpenExternalFileAction() {
		setEnabled(true);
	}

	/*
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		fWindow = null;
		fFilterPath = null;
	}

	/*
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		fWindow = window;
		fFilterPath = System.getProperty("user.home"); //$NON-NLS-1$
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		run();
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/*
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run(List filePaths) {
		if (filePaths != null) {
			int numberOfFilesNotFound = 0;

			for (Iterator iter = filePaths.iterator(); iter.hasNext();) {
				String currentFilePath = (String) iter.next();
				numberOfFilesNotFound = openFile(numberOfFilesNotFound, currentFilePath, -1);
			}
		}
	}

	public void run(String filePath, int lineNumber) {
		openFile(0, filePath, lineNumber);
	}

	private int openFile(int numberOfFilesNotFound, String currentFilePath, int lineNumber) {
		IPath path = new Path(currentFilePath);
		fFilterPath = path.removeLastSegments(1).toOSString();
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(fFilterPath));
		fileStore = fileStore.getChild(path.lastSegment());
		if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
//			IEditorInput input = createEditorInput(fileStore);
//			String editorId = getEditorId(fileStore);
			IWorkbenchPage page = fWindow.getActivePage();
			IEditorPart editorPart = null;
			try {
				IDE.openEditorOnFileStore(page, fileStore);				
				// if the open file request has a line number, try to set the cursor on that line				
				if (lineNumber >= 0) {
					editorPart = page.getActiveEditor();
					gotoLine(editorPart, currentFilePath, lineNumber);
				}
			} catch (PartInitException e) {
				Logger.logException(PHPUIMessages.getString("PHPOpenExternalFileAction.0"), e); //$NON-NLS-1$
			}
		} else {
			if (++numberOfFilesNotFound > 1)
				notFound.append('\n');
			notFound.append(currentFilePath);
		}
		return numberOfFilesNotFound;
	}

	/**
	 * Sets the focus on a specific line number for the given file 
	 * @param editorPart
	 * @param filePath
	 * @param lineNumber
	 */
	private void gotoLine(IEditorPart editorPart, String filePath, int lineNumber) {
		int offset = 0;
		int length = 0;
		if (editorPart != null && lineNumber > 0 && editorPart instanceof StructuredTextEditor) {
			IRegion region;
			try {
				region = ((StructuredTextEditor) editorPart).getTextViewer().getDocument().getLineInformation(lineNumber - 1);
				offset = region.getOffset();
				length = region.getLength();
			} catch (BadLocationException e) {
				// failed calculating offset for goto line feature
				return;
			}

			if (editorPart != null) {
				EditorUtility.revealInEditor(editorPart, offset, length);
			}

		}
	}

	/**
	 * Returns a String with the names of the files that couldnt be found
	 */
	public String getFileNotFoundStr() {
		return notFound.toString();
	}

	/*
	 * XXX: Requested a helper to get the correct editor descriptor
	 *		see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=110203
	 */
	private String getEditorId(IFileStore file) {
		IWorkbench workbench = fWindow.getWorkbench();
		IEditorRegistry editorRegistry = workbench.getEditorRegistry();
		IEditorDescriptor descriptor = editorRegistry.getDefaultEditor(file.getName(), getContentType(file));

		// check the OS for in-place editor (OLE on Win32)
		if (descriptor == null && editorRegistry.isSystemInPlaceEditorAvailable(file.getName()))
			descriptor = editorRegistry.findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);

		// check the OS for external editor
		if (descriptor == null && editorRegistry.isSystemExternalEditorAvailable(file.getName()))
			descriptor = editorRegistry.findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);

		if (descriptor != null)
			return descriptor.getId();

		return EditorsUI.DEFAULT_TEXT_EDITOR_ID;
	}

	private IContentType getContentType(IFileStore fileStore) {
		if (fileStore == null)
			return null;

		InputStream stream = null;
		try {
			stream = fileStore.openInputStream(EFS.NONE, null);
			return Platform.getContentTypeManager().findContentTypeFor(stream, fileStore.getName());
		} catch (IOException x) {
			EditorsPlugin.log(x);
			return null;
		} catch (CoreException x) {
			// Do not log FileNotFoundException (no access)
			if (!(x.getStatus().getException() instanceof FileNotFoundException))
				EditorsPlugin.log(x);

			return null;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException x) {
				EditorsPlugin.log(x);
			}
		}
	}

	private IEditorInput createEditorInput(IFileStore fileStore) {
		IFile workspaceFile = getWorkspaceFile(fileStore);
		if (workspaceFile != null)
			return new FileEditorInput(workspaceFile);

		return new FileStoreEditorInput(fileStore);
	}

	private IFile getWorkspaceFile(IFileStore fileStore) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile[] files = workspace.getRoot().findFilesForLocation(new Path(fileStore.toURI().getPath()));
		files = filterNonExistentFiles(files);
		if (files == null || files.length == 0)
			return null;
		if (files.length == 1)
			return files[0];
		return selectWorkspaceFile(files);
	}

	private IFile[] filterNonExistentFiles(IFile[] files) {
		if (files == null)
			return null;

		int length = files.length;
		ArrayList existentFiles = new ArrayList(length);
		for (int i = 0; i < length; i++) {
			if (files[i].exists())
				existentFiles.add(files[i]);
		}
		return (IFile[]) existentFiles.toArray(new IFile[existentFiles.size()]);
	}

	private IFile selectWorkspaceFile(IFile[] files) {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(fWindow.getShell(), new FileLabelProvider());
		dialog.setElements(files);
//		dialog.setTitle(TextEditorMessages.OpenExternalFileAction_title_selectWorkspaceFile);
//		dialog.setMessage(TextEditorMessages.OpenExternalFileAction_message_fileLinkedToMultiple);
		if (dialog.open() == Window.OK)
			return (IFile) dialog.getFirstResult();
		return null;
	}
}
