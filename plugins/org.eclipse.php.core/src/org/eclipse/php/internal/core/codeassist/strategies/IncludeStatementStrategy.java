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
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ScriptFolder;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.IncludeStatementContext;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;

/**
 * This strategy completes resources (both folders and files) that are 
 * available to the include statement.
 * {@link IncludeStatementContext}
 */
public class IncludeStatementStrategy extends AbstractCompletionStrategy {
	
	public final static String FOLDER_SEPARATOR = "/"; //$NON-NLS-1$

	public IncludeStatementStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws Exception {
		ICompletionContext context = getContext();
		if (!(context instanceof IncludeStatementContext)) {
			return;
		}

		IncludeStatementContext includeContext = (IncludeStatementContext) context;
		String prefix = includeContext.getPrefix();
		SourceRange replaceRange = getReplacementRange(includeContext);

		final ISourceModule sourceModule = includeContext.getSourceModule();
		if (sourceModule == null || sourceModule.getScriptProject() == null) {
			if (DLTKCore.DEBUG_COMPLETION) {
				System.out.println("Unable to locate source module or project"); //$NON-NLS-1$
			}
			return;
		}

		final IScriptProject scriptProject = sourceModule.getScriptProject();
		final IncludePath[] includePaths = IncludePathManager.getInstance().getIncludePaths(scriptProject.getProject());

		for (IncludePath includePath : includePaths) {
			visitEntry(includePath, prefix, reporter, replaceRange, sourceModule.getScriptProject());
		}

	}

	private void visitEntry(IncludePath includePath, String prefix, ICompletionReporter reporter, SourceRange replaceRange, IScriptProject project) {
		// the root entry of this element
		final Object entry = includePath.getEntry();

		final IPath prefixPath = new Path(prefix);
		IPath prefixPathFolder = prefixPath;
		IPath lastSegmant = new Path("");
		if (prefixPath.segmentCount() != 0 && !prefix.endsWith("\\") && !prefix.endsWith("/")) {
			prefixPathFolder = prefixPath.removeLastSegments(1);
			lastSegmant = new Path(prefixPath.lastSegment());
		}
		try {
			if (!includePath.isBuildpath()) {
				addInternalEntries(reporter, replaceRange, entry, prefixPathFolder, lastSegmant);
			} else {
				addExternalEntries(reporter, replaceRange, project, entry, prefixPathFolder, lastSegmant);
			}
		} catch (CoreException e) {
			Logger.logException(e);

		}
	}

	private void addExternalEntries(ICompletionReporter reporter, SourceRange replaceRange, IScriptProject project, final Object entry, IPath prefixPathFolder, IPath lastSegmant) throws ModelException {
		switch (((IBuildpathEntry) entry).getEntryKind()) {
			case IBuildpathEntry.BPE_CONTAINER:
				final IProjectFragment[] findProjectFragments = project.findProjectFragments((IBuildpathEntry) entry);
				for (IProjectFragment projectFragment : findProjectFragments) {
					
					// add folders 
					IModelElement[] children = projectFragment.getChildren();
					for (IModelElement element : children) {
						if (element instanceof ScriptFolder) {
							final IPath relative = ((ScriptFolder) element).getRelativePath();
							if (relative.segmentCount() != 0 && isLastSegmantPrefix(lastSegmant, relative) && isPathPrefix(prefixPathFolder, relative)) {
								reporter.reportResource(element, relative, getSuffix(element), replaceRange);
							}
						}
					}

					// add files
					final IScriptFolder scriptFolder = projectFragment.getScriptFolder(prefixPathFolder);
					children = scriptFolder.getChildren();
					for (IModelElement element : children) {
						final IPath relative = element.getPath().makeRelativeTo(projectFragment.getPath());
						if (isLastSegmantPrefix(lastSegmant, relative)) {
							reporter.reportResource(element, relative, getSuffix(element), replaceRange);
						}
					}
				}
				break;
			default:

		}
	}

	private void addInternalEntries(ICompletionReporter reporter, SourceRange replaceRange, final Object entry, IPath prefixPathFolder, IPath lastSegmant) throws CoreException {
		IContainer container = (IContainer) entry;
		if (prefixPathFolder.segmentCount() > 0) {
			container = container.getFolder(prefixPathFolder);
		}
		
		if (!container.exists()) {
			return;
		}
		
		ICompletionContext context = getContext();
		IResource[] members = container.members();
		for (IResource resource : members) {
			final IPath relative = resource.getFullPath().makeRelativeTo(container.getFullPath());
			if (isLastSegmantPrefix(lastSegmant, relative)) {
				final IPath rel = resource.getFullPath().makeRelativeTo(((IContainer) entry).getFullPath());
				final IModelElement modelElement = DLTKCore.create(resource);
				if (resource.getType() == IResource.FILE) {
					if (PHPToolkitUtil.isPhpFile((IFile) resource) && !modelElement.equals(((IncludeStatementContext)context).getSourceModule())) {
						reporter.reportResource(modelElement, rel, getSuffix(modelElement), replaceRange);
					}
				} else {
					if (resource.getName().charAt(0) != '.') { // filter dot resources
						reporter.reportResource(modelElement, rel, getSuffix(modelElement), replaceRange);
					}
				}
			}
		}
	}

	private boolean isPathPrefix(IPath prefixPath, IPath path) {
		if (prefixPath.segmentCount() != path.segmentCount() - 1) {
			return false;
		}

		return prefixPath.isPrefixOf(path);
	}

	private boolean isLastSegmantPrefix(IPath prefixPath, IPath relative) {
		String lastCurrentSegment = relative.lastSegment();
		String lastPrefixSegment = prefixPath.lastSegment();

		if (lastCurrentSegment == null) {
			lastCurrentSegment = ""; //$NON-NLS-1$
		}
		if (lastPrefixSegment == null) {
			lastPrefixSegment = ""; //$NON-NLS-1$
		}
		if (CodeAssistUtils.startsWithIgnoreCase(lastCurrentSegment, lastPrefixSegment)) {
			return true;
		}

		return false;
	}

	public String getSuffix(IModelElement modelElement) {
		return modelElement.getElementType() == IModelElement.SOURCE_MODULE ? "" : FOLDER_SEPARATOR; //$NON-NLS-1$ 
	}

}
