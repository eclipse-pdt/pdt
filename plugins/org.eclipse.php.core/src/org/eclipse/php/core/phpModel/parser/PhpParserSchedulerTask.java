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
package org.eclipse.php.core.phpModel.parser;

import java.io.Reader;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.eclipse.php.core.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This task handles the schedulaing of the model parser queue. <br>
 * The task can be suspended, resumed and stopped, depending on the que state.<br> 
 * Refer to the following link for more information about timing a thread: 
 * {@link http://java.sun.com/docs/books/tutorial/essential/threads/synchronization.html}
 * In addition the que is handled in a Mutual exclusion way, so no double reference is possible for the que  
 *    
 * @author Roy Ganor, 2006
 */
public class PhpParserSchedulerTask implements Runnable {

	// variable needed for the stop operation 
	private volatile boolean threadAlive = true;

	// a limit size for the parser stack 
	private static final int BUFFER_MAX_SIZE = 100;
	
	// holds the stack of tasks
	private final LinkedList buffer = new LinkedList();

	// this class is singleton - only one instance is allowed  
	protected static final PhpParserSchedulerTask instance = new PhpParserSchedulerTask();

	// needed for the parsing action (operates the parse async thread) 
	protected final Display display;

	/**
	 * Private constructor, only one instance is given
	 */
	private PhpParserSchedulerTask() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		display = workbench == null ? null : PlatformUI.getWorkbench().getDisplay();
	}

	/**
	 * @return the instance 
	 */
	public static PhpParserSchedulerTask getInstance() {
		return instance;
	}

	/**
	 * Run the consumer operation 
	 */
	public void run() {

		while (threadAlive) {
			try {
				// do a single operation of parsing
				final ParserExecuter release = release();

				// if release == null something with our stack lock mechanism is wrong!
				assert release != null;

				// do the job of parsing with the given information
				display.asyncExec(release);

				// waits until the parse action is done, no cross parsing is allowed
				release.join();

			} catch (InterruptedException e) {
				// thread was stoped or canceled...
				// just go out!
			}
		}
	}

	/**
	 * CONSUMER - Releases the first item in the stack 
	 * @throws InterruptedException 
	 * @throws InterruptedException 
	 */
	protected ParserExecuter release() throws InterruptedException {

		synchronized (buffer) {

			// pop a new parser task properties from stack
			while (buffer.size() == 0) {
				try {
					buffer.wait();
				} catch (InterruptedException e) {
					// process of releasing was canceled
					// ignore operation
				}
			}

			// get the next item
			final ParserExecuter item = (ParserExecuter) buffer.removeFirst();

			// notify that the stack is not full 
			buffer.notifyAll();

			return item;
		}

	}

	/**
	 * PRODUCER - Schedules a new operation for the parser stack
	 * @param phpParser
	 * @param client
	 * @param reader
	 * @param lastModified
	 */
	public void schedule(PHPParserManager parserManager, PhpParser phpParser, ParserClient client, String filename, Reader reader, Pattern[] tasksPatterns, long lastModified, boolean useAspTagsAsPhp) {

		synchronized (buffer) {

			// add it (saftly)
			// if the stack is full - wait() for an empty place 
			while (buffer.size() >= BUFFER_MAX_SIZE) {
				try {
					buffer.wait();
				} catch (InterruptedException e) {
					// process of scheduling was canceled
					// ignore operation
					return;
				}
			}

			// creates the new task properties
			final ParserExecuter parserProperties = new ParserExecuter(parserManager, phpParser, client, filename, reader, tasksPatterns, lastModified, useAspTagsAsPhp);

			buffer.addFirst(parserProperties);

			// now you can notify that the stack is not empty
			buffer.notifyAll();

		}
	}

	/**
	 * the parser properties holder and executer 
	 */
	private static class ParserExecuter extends Thread {

		private final PHPParserManager parserManager;
		private PhpParser phpParser; // maybe we should re-create the parser
		private final ParserClient client;
		private final String filename;
		private final Reader reader;
		private final long lastModified;
		private final Pattern[] tasksPatterns;
		private final boolean useAspTagsAsPhp;

		public ParserExecuter(PHPParserManager parserManager, PhpParser phpParser, ParserClient client, String filename, Reader reader, Pattern[] tasksPatterns, long lastModified, boolean useAspTagsAsPhp) {
			this.parserManager = parserManager;
			this.phpParser = phpParser;
			this.client = client;
			this.filename = filename;
			this.reader = reader;
			this.tasksPatterns = tasksPatterns;
			this.lastModified = lastModified;
			this.useAspTagsAsPhp = useAspTagsAsPhp;
		}

		/**
		 * The parsing action - in a seperate (async) thread 
		 * @throws InterruptedException 
		 */
		public void run() {
			try {
				final CompletionLexer lexer = parserManager.createCompletionLexer(reader);
				lexer.setUseAspTagsAsPhp(useAspTagsAsPhp);
				lexer.setParserClient(client);
				lexer.setTasksPatterns(tasksPatterns);

				if (phpParser == null) {
					phpParser = parserManager.createPhpParser();
				}
				phpParser.setScanner(lexer);
				phpParser.setParserClient(client);

				client.startParsing(filename);
				phpParser.parse();

			} catch (Exception e) {
				Logger.logException(e);


			} finally {
				if (client != null && phpParser != null) {
					client.finishParsing(phpParser.getLength(), phpParser.getCurrentLine(), lastModified);
				}
				
				try {
					reader.close();
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		}
	}
}
