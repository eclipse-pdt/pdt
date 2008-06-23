package org.eclipse.php.internal.debug.ui.console;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.console.FileLink;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.ide.IDE;

/**
 *
 * @author seva
 *
 * A version of {@link FileLink} which also supports external resources
 *
 */
public class PHPFileLink implements IHyperlink {

	protected String fileName;
	protected int lineNumber;

	/**
	 * Constructs a hyperlink to the specified file.
	 *
	 * @param fileName The file name to open
	 * @param lineNumber The line number to select
	 */
	public PHPFileLink(String fileName, int lineNumber) {
		this.fileName = fileName;
		this.lineNumber = lineNumber;
	}
	
	public void linkActivated() {
		Object element = findSourceModule(fileName);
		if (element == null) {
			// did not find source
			MessageDialog.openInformation(PHPDebugUIPlugin.getActiveWorkbenchShell(), "Information", MessageFormat.format("Source not found for {0}", new Object[] { fileName }));
			return;
		}
		openElementInEditor(element);
	}
	
	protected void openElementInEditor(Object element) {
		Assert.isNotNull(element);
		
		IEditorInput input = EditorUtility.getEditorInput(element);
		if (input == null) {
			return;
		}
		IEditorDescriptor descriptor;
		try {
			descriptor = IDE.getEditorDescriptor(input.getName());
			IWorkbenchPage page = PHPDebugUIPlugin.getActivePage();
			IEditorPart editor = page.openEditor(input, descriptor.getId());
			EditorUtility.revealInEditor(editor, lineNumber);
		} catch (PartInitException e) {
			PHPDebugUIPlugin.log(e);
		}	
	}

	/**
	 * Finds {@link IFile} or {@link ISourceModule} matching the specified file name
	 * 
	 * @param fileName
	 * @return
	 */
	public static Object findSourceModule(String fileName) {
		IPath path = Path.fromOSString(fileName);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile f = root.getFileForLocation(path);
		if (f != null) {
			return f;
		}
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		PHPConsoleSourceModuleLookup lookup = new PHPConsoleSourceModuleLookup(toolkit);
		return lookup.findSourceModuleByLocalPath(path);
	}

	public void linkEntered() {
	}

	public void linkExited() {
	}
}