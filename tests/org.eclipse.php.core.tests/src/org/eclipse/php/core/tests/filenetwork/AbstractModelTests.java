/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.core.tests.filenetwork;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ElementChangedEvent;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IElementChangedListener;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementDelta;
import org.eclipse.dltk.core.IParent;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptModel;
import org.eclipse.dltk.core.IScriptModelMarker;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.util.Util;
import org.eclipse.dltk.internal.core.util.Util.Comparer;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

public abstract class AbstractModelTests extends SuiteOfTestCases {
	protected boolean displayName = false;
	protected String endChar = ",";

	// infos for invalid results
	protected int tabs = 2;

	// working copies usage
	protected ISourceModule[] workingCopies;

	protected WorkingCopyOwner wcOwner;

	protected boolean discard;

	protected String fTestProjectName = "org.eclipse.dltk.core.tests";

	class DeltaListener implements IElementChangedListener {
		IModelElementDelta[] deltas;

		public void elementChanged(ElementChangedEvent ev) {
			IModelElementDelta[] copy = new IModelElementDelta[deltas.length + 1];
			System.arraycopy(deltas, 0, copy, 0, deltas.length);
			copy[deltas.length] = ev.getDelta();
			deltas = copy;
		}

		public String toString() {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0, length = this.deltas.length; i < length; i++) {
				IModelElementDelta delta = this.deltas[i];
				IModelElementDelta[] children = delta.getAffectedChildren();
				int childrenLength = children.length;
				IResourceDelta[] resourceDeltas = delta.getResourceDeltas();
				int resourceDeltasLength = resourceDeltas == null ? 0
						: resourceDeltas.length;
				if (childrenLength == 0 && resourceDeltasLength == 0) {
					buffer.append(delta);
				} else {
					sortDeltas(children);
					for (int j = 0; j < childrenLength; j++) {
						buffer.append(children[j]);
						if (j != childrenLength - 1) {
							buffer.append("\n");
						}
					}
					for (int j = 0; j < resourceDeltasLength; j++) {
						if (j == 0 && buffer.length() != 0) {
							buffer.append("\n");
						}
						buffer.append(resourceDeltas[j]);
						if (j != resourceDeltasLength - 1) {
							buffer.append("\n");
						}
					}
				}
				if (i != length - 1) {
					buffer.append("\n\n");

				}
			}
			return buffer.toString();
		}

		protected void sortDeltas(IModelElementDelta[] elementDeltas) {
			Comparer comparer = new Comparer() {
				public int compare(Object a, Object b) {
					IModelElementDelta deltaA = (IModelElementDelta) a;
					IModelElementDelta deltaB = (IModelElementDelta) b;
					return deltaA.getElement().getElementName()
							.compareTo(deltaB.getElement().getElementName());
				}
			};
			org.eclipse.dltk.internal.core.util.Util.sort(elementDeltas,
					comparer);
		}
	}

	protected DeltaListener deltaListener = new DeltaListener();

	public AbstractModelTests(String testProjectName, String name) {
		super(name);
		this.fTestProjectName = testProjectName;
	}

	public AbstractModelTests(String testProjectName, String name, int tabs) {
		super(name);
		this.fTestProjectName = testProjectName;
		this.tabs = tabs;
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (this.discard) {
			this.workingCopies = null;
		}
		this.discard = true;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		if (this.discard && this.workingCopies != null) {
			discardWorkingCopies(this.workingCopies);
			this.wcOwner = null;
		}
	}

	public File getSourceWorkspacePath() {
		return new File(getPluginDirectoryPath(), "workspace");
	}

	/**
	 * Returns the OS path to the directory that contains this plugin.
	 */
	protected File getPluginDirectoryPath() {
		try {
			final Bundle bundle = Platform.getBundle(this.fTestProjectName);
			if (bundle == null) {
				throw new IllegalStateException(NLS.bind(
						"Bundle \"{0}\" with test data not found",
						fTestProjectName));
			}
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
		FileUtil.copyDirectory(source, target);
	}

	/**
	 * Copy file from src (path to the original file) to dest (path to the
	 * destination file).
	 */
	public static void copy(File src, File dest) throws IOException {
		FileUtil.copyFile(src, dest);
	}

	public IProject setUpProject(final String projectName)
			throws CoreException, IOException {
		return setUpProjectTo(projectName, projectName);
	}

	protected IScriptProject setUpScriptProjectTo(final String projectName,
			final String fromName) throws CoreException, IOException {
		final IProject project = setUpProjectTo(projectName, fromName);
		return DLTKCore.create(project);
	}

	protected IProject setUpProjectTo(final String projectName,
			final String fromName) throws CoreException, IOException {
		// copy files in project from source workspace to target workspace
		final File sourceWorkspacePath = getSourceWorkspacePath();
		final File targetWorkspacePath = getWorkspaceRoot().getLocation()
				.toFile();

		final File source = new File(sourceWorkspacePath, fromName);
		if (!source.isDirectory()) {
			throw new IllegalArgumentException(NLS.bind(
					"Source directory \"{0}\" doesn't exist", source));
		}
		copyDirectory(source, new File(targetWorkspacePath, projectName));

		return createProject(projectName);
	}

	protected IScriptProject setUpScriptProject(final String projectName)
			throws CoreException, IOException {
		final IProject project = setUpProject(projectName);
		return DLTKCore.create(project);
	}

	/**
	 * Returns the specified source module in the given project, root, and
	 * folder or <code>null</code> if it does not exist.
	 */
	public ISourceModule getSourceModule(String projectName, String rootPath,
			IPath path) throws ModelException {
		IScriptFolder folder = getScriptFolder(projectName, rootPath,
				path.removeLastSegments(1));
		if (folder == null) {
			return null;
		}
		return folder.getSourceModule(path.lastSegment());
	}

	public ISourceModule getSourceModule(String projectName, String rootPath,
			String path) throws ModelException {
		IScriptFolder folder = getScriptFolder(projectName, rootPath, new Path(
				path).removeLastSegments(1));
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
	public IScriptFolder getScriptFolder(String projectName,
			String fragmentPath, IPath path) throws ModelException {
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
	public IProjectFragment getProjectFragment(String projectName,
			String fragmentPath) throws ModelException {

		IScriptProject project = getScriptProject(projectName);
		if (project == null) {
			return null;
		}
		IPath path = new Path(fragmentPath);
		if (path.isAbsolute()) {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
					.getRoot();
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
				if (root.getUnderlyingResource().getProjectRelativePath()
						.equals(path)) {
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

	protected void discardWorkingCopies(ISourceModule[] units)
			throws ModelException {
		if (units == null)
			return;
		for (int i = 0, length = units.length; i < length; i++)
			if (units[i] != null)
				units[i].discardWorkingCopy();
	}

	public void setUpSuite() throws Exception {
		super.setUpSuite();

		// ensure autobuilding is turned off
		WorkspaceAutoBuild.disable();
	}

	/**
	 * Deprecated since 2012-01-11 because of the typo in method name.
	 */
	@Deprecated
	public static void disableAutoBulid() throws CoreException {
		WorkspaceAutoBuild.disable();
	}

	protected ISourceModule getSourceModule(String path) {
		return (ISourceModule) DLTKCore.create(getFile(path));
	}

	protected IFile getFile(String path) {
		return getWorkspaceRoot().getFile(new Path(path));
	}

	protected IFolder getFolder(IPath path) {
		return getWorkspaceRoot().getFolder(path);
	}

	protected void deleteFolder(IPath folderPath) throws CoreException {
		deleteResource(getFolder(folderPath));
	}

	/**
	 * Returns the IWorkspace this test suite is running on.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static IWorkspaceRoot getWorkspaceRoot() {
		return getWorkspace().getRoot();
	}

	public static IProject getProject(String project) {
		return getWorkspaceRoot().getProject(project);
	}

	/*
	 * Create simple project.
	 */
	protected IProject createProject(final String projectName)
			throws CoreException {
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
	protected IScriptProject createScriptProject(String projectName,
			String[] natures, String[] sourceFolders) throws CoreException {
		return createScriptProject(projectName, natures, sourceFolders, null);
	}

	protected IScriptProject createScriptProject(final String projectName,
			final String[] natures, final String[] sourceFolders,
			final String[] projects) throws CoreException {
		return createScriptProject(projectName, natures, sourceFolders,
				projects, null);
	}

	/*
	 * Creates a script project with the given source folders an output
	 * location. Add those on the project's buildpath.
	 */
	protected IScriptProject createScriptProject(final String projectName,
			final String[] natures, final String[] sourceFolders,
			final String[] projects, final String[] containers)
			throws CoreException {
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
				int sourceLength = sourceFolders == null ? 0
						: sourceFolders.length;
				int containersLength = containers == null ? 0
						: containers.length;
				int projectLength = projects == null ? 0 : projects.length;
				IBuildpathEntry[] entries = new IBuildpathEntry[sourceLength
						+ projectLength + containersLength];
				for (int i = 0; i < sourceLength; i++) {
					IPath sourcePath = new Path(sourceFolders[i]);
					int segmentCount = sourcePath.segmentCount();
					if (segmentCount > 0) {
						// create folder and its parents
						IContainer container = project;
						for (int j = 0; j < segmentCount; j++) {
							IFolder folder = container.getFolder(new Path(
									sourcePath.segment(j)));
							if (!folder.exists()) {
								folder.create(true, true, null);
							}
							container = folder;
						}
					}
					// create source entry
					entries[i] = DLTKCore.newSourceEntry(projectPath
							.append(sourcePath));
				}

				for (int i = 0; i < projectLength; i++) {

					// accessible files
					IPath[] accessibleFiles;
					accessibleFiles = new IPath[0];

					// non accessible files
					IPath[] nonAccessibleFiles;
					nonAccessibleFiles = new IPath[0];

					entries[sourceLength + i] = DLTKCore.newProjectEntry(
							new Path(projects[i]), BuildpathEntry
									.getAccessRules(accessibleFiles,
											nonAccessibleFiles), true,
							new IBuildpathAttribute[0], false);
				}

				for (int i = 0; i < containersLength; i++) {
					entries[sourceLength + projectLength + i] = DLTKCore
							.newContainerEntry(new Path(containers[i]));
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

	/*
	 * Asserts that the given actual source (usually coming from a file content)
	 * is equal to the expected one. Note that 'expected' is assumed to have the
	 * '\n' line separator. The line separators in 'actual' are converted to
	 * '\n' before the comparison.
	 */
	protected void assertSourceEquals(String message, String expected,
			String actual) {
		if (actual == null) {
			assertEquals(message, expected, null);
			return;
		}
		actual = DltkUtil.convertToIndependantLineDelimiter(actual);
		if (!actual.equals(expected)) {
			System.out.print(DltkUtil.displayString(actual.toString(), 2));
			System.out.println(this.endChar);
		}
		assertEquals(message, expected, actual);
	}

	/**
	 * Delete this resource.
	 */
	public static void deleteResource(IResource resource) throws CoreException {
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
				System.out.println("Retry " + retryCount + ": "
						+ e.getMessage());
			} catch (IllegalArgumentException iae) {
				// just print for info
				System.out.println("Retry " + retryCount + ": "
						+ iae.getMessage());
			}
		}
		if (!resource.isAccessible())
			return;
		System.err.println("Failed to delete " + resource.getFullPath());
		if (lastException != null) {
			throw lastException;
		}
	}

	protected IFolder createFolder(IPath path) throws CoreException {
		final IFolder folder = getWorkspaceRoot().getFolder(path);
		getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IContainer parent = folder.getParent();
				if (parent instanceof IFolder && !parent.exists()) {
					createFolder(parent.getFullPath());
				}
				folder.create(true, true, null);
			}
		}, null);

		return folder;
	}

	public void deleteProject(String projectName) throws CoreException {
		IProject project = getProject(projectName);
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
	 * Batch deletion of projects
	 */
	protected void deleteProjects(final String... projectNames)
			throws CoreException {
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				if (projectNames != null) {
					for (int i = 0, max = projectNames.length; i < max; i++) {
						if (projectNames[i] != null)
							deleteProject(projectNames[i]);
					}
				}
			}
		}, null);
	}

	/**
	 * Starts listening to element deltas, and queues them in fgDeltas.
	 */
	public void startDeltas() {
		clearDeltas();
		DLTKCore.addElementChangedListener(this.deltaListener);
	}

	/**
	 * Stops listening to element deltas, and clears the current deltas.
	 */
	public void stopDeltas() {
		DLTKCore.removeElementChangedListener(this.deltaListener);
		clearDeltas();
	}

	/**
	 * Empties the current deltas.
	 */
	public void clearDeltas() {
		this.deltaListener.deltas = new IModelElementDelta[0];
	}

	protected void sortElements(IModelElement[] elements) {
		Comparer comparer = new Comparer() {
			public int compare(Object a, Object b) {
				ModelElement elementA = (ModelElement) a;
				ModelElement elementB = (ModelElement) b;
				// char[] tempJCLPath = "<externalJCLPath>".toCharArray();
				// String idA = new String(CharOperation.replace(
				// elementA.toStringWithAncestors().toCharArray(),
				// getExternalJCLPathString().toCharArray(),
				// tempJCLPath));
				// String idB = new String(CharOperation.replace(
				// elementB.toStringWithAncestors().toCharArray(),
				// getExternalJCLPathString().toCharArray(),
				// tempJCLPath));
				return elementA.toStringWithAncestors().compareTo(
						elementB.toStringWithAncestors().toString());
				// return idA.compareTo(idB);
			}
		};
		org.eclipse.dltk.internal.core.util.Util.sort(elements, comparer);
	}

	protected void assertSortedElementsEqual(String message, String expected,
			IModelElement[] elements) {
		sortElements(elements);
		assertElementsEqual(message, expected, elements);
	}

	protected void assertElementsEqual(String message, String expected,
			IModelElement[] elements) {
		assertElementsEqual(message, expected, elements, false/*
															 * don't show key
															 */);
	}

	protected void assertElementsEqual(String message, String expected,
			IModelElement[] elements, boolean showResolvedInfo) {
		StringBuffer buffer = new StringBuffer();
		if (elements != null) {
			for (int i = 0, length = elements.length; i < length; i++) {
				ModelElement element = (ModelElement) elements[i];
				if (element == null) {
					buffer.append("<null>");
				} else {
					buffer.append(element
							.toStringWithAncestors(showResolvedInfo));
				}
				if (i != length - 1)
					buffer.append("\n");
			}
		} else {
			buffer.append("<null>");
		}
		String actual = buffer.toString();
		if (!expected.equals(actual)) {
			if (this.displayName)
				System.out.println(getName() + " actual result is:");
			System.out.println(DltkUtil.displayString(actual, this.tabs)
					+ this.endChar);
		}
		assertEquals(message, expected, actual);
	}

	protected void sortResources(Object[] resources) {
		Util.Comparer comparer = new Util.Comparer() {
			public int compare(Object a, Object b) {
				IResource resourceA = (IResource) a;
				IResource resourceB = (IResource) b;
				return resourceA.getFullPath().toString()
						.compareTo(resourceB.getFullPath().toString());
			}
		};
		Util.sort(resources, comparer);
	}

	protected void assertResourceNamesEqual(String message, String expected,
			Object[] resources) {
		sortResources(resources);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0, length = resources.length; i < length; i++) {
			IResource resource = (IResource) resources[i];
			buffer.append(resource == null ? "<null>" : resource.getName());
			if (i != length - 1)
				buffer.append("\n");
		}
		if (!expected.equals(buffer.toString())) {
			System.out.print(DltkUtil.displayString(buffer.toString(), 2));
			System.out.println(this.endChar);
		}
		assertEquals(message, expected, buffer.toString());
	}

	protected void assertDeltas(String message, String expected) {
		String actual = this.deltaListener.toString();
		if (!expected.equals(actual)) {
			System.out.println(actual);
		}
		assertEquals(message, expected, actual);
	}

	protected void assertDeltas(String message, String expected,
			IModelElementDelta delta) {
		String actual = delta == null ? "<null>" : delta.toString();
		if (!expected.equals(actual)) {
			System.out.println(actual);
		}
		assertEquals(message, expected, actual);
	}

	protected void assertMarkers(String message, String expectedMarkers,
			IScriptProject project) throws CoreException {
		IMarker[] markers = project.getProject().findMarkers(
				IScriptModelMarker.BUILDPATH_PROBLEM_MARKER, false,
				IResource.DEPTH_ZERO);
		sortMarkers(markers);
		assertMarkers(message, expectedMarkers, markers);
	}

	protected void assertMarkers(String message, String expectedMarkers,
			IMarker[] markers) throws CoreException {
		StringBuffer buffer = new StringBuffer();
		if (markers != null) {
			for (int i = 0, length = markers.length; i < length; i++) {
				IMarker marker = markers[i];
				buffer.append(marker.getAttribute(IMarker.MESSAGE));
				if (i != length - 1) {
					buffer.append("\n");
				}
			}
		}
		String actual = buffer.toString();
		if (!expectedMarkers.equals(actual)) {
			System.out.println(DltkUtil.displayString(actual, 2));
		}
		assertEquals(message, expectedMarkers, actual);
	}

	protected void sortMarkers(IMarker[] markers) {
		org.eclipse.dltk.internal.core.util.Util.Comparer comparer = new org.eclipse.dltk.internal.core.util.Util.Comparer() {
			public int compare(Object a, Object b) {
				IMarker markerA = (IMarker) a;
				IMarker markerB = (IMarker) b;
				return markerA
						.getAttribute(IMarker.MESSAGE, "").compareTo(markerB.getAttribute(IMarker.MESSAGE, "")); //$NON-NLS-1$ //$NON-NLS-2$
			}
		};
		org.eclipse.dltk.internal.core.util.Util.sort(markers, comparer);
	}

	/**
	 * Sets the class path of the script project.
	 */
	public void setBuildpath(IScriptProject scriptProject,
			IBuildpathEntry[] buildpath) {
		try {
			scriptProject.setRawBuildpath(buildpath, null);
		} catch (ModelException e) {
			assertTrue("failed to set buildpath", false);
		}
	}

	/**
	 * Returns the last delta for the given element from the cached delta.
	 */
	protected IModelElementDelta getDeltaFor(IModelElement element) {
		return getDeltaFor(element, false);
	}

	/**
	 * Returns the delta for the given element from the cached delta. If the
	 * boolean is true returns the first delta found.
	 */
	protected IModelElementDelta getDeltaFor(IModelElement element,
			boolean returnFirst) {
		IModelElementDelta[] deltas = this.deltaListener.deltas;
		if (deltas == null)
			return null;
		IModelElementDelta result = null;
		for (int i = 0; i < deltas.length; i++) {
			IModelElementDelta delta = searchForDelta(element,
					this.deltaListener.deltas[i]);
			if (delta != null) {
				if (returnFirst) {
					return delta;
				}
				result = delta;
			}
		}
		return result;
	}

	/**
	 * Returns a delta for the given element in the delta tree
	 */
	protected IModelElementDelta searchForDelta(IModelElement element,
			IModelElementDelta delta) {

		if (delta == null) {
			return null;
		}
		if (delta.getElement().equals(element)) {
			return delta;
		}
		for (int i = 0; i < delta.getAffectedChildren().length; i++) {
			IModelElementDelta child = searchForDelta(element,
					delta.getAffectedChildren()[i]);
			if (child != null) {
				return child;
			}
		}
		return null;
	}

	/**
	 * Ensures the elements are present after creation.
	 */
	public void assertCreation(IModelElement[] newElements) {
		for (int i = 0; i < newElements.length; i++) {
			IModelElement newElement = newElements[i];
			assertTrue("Element should be present after creation",
					newElement.exists());
		}
	}

	/**
	 * Ensures the element is present after creation.
	 */
	public void assertCreation(IModelElement newElement) {
		assertCreation(new IModelElement[] { newElement });
	}

	/**
	 * Creates an operation to delete the given elements, asserts the operation
	 * is successful, and ensures the elements are no longer present in the
	 * model.
	 */
	public void assertDeletion(IModelElement[] elementsToDelete)
			throws ModelException {
		IModelElement elementToDelete = null;
		for (int i = 0; i < elementsToDelete.length; i++) {
			elementToDelete = elementsToDelete[i];
			assertTrue("Element must be present to be deleted",
					elementToDelete.exists());
		}

		getScriptModel().delete(elementsToDelete, false, null);

		for (int i = 0; i < elementsToDelete.length; i++) {
			elementToDelete = elementsToDelete[i];
			assertTrue("Element should not be present after deletion: "
					+ elementToDelete, !elementToDelete.exists());
		}
	}

	/**
	 * Returns the script Model this test suite is running on.
	 */
	public IScriptModel getScriptModel() {
		return DLTKCore.create(getWorkspaceRoot());
	}

	/**
	 * Wait for autobuild notification to occur
	 */
	public static void waitForAutoBuild() {
		WorkspaceAutoBuild.waitFor();
	}

	public void ensureCorrectPositioning(IParent container,
			IModelElement sibling, IModelElement positioned)
			throws ModelException {
		IModelElement[] children = container.getChildren();
		if (sibling != null) {
			// find the sibling
			boolean found = false;
			for (int i = 0; i < children.length; i++) {
				if (children[i].equals(sibling)) {
					assertTrue("element should be before sibling", i > 0
							&& children[i - 1].equals(positioned));
					found = true;
					break;
				}
			}
			assertTrue("Did not find sibling", found);
		}
	}

	public ISourceModule getWorkingCopy(String path, boolean computeProblems)
			throws ModelException {
		return getWorkingCopy(path, "", computeProblems);
	}

	public ISourceModule getWorkingCopy(String path, String source)
			throws ModelException {
		return getWorkingCopy(path, source, false);
	}

	public ISourceModule getWorkingCopy(String path, String source,
			boolean computeProblems) throws ModelException {
		if (this.wcOwner == null)
			this.wcOwner = new WorkingCopyOwner() {
			};
		return getWorkingCopy(path, source, this.wcOwner, computeProblems);
	}

	public ISourceModule getWorkingCopy(String path, String source,
			WorkingCopyOwner owner, boolean computeProblems)
			throws ModelException {
		IProblemRequestor problemRequestor = computeProblems ? new IProblemRequestor() {
			public void acceptProblem(IProblem problem) {
			}

			public void beginReporting() {
			}

			public void endReporting() {
			}

			public boolean isActive() {
				return true;
			}
		}
				: null;
		return getWorkingCopy(path, source, owner, problemRequestor);
	}

	public ISourceModule getWorkingCopy(String path, String source,
			WorkingCopyOwner owner, IProblemRequestor problemRequestor)
			throws ModelException {
		ISourceModule workingCopy = getSourceModule(path);
		if (owner != null)
			workingCopy = workingCopy
					.getWorkingCopy(/* owner, problemRequestor, */null/*
																	 * no
																	 * progress
																	 * monitor
																	 */);
		else
			workingCopy.becomeWorkingCopy(problemRequestor, null/*
																 * no progress
																 * monitor
																 */);
		workingCopy.getBuffer().setContents(source);
		// if (problemRequestor instanceof ProblemRequestor)
		// ((ProblemRequestor)
		// problemRequestor).initialize(source.toCharArray());
		workingCopy.makeConsistent(null/* no progress monitor */);
		return workingCopy;
	}

	public static void waitUntilIndexesReady() {
		ModelManager.getModelManager().getIndexManager().waitUntilReady();
	}

	public static void storeFile(File dest, URL url) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new BufferedInputStream(url.openStream());

			output = new BufferedOutputStream(new FileOutputStream(dest));

			// Simple copy
			int ch = -1;
			while ((ch = input.read()) != -1) {
				output.write(ch);
			}
		} finally {
			if (input != null) {
				input.close();
			}

			if (output != null) {
				output.close();
			}
		}
	}

	public static File storeToMetadata(Bundle bundle, String name, String path)
			throws IOException {
		File pathFile = DLTKCore.getDefault().getStateLocation().append(name)
				.toFile();
		storeFile(pathFile, FileLocator.resolve(bundle.getEntry(path)));
		return pathFile;
	}
}
