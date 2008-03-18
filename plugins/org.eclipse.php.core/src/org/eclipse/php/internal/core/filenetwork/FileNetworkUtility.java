package org.eclipse.php.internal.core.filenetwork;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.mixin.IncludeField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.util.PHPSearchEngine;
import org.eclipse.php.internal.core.util.PHPSearchEngine.*;

public class FileNetworkUtility {

	/**
	 * Analyzes file dependences, and builds tree of all source modules that reference the given source module.
	 * @param file Source module
	 * @return reference tree
	 */
	public static ReferenceTreeNode buildReferencingFilesTree(ISourceModule file) {
		ReferenceTreeNode tree = new ReferenceTreeNode(file);

		HashSet<ISourceModule> processedFiles = new HashSet<ISourceModule>();
		processedFiles.add(file);

		internalBuildReferencingFilesTree(tree, processedFiles);
		return tree;
	}

	private static void internalBuildReferencingFilesTree(ReferenceTreeNode root, Set<ISourceModule> processedFiles) {

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
				internalBuildReferencingFilesTree(child, processedFiles);
			}
		}
	}

	/**
	 * Analyzes file dependences, and builds tree of all source modules, which are referenced by the given source module.
	 * @param file Source module
	 * @return reference tree
	 */
	public static ReferenceTreeNode buildReferencedFilesTree(ISourceModule file) {
		ReferenceTreeNode tree = new ReferenceTreeNode(file);
		// XXX: implement me
		return tree;
	}

	private static void internalBuildReferencedFilesTree(ReferenceTreeNode root, Set<ISourceModule> processedFiles) {

		final ISourceModule sourceModule = root.getFile();
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
}
