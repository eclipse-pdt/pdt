package org.eclipse.php.internal.debug.ui.console;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.console.FileLink;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.resources.ExternalFileDecorator;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.wst.sse.core.StructuredModelManager;

/**
 * 
 * @author seva
 * 
 * A version of {@link FileLink} which also supports external resources
 *
 */
public class PHPFileLink implements IHyperlink {

	private Object fFile;
	private int fFileOffset;
	private int fFileLength;
	private int fFileLineNumber;

	/**
	 * Constructs a hyperlink to the specified file.
	 * 
	 * @param file the file to open when activated
	 * <code>null</code> if the default editor should be used
	 * @param fileOffset the offset in the file to select when activated, or -1
	 * @param fileLength the length of text to select in the file when activated
	 * or -1
	 * @param fileLineNumber the line number to select in the file when
	 * activated, or -1
	 */
	public PHPFileLink(Object file, int fileOffset, int fileLength, int fileLineNumber) {
		fFile = file;
		fFileOffset = fileOffset;
		fFileLength = fileLength;
		fFileLineNumber = fileLineNumber;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.console.IConsoleHyperlink#linkActivated()
	 */
	public void linkActivated() {
		IEditorPart editorPart = null;
		DOMModelForPHP domModel = null;
		try {
			if (fFile instanceof PHPCodeData) {
				IPath path = Path.fromOSString(((PHPCodeData) fFile).getName());
				fFile = new ExternalFileDecorator(ResourcesPlugin.getWorkspace().getRoot().getFile(path), path.getDevice()) {
					/*
					 * Override the default IFile exists method to allow the retrival of the DOM model.
					 */
					public boolean exists() {
						return getFullPath().toFile().exists();
					}
				};
			}
			editorPart = EditorUtility.openInEditor(fFile, false);
			if (editorPart != null && fFileLineNumber > 0) {
				if (fFileOffset < 0) {
					domModel = ((DOMModelForPHP) StructuredModelManager.getModelManager().getModelForRead((IFile) fFile));
					IRegion region = domModel.getDocument().getStructuredDocument().getLineInformation(fFileLineNumber - 1);
					fFileOffset = region.getOffset();
					fFileLength = region.getLength();
				}
			}
		} catch (PartInitException e) {
			Logger.logException(e);
		} catch (BadLocationException e) {
			Logger.logException(e);
		} catch (IOException e) {
			Logger.logException(e);
		} catch (CoreException e) {
			Logger.logException(e);
		} catch (NullPointerException npe) {
			Logger.logException(npe);
		} finally {
			if (editorPart != null) {
				EditorUtility.revealInEditor(editorPart, fFileOffset, fFileLength);
				if (domModel != null) {
					domModel.releaseFromRead();
				}
			}
		}
	}

	public void linkEntered() {
		// TODO Auto-generated method stub

	}

	public void linkExited() {
		// TODO Auto-generated method stub

	}

}