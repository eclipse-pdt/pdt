/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.IncludeStatementContext;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;

public class IncludeStatementStrategy extends AbstractCompletionStrategy {

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
		String suffix = getSuffix(includeContext);
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
			reportOnContainer(includePath, prefix, reporter, suffix, replaceRange);
		}

	}

	private void reportOnContainer(IncludePath includePath, String prefix, ICompletionReporter reporter, String suffix, SourceRange replaceRange) {
		
		final boolean buildpath = includePath.isBuildpath();
		final Object entry = includePath.getEntry();
		
		IPath prefixPath = new Path(prefix);
		if (!buildpath && entry instanceof IContainer) {
			IContainer container = (IContainer) entry;
			IResource[] members;
			try {
				IPath removeLastSegments = prefixPath;
				IPath lastSegmant = new Path("");
				if (prefixPath.segmentCount() != 0 && !prefix.endsWith("\\") && !prefix.endsWith("/")) {
					removeLastSegments = prefixPath.removeLastSegments(1);
					lastSegmant = new Path(prefixPath.lastSegment());
				}
				if (removeLastSegments.segmentCount() > 0) {
					container = container.getFolder(removeLastSegments);
				}
				
				members = container.members();
				for (IResource resource : members) {
					final IPath relative = resource.getFullPath().makeRelativeTo(container.getFullPath());
					if (isPrefix(lastSegmant, relative)) {
						if (resource.getType() == IResource.FILE) {
							if (PHPToolkitUtil.isPhpFile((IFile) resource)) {
								reporter.reportResource(resource, (IContainer) entry, suffix, replaceRange);
							}
						} else {
								reporter.reportResource(resource, (IContainer) entry, suffix, replaceRange);	
						}
					}
				}
			} catch (CoreException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean isPrefix(IPath prefixPath, IPath relative) {
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

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$ 
	}
	
	

}
