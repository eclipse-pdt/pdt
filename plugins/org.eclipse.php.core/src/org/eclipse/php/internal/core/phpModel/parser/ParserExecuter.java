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

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.php.internal.core.Logger;

/**
 * This class handles the execution of a parser
 * @author Roy Ganor, 2006
 */
public final class ParserExecuter implements Runnable {

	// we want that the executor will run exclusively
	private final static Object mutex = new Object();

	public final PHPParserManager parserManager;
	public final ParserClient client;
	public final String filename;
	private final Reader reader;
	public final long lastModified;
	public final Pattern[] tasksPatterns;
	public final boolean useAspTagsAsPhp;
	private PhpParser phpParser; // maybe we should re-create the parser

	public ParserExecuter(PHPParserManager parserManager, ParserClient client, String filename, Reader reader, Pattern[] tasksPatterns, long lastModified, boolean useAspTagsAsPhp) {
		this.parserManager = parserManager;
		this.client = client;
		this.filename = filename;
		this.reader = reader;
		this.tasksPatterns = tasksPatterns;
		this.lastModified = lastModified;
		this.useAspTagsAsPhp = useAspTagsAsPhp;
	}

	/**
	 * The parsing action - in a separate (async) thread
	 * The mutex keeps that only one parsing will be performed
	 */
	public final void run() {

		IWorkspaceRunnable runner = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					final CompletionLexer lexer = parserManager.createCompletionLexer(reader);
					lexer.setUseAspTagsAsPhp(useAspTagsAsPhp);
					lexer.setParserClient(client);
					lexer.setTasksPatterns(tasksPatterns);

					if (phpParser == null) {
						phpParser = parserManager.createPhpParser(lexer);
					}
					phpParser.setScanner(lexer);
					phpParser.setParserClient(client);

					client.startParsing(filename);

					phpParser.parse();

				} catch (Exception e) {
					Logger.logException(e);

				} finally {

					try {
						if (client != null && phpParser != null) {
							client.finishParsing(phpParser.getLength(), phpParser.getCurrentLine(), lastModified);
						}

					} catch (Exception ex) {
						Logger.logException(ex);

					} finally {
						try {
							reader.close();
						} catch (IOException exception) {
							Logger.logException(exception);
						}
					}
				}
			}
		};
		//		Job currentJob = Job.getJobManager().currentJob();
		try {
			//			if (currentJob.getThread() != Thread.currentThread()) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IResource resource = workspace.getRoot().findMember(new Path(filename));
			ISchedulingRule rule = resource != null ? resource.getProject() : workspace.getRoot();
			workspace.run(runner, rule, 0, null);
			//			} else {
			//				runner.run(null);
			//			}
		} catch (OperationCanceledException e) {
			// don't do anything, it's ok
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}
}
