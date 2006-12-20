package org.eclipse.php.debug.ui.console;

import org.eclipse.debug.ui.console.FileLink;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IHyperlink;

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
		IEditorPart editorPart;
		try {
			editorPart = EditorUtility.openInEditor(fFile, false);
			if (editorPart != null && fFileLineNumber > 0) {
				EditorUtility.revealInEditor(editorPart, fFileOffset, fFileLength);
			}
		} catch (PartInitException e) {
		}
	}

	public void linkEntered() {
		// TODO Auto-generated method stub

	}

	public void linkExited() {
		// TODO Auto-generated method stub

	}

}