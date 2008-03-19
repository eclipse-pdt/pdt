package org.eclipse.php.internal.core.filenetwork;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.mixin.IncludeField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.util.PHPSearchEngine;
import org.eclipse.php.internal.core.util.PHPSearchEngine.*;

public class FileNetworkUtility {

	/**
	 * Analyzes file dependences, and builds tree of all source modules that reference the given source module.
	 * @param file Source module
	 * @param monitor Progress monitor
	 * @return reference tree
	 */
	public static ReferenceTreeNode buildReferencingFilesTree(ISourceModule file, IProgressMonitor monitor) {
		ReferenceTreeNode tree = new ReferenceTreeNode(file);

		HashSet<ISourceModule> processedFiles = new HashSet<ISourceModule>();
		processedFiles.add(file);

		internalBuildReferencingFilesTree(tree, processedFiles, monitor);
		return tree;
	}

	private static void internalBuildReferencingFilesTree(ReferenceTreeNode root, Set<ISourceModule> processedFiles, IProgressMonitor monitor) {

		ISourceModule file = root.getFile();

		// Find all includes to the current source module in mixin:
		IModelElement[] includes = PHPMixinModel.getInstance().getInclude(file.getPath().lastSegment());
		for (IModelElement e : includes) {
			IncludeField include = (IncludeField) e;

			// Candidate that includes the original source module:
			ISourceModule referencingFile = include.getSourceModule();

			// Try to resolve include:
			ISourceModule testFile = findSourceModule(referencingFile, include.getFilePath());

			// If this is the correct include (that means that included file is the original file):
			if (file.equals(testFile) && !processedFiles.contains(referencingFile)) {
				processedFiles.add(referencingFile);
				root.addChild(new ReferenceTreeNode(referencingFile));
			}
		}

		Collection<ReferenceTreeNode> children = root.getChildren();
		if (children != null) {
			for (ReferenceTreeNode child : children) {
				internalBuildReferencingFilesTree(child, processedFiles, monitor);
			}
		}
	}

	/**
	 * Analyzes file dependences, and builds tree of all source modules, which are referenced by the given source module.
	 * @param file Source module
	 * @param monitor Progress monitor
	 * @return reference tree
	 */
	public static ReferenceTreeNode buildReferencedFilesTree(ISourceModule file, IProgressMonitor monitor) {
		ReferenceTreeNode tree = new ReferenceTreeNode(file);

		HashSet<ISourceModule> processedFiles = new HashSet<ISourceModule>();
		processedFiles.add(file);

		try {
			internalBuildReferencedFilesTree(tree, processedFiles, monitor);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return tree;
	}

	private static void internalBuildReferencedFilesTree(ReferenceTreeNode root, Set<ISourceModule> processedFiles, IProgressMonitor monitor) throws CoreException {
		ISourceModule sourceModule = root.getFile();

		final List<String> includes = new LinkedList<String>();
		final IBuffer buffer = sourceModule.getBuffer();

		if (buffer != null) {
			IDLTKSearchScope scope = SearchEngine.createSearchScope(new IModelElement[] { sourceModule });
			SearchEngine engine = new SearchEngine();
			SearchPattern pattern = SearchPattern.createPattern("include", IDLTKSearchConstants.METHOD, IDLTKSearchConstants.REFERENCES, SearchPattern.R_EXACT_MATCH);
			engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					String text = buffer.getText(match.getOffset(), match.getLength());
					includes.add(stripQuotes(text));
				}
			}, monitor);
		}

		for (String filePath : includes) {
			ISourceModule testFile = findSourceModule(sourceModule, filePath);
			if (testFile != null && !processedFiles.contains(testFile)) {
				processedFiles.add(testFile);
				root.addChild(new ReferenceTreeNode(testFile));
			}
		}

		Collection<ReferenceTreeNode> children = root.getChildren();
		if (children != null) {
			for (ReferenceTreeNode child : children) {
				internalBuildReferencedFilesTree(child, processedFiles, monitor);
			}
		}
	}

	private static ISourceModule findSourceModule(ISourceModule from, String path) {
		ISourceModule sourceModule = null;

		IProject currentProject = from.getScriptProject().getProject();
		String currentScriptDir = from.getParent().getPath().toString();
		String currentWorkingDir = currentProject.getFullPath().toString();
		Result<?, ?> result = PHPSearchEngine.find(path, currentWorkingDir, currentScriptDir, currentProject);

		if (result instanceof ResourceResult) {
			// workspace file
			ResourceResult resResult = (ResourceResult) result;
			IResource resource = resResult.getFile();
			sourceModule = (ISourceModule) DLTKCore.create(resource);
		} else {
			// XXX: add support for external files
		}

		return sourceModule;
	}

	/**
	 * Strips single or double quotes from the start and from the end of the given string
	 * @param name String
	 * @return
	 */
	private static String stripQuotes(String name) {
		int len = name.length();
		if (len > 1 && (name.charAt(0) == '\'' && name.charAt(len - 1) == '\'' || name.charAt(0) == '"' && name.charAt(len - 1) == '"')) {
			name = name.substring(1, len - 1);
		}
		return name;
	}
}
