/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.internal.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.IPhpProjectOptionChangeListener;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionChangedHandler;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.php.internal.core.util.DefaultCacheManager;
import org.eclipse.php.internal.core.util.IncludeCacheManager;

public class PHPIncludePathModelManager extends PhpModelProxy implements ExternalFilesModel {

	CompositePhpModel compositePhpModel;
	private PHPParserManager parserManager;
	private String phpVersion;

	private ArrayList libraries;
	private ArrayList projects;
	private ArrayList variables;
	private String[] validExtensions;
	private IncludePathListener includePathListener;
	private PhpVersionListener phpVersionListener;
	private IProject project;
	private HashMap modelsToCache;
	private IncludeCacheManager includeCacheManager;
	private boolean initialized;
	private IResourceChangeListener projectResourcesListener;

	public PHPIncludePathModelManager() {
		compositePhpModel = new CompositePhpModel() {
			public String getID() {
				return "CompositeIncludePathModel"; //$NON-NLS-1$
			}

			public void initialize(IProject project) {
			}
		};
		model = compositePhpModel;
		libraries = new ArrayList();
		projects = new ArrayList();
		variables = new ArrayList();
		modelsToCache = new HashMap();
	}

	public IPhpModel getModel(String id) {
		return compositePhpModel.getModel(id);
	}

	public void initialize(IProject project) {
		if (initialized) {
			if (project != this.project) {
				PHPCorePlugin.logErrorMessage("PHPIncludePathModelManager in already initialized with a different project."); //$NON-NLS-1$
			}
			return;
		}
		includeCacheManager = DefaultCacheManager.instance().getIncludeCacheManager();
		this.project = project;
		initListeners();

		setPHPVersion(PhpVersionProjectPropertyHandler.getVersion(project));

		PHPProjectOptions options = PHPProjectOptions.forProject(project);
		if (options == null) {
			return;
		}
		IIncludePathEntry[] entries = options.readRawIncludePath();
		loadChanges(entries);

		// Update the XML mapping for this project.
		List list = new ArrayList(variables);
		list.addAll(libraries);
		includeCacheManager.includePathChanged(project, getAsCachedNames(list));

		initialized = true;
	}

	public IPhpModel[] listModels() {
		return compositePhpModel.getModels();
	}

	private void addLibrary(File library) {
		if (getIndexOf(library) != -1) {
			return;
		}
		libraries.add(library);
		innerAddLibrary(library);
	}

	private void innerAddLibrary(File library) {
		PHPIncludePathModel model = new PHPIncludePathModel(library.getPath(), PHPIncludePathModel.TYPE_LIBRARY);

		PHPLanguageManager languageManager = PHPLanguageManagerProvider.instance().getPHPLanguageManager(phpVersion);
		ParserClient client = languageManager.createParserClient(model, project);

		updateExtentionList();
		if (library.exists()) {
			// Load the cache for the library
			PHPIncludePathModel cacheModel = new PHPIncludePathModel(library.getPath(), PHPIncludePathModel.TYPE_LIBRARY);
			DefaultCacheManager.instance().load(project, cacheModel, true);
			recursiveParse(library, client, model, cacheModel);
		}
		compositePhpModel.addModel(model);
	}

	private void recursiveParse(File folder, ParserClient client, PHPIncludePathModel model, PHPIncludePathModel cachedModel) {
		File[] children = folder.listFiles();

		for (int i = 0; i < children.length; i++) {
			if (children[i].isDirectory()) {
				recursiveParse(children[i], client, model, cachedModel);
			} else if (children[i].isFile()) {
				File file = children[i];
				if (isPhpFile(file.getName())) {
					String fileName = file.getPath();
					PHPFileData fileData = cachedModel.getFileData(fileName);
					// If the file is cached, update the model with the cached version.
					if (isValid(fileData, file)) {
						model.insert(fileData);
						cachedModel.delete(fileName);
					} else {
						// add the model to the list of need-to-cache models
						modelsToCache.put(model, model);
						parse(file, client);
					}
				}
			}
		}
	}

	/*
	 * Since we can't use the PHPFileData isValid (the include is not a resource) we have to check the validity here.
	 */
	private boolean isValid(PHPFileData fileData, File file) {
		if (fileData != null && file != null) {
			return fileData.getCreationTimeLastModified() - file.lastModified() >= 0;
		}
		return false;
	}

	private void parse(File file, ParserClient client) {
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(is);

			parserManager.parse(inputStreamReader, file.getPath(), file.lastModified(), client, UseAspTagsHandler.useAspTagsAsPhp(project));

		} catch (FileNotFoundException e) {
			PHPCorePlugin.log(e);
			return;
		}
	}

	private boolean isPhpFile(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return false;
		}
		String ext = fileName.substring(index + 1);
		for (int i = 0; i < validExtensions.length; i++) {
			if (ext.equals(validExtensions[i])) {
				return true;
			}
		}
		return false;
	}

	private void removeLibrary(File library) {
		int index = getIndexOf(library);
		if (index == -1) {
			return;
		}
		libraries.remove(index);
		innerRemoveLibrary(library);
	}

	private void innerRemoveLibrary(File library) {
		IPhpModel removed = compositePhpModel.remove(library.getPath());
		if (removed != null) {
			modelsToCache.remove(removed);
		}
	}

//	class ZipInputStreamPermanentReader extends InputStreamReader {
//
//		public ZipInputStreamPermanentReader(InputStream in) {
//			super(in);
//		}
//
//		/* (non-Javadoc)
//		 * @see java.io.InputStreamReader#close()
//		 */
//		public void close() {
//			// don't close.
//		}
//	}

	//	private void parseZip(File zipFile, ParserClient client) {
//		ZipInputStream is = null;
//		try {
//			is = new ZipInputStream(new FileInputStream(zipFile));
//			ZipEntry ze;
//			while ((ze = is.getNextEntry()) != null) {
//				if (!ze.isDirectory()) {
//					String fileName = ze.getName();
//					if (isPhpFile(fileName)) {
//						ParserExecuter executer = new ParserExecuter(parserManager, null, client, zipFile.getName() + File.separator + fileName, new ZipInputStreamPermanentReader(is), new Pattern[0], zipFile.lastModified(), UseAspTagsHandler.useAspTagsAsPhp(project));
//						executer.run();
//					}
//				}
//			}
//			is.close();
//		} catch (FileNotFoundException e) {
//			//handled before
//		} catch (IOException io) {
//			Logger.logException(io);
//		}
//		if (is != null) {
//			try {
//				is.close();
//			} catch (IOException e) {
//			}
//		}
//	}

	private void updatePHPVersion(String oldVersion, String newVersion) {
		setPHPVersion(newVersion);
		includeCacheManager.phpVersionChanged(project, oldVersion, newVersion);
		// All the models that this include path model has to be re-cached
		// Any model that already exists is skipped.
		DefaultCacheManager cacheManager = DefaultCacheManager.instance();
		IPhpModel[] models = compositePhpModel.getModels();
		for (int i = 0; i < models.length; i++) {
			File cacheFile = cacheManager.getSharedCacheFile(newVersion, models[i].getID());
			if (!cacheFile.exists()) {
				modelsToCache.put(models[i], models[i]);
			} else {
				modelsToCache.remove(models[i]);
			}
		}
	}

	private void setPHPVersion(String phpVersion) {
		this.phpVersion = phpVersion;
		PHPLanguageManager languageManager = PHPLanguageManagerProvider.instance().getPHPLanguageManager(this.phpVersion);
		parserManager = languageManager.createPHPParserManager();
	}

	private int getIndexOf(File library) {
		int index = 0;
		String libraryName = library.getPath();
		Iterator iter = libraries.iterator();

		while (iter.hasNext()) {
			File tmp = (File) iter.next();
			if (tmp.getPath().equals(libraryName)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public void includePathChanged(IPath[] paths) {
		File[] newLibrariesList = new File[paths.length];

		for (int i = 0; i < paths.length; i++) {
			newLibrariesList[i] = new File(paths[i].toString());
			addLibrary(newLibrariesList[i]);
		}
		ArrayList olds = libraries;
		File[] oldLibrariesList = new File[olds.size()];
		olds.toArray(oldLibrariesList);

		for (int i = 0; i < oldLibrariesList.length; i++) {
			String currPath = oldLibrariesList[i].getPath();
			boolean found = false;
			for (int j = 0; j < newLibrariesList.length; j++) {
				if (newLibrariesList[j].getPath().equals(currPath)) {
					found = true;
					break;
				}
			}
			if (!found) {
				removeLibrary(oldLibrariesList[i]);
			}
		}
	}

	private void projectListChanged(IResource[] projects) {
		for (int i = 0; i < projects.length; i++) {
			addProject(projects[i]);
		}

		IResource[] oldProjectsList = new IResource[this.projects.size()];
		this.projects.toArray(oldProjectsList);

		for (int i = 0; i < oldProjectsList.length; i++) {
			String currName = oldProjectsList[i].getName();
			boolean found = false;
			for (int j = 0; j < projects.length; j++) {
				if (projects[j].getName().equals(currName)) {
					found = true;
					break;
				}
			}
			if (!found) {
				removeProject(oldProjectsList[i]);
			}
		}
	}

	private void addProject(IResource project) {
		if (getIndexOf(project) != -1) {
			return;
		}
		projects.add(project);
		innerAddProject((IProject) project);
	}

	private void innerAddProject(IProject project) {
		IPhpProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		IPhpModel userModel = null;
		if (projectModel == null) {
			userModel = new PHPUserModel();
		} else {
			userModel = projectModel.getModel(PHPUserModel.ID);
		}
		ModelWrapper modelWrapper = new ModelWrapper(userModel);
		modelWrapper.initialize(project);
		compositePhpModel.addModel(modelWrapper);
	}

	public void removeProject(IResource project) {
		int index = getIndexOf(project);
		if (index == -1) {
			return;
		}
		projects.remove(index);
		innerRemoveProject(project);
	}

	private void innerRemoveProject(IResource project) {
		IPhpModel removed = compositePhpModel.remove(project.getName());
		((ModelWrapper) removed).dispose();
	}

	private int getIndexOf(IResource project) {
		int index = 0;
		String projectName = project.getName();
		Iterator iter = projects.iterator();

		while (iter.hasNext()) {
			IResource tmp = (IResource) iter.next();
			if (tmp.getName().equals(projectName)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	private void updateExtentionList() {
		IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
	}

	private void initListeners() {
		final PHPProjectOptions options = PHPProjectOptions.forProject(project);
		if (options == null)
			return;

		includePathListener = new IncludePathListener();
		options.addOptionChangeListener(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, includePathListener);
		phpVersionListener = new PhpVersionListener();
		PhpVersionChangedHandler.getInstance().addPhpVersionChangedListener(phpVersionListener);

		if (projectResourcesListener == null) {
			projectResourcesListener = new ProjectResourceChangeListener();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(projectResourcesListener, IResourceChangeEvent.PRE_DELETE);
		}
	}

	public void dispose() {
		super.dispose();
		PHPProjectOptions options = PHPProjectOptions.forProject(project);
		if (options != null) {
			options.removeOptionChangeListener(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, includePathListener);
		}
		PhpVersionChangedHandler.getInstance().removePhpVersionChangedListener(phpVersionListener);

		IPath location = project.getLocation();
		if (location != null) {
			// Cache the models that are new or out-of-date
			Iterator models = modelsToCache.keySet().iterator();
			while (models.hasNext()) {
				IPhpModel model = (IPhpModel) models.next();
				DefaultCacheManager.instance().save(project, model, true);
			}
		}
	}

	public void clean() {
		//we shouldn't call super because it will rebuild the other projects model
		rebuild();
	}

	private void rebuild() {
		rebuildList(libraries);
		rebuildVariablesList();
	}

	private void rebuildList(ArrayList list) {
		Iterator iter = list.iterator();

		while (iter.hasNext()) {
			File library = (File) iter.next();
			innerRemoveLibrary(library);
			innerAddLibrary(library);
		}
	}

	private void rebuildVariablesList() {
		Iterator iter = variables.iterator();

		while (iter.hasNext()) {
			IPath variable = (IPath) iter.next();
			innerRemoveVariable(variable);
			innerAddVariable(variable);
		}
	}

	private void loadChanges(IIncludePathEntry[] entries) {
		ArrayList liberaries = new ArrayList();
		ArrayList projectsList = new ArrayList();
		ArrayList variablesList = new ArrayList();

		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
				if (entries[i].getContentKind() == IIncludePathEntry.K_BINARY) {
					// do nothing, support for zips was removed
				} else {
					liberaries.add(entries[i]);
				}
			} else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
				projectsList.add(entries[i]);
			} else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
				variablesList.add(entries[i]);
			}
		}
		IPath[] paths = new IPath[liberaries.size()];
		for (int i = 0; i < liberaries.size(); i++) {
			paths[i] = ((IIncludePathEntry) liberaries.get(i)).getPath();
		}

		includePathChanged(paths);

		IResource[] projects = new IResource[projectsList.size()];
		for (int i = 0; i < projectsList.size(); i++) {
			projects[i] = ((IIncludePathEntry) projectsList.get(i)).getResource();
		}

		projectListChanged(projects);

		IPath[] variablespaths = new IPath[variablesList.size()];
		for (int i = 0; i < variablesList.size(); i++) {
			variablespaths[i] = ((IIncludePathEntry) variablesList.get(i)).getPath();
		}

		variablesListChanged(variablespaths);
	}

	private void variablesListChanged(IPath[] paths) {

		for (int i = 0; i < paths.length; i++) {
			addVariable(paths[i]);
		}
		IPath[] oldVariablesList = new IPath[variables.size()];
		variables.toArray(oldVariablesList);

		for (int i = 0; i < oldVariablesList.length; i++) {
			IPath currVar = oldVariablesList[i];
			boolean found = false;
			for (int j = 0; j < paths.length; j++) {
				if (paths[j].equals(currVar)) {
					found = true;
					break;
				}
			}
			if (!found) {
				removeVariable(oldVariablesList[i]);
			}
		}

		if (initialized) {
			// Update the XML mapping for this project cache.
			List list = new ArrayList(variables);
			list.addAll(libraries);
			includeCacheManager.includePathChanged(project, getAsCachedNames(list));
		}

	}

	/*
	 * Returns a new List that contains the values in the given list (path strings) as they should 
	 * be named in the file system when they are written as cache files.
	 * The returned list actually holds the cache file names (without the path). 
	 */
	private List getAsCachedNames(List list) {
		List newList = new ArrayList(list.size());
		DefaultCacheManager cacheManager = DefaultCacheManager.instance();
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof File) {
				newList.add(cacheManager.getSharedCacheFile(project, ((File) obj).getPath()).getName());
			} else if (obj instanceof IPath) {
				newList.add(cacheManager.getSharedCacheFile(project, ((IPath) obj).toFile().getPath()).getName());
			}
		}
		return newList;
	}

	private void addVariable(IPath variable) {
		if (variables.indexOf(variable) != -1) {
			return;
		}
		variables.add(variable);
		innerAddVariable(variable);
	}

	private void innerAddVariable(IPath variable) {
		updateExtentionList();
		String variableName = variable.toString();
		PHPIncludePathModel model = new PHPIncludePathModel(variableName, PHPIncludePathModel.TYPE_VARIABLE);
		PHPLanguageManager languageManager = PHPLanguageManagerProvider.instance().getPHPLanguageManager(phpVersion);
		ParserClient client = languageManager.createParserClient(model, project);

		File file = getVriableFile(variableName);

		if (file != null)
			if (file.isDirectory()) {
				PHPIncludePathModel cachedModel = new PHPIncludePathModel(variableName, PHPIncludePathModel.TYPE_VARIABLE);
				DefaultCacheManager.instance().load(project, cachedModel, true);
				recursiveParse(file, client, model, cachedModel);
			} else {
				String fileName = file.getName();
				if (isPhpFile(fileName)) {
					PHPIncludePathModel cachedModel = new PHPIncludePathModel(variableName, PHPIncludePathModel.TYPE_VARIABLE);
					DefaultCacheManager.instance().load(project, cachedModel, true);
					PHPFileData fileData = cachedModel.getFileData(fileName);
					// If the file is cached, update the model with the cached version.
					if (isValid(fileData, file)) {
						model.insert(fileData);
						cachedModel.delete(fileName);
					} else {
						// add the model to the list of need-to-cache models
						modelsToCache.put(model, model);
						parse(file, client);
					}
				}
			}
		compositePhpModel.addModel(model);
	}

	private File getVriableFile(String variableName) {
		int index = variableName.indexOf('/');
		String extention = ""; //$NON-NLS-1$
		if (index != -1) {
			if (index + 1 < variableName.length()) {
				extention = variableName.substring(index + 1);
			}
			variableName = variableName.substring(0, index);
		}
		IPath path = PHPProjectOptions.getIncludePathVariable(variableName);
		if (path == null)
			return null;
		path = path.append(extention);
		return path.toFile();
	}

	private void removeVariable(IPath variable) {
		if (variables.remove(variable)) {
			innerRemoveVariable(variable);
		}
	}

	private void innerRemoveVariable(IPath variable) {
		compositePhpModel.remove(variable.toString());
	}

	private class IncludePathListener implements IPhpProjectOptionChangeListener {

		public void notifyOptionChanged(Object oldOption, Object newOption) {
			IIncludePathEntry[] entries = (IIncludePathEntry[]) newOption;
			loadChanges(entries);
		}
	}

	private class PhpVersionListener implements IPreferencesPropagatorListener {

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String newVersion = (String) event.getNewValue();
			String oldVersion = (String) event.getOldValue();
			if (oldVersion == null) {
				// If we got an old version of null, then we moved from the worspace setting to the project-specific settings and
				// we need to get an old valid value, which is the value of the workspace PHP version.
				oldVersion = PreferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.PHP_OPTIONS_PHP_VERSION, PHPCorePlugin.getDefault().getPreferenceStore());
			}
			updatePHPVersion(oldVersion, newVersion);
		}

		public IProject getProject() {
			return PHPIncludePathModelManager.this.project;
		}
	}

	private class ModelWrapper extends PhpModelProxy {
		String id;
		IWorkspaceModelListener listener;

		ModelWrapper(IPhpModel model) {
			this.model = model;
		}

		public String getID() {
			return id;
		}

		public void initialize(IProject project) {
			id = project.getName();
			initListeners(project);
		}

		public void dispose() {
			PHPWorkspaceModelManager.getInstance().removeWorkspaceModelListener(id, listener);
		}

		private void initListeners(IProject project) {
			listener = new WorkspaceModelListener();
			PHPWorkspaceModelManager.getInstance().addWorkspaceModelListener(project.getName(), listener);
		}

		private void updateModelWrapper(IPhpModel model) {
			this.model = model;
		}

		private class WorkspaceModelListener implements IWorkspaceModelListener {

			public void projectModelAdded(IProject project) {
				updateModel(project);
			}

			public void projectModelRemoved(IProject project) {
				IPhpModel model = new PHPUserModel();
				updateModelWrapper(model);
			}

			public void projectModelChanged(IProject project) {
				updateModel(project);
			}

			private void updateModel(IProject project) {
				IPhpProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
				IPhpModel userModel = (projectModel == null) ? new PHPUserModel() : projectModel.getModel(PHPUserModel.ID);

				updateModelWrapper(userModel);
			}
		}
	}

	/*
	 * A listener that handles project deletion.
	 */
	private class ProjectResourceChangeListener implements IResourceChangeListener {

		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.PRE_DELETE) {
				handleProjectDeletion(event.getResource());
			}
		}

		private void handleProjectDeletion(IResource resource) {
			if (resource == project) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(projectResourcesListener);
				projectResourcesListener = null;
				includeCacheManager.projectRemoved((IProject) resource);
			} else {
				removeProject(resource);

				PHPProjectOptions options = PHPProjectOptions.forProject(resource.getProject());
				options.removeResourceFromIncludePath(resource);
			}
		}
	}

	public Object getExternalResource(PHPFileData fileData) {
		IPhpModel[] models = compositePhpModel.getModels();
		for (int i = 0; i < models.length; i++) {
			if (contains(models[i], fileData)) {
				String resourceName = models[i].getID();
				Path path = new Path(resourceName);
				if (variables.indexOf(path) != -1) {
					File variableFile = getVriableFile(resourceName);
					if (variableFile == null)
						return null;
					File file = new File(fileData.getName());
					if (variableFile.isDirectory()) {
						return file;
					}
//					if (variableFile.getName().endsWith(".zip")) {
//						try {
//							return new ZipFile(variableFile);
//						} catch (ZipException e) {
//							Logger.logException(e);
//						} catch (IOException e) {
//							Logger.logException(e);
//						}
//					}
				}
				File file = new File(resourceName);
				if (getIndexOf(file) != -1) {
					return new File(fileData.getName());
				}
//				if (getIndexOf(file, zips) != -1) {
//					try {
//						return new ZipFile(file);
//					} catch (ZipException e) {
//						Logger.logException(e);
//					} catch (IOException e) {
//						Logger.logException(e);
//					}
//				}
				return null;
			}
		}
		return null;
	}

	private boolean contains(IPhpModel model, PHPFileData fileData) {
		return (model.getFileData(fileData.getName()) != null);
	}
}
