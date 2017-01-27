/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.util.Util;
import org.eclipse.php.composer.core.ComposerBuildpathContainerInitializer;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.internal.core.project.PHPNature;
import org.osgi.service.prefs.BackingStoreException;

public class PackageManager {
	private Map<String, BuildpathPackage> packages;

	/**
	 * Maps project to installed local packages
	 */
	private Map<String, List<InstalledPackage>> installedPackages;

	private Map<String, List<InstalledPackage>> installedDevPackages;

	public final static String BP_COMPOSERPACKAGE_PREFERENCES_PREFIX = ComposerPlugin.ID + ".composerPackage."; //$NON-NLS-1$

	public final static String BP_PROJECT_BUILDPATH_PREFIX = ComposerPlugin.ID + ".projectPackages#"; //$NON-NLS-1$
	public final static String BP_PROJECT_BUILDPATH_DEV_PREFIX = ComposerPlugin.ID + ".projectDevPackages#"; //$NON-NLS-1$

	private BuildpathJob buildpathJob;

	public PackageManager() {
		initialize();
	}

	private void reloadPackages() {

		IEclipsePreferences instancePreferences = ConfigurationScope.INSTANCE.getNode(ComposerPlugin.ID);

		String[] propertyNames;
		try {
			propertyNames = instancePreferences.keys();
		} catch (BackingStoreException e) {
			Util.log(e, "Exception while initializing user libraries"); //$NON-NLS-1$
			return;
		}

		for (int i = 0, length = propertyNames.length; i < length; i++) {
			String propertyName = propertyNames[i];
			if (propertyName.startsWith(BP_PROJECT_BUILDPATH_PREFIX)) {
				String propertyValue = instancePreferences.get(propertyName, null);
				if (propertyValue != null) {
					try {
						List<InstalledPackage> packages = InstalledPackage.deserialize(propertyValue);
						installedPackages.put(unpackProjectName(propertyName), packages);
					} catch (IOException e) {
						Logger.logException(e);
					}
				}
			} else if (propertyName.startsWith(BP_PROJECT_BUILDPATH_DEV_PREFIX)) {
				String propertyValue = instancePreferences.get(propertyName, null);
				if (propertyValue != null) {
					try {
						List<InstalledPackage> packages = InstalledPackage.deserialize(propertyValue);
						installedDevPackages.put(unpackProjectName(propertyName), packages);
					} catch (IOException e) {
						Logger.logException(e);
					}
				}
			}
		}
	}

	private String unpackProjectName(String propertyName) {

		String[] strings = propertyName.split("#"); //$NON-NLS-1$
		return strings[1];
	}

	private void initialize() {

		packages = new HashMap<String, BuildpathPackage>();
		installedPackages = new HashMap<String, List<InstalledPackage>>();
		installedDevPackages = new HashMap<String, List<InstalledPackage>>();
		IEclipsePreferences instancePreferences = ConfigurationScope.INSTANCE.getNode(ComposerPlugin.ID);

		String[] propertyNames;
		try {
			propertyNames = instancePreferences.keys();
		} catch (BackingStoreException e) {
			Util.log(e, "Exception while initializing user libraries"); //$NON-NLS-1$
			return;
		}

		boolean preferencesNeedFlush = false;
		for (int i = 0, length = propertyNames.length; i < length; i++) {
			String propertyName = propertyNames[i];
			if (propertyName.startsWith(BP_COMPOSERPACKAGE_PREFERENCES_PREFIX)) {
				String propertyValue = instancePreferences.get(propertyName, null);
				if (propertyValue != null) {
					String libName = propertyName.substring(BP_COMPOSERPACKAGE_PREFERENCES_PREFIX.length());
					StringReader reader = new StringReader(propertyValue);
					BuildpathPackage library;
					try {
						library = BuildpathPackage.createFromString(reader);
					} catch (Exception e) {
						Util.log(e, "Exception while initializing user library " + libName); //$NON-NLS-1$
						instancePreferences.remove(propertyName);
						preferencesNeedFlush = true;
						continue;
					}
					packages.put(libName, library);
				}
			}
		}
		if (preferencesNeedFlush) {
			try {
				instancePreferences.flush();
			} catch (BackingStoreException e) {
				Util.log(e, "Exception while flusing instance preferences"); //$NON-NLS-1$
			}
		}

		buildpathJob = new BuildpathJob();

		reloadPackages();
	}

	public synchronized void setPackage(String name, IBuildpathEntry[] buildpathEntries, boolean isSystemLibrary) {
		IEclipsePreferences prefs = ConfigurationScope.INSTANCE.getNode(ComposerPlugin.ID);
		String propertyName = BP_COMPOSERPACKAGE_PREFERENCES_PREFIX + makePackageName(name);
		try {
			String propertyValue = BuildpathPackage.serialize(buildpathEntries, isSystemLibrary);

			prefs.put(propertyName, propertyValue);
			prefs.flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	public void removePackage(String name) {
		try {
			IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode(ComposerPlugin.ID);

			String propertyName = BP_COMPOSERPACKAGE_PREFERENCES_PREFIX + makePackageName(name);

			preferences.remove(propertyName);
			preferences.flush();
		} catch (BackingStoreException e) {
			Util.log(e, "Exception while removing user library " + name); //$NON-NLS-1$
		}
	}

	public BuildpathPackage getPackage(String packageName) {
		if (!packages.containsKey(packageName)) {
			return null;
		}
		return (BuildpathPackage) packages.get(makePackageName(packageName));
	}

	private Object makePackageName(String packageName) {
		return PHPNature.ID + "#" + packageName; //$NON-NLS-1$
	}

	private String getPackageName(String key) {
		int pos = key.indexOf("#"); //$NON-NLS-1$
		if (pos != -1) {
			return key.substring(pos + 1);
		}
		return key;
	}

	public synchronized String[] getPackageNames() {

		Set<String> set = packages.keySet();
		Set<String> result = new HashSet<String>();
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			result.add(getPackageName(key));
		}

		return (String[]) result.toArray(new String[result.size()]);
	}

	public PackagePath[] getPackagePaths(IScriptProject project) {
		List<PackagePath> packagePaths = new ArrayList<PackagePath>();

		try {
			IBuildpathContainer container = ModelManager.getModelManager()
					.getBuildpathContainer(new Path(ComposerBuildpathContainerInitializer.CONTAINER), project);

			for (IBuildpathEntry entry : container.getBuildpathEntries()) {
				packagePaths.add(new PackagePath(entry, project));
			}
		} catch (ModelException e) {
			Logger.logException(e.getStatus().getMessage(), e);
		}

		return packagePaths.toArray(new PackagePath[packagePaths.size()]);
	}

	public void updateBuildpath() {

		if (buildpathJob == null) {
			return;
		}

		synchronized (buildpathJob) {
			buildpathJob.cancel();
			buildpathJob.setPriority(Job.LONG);
			buildpathJob.schedule(1000);
		}
	}

	public void updateBuildpath(IProject project) {
		if (buildpathJob == null) {
			return;
		}

		synchronized (buildpathJob) {
			buildpathJob.cancel();
			buildpathJob.setPriority(Job.LONG);
			buildpathJob.setProject(project);
			buildpathJob.schedule(1000);
		}
	}

	private class BuildpathJob extends Job {

		private IPath installedPath;
		private IPath installedDevPath;
		private IProject project;

		private boolean running;

		public BuildpathJob() {
			super(Messages.PackageManager_BuildPathJobName);
			installedPath = new Path("vendor/composer/installed.json"); //$NON-NLS-1$
			installedDevPath = new Path("vendor/composer/installed_dev.json"); //$NON-NLS-1$
		}

		public void setProject(IProject project) {
			this.project = project;
		}

		private void installLocalPackage(InstalledPackage installedPackage, IProject project) {
			IPath path = new Path("vendor").append(installedPackage.name); //$NON-NLS-1$

			Logger.debug("Installing local version of " + installedPackage.getFullName()); //$NON-NLS-1$
			IResource resource = project.findMember(path);

			if (resource instanceof IFolder) {
				IFolder folder = (IFolder) resource;
				File file = folder.getRawLocation().makeAbsolute().toFile();

				if (file != null && file.exists() && installedPackage.getLocalFile() != null) {
					try {
						Logger.debug("Installing local package " + installedPackage.name + " to " //$NON-NLS-1$//$NON-NLS-2$
								+ installedPackage.getLocalFile().getAbsolutePath());
						installedPackage.getLocalFile().mkdirs();
						final java.nio.file.Path sourcePath = file.toPath();
						final java.nio.file.Path packagePath = installedPackage.getLocalFile().toPath();
						SimpleFileVisitor<java.nio.file.Path> n = new SimpleFileVisitor<java.nio.file.Path>() {
							@Override
							public FileVisitResult preVisitDirectory(java.nio.file.Path dirPath,
									BasicFileAttributes attrs) throws IOException {
								java.nio.file.Path targetPath = packagePath.resolve(sourcePath.relativize(dirPath));
								if (!Files.exists(targetPath)) {
									Files.createDirectory(targetPath);
								}

								return FileVisitResult.CONTINUE;
							}

							@Override
							public FileVisitResult visitFile(java.nio.file.Path filePath, BasicFileAttributes attrs)
									throws IOException {
								Files.copy(filePath, packagePath.resolve(sourcePath.relativize(filePath)),
										StandardCopyOption.REPLACE_EXISTING);

								return FileVisitResult.CONTINUE;
							}
						};
						Files.walkFileTree(file.toPath(), n);
					} catch (IOException e) {
						Logger.logException(e);
					}
				}
			} else {
				Logger.debug("Unable to find folder in project for path " + path.toString()); //$NON-NLS-1$
			}
		}

		@Override
		protected void canceling() {
			super.canceling();
			running = false;
		}

		private void handleDevPackages(IProject project) throws Exception {

			handlePackages(project, BP_PROJECT_BUILDPATH_DEV_PREFIX + project.getName(), installedDevPath);
		}

		private void handleProdPackages(IProject project) throws Exception {

			handlePackages(project, BP_PROJECT_BUILDPATH_PREFIX + project.getName(), installedPath);

		}

		private void handlePackages(IProject project, String propertyName, IPath path) throws Exception {

			IFile installed = (IFile) project.findMember(path);

			if (installed == null) {
				Logger.debug("Unable to find '" + path.lastSegment() + "' in " + project.getName() + " using path " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ path.toString());
				return;
			}

			List<InstalledPackage> json = InstalledPackage.deserialize(installed.getContents());
			installPackages(json, project);
			persist(propertyName, installed);
		}

		private void persist(String key, IFile file) throws IOException, CoreException, BackingStoreException {

			IEclipsePreferences prefs = ConfigurationScope.INSTANCE.getNode(ComposerPlugin.ID);
			StringWriter writer = new StringWriter();
			InputStream contents = file.getContents();
			Scanner s = new Scanner(contents).useDelimiter("\\A"); //$NON-NLS-1$
			String propertyValue = s.hasNext() ? s.next() : ""; //$NON-NLS-1$
			contents.close();
			prefs.put(key, propertyValue);
			prefs.flush();
			writer.close();
		}

		private void installPackages(List<InstalledPackage> packages, IProject project) {

			Logger.debug("Installing local packages for project " + project.getName()); //$NON-NLS-1$

			for (InstalledPackage installedPackage : packages) {
				if (!installedPackage.isLocalVersionAvailable()) {
					installLocalPackage(installedPackage, project);
				} else {
					Logger.debug(installedPackage.getFullName() + " is already installed locally"); //$NON-NLS-1$
				}
			}
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			Logger.debug("Running buildpath job"); //$NON-NLS-1$
			running = true;
			monitor.setTaskName(Messages.PackageManager_BuildPathTaskName);

			if (project != null) {
				updateProject(project);
				monitor.worked(1);
			} else {
				for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
					updateProject(project);
					monitor.worked(1);
				}
			}

			reloadPackages();
			return Status.OK_STATUS;
		}

		protected void updateProject(IProject project) {
			try {
				if (!running) {
					Logger.debug("Job cancelled"); //$NON-NLS-1$
					throw new InterruptedException();
				}

				if (!FacetManager.hasComposerFacet(project)) {
					Logger.debug("Buildpath not running on project without composer nature " + project.getName()); //$NON-NLS-1$
					return;
				}

				IFile installed = (IFile) project.findMember(installedPath);

				if (installed == null) {
					Logger.debug("Unabled to find installed.json in project " + project.getName()); //$NON-NLS-1$
					return;
				}

				handleProdPackages(project);
				handleDevPackages(project);
				DLTKCore.refreshBuildpathContainers(DLTKCore.create(project));

			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	public List<InstalledPackage> getInstalledPackages(IScriptProject project) {
		if (installedPackages.containsKey(project.getProject().getName())) {
			return installedPackages.get(project.getProject().getName());
		}

		return null;
	}

	public List<InstalledPackage> getInstalledDevPackages(IScriptProject project) {

		if (installedDevPackages.containsKey(project.getProject().getName())) {
			return installedDevPackages.get(project.getProject().getName());
		}

		return null;
	}

	public List<InstalledPackage> getAllPackages(IScriptProject project) {

		List<InstalledPackage> allPackages = new ArrayList<InstalledPackage>();

		if (!installedPackages.containsKey(project.getProject().getName())) {
			return allPackages;
		}

		for (InstalledPackage pack : installedPackages.get(project.getProject().getName())) {
			pack.isDev = false;
			allPackages.add(pack);
		}

		if (installedDevPackages.containsKey(project.getProject().getName())) {
			for (InstalledPackage pack : installedDevPackages.get(project.getProject().getName())) {
				pack.isDev = true;
				allPackages.add(pack);
			}
		}

		return allPackages;
	}

	public void removeProject(IProject project) {
		try {

			String name = project.getName();
			String propertyName = BP_PROJECT_BUILDPATH_PREFIX + name;
			IEclipsePreferences instancePreferences = ConfigurationScope.INSTANCE.getNode(ComposerPlugin.ID);
			instancePreferences.remove(propertyName);
			instancePreferences.flush();

			if (installedPackages.containsKey(name)) {
				installedPackages.remove(name);
			}

		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}
}
