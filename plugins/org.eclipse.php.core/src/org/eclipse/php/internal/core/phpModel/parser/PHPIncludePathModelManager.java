/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.phpModel.parser.PHPIncludePathModel.IncludePathModelType;
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

	private List<File> libraries;
	private List<IContainer> containers;
	private List<IPath> variables;
	private String[] validExtensions;
	private IncludePathListener includePathListener;
	private PhpVersionListener phpVersionListener;
	private IProject project;
	private Set<IPhpModel> modelsToCache;
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
		libraries = new ArrayList<File>();
		containers = new ArrayList<IContainer>();
		variables = new ArrayList<IPath>();
		modelsToCache = new HashSet<IPhpModel>();
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
		}
		modelListeners.remove(listener);
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
		if (modelListeners == null)
			return;
		for (IncludePathModelListener listener : modelListeners) {
			listener.includeModelAdded(model);
		}
	}

	private void innerAddLibrary(File library) {
		PHPIncludePathModel model = new PHPIncludePathModel(library.getPath(), IncludePathModelType.LIBRARY);

		PHPLanguageManager languageManager = PHPLanguageManagerProvider.instance().getPHPLanguageManager(phpVersion);
		ParserClient client = languageManager.createParserClient(model, project);

		updateExtentionList();
		if (library.exists()) {
			// Load the cache for the library
			PHPIncludePathModel cacheModel = new PHPIncludePathModel(library.getPath(), IncludePathModelType.LIBRARY);
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
						modelsToCache.add(model);
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
		if (modelListeners == null)
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
				modelsToCache.add(element);
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
		String libraryName = library.getPath();
		for (int index = 0; index < libraries.size(); ++index) {
			File existingLibrary = libraries.get(index);
			if (existingLibrary.getPath().equals(libraryName)) {
				return index;
			}
		}
		return -1;
	}

	public void includePathChanged(IPath[] paths) {
		File[] newLibrariesList = new File[paths.length];

		for (int i = 0; i < paths.length; i++) {
			newLibrariesList[i] = new File(paths[i].toString());
			addLibrary(newLibrariesList[i]);
		}
		List<File> olds = libraries;
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

	private void containersListChanged(IContainer[] containers) {
		for (IContainer element : containers) {
			addContainer(element);
		}

		IContainer[] oldContainers = new IContainer[this.containers.size()];
		this.containers.toArray(oldContainers);

		for (IContainer container : oldContainers) {
			IPath containerPath = container.getFullPath();
			boolean found = false;
			for (IContainer newContainer : containers) {
				if (newContainer.getFullPath().equals(containerPath)) {
					found = true;
					break;
				}
			}
			if (!found) {
				removeContainer(container);
			}
		}
	}

	private void addContainer(IContainer container) {
		if (getIndexOf(container) != -1) {
			return;
		}
		containers.add(container);
		innerAddContainer(container);
	}

	private void innerAddContainer(IContainer container) {
		IProject project = container.getProject();
		boolean forceModel = !project.exists();
		IPhpProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project, forceModel);
		IPhpModel userModel = null;
		if (projectModel == null) {
			userModel = new PHPUserModel();
		} else {
			userModel = projectModel.getModel(PHPUserModel.ID);
		}
		IPhpModel modelWrapper = new FolderFilteredUserModel((PHPUserModel) userModel, container);
		modelWrapper.initialize(project);
		compositePhpModel.addModel(modelWrapper);
	}

	public boolean removeContainer(IContainer container) {
		int index = getIndexOf(container);
		if (index == -1) {
			return false;
		}
		containers.remove(index);
		innerRemoveContainer(container);
		return true;
	}

	private void innerRemoveContainer(IContainer container) {
		String modelId = container.getFullPath().toString();
		IPhpModel removed = compositePhpModel.remove(modelId);
		if (removed != null) {
			((FolderFilteredUserModel) removed).dispose();
		}
	}

	private int getIndexOf(IContainer container) {
		IPath containerPath = container.getFullPath();
		for (int index = 0; index < containers.size(); ++index) {
			IResource existingContainer = containers.get(index);
			if (existingContainer.getFullPath().equals(containerPath)) {
				return index;
			}
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
			for (IPhpModel model : modelsToCache) {
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

	private void rebuildList(List<File> list) {
		for (File library : list) {
			innerRemoveLibrary(library);
			innerAddLibrary(library);
		}
	}

	private void rebuildVariablesList() {
		for (IPath variable : variables) {
			innerRemoveVariable(variable);
			innerAddVariable(variable);
		}
	}

	public void loadChanges(IIncludePathEntry[] entries) {
		List<IIncludePathEntry> liberaries = new ArrayList<IIncludePathEntry>();
		List<IIncludePathEntry> containersList = new ArrayList<IIncludePathEntry>();
		List<IIncludePathEntry> variablesList = new ArrayList<IIncludePathEntry>();

		for (IIncludePathEntry element : entries) {
			if (element.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
				if (element.getContentKind() == IIncludePathEntry.K_BINARY) {
					// do nothing, support for archives was removed
				} else {
					liberaries.add(element);
				}
			} else if (element.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
				if (element.getResource() != null) { // if project exists
					containersList.add(element);
				}
			} else if (element.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
				variablesList.add(element);
			}
		}
		IPath[] paths = new IPath[liberaries.size()];
		for (int i = 0; i < liberaries.size(); i++) {
			paths[i] = liberaries.get(i).getPath();
		}

		includePathChanged(paths);

		IContainer[] containers = new IContainer[containersList.size()];
		for (int i = 0; i < containersList.size(); i++) {
			containers[i] = (IContainer) containersList.get(i).getResource();
		}

		containersListChanged(containers);

		IPath[] variablespaths = new IPath[variablesList.size()];
		for (int i = 0; i < variablesList.size(); i++) {
			variablespaths[i] = variablesList.get(i).getPath();
		}

		variablesListChanged(variablespaths);

		updateOrder(entries);
	}

	/**
	 * @param entries
	 */
	private void updateOrder(IIncludePathEntry[] entries) {
		for (IIncludePathEntry entry : entries) {
			IPhpModel model = compositePhpModel.remove(entry.getPath().toString());
			if (model == null) {
				// for windows directories:
				model = compositePhpModel.remove(entry.getPath().toOSString());
			}
			if (model != null) {
				compositePhpModel.addModel(model);
			}
		}
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
		PHPIncludePathModel model = new PHPIncludePathModel(variableName, IncludePathModelType.VARIABLE);
		PHPLanguageManager languageManager = PHPLanguageManagerProvider.instance().getPHPLanguageManager(phpVersion);
		ParserClient client = languageManager.createParserClient(model, project);

		File file = getVriableFile(variableName);

		if (file != null)
			if (file.isDirectory()) {
				PHPIncludePathModel cachedModel = new PHPIncludePathModel(variableName, IncludePathModelType.VARIABLE);
				DefaultCacheManager.instance().load(project, cachedModel, true);
				recursiveParse(file, client, model, cachedModel);
			} else {
				String fileName = file.getName();
				if (isPhpFile(fileName)) {
					PHPIncludePathModel cachedModel = new PHPIncludePathModel(variableName, IncludePathModelType.VARIABLE);
					DefaultCacheManager.instance().load(project, cachedModel, true);
					PHPFileData fileData = cachedModel.getFileData(fileName);
					// If the file is cached, update the model with the cached version.
					if (isValid(fileData, file)) {
						model.insert(fileData);
						cachedModel.delete(fileName);
					} else {
						// add the model to the list of need-to-cache models
						modelsToCache.add(model);
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
				oldVersion = PreferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.PHP_OPTIONS_PHP_VERSION, PHPCorePlugin.getDefault().getPluginPreferences());
			}
			updatePHPVersion(oldVersion, newVersion);
		}

		public IProject getProject() {
			return PHPIncludePathModelManager.this.project;
		}
	}

	/*
	 * A listener that handles project deletion.
	 */
	private class ProjectResourceChangeListener implements IResourceChangeListener {

		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				IResourceDelta delta = event.getDelta();
				try {
					delta.accept(new IResourceDeltaVisitor() {
						public boolean visit(IResourceDelta delta) {
							if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0 && delta.getResource() instanceof IContainer) {
								IContainer containerMovedFrom = (IContainer) delta.getResource();
								IContainer containerMovedTo = (IContainer) ResourcesPlugin.getWorkspace().getRoot().findMember(delta.getMovedToPath());
								handleContainerRename(containerMovedFrom, containerMovedTo);
								return false;
							} else if (delta.getKind() == IResourceDelta.REMOVED && delta.getResource() instanceof IContainer) {
								IContainer removedContainer = (IContainer) delta.getResource();
								handleContainerDeletion(removedContainer);
								return false;
							}
							return true;
						}
					});
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void handleContainerRename(IContainer from, IContainer to) {
			IPath fromPath = from.getFullPath();
			IPath toPath = to.getFullPath();
			int matchingToSegments = toPath.matchingFirstSegments(fromPath);
			PHPProjectOptions options = PHPProjectOptions.forProject(project);
			if (options == null) {
				return;
			}

			for (IContainer container : new ArrayList<IContainer>(containers)) {
				IPath containerPath = container.getFullPath();
				int matchingContainerSegments = fromPath.matchingFirstSegments(containerPath);
				if (matchingContainerSegments >= matchingToSegments + 1) {
					int containerIndex = getIndexOf(container);
					if (containerIndex > -1) {
						IPath newContainerPath = fromPath.removeLastSegments(fromPath.segmentCount() - matchingToSegments).append(toPath.removeFirstSegments(matchingToSegments)).append(containerPath.removeFirstSegments(matchingContainerSegments));
						IResource newResource = ResourcesPlugin.getWorkspace().getRoot().findMember(newContainerPath);
						if (newResource instanceof IContainer) {
							IContainer newContainer = (IContainer) newResource;
							addContainer(newContainer);
							removeContainer(container);
							options.renameContainerAtIncludePath(container, newContainer);
						}
					}
				}
			}
			if (from == project) {
				handleContainerDeletion(from);
			}
		}

		private void handleContainerDeletion(IContainer oldContainer) {
			if (oldContainer == project) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(projectResourcesListener);
				projectResourcesListener = null;
				includeCacheManager.projectRemoved((IProject)oldContainer);
			} else {
				PHPProjectOptions options = PHPProjectOptions.forProject(project);

				if (options == null)
					return;

				IPath oldContainerPath = oldContainer.getFullPath();
				for (IContainer container : new ArrayList<IContainer>(containers)) {
					IPath containerPath = container.getFullPath();
					int matchingContainerSegments = oldContainerPath.matchingFirstSegments(containerPath);
					if (matchingContainerSegments > 0) {
						boolean removed = removeContainer(container);
						if (removed) {
							options.removeContainerFromIncludePath(oldContainer);
						}
					}
				}

				removeContainer(oldContainer);

				options.removeContainerFromIncludePath(oldContainer);
			}
		}
	}

	public Object getExternalResource(PHPFileData fileData) {
		IPhpModel[] models = compositePhpModel.getModels();
		for (IPhpModel model : models) {
			if (contains(model, fileData)) {
				String modelId = model.getID();
				Path modelPath = new Path(modelId);
				if (variables.indexOf(modelPath) != -1) {
					File variableFile = getVriableFile(modelId);
					if (variableFile == null)
						return null;
					File file = new File(fileData.getName());
					if (variableFile.isDirectory()) {
						return file;
					}
				}
				File file = new File(modelId);
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
	 */
	public static IPHPUserModel getUserModelForIncludeEntry(IIncludePathEntry entry, PHPProjectModel projectModel) {
		IPHPUserModel userModel = null;
		PHPIncludePathModelManager includeManager = (PHPIncludePathModelManager) projectModel.getModel(COMPOSITE_INCLUDE_PATH_MODEL_ID);
		if (includeManager == null)
			return null;
		if (entry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
			userModel = (IPHPUserModel) includeManager.getModel(entry.getPath().toString());
		} else if (entry.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
			userModel = (IPHPUserModel) includeManager.getModel(entry.getPath().toOSString());
		} else if (entry.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
			IResource resource = entry.getResource();
			if (resource == null) {
				return null;
			}
			userModel = (IPHPUserModel) includeManager.getModel(resource.getFullPath().toString());
		}
		return userModel;
	}
}
