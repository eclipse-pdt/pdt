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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

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

	public static final String COMPOSITE_INCLUDE_PATH_MODEL_ID = "CompositeIncludePathModel"; //$NON-NLS-1$
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
	private List<IncludePathModelListener> modelListeners;

	public PHPIncludePathModelManager() {
		compositePhpModel = new CompositePhpModel() {
			public String getID() {
				return COMPOSITE_INCLUDE_PATH_MODEL_ID;
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

	/**
	 * Adds listener of model addition/removal events
	 * @param listener
	 */
	public void addIncludePathModelListener(IncludePathModelListener listener) {
		if (modelListeners == null) {
			modelListeners = new ArrayList<IncludePathModelListener>(1); // for now by default only one listener exists - IncludeNode from UI
			modelListeners.add(listener);
		} else if (!modelListeners.contains(listener)) {
			modelListeners.add(listener);
		}
	}

	/**
	 * Removes listener of model addition/removal events
	 * @param listener
	 */
	public void removeIncludePathModelListener(IncludePathModelListener listener) {
		if (modelListeners == null) {
			return;
		} else {
			modelListeners.remove(listener);
		}
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

	/**
	 * fires model removal event
	 * @param model
	 */
	private void fireModelAdded(IPhpModel model) {
		if(modelListeners == null)
			return;
		for (IncludePathModelListener listener : modelListeners) {
			listener.includeModelAdded(model);
		}
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
		fireModelAdded(model);
	}

	private void recursiveParse(File folder, ParserClient client, PHPIncludePathModel model, PHPIncludePathModel cachedModel) {
		if (folder == null) {
			return;
		}
		File[] children = folder.listFiles();
		if (children == null) {
			return;
		}

		for (File file : children) {
			if (file.isDirectory()) {
				recursiveParse(file, client, model, cachedModel);
			} else if (file.isFile()) {
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
			final FileReader fileReader = new FileReader(file);
			parserManager.parseNow(fileReader, file.getPath(), file.lastModified(), client, new Pattern[0], UseAspTagsHandler.useAspTagsAsPhp(project));

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
		for (String element : validExtensions) {
			if (ext.equals(element)) {
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
			fireModelRemoved(removed);
		}
	}

	/**
	 * Fires model removal event
	 * @param removed
	 */
	private void fireModelRemoved(IPhpModel removed) {
		if(modelListeners == null)
			return;
		for (IncludePathModelListener listener : modelListeners) {
			listener.includeModelRemoved(model);
		}
	}

	private void updatePHPVersion(String oldVersion, String newVersion) {
		setPHPVersion(newVersion);
		includeCacheManager.phpVersionChanged(project, oldVersion, newVersion);
		// All the models that this include path model has to be re-cached
		// Any model that already exists is skipped.
		DefaultCacheManager cacheManager = DefaultCacheManager.instance();
		IPhpModel[] models = compositePhpModel.getModels();
		for (IPhpModel element : models) {
			File cacheFile = cacheManager.getSharedCacheFile(newVersion, element.getID());
			if (!cacheFile.exists()) {
				modelsToCache.put(element, element);
			} else {
				modelsToCache.remove(element);
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

		for (File element : oldLibrariesList) {
			String currPath = element.getPath();
			boolean found = false;
			for (File element2 : newLibrariesList) {
				if (element2.getPath().equals(currPath)) {
					found = true;
					break;
				}
			}
			if (!found) {
				removeLibrary(element);
			}
		}
	}

	private void projectListChanged(IResource[] projects) {
		for (IResource element : projects) {
			addProject(element);
		}

		IResource[] oldProjectsList = new IResource[this.projects.size()];
		this.projects.toArray(oldProjectsList);

		for (IResource element : oldProjectsList) {
			String currName = element.getName();
			boolean found = false;
			for (IResource element2 : projects) {
				if (element2.getName().equals(currName)) {
					found = true;
					break;
				}
			}
			if (!found) {
				removeProject(element);
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

	public boolean removeProject(IResource project) {
		int index = getIndexOf(project);
		if (index == -1) {
			return false;
		}
		projects.remove(index);
		innerRemoveProject(project);
		return true;
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
			ResourcesPlugin.getWorkspace().addResourceChangeListener(projectResourcesListener, IResourceChangeEvent.POST_CHANGE);
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

	public void clear() {
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

		for (IIncludePathEntry element : entries) {
			if (element.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
				if (element.getContentKind() == IIncludePathEntry.K_BINARY) {
					// do nothing, support for zips was removed
				} else {
					liberaries.add(element);
				}
			} else if (element.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
				if (element.getResource() != null) { // if project exists
					projectsList.add(element);
				}
			} else if (element.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
				variablesList.add(element);
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

		for (IPath element : paths) {
			addVariable(element);
		}
		IPath[] oldVariablesList = new IPath[variables.size()];
		variables.toArray(oldVariablesList);

		for (IPath currVar : oldVariablesList) {
			boolean found = false;
			for (IPath element : paths) {
				if (element.equals(currVar)) {
					found = true;
					break;
				}
			}
			if (!found) {
				removeVariable(currVar);
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
		fireModelAdded(model);
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
		IPhpModel model = compositePhpModel.remove(variable.toString());
		if (model != null)
			fireModelRemoved(model);
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
			}

			private void updateModel(IProject project) {
				IPhpProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
				IPhpModel userModel = projectModel == null ? new PHPUserModel() : projectModel.getModel(PHPUserModel.ID);

				updateModelWrapper(userModel);
			}
		}
	}

	/*
	 * A listener that handles project deletion.
	 */
	private class ProjectResourceChangeListener implements IResourceChangeListener {

		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE && event.getSource() instanceof IWorkspace) {
				IResourceDelta[] affectedChildren = event.getDelta().getAffectedChildren();
				for (IResourceDelta element : affectedChildren) {
					if ((element.getFlags() & IResourceDelta.MOVED_TO) != 0) {
						IProject projectMovedFrom = (IProject) element.getResource();
						IProject projectMovedTo = ResourcesPlugin.getWorkspace().getRoot().getProject(element.getMovedToPath().lastSegment());
						handleProjectRename(projectMovedFrom, projectMovedTo);
					} else if (element.getKind() == IResourceDelta.REMOVED) {
						IProject removedProject = (IProject) element.getResource();
						handleProjectDeletion(removedProject);
					}

				}
			}
		}

		private void handleProjectRename(IProject from, IProject to) {
			if (from == project) {
				handleProjectDeletion(from);
			} else {
				boolean removed = removeProject(from);
				if (removed) {
					addProject(to);
				}

				PHPProjectOptions options = PHPProjectOptions.forProject(project);
				if (options == null)
					return;

				options.renameResourceAtIncludePath(from, to);
			}
		}

		private void handleProjectDeletion(IResource resource) {
			if (resource == project) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(projectResourcesListener);
				projectResourcesListener = null;
				includeCacheManager.projectRemoved((IProject) resource);
			} else {
				removeProject(resource);

				PHPProjectOptions options = PHPProjectOptions.forProject(project);
				if (options == null)
					return;
				options.removeResourceFromIncludePath(resource);
			}
		}
	}

	public Object getExternalResource(PHPFileData fileData) {
		IPhpModel[] models = compositePhpModel.getModels();
		for (IPhpModel element : models) {
			if (contains(element, fileData)) {
				String resourceName = element.getID();
				Path path = new Path(resourceName);
				if (variables.indexOf(path) != -1) {
					File variableFile = getVriableFile(resourceName);
					if (variableFile == null)
						return null;
					File file = new File(fileData.getName());
					if (variableFile.isDirectory()) {
						return file;
					}
				}
				File file = new File(resourceName);
				if (getIndexOf(file) != -1) {
					return new File(fileData.getName());
				}
				return null;
			}
		}
		return null;
	}

	private boolean contains(IPhpModel model, PHPFileData fileData) {
		return model.getFileData(fileData.getName()) != null;
	}

	/**
	 * @param entry
	 * @param projectModel
	 * @return
	 */
	public static PHPUserModel getUserModelForIncludeEntry(IIncludePathEntry entry, PHPProjectModel projectModel) {
		PHPUserModel userModel = null;
		PHPIncludePathModelManager includeManager = (PHPIncludePathModelManager) projectModel.getModel(COMPOSITE_INCLUDE_PATH_MODEL_ID);
		if (includeManager == null)
			return null;
		if (entry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
			userModel = (PHPUserModel) includeManager.getModel(entry.getPath().toString());
		} else if (entry.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
			userModel = (PHPUserModel) includeManager.getModel(entry.getPath().toOSString());
		} else if (entry.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
			PHPProjectModel modelForProject = PHPWorkspaceModelManager.getInstance().getModelForProject((IProject) entry.getResource());
			if (modelForProject != null)
				userModel = modelForProject.getPHPUserModel();
		}
		return userModel;
	}
}
