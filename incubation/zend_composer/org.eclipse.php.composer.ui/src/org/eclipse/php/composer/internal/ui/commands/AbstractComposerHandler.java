/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.commands;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.internal.ui.ProjectUtils;
import org.eclipse.php.composer.ui.console.ConsoleCommandExecutor;
import org.eclipse.ui.PlatformUI;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public abstract class AbstractComposerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
			return null;
		}
		IContainer root = getProject(event);
		if (root != null && shouldPerform(root)) {
			Job resolveJob = getJob(new ComposerService(root, new ConsoleCommandExecutor()));
			resolveJob.setUser(true);
			resolveJob.schedule();
		}
		return null;
	}

	protected boolean shouldPerform(IContainer root) {
		return ProjectUtils.checkProject(root);
	}

	protected abstract Job getJob(ComposerService composer);

	private IContainer getProject(ExecutionEvent event) {
		Object ctxObj = event.getApplicationContext();
		if (ctxObj instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext) ctxObj;
			Object defaultVar = context.getDefaultVariable();
			if (defaultVar instanceof List<?>) {
				List<?> vars = (List<?>) defaultVar;
				for (Object var : vars) {
					if (var instanceof IScriptProject) {
						IScriptProject project = (IScriptProject) var;
						return (IContainer) project.getProject();
					} else if (var instanceof IFile) {
						IFile file = (IFile) var;
						return file.getProject();
					}
				}
			}
		}
		return null;
	}

}
