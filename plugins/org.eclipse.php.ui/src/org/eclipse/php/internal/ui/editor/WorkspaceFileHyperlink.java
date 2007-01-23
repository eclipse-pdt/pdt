package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.PartInitException;

/**
 * Hyperlink for files within the workspace. (As long as there is an IFile,
 * this can be used) Opens the default editor for the file.
 */
public class WorkspaceFileHyperlink implements IHyperlink {

	private IRegion fRegion;
	private IFile fFile;

	public WorkspaceFileHyperlink(IRegion region, IFile file) {
		fRegion = region;
		fFile = file;
	}

	public IRegion getHyperlinkRegion() {
		return fRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getTypeLabel()
	 */
	public String getTypeLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getHyperlinkText()
	 */
	public String getHyperlinkText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void open() {
		if (fFile != null && fFile.exists()) {
			try {
				EditorUtility.openInEditor(fFile, true);
			} catch (PartInitException e) {
				PHPUiPlugin.log(e);
			}
		}
	}

}

