package org.eclipse.php.ui.dialogs.saveFiles;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * A helper class for the save dirty files dialog.
 */
public class SaveFilesHelper {

	/**
	 * Retreive an array of IEditorParts representing all the dirty
	 * editors open for the files provided in the list.
	 * 
	 * @param files
	 * 			A list of IFiles.
	 * @return
	 * 			An array of IEditorParts containing all the dirty editors for the files in the list.
	 */
	public static IEditorPart[] getDirtyEditors(IProject project) {
		List result = new ArrayList(0);
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int x = 0; x < pages.length; x++) {
				IEditorPart[] editors = pages[x].getDirtyEditors();
				for (int z = 0; z < editors.length; z++) {
					IEditorPart ep = editors[z];
					IEditorInput input = ep.getEditorInput();
					if (input instanceof IFileEditorInput) {
						IFileEditorInput fileInput = (IFileEditorInput) input;
						IFile file = fileInput.getFile();
						if (file.getProject() == project)
							result.add(ep);
					}
				}
			}
		}
		return (IEditorPart[]) result.toArray(new IEditorPart[result.size()]);
	}
}
