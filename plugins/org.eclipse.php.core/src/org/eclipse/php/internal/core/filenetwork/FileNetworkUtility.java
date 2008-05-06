package org.eclipse.php.internal.core.filenetwork;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree.Node;
import org.eclipse.php.internal.core.mixin.IncludeField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.util.PHPSearchEngine;
import org.eclipse.php.internal.core.util.PHPSearchEngine.*;

/**
 * This utility is used for resolving reference dependencies between files.
 * Usage examples:
 * <p>I. Filter model elements that accessible from current source module:
 * <pre>
 * ReferenceTree referenceTree = FileNetworkUtility.buildReferencedFilesTree(currentSourceModule, null);
 * List&lt;IModelElement&gt; filteredElements = new LinkedList&lt;IModelElement&gt();
 * for (IModelElement element : elements) {
 *   if (referenceTree.find(element.getSourceModule()) {
 *     filteredElements.add(element);
 *   }
 * }
 * </pre>
 * </p>
 * <p>II. Find all files that reference current file and rebuild them
 * <pre>
 * ReferenceTree referenceTree = FileNetworkUtility.buildReferencingFilesTree(currentSourceModule, null);
 * LinkedList&lt;Node&gt; nodesQ = new  LinkedList&lt;Node&gt();
 * nodesQ.addFirst(referenceTree.getRoot());
 * while (!nodesQ.isEmpty()) {
 *   Node node = nodesQ.removeLast();
 *   rebuildFile (node.getFile());
 *   if (node.getChildren() != null) {
 *     for (Node child : node.getChildren()) {
 *       nodesQ.addFirst(child);
 *     }
 *   }
 * }
 * </pre>
 * </p>
 */
public class FileNetworkUtility {

	/**
	 * Analyzes file dependences, and builds tree of all source modules that reference the given source module.
	 * @param file Source module
	 * @param monitor Progress monitor
	 * @return reference tree
	 */
	public static ReferenceTree buildReferencingFilesTree(ISourceModule file, IProgressMonitor monitor) {

		HashSet<ISourceModule> processedFiles = new HashSet<ISourceModule>();
		processedFiles.add(file);

		Node root = new Node(file);
		internalBuildReferencingFilesTree(root, processedFiles, monitor);

		return new ReferenceTree(root);
	}

	private static void internalBuildReferencingFilesTree(Node root, Set<ISourceModule> processedFiles, IProgressMonitor monitor) {

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
				root.addChild(new Node(referencingFile));
			}
		}

		Collection<Node> children = root.getChildren();
		if (children != null) {
			for (Node child : children) {
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
	public static ReferenceTree buildReferencedFilesTree(ISourceModule file, IProgressMonitor monitor) {
		HashSet<ISourceModule> processedFiles = new HashSet<ISourceModule>();
		processedFiles.add(file);

		Node root = new Node(file);
		try {
			internalBuildReferencedFilesTree(root, processedFiles, monitor);
		} catch (CoreException e) {
			Logger.logException(e);
		}

		return new ReferenceTree(root);
	}

	private static void internalBuildReferencedFilesTree(Node root, Set<ISourceModule> processedFiles, IProgressMonitor monitor) throws CoreException {
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
				root.addChild(new Node(testFile));
			}
		}

		Collection<Node> children = root.getChildren();
		if (children != null) {
			for (Node child : children) {
				internalBuildReferencedFilesTree(child, processedFiles, monitor);
			}
		}
	}

	public static ISourceModule findSourceModule(ISourceModule from, String path) {
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
