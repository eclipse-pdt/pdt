package org.eclipse.php.internal.ui;

import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;


public class TestProject  {

	private static final String TEST_PROJECT_NAME = "TestProject"; //$NON-NLS-1$
	private static final String FILE_NAME = "test.php"; //$NON-NLS-1$

	public TestProject() {
	}

	public static void run() {

		final IProject testProject = ResourcesPlugin.getWorkspace().getRoot().getProject(TEST_PROJECT_NAME);
		try {
			//create Example project
			testProject.create(null);
			testProject.open(null);
			providePhpNatureToProject(testProject);

			//create PHP file
			URL demoFileURL = FileLocator.find(Platform.getBundle(PHPUiPlugin.getDefault().getPluginId()), new Path("/resources/examples/" + FILE_NAME), null); //$NON-NLS-1$
			demoFileURL = FileLocator.resolve(demoFileURL);
			IPath p = testProject.getFullPath();
			p = p.append(FILE_NAME);
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(p);
			InputStream inputStream = (InputStream) demoFileURL.getContent();
			file.create(inputStream, true, null);

			//open the DebugDemo.php file in the editor Bug fix #16723
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					if (page != null) {
						try {
							page.openEditor(new FileEditorInput(file), PHPUiConstants.PHP_EDITOR_ID, false);//use false in order to keep the welcome screen
						} catch (PartInitException e) {
							Logger.logException(e);
						}
					}
				}
			});
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	//this method provides a PHP nature to the created project that is given
	private static void providePhpNatureToProject(IProject project) throws ExecutionException {
		try {
			IProjectDescription desc = null;

			//configure .project
			String[] natureIds = new String[] { PHPNature.ID };
			if (null != natureIds) {
				desc = project.getDescription();
				desc.setNatureIds(natureIds);
				project.setDescription(desc, null);
			}

			//create .projectOptions
//			PHPNature nature = (PHPNature) project.getNature(PHPNature.ID);
//			PHPProjectOptions options = nature.getOptions();
//			options.setOption(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING, ""); //$NON-NLS-1$
//			options.setOption(PHPCoreConstants.PHPOPTION_CONTEXT_ROOT, ""); //$NON-NLS-1$

		} catch (CoreException e) {
			Logger.logException(e);
		}
	}
}
