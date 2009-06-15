/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import junit.framework.TestSuite;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.core.ModelManager;
import org.osgi.framework.Bundle;

public class AbstractProjectSuite extends TestSuite {

	public static final String WORKSPACE_BASE = "workspace";
	private Bundle bundle;
	
	public AbstractProjectSuite(String name, Bundle bundle) {
		super(name);
		this.bundle = bundle;
	}

	public AbstractProjectSuite(String name) {
		this(name, PHPCoreTests.getDefault().getBundle());
	}

	protected IProject setUpProject(final String projectName) throws CoreException, IOException {
		// copy files in project from source workspace to target workspace
		final File sourceWorkspacePath = getSourceWorkspacePath();
		final File targetWorkspacePath = getWorkspaceRoot().getLocation().toFile();
		copyDirectory(new File(sourceWorkspacePath, projectName), new File(targetWorkspacePath, projectName));

		return createProject(projectName);
	}

	protected IScriptProject setUpScriptProjectTo(final String projectName, final String fromName) throws CoreException, IOException {
		final IProject project = setUpProjectTo(projectName, fromName);
		return DLTKCore.create(project);
	}

	protected IProject setUpProjectTo(final String projectName, final String fromName) throws CoreException, IOException {
		// copy files in project from source workspace to target workspace
		final File sourceWorkspacePath = getSourceWorkspacePath();
		final File targetWorkspacePath = getWorkspaceRoot().getLocation().toFile();

		copyDirectory(new File(sourceWorkspacePath, fromName), new File(targetWorkspacePath, projectName));

		return createProject(projectName);
	}

	protected IScriptProject setUpScriptProject(final String projectName) throws CoreException, IOException {
		final IProject project = setUpProject(projectName);
		return DLTKCore.create(project);
	}

	/**
	 * Returns the specified source module in the given project, root, and
	 * folder or <code>null</code> if it does not exist.
	 */
	public ISourceModule getSourceModule(String projectName, String rootPath, IPath path) throws ModelException {
		IScriptFolder folder = getScriptFolder(projectName, rootPath, path.removeLastSegments(1));
		if (folder == null) {
			return null;
		}
		return folder.getSourceModule(path.lastSegment());
	}

	public ISourceModule getSourceModule(String projectName, String rootPath, String path) throws ModelException {
		IScriptFolder folder = getScriptFolder(projectName, rootPath, new Path(path).removeLastSegments(1));
		if (folder == null) {
			return null;
		}
		return folder.getSourceModule(new Path(path).lastSegment().toString());
	}

	/**
	 * Returns the specified script folder in the given project and fragment, or
	 * <code>null</code> if it does not exist. The rootPath must be specified as
	 * a project relative path. The empty path refers to the default package
	 * fragment.
	 */
	public IScriptFolder getScriptFolder(String projectName, String fragmentPath, IPath path) throws ModelException {
		IProjectFragment root = getProjectFragment(projectName, fragmentPath);
		if (root == null) {
			return null;
		}
		return root.getScriptFolder(path);
	}

	/**
	 * Returns the specified package fragment root in the given project, or
	 * <code>null</code> if it does not exist. If relative, the rootPath must be
	 * specified as a project relative path. The empty path refers to the
	 * package fragment root that is the project folder iteslf. If absolute, the
	 * rootPath refers to either an external zip, or a resource internal to the
	 * workspace
	 */
	public IProjectFragment getProjectFragment(String projectName, String fragmentPath) throws ModelException {

		IScriptProject project = getScriptProject(projectName);
		if (project == null) {
			return null;
		}
		IPath path = new Path(fragmentPath);
		if (path.isAbsolute()) {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = workspaceRoot.findMember(path);
			IProjectFragment root;
			// resource in the workspace
			root = project.getProjectFragment(resource);
			return root;
		} else {
			IProjectFragment[] roots = project.getProjectFragments();
			if (roots == null || roots.length == 0) {
				return null;
			}
			for (int i = 0; i < roots.length; i++) {
				IProjectFragment root = roots[i];
				if (root.getUnderlyingResource().getProjectRelativePath().equals(path)) {
					return root;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the script Project with the given name in this test suite's
	 * model. This is a convenience method.
	 */
	public IScriptProject getScriptProject(String name) {
		IProject project = getProject(name);
		return DLTKCore.create(project);
	}

	public IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public IWorkspaceRoot getWorkspaceRoot() {
		return getWorkspace().getRoot();
	}

	protected IProject getProject(String project) {
		return getWorkspaceRoot().getProject(project);
	}

	public File getSourceWorkspacePath() {
		return new File(getPluginDirectoryPath(), WORKSPACE_BASE);
	}

	/**
	 * Returns the OS path to the directory that contains this plugin.
	 */
	protected File getPluginDirectoryPath() {
		try {
			URL platformURL = bundle.getEntry("/");
			return new File(FileLocator.toFileURL(platformURL).getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Copy the given source directory (and all its contents) to the given
	 * target directory.
	 */
	protected void copyDirectory(File source, File target) throws IOException {
		if (!target.exists()) {
			target.mkdirs();
		}
		File[] files = source.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			File sourceChild = files[i];
			String name = sourceChild.getName();
			if (name.equals("CVS") || name.equals(".svn"))
				continue;
			File targetChild = new File(target, name);
			if (sourceChild.isDirectory()) {
				copyDirectory(sourceChild, targetChild);
			} else {
				copy(sourceChild, targetChild);
			}
		}
	}

	/**
	 * Copy file from src (path to the original file) to dest (path to the
	 * destination file).
	 */
	public static void copy(File src, File dest) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		byte[] buffer = new byte[12 * 1024];
		int read;

		try {
			in = new FileInputStream(src);

			try {
				out = new FileOutputStream(dest);

				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/*
	 * Create simple project.
	 */
	protected IProject createProject(final String projectName) throws CoreException {
		final IProject project = getProject(projectName);
		IWorkspaceRunnable create = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				project.create(null);
				project.open(null);
			}
		};
		getWorkspace().run(create, null);
		return project;
	}

	/*
	 * Creates a script project with the given source folders an output
	 * location. Add those on the project's buildpath.
	 */
	protected IScriptProject createScriptProject(String projectName, String[] natures, String[] sourceFolders) throws CoreException {
		return createScriptProject(projectName, natures, sourceFolders, null);
	}

	/*
	 * Creates a script project with the given source folders an output
	 * location. Add those on the project's buildpath.
	 */
	protected IScriptProject createScriptProject(final String projectName, final String[] natures, final String[] sourceFolders, final String[] projects) throws CoreException {
		final IScriptProject[] result = new IScriptProject[1];
		IWorkspaceRunnable create = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				// create project
				createProject(projectName);

				IProject project = getWorkspaceRoot().getProject(projectName);

				// add natures
				IProjectDescription description = project.getDescription();
				description.setNatureIds(natures);
				project.setDescription(description, null);

				// create buildpath entries
				IPath projectPath = project.getFullPath();
				int sourceLength = sourceFolders == null ? 0 : sourceFolders.length;
				int projectLength = projects == null ? 0 : projects.length;
				IBuildpathEntry[] entries = new IBuildpathEntry[sourceLength + projectLength];
				for (int i = 0; i < sourceLength; i++) {
					IPath sourcePath = new Path(sourceFolders[i]);
					int segmentCount = sourcePath.segmentCount();
					if (segmentCount > 0) {
						// create folder and its parents
						IContainer container = project;
						for (int j = 0; j < segmentCount; j++) {
							IFolder folder = container.getFolder(new Path(sourcePath.segment(j)));
							if (!folder.exists()) {
								folder.create(true, true, null);
							}
							container = folder;
						}
					}
					// create source entry
					entries[i] = DLTKCore.newSourceEntry(projectPath.append(sourcePath));
				}
				for (int i = 0; i < projectLength; i++) {

					// accessible files
					IPath[] accessibleFiles;
					accessibleFiles = new IPath[0];

					// non accessible files
					IPath[] nonAccessibleFiles;
					nonAccessibleFiles = new IPath[0];

					entries[sourceLength + i] = DLTKCore.newProjectEntry(new Path(projects[i]), BuildpathEntry.getAccessRules(accessibleFiles, nonAccessibleFiles), true, new IBuildpathAttribute[0], false);
				}
				// set buildpath and output location
				IScriptProject scriptProject = DLTKCore.create(project);
				scriptProject.setRawBuildpath(entries, null);

				result[0] = scriptProject;
			}
		};
		getWorkspace().run(create, null);
		return result[0];
	}

	public static void waitUntilIndexesReady() {
		ModelManager.getModelManager().getIndexManager().waitUntilReady();
	}

	protected void deleteProject(String projectName) throws CoreException {
		IProject project = this.getProject(projectName);
		if (project.exists() && !project.isOpen()) { // force opening so that
			// project can be
			// deleted without
			// logging (see bug
			// 23629)
			project.open(null);
		}
		deleteResource(project);
	}

	/**
	 * Delete this resource.
	 */
	public void deleteResource(IResource resource) throws CoreException {
		CoreException lastException = null;
		try {
			resource.delete(true, null);
		} catch (CoreException e) {
			lastException = e;
			// just print for info
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException iae) {
			// just print for info
			System.out.println(iae.getMessage());
		}
		int retryCount = 60; // wait 1 minute at most
		while (resource.isAccessible() && --retryCount >= 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			try {
				resource.delete(true, null);
			} catch (CoreException e) {
				lastException = e;
				// just print for info
				System.out.println("Retry " + retryCount + ": " + e.getMessage());
			} catch (IllegalArgumentException iae) {
				// just print for info
				System.out.println("Retry " + retryCount + ": " + iae.getMessage());
			}
		}
		if (!resource.isAccessible())
			return;
		System.err.println("Failed to delete " + resource.getFullPath());
		if (lastException != null) {
			throw lastException;
		}
	}
}
